package com.example.myapplication.hardware

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import com.bitmick.marshall.models.vmc_configuration
import com.bitmick.marshall.vmc.vmc_framework
import com.bitmick.marshall.vmc.vmc_link
import com.bitmick.marshall.vmc.vmc_vend_t
import com.digitalmediavending.hardware.nayax_sdk_utils.lowlevel_serial_ftdi
import com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.FtdiSerialDriver
import com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class PaymentState {
    IDLE,
    INITIALIZING,
    WAITING_FOR_CARD,
    PROCESSING,
    APPROVED,
    DECLINED,
    ERROR,
    CANCELLED
}

data class PaymentResult(
    val success: Boolean,
    val amount: Double? = null,
    val transactionId: String? = null,
    val error: String? = null
)

class NayaxPaymentManager(
    private val usbManager: UsbManager,
    private val usbDevice: UsbDevice,
    private val scope: CoroutineScope
) : vmc_vend_t.vend_callbacks_t, vmc_link.vmc_link_events_t {

    private val _paymentState = MutableStateFlow(PaymentState.IDLE)
    val paymentState: StateFlow<PaymentState> = _paymentState

    private val _paymentResult = MutableStateFlow<PaymentResult?>(null)
    val paymentResult: StateFlow<PaymentResult?> = _paymentResult

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    private var serialPort: UsbSerialPort? = null
    private var framework: vmc_framework? = null
    private var pendingSession: vmc_vend_t.vend_session_t? = null
    private var pendingAmountCents: Int = 0

    companion object {
        private const val TAG = "NayaxPayment"
    }

    fun initialize() {
        scope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "initialize() — VID=%04X PID=%04X".format(usbDevice.vendorId, usbDevice.productId))

                if (!usbManager.hasPermission(usbDevice)) {
                    Log.w(TAG, "No USB permission")
                    _paymentState.value = PaymentState.ERROR
                    _paymentResult.value = PaymentResult(false, error = "USB permission not granted")
                    return@launch
                }

                // Open the FTDI serial port using the bundled usb-serial driver
                val driver = FtdiSerialDriver(usbDevice)
                val connection = usbManager.openDevice(usbDevice)
                    ?: throw IllegalStateException("Failed to open USB device")

                val port = driver.ports[0]
                port.open(connection)
                serialPort = port
                Log.d(TAG, "FTDI serial port opened")

                // Reset singleton to avoid stale state from previous app sessions
                vmc_framework.reset()

                val config = vmc_configuration().apply {
                    port_vpos      = port
                    port_vpos_baud = HardwareService.NAYAX_BAUD_RATE
                    model          = "android-marshall-demo"
                    serial         = "1434324619381374"
                    sw_ver         = "1.0.0.0"
                    machine_type   = vmc_configuration.machine_type_type_retail
                    always_idle    = true   // Pre-Selection: price sent before card tap
                    // reader_always_on is forced true by vmc_link.start() when always_idle=true
                    multi_vend_support      = false  // true causes Auth Status -1 on most accounts
                    multi_session_support   = false
                    price_not_final_support = false
                    debug               = true
                    dump_packets_level  = 2
                }

                framework = vmc_framework.getInstance().also { fw ->
                    fw.link
                        .set_lowlevel(lowlevel_serial_ftdi())
                        .configure(config)
                        .set_events(this@NayaxPaymentManager)
                    fw.vend.register_callbacks(this@NayaxPaymentManager)
                    fw.start()
                }

                _paymentState.value = PaymentState.INITIALIZING
                Log.d(TAG, "Marshall SDK started — waiting for link-up")

            } catch (e: Exception) {
                Log.e(TAG, "initialize() failed: ${e.message}", e)
                _paymentState.value = PaymentState.ERROR
                _paymentResult.value = PaymentResult(false, error = e.message)
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    // vmc_link.vmc_link_events_t
    // ─────────────────────────────────────────────────────────────

    override fun onReady(config: vmc_link.vpos_config_t?) {
        Log.i(TAG, "Marshall SDK connected to Nayax VPOS Touch")
        _isReady.value = true
    }

    override fun onCommError() {
        Log.w(TAG, "Marshall SDK comm error / link down")
        _isReady.value = false
        if (_paymentState.value == PaymentState.WAITING_FOR_CARD ||
            _paymentState.value == PaymentState.PROCESSING) {
            _paymentState.value = PaymentState.ERROR
            _paymentResult.value = PaymentResult(false, error = "Reader disconnected")
        }
    }

    // ─────────────────────────────────────────────────────────────
    // vmc_vend_t.vend_callbacks_t
    // ─────────────────────────────────────────────────────────────

    override fun onReady(previousSession: vmc_vend_t.vend_session_t?) {
        Log.i(TAG, "Ready to accept payments!")
        // If a payment was pending and session ended, user cancelled on the VPOS
        if (_paymentState.value == PaymentState.WAITING_FOR_CARD ||
            _paymentState.value == PaymentState.PROCESSING) {
            Log.w(TAG, "Session ended while payment pending — user cancelled on VPOS")
            _paymentState.value = PaymentState.CANCELLED
            _paymentResult.value = PaymentResult(false, error = "Payment cancelled")
        } else {
            _paymentState.value = PaymentState.IDLE
        }
    }

    override fun onSessionBegin(fundsAvailable: Int) {
        Log.i(TAG, "Card tapped — funds available: $fundsAvailable cents")
        _paymentState.value = PaymentState.PROCESSING
    }

    override fun onVendApproved(session: vmc_vend_t.vend_session_t?): Boolean {
        Log.i(TAG, "Payment APPROVED — amount: $pendingAmountCents cents")
        pendingSession = session
        _paymentState.value = PaymentState.APPROVED
        _paymentResult.value = PaymentResult(
            success = true,
            amount = pendingAmountCents / 100.0,
            transactionId = "TXN_${System.currentTimeMillis()}"
        )
        return true  // return true = vend success; false = motor failed, trigger refund
    }

    override fun onVendDenied(session: vmc_vend_t.vend_session_t?) {
        Log.i(TAG, "Payment DENIED — auth status: ${session?.data?.vmc_auth_status}")
        _paymentState.value = PaymentState.DECLINED
        _paymentResult.value = PaymentResult(false, error = "Payment declined")
    }

    override fun onSettlement(success: Boolean) {
        if (!success) {
            Log.e(TAG, "Settlement FAILED — payment may not have been captured!")
        } else {
            Log.i(TAG, "Settlement complete")
        }
    }

    override fun onTransactionInfo(data: vmc_vend_t.vend_session_data_t?) {
        Log.d(TAG, "Transaction info — TXN ID: ${data?.transaction_id}, card: ${data?.card_type}")
    }

    override fun onStatus(status: Int) {
        Log.d(TAG, "Status: $status")
    }

    override fun onReaderState(enabled: Boolean) {
        Log.d(TAG, "Reader state: ${if (enabled) "enabled" else "disabled"}")
    }

    override fun onOpenedSessions(sessions: ShortArray?) {
        Log.d(TAG, "Opened sessions: ${sessions?.size}")
    }

    override fun onReceipt(type: Int, data: String?) {
        Log.d(TAG, "Receipt: type=$type data=$data")
    }

    override fun onRemoteVend(fundsAvailable: Int, itemNumber: Int, itemOptions: Int) {
        Log.d(TAG, "Remote vend: item=$itemNumber funds=$fundsAvailable")
    }

    // ─────────────────────────────────────────────────────────────
    // Public API
    // ─────────────────────────────────────────────────────────────

    fun initiatePayment(amount: Double) {
        if (!_isReady.value) {
            Log.w(TAG, "initiatePayment called but reader not ready")
            _paymentState.value = PaymentState.ERROR
            _paymentResult.value = PaymentResult(false, error = "Payment reader not ready")
            return
        }
        pendingAmountCents = (amount * 100).roundToInt()
        Log.i(TAG, "initiatePayment: $pendingAmountCents cents")
        _paymentResult.value = null
        _paymentState.value = PaymentState.WAITING_FOR_CARD

        // Pre-Selection: send vend_request NOW (before card tap) so VPOS displays the price
        val session = vmc_vend_t.vend_session_t(
            1,                                          // item number
            1,                                          // quantity
            vmc_vend_t.vend_item_t.UNIT_DONT_CARE.toByte(),
            pendingAmountCents                          // price in cents
        )
        pendingSession = session
        framework?.vend?.vend_request(session)
        Log.i(TAG, "vend_request sent — VPOS should now show price and 'Tap Card'")
    }

    fun confirmVend(success: Boolean) {
        scope.launch(Dispatchers.IO) {
            val session = pendingSession ?: return@launch
            session.session_status = if (success)
                vmc_vend_t.session_status_ok_e
            else
                vmc_vend_t.session_status_fail_to_dispense_e
            framework?.vend?.session_close(session)
            pendingSession = null
            Log.i(TAG, "confirmVend($success) — session closed")
        }
    }

    fun cancelPayment() {
        scope.launch(Dispatchers.IO) {
            Log.i(TAG, "cancelPayment")
            framework?.vend?.session_cancel()
            _paymentState.value = PaymentState.CANCELLED
        }
    }

    fun reset() {
        scope.launch {
            _paymentState.value = PaymentState.IDLE
            _paymentResult.value = null
            pendingAmountCents = 0
            pendingSession = null
            // Re-arm the reader for the next transaction
            if (_isReady.value) {
                framework?.vend?.vend_request(
                    vmc_vend_t.vend_session_t(1, 1, vmc_vend_t.vend_item_t.UNIT_DONT_CARE.toByte(), 0)
                )
            }
        }
    }

    fun close() {
        scope.launch(Dispatchers.IO) {
            Log.i(TAG, "close()")
            vmc_framework.reset()
            framework = null
            try {
                serialPort?.close()
            } catch (_: Exception) {}
            serialPort = null
            _isReady.value = false
            _paymentState.value = PaymentState.IDLE
        }
    }
}

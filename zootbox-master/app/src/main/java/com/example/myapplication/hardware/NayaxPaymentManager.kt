package com.example.myapplication.hardware

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import com.hoho.android.usbserial.driver.CdcAcmSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException

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
) {
    private var serialPort: UsbSerialPort? = null

    private val _paymentState = MutableStateFlow(PaymentState.IDLE)
    val paymentState: StateFlow<PaymentState> = _paymentState

    private val _paymentResult = MutableStateFlow<PaymentResult?>(null)
    val paymentResult: StateFlow<PaymentResult?> = _paymentResult

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    companion object {
        // Frame delimiters
        private const val STX: Byte = 0x02
        private const val ETX: Byte = 0x03

        // Commands: VMC → Nayax
        private const val CMD_RESET: Byte     = 0x01
        private const val CMD_SET_PRICE: Byte = 0x02
        private const val CMD_ENABLE: Byte    = 0x03
        private const val CMD_DISABLE: Byte   = 0x04
        private const val CMD_CANCEL: Byte    = 0x05

        // Responses: Nayax → VMC
        private const val RESP_READY: Byte         = 0x10
        private const val RESP_CARD_DETECTED: Byte = 0x11
        private const val RESP_PROCESSING: Byte    = 0x12
        private const val RESP_APPROVED: Byte      = 0x13
        private const val RESP_DECLINED: Byte      = 0x14
        private const val RESP_ERROR: Byte         = 0x15
    }

    fun initialize() {
        scope.launch(Dispatchers.IO) {
            try {
                if (!usbManager.hasPermission(usbDevice)) {
                    _isReady.value = false
                    return@launch
                }

                val driver = CustomSerialProber.getCustomProber().probeDevice(usbDevice)
                    ?: CdcAcmSerialDriver(usbDevice)

                serialPort = driver.ports[0]
                serialPort?.open(usbManager.openDevice(usbDevice))
                serialPort?.setParameters(
                    HardwareService.SERIAL_BAUD_RATE,
                    8,
                    UsbSerialPort.STOPBITS_1,
                    UsbSerialPort.PARITY_NONE
                )

                sendFrame(CMD_RESET)
                delay(500)

                _isReady.value = true
                _paymentState.value = PaymentState.IDLE
                startReading()
            } catch (e: Exception) {
                _isReady.value = false
                _paymentState.value = PaymentState.ERROR
                _paymentResult.value = PaymentResult(false, error = e.message)
            }
        }
    }

    // Frame layout: STX | LEN | CMD | DATA… | LRC | ETX
    // LEN  = sizeof(CMD) + sizeof(DATA)
    // LRC  = XOR of (LEN, CMD, and every DATA byte)
    private fun buildFrame(cmd: Byte, data: ByteArray = ByteArray(0)): ByteArray {
        val len = (1 + data.size).toByte()
        var lrc = (len.toInt() xor cmd.toInt()).toByte()
        for (b in data) lrc = (lrc.toInt() xor b.toInt()).toByte()

        return ByteArray(5 + data.size).apply {
            this[0] = STX
            this[1] = len
            this[2] = cmd
            data.copyInto(this, 3)
            this[3 + data.size] = lrc
            this[4 + data.size] = ETX
        }
    }

    private fun sendFrame(cmd: Byte, data: ByteArray = ByteArray(0)) {
        serialPort?.write(buildFrame(cmd, data), 1000)
    }

    private fun startReading() {
        scope.launch(Dispatchers.IO) {
            val buffer = ByteArray(1024)
            val frameBuffer = mutableListOf<Byte>()
            var inFrame = false

            while (isActive && _isReady.value) {
                try {
                    val bytesRead = serialPort?.read(buffer, 500) ?: break
                    for (i in 0 until bytesRead) {
                        val b = buffer[i]
                        when {
                            b == STX -> {
                                inFrame = true
                                frameBuffer.clear()
                            }
                            b == ETX && inFrame -> {
                                inFrame = false
                                processFrame(frameBuffer.toByteArray())
                                frameBuffer.clear()
                            }
                            inFrame -> frameBuffer.add(b)
                        }
                    }
                } catch (e: IOException) {
                    if (isActive) delay(100)
                }
            }
        }
    }

    // data contains: LEN | CMD | PAYLOAD… | LRC  (no STX/ETX)
    private fun processFrame(data: ByteArray) {
        if (data.size < 3) return

        // Verify LRC
        var calcLrc: Byte = 0
        for (i in 0 until data.size - 1) calcLrc = (calcLrc.toInt() xor data[i].toInt()).toByte()
        if (calcLrc != data.last()) return

        val cmd = data[1]
        scope.launch {
            when (cmd) {
                RESP_READY -> _isReady.value = true

                RESP_CARD_DETECTED -> {
                    if (_paymentState.value == PaymentState.WAITING_FOR_CARD) {
                        _paymentState.value = PaymentState.PROCESSING
                    }
                }

                RESP_PROCESSING -> _paymentState.value = PaymentState.PROCESSING

                RESP_APPROVED -> {
                    val txnId = if (data.size >= 7) extractTxnId(data, 2)
                                else "TXN_${System.currentTimeMillis()}"
                    _paymentState.value = PaymentState.APPROVED
                    _paymentResult.value = PaymentResult(success = true, transactionId = txnId)
                }

                RESP_DECLINED -> {
                    _paymentState.value = PaymentState.DECLINED
                    _paymentResult.value = PaymentResult(success = false, error = "Payment declined")
                }

                RESP_ERROR -> {
                    val code = if (data.size >= 4) (data[2].toInt() and 0xFF) else 0
                    _paymentState.value = PaymentState.ERROR
                    _paymentResult.value = PaymentResult(false, error = "Reader error (code $code)")
                }
            }
        }
    }

    private fun extractTxnId(data: ByteArray, offset: Int): String {
        val value = ((data[offset].toInt() and 0xFF) shl 24) or
                    ((data[offset + 1].toInt() and 0xFF) shl 16) or
                    ((data[offset + 2].toInt() and 0xFF) shl 8) or
                    (data[offset + 3].toInt() and 0xFF)
        return "TXN_$value"
    }

    fun initiatePayment(amount: Double) {
        scope.launch(Dispatchers.IO) {
            if (!_isReady.value) {
                _paymentState.value = PaymentState.ERROR
                _paymentResult.value = PaymentResult(false, error = "Payment reader not ready")
                return@launch
            }
            try {
                _paymentResult.value = null
                _paymentState.value = PaymentState.INITIALIZING

                val cents = (amount * 100).toInt()
                val priceBytes = byteArrayOf(
                    (cents shr 24).toByte(),
                    (cents shr 16).toByte(),
                    (cents shr 8).toByte(),
                    cents.toByte()
                )
                sendFrame(CMD_SET_PRICE, priceBytes)
                delay(200)
                sendFrame(CMD_ENABLE)

                _paymentState.value = PaymentState.WAITING_FOR_CARD
            } catch (e: Exception) {
                _paymentState.value = PaymentState.ERROR
                _paymentResult.value = PaymentResult(false, error = e.message)
            }
        }
    }

    fun cancelPayment() {
        scope.launch(Dispatchers.IO) {
            try { sendFrame(CMD_CANCEL) } catch (_: Exception) {}
            _paymentState.value = PaymentState.CANCELLED
        }
    }

    fun reset() {
        scope.launch {
            _paymentState.value = PaymentState.IDLE
            _paymentResult.value = null
        }
    }

    fun close() {
        scope.launch(Dispatchers.IO) {
            try {
                sendFrame(CMD_DISABLE)
                serialPort?.close()
            } catch (_: Exception) {}
            serialPort = null
            _isReady.value = false
            _paymentState.value = PaymentState.IDLE
        }
    }
}

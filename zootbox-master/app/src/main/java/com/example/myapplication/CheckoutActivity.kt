package com.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.hardware.HardwareService
import com.example.myapplication.hardware.NayaxPaymentManager
import com.example.myapplication.hardware.PaymentState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : KioskActivity() {

    private lateinit var textCountdown: TextView
    private lateinit var textAwaiting: TextView
    private lateinit var btnRetry: Button
    private var countdownJob: Job? = null
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    private var hardwareService: HardwareService? = null
    private var paymentManager: NayaxPaymentManager? = null
    private var paymentStarted = false
    private var totalPrice: Double = 0.0

    companion object {
        private const val SESSION_SECONDS = 60
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            hardwareService = (binder as HardwareService.LocalBinder).getService()
            observePaymentManager()
        }
        override fun onServiceDisconnected(name: ComponentName) {
            hardwareService = null
            paymentManager = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val productName = intent.getStringExtra("productName") ?: "Unknown Product"
        val quantity    = intent.getIntExtra("quantity", 1)
        val unitPrice   = intent.getDoubleExtra("unitPrice", 0.0)
        totalPrice      = intent.getDoubleExtra("totalPrice", 0.0)

        textCountdown = findViewById(R.id.text_countdown)
        textAwaiting  = findViewById(R.id.text_awaiting)
        btnRetry      = findViewById(R.id.btn_retry)

        findViewById<TextView>(R.id.text_product_name).text = productName
        findViewById<TextView>(R.id.text_qty_label).text    = "Qty: $quantity"
        findViewById<TextView>(R.id.text_unit_price).text   = "${currencyFormatter.format(unitPrice)} each"
        findViewById<TextView>(R.id.text_total_price).text  = currencyFormatter.format(totalPrice)

        val cancel = {
            paymentManager?.cancelPayment()
            finish()
        }
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener { cancel() }
        findViewById<Button>(R.id.btn_cancel).setOnClickListener    { cancel() }

        btnRetry.setOnClickListener {
            btnRetry.visibility = View.GONE
            retryPayment()
        }

        updateStatus("Connecting to payment reader…", "#6B7FA3")
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, HardwareService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
        hardwareService = null
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownJob?.cancel()
        if (!isChangingConfigurations) {
            paymentManager?.cancelPayment()
        }
    }

    private fun observePaymentManager() {
        val service = hardwareService ?: return
        lifecycleScope.launch {
            service.nayaxPaymentManagerFlow.collect { manager ->
                if (manager != null && !paymentStarted) {
                    paymentManager = manager
                    startPaymentFlow()
                }
            }
        }
    }

    private fun startPaymentFlow() {
        paymentStarted = true
        startCountdown()
        paymentManager?.initiatePayment(totalPrice)
        observePaymentState()
    }

    private fun retryPayment() {
        paymentManager?.reset()
        countdownJob?.cancel()
        startCountdown()
        paymentManager?.initiatePayment(totalPrice)
    }

    private fun observePaymentState() {
        val manager = paymentManager ?: return
        lifecycleScope.launch {
            manager.paymentState.collect { state ->
                when (state) {
                    PaymentState.IDLE         -> updateStatus("Initializing…", "#6B7FA3")
                    PaymentState.INITIALIZING -> updateStatus("Preparing payment…", "#6B7FA3")
                    PaymentState.WAITING_FOR_CARD -> updateStatus("Awaiting payment…", "#4ADE80")
                    PaymentState.PROCESSING   -> {
                        countdownJob?.cancel()
                        updateStatus("Processing payment…", "#FCD34D")
                    }
                    PaymentState.APPROVED     -> {
                        countdownJob?.cancel()
                        updateStatus("Payment Approved!", "#4ADE80")
                        lifecycleScope.launch {
                            delay(2000)
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                    PaymentState.DECLINED     -> {
                        countdownJob?.cancel()
                        updateStatus("Payment Declined", "#EF4444")
                        btnRetry.visibility = View.VISIBLE
                    }
                    PaymentState.ERROR        -> {
                        countdownJob?.cancel()
                        updateStatus("Reader Error — Try again", "#EF4444")
                        btnRetry.visibility = View.VISIBLE
                    }
                    PaymentState.CANCELLED    -> { /* handled by cancel() */ }
                }
            }
        }
    }

    private fun updateStatus(message: String, colorHex: String) {
        textAwaiting.text = message
        textAwaiting.setTextColor(Color.parseColor(colorHex))
    }

    private fun startCountdown() {
        countdownJob?.cancel()
        countdownJob = lifecycleScope.launch {
            for (seconds in SESSION_SECONDS downTo 1) {
                textCountdown.text = "Session expires in ${seconds}s"
                delay(1000)
                // Stop the timer if payment is past the card-wait phase
                val state = paymentManager?.paymentState?.value
                if (state == PaymentState.PROCESSING ||
                    state == PaymentState.APPROVED   ||
                    state == PaymentState.DECLINED   ||
                    state == PaymentState.ERROR) return@launch
            }
            paymentManager?.cancelPayment()
            finish()
        }
    }
}

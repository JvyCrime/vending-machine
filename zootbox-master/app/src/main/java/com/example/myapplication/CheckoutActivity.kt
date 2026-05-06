package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : KioskActivity() {

    private lateinit var textCountdown: TextView
    private var countdownJob: Job? = null
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    companion object {
        private const val SESSION_SECONDS = 60
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val productName = intent.getStringExtra("productName") ?: "Unknown Product"
        val quantity    = intent.getIntExtra("quantity", 1)
        val unitPrice   = intent.getDoubleExtra("unitPrice", 0.0)
        val totalPrice  = intent.getDoubleExtra("totalPrice", 0.0)

        textCountdown = findViewById(R.id.text_countdown)

        findViewById<TextView>(R.id.text_product_name).text = productName
        findViewById<TextView>(R.id.text_qty_label).text    = "Qty: $quantity"
        findViewById<TextView>(R.id.text_unit_price).text   = "${currencyFormatter.format(unitPrice)} each"
        findViewById<TextView>(R.id.text_total_price).text  = currencyFormatter.format(totalPrice)

        val cancel = { finish() }
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener { cancel() }
        findViewById<Button>(R.id.btn_cancel).setOnClickListener    { cancel() }

        startCountdown()

        // TODO: observe NayaxPaymentManager result here once payment integration is ready
    }

    private fun startCountdown() {
        countdownJob = lifecycleScope.launch {
            for (seconds in SESSION_SECONDS downTo 1) {
                textCountdown.text = "Session expires in ${seconds}s"
                delay(1000)
            }
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownJob?.cancel()
    }
}

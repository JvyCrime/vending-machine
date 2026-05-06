package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.text.NumberFormat
import java.util.Locale

class ProductDetailActivity : KioskActivity() {

    private lateinit var videoView: VideoView
    private lateinit var imageView: ImageView
    private var videoFileName: String? = null

    // Product data
    private var basePrice: Double = 0.0
    private var quantity: Int = 1
    private var ageRestriction: Int = -1

    // UI References
    private lateinit var textQuantity: TextView
    private lateinit var textPrice: TextView
    private lateinit var btnQuantityMinus: Button
    private lateinit var btnQuantityPlus: Button
    private lateinit var btnAddToCart: Button

    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    private lateinit var idScanLauncher: ActivityResultLauncher<Intent>

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
        private val VIDEO_BASE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/Movies/ZootBox/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Get Intent data
        val name = intent.getStringExtra("name") ?: ""
        basePrice = intent.getDoubleExtra("price", 0.0)
        val imageRes = intent.getIntExtra("imageRes", 0)
        val description = intent.getStringExtra("desc") ?: ""
        val category = intent.getStringExtra("category") ?: ""
        videoFileName = intent.getStringExtra("videoFileName")
        ageRestriction = intent.getIntExtra("ageRestriction", -1)

        // Initialize views
        initializeViews()

        // Register Activity Result Launcher for ID Scan
        idScanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                launchCheckout()
            } else {
                Toast.makeText(this, "Verification failed or cancelled.", Toast.LENGTH_SHORT).show()
            }
        }

        // Setup product information
        setupProductInfo(name, basePrice, description, category)
        setupSpecifications(ageRestriction, category)

        // Setup quantity controls
        setupQuantityControls()

        // Initialize button text with initial price
        updateQuantityDisplay()

        // Setup image/video
        setupMedia(imageRes)

        if (videoFileName != null) {
            checkAndRequestPermissions()
        }
    }

    private fun initializeViews() {
        imageView = findViewById(R.id.detail_image)
        videoView = findViewById(R.id.detail_video)
        textQuantity = findViewById(R.id.text_quantity)
        textPrice = findViewById(R.id.detail_price)
        btnQuantityMinus = findViewById(R.id.btn_quantity_minus)
        btnQuantityPlus = findViewById(R.id.btn_quantity_plus)
        btnAddToCart = findViewById(R.id.btn_add_to_cart)

        findViewById<android.widget.ImageButton>(R.id.btn_back).setOnClickListener { finish() }
    }

    private fun setupProductInfo(name: String, price: Double, description: String, category: String) {
        findViewById<TextView>(R.id.detail_name).text = name
        textPrice.text = currencyFormatter.format(price)
        findViewById<TextView>(R.id.detail_description).text = description

        // Brand · Category eyebrow
        val eyebrow = buildString {
            if (category.isNotEmpty()) append(category.uppercase())
        }
        if (eyebrow.isNotEmpty()) {
            findViewById<TextView>(R.id.text_category).text = eyebrow
        }
    }

    private fun setupSpecifications(age: Int, category: String) {
        // Age restriction (maps to spec_nicotine_value view)
        val ageLabel = if (age > 0) "${age}+" else "21+"
        findViewById<TextView>(R.id.spec_nicotine_value).text = ageLabel

        // Category (maps to spec_pouches_value view)
        val catLabel = if (category.isNotEmpty()) category else "—"
        findViewById<TextView>(R.id.spec_pouches_value).text = catLabel

        // Tobacco-free (maps to spec_flavor_profile_value view)
        val tobaccoFree = when (category.lowercase()) {
            "pouches" -> "Yes"
            "cigarettes" -> "No"
            else -> "No"
        }
        findViewById<TextView>(R.id.spec_flavor_profile_value).text = tobaccoFree
    }

    private fun setupQuantityControls() {
        updateQuantityDisplay()

        btnQuantityMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityDisplay()
            }
        }

        btnQuantityPlus.setOnClickListener {
            if (quantity < 9) {
                quantity++
                updateQuantityDisplay()
            }
        }

        btnAddToCart.setOnClickListener {
            Log.d("ProductDetailActivity", "Buy Now Clicked. Age Restriction: $ageRestriction")
            val checkAge = if (ageRestriction > 0) ageRestriction else 21

            if (checkAge > 0) {
                Log.d("ProductDetailActivity", "Launching ID Scan")
                val intent = Intent(this, IdScanActivity::class.java)
                intent.putExtra("requiredAge", checkAge)
                idScanLauncher.launch(intent)
            } else {
                launchCheckout()
            }
        }
    }

    private fun launchCheckout() {
        val productName = intent.getStringExtra("name") ?: ""
        val totalPrice = basePrice * quantity
        val checkoutIntent = Intent(this, CheckoutActivity::class.java).apply {
            putExtra("productName", productName)
            putExtra("quantity", quantity)
            putExtra("unitPrice", basePrice)
            putExtra("totalPrice", totalPrice)
        }
        startActivity(checkoutIntent)
    }

    private fun updateQuantityDisplay() {
        textQuantity.text = quantity.toString()
        val totalPrice = basePrice * quantity
        btnAddToCart.text = "ADD TO CART · ${currencyFormatter.format(totalPrice)}"
    }

    private fun setupMedia(imageRes: Int) {
        if (imageRes != 0) {
            imageView.setImageResource(imageRes)
        }
    }

    private fun checkAndRequestPermissions() {
        val permission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_VIDEO
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_REQUEST_CODE)
        } else {
            playVideo()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                playVideo()
            }
        }
    }

    private fun playVideo() {
        val fileName = videoFileName ?: return
        val videoFile = File(VIDEO_BASE_PATH, fileName)

        if (videoFile.exists()) {
            imageView.visibility = View.GONE
            videoView.visibility = View.VISIBLE
            videoView.setVideoURI(Uri.fromFile(videoFile))
            videoView.setOnPreparedListener { mp ->
                mp.isLooping = true
                videoView.start()
            }
            videoView.setOnErrorListener { _, _, _ ->
                videoView.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::videoView.isInitialized && videoView.visibility == View.VISIBLE && !videoView.isPlaying) {
            videoView.start()
        }
    }
}

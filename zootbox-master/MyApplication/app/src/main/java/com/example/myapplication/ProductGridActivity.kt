package com.example.myapplication

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sin

class ProductGridActivity : AppCompatActivity() {

    private lateinit var productRecycler: RecyclerView
    private lateinit var bgVideoView1: FullScreenVideoView
    private lateinit var bgVideoView2: FullScreenVideoView

    private val fadeDuration = 1500L // 1.5s cross-fade
    private var activePlayer = 1 // 1 or 2
    private val loopHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private var loopRunnable: Runnable? = null
    private val playbackSpeed = 1.0f // Normal playback speed

    // Idle screensaver
    private val idleTimeout = 30000L // 30 seconds
    private val idleHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private var idleRunnable: Runnable? = null

    // Category selection
    private var categoryId: String = ""
    private var categoryName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_grid)

        // Get category from intent
        categoryId = intent.getStringExtra("category_id") ?: ""
        categoryName = intent.getStringExtra("category_name") ?: ""

        // Enable edge-to-edge and hide system bars (must be after setContentView)
        setupFullScreen()

        // Initialize Views
        productRecycler = findViewById(R.id.product_recycler)
        bgVideoView1 = findViewById(R.id.bg_video_view_1)
        bgVideoView2 = findViewById(R.id.bg_video_view_2)

        // Setup back button
        val backButton = findViewById<MaterialButton>(R.id.backButton)
        backButton.bringToFront() // Ensure button is on top of all views
        backButton.setOnClickListener {
            finish() // Return to CategorySelectionActivity
        }

        // Video background disabled - white background used instead
        // setupDualVideoBackground()

        // Setup ZootBox Product Grid (2 Columns)
        productRecycler.layoutManager = GridLayoutManager(this, 2)

        // Performance optimizations
        productRecycler.setHasFixedSize(true)
        productRecycler.setItemViewCacheSize(20)

        // Simple placeholder products (same for all categories)
        // In a real app, filter based on categoryName
        val allProducts = listOf(
            // VAPES
            Product("01", "FUME ULTRA 2500", "1", "1", R.drawable.img_fume_ultra_strawberry_banana, price = 16.99, ageRestriction = 21, videoFileName = "fume_ultra.mp4", category = "VAPES"),
            Product("02", "LOST MARY NERA 70K", "1", "2", R.drawable.img_lost_mary, price = 24.99, ageRestriction = 21, videoFileName = "lost_mary.mp4", category = "VAPES"),
            Product("03", "OFF STAMP CUBE KIT", "1", "3", R.drawable.img_off_stamp_black_mint, price = 17.99, ageRestriction = 21, videoFileName = "off_stamp.mp4", category = "VAPES"),
            Product("04", "RAZ 25K LTX", "1", "4", R.drawable.img_raz, price = 19.99, ageRestriction = 21, videoFileName = "raz.mp4", category = "VAPES"),
            Product("05", "GEEK BAR PULSE X 40K", "1", "5", R.drawable.img_geek_bar, price = 21.99, ageRestriction = 21, videoFileName = "geek_bar.mp4", category = "VAPES"),
            // POUCHES
            Product("06", "ZYN CITRUS 6MG", "2", "1", R.drawable.img_zyn_citrus_6mg, price = 8.99, ageRestriction = 21, videoFileName = "zyn_citrus.mp4", category = "POUCHES"),
            Product("07", "ZYN COOL MINT 6MG", "2", "2", R.drawable.img_zyn_cool_mint_6mg, price = 8.99, ageRestriction = 21, videoFileName = "zyn_cool_mint.mp4", category = "POUCHES"),
            Product("08", "ZYN WINTERGREEN 6MG", "2", "3", R.drawable.img_zyn_wintergreen_6mg, price = 8.99, ageRestriction = 21, videoFileName = "zyn_wintergreen.mp4", category = "POUCHES"),
            Product("09", "ZYN SPEARMINT 3MG", "2", "4", R.drawable.img_zyn_spearmint_3mg, price = 8.99, ageRestriction = 21, videoFileName = "zyn_spearmint.mp4", category = "POUCHES"),
            Product("10", "ZYN PEPPERMINT 3MG", "2", "5", R.drawable.img_zyn_peppermint_3mg, price = 8.99, ageRestriction = 21, videoFileName = "zyn_peppermint.mp4", category = "POUCHES"),
            // CIGARETTES
            Product("11", "NEWPORTS", "3", "1", R.drawable.img_newport, price = 13.99, ageRestriction = 21, videoFileName = "newports.mp4", category = "CIGARETTES")
        )
        
        val filteredProducts = if (categoryName.isNotEmpty()) {
            allProducts.filter { it.category == categoryName }
        } else {
            allProducts
        }

        val adapter = ProductAdapter(filteredProducts) { product ->
            // Launch Product Detail Activity
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("name", product.name)
            intent.putExtra("row", product.row)
            intent.putExtra("col", product.col)
            intent.putExtra("imageRes", product.imageRes)
            intent.putExtra("backgroundRes", product.backgroundRes)
            intent.putExtra("isDigital", product.isDigital)
            intent.putExtra("price", product.price)
            intent.putExtra("ageRestriction", product.ageRestriction ?: -1)
            intent.putExtra("desc", if(product.isDigital) "Support the cause. All proceeds go directly to the Autism Fund." else "Premium nightlife selection. High quality.")
            intent.putExtra("videoFileName", product.videoFileName)
            startActivity(intent)
        }
        productRecycler.adapter = adapter
    }

    private var videoDuration = 0

    private fun setupDualVideoBackground() {
        // Ensure video views are visible
        bgVideoView1.visibility = View.VISIBLE
        bgVideoView2.visibility = View.VISIBLE
        
        val videoPath = "android.resource://" + packageName + "/" + R.raw.download
        val uri = android.net.Uri.parse(videoPath)

        android.util.Log.d("ProductGridActivity", "Video URI: $videoPath")
        android.util.Log.d("ProductGridActivity", "Resource ID: ${R.raw.download}")

        // Initialize Player 1
        bgVideoView1.setVideoURI(uri)
        bgVideoView1.setOnPreparedListener { mp ->
            android.util.Log.d("ProductGridActivity", "Video 1 prepared, duration: ${mp.duration}")
            mp.setVolume(0f, 0f)
            // Set playback speed
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    mp.playbackParams = mp.playbackParams.setSpeed(playbackSpeed)
                } catch (e: Exception) {
                    android.util.Log.e("ProductGridActivity", "Error setting playback speed", e)
                }
            }

            videoDuration = mp.duration
            bgVideoView1.start()
            android.util.Log.d("ProductGridActivity", "Video 1 started")
            scheduleNextLoop(videoDuration)
        }
        
        bgVideoView1.setOnErrorListener { mp, what, extra ->
            android.util.Log.e("ProductGridActivity", "Video 1 error: what=$what, extra=$extra")
            false
        }

        // Initialize Player 2 (don't start yet)
        bgVideoView2.setVideoURI(uri)
        bgVideoView2.setOnPreparedListener { mp ->
            android.util.Log.d("ProductGridActivity", "Video 2 prepared")
            mp.setVolume(0f, 0f)
            // Set playback speed for P2 as well
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    mp.playbackParams = mp.playbackParams.setSpeed(playbackSpeed)
                } catch (e: Exception) {
                    android.util.Log.e("ProductGridActivity", "Error setting playback speed for P2", e)
                }
            }
        }

        bgVideoView2.setOnErrorListener { mp, what, extra ->
            android.util.Log.e("ProductGridActivity", "Video 2 error: what=$what, extra=$extra")
            false
        }
        // Ensure P2 is ready (it will auto-prepare on setVideoURI)
    }

    private fun scheduleNextLoop(durationMs: Int) {
        if (durationMs <= 0) return

        // Schedule cross-fade before end (playing at normal 1.0x speed now)
        val triggerTime = (durationMs - fadeDuration).coerceAtLeast(0).toLong()

        loopRunnable = Runnable {
            performCrossFade()
        }
        loopHandler.postDelayed(loopRunnable!!, triggerTime)
    }

    private fun performCrossFade() {
        val outgoing = if (activePlayer == 1) bgVideoView1 else bgVideoView2
        val incoming = if (activePlayer == 1) bgVideoView2 else bgVideoView1

        // Start incoming player from beginning
        incoming.seekTo(0)
        incoming.start()

        // Cross-fade animations
        incoming.animate().alpha(1f).setDuration(fadeDuration).start()
        outgoing.animate().alpha(0f).setDuration(fadeDuration).withEndAction {
            // Reset outgoing player for next cycle
            outgoing.pause()
            outgoing.seekTo(0)
        }.start()

        // Swap active player
        activePlayer = if (activePlayer == 1) 2 else 1

        // Schedule next loop
        if (videoDuration > 0) {
            scheduleNextLoop(videoDuration)
        }
    }

    override fun onResume() {
        super.onResume()
        // Video background disabled
        // if (activePlayer == 1) bgVideoView1.start() else bgVideoView2.start()
        resetIdleTimer()
    }

    override fun onPause() {
        super.onPause()
        loopRunnable?.let { loopHandler.removeCallbacks(it) }
        idleRunnable?.let { idleHandler.removeCallbacks(it) }
        // Video background disabled
        // bgVideoView1.pause()
        // bgVideoView2.pause()
    }

    override fun dispatchTouchEvent(event: android.view.MotionEvent): Boolean {
        resetIdleTimer()
        return super.dispatchTouchEvent(event)
    }

    private fun resetIdleTimer() {
        idleRunnable?.let { idleHandler.removeCallbacks(it) }
        idleRunnable = Runnable { launchScreensaver() }
        idleHandler.postDelayed(idleRunnable!!, idleTimeout)
    }

    private fun launchScreensaver() {
        val intent = Intent(this, ScreensaverActivity::class.java)
        startActivity(intent)
    }

    private fun setupFullScreen() {
        // Make the app edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Hide the navigation bar and status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
        }

        // Keep screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            setupFullScreen()
        }
    }
}
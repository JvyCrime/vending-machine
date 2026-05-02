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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sin

class MainActivity : AppCompatActivity() {

    private lateinit var productRecycler: RecyclerView
    private lateinit var headerText: TextView
    
    // Background Layers
    private lateinit var bgLayer1: ImageView
    private lateinit var bgLayer2: ImageView
    private lateinit var bgLayer3: ImageView
    private lateinit var bgLayer4: ImageView
    private lateinit var bgLayer5: ImageView
    
    // Orbs
    private lateinit var orbSun: ImageView
    private lateinit var orbOcean: ImageView
    private lateinit var orbSunset: ImageView
    private lateinit var orbTropical: ImageView
    private lateinit var orbPurple: ImageView
    
    // Overlay
    private lateinit var meshOverlay: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enable edge-to-edge and hide system bars (must be after setContentView)
        setupFullScreen()

        // Initialize Views
        productRecycler = findViewById(R.id.product_recycler)
        headerText = findViewById(R.id.tv_header)
        
        bgLayer1 = findViewById(R.id.bg_layer_1)
        bgLayer2 = findViewById(R.id.bg_layer_2)
        bgLayer3 = findViewById(R.id.bg_layer_3)
        bgLayer4 = findViewById(R.id.bg_layer_4)
        bgLayer5 = findViewById(R.id.bg_layer_5)
        
        orbSun = findViewById(R.id.orb_sun)
        orbOcean = findViewById(R.id.orb_ocean)
        orbSunset = findViewById(R.id.orb_sunset)
        orbTropical = findViewById(R.id.orb_tropical)
        orbPurple = findViewById(R.id.orb_purple)
        meshOverlay = findViewById(R.id.mesh_overlay)

        // Setup Animations
        setupBackgroundAnimations()
        setupOrbAnimations()
        setupMeshAnimation()
        // setupTextGradient() - Removed as we now use an SVG image

        // Start hardware service
        try {
            val serviceIntent = Intent(this, com.example.myapplication.hardware.HardwareService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                @Suppress("DEPRECATION")
                startService(serviceIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Setup ZootBox Product Grid (2 Columns)
        productRecycler.layoutManager = GridLayoutManager(this, 2)

        // Performance optimizations
        productRecycler.setHasFixedSize(true)
        productRecycler.setItemViewCacheSize(20)

        Toast.makeText(this, "USB Connection Active", Toast.LENGTH_LONG).show()

        // Create fixed list of 10 slots mapping to Hardware Row 1, Cols 1-10
        // Plus one Digital Donation item
        // Note: videoFileName assumes files are in /storage/emulated/0/Movies/ZootBox/
        val products = listOf(
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

        val adapter = ProductAdapter(products) { product ->
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

    private fun setupBackgroundAnimations() {
        val duration = 24000L // 24s

        // Helper to create keyframe alpha animator
        fun createAlphaAnimator(view: View, keyframes: Array<Keyframe>): ObjectAnimator {
            val pvh = PropertyValuesHolder.ofKeyframe(View.ALPHA, *keyframes)
            return ObjectAnimator.ofPropertyValuesHolder(view, pvh).apply {
                this.duration = duration
                this.repeatCount = ValueAnimator.INFINITE
                this.interpolator = AccelerateDecelerateInterpolator() // Ease in-out
            }
        }

        // Layer 1: 0%->1, 20%->0, 80%->0.5, 100%->1
        val kf1 = arrayOf(
            Keyframe.ofFloat(0f, 1f),
            Keyframe.ofFloat(0.2f, 0f),
            Keyframe.ofFloat(0.6f, 0f),
            Keyframe.ofFloat(0.8f, 0.5f),
            Keyframe.ofFloat(1f, 1f)
        )
        createAlphaAnimator(bgLayer1, kf1).start()

        // Layer 2: 0%->0, 20%->1, 40%->0
        val kf2 = arrayOf(
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.2f, 1f),
            Keyframe.ofFloat(0.4f, 0f),
            Keyframe.ofFloat(1f, 0f)
        )
        createAlphaAnimator(bgLayer2, kf2).start()

        // Layer 3: 0%->0, 40%->1, 60%->0
        val kf3 = arrayOf(
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.2f, 0f),
            Keyframe.ofFloat(0.4f, 1f),
            Keyframe.ofFloat(0.6f, 0f),
            Keyframe.ofFloat(1f, 0f)
        )
        createAlphaAnimator(bgLayer3, kf3).start()

        // Layer 4: 0%->0, 60%->1, 80%->0
        val kf4 = arrayOf(
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.4f, 0f),
            Keyframe.ofFloat(0.6f, 1f),
            Keyframe.ofFloat(0.8f, 0f),
            Keyframe.ofFloat(1f, 0f)
        )
        createAlphaAnimator(bgLayer4, kf4).start()

        // Layer 5: 0%->0, 80%->1, 100%->0 (Actually wraps to 0 in CSS? CSS says 0%->0, 80%->1. Let's assume 100%->0 to loop smooth)
        // CSS fadeInOut5: 0% 0, 40% 0.5, 80% 1.
        val kf5 = arrayOf(
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.4f, 0.5f),
            Keyframe.ofFloat(0.8f, 1f),
            Keyframe.ofFloat(1f, 0f) 
        )
        createAlphaAnimator(bgLayer5, kf5).start()
    }

    private fun setupOrbAnimations() {
        // Generic Float Animation
        fun animateFloat(view: View, xDist: Float, yDist: Float, duration: Long) {
            val animatorX = ObjectAnimator.ofFloat(view, "translationX", 0f, xDist).apply {
                this.duration = duration
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                interpolator = AccelerateDecelerateInterpolator()
            }
            val animatorY = ObjectAnimator.ofFloat(view, "translationY", 0f, yDist).apply {
                this.duration = (duration * 1.2).toLong() // Different timeline for organic feel
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                interpolator = AccelerateDecelerateInterpolator()
            }
            animatorX.start()
            animatorY.start()
        }

        // Sun: Float
        animateFloat(orbSun, 20f, -30f, 10000)
        
        // Ocean: Drift
        animateFloat(orbOcean, 40f, -20f, 20000)

        // Sunset: Sway
        animateFloat(orbSunset, -25f, 15f, 19000)

        // Tropical: Float
        animateFloat(orbTropical, 10f, -15f, 18000)

        // Purple: Drift Reverse (just negative values)
        animateFloat(orbPurple, -20f, -20f, 21000)
    }

    private fun setupMeshAnimation() {
        // Mesh Move: Scale and Rotate
        val scaleX = ObjectAnimator.ofFloat(meshOverlay, "scaleX", 1f, 1.1f)
        val scaleY = ObjectAnimator.ofFloat(meshOverlay, "scaleY", 1f, 1.1f)
        val rotate = ObjectAnimator.ofFloat(meshOverlay, "rotation", 0f, 5f)

        val set = android.animation.AnimatorSet().apply {
            playTogether(scaleX, scaleY, rotate)
            duration = 12500 // 25s full cycle / 2 for reverse
            // We want smooth loop. 
            // ObjectAnimator REVERSE works well.
        }
        
        // Apply repeat to individual animators inside set is tricky with Set.
        // Better:
        scaleX.repeatCount = ValueAnimator.INFINITE
        scaleX.repeatMode = ValueAnimator.REVERSE
        scaleX.duration = 12500
        
        scaleY.repeatCount = ValueAnimator.INFINITE
        scaleY.repeatMode = ValueAnimator.REVERSE
        scaleY.duration = 12500

        rotate.repeatCount = ValueAnimator.INFINITE
        rotate.repeatMode = ValueAnimator.REVERSE
        rotate.duration = 12500

        scaleX.start()
        scaleY.start()
        rotate.start()
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
package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : KioskActivity() {

    private companion object {
        // How long the screen must be idle before the ad carousel launches (45 seconds).
        const val IDLE_TIMEOUT_MS = 45_000L
    }

    private lateinit var productRecycler: RecyclerView
    private lateinit var categoryRail: LinearLayout
    private lateinit var tvClock: TextView

    // Idle-screen timer — resets on every touch event, fires IdleActivity on timeout.
    private val idleHandler  = Handler(Looper.getMainLooper())
    private val idleRunnable = Runnable {
        startActivity(Intent(this, IdleActivity::class.java))
    }

    private val allProducts = listOf(
        Product("01", "FUME ULTRA 2500",     "1", "1", R.drawable.fume,                         price = 16.99, ageRestriction = 21, videoFileName = "fume_ultra.mp4",      category = "VAPES"),
        Product("02", "LOST MARY NERA 70K",  "1", "2", R.drawable.lost_mary,                    price = 24.99, ageRestriction = 21, videoFileName = "lost_mary.mp4",       category = "VAPES"),
        Product("03", "OFF STAMP CUBE KIT",  "1", "3", R.drawable.off_stamp,                    price = 17.99, ageRestriction = 21, videoFileName = "off_stamp.mp4",       category = "VAPES"),
        Product("04", "RAZ 25K LTX",         "1", "4", R.drawable.raz,                          price = 19.99, ageRestriction = 21, videoFileName = "raz.mp4",             category = "VAPES"),
        Product("05", "GEEK BAR PULSE X 40K","1", "5", R.drawable.geek_bar,                     price = 21.99, ageRestriction = 21, videoFileName = "geek_bar.mp4",        category = "VAPES"),
        Product("06", "ZYN CITRUS 6MG",      "2", "1", R.drawable.img_zyn_citrus_6mg,           price = 8.99,  ageRestriction = 21, videoFileName = "zyn_citrus.mp4",      category = "POUCHES"),
        Product("07", "ZYN WINTERGREEN 6MG", "2", "2", R.drawable.img_zyn_wintergreen_6mg,      price = 8.99,  ageRestriction = 21, videoFileName = "zyn_wintergreen.mp4", category = "POUCHES"),
        Product("08", "ZYN PEPPERMINT 3MG",  "2", "3", R.drawable.img_zyn_peppermint_3mg,       price = 8.99,  ageRestriction = 21, videoFileName = "zyn_peppermint.mp4",  category = "POUCHES"),
        Product("09", "NEWPORTS",            "3", "1", R.drawable.newport,                      price = 13.99, ageRestriction = 21, videoFileName = "newports.mp4",        category = "CIGARETTES"),
        Product("10", "BLACK & MILD",        "3", "2", R.drawable.black_mild,                   price = 7.99,  ageRestriction = 21, videoFileName = "black_mild.mp4",      category = "CIGARETTES"),
    )

    private lateinit var adapter: ProductAdapter
    private var selectedCategory = "ALL"

    private val clockHandler = Handler(Looper.getMainLooper())
    private val clockRunnable = object : Runnable {
        override fun run() {
            tvClock.text = SimpleDateFormat("h:mm a", Locale.US).format(Date())
            clockHandler.postDelayed(this, 30_000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productRecycler = findViewById(R.id.product_recycler)
        categoryRail     = findViewById(R.id.category_rail)
        tvClock          = findViewById(R.id.tv_clock)

        // Start hardware service
        try {
            val svcIntent = Intent(this, com.example.myapplication.hardware.HardwareService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(svcIntent)
            } else {
                @Suppress("DEPRECATION")
                startService(svcIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setupCategoryRail()
        setupRecyclerView()
        clockRunnable.run()
    }

    private fun setupCategoryRail() {
        val categories = listOf("ALL") + allProducts.map { it.category }.distinct()
        val gap = (8 * resources.displayMetrics.density).toInt()

        categories.forEach { cat ->
            val btn = Button(this).apply {
                text = cat
                textSize = 14f
                letterSpacing = 0.10f
                isAllCaps = true
                setTypeface(resources.getFont(R.font.archivo_black))
                setPadding(
                    (20 * resources.displayMetrics.density).toInt(),
                    (12 * resources.displayMetrics.density).toInt(),
                    (20 * resources.displayMetrics.density).toInt(),
                    (12 * resources.displayMetrics.density).toInt()
                )
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { marginEnd = gap }
                elevation = 0f
                stateListAnimator = null
                updateCatStyle(this, cat == selectedCategory)
                setOnClickListener {
                    selectedCategory = cat
                    updateCategory()
                }
            }
            categoryRail.addView(btn)
        }
    }

    private fun updateCatStyle(btn: Button, active: Boolean) {
        if (active) {
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.abs_bone))
            btn.setTextColor(Color.parseColor("#0A0A0B"))
        } else {
            btn.setBackgroundColor(Color.TRANSPARENT)
            btn.setTextColor(ContextCompat.getColor(this, R.color.abs_fog))
            // Transparent background with border isn't easy inline — use a drawable
            btn.background = ContextCompat.getDrawable(this, R.drawable.bg_cat_btn_inactive)
        }
    }

    private fun updateCategory() {
        val filtered = if (selectedCategory == "ALL") allProducts
                       else allProducts.filter { it.category == selectedCategory }
        adapter.updateProducts(filtered)

        // Refresh button styles
        for (i in 0 until categoryRail.childCount) {
            val btn = categoryRail.getChildAt(i) as? Button ?: continue
            val cat = btn.text.toString()
            updateCatStyle(btn, cat == selectedCategory)
        }
    }

    private fun setupRecyclerView() {
        productRecycler.layoutManager = GridLayoutManager(this, 2)
        productRecycler.setHasFixedSize(false)
        productRecycler.setItemViewCacheSize(20)

        adapter = ProductAdapter(allProducts) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("name",          product.name)
                putExtra("row",           product.row)
                putExtra("col",           product.col)
                putExtra("imageRes",      product.imageRes)
                putExtra("price",         product.price)
                putExtra("ageRestriction",product.ageRestriction ?: 21)
                putExtra("category",      product.category)
                putExtra("desc",          descriptionFor(product))
                putExtra("videoFileName", product.videoFileName)
            }
            startActivity(intent)
        }
        productRecycler.adapter = adapter
    }

    private fun descriptionFor(p: Product): String = when (p.category) {
        "VAPES"      -> "Disposable vape · ${p.name.lowercase().capitalize(Locale.US)}"
        "POUCHES"    -> "Tobacco-free pouches · 15 ct"
        "CIGARETTES" -> "Menthol cigarettes · pack of 20"
        else         -> p.name
    }

    override fun onResume() {
        super.onResume()
        clockRunnable.run()
        resetIdleTimer()
    }

    override fun onPause() {
        super.onPause()
        clockHandler.removeCallbacks(clockRunnable)
        idleHandler.removeCallbacks(idleRunnable)
    }

    // Reset the idle countdown on every touch — keeps the timer from firing while
    // the user is actively browsing the product grid.
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        resetIdleTimer()
        return super.dispatchTouchEvent(ev)
    }

    private fun resetIdleTimer() {
        idleHandler.removeCallbacks(idleRunnable)
        idleHandler.postDelayed(idleRunnable, IDLE_TIMEOUT_MS)
    }
}

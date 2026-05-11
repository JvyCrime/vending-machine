package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IdleActivity : KioskActivity() {


    private lateinit var viewFlipper: ViewFlipper
    private lateinit var dotContainer: LinearLayout
    private var autoAdvanceJob: Job? = null

    // ─────────────────────────────────────────────────────────────────────────────
    // AD SLIDES — 6 placeholder slides. Slots 1–5 are brand ad slots; slot 6 is
    // an ABS house ad held as a contact info for future prospects.
    // ─────────────────────────────────────────────────────────────────────────────
    private val ads = listOf(

        // ── SLOT 1 — Fume Ultra ──────────────────────────────────────────────────
        AdSlide(
            bgColor    = "#0D1B2A",
            adTitle    = "BLACK & MILD",
            adSubtitle = "Pipe Tobacco Experience · 5 Pack",
            badgeText  = "BEST SELLER",
            imageRes   = R.drawable.black_mild
        ),

        // ── SLOT 2 — Lost Mary ───────────────────────────────────────────────────
        AdSlide(
            bgColor    = "#1A0524",
            adTitle    = "LOST MARY NERA 70K",
            adSubtitle = "70,000 Puffs · Long-Lasting",
            badgeText  = "NEW ARRIVAL",
            imageRes   = R.drawable.lost_mary
        ),

        // ── SLOT 3 — ZYN ─────────────────────────────────────────────────────────
        AdSlide(
            bgColor    = "#0A2420",
            adTitle    = "ZYN NICOTINE POUCHES",
            adSubtitle = "Smoke-Free · 15 Count",
            badgeText  = "SMOKE FREE",
            imageRes   = R.drawable.img_zyn_wintergreen_6mg
        ),

        // ── SLOT 4 — RAZ ─────────────────────────────────────────────────────────
        AdSlide(
            bgColor    = "#1A0810",
            adTitle    = "RAZ 25K LTX",
            adSubtitle = "25,000 Puffs · Smooth Draw",
            badgeText  = "POPULAR PICK",
            imageRes   = R.drawable.raz
        ),

        // ── SLOT 5 — Geek Bar ────────────────────────────────────────────────────
        AdSlide(
            bgColor    = "#141416",
            adTitle    = "GEEK BAR PULSE X 40K",
            adSubtitle = "40,000 Puffs · Ultimate Power",
            badgeText  = "BEST SELLER",
            imageRes   = R.drawable.geek_bar
        ),

        // ── SLOT 6 — Advertise With Us ───────────────────────────────────────────
        AdSlide(
            bgColor    = "#0A0A0B",
            adTitle    = "WANT YOUR BRAND ON THIS SCREEN?",
            adSubtitle = "(727) 307-4615\ncontact.absvendingservices@gmail.com",
            badgeText  = "ADVERTISE WITH US",
            imageRes   = R.drawable.absqrcode,
            durationMs = 10_000L,
            layoutRes  = R.layout.item_ad_slide_contact
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idle)

        viewFlipper  = findViewById(R.id.vf_ads)
        dotContainer = findViewById(R.id.dot_container)

        viewFlipper.inAnimation  = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        viewFlipper.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)

        inflateSlides()
        setupDots()
    }

    override fun onResume() {
        super.onResume()
        startAutoAdvance()
    }

    override fun onPause() {
        super.onPause()
        autoAdvanceJob?.cancel()
    }

    // Any tap anywhere on the screen dismisses idle and returns to the product grid.
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) finish()
        return true
    }

    private fun inflateSlides() {
        val inflater = LayoutInflater.from(this)
        ads.forEach { ad ->
            val slide = inflater.inflate(ad.layoutRes, viewFlipper, false)
            slide.setBackgroundColor(Color.parseColor(ad.bgColor))
            slide.findViewById<TextView>(R.id.tv_badge).text = ad.badgeText
            slide.findViewById<TextView>(R.id.tv_ad_title).text = ad.adTitle
            slide.findViewById<TextView>(R.id.tv_ad_subtitle).text = ad.adSubtitle

            val imageView = slide.findViewById<ImageView>(R.id.iv_ad_image)
            if (ad.imageRes != null) {
                imageView.setImageResource(ad.imageRes)
            } else {
                // No image set for this slot — hide the ImageView so text centers cleanly.
                imageView.visibility = View.GONE
            }

            viewFlipper.addView(slide)
        }
    }

    private fun setupDots() {
        val density = resources.displayMetrics.density
        val dotSize = (8 * density).toInt()
        val dotGap  = (6 * density).toInt()

        repeat(ads.size) { i ->
            val dot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(dotSize, dotSize).also { lp ->
                    lp.marginEnd = dotGap
                }
                background = ContextCompat.getDrawable(
                    this@IdleActivity,
                    if (i == 0) R.drawable.bg_dot_active else R.drawable.bg_dot_inactive
                )
            }
            dotContainer.addView(dot)
        }
    }

    private fun updateDots(activeIndex: Int) {
        for (i in 0 until dotContainer.childCount) {
            dotContainer.getChildAt(i).background = ContextCompat.getDrawable(
                this,
                if (i == activeIndex) R.drawable.bg_dot_active else R.drawable.bg_dot_inactive
            )
        }
    }

    // Advances the carousel using each slide's own durationMs before moving to the next.
    private fun startAutoAdvance() {
        autoAdvanceJob?.cancel()
        autoAdvanceJob = lifecycleScope.launch {
            while (true) {
                delay(ads[viewFlipper.displayedChild].durationMs)
                val next = (viewFlipper.displayedChild + 1) % ads.size
                viewFlipper.displayedChild = next
                updateDots(next)
            }
        }
    }
}

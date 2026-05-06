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

    private companion object {
        const val AD_INTERVAL_MS = 5_000L   // time each ad slide is shown (ms)
    }

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var dotContainer: LinearLayout
    private var autoAdvanceJob: Job? = null

    // ─────────────────────────────────────────────────────────────────────────────
    // AD SLIDES — 6 placeholder slides. Slots 1–5 are brand ad slots; slot 6 is
    // an ABS house ad held as an open slot for a future brand partner.
    //
    // For each slot, look for the "TODO:" comment block — it tells you exactly
    // which fields to update and what ad asset to add when you're ready.
    // ─────────────────────────────────────────────────────────────────────────────
    private val ads = listOf(

        // ── SLOT 1 — Fume Ultra ──────────────────────────────────────────────────
        // TODO: Replace this placeholder with the final Fume Ultra ad creative.
        //   • bgColor    → Change to the brand's official background hex (or use a gradient
        //                  drawable on the slide root instead of a solid color).
        //   • imageRes   → Add your ad image to res/drawable/ and reference it here.
        //                  Recommended: R.drawable.ad_fume_ultra_banner
        //                  Ideal format: transparent-background PNG or square WebP, 400×400+.
        //   • adTitle    → Update to the final headline approved for this campaign.
        //   • adSubtitle → Update to the final tagline or offer copy.
        //   • badgeText  → Short promo label: "NEW FLAVOR", "LIMITED TIME", etc.
        AdSlide(
            bgColor    = "#0D1B2A",
            adTitle    = "FUME ULTRA 2500",
            adSubtitle = "2500 Puffs · Available Now",
            badgeText  = "IN STOCK",
            imageRes   = R.drawable.img_fume_ultra_strawberry_banana
        ),

        // ── SLOT 2 — Lost Mary ───────────────────────────────────────────────────
        // TODO: Replace this placeholder with the final Lost Mary / NERA ad creative.
        //   • imageRes   → Recommended: R.drawable.ad_lost_mary_banner
        //   • Same field notes as Slot 1 above.
        AdSlide(
            bgColor    = "#1A0524",
            adTitle    = "LOST MARY NERA 70K",
            adSubtitle = "70,000 Puffs · Long-Lasting",
            badgeText  = "NEW ARRIVAL",
            imageRes   = R.drawable.img_lost_mary
        ),

        // ── SLOT 3 — ZYN ─────────────────────────────────────────────────────────
        // TODO: Replace this placeholder with the final ZYN ad creative.
        //   • imageRes   → Recommended: R.drawable.ad_zyn_banner
        //   • Tip: You can rotate flavors here; change adTitle/adSubtitle to highlight
        //     a specific ZYN variant (e.g. "ZYN CITRUS 6MG" or "ZYN COOL MINT").
        AdSlide(
            bgColor    = "#0A2420",
            adTitle    = "ZYN NICOTINE POUCHES",
            adSubtitle = "Tobacco-Free · 15 Count",
            badgeText  = "TOBACCO FREE",
            imageRes   = R.drawable.img_zyn_citrus_6mg
        ),

        // ── SLOT 4 — RAZ ─────────────────────────────────────────────────────────
        // TODO: Replace this placeholder with the final RAZ ad creative.
        //   • imageRes → Recommended: R.drawable.ad_raz_banner
        //   • Same field notes as Slot 1 above.
        AdSlide(
            bgColor    = "#1A0810",
            adTitle    = "RAZ 25K LTX",
            adSubtitle = "25,000 Puffs · Smooth Draw",
            badgeText  = "POPULAR PICK",
            imageRes   = R.drawable.img_raz
        ),

        // ── SLOT 5 — Geek Bar ────────────────────────────────────────────────────
        // TODO: Replace this placeholder with the final Geek Bar Pulse X ad creative.
        //   • imageRes → Recommended: R.drawable.ad_geek_bar_banner
        //   • Same field notes as Slot 1 above.
        AdSlide(
            bgColor    = "#141416",
            adTitle    = "GEEK BAR PULSE X 40K",
            adSubtitle = "40,000 Puffs · Ultimate Power",
            badgeText  = "BEST SELLER",
            imageRes   = R.drawable.img_geek_bar
        ),

        // ── SLOT 6 — OPEN SLOT (ABS house ad) ───────────────────────────────────
        // This slide is intentionally left as an ABS Vending Services house ad
        // until a 6th brand partner is confirmed. When you're ready to add a new ad:
        //   1. Update bgColor, adTitle, adSubtitle, and badgeText below.
        //   2. Add a dedicated ad image to res/drawable/ and set imageRes.
        //   3. Remove this comment block.
        AdSlide(
            bgColor    = "#0A0A0B",
            adTitle    = "ABS VENDING SERVICES",
            adSubtitle = "Premium Products · Always Fresh",
            badgeText  = "ABS",
            imageRes   = null   // no image for the house ad; text-only is intentional
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
            val slide = inflater.inflate(R.layout.item_ad_slide, viewFlipper, false)
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

    // Advances the carousel and syncs the dot indicator every AD_INTERVAL_MS.
    private fun startAutoAdvance() {
        autoAdvanceJob?.cancel()
        autoAdvanceJob = lifecycleScope.launch {
            while (true) {
                delay(AD_INTERVAL_MS)
                val next = (viewFlipper.displayedChild + 1) % ads.size
                viewFlipper.displayedChild = next
                updateDots(next)
            }
        }
    }
}

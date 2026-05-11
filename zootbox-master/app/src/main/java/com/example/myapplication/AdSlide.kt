package com.example.myapplication

data class AdSlide(
    val bgColor: String,
    val adTitle: String,
    val adSubtitle: String,
    val badgeText: String,
    // TODO: Add a dedicated ad image drawable to res/drawable/ and set this field.
    // Example: imageRes = R.drawable.ad_fume_ultra_banner
    // Leave null to hide the image and show text-only content (e.g. the ABS open slot).
    val imageRes: Int? = null,
    val durationMs: Long = 7_000L,
    val layoutRes: Int = R.layout.item_ad_slide
)

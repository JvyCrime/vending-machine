# ABS Vending Services — Development TODO

## 1. Admin Screen (Re-implement)
**Priority: High**

An admin screen existed previously but is no longer accessible. It needs to be rebuilt.

- [ ] Create `AdminActivity.kt` with a secure entry point (e.g. long-press on logo or hidden tap sequence from MainActivity)
- [ ] Coil testing panel — trigger individual coil vend motors by slot number to verify dispensing works
- [ ] Stock management — view current stock count per product slot and allow manual edits
- [ ] Save stock data persistently (SharedPreferences or local SQLite/Room database)
- [ ] Re-add `AdminActivity` to `AndroidManifest.xml`
- [ ] Create layout `activity_admin.xml` with tab or section layout (Coil Test | Stock)

---

## 2. Out of Stock View on Product Page
**Priority: High**

- [ ] Add `inStock: Boolean` (or `stockCount: Int`) field to the product data model
- [ ] Update `ProductAdapter.kt` to show an "Out of Stock" overlay/badge on product cards when stock is 0
- [ ] On `ProductDetailActivity.kt`, disable the "Add to Cart" button and show an "Out of Stock" message when stock is depleted
- [ ] Visually grey out or dim the product image/card for out-of-stock items in the grid

---

## 3. Contact Information Area
**Priority: Medium**

- [ ] Add a contact/info section accessible from the main screen or product screen footer
- [ ] Include: business name (ABS Vending Services), phone number, email, and/or service request info
- [ ] Could be a bottom sheet, a dedicated `ContactActivity`, or a persistent footer on MainActivity
- [ ] Display machine ID or location info if relevant for service calls

---

## 4. ~~Fix ID Scanner~~ — COMPLETE
**E-Seek M260 age verification is fully working.**

- [x] Removed fake timer — success flow now requires a real `IdScannerManager.scanResult`
- [x] Added IDLE / SCANNING / SUCCESS / DENIED states to `activity_id_scan.xml`
- [x] USB permission flow fixed — `HardwareService` uses `StateFlow` so `IdScanActivity` reactively waits for the scanner
- [x] Fixed baud rate: M260 runs at **9600** baud (was incorrectly set to 115200 — caused all-null-byte output)
- [x] AAMVA parsing uses correct field codes (`DBB` for DOB, `DBA` for expiration)
- [x] DENIED state shows for under-21 and auto-resets to scanning after 4 s
- [x] Back button added as fallback when scanner is unavailable

---

## 5. Checkout Screen — COMPLETE
**After age verification passes, a checkout screen now displays the order summary and payment instructions.**

- [x] `CheckoutActivity.kt` receives product name, quantity, unit price, and total from `ProductDetailActivity`
- [x] Order summary card shows product, quantity, per-unit price, and bold total
- [x] Payment panel instructs customer to tap/insert/swipe at the Nayax card reader
- [x] 60-second countdown auto-cancels the session if no payment is made
- [x] Cancel button returns to the product detail screen
- [ ] Wire up `NayaxPaymentManager` when payment integration is ready

---

## 6. Idle Ad Space (Screensaver / Idle Screen)
**Priority: Medium**

**Current state:** `ScreensaverActivity.kt` plays a looping video (`R.raw.download`) after 30 seconds of idle.

- [ ] Replace or supplement the video with a proper ad space layout — support image slideshows, promotional text, and/or video ads
- [ ] Add ability to configure ad content (images/videos) without a full app rebuild — e.g. pull from a local folder or assets directory
- [ ] Consider a multi-slide carousel for rotating promotions (product highlights, pricing, brand messaging)
- [ ] Ensure "Touch to start" or similar prompt is visible so users know how to exit the screensaver
- [ ] Display "ABS Vending Services" branding on the idle screen
- [ ] Adjust idle timeout if needed (currently 30 seconds in `ProductGridActivity.kt`)

---

## 7. UI Redesign — Black, Red & White Theme — PARTIALLY COMPLETE

**Color Palette:** Black (`#000000` / `#141416`), Red (`#CC0000`), Bone (`#F5F1EA`), Wine (`#A82342`)

- [x] Define ABS brand color palette in `res/values/colors.xml` (`abs_black`, `abs_coal`, `abs_bone`, `abs_red`, `abs_wine_500`, `abs_wine_700`, `abs_ash`, `abs_fog`, `abs_green_ok/dark/total`)
- [x] Update `res/values/themes.xml` — `NoActionBar` theme, `abs_black` window background
- [x] Redesign `activity_main.xml` — black background, watercolor atmosphere, ABS logo header, category filter rail, product grid, footer with support number
- [x] Redesign `activity_product_detail.xml` — black background, coal spec/cart cards, red "ADD TO CART" button, wine watercolor hero, hard-offset shadows
- [x] Redesign `activity_id_scan.xml` — black background, bone scan card with hard-shadow, IDLE/SCANNING/SUCCESS states, scanning line animation
- [x] Redesign `activity_checkout.xml` — black background, coal order summary card, green total amount, red CANCEL button, countdown timer
- [x] Update all button drawable backgrounds — `bg_abs_primary_btn` (red), `bg_abs_bone_btn` (bone), `bg_abs_coal_card`, `bg_abs_qty_display`, `bg_abs_pcard`, `bg_cat_btn_inactive`, and hard-shadow variants
- [x] Replace gradient orbs and glass morphism — removed from `MainActivity` and `ProductDetailActivity`
- [x] Status bar and navigation bar hidden on all customer screens via `KioskActivity` base class (API 30+ `WindowInsetsController`; legacy `SYSTEM_UI_FLAG_IMMERSIVE_STICKY`)
- [ ] Redesign `activity_screensaver.xml` — `ScreensaverActivity` not found in current project; verify if screensaver exists and needs rebrand
- [ ] Verify all text has sufficient contrast (WCAG AA) for bright retail lighting conditions

---

## 8. Replace Zootbox Branding → ABS Vending Services — PARTIALLY COMPLETE

- [x] `item_product_card.xml` header label — now shows product category name (VAPES / POUCHES / CIGARETTES), no "zb" branding
- [x] `ZootBoxGradientView.kt` — file does not exist in current project; no action needed
- [x] `zootbox_title.xml` — file does not exist in current project; no action needed
- [x] `zb_logo.png` / `title.png` — files do not exist in current project; no action needed
- [x] Main screen header — now shows "ABS Vending Services" logo lockup (Ancient Medium + Back_In_the_USSR_DL_k fonts)
- [ ] App name in `res/values/strings.xml` — still reads `"My Application"`, change to `"ABS Vending Services"`
- [ ] App launcher icon (`ic_launcher.webp` in all mipmap densities) — still the default Android icon; replace with ABS Vending branding
- [ ] `ProductDetailActivity.kt` line 48: `VIDEO_BASE_PATH` still references `/Movies/ZootBox/` — update path to `/Movies/ABSVending/` (and rename the folder on-device)
- [ ] Search entire codebase for any remaining "Zootbox" / "ZB" / "zb_" string literals not yet caught

---

## Notes

- All age-restricted products (vapes, cigarettes, pouches) require a working ID scanner fix before the machine can legally operate
- Admin screen access method should be subtle but reliable (suggest: 5-tap sequence on a logo corner)
- Stock counts should sync with coil test results where possible — a successful vend should decrement stock

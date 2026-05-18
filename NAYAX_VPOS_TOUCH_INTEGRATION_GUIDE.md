# Nayax VPOS Touch — Complete Android Integration Guide

> A field-tested, end-to-end recipe for getting the Nayax VPOS Touch working on Android with the Marshall SDK. Written from a working production deployment (ZootBox vending kiosk, 2025-12 → 2026-01). Every fix below was a real bug we hit; every code snippet was verified against the running source.

---

## Table of contents

1. [TL;DR — the four things that almost always go wrong](#tldr)
2. [Hardware bill of materials](#hardware)
3. [The #1 gotcha: USE THE FTDI INTERFACE](#1-gotcha)
4. [Complete VID/PID reference](#vidpid)
5. [Marshall SDK configuration (working values)](#sdk-config)
6. [State machine — what JADX broke and how to fix it](#state-machine)
7. [The CRC sign-extension bug](#crc-bug)
8. [Duplicate packet processing (CMD=13 boot loop)](#duplicate-packets)
9. [Singleton reset — fixing "CASH ONLY" after app restart](#singleton-reset)
10. [Boot timing — the USB permission race condition](#boot-timing)
11. [Multi-device permission queue](#permission-queue)
12. [Auto-grant script for kiosks](#auto-grant)
13. [Pre-Selection vs Post-Selection (and why we picked Pre-Selection)](#pre-vs-post)
14. [Complete payment flow](#payment-flow)
15. [Nayax Core cloud configuration (mandatory)](#cloud-config)
16. [Verification commands](#verification)
17. [Troubleshooting matrix](#troubleshooting)
18. [File-by-file change reference](#file-reference)
19. [Common pitfalls / things not to do](#pitfalls)

---

<a id="tldr"></a>
## 1. TL;DR — the four things that almost always go wrong

If your Nayax VPOS isn't working, it is almost certainly one of these four:

| # | Symptom | Cause | Section |
|---|---------|-------|---------|
| 1 | Reader shows "Cash Only", no packets | Connecting to CDC-ACM (`26f1:5650`) instead of FTDI (`0403:6015`) | [#3](#1-gotcha) |
| 2 | Reader shows "Cash Only" after app restart | `vmc_framework` singleton holds stale `reader_always_on=false` config | [#9](#singleton-reset) |
| 3 | "USB permission denied" on boot, works after manual restart | `HardwareService` starts before USB permission DB loads | [#10](#boot-timing) |
| 4 | App throws `UnsupportedOperationException` on every Marshall event | JADX failed to decompile `vmc_vend_t.handleMessage()` | [#6](#state-machine) |

There are also a handful of secondary bugs (CRC sign-extension, duplicate packet processing causing CMD=13 boot loop, `vend_approved` callback not firing in Pre-Selection mode) covered below.

---

<a id="hardware"></a>
## 2. Hardware bill of materials

| Component | Notes |
|-----------|-------|
| **Nayax VPOS Touch** | Card / NFC payment terminal |
| **Marshall Cable** (Nayax P/N C130026) | 40-pin header on VPOS → DB9 serial |
| **USB-to-Serial adapter** (FTDI FT232R recommended) | DB9 → USB-A. The VPOS internally also exposes an FTDI USB chip (PID 0x6015) — you'll see this VID/PID in the USB device list, not your external adapter's VID/PID. |
| **24V DC 2A power supply** | Dedicated for the VPOS Touch — do not share with the tablet |
| **Android device** | API 26+ (Oreo). Tested on Android 11+ on a 1080×1920 kiosk tablet |

**Wiring:**
```
Nayax VPOS Touch (40-pin header)
      ↓ Marshall Cable C130026
DB9 Serial Connector
      ↓ USB-to-Serial adapter
Android tablet USB-C
      + 24V DC 2A power supply (VPOS Touch)
```

---

<a id="1-gotcha"></a>
## 3. The #1 gotcha: USE THE FTDI INTERFACE

The Nayax VPOS Touch enumerates as **two different USB interfaces** depending on platform / firmware. Only one of them works.

| Interface | VID:PID | What it returns | Verdict |
|-----------|---------|-----------------|---------|
| **FTDI (Chipi-X)** | `0x0403 : 0x6015` | Real Marshall protocol packets | ✅ **USE THIS** |
| CDC-ACM | `0x26f1 : 0x5650` | NAK bytes only | ❌ Don't use |
| CDC-ACM (alt) | `0x26f1 : 0x222a` | NAK bytes only | ❌ Don't use |

The FTDI device shows up in the Android USB permission dialog as **"Chipi-X"** (manufactured by FTDI). Don't be confused by the name — it's the right device.

### Detection code that works

```kotlin
// HardwareService.kt
companion object {
    const val FTDI_VID         = 0x0403
    const val NAYAX_FTDI_PID   = 0x6015   // <-- THE ONE THAT WORKS
    const val NAYAX_VID        = 0x26f1   // CDC-ACM, fallback only
    const val NAYAX_PID        = 0x5650
    const val NAYAX_PID_ALT    = 0x222a
    const val NAYAX_BAUD_RATE  = 115200   // Marshall protocol requirement
}

// Always look for FTDI first
val nayaxDevice = findUsbDevice(FTDI_VID, NAYAX_FTDI_PID)
    ?: findUsbDevice(NAYAX_VID, NAYAX_PID)
    ?: findUsbDevice(NAYAX_VID, NAYAX_PID_ALT)
```

### Driver selection

Picking the right driver is just as important as picking the right interface:

```kotlin
val driver: UsbSerialDriver = if (device.vendorId == FTDI_VID) {
    FtdiSerialDriver(device)   // Required for the FTDI Chipi-X interface
} else {
    CdcAcmSerialDriver(device) // Only used if you're stuck with the CDC fallback
}
```

The `FtdiSerialDriver` handles the FTDI protocol's **2-byte status header** that prefixes every read. If you point a CDC-ACM driver at an FTDI device, you'll receive garbage with the status bytes interleaved.

### Register the device in the manifest

```xml
<!-- res/xml/device_filter.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- E-Seek M260 ID Scanner -->
    <usb-device vendor-id="1027" product-id="24577" />

    <!-- Nayax VPOS — CDC-ACM fallback -->
    <usb-device vendor-id="9969" product-id="22096" />
    <usb-device vendor-id="9969" product-id="8746" />

    <!-- Nayax VPOS internal FTDI chip — THIS is what actually works -->
    <usb-device vendor-id="1027" product-id="24597" />
</resources>
```

(Remember: VID/PID are **decimal** in `device_filter.xml`. `0x0403 = 1027`, `0x6015 = 24597`.)

---

<a id="vidpid"></a>
## 4. Complete VID/PID reference

| Device | Hex | Decimal | Driver | Baud |
|--------|-----|---------|--------|------|
| Nayax VPOS — FTDI (Chipi-X) ✅ | `0x0403 : 0x6015` | `1027 : 24597` | `FtdiSerialDriver` | 115200 |
| Nayax VPOS — CDC-ACM | `0x26f1 : 0x5650` | `9969 : 22096` | `CdcAcmSerialDriver` | 115200 |
| Nayax VPOS — CDC-ACM alt | `0x26f1 : 0x222a` | `9969 : 8746` | `CdcAcmSerialDriver` | 115200 |
| E-Seek M260 ID Scanner | `0x0403 : 0x6001` | `1027 : 24577` | (USB HID-style) | 9600 |

**Watch out:** The ID scanner and Nayax FTDI interface share the FTDI VID `0x0403`. Match by **(VID, PID) tuple**, never by VID alone.

---

<a id="sdk-config"></a>
## 5. Marshall SDK configuration (working values)

Every flag in this block matters. We learned each one the hard way.

```kotlin
val config = vmc_configuration().apply {
    port_vpos      = serialPort
    port_vpos_baud = 115200

    // EXACT DMVI reference values — these are pre-registered with Nayax
    model    = "android-marshall-demo"
    serial   = "1434324619381374"
    sw_ver   = "1.0.0.0"

    machine_type = vmc_configuration.machine_type_type_retail

    // Feature flags (every one of these is load-bearing)
    mifare_approved_by_vmc_support  = false
    mag_card_approved_by_vmc_support = false
    multi_vend_support              = false  // CRITICAL: see note below
    multi_session_support           = false
    price_not_final_support         = false

    reader_always_on = true   // Reader stays armed → "Tap Card" instead of "Cash Only"
    always_idle      = true   // Pre-Selection: app sends price BEFORE card tap
    vend_denied_policy = 0

    // Debugging — turn down for production
    dump_packets_level = 2
    debug = true
}
```

### Why `multi_vend_support = false`

We initially set this to `true` to support shopping carts, but Nayax returned **Auth Status = -1** on every transaction. The Extended MDB multi-vend code path requires a backend feature that isn't enabled by default on most Nayax accounts.

**Fix:** Use `multi_vend_support = false` and treat carts as a series of single vends from the SDK's perspective. The app can still display a multi-item cart UI; just send one `vend_request` per item internally if you need true multi-vend, or charge the cart total as a single vend and dispense sequentially.

### Why `reader_always_on = true`

Without this, the VPOS sits in idle mode and displays "Cash Only" because the card reader is disabled. Setting it to `true` causes `vmc_vend_t.change_state(idle)` to call `session_start(0)`, which arms the reader.

### Why `always_idle = true`

This enables **Pre-Selection** flow (price displayed on VPOS BEFORE the customer taps). Without it, the customer has to tap first, then the price appears — which is confusing and creates an opening for misunderstandings ("Wait, how much?"). See [§13](#pre-vs-post).

### Important: do NOT call `session_start(0)` manually

We tried this. It looked like a clean way to arm the reader. The VPOS interpreted it as an invalid payment request, entered an error state, and stopped responding to keep-alives.

```kotlin
// In onReady() — DO NOT do this:
// framework.vend.session_start(0)  ❌

// Correct: just flip your ready flag and wait for the SDK
override fun onReady(config: vpos_config_t?) {
    _isReady.value = true
    _paymentState.value = PaymentState.READY
}
```

### Serial parameters

```
Baud rate     : 115200   (Marshall protocol requirement)
Data bits     : 8
Stop bits     : 1
Parity        : None
Flow control  : None     (do NOT toggle DTR/RTS)
```

---

<a id="state-machine"></a>
## 6. State machine — what JADX broke and how to fix it

If you're decompiling the Marshall SDK from a reference APK with JADX, **`vmc_vend_t.handleMessage()` will fail to decompile** and you'll see `throw new UnsupportedOperationException("Method not decompiled: ...")` thrown on every event.

You have to reconstruct it. Here is the full working version, derived from state-machine analysis. It also contains the Pre-Selection patches — without those, vend_request is rejected in Pre-Selection mode.

### State and event constants

```java
// States
private static final int vmc_vend_state_init_e              = 0;
private static final int vmc_vend_state_idle_e              = 1;
private static final int vmc_vend_state_reader_enabled_e    = 2;
private static final int vmc_vend_state_wait_vend_request_e = 3;
private static final int vmc_vend_state_vend_process_e      = 4;
private static final int vmc_vend_state_wait_end_session_e  = 5;
private static final int vmc_vend_state_reader_disable_e    = 6;

// Events
private static final int vmc_event_init_done_e       = 0;
private static final int vmc_event_reader_enable_e   = 1;
private static final int vmc_event_session_begin_e   = 2;
private static final int vmc_event_session_cancel_e  = 3;
private static final int vmc_event_vend_request_e    = 4;
private static final int vmc_event_vend_approved_e   = 5;
private static final int vmc_event_vend_denied_e     = 6;
private static final int vmc_event_session_end_e     = 7;
private static final int vmc_event_session_close_e   = 8;
```

### `handleMessage()` (reconstructed, with Pre-Selection fixes)

```java
@Override
public boolean handleMessage(MsgObject msgObject) {
    if (!super.handleMessage(msgObject)) return false;  // stop event 65535

    int eventId = msgObject.id;
    Object eventData = msgObject.data;
    Log.d(TAG, String.format("handleMessage: event=%d, state=%d", eventId, m_vend_state));

    switch (eventId) {
        case vmc_event_init_done_e:                      // 0
            change_state(vmc_vend_state_idle_e);
            break;

        case vmc_event_reader_enable_e:                  // 1
            if (m_vend_state == vmc_vend_state_idle_e) {
                vendSessionType(m_session_type);
                change_state(vmc_vend_state_reader_enabled_e);
            }
            break;

        case vmc_event_session_begin_e:                  // 2 — card tapped
            if (m_vend_state == vmc_vend_state_reader_enabled_e) {
                int fundsAvailable = (eventData instanceof Integer) ? (Integer) eventData : 0;
                if (m_current_session == null) {
                    m_current_session = new vend_session_t(new ArrayList<>());
                }
                m_current_session.funds_avail = fundsAvailable;
                m_vend_callbacks.onSessionBegin(fundsAvailable);

                // PRE-SELECTION FIX: vend_request was already sent — go straight to VEND_PROCESS
                if (m_vmc_configuration.always_idle
                        && m_current_session.products_list != null
                        && m_current_session.products_list.size() > 0) {
                    Log.d(TAG, "Pre-Selection mode: Card tapped, proceeding to vend process");
                    change_state(vmc_vend_state_vend_process_e);
                } else {
                    // Post-Selection: now wait for the app to send vend_request
                    change_state(vmc_vend_state_wait_vend_request_e);
                }
            }
            break;

        case vmc_event_session_cancel_e:                 // 3
            if (m_vend_state == vmc_vend_state_reader_enabled_e
                    || m_vend_state == vmc_vend_state_wait_vend_request_e) {
                readerCancel();
                change_state(vmc_vend_state_reader_disable_e);
            }
            break;

        case vmc_event_vend_request_e:                   // 4 — app requests vend
            // PRE-SELECTION FIX: also accept vend_request from READER_ENABLED when always_idle=true
            boolean allowVendRequest =
                m_vend_state == vmc_vend_state_wait_vend_request_e
                || (m_vmc_configuration.always_idle
                    && m_vend_state == vmc_vend_state_reader_enabled_e);

            if (allowVendRequest && eventData instanceof vend_session_t) {
                vend_session_t session = (vend_session_t) eventData;
                m_current_session = session.m10clone();

                if (m_vmc_configuration.multi_vend_support && session.products_list.size() > 1) {
                    vendExMultiVendRequest(session.products_list);
                } else if (session.products_list.size() > 0) {
                    vend_item_t item = session.products_list.get(0);
                    vendRequest(item.code, (short) item.price);
                }

                // PRE-SELECTION FIX: stay in READER_ENABLED waiting for card tap
                if (m_vmc_configuration.always_idle
                        && m_vend_state == vmc_vend_state_reader_enabled_e) {
                    Log.d(TAG, "Pre-Selection mode: Waiting for card tap after vend_request");
                    // do NOT change state
                } else {
                    change_state(vmc_vend_state_vend_process_e);
                }
            }
            break;

        case vmc_event_vend_approved_e:                  // 5 — VPOS approved
            // PRE-SELECTION FIX: approval can arrive in state 2 (READER_ENABLED) too
            if (m_vend_state == vmc_vend_state_vend_process_e
                    || m_vend_state == vmc_vend_state_reader_enabled_e) {
                if (m_vend_callbacks.onVendApproved(m_current_session)) {
                    change_state(vmc_vend_state_wait_end_session_e);
                } else {
                    vendFailure();
                    change_state(vmc_vend_state_wait_end_session_e);
                }
            }
            break;

        case vmc_event_vend_denied_e:                    // 6
            if (m_vend_state == vmc_vend_state_vend_process_e
                    || m_vend_state == vmc_vend_state_reader_enabled_e) {
                if (m_current_session != null) {
                    m_current_session.session_status = session_status_vend_denied_e;
                }
                m_vend_callbacks.onVendDenied(m_current_session);
                if (m_vend_state == vmc_vend_state_vend_process_e) {
                    change_state(vmc_vend_state_wait_end_session_e);
                }
            }
            break;

        case vmc_event_session_end_e:                    // 7
            if (m_vend_state == vmc_vend_state_wait_end_session_e
                    || m_vend_state == vmc_vend_state_reader_disable_e) {
                change_state(vmc_vend_state_idle_e);
            }
            break;

        case vmc_event_session_close_e:                  // 8 — app closes session
            if (eventData instanceof vend_session_t) {
                vend_session_t session = (vend_session_t) eventData;
                if (session.products_list != null && session.products_list.size() > 0) {
                    vend_item_t item = session.products_list.get(0);
                    if (session.session_status == session_status_ok_e) {
                        if (m_vmc_configuration.multi_vend_support && session.products_list.size() > 1) {
                            vendExMultiVendSuccess(session.products_list);
                        } else {
                            vendSuccess(item.code);
                        }
                    } else {
                        vendFailure();
                    }
                    vendCloseSession((byte) session.session_status, item.code,
                        (short) item.price, item.qty, item.unit);
                }
                vendSessionComplete();
            }
            change_state(vmc_vend_state_reader_disable_e);
            break;
    }
    return true;
}
```

### Why each Pre-Selection fix is needed

| Fix | Without it |
|-----|------------|
| `vend_request` accepted in state 2 | App sends price → SDK silently drops it; VPOS keeps showing default screen |
| Stay in state 2 after `vend_request` | SDK transitions to state 4 immediately; when card is tapped, `session_begin` is ignored |
| `session_begin` jumps to state 4 if `products_list` non-empty | Card tap puts state machine in `WAIT_VEND_REQUEST` and you've already sent it — deadlock |
| `vend_approved` accepted in state 2 | Approval arrives, callback never fires, motors never run, customer's card was charged |

---

<a id="crc-bug"></a>
## 7. The CRC sign-extension bug

If your link briefly comes up and then drops with logs like:

```
wrong crc on packet(13), index: 0, length: 7, crc_rx: a07e, crc_calc: ffffa07e
link down
```

…you have the CRC sign-extension bug. Java's `short` is signed, so the CRC value sign-extends to 32 bits when compared.

### Fix

In `marshall_t.java` (around line 277), mask with `0xFFFF`:

```java
// BEFORE (broken):
int Calc_CRC_CCITT = marshall_t.Calc_CRC_CCITT(bArr, i, this.packet_len, (short) 0);

// AFTER (working):
int Calc_CRC_CCITT = marshall_t.Calc_CRC_CCITT(bArr, i, this.packet_len, (short) 0) & 0xFFFF;
```

Do **not** use `UShort.MAX_VALUE` from Kotlin — it's an inline class wrapper and won't mask correctly when called from Java. Use the literal `0xFFFF`.

---

<a id="duplicate-packets"></a>
## 8. Duplicate packet processing (CMD=13 boot loop)

**Symptom:** SDK connects, then every ~17–20 seconds the link drops and re-handshakes with `CMD=13`. No transactions ever complete.

```
Marshall: Rcv MDB CMD = 13, MDB Sub CMD = 81
Session type - info
[17 seconds later]
link down
Marshall: Rcv MDB CMD = 13...   (forever)
```

**Cause:** Two paths are processing the same packets:

1. `UsbSerialBridge.readLoop()` calls `dataCallback.onDataReceived()` → fills `AndroidUsbPort`'s `CircularBuffer`
2. `UsbSerialBridge.readLoop()` ALSO calls `processPackets()` → dispatches via `linkEvents.onReceive()`
3. `SerialPortPollingRunnable` (in `vmc_link.java`) polls `AndroidUsbPort` and ALSO calls `onReceive()`

The VPOS sees duplicate handshake responses, decides the VMC is buggy, and resets the link.

### Fix

In `UsbSerialBridge.java` (read loop, around line 282), add `continue` after dispatching to the callback so `processPackets()` is skipped on the AndroidUsbPort path:

```java
if (dataCallback != null) {
    byte[] rawData = new byte[bytesRead];
    System.arraycopy(tempBuffer, 0, rawData, 0, bytesRead);
    dataCallback.onDataReceived(rawData);
    logData("RX->Callback", tempBuffer, bytesRead);
    continue;  // <-- THE FIX: SerialPortPollingRunnable handles framing
}

// Legacy path: only used when no dataCallback registered
System.arraycopy(tempBuffer, 0, rxBuffer, head, bytesRead);
head += bytesRead;
processPackets();
```

### Architecture (so this makes sense)

```
USB read thread          dataCallback         CircularBuffer (16KB)
    │                         │                       │
    ├── byte[] read ──────────▶                        │
    │                         ├── write ─────────────▶ │
    │                                                  │
    └── continue (skip processPackets)                 │
                                                       ▼
                                          SerialPortPollingRunnable
                                                       │
                                          (Marshall packet framing)
                                                       │
                                                       ▼
                                                vmc_link.onReceive()
```

---

<a id="singleton-reset"></a>
## 9. Singleton reset — fixing "CASH ONLY" after app restart

**Symptom:** Fresh install works perfectly. Restart the app (without force-stopping) and the VPOS displays "CASH ONLY". Reboot the device and it works again.

**Cause:** `vmc_framework` is a static singleton. On app restart within the same Android process, `getInstance()` returns the old instance with `vmc_vend_t` still holding a stale `m_vmc_configuration` reference where `reader_always_on = false`. Even though you call `configure(config)` with the new config, the cached reference inside `vmc_vend_t` doesn't update — so when `change_state(idle)` runs, it skips the `session_start(0)` call that would arm the reader.

### Fix — add a `reset()` method

```java
// vmc_framework.java
public static void reset() {
    if (m_instance != null) {
        Log.d(TAG, "Resetting vmc_framework singleton");
        try {
            m_instance.stop();
        } catch (Exception e) {
            Log.e(TAG, "Error stopping framework during reset: " + e.getMessage());
        }
        m_instance = null;
    }
}
```

Call it before every `getInstance()` and on close:

```kotlin
// NayaxPaymentManager.initialize()
vmc_framework.reset()
framework = vmc_framework.getInstance().apply {
    link.set_serial_port(androidUsbPort)
        .set_lowlevel(usbBridge)
        .configure(config)
        .set_events(this@NayaxPaymentManager)
    vend.register_callbacks(this@NayaxPaymentManager)
}

// NayaxPaymentManager.close()
vmc_framework.reset()
framework = null
```

---

<a id="boot-timing"></a>
## 10. Boot timing — the USB permission race condition

**Symptom:** "USB permission denied" on every boot. App works after a manual restart.

**Cause:** On boot, when your app launches as the HOME activity (~10s after power-on), the Android USB permission database (`/data/system/users/0/usb_device_manager.xml`) hasn't loaded yet — it loads around 15–20s. If `HardwareService` starts immediately, it calls `usbManager.hasPermission(device)` → returns `false` because the DB isn't loaded → permission request fires before the user's previously-granted permission is visible.

### Fix — delay HardwareService start by 10 seconds

```kotlin
// MainActivity.onCreate()
android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
    val serviceIntent = Intent(this, HardwareService::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(serviceIntent)
    } else {
        @Suppress("DEPRECATION")
        startService(serviceIntent)
    }
    bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    Log.i("MainActivity", "HardwareService started after 10s delay")
}, 10000)
```

### Boot timeline

```
0s   : Power on
~10s : MyApplication launches as HOME → "Starting…" overlay
~15s : USB permission DB loads
~20s : HardwareService starts (after 10s delay)
~21s : Nayax permission already granted → port opens
~22s : Marshall SDK link comes up
~23s : Loading overlay fades out, app is interactive
```

### Loading overlay (so users don't think it's broken)

The 10-second delay is invisible to operators, who try to tap the screen during initialization and conclude the reader is broken. Add a full-screen overlay:

```kotlin
sealed class HardwareStatus {
    object WaitingForUsbDatabase : HardwareStatus()
    object ConnectingNayax       : HardwareStatus()
    object ConnectingScanner     : HardwareStatus()
    object Ready                 : HardwareStatus()
    data class Error(val message: String) : HardwareStatus()
}
```

```kotlin
// MainActivity — observe NayaxPaymentManager.isReady, hide overlay when true
private fun observeHardwareStatus() {
    lifecycleScope.launch {
        hardwareService?.getNayaxPaymentManager()?.isReady?.collect { isReady ->
            if (isReady && loadingOverlay.visibility == View.VISIBLE) {
                loadingStatusText.text = getString(R.string.loading_ready)
                loadingSpinner.visibility = View.GONE
                delay(800)
                loadingOverlay.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .withEndAction {
                        loadingOverlay.visibility = View.GONE
                        loadingOverlay.alpha = 1f
                    }.start()
            }
        }
    }
}
```

Add a 30-second bypass timeout so operators can still use the rest of the app if the reader never comes up.

---

<a id="permission-queue"></a>
## 11. Multi-device permission queue

If you have multiple USB devices that need permission (e.g., Nayax + ID scanner), Android only renders **one permission dialog at a time**. Requesting two within a few hundred milliseconds causes the second one to be silently lost.

### Fix — queue permissions, request one at a time

```kotlin
// HardwareService.kt
private val pendingPermissionDevices = mutableListOf<UsbDevice>()

private fun queuePermissionRequest(device: UsbDevice) {
    synchronized(pendingPermissionDevices) {
        pendingPermissionDevices.add(device)
    }
}

private fun processNextPermissionRequest() {
    synchronized(pendingPermissionDevices) {
        if (pendingPermissionDevices.isNotEmpty()) {
            val device = pendingPermissionDevices.removeAt(0)
            requestUsbPermissionInternal(device)
        }
    }
}

private fun requestUsbPermissionInternal(device: UsbDevice) {
    val permissionIntent = PendingIntent.getBroadcast(
        this,
        device.deviceId,                       // device ID as request code differentiates devices
        Intent(ACTION_USB_PERMISSION).apply {
            putExtra(UsbManager.EXTRA_DEVICE, device)
        },
        PendingIntent.FLAG_MUTABLE
    )
    usbManager.requestPermission(device, permissionIntent)
}

// In the BroadcastReceiver, after handling the result:
processNextPermissionRequest()
```

### Initialization order

Initialize **Nayax first**, ID scanner second. The reader is the more critical of the two for the user's experience.

```kotlin
private fun initializeHardware() {
    // 1. Nayax (FIRST priority)
    val nayaxDevice = findUsbDevice(FTDI_VID, NAYAX_FTDI_PID)
        ?: findUsbDevice(NAYAX_VID, NAYAX_PID)
        ?: findUsbDevice(NAYAX_VID, NAYAX_PID_ALT)
    nayaxDevice?.let {
        if (usbManager.hasPermission(it)) initializeNayax(it)
        else queuePermissionRequest(it)
    }

    // 2. ID Scanner (SECOND priority)
    val idScannerDevice = findUsbDevice(ID_SCANNER_VID, ID_SCANNER_PID)
    idScannerDevice?.let {
        if (usbManager.hasPermission(it)) initializeScanner(it)
        else queuePermissionRequest(it)
    }

    processNextPermissionRequest()
}
```

---

<a id="auto-grant"></a>
## 12. Auto-grant script for kiosks

For unattended kiosk deployment, you don't want anyone tapping permission dialogs. This script monitors for the dialog and clicks "Always allow" + OK automatically. Requires a **rooted device** with `/system` writable.

### `auto_usb_grant.sh` (multi-device version)

```bash
#!/system/bin/sh
# Auto-grant USB permissions on boot. Handles multiple sequential dialogs.

LOG_TAG="USB_AUTO_GRANT"
log -t $LOG_TAG "Starting USB auto-grant service (multi-device support)"

# These coordinates are for a 1080x1920 display rotated 180°. ADJUST FOR YOUR DEVICE.
# Discover them with: adb shell uiautomator dump /sdcard/window_dump.xml
# Checkbox bounds: [499,953][579,993] → center (539, 973)
# OK button bounds: [864,1056][948,1110] → center (906, 1083)
CHECKBOX_X=539; CHECKBOX_Y=973
OK_X=906;      OK_Y=1083

DIALOGS_CLICKED=0
MAX_DIALOGS=5
NO_DIALOG_COUNT=0
MAX_NO_DIALOG=10
TOTAL_ATTEMPTS=0
MAX_TOTAL_ATTEMPTS=60

while [ $TOTAL_ATTEMPTS -lt $MAX_TOTAL_ATTEMPTS ] && [ $DIALOGS_CLICKED -lt $MAX_DIALOGS ]; do
    DIALOG_CHECK=$(dumpsys window | grep -i "UsbPermission" | wc -l)
    if [ "$DIALOG_CHECK" -gt 0 ]; then
        log -t $LOG_TAG "Dialog detected — clicking Allow"
        input tap $CHECKBOX_X $CHECKBOX_Y
        sleep 0.5
        input tap $OK_X $OK_Y
        sleep 0.5
        DIALOGS_CLICKED=$((DIALOGS_CLICKED + 1))
        NO_DIALOG_COUNT=0
        sleep 1
    else
        NO_DIALOG_COUNT=$((NO_DIALOG_COUNT + 1))
        if [ $DIALOGS_CLICKED -gt 0 ] && [ $NO_DIALOG_COUNT -ge $MAX_NO_DIALOG ]; then
            log -t $LOG_TAG "Done — granted $DIALOGS_CLICKED permissions"
            break
        fi
        sleep 1
    fi
    TOTAL_ATTEMPTS=$((TOTAL_ATTEMPTS + 1))
done
```

### Init service `auto_usb_grant.rc`

```
service auto_usb_grant /system/bin/sh /system/bin/auto_usb_grant.sh
    class main
    user root
    group root
    seclabel u:r:su:s0
    disabled

on property:dev.bootcomplete=1
    start auto_usb_grant
```

### Deployment

```bash
adb root
adb remount
adb push auto_usb_grant.sh /system/bin/
adb push auto_usb_grant.rc /system/etc/init/
adb shell chmod 755 /system/bin/auto_usb_grant.sh
adb shell chmod 644 /system/etc/init/auto_usb_grant.rc
adb reboot
```

### Discovering tap coordinates for your device

```bash
# Trigger the dialog (e.g. plug in the USB device, launch the app)
adb shell uiautomator dump /sdcard/window_dump.xml
adb pull /sdcard/window_dump.xml
# Search the XML for "checkbox" and "OK" bounds, compute centers
```

---

<a id="pre-vs-post"></a>
## 13. Pre-Selection vs Post-Selection

| | Pre-Selection (`always_idle = true`) | Post-Selection (`always_idle = false`) |
|---|---|---|
| **What customer sees first** | Price displayed on VPOS | Generic "Tap Card" prompt |
| **Order of events** | App sends price → customer taps card → approve | Customer taps → app sends price → approve |
| **UX** | Better: customer sees price before paying | Worse: customer doesn't know amount until after tap |
| **Required SDK changes** | Yes — patch `vmc_vend_t` (see [§6](#state-machine)) | None |
| **Required Nayax Core setting** | "Always Idle" must be enabled in MDB Configuration | Default works |

We strongly recommend Pre-Selection. Post-Selection creates customer disputes ("I thought it was cheaper").

### Pre-Selection event sequence

```
1. SDK init        → handleMessage(event=0, state=0)  → state 1 (IDLE)
                                                        reader_always_on=true → session_start(0)

2. Reader enable   → handleMessage(event=1, state=1)  → state 2 (READER_ENABLED)
                                                        readerEnable() → VPOS shows "Tap Card"

3. App sends price → vend.vend_request(session)
                   → handleMessage(event=4, state=2)  → vendRequest(code, price)
                                                        VPOS shows "$X.XX — Tap Card"
                                                        STAY in state 2

4. Card tapped     → handleMessage(event=2, state=2)  → onSessionBegin()
                                                        products_list non-empty → state 4

5. Approved        → handleMessage(event=5, state=4 or 2) → onVendApproved()
                                                            → state 5 (WAIT_END_SESSION)

6. App vends product, then confirmVend(true)
                   → vend.session_close(session)
                   → handleMessage(event=8) → state 6 (READER_DISABLE)

7. Settlement      → onSettlement(success)
                   → eventually state 1 (IDLE) → arms reader for next sale
```

---

<a id="payment-flow"></a>
## 14. Complete payment flow

### Public API

```kotlin
class NayaxPaymentManager(...) :
    vmc_vend_t.vend_callbacks_t,
    vmc_link.vmc_link_events_t {

    val isReady: StateFlow<Boolean>
    val paymentState: StateFlow<PaymentState>
    val paymentResult: StateFlow<PaymentResult?>

    suspend fun initiatePayment(amount: Double, itemNumber: Int = 1): Boolean
    suspend fun confirmVend(success: Boolean)
    fun cancelPayment()
    fun close()
}
```

### initiatePayment

```kotlin
suspend fun initiatePayment(amount: Double, itemNumber: Int = 1): Boolean = withContext(Dispatchers.IO) {
    if (!_isReady.value) return@withContext false
    val vend = framework?.vend ?: return@withContext false

    // CRITICAL: roundToInt(), not toInt() — see §15 Pitfalls
    pendingAmountCents = (amount * 100).roundToInt()
    pendingItemNumber = itemNumber

    delay(200)  // Let state machine settle

    val vendSession = vmc_vend_t.vend_session_t(
        itemNumber, 1,
        vmc_vend_t.vend_item_t.UNIT_DONT_CARE.toByte(),
        pendingAmountCents
    )
    pendingVendSession = vendSession
    vend.vend_request(vendSession)

    _paymentState.value = PaymentState.WAITING_FOR_CARD

    suspendCancellableCoroutine<Boolean> { continuation ->
        paymentContinuation = continuation
        scope.launch {
            delay(120_000)  // 2 minute timeout
            paymentContinuation?.let {
                _paymentState.value = PaymentState.CANCELLED
                _paymentResult.value = PaymentResult(false, error = "Payment timeout")
                it.resume(false) {}
                paymentContinuation = null
            }
        }
    }
}
```

### Handle user cancellation (Marshall certification requirement)

```kotlin
override fun onReady(previousSession: vmc_vend_t.vend_session_t?) {
    // If a payment is pending and the session ended, the user pressed Cancel
    scope.launch {
        if (paymentContinuation != null) {
            Log.w(TAG, "Session ended while payment pending — user cancelled or timeout")
            _paymentState.value = PaymentState.CANCELLED
            _paymentResult.value = PaymentResult(false, error = "Payment cancelled")
            paymentContinuation?.resume(false) {}
            paymentContinuation = null
        }
        _paymentState.value = PaymentState.READY
    }
}
```

Without this, the app hangs for 120 seconds (timeout) when the user presses Cancel on the VPOS.

### Track settlement failures (audit compliance)

```kotlin
override fun onSettlement(success: Boolean) {
    if (!success) {
        Log.e(TAG, "Settlement FAILED — payment may not have been captured!")
        scope.launch {
            val current = _paymentResult.value
            if (current != null && current.success) {
                _paymentResult.value = current.copy(
                    error = "Settlement failed - payment may not have been captured"
                )
            }
        }
    }
}
```

A successful `onVendApproved` followed by failed `onSettlement` means the product dispensed but Nayax never collected — you need to flag this to your back office.

### Confirming the vend

```kotlin
suspend fun confirmVend(success: Boolean) = withContext(Dispatchers.IO) {
    val vend = framework?.vend ?: return@withContext
    val session = pendingVendSession ?: return@withContext
    session.session_status = if (success)
        vmc_vend_t.session_status_ok_e
    else
        vmc_vend_t.session_status_fail_to_dispense_e
    vend.session_close(session)
    pendingVendSession = null
}
```

Always call `confirmVend(true)` after a successful dispense and `confirmVend(false)` if the motor jammed — `false` triggers an automatic refund through Nayax's cloud.

### End-to-end happy-path sequence

```
1. User selects product in your app
2. (optional) ID/age verification
3. paymentManager.initiatePayment(3.50)
       → vend_request(350, 1) → VPOS displays "$3.50 — Please Tap Card"
4. Customer taps card
       → onSessionBegin(funds_avail)
       → onVendApproved(session)  ← coroutine resumes with true
5. Your code dispenses the product (motor / robot / whatever)
6. paymentManager.confirmVend(success = true)
       → session_close() → VPOS shows "Thank You"
7. onSettlement(true) → money captured
8. Reader re-arms automatically; ready for next sale
```

### Failure / refund path

```
4'. Customer taps a declined card
       → onVendDenied(session)  ← coroutine resumes with false
       → log denial reason from session.data.vmc_auth_status

OR

5'. Motor jams
6'. paymentManager.confirmVend(success = false)
       → session_close(session_status_fail_to_dispense_e)
       → Nayax cloud auto-refunds the customer
```

---

<a id="cloud-config"></a>
## 15. Nayax Core cloud configuration (mandatory)

The device-side `always_idle = true` flag is ignored unless the matching feature is enabled on the Nayax cloud side.

1. Log in to Nayax Core: <https://core.nayax.com>
2. **Operations → Machines → [your VPOS Touch]**
3. **Settings:**
   - Machine Model: `Marshall - Generic`
   - VMC Protocol: `RS232 - PC Machine`
   - Keep Alive Interval: `1 second`
   - Session Timeout: `120 seconds`
   - **Pre-Selection: ✓ Enabled** ← CRITICAL
   - **Multi-Session: ✓ Enabled** ← if you want shopping cart
4. **Settings → MDB Configuration → MDB Level 3 Optional Features:**
   - **"Always Idle" must be ENABLED** ← without this Pre-Selection won't work
5. Save and sync the device.

After saving, the device may take a few minutes to pull the new config. Power-cycle the VPOS to force an immediate refresh.

---

<a id="verification"></a>
## 16. Verification commands

### Is the device enumerated correctly?

```bash
adb shell lsusb
# Look for: Bus 005 Device 005: ID 0403:6015  (the FTDI Chipi-X)
```

### Is the permission persisted?

```bash
adb shell cat /data/system/users/0/usb_device_manager.xml
# Should contain:
# <usb-device vendor-id="1027" product-id="24597" ... />
```

### Is the SDK connected and online?

```bash
adb logcat -v time | grep -iE "Nayax|vmc_link|vmc_vend_t|handleMessage|Pre-Selection"
```

Healthy output looks like:

```
NayaxPaymentManager:  Marshall SDK connected to Nayax VPOS Touch
vmc_link:             vmc is online. time: <timestamp>
vmc_vend_t:           handleMessage: event=0, state=0   (init_done)
vmc_vend_t:           handleMessage: event=1, state=1   (reader_enable)
vmc_vend_t:           Reader Enable
NayaxPaymentManager:  Ready to accept payments!
```

### Did Pre-Selection work?

```bash
adb logcat -v time | grep "event=4, state=2"
# Should appear when you call initiatePayment().
adb logcat -v time | grep "Pre-Selection mode: Card tapped"
# Should appear when the customer taps the card.
```

### Did the auto-grant script run?

```bash
adb logcat -d | grep USB_AUTO_GRANT
# Should show "Auto-granted USB permission #1" etc.
```

### Quick smoke-test sequence

```bash
adb reboot
# Wait 60 seconds
adb shell ps -A | grep myapplication              # app running?
adb logcat -d | grep "HardwareService started"    # service started?
adb logcat -d | grep "Marshall SDK connected"     # SDK connected?
```

---

<a id="troubleshooting"></a>
## 17. Troubleshooting matrix

| Symptom | Likely cause | Where to look |
|---------|-------------|---------------|
| VPOS shows "CASH ONLY" on first boot | `reader_always_on = false` or wrong USB interface | [§3](#1-gotcha), [§5](#sdk-config) |
| VPOS shows "CASH ONLY" only after app restart | `vmc_framework` singleton not reset | [§9](#singleton-reset) |
| `wrong crc on packet … crc_calc: ffffXXXX` | CRC sign-extension | [§7](#crc-bug) |
| CMD=13 every 17 seconds, link drops | Duplicate packet processing | [§8](#duplicate-packets) |
| `UnsupportedOperationException` on every event | `vmc_vend_t.handleMessage` not reconstructed | [§6](#state-machine) |
| `vend_request` accepted but `session_begin` ignored | Pre-Selection state transition wrong | [§6](#state-machine) |
| Payment approved but motors don't run | `vend_approved` callback only checks state 4 | [§6](#state-machine) |
| Auth Status -1 / payment always declined | `multi_vend_support = true` | [§5](#sdk-config) |
| "USB permission denied" on every boot | `HardwareService` starts before USB perm DB loads | [§10](#boot-timing) |
| First device gets permission, second never sees dialog | No permission queue | [§11](#permission-queue) |
| App hangs 120s when user taps Cancel on VPOS | `onReady(previousSession)` not handling cancel | [§14](#payment-flow) |
| Charged customer wrong amount ($1.07 vs $1.08) | `(amount * 100).toInt()` truncates — use `roundToInt()` | [§18](#pitfalls) |
| `vend.is_ready` always false during payment | Don't check `vend.is_ready` — it's only true when idle | [§5](#sdk-config) |
| FATAL EXCEPTION on system app launch after install | Package manager cache stale | `adb reboot` |

### When all else fails, capture a full log

```bash
adb logcat -c                        # clear
adb logcat -v time > nayax_debug.log # capture
# Reproduce the issue, then Ctrl+C
```

Look for the first error or warning before things go wrong.

---

<a id="file-reference"></a>
## 18. File-by-file change reference

| File | Purpose | Key changes |
|------|---------|-------------|
| `res/xml/device_filter.xml` | USB device manifest filter | Added FTDI VID/PID `1027 / 24597` |
| `hardware/HardwareService.kt` | Foreground service managing USB hardware | FTDI-first device discovery, driver selection by VID, permission queue, status flow |
| `hardware/NayaxPaymentManager.kt` | Wraps Marshall SDK | Config (`always_idle=true`, `reader_always_on=true`, `multi_vend_support=false`), `vmc_framework.reset()` on init/close, `roundToInt()` for cents, cancellation handling, settlement tracking |
| `hardware/HardwareStatus.kt` | Sealed class | NEW — drives loading screen states |
| `hardware/AndroidUsbPort.java` | `serial_port_i` impl | Bridges async USB reads → 16KB CircularBuffer for SDK pull-based reads |
| `bitmick/marshall/UsbSerialBridge.java` | Read loop | `continue` after `dataCallback.onDataReceived()` to skip duplicate `processPackets()` |
| `bitmick/marshall/vmc/vmc_vend_t.java` | State machine | RECONSTRUCTED `handleMessage()` + Pre-Selection patches in events 2, 4, 5, 6 |
| `bitmick/marshall/vmc/vmc_framework.java` | SDK singleton | Added `static reset()` |
| `bitmick/marshall/vmc/marshall_t.java` | Protocol framing | CRC `& 0xFFFF` mask |
| `digitalmediavending/.../FtdiSerialDriver.java` | FTDI driver | Strips 2-byte status header from every read; sets FTDI baud divisor |
| `MainActivity.kt` | App entry | 10-second `postDelayed` before starting `HardwareService`; service binding; loading overlay observer |
| `res/layout/activity_main.xml` | Main UI | Full-screen `loadingOverlay` FrameLayout (elevation 1000dp) |
| `auto_usb_grant.sh` | Boot script | Multi-device support — handles up to 5 sequential dialogs |
| `auto_usb_grant.rc` | init.rc service | Triggered by `dev.bootcomplete=1` |

---

<a id="pitfalls"></a>
## 19. Common pitfalls / things not to do

1. **Don't use `(amount * 100).toInt()`** — it truncates. `1.075 * 100 = 107.5 → 107` cents. Customer sees `$1.08` but gets charged `$1.07`. **Use `(amount * 100).roundToInt()`**.

2. **Don't toggle DTR/RTS** on the serial port. The FTDI driver handles flow control internally; explicit DTR/RTS toggles cause spurious resets.

3. **Don't call `session_start(0)` manually** in `onReady()`. The VPOS treats it as an invalid payment request and stops responding to keep-alives.

4. **Don't check `vend.is_ready`** before initiating payment. That flag is only `true` when the SDK is idle — it's `false` during active sessions, which is the wrong condition.

5. **Don't redirect stderr from native commands inside PowerShell** (Windows dev only). PowerShell wraps stderr in `ErrorRecord` and breaks tools like `gradlew`.

6. **Don't `adb install` over a system app**. After deploying a new APK to `/system/priv-app/`, you MUST `adb reboot` for the package manager cache to refresh. Otherwise you'll see `NullPointerException ... Resources.getConfiguration()` on launch.

7. **Don't use `--amend` to fix a failed pre-commit hook**. The commit didn't happen — `--amend` modifies the previous commit and can lose work.

8. **Don't enable `multi_vend_support`** unless you've explicitly arranged Extended MDB support with your Nayax representative. Default deployments return Auth Status -1 for multi-vend.

9. **Don't skip the Nayax Core cloud config**. The device-side `always_idle=true` flag is silently ignored if "Always Idle" isn't enabled in the cloud MDB Configuration.

10. **Don't forget the `device_filter.xml` entry**. Without it, Android won't show your app in the "Open with…" dialog when the USB device is plugged in, and `requestPermission()` may behave inconsistently.

---

## Appendix: Marshall protocol cheat sheet

### Packet format

```
[length_low] [length_high] [...payload (7-510 bytes)...] [crc_low] [crc_high]
```

- 2-byte length prefix, little-endian
- Payload
- 2-byte CRC-CCITT, little-endian, masked with `0xFFFF` ([§7](#crc-bug))

### Key MDB commands

| CMD | Direction | Meaning |
|-----|-----------|---------|
| Just Reset | VPOS → VMC | VPOS startup |
| FW_INFO | VMC → VPOS | VMC identification |
| Config | VPOS → VMC | VPOS config response |
| Status | both | Heartbeat / keep-alive (every 1s) |
| Reader Enable | VMC → VPOS | Arm card reader |
| Vend Request | VMC → VPOS | Request charge (Pre-Selection sends price first) |
| Begin Session | VPOS → VMC | Card tapped, funds available |
| Vend Approved | VPOS → VMC | Payment approved |
| Vend Denied | VPOS → VMC | Payment declined |

### Vend session statuses

| Constant | Meaning |
|----------|---------|
| `session_status_ok_e` | Vend succeeded; capture payment |
| `session_status_fail_to_dispense_e` | Vend failed; trigger refund |
| `session_status_vend_denied_e` | Payment declined (set in `onVendDenied`) |

---

## Appendix: end-to-end checklist

When deploying to a new kiosk for the first time, in order:

- [ ] VPOS Touch wired with Marshall cable + USB-to-serial + 24V dedicated PSU
- [ ] `device_filter.xml` lists FTDI VID/PID `1027 / 24597`
- [ ] `vmc_vend_t.handleMessage()` reconstructed with Pre-Selection patches
- [ ] `marshall_t.java` CRC masked with `& 0xFFFF`
- [ ] `UsbSerialBridge.java` has `continue` after `dataCallback.onDataReceived()`
- [ ] `vmc_framework.java` has `static reset()` method
- [ ] `NayaxPaymentManager.initialize()` calls `vmc_framework.reset()` first
- [ ] `NayaxPaymentManager` config: `reader_always_on = true`, `always_idle = true`, `multi_vend_support = false`
- [ ] `NayaxPaymentManager.initiatePayment()` uses `roundToInt()` for cents
- [ ] `MainActivity` delays `HardwareService` start by 10 seconds
- [ ] Loading overlay implemented and bound to `NayaxPaymentManager.isReady`
- [ ] Permission queue in `HardwareService` requests Nayax first, ID scanner second
- [ ] Nayax Core: "Always Idle" enabled in MDB Configuration
- [ ] Nayax Core: Pre-Selection enabled, machine model = "Marshall - Generic"
- [ ] (Kiosk) `auto_usb_grant.sh` deployed to `/system/bin/` with correct tap coords
- [ ] (Kiosk) `auto_usb_grant.rc` deployed to `/system/etc/init/`
- [ ] First boot: confirm `Marshall SDK connected to Nayax VPOS Touch` in logcat
- [ ] First boot: confirm VPOS displays "Tap Card", not "Cash Only"
- [ ] Test card transaction: confirm price displayed, card tap, approval, dispense, settlement
- [ ] Test cancel: press Cancel on VPOS during payment → app unblocks within 1-2 seconds
- [ ] Test app restart (don't force-stop): VPOS still displays "Tap Card" after relaunch
- [ ] Reboot device: full sequence completes within 30 seconds, no manual intervention

---

**Last verified against:** ZootBox production codebase, branch `001-inventory-portal`, 2026-01-16.

**Status:** Production. Processing live transactions end-to-end with no known critical issues.

package com.example.myapplication.hardware

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

data class IdScanResult(
    val success: Boolean,
    val dateOfBirth: Date? = null,
    val expirationDate: Date? = null,
    val rawData: String? = null,
    val error: String? = null
)

class IdScannerManager(
    private val usbManager: UsbManager,
    private val usbDevice: UsbDevice,
    private val scope: CoroutineScope
) {
    companion object {
        private const val TAG = "IdScannerM260"
        // How long with no new bytes before we treat the buffer as a complete record
        private const val IDLE_TIMEOUT_MS = 500L
    }

    private var serialPort: UsbSerialPort? = null
    private val _scanResult = MutableStateFlow<IdScanResult?>(null)
    val scanResult: StateFlow<IdScanResult?> = _scanResult

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    private val dataBuffer = StringBuilder()
    private var lastByteTime = 0L

    fun initialize() {
        scope.launch {
            try {
                if (!usbManager.hasPermission(usbDevice)) {
                    Log.w(TAG, "No USB permission")
                    _isReady.value = false
                    return@launch
                }

                val driver: UsbSerialDriver? = UsbSerialProber.getDefaultProber().probeDevice(usbDevice)
                Log.d(TAG, "Driver detected: ${driver?.javaClass?.simpleName}")

                if (driver == null) {
                    Log.e(TAG, "No driver found for device VID=${usbDevice.vendorId} PID=${usbDevice.productId}")
                    _isReady.value = false
                    return@launch
                }

                // Accept any driver — don't restrict to FtdiSerialDriver
                serialPort = driver.ports[0]
                serialPort?.open(usbManager.openDevice(usbDevice))
                serialPort?.setParameters(
                    HardwareService.ID_SCANNER_BAUD_RATE,
                    8,
                    UsbSerialPort.STOPBITS_1,
                    UsbSerialPort.PARITY_NONE
                )

                Log.i(TAG, "Port open at ${HardwareService.ID_SCANNER_BAUD_RATE} baud 8N1 — ready to scan")
                _isReady.value = true
                startReading()
            } catch (e: Exception) {
                Log.e(TAG, "initialize() failed", e)
                _isReady.value = false
                _scanResult.value = IdScanResult(false, error = e.message)
            }
        }
    }

    private fun startReading() {
        scope.launch {
            // 4 KB buffer — a full AAMVA record is typically 500–2000 bytes
            val readBuf = ByteArray(4096)
            while (isActive && _isReady.value) {
                try {
                    val port = serialPort ?: break
                    // Short read timeout so we can detect end-of-transmission gaps
                    val bytesRead = port.read(readBuf, 100)
                    if (bytesRead > 0) {
                        // ISO-8859-1 preserves every byte exactly — AAMVA is ASCII
                        // but the scanner may send framing bytes outside the UTF-8 range
                        val chunk = String(readBuf, 0, bytesRead, Charsets.ISO_8859_1)
                        dataBuffer.append(chunk)
                        lastByteTime = System.currentTimeMillis()

                        val hex = readBuf.take(bytesRead).joinToString(" ") { "%02X".format(it) }
                        Log.d(TAG, "RX $bytesRead bytes: $hex")
                    } else if (dataBuffer.isNotEmpty() &&
                        System.currentTimeMillis() - lastByteTime >= IDLE_TIMEOUT_MS
                    ) {
                        // No new bytes for IDLE_TIMEOUT_MS — process complete record
                        val completeData = dataBuffer.toString()
                        dataBuffer.clear()
                        Log.d(TAG, "Idle timeout — processing ${completeData.length} chars")
                        processScanData(completeData)
                    }
                } catch (e: IOException) {
                    if (isActive) delay(100)
                }
            }
        }
    }

    private fun processScanData(data: String) {
        scope.launch(Dispatchers.Default) {
            try {
                // Full hex + text dump — run `adb logcat -s IdScannerM260` to read this
                val hex = data.toByteArray(Charsets.ISO_8859_1).joinToString(" ") { "%02X".format(it) }
                Log.d(TAG, "Buffer hex:\n$hex")
                Log.d(TAG, "Buffer text:\n$data")

                // Strip common serial framing bytes: NUL (0x00), STX (0x02), ETX (0x03)
                val cleaned = buildString {
                    for (ch in data) {
                        val code = ch.code
                        if (code != 0x00 && code != 0x02 && code != 0x03) append(ch)
                    }
                }.trim()

                // AAMVA records always contain '@' (file separator) and 'ANSI'
                val aamvaStart = cleaned.indexOf('@')
                val hasAnsi = cleaned.contains("ANSI")
                Log.d(TAG, "@ at index=$aamvaStart  ANSI present=$hasAnsi")

                if (aamvaStart < 0 || !hasAnsi) {
                    Log.w(TAG, "Not valid AAMVA — missing @ or ANSI header")
                    _scanResult.value = IdScanResult(false, error = "Invalid ID format")
                    return@launch
                }

                val record = cleaned.substring(aamvaStart)
                val lines = record.split('\n', '\r')
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                Log.d(TAG, "${lines.size} AAMVA lines:")
                lines.forEachIndexed { i, line -> Log.d(TAG, "  [$i] $line") }

                val dob = extractDateOfBirth(lines)
                val expiration = extractExpirationDate(lines)

                if (dob != null) {
                    Log.i(TAG, "Scan success — DOB=$dob")
                    _scanResult.value = IdScanResult(
                        success = true,
                        dateOfBirth = dob,
                        expirationDate = expiration,
                        rawData = data
                    )
                } else {
                    val presentFields = lines.filter { it.length >= 3 }.map { it.take(3) }
                    Log.w(TAG, "DBB not found. Fields present: $presentFields")
                    _scanResult.value = IdScanResult(false, error = "Could not parse date of birth")
                }
            } catch (e: Exception) {
                Log.e(TAG, "processScanData error", e)
                _scanResult.value = IdScanResult(false, error = e.message)
            }
        }
    }

    private fun extractDateOfBirth(lines: List<String>): Date? {
        // AAMVA field DBB = Date of Birth (MMDDYYYY or YYYYMMDD depending on state/version)
        val dobLine = lines.find { it.startsWith("DBB") } ?: run {
            Log.w(TAG, "No DBB line in AAMVA data")
            return null
        }
        val raw = dobLine.removePrefix("DBB").trim().take(8)
        Log.d(TAG, "DBB raw value: '$raw'")
        return parseDate(raw, "MMddyyyy") ?: parseDate(raw, "yyyyMMdd")
    }

    private fun extractExpirationDate(lines: List<String>): Date? {
        // AAMVA field DBA = Document Expiration Date
        val expLine = lines.find { it.startsWith("DBA") } ?: return null
        val raw = expLine.removePrefix("DBA").trim().take(8)
        return parseDate(raw, "MMddyyyy") ?: parseDate(raw, "yyyyMMdd")
    }

    private fun parseDate(dateStr: String, pattern: String): Date? {
        if (dateStr.length < 8) return null
        return try {
            SimpleDateFormat(pattern, Locale.US).apply { isLenient = false }.parse(dateStr)
        } catch (e: Exception) {
            null
        }
    }

    fun isAgeVerified(requiredAge: Int = 21): Boolean {
        val result = _scanResult.value
        if (result?.success != true || result.dateOfBirth == null) return false

        val today = Calendar.getInstance()
        val dob = Calendar.getInstance().apply { time = result.dateOfBirth }
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        val monthDiff = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH)
        if (monthDiff < 0 || (monthDiff == 0 &&
                today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))
        ) {
            age--
        }

        return age >= requiredAge
    }

    fun clearScanResult() {
        _scanResult.value = null
        dataBuffer.clear()
    }

    fun close() {
        scope.launch {
            try {
                serialPort?.close()
            } catch (e: Exception) {
                // Ignore
            }
            serialPort = null
            _isReady.value = false
        }
    }
}

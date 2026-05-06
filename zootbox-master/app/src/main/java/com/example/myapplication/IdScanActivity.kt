package com.example.myapplication

import android.animation.ObjectAnimator
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.hardware.HardwareService
import com.example.myapplication.hardware.IdScanResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

class IdScanActivity : KioskActivity() {
    private var hardwareService: HardwareService? = null
    private var isBound = false

    private lateinit var layoutIdle: ConstraintLayout
    private lateinit var layoutScanning: ConstraintLayout
    private lateinit var layoutSuccess: ConstraintLayout

    private lateinit var scanningLine: View

    private var scanningLineAnimator: ObjectAnimator? = null

    private enum class ScanState {
        IDLE, SCANNING, SUCCESS, DENIED
    }

    private var currentState = ScanState.IDLE

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as HardwareService.LocalBinder
            hardwareService = binder.getService()
            isBound = true
            setupHardwareScanning()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            hardwareService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_scan)

        initializeViews()
        setScanningState(ScanState.IDLE)

        val intent = Intent(this, HardwareService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun initializeViews() {
        layoutIdle = findViewById(R.id.layout_idle)
        layoutScanning = findViewById(R.id.layout_scanning)
        layoutSuccess = findViewById(R.id.layout_success)
        scanningLine = findViewById(R.id.scanning_line)

        findViewById<ImageButton>(R.id.btn_back).setOnClickListener { finish() }
    }

    private fun setScanningState(state: ScanState) {
        currentState = state
        layoutIdle.isVisible = state == ScanState.IDLE
        layoutScanning.isVisible = state == ScanState.SCANNING
        layoutSuccess.isVisible = state == ScanState.SUCCESS

        when (state) {
            ScanState.IDLE -> stopScanningAnimations()
            ScanState.SCANNING -> startScanningAnimations()
            ScanState.SUCCESS -> {
                stopScanningAnimations()
                lifecycleScope.launch {
                    kotlinx.coroutines.delay(3000)
                    finishWithSuccess()
                }
            }
            ScanState.DENIED -> {
                // No customer-facing denied UI — silently reset to scanning
                stopScanningAnimations()
                lifecycleScope.launch {
                    hardwareService?.getIdScannerManager()?.clearScanResult()
                    setScanningState(ScanState.SCANNING)
                }
            }
        }
    }

    private fun startScanningAnimations() {
        scanningLine.post {
            val height = (scanningLine.parent as View).height.toFloat()
            scanningLineAnimator = ObjectAnimator.ofFloat(scanningLine, "translationY", 0f, height).apply {
                duration = 2000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.RESTART
                interpolator = LinearInterpolator()
                start()
            }
        }
    }

    private fun stopScanningAnimations() {
        scanningLineAnimator?.cancel()
        scanningLineAnimator = null
    }

    private fun setupHardwareScanning() {
        val service = hardwareService ?: return

        lifecycleScope.launch {
            service.idScannerManagerFlow
                .filterNotNull()
                .collectLatest { manager ->
                    manager.clearScanResult()
                    setScanningState(ScanState.SCANNING)
                    manager.scanResult.collect { result ->
                        result?.let { handleScanResult(it) }
                    }
                }
        }
    }

    private fun handleScanResult(result: IdScanResult) {
        if (currentState != ScanState.SCANNING) return

        if (result.success) {
            val requiredAge = intent.getIntExtra("requiredAge", 21)
            val scannerManager = hardwareService?.getIdScannerManager()
            if (scannerManager?.isAgeVerified(requiredAge) == true) {
                setScanningState(ScanState.SUCCESS)
            } else {
                setScanningState(ScanState.DENIED)
            }
        } else {
            Toast.makeText(this, "Scan failed: ${result.error ?: "Unknown error"}. Please try again.", Toast.LENGTH_SHORT).show()
            hardwareService?.getIdScannerManager()?.clearScanResult()
        }
    }

    private fun finishWithSuccess() {
        setResult(RESULT_OK)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopScanningAnimations()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}

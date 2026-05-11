package com.example.myapplication.hardware

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HardwareService : Service() {
    private val binder = LocalBinder()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private lateinit var usbManager: UsbManager
    private var idScannerManager: IdScannerManager? = null
    private var nayaxPaymentManager: NayaxPaymentManager? = null

    private val _idScannerManagerFlow = MutableStateFlow<IdScannerManager?>(null)
    val idScannerManagerFlow: StateFlow<IdScannerManager?> = _idScannerManagerFlow

    private val _nayaxPaymentManagerFlow = MutableStateFlow<NayaxPaymentManager?>(null)
    val nayaxPaymentManagerFlow: StateFlow<NayaxPaymentManager?> = _nayaxPaymentManagerFlow

    companion object {
        private const val CHANNEL_ID = "HardwareServiceChannel"
        private const val NOTIFICATION_ID = 1
        private const val ACTION_USB_PERMISSION = "com.example.myapplication.USB_PERMISSION"

        // USB Vendor/Product IDs from hardware analysis
        const val ID_SCANNER_VID = 0x0403
        const val ID_SCANNER_PID = 0x6001
        const val NAYAX_VID = 0x26f1
        const val NAYAX_PID = 0x5650
        const val NAYAX_TTY_ACM = "/dev/ttyACM0"
        const val SERIAL_BAUD_RATE = 9600
    }

    private val usbPermissionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != ACTION_USB_PERMISSION) return
            val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
            val granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
            if (!granted || device == null) return

            if (device.vendorId == ID_SCANNER_VID && device.productId == ID_SCANNER_PID) {
                idScannerManager = IdScannerManager(usbManager, device, serviceScope)
                _idScannerManagerFlow.value = idScannerManager
                idScannerManager?.initialize()
            }

            if (device.vendorId == NAYAX_VID && device.productId == NAYAX_PID) {
                nayaxPaymentManager = NayaxPaymentManager(usbManager, device, serviceScope)
                _nayaxPaymentManagerFlow.value = nayaxPaymentManager
                nayaxPaymentManager?.initialize()
            }
        }
    }

    private val usbDeviceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
            when (intent.action) {
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    if (device != null &&
                        ((device.vendorId == ID_SCANNER_VID && device.productId == ID_SCANNER_PID) ||
                         (device.vendorId == NAYAX_VID      && device.productId == NAYAX_PID))) {
                        initializeHardware()
                    }
                }
                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    if (device?.vendorId == ID_SCANNER_VID && device?.productId == ID_SCANNER_PID) {
                        idScannerManager?.close()
                        idScannerManager = null
                        _idScannerManagerFlow.value = null
                    }
                    if (device?.vendorId == NAYAX_VID && device?.productId == NAYAX_PID) {
                        nayaxPaymentManager?.close()
                        nayaxPaymentManager = null
                        _nayaxPaymentManagerFlow.value = null
                    }
                }
            }
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): HardwareService = this@HardwareService
    }
    
    override fun onCreate() {
        super.onCreate()
        usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        val permFilter = IntentFilter(ACTION_USB_PERMISSION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(usbPermissionReceiver, permFilter, RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            registerReceiver(usbPermissionReceiver, permFilter)
        }
        val deviceFilter = IntentFilter().apply {
            addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
            addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(usbDeviceReceiver, deviceFilter, RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            registerReceiver(usbDeviceReceiver, deviceFilter)
        }
        initializeHardware()
    }
    
    override fun onBind(intent: Intent?): IBinder = binder
    
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(usbPermissionReceiver)
        unregisterReceiver(usbDeviceReceiver)
        idScannerManager?.close()
        nayaxPaymentManager?.close()
        serviceScope.cancel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Hardware Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Manages ID scanner and payment reader"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Hardware Service")
            .setContentText("ID Scanner and Payment Reader Active")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .build()
    }
    
    private fun initializeHardware() {
        // ID Scanner
        val idScannerDevice = findUsbDevice(ID_SCANNER_VID, ID_SCANNER_PID)
        if (idScannerDevice != null) {
            if (usbManager.hasPermission(idScannerDevice)) {
                idScannerManager = IdScannerManager(usbManager, idScannerDevice, serviceScope)
                _idScannerManagerFlow.value = idScannerManager
                idScannerManager?.initialize()
            } else {
                val permissionIntent = PendingIntent.getBroadcast(
                    this, 0,
                    Intent(ACTION_USB_PERMISSION),
                    PendingIntent.FLAG_IMMUTABLE
                )
                usbManager.requestPermission(idScannerDevice, permissionIntent)
            }
        }

        // Nayax payment reader
        val nayaxDevice = findUsbDevice(NAYAX_VID, NAYAX_PID)
        if (nayaxDevice != null) {
            if (usbManager.hasPermission(nayaxDevice)) {
                nayaxPaymentManager = NayaxPaymentManager(usbManager, nayaxDevice, serviceScope)
                _nayaxPaymentManagerFlow.value = nayaxPaymentManager
                nayaxPaymentManager?.initialize()
            } else {
                val permissionIntent = PendingIntent.getBroadcast(
                    this, 1,
                    Intent(ACTION_USB_PERMISSION),
                    PendingIntent.FLAG_IMMUTABLE
                )
                usbManager.requestPermission(nayaxDevice, permissionIntent)
            }
        }
    }
    
    private fun findUsbDevice(vid: Int, pid: Int): UsbDevice? {
        return usbManager.deviceList.values.find { device ->
            device.vendorId == vid && device.productId == pid
        }
    }
    
    fun getIdScannerManager(): IdScannerManager? = idScannerManager
    fun getNayaxPaymentManager(): NayaxPaymentManager? = nayaxPaymentManager
}


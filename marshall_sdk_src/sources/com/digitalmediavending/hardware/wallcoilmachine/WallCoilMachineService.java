package com.digitalmediavending.hardware.wallcoilmachine;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.bitmick.marshall.models.vmc_configuration;
import com.bitmick.marshall.vmc.vmc_framework;
import com.bitmick.marshall.vmc.vmc_vend_t;
import com.blankj.utilcode.util.ShellUtils;
import com.digitalmediavending.hardware.MainApp;
import com.digitalmediavending.hardware.R;
import com.digitalmediavending.hardware.SocketSendData;
import com.digitalmediavending.hardware.idScan.IdScanService;
import com.digitalmediavending.hardware.nayax_sdk_utils.lowlevel_serial_ftdi;
import com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialDriver;
import com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort;
import com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialProber;
import com.digitalmediavending.hardware.qrCodeScan.QRCodeScannerService;
import com.digitalmediavending.hardware.utils.AppConstants;
import com.digitalmediavending.hardware.utils.LoggerHelp;
import com.digitalmediavending.hardware.wallcoilmachine.WallCoilMachineService;
import com.digitalmediavending.wallcoilmachine.inner.WallCoilMachine;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WallCoilMachineService extends Service {
    private static final String TAG = "VMHardwareService";
    public static boolean isNayaxConnected = false;
    public static vmc_vend_t.vend_session_t m_session = null;
    public static vmc_framework m_vmc = null;
    public static ArrayList<BigDecimal> price = null;
    public static int quantity = 0;
    public static ArrayList<BigDecimal> settlementAmount = null;
    public static boolean shouldSessionTimeout = true;
    public static boolean shouldSettle = false;
    public static long transactionId = 0;
    public static boolean vendingComplete = false;
    public static vmc_configuration vmc_config;
    public DataReceiver dataReceiver;
    public int nayaxPortNumber = 0;
    private final BroadcastReceiver usbDeviceReceiver = new BroadcastReceiver() { // from class: com.digitalmediavending.hardware.wallcoilmachine.WallCoilMachineService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            UsbDevice usbDevice = intent.hasExtra("device") ? (UsbDevice) intent.getParcelableExtra("device") : null;
            if (usbDevice != null) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                    Logger.e("DEVICE_ATTACHED => " + usbDevice.getProductId(), new Object[0]);
                    WallCoilMachineService.this.forceCloseSelf();
                    return;
                }
                if (action.equals("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
                    Logger.e("DEVICE_DETACHED => " + usbDevice.getProductId(), new Object[0]);
                }
            }
        }
    };
    public WallCoilMachine vendingMachine;
    public WallCoilMachineEventHandler vmEventHandlerService;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        Logger.addLogAdapter(new DiskLogAdapter(CsvFormatStrategy.newBuilder().tag(getPackageName()).build()));
        Logger.e("Logger was initialized here", new Object[0]);
        try {
            Logger.e("The start command started", new Object[0]);
            this.vendingMachine = new WallCoilMachine(this);
            WallCoilMachineEventHandler wallCoilMachineEventHandler = new WallCoilMachineEventHandler();
            this.vmEventHandlerService = wallCoilMachineEventHandler;
            this.vendingMachine.start("/dev/ttyS0", wallCoilMachineEventHandler);
        } catch (Exception e) {
            Logger.e("vendingMachineStartException : " + e.getLocalizedMessage(), new Object[0]);
        }
        this.dataReceiver = new DataReceiver();
        registerReceiver();
        iterateAllPorts(this);
        Logger.e("Socket was initialized from the service", new Object[0]);
        SocketSendData.init();
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e("Start command was called", new Object[0]);
        price = new ArrayList<>();
        settlementAmount = new ArrayList<>();
        startForegroundService();
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.dataReceiver);
        unregisterReceiver(this.usbDeviceReceiver);
        SocketSendData.close();
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    public void registerReceiver() {
        registerReceiver(this.dataReceiver, new IntentFilter("sendBroadcast"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        registerReceiver(this.usbDeviceReceiver, intentFilter);
    }

    public void iterateAllPorts(final Context context) {
        isNayaxConnected = false;
        Logger.e("Trying to connect to port : " + this.nayaxPortNumber, new Object[0]);
        findNayaxPort(getPortFromDriver(context, this.nayaxPortNumber));
        new Handler().postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.wallcoilmachine.-$$Lambda$WallCoilMachineService$wudwJmPs8CUN-RHCWf_l__P_7r0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$iterateAllPorts$0$WallCoilMachineService(context);
            }
        }, 5000L);
    }

    public /* synthetic */ void lambda$iterateAllPorts$0$WallCoilMachineService(Context context) {
        if (!isNayaxConnected) {
            this.nayaxPortNumber++;
            iterateAllPorts(context);
            return;
        }
        Toast.makeText(context, "Device initiated at port" + this.nayaxPortNumber, 0).show();
        Logger.e("Connected to the port at : " + this.nayaxPortNumber, new Object[0]);
    }

    public UsbSerialPort getPortFromDriver(Context context, int portNumber) {
        UsbManager usbManager = (UsbManager) context.getSystemService("usb");
        List<UsbSerialDriver> listFindAllDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
        Logger.e("getPortFromDriver() => BM => Driver_Size => " + listFindAllDrivers.size() + ", Nayax_Port => " + this.nayaxPortNumber + ", Local_Port => " + portNumber, new Object[0]);
        if (this.nayaxPortNumber >= listFindAllDrivers.size()) {
            this.nayaxPortNumber = 0;
            portNumber = 0;
        }
        Logger.e("getPortFromDriver() => AM => Driver_Size => " + listFindAllDrivers.size() + ", Nayax_Port => " + this.nayaxPortNumber + ", Local_Port => " + portNumber, new Object[0]);
        if (listFindAllDrivers.isEmpty()) {
            Logger.e("Available drivers returned null", new Object[0]);
            return null;
        }
        UsbSerialDriver usbSerialDriver = listFindAllDrivers.get(portNumber);
        if (usbSerialDriver != null) {
            try {
                UsbDeviceConnection usbDeviceConnectionOpenDevice = usbManager.openDevice(usbSerialDriver.getDevice());
                UsbSerialPort usbSerialPort = usbSerialDriver.getPorts().get(0);
                usbSerialPort.open(usbDeviceConnectionOpenDevice);
                Logger.e("USB connection opened", new Object[0]);
                return usbSerialPort;
            } catch (IOException e) {
                Logger.e("ConnectionException => " + e.getMessage() + ", Local_Port => " + portNumber + ", Nayax_Port => " + this.nayaxPortNumber, new Object[0]);
                return null;
            }
        }
        Logger.e("Driver returned null for port number " + portNumber, new Object[0]);
        return null;
    }

    public void findNayaxPort(UsbSerialPort port) {
        if (port != null) {
            Logger.e("The findNayaxPort was started", new Object[0]);
            vmc_configuration vmc_configurationVar = new vmc_configuration();
            vmc_config = vmc_configurationVar;
            vmc_configurationVar.port_vpos = port;
            vmc_config.port_vpos_baud = 115200;
            vmc_config.model = "android-marshall-demo";
            vmc_config.serial = "1434324619381374";
            vmc_config.sw_ver = "1.0.0.0";
            vmc_config.mifare_approved_by_vmc_support = false;
            vmc_config.mag_card_approved_by_vmc_support = false;
            vmc_config.multi_vend_support = true;
            vmc_config.multi_session_support = false;
            vmc_config.price_not_final_support = false;
            vmc_config.reader_always_on = false;
            vmc_config.always_idle = false;
            vmc_config.vend_denied_policy = 0;
            vmc_config.dump_packets_level = 2;
            vmc_config.debug = true;
            vmc_framework vmc_frameworkVar = vmc_framework.getInstance();
            m_vmc = vmc_frameworkVar;
            vmc_frameworkVar.link.set_lowlevel(new lowlevel_serial_ftdi());
            m_vmc.link.configure(vmc_config);
            m_vmc.vend.register_callbacks(new vmc_vend_events());
            m_vmc.socket.register_callbacks(null);
            try {
                m_vmc.link.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void forceCloseSelf() {
        Logger.e("foreStopSelf() => cmd => am force-stop " + getPackageName(), new Object[0]);
        ShellUtils.CommandResult commandResultExecCmd = ShellUtils.execCmd("am force-stop " + getPackageName(), true);
        if (commandResultExecCmd.result != 0) {
            Logger.e("forceCloseSelf() => " + commandResultExecCmd.errorMsg, new Object[0]);
            return;
        }
        Logger.e("forceCloseSelf() => " + commandResultExecCmd.successMsg, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DataReceiver extends BroadcastReceiver {
        String methodName;

        private DataReceiver() {
            this.methodName = "None";
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            final JSONObject jSONObject;
            final LoggerHelp loggerHelp = new LoggerHelp();
            if (intent.getAction().equals("sendBroadcast")) {
                try {
                    jSONObject = new JSONObject(intent.getStringExtra("data"));
                    switch (jSONObject.getString(FirebaseAnalytics.Param.METHOD)) {
                        case "requestInitNayax":
                            WallCoilMachineService.mockInitNayax();
                            WallCoilMachineService.shouldSettle = false;
                            WallCoilMachineService.vendingComplete = false;
                            return;
                        case "requestProductVend":
                            Logger.e("Product vend was requested", new Object[0]);
                            int i = Integer.parseInt(jSONObject.getString("col"));
                            int i2 = Integer.parseInt(jSONObject.getString("row"));
                            Logger.e("Vending was initiated", new Object[0]);
                            WallCoilMachineService.this.vendingMachine.triggerVending(i2 - 1, i - 1);
                            return;
                        case "requestSessionStart":
                            WallCoilMachineService.vendingComplete = false;
                            this.methodName = "requestSessionStart";
                            WallCoilMachineService.price = new ArrayList<>();
                            Logger.e("session was started by the card", new Object[0]);
                            JSONArray jSONArray = jSONObject.getJSONObject(FirebaseAnalytics.Param.PRICE).getJSONArray("values");
                            int length = jSONArray.length();
                            if (length > 0) {
                                for (int i3 = 0; i3 < length; i3++) {
                                    Logger.e("Price in string is " + jSONArray.get(i3), new Object[0]);
                                    WallCoilMachineService.price.add(new BigDecimal(jSONArray.get(i3).toString()));
                                }
                            }
                            WallCoilMachineService.m_vmc.vend.session_start(0);
                            WallCoilMachineService.quantity = Integer.parseInt(jSONObject.getString(FirebaseAnalytics.Param.QUANTITY));
                            WallCoilMachineService.this.sessionTimeoutCounterStart(System.currentTimeMillis());
                            return;
                        case "requestSettlement":
                            JSONArray jSONArray2 = jSONObject.getJSONObject("settlementAmount").getJSONArray("values");
                            WallCoilMachineService.settlementAmount = new ArrayList<>();
                            int length2 = jSONArray2.length();
                            if (length2 > 0) {
                                for (int i4 = 0; i4 < length2; i4++) {
                                    Logger.e("Price as string in settlement amount is " + jSONArray2.get(i4), new Object[0]);
                                    WallCoilMachineService.settlementAmount.add(new BigDecimal(jSONArray2.get(i4).toString()).setScale(2, RoundingMode.CEILING));
                                }
                            }
                            Logger.e("settlement amount was " + WallCoilMachineService.settlementAmount, new Object[0]);
                            if (WallCoilMachineService.settlementAmount.size() > 0) {
                                if (WallCoilMachineService.settlementAmount.get(0).compareTo(BigDecimal.ZERO) == 0 && WallCoilMachineService.settlementAmount.size() <= 1) {
                                    Logger.e("Should settle was false", new Object[0]);
                                    WallCoilMachineService.shouldSettle = false;
                                } else {
                                    Logger.e("Should settle was true and amount was " + WallCoilMachineService.settlementAmount, new Object[0]);
                                    WallCoilMachineService.shouldSettle = true;
                                }
                            } else {
                                WallCoilMachineService.shouldSettle = false;
                            }
                            WallCoilMachineService.vendingComplete = true;
                            Logger.e("Vending was initiated", new Object[0]);
                            return;
                        case "requestSessionClose":
                            try {
                                WallCoilMachineService.m_vmc.vend.session_cancel();
                                WallCoilMachineService.price.clear();
                                WallCoilMachineService.settlementAmount.clear();
                                WallCoilMachineService.shouldSettle = false;
                                WallCoilMachineService.vendingComplete = true;
                                WallCoilMachineService.shouldSessionTimeout = false;
                                break;
                            } catch (Exception e) {
                                Logger.e("requestSessionClose => " + e.getMessage(), new Object[0]);
                            }
                            Logger.e("payment was cancelled", new Object[0]);
                            return;
                        case "requestChannelScan":
                            WallCoilMachineService.this.requestChannelScan();
                            Logger.e("requestChannelScan", new Object[0]);
                            return;
                        case "requestIdScan":
                            Handler handler = new Handler();
                            loggerHelp.appendLog("VMHardwareService : IdScanService called");
                            handler.post(new Runnable() { // from class: com.digitalmediavending.hardware.wallcoilmachine.-$$Lambda$WallCoilMachineService$DataReceiver$1tEzrLEUiz1Uq7QvvQC6hsxEjLU
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WallCoilMachineService.DataReceiver.lambda$onReceive$1(jSONObject, loggerHelp);
                                }
                            });
                            return;
                        case "requestQrCodeScanFlag":
                            Handler handler2 = new Handler();
                            loggerHelp.appendLog("VMHardwareService : QrCodeScanner called");
                            handler2.post(new Runnable() { // from class: com.digitalmediavending.hardware.wallcoilmachine.-$$Lambda$WallCoilMachineService$DataReceiver$qoEnGQrfsrwuGAvlWE3745khVcM
                                @Override // java.lang.Runnable
                                public final void run() {
                                    new QRCodeScannerService(MainApp.getApp(), new QRCodeScannerService.OnDataFoundInterface() { // from class: com.digitalmediavending.hardware.wallcoilmachine.-$$Lambda$WallCoilMachineService$DataReceiver$qnCY0x-94Jc8uWQES7IivGtcSkE
                                        @Override // com.digitalmediavending.hardware.qrCodeScan.QRCodeScannerService.OnDataFoundInterface
                                        public final void dataFoundListener(JSONObject jSONObject2) {
                                            WallCoilMachineService.DataReceiver.lambda$onReceive$2(loggerHelp, jSONObject2);
                                        }
                                    }).connectToQrCodeScanner();
                                }
                            });
                            return;
                        case "requestDoorOpen":
                            Logger.e("Door toggle was requested", new Object[0]);
                            WallCoilMachineService.this.vendingMachine.controlDoorLock(1, 1);
                            return;
                        default:
                            return;
                    }
                } catch (JSONException e2) {
                    Logger.e("New method threw exception", new Object[0]);
                    String message = e2.getMessage();
                    Objects.requireNonNull(message);
                    Logger.e(message, new Object[0]);
                    WallCoilMachineService.sendException(this.methodName, e2.getMessage());
                    e2.printStackTrace();
                }
                Logger.e("New method threw exception", new Object[0]);
                String message2 = e2.getMessage();
                Objects.requireNonNull(message2);
                Logger.e(message2, new Object[0]);
                WallCoilMachineService.sendException(this.methodName, e2.getMessage());
                e2.printStackTrace();
            }
        }

        static /* synthetic */ void lambda$onReceive$1(JSONObject jSONObject, final LoggerHelp loggerHelp) {
            try {
                if (jSONObject.has("vendorID")) {
                    String strTrim = jSONObject.getString("vendorID").trim();
                    if (strTrim.length() > 0) {
                        new IdScanService(strTrim, MainApp.getApp(), new IdScanService.OnDataFoundInterface() { // from class: com.digitalmediavending.hardware.wallcoilmachine.-$$Lambda$WallCoilMachineService$DataReceiver$ZSHtcvug8VZMkRMoLDSUXPc3EqA
                            @Override // com.digitalmediavending.hardware.idScan.IdScanService.OnDataFoundInterface
                            public final void dataFoundListener(JSONObject jSONObject2) {
                                WallCoilMachineService.DataReceiver.lambda$onReceive$0(loggerHelp, jSONObject2);
                            }
                        }).initUsbDevice();
                    } else {
                        loggerHelp.appendLog("VMHardwareService Request_error -> Vendor ID is empty");
                    }
                } else {
                    loggerHelp.appendLog("VMHardwareService Request_error -> VendorID not send");
                }
            } catch (Exception e) {
                loggerHelp.appendLog("VMHardwareService Exception_in_request -> " + e.getMessage());
                e.printStackTrace();
            }
        }

        static /* synthetic */ void lambda$onReceive$0(LoggerHelp loggerHelp, JSONObject jSONObject) {
            if (jSONObject != null) {
                loggerHelp.appendLog("VMHardwareService : Found Data => " + jSONObject);
                SocketSendData.sendMsg(jSONObject.toString());
            }
        }

        static /* synthetic */ void lambda$onReceive$2(LoggerHelp loggerHelp, JSONObject jSONObject) {
            loggerHelp.appendLog("VMHardwareService => qrCodeScanResult() =>" + jSONObject);
            SocketSendData.sendMsg(jSONObject.toString());
        }
    }

    public static class vmc_vend_events implements vmc_vend_t.vend_callbacks_t {
        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onOpenedSessions(short[] shorts) {
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onReceipt(int i, String s) {
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onRemoteVend(int i, int i1, int i2) {
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onStatus(int i) {
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onReady(vmc_vend_t.vend_session_t vend_session_t) {
            Logger.e("onReady() => Nayax port ready", new Object[0]);
            WallCoilMachineService.isNayaxConnected = true;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "nayax");
                jSONObject.put("status", "ready");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SocketSendData.sendMsg(jSONObject.toString());
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onSessionBegin(int i) {
            WallCoilMachineService.shouldSessionTimeout = false;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "session");
                jSONObject.put("status", "started");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SocketSendData.sendMsg(jSONObject.toString());
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < WallCoilMachineService.price.size(); i2++) {
                arrayList.add(new vmc_vend_t.vend_item_t((short) 1, WallCoilMachineService.price.get(i2).multiply(new BigDecimal("100")).intValue(), 1, (byte) 1));
            }
            WallCoilMachineService.m_session = new vmc_vend_t.vend_session_t(arrayList);
            WallCoilMachineService.m_vmc.vend.vend_request(WallCoilMachineService.m_session);
            if (WallCoilMachineService.vmc_config.mag_card_approved_by_vmc_support || WallCoilMachineService.vmc_config.mifare_approved_by_vmc_support) {
                WallCoilMachineService.m_vmc.vend.client_gateway_auth(true);
            }
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onTransactionInfo(vmc_vend_t.vend_session_data_t vend_session_data_t) {
            WallCoilMachineService.transactionId = vend_session_data_t.transaction_id;
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public boolean onVendApproved(vmc_vend_t.vend_session_t vend_session_t) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "vending_status");
                jSONObject.put(FirebaseAnalytics.Param.TRANSACTION_ID, WallCoilMachineService.transactionId);
                jSONObject.put("status", "approved");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SocketSendData.sendMsg(jSONObject.toString());
            Logger.e("onVendApproved", new Object[0]);
            long jCurrentTimeMillis = System.currentTimeMillis();
            Log.e("log123", "Qty:: " + WallCoilMachineService.quantity + "  TimeStamp:: " + System.currentTimeMillis());
            while (!WallCoilMachineService.vendingComplete) {
                WallCoilMachineService.startTimeoutSettlement(jCurrentTimeMillis, WallCoilMachineService.quantity);
                SystemClock.sleep(1000L);
            }
            ArrayList<vmc_vend_t.vend_item_t> arrayList = new ArrayList<>();
            Log.e(WallCoilMachineService.TAG, "settlementAmount:: " + WallCoilMachineService.settlementAmount);
            Log.e(WallCoilMachineService.TAG, "settlementAmount.size():: " + WallCoilMachineService.settlementAmount.size());
            if (WallCoilMachineService.settlementAmount != null && WallCoilMachineService.settlementAmount.size() > 0) {
                for (int i = 0; i < WallCoilMachineService.settlementAmount.size(); i++) {
                    Logger.e("Settlement amount true was " + WallCoilMachineService.settlementAmount.get(i).multiply(new BigDecimal("100")), new Object[0]);
                    arrayList.add(new vmc_vend_t.vend_item_t((short) 1, 1, 1, (byte) 1));
                    arrayList.add(new vmc_vend_t.vend_item_t((short) 1, WallCoilMachineService.settlementAmount.get(i).multiply(new BigDecimal("100")).subtract(new BigDecimal("1")).intValue(), 1, (byte) 1));
                }
                WallCoilMachineService.settlementAmount.clear();
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put(AppConstants.RESPONSE_TYPE_KEY, "settlement");
                    jSONObject2.put("status", "Success");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Log.e("settlement:: ", "settlement:: " + jSONObject2);
                SocketSendData.sendMsg(jSONObject2.toString());
            } else {
                for (int i2 = 0; i2 < WallCoilMachineService.price.size(); i2++) {
                    Logger.e("Settlement amount true was " + WallCoilMachineService.price.get(i2).multiply(new BigDecimal("100")), new Object[0]);
                    arrayList.add(new vmc_vend_t.vend_item_t((short) 1, 1, 1, (byte) 1));
                    arrayList.add(new vmc_vend_t.vend_item_t((short) 1, WallCoilMachineService.price.get(i2).multiply(new BigDecimal("100")).subtract(new BigDecimal("1")).intValue(), 1, (byte) 1));
                    JSONObject jSONObject3 = new JSONObject();
                    try {
                        jSONObject3.put(AppConstants.RESPONSE_TYPE_KEY, "settlement");
                        jSONObject3.put("status", "timeout");
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                    SocketSendData.sendMsg(jSONObject3.toString());
                }
            }
            vend_session_t.products_list = arrayList;
            SystemClock.sleep(AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
            WallCoilMachineService.vendingComplete = false;
            WallCoilMachineService.price.clear();
            return WallCoilMachineService.shouldSettle;
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onVendDenied(vmc_vend_t.vend_session_t vend_session_t) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "vending_status");
                jSONObject.put(FirebaseAnalytics.Param.TRANSACTION_ID, WallCoilMachineService.transactionId);
                jSONObject.put("status", "failed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SocketSendData.sendMsg(jSONObject.toString());
            WallCoilMachineService.settlementAmount.clear();
            WallCoilMachineService.price.clear();
            WallCoilMachineService.m_vmc.vend.session_cancel();
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onSettlement(boolean b) {
            Logger.e("Settlement was called " + b, new Object[0]);
        }

        @Override // com.bitmick.marshall.vmc.vmc_vend_t.vend_callbacks_t
        public void onReaderState(boolean b) {
            Logger.e("Reader state is " + b, new Object[0]);
        }
    }

    public static void mockInitNayax() {
        boolean zIs_ready = false;
        try {
            zIs_ready = m_vmc.link.is_ready();
        } catch (Exception e) {
            Logger.e("Exception while init nayax " + e.getMessage(), new Object[0]);
        }
        if (zIs_ready) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.wallcoilmachine.-$$Lambda$WallCoilMachineService$qv6VY3thiwQ1_DHBq1mrpAikHQE
                @Override // java.lang.Runnable
                public final void run() {
                    WallCoilMachineService.lambda$mockInitNayax$1();
                }
            }, 1000L);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "nayax");
            jSONObject.put("status", "failed");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        SocketSendData.sendMsg(jSONObject.toString());
    }

    static /* synthetic */ void lambda$mockInitNayax$1() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "nayax");
            jSONObject.put("status", "ready");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SocketSendData.sendMsg(jSONObject.toString());
    }

    public static void startTimeoutSettlement(long sessionStarted, int numOfProducts) {
        long j = numOfProducts;
        if (System.currentTimeMillis() - sessionStarted > 130 * j * 1000) {
            Log.e("Settlement", "Settled through timeout counter with current time being " + (System.currentTimeMillis() - sessionStarted) + "and timeout being " + (j * 60));
            shouldSettle = true;
            vendingComplete = true;
        }
    }

    public void sessionTimeoutCounterStart(final long sessionStarted) {
        new Handler().postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.wallcoilmachine.-$$Lambda$WallCoilMachineService$JQImLj9EtNC2skNhp-usxZY6ydw
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sessionTimeoutCounterStart$2$WallCoilMachineService(sessionStarted);
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$sessionTimeoutCounterStart$2$WallCoilMachineService(long j) {
        try {
            if (System.currentTimeMillis() - j > 120000) {
                Logger.e("Inside timeout with current time as " + System.currentTimeMillis() + " And time difference as " + (System.currentTimeMillis() - j), new Object[0]);
                try {
                    m_vmc.vend.session_cancel();
                    ArrayList<BigDecimal> arrayList = price;
                    if (arrayList != null) {
                        arrayList.clear();
                    }
                    ArrayList<BigDecimal> arrayList2 = settlementAmount;
                    if (arrayList2 != null) {
                        arrayList2.clear();
                    }
                    shouldSettle = false;
                    vendingComplete = true;
                    if (shouldSessionTimeout) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "session");
                            jSONObject.put("status", "timeout");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SocketSendData.sendMsg(jSONObject.toString());
                    }
                    shouldSessionTimeout = false;
                } catch (Exception e2) {
                    Logger.e("sessionTimeoutCounterStart => Exception => " + e2.getMessage(), new Object[0]);
                }
            }
            if (!shouldSessionTimeout) {
                shouldSessionTimeout = true;
            } else {
                sessionTimeoutCounterStart(j);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public static void sendException(String methodName, String message) {
        if (methodName.equals("requestSessionStart")) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "session");
                jSONObject.put("status", "failed");
                jSONObject.put("reason", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SocketSendData.sendMsg(jSONObject.toString());
        }
    }

    private void startForegroundService() {
        Notification notificationBuild;
        String packageName = getPackageName();
        int i = 1;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(packageName, "Hardware Service", 0);
            notificationChannel.setLightColor(-16776961);
            notificationChannel.setLockscreenVisibility(0);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(notificationChannel);
            notificationBuild = new NotificationCompat.Builder(this, packageName).setOngoing(true).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("App is running in background").setPriority(1).setCategory(NotificationCompat.CATEGORY_SERVICE).build();
        } else {
            notificationBuild = new NotificationCompat.Builder(this, packageName).setContentTitle("Background Service").setContentText("Tap for more options").setSmallIcon(R.mipmap.ic_launcher).build();
            i = 2;
        }
        startForeground(i, notificationBuild);
    }

    public void requestChannelScan() {
        this.vendingMachine.scanTrays(null);
    }
}

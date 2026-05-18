package com.digitalmediavending.hardware;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import com.digitalmediavending.hardware.qrCodeScan.RESULT_TYPE;
import com.digitalmediavending.hardware.utils.AppConstants;
import com.digitalmediavending.hardware.wallcoilmachine.ConstantsWallMachine;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.orhanobut.logger.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/* JADX INFO: loaded from: classes.dex */
public class VMHardwareMockService extends Service {
    private static final String TAG = "VMHardwareMockService";
    private static int tempCounter = 0;
    private static int updatedTemp = 0;
    public static boolean vendingComplete = false;
    private DataReceiver dataReceiver;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    static /* synthetic */ int access$108() {
        int i = tempCounter;
        tempCounter = i + 1;
        return i;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Mock Service Started", 1).show();
        Log.e("Mock", "The mock service started");
        this.dataReceiver = new DataReceiver();
        SocketSendData.init();
        registerReceiver();
        heartbeatInit();
        return 2;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.dataReceiver);
        Log.e("Mock", "onDestroy()");
    }

    public void registerReceiver() {
        Log.e("Mock", "The mock service broadcast registered");
        registerReceiver(this.dataReceiver, new IntentFilter("sendBroadcast"));
    }

    public static void sessionStart(final Boolean statusFailure) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.VMHardwareMockService.1
            @Override // java.lang.Runnable
            public void run() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "session");
                    jSONObject.put("status", "started");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SocketSendData.sendMsg(jSONObject.toString());
                if (statusFailure.booleanValue()) {
                    VMHardwareMockService.initPayment(3, 2);
                } else {
                    VMHardwareMockService.initPaymentFailed();
                }
            }
        }, 10000L);
    }

    public static void heartbeatInit() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.VMHardwareMockService.2
            @Override // java.lang.Runnable
            public void run() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "heartbeat");
                    jSONObject.put("a_current", "123");
                    jSONObject.put("b_current", "123");
                    jSONObject.put("c_current", "123");
                    jSONObject.put("fault_state", "0");
                    jSONObject.put("input", "123");
                    jSONObject.put("output", "123");
                    jSONObject.put("run_state", "123");
                    jSONObject.put("temperature_cfm", "123");
                    if (VMHardwareMockService.tempCounter % 8 == 0) {
                        VMHardwareMockService.updatedTemp++;
                        if (VMHardwareMockService.updatedTemp > 30) {
                            int unused = VMHardwareMockService.tempCounter = 0;
                            int unused2 = VMHardwareMockService.updatedTemp = 0;
                        }
                    }
                    jSONObject.put("temperature_inside", Integer.toString(VMHardwareMockService.updatedTemp));
                    jSONObject.put("temperature_outside", "123");
                    jSONObject.put("weight_sensor", "123");
                    jSONObject.put("x_coordinate", "123");
                    jSONObject.put("y_coordinate", "123");
                    jSONObject.put("z_coordinate", "123");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SocketSendData.sendMsg(jSONObject.toString());
                VMHardwareMockService.access$108();
                VMHardwareMockService.heartbeatInit();
            }
        }, AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
    }

    public static void initPayment(int quantity, int price) {
        Log.e("vend", "init payment");
        Logger.e("initPayment was called", new Object[0]);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.-$$Lambda$VMHardwareMockService$uYe58jiSf6yS-SrnM141xM-wbyw
            @Override // java.lang.Runnable
            public final void run() {
                VMHardwareMockService.lambda$initPayment$0();
            }
        }, 10000L);
    }

    static /* synthetic */ void lambda$initPayment$0() {
        Logger.e("Run was also called", new Object[0]);
        JSONObject jSONObject = new JSONObject();
        try {
            Logger.e("JSON OBJECT PARSING WAS TRIED", new Object[0]);
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "vending_status");
            jSONObject.put(FirebaseAnalytics.Param.TRANSACTION_ID, Long.toString(1231123123L));
            jSONObject.put("status", "approved");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SocketSendData.sendMsg(jSONObject.toString());
    }

    public static void initPaymentFailed() {
        Log.e("vend", "init payment");
        Logger.e("initPayment was called", new Object[0]);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.-$$Lambda$VMHardwareMockService$0xRUEz73ggXtOoDxUi3CtdBsjnA
            @Override // java.lang.Runnable
            public final void run() {
                VMHardwareMockService.lambda$initPaymentFailed$1();
            }
        }, 10000L);
    }

    static /* synthetic */ void lambda$initPaymentFailed$1() {
        Logger.e("Run was also called", new Object[0]);
        JSONObject jSONObject = new JSONObject();
        try {
            Logger.e("JSON OBJECT PARSING WAS TRIED", new Object[0]);
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "vending_status");
            jSONObject.put(FirebaseAnalytics.Param.TRANSACTION_ID, Long.toString(1231123123L));
            jSONObject.put("status", "failed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SocketSendData.sendMsg(jSONObject.toString());
    }

    public static void vendSingleProduct(int column, int row) {
        Log.e("vend", "vend single product");
        Logger.e("Vending product was called", new Object[0]);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.-$$Lambda$VMHardwareMockService$TRVXlr6uFXZnigL3H8S1uYZbCao
            @Override // java.lang.Runnable
            public final void run() {
                VMHardwareMockService.lambda$vendSingleProduct$2();
            }
        }, 10000L);
    }

    static /* synthetic */ void lambda$vendSingleProduct$2() {
        Logger.e("Run was also called", new Object[0]);
        JSONObject jSONObject = new JSONObject();
        try {
            Logger.e("JSON object was also called", new Object[0]);
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "vendout");
            jSONObject.put("status", FirebaseAnalytics.Param.SUCCESS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SocketSendData.sendMsg(jSONObject.toString());
    }

    public static void vendSingleProductFailed(int column, int row) {
        Log.e("vend", "vend single product");
        Logger.e("Vending product was called", new Object[0]);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.-$$Lambda$VMHardwareMockService$YnJQ06a1P6rhw9Jt-L_e3jNo9BE
            @Override // java.lang.Runnable
            public final void run() {
                VMHardwareMockService.lambda$vendSingleProductFailed$3();
            }
        }, 10000L);
    }

    static /* synthetic */ void lambda$vendSingleProductFailed$3() {
        Logger.e("Run was also called", new Object[0]);
        JSONObject jSONObject = new JSONObject();
        try {
            Logger.e("JSON object was also called", new Object[0]);
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "vendout");
            jSONObject.put("status", "failed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SocketSendData.sendMsg(jSONObject.toString());
    }

    public static void sendMockIdScanData() {
        new Handler().postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.-$$Lambda$VMHardwareMockService$KAvaK5JXMSDriM2g8fM4qGCAKl8
            @Override // java.lang.Runnable
            public final void run() {
                VMHardwareMockService.lambda$sendMockIdScanData$4();
            }
        }, 1000L);
    }

    static /* synthetic */ void lambda$sendMockIdScanData$4() {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(AppConstants.RESPONSE_FIRST_NAME_KEY, "STEPHEN");
            jSONObject2.put(AppConstants.RESPONSE_MIDDLE_NAME_KEY, "AARON");
            jSONObject2.put(AppConstants.RESPONSE_LAST_NAME_KEY, "AUSBURNE");
            jSONObject2.put(AppConstants.RESPONSE_ADDRESS_1_KEY, "492 LILLIAN WAY");
            jSONObject2.put(AppConstants.RESPONSE_ADDRESS_2_KEY, "");
            jSONObject2.put(AppConstants.RESPONSE_BIRTH_DATE_KEY, "04/27/1996");
            jSONObject2.put(AppConstants.RESPONSE_EXPIRATION_DATE_KEY, "04/27/2023");
            jSONObject2.put(AppConstants.RESPONSE_PARSER_VERSION_KEY, "v.1.27.0");
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, AppConstants.RESPONSE_ID_SCAN_FLAG);
            jSONObject.put(AppConstants.RESPONSE_USER_INFO_KEY, jSONObject2);
            SocketSendData.sendMsg(jSONObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMockQRCodeScanData() {
        new Handler().postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.-$$Lambda$VMHardwareMockService$NOvkE53lUixk1gC1PtPXL2S3tcs
            @Override // java.lang.Runnable
            public final void run() {
                VMHardwareMockService.lambda$sendMockQRCodeScanData$5();
            }
        }, 1000L);
    }

    static /* synthetic */ void lambda$sendMockQRCodeScanData$5() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, AppConstants.RESPONSE_QR_CODE_SCAN_FLAG);
            jSONObject.put("status", RESULT_TYPE.SUCCESS);
            jSONObject.put(AppConstants.RESPONSE_MSG_QR_CODE_SCAN, AppConstants.READ_DATA_CALL_BACK);
            jSONObject.put(AppConstants.QR_CODE_USB_DEVICE, "[DeviceName : /dev/bus/usb/001/006, DeviceVID : 9969, DevicePID : 22096]");
            jSONObject.put(AppConstants.RESPONSE_QR_CODE_SCAN_INFO_KEY, "OTGXN2Q3YJFJZJVKNG");
            Log.e(TAG, "sendMockQRCodeScanData: " + jSONObject.toString());
            SocketSendData.sendMsg(jSONObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestSettlement() {
        while (!vendingComplete) {
            SystemClock.sleep(1000L);
        }
        vendingComplete = false;
        new Handler().postDelayed(new Runnable() { // from class: com.digitalmediavending.hardware.-$$Lambda$VMHardwareMockService$C0UIoROYQqlBCLh-yyjSg69hc6g
            @Override // java.lang.Runnable
            public final void run() {
                VMHardwareMockService.lambda$requestSettlement$6();
            }
        }, 1000L);
    }

    static /* synthetic */ void lambda$requestSettlement$6() {
        try {
            initNayaxTerminal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class DataReceiver extends BroadcastReceiver {
        private DataReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            JSONObject jSONObject;
            if (intent.getAction().equals("sendBroadcast")) {
                try {
                    jSONObject = new JSONObject(intent.getStringExtra("data"));
                    String string = jSONObject.getString(FirebaseAnalytics.Param.METHOD);
                    Log.e("Mock ", string);
                    switch (string) {
                        case "requestInitNayax":
                            VMHardwareMockService.initNayaxTerminal();
                            break;
                        case "requestProductVend":
                            Log.e("Mock", ConstantsWallMachine.REQUEST_PRODUCT_VEND);
                            Logger.e("Product vend was requested", new Object[0]);
                            Integer.parseInt(jSONObject.getString("col"));
                            Integer.parseInt(jSONObject.getString("row"));
                            VMHardwareMockService.vendSingleProductFailed(2, 3);
                            Logger.e("Vending was initiated", new Object[0]);
                            break;
                        case "requestSessionStart":
                            Log.e("Mock", "requestSessionStart");
                            Logger.e("session was started by the card", new Object[0]);
                            VMHardwareMockService.sessionStart(true);
                            break;
                        case "requestSessionStartFail":
                            Log.e("Mock", "requestSessionStart");
                            Logger.e("session was started by the card", new Object[0]);
                            VMHardwareMockService.sessionStart(false);
                            break;
                        case "requestPayment":
                            Log.e("Mock", "requestPayment");
                            Logger.e("payment was requested by the vending machine", new Object[0]);
                            Logger.e("The quantity was " + Integer.parseInt(jSONObject.getString(FirebaseAnalytics.Param.QUANTITY)) + "And the price was " + Integer.parseInt(jSONObject.getString(FirebaseAnalytics.Param.PRICE)), new Object[0]);
                            break;
                        case "requestSettlement":
                            Log.e("Mock", "requestSettlement");
                            Integer.parseInt(jSONObject.getString("numberOfProducts"));
                            VMHardwareMockService.vendingComplete = true;
                            VMHardwareMockService.requestSettlement();
                            Logger.e("Vending was initiated", new Object[0]);
                            break;
                        case "requestIdScan":
                            VMHardwareMockService.sendMockIdScanData();
                            break;
                        case "requestQrCodeScanFlag":
                            Log.e("Mock", "RequestQRCode was called");
                            Logger.e("RequestQRCode was called from broadcast", new Object[0]);
                            VMHardwareMockService.sendMockQRCodeScanData();
                            break;
                    }
                } catch (JSONException e) {
                    Logger.e("New method threw exception", new Object[0]);
                    e.printStackTrace();
                }
            }
        }
    }

    public static void initNayaxTerminal() {
        Log.e("Nayax", "the nayax terminal was called");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "nayax");
            jSONObject.put("status", "ready");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SocketSendData.sendMsg(jSONObject.toString());
    }

    public void callAuthInt() {
        new Thread(new Runnable() { // from class: com.digitalmediavending.hardware.-$$Lambda$VMHardwareMockService$a7Y2fU_jm5UVa2pDbmFMiHFIAgY
            @Override // java.lang.Runnable
            public final void run() {
                VMHardwareMockService.lambda$callAuthInt$7();
            }
        }).start();
    }

    static /* synthetic */ void lambda$callAuthInt$7() {
        String str = "http://www.dataaccess.com/webservicesserver//NumberToWords";
        SoapObject soapObject = new SoapObject("http://www.dataaccess.com/webservicesserver/", "NumberToWords");
        soapObject.addProperty("ubiNum", "500");
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(120);
        soapSerializationEnvelope.dotNet = true;
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        Log.e("envelope was", "this : data was : " + soapSerializationEnvelope.bodyOut);
        HttpTransportSE httpTransportSE = new HttpTransportSE("https://www.dataaccess.com/webservicesserver/NumberConversion.wso");
        try {
            httpTransportSE.debug = true;
            httpTransportSE.call(str, soapSerializationEnvelope);
            Log.e("Result", "Something was called from here");
        } catch (Exception e) {
            Log.e("Exception", "the exception cause by : " + e.getMessage());
            e.printStackTrace();
        }
        Log.e("data", "Request dump was " + httpTransportSE.requestDump);
        Log.e("data", "The data received is " + soapSerializationEnvelope.bodyIn.toString());
    }
}

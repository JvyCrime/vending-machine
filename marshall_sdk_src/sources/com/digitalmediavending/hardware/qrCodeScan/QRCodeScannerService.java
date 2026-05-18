package com.digitalmediavending.hardware.qrCodeScan;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import com.digitalmediavending.hardware.utils.AppConstants;
import com.digitalmediavending.hardware.utils.LoggerHelp;
import com.felhr.usbserial.CDCSerialDevice;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class QRCodeScannerService {
    private static final String ACTION_CDC_DRIVER_NOT_WORKING = "com.felhr.connectivityservices.ACTION_CDC_DRIVER_NOT_WORKING";
    private static final String ACTION_USB_DEVICE_NOT_WORKING = "com.felhr.connectivityservices.ACTION_USB_DEVICE_NOT_WORKING";
    public static final String ACTION_USB_NOT_SUPPORTED = "com.felhr.usbservice.USB_NOT_SUPPORTED";
    private static final String ACTION_USB_READY = "com.felhr.connectivityservices.USB_READY";
    private static final int BAUD_RATE = 9600;
    private static final String TAG = "QRCodeScannerService";
    private Context mContext;
    private final OnDataFoundInterface mListener;
    private boolean serialPortConnected;
    private UsbManager usbManager = null;
    private UsbDevice device = null;
    private String foundDeviceDetails = null;
    private UsbDeviceConnection connection = null;
    private UsbSerialDevice serialPort = null;
    private long serialPortInitiationTime = 0;
    private long serialPortDataReadTime = 0;
    private final UsbSerialInterface.UsbReadCallback readCallback = new UsbSerialInterface.UsbReadCallback() { // from class: com.digitalmediavending.hardware.qrCodeScan.-$$Lambda$QRCodeScannerService$hR2rM_-mfl-856jdyeqLhFqsNCs
        @Override // com.felhr.usbserial.UsbSerialInterface.UsbReadCallback
        public final void onReceivedData(byte[] bArr) {
            this.f$0.lambda$new$0$QRCodeScannerService(bArr);
        }
    };
    private final UsbSerialInterface.UsbCTSCallback ctsCallback = new UsbSerialInterface.UsbCTSCallback() { // from class: com.digitalmediavending.hardware.qrCodeScan.-$$Lambda$QRCodeScannerService$vP2iGcL4tfx8bfNHF0tIk51p90E
        @Override // com.felhr.usbserial.UsbSerialInterface.UsbCTSCallback
        public final void onCTSChanged(boolean z) {
            this.f$0.lambda$new$1$QRCodeScannerService(z);
        }
    };
    private final UsbSerialInterface.UsbDSRCallback dsrCallback = new UsbSerialInterface.UsbDSRCallback() { // from class: com.digitalmediavending.hardware.qrCodeScan.-$$Lambda$QRCodeScannerService$8iizuTsuav9oGgJrDEVynuwOV-w
        @Override // com.felhr.usbserial.UsbSerialInterface.UsbDSRCallback
        public final void onDSRChanged(boolean z) {
            this.f$0.lambda$new$2$QRCodeScannerService(z);
        }
    };
    private final LoggerHelp loggerHelp = new LoggerHelp();

    public interface OnDataFoundInterface {
        void dataFoundListener(JSONObject dataObject);
    }

    public QRCodeScannerService(Context mContext, OnDataFoundInterface dataFoundInterface) {
        this.mContext = null;
        this.mContext = mContext;
        this.mListener = dataFoundInterface;
    }

    public void connectToQrCodeScanner() {
        UsbDevice usbDevice;
        UsbManager usbManager = (UsbManager) this.mContext.getSystemService("usb");
        this.usbManager = usbManager;
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        this.loggerHelp.appendLog("QRCodeScannerService => connectToQrCodeScanner => Available devices : " + deviceList.size());
        if (!deviceList.isEmpty()) {
            Iterator<Map.Entry<String, UsbDevice>> it = deviceList.entrySet().iterator();
            boolean z = true;
            boolean z2 = true;
            while (it.hasNext()) {
                this.device = it.next().getValue();
                this.foundDeviceDetails = "[DeviceName : " + this.device.getDeviceName() + ", DeviceVID : " + this.device.getVendorId() + ", DevicePID : " + this.device.getProductId() + "]";
                int vendorId = this.device.getVendorId();
                int productId = this.device.getProductId();
                String deviceName = this.device.getDeviceName();
                LoggerHelp loggerHelp = this.loggerHelp;
                StringBuilder sb = new StringBuilder();
                sb.append("QRCodeScannerService => usbDevices => test_DeviceName : ");
                sb.append(deviceName);
                loggerHelp.appendLog(sb.toString());
                this.loggerHelp.appendLog("QRCodeScannerService => usbDevices => test_deviceVID : " + vendorId);
                this.loggerHelp.appendLog("QRCodeScannerService => usbDevices => test_devicePID : " + productId);
                if ((vendorId == 10205 && productId == 2) || ((vendorId == 259 && productId == 24673) || ((vendorId == 9969 && productId == 22096) || ((vendorId == 9969 && productId == 53249) || (vendorId == 1155 && productId == 22336))))) {
                    this.loggerHelp.appendLog("QRCodeScannerService => usbDeviceIsValid => " + deviceName + " : true");
                    break;
                }
                this.loggerHelp.appendLog("QRCodeScannerService => usbDeviceIsValid => " + deviceName + " : false");
                z2 = false;
            }
            z = z2;
            sendDataToBroadCast(z ? RESULT_TYPE.DEFAULT : RESULT_TYPE.ERROR, z ? AppConstants.VALID_USB_SCANNER_FOUND : AppConstants.INVALID_USB_SCANNER_FOUND, this.foundDeviceDetails, null);
            if (!z || (usbDevice = this.device) == null) {
                return;
            }
            this.connection = this.usbManager.openDevice(usbDevice);
            openDeviceForScanning();
            return;
        }
        sendDataToBroadCast(RESULT_TYPE.ERROR, AppConstants.NO_USB_SCANNER_FOUND, this.foundDeviceDetails, null);
    }

    private void openDeviceForScanning() {
        UsbSerialDevice usbSerialDeviceCreateUsbSerialDevice = UsbSerialDevice.createUsbSerialDevice(this.device, this.connection);
        this.serialPort = usbSerialDeviceCreateUsbSerialDevice;
        if (usbSerialDeviceCreateUsbSerialDevice != null) {
            if (usbSerialDeviceCreateUsbSerialDevice.open()) {
                this.serialPortInitiationTime = Calendar.getInstance().getTimeInMillis();
                this.serialPortConnected = true;
                this.serialPort.setBaudRate(BAUD_RATE);
                this.serialPort.setDataBits(8);
                this.serialPort.setStopBits(1);
                this.serialPort.setParity(0);
                this.serialPort.setFlowControl(0);
                this.serialPort.read(this.readCallback);
                this.serialPort.getCTS(this.ctsCallback);
                this.serialPort.getDSR(this.dsrCallback);
                sendDataToBroadCast(RESULT_TYPE.DEFAULT, ACTION_USB_READY, this.foundDeviceDetails, null);
                return;
            }
            if (this.serialPort instanceof CDCSerialDevice) {
                sendDataToBroadCast(RESULT_TYPE.ERROR, ACTION_CDC_DRIVER_NOT_WORKING, this.foundDeviceDetails, null);
                return;
            } else {
                sendDataToBroadCast(RESULT_TYPE.ERROR, ACTION_USB_DEVICE_NOT_WORKING, this.foundDeviceDetails, null);
                return;
            }
        }
        sendDataToBroadCast(RESULT_TYPE.ERROR, ACTION_USB_NOT_SUPPORTED, this.foundDeviceDetails, null);
    }

    public void disconnectUsbScanner() {
        UsbSerialDevice usbSerialDevice = this.serialPort;
        if (usbSerialDevice == null || !this.serialPortConnected) {
            return;
        }
        usbSerialDevice.close();
        this.serialPort = null;
        this.serialPortConnected = false;
        this.loggerHelp.appendLog("QRCodeScannerService => serialPort.close() called AND serialPort assigned null");
    }

    public void write(byte[] data) {
        UsbSerialDevice usbSerialDevice = this.serialPort;
        if (usbSerialDevice == null || !this.serialPortConnected) {
            return;
        }
        usbSerialDevice.write(data);
    }

    public void sendDataToBroadCast(RESULT_TYPE result_type, String resultMessage, String usbDevice, String foundData) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, AppConstants.RESPONSE_QR_CODE_SCAN_FLAG);
            jSONObject.put("status", result_type);
            jSONObject.put(AppConstants.RESPONSE_MSG_QR_CODE_SCAN, resultMessage);
            jSONObject.put(AppConstants.QR_CODE_USB_DEVICE, usbDevice);
            jSONObject.put(AppConstants.RESPONSE_QR_CODE_SCAN_INFO_KEY, foundData);
            this.mListener.dataFoundListener(jSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ void lambda$new$0$QRCodeScannerService(byte[] bArr) {
        String str = new String(bArr, StandardCharsets.UTF_8);
        if (str.length() > 0) {
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            this.serialPortDataReadTime = timeInMillis;
            if (timeInMillis - this.serialPortInitiationTime > 500) {
                sendDataToBroadCast(RESULT_TYPE.SUCCESS, AppConstants.READ_DATA_CALL_BACK, this.foundDeviceDetails, str);
                disconnectUsbScanner();
            }
        }
    }

    public /* synthetic */ void lambda$new$1$QRCodeScannerService(boolean z) {
        sendDataToBroadCast(RESULT_TYPE.SUCCESS, AppConstants.CTS_CHANGE_CALL_BACK, this.foundDeviceDetails, String.valueOf(z));
    }

    public /* synthetic */ void lambda$new$2$QRCodeScannerService(boolean z) {
        sendDataToBroadCast(RESULT_TYPE.SUCCESS, AppConstants.DSR_CHANGE_CALL_BACK, this.foundDeviceDetails, String.valueOf(z));
    }
}

package com.digitalmediavending.hardware.idScan;

import android.content.Context;
import android.util.Log;
import com.digitalmediavending.hardware.utils.AppConstants;
import com.digitalmediavending.hardware.utils.LoggerHelp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import net.idscan.android.dlparser.DLParser;
import net.idscan.components.android.hwreaders.common.DocumentData;
import net.idscan.components.android.hwreaders.common.DocumentType;
import net.idscan.components.android.hwreaders.common.Observer;
import net.idscan.components.android.hwreaders.usbftdi.DeviceEvent;
import net.idscan.components.android.hwreaders.usbftdi.FTDIDevice;
import net.idscan.components.android.hwreaders.usbftdi.FTDIMgr;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class IdScanService {
    private static final String TAG = "IdScanActivity";
    private List<FTDIDevice> ftdiDeviceArrayList;
    private FTDIMgr ftdiMgr;
    private final Context mContext;
    private OnDataFoundInterface mListener;
    private final String vendorID;
    private FTDIDevice scannerDevice = null;
    private long serialPortInitiationTime = 0;
    private long serialPortDataReadTime = 0;
    private final LoggerHelp logger = new LoggerHelp();

    public interface OnDataFoundInterface {
        void dataFoundListener(JSONObject dataObject);
    }

    public IdScanService(String vendorID, Context mContext, OnDataFoundInterface mListener) {
        this.vendorID = vendorID;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    public void initUsbDevice() {
        FTDIMgr fTDIMgrCreate = FTDIMgr.INSTANCE.create(this.mContext);
        this.ftdiMgr = fTDIMgrCreate;
        int i = 0;
        fTDIMgrCreate.setAcceptableDocumentTypes(new HashSet(Arrays.asList(DocumentType.PDF417, DocumentType.MAGNETIC_STRIPE, DocumentType.OCR_MRZ)));
        if (!this.ftdiMgr.getIsEnabled()) {
            Log.w(TAG, "initUsbDevice: ftdiMgr is disabled. Enabling now");
            this.logger.appendLog("ftdiMgr is disabled. Enabling now");
            this.ftdiMgr.enable();
        }
        List<FTDIDevice> deviceList = this.ftdiMgr.getDeviceList();
        this.ftdiDeviceArrayList = deviceList;
        if (deviceList.size() > 0) {
            Log.w(TAG, "initUsbDevice: ftdiMgr found device list size : " + this.ftdiDeviceArrayList);
            this.logger.appendLog("initUsbDevice: ftdiMgr found device list size : " + this.ftdiDeviceArrayList.size());
            while (true) {
                if (i >= this.ftdiDeviceArrayList.size()) {
                    break;
                }
                this.logger.appendLog("Device " + i + " => Name : " + this.ftdiDeviceArrayList.get(i).getName() + ", VID : " + this.ftdiDeviceArrayList.get(i).getVid() + ", PID : " + this.ftdiDeviceArrayList.get(i).getPid());
                if (this.ftdiDeviceArrayList.get(i).getVid() == 1027 && this.ftdiDeviceArrayList.get(i).getPid() == 24577) {
                    this.scannerDevice = this.ftdiDeviceArrayList.get(i);
                    break;
                }
                i++;
            }
            if (this.scannerDevice != null) {
                this.logger.appendLog("APP_PACKAGE :  " + this.mContext.getPackageName());
                this.logger.appendLog("LICENSE_KEY :  " + this.vendorID);
                this.logger.appendLog("Device_status : VALID => Connecting to " + this.scannerDevice.getName());
                this.serialPortInitiationTime = Calendar.getInstance().getTimeInMillis();
                this.scannerDevice.connect();
            } else {
                Log.w(TAG, "Device_status: INVALID => Scanner with required VID and PID not found");
                this.logger.appendLog("Device_status : INVALID => Scanner with required VID and PID not found");
            }
        } else {
            Log.w(TAG, "Device_status: ERROR => No devices found");
            this.logger.appendLog("Device_status : ERROR => No devices found");
        }
        this.ftdiMgr.getObservableDeviceEvent().registerObserver(new Observer() { // from class: com.digitalmediavending.hardware.idScan.-$$Lambda$IdScanService$6rHCgvJLQo_-6KVqW_Hs3L9tsZQ
            @Override // net.idscan.components.android.hwreaders.common.Observer
            public final void onNotify(Object obj) {
                this.f$0.lambda$initUsbDevice$0$IdScanService((DeviceEvent) obj);
            }
        });
        this.ftdiMgr.getObservableData().registerObserver(new Observer() { // from class: com.digitalmediavending.hardware.idScan.-$$Lambda$IdScanService$y4eJL5IHDrA0bghR05yyoph4RAw
            @Override // net.idscan.components.android.hwreaders.common.Observer
            public final void onNotify(Object obj) {
                this.f$0.lambda$initUsbDevice$1$IdScanService((DocumentData) obj);
            }
        });
        this.ftdiMgr.getObservableState().registerObserver(new Observer() { // from class: com.digitalmediavending.hardware.idScan.-$$Lambda$IdScanService$GOyGn-_BvIeuLTlg2Jf049UjND8
            @Override // net.idscan.components.android.hwreaders.common.Observer
            public final void onNotify(Object obj) {
                this.f$0.lambda$initUsbDevice$2$IdScanService((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$initUsbDevice$0$IdScanService(DeviceEvent deviceEvent) {
        Log.w(TAG, "getObservableDeviceEvent(): " + deviceEvent);
        this.logger.appendLog("getObservableDeviceEvent() : " + deviceEvent);
    }

    public /* synthetic */ void lambda$initUsbDevice$1$IdScanService(DocumentData documentData) {
        Log.w(TAG, "getObservableData(): " + documentData);
        this.logger.appendLog("getObservableData() -> documentData -> documentDataByteArray : " + Arrays.toString(documentData.getData()));
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        this.serialPortDataReadTime = timeInMillis;
        if (timeInMillis - this.serialPortInitiationTime > 500) {
            startParsing(documentData.getData());
        }
    }

    public /* synthetic */ void lambda$initUsbDevice$2$IdScanService(Boolean bool) {
        Log.w(TAG, "getObservableState() -> ftdiMgr_State : " + bool);
        this.logger.appendLog("getObservableState() -> ftdiMgr_State : " + bool);
    }

    private void startParsing(byte[] observedData) {
        JSONObject jSONObject = new JSONObject();
        try {
            DLParser dLParser = new DLParser();
            dLParser.setup(this.mContext, this.vendorID);
            DLParser.DLResult dLResult = dLParser.parse(observedData);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(AppConstants.RESPONSE_FIRST_NAME_KEY, dLResult.firstName);
            jSONObject2.put(AppConstants.RESPONSE_MIDDLE_NAME_KEY, dLResult.middleName);
            jSONObject2.put(AppConstants.RESPONSE_LAST_NAME_KEY, dLResult.lastName);
            jSONObject2.put(AppConstants.RESPONSE_ADDRESS_1_KEY, dLResult.address1);
            jSONObject2.put(AppConstants.RESPONSE_ADDRESS_2_KEY, dLResult.address2);
            jSONObject2.put(AppConstants.RESPONSE_BIRTH_DATE_KEY, dLResult.birthdate);
            jSONObject2.put(AppConstants.RESPONSE_EXPIRATION_DATE_KEY, dLResult.expirationDate);
            jSONObject2.put(AppConstants.RESPONSE_PARSER_VERSION_KEY, dLParser.getVersion());
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, AppConstants.RESPONSE_ID_SCAN_FLAG);
            jSONObject.put(AppConstants.RESPONSE_USER_INFO_KEY, jSONObject2);
            this.logger.appendLog("Parsed Data : " + jSONObject);
            this.mListener.dataFoundListener(jSONObject);
            disconnectIDSCan();
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.appendLog("ParserException() : " + e.getMessage());
            this.mListener.dataFoundListener(null);
        }
    }

    public void disconnectIDSCan() {
        this.logger.appendLog("disconnectIDSCan() : called");
        FTDIDevice fTDIDevice = this.scannerDevice;
        if (fTDIDevice != null) {
            fTDIDevice.disconnect();
            this.logger.appendLog("disconnectIDSCan() => : " + this.scannerDevice.getName() + " disconnected");
            this.scannerDevice = null;
        }
        FTDIMgr fTDIMgr = this.ftdiMgr;
        if (fTDIMgr != null) {
            fTDIMgr.disable();
            this.logger.appendLog("disconnectIDSCan() => : " + this.ftdiMgr + " : disabled");
        }
    }
}

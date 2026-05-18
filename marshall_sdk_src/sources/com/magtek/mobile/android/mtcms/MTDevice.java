package com.magtek.mobile.android.mtcms;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MTDevice {
    private static String SDK_VERSION = "100.01";
    public static final int SEND_BUSY = 15;
    public static final int SEND_ERROR = 9;
    public static final int SEND_SUCCESS = 0;
    private static final String TAG = "MTDevice";
    private String m_address;
    private Context m_appContext;
    private Handler m_cmsEventHandler;
    private MTConnectionType m_connectionType;
    private IMTService m_service;
    private Handler m_deviceEventHandler = new Handler(new DeviceEventHandler(this, null));
    private MTCMSDevice m_device = new MTCMSDevice();

    private class DeviceEventHandler implements Handler.Callback {
        private DeviceEventHandler() {
        }

        /* synthetic */ DeviceEventHandler(MTDevice mTDevice, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 0) {
                    MTDevice.this.OnConnectionState((MTConnectionState) message.obj);
                } else if (i == 1) {
                    MTDevice.this.OnDeviceResponse((byte[]) message.obj);
                }
            } catch (Exception unused) {
            }
            return true;
        }
    }

    protected void OnConnectionState(MTConnectionState mTConnectionState) {
        String str = TAG;
        Log.i(str, "OnConnectionState");
        if (this.m_cmsEventHandler != null) {
            Message message = new Message();
            message.what = 0;
            message.obj = mTConnectionState;
            this.m_cmsEventHandler.sendMessage(message);
            if (mTConnectionState == MTConnectionState.Disconnected) {
                Log.i(str, "OnConnectionState Disconnected");
            }
        }
    }

    protected void OnDeviceResponse(byte[] bArr) {
        Log.i(TAG, "OnDeviceResponse");
        SetDeviceResponse(bArr);
    }

    protected void SetDeviceResponse(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        if (this.m_cmsEventHandler != null) {
            Message message = new Message();
            message.what = 2;
            message.obj = bArr;
            this.m_cmsEventHandler.sendMessage(message);
        }
        if (this.m_cmsEventHandler != null) {
            Message message2 = new Message();
            message2.what = 1;
            message2.obj = MTParser.getHexString(bArr);
            this.m_cmsEventHandler.sendMessage(message2);
        }
        processDeviceResponseDataStream(bArr);
    }

    public MTDevice(Context context, Handler handler) {
        this.m_appContext = context;
        this.m_cmsEventHandler = handler;
    }

    protected void finalize() {
        Log.i(TAG, "**** finalize");
    }

    public void setConnectionType(MTConnectionType mTConnectionType) {
        this.m_connectionType = mTConnectionType;
    }

    public void setAddress(String str) {
        this.m_address = str;
    }

    public void openDevice() {
        Log.i(TAG, "openDevice:ConnectionType:" + this.m_connectionType);
        this.m_service = null;
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionType[this.m_connectionType.ordinal()];
        if (i == 1) {
            createUSBService();
        } else {
            if (i != 2) {
                return;
            }
            createTCPService();
        }
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtcms.MTDevice$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionType;

        static {
            int[] iArr = new int[MTConnectionType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionType = iArr;
            try {
                iArr[MTConnectionType.USB.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionType[MTConnectionType.IP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    protected void createUSBService() {
        IMTService iMTService;
        if (this.m_service != null) {
            Log.i(TAG, "*** Service is not NULL [" + toString() + "]");
            this.m_service = null;
        }
        MTUSBService mTUSBService = new MTUSBService();
        this.m_service = mTUSBService;
        mTUSBService.setAddress(this.m_address);
        MTCMSDevice mTCMSDevice = this.m_device;
        if (mTCMSDevice == null || (iMTService = this.m_service) == null) {
            return;
        }
        mTCMSDevice.initialize(this.m_appContext, this.m_deviceEventHandler, iMTService);
        this.m_device.open();
    }

    protected void createTCPService() {
        IMTService iMTService;
        if (this.m_service != null) {
            Log.i(TAG, "*** Service is not NULL [" + toString() + "]");
            this.m_service = null;
        }
        MTTCPService mTTCPService = new MTTCPService();
        this.m_service = mTTCPService;
        mTTCPService.setAddress(this.m_address);
        MTCMSDevice mTCMSDevice = this.m_device;
        if (mTCMSDevice == null || (iMTService = this.m_service) == null) {
            return;
        }
        mTCMSDevice.initialize(this.m_appContext, this.m_deviceEventHandler, iMTService);
        this.m_device.open();
    }

    public void closeDevice() {
        Log.i(TAG, "MTDevice closeDevice");
        MTCMSDevice mTCMSDevice = this.m_device;
        if (mTCMSDevice != null) {
            mTCMSDevice.close();
            this.m_service = null;
        }
    }

    public boolean isDeviceConnected() {
        IMTService iMTService = this.m_service;
        return iMTService != null && iMTService.getState() == MTServiceState.Connected;
    }

    public int sendDataString(String str) {
        if (str == null) {
            return 9;
        }
        try {
            byte[] byteArrayFromHexString = MTParser.getByteArrayFromHexString(str);
            if (byteArrayFromHexString != null) {
                return sendDataBytes(byteArrayFromHexString);
            }
            return 9;
        } catch (Exception unused) {
            return 9;
        }
    }

    public int sendDataBytes(byte[] bArr) {
        return (isDeviceConnected() && this.m_device.sendCommand(bArr)) ? 0 : 9;
    }

    public int sendMTCMSMessage(MTCMSMessage mTCMSMessage) {
        if (mTCMSMessage == null) {
            return 9;
        }
        try {
            byte[] messageBytes = mTCMSMessage.getMessageBytes();
            if (messageBytes != null) {
                return sendDataBytes(messageBytes);
            }
            return 9;
        } catch (Exception unused) {
            return 9;
        }
    }

    protected void processDeviceResponseData(byte[] bArr) {
        MTCMSMessage mTCMSMessage = new MTCMSMessage(bArr);
        if (mTCMSMessage.getMessageType() == 2) {
            if (this.m_cmsEventHandler != null) {
                MTCMSResponseMessage mTCMSResponseMessage = new MTCMSResponseMessage(mTCMSMessage.getApplicationID(), mTCMSMessage.getCommandID(), mTCMSMessage.getResultCode(), mTCMSMessage.getDataTag(), mTCMSMessage.getData());
                Message message = new Message();
                message.what = 3;
                message.obj = mTCMSResponseMessage;
                this.m_cmsEventHandler.sendMessage(message);
                return;
            }
            return;
        }
        if (mTCMSMessage.getMessageType() != 3 || this.m_cmsEventHandler == null) {
            return;
        }
        MTCMSNotificationMessage mTCMSNotificationMessage = new MTCMSNotificationMessage(mTCMSMessage.getApplicationID(), mTCMSMessage.getCommandID(), mTCMSMessage.getResultCode(), mTCMSMessage.getDataTag(), mTCMSMessage.getData());
        Message message2 = new Message();
        message2.what = 4;
        message2.obj = mTCMSNotificationMessage;
        this.m_cmsEventHandler.sendMessage(message2);
    }

    protected int getFirstByteValue(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return 0;
        }
        return bArr[0] & (-1);
    }

    protected void processDeviceResponseDataStream(byte[] bArr) {
        List<MTTLVObject> tLVByteArray;
        byte[] tagByteArray;
        if (bArr == null || (tLVByteArray = MTTLVParser.parseTLVByteArray(bArr, false)) == null) {
            return;
        }
        byte[] valueByteArray = null;
        int i = 0;
        int firstByteValue = 0;
        int firstByteValue2 = 0;
        int firstByteValue3 = 0;
        int firstByteValue4 = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < tLVByteArray.size(); i3++) {
            MTTLVObject mTTLVObject = tLVByteArray.get(i3);
            if (mTTLVObject != null && (tagByteArray = mTTLVObject.getTagByteArray()) != null && tagByteArray.length == 1) {
                byte b = tagByteArray[0];
                if (b != -32) {
                    switch (b) {
                        case -64:
                            if (i > 0) {
                                processMTCMSMessage(new MTCMSMessage(firstByteValue, firstByteValue2, firstByteValue3, firstByteValue4, i2, valueByteArray));
                            }
                            firstByteValue = getFirstByteValue(mTTLVObject.getValueByteArray());
                            i++;
                            valueByteArray = null;
                            firstByteValue2 = 0;
                            firstByteValue3 = 0;
                            firstByteValue4 = 0;
                            i2 = 0;
                            break;
                        case -63:
                            firstByteValue2 = getFirstByteValue(mTTLVObject.getValueByteArray());
                            break;
                        case -62:
                            firstByteValue3 = getFirstByteValue(mTTLVObject.getValueByteArray());
                            break;
                        case -61:
                            firstByteValue4 = getFirstByteValue(mTTLVObject.getValueByteArray());
                            break;
                        case -60:
                            i2 = 196;
                            valueByteArray = mTTLVObject.getValueByteArray();
                            break;
                    }
                } else {
                    i2 = DataTypeTag.CONSTRUCTIVE;
                    valueByteArray = mTTLVObject.getValueByteArray();
                }
            }
        }
        if (i > 0) {
            processMTCMSMessage(new MTCMSMessage(firstByteValue, firstByteValue2, firstByteValue3, firstByteValue4, i2, valueByteArray));
        }
    }

    protected void processMTCMSMessage(MTCMSMessage mTCMSMessage) {
        if (mTCMSMessage != null) {
            if (mTCMSMessage.getMessageType() == 2) {
                if (this.m_cmsEventHandler != null) {
                    MTCMSResponseMessage mTCMSResponseMessage = new MTCMSResponseMessage(mTCMSMessage.getApplicationID(), mTCMSMessage.getCommandID(), mTCMSMessage.getResultCode(), mTCMSMessage.getDataTag(), mTCMSMessage.getData());
                    Message message = new Message();
                    message.what = 3;
                    message.obj = mTCMSResponseMessage;
                    this.m_cmsEventHandler.sendMessage(message);
                    return;
                }
                return;
            }
            if (mTCMSMessage.getMessageType() != 3 || this.m_cmsEventHandler == null) {
                return;
            }
            MTCMSNotificationMessage mTCMSNotificationMessage = new MTCMSNotificationMessage(mTCMSMessage.getApplicationID(), mTCMSMessage.getCommandID(), mTCMSMessage.getResultCode(), mTCMSMessage.getDataTag(), mTCMSMessage.getData());
            Message message2 = new Message();
            message2.what = 4;
            message2.obj = mTCMSNotificationMessage;
            this.m_cmsEventHandler.sendMessage(message2);
        }
    }
}

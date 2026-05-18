package com.magtek.mobile.android.mtusdk.mmx;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.magtek.mobile.android.mtusdk.common.TLVParser;

/* JADX INFO: loaded from: classes.dex */
public class MMXDevice implements IMMXServiceAdapter {
    private static final String TAG = "MMXDevice";
    protected Context mContext;
    private IMMXDeviceAdapter mDeviceAdapter;
    private Handler mProgressHandler;
    protected Object mResponseEvent;
    protected IMMXService mService;
    protected MMXConnectionType mConnectionType = MMXConnectionType.USB;
    protected String mAddress = "";
    protected byte[] mResponseBytes = null;

    public MMXDevice(Context context, IMMXDeviceAdapter iMMXDeviceAdapter) {
        this.mContext = context;
        this.mDeviceAdapter = iMMXDeviceAdapter;
    }

    public void setConnectionType(MMXConnectionType mMXConnectionType) {
        this.mConnectionType = mMXConnectionType;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.mmx.MMXDevice$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionType;

        static {
            int[] iArr = new int[MMXConnectionType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionType = iArr;
            try {
                iArr[MMXConnectionType.USB.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionType[MMXConnectionType.BLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionType[MMXConnectionType.WEBSOCKET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionType[MMXConnectionType.SERIAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void createService() {
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionType[this.mConnectionType.ordinal()];
        if (i == 1) {
            this.mService = new MMXUSBService();
        } else if (i == 3) {
            this.mService = new MMXWebSocketService();
        } else if (i == 4) {
            this.mService = new MMXSerialService();
        }
        IMMXService iMMXService = this.mService;
        if (iMMXService != null) {
            iMMXService.initialize(this.mContext, this);
        }
    }

    public void open() {
        try {
            if (this.mService == null) {
                createService();
            }
            IMMXService iMMXService = this.mService;
            if (iMMXService != null) {
                iMMXService.setAddress(this.mAddress);
                this.mService.connect();
            }
        } catch (Exception unused) {
        }
    }

    public void close() {
        try {
            IMMXService iMMXService = this.mService;
            if (iMMXService != null) {
                iMMXService.disconnect();
                this.mService = null;
            }
        } catch (Exception unused) {
        }
    }

    public void sendMessage(MMXMessage mMXMessage) {
        if (mMXMessage != null) {
            try {
                byte[] data = mMXMessage.getData();
                if (this.mService != null) {
                    String hexString = TLVParser.getHexString(data);
                    Log.d(TAG, "sendMessage: " + hexString);
                    this.mProgressHandler = null;
                    this.mService.sendData(data);
                }
            } catch (Exception unused) {
            }
        }
    }

    public void sendMessageWithProgress(MMXMessage mMXMessage, Handler handler) {
        if (mMXMessage != null) {
            try {
                byte[] data = mMXMessage.getData();
                String hexString = TLVParser.getHexString(data);
                Log.d(TAG, "sendMessageWithProgress: " + hexString);
                IMMXService iMMXService = this.mService;
                if (iMMXService != null) {
                    this.mProgressHandler = handler;
                    iMMXService.sendData(data);
                }
            } catch (Exception unused) {
            }
        }
    }

    public byte[] sendAndReceive(byte[] bArr, int i, Handler handler) {
        byte[] bArr2;
        if (bArr == null) {
            return null;
        }
        this.mResponseEvent = new Object();
        if (this.mService != null) {
            String hexString = TLVParser.getHexString(bArr);
            Log.d(TAG, "sendAndReceive: " + hexString);
            this.mProgressHandler = handler;
            this.mService.sendData(bArr);
            synchronized (this.mResponseEvent) {
                try {
                    this.mResponseEvent.wait(i);
                    bArr2 = this.mResponseBytes;
                } catch (Exception unused) {
                    bArr2 = null;
                }
            }
        } else {
            bArr2 = null;
        }
        this.mResponseEvent = null;
        return bArr2;
    }

    public boolean isConnected() {
        IMMXService iMMXService = this.mService;
        return iMMXService != null && iMMXService.getState() == MMXConnectionState.Connected;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXServiceAdapter
    public void OnConnectionStateChanged(MMXConnectionState mMXConnectionState) {
        IMMXDeviceAdapter iMMXDeviceAdapter = this.mDeviceAdapter;
        if (iMMXDeviceAdapter != null) {
            try {
                iMMXDeviceAdapter.OnMessage(0, -1, -1, mMXConnectionState);
            } catch (Exception e) {
                Log.d(TAG, " OnConnectionStateChanged Exception: " + e.getMessage());
            }
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXServiceAdapter
    public void OnDataReceived(byte[] bArr) {
        try {
            Object obj = this.mResponseEvent;
            if (obj != null) {
                synchronized (obj) {
                    String hexString = TLVParser.getHexString(bArr);
                    Log.d(TAG, "OnDataReceived: " + hexString);
                    this.mResponseBytes = bArr;
                    this.mResponseEvent.notifyAll();
                }
            } else {
                this.mResponseBytes = null;
            }
            IMMXDeviceAdapter iMMXDeviceAdapter = this.mDeviceAdapter;
            if (iMMXDeviceAdapter != null) {
                iMMXDeviceAdapter.OnMessage(1, -1, -1, bArr);
            }
        } catch (Exception e) {
            Log.d(TAG, " OnDataReceived Exception: " + e.getMessage());
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXServiceAdapter
    public void OnSendDataProgress(int i) {
        if (this.mProgressHandler != null) {
            try {
                Log.d(TAG, "SendDataProgress: " + i);
                Message message = new Message();
                message.what = i;
                this.mProgressHandler.sendMessage(message);
            } catch (Exception e) {
                Log.d(TAG, " OnSendDataProgress Exception: " + e.getMessage());
            }
        }
    }
}

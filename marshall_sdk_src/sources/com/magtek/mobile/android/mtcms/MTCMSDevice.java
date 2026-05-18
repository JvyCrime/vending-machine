package com.magtek.mobile.android.mtcms;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class MTCMSDevice implements MTServiceAdapter {
    private static final String TAG = "MTCMSDevice";
    private MTConnectionState m_connectionState;
    private Context m_context;
    private Handler m_deviceEventHandler;
    private IMTService m_service;

    @Override // com.magtek.mobile.android.mtcms.MTServiceAdapter
    public void OnServiceState(MTServiceState mTServiceState) {
        setConnectionState(getConnectionState(mTServiceState));
    }

    @Override // com.magtek.mobile.android.mtcms.MTServiceAdapter
    public void OnDeviceData(byte[] bArr) {
        Log.i(TAG, "OnDeviceData");
        if (this.m_deviceEventHandler != null) {
            Message message = new Message();
            message.what = 1;
            message.obj = bArr;
            this.m_deviceEventHandler.sendMessage(message);
        }
    }

    private MTConnectionState getConnectionState(MTServiceState mTServiceState) {
        MTConnectionState mTConnectionState = MTConnectionState.Error;
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtcms$MTServiceState[mTServiceState.ordinal()];
        if (i == 1) {
            return MTConnectionState.Disconnected;
        }
        if (i == 2) {
            return MTConnectionState.Connected;
        }
        if (i != 3) {
            return i != 4 ? mTConnectionState : MTConnectionState.Disconnecting;
        }
        return MTConnectionState.Connecting;
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtcms.MTCMSDevice$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtcms$MTServiceState;

        static {
            int[] iArr = new int[MTServiceState.values().length];
            $SwitchMap$com$magtek$mobile$android$mtcms$MTServiceState = iArr;
            try {
                iArr[MTServiceState.Disconnected.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTServiceState[MTServiceState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTServiceState[MTServiceState.Connecting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTServiceState[MTServiceState.Disconnecting.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void setConnectionState(MTConnectionState mTConnectionState) {
        if (mTConnectionState == this.m_connectionState) {
            return;
        }
        this.m_connectionState = mTConnectionState;
        if (this.m_deviceEventHandler != null) {
            Message message = new Message();
            message.what = 0;
            message.obj = mTConnectionState;
            this.m_deviceEventHandler.sendMessage(message);
        }
    }

    public boolean initialize(Context context, Handler handler, IMTService iMTService) {
        this.m_context = context;
        this.m_deviceEventHandler = handler;
        this.m_service = iMTService;
        this.m_connectionState = MTConnectionState.Disconnected;
        return true;
    }

    public boolean open() {
        try {
            if (this.m_service == null || this.m_connectionState != MTConnectionState.Disconnected) {
                return false;
            }
            Log.i(TAG, "Device Is Disconnected");
            this.m_service.initialize(this.m_context, this);
            this.m_service.connect();
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public void close() {
        try {
            Log.i(TAG, "MTCMSDevice Close");
            IMTService iMTService = this.m_service;
            if (iMTService != null) {
                iMTService.disconnect();
            }
        } catch (Exception unused) {
        }
    }

    public boolean sendCommand(byte[] bArr) {
        try {
            IMTService iMTService = this.m_service;
            if (iMTService == null) {
                return false;
            }
            iMTService.sendData(bArr);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}

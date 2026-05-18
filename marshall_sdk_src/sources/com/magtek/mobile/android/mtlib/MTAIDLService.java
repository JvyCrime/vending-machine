package com.magtek.mobile.android.mtlib;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.magtek.mobile.android.mtservice.IDevice;
import com.magtek.mobile.android.mtservice.IDeviceCallback;

/* JADX INFO: loaded from: classes.dex */
public class MTAIDLService extends MTBaseService implements ServiceConnection {
    protected IDevice m_device;
    protected IDeviceCallback m_deviceCallback = new IDeviceCallback.Stub() { // from class: com.magtek.mobile.android.mtlib.MTAIDLService.1
        @Override // com.magtek.mobile.android.mtservice.IDeviceCallback
        public void onResponseReceived(byte[] bArr) {
            if (MTAIDLService.this.m_serviceAdapter != null) {
                MTAIDLService.this.m_serviceAdapter.OnCommandData(bArr);
            }
        }

        @Override // com.magtek.mobile.android.mtservice.IDeviceCallback
        public void onNotificationReceived(byte[] bArr) {
            if (MTAIDLService.this.m_serviceAdapter != null) {
                MTAIDLService.this.m_serviceAdapter.OnDeviceData(bArr);
            }
        }
    };

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDeviceName() {
        return "DynaGlass";
    }

    protected void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.imatric.sdk.service");
        intent.setAction("com.imatric.sdk.service.COMMAND_SERVICE");
        this.m_context.bindService(intent, this, 1);
    }

    protected void unbindService() {
        this.m_context.unbindService(this);
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        IDevice iDeviceAsInterface = IDevice.Stub.asInterface(iBinder);
        this.m_device = iDeviceAsInterface;
        if (iDeviceAsInterface != null) {
            try {
                iDeviceAsInterface.setCallback(this.m_deviceCallback);
            } catch (Exception unused) {
            }
            setState(MTServiceState.Connected);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        setState(MTServiceState.Disconnected);
        this.m_device = null;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public MTDeviceFeatures getDeviceFeatures() {
        MTDeviceFeatures mTDeviceFeatures = new MTDeviceFeatures();
        mTDeviceFeatures.MSR = true;
        mTDeviceFeatures.Contact = true;
        mTDeviceFeatures.Contactless = true;
        mTDeviceFeatures.BatteryBackedClock = true;
        mTDeviceFeatures.MSRPowerSaver = false;
        return mTDeviceFeatures;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void connect() {
        setState(MTServiceState.Connecting);
        bindService();
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void disconnect() {
        setState(MTServiceState.Disconnecting);
        unbindService();
        setState(MTServiceState.Disconnected);
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean sendData(byte[] bArr) {
        IDevice iDevice = this.m_device;
        if (iDevice != null) {
            try {
                iDevice.send(bArr);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public long getBatteryLevel() {
        return MTDeviceConstants.BATTERY_LEVEL_MAX;
    }
}

package com.magtek.mobile.android.mtusdk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.magtek.mobile.android.mtlib.MTDeviceConstants;
import com.magtek.mobile.android.mtusdk.ppscra.PPSCRAAPI;
import com.magtek.mobile.android.mtusdk.scra.SCRAAPI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class DeviceScanningThread extends Thread {
    private Context mContext;
    private List<IDevice> mDeviceList;
    private IDeviceListCallback mDeviceListCallback;
    private List<DeviceType> mDeviceTypes;
    private Handler mHandler;
    private List<BluetoothDevice> mBluetoothDevices = new ArrayList();
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() { // from class: com.magtek.mobile.android.mtusdk.DeviceScanningThread.2
        @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            if (DeviceScanningThread.this.mBluetoothDevices.contains(bluetoothDevice) || bArr == null) {
                return;
            }
            ListIterator listIterator = DeviceScanningThread.parseUUIDs(bArr).listIterator();
            while (listIterator.hasNext()) {
                UUID uuid = (UUID) listIterator.next();
                if (uuid.compareTo(MTDeviceConstants.UUID_SCRA_BLE_EMV_DEVICE_READER_SERVICE) == 0) {
                    if (DeviceScanningThread.this.deviceTypeRequested(DeviceType.SCRA)) {
                        DeviceScanningThread.this.mDeviceList.add(SCRAAPI.createDevice(DeviceScanningThread.this.mContext, ConnectionType.BLUETOOTH_LE_EMV, bluetoothDevice.getAddress(), "SCRA", bluetoothDevice.getName(), ""));
                        DeviceScanningThread.this.mBluetoothDevices.add(bluetoothDevice);
                    }
                } else if (uuid.compareTo(MTDeviceConstants.UUID_SCRA_BLE_EMV_T_DEVICE_READER_SERVICE) == 0) {
                    if (DeviceScanningThread.this.deviceTypeRequested(DeviceType.SCRA)) {
                        DeviceScanningThread.this.mDeviceList.add(SCRAAPI.createDevice(DeviceScanningThread.this.mContext, ConnectionType.BLUETOOTH_LE_EMVT, bluetoothDevice.getAddress(), "SCRA", bluetoothDevice.getName(), ""));
                        DeviceScanningThread.this.mBluetoothDevices.add(bluetoothDevice);
                    }
                } else if (uuid.compareTo(MTDeviceConstants.UUID_PPSCRA_BLE_DEVICE_READER_SERVICE) == 0 && DeviceScanningThread.this.deviceTypeRequested(DeviceType.PPSCRA)) {
                    DeviceScanningThread.this.mDeviceList.add(PPSCRAAPI.createDevice(DeviceScanningThread.this.mContext, ConnectionType.BLUETOOTH_LE_EMV, bluetoothDevice.getAddress(), "PPSCRA", bluetoothDevice.getName(), ""));
                    DeviceScanningThread.this.mBluetoothDevices.add(bluetoothDevice);
                }
            }
        }
    };

    public DeviceScanningThread(Context context, List<DeviceType> list, List<IDevice> list2, IDeviceListCallback iDeviceListCallback) {
        this.mContext = null;
        this.mDeviceTypes = null;
        this.mDeviceList = null;
        this.mDeviceListCallback = null;
        this.mContext = context;
        this.mDeviceTypes = list;
        this.mDeviceList = new ArrayList(list2);
        this.mDeviceListCallback = iDeviceListCallback;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        startLeScan();
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        handler.postDelayed(new Runnable() { // from class: com.magtek.mobile.android.mtusdk.DeviceScanningThread.1
            @Override // java.lang.Runnable
            public void run() {
                DeviceScanningThread.this.stopLeScan();
                DeviceScanningThread.this.sendDeviceListEvent();
            }
        }, 3000L);
    }

    private void startLeScan() {
        BluetoothAdapter adapter = ((BluetoothManager) this.mContext.getSystemService("bluetooth")).getAdapter();
        if (adapter != null) {
            adapter.startLeScan(this.mLeScanCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopLeScan() {
        BluetoothAdapter adapter = ((BluetoothManager) this.mContext.getSystemService("bluetooth")).getAdapter();
        if (adapter != null) {
            adapter.stopLeScan(this.mLeScanCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean deviceTypeRequested(DeviceType deviceType) {
        List<DeviceType> list = this.mDeviceTypes;
        if (list == null) {
            return true;
        }
        return list.contains(deviceType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendDeviceListEvent() {
        try {
            IDeviceListCallback iDeviceListCallback = this.mDeviceListCallback;
            if (iDeviceListCallback != null) {
                iDeviceListCallback.OnDeviceList(this.mDeviceList);
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<UUID> parseUUIDs(byte[] bArr) {
        int i;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < bArr.length - 2; i2 = i) {
            int i3 = i2 + 1;
            int i4 = bArr[i2];
            if (i4 == 0) {
                break;
            }
            i = i3 + 1;
            char c = bArr[i3];
            if (c == 6 || c == 7) {
                while (i4 >= 16) {
                    int i5 = i + 1;
                    try {
                        ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr, i, 16).order(ByteOrder.LITTLE_ENDIAN);
                        arrayList.add(new UUID(byteBufferOrder.getLong(), byteBufferOrder.getLong()));
                    } catch (IndexOutOfBoundsException unused) {
                    }
                    i = i5 + 15;
                    i4 -= 16;
                }
            } else {
                i += i4 - 1;
            }
        }
        return arrayList;
    }
}

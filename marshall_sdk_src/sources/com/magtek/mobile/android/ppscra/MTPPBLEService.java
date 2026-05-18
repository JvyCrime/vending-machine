package com.magtek.mobile.android.ppscra;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.digitalmediavending.hardware.utils.AppConstants;
import com.felhr.usbserial.FTDISerialDevice;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
public class MTPPBLEService extends MTPPBaseService {
    public static final long CONNECTING_TIMEOUT = 5000;
    private static final String c = "MTPPBLEService";
    private boolean A;
    private boolean B;
    private boolean C;
    private int D;
    private byte[] E;
    private boolean F;
    private int G;
    private int H;
    private Handler I;
    private Runnable J;
    private BroadcastReceiver K;
    private final BluetoothGattCallback L;
    private Handler M;
    private Timer N;
    private Timer O;
    d a;
    b b;
    private BluetoothAdapter j;
    private BluetoothGatt k;
    private BluetoothManager l;
    private String m;
    private final ReentrantReadWriteLock p;
    private final Lock q;
    private final Lock r;
    private final Lock s;
    private Queue<a> t;
    private Queue<a> u;
    private Queue<c> v;
    private Queue<a> w;
    private Queue<c> x;
    private boolean y;
    private boolean z;
    protected UUID m_serviceUUID = MTPPDeviceConstants.UUID_PPSCRA_BLE_DEVICE_READER_SERVICE;
    private final boolean d = true;
    private final int e = 1;
    private final int f = 2;
    private final int g = 3;
    private final int h = 4;
    private final int i = 5;
    private boolean n = false;
    private byte o = -1;

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public byte[] getDeviceSerialNumber() {
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getFirmwareVersion() {
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getProductID() {
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getProductName() {
        return null;
    }

    public MTPPBLEService() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.p = reentrantReadWriteLock;
        this.q = reentrantReadWriteLock.readLock();
        this.r = reentrantReadWriteLock.writeLock();
        this.s = new ReentrantLock();
        this.t = new LinkedList();
        this.u = new LinkedList();
        this.v = new LinkedList();
        this.w = new LinkedList();
        this.x = new LinkedList();
        this.y = false;
        this.z = false;
        this.A = false;
        this.B = false;
        this.C = false;
        this.D = 0;
        this.F = false;
        this.G = 5000;
        this.H = FTDISerialDevice.FTDI_BAUDRATE_300;
        this.I = new Handler();
        this.J = null;
        this.K = new BroadcastReceiver() { // from class: com.magtek.mobile.android.ppscra.MTPPBLEService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String str = (String) intent.getParcelableExtra("android.bluetooth.device.extra.NAME");
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
                int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
                if (intExtra2 == 11 && intExtra == 12 && MTPPBLEService.this.n) {
                    MTPPBLEService.this.C = true;
                }
                Log.i(MTPPBLEService.c, "Bond state changed for: " + str + ", new state: " + intExtra + " previous: " + intExtra2);
                String str2 = MTPPBLEService.c;
                StringBuilder sb = new StringBuilder();
                sb.append("BroadcastReceiver Bond State = ");
                sb.append(intExtra);
                Log.i(str2, sb.toString());
                if (intExtra == 12) {
                    if (MTPPBLEService.this.z) {
                        MTPPBLEService.this.unregisterReceiver();
                        Log.i(MTPPBLEService.c, "Device has just been paired, disconnect and reconnect...");
                        Message message = new Message();
                        message.what = 3;
                        MTPPBLEService.this.M.sendMessageDelayed(message, 1000L);
                        return;
                    }
                    return;
                }
                if (intExtra == 10) {
                    MTPPBLEService.this.z = false;
                    MTPPBLEService.this.g();
                    if (str == null) {
                        Message message2 = new Message();
                        message2.what = 5;
                        MTPPBLEService.this.M.sendMessageDelayed(message2, AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
                        Log.i(MTPPBLEService.c, "Disconnect...");
                    }
                }
            }
        };
        this.L = new BluetoothGattCallback() { // from class: com.magtek.mobile.android.ppscra.MTPPBLEService.3
            @Override // android.bluetooth.BluetoothGattCallback
            public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
                MTPPBLEService.this.N.cancel();
                try {
                    if (i2 == 2) {
                        Log.i(MTPPBLEService.c, "BluetoothGattCallback::onConnectionStateChange:BluetoothProfile.STATE_CONNECTED");
                        MTPPBLEService.this.k = bluetoothGatt;
                        MTPPBLEService.this.u.clear();
                        MTPPBLEService.this.t.clear();
                        MTPPBLEService.this.w.clear();
                        MTPPBLEService.this.v.clear();
                        MTPPBLEService.this.x.clear();
                        MTPPBLEService.this.E = null;
                        int bondState = bluetoothGatt.getDevice().getBondState();
                        Log.i(MTPPBLEService.c, "GattCallback Bond State = " + bondState);
                        if (bondState == 12) {
                            if (MTPPBLEService.this.O != null) {
                                MTPPBLEService.this.O.cancel();
                            }
                            MTPPBLEService.this.setState(MTServiceState.Connecting);
                            Message message = new Message();
                            message.what = 1;
                            MTPPBLEService.this.M.sendMessageDelayed(message, 200L);
                            return;
                        }
                        if (bondState != 10) {
                            if (MTPPBLEService.this.H <= 0 || MTPPBLEService.this.O == null) {
                            } else {
                                MTPPBLEService.this.O.schedule(MTPPBLEService.this.a, MTPPBLEService.this.H);
                            }
                        } else {
                            MTPPBLEService.this.setState(MTServiceState.Connecting);
                            MTPPBLEService.this.z = true;
                            MTPPBLEService.this.registerReceiver();
                            if (Build.VERSION.SDK_INT >= 19) {
                                if (bluetoothGatt.getDevice().createBond()) {
                                    Log.i(MTPPBLEService.c, "GattCallback Bond Created = true");
                                    Log.i(MTPPBLEService.c, "GattCallback Bond Device Name = " + bluetoothGatt.getDevice().getName());
                                    return;
                                }
                                Log.i(MTPPBLEService.c, "GattCallback Bond Created = false");
                                return;
                            }
                            Message message2 = new Message();
                            message2.what = 1;
                            MTPPBLEService.this.M.sendMessageDelayed(message2, 200L);
                        }
                    } else {
                        if (i2 != 0) {
                            return;
                        }
                        Log.i(MTPPBLEService.c, "BluetoothGattCallback::onConnectionStateChange:BluetoothProfile.STATE_DISCONNECTED");
                        if (MTPPBLEService.this.z) {
                            MTPPBLEService.this.g();
                        } else if (MTPPBLEService.this.m_state == MTServiceState.Connecting) {
                            MTPPBLEService.this.f();
                        }
                        MTPPBLEService.this.M.removeMessages(1);
                        MTPPBLEService.this.M.removeMessages(2);
                        MTPPBLEService.this.M.removeMessages(4);
                        if (MTPPBLEService.this.O != null) {
                            MTPPBLEService.this.O.cancel();
                        }
                        Log.i(MTPPBLEService.c, "Disconnected from GATT server.");
                        if (!MTPPBLEService.this.C) {
                            MTPPBLEService.this.u.clear();
                            MTPPBLEService.this.t.clear();
                            MTPPBLEService.this.w.clear();
                            MTPPBLEService.this.v.clear();
                            MTPPBLEService.this.x.clear();
                            MTPPBLEService.this.unregisterReceiver();
                            Log.i(MTPPBLEService.c, "Disconnected from GATT server.");
                            MTPPBLEService.this.setState(MTServiceState.Disconnected);
                            Log.i(MTPPBLEService.c, "Close Gatt Client");
                            MTPPBLEService.this.k.close();
                            MTPPBLEService.this.k = null;
                            MTPPBLEService.this.a(false);
                            return;
                        }
                        MTPPBLEService.this.unregisterReceiver();
                        Log.i(MTPPBLEService.c, "Close Gatt Client");
                        MTPPBLEService.this.k.close();
                        Log.i(MTPPBLEService.c, "Reconnecting to GATT server...");
                        Message message3 = new Message();
                        message3.what = 4;
                        MTPPBLEService.this.M.sendMessageDelayed(message3, AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
                        if (MTPPBLEService.this.G <= 0 || MTPPBLEService.this.N == null) {
                        } else {
                            MTPPBLEService.this.N.schedule(MTPPBLEService.this.b, MTPPBLEService.this.G);
                        }
                    }
                } catch (Exception unused) {
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                Log.i(MTPPBLEService.c, "onServicesDiscovered Status=" + i);
                if (i == 0) {
                    MTPPBLEService.this.N.cancel();
                    int bondState = bluetoothGatt.getDevice().getBondState();
                    Log.i(MTPPBLEService.c, "ServicesDiscovered Bond State = " + bondState);
                    if (bondState == 12) {
                        MTPPBLEService.this.A = false;
                        MTPPBLEService.this.setNotification(true);
                        return;
                    } else {
                        if (bondState == 10 && MTPPBLEService.this.z) {
                            MTPPBLEService.this.setNotification(true);
                            return;
                        }
                        return;
                    }
                }
                Log.i(MTPPBLEService.c, "onServicesDiscovered received: " + i);
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                Log.i(MTPPBLEService.c, "MTPPBLEService onCharacteristicChanged " + bluetoothGattCharacteristic.getUuid().toString() + " [" + toString() + "]");
                if (MTPPDeviceConstants.UUID_PPSCRA_BLE_DEVICE_NOTIFY_LEN.equals(bluetoothGattCharacteristic.getUuid())) {
                    Log.i(MTPPBLEService.c, "MTPPBLEService onCharacteristicChanged UUID_SCRA_BLE_DEVICE_NOTIFY_DATA");
                    byte[] value = bluetoothGattCharacteristic.getValue();
                    if (value.length > 0) {
                        MTPPBLEService.this.OnNotificationReceived(value);
                    }
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) throws Throwable {
                boolean z;
                a aVar;
                String str = MTPPBLEService.c;
                StringBuilder sb = new StringBuilder();
                sb.append("MTPPBLEService onCharacteristicRead status=0x");
                boolean z2 = true;
                sb.append(String.format("%02X", Integer.valueOf(i)));
                sb.append(", UUID=");
                sb.append(bluetoothGattCharacteristic.getUuid().toString());
                Log.i(str, sb.toString());
                if (i != 0) {
                    Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead status != 0");
                }
                MTPPBLEService.this.r.lock();
                try {
                    if (MTPPBLEService.this.w.size() <= 0 || (aVar = (a) MTPPBLEService.this.w.peek()) == null || aVar.b || aVar.c.getUuid() != bluetoothGattCharacteristic.getUuid()) {
                        z = true;
                    } else {
                        Log.i(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead UUID=" + bluetoothGattCharacteristic.getUuid().toString() + " was read successfully");
                        MTPPBLEService.this.w.remove();
                        MTPPBLEService.this.r.unlock();
                        try {
                            MTPPBLEService.this.c();
                            z = false;
                        } catch (Throwable th) {
                            th = th;
                            z2 = false;
                            if (z2) {
                                MTPPBLEService.this.r.unlock();
                            }
                            throw th;
                        }
                    }
                    if (z) {
                        MTPPBLEService.this.r.unlock();
                    }
                    Log.i(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead, UUID=" + bluetoothGattCharacteristic.getUuid().toString());
                    Log.i(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead, len=0x" + String.format("%02X", Integer.valueOf(bluetoothGattCharacteristic.getValue().length)));
                    Log.i(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead, Data:\n" + PPSCRACommon.getHexString(bluetoothGattCharacteristic.getValue()) + "\n");
                    try {
                        if (i == 0) {
                            Log.i(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_SUCCESS");
                            MTPPBLEService.this.e(bluetoothGattCharacteristic);
                        } else if (i == 13) {
                            Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_INVALID_ATTRIBUTE_LENGTH");
                        } else if (i == 15) {
                            Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_INSUFFICIENT_ENCRYPTION");
                        } else if (i == 257) {
                            Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_FAILURE");
                        } else if (i == 2) {
                            Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_READ_NOT_PERMITTED");
                        } else if (i == 3) {
                            Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_WRITE_NOT_PERMITTED");
                        } else if (i == 5) {
                            Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_INSUFFICIENT_AUTHENTICATION");
                        } else if (i == 6) {
                            Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_REQUEST_NOT_SUPPORTED");
                        } else if (i != 7) {
                        } else {
                            Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead GATT_INVALID_OFFSET");
                        }
                    } catch (Exception e2) {
                        Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicRead Exception Caught: " + e2.toString());
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) throws Throwable {
                a aVar;
                String str = MTPPBLEService.c;
                StringBuilder sb = new StringBuilder();
                sb.append("MTPPBLEService onCharacteristicWrite status=0x");
                boolean z = true;
                sb.append(String.format("%02X", Integer.valueOf(i)));
                sb.append(", UUID=");
                sb.append(bluetoothGattCharacteristic.getUuid().toString());
                Log.i(str, sb.toString());
                if (i != 0) {
                    Log.w(MTPPBLEService.c, "MTPPBLEService onCharacteristicWrite status != 0");
                }
                MTPPBLEService.this.r.lock();
                try {
                    if (MTPPBLEService.this.w.size() > 0 && (aVar = (a) MTPPBLEService.this.w.peek()) != null && aVar.b && aVar.c.getUuid() == bluetoothGattCharacteristic.getUuid()) {
                        Log.i(MTPPBLEService.c, "MTPPBLEService onCharacteristicWrite UUID=" + bluetoothGattCharacteristic.getUuid().toString() + " was written successfully");
                        MTPPBLEService.this.w.remove();
                        MTPPBLEService.this.r.unlock();
                        try {
                            MTPPBLEService.this.c();
                            z = false;
                        } catch (Throwable th) {
                            th = th;
                            z = false;
                            if (z) {
                                MTPPBLEService.this.r.unlock();
                            }
                            throw th;
                        }
                    }
                    if (z) {
                        MTPPBLEService.this.r.unlock();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) throws Throwable {
                String str = MTPPBLEService.c;
                StringBuilder sb = new StringBuilder();
                sb.append("MTPPBLEService onDescriptorRead status=0x");
                boolean z = true;
                sb.append(String.format("%02X", Integer.valueOf(i)));
                Log.i(str, sb.toString());
                if ((i & 5) == 5) {
                    Log.i(MTPPBLEService.c, "MTPPBLEService onDescriptorRead GATT_INSUFFICIENT_AUTHENTICATION");
                    Log.i(MTPPBLEService.c, "MTPPBLEService onDescriptorRead BondState=0x" + String.format("%04X", Integer.valueOf(bluetoothGatt.getDevice().getBondState())));
                }
                MTPPBLEService.this.r.lock();
                try {
                    if (MTPPBLEService.this.x.size() > 0) {
                        c cVar = (c) MTPPBLEService.this.x.peek();
                        if (!cVar.b && cVar.c.getUuid() == bluetoothGattDescriptor.getUuid()) {
                            Log.i(MTPPBLEService.c, "MTPPBLEService onDescriptorRead UUID=" + bluetoothGattDescriptor.getUuid().toString() + " was read successfully");
                            MTPPBLEService.this.x.remove();
                            MTPPBLEService.this.r.unlock();
                            try {
                                MTPPBLEService.this.c();
                                z = false;
                            } catch (Throwable th) {
                                th = th;
                                z = false;
                                if (z) {
                                    MTPPBLEService.this.r.unlock();
                                }
                                throw th;
                            }
                        }
                    }
                    if (z) {
                        MTPPBLEService.this.r.unlock();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) throws Throwable {
                String str = MTPPBLEService.c;
                StringBuilder sb = new StringBuilder();
                sb.append("MTPPBLEService onDescriptorWrite status=0x");
                boolean z = true;
                sb.append(String.format("%02X", Integer.valueOf(i)));
                Log.i(str, sb.toString());
                if (i == 0) {
                    if (!MTPPBLEService.this.z) {
                        if (!MTPPBLEService.this.n || !MTPPBLEService.this.h()) {
                            Log.i(MTPPBLEService.c, "MTPPBLEService onDescriptorWrite SetState to CONNECTED");
                            MTPPBLEService.this.setState(MTServiceState.Connected);
                            MTPPBLEService.this.o = (byte) -1;
                        } else {
                            MTPPBLEService.this.setState(MTServiceState.Disconnecting);
                            Message message = new Message();
                            message.what = 5;
                            MTPPBLEService.this.M.sendMessageDelayed(message, 1000L);
                        }
                        MTPPBLEService.this.C = false;
                        if (MTPPBLEService.this.h()) {
                            MTPPBLEService.this.a(false);
                            MTPPBLEService.this.disconnectGatt();
                        }
                    }
                } else if (i == 5) {
                    Log.i(MTPPBLEService.c, "MTPPBLEService onDescriptorWrite GATT_INSUFFICIENT_AUTHENTICATION");
                }
                MTPPBLEService.this.r.lock();
                try {
                    if (MTPPBLEService.this.x.size() > 0) {
                        c cVar = (c) MTPPBLEService.this.x.peek();
                        if (cVar.b && cVar.c.getUuid() == bluetoothGattDescriptor.getUuid()) {
                            MTPPBLEService.this.x.remove();
                            MTPPBLEService.this.r.unlock();
                            try {
                                MTPPBLEService.this.c();
                                z = false;
                            } catch (Throwable th) {
                                th = th;
                                z = false;
                                if (z) {
                                    MTPPBLEService.this.r.unlock();
                                }
                                throw th;
                            }
                        }
                    }
                    if (z) {
                        MTPPBLEService.this.r.unlock();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        };
        this.M = new e();
        this.N = new Timer();
        this.O = new Timer();
        this.a = new d();
        this.b = new b();
    }

    private class a {
        private boolean b;
        private BluetoothGattCharacteristic c;

        private a() {
        }
    }

    private class c {
        private boolean b;
        private BluetoothGattDescriptor c;

        private c() {
        }
    }

    protected void StartConnectingTimeout() {
        Runnable runnable = this.J;
        if (runnable != null) {
            this.I.removeCallbacks(runnable);
            this.J = null;
        }
        Runnable runnable2 = new Runnable() { // from class: com.magtek.mobile.android.ppscra.MTPPBLEService.1
            @Override // java.lang.Runnable
            public void run() {
                if (MTPPBLEService.this.m_state == MTServiceState.Connecting) {
                    MTPPBLEService.this.disconnectGatt();
                }
            }
        };
        this.J = runnable2;
        this.I.postDelayed(runnable2, 5000L);
    }

    protected void StopConnectingTimeout() {
        Runnable runnable = this.J;
        if (runnable != null) {
            this.I.removeCallbacks(runnable);
            this.J = null;
        }
    }

    private BluetoothGattService b() {
        BluetoothGatt bluetoothGatt = this.k;
        if (bluetoothGatt != null) {
            return bluetoothGatt.getService(this.m_serviceUUID);
        }
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService
    protected void setState(MTServiceState mTServiceState) {
        if (mTServiceState != MTServiceState.Connecting) {
            StopConnectingTimeout();
        }
        super.setState(mTServiceState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (d()) {
            return;
        }
        e();
    }

    private boolean d() {
        boolean zA;
        this.r.lock();
        try {
            int size = this.x.size() + this.w.size();
            int size2 = this.v.size();
            String str = c;
            Log.i(str, "BLEService processDescriptorActionsQueue sentSize=" + size);
            Log.i(str, "BLEService processDescriptorActionsQueue waitingSize=" + size2);
            if (size != 0 || size2 <= 0) {
                zA = false;
            } else {
                c cVarPeek = this.v.peek();
                if (cVarPeek.b) {
                    Log.i(str, "BLEService processDescriptorActionsQueue writing UUID=" + cVarPeek.c.getUuid().toString());
                    zA = b(cVarPeek.c);
                } else {
                    Log.i(str, "BLEService processDescriptorActionsQueue reading UUID=" + cVarPeek.c.getUuid().toString());
                    zA = a(cVarPeek.c);
                }
                if (zA) {
                    this.x.add(this.v.remove());
                }
            }
            return zA;
        } finally {
            this.r.unlock();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean e() {
        /*
            Method dump skipped, instruction units count: 331
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.ppscra.MTPPBLEService.e():boolean");
    }

    private boolean a(BluetoothGattDescriptor bluetoothGattDescriptor) {
        if (this.j == null || this.k == null) {
            Log.w(c, "BluetoothAdapter not initialized");
            return false;
        }
        String str = c;
        Log.i(str, "BluetoothGatt.readDescriptorFromBLE");
        boolean descriptor = this.k.readDescriptor(bluetoothGattDescriptor);
        if (!descriptor) {
            Log.w(str, "*** BluetoothGatt.readDescriptorFromBLE failed");
        }
        return descriptor;
    }

    private boolean b(BluetoothGattDescriptor bluetoothGattDescriptor) {
        if (this.j == null || this.k == null) {
            Log.w(c, "BluetoothAdapter not initialized");
            return false;
        }
        String str = c;
        Log.i(str, "BluetoothGatt.writeDescriptorToBLE");
        boolean zWriteDescriptor = this.k.writeDescriptor(bluetoothGattDescriptor);
        if (!zWriteDescriptor) {
            Log.w(str, "*** BluetoothGatt.writeDescriptorToBLE failed");
        }
        return zWriteDescriptor;
    }

    private boolean a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.j == null || this.k == null) {
            Log.w(c, "BluetoothAdapter not initialized");
            return false;
        }
        String str = c;
        Log.i(str, "BluetoothGatt.readCharacteristicFromBLE");
        boolean characteristic = this.k.readCharacteristic(bluetoothGattCharacteristic);
        if (!characteristic) {
            Log.w(str, "*** BluetoothGatt.readCharacteristicFromBLE failed");
        }
        return characteristic;
    }

    private boolean b(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.j == null || this.k == null) {
            Log.w(c, "BluetoothAdapter not initialized");
            return false;
        }
        String str = c;
        Log.i(str, "BluetoothGatt.writeCharacteristicToBLE");
        boolean zWriteCharacteristic = this.k.writeCharacteristic(bluetoothGattCharacteristic);
        if (!zWriteCharacteristic) {
            Log.w(str, "*** BluetoothGatt.writeCharacteristicToBLE failed");
        }
        return zWriteCharacteristic;
    }

    private void c(BluetoothGattDescriptor bluetoothGattDescriptor) {
        Log.i(c, "BluetoothGatt.writeDescriptor");
        c cVar = new c();
        cVar.b = true;
        cVar.c = bluetoothGattDescriptor;
        this.r.lock();
        try {
            this.v.add(cVar);
            this.r.unlock();
            c();
        } catch (Throwable th) {
            this.r.unlock();
            throw th;
        }
    }

    private void c(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.i(c, "BluetoothGatt.readCharacteristic");
        a aVar = new a();
        aVar.b = false;
        aVar.c = bluetoothGattCharacteristic;
        this.r.lock();
        try {
            this.u.add(aVar);
            this.r.unlock();
            c();
        } catch (Throwable th) {
            this.r.unlock();
            throw th;
        }
    }

    private void d(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.i(c, "BluetoothGatt.writeCharacteristic");
        a aVar = new a();
        aVar.b = true;
        aVar.c = bluetoothGattCharacteristic;
        this.r.lock();
        try {
            this.t.add(aVar);
            this.r.unlock();
            c();
        } catch (Throwable th) {
            this.r.unlock();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        BluetoothGatt bluetoothGatt;
        if (this.j == null || (bluetoothGatt = this.k) == null) {
            Log.i(c, "BluetoothAdapter not initialized");
            return false;
        }
        boolean characteristicNotification = bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, z);
        if (characteristicNotification) {
            List<BluetoothGattDescriptor> descriptors = bluetoothGattCharacteristic.getDescriptors();
            if (descriptors.size() > 0) {
                BluetoothGattDescriptor bluetoothGattDescriptor = descriptors.get(0);
                Log.i(c, "Descriptor Count=" + descriptors.size());
                for (BluetoothGattDescriptor bluetoothGattDescriptor2 : descriptors) {
                    String str = c;
                    Log.i(str, "Descriptor UUID=" + bluetoothGattDescriptor2.getUuid().toString());
                    Log.i(str, "Descriptor Permission=" + bluetoothGattDescriptor2.getPermissions());
                }
                if (z) {
                    Log.i(c, "setCharacteristicNotification BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE");
                    bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else {
                    bluetoothGattDescriptor.setValue(new byte[]{0, 0});
                }
                Log.i(c, "setCharacteristicNotification BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE DATA Permission=0x" + String.format("%04X", Integer.valueOf(bluetoothGattDescriptor.getPermissions())));
                c(bluetoothGattDescriptor);
            }
        }
        return characteristicNotification;
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        BluetoothGatt bluetoothGatt = this.k;
        if (bluetoothGatt == null) {
            return null;
        }
        return bluetoothGatt.getServices();
    }

    public boolean setNotification(boolean z) {
        if (this.k == null) {
            return false;
        }
        Message message = new Message();
        message.what = 2;
        message.arg1 = z ? 1 : 0;
        this.M.sendMessageDelayed(message, 200L);
        return true;
    }

    class d extends TimerTask {
        d() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            MTPPBLEService.this.setState(MTServiceState.Disconnected);
            Message message = new Message();
            message.what = 5;
            MTPPBLEService.this.M.sendMessageDelayed(message, 1000L);
        }
    }

    class b extends TimerTask {
        b() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Log.i(MTPPBLEService.c, "ConnectionTimerTask");
            MTPPBLEService.this.setState(MTServiceState.Disconnecting);
            Message message = new Message();
            message.what = 5;
            MTPPBLEService.this.M.sendMessageDelayed(message, 1000L);
        }
    }

    private class e extends Handler {
        private e() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i(MTPPBLEService.c, "PendingCommandHandler");
            try {
                if (message.what == 1) {
                    if (MTPPBLEService.this.m_state == MTServiceState.Connecting) {
                        Log.i(MTPPBLEService.c, "Start Service Discovery...");
                        MTPPBLEService.this.k.discoverServices();
                        if (MTPPBLEService.this.G > 0) {
                            MTPPBLEService.this.N.schedule(MTPPBLEService.this.b, MTPPBLEService.this.G);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (message.what == 2) {
                    Log.i(MTPPBLEService.c, "Enable Notification...");
                    List<BluetoothGattService> supportedGattServices = MTPPBLEService.this.getSupportedGattServices();
                    if (supportedGattServices != null) {
                        Log.i(MTPPBLEService.c, "Gatt Services=" + supportedGattServices.size());
                    }
                    for (BluetoothGattService bluetoothGattService : supportedGattServices) {
                        Log.i(MTPPBLEService.c, "enableBleNotification gattService UUID:{" + bluetoothGattService.getUuid().toString() + "}");
                        if (MTPPBLEService.this.m_serviceUUID.equals(bluetoothGattService.getUuid())) {
                            Log.i(MTPPBLEService.c, "enableBleNotification gattService found");
                            BluetoothGattCharacteristic characteristic = bluetoothGattService.getCharacteristic(MTPPDeviceConstants.UUID_PPSCRA_BLE_DEVICE_NOTIFY_LEN);
                            if (characteristic != null) {
                                Log.i(MTPPBLEService.c, "enableBleNotification charNotifyData found");
                                if ((characteristic.getProperties() | 16) > 0) {
                                    Log.i(MTPPBLEService.c, "enableBleNotification setCharacteristicNotification for Len");
                                    MTPPBLEService.this.a(characteristic, message.arg1 > 0);
                                }
                            }
                        }
                    }
                    return;
                }
                if (message.what == 5) {
                    MTPPBLEService.this.z = false;
                    MTPPBLEService.this.B = false;
                    MTPPBLEService.this.C = false;
                    if (MTPPBLEService.this.m_state != MTServiceState.Disconnected) {
                        Log.i(MTPPBLEService.c, "PENDING_DISCONNECT_NO_RETRY:Disconnect...");
                        MTPPBLEService.this.disconnectGatt();
                        return;
                    }
                    return;
                }
                if (message.what == 3) {
                    MTPPBLEService.this.z = false;
                    if (MTPPBLEService.this.m_state != MTServiceState.Disconnected) {
                        Log.i(MTPPBLEService.c, "PENDING_DISCONNECT:Disconnect...");
                        MTPPBLEService.this.setState(MTServiceState.Disconnecting);
                        MTPPBLEService.this.disconnectGatt();
                        return;
                    }
                    return;
                }
                if (message.what == 4) {
                    Log.i(MTPPBLEService.c, "PENDING_RECONNECT:Reconnecting...");
                    MTPPBLEService.this.B = false;
                    MTPPBLEService.this.reconnect();
                    return;
                }
                Log.i(MTPPBLEService.c, "Unknown State");
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        Log.w(c, "*** Connection Failure");
        MTServiceAdapter mTServiceAdapter = this.m_serviceAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        Log.w(c, "*** Bonding Failure");
        MTServiceAdapter mTServiceAdapter = this.m_serviceAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (MTPPDeviceConstants.UUID_PPSCRA_BLE_DEVICE_NOTIFY_DATA.equals(bluetoothGattCharacteristic.getUuid())) {
            String str = c;
            Log.i(str, "MTPPBLEService handleUpdate PPSCRA_BLE_DEVICE_NOTIFY_DATA");
            byte[] value = bluetoothGattCharacteristic.getValue();
            if (value == null || value.length <= 0) {
                return;
            }
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnDeviceData(value);
            } else {
                Log.i(str, "ServiceAdapter is NULL");
            }
        }
    }

    protected void readData() {
        c(b().getCharacteristic(MTPPDeviceConstants.UUID_PPSCRA_BLE_DEVICE_NOTIFY_DATA));
    }

    protected void OnNotificationReceived(byte[] bArr) {
        if (bArr.length > 0) {
            readData();
        }
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void sendData(byte[] bArr) {
        String str = c;
        Log.i(str, "MTPPBLEService writeData data.length=" + bArr.length);
        Log.i(str, "MTPPBLEService writeData data=" + PPSCRACommon.getHexString(bArr));
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int i2 = length - i;
            if (i2 > 65) {
                i2 = 65;
            }
            int i3 = i2 + i;
            writeDataBlock(Arrays.copyOfRange(bArr, i, i3));
            i = i3;
        }
    }

    public boolean writeDataBlock(byte[] bArr) {
        String str = c;
        Log.i(str, "MTPPBLEService writeDataBlock data.length=" + bArr.length);
        Log.i(str, "MTPPBLEService writeDataBlock data=" + PPSCRACommon.getHexString(bArr));
        byte[] bArr2 = {(byte) bArr.length};
        BluetoothGattService bluetoothGattServiceB = b();
        if (bluetoothGattServiceB == null) {
            Log.i(str, "BLEService gattService cannot be found UUID=" + this.m_serviceUUID);
            return false;
        }
        BluetoothGattCharacteristic characteristic = bluetoothGattServiceB.getCharacteristic(MTPPDeviceConstants.UUID_PPSCRA_BLE_DEVICE_WRITE_LEN);
        characteristic.setWriteType(2);
        characteristic.setValue(bArr2);
        Log.i(str, "BLEService writeCharacteristic writeLen");
        d(characteristic);
        BluetoothGattCharacteristic characteristic2 = bluetoothGattServiceB.getCharacteristic(MTPPDeviceConstants.UUID_PPSCRA_BLE_DEVICE_WRITE_DATA);
        characteristic2.setWriteType(2);
        characteristic2.setValue(bArr);
        Log.i(str, "BLEService writeCharacteristic writeData");
        d(characteristic2);
        return true;
    }

    protected void registerReceiver() {
        if (this.y) {
            return;
        }
        this.y = true;
        this.m_context.registerReceiver(this.K, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
    }

    protected void unregisterReceiver() {
        if (this.y) {
            this.y = false;
            this.m_context.unregisterReceiver(this.K);
        }
    }

    public void reconnect() {
        Log.i(c, "Reconnect to Gatt...");
        connectGatt();
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void setConnectionRetry(boolean z) {
        if (this.m_state != MTServiceState.Disconnected) {
            return;
        }
        this.n = z;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void connect() {
        if (this.m_state != MTServiceState.Disconnected) {
            Log.i(c, "Service is not disconnected");
        } else {
            a(false);
            connectGatt();
        }
    }

    public void connectGatt() {
        setState(MTServiceState.Connecting);
        if (this.l == null) {
            BluetoothManager bluetoothManager = (BluetoothManager) this.m_context.getSystemService("bluetooth");
            this.l = bluetoothManager;
            if (bluetoothManager == null) {
                Log.e(c, "Unable to initialize BluetoothManager.");
                f();
                setState(MTServiceState.Disconnected);
                return;
            }
        }
        BluetoothAdapter adapter = this.l.getAdapter();
        this.j = adapter;
        if (adapter == null) {
            Log.e(c, "Unable to obtain a BluetoothAdapter.");
            f();
            setState(MTServiceState.Disconnected);
            return;
        }
        if (this.m_address == null) {
            Log.w(c, "Unspecified address.");
            f();
            setState(MTServiceState.Disconnected);
            return;
        }
        if (this.m != null && this.m_address.equals(this.m) && this.k != null) {
            String str = c;
            Log.i(str, "Close Gatt Client");
            this.k.close();
            Log.d(str, "Unload the existing mBluetoothGatt instance");
            this.k = null;
        }
        this.D = 0;
        this.A = false;
        BluetoothDevice remoteDevice = this.j.getRemoteDevice(this.m_address);
        if (remoteDevice == null) {
            Log.w(c, "Device not found.  Unable to connect.");
            f();
            setState(MTServiceState.Disconnected);
            return;
        }
        this.y = false;
        String str2 = c;
        Log.i(str2, "Connect to Gatt...");
        BluetoothGatt bluetoothGattConnectGatt = remoteDevice.connectGatt(this.m_context, false, this.L);
        this.k = bluetoothGattConnectGatt;
        if (bluetoothGattConnectGatt == null) {
            Log.w(str2, "Unable to connect to Gatt.  Disconnecting...");
            f();
            setState(MTServiceState.Disconnecting);
            Message message = new Message();
            message.what = 5;
            this.M.sendMessageDelayed(message, 1000L);
        }
        this.m = this.m_address;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void disconnect() {
        if (this.m_state == MTServiceState.Disconnected || this.m_state == MTServiceState.Disconnecting) {
            return;
        }
        if (this.m_state == MTServiceState.Connecting) {
            if (h()) {
                return;
            }
            a(true);
            StartConnectingTimeout();
            return;
        }
        disconnectGatt();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        Log.i(c, "setDisconnectRequest=" + z);
        this.F = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean h() {
        return this.F;
    }

    public void disconnectGatt() {
        String str = c;
        Log.i(str, "disconnectGatt");
        if (this.j == null) {
            Log.w(str, "BluetoothAdapter not initialized");
            return;
        }
        if (this.k != null) {
            Log.i(str, "MTPPBLEService Disconnect Called Gatt disconnect");
            this.k.disconnect();
        } else {
            Log.i(str, "Gatt is NULL");
        }
        setState(MTServiceState.Disconnected);
    }
}

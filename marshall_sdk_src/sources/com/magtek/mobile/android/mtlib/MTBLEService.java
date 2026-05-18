package com.magtek.mobile.android.mtlib;

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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
public class MTBLEService extends MTBaseService {
    public static final long CONNECTING_TIMEOUT = 5000;
    private static final String TAG = "MTBLEService";
    private BroadcastReceiver mBondingBroadcastReceiver;
    private Queue<CharacteristicAction> mCharacteristicActionsSent;
    private Queue<CharacteristicAction> mCharacteristicActionsWaiting;
    private Handler mConnectingTimeoutHandler;
    private Runnable mConnectingTimeoutRunnable;
    private int mConnectionTimeout;
    private Timer mConnectionTimer;
    ConnectionTimerTask mConnectionTimerTask;
    private Queue<DescriptorAction> mDescriptorActionsSent;
    private Queue<DescriptorAction> mDescriptorActionsWaiting;
    private boolean mDisconnectRequested;
    private int mDisconnectTimeout;
    private Timer mDisconnectTimer;
    DisconnectTimerTask mDisconnectTimerTask;
    private Handler mPendingCommandHandler;
    private Queue<CharacteristicAction> mTopCharacteristicActionsWaiting;
    private Object m_batteryLevelObject;
    private BluetoothAdapter m_bluetoothAdapter;
    private BluetoothGatt m_bluetoothGatt;
    private BluetoothManager m_bluetoothManager;
    private byte[] m_completeData;
    private String m_connectedAddress;
    private byte[] m_dataRead;
    private Object m_deviceNameObject;
    private Object m_deviceSerialObject;
    private Object m_firmwareIDObject;
    private final BluetoothGattCallback m_gattCallback;
    private byte[] m_notificationData;
    private int m_notificationFailure;
    private boolean m_pendingBonding;
    private boolean m_pendingConnect;
    private boolean m_pendingReconnect;
    private boolean m_pendingSetupNotification;
    private boolean m_receiverRegistered;
    private final Lock sReadLock;
    private final ReentrantReadWriteLock sReadWriteLock;
    private final Lock sSendLock;
    private final Lock sWriteLock;
    private final boolean ENABLE_NOTIFICATION_TO_RECEIVE_DATA = true;
    private final int PENDING_DISCOVERY = 1;
    private final int PENDING_SET_UP_NOTIFICATION = 2;
    private final int PENDING_DISCONNECT = 3;
    private final int PENDING_RECONNECT = 4;
    private final int PENDING_DISCONNECT_NO_RETRY = 5;
    private boolean m_ConnectionRetry = false;
    private long m_batteryLevel = 0;
    private String m_deviceName = "";
    private String m_firmwareID = "";
    private String m_deviceSerial = "";
    private byte m_lastBlockID = -1;

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean isOutputChannelConfigurable() {
        return true;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean isServiceEMV() {
        return true;
    }

    public MTBLEService() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.sReadWriteLock = reentrantReadWriteLock;
        this.sReadLock = reentrantReadWriteLock.readLock();
        this.sWriteLock = reentrantReadWriteLock.writeLock();
        this.sSendLock = new ReentrantLock();
        this.mCharacteristicActionsWaiting = new LinkedList();
        this.mTopCharacteristicActionsWaiting = new LinkedList();
        this.mDescriptorActionsWaiting = new LinkedList();
        this.mCharacteristicActionsSent = new LinkedList();
        this.mDescriptorActionsSent = new LinkedList();
        this.m_receiverRegistered = false;
        this.m_pendingBonding = false;
        this.m_pendingSetupNotification = false;
        this.m_pendingConnect = false;
        this.m_pendingReconnect = false;
        this.m_notificationFailure = 0;
        this.mDisconnectRequested = false;
        this.mConnectionTimeout = 5000;
        this.mDisconnectTimeout = FTDISerialDevice.FTDI_BAUDRATE_300;
        this.mConnectingTimeoutHandler = new Handler();
        this.mConnectingTimeoutRunnable = null;
        this.mBondingBroadcastReceiver = new BroadcastReceiver() { // from class: com.magtek.mobile.android.mtlib.MTBLEService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String str = (String) intent.getParcelableExtra("android.bluetooth.device.extra.NAME");
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
                int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
                if (intExtra2 == 11 && intExtra == 12 && MTBLEService.this.m_ConnectionRetry) {
                    MTBLEService.this.m_pendingReconnect = true;
                }
                Log.i(MTBLEService.TAG, "Bond state changed for: " + str + ", new state: " + intExtra + " previous: " + intExtra2);
                String str2 = MTBLEService.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("BroadcastReceiver Bond State = ");
                sb.append(intExtra);
                Log.i(str2, sb.toString());
                if (intExtra == 12) {
                    if (MTBLEService.this.m_pendingBonding) {
                        MTBLEService.this.unregisterReceiver();
                        Log.i(MTBLEService.TAG, "Device has just been paired, disconnect and reconnect...");
                        Message message = new Message();
                        message.what = 3;
                        MTBLEService.this.mPendingCommandHandler.sendMessageDelayed(message, 1000L);
                        return;
                    }
                    return;
                }
                if (intExtra == 10) {
                    MTBLEService.this.m_pendingBonding = false;
                    MTBLEService.this.handleBondingFailure();
                    if (str == null) {
                        Message message2 = new Message();
                        message2.what = 5;
                        MTBLEService.this.mPendingCommandHandler.sendMessageDelayed(message2, 1000L);
                        Log.i(MTBLEService.TAG, "Disconnect...");
                    }
                }
            }
        };
        this.m_gattCallback = new BluetoothGattCallback() { // from class: com.magtek.mobile.android.mtlib.MTBLEService.3
            @Override // android.bluetooth.BluetoothGattCallback
            public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
                MTBLEService.this.mConnectionTimer.cancel();
                try {
                    if (i2 == 2) {
                        Log.i(MTBLEService.TAG, "BluetoothGattCallback::onConnectionStateChange:BluetoothProfile.STATE_CONNECTED");
                        MTBLEService.this.m_bluetoothGatt = bluetoothGatt;
                        MTBLEService.this.mTopCharacteristicActionsWaiting.clear();
                        MTBLEService.this.mCharacteristicActionsWaiting.clear();
                        MTBLEService.this.mCharacteristicActionsSent.clear();
                        MTBLEService.this.mDescriptorActionsWaiting.clear();
                        MTBLEService.this.mDescriptorActionsSent.clear();
                        MTBLEService.this.m_dataRead = null;
                        int bondState = bluetoothGatt.getDevice().getBondState();
                        Log.i(MTBLEService.TAG, "GattCallback Bond State = " + bondState);
                        if (bondState == 12) {
                            if (MTBLEService.this.mDisconnectTimer != null) {
                                MTBLEService.this.mDisconnectTimer.cancel();
                            }
                            MTBLEService.this.setState(MTServiceState.Connecting);
                            Message message = new Message();
                            message.what = 1;
                            MTBLEService.this.mPendingCommandHandler.sendMessageDelayed(message, 200L);
                            return;
                        }
                        if (bondState != 10) {
                            if (MTBLEService.this.mDisconnectTimeout <= 0 || MTBLEService.this.mDisconnectTimer == null) {
                            } else {
                                MTBLEService.this.mDisconnectTimer.schedule(MTBLEService.this.mDisconnectTimerTask, MTBLEService.this.mDisconnectTimeout);
                            }
                        } else {
                            MTBLEService.this.setState(MTServiceState.Connecting);
                            MTBLEService.this.m_pendingBonding = true;
                            MTBLEService.this.registerReceiver();
                            if (Build.VERSION.SDK_INT >= 19) {
                                if (bluetoothGatt.getDevice().createBond()) {
                                    Log.i(MTBLEService.TAG, "GattCallback Bond Created = true");
                                    Log.i(MTBLEService.TAG, "GattCallback Bond Device Name = " + bluetoothGatt.getDevice().getName());
                                    return;
                                }
                                Log.i(MTBLEService.TAG, "GattCallback Bond Created = false");
                                return;
                            }
                            Message message2 = new Message();
                            message2.what = 1;
                            MTBLEService.this.mPendingCommandHandler.sendMessageDelayed(message2, 200L);
                        }
                    } else {
                        if (i2 != 0) {
                            return;
                        }
                        Log.i(MTBLEService.TAG, "BluetoothGattCallback::onConnectionStateChange:BluetoothProfile.STATE_DISCONNECTED");
                        if (MTBLEService.this.m_pendingBonding) {
                            MTBLEService.this.handleBondingFailure();
                        } else if (MTBLEService.this.m_state == MTServiceState.Connecting) {
                            MTBLEService.this.handleConnectionFailure();
                        }
                        MTBLEService.this.mPendingCommandHandler.removeMessages(1);
                        MTBLEService.this.mPendingCommandHandler.removeMessages(2);
                        MTBLEService.this.mPendingCommandHandler.removeMessages(4);
                        if (MTBLEService.this.mDisconnectTimer != null) {
                            MTBLEService.this.mDisconnectTimer.cancel();
                        }
                        Log.i(MTBLEService.TAG, "Disconnected from GATT server.");
                        if (!MTBLEService.this.m_pendingReconnect) {
                            MTBLEService.this.mTopCharacteristicActionsWaiting.clear();
                            MTBLEService.this.mCharacteristicActionsWaiting.clear();
                            MTBLEService.this.mCharacteristicActionsSent.clear();
                            MTBLEService.this.mDescriptorActionsWaiting.clear();
                            MTBLEService.this.mDescriptorActionsSent.clear();
                            MTBLEService.this.unregisterReceiver();
                            Log.i(MTBLEService.TAG, "Disconnected from GATT server.");
                            MTBLEService.this.setState(MTServiceState.Disconnected);
                            Log.i(MTBLEService.TAG, "Close Gatt Client");
                            if (MTBLEService.this.m_bluetoothGatt != null) {
                                MTBLEService.this.m_bluetoothGatt.close();
                                MTBLEService.this.m_bluetoothGatt = null;
                            }
                            MTBLEService.this.setDisconnectRequested(false);
                            return;
                        }
                        MTBLEService.this.unregisterReceiver();
                        Log.i(MTBLEService.TAG, "Close Gatt Client");
                        MTBLEService.this.m_bluetoothGatt.close();
                        Log.i(MTBLEService.TAG, "Reconnecting to GATT server...");
                        Message message3 = new Message();
                        message3.what = 4;
                        MTBLEService.this.mPendingCommandHandler.sendMessageDelayed(message3, AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
                        if (MTBLEService.this.mConnectionTimeout <= 0 || MTBLEService.this.mConnectionTimer == null) {
                        } else {
                            MTBLEService.this.mConnectionTimer.schedule(MTBLEService.this.mConnectionTimerTask, MTBLEService.this.mConnectionTimeout);
                        }
                    }
                } catch (Exception unused) {
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                Log.i(MTBLEService.TAG, "onServicesDiscovered Status=" + i);
                if (i == 0) {
                    MTBLEService.this.mConnectionTimer.cancel();
                    int bondState = bluetoothGatt.getDevice().getBondState();
                    Log.i(MTBLEService.TAG, "ServicesDiscovered Bond State = " + bondState);
                    if (bondState == 12) {
                        MTBLEService.this.m_pendingSetupNotification = false;
                        MTBLEService.this.setNotification(true);
                        return;
                    } else {
                        if (bondState == 10 && MTBLEService.this.m_pendingBonding) {
                            MTBLEService.this.setNotification(true);
                            return;
                        }
                        return;
                    }
                }
                Log.i(MTBLEService.TAG, "onServicesDiscovered received: " + i);
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                Log.i(MTBLEService.TAG, "BLEService onCharacteristicChanged " + bluetoothGattCharacteristic.getUuid().toString());
                Log.i(MTBLEService.TAG, "BLEService address=" + this);
                if (MTDeviceConstants.UUID_SCRA_BLE_DEVICE_CARD_DATA.equals(bluetoothGattCharacteristic.getUuid())) {
                    Log.i(MTBLEService.TAG, "BLEService onCharacteristicChanged UUID_SCRA_BLE_DEVICE_CARD_DATA");
                    byte[] value = bluetoothGattCharacteristic.getValue();
                    if (value.length > 0) {
                        MTBLEService.this.OnCardDataReceived(value);
                        return;
                    }
                    return;
                }
                if (MTDeviceConstants.UUID_SCRA_BLE_DEVICE_NOTIFY_DATA.equals(bluetoothGattCharacteristic.getUuid())) {
                    Log.i(MTBLEService.TAG, "BLEService onCharacteristicChanged UUID_SCRA_BLE_DEVICE_NOTIFY_DATA");
                    byte[] value2 = bluetoothGattCharacteristic.getValue();
                    if (value2.length > 0) {
                        MTBLEService.this.OnNotificationReceived(value2);
                    }
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) throws Throwable {
                boolean z;
                CharacteristicAction characteristicAction;
                String str = MTBLEService.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("BLEService onCharacteristicRead status=0x");
                boolean z2 = true;
                sb.append(String.format("%02X", Integer.valueOf(i)));
                sb.append(", UUID=");
                sb.append(bluetoothGattCharacteristic.getUuid().toString());
                Log.i(str, sb.toString());
                if (i != 0) {
                    Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead status != 0");
                }
                MTBLEService.this.sWriteLock.lock();
                try {
                    if (MTBLEService.this.mCharacteristicActionsSent.size() <= 0 || (characteristicAction = (CharacteristicAction) MTBLEService.this.mCharacteristicActionsSent.peek()) == null || characteristicAction.write || characteristicAction.characteristic.getUuid() != bluetoothGattCharacteristic.getUuid()) {
                        z = true;
                    } else {
                        Log.i(MTBLEService.TAG, "BLEService onCharacteristicRead UUID=" + bluetoothGattCharacteristic.getUuid().toString() + " was read successfully");
                        MTBLEService.this.mCharacteristicActionsSent.remove();
                        MTBLEService.this.sWriteLock.unlock();
                        try {
                            MTBLEService.this.processActionsQueues();
                            z = false;
                        } catch (Throwable th) {
                            th = th;
                            z2 = false;
                            if (z2) {
                                MTBLEService.this.sWriteLock.unlock();
                            }
                            throw th;
                        }
                    }
                    if (z) {
                        MTBLEService.this.sWriteLock.unlock();
                    }
                    Log.i(MTBLEService.TAG, "BLEService onCharacteristicRead, UUID=" + bluetoothGattCharacteristic.getUuid().toString());
                    Log.i(MTBLEService.TAG, "BLEService onCharacteristicRead, len=0x" + String.format("%02X", Integer.valueOf(bluetoothGattCharacteristic.getValue().length)));
                    Log.i(MTBLEService.TAG, "BLEService onCharacteristicRead, Data:\n" + MTParser.getHexString(bluetoothGattCharacteristic.getValue()) + "\n");
                    try {
                        if (i == 0) {
                            Log.i(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_SUCCESS");
                            byte[] value = bluetoothGattCharacteristic.getValue();
                            if (MTDeviceConstants.UUID_SCRA_BLE_DEVICE_CARD_DATA.equals(bluetoothGattCharacteristic.getUuid())) {
                                Log.i(MTBLEService.TAG, "onCharacteristicRead CARD_DATA");
                                MTBLEService.this.handleDeviceData(value);
                            } else if (MTDeviceConstants.UUID_SCRA_BLE_DEVICE_COMMAND_DATA.equals(bluetoothGattCharacteristic.getUuid())) {
                                Log.i(MTBLEService.TAG, "onCharacteristicRead COMMAND_DATA");
                                MTBLEService.this.handleDeviceResponse(value);
                            }
                        } else if (i == 13) {
                            Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_INVALID_ATTRIBUTE_LENGTH");
                        } else if (i == 15) {
                            Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_INSUFFICIENT_ENCRYPTION");
                        } else if (i == 257) {
                            Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_FAILURE");
                        } else if (i == 2) {
                            Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_READ_NOT_PERMITTED");
                        } else if (i == 3) {
                            Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_WRITE_NOT_PERMITTED");
                        } else if (i == 5) {
                            Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_INSUFFICIENT_AUTHENTICATION");
                        } else if (i == 6) {
                            Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_REQUEST_NOT_SUPPORTED");
                        } else if (i != 7) {
                        } else {
                            Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead GATT_INVALID_OFFSET");
                        }
                    } catch (Exception e) {
                        Log.w(MTBLEService.TAG, "BLEService onCharacteristicRead Exception Caught: " + e.toString());
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) throws Throwable {
                CharacteristicAction characteristicAction;
                String str = MTBLEService.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("BLEService onCharacteristicWrite status=0x");
                boolean z = true;
                sb.append(String.format("%02X", Integer.valueOf(i)));
                sb.append(", UUID=");
                sb.append(bluetoothGattCharacteristic.getUuid().toString());
                Log.i(str, sb.toString());
                if (i != 0) {
                    Log.w(MTBLEService.TAG, "BLEService onCharacteristicWrite status != 0");
                }
                MTBLEService.this.sWriteLock.lock();
                try {
                    if (MTBLEService.this.mCharacteristicActionsSent.size() > 0 && (characteristicAction = (CharacteristicAction) MTBLEService.this.mCharacteristicActionsSent.peek()) != null && characteristicAction.write && characteristicAction.characteristic.getUuid() == bluetoothGattCharacteristic.getUuid()) {
                        Log.i(MTBLEService.TAG, "BLEService onCharacteristicWrite UUID=" + bluetoothGattCharacteristic.getUuid().toString() + " was written successfully");
                        MTBLEService.this.mCharacteristicActionsSent.remove();
                        MTBLEService.this.sWriteLock.unlock();
                        try {
                            MTBLEService.this.processActionsQueues();
                            z = false;
                        } catch (Throwable th) {
                            th = th;
                            z = false;
                            if (z) {
                                MTBLEService.this.sWriteLock.unlock();
                            }
                            throw th;
                        }
                    }
                    if (z) {
                        MTBLEService.this.sWriteLock.unlock();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) throws Throwable {
                String str = MTBLEService.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("BLEService onDescriptorRead status=0x");
                boolean z = true;
                sb.append(String.format("%02X", Integer.valueOf(i)));
                Log.i(str, sb.toString());
                if ((i & 5) == 5) {
                    Log.i(MTBLEService.TAG, "BLEService onDescriptorRead GATT_INSUFFICIENT_AUTHENTICATION");
                    Log.i(MTBLEService.TAG, "BLEService onDescriptorRead BondState=0x" + String.format("%04X", Integer.valueOf(bluetoothGatt.getDevice().getBondState())));
                }
                MTBLEService.this.sWriteLock.lock();
                try {
                    if (MTBLEService.this.mDescriptorActionsSent.size() > 0) {
                        DescriptorAction descriptorAction = (DescriptorAction) MTBLEService.this.mDescriptorActionsSent.peek();
                        if (!descriptorAction.write && descriptorAction.descriptor.getUuid() == bluetoothGattDescriptor.getUuid()) {
                            Log.i(MTBLEService.TAG, "BLEService onDescriptorRead UUID=" + bluetoothGattDescriptor.getUuid().toString() + " was read successfully");
                            MTBLEService.this.mDescriptorActionsSent.remove();
                            MTBLEService.this.sWriteLock.unlock();
                            try {
                                MTBLEService.this.processActionsQueues();
                                z = false;
                            } catch (Throwable th) {
                                th = th;
                                z = false;
                                if (z) {
                                    MTBLEService.this.sWriteLock.unlock();
                                }
                                throw th;
                            }
                        }
                    }
                    if (z) {
                        MTBLEService.this.sWriteLock.unlock();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) throws Throwable {
                String str = MTBLEService.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("BLEService onDescriptorWrite status=0x");
                boolean z = true;
                sb.append(String.format("%02X", Integer.valueOf(i)));
                Log.i(str, sb.toString());
                if (i == 0) {
                    if (!MTBLEService.this.m_pendingBonding) {
                        if (!MTBLEService.this.m_ConnectionRetry || !MTBLEService.this.isDisconnectRequested()) {
                            Log.i(MTBLEService.TAG, "BLEService onDescriptorWrite SetState to CONNECTED");
                            MTBLEService.this.setState(MTServiceState.Connected);
                            MTBLEService.this.m_lastBlockID = (byte) -1;
                        } else {
                            MTBLEService.this.setState(MTServiceState.Disconnecting);
                            Message message = new Message();
                            message.what = 5;
                            MTBLEService.this.mPendingCommandHandler.sendMessageDelayed(message, 1000L);
                        }
                        MTBLEService.this.m_pendingReconnect = false;
                        if (MTBLEService.this.isDisconnectRequested()) {
                            MTBLEService.this.setDisconnectRequested(false);
                            MTBLEService.this.disconnectGatt();
                        }
                    }
                } else if (i == 5) {
                    Log.i(MTBLEService.TAG, "BLEService onDescriptorWrite GATT_INSUFFICIENT_AUTHENTICATION");
                }
                MTBLEService.this.sWriteLock.lock();
                try {
                    if (MTBLEService.this.mDescriptorActionsSent.size() > 0) {
                        DescriptorAction descriptorAction = (DescriptorAction) MTBLEService.this.mDescriptorActionsSent.peek();
                        if (descriptorAction.write && descriptorAction.descriptor.getUuid() == bluetoothGattDescriptor.getUuid()) {
                            MTBLEService.this.mDescriptorActionsSent.remove();
                            MTBLEService.this.sWriteLock.unlock();
                            try {
                                MTBLEService.this.processActionsQueues();
                                z = false;
                            } catch (Throwable th) {
                                th = th;
                                z = false;
                                if (z) {
                                    MTBLEService.this.sWriteLock.unlock();
                                }
                                throw th;
                            }
                        }
                    }
                    if (z) {
                        MTBLEService.this.sWriteLock.unlock();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        };
        this.mPendingCommandHandler = new PendingCommandHandler();
        this.mConnectionTimer = new Timer();
        this.mDisconnectTimer = new Timer();
        this.mDisconnectTimerTask = new DisconnectTimerTask();
        this.mConnectionTimerTask = new ConnectionTimerTask();
    }

    private class CharacteristicAction {
        private BluetoothGattCharacteristic characteristic;
        private boolean write;

        private CharacteristicAction() {
        }
    }

    private class DescriptorAction {
        private BluetoothGattDescriptor descriptor;
        private boolean write;

        private DescriptorAction() {
        }
    }

    protected void StartConnectingTimeout() {
        Runnable runnable = this.mConnectingTimeoutRunnable;
        if (runnable != null) {
            this.mConnectingTimeoutHandler.removeCallbacks(runnable);
            this.mConnectingTimeoutRunnable = null;
        }
        Runnable runnable2 = new Runnable() { // from class: com.magtek.mobile.android.mtlib.MTBLEService.1
            @Override // java.lang.Runnable
            public void run() {
                if (MTBLEService.this.m_state == MTServiceState.Connecting) {
                    MTBLEService.this.disconnectGatt();
                }
            }
        };
        this.mConnectingTimeoutRunnable = runnable2;
        this.mConnectingTimeoutHandler.postDelayed(runnable2, 5000L);
    }

    protected void StopConnectingTimeout() {
        Runnable runnable = this.mConnectingTimeoutRunnable;
        if (runnable != null) {
            this.mConnectingTimeoutHandler.removeCallbacks(runnable);
            this.mConnectingTimeoutRunnable = null;
        }
    }

    private BluetoothGattService getGattService() {
        BluetoothGatt bluetoothGatt = this.m_bluetoothGatt;
        if (bluetoothGatt != null) {
            return bluetoothGatt.getService(this.m_serviceUUID);
        }
        return null;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService
    protected void setState(MTServiceState mTServiceState) {
        if (mTServiceState != MTServiceState.Connecting) {
            StopConnectingTimeout();
        }
        super.setState(mTServiceState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processActionsQueues() {
        if (processDescriptorActionsQueue()) {
            return;
        }
        processCharacteristicActionsQueue();
    }

    private boolean processDescriptorActionsQueue() {
        boolean descriptorFromBLE;
        this.sWriteLock.lock();
        try {
            int size = this.mDescriptorActionsSent.size() + this.mCharacteristicActionsSent.size();
            int size2 = this.mDescriptorActionsWaiting.size();
            String str = TAG;
            Log.i(str, "BLEService processDescriptorActionsQueue sentSize=" + size);
            Log.i(str, "BLEService processDescriptorActionsQueue waitingSize=" + size2);
            if (size != 0 || size2 <= 0) {
                descriptorFromBLE = false;
            } else {
                DescriptorAction descriptorActionPeek = this.mDescriptorActionsWaiting.peek();
                if (descriptorActionPeek.write) {
                    Log.i(str, "BLEService processDescriptorActionsQueue writing UUID=" + descriptorActionPeek.descriptor.getUuid().toString());
                    descriptorFromBLE = writeDescriptorToBLE(descriptorActionPeek.descriptor);
                } else {
                    Log.i(str, "BLEService processDescriptorActionsQueue reading UUID=" + descriptorActionPeek.descriptor.getUuid().toString());
                    descriptorFromBLE = readDescriptorFromBLE(descriptorActionPeek.descriptor);
                }
                if (descriptorFromBLE) {
                    this.mDescriptorActionsSent.add(this.mDescriptorActionsWaiting.remove());
                }
            }
            return descriptorFromBLE;
        } finally {
            this.sWriteLock.unlock();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean processCharacteristicActionsQueue() {
        /*
            Method dump skipped, instruction units count: 331
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtlib.MTBLEService.processCharacteristicActionsQueue():boolean");
    }

    private boolean readDescriptorFromBLE(BluetoothGattDescriptor bluetoothGattDescriptor) {
        if (this.m_bluetoothAdapter == null || this.m_bluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        String str = TAG;
        Log.i(str, "BluetoothGatt.readDescriptorFromBLE");
        boolean descriptor = this.m_bluetoothGatt.readDescriptor(bluetoothGattDescriptor);
        if (!descriptor) {
            Log.w(str, "*** BluetoothGatt.readDescriptorFromBLE failed");
        }
        return descriptor;
    }

    private boolean writeDescriptorToBLE(BluetoothGattDescriptor bluetoothGattDescriptor) {
        if (this.m_bluetoothAdapter == null || this.m_bluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        String str = TAG;
        Log.i(str, "BluetoothGatt.writeDescriptorToBLE");
        boolean zWriteDescriptor = this.m_bluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
        if (!zWriteDescriptor) {
            Log.w(str, "*** BluetoothGatt.writeDescriptorToBLE failed");
        }
        return zWriteDescriptor;
    }

    private boolean readCharacteristicFromBLE(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.m_bluetoothAdapter == null || this.m_bluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        String str = TAG;
        Log.i(str, "BluetoothGatt.readCharacteristicFromBLE");
        boolean characteristic = this.m_bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
        if (!characteristic) {
            Log.w(str, "*** BluetoothGatt.readCharacteristicFromBLE failed");
        }
        return characteristic;
    }

    private boolean writeCharacteristicToBLE(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.m_bluetoothAdapter == null || this.m_bluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        String str = TAG;
        Log.i(str, "BluetoothGatt.writeCharacteristicToBLE");
        boolean zWriteCharacteristic = this.m_bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
        if (!zWriteCharacteristic) {
            Log.w(str, "*** BluetoothGatt.writeCharacteristicToBLE failed");
        }
        return zWriteCharacteristic;
    }

    private void readDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        Log.i(TAG, "BluetoothGatt.readDescriptor");
        DescriptorAction descriptorAction = new DescriptorAction();
        descriptorAction.write = false;
        descriptorAction.descriptor = bluetoothGattDescriptor;
        this.sWriteLock.lock();
        try {
            this.mDescriptorActionsWaiting.add(descriptorAction);
            this.sWriteLock.unlock();
            processActionsQueues();
        } catch (Throwable th) {
            this.sWriteLock.unlock();
            throw th;
        }
    }

    private void writeDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        Log.i(TAG, "BluetoothGatt.writeDescriptor");
        DescriptorAction descriptorAction = new DescriptorAction();
        descriptorAction.write = true;
        descriptorAction.descriptor = bluetoothGattDescriptor;
        this.sWriteLock.lock();
        try {
            this.mDescriptorActionsWaiting.add(descriptorAction);
            this.sWriteLock.unlock();
            processActionsQueues();
        } catch (Throwable th) {
            this.sWriteLock.unlock();
            throw th;
        }
    }

    private void readCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.i(TAG, "BluetoothGatt.readCharacteristic");
        CharacteristicAction characteristicAction = new CharacteristicAction();
        characteristicAction.write = false;
        characteristicAction.characteristic = bluetoothGattCharacteristic;
        this.sWriteLock.lock();
        try {
            this.mTopCharacteristicActionsWaiting.add(characteristicAction);
            this.sWriteLock.unlock();
            processActionsQueues();
        } catch (Throwable th) {
            this.sWriteLock.unlock();
            throw th;
        }
    }

    private void writeCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.i(TAG, "BluetoothGatt.writeCharacteristic");
        CharacteristicAction characteristicAction = new CharacteristicAction();
        characteristicAction.write = true;
        characteristicAction.characteristic = bluetoothGattCharacteristic;
        this.sWriteLock.lock();
        try {
            this.mCharacteristicActionsWaiting.add(characteristicAction);
            this.sWriteLock.unlock();
            processActionsQueues();
        } catch (Throwable th) {
            this.sWriteLock.unlock();
            throw th;
        }
    }

    public boolean writeData(byte[] bArr) {
        String str = TAG;
        Log.i(str, "BLEService writeData data.length=" + bArr.length);
        Log.i(str, "BLEService writeData data=" + MTParser.getHexString(bArr));
        int length = bArr.length;
        int i = 0;
        boolean zWriteDataBlock = false;
        while (i < length) {
            int i2 = length - i;
            if (i2 > 65) {
                i2 = 65;
            }
            int i3 = i2 + i;
            zWriteDataBlock = writeDataBlock(Arrays.copyOfRange(bArr, i, i3));
            i = i3;
        }
        return zWriteDataBlock;
    }

    public boolean writeDataBlock(byte[] bArr) {
        String str = TAG;
        Log.i(str, "BLEService writeDataBlock data.length=" + bArr.length);
        Log.i(str, "BLEService writeDataBlock data=" + MTParser.getHexString(bArr));
        int length = bArr.length;
        BluetoothGattService gattService = getGattService();
        if (gattService == null) {
            Log.i(str, "BLEService gattService cannot be found UUID=" + this.m_serviceUUID);
            return false;
        }
        BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_COMMAND_DATA);
        characteristic.setWriteType(2);
        characteristic.setValue(bArr);
        Log.i(str, "BLEService writeCharacteristic writeData");
        writeCharacteristic(characteristic);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setCharacteristicNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        BluetoothGatt bluetoothGatt;
        if (this.m_bluetoothAdapter == null || (bluetoothGatt = this.m_bluetoothGatt) == null) {
            Log.i(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        boolean characteristicNotification = bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, z);
        if (characteristicNotification) {
            List<BluetoothGattDescriptor> descriptors = bluetoothGattCharacteristic.getDescriptors();
            if (descriptors.size() > 0) {
                BluetoothGattDescriptor bluetoothGattDescriptor = descriptors.get(0);
                Log.i(TAG, "Descriptor Count=" + descriptors.size());
                for (BluetoothGattDescriptor bluetoothGattDescriptor2 : descriptors) {
                    String str = TAG;
                    Log.i(str, "Descriptor UUID=" + bluetoothGattDescriptor2.getUuid().toString());
                    Log.i(str, "Descriptor Permission=" + bluetoothGattDescriptor2.getPermissions());
                }
                if (z) {
                    Log.i(TAG, "setCharacteristicNotification BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE");
                    bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else {
                    bluetoothGattDescriptor.setValue(new byte[]{0, 0});
                }
                Log.i(TAG, "setCharacteristicNotification BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE DATA Permission=0x" + String.format("%04X", Integer.valueOf(bluetoothGattDescriptor.getPermissions())));
                writeDescriptor(bluetoothGattDescriptor);
            }
        }
        return characteristicNotification;
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        BluetoothGatt bluetoothGatt = this.m_bluetoothGatt;
        if (bluetoothGatt == null) {
            return null;
        }
        return bluetoothGatt.getServices();
    }

    public boolean setNotification(boolean z) {
        if (this.m_bluetoothGatt == null) {
            return false;
        }
        Message message = new Message();
        message.what = 2;
        message.arg1 = z ? 1 : 0;
        this.mPendingCommandHandler.sendMessageDelayed(message, 200L);
        return true;
    }

    class DisconnectTimerTask extends TimerTask {
        DisconnectTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            MTBLEService.this.setState(MTServiceState.Disconnected);
            Message message = new Message();
            message.what = 5;
            MTBLEService.this.mPendingCommandHandler.sendMessageDelayed(message, 1000L);
        }
    }

    class ConnectionTimerTask extends TimerTask {
        ConnectionTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Log.i(MTBLEService.TAG, "ConnectionTimerTask");
            MTBLEService.this.setState(MTServiceState.Disconnecting);
            Message message = new Message();
            message.what = 5;
            MTBLEService.this.mPendingCommandHandler.sendMessageDelayed(message, 1000L);
        }
    }

    private class PendingCommandHandler extends Handler {
        private PendingCommandHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i(MTBLEService.TAG, "PendingCommandHandler");
            try {
                if (message.what == 1) {
                    if (MTBLEService.this.m_state == MTServiceState.Connecting) {
                        Log.i(MTBLEService.TAG, "Start Service Discovery...");
                        MTBLEService.this.m_bluetoothGatt.discoverServices();
                        if (MTBLEService.this.mConnectionTimeout > 0) {
                            MTBLEService.this.mConnectionTimer.schedule(MTBLEService.this.mConnectionTimerTask, MTBLEService.this.mConnectionTimeout);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (message.what == 2) {
                    Log.i(MTBLEService.TAG, "Enable Notification...");
                    for (BluetoothGattService bluetoothGattService : MTBLEService.this.getSupportedGattServices()) {
                        Log.i(MTBLEService.TAG, "enableBleNotification gattService UUID:{" + bluetoothGattService.getUuid().toString() + "}");
                        if (MTBLEService.this.m_serviceUUID.equals(bluetoothGattService.getUuid())) {
                            Log.i(MTBLEService.TAG, "enableBleNotification gattService found");
                            BluetoothGattCharacteristic characteristic = bluetoothGattService.getCharacteristic(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_NOTIFY_DATA);
                            if (characteristic != null) {
                                Log.i(MTBLEService.TAG, "enableBleNotification charNotifyData CARDDATA found");
                                if ((characteristic.getProperties() | 16) > 0) {
                                    Log.i(MTBLEService.TAG, "enableBleNotification setCharacteristicNotification for Len");
                                    MTBLEService.this.setCharacteristicNotification(characteristic, message.arg1 > 0);
                                }
                            }
                            BluetoothGattCharacteristic characteristic2 = bluetoothGattService.getCharacteristic(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_CARD_DATA);
                            if (characteristic2 != null) {
                                Log.i(MTBLEService.TAG, "enableBleNotification charCardData found");
                                if ((characteristic2.getProperties() | 16) > 0) {
                                    Log.i(MTBLEService.TAG, "enableBleNotification setCharacteristicNotification for Card Data");
                                    MTBLEService.this.setCharacteristicNotification(characteristic2, message.arg1 > 0);
                                }
                            }
                        }
                    }
                    return;
                }
                if (message.what == 5) {
                    MTBLEService.this.m_pendingBonding = false;
                    MTBLEService.this.m_pendingConnect = false;
                    MTBLEService.this.m_pendingReconnect = false;
                    if (MTBLEService.this.m_state != MTServiceState.Disconnected) {
                        Log.i(MTBLEService.TAG, "PENDING_DISCONNECT_NO_RETRY:Disconnect...");
                        MTBLEService.this.disconnectGatt();
                        return;
                    }
                    return;
                }
                if (message.what == 3) {
                    MTBLEService.this.m_pendingBonding = false;
                    if (MTBLEService.this.m_state != MTServiceState.Disconnected) {
                        Log.i(MTBLEService.TAG, "PENDING_DISCONNECT:Disconnect...");
                        MTBLEService.this.setState(MTServiceState.Disconnecting);
                        MTBLEService.this.disconnectGatt();
                        return;
                    }
                    return;
                }
                if (message.what == 4) {
                    Log.i(MTBLEService.TAG, "PENDING_RECONNECT:Reconnecting...");
                    MTBLEService.this.m_pendingConnect = false;
                    MTBLEService.this.reconnect();
                    return;
                }
                Log.i(MTBLEService.TAG, "Unknown State");
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleConnectionFailure() {
        Log.w(TAG, "*** Connection Failure");
        if (this.m_serviceAdapter != null) {
            this.m_serviceAdapter.OnDeviceError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBondingFailure() {
        Log.w(TAG, "*** Bonding Failure");
        if (this.m_serviceAdapter != null) {
            this.m_serviceAdapter.OnBondingFailure();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceData(byte[] bArr) {
        int length;
        byte b = 0;
        if (bArr != null && bArr.length > 2) {
            byte b2 = bArr[0];
            int i = ((bArr[1] & 255) << 8) + (bArr[2] & 255);
            if (i > 0 && (length = bArr.length - 3) > 0) {
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 3, bArr2, 0, length);
                if (b2 == 0) {
                    this.m_dataRead = bArr2;
                } else if (b2 == 1) {
                    this.m_dataRead = handleCompressedData(bArr2, i);
                } else if (b2 == 2) {
                    this.m_dataRead = bArr2;
                } else if (b2 == 3) {
                    this.m_dataRead = handleCompressedData(bArr2, i);
                }
            }
            b = b2;
        }
        byte[] bArr3 = this.m_dataRead;
        if (bArr3 != null) {
            byte[] bArrCopyOf = Arrays.copyOf(bArr3, bArr3.length);
            Log.i(TAG, "Data Copy Length=" + bArrCopyOf.length);
            if (this.m_serviceAdapter != null) {
                new Message();
                if (b == 0 || b == 1) {
                    this.m_serviceAdapter.OnCardData(bArrCopyOf);
                } else if (b == 2 || b == 3) {
                    this.m_serviceAdapter.OnDeviceData(bArrCopyOf);
                }
            }
            this.m_dataRead = null;
        }
    }

    private byte[] handleCompressedData(byte[] bArr, int i) {
        byte[] bArrDecodeRLEData = decodeRLEData(bArr);
        String str = TAG;
        Log.i(str, "*** Expected Data Length     =" + i);
        Log.i(str, "*** Decompressed Data Length=" + bArrDecodeRLEData.length);
        return bArrDecodeRLEData;
    }

    private byte[] decodeRLEData(byte[] bArr) {
        int i;
        int decodeRLEDataLength = getDecodeRLEDataLength(bArr);
        if (decodeRLEDataLength < 1) {
            return null;
        }
        byte[] bArr2 = new byte[decodeRLEDataLength];
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            int i4 = i2 + 1;
            if (i4 < bArr.length) {
                if (bArr[i2] == bArr[i4]) {
                    int i5 = i2 + 2;
                    if (i5 < bArr.length) {
                        int i6 = bArr[i5] & 255;
                        int i7 = 0;
                        while (i7 < i6) {
                            bArr2[i3] = bArr[i2];
                            i7++;
                            i3++;
                        }
                        i2 += 3;
                    }
                } else {
                    i = i3 + 1;
                    bArr2[i3] = bArr[i2];
                }
            } else {
                i = i3 + 1;
                bArr2[i3] = bArr[i2];
            }
            i2 = i4;
            i3 = i;
        }
        return bArr2;
    }

    private int getDecodeRLEDataLength(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = i + 1;
            if (i3 >= bArr.length || bArr[i] != bArr[i3]) {
                i2++;
                i = i3;
            } else {
                int i4 = i + 2;
                if (i4 < bArr.length) {
                    i2 += bArr[i4] & 255;
                    i += 3;
                }
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceResponse(byte[] bArr) {
        if (bArr != 0) {
            if (this.m_batteryLevelObject != null) {
                if (isPM2Device()) {
                    if (bArr.length >= 6 && bArr[0] == 0 && bArr[2] == 1 && bArr[3] == 1 && bArr[4] == 0) {
                        this.m_batteryLevel = bArr[5];
                    }
                } else if (bArr.length >= 3 && bArr[0] == 0 && bArr[1] == 1) {
                    this.m_batteryLevel = bArr[2];
                }
                this.m_batteryLevelObject.notifyAll();
            }
            if (this.m_deviceNameObject != null) {
                if (bArr.length >= 1 && bArr[0] == 0) {
                    int i = bArr[1];
                    if (bArr.length - 2 >= i) {
                        byte[] bArr2 = new byte[i];
                        System.arraycopy(bArr, 2, bArr2, 0, i);
                        this.m_deviceName = MTParser.getTextString(bArr2, 0, i);
                    } else {
                        this.m_deviceName = "";
                    }
                }
                this.m_deviceNameObject.notifyAll();
            }
            if (this.m_firmwareIDObject != null) {
                if (bArr.length >= 1 && bArr[0] == 0) {
                    int i2 = bArr[1];
                    if (bArr.length - 2 >= i2) {
                        byte[] bArr3 = new byte[i2];
                        System.arraycopy(bArr, 2, bArr3, 0, i2);
                        this.m_firmwareID = MTParser.getTextString(bArr3, 0, i2);
                    } else {
                        this.m_firmwareID = "";
                    }
                }
                this.m_firmwareIDObject.notifyAll();
            }
            if (this.m_deviceSerialObject != null) {
                if (bArr.length >= 1 && bArr[0] == 0) {
                    int i3 = bArr[1];
                    if (bArr.length - 2 >= i3) {
                        byte[] bArr4 = new byte[i3];
                        System.arraycopy(bArr, 2, bArr4, 0, i3);
                        this.m_deviceSerial = MTParser.getTextString(bArr4, 0, i3);
                    } else {
                        this.m_deviceSerial = "";
                    }
                }
                this.m_deviceSerialObject.notifyAll();
            }
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            Log.i(TAG, "Data Copy Length=" + bArrCopyOf.length);
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnCommandData(bArrCopyOf);
            }
        }
    }

    protected void writeStatus(byte[] bArr) {
        BluetoothGattService gattService = getGattService();
        if (gattService != null) {
            Log.i(TAG, "BLEService Write Status");
            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_READ_STATUS);
            characteristic.setWriteType(1);
            characteristic.setValue(bArr);
            writeCharacteristic(characteristic);
        }
    }

    protected void writeCommand(byte[] bArr) {
        BluetoothGattService gattService = getGattService();
        if (gattService != null) {
            Log.i(TAG, "BLEService Write Command");
            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_COMMAND_DATA);
            characteristic.setWriteType(2);
            characteristic.setValue(bArr);
            writeCharacteristic(characteristic);
            return;
        }
        Log.i(TAG, "BLEService Write Command:gattService is Null");
    }

    protected void readCommandData() {
        readCharacteristic(getGattService().getCharacteristic(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_COMMAND_DATA));
    }

    protected void readCardData() {
        readCharacteristic(getGattService().getCharacteristic(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_CARD_DATA));
    }

    protected void OnCardDataError(MTCardDataError mTCardDataError) {
        if (this.m_serviceAdapter != null) {
            this.m_serviceAdapter.OnCardDataError();
        }
    }

    protected void OnNotificationReceived(byte[] bArr) {
        int length = bArr.length;
        if (length > 0) {
            this.m_notificationData = Arrays.copyOf(bArr, bArr.length);
            if ((length >= 4 ? ((bArr[3] & 255) << 8) + (bArr[2] & 255) : 0) > 0) {
                if (bArr[0] == 0) {
                    readCommandData();
                } else {
                    readCardData();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void OnCardDataReceived(byte[] r7) {
        /*
            r6 = this;
            int r0 = r7.length
            if (r0 <= 0) goto L4e
            r1 = 1
            if (r0 < r1) goto L4e
            r2 = 0
            r3 = r7[r2]
            r4 = -1
            if (r3 != r4) goto L16
            r6.m_lastBlockID = r3
            byte[] r7 = r6.m_completeData
            if (r7 == 0) goto L4e
            r6.handleDeviceData(r7)
            goto L4e
        L16:
            if (r3 != 0) goto L1c
            r4 = 0
            r6.m_completeData = r4
            goto L23
        L1c:
            byte r4 = r6.m_lastBlockID
            int r4 = r4 + r1
            if (r4 == r3) goto L23
            r4 = 1
            goto L24
        L23:
            r4 = 0
        L24:
            r6.m_lastBlockID = r3
            if (r4 == 0) goto L2e
            com.magtek.mobile.android.mtlib.MTCardDataError r7 = com.magtek.mobile.android.mtlib.MTCardDataError.PACKET_ERROR
            r6.OnCardDataError(r7)
            goto L4e
        L2e:
            int r0 = r0 - r1
            if (r0 <= 0) goto L4e
            byte[] r3 = r6.m_completeData
            if (r3 != 0) goto L3d
            byte[] r3 = new byte[r0]
            r6.m_completeData = r3
            java.lang.System.arraycopy(r7, r1, r3, r2, r0)
            goto L4e
        L3d:
            int r4 = r3.length
            int r4 = r4 + r0
            byte[] r4 = new byte[r4]
            int r5 = r3.length
            java.lang.System.arraycopy(r3, r2, r4, r2, r5)
            byte[] r2 = r6.m_completeData
            int r2 = r2.length
            java.lang.System.arraycopy(r7, r1, r4, r2, r0)
            r6.m_completeData = r4
        L4e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtlib.MTBLEService.OnCardDataReceived(byte[]):void");
    }

    protected void registerReceiver() {
        if (this.m_receiverRegistered) {
            return;
        }
        this.m_receiverRegistered = true;
        this.m_context.registerReceiver(this.mBondingBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
    }

    protected void unregisterReceiver() {
        if (this.m_receiverRegistered) {
            this.m_receiverRegistered = false;
            this.m_context.unregisterReceiver(this.mBondingBroadcastReceiver);
        }
    }

    public void reconnect() {
        Log.i(TAG, "Reconnect to Gatt...");
        connectGatt();
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionRetry(boolean z) {
        if (this.m_state != MTServiceState.Disconnected) {
            return;
        }
        this.m_ConnectionRetry = z;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void connect() {
        if (this.m_state != MTServiceState.Disconnected) {
            Log.i(TAG, "Service is not disconnected");
        } else {
            setDisconnectRequested(false);
            connectGatt();
        }
    }

    public void connectGatt() {
        setState(MTServiceState.Connecting);
        if (this.m_bluetoothManager == null) {
            BluetoothManager bluetoothManager = (BluetoothManager) this.m_context.getSystemService("bluetooth");
            this.m_bluetoothManager = bluetoothManager;
            if (bluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                handleConnectionFailure();
                setState(MTServiceState.Disconnected);
                return;
            }
        }
        BluetoothAdapter adapter = this.m_bluetoothManager.getAdapter();
        this.m_bluetoothAdapter = adapter;
        if (adapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            handleConnectionFailure();
            setState(MTServiceState.Disconnected);
            return;
        }
        if (this.m_address == null) {
            Log.w(TAG, "Unspecified address.");
            handleConnectionFailure();
            setState(MTServiceState.Disconnected);
            return;
        }
        if (this.m_connectedAddress != null && this.m_address.equals(this.m_connectedAddress) && this.m_bluetoothGatt != null) {
            String str = TAG;
            Log.i(str, "Close Gatt Client");
            this.m_bluetoothGatt.close();
            Log.d(str, "Unload the existing mBluetoothGatt instance");
            this.m_bluetoothGatt = null;
        }
        this.m_notificationFailure = 0;
        this.m_pendingSetupNotification = false;
        BluetoothDevice remoteDevice = this.m_bluetoothAdapter.getRemoteDevice(this.m_address);
        if (remoteDevice == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            handleConnectionFailure();
            setState(MTServiceState.Disconnected);
            return;
        }
        this.m_receiverRegistered = false;
        String str2 = TAG;
        Log.i(str2, "Connect to Gatt...");
        BluetoothGatt bluetoothGattConnectGatt = remoteDevice.connectGatt(this.m_context, false, this.m_gattCallback);
        this.m_bluetoothGatt = bluetoothGattConnectGatt;
        if (bluetoothGattConnectGatt == null) {
            Log.w(str2, "Unable to connect to Gatt.  Disconnecting...");
            handleConnectionFailure();
            setState(MTServiceState.Disconnecting);
            Message message = new Message();
            message.what = 5;
            this.mPendingCommandHandler.sendMessageDelayed(message, 1000L);
        }
        this.m_connectedAddress = this.m_address;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionTimeout(int i) {
        this.mConnectionTimeout = i;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void disconnect() {
        if (this.m_state == MTServiceState.Disconnected || this.m_state == MTServiceState.Disconnecting) {
            return;
        }
        if (this.m_state == MTServiceState.Connecting) {
            if (isDisconnectRequested()) {
                return;
            }
            setDisconnectRequested(true);
            StartConnectingTimeout();
            return;
        }
        disconnectGatt();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisconnectRequested(boolean z) {
        Log.i(TAG, "setDisconnectRequest=" + z);
        this.mDisconnectRequested = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDisconnectRequested() {
        return this.mDisconnectRequested;
    }

    public void disconnectGatt() {
        String str = TAG;
        Log.i(str, "disconnectGatt");
        if (this.m_bluetoothAdapter == null) {
            Log.w(str, "BluetoothAdapter not initialized");
            return;
        }
        if (this.m_bluetoothGatt != null) {
            Log.i(str, "MTBLEService Disconnect Called Gatt disconnect");
            this.m_bluetoothGatt.disconnect();
        } else {
            Log.i(str, "Gatt is NULL");
        }
        setState(MTServiceState.Disconnected);
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean sendData(byte[] bArr) {
        new Runnable(bArr) { // from class: com.magtek.mobile.android.mtlib.MTBLEService.4
            byte[] dataToSend;
            final /* synthetic */ byte[] val$data;

            {
                this.val$data = bArr;
                this.dataToSend = (byte[]) bArr.clone();
            }

            @Override // java.lang.Runnable
            public void run() {
                Log.i(MTBLEService.TAG, "sendData");
                MTBLEService.this.sSendLock.lock();
                MTBLEService.this.writeCommand(this.dataToSend);
                try {
                    Thread.sleep(500L);
                } catch (Exception unused) {
                }
                MTBLEService.this.sSendLock.unlock();
            }
        }.run();
        return true;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDevicePMValue() {
        return this.m_serviceUUID.compareTo(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_READER_SERVICE) == 0 ? "PM2" : this.m_serviceUUID.compareTo(MTDeviceConstants.UUID_SCRA_BLE_EMV_DEVICE_READER_SERVICE) == 0 ? "PM3" : this.m_serviceUUID.compareTo(MTDeviceConstants.UUID_SCRA_BLE_EMV_T_DEVICE_READER_SERVICE) == 0 ? "PM5" : "";
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public MTDeviceFeatures getDeviceFeatures() {
        MTDeviceFeatures mTDeviceFeatures = new MTDeviceFeatures();
        mTDeviceFeatures.MSR = true;
        if (this.m_serviceUUID.compareTo(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_READER_SERVICE) == 0) {
            mTDeviceFeatures.BatteryBackedClock = true;
        } else if (this.m_serviceUUID.compareTo(MTDeviceConstants.UUID_SCRA_BLE_EMV_DEVICE_READER_SERVICE) == 0) {
            mTDeviceFeatures.Contact = true;
            mTDeviceFeatures.BatteryBackedClock = true;
        } else if (this.m_serviceUUID.compareTo(MTDeviceConstants.UUID_SCRA_BLE_EMV_T_DEVICE_READER_SERVICE) == 0) {
            mTDeviceFeatures.Contact = true;
            mTDeviceFeatures.Contactless = true;
            mTDeviceFeatures.MSRPowerSaver = true;
        }
        return mTDeviceFeatures;
    }

    protected boolean isPM2Device() {
        try {
            return getDevicePMValue().equalsIgnoreCase("PM2");
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public long getBatteryLevel() {
        long j = MTDeviceConstants.BATTERY_LEVEL_NA;
        if (this.m_state != MTServiceState.Connected) {
            return j;
        }
        this.m_batteryLevelObject = new Object();
        this.m_batteryLevel = MTDeviceConstants.BATTERY_LEVEL_NA;
        synchronized (this.m_batteryLevelObject) {
            try {
                if (isPM2Device()) {
                    sendData(MTDeviceConstants.SCRA_DEVICE_COMMAND_BLE_GET_BATTERY_LEVEL);
                } else {
                    sendData(MTDeviceConstants.SCRA_DEVICE_COMMAND_GET_BATTERY_LEVEL);
                }
                this.m_batteryLevelObject.wait(AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
            } catch (InterruptedException unused) {
            }
        }
        long j2 = this.m_batteryLevel;
        this.m_batteryLevelObject = null;
        return j2;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDeviceName() {
        if (this.m_state != MTServiceState.Connected) {
            return "";
        }
        Object obj = new Object();
        this.m_deviceNameObject = obj;
        this.m_deviceName = "";
        synchronized (obj) {
            try {
                sendData(MTDeviceConstants.SCRA_DEVICE_COMMAND_BLE_GET_DEVICE_NAME);
                this.m_deviceNameObject.wait(AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
            } catch (InterruptedException unused) {
            }
        }
        String str = this.m_deviceName;
        this.m_deviceNameObject = null;
        return str;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getFirmwareID() {
        if (this.m_state != MTServiceState.Connected) {
            return "";
        }
        Object obj = new Object();
        this.m_firmwareIDObject = obj;
        this.m_firmwareID = "";
        synchronized (obj) {
            try {
                sendData(MTDeviceConstants.SCRA_DEVICE_COMMAND_GET_FIRMWARE_ID);
                this.m_firmwareIDObject.wait(AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
            } catch (InterruptedException unused) {
            }
        }
        String str = this.m_firmwareID;
        this.m_firmwareIDObject = null;
        return str;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDeviceSerial() {
        if (this.m_state != MTServiceState.Connected) {
            return "";
        }
        Object obj = new Object();
        this.m_deviceSerialObject = obj;
        this.m_deviceSerial = "";
        synchronized (obj) {
            try {
                sendData(MTDeviceConstants.SCRA_DEVICE_COMMAND_GET_DEVICE_SN);
                this.m_deviceSerialObject.wait(AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
            } catch (InterruptedException unused) {
            }
        }
        String str = this.m_deviceSerial;
        this.m_deviceSerialObject = null;
        return str;
    }
}

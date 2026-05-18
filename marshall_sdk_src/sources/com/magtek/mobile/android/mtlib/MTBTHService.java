package com.magtek.mobile.android.mtlib;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MTBTHService extends MTBaseService {
    private static final int MAX_READ_SIZE = 1024;
    private static final String MTBTHSERVICE_NAME = "MTBTHSERVICE";
    private static final String TAG = "MTBTHService";
    private BluetoothAdapter m_bluetoothAdapter = null;
    private ConnectThread m_connectThread;
    private ConnectedThread m_connectedThread;

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDeviceName() {
        return "MagTek MSR Bluetooth Device";
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDevicePMValue() {
        return "PM1";
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionRetry(boolean z) {
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionTimeout(int i) {
    }

    protected void OnDataReceived(byte[] bArr) {
        if (bArr != null) {
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            Log.i(TAG, "Data Copy Length=" + bArrCopyOf.length);
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnCardData(bArrCopyOf);
            }
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public MTDeviceFeatures getDeviceFeatures() {
        MTDeviceFeatures mTDeviceFeatures = new MTDeviceFeatures();
        mTDeviceFeatures.MSR = true;
        return mTDeviceFeatures;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void connect() {
        setState(MTServiceState.Connecting);
        Log.i(TAG, "connecting");
        ConnectThread connectThread = this.m_connectThread;
        if (connectThread != null) {
            connectThread.cancel();
            this.m_connectThread = null;
        }
        ConnectedThread connectedThread = this.m_connectedThread;
        if (connectedThread != null) {
            connectedThread.cancel();
            this.m_connectedThread = null;
        }
        if (this.m_connectThread == null) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            this.m_bluetoothAdapter = defaultAdapter;
            if (defaultAdapter == null) {
                setState(MTServiceState.Disconnected);
                return;
            }
            BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(this.m_address);
            if (remoteDevice == null) {
                setState(MTServiceState.Disconnected);
                return;
            }
            ConnectThread connectThread2 = new ConnectThread(remoteDevice);
            this.m_connectThread = connectThread2;
            connectThread2.start();
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void disconnect() {
        if (this.m_state == MTServiceState.Disconnecting || this.m_state == MTServiceState.Disconnected) {
            return;
        }
        stopThreads();
        setState(MTServiceState.Disconnected);
    }

    private void stopThreads() {
        ConnectThread connectThread = this.m_connectThread;
        if (connectThread != null) {
            connectThread.cancel();
            this.m_connectThread = null;
        }
        ConnectedThread connectedThread = this.m_connectedThread;
        if (connectedThread != null) {
            connectedThread.cancel();
            this.m_connectedThread = null;
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean sendData(byte[] bArr) {
        ConnectedThread connectedThread;
        byte[] bytes;
        int length;
        if (this.m_state != MTServiceState.Connected) {
            return true;
        }
        synchronized (this) {
            connectedThread = this.m_connectedThread;
        }
        if (connectedThread == null || bArr == null || bArr.length <= 0 || (bytes = MTParser.getHexString(bArr).getBytes()) == null || (length = bytes.length) <= 0) {
            return true;
        }
        byte[] bArr2 = new byte[length + 1];
        System.arraycopy(bytes, 0, bArr2, 0, length);
        bArr2[length] = 13;
        connectedThread.write(bArr2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void OnConnected(BluetoothSocket bluetoothSocket, BluetoothDevice bluetoothDevice) {
        ConnectThread connectThread = this.m_connectThread;
        if (connectThread != null) {
            connectThread.cancel();
            this.m_connectThread = null;
        }
        ConnectedThread connectedThread = this.m_connectedThread;
        if (connectedThread != null) {
            connectedThread.cancel();
            this.m_connectedThread = null;
        }
        ConnectedThread connectedThread2 = new ConnectedThread(bluetoothSocket);
        this.m_connectedThread = connectedThread2;
        connectedThread2.start();
        setState(MTServiceState.Connected);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void OnConnectionFailed() {
        stopThreads();
        setState(MTServiceState.Disconnected);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void OnConnectionLost() {
        stopThreads();
        setState(MTServiceState.Disconnected);
    }

    private class ConnectThread extends Thread {
        private final BluetoothDevice m_btDevice;
        private final BluetoothSocket m_btSocket;

        public ConnectThread(BluetoothDevice bluetoothDevice) {
            BluetoothSocket bluetoothSocketCreateRfcommSocketToServiceRecord;
            this.m_btDevice = bluetoothDevice;
            try {
                bluetoothSocketCreateRfcommSocketToServiceRecord = bluetoothDevice.createRfcommSocketToServiceRecord(MTBTHService.this.m_serviceUUID);
            } catch (IOException unused) {
                bluetoothSocketCreateRfcommSocketToServiceRecord = null;
            }
            this.m_btSocket = bluetoothSocketCreateRfcommSocketToServiceRecord;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("ConnectThread");
            if (MTBTHService.this.m_bluetoothAdapter != null) {
                MTBTHService.this.m_bluetoothAdapter.cancelDiscovery();
            }
            try {
                BluetoothSocket bluetoothSocket = this.m_btSocket;
                if (bluetoothSocket != null) {
                    bluetoothSocket.connect();
                }
                synchronized (MTBTHService.this) {
                    MTBTHService.this.m_connectThread = null;
                }
                MTBTHService.this.OnConnected(this.m_btSocket, this.m_btDevice);
            } catch (IOException unused) {
                MTBTHService.this.OnConnectionFailed();
                try {
                    this.m_btSocket.close();
                } catch (IOException unused2) {
                }
            }
        }

        public void cancel() {
            try {
                this.m_btSocket.close();
            } catch (IOException unused) {
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket m_btSocket;
        private final InputStream m_inputStream;
        private final OutputStream m_outputStream;

        public ConnectedThread(BluetoothSocket bluetoothSocket) {
            InputStream inputStream;
            OutputStream outputStream;
            this.m_btSocket = bluetoothSocket;
            OutputStream outputStream2 = null;
            InputStream inputStream2 = null;
            if (bluetoothSocket != null) {
                try {
                    inputStream = bluetoothSocket.getInputStream();
                    try {
                        outputStream = bluetoothSocket.getOutputStream();
                        inputStream2 = inputStream;
                    } catch (IOException unused) {
                        MTBTHService.this.OnConnectionFailed();
                    }
                } catch (IOException unused2) {
                    inputStream = null;
                }
            } else {
                outputStream = null;
            }
            inputStream = inputStream2;
            outputStream2 = outputStream;
            this.m_inputStream = inputStream;
            this.m_outputStream = outputStream2;
        }

        public void handleData(byte[] bArr) {
            if (MTBTHService.this.m_serviceAdapter != null) {
                MTBTHService.this.m_serviceAdapter.OnCardData(bArr);
            }
            if (MTBTHService.this.m_serviceAdapter != null) {
                MTBTHService.this.m_serviceAdapter.OnCommandData(bArr);
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            byte[] bArr;
            int i;
            while (true) {
                try {
                    InputStream inputStream = this.m_inputStream;
                    if (inputStream != null && (i = inputStream.read((bArr = new byte[1024]))) > 0) {
                        byte[] bArrCopyOf = Arrays.copyOf(bArr, i);
                        Log.i(MTBTHService.TAG, "Data Copy Length=" + bArrCopyOf.length);
                        handleData(bArrCopyOf);
                    }
                } catch (IOException unused) {
                    MTBTHService.this.OnConnectionLost();
                    return;
                }
            }
        }

        public void write(byte[] bArr) {
            try {
                OutputStream outputStream = this.m_outputStream;
                if (outputStream == null || bArr == null || bArr.length <= 0) {
                    return;
                }
                outputStream.write(bArr);
                Log.i(MTBTHService.TAG, "BT Write Length=" + bArr.length);
            } catch (IOException unused) {
            }
        }

        public void cancel() {
            try {
                BluetoothSocket bluetoothSocket = this.m_btSocket;
                if (bluetoothSocket != null) {
                    bluetoothSocket.close();
                }
            } catch (IOException unused) {
            }
        }
    }
}

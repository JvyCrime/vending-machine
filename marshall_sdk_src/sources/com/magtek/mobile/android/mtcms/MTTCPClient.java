package com.magtek.mobile.android.mtcms;

import android.util.Log;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MTTCPClient implements IMTTCPClient {
    private static final int READ_BUFFER_SIZE = 1024;
    private static final String TAG = "MTTCPClient";
    protected MTTCPClientAdapter mAdapter;
    protected String mAddress;
    protected int mConnectTimeout;
    protected InputStream mIn;
    protected OutputStream mOut;
    protected int mPort;
    protected boolean mRun = false;
    protected Socket mSocket = null;
    protected byte[] mOutputData = null;

    public MTTCPClient(String str, int i, int i2, MTTCPClientAdapter mTTCPClientAdapter) {
        this.mAdapter = null;
        this.mAddress = str;
        this.mPort = i;
        this.mConnectTimeout = i2;
        this.mAdapter = mTTCPClientAdapter;
    }

    @Override // com.magtek.mobile.android.mtcms.IMTTCPClient
    public void sendData(byte[] bArr) {
        if (bArr == null || this.mOut == null || bArr == null) {
            return;
        }
        this.mOutputData = Arrays.copyOfRange(bArr, 0, bArr.length);
        new Thread() { // from class: com.magtek.mobile.android.mtcms.MTTCPClient.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Log.i(MTTCPClient.TAG, "Write: " + MTParser.getHexString(MTTCPClient.this.mOutputData));
                    MTTCPClient.this.mOut.write(MTTCPClient.this.mOutputData);
                    MTTCPClient.this.mOut.flush();
                } catch (Exception e) {
                    Log.e(MTTCPClient.TAG, "MTTCPClient sendData Exception" + e.getMessage());
                }
            }
        }.start();
    }

    @Override // com.magtek.mobile.android.mtcms.IMTTCPClient
    public void stopClient() {
        String str = TAG;
        Log.i(str, "Disconnecting");
        this.mRun = false;
        Socket socket = this.mSocket;
        if (socket != null) {
            try {
                socket.shutdownInput();
            } catch (Exception unused) {
            }
        } else if (this.mAdapter != null) {
            Log.i(str, "Sending Disconnected State");
            this.mAdapter.OnDisconnected();
        }
    }

    @Override // com.magtek.mobile.android.mtcms.IMTTCPClient
    public void startClient() {
        Socket socket;
        this.mRun = true;
        try {
            InetAddress byName = InetAddress.getByName(this.mAddress);
            String str = TAG;
            Log.i(str, "Connecting...");
            InetSocketAddress inetSocketAddress = new InetSocketAddress(byName, this.mPort);
            Socket socket2 = new Socket();
            this.mSocket = socket2;
            try {
                socket2.connect(inetSocketAddress, this.mConnectTimeout);
                this.mOut = this.mSocket.getOutputStream();
                this.mIn = this.mSocket.getInputStream();
                Log.i(str, "Connected");
                MTTCPClientAdapter mTTCPClientAdapter = this.mAdapter;
                if (mTTCPClientAdapter != null) {
                    mTTCPClientAdapter.OnConnected();
                }
                byte[] bArr = new byte[1024];
                while (this.mRun) {
                    Arrays.fill(bArr, (byte) 0);
                    int i = this.mIn.read(bArr, 0, 1024);
                    if (i > 0) {
                        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, i);
                        Log.i(TAG, "Received: " + MTParser.getHexString(bArrCopyOfRange));
                        MTTCPClientAdapter mTTCPClientAdapter2 = this.mAdapter;
                        if (mTTCPClientAdapter2 != null) {
                            mTTCPClientAdapter2.OnDataReceived(bArrCopyOfRange);
                        }
                    }
                }
                String str2 = TAG;
                Log.i(str2, "Stopping Reading Data");
                Log.i(str2, "Closing socket...");
                socket = this.mSocket;
            } catch (Exception unused) {
                Log.i(TAG, "Closing socket...");
                socket = this.mSocket;
            } catch (Throwable th) {
                Log.i(TAG, "Closing socket...");
                this.mSocket.close();
                this.mSocket = null;
                throw th;
            }
            socket.close();
            this.mSocket = null;
            if (this.mAdapter != null) {
                Log.i(TAG, "Sending Disconnected State");
                this.mAdapter.OnDisconnected();
            }
        } catch (Exception unused2) {
        }
    }
}

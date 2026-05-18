package com.magtek.mobile.android.ppscra;

import android.util.Log;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MTTCPSocket implements IMTSocket {
    private static final String a = "MTTCPSocket";
    protected MTSocketAdapter mAdapter;
    protected String mAddress;
    protected InputStream mIn;
    protected OutputStream mOut;
    protected int mPort;
    protected boolean mRun = false;
    protected Socket mSocket = null;
    protected ServerSocket mServerSocket = null;

    @Override // com.magtek.mobile.android.ppscra.IMTSocket
    public void setClientCertificate(String str, byte[] bArr, String str2) {
    }

    @Override // com.magtek.mobile.android.ppscra.IMTSocket
    public void setTrustAll(boolean z) {
    }

    public MTTCPSocket(String str, int i, MTSocketAdapter mTSocketAdapter) {
        this.mAdapter = null;
        this.mAddress = str;
        this.mPort = i;
        this.mAdapter = mTSocketAdapter;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTSocket
    public void sendData(byte[] bArr) {
        if (this.mOut != null) {
            try {
                Log.i(a, "Write: " + PPSCRACommon.getHexString(bArr));
                this.mOut.write(bArr);
                this.mOut.flush();
            } catch (Exception e) {
                Log.i(a, "Send Data exception: " + e.getMessage());
            }
        }
    }

    protected void keepConnection() {
        try {
            this.mOut = this.mSocket.getOutputStream();
            this.mIn = this.mSocket.getInputStream();
            Log.i(a, "Connected");
            MTSocketAdapter mTSocketAdapter = this.mAdapter;
            if (mTSocketAdapter != null) {
                mTSocketAdapter.OnConnected();
            }
            byte[] bArr = new byte[64];
            while (this.mRun) {
                Arrays.fill(bArr, (byte) 0);
                int i = this.mIn.read(bArr, 0, 64);
                if (i > 0) {
                    byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, i);
                    Log.i(a, "Received: " + PPSCRACommon.getHexString(bArrCopyOfRange));
                    MTSocketAdapter mTSocketAdapter2 = this.mAdapter;
                    if (mTSocketAdapter2 != null) {
                        mTSocketAdapter2.OnDataReceived(bArrCopyOfRange);
                    }
                } else {
                    Log.i(a, "*** Received 0");
                    this.mRun = false;
                }
            }
            Log.i(a, "Stopping Reading Data");
        } catch (Exception e) {
            Log.e(a, "Exception: " + e.getMessage());
        }
        MTSocketAdapter mTSocketAdapter3 = this.mAdapter;
        if (mTSocketAdapter3 != null) {
            mTSocketAdapter3.OnDisconnecting();
        }
        if (this.mSocket != null) {
            Log.i(a, "Closing socket...");
            try {
                this.mSocket.close();
            } catch (Exception e2) {
                Log.e(a, "Exception: " + e2.getMessage());
            }
            this.mSocket = null;
        }
        MTSocketAdapter mTSocketAdapter4 = this.mAdapter;
        if (mTSocketAdapter4 != null) {
            mTSocketAdapter4.OnDisconnected();
        }
    }

    @Override // com.magtek.mobile.android.ppscra.IMTSocket
    public void startClient(int i) {
        this.mRun = true;
        try {
            InetAddress byName = InetAddress.getByName(this.mAddress);
            Log.i(a, "Connecting...");
            MTSocketAdapter mTSocketAdapter = this.mAdapter;
            if (mTSocketAdapter != null) {
                mTSocketAdapter.OnConnecting();
            }
            InetSocketAddress inetSocketAddress = new InetSocketAddress(byName, this.mPort);
            Socket socket = new Socket();
            this.mSocket = socket;
            if (socket != null) {
                socket.connect(inetSocketAddress, i);
                keepConnection();
            }
        } catch (Exception e) {
            Log.i(a, "Client Socket exception: " + e.getMessage());
            MTSocketAdapter mTSocketAdapter2 = this.mAdapter;
            if (mTSocketAdapter2 != null) {
                mTSocketAdapter2.OnDisconnected();
            }
        }
    }

    @Override // com.magtek.mobile.android.ppscra.IMTSocket
    public void stopClient() {
        Log.i(a, "Disconnecting");
        this.mRun = false;
        MTSocketAdapter mTSocketAdapter = this.mAdapter;
        if (mTSocketAdapter != null) {
            mTSocketAdapter.OnDisconnecting();
        }
        if (this.mSocket != null) {
            new Thread() { // from class: com.magtek.mobile.android.ppscra.MTTCPSocket.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        MTTCPSocket.this.mSocket.shutdownInput();
                    } catch (Exception e) {
                        Log.e(MTTCPSocket.a, "MTTCPSocket stopClient Exception" + e.getMessage());
                        MTTCPSocket.this.mSocket = null;
                        if (MTTCPSocket.this.mAdapter != null) {
                            MTTCPSocket.this.mAdapter.OnDisconnected();
                        }
                    }
                }
            }.start();
            return;
        }
        MTSocketAdapter mTSocketAdapter2 = this.mAdapter;
        if (mTSocketAdapter2 != null) {
            mTSocketAdapter2.OnDisconnected();
        }
    }

    public void startListening() {
        String str = a;
        Log.i(str, "Start Listening...");
        this.mRun = true;
        try {
            MTSocketAdapter mTSocketAdapter = this.mAdapter;
            if (mTSocketAdapter != null) {
                mTSocketAdapter.OnListening();
            }
            ServerSocket serverSocket = new ServerSocket(this.mPort, 1);
            this.mServerSocket = serverSocket;
            this.mSocket = serverSocket.accept();
            Log.i(str, "Incoming connection accepted");
            this.mServerSocket.close();
            this.mServerSocket = null;
            if (this.mSocket != null) {
                MTSocketAdapter mTSocketAdapter2 = this.mAdapter;
                if (mTSocketAdapter2 != null) {
                    mTSocketAdapter2.OnConnecting();
                }
                if (this.mSocket != null) {
                    keepConnection();
                    return;
                }
                return;
            }
            MTSocketAdapter mTSocketAdapter3 = this.mAdapter;
            if (mTSocketAdapter3 != null) {
                mTSocketAdapter3.OnDisconnected();
            }
        } catch (Exception e) {
            Log.i(a, "Server Socket exception: " + e.getMessage());
            this.mSocket = null;
            this.mServerSocket = null;
            MTSocketAdapter mTSocketAdapter4 = this.mAdapter;
            if (mTSocketAdapter4 != null) {
                mTSocketAdapter4.OnDisconnected();
            }
        }
    }

    public void stopListening() {
        Log.i(a, "Stop Listening");
        MTSocketAdapter mTSocketAdapter = this.mAdapter;
        if (mTSocketAdapter != null) {
            mTSocketAdapter.OnDisconnecting();
        }
        this.mRun = false;
        ServerSocket serverSocket = this.mServerSocket;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (Exception unused) {
            }
            this.mAdapter.OnDisconnected();
        }
    }
}

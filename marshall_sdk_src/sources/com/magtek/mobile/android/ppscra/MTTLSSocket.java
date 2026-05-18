package com.magtek.mobile.android.ppscra;

import android.util.Log;
import iaik.security.jsse.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public class MTTLSSocket implements IMTSocket {
    private static final String a = "MTTLSSocket";
    protected MTSocketAdapter mAdapter;
    protected String mAddress;
    protected InputStream mIn;
    protected OutputStream mOut;
    protected int mPort;
    private String b = "";
    private byte[] c = null;
    private String d = "";
    private boolean e = false;
    protected boolean mRun = false;
    protected byte[] mOutputData = null;
    protected Socket mSocket = null;
    protected SSLSocket mSSLSocket = null;
    protected TrustManager[] mTrustAllTrustManager = {new X509TrustManager() { // from class: com.magtek.mobile.android.ppscra.MTTLSSocket.1
        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            Log.i("TrustAllTrustManager", "getAcceptedIssuers");
            return null;
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            Log.i("TrustAllTrustManager", "checkClientTrusted");
            Log.i("TrustAllTrustManager", "arg1=" + str);
            Log.i("TrustAllTrustManager", "cert=" + x509CertificateArr);
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            Log.i("TrustAllTrustManager", "checkServerTrusted");
            Log.i("TrustAllTrustManager", "arg1=" + str);
            for (X509Certificate x509Certificate : x509CertificateArr) {
                Log.i("TrustAllTrustManager", "name=" + x509Certificate.getSubjectX500Principal().getName());
                Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
                if (subjectAlternativeNames != null) {
                    for (List<?> list : subjectAlternativeNames) {
                        if (((Integer) list.get(0)).intValue() == 2) {
                            Log.i("TrustAllTrustManager", "dns-name=" + ((String) list.get(1)));
                        }
                    }
                }
            }
            Log.i("TrustAllTrustManager", "cert=" + x509CertificateArr);
        }
    }};

    public MTTLSSocket(String str, int i, MTSocketAdapter mTSocketAdapter) {
        this.mAdapter = null;
        this.mAddress = str;
        this.mPort = i;
        this.mAdapter = mTSocketAdapter;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTSocket
    public void setClientCertificate(String str, byte[] bArr, String str2) {
        this.b = str;
        this.c = bArr;
        this.d = str2;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTSocket
    public void setTrustAll(boolean z) {
        this.e = z;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTSocket
    public void sendData(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        this.mOutputData = Arrays.copyOfRange(bArr, 0, bArr.length);
        new Thread() { // from class: com.magtek.mobile.android.ppscra.MTTLSSocket.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (MTTLSSocket.this.mOut != null) {
                    try {
                        MTTLSSocket.this.mOut.write(MTTLSSocket.this.mOutputData);
                        MTTLSSocket.this.mOut.flush();
                    } catch (Exception e) {
                        Log.e(MTTLSSocket.a, "SSLClient sendData Exception" + e.getMessage());
                    }
                }
            }
        }.start();
    }

    protected void keepConnection() {
        try {
            String str = a;
            Log.i(str, "Connecting...");
            MTSocketAdapter mTSocketAdapter = this.mAdapter;
            if (mTSocketAdapter != null) {
                mTSocketAdapter.OnConnecting();
            }
            Log.i(str, "Initializing Client Certificate Key Store...");
            KeyStore keyStore = KeyStore.getInstance(this.b);
            keyStore.load(new ByteArrayInputStream(this.c), this.d.toCharArray());
            Log.i(str, "Initializing Key Managers...");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, this.d.toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
            Log.i(str, "Loading CA KeyStore...");
            KeyStore keyStore2 = KeyStore.getInstance("AndroidCAStore");
            keyStore2.load(null, null);
            try {
                Provider[] providers = Security.getProviders();
                if (providers != null) {
                    for (Provider provider : providers) {
                        if (provider != null) {
                            String str2 = a;
                            Log.i(str2, "Provider: " + provider.getName());
                            Log.i(str2, "    Info: " + provider.getInfo());
                            Log.i(str2, " Version: " + provider.getVersion());
                        }
                    }
                }
                SSLContext sSLContext = SSLContext.getInstance(Util.TLSv12);
                Provider provider2 = sSLContext.getProvider();
                if (provider2 != null) {
                    String str3 = a;
                    Log.i(str3, "Selected Provider: " + provider2.getName());
                    Log.i(str3, "             Info: " + provider2.getInfo());
                    Log.i(str3, "          Version: " + provider2.getVersion());
                }
                if (this.e) {
                    Log.i(a, "TLS Trust All ***");
                    sSLContext.init(keyManagers, this.mTrustAllTrustManager, new SecureRandom());
                } else {
                    Log.i(a, "Initializing Trust Manager...");
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init(keyStore2);
                    sSLContext.init(keyManagers, trustManagerFactory.getTrustManagers(), new SecureRandom());
                }
                SSLSocketFactory socketFactory = sSLContext.getSocketFactory();
                String str4 = a;
                Log.i(str4, "Creating SSL socket...");
                SSLSocket sSLSocket = (SSLSocket) socketFactory.createSocket(this.mSocket, this.mAddress, this.mPort, false);
                this.mSSLSocket = sSLSocket;
                sSLSocket.setEnabledProtocols(new String[]{Util.TLSv12});
                this.mSSLSocket.setEnabledCipherSuites(this.mSSLSocket.getSupportedCipherSuites());
                Log.i(str4, "Starting SSL handshake...");
                this.mSSLSocket.startHandshake();
                Log.i(str4, "SSLSocket handshake completed");
                SSLSession session = this.mSSLSocket.getSession();
                if (session != null) {
                    Log.i(str4, "PeerHost: " + session.getPeerHost());
                    Log.i(str4, "PeerPrincipal: " + session.getPeerPrincipal());
                    HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
                    if (!this.e && !defaultHostnameVerifier.verify(this.mAddress, session)) {
                        throw new SSLHandshakeException("Hostname Verification Exception for " + this.mAddress);
                    }
                }
                this.mOut = this.mSSLSocket.getOutputStream();
                this.mIn = this.mSSLSocket.getInputStream();
                Log.i(str4, "Connected");
                MTSocketAdapter mTSocketAdapter2 = this.mAdapter;
                if (mTSocketAdapter2 != null) {
                    mTSocketAdapter2.OnConnected();
                }
                byte[] bArr = new byte[64];
                while (this.mRun) {
                    Arrays.fill(bArr, (byte) 0);
                    int i = this.mIn.read(bArr, 0, 64);
                    if (i > 0) {
                        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, i);
                        Log.i(a, "Received: " + PPSCRACommon.getHexString(bArrCopyOfRange));
                        MTSocketAdapter mTSocketAdapter3 = this.mAdapter;
                        if (mTSocketAdapter3 != null) {
                            mTSocketAdapter3.OnDataReceived(bArrCopyOfRange);
                        }
                    } else {
                        Log.i(a, "*** Received 0");
                        this.mRun = false;
                    }
                }
                Log.i(a, "Stopping Reading Data");
            } catch (Exception e) {
                String str5 = a;
                Log.e(str5, "Exception: " + e.getMessage());
                SSLParameters sSLParameters = this.mSSLSocket.getSSLParameters();
                if (sSLParameters != null) {
                    Log.i(str5, "SSLParams" + sSLParameters.toString());
                }
                SSLSession session2 = this.mSSLSocket.getSession();
                if (session2 != null) {
                    Log.i(str5, "SSLSession: " + session2.toString());
                }
            }
        } catch (Exception e2) {
            Log.e(a, "Exception: " + e2.getMessage());
        }
        MTSocketAdapter mTSocketAdapter4 = this.mAdapter;
        if (mTSocketAdapter4 != null) {
            mTSocketAdapter4.OnDisconnecting();
        }
        if (this.mSSLSocket != null) {
            Log.i(a, "Closing TLS socket...");
            try {
                this.mSSLSocket.close();
            } catch (Exception e3) {
                Log.e(a, "Exception: " + e3.getMessage());
            }
            this.mSSLSocket = null;
        }
        if (this.mSocket != null) {
            Log.i(a, "Closing socket...");
            try {
                this.mSocket.close();
            } catch (Exception e4) {
                Log.e(a, "Exception: " + e4.getMessage());
            }
            this.mSocket = null;
        }
        MTSocketAdapter mTSocketAdapter5 = this.mAdapter;
        if (mTSocketAdapter5 != null) {
            mTSocketAdapter5.OnDisconnected();
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
            new Thread() { // from class: com.magtek.mobile.android.ppscra.MTTLSSocket.3
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        MTTLSSocket.this.mSocket.shutdownInput();
                    } catch (Exception e) {
                        Log.e(MTTLSSocket.a, "MTTLSSocket stopClient Exception" + e.getMessage());
                        MTTLSSocket.this.mSocket = null;
                        if (MTTLSSocket.this.mAdapter != null) {
                            MTTLSSocket.this.mAdapter.OnDisconnected();
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
}

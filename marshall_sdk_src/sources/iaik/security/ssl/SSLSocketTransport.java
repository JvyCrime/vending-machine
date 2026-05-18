package iaik.security.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: classes.dex */
public class SSLSocketTransport extends SSLTransportSpi {
    private Socket a;
    private SSLSocket b;
    protected String remoteHost_;
    protected Object remotePeerId_;

    public SSLSocketTransport(Socket socket, SSLContext sSLContext, boolean z) {
        super(sSLContext, z);
        this.remotePeerId_ = null;
        this.remoteHost_ = null;
        this.a = socket;
        if (socket instanceof SSLSocket) {
            this.b = (SSLSocket) socket;
        }
    }

    @Override // iaik.security.ssl.SSLTransportSpi
    protected InputStream engineGetInputStream() throws IOException {
        SSLSocket sSLSocket = this.b;
        if (sSLSocket != null) {
            return sSLSocket.b();
        }
        return this.a.getInputStream();
    }

    @Override // iaik.security.ssl.SSLTransportSpi
    protected OutputStream engineGetOutputStream() throws IOException {
        SSLSocket sSLSocket = this.b;
        if (sSLSocket != null) {
            return sSLSocket.c();
        }
        return this.a.getOutputStream();
    }

    @Override // iaik.security.ssl.SSLTransportSpi
    protected String engineGetRemotePeerName() {
        if (this.remotePeerId_ != null) {
            return this.remoteHost_;
        }
        String str = this.remoteHost_;
        return str == null ? this.a.getInetAddress().getHostName() : str;
    }

    @Override // iaik.security.ssl.SSLTransportSpi
    protected InetAddress engineGetRemoteInetAddress() {
        if (this.remotePeerId_ != null) {
            try {
                return InetAddress.getByName(this.remoteHost_);
            } catch (UnknownHostException unused) {
                return null;
            }
        }
        return this.a.getInetAddress();
    }

    protected Object convertToRemotePeerId(String str, int i) {
        if (this.useClientMode) {
            StringBuffer stringBuffer = new StringBuffer(String.valueOf(str));
            stringBuffer.append(":");
            stringBuffer.append(i);
            return stringBuffer.toString();
        }
        StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(this.a.getLocalAddress().getHostAddress()));
        stringBuffer2.append(":");
        stringBuffer2.append(this.a.getLocalPort());
        stringBuffer2.append(":");
        stringBuffer2.append(str);
        return stringBuffer2.toString();
    }

    @Override // iaik.security.ssl.SSLTransportSpi
    protected Object engineGetRemotePeerId() {
        Object obj = this.remotePeerId_;
        return obj != null ? obj : convertToRemotePeerId(this.a.getInetAddress().getHostAddress(), this.a.getPort());
    }

    @Override // iaik.security.ssl.SSLTransportSpi
    protected void engineClose() throws IOException {
        SSLSocket sSLSocket = this.b;
        if (sSLSocket != null) {
            sSLSocket.a();
        } else {
            this.a.close();
        }
    }

    void a(String str, int i) {
        this.remotePeerId_ = convertToRemotePeerId(str, i);
        this.remoteHost_ = str;
    }

    void a(String str) {
        this.remoteHost_ = str;
    }

    String a() {
        Object obj = this.remotePeerId_;
        if (obj != null) {
            return (String) obj;
        }
        String str = this.remoteHost_;
        if (str != null) {
            StringBuffer stringBuffer = new StringBuffer(String.valueOf(str));
            stringBuffer.append(":");
            stringBuffer.append(this.a.getPort());
            return stringBuffer.toString();
        }
        StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(this.a.getInetAddress().getHostName()));
        stringBuffer2.append(":");
        stringBuffer2.append(this.a.getPort());
        return stringBuffer2.toString();
    }
}

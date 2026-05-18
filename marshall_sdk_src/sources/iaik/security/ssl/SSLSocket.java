package iaik.security.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes.dex */
public class SSLSocket extends Socket implements SSLCommunication {
    SSLTransport a;
    private Socket b;

    SSLSocket() {
    }

    public SSLSocket(Socket socket, SSLContext sSLContext) throws IOException {
        if (socket instanceof SSLSocket) {
            throw new IllegalArgumentException("Cannot talk SSL over SSL, socket may not be an instance of SSLSocket!");
        }
        this.b = socket;
        init(socket, sSLContext, true);
    }

    public SSLSocket(String str, int i, SSLContext sSLContext) throws IOException {
        super(str, i);
        a(this, sSLContext, true, str, i);
    }

    public SSLSocket(InetAddress inetAddress, int i, SSLContext sSLContext) throws IOException {
        super(inetAddress, i);
        init(this, sSLContext, true);
    }

    public SSLSocket(InetAddress inetAddress, String str, int i, SSLContext sSLContext) throws IOException {
        super(inetAddress, i);
        a(this, sSLContext, true, str, i);
    }

    public SSLSocket(String str, int i, InetAddress inetAddress, int i2, SSLContext sSLContext) throws IOException {
        super(str, i, inetAddress, i2);
        a(this, sSLContext, true, str, i);
    }

    public SSLSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2, SSLContext sSLContext) throws IOException {
        super(inetAddress, i, inetAddress2, i2);
        init(this, sSLContext, true);
    }

    public SSLSocket(InetAddress inetAddress, String str, int i, InetAddress inetAddress2, int i2, SSLContext sSLContext) throws IOException {
        super(inetAddress, i, inetAddress2, i2);
        a(this, sSLContext, true, str, i);
    }

    public SSLSocket(Socket socket, SSLContext sSLContext, String str, int i) throws IOException {
        this.b = socket;
        SSLSocketTransport sSLSocketTransport = new SSLSocketTransport(socket, sSLContext, true);
        sSLSocketTransport.a(str, i);
        this.a = new SSLTransport(sSLSocketTransport);
    }

    public SSLTransport getTransport() {
        return this.a;
    }

    InputStream b() throws IOException {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getInputStream();
        }
        return super.getInputStream();
    }

    OutputStream c() throws IOException {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getOutputStream();
        }
        return super.getOutputStream();
    }

    void a() throws IOException {
        Socket socket = this.b;
        if (socket != null) {
            socket.close();
        } else {
            super.close();
        }
    }

    public void init(Socket socket, SSLContext sSLContext, boolean z) throws IOException {
        a(socket, sSLContext, z, null, -1);
    }

    void a(Socket socket, SSLContext sSLContext, boolean z, String str, int i) throws IOException {
        SSLSocketTransport sSLSocketTransport = new SSLSocketTransport(socket, sSLContext, z);
        sSLSocketTransport.a(str);
        this.a = new SSLTransport(sSLSocketTransport);
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void setUseClientMode(boolean z) throws IOException {
        this.a.setUseClientMode(z);
    }

    @Override // iaik.security.ssl.SSLCommunication
    public boolean getUseClientMode() {
        return this.a.getUseClientMode();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public SSLContext getContext() {
        return this.a.getContext();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void setAutoHandshake(boolean z) {
        this.a.setAutoHandshake(z);
    }

    @Override // iaik.security.ssl.SSLCommunication
    public X509Certificate[] getPeerCertificateChain() {
        return this.a.getPeerCertificateChain();
    }

    public String getPSKIdentity() {
        return this.a.getPSKIdentity();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public int getActiveProtocolVersion() {
        return this.a.getActiveProtocolVersion();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public CipherSuite getActiveCipherSuite() {
        return this.a.getActiveCipherSuite();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public CompressionMethod getActiveCompressionMethod() {
        return this.a.getActiveCompressionMethod();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public CipherSuiteList getPeerSupportedCipherSuiteList() {
        return this.a.getPeerSupportedCipherSuiteList();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public CompressionMethod[] getPeerSupportedCompressionMethods() {
        return this.a.getPeerSupportedCompressionMethods();
    }

    public ExtensionList getPeerExtensions() {
        return this.a.getPeerExtensions();
    }

    public ExtensionList getActiveExtensions() {
        return this.a.getActiveExtensions();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public Session getSession() {
        return this.a.getSession();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void renegotiate() throws IOException {
        this.a.renegotiate();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void startHandshake() throws IOException {
        this.a.startHandshake();
    }

    @Override // java.net.Socket, iaik.security.ssl.SSLCommunication
    public OutputStream getOutputStream() throws IOException {
        return this.a.getOutputStream();
    }

    @Override // java.net.Socket, iaik.security.ssl.SSLCommunication
    public InputStream getInputStream() throws IOException {
        return this.a.getInputStream();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void setDebugStream(Writer writer) {
        this.a.setDebugStream(writer);
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void setDebugStream(OutputStream outputStream) {
        this.a.setDebugStream(outputStream);
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void shutdown() throws IOException {
        this.a.shutdown();
    }

    @Override // java.net.Socket, java.io.Closeable, java.lang.AutoCloseable, iaik.security.ssl.SSLCommunication
    public void close() throws IOException {
        SSLTransport sSLTransport = this.a;
        if (sSLTransport == null) {
            return;
        }
        sSLTransport.close();
    }

    public CipherSuite[] getClientSupportedCipherSuites() {
        return getPeerSupportedCipherSuiteList().toArray();
    }

    public CompressionMethod[] getClientSupportedCompressionMethods() {
        return getPeerSupportedCompressionMethods();
    }

    public boolean isServer() {
        return !getUseClientMode();
    }

    @Override // java.net.Socket
    public InetAddress getInetAddress() {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getInetAddress();
        }
        return super.getInetAddress();
    }

    @Override // java.net.Socket
    public InetAddress getLocalAddress() {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getLocalAddress();
        }
        return super.getLocalAddress();
    }

    @Override // java.net.Socket
    public int getPort() {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getPort();
        }
        return super.getPort();
    }

    @Override // java.net.Socket
    public int getLocalPort() {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getLocalPort();
        }
        return super.getLocalPort();
    }

    @Override // java.net.Socket
    public void setTcpNoDelay(boolean z) throws SocketException {
        Socket socket = this.b;
        if (socket != null) {
            socket.setTcpNoDelay(z);
        } else {
            super.setTcpNoDelay(z);
        }
    }

    @Override // java.net.Socket
    public boolean getTcpNoDelay() throws SocketException {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getTcpNoDelay();
        }
        return super.getTcpNoDelay();
    }

    @Override // java.net.Socket
    public void setSoLinger(boolean z, int i) throws SocketException {
        Socket socket = this.b;
        if (socket != null) {
            socket.setSoLinger(z, i);
        } else {
            super.setSoLinger(z, i);
        }
    }

    @Override // java.net.Socket
    public int getSoLinger() throws SocketException {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getSoLinger();
        }
        return super.getSoLinger();
    }

    @Override // java.net.Socket
    public void setSoTimeout(int i) throws SocketException {
        Socket socket = this.b;
        if (socket != null) {
            socket.setSoTimeout(i);
        } else {
            super.setSoTimeout(i);
        }
    }

    @Override // java.net.Socket
    public int getSoTimeout() throws SocketException {
        Socket socket = this.b;
        if (socket != null) {
            return socket.getSoTimeout();
        }
        return super.getSoTimeout();
    }

    @Override // java.net.Socket
    public final void shutdownInput() throws IOException {
        throw new UnsupportedOperationException("shutdownInput() not supported!");
    }

    @Override // java.net.Socket
    public final void shutdownOutput() throws IOException {
        throw new UnsupportedOperationException("shutdownOutput() not supported!");
    }

    static {
        Utils.a();
    }
}

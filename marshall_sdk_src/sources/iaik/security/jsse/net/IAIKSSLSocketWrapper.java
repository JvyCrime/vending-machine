package iaik.security.jsse.net;

import iaik.security.jsse.utils.Debug;
import iaik.security.jsse.utils.Util;
import iaik.security.ssl.CipherSuite;
import iaik.security.ssl.CipherSuiteList;
import iaik.security.ssl.SSLContext;
import iaik.security.ssl.SSLServerContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImplFactory;
import java.util.Vector;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLSocketWrapper extends SSLSocket {
    private static Debug a = Debug.getInstance();
    private iaik.security.ssl.SSLSocket b;
    private Socket c;
    private SocketAddress d;
    private int e;
    private int f;
    private int g;
    private boolean h;
    private boolean i;
    private Vector j;
    private boolean k;
    private boolean l;
    private boolean m;
    private SSLContext n;
    private SSLSessionWrapper o;
    private String[] p;

    private void c() {
        a.println("jsse_debug: SocketWrapper: Notifying Listeners");
        HandshakeCompletedEvent handshakeCompletedEvent = new HandshakeCompletedEvent(this, getSession());
        for (int i = 0; i < this.j.size(); i++) {
            ((HandshakeCompletedListener) this.j.elementAt(i)).handshakeCompleted(handshakeCompletedEvent);
        }
    }

    IAIKSSLSocketWrapper() {
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = true;
        this.j = new Vector();
        this.k = true;
        this.l = false;
        this.m = true;
        this.o = null;
        this.i = false;
    }

    IAIKSSLSocketWrapper(SSLContext sSLContext) throws IOException {
        this();
        this.n = sSLContext;
    }

    protected IAIKSSLSocketWrapper(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2, SSLContext sSLContext) throws IOException {
        this();
        iaik.security.ssl.SSLSocket sSLSocket = new iaik.security.ssl.SSLSocket(inetAddress, i, inetAddress2, i2, sSLContext);
        this.b = sSLSocket;
        sSLSocket.setAutoHandshake(this.i);
        if (a.getDebugMode() == 0) {
            this.b.setDebugStream(a.getDebugStream());
        }
        this.n = sSLContext;
    }

    protected IAIKSSLSocketWrapper(InetAddress inetAddress, int i, SSLContext sSLContext) throws IOException {
        this();
        iaik.security.ssl.SSLSocket sSLSocket = new iaik.security.ssl.SSLSocket(inetAddress, i, sSLContext);
        this.b = sSLSocket;
        sSLSocket.setAutoHandshake(this.i);
        if (a.getDebugMode() == 0) {
            this.b.setDebugStream(a.getDebugStream());
        }
        this.n = sSLContext;
    }

    protected IAIKSSLSocketWrapper(String str, int i, InetAddress inetAddress, int i2, SSLContext sSLContext) throws IOException {
        this();
        iaik.security.ssl.SSLSocket sSLSocket = new iaik.security.ssl.SSLSocket(str, i, inetAddress, i2, sSLContext);
        this.b = sSLSocket;
        sSLSocket.setAutoHandshake(this.i);
        if (a.getDebugMode() == 0) {
            this.b.setDebugStream(a.getDebugStream());
        }
        this.n = sSLContext;
    }

    protected IAIKSSLSocketWrapper(String str, int i, SSLContext sSLContext) throws IOException {
        this();
        iaik.security.ssl.SSLSocket sSLSocket = new iaik.security.ssl.SSLSocket(str, i, sSLContext);
        this.b = sSLSocket;
        sSLSocket.setAutoHandshake(this.i);
        if (a.getDebugMode() == 0) {
            this.b.setDebugStream(a.getDebugStream());
        }
        this.n = sSLContext;
    }

    protected IAIKSSLSocketWrapper(Socket socket, String str, int i, boolean z, SSLContext sSLContext) throws IOException {
        this();
        this.h = z;
        iaik.security.ssl.SSLSocket sSLSocket = new iaik.security.ssl.SSLSocket(socket, sSLContext, str, i);
        this.b = sSLSocket;
        sSLSocket.setAutoHandshake(this.i);
        if (a.getDebugMode() == 0) {
            this.b.setDebugStream(a.getDebugStream());
        }
        this.n = sSLContext;
    }

    IAIKSSLSocketWrapper(Socket socket, SSLContext sSLContext) throws IOException {
        this();
        iaik.security.ssl.SSLSocket sSLSocket = new iaik.security.ssl.SSLSocket(socket, sSLContext);
        this.b = sSLSocket;
        sSLSocket.setAutoHandshake(this.i);
        if (a.getDebugMode() == 0) {
            this.b.setDebugStream(a.getDebugStream());
        }
        this.n = sSLContext;
        this.c = socket;
    }

    public void init(Socket socket, SSLContext sSLContext, boolean z) throws IOException {
        this.b.init(socket, sSLContext, z);
        this.b.setAutoHandshake(this.i);
        if (a.getDebugMode() == 0) {
            this.b.setDebugStream(a.getDebugStream());
        }
        this.c = socket;
    }

    void a() throws IOException {
        if (this.l) {
            return;
        }
        startHandshake();
    }

    @Override // javax.net.ssl.SSLSocket
    public void startHandshake() throws IOException {
        if (!this.k && ((JSSESessionManager) this.n.getSessionManager()).getSession(this.b.getTransport(), null) == null) {
            throw new RuntimeException("session creation not allowed and no valid session cached");
        }
        if (this.l) {
            a.println("\n **** SocketWrapper RENEGOTIATE ****");
            this.b.renegotiate();
            this.m = true;
            Debug debug = a;
            StringBuffer stringBuffer = new StringBuffer("\n **** SocketWrapper\n **** Current session of encapsulated SSLSocket:\n **** ");
            stringBuffer.append(this.b.getSession().getID());
            stringBuffer.append("\n");
            debug.println(stringBuffer.toString());
            if (this.b.getSession().isValid()) {
                a.println(" **** SocketWrapper \n **** Session of encapsulated Socket is valid");
            } else {
                a.println(" **** SocketWrapper \n **** Session of encapsulated Socket NOT valid");
            }
        } else {
            a.println("\n **** SocketWrapper START HANDSHAKE ****");
            this.b.startHandshake();
            this.l = true;
        }
        c();
    }

    @Override // javax.net.ssl.SSLSocket
    public void setUseClientMode(boolean z) throws IllegalArgumentException {
        try {
            this.b.setUseClientMode(z);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.toString());
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public boolean getNeedClientAuth() {
        if (!this.b.getUseClientMode()) {
            try {
                return ((SSLServerContext) this.n).getRequestClientCertificate();
            } catch (Exception e) {
                a.println(e);
            }
        }
        return false;
    }

    @Override // javax.net.ssl.SSLSocket
    public void setEnabledCipherSuites(String[] strArr) {
        this.n.setEnabledCipherSuiteList(Util.fromStringArray(strArr));
        this.n.updateCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocket
    public void setEnableSessionCreation(boolean z) {
        this.k = z;
    }

    @Override // javax.net.ssl.SSLSocket
    public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        while (this.j.removeElement(handshakeCompletedListener)) {
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public boolean getUseClientMode() {
        return this.b.getUseClientMode();
    }

    @Override // javax.net.ssl.SSLSocket
    public String[] getSupportedCipherSuites() {
        CipherSuiteList cipherSuiteList = new CipherSuiteList(3);
        String[] strArr = new String[cipherSuiteList.size()];
        for (int i = 0; i < cipherSuiteList.size(); i++) {
            strArr[i] = cipherSuiteList.elementAt(i).getName();
        }
        return strArr;
    }

    @Override // javax.net.ssl.SSLSocket
    public SSLSession getSession() {
        if (!this.l) {
            try {
                startHandshake();
            } catch (IOException unused) {
                return null;
            }
        }
        if (this.m || !this.o.equals(this.b.getSession())) {
            this.o = new SSLSessionWrapper(this.b.getSession(), (JSSESessionManager) this.n.getSessionManager(), this.b.getTransport());
            this.m = false;
        }
        return this.o;
    }

    @Override // javax.net.ssl.SSLSocket
    public void setNeedClientAuth(boolean z) {
        if (this.b.getUseClientMode() && z) {
            throw new IllegalArgumentException("Not a server side socket");
        }
        ((SSLServerContext) this.n).setRequestClientCertificate(z);
        if (z) {
            this.n.getChainVerifier().removeTrustedCertificate(null);
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public String[] getEnabledCipherSuites() {
        CipherSuite[] array = this.n.getEnabledCipherSuiteList().toArray();
        int length = array.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = array[i].toString();
        }
        return strArr;
    }

    @Override // javax.net.ssl.SSLSocket
    public String[] getSupportedProtocols() {
        return Util.supportedProtocolVersions_;
    }

    @Override // javax.net.ssl.SSLSocket
    public String[] getEnabledProtocols() {
        String[] strArr = this.p;
        return strArr != null ? strArr : Util.toProtocol(this.n.getAllowedProtocolVersions());
    }

    @Override // javax.net.ssl.SSLSocket
    public void setEnabledProtocols(String[] strArr) {
        int[] protocolVersion = Util.toProtocolVersion(strArr);
        this.n.setAllowedProtocolVersions(protocolVersion[0], protocolVersion[1]);
        this.p = strArr;
    }

    @Override // javax.net.ssl.SSLSocket
    public boolean getEnableSessionCreation() {
        return this.k;
    }

    @Override // javax.net.ssl.SSLSocket
    public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        this.j.addElement(handshakeCompletedListener);
    }

    @Override // java.net.Socket
    public InputStream getInputStream() throws IOException {
        return new d(this.b.getTransport(), this);
    }

    @Override // java.net.Socket
    public OutputStream getOutputStream() throws IOException {
        return new e(this.b.getTransport(), this);
    }

    @Override // java.net.Socket
    public InetAddress getInetAddress() {
        return this.b.getInetAddress();
    }

    @Override // java.net.Socket
    public InetAddress getLocalAddress() {
        return this.b.getLocalAddress();
    }

    @Override // java.net.Socket
    public int getPort() {
        return this.b.getPort();
    }

    @Override // java.net.Socket
    public int getLocalPort() {
        return this.b.getLocalPort();
    }

    @Override // java.net.Socket
    public void setTcpNoDelay(boolean z) throws SocketException {
        this.b.setTcpNoDelay(z);
    }

    @Override // java.net.Socket
    public boolean getTcpNoDelay() throws SocketException {
        return this.b.getTcpNoDelay();
    }

    @Override // java.net.Socket
    public void setSoLinger(boolean z, int i) throws SocketException {
        this.b.setSoLinger(z, i);
    }

    @Override // java.net.Socket
    public int getSoLinger() throws SocketException {
        return this.b.getSoLinger();
    }

    @Override // java.net.Socket
    public void setSoTimeout(int i) throws SocketException {
        this.b.setSoTimeout(i);
    }

    @Override // java.net.Socket
    public int getSoTimeout() throws SocketException {
        return this.b.getSoTimeout();
    }

    @Override // java.net.Socket
    public void setSendBufferSize(int i) throws SocketException {
        Socket socketB = b();
        if (socketB != null) {
            socketB.setSendBufferSize(i);
        }
        this.e = i;
    }

    @Override // java.net.Socket
    public int getSendBufferSize() throws SocketException {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.getSendBufferSize();
        }
        return this.e;
    }

    @Override // java.net.Socket
    public void setReceiveBufferSize(int i) throws SocketException {
        Socket socketB = b();
        if (socketB != null) {
            socketB.setReceiveBufferSize(i);
        }
        this.f = i;
    }

    @Override // java.net.Socket
    public int getReceiveBufferSize() throws SocketException {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.getReceiveBufferSize();
        }
        return this.f;
    }

    @Override // java.net.Socket
    public void setKeepAlive(boolean z) throws SocketException {
        Socket socketB = b();
        if (socketB != null) {
            socketB.setKeepAlive(z);
        }
        this.g = z ? 1 : 2;
    }

    @Override // java.net.Socket
    public boolean getKeepAlive() throws SocketException {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.getKeepAlive();
        }
        return this.g <= 1;
    }

    @Override // java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.h) {
            this.b.close();
        } else {
            this.b.shutdown();
        }
        this.l = false;
        this.m = true;
    }

    @Override // javax.net.ssl.SSLSocket, java.net.Socket
    public String toString() {
        return this.b.toString();
    }

    public static void setSocketImplFactory(SocketImplFactory socketImplFactory) throws IOException {
        Socket.setSocketImplFactory(socketImplFactory);
    }

    @Override // javax.net.ssl.SSLSocket
    public void setWantClientAuth(boolean z) {
        if (this.b.getUseClientMode()) {
            if (z) {
                throw new IllegalArgumentException("Not a serverside socket");
            }
        } else {
            ((SSLServerContext) this.n).setRequestClientCertificate(z);
            if (z) {
                this.n.addTrustedCertificate(null);
            }
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public boolean getWantClientAuth() {
        if (this.b.getUseClientMode()) {
            return false;
        }
        return ((SSLServerContext) this.n).getRequestClientCertificate();
    }

    @Override // java.net.Socket
    public void connect(SocketAddress socketAddress, int i) throws IOException {
        if (this.b != null) {
            throw new SocketException("Already connected");
        }
        Socket socket = new Socket();
        SocketAddress socketAddress2 = this.d;
        if (socketAddress2 != null) {
            socket.bind(socketAddress2);
            this.d = null;
        }
        int i2 = this.e;
        if (i2 > 0) {
            socket.setSendBufferSize(i2);
        }
        int i3 = this.f;
        if (i3 > 0) {
            socket.setReceiveBufferSize(i3);
        }
        int i4 = this.g;
        if (i4 > 0) {
            socket.setKeepAlive(i4 == 1);
        }
        socket.connect(socketAddress, i);
        iaik.security.ssl.SSLSocket sSLSocket = new iaik.security.ssl.SSLSocket(socket, this.n);
        this.b = sSLSocket;
        sSLSocket.setAutoHandshake(this.i);
        if (a.getDebugMode() == 0) {
            this.b.setDebugStream(a.getDebugStream());
        }
        this.c = socket;
    }

    @Override // java.net.Socket
    public boolean isConnected() {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.isConnected();
        }
        return false;
    }

    @Override // java.net.Socket
    public void bind(SocketAddress socketAddress) throws IOException {
        Socket socketB = b();
        if (socketB != null) {
            socketB.bind(socketAddress);
        } else {
            this.d = socketAddress;
        }
    }

    @Override // java.net.Socket
    public SocketAddress getRemoteSocketAddress() {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.getRemoteSocketAddress();
        }
        return null;
    }

    @Override // java.net.Socket
    public SocketAddress getLocalSocketAddress() {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.getLocalSocketAddress();
        }
        return null;
    }

    @Override // java.net.Socket
    public void sendUrgentData(int i) throws IOException {
        Socket socketB = b();
        if (socketB != null) {
            socketB.sendUrgentData(i);
            return;
        }
        throw new SocketException("Not connected!");
    }

    @Override // java.net.Socket
    public void setOOBInline(boolean z) throws SocketException {
        Socket socketB = b();
        if (socketB != null) {
            socketB.setOOBInline(z);
        }
    }

    @Override // java.net.Socket
    public boolean getOOBInline() throws SocketException {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.getOOBInline();
        }
        return false;
    }

    @Override // java.net.Socket
    public boolean isBound() {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.isBound();
        }
        return false;
    }

    @Override // java.net.Socket
    public boolean isClosed() {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.isClosed();
        }
        return false;
    }

    @Override // java.net.Socket
    public boolean isInputShutdown() {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.isInputShutdown();
        }
        return false;
    }

    @Override // java.net.Socket
    public boolean isOutputShutdown() {
        Socket socketB = b();
        if (socketB != null) {
            return socketB.isOutputShutdown();
        }
        return false;
    }

    @Override // java.net.Socket
    public void shutdownInput() throws IOException {
        Socket socketB = b();
        if (socketB != null) {
            socketB.shutdownInput();
        }
    }

    @Override // java.net.Socket
    public void shutdownOutput() throws IOException {
        Socket socketB = b();
        if (socketB != null) {
            socketB.shutdownOutput();
        }
    }

    private Socket b() {
        Socket socket = this.c;
        return socket != null ? socket : this.b;
    }
}

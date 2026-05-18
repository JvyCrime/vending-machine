package iaik.security.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
public class SSLServerSocket extends ServerSocket {
    SSLServerContext a;

    public SSLServerSocket(int i, SSLServerContext sSLServerContext) throws IOException {
        this(i, 50, null, sSLServerContext);
    }

    public SSLServerSocket(int i, int i2, SSLServerContext sSLServerContext) throws IOException {
        this(i, i2, null, sSLServerContext);
    }

    public SSLServerSocket(int i, int i2, InetAddress inetAddress, SSLServerContext sSLServerContext) throws IOException {
        super(i, i2, inetAddress);
        setContext(sSLServerContext);
    }

    public SSLServerContext getContext() {
        return this.a;
    }

    public void setContext(SSLServerContext sSLServerContext) {
        if (sSLServerContext == null) {
            sSLServerContext = new SSLServerContext();
        }
        this.a = sSLServerContext;
    }

    @Override // java.net.ServerSocket
    public Socket accept() throws IOException {
        SSLSocket sSLSocket = new SSLSocket();
        implAccept(sSLSocket);
        sSLSocket.init(sSLSocket, this.a.a(false), false);
        return sSLSocket;
    }

    static {
        Utils.a();
    }
}

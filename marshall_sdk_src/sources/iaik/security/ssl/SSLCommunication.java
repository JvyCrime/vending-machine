package iaik.security.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes.dex */
public interface SSLCommunication {
    void close() throws IOException;

    CipherSuite getActiveCipherSuite();

    CompressionMethod getActiveCompressionMethod();

    int getActiveProtocolVersion();

    SSLContext getContext();

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    X509Certificate[] getPeerCertificateChain();

    CipherSuiteList getPeerSupportedCipherSuiteList();

    CompressionMethod[] getPeerSupportedCompressionMethods();

    Session getSession();

    boolean getUseClientMode();

    void renegotiate() throws IOException;

    void setAutoHandshake(boolean z);

    void setDebugStream(OutputStream outputStream);

    void setDebugStream(Writer writer);

    void setUseClientMode(boolean z) throws IOException;

    void shutdown() throws IOException;

    void startHandshake() throws IOException;
}

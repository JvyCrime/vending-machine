package iaik.security.jsse.net;

import iaik.security.ssl.SSLOutputStream;
import iaik.security.ssl.SSLTransport;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class e extends SSLOutputStream {
    SSLOutputStream a;
    private IAIKSSLSocketWrapper b;

    e(SSLTransport sSLTransport, IAIKSSLSocketWrapper iAIKSSLSocketWrapper) throws IOException {
        super(sSLTransport);
        this.b = iAIKSSLSocketWrapper;
        sSLTransport.setAutoHandshake(false);
        this.a = (SSLOutputStream) sSLTransport.getOutputStream();
    }

    @Override // iaik.security.ssl.SSLOutputStream
    public void setAutoFlush(boolean z) {
        this.a.setAutoFlush(z);
    }

    @Override // iaik.security.ssl.SSLOutputStream
    public boolean getAutoFlush() {
        return this.a.getAutoFlush();
    }

    @Override // iaik.security.ssl.SSLOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        this.b.a();
        this.a.write(i);
    }

    @Override // iaik.security.ssl.SSLOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // iaik.security.ssl.SSLOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.b.a();
        this.a.write(bArr, i, i2);
    }

    @Override // iaik.security.ssl.SSLOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.a.flush();
    }

    @Override // iaik.security.ssl.SSLOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }
}

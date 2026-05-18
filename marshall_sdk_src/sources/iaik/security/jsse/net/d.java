package iaik.security.jsse.net;

import iaik.security.ssl.SSLTransport;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
class d extends InputStream {
    InputStream a;
    private IAIKSSLSocketWrapper b;

    d(SSLTransport sSLTransport, IAIKSSLSocketWrapper iAIKSSLSocketWrapper) throws IOException {
        this.b = iAIKSSLSocketWrapper;
        sSLTransport.setAutoHandshake(false);
        this.a = sSLTransport.getInputStream();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        this.b.a();
        return this.a.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.b.a();
        return this.a.read(bArr, i, i2);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        this.b.a();
        return this.a.skip(j);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        this.b.a();
        return this.a.available();
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.a.mark(i);
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        this.a.reset();
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.a.markSupported();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }
}

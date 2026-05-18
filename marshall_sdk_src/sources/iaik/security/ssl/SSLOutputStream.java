package iaik.security.ssl;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class SSLOutputStream extends OutputStream {
    private SSLTransport a;
    private OutputStream b;
    private boolean c = true;

    protected SSLOutputStream(SSLTransport sSLTransport) throws IOException {
        this.a = sSLTransport;
        this.b = sSLTransport.f();
    }

    void a(OutputStream outputStream) {
        this.b = outputStream;
    }

    private void a() throws IOException {
        if (this.c) {
            this.b.flush();
        }
    }

    public void setAutoFlush(boolean z) {
        this.c = z;
    }

    public boolean getAutoFlush() {
        return this.c;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        synchronized (this.b) {
            if (this.a.a()) {
                return;
            }
            this.b.write(i);
            a();
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        synchronized (this.b) {
            if (this.a.a()) {
                return;
            }
            if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
                StringBuffer stringBuffer = new StringBuffer("Input buffer indices out of bounds: n=");
                stringBuffer.append(bArr.length);
                stringBuffer.append(", o=");
                stringBuffer.append(i);
                stringBuffer.append(", l=");
                stringBuffer.append(i2);
                throw new ArrayIndexOutOfBoundsException(stringBuffer.toString());
            }
            this.b.write(bArr, i, i2);
            a();
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        synchronized (this.b) {
            if (this.a.a()) {
                return;
            }
            this.b.flush();
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.b) {
            this.a.close();
        }
    }
}

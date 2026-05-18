package iaik.x509.stream;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
class a extends FilterInputStream {
    protected boolean a;
    protected ByteArrayOutputStream b;
    protected Signature c;

    public a(InputStream inputStream) {
        super(inputStream);
        this.a = true;
        Objects.requireNonNull(inputStream, "Argument \"input\" must not be null");
        this.b = new ByteArrayOutputStream(32);
    }

    public Signature a() {
        return this.c;
    }

    public void a(Signature signature) throws SignatureException {
        Objects.requireNonNull(signature, "Argument \"signatureEngine\" must not be null");
        this.c = signature;
        ByteArrayOutputStream byteArrayOutputStream = this.b;
        if (byteArrayOutputStream != null) {
            this.c.update(byteArrayOutputStream.toByteArray());
            this.b = null;
        }
    }

    public boolean a(boolean z) {
        boolean z2 = this.a;
        this.a = z;
        return z2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = this.in.read();
        if (this.a && i >= 0) {
            ByteArrayOutputStream byteArrayOutputStream = this.b;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.write((byte) i);
            } else {
                try {
                    this.c.update((byte) i);
                } catch (SignatureException e) {
                    throw new IOException(e.toString());
                }
            }
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.in.read(bArr, i, i2);
        if (this.a && i3 >= 0) {
            ByteArrayOutputStream byteArrayOutputStream = this.b;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.write(bArr, i, i3);
            } else {
                try {
                    this.c.update(bArr, i, i3);
                } catch (SignatureException e) {
                    throw new IOException(e.toString());
                }
            }
        }
        return i3;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(32);
        stringBuffer.append(this.in.toString());
        stringBuffer.append('|');
        stringBuffer.append(this.c.toString());
        return stringBuffer.toString();
    }
}

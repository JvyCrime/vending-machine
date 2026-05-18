package iaik.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/* JADX INFO: loaded from: classes.dex */
class b extends PushbackInputStream {
    int a;

    public b(InputStream inputStream) {
        super(inputStream);
        this.a = 0;
    }

    public int a() {
        return this.a;
    }

    @Override // java.io.PushbackInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = super.read();
        if (i != -1) {
            this.a++;
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.PushbackInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = super.read(bArr, i, i2);
        if (i3 > 0) {
            this.a += i3;
        }
        return i3;
    }

    @Override // java.io.PushbackInputStream
    public void unread(int i) throws IOException {
        super.unread(i);
        this.a--;
    }
}

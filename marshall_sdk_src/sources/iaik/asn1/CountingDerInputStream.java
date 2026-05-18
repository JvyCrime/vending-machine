package iaik.asn1;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class CountingDerInputStream extends DerInputStream {
    private int g;

    public CountingDerInputStream(InputStream inputStream) {
        super(inputStream);
        this.g = 0;
    }

    @Override // iaik.asn1.DerInputStream
    void a(int i) throws IOException {
        super.a(i);
        this.g--;
    }

    @Override // iaik.asn1.DerInputStream
    void a(byte[] bArr, int i) throws IOException {
        super.a(bArr, i);
        this.g -= i;
    }

    public int getPos() {
        return this.g;
    }

    @Override // iaik.asn1.DerInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = super.read();
        this.g++;
        return i;
    }

    @Override // iaik.asn1.DerInputStream
    public int read(boolean z) throws IOException {
        int i = super.read(z);
        if (!z) {
            this.g++;
        }
        return i;
    }

    @Override // iaik.asn1.DerInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = super.read(bArr, i, i2);
        if (i3 > 0) {
            this.g += i3;
        }
        return i3;
    }

    @Override // iaik.asn1.DerInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        long jSkip = super.skip(j);
        if (jSkip > 0) {
            this.g = (int) (((long) this.g) + jSkip);
        }
        return jSkip;
    }
}

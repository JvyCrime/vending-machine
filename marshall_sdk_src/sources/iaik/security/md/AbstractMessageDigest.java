package iaik.security.md;

import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractMessageDigest extends MessageDigest implements Cloneable {
    static final byte[] a;
    byte[] b;
    final int c;
    final int d;
    final int e;
    long f;
    private final transient byte[] g;

    static {
        byte[] bArr = new byte[FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_OSC_TRIM1_REG];
        a = bArr;
        bArr[0] = -128;
    }

    AbstractMessageDigest(String str, int i, int i2) {
        super(str);
        this.g = new byte[1];
        this.c = i;
        this.d = i2;
        this.e = i2 - 1;
        this.b = new byte[i2];
    }

    abstract void a();

    abstract void a(byte[] bArr, int i);

    int b() {
        return this.d;
    }

    abstract void b(byte[] bArr, int i);

    @Override // java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        AbstractMessageDigest abstractMessageDigest;
        AbstractMessageDigest abstractMessageDigest2 = null;
        try {
            abstractMessageDigest = (AbstractMessageDigest) super.clone();
        } catch (CloneNotSupportedException unused) {
        }
        try {
            byte[] bArr = this.b;
            if (bArr != null) {
                abstractMessageDigest.b = (byte[]) bArr.clone();
            }
            abstractMessageDigest.g[0] = this.g[0];
            return abstractMessageDigest;
        } catch (CloneNotSupportedException unused2) {
            abstractMessageDigest2 = abstractMessageDigest;
            return abstractMessageDigest2;
        }
    }

    @Override // java.security.MessageDigestSpi
    protected byte[] engineDigest() {
        byte[] bArr = new byte[this.c];
        a();
        b(bArr, 0);
        engineReset();
        return bArr;
    }

    @Override // java.security.MessageDigestSpi
    public int engineGetDigestLength() {
        return this.c;
    }

    @Override // java.security.MessageDigestSpi
    protected abstract void engineReset();

    @Override // java.security.MessageDigestSpi
    protected final void engineUpdate(byte b) {
        byte[] bArr = this.g;
        bArr[0] = b;
        engineUpdate(bArr, 0, 1);
    }

    @Override // java.security.MessageDigestSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        long j = this.f;
        int i3 = (int) (((long) this.e) & j);
        this.f = j + ((long) i2);
        if (i3 != 0) {
            int i4 = this.d - i3;
            if (i4 > i2) {
                System.arraycopy(bArr, i, this.b, i3, i2);
                return;
            }
            System.arraycopy(bArr, i, this.b, i3, i4);
            a(this.b, 0);
            i2 -= i4;
            if (i2 == 0) {
                return;
            } else {
                i += i4;
            }
        }
        while (i2 >= this.d) {
            a(bArr, i);
            int i5 = this.d;
            i += i5;
            i2 -= i5;
        }
        if (i2 > 0) {
            System.arraycopy(bArr, i, this.b, 0, i2);
        }
    }
}

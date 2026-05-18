package iaik.security.ssl;

import java.security.MessageDigest;
import javax.crypto.Mac;

/* JADX INFO: loaded from: classes.dex */
class ad {
    private int a;
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private byte[] e;
    private int f;
    private MessageDigest g;
    private Mac h;

    ad(MessageDigest messageDigest, byte[] bArr, byte[] bArr2) {
        this.a = SSLContext.VERSION_SSL30;
        this.g = messageDigest;
        this.d = bArr;
        this.e = bArr2;
        this.c = new byte[8];
        this.f = messageDigest.digest().length;
    }

    ad(Mac mac) {
        this.a = SSLContext.VERSION_TLS10;
        this.h = mac;
        this.c = new byte[8];
        this.f = mac.getMacLength();
    }

    ad(MessageDigest messageDigest, int i) {
        this.a = 2;
        this.g = messageDigest;
        this.d = null;
        this.e = null;
        byte[] bArr = new byte[4];
        this.c = bArr;
        bArr[3] = (byte) i;
        this.f = messageDigest.digest().length;
    }

    int a() {
        return this.f;
    }

    void a(int i) {
        this.f = i;
    }

    void a(byte[] bArr) {
        this.b = bArr;
    }

    private void b() {
        int length = this.c.length - 1;
        while (true) {
            byte[] bArr = this.c;
            int i = length - 1;
            byte b = (byte) (bArr[length] + 1);
            bArr[length] = b;
            if (b != 0 || i < 0) {
                return;
            } else {
                length = i;
            }
        }
    }

    private byte[] b(byte[] bArr, int i, int i2, int i3) {
        int i4 = this.a;
        if (i4 > 768) {
            this.h.update(this.c);
            b();
            this.h.update(bArr, 0, 3);
            this.h.update((byte) (i2 >> 8));
            this.h.update((byte) i2);
            this.h.update(bArr, i, i2);
            byte[] bArrDoFinal = this.h.doFinal();
            if (i3 <= 0) {
                return bArrDoFinal;
            }
            int i5 = i3 * 64;
            if (i + i5 >= bArr.length) {
                for (int i6 = 0; i6 < i3; i6++) {
                    this.h.update(bArr, 0, 64);
                }
            } else {
                this.h.update(bArr, i, i5);
            }
            this.h.reset();
            return bArrDoFinal;
        }
        if (i4 == 768) {
            this.g.update(this.b);
            this.g.update(this.d);
            this.g.update(this.c);
            b();
            this.g.update(bArr[0]);
            this.g.update((byte) (i2 >> 8));
            this.g.update((byte) i2);
            this.g.update(bArr, i, i2);
            byte[] bArrDigest = this.g.digest();
            this.g.update(this.b);
            this.g.update(this.e);
            this.g.update(bArrDigest);
            byte[] bArrDigest2 = this.g.digest();
            if (i3 <= 0) {
                return bArrDigest2;
            }
            int i7 = i3 * 64;
            if (i + i7 >= bArr.length) {
                for (int i8 = 0; i8 < i3; i8++) {
                    this.g.update(bArr, 0, 64);
                }
            } else {
                this.g.update(bArr, i, i7);
            }
            this.g.reset();
            return bArrDigest2;
        }
        this.g.update(this.b);
        this.g.update(bArr, i, i2);
        this.g.update(this.c);
        b();
        return this.g.digest();
    }

    int a(byte[] bArr, int i, int i2) {
        if (this.a >= 768) {
            System.arraycopy(b(bArr, i, i2, 0), 0, bArr, i + i2, this.f);
            return i2 + this.f;
        }
        byte[] bArrB = b(bArr, i, i2, 0);
        int i3 = this.f;
        System.arraycopy(bArrB, 0, bArr, i - i3, i3);
        return i2;
    }

    int a(byte[] bArr, int i, int i2, int i3) {
        int i4;
        if (this.a >= 768) {
            int i5 = this.f;
            i2 -= i5;
            if (i3 > 0) {
                i4 = ((((i3 - i5) + 13) + 8) / 64) - (((i2 + 13) + 8) / 64);
            } else {
                i4 = 0;
            }
            if (!Utils.equalsBlock(b(bArr, i, i2, i4), 0, bArr, i + i2, this.f)) {
                return -1;
            }
        } else {
            byte[] bArrB = b(bArr, i, i2, 0);
            int i6 = this.f;
            if (!Utils.equalsBlock(bArrB, 0, bArr, i - i6, i6)) {
                return -1;
            }
        }
        return i2;
    }
}

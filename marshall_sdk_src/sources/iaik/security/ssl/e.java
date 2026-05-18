package iaik.security.ssl;

import com.bitmick.marshall.vmc.marshall_t;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
final class e {
    private static final byte[] a;
    private static final byte[] b;

    static {
        try {
            a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes("ASCII");
            b = new byte[256];
            int i = 0;
            int i2 = 0;
            while (true) {
                byte[] bArr = b;
                if (i2 >= bArr.length) {
                    break;
                }
                bArr[i2] = 64;
                i2++;
            }
            while (true) {
                byte[] bArr2 = a;
                if (i < bArr2.length) {
                    b[bArr2[i]] = (byte) i;
                    i++;
                } else {
                    b[61] = -1;
                    return;
                }
            }
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("ASCII encoding unsupported");
        }
    }

    static final byte[] a(byte[] bArr) {
        int i;
        int length = bArr.length;
        byte[] bArr2 = new byte[((length + 2) / 3) * 4];
        int i2 = 0;
        int i3 = length + 0;
        int i4 = i3 - 2;
        int i5 = 0;
        while (i2 < i4) {
            int i6 = i5 + 1;
            byte[] bArr3 = a;
            bArr2[i5] = bArr3[(bArr[i2] >>> 2) & 63];
            int i7 = i6 + 1;
            int i8 = i2 + 1;
            bArr2[i6] = bArr3[((bArr[i2] << 4) & 48) | ((bArr[i8] >>> 4) & 15)];
            int i9 = i7 + 1;
            int i10 = i8 + 1;
            bArr2[i7] = bArr3[((bArr[i8] << 2) & 60) | ((bArr[i10] >>> 6) & 3)];
            i5 = i9 + 1;
            bArr2[i9] = bArr3[bArr[i10] & 63];
            i2 = i10 + 1;
        }
        if (i2 < i3) {
            int i11 = i5 + 1;
            byte[] bArr4 = a;
            bArr2[i5] = bArr4[(bArr[i2] >>> 2) & 63];
            if (i3 - i2 > 1) {
                int i12 = i11 + 1;
                int i13 = i2 + 1;
                bArr2[i11] = bArr4[((bArr[i2] << 4) & 48) | ((bArr[i13] >>> 4) & 15)];
                i = i12 + 1;
                bArr2[i12] = bArr4[(bArr[i13] << 2) & 60];
            } else {
                int i14 = i11 + 1;
                bArr2[i11] = bArr4[(bArr[i2] << 4) & 48];
                i = i14 + 1;
                bArr2[i14] = marshall_t.status_vpos_insert_card;
            }
            bArr2[i] = marshall_t.status_vpos_insert_card;
        }
        return bArr2;
    }
}

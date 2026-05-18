package iaik.security.md;

import iaik.utils.CryptoUtils;
import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class Md2 extends AbstractMessageDigest {
    private static final int[] g;
    private int[] h;
    private int[] i;
    private transient byte[] j;

    static {
        byte[] bArrDecodeByteArray = Util.decodeByteArray("KS5DyaLYfAE9NlSh7PAGE2KnBfPAx3OMmJMr2bxMgsoem1c8/dTgFmdCbxiKF+USvk7E1tqe3kmg+/WOuy/ueqloeZEVsgc/lMIQiQsiXyGAf12aWpAyJzU+zOe/95cD/xkws0iltdHXXpIqrFaqxk+4ONKWpH22dvxr4px0BPFFnXBZZHGHIIZbz2XmLagCG2Alra6wufYcRmFpNEB+D1VHoyPdUa86w1z5zrrF6iYsUw1uhSiECdPfzfRBgU1Satw3yGzBq/ok4XsIDL2xSniIlYvjY+ht6cvV/jsAHTny77cOZljQ5KZ3cvjrdUsKMURQtI/tHxrbmY0znxGDFA==");
        int length = bArrDecodeByteArray.length;
        g = new int[length];
        for (int i = 0; i < length; i++) {
            g[i] = bArrDecodeByteArray[i] & 255;
        }
    }

    public Md2() {
        super("MD2", 16, 16);
        this.j = new byte[16];
        this.h = new int[48];
        this.i = new int[16];
        engineReset();
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        byte b = (byte) (16 - (this.f & 15));
        for (int i = 0; i < b; i++) {
            this.j[i] = b;
        }
        engineUpdate(this.j, 0, b);
        int i2 = 0;
        while (i2 < 16) {
            byte[] bArr = this.j;
            int[] iArr = this.i;
            int i3 = i2 + 1;
            bArr[i2] = (byte) iArr[i2];
            int i4 = i3 + 1;
            bArr[i3] = (byte) iArr[i3];
            int i5 = i4 + 1;
            bArr[i4] = (byte) iArr[i4];
            i2 = i5 + 1;
            bArr[i5] = (byte) iArr[i5];
        }
        a(this.j, 0);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i) {
        for (int i2 = 0; i2 < 16; i2++) {
            int[] iArr = this.h;
            int i3 = i2 + 16;
            iArr[i3] = bArr[i + i2] & 255;
            iArr[i2 + 32] = iArr[i3] ^ iArr[i2];
        }
        int i4 = this.i[15];
        int i5 = 0;
        while (i5 < 16) {
            int[] iArr2 = this.i;
            int i6 = iArr2[i5];
            int[] iArr3 = g;
            int[] iArr4 = this.h;
            int i7 = i5 + 1;
            int i8 = iArr3[i4 ^ iArr4[i5 + 16]] ^ i6;
            iArr2[i5] = i8;
            int i9 = i7 + 1;
            int i10 = iArr3[i8 ^ iArr4[i7 + 16]] ^ iArr2[i7];
            iArr2[i7] = i10;
            int i11 = i9 + 1;
            int i12 = iArr3[i10 ^ iArr4[i9 + 16]] ^ iArr2[i9];
            iArr2[i9] = i12;
            i4 = iArr3[i12 ^ iArr4[i11 + 16]] ^ iArr2[i11];
            iArr2[i11] = i4;
            i5 = i11 + 1;
        }
        int i13 = 0;
        for (int i14 = 0; i14 <= 17; i14++) {
            for (int i15 = 0; i15 <= 47; i15 += 4) {
                int[] iArr5 = this.h;
                int i16 = iArr5[i15];
                int[] iArr6 = g;
                int i17 = iArr6[i13] ^ i16;
                iArr5[i15] = i17;
                int i18 = i15 + 1;
                int i19 = iArr6[i17] ^ iArr5[i18];
                iArr5[i18] = i19;
                int i20 = i15 + 2;
                int i21 = iArr6[i19] ^ iArr5[i20];
                iArr5[i20] = i21;
                int i22 = i15 + 3;
                i13 = iArr6[i21] ^ iArr5[i22];
                iArr5[i22] = i13;
            }
            i13 = (i13 + i14) & 255;
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        for (int i2 = 0; i2 < 16; i2++) {
            bArr[i + i2] = (byte) this.h[i2];
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        Md2 md2 = (Md2) super.clone();
        int[] iArr = this.h;
        if (iArr != null) {
            md2.h = (int[]) iArr.clone();
        }
        int[] iArr2 = this.i;
        if (iArr2 != null) {
            md2.i = (int[]) iArr2.clone();
        }
        byte[] bArr = this.j;
        if (bArr != null) {
            md2.j = (byte[]) bArr.clone();
        }
        return md2;
    }

    public void destroyCriticalData() {
        reset();
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    public void engineReset() {
        CryptoUtils.zeroBlock(this.h);
        CryptoUtils.zeroBlock(this.i);
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.zeroBlock(this.j);
        this.f = 0L;
    }

    protected void finalize() {
        destroyCriticalData();
    }
}

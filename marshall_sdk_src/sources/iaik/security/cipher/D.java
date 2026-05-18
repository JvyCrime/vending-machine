package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.RC2ParameterSpec;

/* JADX INFO: loaded from: classes.dex */
final class D extends t {
    static Class a;
    private static final byte[] p = Util.decodeByteArray("2Xj5xBndte0o6f15SqDYncZ+N4MrdlOOYkxkiESL+6IXmln1h7NPE2FFbY0JgX0yvY9A64a3ewvwlSEiXGtOglTWZZPOYLIcc1bAFKeM8dwSdcofO77k0UI91DCjPLYmb78O2kZpB1cn8h2bvJRDA/gRx/aQ7z7nBsPVL8hmHtcI6OregFLu94Sqcqw1TWoqlhrScVoVSXRLn9BeBBik7MLgQW4PUcvMJJGvUKH0cDmZfDqFI7i0evwCNlslVZcxLV36mOOKkq4F3ykQZ2y6ydMA5s/hnqgsYxYBP1jiiakNODQbqzP/sLtIDF+5sc0uxfPbR+WlnHcKpiBo/n/BrQ==");
    private int[] b;
    private int c;
    private final int[] m;
    private final int[] n;
    private int o;

    D() {
        super("RC2", 8, 8);
        this.m = new int[8];
        this.n = new int[8];
        this.b = new int[32];
    }

    private int[] a(byte[] bArr, int i) throws InvalidKeyException {
        if (i == -1) {
            i = bArr.length << 3;
        }
        if (bArr.length < 1 || bArr.length > 128 || i < 8 || i > 1024) {
            throw new InvalidKeyException("Key and effective key size must be between 8 and 1024 bits long!");
        }
        this.c = i;
        int length = bArr.length;
        byte[] bArr2 = new byte[128];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        byte b = bArr2[length - 1];
        int i2 = 0;
        while (length < 128) {
            b = p[(bArr2[i2] + b) & 255];
            bArr2[length] = b;
            length++;
            i2++;
        }
        int i3 = (i + 7) >> 3;
        int i4 = 128 - i3;
        int i5 = p[(255 >> ((-i) & 7)) & bArr2[i4]];
        bArr2[i4] = (byte) i5;
        while (true) {
            int i6 = i4 - 1;
            if (i4 <= 0) {
                int[] iArr = new int[64];
                this.b = iArr;
                CryptoUtils.squashBytesToShortsLE(bArr2, 0, iArr, 0, 64);
                return this.b;
            }
            i5 = p[(i5 ^ bArr2[i6 + i3]) & 255] & 255;
            bArr2[i6] = (byte) i5;
            i4 = i6;
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.security.cipher.t
    void a() {
        if (this.d == 2) {
            System.arraycopy(this.m, 0, this.n, 0, 8);
            this.o = 0;
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameters == null) {
            a(i, key, (AlgorithmParameterSpec) null, secureRandom);
            return;
        }
        try {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("javax.crypto.spec.RC2ParameterSpec");
                a = clsClass$;
            }
            a(i, key, algorithmParameters.getParameterSpec(clsClass$), secureRandom);
        } catch (InvalidParameterSpecException unused) {
            super.a(i, key, algorithmParameters, secureRandom);
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!key.getFormat().equals("RAW")) {
            throw new InvalidKeyException("Key must be RAW bytes!");
        }
        byte[] encoded = key.getEncoded();
        int effectiveKeyBits = -1;
        if (algorithmParameterSpec != null && (algorithmParameterSpec instanceof RC2ParameterSpec)) {
            effectiveKeyBits = ((RC2ParameterSpec) algorithmParameterSpec).getEffectiveKeyBits();
        }
        this.b = a(encoded, effectiveKeyBits);
        if (this.d != 2) {
            this.e = null;
            return;
        }
        this.e = a(i, algorithmParameterSpec, secureRandom, 8);
        CryptoUtils.squashBytesToShortsLE(this.e, 0, this.n, 0, 4);
        System.arraycopy(this.n, 0, this.m, 0, 4);
        this.o = 0;
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4;
        int i5 = i + 1;
        int i6 = i5 + 1;
        int i7 = (bArr[i] & 255) + ((bArr[i5] & 255) << 8);
        int i8 = i6 + 1;
        int i9 = i8 + 1;
        int i10 = (bArr[i6] & 255) + ((bArr[i8] & 255) << 8);
        int i11 = i9 + 1;
        int i12 = i11 + 1;
        int i13 = (bArr[i9] & 255) + ((bArr[i11] & 255) << 8);
        int i14 = (bArr[i12] & 255) + ((bArr[i12 + 1] & 255) << 8);
        if (this.d == 2) {
            i4 = this.o ^ 4;
            int[] iArr = this.n;
            iArr[i4] = i7;
            iArr[i4 + 1] = i10;
            iArr[i4 + 2] = i13;
            iArr[i4 + 3] = i14;
        } else {
            i4 = 0;
        }
        int i15 = 63;
        while (i15 > 0) {
            int[] iArr2 = this.b;
            int i16 = i15 - 1;
            int i17 = ((((((i14 << 11) + (i14 >>> 5)) & 65535) - iArr2[i15]) - (i10 & i13)) - ((~i13) & i7)) & 65535;
            int i18 = i16 - 1;
            i13 = ((((((i13 << 13) + (i13 >>> 3)) & 65535) - iArr2[i16]) - (i10 & i7)) - ((~i10) & i17)) & 65535;
            int i19 = i18 - 1;
            i10 = ((((((i10 << 14) + (i10 >>> 2)) & 65535) - iArr2[i18]) - (i7 & i17)) - ((~i7) & i13)) & 65535;
            int i20 = i19 - 1;
            i7 = ((((((i7 << 15) + (i7 >>> 1)) & 65535) - iArr2[i19]) - (i17 & i13)) - ((~i17) & i10)) & 65535;
            if (i20 == 19 || i20 == 43) {
                i17 = (i17 - iArr2[i13 & 63]) & 65535;
                i13 = (i13 - iArr2[i10 & 63]) & 65535;
                i10 = (i10 - iArr2[i7 & 63]) & 65535;
                i7 = (i7 - iArr2[i17 & 63]) & 65535;
            }
            i14 = i17;
            i15 = i20;
        }
        if (this.d == 2) {
            int[] iArr3 = this.n;
            int i21 = this.o;
            int i22 = i21 + 1;
            this.o = i22;
            i7 ^= iArr3[i21];
            int i23 = i22 + 1;
            this.o = i23;
            i10 ^= iArr3[i22];
            int i24 = i23 + 1;
            this.o = i24;
            i13 ^= iArr3[i23];
            i14 ^= iArr3[i24];
            this.o = i4;
        }
        int i25 = i3 + 1;
        bArr2[i3] = (byte) i7;
        int i26 = i25 + 1;
        bArr2[i25] = (byte) (i7 >> 8);
        int i27 = i26 + 1;
        bArr2[i26] = (byte) i10;
        int i28 = i27 + 1;
        bArr2[i27] = (byte) (i10 >> 8);
        int i29 = i28 + 1;
        bArr2[i28] = (byte) i13;
        int i30 = i29 + 1;
        bArr2[i29] = (byte) (i13 >> 8);
        bArr2[i30] = (byte) i14;
        bArr2[i30 + 1] = (byte) (i14 >> 8);
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i == 1 || i == 2) {
            this.d = i;
            return true;
        }
        this.d = 0;
        return false;
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = i + 1;
        int i5 = i4 + 1;
        int i6 = (bArr[i] & 255) + ((bArr[i4] & 255) << 8);
        int i7 = i5 + 1;
        int i8 = i7 + 1;
        int i9 = (bArr[i5] & 255) + ((bArr[i7] & 255) << 8);
        int i10 = i8 + 1;
        int i11 = i10 + 1;
        int i12 = (bArr[i8] & 255) + ((bArr[i10] & 255) << 8);
        int i13 = (bArr[i11] & 255) + ((bArr[i11 + 1] & 255) << 8);
        if (this.d == 2) {
            int[] iArr = this.n;
            i6 ^= iArr[0];
            i9 ^= iArr[1];
            i12 ^= iArr[2];
            i13 ^= iArr[3];
        }
        int i14 = 0;
        while (i14 < 64) {
            int[] iArr2 = this.b;
            int i15 = i14 + 1;
            int i16 = (i6 + iArr2[i14] + (i13 & i12) + ((~i13) & i9)) & 65535;
            i6 = (i16 >>> 15) + (i16 << 1);
            int i17 = i15 + 1;
            int i18 = (i9 + iArr2[i15] + (i6 & i13) + ((~i6) & i12)) & 65535;
            i9 = (i18 >>> 14) + (i18 << 2);
            int i19 = i17 + 1;
            int i20 = (i12 + iArr2[i17] + (i9 & i6) + ((~i9) & i13)) & 65535;
            i12 = (i20 >>> 13) + (i20 << 3);
            int i21 = i19 + 1;
            int i22 = 65535 & (i13 + iArr2[i19] + (i12 & i9) + ((~i12) & i6));
            i13 = (i22 << 5) + (i22 >>> 11);
            if (i21 == 20 || i21 == 44) {
                i6 += iArr2[i13 & 63];
                i9 += iArr2[i6 & 63];
                i12 += iArr2[i9 & 63];
                i13 += iArr2[i12 & 63];
            }
            i14 = i21;
        }
        if (this.d == 2) {
            int[] iArr3 = this.n;
            iArr3[0] = i6;
            iArr3[1] = i9;
            iArr3[2] = i12;
            iArr3[3] = i13;
        }
        int i23 = i3 + 1;
        bArr2[i3] = (byte) i6;
        int i24 = i23 + 1;
        bArr2[i23] = (byte) (i6 >> 8);
        int i25 = i24 + 1;
        bArr2[i24] = (byte) i9;
        int i26 = i25 + 1;
        bArr2[i25] = (byte) (i9 >> 8);
        int i27 = i26 + 1;
        bArr2[i26] = (byte) i12;
        int i28 = i27 + 1;
        bArr2[i27] = (byte) (i12 >> 8);
        bArr2[i28] = (byte) i13;
        bArr2[i28 + 1] = (byte) (i13 >> 8);
    }

    public void c() {
        CryptoUtils.zeroBlock(this.b);
        int[] iArr = this.n;
        if (iArr != null) {
            CryptoUtils.zeroBlock(iArr);
            CryptoUtils.zeroBlock(this.m);
        }
    }

    @Override // iaik.security.cipher.t
    AlgorithmParameters e() {
        RC2ParameterSpec rC2ParameterSpec = this.e == null ? new RC2ParameterSpec(this.c) : new RC2ParameterSpec(this.c, this.e);
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("RC2", "IAIK");
            algorithmParameters.init(rC2ParameterSpec);
            return algorithmParameters;
        } catch (Exception unused) {
            return null;
        }
    }

    protected void finalize() {
        c();
    }
}

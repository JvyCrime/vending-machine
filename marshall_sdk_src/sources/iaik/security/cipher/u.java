package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
final class u extends t {
    private static final byte[] E;
    private static final byte[] F;
    private static final byte[] G;
    private static byte[] H;
    private static byte[] I;
    private static final long[] J;
    private static final int[] K;
    private static final int[] m;
    private static final int[] n;
    private static final int[] o;
    private static final int[] p;
    private static final int[] q;
    private static final int[] r;
    private static final int[] s;
    private static final int[] t;
    private int[] A;
    private int[] B;
    private int C;
    private int D;
    private final int[] a;
    private final int[] b;
    private final int[] c;
    private int[] u;
    private int[] v;
    private int[] w;
    private int[] x;
    private int[] y;
    private int[] z;

    static {
        byte[] bArrDecodeByteArray = Util.decodeByteArray("EAcUFR0MHBEBDxcaBRIfCgIIGA4gGwMJEw0eBhYLBBk=");
        E = bArrDecodeByteArray;
        byte[] bArrDecodeByteArray2 = Util.decodeByteArray("DgQNAQIPCwgDCgYMBQkABwAPBwQOAg0BCgYMCwkFAwgEAQ4IDQYCCw8MCQcDCgUADwwIAgQJAQcFCwMOCgAGDQ8BCA4GCwMECQcCDQwABQoDDQQHDwIIDgwAAQoGCQsFAA4HCwoEDQEFCAwGCQMCDw0ICgEDDwQCCwYHDAAFDgkKAAkOBgMPBQENDAcLBAIIDQcACQMEBgoCCAUODAsPAQ0GBAkIDwMACwECDAUKDgcBCg0ABgkIBwQPDgMLBQIMBw0OAwAGCQoBAggFCwwEDw0ICwUGDwADBAcCDAEKDgkKBgkADAsHDQ8BAw4FAggEAw8ABgoBDQgJBAULDAcCDgIMBAEHCgsGCAUDDw0ADgkOCwIMBAcNAQUADwoDCQgGBAIBCwoNBwgPCQwFBgMADgsIDAcBDgINBg8ACQoEBQMMAQoPCQIGCAANAwQOBwULCg8EAgcMCQUGAQ0OAAsDCAkODwUCCAwDBwAECgENCwYEAwIMCQUPCgsOAQcGAAgNBAsCDg8ACA0DDAkHBQoGAQ0ACwcECQEKDgMFDAIPCAYBBAsNDAMHDgoPBggABQkCBgsNCAEECgcJBQAPDgIDDA0CCAQGDwsBCgkDDgUADAcBDw0ICgMHBAwFBgsADgkCBwsEAQkMDgIABgoNDwMFCAIBDgcECggNDwwJAAMFBgs=");
        F = bArrDecodeByteArray2;
        I = Util.decodeByteArray("OTEpIRkRCQE6MioiGhIKAjszKyMbEwsDPDQsJD83LycfFw8HPjYuJh4WDgY9NS0lHRUNBRwUDAQ=");
        G = Util.decodeByteArray("AQECAgICAgIBAgICAgICAQ==");
        H = Util.decodeByteArray("DhELGAEFAxwPBhUKFxMMBBoIEAcbFA0CKTQfJS83HigzLSEwLDEnOCI1LioyJB0g");
        int[] iArr = new int[256];
        m = iArr;
        int[] iArr2 = new int[256];
        n = iArr2;
        int[] iArr3 = new int[64];
        o = iArr3;
        int[] iArr4 = new int[64];
        p = iArr4;
        int[] iArr5 = new int[64];
        q = iArr5;
        int[] iArr6 = new int[64];
        r = iArr6;
        int[] iArr7 = new int[64];
        s = iArr7;
        int[] iArr8 = new int[64];
        t = iArr8;
        a(bArrDecodeByteArray2, bArrDecodeByteArray, new int[][]{iArr, iArr2, iArr3, iArr4, iArr5, iArr6, iArr7, iArr8});
        J = new long[65];
        K = new int[57];
        d();
        f();
        H = null;
        I = null;
    }

    u() {
        super("DES", 8, 8);
        this.a = new int[32];
        this.b = new int[4];
        this.c = new int[4];
    }

    private void a(byte[] bArr, int i) {
        int i2;
        int i3;
        long j = 0;
        int i4 = 0;
        while (i4 < 8) {
            int i5 = bArr[i4] & 255;
            i4++;
            int i6 = i4 << 3;
            if ((i5 & 1) != 0) {
                j |= J[i6];
            }
            if ((i5 & 2) != 0) {
                j |= J[i6 - 1];
            }
            if ((i5 & 4) != 0) {
                j |= J[i6 - 2];
            }
            if ((i5 & 8) != 0) {
                j |= J[i6 - 3];
            }
            if ((i5 & 16) != 0) {
                j |= J[i6 - 4];
            }
            if ((i5 & 32) != 0) {
                j |= J[i6 - 5];
            }
            if ((i5 & 64) != 0) {
                j |= J[i6 - 6];
            }
            if ((i5 & 128) != 0) {
                j |= J[i6 - 7];
            }
        }
        int i7 = (int) (j >> 32);
        int i8 = (int) j;
        for (int i9 = 0; i9 < 16; i9++) {
            byte b = G[i9];
            int i10 = 28 - b;
            i7 = (i7 >> i10) | ((i7 << b) & 268435455);
            i8 = (i8 >> i10) | ((i8 << b) & 268435455);
            int i11 = 28;
            int i12 = 0;
            for (int i13 = i7; i13 != 0; i13 >>= 2) {
                int i14 = i13 & 3;
                if (i14 == 1) {
                    i3 = K[i11];
                } else if (i14 == 2) {
                    i3 = K[i11 - 1];
                } else if (i14 != 3) {
                    i11 -= 2;
                } else {
                    int[] iArr = K;
                    i3 = iArr[i11 - 1] | iArr[i11];
                }
                i12 |= i3;
                i11 -= 2;
            }
            int i15 = 56;
            int i16 = 0;
            for (int i17 = i8; i17 != 0; i17 >>= 2) {
                int i18 = i17 & 3;
                if (i18 == 1) {
                    i2 = K[i15];
                } else if (i18 == 2) {
                    i2 = K[i15 - 1];
                } else if (i18 != 3) {
                    i15 -= 2;
                } else {
                    int[] iArr2 = K;
                    i2 = iArr2[i15 - 1] | iArr2[i15];
                }
                i16 |= i2;
                i15 -= 2;
            }
            int i19 = i == 1 ? i9 << 1 : (15 - i9) << 1;
            int[] iArr3 = this.a;
            iArr3[i19] = ((16515072 & i16) >>> 10) | ((i12 & 16515072) << 6) | ((i12 & 4032) << 10) | ((i16 & 4032) >>> 6);
            iArr3[i19 + 1] = ((i12 & 63) << 16) | ((i12 & 258048) << 12) | ((i16 & 258048) >>> 4) | (i16 & 63);
        }
    }

    static void a(int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = ((i >>> 4) ^ i2) & 252645135;
        int i4 = i2 ^ i3;
        int i5 = i ^ (i3 << 4);
        int i6 = ((i5 >>> 16) ^ i4) & 65535;
        int i7 = i4 ^ i6;
        int i8 = i5 ^ (i6 << 16);
        int i9 = ((i7 >>> 2) ^ i8) & 858993459;
        int i10 = i8 ^ i9;
        int i11 = i7 ^ (i9 << 2);
        int i12 = ((i11 >>> 8) ^ i10) & 16711935;
        int i13 = i10 ^ i12;
        int i14 = i11 ^ (i12 << 8);
        int i15 = (i14 >>> 31) | (i14 << 1);
        int i16 = (i13 ^ i15) & (-1431655766);
        int i17 = i13 ^ i16;
        int i18 = i15 ^ i16;
        iArr[0] = (i17 >>> 31) | (i17 << 1);
        iArr[1] = i18;
    }

    private void a(int[][] iArr) {
        this.u = iArr[0];
        this.v = iArr[1];
        this.w = iArr[2];
        this.x = iArr[3];
        this.y = iArr[4];
        this.z = iArr[5];
        this.A = iArr[6];
        this.B = iArr[7];
    }

    private static int[][] a(byte[] bArr, byte[] bArr2, int[][] iArr) {
        int[] iArr2 = new int[64];
        int[] iArr3 = new int[33];
        if (iArr == null) {
            iArr = new int[8][];
            iArr[0] = new int[256];
            iArr[1] = new int[256];
            for (int i = 2; i < 8; i++) {
                iArr[i] = new int[64];
            }
        }
        for (int i2 = 0; i2 < 64; i2++) {
            iArr2[i2] = ((i2 >> 1) & 15) + (i2 & 32) + ((i2 & 1) << 4);
        }
        for (int i3 = 0; i3 < 32; i3++) {
            iArr3[32 - bArr2[i3]] = 1 << ((32 - i3) & 31);
        }
        for (int i4 = 0; i4 < 8; i4++) {
            for (int i5 = 0; i5 < 64; i5++) {
                int i6 = 28 - (i4 << 2);
                for (int i7 = bArr[(i4 << 6) + iArr2[i5]]; i7 != 0; i7 >>= 1) {
                    if ((i7 & 1) != 0) {
                        int[] iArr4 = iArr[i4];
                        iArr4[i5] = iArr4[i5] | iArr3[i6];
                    }
                    i6++;
                }
            }
        }
        System.arraycopy(iArr[0], 0, iArr[0], 64, 64);
        System.arraycopy(iArr[0], 0, iArr[0], 128, 128);
        System.arraycopy(iArr[1], 0, iArr[1], 64, 64);
        System.arraycopy(iArr[1], 0, iArr[1], 128, 128);
        return iArr;
    }

    static void b(int[] iArr) {
        int i = iArr[1];
        int i2 = iArr[0];
        int i3 = (i2 >>> 1) | (i2 << 31);
        int i4 = (i ^ i3) & (-1431655766);
        int i5 = i ^ i4;
        int i6 = i3 ^ i4;
        int i7 = (i5 >>> 1) | (i5 << 31);
        int i8 = ((i7 >>> 8) ^ i6) & 16711935;
        int i9 = i6 ^ i8;
        int i10 = i7 ^ (i8 << 8);
        int i11 = ((i10 >>> 2) ^ i9) & 858993459;
        int i12 = i9 ^ i11;
        int i13 = i10 ^ (i11 << 2);
        int i14 = ((i12 >>> 16) ^ i13) & 65535;
        int i15 = i13 ^ i14;
        int i16 = i12 ^ (i14 << 16);
        int i17 = ((i16 >>> 4) ^ i15) & 252645135;
        iArr[1] = i15 ^ i17;
        iArr[0] = i16 ^ (i17 << 4);
    }

    private static void d() {
        long j = 576460752303423488L;
        for (int i = 0; i < 56; i++) {
            if (i == 28) {
                j = 134217728;
            }
            J[I[i]] = j;
            j >>= 1;
        }
    }

    private static void f() {
        int i = 0;
        for (int i2 = 0; i2 < 48; i2++) {
            if (i2 == 0 || i2 == 24) {
                i = 8388608;
            }
            K[H[i2]] = i;
            i >>= 1;
        }
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return 56;
    }

    @Override // iaik.security.cipher.t
    void a() {
        if (this.d == 2) {
            System.arraycopy(this.c, 0, this.b, 0, 4);
            int[] iArr = this.b;
            this.C = iArr[0];
            this.D = iArr[1];
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!key.getFormat().equals("RAW")) {
            throw new InvalidKeyException("Key must have RAW format!");
        }
        int length = key.getEncoded().length;
        if (length != 8) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid Key length (");
            stringBuffer.append(length);
            stringBuffer.append("). Key must be 8 bytes long!");
            throw new InvalidKeyException(stringBuffer.toString());
        }
        if (algorithmParameterSpec != null && (algorithmParameterSpec instanceof DESParameterSpec)) {
            DESParameterSpec dESParameterSpec = (DESParameterSpec) algorithmParameterSpec;
            byte[] sBoxes = dESParameterSpec.getSBoxes();
            byte[] pBox = dESParameterSpec.getPBox();
            if (sBoxes == null && pBox == null) {
                a(new int[8][]);
            } else {
                if (sBoxes == null) {
                    sBoxes = F;
                }
                if (pBox == null) {
                    pBox = E;
                }
                a(a(sBoxes, pBox, (int[][]) null));
            }
        }
        if (this.d == 2) {
            this.e = a(i, algorithmParameterSpec, secureRandom, 8);
            CryptoUtils.squashBytesToInts(this.e, 0, this.b, 0, 2);
            System.arraycopy(this.b, 0, this.c, 0, 2);
            int[] iArr = this.b;
            this.C = iArr[0];
            this.D = iArr[1];
        } else {
            this.e = null;
        }
        a(key.getEncoded(), i);
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        CryptoUtils.squashBytesToInts(bArr, i, this.b, 0, 2);
        if (this.d == 2) {
            int[] iArr = this.b;
            iArr[2] = iArr[0];
            iArr[3] = iArr[1];
            a(iArr);
            c(this.b);
            b(this.b);
            int[] iArr2 = this.b;
            iArr2[0] = iArr2[0] ^ this.C;
            iArr2[1] = iArr2[1] ^ this.D;
            this.C = iArr2[2];
            this.D = iArr2[3];
        } else {
            a(this.b);
            c(this.b);
            b(this.b);
        }
        CryptoUtils.spreadIntsToBytes(this.b, 0, bArr2, i3, 2);
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
        CryptoUtils.squashBytesToInts(bArr, i, this.b, 0, 2);
        if (this.d == 2) {
            int[] iArr = this.b;
            iArr[0] = iArr[0] ^ this.C;
            iArr[1] = iArr[1] ^ this.D;
            a(iArr);
            c(this.b);
            b(this.b);
            int[] iArr2 = this.b;
            this.C = iArr2[0];
            this.D = iArr2[1];
        } else {
            a(this.b);
            c(this.b);
            b(this.b);
        }
        CryptoUtils.spreadIntsToBytes(this.b, 0, bArr2, i3, 2);
    }

    public void c() {
        CryptoUtils.zeroBlock(this.a);
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.zeroBlock(this.c);
        if (this.e != null) {
            CryptoUtils.zeroBlock(this.e);
        }
        this.D = 0;
        this.C = 0;
        this.B = null;
        this.A = null;
        this.z = null;
        this.y = null;
        this.x = null;
        this.w = null;
        this.v = null;
        this.u = null;
    }

    void c(int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        if (this.u == null) {
            int i3 = 0;
            while (i3 < 32) {
                int[] iArr2 = this.a;
                int i4 = i3 + 1;
                int i5 = iArr2[i3] ^ ((i2 << 28) | (i2 >>> 4));
                int[] iArr3 = s;
                int i6 = i ^ iArr3[i5 & 63];
                int[] iArr4 = q;
                int i7 = i6 ^ iArr4[(i5 >>> 8) & 63];
                int[] iArr5 = o;
                int i8 = i7 ^ iArr5[(i5 >>> 16) & 63];
                int[] iArr6 = m;
                int i9 = i8 ^ iArr6[i5 >>> 24];
                int i10 = i4 + 1;
                int i11 = iArr2[i4] ^ i2;
                int[] iArr7 = t;
                int i12 = i9 ^ iArr7[i11 & 63];
                int[] iArr8 = r;
                int i13 = i12 ^ iArr8[(i11 >>> 8) & 63];
                int[] iArr9 = p;
                int i14 = i13 ^ iArr9[(i11 >>> 16) & 63];
                int[] iArr10 = n;
                i = i14 ^ iArr10[i11 >>> 24];
                int i15 = i10 + 1;
                int i16 = iArr2[i10] ^ ((i << 28) | (i >>> 4));
                int i17 = (((i2 ^ iArr3[i16 & 63]) ^ iArr4[(i16 >>> 8) & 63]) ^ iArr5[(i16 >>> 16) & 63]) ^ iArr6[i16 >>> 24];
                int i18 = iArr2[i15] ^ i;
                i2 = (((i17 ^ iArr7[i18 & 63]) ^ iArr8[(i18 >>> 8) & 63]) ^ iArr9[(i18 >>> 16) & 63]) ^ iArr10[i18 >>> 24];
                i3 = i15 + 1;
            }
        } else {
            int i19 = 0;
            for (int i20 = 32; i19 < i20; i20 = 32) {
                int[] iArr11 = this.a;
                int i21 = i19 + 1;
                int i22 = iArr11[i19] ^ ((i2 << 28) | (i2 >>> 4));
                int[] iArr12 = this.A;
                int i23 = i ^ iArr12[i22 & 63];
                int[] iArr13 = this.y;
                int i24 = i23 ^ iArr13[(i22 >>> 8) & 63];
                int[] iArr14 = this.w;
                int i25 = i24 ^ iArr14[(i22 >>> 16) & 63];
                int[] iArr15 = this.u;
                int i26 = i25 ^ iArr15[i22 >>> 24];
                int i27 = i21 + 1;
                int i28 = iArr11[i21] ^ i2;
                int[] iArr16 = this.B;
                int i29 = i26 ^ iArr16[i28 & 63];
                int[] iArr17 = this.z;
                int i30 = i29 ^ iArr17[(i28 >>> 8) & 63];
                int[] iArr18 = this.x;
                int i31 = i30 ^ iArr18[(i28 >>> 16) & 63];
                int[] iArr19 = this.v;
                i = i31 ^ iArr19[i28 >>> 24];
                int i32 = i27 + 1;
                int i33 = iArr11[i27] ^ ((i << 28) | (i >>> 4));
                int i34 = (((i2 ^ iArr12[i33 & 63]) ^ iArr13[(i33 >>> 8) & 63]) ^ iArr14[(i33 >>> 16) & 63]) ^ iArr15[i33 >>> 24];
                int i35 = iArr11[i32] ^ i;
                i2 = (((i34 ^ iArr16[i35 & 63]) ^ iArr17[(i35 >>> 8) & 63]) ^ iArr18[(i35 >>> 16) & 63]) ^ iArr19[i35 >>> 24];
                i19 = i32 + 1;
            }
        }
        iArr[0] = i2;
        iArr[1] = i;
    }

    protected void finalize() {
        c();
    }
}

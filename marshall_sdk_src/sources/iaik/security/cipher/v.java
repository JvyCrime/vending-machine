package iaik.security.cipher;

import com.bitmick.marshall.vmc.marshall_t;
import com.google.android.gms.stats.CodePackage;
import iaik.pkcs.pkcs1.Padding;
import iaik.security.random.SecRandom;
import iaik.utils.CryptoUtils;
import java.lang.reflect.Array;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
final class v extends t {
    static Class a;
    private static final byte[] v = new byte[16];
    private final t b;
    private q c;
    private q m;
    private byte[] n;
    private byte[] o;
    private int p;
    private byte[] q;
    private byte[] r;
    private long s;
    private byte[] t;
    private byte[] u;
    private final byte[] w;
    private final long[][][] x;

    public v(t tVar) throws NoSuchAlgorithmException {
        super(tVar.i(), 16, tVar.h());
        this.w = new byte[16];
        this.x = (long[][][]) Array.newInstance((Class<?>) long.class, 32, 16, 2);
        if (tVar.g() != 16) {
            throw new NoSuchAlgorithmException("Encryption algorithm must process a block-size of 128 bit.");
        }
        this.b = tVar;
        this.g = 16;
    }

    private void a(byte[] bArr, int i) {
        byte[] bArr2 = this.t;
        CryptoUtils.xorBlock(bArr2, 0, bArr, i, bArr2, 0, 16);
        byte[] bArr3 = this.t;
        a(bArr3, bArr3);
    }

    private void a(byte[] bArr, byte[] bArr2) {
        long[] jArr = new long[2];
        byte b = bArr[0];
        System.arraycopy(this.x[0][(b & marshall_t.marshall_packet_option_rfu_mask) >>> 4], 0, jArr, 0, 2);
        CryptoUtils.xorBlock(jArr, this.x[1][b & 15], jArr);
        for (int i = 1; i < 16; i++) {
            byte b2 = bArr[i];
            int i2 = i << 1;
            CryptoUtils.xorBlock(jArr, this.x[i2][(b2 & marshall_t.marshall_packet_option_rfu_mask) >>> 4], jArr);
            CryptoUtils.xorBlock(jArr, this.x[i2 + 1][b2 & 15], jArr);
        }
        CryptoUtils.spreadLongsToBytes(jArr, 0, bArr2, 0, 2);
    }

    private long[] a(long[] jArr) {
        long[] jArr2 = new long[2];
        System.arraycopy(jArr, 0, jArr2, 0, 2);
        long j = jArr2[1] & 1;
        long[] jArrShiftRight = CryptoUtils.shiftRight(jArr);
        if (j == 1) {
            jArrShiftRight[0] = jArrShiftRight[0] ^ (-2233785415175766016L);
        }
        return jArrShiftRight;
    }

    private void c() throws InvalidKeyException {
        try {
            this.b.b(v, 0, 16, this.w, 0);
            f();
        } catch (IllegalBlockSizeException e) {
            throw new InvalidKeyException(e.toString());
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private void d() {
        byte[] bArr = this.n;
        int length = bArr == null ? 0 : bArr.length;
        int i = 0;
        while (true) {
            int i2 = i + 16;
            if (i2 > length) {
                break;
            }
            a(this.n, i);
            i = i2;
        }
        if (i < length) {
            byte[] bArr2 = new byte[16];
            System.arraycopy(this.n, i, bArr2, 0, length - i);
            a(bArr2, 0);
        }
    }

    private void f() {
        int length = this.x.length;
        j();
        int i = 0;
        CryptoUtils.squashBytesToLongs(this.w, 0, this.x[0][8], 0, 2);
        while (i < length) {
            for (int i2 = 4; i2 > 0; i2 >>= 1) {
                long[][][] jArr = this.x;
                jArr[i][i2] = a(jArr[i][i2 << 1]);
            }
            int i3 = 2;
            while (true) {
                if (i3 > 8) {
                    break;
                }
                for (int i4 = 1; i4 < i3; i4++) {
                    long[][][] jArr2 = this.x;
                    CryptoUtils.xorBlock(jArr2[i][i3], jArr2[i][i4], jArr2[i][i3 + i4]);
                }
                i3 <<= 1;
            }
            long[][][] jArr3 = this.x;
            int i5 = i + 1;
            jArr3[i5][8] = a(jArr3[i][1]);
            for (int i6 = 4; i6 > 0; i6 >>= 1) {
                long[][][] jArr4 = this.x;
                jArr4[i5][i6] = a(jArr4[i5][i6 << 1]);
            }
            for (int i7 = 2; i7 <= 8; i7 <<= 1) {
                for (int i8 = 1; i8 < i7; i8++) {
                    long[][][] jArr5 = this.x;
                    CryptoUtils.xorBlock(jArr5[i5][i7], jArr5[i5][i8], jArr5[i5][i7 + i8]);
                }
            }
            if (i5 < length - 2) {
                long[][][] jArr6 = this.x;
                jArr6[i5 + 1][8] = a(jArr6[i5][1]);
            }
            i = i5 + 1;
        }
    }

    private void j() {
        for (int i = 0; i < 32; i++) {
            for (int i2 = 0; i2 < 16; i2++) {
                CryptoUtils.zeroBlock(this.x[i][i2]);
            }
        }
    }

    @Override // iaik.security.cipher.t
    int a(int i, int i2, boolean z, boolean z2) {
        int i3 = i + i2;
        return z ? i3 : i3 - (i3 % 16);
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return this.b.a(key);
    }

    @Override // iaik.security.cipher.t
    void a() {
        this.b.a();
        if (this.d == 8) {
            this.c.a();
            this.m.a();
            this.s = 0L;
            byte[] bArr = this.r;
            if (bArr != null) {
                byte[] bArr2 = this.q;
                System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
            } else {
                this.q = null;
            }
            byte[] bArr3 = this.u;
            if (bArr3 == null) {
                this.t = null;
            } else {
                byte[] bArr4 = this.t;
                System.arraycopy(bArr3, 0, bArr4, 0, bArr4.length);
            }
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.d != 8) {
            this.b.a(i, key, algorithmParameters, secureRandom);
            this.e = null;
            return;
        }
        try {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.security.cipher.GCMParameterSpec");
                a = clsClass$;
            }
            a(i, key, algorithmParameters.getParameterSpec(clsClass$), secureRandom);
        } catch (InvalidParameterSpecException unused) {
            super.a(i, key, algorithmParameters, secureRandom);
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] bArr;
        if (this.d != 8) {
            this.b.a(i, key, algorithmParameterSpec, secureRandom);
            this.e = null;
            return;
        }
        this.s = 0L;
        this.p = -1;
        this.o = null;
        this.n = null;
        this.q = null;
        this.t = new byte[16];
        this.b.a(1, key, algorithmParameterSpec, secureRandom);
        if (algorithmParameterSpec instanceof GCMParameterSpec) {
            GCMParameterSpec gCMParameterSpec = (GCMParameterSpec) algorithmParameterSpec;
            this.n = gCMParameterSpec.getAAD();
            this.o = gCMParameterSpec.getNonce();
            this.p = gCMParameterSpec.getMacLength();
            this.q = gCMParameterSpec.getMac();
        } else if (algorithmParameterSpec != null) {
            byte[] bArrA = t.a(i, algorithmParameterSpec, secureRandom, -1);
            this.o = bArrA;
            if (bArrA == null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("GCM can not be used with ");
                stringBuffer.append(algorithmParameterSpec.getClass());
                throw new InvalidAlgorithmParameterException(stringBuffer.toString());
            }
        }
        if (i == 1) {
            byte[] bArr2 = this.q;
            if (bArr2 != null && bArr2.length != 0) {
                throw new InvalidAlgorithmParameterException("Authentication block must not be specified for encryption");
            }
            byte[] bArr3 = this.o;
            if (bArr3 == null || bArr3.length == 0) {
                this.o = new byte[12];
                if (secureRandom == null) {
                    secureRandom = SecRandom.getDefault();
                }
                secureRandom.nextBytes(this.o);
            }
            if (this.p == -1) {
                this.p = 12;
            }
            this.q = new byte[this.p];
        } else {
            byte[] bArr4 = this.q;
            if (bArr4 == null || bArr4.length == 0) {
                throw new InvalidAlgorithmParameterException("Authentication block (MAC) has to be specified for decryption");
            }
            this.p = bArr4.length;
            byte[] bArr5 = this.o;
            if (bArr5 == null || bArr5.length == 0) {
                throw new InvalidAlgorithmParameterException("Nonce has to be specified for decryption");
            }
        }
        this.e = new byte[this.o.length];
        System.arraycopy(this.o, 0, this.e, 0, this.o.length);
        c();
        byte[] bArr6 = new byte[16];
        if (this.o.length == 12) {
            CryptoUtils.increment(bArr6);
            byte[] bArr7 = this.o;
            System.arraycopy(bArr7, 0, bArr6, 0, bArr7.length);
        } else {
            int i2 = 0;
            while (true) {
                int i3 = i2 + 16;
                bArr = this.o;
                if (i3 > bArr.length) {
                    break;
                }
                a(bArr, i2);
                i2 = i3;
            }
            if (i2 < bArr.length) {
                byte[] bArr8 = new byte[16];
                System.arraycopy(bArr, i2, bArr8, 0, bArr.length - i2);
                a(bArr8, 0);
            }
            byte[] bArr9 = new byte[16];
            long length = this.o.length << 3;
            for (int i4 = 15; i4 >= 8; i4--) {
                bArr9[i4] = (byte) length;
                length >>= 8;
            }
            a(bArr9, 0);
            System.arraycopy(this.t, 0, bArr6, 0, 16);
            this.t = new byte[16];
        }
        this.m.a(1, key, new IvParameterSpec(bArr6), (SecureRandom) null);
        CryptoUtils.increment(bArr6);
        this.c.a(1, key, new IvParameterSpec(bArr6), (SecureRandom) null);
        d();
        this.u = (byte[]) this.t.clone();
        this.r = (byte[]) this.q.clone();
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 8) {
            this.b.b(bArr, i, i2, bArr2, i3);
            return;
        }
        this.c.b(bArr, i, i2, bArr2, i3);
        a(bArr, i);
        this.s += (long) i2;
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i != 8) {
            if (i != 1) {
                this.d = 0;
                this.b.a(false);
                return this.b.a(i, i2);
            }
            this.d = i;
            this.b.a(1, 0);
            this.b.a(false);
            return true;
        }
        this.d = i;
        q qVar = new q(this.b);
        this.c = qVar;
        qVar.a(6, 0);
        q qVar2 = new q(this.b);
        this.m = qVar2;
        qVar2.a(6, 0);
        this.b.a(1, 0);
        this.j = false;
        this.k = true;
        this.b.a(false);
        this.c.a(false);
        this.m.a(false);
        return true;
    }

    @Override // iaik.security.cipher.t
    boolean a(String str) throws NoSuchPaddingException {
        if (this.d != 8 || str.equalsIgnoreCase(Padding.PADDING_NONE)) {
            return true;
        }
        throw new NoSuchPaddingException("Mode GCM must be used with Padding 'NoPadding'.");
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 8) {
            this.b.b(bArr, i, i2, bArr2, i3);
            return;
        }
        this.c.b(bArr, i, i2, bArr2, i3);
        this.s += (long) i2;
        a(bArr2, i3);
    }

    @Override // iaik.security.cipher.t
    void c(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        int i4 = 0;
        while (true) {
            int i5 = i4 + 16;
            if (i5 > i2) {
                break;
            }
            this.c.b(bArr, i + i4, 16, bArr2, i3);
            a(bArr2, i3);
            i3 += 16;
            i4 = i5;
        }
        if (i4 < i2) {
            int i6 = i + i4;
            int i7 = i2 - i4;
            this.c.b(bArr, i6, i7, bArr2, i3);
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArr2, i3, bArr3, 0, i7);
            a(bArr3, 0);
        }
        this.s += (long) i2;
        byte[] bArr4 = new byte[16];
        long length = this.n == null ? 0L : r10.length << 3;
        for (int i8 = 7; i8 >= 0; i8--) {
            bArr4[i8] = (byte) length;
            length >>= 8;
        }
        long j = this.s << 3;
        for (int i9 = 15; i9 >= 8; i9--) {
            bArr4[i9] = (byte) j;
            j >>= 8;
        }
        a(bArr4, 0);
        q qVar = this.m;
        byte[] bArr5 = this.t;
        qVar.b(bArr5, 0, 16, bArr5, 0);
        System.arraycopy(this.t, 0, this.q, 0, this.p);
    }

    @Override // iaik.security.cipher.t
    void d(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        int i4 = 0;
        while (true) {
            int i5 = i4 + 16;
            if (i5 > i2) {
                break;
            }
            int i6 = i + i4;
            this.c.b(bArr, i6, 16, bArr2, i3 + i4);
            a(bArr, i6);
            i4 = i5;
        }
        if (i4 < i2) {
            int i7 = i + i4;
            int i8 = i2 - i4;
            this.c.b(bArr, i7, i8, bArr2, i3 + i4);
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArr, i7, bArr3, 0, i8);
            a(bArr3, 0);
        }
        this.s += (long) i2;
        byte[] bArr4 = new byte[16];
        long length = this.n == null ? 0L : r12.length << 3;
        for (int i9 = 7; i9 >= 0; i9--) {
            bArr4[i9] = (byte) length;
            length >>= 8;
        }
        long j = this.s << 3;
        for (int i10 = 15; i10 >= 8; i10--) {
            bArr4[i10] = (byte) j;
            j >>= 8;
        }
        a(bArr4, 0);
        q qVar = this.m;
        byte[] bArr5 = this.t;
        qVar.b(bArr5, 0, 16, bArr5, 0);
        if (!CryptoUtils.secureEqualsBlock(this.t, 0, this.q, 0, this.p)) {
            throw new IllegalBlockSizeException("MAC verification not successful!");
        }
    }

    @Override // iaik.security.cipher.t
    AlgorithmParameters e() {
        if (this.d == 8) {
            try {
                byte[] bArr = this.q;
                GCMParameterSpec gCMParameterSpec = bArr != null ? new GCMParameterSpec(this.n, this.o, bArr) : new GCMParameterSpec(this.n, this.o, this.p);
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(CodePackage.GCM, "IAIK");
                algorithmParameters.init(gCMParameterSpec);
                return algorithmParameters;
            } catch (Exception unused) {
            }
        }
        return null;
    }
}

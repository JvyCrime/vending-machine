package iaik.security.cipher;

import iaik.pkcs.pkcs1.Padding;
import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;

/* JADX INFO: loaded from: classes.dex */
final class B extends t {
    private byte[] a;
    private byte[] b;
    private final t c;
    private int m;
    private int n;
    private int o;

    B(t tVar) {
        super(tVar.i(), -1, tVar.h());
        this.c = tVar;
        this.o = 0;
        this.i = false;
    }

    @Override // iaik.security.cipher.t
    int a(int i, int i2, boolean z, boolean z2) {
        int i3 = i + i2;
        return z ? i3 : i3 - (i3 % this.g);
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return this.c.a(key);
    }

    @Override // iaik.security.cipher.t
    void a() {
        this.c.a();
        if (this.d == 4) {
            System.arraycopy(this.e, 0, this.a, 0, this.m);
            this.o = 0;
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] iv = null;
        if (this.d != 4) {
            this.c.a(i, key, algorithmParameterSpec, secureRandom);
            this.e = null;
            return;
        }
        this.c.a(1, key, algorithmParameterSpec, secureRandom);
        if (algorithmParameterSpec != null) {
            if (algorithmParameterSpec instanceof IvParameterSpec) {
                iv = ((IvParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof RC2ParameterSpec) {
                iv = ((RC2ParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof RC5ParameterSpec) {
                iv = ((RC5ParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof DESParameterSpec) {
                iv = ((DESParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof GOSTParameterSpec) {
                iv = ((GOSTParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof CAST128ParameterSpec) {
                iv = ((CAST128ParameterSpec) algorithmParameterSpec).getIV();
            }
            if (iv != null && !CryptoUtils.equalsBlock(this.e, iv)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Iv must consist of ");
                stringBuffer.append(this.m);
                stringBuffer.append(" bytes (all zeros)!");
                throw new InvalidAlgorithmParameterException(stringBuffer.toString());
            }
        }
        System.arraycopy(this.e, 0, this.a, 0, this.m);
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = i;
        if (this.d != 4) {
            this.c.a(bArr, i, i2, bArr2, i3);
            return;
        }
        if (this.o == 0) {
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i, this.b, 0, bArr2, i3, this.m);
            System.arraycopy(bArr, i9, this.a, 0, this.m);
            int i10 = this.m;
            this.c.b(this.a, 0, i10, this.b, 0);
            this.o = 1;
            i4 = i2 - i10;
            i9 += i10;
            i5 = i3 + i10;
        } else {
            i4 = i2;
            i5 = i3;
        }
        while (i4 > 0) {
            int i11 = this.o;
            byte[] bArr3 = this.b;
            if (i11 == 1) {
                CryptoUtils.xorBlock(bArr, i9, bArr3, 0, bArr2, i5, 2);
                byte[] bArr4 = this.a;
                System.arraycopy(bArr4, 2, bArr4, 0, this.n);
                System.arraycopy(bArr, i9, this.a, this.n, 2);
                i6 = i4 - 2;
                i7 = i9 + 2;
                i8 = i5 + 2;
                this.o = 2;
            } else {
                CryptoUtils.xorBlock(bArr, i9, bArr3, this.n, bArr2, i5, 2);
                System.arraycopy(bArr, i9, this.a, this.n, 2);
                i6 = i4 - 2;
                i7 = i9 + 2;
                i8 = i5 + 2;
            }
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i7, this.b, 0, bArr2, i8, this.n);
            System.arraycopy(bArr, i7, this.a, 0, this.n);
            int i12 = this.n;
            i4 = i6 - i12;
            i9 = i7 + i12;
            i5 = i8 + i12;
        }
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i == 4) {
            this.j = false;
            this.k = true;
        }
        if (i != 4 && i != 1) {
            this.d = 0;
            boolean zA = this.c.a(i, i2);
            this.g = this.c.h();
            this.c.a(false);
            return zA;
        }
        this.d = i;
        this.c.a(1, 0);
        int iH = this.c.h();
        this.m = iH;
        this.n = iH - 2;
        if (i == 4) {
            if (i2 == -1) {
                this.g = iH;
            } else {
                this.g = i2;
            }
            int i3 = this.m;
            this.b = new byte[i3];
            this.a = new byte[i3];
            this.e = new byte[i3];
        } else {
            this.g = iH;
        }
        this.c.a(false);
        return true;
    }

    @Override // iaik.security.cipher.t
    boolean a(String str) throws NoSuchPaddingException {
        if (this.d != 4 || str.equalsIgnoreCase(Padding.PADDING_NONE)) {
            return true;
        }
        throw new NoSuchPaddingException("Mode OPENPGPCFB must be used with Padding 'NoPadding'.");
    }

    @Override // iaik.security.cipher.t
    public byte[] a_() {
        return this.d == 4 ? this.e : this.c.a_();
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        if (this.d != 4) {
            this.c.b(bArr, i, i2, bArr2, i3);
            return;
        }
        if (this.o == 0) {
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i, this.b, 0, bArr2, i3, this.m);
            System.arraycopy(bArr2, i3, this.a, 0, this.m);
            int i10 = this.m;
            this.c.b(this.a, 0, i10, this.b, 0);
            this.o = 1;
            i4 = i2 - i10;
            i6 = i + i10;
            i5 = i3 + i10;
        } else {
            i4 = i2;
            i5 = i3;
            i6 = i;
        }
        while (i4 > 0) {
            int i11 = this.o;
            byte[] bArr3 = this.b;
            if (i11 == 1) {
                CryptoUtils.xorBlock(bArr, i6, bArr3, 0, bArr2, i5, 2);
                byte[] bArr4 = this.a;
                System.arraycopy(bArr4, 2, bArr4, 0, this.n);
                System.arraycopy(bArr2, i5, this.a, this.n, 2);
                i7 = i4 - 2;
                i8 = i6 + 2;
                i9 = i5 + 2;
                this.o = 2;
            } else {
                CryptoUtils.xorBlock(bArr, i6, bArr3, this.n, bArr2, i5, 2);
                System.arraycopy(bArr2, i5, this.a, this.n, 2);
                i7 = i4 - 2;
                i8 = i6 + 2;
                i9 = i5 + 2;
            }
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i8, this.b, 0, bArr2, i9, this.n);
            System.arraycopy(bArr2, i9, this.a, 0, this.n);
            int i12 = this.n;
            i4 = i7 - i12;
            i6 = i8 + i12;
            i5 = i9 + i12;
        }
    }

    @Override // iaik.security.cipher.t
    void c(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        int i4;
        int i5;
        int i6;
        if (this.d == 4) {
            if (this.o == 0) {
                int i7 = i + i2;
                int i8 = this.m;
                if (i7 < i8 + 2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Data too short. Must be at least ");
                    stringBuffer.append(this.m + 2);
                    stringBuffer.append(" bytes");
                    throw new IllegalBlockSizeException(stringBuffer.toString());
                }
                this.c.b(this.a, 0, i8, this.b, 0);
                CryptoUtils.xorBlock(bArr, i, this.b, 0, bArr2, i3, this.m);
                System.arraycopy(bArr2, i3, this.a, 0, this.m);
                int i9 = this.m;
                this.c.b(this.a, 0, i9, this.b, 0);
                this.o = 1;
                i4 = i2 - i9;
                i6 = i + i9;
                i5 = i3 + i9;
            } else {
                i4 = i2;
                i5 = i3;
                i6 = i;
            }
            if (this.o == 1) {
                if (i6 + i4 < 2) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Data too short. Must be at least ");
                    stringBuffer2.append(this.m + 2);
                    stringBuffer2.append(" bytes");
                    throw new IllegalBlockSizeException(stringBuffer2.toString());
                }
                int i10 = i5;
                CryptoUtils.xorBlock(bArr, i6, this.b, 0, bArr2, i5, 2);
                byte[] bArr3 = this.a;
                System.arraycopy(bArr3, 2, bArr3, 0, this.n);
                System.arraycopy(bArr2, i10, this.a, this.n, 2);
                i4 -= 2;
                i6 += 2;
                i5 = i10 + 2;
            }
            int i11 = i5;
            while (i4 - this.m >= 0) {
                if (this.o == 2) {
                    CryptoUtils.xorBlock(bArr, i6, this.b, this.n, bArr2, i11, 2);
                    System.arraycopy(bArr2, i11, this.a, this.n, 2);
                    i4 -= 2;
                    i6 += 2;
                    i11 += 2;
                } else {
                    this.o = 2;
                }
                this.c.b(this.a, 0, this.m, this.b, 0);
                CryptoUtils.xorBlock(bArr, i6, this.b, 0, bArr2, i11, this.n);
                System.arraycopy(bArr2, i11, this.a, 0, this.n);
                int i12 = this.n;
                i4 -= i12;
                i6 += i12;
                i11 += i12;
            }
            if (this.o == 2 && i4 > 0) {
                int i13 = i4 != 1 ? 2 : 1;
                CryptoUtils.xorBlock(bArr, i6, this.b, this.n, bArr2, i11, i13);
                System.arraycopy(bArr2, i11, this.a, this.n, i13);
                i4 -= i13;
                i6 += i13;
                i11 += i13;
            }
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i6, this.b, 0, bArr2, i11, i4);
        } else {
            this.c.b(bArr, i, i2, bArr2, i3);
        }
        a();
    }

    @Override // iaik.security.cipher.t
    void d(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        int i4;
        int i5;
        int i6 = i;
        if (this.d == 4) {
            if (this.o == 0) {
                int i7 = i6 + i2;
                int i8 = this.m;
                if (i7 < i8 + 2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Data too short. Must be at least ");
                    stringBuffer.append(this.m + 2);
                    stringBuffer.append(" bytes");
                    throw new IllegalBlockSizeException(stringBuffer.toString());
                }
                this.c.b(this.a, 0, i8, this.b, 0);
                CryptoUtils.xorBlock(bArr, i, this.b, 0, bArr2, i3, this.m);
                System.arraycopy(bArr, i6, this.a, 0, this.m);
                int i9 = this.m;
                this.c.b(this.a, 0, i9, this.b, 0);
                this.o = 1;
                i4 = i2 - i9;
                i6 += i9;
                i5 = i3 + i9;
            } else {
                i4 = i2;
                i5 = i3;
            }
            if (this.o == 1) {
                if (i6 + i4 < 2) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Data too short. Must be at least ");
                    stringBuffer2.append(this.m + 2);
                    stringBuffer2.append(" bytes");
                    throw new IllegalBlockSizeException(stringBuffer2.toString());
                }
                CryptoUtils.xorBlock(bArr, i6, this.b, 0, bArr2, i5, 2);
                byte[] bArr3 = this.a;
                System.arraycopy(bArr3, 2, bArr3, 0, this.n);
                System.arraycopy(bArr, i6, this.a, this.n, 2);
                i4 -= 2;
                i6 += 2;
                i5 += 2;
            }
            while (i4 - this.m >= 0) {
                if (this.o == 2) {
                    CryptoUtils.xorBlock(bArr, i6, this.b, this.n, bArr2, i5, 2);
                    System.arraycopy(bArr, i6, this.a, this.n, 2);
                    i4 -= 2;
                    i6 += 2;
                    i5 += 2;
                } else {
                    this.o = 2;
                }
                this.c.b(this.a, 0, this.m, this.b, 0);
                CryptoUtils.xorBlock(bArr, i6, this.b, 0, bArr2, i5, this.n);
                System.arraycopy(bArr, i6, this.a, 0, this.n);
                int i10 = this.n;
                i4 -= i10;
                i6 += i10;
                i5 += i10;
            }
            if (this.o == 2 && i4 > 0) {
                int i11 = i4 != 1 ? 2 : 1;
                CryptoUtils.xorBlock(bArr, i6, this.b, this.n, bArr2, i5, i11);
                System.arraycopy(bArr, i6, this.a, this.n, i11);
                i4 -= i11;
                i6 += i11;
                i5 += i11;
            }
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i6, this.b, 0, bArr2, i5, i4);
        } else {
            this.c.a(bArr, i, i2, bArr2, i3);
        }
        a();
    }
}

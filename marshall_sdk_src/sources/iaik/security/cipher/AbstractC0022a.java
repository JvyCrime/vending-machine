package iaik.security.cipher;

import com.google.android.gms.stats.CodePackage;
import iaik.security.provider.IAIK;
import iaik.utils.Util;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Locale;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: renamed from: iaik.security.cipher.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0022a extends CipherSpi {
    t a;
    int d;
    int h;
    SecureRandom i;
    private boolean j;
    int b = 0;
    int c = 0;
    byte[] e = null;
    int f = 0;
    AbstractC0026g g = null;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    AbstractC0022a() {
    }

    AbstractC0022a(t tVar) {
        a(tVar);
    }

    static int a(int i) {
        if (i == 3) {
            return 1;
        }
        if (i == 4) {
            return 2;
        }
        return i;
    }

    private void b() {
        AbstractC0026g abstractC0026g = this.g;
        if (abstractC0026g != null) {
            abstractC0026g.a(this.c);
        }
    }

    private void b(int i) {
        this.c = i;
        this.e = new byte[i << 1];
        b();
    }

    final int a(int i, boolean z, boolean z2) throws IllegalBlockSizeException {
        if (!this.a.j && this.g == null) {
            return this.a.a(i, this.f, z, z2);
        }
        int i2 = this.f;
        int i3 = i + i2;
        int i4 = this.c;
        int i5 = i4 != 1 ? i4 != 8 ? i4 != 16 ? i4 != 32 ? i3 % i4 : i3 & 31 : i3 & 15 : i3 & 7 : 0;
        int i6 = (i + i2) - i5;
        if (!z) {
            return (this.d != 2 || i5 != 0 || i6 < i4 || this.g == null) ? i6 : i6 - i4;
        }
        if (this.g != null && this.d == 1) {
            return i6 + i4;
        }
        if (i5 <= 0) {
            return i6;
        }
        throw new IllegalBlockSizeException("Input data length not a multiple of blocksize.");
    }

    int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3, boolean z) throws BadPaddingException, IllegalBlockSizeException {
        byte[] bArr3;
        int i4;
        AbstractC0026g abstractC0026g;
        AbstractC0026g abstractC0026g2;
        if (this.j) {
            a();
            this.j = false;
        }
        int iA = a(i2, z, false);
        if (bArr == bArr2 && IAIK.getCopyCipherData()) {
            byte[] bArr4 = new byte[i2];
            System.arraycopy(bArr, i, bArr4, 0, i2);
            bArr3 = bArr4;
            i4 = 0;
        } else {
            bArr3 = bArr;
            i4 = i;
        }
        int i5 = (this.a.i && iA == 0) ? i2 : iA;
        int i6 = this.f;
        int i7 = (i2 + i6) - i5;
        if (i6 == 0 && (!z || this.g == null)) {
            b(bArr3, i4, i5, bArr2, i3, z);
            int iA2 = a(i2, z, true);
            if (i7 > 0) {
                byte[] bArr5 = this.e;
                if (bArr5.length < i7) {
                    this.e = Util.resizeArray(bArr5, i7);
                }
                System.arraycopy(bArr3, (i4 + i2) - i7, this.e, 0, i7);
                this.f = i7;
            }
            if (z) {
                this.j = true;
            }
            return iA2;
        }
        if (z && this.g != null) {
            i7 = 0;
        }
        if (i5 > 0) {
            byte[] bArr6 = new byte[i5];
            int i8 = i2 - i7;
            if (i6 > 0) {
                System.arraycopy(this.e, 0, bArr6, 0, i6);
            }
            if (i8 > 0) {
                System.arraycopy(bArr3, i4, bArr6, this.f, i8);
            }
            if (z && this.d == 1 && (abstractC0026g2 = this.g) != null) {
                abstractC0026g2.a(bArr6, 0, this.f + i2);
            }
            b(bArr6, 0, i5, bArr2, i3, z);
            int iA3 = a(i2, z, true);
            if (z && this.d == 2 && (abstractC0026g = this.g) != null) {
                iA3 = abstractC0026g.b(bArr2, i3, iA3);
            }
            i5 = iA3;
            if (i7 > 0) {
                System.arraycopy(bArr3, i4 + i8, this.e, 0, i7);
            }
            this.f = i7;
        } else if (i2 > 0) {
            System.arraycopy(bArr3, i4, this.e, i6, i2);
            this.f += i2;
        }
        if (z) {
            this.j = true;
        }
        return i5;
    }

    void a() {
        this.a.a();
        this.f = 0;
    }

    void a(t tVar) {
        this.a = tVar;
        this.b = tVar.g();
        int iH = this.a.h();
        this.h = iH;
        if (iH > 0) {
            this.g = new f();
        }
        b(this.b);
    }

    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3, boolean z) throws IllegalBlockSizeException {
        if (z && this.a.k) {
            if (this.d == 1) {
                this.a.c(bArr, i, i2, bArr2, i3);
                return;
            } else {
                this.a.d(bArr, i, i2, bArr2, i3);
                return;
            }
        }
        if (this.a.i) {
            if (this.d == 1) {
                this.a.b(bArr, i, i2, bArr2, i3);
                return;
            } else {
                this.a.a(bArr, i, i2, bArr2, i3);
                return;
            }
        }
        if (this.c == 1) {
            if (this.d == 1) {
                this.a.b(bArr, i, i2, bArr2, i3);
                return;
            } else {
                this.a.a(bArr, i, i2, bArr2, i3);
                return;
            }
        }
        int i4 = i2 + i;
        if (this.d == 1) {
            while (i < i4) {
                this.a.b(bArr, i, this.c, bArr2, i3);
                int i5 = this.c;
                i += i5;
                i3 += i5;
            }
            return;
        }
        while (i < i4) {
            this.a.a(bArr, i, this.c, bArr2, i3);
            int i6 = this.c;
            i += i6;
            i3 += i6;
        }
    }

    @Override // javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        return a(bArr, i, i2, bArr2, i3, true);
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) throws BadPaddingException, IllegalBlockSizeException {
        int iA = a(i2, true, true);
        byte[] bArr2 = new byte[iA];
        int iA2 = a(bArr, i, i2, bArr2, 0, true);
        if (iA2 >= 0) {
            return iA2 < iA ? Util.resizeArray(bArr2, iA2) : bArr2;
        }
        throw new BadPaddingException("Invalid padding length");
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        return this.h;
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineGetIV() {
        return this.a.a_();
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetKeySize(Key key) throws InvalidKeyException {
        return this.a.a(key);
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetOutputSize(int i) {
        try {
            return a(i, true, true);
        } catch (IllegalBlockSizeException unused) {
            int i2 = (this.f + i) % this.c;
            int i3 = (i + this.f) - i2;
            return i2 > 0 ? i3 + this.c : i3;
        }
    }

    @Override // javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        return this.a.e();
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.d = a(i);
        this.i = secureRandom;
        AbstractC0026g abstractC0026g = this.g;
        if (abstractC0026g != null) {
            abstractC0026g.a(secureRandom);
        }
        this.a.a(this.d, key, algorithmParameters, secureRandom);
        this.j = false;
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        this.d = a(i);
        this.i = secureRandom;
        AbstractC0026g abstractC0026g = this.g;
        if (abstractC0026g != null) {
            abstractC0026g.a(secureRandom);
        }
        try {
            this.a.a(this.d, key, (AlgorithmParameterSpec) null, secureRandom);
            this.j = false;
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.d = a(i);
        this.i = secureRandom;
        AbstractC0026g abstractC0026g = this.g;
        if (abstractC0026g != null) {
            abstractC0026g.a(secureRandom);
        }
        this.a.a(this.d, key, algorithmParameterSpec, secureRandom);
        this.j = false;
    }

    @Override // javax.crypto.CipherSpi
    public void engineSetMode(String str) throws NoSuchAlgorithmException {
        int i;
        int i2;
        t rVar;
        t qVar;
        t b;
        if (str == null) {
            return;
        }
        String upperCase = str.toUpperCase(Locale.US);
        int length = upperCase.length();
        int i3 = 0;
        while (i3 < length) {
            char cCharAt = upperCase.charAt(i3);
            if (cCharAt >= '0' && cCharAt <= '9') {
                break;
            } else {
                i3++;
            }
        }
        int i4 = 3;
        if (i3 < length) {
            int iIntValue = Integer.decode(upperCase.substring(i3, length)).intValue();
            upperCase = upperCase.substring(0, i3);
            if (iIntValue > (this.b << 3) || iIntValue % 8 != 0) {
                throw new NoSuchAlgorithmException("Invalid feedback value. Must be multiple of 8 and less than or equal to blocksize!");
            }
            i = iIntValue >>> 3;
        } else {
            i = -1;
        }
        if (upperCase.equals("CBC")) {
            if (i != -1) {
                throw new NoSuchAlgorithmException("CBC mode can only be used as a block-sized feedback cipher!");
            }
            i2 = 2;
            if (!this.a.a(2, i)) {
                rVar = new n(this.a);
                this.a = rVar;
                rVar.a(i2, i);
            }
        } else if (upperCase.equals("CFB")) {
            if (!this.a.a(4, i)) {
                b = new p(this.a);
                this.a = b;
                b.a(4, i);
            }
        } else if (upperCase.equals("OPENPGPCFB")) {
            if (!this.a.a(4, i)) {
                b = new B(this.a);
                this.a = b;
                b.a(4, i);
            }
        } else if (upperCase.equals("OFB")) {
            if (!this.a.a(3, i)) {
                qVar = new A(this.a);
                this.a = qVar;
                qVar.a(i4, i);
            }
        } else if (upperCase.equals("PCBC")) {
            if (i != -1) {
                throw new NoSuchAlgorithmException("PCBC mode can only be used as a block-sized feedback cipher!");
            }
            i2 = 5;
            if (!this.a.a(5, i)) {
                rVar = new C(this.a);
                this.a = rVar;
                rVar.a(i2, i);
            }
        } else if (upperCase.equals("CTR")) {
            if (i != -1) {
                throw new NoSuchAlgorithmException("CTR mode can only be used as a block-sized feedback cipher!");
            }
            i4 = 6;
            if (!this.a.a(6, i)) {
                this.a.a(false);
                qVar = new q(this.a);
                this.a = qVar;
                qVar.a(i4, i);
            }
        } else if (upperCase.equals("CCM")) {
            i2 = 7;
            if (!this.a.a(7, i)) {
                rVar = new o(this.a);
                this.a = rVar;
                rVar.a(i2, i);
            }
        } else if (upperCase.equals(CodePackage.GCM)) {
            i2 = 8;
            if (!this.a.a(8, i)) {
                rVar = new v(this.a);
                this.a = rVar;
                rVar.a(i2, i);
            }
        } else if (upperCase.equals("CTS")) {
            i2 = 9;
            if (!this.a.a(9, i)) {
                rVar = new r(this.a);
                this.a = rVar;
                rVar.a(i2, i);
            }
        } else if (upperCase.equals("ECB")) {
            this.a.a(1, 0);
        } else {
            if (!upperCase.equals("NONE")) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cipher mode ");
                stringBuffer.append(upperCase);
                stringBuffer.append(" not implemented!");
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
            this.a.a(0, 0);
        }
        b(this.a.g());
        if (upperCase.equals("CTR") || upperCase.equals("CTS") || upperCase.equals("CCM") || upperCase.equals(CodePackage.GCM)) {
            this.g = null;
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        AbstractC0026g eVar;
        String upperCase = str.toUpperCase(Locale.US);
        if (upperCase.equals("PKCS5PADDING")) {
            eVar = new f();
        } else if (upperCase.equals("SSL3PADDING")) {
            eVar = new L();
        } else if (upperCase.equals("NOPADDING")) {
            eVar = null;
        } else if (upperCase.equals("ISO78164PADDING") || upperCase.equals("ISO-7816-4")) {
            eVar = new e();
        } else {
            if (!upperCase.equals("ISO10126-2") && !upperCase.equals("ISO10126") && !upperCase.equals("ISO10126-2PADDING") && !upperCase.equals("ISO10126PADDING")) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Padding '");
                stringBuffer.append(str);
                stringBuffer.append("' not implemented.");
                throw new NoSuchPaddingException(stringBuffer.toString());
            }
            eVar = new C0025d();
        }
        this.g = eVar;
        this.a.a(str);
        b();
    }

    @Override // javax.crypto.CipherSpi
    protected Key engineUnwrap(byte[] bArr, String str, int i) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            return Util.decodeKey(i, str, engineDoFinal(bArr, 0, bArr.length));
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not unwrap key: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        } catch (IllegalBlockSizeException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Could not unwrap key: ");
            stringBuffer2.append(e2.toString());
            throw new InvalidKeyException(stringBuffer2.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException {
        try {
            return a(bArr, i, i2, bArr2, i3, false);
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Internal error: ");
            stringBuffer.append(e.toString());
            throw new RuntimeException(stringBuffer.toString());
        } catch (IllegalBlockSizeException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Internal error: ");
            stringBuffer2.append(e2.toString());
            throw new RuntimeException(stringBuffer2.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        try {
            byte[] bArr2 = new byte[a(i2, false, true)];
            a(bArr, i, i2, bArr2, 0, false);
            return bArr2;
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Internal error: ");
            stringBuffer.append(e.toString());
            throw new RuntimeException(stringBuffer.toString());
        } catch (IllegalBlockSizeException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Internal error: ");
            stringBuffer2.append(e2.toString());
            throw new RuntimeException(stringBuffer2.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        byte[] encoded = key.getEncoded();
        if (encoded == null || encoded.length == 0) {
            throw new InvalidKeyException("Could not encode key for wrapping!");
        }
        try {
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not wrap key: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public int getModeBlockSize() {
        return this.c;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.a.i());
        if (this.g != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(" and ");
            stringBuffer2.append(this.g.a());
            stringBuffer.append(stringBuffer2.toString());
        }
        return stringBuffer.toString();
    }
}

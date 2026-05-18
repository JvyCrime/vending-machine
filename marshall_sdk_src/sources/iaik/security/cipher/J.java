package iaik.security.cipher;

import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
final class J extends t {
    private final int[] a;
    private final int[] b;
    private final u c;
    private final u m;
    private final u n;
    private int o;
    private int p;

    J() {
        super("DESede", 8, 8);
        this.a = new int[4];
        this.b = new int[4];
        this.c = new u();
        this.m = new u();
        this.n = new u();
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_OSC_TRIM1_REG;
    }

    @Override // iaik.security.cipher.t
    void a() {
        this.c.a();
        this.m.a();
        this.n.a();
        if (this.d == 2) {
            int[] iArr = this.a;
            int[] iArr2 = this.b;
            System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
            int[] iArr3 = this.b;
            this.o = iArr3[0];
            this.o = iArr3[0];
            this.p = iArr3[1];
            this.p = iArr3[1];
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] encoded = key.getEncoded();
        if (!key.getFormat().equals("RAW") || (encoded.length != 16 && encoded.length != 24)) {
            throw new InvalidKeyException("Key must be RAW and 16 or 24 bytes long!");
        }
        SecretKey secretKey = new SecretKey(encoded, 0, 8, "DES");
        SecretKey secretKey2 = new SecretKey(encoded, 8, 8, "DES");
        SecretKey secretKey3 = encoded.length == 24 ? new SecretKey(encoded, 16, 8, "DES") : secretKey;
        u uVar = this.c;
        if (i == 1) {
            uVar.a(1, secretKey, algorithmParameterSpec, secureRandom);
            this.m.a(2, secretKey2, algorithmParameterSpec, secureRandom);
            this.n.a(1, secretKey3, algorithmParameterSpec, secureRandom);
        } else {
            uVar.a(2, secretKey3, algorithmParameterSpec, secureRandom);
            this.m.a(1, secretKey2, algorithmParameterSpec, secureRandom);
            this.n.a(2, secretKey, algorithmParameterSpec, secureRandom);
        }
        if (this.d != 2) {
            this.e = null;
            return;
        }
        this.e = a(i, algorithmParameterSpec, secureRandom, 8);
        CryptoUtils.squashBytesToInts(this.e, 0, this.b, 0, 2);
        int[] iArr = this.b;
        System.arraycopy(iArr, 0, this.a, 0, iArr.length);
        int[] iArr2 = this.b;
        this.o = iArr2[0];
        this.o = iArr2[0];
        this.p = iArr2[1];
        this.p = iArr2[1];
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        CryptoUtils.squashBytesToInts(bArr, i, this.b, 0, 2);
        if (this.d == 2) {
            int[] iArr = this.b;
            iArr[2] = iArr[0];
            iArr[3] = iArr[1];
            u.a(iArr);
            this.c.c(this.b);
            this.m.c(this.b);
            this.n.c(this.b);
            u.b(this.b);
            int[] iArr2 = this.b;
            iArr2[0] = iArr2[0] ^ this.o;
            iArr2[1] = iArr2[1] ^ this.p;
            this.o = iArr2[2];
            this.p = iArr2[3];
        } else {
            u.a(this.b);
            this.c.c(this.b);
            this.m.c(this.b);
            this.n.c(this.b);
            u.b(this.b);
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
            iArr[0] = iArr[0] ^ this.o;
            iArr[1] = iArr[1] ^ this.p;
            u.a(iArr);
            this.c.c(this.b);
            this.m.c(this.b);
            this.n.c(this.b);
            u.b(this.b);
            int[] iArr2 = this.b;
            this.o = iArr2[0];
            this.p = iArr2[1];
        } else {
            u.a(this.b);
            this.c.c(this.b);
            this.m.c(this.b);
            this.n.c(this.b);
            u.b(this.b);
        }
        CryptoUtils.spreadIntsToBytes(this.b, 0, bArr2, i3, 2);
    }
}

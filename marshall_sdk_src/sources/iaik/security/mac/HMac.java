package iaik.security.mac;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.MacSpi;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
public class HMac extends MacSpi {
    private int a;
    private byte[] b;
    private int c;
    private MessageDigest d;
    private byte[] e;
    private byte[] f;

    public HMac(String str) throws NoSuchAlgorithmException {
        this(str, 64);
    }

    public HMac(String str, int i) throws NoSuchAlgorithmException {
        try {
            this.d = MessageDigest.getInstance(str, "IAIK");
        } catch (Throwable unused) {
            this.d = MessageDigest.getInstance(str);
        }
        this.c = this.d.getDigestLength();
        this.a = i;
        this.e = new byte[i];
        this.f = new byte[i];
    }

    @Override // javax.crypto.MacSpi
    public byte[] engineDoFinal() {
        byte[] bArrDigest = this.d.digest();
        this.d.update(this.f);
        this.d.update(bArrDigest);
        byte[] bArrDigest2 = this.d.digest();
        engineReset();
        return bArrDigest2;
    }

    @Override // javax.crypto.MacSpi
    public int engineGetMacLength() {
        return this.c;
    }

    @Override // javax.crypto.MacSpi
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException {
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException("Key must be a SecretKey object");
        }
        this.b = new byte[this.a];
        byte[] encoded = key.getEncoded();
        this.d.reset();
        if (encoded.length > this.a) {
            encoded = this.d.digest(encoded);
        }
        System.arraycopy(encoded, 0, this.b, 0, encoded.length);
        for (int i = 0; i < this.a; i++) {
            byte[] bArr = this.e;
            byte[] bArr2 = this.b;
            bArr[i] = (byte) (bArr2[i] ^ 54);
            this.f[i] = (byte) (bArr2[i] ^ 92);
        }
        engineReset();
    }

    @Override // javax.crypto.MacSpi
    public void engineReset() {
        this.d.reset();
        this.d.update(this.e);
    }

    @Override // javax.crypto.MacSpi
    public void engineUpdate(byte b) {
        this.d.update(b);
    }

    @Override // javax.crypto.MacSpi
    public void engineUpdate(byte[] bArr, int i, int i2) {
        this.d.update(bArr, i, i2);
    }
}

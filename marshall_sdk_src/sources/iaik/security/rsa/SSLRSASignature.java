package iaik.security.rsa;

import iaik.pkcs.pkcs1.Padding;
import iaik.security.md.Md5;
import iaik.security.md.SHA;
import iaik.utils.CryptoUtils;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;

/* JADX INFO: loaded from: classes.dex */
public class SSLRSASignature extends SignatureSpi {
    private MessageDigest a = new Md5();
    private MessageDigest b = new SHA();
    private RSA c;

    public SSLRSASignature() throws NoSuchAlgorithmException {
        try {
            RSA rsaA = RSA.a();
            this.c = rsaA;
            rsaA.setMode("ECB");
            this.c.setPadding(Padding.PADDING_PKCS1);
        } catch (Exception e) {
            throw new NoSuchAlgorithmException(e.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected Object engineGetParameter(String str) throws InvalidParameterException {
        throw new InvalidParameterException("Method not supported!");
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        engineInitSign(privateKey, null);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        this.appRandom = secureRandom;
        this.c.init(1, privateKey, secureRandom);
        this.a.reset();
        this.b.reset();
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        this.c.init(2, publicKey, null);
        this.a.reset();
        this.b.reset();
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) throws InvalidParameterException {
        throw new InvalidParameterException("Method not supported!");
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        byte[] bArr = new byte[36];
        System.arraycopy(this.a.digest(), 0, bArr, 0, 16);
        System.arraycopy(this.b.digest(), 0, bArr, 16, 20);
        try {
            return this.c.doFinal(bArr, 0, 36);
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) {
        this.a.update(b);
        this.b.update(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
        this.b.update(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        byte[] bArr2 = new byte[36];
        System.arraycopy(this.a.digest(), 0, bArr2, 0, 16);
        System.arraycopy(this.b.digest(), 0, bArr2, 16, 20);
        try {
            return CryptoUtils.secureEqualsBlock(bArr2, this.c.doFinal(bArr, 0, bArr.length));
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }
}

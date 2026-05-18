package iaik.security.dsa;

import iaik.security.md.SHA;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class DSA extends SignatureSpi {
    private MessageDigest a;
    private RawDSA b;

    public DSA() {
        this("SHA1", new SHA());
    }

    DSA(String str, MessageDigest messageDigest) {
        this.b = new RawDSA();
        this.a = messageDigest;
    }

    @Override // java.security.SignatureSpi
    protected Object engineGetParameter(String str) throws InvalidParameterException {
        return this.b.engineGetParameter(str);
    }

    @Override // java.security.SignatureSpi
    protected AlgorithmParameters engineGetParameters() {
        return this.b.engineGetParameters();
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (this.appRandom != null) {
            this.b.a(this.appRandom);
        }
        this.b.engineInitSign(privateKey);
        this.a.reset();
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        this.b.a(secureRandom);
        engineInitSign(privateKey);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        this.b.engineInitVerify(publicKey);
        this.a.reset();
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) throws InvalidParameterException {
        this.b.engineSetParameter(str, obj);
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.b.engineSetParameter(algorithmParameterSpec);
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        byte[] bArrDigest = this.a.digest();
        this.b.engineUpdate(bArrDigest, 0, bArrDigest.length);
        return this.b.engineSign();
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) throws SignatureException {
        this.a.update(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) throws SignatureException {
        this.a.update(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        byte[] bArrDigest = this.a.digest();
        this.b.engineUpdate(bArrDigest, 0, bArrDigest.length);
        return this.b.engineVerify(bArr);
    }
}

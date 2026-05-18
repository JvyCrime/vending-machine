package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.PKCS1AlgorithmParameterSpec;
import iaik.security.random.SecRandom;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
abstract class b extends SignatureSpi {
    private String a;
    private RSA b;
    AlgorithmID c;
    private int d;
    private SecureRandom e;
    protected MessageDigest hash;

    protected b(String str, AlgorithmID algorithmID, MessageDigest messageDigest, String str2) {
        this.c = algorithmID;
        this.hash = messageDigest;
        this.a = str2;
    }

    protected b(String str, String str2) {
        this.a = str2;
    }

    protected b(String str, MessageDigest messageDigest, String str2) {
        this.hash = messageDigest;
        this.a = str2;
    }

    void a(SecureRandom secureRandom) {
        this.e = secureRandom;
        RSA rsa = this.b;
        if (rsa != null) {
            rsa.setSecureRandom(secureRandom);
        }
    }

    byte[] a() {
        MessageDigest messageDigest = this.hash;
        Objects.requireNonNull(messageDigest, "MessageDigest engine must not be null!");
        return messageDigest.digest();
    }

    byte[] a(byte[] bArr) throws Exception {
        return this.b.doFinal(bArr, 0, bArr.length);
    }

    int b() {
        return this.d;
    }

    SecureRandom c() {
        if (this.e == null) {
            a(this.appRandom == null ? SecRandom.getDefault() : this.appRandom);
        }
        return this.e;
    }

    int d() {
        return 1;
    }

    @Override // java.security.SignatureSpi
    protected Object engineGetParameter(String str) throws InvalidParameterException {
        throw new InvalidParameterException("Method not supported!");
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        try {
            RSA rsaA = RSA.a();
            this.b = rsaA;
            rsaA.setMode("ECB");
            this.b.setPadding(this.a);
            this.b.init(d(), privateKey, c());
            this.d = ((java.security.interfaces.RSAPrivateKey) privateKey).getModulus().bitLength();
            MessageDigest messageDigest = this.hash;
            if (messageDigest != null) {
                messageDigest.reset();
            }
        } catch (InvalidKeyException e) {
            throw e;
        } catch (Exception e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("InitSign error: ");
            stringBuffer.append(e2.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        this.appRandom = secureRandom;
        a(secureRandom);
        engineInitSign(privateKey);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        try {
            RSA rsaA = RSA.a();
            this.b = rsaA;
            rsaA.setMode("ECB");
            this.b.setPadding(this.a);
            this.b.init(2, publicKey, null);
            this.d = ((java.security.interfaces.RSAPublicKey) publicKey).getModulus().bitLength();
            MessageDigest messageDigest = this.hash;
            if (messageDigest != null) {
                messageDigest.reset();
            }
        } catch (InvalidKeyException e) {
            throw e;
        } catch (Exception e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("InitVerify error: ");
            stringBuffer.append(e2.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) throws InvalidParameterException {
        try {
            if (!(obj instanceof AlgorithmParameterSpec)) {
                throw new InvalidParameterException("value must be an instance of AlgorithmParameterSpec.");
            }
            engineSetParameter((AlgorithmParameterSpec) obj);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof PKCS1AlgorithmParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Parameters must be a PKCS1AlgorithmParameterSpec!");
        }
        a(((PKCS1AlgorithmParameterSpec) algorithmParameterSpec).getSecureRandom());
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) {
        MessageDigest messageDigest = this.hash;
        Objects.requireNonNull(messageDigest, "MessageDigest engine must not be null!");
        messageDigest.update(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        MessageDigest messageDigest = this.hash;
        Objects.requireNonNull(messageDigest, "MessageDigest engine must not be null!");
        messageDigest.update(bArr, i, i2);
    }
}

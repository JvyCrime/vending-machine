package iaik.security.rsa;

import iaik.iso.iso9796.ISO9796P2S2S3Signature;
import iaik.pkcs.pkcs1.Padding;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RSAISO9796P2S2S3Signature extends ISO9796P2S2S3Signature {
    private RSA p;
    private int q;

    public RSAISO9796P2S2S3Signature() {
        super("RSA-ISO9796-2-2-3");
    }

    protected RSAISO9796P2S2S3Signature(String str, int i, byte b) {
        super(str, i, b);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        try {
            RSA rsaA = RSA.a();
            this.p = rsaA;
            rsaA.setMode("ECB");
            this.p.setPadding(Padding.PADDING_NONE);
            this.p.init(1, privateKey, getSecureRandom());
            BigInteger modulus = ((java.security.interfaces.RSAPrivateKey) privateKey).getModulus();
            this.q = modulus.bitLength();
            reset(modulus);
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
        engineInitSign(privateKey);
        setSecureRandom(secureRandom);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        try {
            RSA rsaA = RSA.a();
            this.p = rsaA;
            rsaA.setMode("ECB");
            this.p.setPadding(Padding.PADDING_NONE);
            this.p.init(2, publicKey, null);
            BigInteger modulus = ((java.security.interfaces.RSAPublicKey) publicKey).getModulus();
            this.q = modulus.bitLength();
            reset(modulus);
        } catch (InvalidKeyException e) {
            throw e;
        } catch (Exception e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("InitVerify error: ");
            stringBuffer.append(e2.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected byte[] openSignature(byte[] bArr) throws SignatureException {
        RSA rsa = this.p;
        Objects.requireNonNull(rsa, "RSA Cipher must not be null!");
        if ((this.q + 7) / 8 != bArr.length) {
            throw new SignatureException("Signature value must be k bits long.");
        }
        try {
            return rsa.doFinal(bArr, 0, bArr.length);
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected byte[] produceSignature(byte[] bArr) throws SignatureException {
        RSA rsa = this.p;
        Objects.requireNonNull(rsa, "RSA Cipher must not be null!");
        rsa.setSecureRandom(getSecureRandom());
        try {
            return this.p.doFinal(bArr, 0, bArr.length);
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected void setSecureRandom(SecureRandom secureRandom) {
        super.setSecureRandom(secureRandom);
        RSA rsa = this.p;
        if (rsa != null) {
            rsa.setSecureRandom(secureRandom);
        }
    }
}

package iaik.security.rsa;

import iaik.pkcs.pkcs1.Padding;
import iaik.utils.CryptoUtils;
import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;

/* JADX INFO: loaded from: classes.dex */
public class RawRSASignature extends SignatureSpi {
    private RSA a;
    protected ByteArrayOutputStream dataBuffer_;

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
        try {
            this.a = RSA.a();
            this.dataBuffer_ = new ByteArrayOutputStream(64);
            this.a.setMode("ECB");
            this.a.setPadding(Padding.PADDING_PKCS1);
            this.a.init(1, privateKey, secureRandom);
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
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        try {
            this.a = RSA.a();
            this.dataBuffer_ = new ByteArrayOutputStream(64);
            this.a.setMode("ECB");
            this.a.setPadding(Padding.PADDING_PKCS1);
            this.a.init(2, publicKey, null);
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
        throw new InvalidParameterException("Method not supported!");
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        byte[] byteArray = this.dataBuffer_.toByteArray();
        try {
            return this.a.doFinal(byteArray, 0, byteArray.length);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Signing error: ");
            stringBuffer.append(e.toString());
            throw new SignatureException(stringBuffer.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) {
        this.dataBuffer_.write(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.dataBuffer_.write(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        try {
            return CryptoUtils.secureEqualsBlock(this.dataBuffer_.toByteArray(), this.a.doFinal(bArr, 0, bArr.length));
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Signature decryption error: ");
            stringBuffer.append(e.toString());
            throw new SignatureException(stringBuffer.toString());
        }
    }
}

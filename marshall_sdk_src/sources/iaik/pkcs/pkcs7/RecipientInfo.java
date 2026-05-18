package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.utils.Util;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Objects;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
public class RecipientInfo implements ASN1Type {
    int a;
    IssuerAndSerialNumber b;
    AlgorithmID c;
    PublicKey d;
    byte[] e;
    RSACipherProvider f;

    public RecipientInfo() {
        this.a = 0;
        this.f = RSACipherProvider.getDefault();
    }

    public RecipientInfo(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public RecipientInfo(IssuerAndSerialNumber issuerAndSerialNumber, AlgorithmID algorithmID, byte[] bArr) {
        this();
        this.b = issuerAndSerialNumber;
        this.c = algorithmID;
        this.e = bArr;
    }

    public RecipientInfo(X509Certificate x509Certificate, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        this();
        this.d = x509Certificate.getPublicKey();
        this.b = new IssuerAndSerialNumber(x509Certificate);
        this.c = algorithmID;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = ((BigInteger) aSN1Object.getComponentAt(0).getValue()).intValue();
        this.b = new IssuerAndSerialNumber(aSN1Object.getComponentAt(1));
        this.c = new AlgorithmID(aSN1Object.getComponentAt(2));
        this.e = (byte[]) aSN1Object.getComponentAt(3).getValue();
    }

    public SecretKey decryptKey(PrivateKey privateKey) throws PKCSException, InvalidKeyException {
        return decryptKey(privateKey, "RAW");
    }

    public SecretKey decryptKey(PrivateKey privateKey, String str) throws PKCSException, InvalidKeyException {
        try {
            return new iaik.security.cipher.SecretKey(this.f.cipher(2, privateKey, this.e), str);
        } catch (NoSuchAlgorithmException unused) {
            throw new PKCSException("No implementation for \"RSA/ECB/PKCS1Padding\"");
        } catch (NoSuchProviderException e) {
            throw new PKCSException(e.toString());
        } catch (GeneralSecurityException e2) {
            throw new PKCSException(e2.toString());
        }
    }

    public void encryptKey(SecretKey secretKey) throws PKCSException {
        if (this.c == null) {
            throw new PKCSException("Unable to encrypt symmetric key. Content-encryption algorithm is not set!");
        }
        PublicKey publicKey = this.d;
        if (publicKey == null) {
            throw new PKCSException("Unable to encrypt symmetric key. Public key is not set!");
        }
        try {
            this.e = this.f.cipher(1, publicKey, secretKey.getEncoded());
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to encrypt the symmetric key: ");
            stringBuffer.append(e.toString());
            throw new PKCSException(stringBuffer.toString());
        }
    }

    public byte[] getEncryptedKey() {
        return this.e;
    }

    public IssuerAndSerialNumber getIssuerAndSerialNumber() {
        return this.b;
    }

    public AlgorithmID getKeyEncryptionAlgorithm() {
        return this.c;
    }

    public RSACipherProvider getRSACipherProvider() {
        return this.f;
    }

    public int getVersion() {
        return this.a;
    }

    public void setRSACipherProvider(RSACipherProvider rSACipherProvider) {
        if (rSACipherProvider == null) {
            throw new IllegalArgumentException("Provider is not alloed tp be null!");
        }
        this.f = rSACipherProvider;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        Objects.requireNonNull(this.b, "issuerAndSerialNumber field of this RecipientInfo not set!");
        Objects.requireNonNull(this.c, "keyEncryptionAlgorithm field of this RecipientInfo not set!");
        Objects.requireNonNull(this.e, "encryptedKey field of this RecipientInfo not set!");
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new INTEGER(this.a));
        sequence.addComponent(this.b.toASN1Object());
        sequence.addComponent(this.c.toASN1Object());
        sequence.addComponent(new OCTET_STRING(this.e));
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(this.b);
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("keyEncryptionAlgorithm: ");
        stringBuffer3.append(this.c);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("encryptedKey: ");
        byte[] bArr = this.e;
        stringBuffer4.append(bArr == null ? "not set" : Util.toString(bArr, 0, 5));
        stringBuffer4.append("...");
        stringBuffer.append(stringBuffer4.toString());
        return stringBuffer.toString();
    }
}

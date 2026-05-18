package iaik.x509.ocsp;

import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Name;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import iaik.x509.X509Certificate;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/* JADX INFO: loaded from: classes2.dex */
public class CertID {
    byte[] a;
    byte[] b;
    AlgorithmID c;
    BigInteger d;

    public CertID(ASN1Object aSN1Object) throws CodingException {
        a(aSN1Object);
    }

    public CertID(AlgorithmID algorithmID, Name name, PublicKey publicKey, BigInteger bigInteger) throws NoSuchAlgorithmException {
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing hash algorithm!");
        }
        if (name == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing issuerName!");
        }
        if (publicKey == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing issuerKey!");
        }
        if (bigInteger == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing serialNumber!");
        }
        try {
            this.c = algorithmID;
            this.a = calculateIssuerNameHash(name, algorithmID);
            this.b = calculateIssuerKeyHash(publicKey, algorithmID);
            this.d = bigInteger;
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot create CertID. Invalid key encoding format: ");
            stringBuffer.append(e.getMessage());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public CertID(AlgorithmID algorithmID, X509Certificate x509Certificate, X509Certificate x509Certificate2) throws NoSuchAlgorithmException {
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing hash algorithm!");
        }
        if (x509Certificate == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing issuerCert!");
        }
        if (x509Certificate2 == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing targetCert!");
        }
        try {
            this.c = algorithmID;
            this.a = calculateIssuerNameHash((Name) x509Certificate.getSubjectDN(), algorithmID);
            this.b = calculateIssuerKeyHash(x509Certificate.getPublicKey(), algorithmID);
            this.d = x509Certificate2.getSerialNumber();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot create CertID. Invalid key encoding format: ");
            stringBuffer.append(e.getMessage());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public CertID(AlgorithmID algorithmID, X509Certificate x509Certificate, BigInteger bigInteger) throws NoSuchAlgorithmException {
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing hash algorithm!");
        }
        if (x509Certificate == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing issuerCert!");
        }
        if (bigInteger == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing serialNumber!");
        }
        try {
            this.c = algorithmID;
            this.a = calculateIssuerNameHash((Name) x509Certificate.getSubjectDN(), algorithmID);
            this.b = calculateIssuerKeyHash(x509Certificate.getPublicKey(), algorithmID);
            this.d = bigInteger;
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot create CertID. Invalid key encoding format: ");
            stringBuffer.append(e.getMessage());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public CertID(AlgorithmID algorithmID, byte[] bArr, byte[] bArr2, BigInteger bigInteger) {
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing hash algorithm!");
        }
        if (bArr == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing issuerNameHash!");
        }
        if (bArr2 == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing issuerKeyHash!");
        }
        if (bigInteger == null) {
            throw new IllegalArgumentException("Cannot create CertID. Missing serialNumber!");
        }
        this.c = algorithmID;
        this.a = bArr;
        this.b = bArr2;
        this.d = bigInteger;
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        AlgorithmID algorithmID = new AlgorithmID(aSN1Object.getComponentAt(0));
        this.c = algorithmID;
        if (algorithmID == null) {
            throw new CodingException("Cannot create CertID. Missing hash algorithm!");
        }
        byte[] bArr = (byte[]) aSN1Object.getComponentAt(1).getValue();
        this.a = bArr;
        if (bArr == null) {
            throw new CodingException("Cannot create CertID. Missing issuerNameHash!");
        }
        byte[] bArr2 = (byte[]) aSN1Object.getComponentAt(2).getValue();
        this.b = bArr2;
        if (bArr2 == null) {
            throw new CodingException("Cannot create CertID. Missing issuerKeyHash!");
        }
        BigInteger bigInteger = (BigInteger) aSN1Object.getComponentAt(3).getValue();
        this.d = bigInteger;
        if (bigInteger == null) {
            throw new CodingException("Cannot create CertID. Missing serialNumber!");
        }
    }

    public static byte[] calculateIssuerKeyHash(PublicKey publicKey, AlgorithmID algorithmID) throws NoSuchAlgorithmException, CodingException {
        BIT_STRING bit_string = (BIT_STRING) DerCoder.decode(publicKey.getEncoded()).getComponentAt(1);
        MessageDigest messageDigestInstance = algorithmID.getMessageDigestInstance();
        messageDigestInstance.update((byte[]) bit_string.getValue());
        return messageDigestInstance.digest();
    }

    public static byte[] calculateIssuerNameHash(Name name, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        MessageDigest messageDigestInstance = algorithmID.getMessageDigestInstance();
        messageDigestInstance.update(name.getEncoded());
        return messageDigestInstance.digest();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CertID)) {
            return false;
        }
        CertID certID = (CertID) obj;
        return this.c.equals(certID.c) && CryptoUtils.equalsBlock(this.a, certID.a) && CryptoUtils.equalsBlock(this.b, certID.b) && this.d.equals(certID.d);
    }

    public AlgorithmID getHashAlgorithm() {
        return this.c;
    }

    public byte[] getIssuerKeyHash() {
        return this.b;
    }

    public byte[] getIssuerNameHash() {
        return this.a;
    }

    public BigInteger getSerialNumber() {
        return this.d;
    }

    public int hashCode() {
        return this.c.hashCode() + Util.calculateHashCode(this.a) + Util.calculateHashCode(this.b) + this.d.hashCode();
    }

    public boolean isCertIDFor(Name name, PublicKey publicKey, BigInteger bigInteger) throws NoSuchAlgorithmException {
        return equals(new CertID((AlgorithmID) this.c.clone(), name, publicKey, bigInteger));
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.c.toASN1Object());
        sequence.addComponent(new OCTET_STRING(this.a));
        sequence.addComponent(new OCTET_STRING(this.b));
        sequence.addComponent(new INTEGER(this.d));
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("hashAlgorithm: ");
        stringBuffer.append(this.c);
        stringBuffer.append("\n");
        stringBuffer.append("issuerNameHash: ");
        stringBuffer.append(Util.toString(this.a));
        stringBuffer.append("\n");
        stringBuffer.append("issuerKeyHash: ");
        stringBuffer.append(Util.toString(this.b));
        stringBuffer.append("\n");
        stringBuffer.append("serialNumber: ");
        stringBuffer.append(this.d);
        return stringBuffer.toString();
    }
}

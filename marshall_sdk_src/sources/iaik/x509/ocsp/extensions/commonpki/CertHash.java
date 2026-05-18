package iaik.x509.ocsp.extensions.commonpki;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class CertHash extends V3Extension {
    public static final ObjectID oid = ObjectID.ocspExt_CertHash;
    private AlgorithmID a;
    private byte[] b;

    public CertHash() {
    }

    public CertHash(AlgorithmID algorithmID, Certificate certificate) throws NoSuchAlgorithmException, CertificateException {
        Objects.requireNonNull(algorithmID, "hashAlgorithm must not be null!");
        Objects.requireNonNull(certificate, "certificate must not be null!");
        this.a = algorithmID;
        this.b = a(algorithmID, certificate.getEncoded());
    }

    public CertHash(AlgorithmID algorithmID, byte[] bArr) {
        Objects.requireNonNull(algorithmID, "hashAlgorithm must not be null!");
        Objects.requireNonNull(bArr, "certificateHash must not be null!");
        this.a = algorithmID;
        this.b = bArr;
    }

    private static final byte[] a(AlgorithmID algorithmID, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest messageDigestInstance;
        try {
            messageDigestInstance = algorithmID.getMessageDigestInstance("IAIK");
        } catch (Exception unused) {
            messageDigestInstance = algorithmID.getMessageDigestInstance();
        }
        return messageDigestInstance.digest(bArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CertHash) {
            CertHash certHash = (CertHash) obj;
            try {
                if (this.a.equals(certHash.a)) {
                    if (CryptoUtils.equalsBlock(this.b, certHash.b)) {
                        return true;
                    }
                }
            } catch (NullPointerException unused) {
            }
        }
        return false;
    }

    public byte[] getCertificateHash() {
        return this.b;
    }

    public AlgorithmID getHashAlgorithm() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    public boolean identifiesCert(Certificate certificate) throws NoSuchAlgorithmException, CertificateException {
        Objects.requireNonNull(certificate, "certificate must not be null!");
        return CryptoUtils.equalsBlock(this.b, a(this.a, certificate.getEncoded()));
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        Objects.requireNonNull(aSN1Object, "Cannot parse null ASN.1 obj!");
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new X509ExtensionException("ASN.1 CertHash must be SEQUENCE!");
        }
        try {
            if (aSN1Object.countComponents() != 2) {
                throw new X509ExtensionException("ASN.1 CertHash must have two components!");
            }
            this.a = new AlgorithmID(aSN1Object.getComponentAt(0));
            ASN1Object componentAt = aSN1Object.getComponentAt(1);
            if (!componentAt.isA(ASN.OCTET_STRING)) {
                throw new X509ExtensionException("certificateHash must be OCTET STRING!");
            }
            this.b = (byte[]) componentAt.getValue();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error parsing ASN.1 CertHash object: ");
            stringBuffer.append(e.toString());
            throw new X509ExtensionException(stringBuffer.toString());
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        Objects.requireNonNull(this.a, "hashAlgorithm must not be null!");
        Objects.requireNonNull(this.b, "certificateHash must not be null!");
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a.toASN1Object());
        sequence.addComponent(new OCTET_STRING(this.b));
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("hashAlgorithm: ");
            stringBuffer2.append(this.a);
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\ncertificateHash: ");
            stringBuffer3.append(Util.toString(this.b));
            stringBuffer.append(stringBuffer3.toString());
        }
        return stringBuffer.toString();
    }
}

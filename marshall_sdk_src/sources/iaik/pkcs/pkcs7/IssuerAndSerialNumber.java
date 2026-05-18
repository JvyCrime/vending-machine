package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.Name;
import java.math.BigInteger;
import java.security.ProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes.dex */
public class IssuerAndSerialNumber {
    private Name a;
    private BigInteger b;

    public IssuerAndSerialNumber(ASN1Object aSN1Object) throws CodingException {
        this.a = null;
        this.b = null;
        this.a = new Name(aSN1Object.getComponentAt(0));
        this.b = (BigInteger) aSN1Object.getComponentAt(1).getValue();
    }

    public IssuerAndSerialNumber(Name name, BigInteger bigInteger) {
        this.a = null;
        this.b = null;
        this.a = name;
        this.b = bigInteger;
    }

    public IssuerAndSerialNumber(X509Certificate x509Certificate) {
        this.a = null;
        this.b = null;
        if (x509Certificate instanceof iaik.x509.X509Certificate) {
            this.a = (Name) x509Certificate.getIssuerDN();
        } else {
            try {
                this.a = (Name) new iaik.x509.X509Certificate(x509Certificate.getEncoded()).getIssuerDN();
            } catch (CertificateException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid encoded certificate. Could not decode issuer DN: ");
                stringBuffer.append(e);
                throw new ProviderException(stringBuffer.toString());
            }
        }
        this.b = x509Certificate.getSerialNumber();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IssuerAndSerialNumber)) {
            return false;
        }
        IssuerAndSerialNumber issuerAndSerialNumber = (IssuerAndSerialNumber) obj;
        return this.a.equals(issuerAndSerialNumber.a) && this.b.compareTo(issuerAndSerialNumber.b) == 0;
    }

    public Name getIssuer() {
        return this.a;
    }

    public BigInteger getSerialNumber() {
        return this.b;
    }

    public int hashCode() {
        return this.a.hashCode() + this.b.hashCode();
    }

    public boolean isIssuerOf(X509Certificate x509Certificate) {
        return this.a.equals(x509Certificate.getIssuerDN()) && this.b.compareTo(x509Certificate.getSerialNumber()) == 0;
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a.toASN1Object());
        sequence.addComponent(new INTEGER(this.b));
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("SerialNumber: ");
        stringBuffer2.append(this.b);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Issuer: ");
        stringBuffer3.append(this.a);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }
}

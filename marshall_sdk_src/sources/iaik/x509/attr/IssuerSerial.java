package iaik.x509.attr;

import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.GeneralNames;
import iaik.asn1.structures.Name;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import iaik.x509.X509Certificate;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class IssuerSerial {
    private GeneralNames a;
    private BigInteger b;
    private BigInteger c;

    public IssuerSerial(ASN1Object aSN1Object) throws CodingException {
        a(aSN1Object);
    }

    public IssuerSerial(GeneralNames generalNames, BigInteger bigInteger) {
        this.a = generalNames;
        this.b = bigInteger;
    }

    public IssuerSerial(Name name, BigInteger bigInteger) {
        this.a = new GeneralNames(new GeneralName(4, name));
        this.b = bigInteger;
    }

    public IssuerSerial(X509Certificate x509Certificate) {
        this.a = new GeneralNames(new GeneralName(4, x509Certificate.getIssuerDN()));
        this.b = x509Certificate.getSerialNumber();
        setIssuerUID(x509Certificate.getIssuerUniqueID());
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        this.a = new GeneralNames(aSN1Object.getComponentAt(0));
        this.b = (BigInteger) aSN1Object.getComponentAt(1).getValue();
        if (aSN1Object.countComponents() == 3) {
            this.c = new BigInteger(1, (byte[]) aSN1Object.getComponentAt(2).getValue());
        }
    }

    public boolean equals(Object obj) {
        BigInteger bigInteger;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IssuerSerial)) {
            return false;
        }
        IssuerSerial issuerSerial = (IssuerSerial) obj;
        if (!this.a.equals(issuerSerial.a) || this.b.compareTo(issuerSerial.b) != 0) {
            return false;
        }
        BigInteger bigInteger2 = this.c;
        return (bigInteger2 == null || (bigInteger = issuerSerial.c) == null) ? bigInteger2 == null && issuerSerial.c == null : bigInteger2.compareTo(bigInteger) == 0;
    }

    public GeneralNames getIssuer() {
        return this.a;
    }

    public boolean[] getIssuerUID() {
        BigInteger bigInteger = this.c;
        if (bigInteger == null) {
            return null;
        }
        return Util.toBooleanArray(bigInteger.toString(2));
    }

    public BigInteger getSerialNumber() {
        return this.b;
    }

    public int hashCode() {
        int iHashCode = this.a.hashCode() + this.b.hashCode();
        BigInteger bigInteger = this.c;
        return bigInteger != null ? iHashCode + bigInteger.hashCode() : iHashCode;
    }

    public boolean identifiesCert(X509Certificate x509Certificate) {
        return equals(new IssuerSerial(x509Certificate));
    }

    public void setIssuerUID(boolean[] zArr) {
        if (zArr != null) {
            this.c = new BigInteger(Util.fromBooleanArray(zArr), 2);
        }
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        try {
            sequence.addComponent(this.a.toASN1Object());
            sequence.addComponent(new INTEGER(this.b));
            if (this.c != null) {
                sequence.addComponent(new BIT_STRING(this.c.toByteArray()));
            }
            return sequence;
        } catch (CodingException e) {
            throw new InternalErrorException(e);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Issuer: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("SerialNumber: ");
        stringBuffer3.append(this.b);
        stringBuffer.append(stringBuffer3.toString());
        if (this.c != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("\nIssuerUID: ");
            stringBuffer4.append(this.c);
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }
}

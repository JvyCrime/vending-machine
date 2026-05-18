package iaik.x509.ocsp;

import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Name;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import iaik.x509.X509Certificate;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/* JADX INFO: loaded from: classes2.dex */
public class ResponderID {
    Name a;
    byte[] b;
    boolean c;

    public ResponderID(ASN1Object aSN1Object) throws CodingException {
        a(aSN1Object);
    }

    public ResponderID(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create ResponderID from a null object");
        }
        this.a = name;
        this.c = true;
    }

    public ResponderID(PublicKey publicKey) throws NoSuchAlgorithmException {
        try {
            this.b = CertID.calculateIssuerKeyHash(publicKey, AlgorithmID.sha1);
            this.c = false;
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot create ResponderID. Invalid key encoding format: ");
            stringBuffer.append(e.getMessage());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public ResponderID(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Cannot create ResponderID from a null object");
        }
        this.b = bArr;
        this.c = false;
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        int tag = aSN1Object.getAsnType().getTag();
        if (tag == 1) {
            this.a = new Name((ASN1Object) aSN1Object.getValue());
            this.c = true;
        } else if (tag == 2) {
            this.b = (byte[]) ((ASN1Object) aSN1Object.getValue()).getValue();
            this.c = false;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 tag in ResponderID choice: ");
            stringBuffer.append(tag);
            throw new CodingException(stringBuffer.toString());
        }
    }

    public boolean byName() {
        return this.c;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResponderID)) {
            return false;
        }
        ResponderID responderID = (ResponderID) obj;
        boolean z = this.c;
        if (z != responderID.c) {
            return false;
        }
        return z ? this.a.equals(responderID.a) : CryptoUtils.equalsBlock(this.b, responderID.b);
    }

    public byte[] getKeyHash() {
        return this.b;
    }

    public Name getName() {
        return this.a;
    }

    public int hashCode() {
        return this.c ? this.a.hashCode() : Util.calculateHashCode(this.b);
    }

    public boolean isResponderIdFor(X509Certificate x509Certificate) throws NoSuchAlgorithmException {
        if (this.c) {
            return this.a.equals(x509Certificate.getSubjectDN());
        }
        try {
            return CryptoUtils.equalsBlock(this.b, CertID.calculateIssuerKeyHash(x509Certificate.getPublicKey(), AlgorithmID.sha1));
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot calculate keyHash. Invalid key encoding: ");
            stringBuffer.append(e.getMessage());
            throw new InternalErrorException(stringBuffer.toString());
        }
    }

    public ASN1Object toASN1Object() {
        return this.c ? new CON_SPEC(1, this.a.toASN1Object()) : new CON_SPEC(2, new OCTET_STRING(this.b));
    }

    public String toString() {
        StringBuffer stringBuffer;
        if (this.c) {
            stringBuffer = new StringBuffer();
            stringBuffer.append("byName: ");
            stringBuffer.append(this.a);
        } else {
            stringBuffer = new StringBuffer();
            stringBuffer.append("byKey: ");
            stringBuffer.append(Util.toString(this.b));
        }
        return stringBuffer.toString();
    }
}

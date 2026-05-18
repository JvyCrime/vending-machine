package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.GeneralNames;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class AuthorityKeyIdentifier extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_AuthorityKeyIdentifier;
    private byte[] a;
    private GeneralNames b;
    private BigInteger c;

    public AuthorityKeyIdentifier() {
        this.a = null;
    }

    public AuthorityKeyIdentifier(GeneralNames generalNames, BigInteger bigInteger) {
        this.a = null;
        this.b = generalNames;
        this.c = bigInteger;
    }

    public AuthorityKeyIdentifier(byte[] bArr) {
        this.a = null;
        this.a = bArr;
    }

    public GeneralNames getAuthorityCertIssuer() {
        return this.b;
    }

    public BigInteger getAuthorityCertSerialNumber() {
        return this.c;
    }

    public byte[] getKeyIdentifier() {
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

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            try {
                CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
                int tag = con_spec.getAsnType().getTag();
                if (tag == 0) {
                    if (con_spec.isImplicitlyTagged()) {
                        con_spec.forceImplicitlyTagged(ASN.OCTET_STRING);
                    }
                    ASN1Object aSN1Object2 = (ASN1Object) con_spec.getValue();
                    if (!(aSN1Object2 instanceof OCTET_STRING)) {
                        throw new CodingException("Cannot parse keyIdentifier. Expected an OCTET_STRING!");
                    }
                    this.a = (byte[]) ((OCTET_STRING) aSN1Object2).getValue();
                } else if (tag == 1) {
                    Object value = con_spec.getValue();
                    if (!(value instanceof ASN1Object) || !((ASN1Object) value).getAsnType().equals(ASN.SEQUENCE)) {
                        con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                    }
                    this.b = new GeneralNames((ASN1Object) con_spec.getValue());
                } else if (tag == 2) {
                    if (con_spec.isImplicitlyTagged()) {
                        con_spec.forceImplicitlyTagged(ASN.INTEGER);
                    }
                    ASN1Object aSN1Object3 = (ASN1Object) con_spec.getValue();
                    if (!(aSN1Object3 instanceof INTEGER)) {
                        throw new CodingException("Cannot parse authorityCertSerialNumber. Expected an INTEGER!");
                    }
                    this.c = (BigInteger) ((INTEGER) aSN1Object3).getValue();
                }
            } catch (Exception e) {
                throw new X509ExtensionException(e.getMessage());
            }
        }
    }

    public void setAuthorityCertIssuer(GeneralNames generalNames) {
        this.b = generalNames;
    }

    public void setAuthorityCertSerialNumber(BigInteger bigInteger) {
        this.c = bigInteger;
    }

    public void setKeyIdentifier(byte[] bArr) {
        this.a = bArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        SEQUENCE sequence = new SEQUENCE();
        if (this.a != null) {
            sequence.addComponent(new CON_SPEC(0, new OCTET_STRING(this.a), true));
        }
        if (this.b != null) {
            try {
                sequence.addComponent(new CON_SPEC(1, this.b.toASN1Object(), true));
            } catch (CodingException e) {
                throw new X509ExtensionException(e.getMessage());
            }
        }
        if (this.c != null) {
            sequence.addComponent(new CON_SPEC(2, new INTEGER(this.c), true));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("KeyIdentifier: ");
            stringBuffer2.append(Util.toString(this.a));
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("AuthorityCertIssuer: ");
            stringBuffer3.append(this.b);
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        if (this.c != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("AuthorityCertSerialNumber: ");
            stringBuffer4.append(this.c.toString(16));
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        if (stringBuffer.length() >= 1) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}

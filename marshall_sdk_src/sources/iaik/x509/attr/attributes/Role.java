package iaik.x509.attr.attributes;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AttributeValue;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.GeneralNames;
import iaik.utils.Util;
import iaik.x509.attr.AttCertIssuer;
import iaik.x509.attr.AttributeCertificate;
import iaik.x509.attr.IssuerSerial;
import iaik.x509.attr.V1Form;
import iaik.x509.attr.V2Form;
import java.util.Enumeration;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class Role extends AttributeValue {
    public static final ObjectID oid = ObjectID.role;
    private GeneralNames a;
    private GeneralName b;

    public Role() {
    }

    public Role(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public Role(GeneralName generalName) {
        Objects.requireNonNull(generalName, "roleName must not be null!");
        this.b = generalName;
    }

    private static boolean a(GeneralNames generalNames, GeneralNames generalNames2) {
        Enumeration names = generalNames.getNames();
        boolean z = false;
        while (names.hasMoreElements()) {
            if (generalNames2.contains((GeneralName) names.nextElement())) {
                z = true;
            }
        }
        return z;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        Objects.requireNonNull(aSN1Object, "Cannot parse null ASN1Object.");
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 type ");
            stringBuffer.append(aSN1Object.getAsnType());
            stringBuffer.append(". Expected SEQUENCE.");
            throw new CodingException(stringBuffer.toString());
        }
        int iCountComponents = aSN1Object.countComponents();
        if (iCountComponents < 1) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid number of components: ");
            stringBuffer2.append(iCountComponents);
            stringBuffer2.append(". roleName must be present.");
            throw new CodingException(stringBuffer2.toString());
        }
        if (iCountComponents > 2) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Invalid number of components: ");
            stringBuffer3.append(iCountComponents);
            stringBuffer3.append(". Role can only hold up to 2 components.");
            throw new CodingException(stringBuffer3.toString());
        }
        for (int i = 0; i < iCountComponents; i++) {
            ASN1Object componentAt = aSN1Object.getComponentAt(i);
            if (!componentAt.isA(ASN.CON_SPEC)) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Invalid ASN.1 type ");
                stringBuffer4.append(aSN1Object.getAsnType());
                stringBuffer4.append(". Expected CON SPEC.");
                throw new CodingException(stringBuffer4.toString());
            }
            CON_SPEC con_spec = (CON_SPEC) componentAt;
            int tag = con_spec.getAsnType().getTag();
            if (tag == 0) {
                con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                this.a = new GeneralNames((ASN1Object) con_spec.getValue());
            } else {
                if (tag != 1) {
                    StringBuffer stringBuffer5 = new StringBuffer();
                    stringBuffer5.append("Invalid tag ");
                    stringBuffer5.append(tag);
                    stringBuffer5.append(". Expected 0 or 1!");
                    throw new CodingException(stringBuffer5.toString());
                }
                this.b = new GeneralName((ASN1Object) con_spec.getValue());
            }
        }
    }

    @Override // iaik.asn1.structures.AttributeValue
    public ObjectID getAttributeType() {
        return oid;
    }

    public GeneralNames getRoleAuthority() {
        return this.a;
    }

    public GeneralName getRoleName() {
        return this.b;
    }

    public boolean roleSpecifiedBy(AttributeCertificate attributeCertificate) {
        IssuerSerial baseCertificateID;
        GeneralNames issuer;
        Objects.requireNonNull(attributeCertificate, "roleSpecificationCertificate must not be null!");
        boolean z = false;
        GeneralNames entityName = attributeCertificate.getHolder().getEntityName();
        if (entityName == null || !entityName.contains(this.b)) {
            return false;
        }
        if (this.a != null) {
            AttCertIssuer issuer2 = attributeCertificate.getIssuer();
            if (issuer2 == null) {
                return false;
            }
            if (issuer2.getVForm() == 1) {
                GeneralNames generalNames = ((V1Form) issuer2).getGeneralNames();
                if (generalNames == null || !a(this.a, generalNames)) {
                    return false;
                }
            } else {
                V2Form v2Form = (V2Form) issuer2;
                GeneralNames issuerName = v2Form.getIssuerName();
                if (issuerName != null && a(this.a, issuerName)) {
                    z = true;
                }
                if (z || (baseCertificateID = v2Form.getBaseCertificateID()) == null || (issuer = baseCertificateID.getIssuer()) == null || !a(this.a, issuer)) {
                    return z;
                }
            }
        }
        return true;
    }

    public GeneralNames setRoleAuthority(AttributeCertificate attributeCertificate) {
        IssuerSerial baseCertificateID;
        GeneralNames issuer;
        AttCertIssuer issuer2 = attributeCertificate.getIssuer();
        if (issuer2 != null) {
            if (issuer2.getVForm() == 1) {
                issuer = ((V1Form) issuer2).getGeneralNames();
            } else {
                V2Form v2Form = (V2Form) issuer2;
                GeneralNames issuerName = v2Form.getIssuerName();
                this.a = issuerName;
                if (issuerName == null && (baseCertificateID = v2Form.getBaseCertificateID()) != null) {
                    issuer = baseCertificateID.getIssuer();
                }
            }
            this.a = issuer;
        }
        return this.a;
    }

    public void setRoleAuthority(GeneralNames generalNames) {
        this.a = generalNames;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        Objects.requireNonNull(this.b, "roleName must not be null!");
        SEQUENCE sequence = new SEQUENCE();
        if (this.a != null) {
            sequence.addComponent(new CON_SPEC(0, this.a.toASN1Object(), true));
        }
        sequence.addComponent(new CON_SPEC(1, this.b.toASN1Object(), false));
        return sequence;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public String toString() {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            stringBuffer.append("roleAuthority: {\n");
            Util.printIndented(this.a.toString(), true, "    ", stringBuffer);
            stringBuffer.append("\n}\n");
        }
        if (this.b != null) {
            stringBuffer.append("roleName: {\n");
            Util.printIndented(this.b.toString(), true, "    ", stringBuffer);
            str = "\n}";
        } else {
            str = "";
        }
        stringBuffer.append(str);
        return stringBuffer.toString();
    }
}

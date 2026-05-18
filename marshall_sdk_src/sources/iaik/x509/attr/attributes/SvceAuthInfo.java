package iaik.x509.attr.attributes;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AttributeValue;
import iaik.asn1.structures.GeneralName;
import iaik.utils.Util;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SvceAuthInfo extends AttributeValue {
    OCTET_STRING a;
    private GeneralName b;
    private GeneralName c;

    protected SvceAuthInfo() {
    }

    protected SvceAuthInfo(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    protected SvceAuthInfo(GeneralName generalName, GeneralName generalName2) {
        Objects.requireNonNull(generalName, "service must not be null!");
        Objects.requireNonNull(generalName2, "ident must not be null!");
        this.b = generalName;
        this.c = generalName2;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        Objects.requireNonNull(aSN1Object, "Cannot parse null ASN1Object!");
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 type: ");
            stringBuffer.append(aSN1Object.getAsnType().getName());
            stringBuffer.append(". Expected SEQUENCE!");
            throw new CodingException(stringBuffer.toString());
        }
        int iCountComponents = aSN1Object.countComponents();
        if (iCountComponents < 2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid number of components: ");
            stringBuffer2.append(iCountComponents);
            stringBuffer2.append(". Must contain service and ident.");
            throw new CodingException(stringBuffer2.toString());
        }
        if (iCountComponents > 3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Invalid number of components: ");
            stringBuffer3.append(iCountComponents);
            stringBuffer3.append(". Only up to 3 allowed.");
            throw new CodingException(stringBuffer3.toString());
        }
        OCTET_STRING octet_string = null;
        ASN1Object componentAt = aSN1Object.getComponentAt(0);
        if (!componentAt.isA(ASN.CON_SPEC)) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("First component has invalid ASN.1 type: ");
            stringBuffer4.append(componentAt.getAsnType().getName());
            stringBuffer4.append(". Expected CON_SPEC!");
            throw new CodingException(stringBuffer4.toString());
        }
        GeneralName generalName = new GeneralName(componentAt);
        ASN1Object componentAt2 = aSN1Object.getComponentAt(1);
        if (!componentAt2.isA(ASN.CON_SPEC)) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Second component has invalid ASN.1 type: ");
            stringBuffer5.append(componentAt2.getAsnType().getName());
            stringBuffer5.append(". Expected CON_SPEC!");
            throw new CodingException(stringBuffer5.toString());
        }
        GeneralName generalName2 = new GeneralName(componentAt2);
        if (iCountComponents == 3) {
            ASN1Object componentAt3 = aSN1Object.getComponentAt(2);
            if (!componentAt3.isA(ASN.OCTET_STRING)) {
                StringBuffer stringBuffer6 = new StringBuffer();
                stringBuffer6.append("Third component has invalid ASN.1 type: ");
                stringBuffer6.append(componentAt3.getAsnType().getName());
                stringBuffer6.append(". Expected OCTET STRING!");
                throw new CodingException(stringBuffer6.toString());
            }
            octet_string = (OCTET_STRING) componentAt3;
        }
        this.b = generalName;
        this.c = generalName2;
        this.a = octet_string;
    }

    public GeneralName getIdent() {
        return this.c;
    }

    public GeneralName getService() {
        return this.b;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        GeneralName generalName = this.b;
        if (generalName == null) {
            throw new CodingException("service field must not be null!");
        }
        sequence.addComponent(generalName.toASN1Object());
        GeneralName generalName2 = this.c;
        if (generalName2 == null) {
            throw new CodingException("ident field must not be null!");
        }
        sequence.addComponent(generalName2.toASN1Object());
        OCTET_STRING octet_string = this.a;
        if (octet_string != null) {
            sequence.addComponent(octet_string);
        }
        return sequence;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.b != null) {
            stringBuffer.append("service: {\n");
            Util.printIndented(this.b.toString(), true, "    ", stringBuffer);
            stringBuffer.append("\n}");
        }
        if (this.c != null) {
            stringBuffer.append("\nident: {\n");
            Util.printIndented(this.c.toString(), true, "    ", stringBuffer);
            stringBuffer.append("\n}");
        }
        if (this.a != null) {
            stringBuffer.append("\nauthInfo: {\n");
            Util.printIndented(Util.toString((byte[]) this.a.getValue()), true, "    ", stringBuffer);
            stringBuffer.append("\n}");
        }
        return stringBuffer.toString();
    }
}

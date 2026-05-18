package iaik.x509.attr.attributes;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.UTF8String;
import iaik.asn1.structures.AttributeValue;
import iaik.asn1.structures.GeneralNames;
import iaik.utils.Util;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public abstract class IetfAttrSyntax extends AttributeValue {
    private GeneralNames a;
    private ASN b;
    private boolean c;
    private Vector d;

    protected IetfAttrSyntax(ASN1Object aSN1Object) throws CodingException {
        this(true);
        decode(aSN1Object);
    }

    protected IetfAttrSyntax(boolean z) {
        this.c = z;
        this.d = new Vector();
    }

    protected IetfAttrSyntax(ObjectID[] objectIDArr, boolean z) throws IllegalArgumentException {
        this(z);
        Objects.requireNonNull(objectIDArr, "values must not be null");
        if (!this.c && objectIDArr.length > 1) {
            throw new IllegalArgumentException("Only one value allowed!");
        }
        for (int i = 0; i < objectIDArr.length; i++) {
            if (objectIDArr[i] != null) {
                this.d.addElement(objectIDArr[i]);
            }
        }
        if (this.d.size() > 0) {
            this.b = ASN.ObjectID;
        }
    }

    protected IetfAttrSyntax(String[] strArr, boolean z) throws IllegalArgumentException {
        this(z);
        Objects.requireNonNull(strArr, "values must not be null");
        if (!this.c && strArr.length > 1) {
            throw new IllegalArgumentException("Only one value allowed!");
        }
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i] != null) {
                this.d.addElement(new UTF8String(strArr[i]));
            }
        }
        if (this.d.size() > 0) {
            this.b = ASN.UTF8String;
        }
    }

    protected IetfAttrSyntax(byte[][] bArr, boolean z) throws IllegalArgumentException {
        this(z);
        Objects.requireNonNull(bArr, "values must not be null");
        if (!this.c && bArr.length > 1) {
            throw new IllegalArgumentException("Only one value allowed!");
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != null) {
                this.d.addElement(new OCTET_STRING(bArr[i]));
            }
        }
        if (this.d.size() > 0) {
            this.b = ASN.OCTET_STRING;
        }
    }

    private void a(ASN1Object aSN1Object) throws IllegalArgumentException {
        ASN asnType = aSN1Object.getAsnType();
        ASN asn = this.b;
        if (asn != null) {
            if (asnType.equals(asn)) {
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN1 type: ");
            stringBuffer.append(asnType.getName());
            stringBuffer.append("Expected ");
            stringBuffer.append(this.b.getName());
            stringBuffer.append("!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (asnType.equals(ASN.OCTET_STRING) || asnType.equals(ASN.ObjectID) || asnType.equals(ASN.UTF8String)) {
            this.b = asnType;
            return;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Invalid ASN1 type: ");
        stringBuffer2.append(asnType.getName());
        stringBuffer2.append("Expected OCTET_STRING, ObjectID or UTF8String!");
        throw new IllegalArgumentException(stringBuffer2.toString());
    }

    public boolean containsValue(Object obj) {
        Object uTF8String;
        ASN asn = this.b;
        if (asn != null) {
            if (asn == ASN.OCTET_STRING) {
                if (obj instanceof byte[]) {
                    uTF8String = new OCTET_STRING((byte[]) obj);
                    obj = uTF8String;
                }
                obj = null;
            } else {
                if (this.b == ASN.ObjectID) {
                    if (!(obj instanceof ObjectID)) {
                    }
                } else if (this.b == ASN.UTF8String && (obj instanceof String)) {
                    uTF8String = new UTF8String((String) obj);
                    obj = uTF8String;
                }
                obj = null;
            }
            if (obj != null) {
                return this.d.contains(obj);
            }
        }
        return false;
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
        if (iCountComponents == 0) {
            throw new CodingException("Invalid IetfAttrSyntax. Must not be empty!");
        }
        if (iCountComponents > 2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid number of components: ");
            stringBuffer2.append(iCountComponents);
            stringBuffer2.append(". Only up to 2 allowed.");
            throw new CodingException(stringBuffer2.toString());
        }
        for (int i = 0; i < iCountComponents; i++) {
            ASN1Object componentAt = aSN1Object.getComponentAt(i);
            if (componentAt.isA(ASN.CON_SPEC)) {
                CON_SPEC con_spec = (CON_SPEC) componentAt;
                con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                this.a = new GeneralNames((ASN1Object) con_spec.getValue());
            } else {
                if (!componentAt.isA(ASN.SEQUENCE)) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("Invalid ASN.1 type: ");
                    stringBuffer3.append(componentAt.getAsnType().getName());
                    stringBuffer3.append(". Expected CON_SPEC or SEQUENCE!");
                    throw new CodingException(stringBuffer3.toString());
                }
                for (int i2 = 0; i2 < componentAt.countComponents(); i2++) {
                    ASN1Object componentAt2 = componentAt.getComponentAt(i2);
                    a(componentAt2);
                    this.d.addElement(componentAt2);
                }
            }
        }
    }

    public ASN getASN1TypeOfValues() {
        return this.b;
    }

    public GeneralNames getPolicyAuthority() {
        return this.a;
    }

    public Enumeration getValues() {
        Enumeration enumerationElements = this.d.elements();
        if (this.d.size() == 0) {
            return enumerationElements;
        }
        Vector vector = new Vector();
        while (enumerationElements.hasMoreElements()) {
            Object objNextElement = enumerationElements.nextElement();
            if (!(objNextElement instanceof ObjectID)) {
                objNextElement = ((ASN1Object) objNextElement).getValue();
            }
            vector.addElement(objNextElement);
        }
        return vector.elements();
    }

    public int numberOfValues() {
        return this.d.size();
    }

    public void setPolicyAuthority(GeneralNames generalNames) {
        this.a = generalNames;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        if (this.a != null) {
            sequence.addComponent(new CON_SPEC(0, this.a.toASN1Object(), true));
        }
        SEQUENCE sequence2 = new SEQUENCE();
        Enumeration enumerationElements = this.d.elements();
        while (enumerationElements.hasMoreElements()) {
            sequence2.addComponent((ASN1Object) enumerationElements.nextElement());
        }
        sequence.addComponent(sequence2);
        return sequence;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            stringBuffer.append("policyAuthority: {\n");
            Util.printIndented(this.a.toString(), true, "    ", stringBuffer);
            stringBuffer.append("\n}\n");
        }
        if (this.d != null) {
            stringBuffer.append("values: {\n");
            Enumeration enumerationElements = this.d.elements();
            int i = 0;
            while (enumerationElements.hasMoreElements()) {
                i++;
                if (i > 1) {
                    stringBuffer.append("\n");
                }
                Util.printIndented(enumerationElements.nextElement().toString(), true, stringBuffer);
            }
        }
        stringBuffer.append("\n}");
        return stringBuffer.toString();
    }
}

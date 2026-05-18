package iaik.x509.attr.attributes;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.SET;
import iaik.asn1.structures.AttributeValue;
import iaik.utils.Util;
import iaik.x509.attr.SecurityCategory;
import iaik.x509.attr.UnknownSecurityCategory;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class Clearance extends AttributeValue {
    public static final int CONFIDENTIAL = 8;
    public static final int RESTRICTED = 4;
    public static final int SECRET = 16;
    public static final int TOP_SECRET = 32;
    public static final int UNCLASSIFIED = 2;
    public static final int UNMARKED = 1;
    private static Hashtable a;
    public static final ObjectID oid = ObjectID.clearance;
    private ObjectID b;
    private int c;
    private SecurityCategory[] d;

    static {
        Hashtable hashtable = new Hashtable(10);
        a = hashtable;
        hashtable.put(new Integer(1), "unmarked");
        a.put(new Integer(2), "unclassified");
        a.put(new Integer(4), "restricted");
        a.put(new Integer(8), "confidential");
        a.put(new Integer(16), "secret");
        a.put(new Integer(32), "topSecret");
    }

    public Clearance() {
        this.c = 2;
    }

    public Clearance(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public Clearance(ObjectID objectID) {
        this();
        Objects.requireNonNull(objectID, "policy id must not be null!");
        this.b = objectID;
    }

    private static int a(BIT_STRING bit_string) {
        return Integer.parseInt(new StringBuffer(bit_string.getBinaryString()).reverse().toString(), 2);
    }

    private static SecurityCategory[] a(SET set) throws CodingException {
        SecurityCategory unknownSecurityCategory;
        int iCountComponents = set.countComponents();
        SecurityCategory[] securityCategoryArr = new SecurityCategory[iCountComponents];
        for (int i = 0; i < iCountComponents; i++) {
            ASN1Object componentAt = set.getComponentAt(i);
            CON_SPEC con_spec = (CON_SPEC) componentAt.getComponentAt(0);
            con_spec.forceImplicitlyTagged(ASN.ObjectID);
            ObjectID objectID = (ObjectID) con_spec.getValue();
            try {
                unknownSecurityCategory = SecurityCategory.create(objectID);
            } catch (InstantiationException unused) {
                unknownSecurityCategory = new UnknownSecurityCategory(objectID);
            }
            unknownSecurityCategory.decode(componentAt.getComponentAt(1));
            securityCategoryArr[i] = unknownSecurityCategory;
        }
        return securityCategoryArr;
    }

    public static String getSecurityClassificationName(int i) {
        Hashtable hashtable = a;
        if (hashtable == null) {
            return null;
        }
        Object obj = hashtable.get(new Integer(i));
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    public static void setSecurityClassificationName(int i, String str) {
        if (i < 64) {
            throw new IllegalArgumentException("Bits 0 through 5 are reserved for basic security classification hierachy.");
        }
        String binaryString = Integer.toBinaryString(i);
        int i2 = 0;
        for (int i3 = 0; i3 < binaryString.length(); i3++) {
            if (binaryString.charAt(i3) == '1' && (i2 = i2 + 1) > 1) {
                StringBuffer stringBuffer = new StringBuffer(binaryString);
                stringBuffer.reverse();
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Invalid security classification value: ");
                stringBuffer2.append(stringBuffer.toString());
                stringBuffer2.append(". Only one bit must be set!");
                throw new IllegalArgumentException(stringBuffer2.toString());
            }
        }
        a.put(new Integer(i), str);
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        Object value;
        Objects.requireNonNull(aSN1Object, "Cannot decode null!");
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 type: ");
            stringBuffer.append(aSN1Object.getAsnType().getName());
            stringBuffer.append(". Expected SEQUENCE!");
            throw new CodingException(stringBuffer.toString());
        }
        int iCountComponents = aSN1Object.countComponents();
        if (iCountComponents < 1 || iCountComponents > 3) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid number of components: ");
            stringBuffer2.append(iCountComponents);
            throw new CodingException(stringBuffer2.toString());
        }
        SecurityCategory[] securityCategoryArrA = null;
        ASN1Object componentAt = aSN1Object.getComponentAt(0);
        if (componentAt.isA(ASN.CON_SPEC)) {
            CON_SPEC con_spec = (CON_SPEC) componentAt;
            int tag = con_spec.getAsnType().getTag();
            if (tag != 0) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid tag number (");
                stringBuffer3.append(tag);
                stringBuffer3.append(") of first (policyID) component. Expected 0!");
                throw new CodingException(stringBuffer3.toString());
            }
            con_spec.forceImplicitlyTagged(ASN.ObjectID);
            value = con_spec.getValue();
        } else {
            boolean zIsA = componentAt.isA(ASN.ObjectID);
            value = componentAt;
            if (!zIsA) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Invalid ASN.1 type (");
                stringBuffer4.append(aSN1Object.getAsnType().getName());
                stringBuffer4.append(") of first (policyID) component. Expected OBJECT IDENTIFIER or CON_SPEC!");
                throw new CodingException(stringBuffer4.toString());
            }
        }
        ObjectID objectID = (ObjectID) value;
        int i = 2;
        if (iCountComponents > 1) {
            int iA = 2;
            for (int i2 = 1; i2 < iCountComponents; i2++) {
                ASN1Object componentAt2 = aSN1Object.getComponentAt(i2);
                if (componentAt2.isA(ASN.CON_SPEC)) {
                    CON_SPEC con_spec2 = (CON_SPEC) componentAt2;
                    int tag2 = con_spec2.getAsnType().getTag();
                    if (tag2 == 1) {
                        con_spec2.forceImplicitlyTagged(ASN.BIT_STRING);
                        iA = a((BIT_STRING) con_spec2.getValue());
                    } else {
                        if (tag2 != 2) {
                            StringBuffer stringBuffer5 = new StringBuffer();
                            stringBuffer5.append("Invalid component tag: ");
                            stringBuffer5.append(tag2);
                            throw new CodingException(stringBuffer5.toString());
                        }
                        con_spec2.forceImplicitlyTagged(ASN.SET);
                        securityCategoryArrA = a((SET) con_spec2.getValue());
                    }
                } else if (componentAt2.isA(ASN.BIT_STRING)) {
                    iA = a((BIT_STRING) componentAt2);
                } else {
                    if (!componentAt2.isA(ASN.SET)) {
                        StringBuffer stringBuffer6 = new StringBuffer();
                        stringBuffer6.append("Invalid ASN.1 type (");
                        stringBuffer6.append(aSN1Object.getAsnType().getName());
                        stringBuffer6.append(") of component ");
                        stringBuffer6.append(i2 + 1);
                        throw new CodingException(stringBuffer6.toString());
                    }
                    securityCategoryArrA = a((SET) componentAt2);
                }
            }
            i = iA;
        }
        this.b = objectID;
        this.c = i;
        this.d = securityCategoryArrA;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public ObjectID getAttributeType() {
        return oid;
    }

    public int getClassList() {
        return this.c;
    }

    public String getClassListAsBinaryString() {
        StringBuffer stringBuffer = new StringBuffer(Integer.toBinaryString(this.c));
        stringBuffer.reverse();
        int length = stringBuffer.length();
        if (length < 6) {
            while (length < 6) {
                stringBuffer.append('0');
                length++;
            }
        }
        return stringBuffer.toString();
    }

    public boolean[] getClassListAsBooleanArray() {
        int length = new StringBuffer(Integer.toBinaryString(this.c)).length();
        if (length < 6) {
            length = 6;
        }
        boolean[] zArr = new boolean[length];
        int i = 0;
        for (int i2 = this.c; i2 != 0; i2 >>= 1) {
            if ((i2 & 1) != 0) {
                zArr[i] = true;
            }
            i++;
        }
        return zArr;
    }

    public String getClassListBitNames() {
        StringBuffer stringBuffer = new StringBuffer();
        Hashtable hashtable = a;
        if (hashtable != null) {
            Enumeration enumerationKeys = hashtable.keys();
            int i = 0;
            while (enumerationKeys.hasMoreElements()) {
                Integer num = (Integer) enumerationKeys.nextElement();
                if ((num.intValue() & this.c) != 0) {
                    int i2 = i + 1;
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    stringBuffer.append(a.get(num));
                    i = i2;
                }
            }
        }
        return stringBuffer.toString();
    }

    public ObjectID getPolicyId() {
        return this.b;
    }

    public SecurityCategory[] getSecurityCategories() {
        SecurityCategory[] securityCategoryArr = this.d;
        return securityCategoryArr == null ? new SecurityCategory[0] : securityCategoryArr;
    }

    public boolean isClassListBitSet(int i) {
        String classListAsBinaryString = getClassListAsBinaryString();
        return i < classListAsBinaryString.length() && classListAsBinaryString.charAt(i) == '1';
    }

    public boolean isSecurityClassificationValueSet(int i) {
        return (i & this.c) != 0;
    }

    public void setClassList(int i) throws IllegalArgumentException {
        if (i >= 1) {
            this.c = i;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid classList value: ");
        stringBuffer.append(i);
        stringBuffer.append(". Must be > 0");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public void setSecurityCategories(SecurityCategory[] securityCategoryArr) {
        if (securityCategoryArr != null) {
            this.d = securityCategoryArr;
        } else {
            this.d = null;
        }
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        return toASN1Object(false);
    }

    public ASN1Object toASN1Object(boolean z) throws CodingException {
        Objects.requireNonNull(this.b, "Cannot encode Clearance. Missing policy id!");
        SEQUENCE sequence = new SEQUENCE();
        ASN1Object con_spec = this.b;
        if (z) {
            con_spec = new CON_SPEC(0, this.b, true);
        }
        sequence.addComponent(con_spec);
        int i = this.c;
        if (i != 2) {
            StringBuffer stringBuffer = new StringBuffer(Integer.toBinaryString(i));
            stringBuffer.reverse();
            ASN1Object bit_string = new BIT_STRING(stringBuffer.toString());
            if (z) {
                bit_string = new CON_SPEC(1, bit_string, true);
            }
            sequence.addComponent(bit_string);
        }
        SecurityCategory[] securityCategoryArr = this.d;
        if (securityCategoryArr != null && securityCategoryArr.length > 0) {
            ASN1Object set = new SET(true);
            for (int i2 = 0; i2 < this.d.length; i2++) {
                SEQUENCE sequence2 = new SEQUENCE();
                sequence2.addComponent(new CON_SPEC(0, this.d[i2].getType(), true));
                sequence2.addComponent(this.d[i2].toASN1Object());
                set.addComponent(sequence2);
            }
            if (z) {
                set = new CON_SPEC(2, set, true);
            }
            sequence.addComponent(set);
        }
        return sequence;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        String name;
        Object obj;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.b != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("policy-id: ");
            stringBuffer2.append(this.b.getName());
            stringBuffer.append(stringBuffer2.toString());
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("\nclass-list: ");
        stringBuffer3.append(getClassListAsBinaryString());
        stringBuffer.append(stringBuffer3.toString());
        Hashtable hashtable = a;
        if (hashtable != null && (obj = hashtable.get(new Integer(this.c))) != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append(" (");
            stringBuffer4.append(obj);
            stringBuffer4.append(")");
            stringBuffer.append(stringBuffer4.toString());
        }
        SecurityCategory[] securityCategoryArr = this.d;
        if (securityCategoryArr != null && securityCategoryArr.length > 0) {
            int length = securityCategoryArr.length;
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("\nThis Clearance contains ");
            stringBuffer5.append(length);
            stringBuffer5.append(" security categor");
            stringBuffer5.append(length == 1 ? "y:" : "ies:");
            stringBuffer.append(stringBuffer5.toString());
            for (int i = 0; i < length; i++) {
                if (z) {
                    StringBuffer stringBuffer6 = new StringBuffer();
                    stringBuffer6.append("\nSecurityCategory No ");
                    stringBuffer6.append(i + 1);
                    stringBuffer6.append(" (type: ");
                    stringBuffer6.append(this.d[i].getType().getName());
                    stringBuffer6.append("): {\n");
                    stringBuffer.append(stringBuffer6.toString());
                    Util.printIndented(this.d[i].toString(), true, "  ", stringBuffer);
                    name = "\n}";
                } else {
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    name = this.d[i].getType().getName();
                }
                stringBuffer.append(name);
            }
        }
        return stringBuffer.toString();
    }
}

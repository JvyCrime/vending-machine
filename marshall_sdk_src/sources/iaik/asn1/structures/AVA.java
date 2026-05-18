package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1String;
import iaik.asn1.ASN1Type;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.PrintableString;
import iaik.asn1.SEQUENCE;
import iaik.asn1.UTF8String;
import iaik.utils.CryptoUtils;
import iaik.utils.RFC2253NameParserException;
import iaik.utils.Util;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class AVA implements ASN1Type {
    static HashMap a = new HashMap();
    static ASN b = ASN.PrintableString;
    static ASN c = ASN.UTF8String;
    private ObjectID d;
    private ASN1Object e;

    static {
        defineEncoding(ObjectID.emailAddress, ASN.IA5String);
        defineEncoding(ObjectID.uniqueIdentifier, ASN.BIT_STRING);
    }

    public AVA(ASN1Object aSN1Object) throws CodingException {
        if (aSN1Object == null) {
            throw new CodingException("Cannot create an AVA from a null object!");
        }
        decode(aSN1Object);
    }

    public AVA(ObjectID objectID, Object obj) throws IllegalArgumentException {
        this(objectID, obj, null);
    }

    public AVA(ObjectID objectID, Object obj, ASN asn) throws IllegalArgumentException {
        if (objectID == null || obj == null) {
            throw new IllegalArgumentException("Type and value must be non null for creating an AVA!");
        }
        this.d = objectID;
        if (obj instanceof ASN1Object) {
            this.e = (ASN1Object) obj;
        } else {
            try {
                this.e = a(obj, objectID, asn);
            } catch (CodingException unused) {
                throw new IllegalArgumentException("Cannot create ASN.1 representation of given object for requested type!");
            }
        }
    }

    private static ASN1Object a(Object obj, ObjectID objectID, ASN asn) throws CodingException {
        try {
            ASN encoding = getEncoding(objectID);
            if (asn == null) {
                asn = encoding;
            }
            if (asn == null) {
                asn = b;
                if ((obj instanceof String) && ((asn == ASN.NumericString || b == ASN.PrintableString) && !PrintableString.isPrintableString((String) obj))) {
                    asn = c;
                }
            }
            return (objectID == ObjectID.uniqueIdentifier && (obj instanceof String) && asn.equals(ASN.BIT_STRING)) ? new BIT_STRING(DerCoder.encode(new PrintableString((String) obj))) : ASN.create(asn, obj);
        } catch (Exception e) {
            throw new CodingException(e.getMessage());
        }
    }

    private static Object a(ASN1Object aSN1Object, ObjectID objectID) {
        Object value;
        if (!objectID.equals(ObjectID.uniqueIdentifier) || aSN1Object.isA(ASN.PrintableString)) {
            value = null;
        } else {
            try {
                value = DerCoder.decode((byte[]) aSN1Object.getValue()).getValue();
            } catch (Exception unused) {
                value = null;
            }
        }
        return value == null ? aSN1Object.getValue() : value;
    }

    private static String a(ASN1Object aSN1Object, boolean z, String str) {
        String string;
        StringBuffer stringBuffer;
        if (aSN1Object.isA(ASN.BIT_STRING) && z) {
            stringBuffer = new StringBuffer();
            stringBuffer.append("#'");
            stringBuffer.append(((BIT_STRING) aSN1Object).getBinaryString());
            string = "'B";
        } else {
            string = Util.toString(DerCoder.encode(aSN1Object), str);
            stringBuffer = new StringBuffer();
            stringBuffer.append("#");
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    public static void defineEncoding(ObjectID objectID, ASN asn) {
        a.put(objectID, asn);
    }

    public static ASN getDefaultEncoding() {
        return b;
    }

    public static ASN getEncoding(ObjectID objectID) {
        return (ASN) a.get(objectID);
    }

    public static ASN getNonPrintableDefaultEncoding() {
        return c;
    }

    public static void setDefaultEncoding(ASN asn) {
        b = asn;
    }

    public static void setNonPrintableDefaultEncoding(ASN asn) {
        c = asn;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.d = (ObjectID) aSN1Object.getComponentAt(0);
        this.e = aSN1Object.getComponentAt(1);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AVA)) {
            return false;
        }
        AVA ava = (AVA) obj;
        if (this.d.equals(ava.d)) {
            return this.e.isStringType() ? ((ASN1String) this.e).equals(ava.e) : CryptoUtils.equalsBlock(DerCoder.encode(this.e), DerCoder.encode(ava.e));
        }
        return false;
    }

    public ASN1Object getASN1Value() throws CodingException {
        return this.e;
    }

    public String getRFC2253String() throws RFC2253NameParserException {
        return getRFC2253String(Util.getDefaultRFC2253StringEscaping());
    }

    public String getRFC2253String(boolean z) throws RFC2253NameParserException {
        String strA;
        Object objA = a(this.e, this.d);
        boolean zEquals = this.e.getAsnType().equals(ASN.BIT_STRING);
        if ((objA instanceof String) && !zEquals && ObjectID.hasRegisteredName(this.d.getID())) {
            try {
                strA = UTF8String.getRFC2253String((String) objA, z);
            } catch (CodingException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot write this AVA: ");
                stringBuffer.append(e.getMessage());
                throw new RFC2253NameParserException(stringBuffer.toString());
            }
        } else {
            try {
                strA = a(this.e, false, "");
            } catch (Exception e2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Cannot write this AVA: ");
                stringBuffer2.append(e2.getMessage());
                throw new RFC2253NameParserException(stringBuffer2.toString());
            }
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append(this.d.getShortName());
        stringBuffer3.append("=");
        stringBuffer3.append((Object) strA);
        return stringBuffer3.toString();
    }

    public ObjectID getType() {
        return this.d;
    }

    public Object getValue() {
        return a(this.e, this.d);
    }

    public String getValueAsString() {
        Object objA = a(this.e, this.d);
        if (!(objA instanceof String)) {
            objA = a(this.e, true, ":");
        }
        return (String) objA;
    }

    public int hashCode() {
        return this.d.hashCode() + a(this.e, this.d).hashCode();
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.d);
        sequence.addComponent(this.e);
        return sequence;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        Object objA = a(this.e, this.d);
        if (!(objA instanceof String)) {
            try {
                objA = a(this.e, true, ":");
            } catch (Exception unused) {
            }
        }
        boolean z2 = false;
        if (objA instanceof String) {
            z2 = ((String) objA).indexOf(61) != -1;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(z ? this.d.getName() : this.d.getShortName());
        if (z2) {
            stringBuffer.append("=\"");
            stringBuffer.append(objA);
            stringBuffer.append("\"");
        } else {
            stringBuffer.append("=");
            stringBuffer.append(objA);
        }
        return stringBuffer.toString();
    }
}

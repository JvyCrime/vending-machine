package iaik.asn1;

import iaik.utils.InternalErrorException;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class ASN implements Cloneable {
    public static final int APPLICATION = 64;
    public static final ASN BIT_STRING;
    public static final ASN BMPString;
    public static final ASN BOOLEAN;
    public static final int CONTEXT_SPECIFIC = 128;
    public static final ASN CON_SPEC;
    public static final ASN ENUMERATED;
    public static final ASN EXTERNAL;
    public static final ASN GeneralString;
    public static final ASN GeneralizedTime;
    public static final ASN IA5String;
    public static final ASN INTEGER;
    public static final ASN NULL;
    public static final ASN NumericString;
    public static final ASN OBJECT_DESCRIPTOR;
    public static final ASN OCTET_STRING;
    public static final ASN ObjectID;
    public static final int PRIVATE = 192;
    public static final ASN PrintableString;
    public static final ASN SEQUENCE;
    public static final ASN SET;
    public static final ASN T61String;
    public static final ASN UNIString;
    public static final int UNIVERSAL = 0;
    public static final ASN UNKNOWN;
    public static final ASN UTCTime;
    public static final ASN UTF8String;
    public static final ASN VisibleString;
    static final Class a;
    static final a b;
    static Class c;
    private static final ASN d;
    protected String name;
    protected int tag;
    protected int tag_class;

    static {
        Class clsClass$ = c;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.asn1.ASN1Object");
            c = clsClass$;
        }
        a = clsClass$;
        b = new a(clsClass$);
        ASN asn = new ASN(0, "UNKNOWN");
        UNKNOWN = asn;
        ASN asn2 = new ASN(1, "BOOLEAN");
        BOOLEAN = asn2;
        ASN asn3 = new ASN(2, "INTEGER");
        INTEGER = asn3;
        ASN asn4 = new ASN(3, "BIT STRING");
        BIT_STRING = asn4;
        ASN asn5 = new ASN(4, "OCTET STRING");
        OCTET_STRING = asn5;
        ASN asn6 = new ASN(5, "NULL");
        NULL = asn6;
        ASN asn7 = new ASN(6, "OBJECT ID");
        ObjectID = asn7;
        OBJECT_DESCRIPTOR = new ASN(7, "OBJECT DESCRIPTOR");
        EXTERNAL = new ASN(8, "EXTERNAL");
        ASN asn8 = new ASN(10, "ENUMERATED");
        ENUMERATED = asn8;
        ASN asn9 = new ASN(12, "UTF8String");
        UTF8String = asn9;
        ASN asn10 = new ASN(16, "SEQUENCE");
        SEQUENCE = asn10;
        ASN asn11 = new ASN(17, "SET");
        SET = asn11;
        ASN asn12 = new ASN(18, "NumericString");
        NumericString = asn12;
        ASN asn13 = new ASN(19, "PrintableString");
        PrintableString = asn13;
        ASN asn14 = new ASN(20, "T61String");
        T61String = asn14;
        ASN asn15 = new ASN(22, "IA5String");
        IA5String = asn15;
        ASN asn16 = new ASN(23, "UTCTime");
        UTCTime = asn16;
        ASN asn17 = new ASN(24, "GeneralizedTime");
        GeneralizedTime = asn17;
        ASN asn18 = new ASN(26, "VisibleString");
        VisibleString = asn18;
        ASN asn19 = new ASN(27, "GeneralString");
        GeneralString = asn19;
        ASN asn20 = new ASN(28, "UNIString");
        UNIString = asn20;
        ASN asn21 = new ASN(30, "BMPString");
        BMPString = asn21;
        CON_SPEC = new ASN(128, "CONTEXTSPECIFIC", 128);
        ASN asn22 = new ASN(0, "CONTEXTSPECIFIC", 128);
        d = asn22;
        a(asn4, "iaik.asn1.BIT_STRING");
        a(asn2, "iaik.asn1.BOOLEAN");
        a(asn7, "iaik.asn1.ObjectID");
        a(asn8, "iaik.asn1.ENUMERATED");
        a(asn9, "iaik.asn1.UTF8String");
        a(asn15, "iaik.asn1.IA5String");
        a(asn3, "iaik.asn1.INTEGER");
        a(asn6, "iaik.asn1.NULL");
        a(asn5, "iaik.asn1.OCTET_STRING");
        a(asn12, "iaik.asn1.NumericString");
        a(asn13, "iaik.asn1.PrintableString");
        a(asn14, "iaik.asn1.T61String");
        a(asn19, "iaik.asn1.GeneralString");
        a(asn21, "iaik.asn1.BMPString");
        a(asn20, "iaik.asn1.UNIString");
        a(asn16, "iaik.asn1.UTCTime");
        a(asn17, "iaik.asn1.GeneralizedTime");
        a(asn18, "iaik.asn1.VisibleString");
        a(asn10, "iaik.asn1.SEQUENCE");
        a(asn11, "iaik.asn1.SET");
        a(asn22, "iaik.asn1.CON_SPEC");
        a(asn, "iaik.asn1.UNKNOWN");
    }

    public ASN(int i, String str) {
        this.tag = i;
        this.name = str;
        this.tag_class = 0;
    }

    public ASN(int i, String str, int i2) {
        this.tag = i;
        this.name = str;
        this.tag_class = i2;
    }

    private static void a(ASN asn, String str) {
        b.register(asn, str);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static ASN1Object create(ASN asn) throws InstantiationException {
        if (asn == null) {
            asn = NULL;
        }
        if (asn.tag_class != 128) {
            return (ASN1Object) b.create(asn);
        }
        ASN1Object aSN1Object = (ASN1Object) b.create(d);
        aSN1Object.asnType.tag = asn.tag;
        return aSN1Object;
    }

    public static ASN1Object create(ASN asn, Object obj) throws InstantiationException {
        ASN1Object aSN1ObjectCreate = create(asn);
        aSN1ObjectCreate.setValue(obj);
        return aSN1ObjectCreate;
    }

    public static ASN1Object createSequenceOf(Vector vector) throws CodingException {
        ASN1Type[] aSN1TypeArr = new ASN1Type[vector.size()];
        Enumeration enumerationElements = vector.elements();
        int i = 0;
        while (enumerationElements.hasMoreElements()) {
            aSN1TypeArr[i] = (ASN1Type) enumerationElements.nextElement();
            i++;
        }
        return createSequenceOf(aSN1TypeArr);
    }

    public static ASN1Object createSequenceOf(ASN1Type[] aSN1TypeArr) throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        if (aSN1TypeArr != null && aSN1TypeArr.length != 0) {
            for (ASN1Type aSN1Type : aSN1TypeArr) {
                sequence.addComponent(aSN1Type.toASN1Object());
            }
        }
        return sequence;
    }

    public static ASN1Object createSetOf(Vector vector) throws CodingException {
        ASN1Type[] aSN1TypeArr = new ASN1Type[vector.size()];
        Enumeration enumerationElements = vector.elements();
        int i = 0;
        while (enumerationElements.hasMoreElements()) {
            aSN1TypeArr[i] = (ASN1Type) enumerationElements.nextElement();
            i++;
        }
        return createSetOf(aSN1TypeArr);
    }

    public static ASN1Object createSetOf(ASN1Type[] aSN1TypeArr) throws CodingException {
        return createSetOf(aSN1TypeArr, false);
    }

    public static ASN1Object createSetOf(ASN1Type[] aSN1TypeArr, boolean z) throws CodingException {
        SET set = new SET(z);
        if (aSN1TypeArr == null) {
            return set;
        }
        for (ASN1Type aSN1Type : aSN1TypeArr) {
            set.addComponent(aSN1Type.toASN1Object());
        }
        return set;
    }

    public static ASN1Type[] parseSequenceOf(ASN1Object aSN1Object, Class cls) throws CodingException {
        int iCountComponents = aSN1Object.countComponents();
        try {
            ASN1Type[] aSN1TypeArr = (ASN1Type[]) Array.newInstance((Class<?>) cls, iCountComponents);
            for (int i = 0; i < iCountComponents; i++) {
                aSN1TypeArr[i] = (ASN1Type) cls.newInstance();
                aSN1TypeArr[i].decode(aSN1Object.getComponentAt(i));
            }
            return aSN1TypeArr;
        } catch (IllegalAccessException e) {
            throw new InternalErrorException(e);
        } catch (InstantiationException e2) {
            throw new InternalErrorException(e2);
        }
    }

    public static void register(ASN asn, Class cls) {
        if (a.isAssignableFrom(cls)) {
            b.register(asn, cls);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Class ");
        stringBuffer.append(cls);
        stringBuffer.append(" is no ASN1Object implementation!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ASN)) {
            return false;
        }
        ASN asn = (ASN) obj;
        return this.tag == asn.tag && this.tag_class == asn.tag_class;
    }

    public String getName() {
        return this.name;
    }

    public int getTag() {
        return this.tag;
    }

    public int getTagClass() {
        return this.tag_class;
    }

    public int hashCode() {
        return this.tag | (this.tag_class << 24);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ASN.1 Type [");
        stringBuffer.append(this.tag);
        stringBuffer.append(",");
        stringBuffer.append(this.name);
        stringBuffer.append(",");
        int i = this.tag_class;
        stringBuffer.append(i == 192 ? "PRIVATE" : i == 64 ? "APPLICATION" : i == 128 ? "CONTEXT_SPECIFIC" : "UNIVERSAL");
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

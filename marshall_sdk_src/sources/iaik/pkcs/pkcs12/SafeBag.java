package iaik.pkcs.pkcs12;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.Attribute;
import iaik.pkcs.PKCSException;
import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public abstract class SafeBag extends Attributes implements ASN1Type {
    private static a c = new a();
    static Class f;
    static Class g;
    static Class h;
    static Class i;
    static Class j;
    static Class k;
    static Class l;
    ObjectID e;

    static {
        ObjectID objectID = ObjectID.pkcs12_keyBag;
        Class clsClass$ = f;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.pkcs.pkcs12.KeyBag");
            f = clsClass$;
        }
        register(objectID, clsClass$);
        ObjectID objectID2 = ObjectID.pkcs12_pkcs8ShroudedKeyBag;
        Class clsClass$2 = g;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.pkcs.pkcs12.PKCS8ShroudedKeyBag");
            g = clsClass$2;
        }
        register(objectID2, clsClass$2);
        ObjectID objectID3 = ObjectID.pkcs12_certBag;
        Class clsClass$3 = h;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.pkcs.pkcs12.CertificateBag");
            h = clsClass$3;
        }
        register(objectID3, clsClass$3);
        ObjectID objectID4 = ObjectID.pkcs12_crlBag;
        Class clsClass$4 = i;
        if (clsClass$4 == null) {
            clsClass$4 = class$("iaik.pkcs.pkcs12.CRLBag");
            i = clsClass$4;
        }
        register(objectID4, clsClass$4);
        ObjectID objectID5 = ObjectID.pkcs12_secretBag;
        Class clsClass$5 = j;
        if (clsClass$5 == null) {
            clsClass$5 = class$("iaik.pkcs.pkcs12.SecretBag");
            j = clsClass$5;
        }
        register(objectID5, clsClass$5);
        ObjectID objectID6 = ObjectID.pkcs12_safeContentsBag;
        Class clsClass$6 = k;
        if (clsClass$6 == null) {
            clsClass$6 = class$("iaik.pkcs.pkcs12.SafeContentsBag");
            k = clsClass$6;
        }
        register(objectID6, clsClass$6);
    }

    protected SafeBag() {
    }

    protected SafeBag(String str, byte[] bArr) {
        super(str, bArr);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static SafeBag create(ObjectID objectID) throws PKCSException {
        try {
            return (SafeBag) c.create(objectID);
        } catch (InstantiationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PKCS#12 bag type registered for the given object ID! ");
            stringBuffer.append(e);
            throw new PKCSException(stringBuffer.toString());
        }
    }

    protected static byte[] encodeSafeContents(SafeBag[] safeBagArr) throws CodingException {
        return DerCoder.encode(encodeSafeContentsAsASN1Object(safeBagArr));
    }

    protected static ASN1Object encodeSafeContentsAsASN1Object(SafeBag[] safeBagArr) throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        for (int i2 = 0; i2 < safeBagArr.length; i2++) {
            SEQUENCE sequence2 = new SEQUENCE();
            sequence2.addComponent(safeBagArr[i2].getBagType());
            sequence2.addComponent(new CON_SPEC(0, safeBagArr[i2].toASN1Object()));
            Attribute[] attributes = safeBagArr[i2].getAttributes();
            if (attributes != null) {
                sequence2.addComponent(ASN.createSetOf(attributes));
            }
            sequence.addComponent(sequence2);
        }
        return sequence;
    }

    protected static SafeBag[] parseSafeContents(ASN1Object aSN1Object) throws CodingException {
        SafeBag[] safeBagArr = new SafeBag[aSN1Object.countComponents()];
        for (int i2 = 0; i2 < aSN1Object.countComponents(); i2++) {
            ASN1Object componentAt = aSN1Object.getComponentAt(i2);
            SafeBag safeBagCreate = null;
            try {
                safeBagCreate = create((ObjectID) componentAt.getComponentAt(0));
            } catch (PKCSException unused) {
            }
            ASN1Object componentAt2 = componentAt.getComponentAt(1);
            if (componentAt2.getAsnType().getTag() != 0) {
                throw new CodingException("No PKCS#12 SafeBag!");
            }
            safeBagCreate.decode((ASN1Object) componentAt2.getValue());
            if (componentAt.countComponents() > 2) {
                ASN1Object componentAt3 = componentAt.getComponentAt(2);
                Class clsClass$ = l;
                if (clsClass$ == null) {
                    clsClass$ = class$("iaik.asn1.structures.Attribute");
                    l = clsClass$;
                }
                safeBagCreate.setAttributes((Attribute[]) ASN.parseSequenceOf(componentAt3, clsClass$));
            }
            safeBagArr[i2] = safeBagCreate;
        }
        return safeBagArr;
    }

    protected static SafeBag[] parseSafeContents(byte[] bArr) throws CodingException {
        return parseSafeContents(DerCoder.decode(bArr));
    }

    public static void register(ObjectID objectID, Class cls) {
        c.register(objectID, cls);
    }

    public ObjectID getBagType() {
        return this.e;
    }

    @Override // iaik.pkcs.pkcs12.Attributes
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Bag type: ");
        stringBuffer2.append(this.e);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        if (this.a != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Friendly name: ");
            stringBuffer3.append(this.a);
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        if (this.b != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("local key ID: ");
            stringBuffer4.append(Util.toString(this.b));
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }
}

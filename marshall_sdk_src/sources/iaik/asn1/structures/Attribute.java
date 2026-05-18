package iaik.asn1.structures;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.SET;
import iaik.utils.CryptoUtils;
import iaik.utils.ObjectFactory;
import iaik.utils.Util;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Attribute implements ASN1Type {
    static Class a;
    static Class b;
    static Class c;
    private static ObjectFactory d = new ObjectFactory();
    private static final Class e;
    private ObjectID f;
    private AttributeValue[] g;
    private boolean h;

    static {
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.asn1.structures.AttributeValue");
            a = clsClass$;
        }
        e = clsClass$;
        ObjectID objectID = ObjectID.extensionRequest;
        Class clsClass$2 = b;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.pkcs.pkcs9.ExtensionRequest");
            b = clsClass$2;
        }
        register(objectID, clsClass$2);
        ObjectID objectID2 = ObjectID.challengePassword;
        Class clsClass$3 = c;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.pkcs.pkcs9.ChallengePassword");
            c = clsClass$3;
        }
        register(objectID2, clsClass$3);
    }

    public Attribute() {
        this.h = false;
    }

    public Attribute(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public Attribute(ObjectID objectID, ASN1Object[] aSN1ObjectArr) {
        this(objectID, aSN1ObjectArr, false);
    }

    public Attribute(ObjectID objectID, ASN1Object[] aSN1ObjectArr, boolean z) {
        this.f = objectID;
        if (aSN1ObjectArr != null) {
            this.g = new AttributeValue[aSN1ObjectArr.length];
            for (int i = 0; i < aSN1ObjectArr.length; i++) {
                this.g[i] = new UnknownAttributeValue(objectID, aSN1ObjectArr[i]);
            }
        }
        this.h = z;
    }

    public Attribute(AttributeValue attributeValue) throws CodingException {
        this(attributeValue, false);
    }

    public Attribute(AttributeValue attributeValue, boolean z) throws CodingException {
        Objects.requireNonNull(attributeValue, "attributeValue must not be null!");
        this.f = attributeValue.getAttributeType();
        this.g = new AttributeValue[]{attributeValue};
        this.h = z;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static AttributeValue create(ObjectID objectID) throws InstantiationException {
        return (AttributeValue) d.create(objectID);
    }

    public static void register(ObjectID objectID, Class cls) {
        if (e.isAssignableFrom(cls)) {
            d.register(objectID, cls);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Class ");
        stringBuffer.append(cls);
        stringBuffer.append(" is no AttributeValue implementation!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public void addAttributeValue(AttributeValue attributeValue) throws CodingException, IllegalArgumentException {
        Objects.requireNonNull(attributeValue, "attributeValue must not be null!");
        ObjectID attributeType = attributeValue.getAttributeType();
        ObjectID objectID = this.f;
        if (objectID == null) {
            this.f = attributeType;
        } else if (!objectID.equals(attributeType)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot add AttributeValue: Invalid type: ");
            stringBuffer.append(attributeType.getName());
            stringBuffer.append(". Expected ");
            stringBuffer.append(this.f.getName());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (this.g == null) {
            this.g = new AttributeValue[1];
        } else if (attributeValue.multipleAllowed()) {
            AttributeValue[] attributeValueArr = this.g;
            this.g = (AttributeValue[]) Util.resizeArray(attributeValueArr, attributeValueArr.length + 1);
        } else if (this.g.length > 1) {
            this.g = new AttributeValue[1];
        }
        AttributeValue[] attributeValueArr2 = this.g;
        attributeValueArr2[attributeValueArr2.length - 1] = attributeValue;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.f = (ObjectID) aSN1Object.getComponentAt(0);
        SET set = (SET) aSN1Object.getComponentAt(1);
        this.g = new AttributeValue[set.countComponents()];
        for (int i = 0; i < set.countComponents(); i++) {
            ASN1Object componentAt = set.getComponentAt(i);
            try {
                this.g[i] = create(this.f);
            } catch (InstantiationException unused) {
                this.g[i] = new UnknownAttributeValue(this.f);
            }
            this.g[i].decode(componentAt);
        }
    }

    public boolean equals(Object obj) {
        AttributeValue[] attributeValueArr;
        ObjectID objectID;
        if (this == obj) {
            return true;
        }
        if (obj instanceof Attribute) {
            Attribute attribute = (Attribute) obj;
            ObjectID objectID2 = this.f;
            boolean zEquals = (objectID2 == null || (objectID = attribute.f) == null) ? objectID2 == null && attribute.f == null : objectID2.equals(objectID);
            if (!zEquals) {
                return zEquals;
            }
            AttributeValue[] attributeValueArr2 = this.g;
            if (attributeValueArr2 == null || (attributeValueArr = attribute.g) == null) {
                if (attributeValueArr2 == null && attribute.g == null) {
                    return true;
                }
            } else if (attributeValueArr2.length == attributeValueArr.length) {
                return CryptoUtils.equalsBlock(DerCoder.encode(toASN1Object()), DerCoder.encode(attribute.toASN1Object()));
            }
        }
        return false;
    }

    public AttributeValue getAttributeValue() throws CodingException {
        AttributeValue[] attributeValueArr = this.g;
        if (attributeValueArr == null || attributeValueArr.length == 0) {
            return null;
        }
        return attributeValueArr[0];
    }

    public AttributeValue[] getAttributeValues() throws CodingException {
        AttributeValue[] attributeValueArr = this.g;
        return attributeValueArr == null ? new AttributeValue[0] : attributeValueArr;
    }

    public ObjectID getType() {
        return this.f;
    }

    public ASN1Object[] getValue() {
        AttributeValue[] attributeValueArr = this.g;
        if (attributeValueArr == null) {
            return null;
        }
        ASN1Object[] aSN1ObjectArr = new ASN1Object[attributeValueArr.length];
        int i = 0;
        while (true) {
            AttributeValue[] attributeValueArr2 = this.g;
            if (i >= attributeValueArr2.length) {
                return aSN1ObjectArr;
            }
            try {
                aSN1ObjectArr[i] = attributeValueArr2[i].toASN1Object();
                i++;
            } catch (CodingException e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("ASN.1 component error: ");
                stringBuffer.append(e2.toString());
                throw new RuntimeException(stringBuffer.toString());
            }
        }
    }

    public int hashCode() {
        ObjectID objectID = this.f;
        if (objectID == null) {
            return 0;
        }
        return objectID.hashCode();
    }

    public void setAttributeValue(AttributeValue attributeValue) throws CodingException, IllegalArgumentException {
        if (attributeValue == null) {
            this.g = null;
            return;
        }
        ObjectID attributeType = attributeValue.getAttributeType();
        ObjectID objectID = this.f;
        if (objectID == null) {
            this.f = attributeType;
        } else if (!objectID.equals(attributeType)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot set AttributeValue: Invalid type: ");
            stringBuffer.append(attributeType.getName());
            stringBuffer.append(". Expected ");
            stringBuffer.append(this.f.getName());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.g = new AttributeValue[]{attributeValue};
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return toASN1Object(this.h);
    }

    public ASN1Object toASN1Object(boolean z) {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.f);
        SET set = new SET(z);
        if (this.g != null) {
            int i = 0;
            while (true) {
                AttributeValue[] attributeValueArr = this.g;
                if (i >= attributeValueArr.length) {
                    break;
                }
                try {
                    set.addComponent(attributeValueArr[i].toASN1Object());
                    i++;
                } catch (CodingException e2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("ASN.1 component error: ");
                    stringBuffer.append(e2.toString());
                    throw new RuntimeException(stringBuffer.toString());
                }
            }
        }
        sequence.addComponent(set);
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.f != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.f.getName());
            stringBuffer2.append(":\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        AttributeValue[] attributeValueArr = this.g;
        if (attributeValueArr != null && attributeValueArr.length > 0) {
            int i = 0;
            while (true) {
                AttributeValue[] attributeValueArr2 = this.g;
                if (i >= attributeValueArr2.length) {
                    break;
                }
                stringBuffer.append(attributeValueArr2[i].toString());
                i++;
            }
        }
        return stringBuffer.toString();
    }
}

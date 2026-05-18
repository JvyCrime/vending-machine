package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;

/* JADX INFO: loaded from: classes.dex */
public class AccessDescription implements ASN1Type {
    private ObjectID a;
    private GeneralName b;

    public AccessDescription() {
    }

    public AccessDescription(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public AccessDescription(ObjectID objectID, GeneralName generalName) throws IllegalArgumentException {
        if (objectID == null) {
            throw new IllegalArgumentException("Cannot create AccessDescription! No accessMethod specified!");
        }
        if (generalName == null) {
            throw new IllegalArgumentException("Cannot create AccessDescription! No accessLocation specified!");
        }
        this.a = objectID;
        this.b = generalName;
    }

    public AccessDescription(ObjectID objectID, String str) throws IllegalArgumentException {
        if (objectID == null) {
            throw new IllegalArgumentException("Cannot create AccessDescription! No accessMethod specified!");
        }
        if (str == null) {
            throw new IllegalArgumentException("Cannot create AccessDescription! No accessLocation uri specified!");
        }
        this.a = objectID;
        this.b = new GeneralName(6, str);
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        try {
            if (!aSN1Object.isA(ASN.SEQUENCE)) {
                throw new CodingException("Cannot parse AccessDescription! Has to be a SEQUENCE!");
            }
            SEQUENCE sequence = (SEQUENCE) aSN1Object;
            if (sequence.countComponents() != 2) {
                throw new CodingException("Cannot parse AccessDescription! Wrong number of components!");
            }
            ASN1Object componentAt = sequence.getComponentAt(0);
            if (!componentAt.isA(ASN.ObjectID)) {
                throw new CodingException("Cannot parse AccessDescription! First component has to be an ObjectID!");
            }
            this.a = (ObjectID) componentAt;
            this.b = new GeneralName(sequence.getComponentAt(1));
        } catch (Exception e) {
            throw new CodingException(e.toString());
        }
    }

    public GeneralName getAccessLocation() {
        return this.b;
    }

    public ObjectID getAccessMethod() {
        return this.a;
    }

    public String getUriAccessLocation() {
        GeneralName generalName = this.b;
        if (generalName == null || generalName.getType() != 6) {
            return null;
        }
        return (String) this.b.getName();
    }

    public void setAccessLocation(GeneralName generalName) throws IllegalArgumentException {
        if (generalName == null) {
            throw new IllegalArgumentException("Cannot set null access location!");
        }
        this.b = generalName;
    }

    public void setAccessMethod(ObjectID objectID) throws IllegalArgumentException {
        if (objectID == null) {
            throw new IllegalArgumentException("Cannot set null accessMethod!");
        }
        this.a = objectID;
    }

    public void setUriAccessLocation(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Cannot set null access location!");
        }
        this.b = new GeneralName(6, str);
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        ObjectID objectID = this.a;
        if (objectID == null) {
            throw new CodingException("Cannot create ASN.1 object for this AccessDescription! accessMethod field not specified!");
        }
        if (this.b == null) {
            throw new CodingException("Cannot create ASN.1 object for this AccessDescription! accessLocation field not specified!");
        }
        sequence.addComponent(objectID);
        sequence.addComponent(this.b.toASN1Object());
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("accessMethod: ");
        ObjectID objectID = this.a;
        stringBuffer.append(objectID == null ? "null" : objectID.toString());
        stringBuffer.append("\naccessLocation: ");
        GeneralName generalName = this.b;
        stringBuffer.append(generalName != null ? generalName.toString() : "null");
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

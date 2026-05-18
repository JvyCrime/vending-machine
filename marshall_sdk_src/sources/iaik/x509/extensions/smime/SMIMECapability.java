package iaik.x509.extensions.smime;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SMIMECapability implements ASN1Type {
    private ObjectID a;
    private ASN1Object b;

    public SMIMECapability() {
    }

    public SMIMECapability(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public SMIMECapability(ObjectID objectID) {
        this.a = objectID;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = (ObjectID) aSN1Object.getComponentAt(0);
        if (aSN1Object.countComponents() == 2) {
            this.b = aSN1Object.getComponentAt(1);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SMIMECapability)) {
            return false;
        }
        try {
            return CryptoUtils.equalsBlock(DerCoder.encode(toASN1Object()), DerCoder.encode(((SMIMECapability) obj).toASN1Object()));
        } catch (Exception unused) {
            return false;
        }
    }

    public ObjectID getCapabilityID() {
        return this.a;
    }

    public ASN1Object getParameters() {
        return this.b;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public void setParameters(ASN1Object aSN1Object) {
        this.b = aSN1Object;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        if (this.a == null) {
            throw new CodingException("Missing capabilityID!");
        }
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a);
        ASN1Object aSN1Object = this.b;
        if (aSN1Object != null) {
            sequence.addComponent(aSN1Object);
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("CapabilityID: ");
        stringBuffer2.append(this.a.getName());
        stringBuffer.append(stringBuffer2.toString());
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\nParameters: ");
            stringBuffer3.append(this.b);
            stringBuffer.append(stringBuffer3.toString());
        }
        return stringBuffer.toString();
    }
}

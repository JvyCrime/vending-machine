package iaik.x509;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public class UnknownExtension extends V3Extension {
    private ObjectID a;
    private ASN1Object b = null;

    public UnknownExtension(ObjectID objectID) {
        this.a = objectID;
    }

    @Override // iaik.x509.V3Extension
    public String getName() {
        return this.a.getID();
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) {
        this.b = aSN1Object;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return this.b;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("UnknownExtension:     ");
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(this.b.toString());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

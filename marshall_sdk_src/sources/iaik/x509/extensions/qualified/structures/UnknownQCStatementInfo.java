package iaik.x509.extensions.qualified.structures;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public class UnknownQCStatementInfo extends QCStatementInfo {
    private ObjectID a;
    private ASN1Object b = null;

    public UnknownQCStatementInfo(ObjectID objectID) {
        this.a = objectID;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public void decode(ASN1Object aSN1Object) {
        this.b = aSN1Object;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public String getName() {
        return this.a.getID();
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ObjectID getStatementID() {
        return this.a;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ASN1Object toASN1Object() {
        return this.b;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unknown QCStatementInfo:\n");
        stringBuffer.append(this.b.toString());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

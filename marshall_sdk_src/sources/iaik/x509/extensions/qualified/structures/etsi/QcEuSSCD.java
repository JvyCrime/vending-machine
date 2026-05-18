package iaik.x509.extensions.qualified.structures.etsi;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.x509.extensions.qualified.structures.QCStatementInfo;

/* JADX INFO: loaded from: classes2.dex */
public class QcEuSSCD extends QCStatementInfo {
    public static final ObjectID statementID = ObjectID.qcEuSSCD;

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public void decode(ASN1Object aSN1Object) throws CodingException {
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ObjectID getStatementID() {
        return statementID;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ASN1Object toASN1Object() {
        return null;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public String toString() {
        return "";
    }
}

package iaik.x509.extensions.qualified.structures;

import iaik.asn1.ObjectID;
import iaik.asn1.structures.GeneralName;

/* JADX INFO: loaded from: classes2.dex */
public class QCSyntaxV1 extends SemanticsInformation {
    public static final ObjectID statementID = ObjectID.qcSyntaxV1;

    public QCSyntaxV1() {
    }

    public QCSyntaxV1(ObjectID objectID, GeneralName[] generalNameArr) {
        super(objectID, generalNameArr);
    }

    @Override // iaik.x509.extensions.qualified.structures.SemanticsInformation, iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ObjectID getStatementID() {
        return statementID;
    }
}

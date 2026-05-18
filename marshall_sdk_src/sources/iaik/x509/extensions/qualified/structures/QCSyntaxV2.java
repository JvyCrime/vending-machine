package iaik.x509.extensions.qualified.structures;

import iaik.asn1.ObjectID;
import iaik.asn1.structures.GeneralName;

/* JADX INFO: loaded from: classes2.dex */
public class QCSyntaxV2 extends SemanticsInformation {
    public static final ObjectID statementID = ObjectID.qcSyntaxV2;

    public QCSyntaxV2() {
    }

    public QCSyntaxV2(ObjectID objectID, GeneralName[] generalNameArr) {
        super(objectID, generalNameArr);
    }

    @Override // iaik.x509.extensions.qualified.structures.SemanticsInformation, iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ObjectID getStatementID() {
        return statementID;
    }
}

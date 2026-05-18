package iaik.x509.extensions.qualified.structures.etsi;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.x509.extensions.qualified.structures.QCStatementInfo;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class QcEuRetentionPeriod extends QCStatementInfo {
    public static final ObjectID statementID = ObjectID.qcEuRetentionPeriod;
    int a;

    public QcEuRetentionPeriod() {
    }

    public QcEuRetentionPeriod(int i) {
        this.a = i;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = ((BigInteger) aSN1Object.getValue()).intValue();
    }

    public int getRetentionPeriod() {
        return this.a;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ObjectID getStatementID() {
        return statementID;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ASN1Object toASN1Object() {
        return new INTEGER(this.a);
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("RetentionPeriod: ");
        stringBuffer.append(this.a);
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

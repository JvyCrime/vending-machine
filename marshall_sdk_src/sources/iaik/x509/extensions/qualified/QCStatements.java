package iaik.x509.extensions.qualified;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.extensions.qualified.structures.QCStatement;

/* JADX INFO: loaded from: classes2.dex */
public class QCStatements extends V3Extension {
    static Class b;
    public static final ObjectID oid = ObjectID.certExt_QcStatements;
    QCStatement[] a;

    public QCStatements() {
    }

    public QCStatements(QCStatement[] qCStatementArr) {
        this.a = qCStatementArr;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public QCStatement getQCStatements(ObjectID objectID) {
        if (this.a == null) {
            return null;
        }
        int i = 0;
        while (true) {
            QCStatement[] qCStatementArr = this.a;
            if (i >= qCStatementArr.length) {
                return null;
            }
            if (qCStatementArr[i].getStatementID().equals(objectID)) {
                return this.a[i];
            }
            i++;
        }
    }

    public QCStatement[] getQCStatements() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        try {
            Class clsClass$ = b;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.x509.extensions.qualified.structures.QCStatement");
                b = clsClass$;
            }
            this.a = (QCStatement[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public void setQCStatements(QCStatement[] qCStatementArr) {
        this.a = qCStatementArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        try {
            return ASN.createSequenceOf(this.a);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            int i = 0;
            while (i < this.a.length) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("QCStatement ");
                int i2 = i + 1;
                stringBuffer2.append(i2);
                stringBuffer2.append(": ");
                stringBuffer.append(stringBuffer2.toString());
                stringBuffer.append(this.a[i]);
                i = i2;
            }
            if (stringBuffer.length() >= 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }
}

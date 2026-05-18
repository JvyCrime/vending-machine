package iaik.x509.extensions.qualified.structures;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.ObjectFactory;

/* JADX INFO: loaded from: classes2.dex */
public class QCStatement implements ASN1Type {
    static Class c;
    static Class d;
    static Class e;
    static Class f;
    static Class g;
    static Class h;
    static Class i;
    private static ObjectFactory j = new ObjectFactory();
    ObjectID a;
    QCStatementInfo b;

    static {
        ObjectID objectID = ObjectID.qcSyntaxV1;
        Class clsClass$ = c;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.extensions.qualified.structures.QCSyntaxV1");
            c = clsClass$;
        }
        register(objectID, clsClass$);
        ObjectID objectID2 = ObjectID.qcSyntaxV2;
        Class clsClass$2 = d;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.x509.extensions.qualified.structures.QCSyntaxV2");
            d = clsClass$2;
        }
        register(objectID2, clsClass$2);
        ObjectID objectID3 = ObjectID.qcEuCompliance;
        Class clsClass$3 = e;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.x509.extensions.qualified.structures.etsi.QcEuCompliance");
            e = clsClass$3;
        }
        register(objectID3, clsClass$3);
        ObjectID objectID4 = ObjectID.qcEuLimitValue;
        Class clsClass$4 = f;
        if (clsClass$4 == null) {
            clsClass$4 = class$("iaik.x509.extensions.qualified.structures.etsi.QcEuLimitValue");
            f = clsClass$4;
        }
        register(objectID4, clsClass$4);
        ObjectID objectID5 = ObjectID.qcEuRetentionPeriod;
        Class clsClass$5 = g;
        if (clsClass$5 == null) {
            clsClass$5 = class$("iaik.x509.extensions.qualified.structures.etsi.QcEuRetentionPeriod");
            g = clsClass$5;
        }
        register(objectID5, clsClass$5);
        ObjectID objectID6 = ObjectID.qcEuSSCD;
        Class clsClass$6 = h;
        if (clsClass$6 == null) {
            clsClass$6 = class$("iaik.x509.extensions.qualified.structures.etsi.QcEuSSCD");
            h = clsClass$6;
        }
        register(objectID6, clsClass$6);
        ObjectID objectID7 = ObjectID.qcEuPDS;
        Class clsClass$7 = i;
        if (clsClass$7 == null) {
            clsClass$7 = class$("iaik.x509.extensions.qualified.structures.etsi.QcEuPDS");
            i = clsClass$7;
        }
        register(objectID7, clsClass$7);
    }

    public QCStatement() {
    }

    public QCStatement(ObjectID objectID) {
        this.a = objectID;
    }

    public QCStatement(QCStatementInfo qCStatementInfo) {
        this.b = qCStatementInfo;
        this.a = qCStatementInfo.getStatementID();
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static QCStatementInfo create(ObjectID objectID) throws InstantiationException {
        return (QCStatementInfo) j.create(objectID);
    }

    public static void register(ObjectID objectID, Class cls) {
        j.register(objectID, cls);
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = (ObjectID) aSN1Object.getComponentAt(0);
        if (aSN1Object.countComponents() == 2) {
            try {
                QCStatementInfo qCStatementInfoCreate = create(this.a);
                this.b = qCStatementInfoCreate;
                qCStatementInfoCreate.decode(aSN1Object.getComponentAt(1));
            } catch (InstantiationException unused) {
                UnknownQCStatementInfo unknownQCStatementInfo = new UnknownQCStatementInfo(this.a);
                this.b = unknownQCStatementInfo;
                unknownQCStatementInfo.decode(aSN1Object.getComponentAt(1));
            }
        }
    }

    public ObjectID getStatementID() {
        return this.a;
    }

    public QCStatementInfo getStatementInfo() {
        return this.b;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        ASN1Object aSN1Object;
        if (this.a == null) {
            throw new CodingException("Cannot create ASN.1 object. Missing statement ID!");
        }
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a);
        QCStatementInfo qCStatementInfo = this.b;
        if (qCStatementInfo != null && (aSN1Object = qCStatementInfo.toASN1Object()) != null) {
            sequence.addComponent(aSN1Object);
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(this.a.getName());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        QCStatementInfo qCStatementInfo = this.b;
        if (qCStatementInfo != null) {
            stringBuffer.append(qCStatementInfo);
        }
        return stringBuffer.toString();
    }
}

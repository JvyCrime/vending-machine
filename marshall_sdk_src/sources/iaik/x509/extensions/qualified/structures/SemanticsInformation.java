package iaik.x509.extensions.qualified.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.GeneralName;

/* JADX INFO: loaded from: classes2.dex */
public class SemanticsInformation extends QCStatementInfo {
    public static final ObjectID statementID = new ObjectID("1.3.6.1.5.5.7.11.1", "SemanticsInformation", null, false);
    ObjectID a;
    GeneralName[] b;

    public SemanticsInformation() {
    }

    public SemanticsInformation(ObjectID objectID, GeneralName[] generalNameArr) throws IllegalArgumentException {
        if (objectID == null && (generalNameArr == null || generalNameArr.length == 0)) {
            throw new IllegalArgumentException("Both semanticIdentifier and nameRegistrationAuthorities are not allowed to be null!");
        }
        this.a = objectID;
        this.b = generalNameArr;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public void decode(ASN1Object aSN1Object) throws CodingException {
        int iCountComponents = aSN1Object.countComponents();
        int i = 1;
        if (iCountComponents < 1 || iCountComponents > 2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid number of components (");
            stringBuffer.append(iCountComponents);
            stringBuffer.append(") in this SemanticsInformation!");
            throw new CodingException(stringBuffer.toString());
        }
        ASN1Object componentAt = aSN1Object.getComponentAt(0);
        if (componentAt.isA(ASN.ObjectID)) {
            this.a = (ObjectID) componentAt;
            if (iCountComponents != 2) {
                return;
            }
        } else {
            i = 0;
        }
        ASN1Object componentAt2 = aSN1Object.getComponentAt(i);
        int iCountComponents2 = componentAt2.countComponents();
        if (i == 0 && iCountComponents2 == 0) {
            throw new CodingException("Invalid empty SemanticsInformation object.");
        }
        this.b = new GeneralName[iCountComponents2];
        for (int i2 = 0; i2 < iCountComponents2; i2++) {
            this.b[i2] = new GeneralName(componentAt2.getComponentAt(i2));
        }
    }

    public GeneralName[] getNameRegistrationAuthorities() {
        return this.b;
    }

    public ObjectID getSemanticsIdentifier() {
        return this.a;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ObjectID getStatementID() {
        return statementID;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ASN1Object toASN1Object() throws CodingException {
        GeneralName[] generalNameArr;
        if (this.a == null && ((generalNameArr = this.b) == null || generalNameArr.length == 0)) {
            throw new CodingException("Cannot create ASN.1 object. Both semanticIdentifier and nameRegistrationAuthorities are not allowed to be null!");
        }
        SEQUENCE sequence = new SEQUENCE();
        ObjectID objectID = this.a;
        if (objectID != null) {
            sequence.addComponent(objectID);
        }
        if (this.b != null) {
            SEQUENCE sequence2 = new SEQUENCE();
            int i = 0;
            while (true) {
                GeneralName[] generalNameArr2 = this.b;
                if (i >= generalNameArr2.length) {
                    break;
                }
                sequence2.addComponent(generalNameArr2[i].toASN1Object());
                i++;
            }
            sequence.addComponent(sequence2);
        }
        return sequence;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("SemanticsIdentifier: ");
            stringBuffer2.append(this.a.getName());
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.b != null) {
            stringBuffer.append("NameRegsitrationAuthorities:\n");
            for (int i = 0; i < this.b.length; i++) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append(this.b[i]);
                stringBuffer3.append("\n");
                stringBuffer.append(stringBuffer3.toString());
            }
        }
        return stringBuffer.toString();
    }
}

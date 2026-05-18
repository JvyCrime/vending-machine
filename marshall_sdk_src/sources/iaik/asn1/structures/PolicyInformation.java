package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;

/* JADX INFO: loaded from: classes.dex */
public class PolicyInformation {
    ObjectID a;
    PolicyQualifierInfo[] b;

    public PolicyInformation(ASN1Object aSN1Object) throws CodingException {
        this.a = null;
        this.b = null;
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new CodingException("No PolicyInformation!");
        }
        this.a = (ObjectID) aSN1Object.getComponentAt(0);
        if (aSN1Object.countComponents() == 2) {
            ASN1Object componentAt = aSN1Object.getComponentAt(1);
            this.b = new PolicyQualifierInfo[componentAt.countComponents()];
            for (int i = 0; i < componentAt.countComponents(); i++) {
                this.b[i] = new PolicyQualifierInfo(componentAt.getComponentAt(i));
            }
        }
    }

    public PolicyInformation(ObjectID objectID, PolicyQualifierInfo[] policyQualifierInfoArr) {
        this.a = null;
        this.b = null;
        this.a = objectID;
        this.b = policyQualifierInfoArr;
    }

    public ObjectID getPolicyIdentifier() {
        return this.a;
    }

    public PolicyQualifierInfo[] getPolicyQualifiers() {
        return this.b;
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a);
        PolicyQualifierInfo[] policyQualifierInfoArr = this.b;
        if (policyQualifierInfoArr != null && policyQualifierInfoArr.length != 0) {
            SEQUENCE sequence2 = new SEQUENCE();
            int i = 0;
            while (true) {
                PolicyQualifierInfo[] policyQualifierInfoArr2 = this.b;
                if (i >= policyQualifierInfoArr2.length) {
                    break;
                }
                sequence2.addComponent(policyQualifierInfoArr2[i].toASN1Object());
                i++;
            }
            sequence.addComponent(sequence2);
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("policyIdentifier: ");
        stringBuffer2.append(this.a.getName());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        if (this.b != null) {
            for (int i = 0; i < this.b.length; i++) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("policyQualifiers[");
                stringBuffer3.append(i);
                stringBuffer3.append("]: ");
                stringBuffer3.append(this.b[i]);
                stringBuffer3.append("\n");
                stringBuffer.append(stringBuffer3.toString());
            }
        }
        stringBuffer.setLength(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }
}

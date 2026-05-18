package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1String;
import iaik.asn1.CodingException;
import iaik.asn1.IA5String;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.UTF8String;
import iaik.utils.InternalErrorException;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public class PolicyQualifierInfo {
    ObjectID a;
    String b;
    ASN1Object c;
    int[] d;
    ASN1Object e;
    private ASN1Object f;

    public PolicyQualifierInfo(ASN1Object aSN1Object) throws CodingException {
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new CodingException("No PolicyQualifierInfo!");
        }
        this.a = (ObjectID) aSN1Object.getComponentAt(0);
        this.f = aSN1Object;
        int iCountComponents = aSN1Object.countComponents();
        if (iCountComponents != 2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid number (");
            stringBuffer.append(iCountComponents);
            stringBuffer.append(") of policyQualifierInfo components!");
            throw new CodingException(stringBuffer.toString());
        }
        if (this.a.equals(ObjectID.id_pkix_cps)) {
            this.b = (String) aSN1Object.getComponentAt(1).getValue();
            return;
        }
        if (this.a.equals(ObjectID.id_pkix_unotice)) {
            ASN1Object componentAt = aSN1Object.getComponentAt(1);
            for (int i = 0; i < componentAt.countComponents(); i++) {
                ASN1Object componentAt2 = componentAt.getComponentAt(i);
                if (componentAt2.isA(ASN.SEQUENCE)) {
                    this.c = componentAt2.getComponentAt(0);
                    ASN1Object componentAt3 = componentAt2.getComponentAt(1);
                    this.d = new int[componentAt3.countComponents()];
                    for (int i2 = 0; i2 < componentAt3.countComponents(); i2++) {
                        this.d[i2] = ((BigInteger) componentAt3.getComponentAt(i2).getValue()).intValue();
                    }
                } else {
                    if (!(componentAt2 instanceof ASN1String)) {
                        throw new CodingException("Invalid ASN.1 type for explicit text!");
                    }
                    this.e = componentAt2;
                }
            }
        }
    }

    public PolicyQualifierInfo(ASN1Object aSN1Object, int[] iArr, ASN1Object aSN1Object2) {
        this.a = ObjectID.id_pkix_unotice;
        if (aSN1Object == null || iArr == null) {
            this.c = null;
            this.d = null;
        } else {
            if (!aSN1Object.isStringType()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid ASN.1 type for organization (only string types allowed): ");
                stringBuffer.append(aSN1Object.getAsnType());
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            this.c = aSN1Object;
            this.d = iArr;
        }
        if (aSN1Object2 == null || aSN1Object2.isStringType()) {
            this.e = aSN1Object2;
            a();
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid ASN.1 type for explicitText (only string types allowed): ");
            stringBuffer2.append(aSN1Object2.getAsnType());
            throw new IllegalArgumentException(stringBuffer2.toString());
        }
    }

    public PolicyQualifierInfo(String str) {
        this.a = ObjectID.id_pkix_cps;
        this.b = str;
        a();
    }

    public PolicyQualifierInfo(String str, int[] iArr, String str2) {
        this.a = ObjectID.id_pkix_unotice;
        if (str == null || iArr == null) {
            this.c = null;
            this.d = null;
        } else {
            this.c = new UTF8String(str);
            this.d = iArr;
        }
        if (str2 != null) {
            if (str2.length() > 200) {
                throw new IllegalArgumentException("explicitText too long! Maximal 200 characters allowed!");
            }
            this.e = new UTF8String(str2);
        }
        a();
    }

    private void a() {
        ASN1Object sequence;
        SEQUENCE sequence2 = new SEQUENCE();
        sequence2.addComponent(this.a);
        if (this.a.equals(ObjectID.id_pkix_cps)) {
            sequence = new IA5String(this.b);
        } else {
            if (!this.a.equals(ObjectID.id_pkix_unotice)) {
                throw new InternalErrorException("Unknown policyQualifierId");
            }
            sequence = new SEQUENCE();
            if (this.c != null) {
                SEQUENCE sequence3 = new SEQUENCE();
                sequence3.addComponent(this.c);
                SEQUENCE sequence4 = new SEQUENCE();
                for (int i = 0; i < this.d.length; i++) {
                    sequence4.addComponent(new INTEGER(this.d[i]));
                }
                sequence3.addComponent(sequence4);
                sequence.addComponent(sequence3);
            }
            ASN1Object aSN1Object = this.e;
            if (aSN1Object != null) {
                sequence.addComponent(aSN1Object);
            }
        }
        sequence2.addComponent(sequence);
        this.f = sequence2;
    }

    public String getCPSuri() {
        return this.b;
    }

    public ASN1Object getExplicitTest() {
        return this.e;
    }

    public ASN1Object getExplicitText() {
        return this.e;
    }

    public int[] getNoticeNumbers() {
        return this.d;
    }

    public ASN1Object getOrganization() {
        return this.c;
    }

    public ObjectID getPolicyQualifier() {
        return this.a;
    }

    public ASN1Object getQualifierValue() {
        try {
            return this.f.getComponentAt(1);
        } catch (CodingException unused) {
            return null;
        }
    }

    public ASN1Object toASN1Object() {
        return this.f;
    }

    public String toString() {
        StringBuffer stringBuffer;
        Object value;
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("policyQualifierId: ");
        stringBuffer3.append(this.a.getName());
        stringBuffer3.append("\n");
        stringBuffer2.append(stringBuffer3.toString());
        if (this.a.equals(ObjectID.id_pkix_cps)) {
            stringBuffer = new StringBuffer();
            stringBuffer.append("CPS URI: ");
            stringBuffer.append(this.b);
        } else {
            if (this.a.equals(ObjectID.id_pkix_unotice)) {
                if (this.c != null) {
                    StringBuffer stringBuffer4 = new StringBuffer();
                    stringBuffer4.append("organization: ");
                    stringBuffer4.append(this.c.getValue());
                    stringBuffer4.append("\n");
                    stringBuffer2.append(stringBuffer4.toString());
                }
                if (this.d != null) {
                    for (int i = 0; i < this.d.length; i++) {
                        StringBuffer stringBuffer5 = new StringBuffer();
                        stringBuffer5.append("noticeNumber[");
                        stringBuffer5.append(i);
                        stringBuffer5.append("]: ");
                        stringBuffer5.append(this.d[i]);
                        stringBuffer5.append("\n");
                        stringBuffer2.append(stringBuffer5.toString());
                    }
                }
                if (this.e != null) {
                    stringBuffer = new StringBuffer();
                    stringBuffer.append("displayText: ");
                    value = this.e.getValue();
                }
                stringBuffer2.setLength(stringBuffer2.length() - 1);
                return stringBuffer2.toString();
            }
            stringBuffer = new StringBuffer();
            stringBuffer.append("Unknown qualifier ");
            value = this.a;
            stringBuffer.append(value);
        }
        stringBuffer.append("\n");
        stringBuffer2.append(stringBuffer.toString());
        stringBuffer2.setLength(stringBuffer2.length() - 1);
        return stringBuffer2.toString();
    }
}

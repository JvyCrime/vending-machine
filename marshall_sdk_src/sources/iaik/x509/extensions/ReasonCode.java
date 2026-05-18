package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.ENUMERATED;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;

/* JADX INFO: loaded from: classes2.dex */
public class ReasonCode extends V3Extension {
    public static final int aACompromise = 10;
    public static final int affiliationChanged = 3;
    public static final int cACompromise = 2;
    public static final int certificateHold = 6;
    public static final int cessationOfOperation = 5;
    public static final int keyCompromise = 1;
    public static final ObjectID oid = ObjectID.crlExt_ReasonCode;
    public static final int privilegeWithdrawn = 9;
    public static final int removeFromCRL = 8;
    public static final int superseded = 4;
    public static final int unspecified = 0;
    private int a;

    public ReasonCode() {
        this.a = 0;
    }

    public ReasonCode(int i) {
        this.a = 0;
        this.a = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ReasonCode) && this.a == ((ReasonCode) obj).a;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public int getReasonCode() {
        return this.a;
    }

    public String getReasonCodeName() {
        switch (this.a) {
            case 0:
                return "unspecified";
            case 1:
                return "keyCompromise";
            case 2:
                return "cACompromise";
            case 3:
                return "affiliationChanged";
            case 4:
                return "superseded";
            case 5:
                return "cessationOfOperation";
            case 6:
                return "certificateHold";
            case 7:
            default:
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("undefined reason code: ");
                stringBuffer.append(this.a);
                return stringBuffer.toString();
            case 8:
                return "removeFromCRL";
            case 9:
                return "privilegeWithdrawn";
            case 10:
                return "aACompromise";
        }
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) {
        this.a = ((Integer) aSN1Object.getValue()).intValue();
    }

    public void setReasonCode(int i) {
        this.a = i;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return new ENUMERATED(this.a);
    }

    public String toString() {
        return getReasonCodeName();
    }
}

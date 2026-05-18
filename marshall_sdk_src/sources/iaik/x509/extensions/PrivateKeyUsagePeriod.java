package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public class PrivateKeyUsagePeriod extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_PrivateKeyUsagePeriod;
    private ChoiceOfTime a;
    private ChoiceOfTime b;

    public PrivateKeyUsagePeriod() {
        this.a = null;
        this.b = null;
    }

    public PrivateKeyUsagePeriod(Date date, Date date2) {
        this.a = null;
        this.b = null;
        if (date != null) {
            this.a = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
        }
        if (date2 != null) {
            this.b = new ChoiceOfTime(date2, ASN.GeneralizedTime, false);
        }
    }

    public Date getNotAfter() {
        return this.b.getDate();
    }

    public Date getNotBefore() {
        return this.a.getDate();
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            try {
                CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
                con_spec.forceImplicitlyTagged(ASN.GeneralizedTime);
                ASN1Object aSN1Object2 = (ASN1Object) con_spec.getValue();
                int tag = con_spec.getAsnType().getTag();
                if (tag == 0) {
                    this.a = new ChoiceOfTime(aSN1Object2);
                } else if (tag == 1) {
                    this.b = new ChoiceOfTime(aSN1Object2);
                }
            } catch (Exception e) {
                throw new X509ExtensionException(e.toString());
            }
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        if (this.a != null) {
            sequence.addComponent(new CON_SPEC(0, this.a.toASN1Object(), true));
        }
        if (this.b != null) {
            sequence.addComponent(new CON_SPEC(1, this.b.toASN1Object(), true));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("not before: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("not after : ");
        stringBuffer3.append(this.b);
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }
}

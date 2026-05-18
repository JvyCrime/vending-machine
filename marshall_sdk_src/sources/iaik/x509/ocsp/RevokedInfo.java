package iaik.x509.ocsp;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.x509.extensions.ReasonCode;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public class RevokedInfo {
    private ChoiceOfTime a;
    private ReasonCode b;

    public RevokedInfo(ASN1Object aSN1Object) throws CodingException {
        a(aSN1Object);
    }

    public RevokedInfo(ChoiceOfTime choiceOfTime) {
        if (!choiceOfTime.getEncodingType().equals(ASN.GeneralizedTime)) {
            throw new IllegalArgumentException("revocationTime must be ASN.1 GeneralizedTime!");
        }
        this.a = choiceOfTime;
    }

    public RevokedInfo(Date date) {
        this.a = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        ChoiceOfTime choiceOfTime = new ChoiceOfTime(aSN1Object.getComponentAt(0));
        this.a = choiceOfTime;
        if (choiceOfTime == null) {
            throw new CodingException("Cannot create RevokedInfo. Missing revocation time!");
        }
        if (aSN1Object.countComponents() == 2) {
            ReasonCode reasonCode = new ReasonCode();
            this.b = reasonCode;
            reasonCode.init((ASN1Object) aSN1Object.getComponentAt(1).getValue());
        }
    }

    public ReasonCode getRevocationReason() {
        return this.b;
    }

    public Date getRevocationTime() {
        ChoiceOfTime choiceOfTime = this.a;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    public void setRevocationReason(ReasonCode reasonCode) {
        this.b = reasonCode;
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a.toASN1Object());
        if (this.b != null) {
            sequence.addComponent(new CON_SPEC(0, this.b.toASN1Object()));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("revocationTime: ");
        stringBuffer2.append(this.a);
        stringBuffer.append(stringBuffer2.toString());
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append(", revocationReason: ");
            stringBuffer3.append(this.b);
            stringBuffer.append(stringBuffer3.toString());
        }
        return stringBuffer.toString();
    }
}

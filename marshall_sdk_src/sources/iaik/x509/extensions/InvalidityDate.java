package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public class InvalidityDate extends V3Extension {
    public static final ObjectID oid = ObjectID.crlExt_InvalidityDate;
    private ChoiceOfTime a;

    public InvalidityDate() {
    }

    public InvalidityDate(Date date) {
        setInvalidityDate(date);
    }

    public Date getInvalidityDate() {
        ChoiceOfTime choiceOfTime = this.a;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
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
        try {
            this.a = new ChoiceOfTime(aSN1Object);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public void setInvalidityDate(Date date) {
        this.a = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return this.a.toASN1Object();
    }

    public String toString() {
        if (this.a == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.a);
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

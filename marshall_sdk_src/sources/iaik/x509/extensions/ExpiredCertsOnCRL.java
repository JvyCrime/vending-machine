package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Date;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class ExpiredCertsOnCRL extends V3Extension {
    public static final ObjectID oid = ObjectID.crlExt_ExpiredCertsOnCRL;
    private ChoiceOfTime a;

    public ExpiredCertsOnCRL() {
    }

    public ExpiredCertsOnCRL(Date date) {
        setDate(date);
    }

    public Date getDate() {
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

    public void setDate(Date date) {
        Objects.requireNonNull(date, "date must not be null!");
        this.a = new ChoiceOfTime(date, ASN.GeneralizedTime);
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        ChoiceOfTime choiceOfTime = this.a;
        Objects.requireNonNull(choiceOfTime, "date must not be null!");
        return choiceOfTime.toASN1Object();
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

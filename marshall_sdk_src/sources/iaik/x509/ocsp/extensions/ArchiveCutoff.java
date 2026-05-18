package iaik.x509.ocsp.extensions;

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
public class ArchiveCutoff extends V3Extension {
    public static final ObjectID oid = ObjectID.ocspExt_ArchiveCutoff;
    private ChoiceOfTime a;

    public ArchiveCutoff() {
    }

    public ArchiveCutoff(Date date) {
        setCutoffTime(date);
    }

    public Date getCutoffTime() {
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
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public void setCutoffTime(Date date) {
        Objects.requireNonNull(date, "cutoffTime must not be null!");
        this.a = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return this.a.toASN1Object();
    }

    public String toString() {
        ChoiceOfTime choiceOfTime = this.a;
        return choiceOfTime != null ? choiceOfTime.toString() : "";
    }
}

package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.PolicyInformation;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class CertificatePolicies extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_CertificatePolicies;
    private PolicyInformation[] a;

    public CertificatePolicies() {
        this.a = null;
    }

    public CertificatePolicies(PolicyInformation[] policyInformationArr) {
        this.a = null;
        this.a = policyInformationArr;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public PolicyInformation[] getPolicyInformation() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        try {
            this.a = new PolicyInformation[aSN1Object.countComponents()];
            for (int i = 0; i < aSN1Object.countComponents(); i++) {
                this.a[i] = new PolicyInformation(aSN1Object.getComponentAt(i));
            }
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        Objects.requireNonNull(this.a, "No PolicyInformations set!");
        SEQUENCE sequence = new SEQUENCE();
        int i = 0;
        while (true) {
            PolicyInformation[] policyInformationArr = this.a;
            if (i >= policyInformationArr.length) {
                return sequence;
            }
            sequence.addComponent(policyInformationArr[i].toASN1Object());
            i++;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            for (int i = 0; i < this.a.length; i++) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("certificatePolicy[");
                stringBuffer2.append(i);
                stringBuffer2.append("]: ");
                stringBuffer2.append(this.a[i]);
                stringBuffer2.append("\n");
                stringBuffer.append(stringBuffer2.toString());
            }
            if (stringBuffer.length() >= 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }
}

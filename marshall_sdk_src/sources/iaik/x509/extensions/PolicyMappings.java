package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.PolicyMapping;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class PolicyMappings extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_PolicyMappings;
    Vector a = new Vector();

    public void addMapping(PolicyMapping policyMapping) {
        this.a.addElement(policyMapping);
    }

    public PolicyMapping[] getMappings() {
        PolicyMapping[] policyMappingArr = new PolicyMapping[this.a.size()];
        this.a.copyInto(policyMappingArr);
        return policyMappingArr;
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
                this.a.addElement(new PolicyMapping(aSN1Object.getComponentAt(i)));
            } catch (CodingException e) {
                throw new X509ExtensionException(e.toString());
            }
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        try {
            return ASN.createSequenceOf(getMappings());
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < this.a.size()) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Mapping ");
            int i2 = i + 1;
            stringBuffer2.append(i2);
            stringBuffer2.append(": ");
            stringBuffer.append(stringBuffer2.toString());
            stringBuffer.append(((PolicyMapping) this.a.elementAt(i)).getIssuerDomainPolicy().getName());
            stringBuffer.append(" = ");
            stringBuffer.append(((PolicyMapping) this.a.elementAt(i)).getSubjectDomainPolicy().getName());
            if (i < this.a.size() - 1) {
                stringBuffer.append("\n");
            }
            i = i2;
        }
        return stringBuffer.toString();
    }
}

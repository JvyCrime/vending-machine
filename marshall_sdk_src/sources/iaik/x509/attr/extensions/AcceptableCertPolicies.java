package iaik.x509.attr.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class AcceptableCertPolicies extends V3Extension {
    public static final ObjectID oid = ObjectID.attrCertExt_AcceptableCertPolicies;
    private Vector a = new Vector();

    public void addAcceptableCertPolicy(ObjectID objectID) {
        this.a.addElement(objectID);
    }

    public void addAcceptableCertPolicy(String str) {
        addAcceptableCertPolicy(new ObjectID(str));
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public ObjectID[] getPolicies() {
        ObjectID[] objectIDArr = new ObjectID[this.a.size()];
        this.a.copyInto(objectIDArr);
        return objectIDArr;
    }

    public String[] getPolicyOIDs() {
        ObjectID[] policies = getPolicies();
        String[] strArr = new String[policies.length];
        for (int i = 0; i < policies.length; i++) {
            strArr[i] = policies[i].getID();
        }
        return strArr;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        this.a = new Vector();
        try {
            int iCountComponents = aSN1Object.countComponents();
            for (int i = 0; i < iCountComponents; i++) {
                this.a.addElement(aSN1Object.getComponentAt(i));
            }
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public void removeAcceptablePolicy(ObjectID objectID) {
        this.a.removeElement(objectID);
    }

    public void removeAcceptablePolicy(String str) {
        removeAcceptablePolicy(new ObjectID(str));
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        try {
            return ASN.createSequenceOf(this.a);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration enumerationElements = this.a.elements();
        int i = 0;
        while (enumerationElements.hasMoreElements()) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("certPolicy[");
            stringBuffer2.append(i);
            stringBuffer2.append("]: ");
            stringBuffer2.append(enumerationElements.nextElement());
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
            i++;
        }
        if (stringBuffer.length() >= 1) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}

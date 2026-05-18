package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.DistributionPoint;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.net.ldap.LdapURLConnection;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public abstract class CRLDistPointsSyntax extends V3Extension {
    private Vector a;

    public CRLDistPointsSyntax() {
        this.a = new Vector();
    }

    public CRLDistPointsSyntax(DistributionPoint distributionPoint) {
        this();
        addDistributionPoint(distributionPoint);
    }

    public void addDistributionPoint(DistributionPoint distributionPoint) {
        if (this instanceof FreshestCRL) {
            distributionPoint.setLdapAttributeDescription(LdapURLConnection.AD_DELTA_REVOCATION_LIST);
        }
        this.a.addElement(distributionPoint);
    }

    public Enumeration getDistributionPoints() {
        return this.a.elements();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            try {
                DistributionPoint distributionPoint = new DistributionPoint(aSN1Object.getComponentAt(i));
                if (this instanceof FreshestCRL) {
                    distributionPoint.setLdapAttributeDescription(LdapURLConnection.AD_DELTA_REVOCATION_LIST);
                }
                this.a.addElement(distributionPoint);
            } catch (Exception e) {
                throw new X509ExtensionException(e.getMessage());
            }
        }
    }

    public void removeAllDistributionPoints() {
        this.a.removeAllElements();
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        try {
            SEQUENCE sequence = new SEQUENCE();
            for (int i = 0; i < this.a.size(); i++) {
                sequence.addComponent(((DistributionPoint) this.a.elementAt(i)).toASN1Object());
            }
            return sequence;
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.a.size(); i++) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.a.elementAt(i).toString());
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
            if (stringBuffer.length() >= 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }
}

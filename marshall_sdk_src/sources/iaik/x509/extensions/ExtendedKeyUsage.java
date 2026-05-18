package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class ExtendedKeyUsage extends V3Extension {
    private Vector a;
    public static final ObjectID oid = ObjectID.certExt_ExtendedKeyUsage;
    public static final ObjectID anyExtendedKeyUsage = new ObjectID("2.5.29.37.0", "Any extended key usage", null, false);
    public static final ObjectID serverAuth = new ObjectID("1.3.6.1.5.5.7.3.1", "TLS Web server authentication", null, false);
    public static final ObjectID clientAuth = new ObjectID("1.3.6.1.5.5.7.3.2", "TLS Web client authentication", null, false);
    public static final ObjectID codeSigning = new ObjectID("1.3.6.1.5.5.7.3.3", "Code signing", null, false);
    public static final ObjectID emailProtection = new ObjectID("1.3.6.1.5.5.7.3.4", "E-mail protection", null, false);
    public static final ObjectID ipsecEndSystem = new ObjectID("1.3.6.1.5.5.7.3.5", "IP security end system", null, false);
    public static final ObjectID ipsecTunnel = new ObjectID("1.3.6.1.5.5.7.3.6", "IP security tunel termination", null, false);
    public static final ObjectID ipsecUser = new ObjectID("1.3.6.1.5.5.7.3.7", "IP security user", null, false);
    public static final ObjectID timeStamping = new ObjectID("1.3.6.1.5.5.7.3.8", "Timestamping", null, false);
    public static final ObjectID ocspSigning = new ObjectID("1.3.6.1.5.5.7.3.9", "OCSPSigning", null, false);
    public static final ObjectID iKEIntermediate = new ObjectID("1.3.6.1.5.5.8.2.2", "iKEIntermediate", null, false);
    public static final ObjectID microsoftSGC = new ObjectID("1.3.6.1.4.1.311.10.3.3", "Microsoft Server Gated Cryptography", null, false);
    public static final ObjectID netscapeSGC = new ObjectID("2.16.840.1.113730.4.1", "Netscape Server Gated Cryptography", null, false);
    public static final ObjectID tslSigning = new ObjectID("0.4.0.2231.3.0", "tsl-signing", null, false);

    public ExtendedKeyUsage() {
        this.a = new Vector();
    }

    public ExtendedKeyUsage(ObjectID objectID) {
        this();
        addKeyPurposeID(objectID);
    }

    public ExtendedKeyUsage(ObjectID[] objectIDArr) {
        this();
        for (ObjectID objectID : objectIDArr) {
            addKeyPurposeID(objectID);
        }
    }

    public void addKeyPurposeID(ObjectID objectID) {
        if (this.a.contains(objectID)) {
            return;
        }
        this.a.addElement(objectID);
    }

    public boolean contains(ObjectID objectID) {
        return this.a.contains(objectID);
    }

    public ObjectID[] getKeyPurposeIDs() {
        ObjectID[] objectIDArr = new ObjectID[this.a.size()];
        this.a.copyInto(objectIDArr);
        return objectIDArr;
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
                this.a.addElement(aSN1Object.getComponentAt(i));
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error decoding extension: ");
                stringBuffer.append(e.getMessage());
                throw new X509ExtensionException(stringBuffer.toString());
            }
        }
    }

    public void removeAllKeyPurposeIDs() {
        this.a.removeAllElements();
    }

    public boolean removeKeyPurposeID(ObjectID objectID) {
        return this.a.removeElement(objectID);
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        SEQUENCE sequence = new SEQUENCE();
        for (int i = 0; i < this.a.size(); i++) {
            sequence.addComponent((ASN1Object) this.a.elementAt(i));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.a.size(); i++) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("KeyPurposeId ");
            stringBuffer2.append(i);
            stringBuffer2.append(":  ");
            stringBuffer2.append(((ObjectID) this.a.elementAt(i)).getName());
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (stringBuffer.length() >= 1) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}

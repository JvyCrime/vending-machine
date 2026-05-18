package iaik.x509.ocsp.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;

/* JADX INFO: loaded from: classes2.dex */
public class AcceptableResponses extends V3Extension {
    public static final ObjectID oid = ObjectID.ocspExt_AcceptableResponses;
    private ObjectID[] a;

    public AcceptableResponses() {
    }

    public AcceptableResponses(ObjectID[] objectIDArr) {
        this.a = objectIDArr;
    }

    public ObjectID[] getAcceptableResponseTypes() {
        return this.a;
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
            int iCountComponents = aSN1Object.countComponents();
            this.a = new ObjectID[iCountComponents];
            for (int i = 0; i < iCountComponents; i++) {
                this.a[i] = (ObjectID) aSN1Object.getComponentAt(i);
            }
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public boolean isResponseTypeAcceptable(ObjectID objectID) {
        if (this.a == null) {
            return true;
        }
        int i = 0;
        while (true) {
            ObjectID[] objectIDArr = this.a;
            if (i >= objectIDArr.length) {
                return false;
            }
            if (objectIDArr[i].equals(objectID)) {
                return true;
            }
            i++;
        }
    }

    public void setAcceptableResponseTypes(ObjectID[] objectIDArr) {
        this.a = objectIDArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        if (this.a != null) {
            int i = 0;
            while (true) {
                ObjectID[] objectIDArr = this.a;
                if (i >= objectIDArr.length) {
                    break;
                }
                sequence.addComponent(objectIDArr[i]);
                i++;
            }
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("acceptable responses: {");
        if (this.a != null) {
            for (int i = 0; i < this.a.length; i++) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("\n  ");
                stringBuffer2.append(this.a[i].getName());
                stringBuffer.append(stringBuffer2.toString());
            }
        }
        stringBuffer.append("\n}");
        return stringBuffer.toString();
    }
}

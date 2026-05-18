package iaik.x509.extensions.smime;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class SMIMECapabilities extends V3Extension {
    static Class a;
    public static final ObjectID oid = ObjectID.smimeCapabilities;
    private SMIMECapability[] b;

    public SMIMECapabilities() {
    }

    public SMIMECapabilities(SMIMECapability[] sMIMECapabilityArr) {
        this();
        this.b = sMIMECapabilityArr;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public SMIMECapability[] getCapabilities() {
        return this.b;
    }

    public SMIMECapability[] getCapabilities(ObjectID objectID) {
        if (this.b == null) {
            return null;
        }
        Vector vector = new Vector();
        int i = 0;
        while (true) {
            SMIMECapability[] sMIMECapabilityArr = this.b;
            if (i >= sMIMECapabilityArr.length) {
                break;
            }
            if (sMIMECapabilityArr[i].getCapabilityID().equals(objectID)) {
                vector.addElement(this.b[i]);
            }
            i++;
        }
        int size = vector.size();
        if (size <= 0) {
            return null;
        }
        SMIMECapability[] sMIMECapabilityArr2 = new SMIMECapability[size];
        vector.copyInto(sMIMECapabilityArr2);
        return sMIMECapabilityArr2;
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
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.x509.extensions.smime.SMIMECapability");
                a = clsClass$;
            }
            this.b = (SMIMECapability[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error parsing extension: ");
            stringBuffer.append(e.toString());
            throw new X509ExtensionException(stringBuffer.toString());
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        try {
            return ASN.createSequenceOf(this.b);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        String name;
        StringBuffer stringBuffer = new StringBuffer();
        SMIMECapability[] sMIMECapabilityArr = this.b;
        if (sMIMECapabilityArr == null || sMIMECapabilityArr.length <= 0) {
            stringBuffer.append("This SMIMECapabilities does not contain any capability");
        } else {
            int length = sMIMECapabilityArr.length;
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("This SMIMECapabilities contains ");
            stringBuffer2.append(length);
            stringBuffer2.append(" capabilit");
            stringBuffer2.append(length == 1 ? "y:\n" : "ies:\n");
            stringBuffer.append(stringBuffer2.toString());
            for (int i = 0; i < length; i++) {
                if (z) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("Capability No ");
                    stringBuffer3.append(i + 1);
                    stringBuffer3.append(":\n");
                    stringBuffer.append(stringBuffer3.toString());
                    StringBuffer stringBuffer4 = new StringBuffer();
                    stringBuffer4.append(this.b[i]);
                    stringBuffer4.append("\n");
                    name = stringBuffer4.toString();
                } else {
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    name = this.b[i].getCapabilityID().getName();
                }
                stringBuffer.append(name);
            }
        }
        return stringBuffer.toString();
    }
}

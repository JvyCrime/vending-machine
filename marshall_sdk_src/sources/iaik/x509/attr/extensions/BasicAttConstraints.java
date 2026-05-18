package iaik.x509.attr.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.BOOLEAN;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class BasicAttConstraints extends V3Extension {
    public static final ObjectID oid = ObjectID.attrCertExt_BasicAttConstraints;
    private boolean a;
    private int b = -1;

    public BasicAttConstraints() {
        this.a = false;
        this.a = false;
    }

    public boolean getAuthority() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public int getPathLenConstraint() {
        return this.b;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        try {
            this.a = ((Boolean) aSN1Object.getComponentAt(0).getValue()).booleanValue();
            if (aSN1Object.countComponents() == 2) {
                int iIntValue = ((BigInteger) aSN1Object.getComponentAt(1).getValue()).intValue();
                this.b = iIntValue;
                if (this.a && iIntValue < 0) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Error: negative pathLengthConstraint! pathLen:");
                    stringBuffer.append(this.b);
                    throw new CodingException(stringBuffer.toString());
                }
            }
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public void setAutority(boolean z) {
        this.a = z;
    }

    public void setPathlenConstraint(int i) {
        this.b = i;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new BOOLEAN(this.a));
        if (this.b >= 0) {
            sequence.addComponent(new INTEGER(this.b));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Authority: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("PathLenConsstrint: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        stringBuffer.setLength(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }
}

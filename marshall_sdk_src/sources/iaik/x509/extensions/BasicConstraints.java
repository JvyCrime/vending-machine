package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.BOOLEAN;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class BasicConstraints extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_BasicConstraints;
    private boolean a;
    private int b;

    public BasicConstraints() {
        this.a = false;
        this.b = -1;
    }

    public BasicConstraints(boolean z) {
        this.a = false;
        this.b = -1;
        this.a = z;
    }

    public BasicConstraints(boolean z, int i) {
        this.a = false;
        this.b = -1;
        this.a = z;
        this.b = i;
    }

    public boolean ca() {
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
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            try {
                ASN1Object componentAt = aSN1Object.getComponentAt(i);
                if (componentAt.isA(ASN.BOOLEAN)) {
                    this.a = ((Boolean) componentAt.getValue()).booleanValue();
                } else if (componentAt.isA(ASN.INTEGER)) {
                    this.b = ((BigInteger) componentAt.getValue()).intValue();
                }
            } catch (Exception e) {
                throw new X509ExtensionException(e.toString());
            }
        }
    }

    public void setCa(boolean z) {
        this.a = z;
    }

    public void setPathLenConstraint(int i) {
        this.b = i;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        if (this.a) {
            sequence.addComponent(new BOOLEAN(this.a));
        }
        if (this.b != -1) {
            sequence.addComponent(new INTEGER(this.b));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CA: ");
        stringBuffer.append(this.a ? "yes" : "no");
        if (this.b >= 0) {
            stringBuffer.append("\nPathLenConstraint: ");
            stringBuffer.append(new Integer(this.b).toString());
        }
        return stringBuffer.toString();
    }
}

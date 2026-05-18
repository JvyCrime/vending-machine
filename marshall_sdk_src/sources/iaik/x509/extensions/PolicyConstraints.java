package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class PolicyConstraints extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_PolicyConstraints;
    int a = -1;
    int b = -1;

    public int getInhibitExplicitPolicy() {
        return getInhibitPolicyMapping();
    }

    public int getInhibitPolicyMapping() {
        return this.b;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public int getRequireExplicitPolicy() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        try {
            if (!aSN1Object.isA(ASN.SEQUENCE)) {
                throw new X509ExtensionException("PolicyConstraints has to be a SEQUENCE!");
            }
            if (aSN1Object.countComponents() > 2) {
                throw new X509ExtensionException("PolicyConstraints cannot have more than 2 components!");
            }
            for (int i = 0; i < aSN1Object.countComponents(); i++) {
                CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
                if (con_spec.isImplicitlyTagged()) {
                    con_spec.forceImplicitlyTagged(ASN.INTEGER);
                }
                int tag = con_spec.getAsnType().getTag();
                if (tag == 0) {
                    this.a = ((BigInteger) ((ASN1Object) con_spec.getValue()).getValue()).intValue();
                } else if (tag == 1) {
                    this.b = ((BigInteger) ((ASN1Object) con_spec.getValue()).getValue()).intValue();
                }
            }
        } catch (Exception e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public void setInhibitExplicitPolicy(int i) {
        setInhibitPolicyMapping(i);
    }

    public void setInhibitPolicyMapping(int i) {
        this.b = i;
    }

    public void setRequireExplicitPolicy(int i) {
        this.a = i;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        if (this.a != -1) {
            sequence.addComponent(new CON_SPEC(0, new INTEGER(this.a), true));
        }
        if (this.b != -1) {
            sequence.addComponent(new CON_SPEC(1, new INTEGER(this.b), true));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != -1) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("require explicit policy: ");
            stringBuffer2.append(this.a);
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.b != -1) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("inhibit policy mapping: ");
            stringBuffer3.append(this.b);
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        if (stringBuffer.length() >= 1) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}

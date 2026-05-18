package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.GeneralSubtree;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;

/* JADX INFO: loaded from: classes2.dex */
public class NameConstraints extends V3Extension {
    static Class c;
    public static final ObjectID oid = ObjectID.certExt_NameConstraints;
    GeneralSubtree[] a;
    GeneralSubtree[] b;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public GeneralSubtree[] getExcludedSubtrees() {
        return this.b;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public GeneralSubtree[] getPermittedSubtrees() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        boolean z;
        try {
            if (!aSN1Object.isA(ASN.SEQUENCE)) {
                throw new X509ExtensionException("NameConstraints has to be a SEQUENCE!");
            }
            int iCountComponents = aSN1Object.countComponents();
            if (iCountComponents > 2) {
                throw new X509ExtensionException("NameConstraints cannot include more than two components!");
            }
            for (int i = 0; i < iCountComponents; i++) {
                CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
                if (con_spec.countComponents() > 1) {
                    con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                    z = true;
                } else {
                    z = false;
                }
                if (!z && !con_spec.getComponentAt(0).getComponentAt(0).isA(ASN.SEQUENCE)) {
                    con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                }
                int tag = con_spec.getAsnType().getTag();
                if (tag == 0) {
                    ASN1Object aSN1Object2 = (ASN1Object) con_spec.getValue();
                    Class clsClass$ = c;
                    if (clsClass$ == null) {
                        clsClass$ = class$("iaik.asn1.structures.GeneralSubtree");
                        c = clsClass$;
                    }
                    this.a = (GeneralSubtree[]) ASN.parseSequenceOf(aSN1Object2, clsClass$);
                } else if (tag == 1) {
                    ASN1Object aSN1Object3 = (ASN1Object) con_spec.getValue();
                    Class clsClass$2 = c;
                    if (clsClass$2 == null) {
                        clsClass$2 = class$("iaik.asn1.structures.GeneralSubtree");
                        c = clsClass$2;
                    }
                    this.b = (GeneralSubtree[]) ASN.parseSequenceOf(aSN1Object3, clsClass$2);
                }
            }
        } catch (Exception e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public void setExcludedSubtrees(GeneralSubtree[] generalSubtreeArr) {
        this.b = generalSubtreeArr;
    }

    public void setPermittedSubtrees(GeneralSubtree[] generalSubtreeArr) {
        this.a = generalSubtreeArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        SEQUENCE sequence = new SEQUENCE();
        try {
            GeneralSubtree[] generalSubtreeArr = this.a;
            if (generalSubtreeArr != null) {
                sequence.addComponent(new CON_SPEC(0, ASN.createSequenceOf(generalSubtreeArr), true));
            }
            GeneralSubtree[] generalSubtreeArr2 = this.b;
            if (generalSubtreeArr2 != null) {
                sequence.addComponent(new CON_SPEC(1, ASN.createSequenceOf(generalSubtreeArr2), true));
            }
            return sequence;
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        if (this.a != null) {
            stringBuffer.append("permitted subtrees:\n");
            int i2 = 0;
            while (i2 < this.a.length) {
                StringBuffer stringBuffer2 = new StringBuffer();
                int i3 = i2 + 1;
                stringBuffer2.append(i3);
                stringBuffer2.append(": ");
                stringBuffer.append(stringBuffer2.toString());
                stringBuffer.append(this.a[i2]);
                stringBuffer.append("\n");
                i2 = i3;
            }
        }
        if (this.b != null) {
            stringBuffer.append("excluded subtrees:\n");
            while (i < this.b.length) {
                StringBuffer stringBuffer3 = new StringBuffer();
                int i4 = i + 1;
                stringBuffer3.append(i4);
                stringBuffer3.append(": ");
                stringBuffer.append(stringBuffer3.toString());
                stringBuffer.append(this.b[i]);
                stringBuffer.append("\n");
                i = i4;
            }
        }
        if (stringBuffer.length() >= 1) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}

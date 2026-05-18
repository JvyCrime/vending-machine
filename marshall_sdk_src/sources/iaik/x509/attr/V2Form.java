package iaik.x509.attr;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.GeneralNames;
import iaik.asn1.structures.Name;
import iaik.utils.InternalErrorException;

/* JADX INFO: loaded from: classes2.dex */
public class V2Form implements AttCertIssuer {
    GeneralNames a;
    IssuerSerial b;
    ObjectDigestInfo c;

    public V2Form(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public V2Form(GeneralNames generalNames) {
        if (generalNames == null) {
            throw new IllegalArgumentException("Cannot create V2Form: Missing issuer name!");
        }
        this.a = generalNames;
    }

    public V2Form(GeneralNames generalNames, IssuerSerial issuerSerial, ObjectDigestInfo objectDigestInfo) {
        if (generalNames == null && issuerSerial == null && objectDigestInfo == null) {
            throw new IllegalArgumentException("All components of a V2Form are not allowed to be null!");
        }
        this.a = generalNames;
        this.b = issuerSerial;
        this.c = objectDigestInfo;
    }

    public V2Form(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create V2Form: Missing issuerName!");
        }
        this.a = new GeneralNames(new GeneralName(4, name));
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public void decode(ASN1Object aSN1Object) throws CodingException {
        int iCountComponents = aSN1Object.countComponents();
        if (iCountComponents > 3) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot decode V2Form: invalid number of components: ");
            stringBuffer.append(iCountComponents);
            throw new CodingException(stringBuffer.toString());
        }
        for (int i = 0; i < iCountComponents; i++) {
            ASN1Object componentAt = aSN1Object.getComponentAt(i);
            if (componentAt.isA(ASN.CON_SPEC)) {
                CON_SPEC con_spec = (CON_SPEC) componentAt;
                con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                int tag = con_spec.getAsnType().getTag();
                if (tag == 0) {
                    this.b = new IssuerSerial((ASN1Object) con_spec.getValue());
                } else {
                    if (tag != 1) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Cannot decode V2Form. Invalid tag: ");
                        stringBuffer2.append(tag);
                        throw new CodingException(stringBuffer2.toString());
                    }
                    this.c = new ObjectDigestInfo((ASN1Object) con_spec.getValue());
                }
            } else {
                this.a = new GeneralNames(componentAt);
            }
        }
        if (this.a == null && this.b == null && this.c == null) {
            throw new CodingException("All components of a V2Form are not allowed to be null!");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0035, code lost:
    
        if (r5.b == null) goto L30;
     */
    @Override // iaik.x509.attr.AttCertIssuer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r4 != r5) goto L4
            return r0
        L4:
            boolean r1 = r5 instanceof iaik.x509.attr.V2Form
            r2 = 0
            if (r1 != 0) goto La
            return r2
        La:
            iaik.x509.attr.V2Form r5 = (iaik.x509.attr.V2Form) r5
            iaik.asn1.structures.GeneralNames r1 = r4.a
            if (r1 == 0) goto L1b
            iaik.asn1.structures.GeneralNames r3 = r5.a
            if (r3 == 0) goto L1b
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L22
            return r2
        L1b:
            if (r1 != 0) goto L51
            iaik.asn1.structures.GeneralNames r1 = r5.a
            if (r1 == 0) goto L22
            goto L51
        L22:
            iaik.x509.attr.IssuerSerial r1 = r4.b
            if (r1 == 0) goto L31
            iaik.x509.attr.IssuerSerial r3 = r5.b
            if (r3 == 0) goto L31
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L38
            return r2
        L31:
            if (r1 != 0) goto L51
            iaik.x509.attr.IssuerSerial r1 = r5.b
            if (r1 == 0) goto L38
            goto L51
        L38:
            iaik.x509.attr.ObjectDigestInfo r1 = r4.c
            if (r1 == 0) goto L48
            iaik.x509.attr.ObjectDigestInfo r3 = r5.c
            if (r3 == 0) goto L48
            boolean r5 = r1.equals(r3)
            if (r5 != 0) goto L47
            return r2
        L47:
            return r0
        L48:
            if (r1 != 0) goto L4f
            iaik.x509.attr.ObjectDigestInfo r5 = r5.c
            if (r5 != 0) goto L4f
            goto L50
        L4f:
            r0 = 0
        L50:
            return r0
        L51:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.x509.attr.V2Form.equals(java.lang.Object):boolean");
    }

    public IssuerSerial getBaseCertificateID() {
        return this.b;
    }

    public Name getIssuerDN() {
        GeneralName[] names;
        GeneralNames generalNames = this.a;
        if (generalNames == null || (names = generalNames.getNames(4)) == null || names.length <= 0) {
            return null;
        }
        return (Name) names[0].getName();
    }

    public GeneralNames getIssuerName() {
        return this.a;
    }

    public ObjectDigestInfo getObjectDigestInfo() {
        return this.c;
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public int getVForm() {
        return 2;
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public int hashCode() {
        GeneralNames generalNames = this.a;
        int iHashCode = generalNames != null ? 0 + generalNames.hashCode() : 0;
        IssuerSerial issuerSerial = this.b;
        if (issuerSerial != null) {
            iHashCode += issuerSerial.hashCode();
        }
        ObjectDigestInfo objectDigestInfo = this.c;
        return objectDigestInfo != null ? iHashCode + objectDigestInfo.hashCode() : iHashCode;
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        GeneralNames generalNames = this.a;
        if (generalNames != null) {
            try {
                sequence.addComponent(generalNames.toASN1Object());
            } catch (CodingException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error when adding issuerName component: ");
                stringBuffer.append(e.getMessage());
                throw new InternalErrorException(stringBuffer.toString(), e);
            }
        }
        if (this.b != null) {
            sequence.addComponent(new CON_SPEC(0, this.b.toASN1Object(), true));
        }
        if (this.c != null) {
            sequence.addComponent(new CON_SPEC(1, this.c.toASN1Object(), true));
        }
        return sequence;
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public String toString() {
        boolean z;
        StringBuffer stringBuffer = new StringBuffer();
        boolean z2 = false;
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("issuerName: ");
            stringBuffer2.append(this.a);
            stringBuffer.append(stringBuffer2.toString());
            z = false;
        } else {
            z = true;
        }
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append(z ? "" : "\n");
            stringBuffer3.append("baseCertificateID: ");
            stringBuffer3.append(this.b);
            stringBuffer.append(stringBuffer3.toString());
        } else {
            z2 = z;
        }
        if (this.c != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append(z2 ? "" : "\n");
            stringBuffer4.append("objectDigestInfo: ");
            stringBuffer4.append(this.c);
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }
}

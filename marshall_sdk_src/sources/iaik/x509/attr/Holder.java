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
import iaik.x509.X509Certificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.extensions.SubjectAltName;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes2.dex */
public class Holder {
    IssuerSerial a;
    GeneralNames b;
    ObjectDigestInfo c;

    public Holder() {
    }

    public Holder(ASN1Object aSN1Object) throws CodingException {
        a(aSN1Object);
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        int iCountComponents;
        CON_SPEC con_spec;
        if (aSN1Object.getAsnType().equals(ASN.SEQUENCE)) {
            iCountComponents = aSN1Object.countComponents();
            if (iCountComponents > 3) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot decode Holder: invalid number of components: ");
                stringBuffer.append(iCountComponents);
                throw new CodingException(stringBuffer.toString());
            }
        } else {
            iCountComponents = 1;
        }
        for (int i = 0; i < iCountComponents; i++) {
            if (aSN1Object.getAsnType().equals(ASN.SEQUENCE)) {
                con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
                con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
            } else {
                con_spec = (CON_SPEC) aSN1Object;
            }
            int tag = con_spec.getAsnType().getTag();
            if (tag == 0) {
                this.a = new IssuerSerial((ASN1Object) con_spec.getValue());
            } else if (tag == 1) {
                this.b = new GeneralNames((ASN1Object) con_spec.getValue());
            } else {
                if (tag != 2) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Cannot decode Holder. Invalid tag: ");
                    stringBuffer2.append(tag);
                    throw new CodingException(stringBuffer2.toString());
                }
                this.c = new ObjectDigestInfo((ASN1Object) con_spec.getValue());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0035, code lost:
    
        if (r5.b == null) goto L30;
     */
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
            boolean r1 = r5 instanceof iaik.x509.attr.Holder
            r2 = 0
            if (r1 != 0) goto La
            return r2
        La:
            iaik.x509.attr.Holder r5 = (iaik.x509.attr.Holder) r5
            iaik.x509.attr.IssuerSerial r1 = r4.a
            if (r1 == 0) goto L1b
            iaik.x509.attr.IssuerSerial r3 = r5.a
            if (r3 == 0) goto L1b
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L22
            return r2
        L1b:
            if (r1 != 0) goto L51
            iaik.x509.attr.IssuerSerial r1 = r5.a
            if (r1 == 0) goto L22
            goto L51
        L22:
            iaik.asn1.structures.GeneralNames r1 = r4.b
            if (r1 == 0) goto L31
            iaik.asn1.structures.GeneralNames r3 = r5.b
            if (r3 == 0) goto L31
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L38
            return r2
        L31:
            if (r1 != 0) goto L51
            iaik.asn1.structures.GeneralNames r1 = r5.b
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
        throw new UnsupportedOperationException("Method not decompiled: iaik.x509.attr.Holder.equals(java.lang.Object):boolean");
    }

    public IssuerSerial getBaseCertificateID() {
        return this.a;
    }

    public GeneralNames getEntityName() {
        return this.b;
    }

    public ObjectDigestInfo getObjectDigestInfo() {
        return this.c;
    }

    public int hashCode() {
        IssuerSerial issuerSerial = this.a;
        int iHashCode = issuerSerial != null ? 0 + issuerSerial.hashCode() : 0;
        GeneralNames generalNames = this.b;
        if (generalNames != null) {
            iHashCode += generalNames.hashCode();
        }
        ObjectDigestInfo objectDigestInfo = this.c;
        return objectDigestInfo != null ? iHashCode + objectDigestInfo.hashCode() : iHashCode;
    }

    public boolean identifiesCert(X509Certificate x509Certificate) throws NoSuchAlgorithmException, CertificateEncodingException {
        boolean z;
        IssuerSerial issuerSerial = this.a;
        if (issuerSerial != null) {
            return issuerSerial.identifiesCert(x509Certificate);
        }
        if (this.b == null) {
            ObjectDigestInfo objectDigestInfo = this.c;
            if (objectDigestInfo != null) {
                return objectDigestInfo.identifiesCert(x509Certificate);
            }
            return false;
        }
        Name name = (Name) x509Certificate.getSubjectDN();
        if (name != null && !name.isEmpty()) {
            return new GeneralNames(new GeneralName(4, name)).equals(this.b);
        }
        try {
            SubjectAltName subjectAltName = (SubjectAltName) x509Certificate.getExtension(SubjectAltName.oid);
            if (subjectAltName != null) {
                GeneralNames generalNames = subjectAltName.getGeneralNames();
                if (this.b.equals(generalNames)) {
                    return true;
                }
                Enumeration names = this.b.getNames();
                while (names.hasMoreElements()) {
                    GeneralName generalName = (GeneralName) names.nextElement();
                    Enumeration names2 = generalNames.getNames();
                    while (true) {
                        if (!names2.hasMoreElements()) {
                            z = false;
                            break;
                        }
                        if (generalName.equals(names2.nextElement())) {
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        break;
                    }
                }
            }
        } catch (X509ExtensionException unused) {
        }
        return false;
    }

    public void setBaseCertificateID(X509Certificate x509Certificate) {
        this.a = new IssuerSerial(x509Certificate);
    }

    public void setBaseCertificateID(IssuerSerial issuerSerial) {
        this.a = issuerSerial;
    }

    public void setEntityName(GeneralNames generalNames) {
        this.b = generalNames;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setEntityName(iaik.x509.X509Certificate r5) throws java.lang.IllegalArgumentException {
        /*
            r4 = this;
            java.security.Principal r0 = r5.getSubjectDN()
            iaik.asn1.structures.Name r0 = (iaik.asn1.structures.Name) r0
            boolean r1 = r0.isEmpty()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L1c
            iaik.asn1.structures.GeneralName r5 = new iaik.asn1.structures.GeneralName
            r1 = 4
            r5.<init>(r1, r0)
            iaik.asn1.structures.GeneralNames r0 = new iaik.asn1.structures.GeneralNames
            r0.<init>(r5)
            r4.b = r0
            goto L38
        L1c:
            iaik.asn1.ObjectID r0 = iaik.x509.extensions.SubjectAltName.oid     // Catch: java.lang.Exception -> L37
            iaik.x509.V3Extension r5 = r5.getExtension(r0)     // Catch: java.lang.Exception -> L37
            iaik.x509.extensions.SubjectAltName r5 = (iaik.x509.extensions.SubjectAltName) r5     // Catch: java.lang.Exception -> L37
            if (r5 == 0) goto L37
            iaik.asn1.structures.GeneralNames r5 = r5.getGeneralNames()     // Catch: java.lang.Exception -> L37
            java.util.Enumeration r0 = r5.getNames()     // Catch: java.lang.Exception -> L37
            boolean r0 = r0.hasMoreElements()     // Catch: java.lang.Exception -> L37
            if (r0 == 0) goto L37
            r4.b = r5     // Catch: java.lang.Exception -> L37
            goto L38
        L37:
            r2 = 0
        L38:
            if (r2 == 0) goto L3b
            return
        L3b:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Cannot create entityName. Certificate has empty subjectDN field and no SubjectAltName extension."
            r5.<init>(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.x509.attr.Holder.setEntityName(iaik.x509.X509Certificate):void");
    }

    public void setObjectDigestInfo(ObjectDigestInfo objectDigestInfo) {
        this.c = objectDigestInfo;
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        if (this.a != null) {
            sequence.addComponent(new CON_SPEC(0, this.a.toASN1Object(), true));
        }
        if (this.b != null) {
            try {
                sequence.addComponent(new CON_SPEC(1, this.b.toASN1Object(), true));
            } catch (CodingException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error when adding entityName component: ");
                stringBuffer.append(e.getMessage());
                throw new InternalErrorException(stringBuffer.toString(), e);
            }
        }
        if (this.c != null) {
            sequence.addComponent(new CON_SPEC(2, this.c.toASN1Object(), true));
        }
        return sequence;
    }

    public String toString() {
        boolean z;
        StringBuffer stringBuffer = new StringBuffer();
        boolean z2 = false;
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("baseCertificateID: ");
            stringBuffer2.append(this.a);
            stringBuffer.append(stringBuffer2.toString());
            z = false;
        } else {
            z = true;
        }
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append(z ? "" : "\n");
            stringBuffer3.append("entityName: ");
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

package iaik.x509.ocsp.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.CryptoUtils;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.extensions.smime.SMIMECapability;

/* JADX INFO: loaded from: classes2.dex */
public class PreferredSignatureAlgorithms extends V3Extension {
    public static final ObjectID oid = ObjectID.ocspExt_PreferredSignatureAlgorithms;
    private PreferredSignatureAlgorithm[] a;

    public static final class PreferredSignatureAlgorithm {
        private AlgorithmID a;
        private SMIMECapability b;

        public PreferredSignatureAlgorithm(ASN1Object aSN1Object) throws CodingException {
            decode(aSN1Object);
        }

        public PreferredSignatureAlgorithm(AlgorithmID algorithmID) {
            if (algorithmID == null) {
                throw new IllegalArgumentException("sigIdentifier must not be null!");
            }
            this.a = algorithmID;
        }

        public void decode(ASN1Object aSN1Object) throws CodingException {
            if (!aSN1Object.isA(ASN.SEQUENCE)) {
                throw new CodingException("PrefferedSignatureAlgorithm must be ASN.1 SEQUENCE!");
            }
            int iCountComponents = aSN1Object.countComponents();
            if (iCountComponents < 1 || iCountComponents > 2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid number of components (");
                stringBuffer.append(iCountComponents);
                stringBuffer.append(")! Only 1 or 2 allowed.");
                throw new CodingException(stringBuffer.toString());
            }
            this.a = new AlgorithmID(aSN1Object.getComponentAt(0));
            if (iCountComponents == 2) {
                this.b = new SMIMECapability(aSN1Object.getComponentAt(1));
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PreferredSignatureAlgorithm)) {
                return false;
            }
            try {
                return CryptoUtils.equalsBlock(DerCoder.encode(toASN1Object()), DerCoder.encode(((PreferredSignatureAlgorithm) obj).toASN1Object()));
            } catch (Exception unused) {
                return false;
            }
        }

        public SMIMECapability getPubKeyAlgIdentifier() {
            return this.b;
        }

        public AlgorithmID getSigIdentifier() {
            return this.a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public void setPubKeyAlgIdentifier(SMIMECapability sMIMECapability) {
            if (sMIMECapability == null) {
                throw new IllegalArgumentException("pubKeyAlgIdentifier must not be null!");
            }
            this.b = sMIMECapability;
        }

        public ASN1Object toASN1Object() throws CodingException {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(this.a.toASN1Object());
            SMIMECapability sMIMECapability = this.b;
            if (sMIMECapability != null) {
                sequence.addComponent(sMIMECapability.toASN1Object());
            }
            return sequence;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("sigIdentifier: ");
            stringBuffer2.append(this.a);
            stringBuffer.append(stringBuffer2.toString());
            if (this.b != null) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("pubKeyIdentifier: ");
                stringBuffer3.append(this.b);
                stringBuffer.append(stringBuffer3.toString());
            }
            return stringBuffer.toString();
        }
    }

    public PreferredSignatureAlgorithms() {
    }

    public PreferredSignatureAlgorithms(PreferredSignatureAlgorithm[] preferredSignatureAlgorithmArr) {
        if (preferredSignatureAlgorithmArr == null) {
            throw new IllegalArgumentException("algorithms must not be null!");
        }
        if (preferredSignatureAlgorithmArr.length == 0) {
            throw new IllegalArgumentException("algorithms must not be empty!");
        }
        this.a = preferredSignatureAlgorithmArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreferredSignatureAlgorithms)) {
            return false;
        }
        try {
            return CryptoUtils.equalsBlock(DerCoder.encode(toASN1Object()), DerCoder.encode(((PreferredSignatureAlgorithms) obj).toASN1Object()));
        } catch (Exception unused) {
            return false;
        }
    }

    public PreferredSignatureAlgorithm[] getAlgorithms() {
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
        if (!aSN1Object.getAsnType().equals(ASN.SEQUENCE)) {
            throw new X509ExtensionException("Invalid ASN.1 representation. Expected ASN.1 SEQUENCE!");
        }
        try {
            int iCountComponents = aSN1Object.countComponents();
            this.a = new PreferredSignatureAlgorithm[iCountComponents];
            for (int i = 0; i < iCountComponents; i++) {
                this.a[i] = new PreferredSignatureAlgorithm(aSN1Object.getComponentAt(i));
            }
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error parsing extension: ");
            stringBuffer.append(e.toString());
            throw new X509ExtensionException(stringBuffer.toString());
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        SEQUENCE sequence = new SEQUENCE();
        int i = 0;
        while (true) {
            try {
                PreferredSignatureAlgorithm[] preferredSignatureAlgorithmArr = this.a;
                if (i >= preferredSignatureAlgorithmArr.length) {
                    return sequence;
                }
                sequence.addComponent(preferredSignatureAlgorithmArr[i].toASN1Object());
                i++;
            } catch (CodingException e) {
                throw new X509ExtensionException(e.toString());
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            stringBuffer.append("Preferred signature algorithms:\n");
            for (int i = 0; i < this.a.length; i++) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("{ \n");
                stringBuffer2.append(this.a[i]);
                stringBuffer2.append("\n}");
                stringBuffer.append(stringBuffer2.toString());
            }
        }
        return stringBuffer.toString();
    }
}

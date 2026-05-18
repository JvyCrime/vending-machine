package iaik.x509.attr;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.GeneralName;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class TargetCert extends Target {
    private IssuerSerial a;
    private GeneralName b;
    private ObjectDigestInfo c;

    TargetCert() {
    }

    public TargetCert(ASN1Object aSN1Object) throws CodingException {
        super(aSN1Object);
    }

    public TargetCert(IssuerSerial issuerSerial) {
        Objects.requireNonNull(issuerSerial, "Target certificate issuer and serial number must not be null.");
        this.a = issuerSerial;
    }

    @Override // iaik.x509.attr.Target
    public void decodeUnTaggedASN1Object(ASN1Object aSN1Object) throws CodingException {
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new CodingException("Invalid ASN.1 target cert. Expected SEQUENCE!");
        }
        int iCountComponents = aSN1Object.countComponents();
        if (iCountComponents < 1 || iCountComponents > 3) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid number (");
            stringBuffer.append(iCountComponents);
            stringBuffer.append(") of components in ASN.1 target certificate. Expected 1, 2 od 3!");
            throw new CodingException(stringBuffer.toString());
        }
        this.a = new IssuerSerial(aSN1Object.getComponentAt(0));
        for (int i = 1; i < iCountComponents; i++) {
            ASN1Object componentAt = aSN1Object.getComponentAt(i);
            if (componentAt.isA(ASN.SEQUENCE)) {
                this.c = new ObjectDigestInfo(componentAt);
            } else {
                this.b = new GeneralName(componentAt);
            }
        }
    }

    @Override // iaik.x509.attr.Target
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TargetCert)) {
            return false;
        }
        try {
            return CryptoUtils.equalsBlock(DerCoder.encode(toASN1Object()), DerCoder.encode(((TargetCert) obj).toASN1Object()));
        } catch (CodingException unused) {
            return false;
        }
    }

    public ObjectDigestInfo getCertDigestInfo() {
        return this.c;
    }

    public IssuerSerial getTargetCertificate() {
        return this.a;
    }

    public GeneralName getTargetName() {
        return this.b;
    }

    @Override // iaik.x509.attr.Target
    public int getType() {
        return 2;
    }

    @Override // iaik.x509.attr.Target
    protected String getTypeAsString() {
        return "TargetCert";
    }

    @Override // iaik.x509.attr.Target
    public int hashCode() {
        return super.hashCode() + this.a.hashCode();
    }

    public void setCertDigestInfo(ObjectDigestInfo objectDigestInfo) {
        this.c = objectDigestInfo;
    }

    public void setTargetName(GeneralName generalName) {
        this.b = generalName;
    }

    @Override // iaik.x509.attr.Target
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(": {\n  targetCertificate: {\n");
        Util.printIndented(this.a.toString(), true, "    ", stringBuffer);
        stringBuffer.append("\n  }");
        if (this.b != null) {
            stringBuffer.append("\n  targetName: {\n");
            Util.printIndented(this.b.toString(), true, "    ", stringBuffer);
            stringBuffer.append(":\n  }");
        }
        if (this.c != null) {
            stringBuffer.append("\n  certDigestInfo: {\n");
            Util.printIndented(this.c.toString(), true, "    ", stringBuffer);
            stringBuffer.append(":\n  }");
        }
        stringBuffer.append("\n}");
        return stringBuffer.toString();
    }

    @Override // iaik.x509.attr.Target
    public ASN1Object toUnTaggedASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a.toASN1Object());
        GeneralName generalName = this.b;
        if (generalName != null) {
            sequence.addComponent(generalName.toASN1Object());
        }
        ObjectDigestInfo objectDigestInfo = this.c;
        if (objectDigestInfo != null) {
            sequence.addComponent(objectDigestInfo.toASN1Object());
        }
        return sequence;
    }
}

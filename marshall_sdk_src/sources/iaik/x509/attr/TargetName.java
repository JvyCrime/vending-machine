package iaik.x509.attr;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.structures.GeneralName;
import iaik.utils.Util;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class TargetName extends Target {
    private GeneralName a;

    TargetName() {
    }

    public TargetName(ASN1Object aSN1Object) throws CodingException {
        super(aSN1Object);
    }

    public TargetName(GeneralName generalName) {
        Objects.requireNonNull(generalName, "Target name must not be null.");
        this.a = generalName;
    }

    @Override // iaik.x509.attr.Target
    public void decodeUnTaggedASN1Object(ASN1Object aSN1Object) throws CodingException {
        this.a = new GeneralName(aSN1Object);
    }

    @Override // iaik.x509.attr.Target
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TargetName) {
            return this.a.equals(((TargetName) obj).getName());
        }
        return false;
    }

    public GeneralName getName() {
        return this.a;
    }

    @Override // iaik.x509.attr.Target
    public int getType() {
        return 0;
    }

    @Override // iaik.x509.attr.Target
    protected String getTypeAsString() {
        return "TargetName";
    }

    @Override // iaik.x509.attr.Target
    public int hashCode() {
        return super.hashCode() + this.a.hashCode();
    }

    @Override // iaik.x509.attr.Target
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(": {\n");
        Util.printIndented(this.a.toString(), true, "  ", stringBuffer);
        stringBuffer.append("\n}");
        return stringBuffer.toString();
    }

    @Override // iaik.x509.attr.Target
    public ASN1Object toUnTaggedASN1Object() throws CodingException {
        return this.a.toASN1Object();
    }
}

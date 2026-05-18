package iaik.x509.attr.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class AuditIdentity extends V3Extension {
    public static final ObjectID oid = ObjectID.attrCertExt_AuditIdentity;
    private byte[] a;

    public AuditIdentity() {
    }

    public AuditIdentity(byte[] bArr) {
        setValue(bArr);
    }

    private static final void a(byte[] bArr) {
        Objects.requireNonNull(bArr, "AuditIdentity value must not be null.");
        if (bArr.length == 0) {
            throw new IllegalArgumentException("AuditIdentity value must not be empty.");
        }
        if (bArr.length > 20) {
            throw new IllegalArgumentException("AuditIdentity value must not be longer than 20 bytes.");
        }
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public byte[] getValue() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        if (aSN1Object.isA(ASN.OCTET_STRING)) {
            this.a = (byte[]) aSN1Object.getValue();
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid value (");
        stringBuffer.append(aSN1Object.getAsnType().getName());
        stringBuffer.append(") of AuditIdentity extension; must be an OCTET STRING!");
        throw new X509ExtensionException(stringBuffer.toString());
    }

    public void setValue(byte[] bArr) {
        a(bArr);
        this.a = bArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        a(this.a);
        return new OCTET_STRING(this.a);
    }

    public String toString() {
        byte[] bArr = this.a;
        return bArr != null ? Util.toString(bArr) : "";
    }
}

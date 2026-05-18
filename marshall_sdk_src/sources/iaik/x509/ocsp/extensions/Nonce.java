package iaik.x509.ocsp.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.utils.Util;
import iaik.x509.V3Extension;

/* JADX INFO: loaded from: classes2.dex */
public class Nonce extends V3Extension {
    private byte[] b;
    public static final ObjectID oid = ObjectID.ocspExt_Nonce;
    private static boolean a = true;

    public Nonce() {
    }

    public Nonce(byte[] bArr) {
        this.b = bArr;
    }

    public static final boolean getWrapNonceValue() {
        return a;
    }

    public static final void setWrapNonceValue(boolean z) {
        a = z;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public byte[] getValue() {
        return this.b;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) {
        this.b = (byte[]) aSN1Object.getValue();
    }

    public void setValue(byte[] bArr) {
        this.b = bArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return new OCTET_STRING(this.b);
    }

    public String toString() {
        byte[] bArr = this.b;
        return bArr != null ? Util.toString(bArr) : "";
    }
}

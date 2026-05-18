package iaik.x509.extensions.netscape;

import iaik.asn1.ASN1Object;
import iaik.asn1.IA5String;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;

/* JADX INFO: loaded from: classes2.dex */
public class NetscapeCaRevocationUrl extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_NetscapeCaRevocationUrl;
    private String a;

    public NetscapeCaRevocationUrl() {
    }

    public NetscapeCaRevocationUrl(String str) {
        this.a = str;
    }

    public String getCaRevocationUrl() {
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
    public void init(ASN1Object aSN1Object) {
        this.a = (String) aSN1Object.getValue();
    }

    public void setCaRevocationUrl(String str) {
        this.a = str;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return new IA5String(this.a);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("NetscapeCaRevocationUrl: ");
        stringBuffer.append(this.a);
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

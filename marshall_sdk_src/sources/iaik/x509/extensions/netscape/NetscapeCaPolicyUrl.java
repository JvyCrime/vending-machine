package iaik.x509.extensions.netscape;

import iaik.asn1.ASN1Object;
import iaik.asn1.IA5String;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;

/* JADX INFO: loaded from: classes2.dex */
public class NetscapeCaPolicyUrl extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_NetscapeCaPolicyUrl;
    private String a;

    public NetscapeCaPolicyUrl() {
    }

    public NetscapeCaPolicyUrl(String str) {
        this.a = str;
    }

    public String getCaPolicyUrl() {
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

    public void setCaPolicyUrl(String str) {
        this.a = str;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return new IA5String(this.a);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("NetscapeCaPolicyUrl: ");
        stringBuffer.append(this.a);
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

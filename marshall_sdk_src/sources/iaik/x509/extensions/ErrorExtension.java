package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;

/* JADX INFO: loaded from: classes2.dex */
public class ErrorExtension extends V3Extension {
    ObjectID a;
    String b;

    public ErrorExtension(ObjectID objectID, boolean z, String str) {
        this.a = objectID;
        this.critical = z;
        this.b = str;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) {
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        throw new X509ExtensionException("ErrorExtension only for displaying broken extensions.");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("This extension could not be initialized!");
        if (this.b != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("\n");
            stringBuffer2.append(this.b);
            stringBuffer.append(stringBuffer2.toString());
        }
        return stringBuffer.toString();
    }
}

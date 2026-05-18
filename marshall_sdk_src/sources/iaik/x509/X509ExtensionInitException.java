package iaik.x509;

import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public class X509ExtensionInitException extends X509ExtensionException {
    private static final long serialVersionUID = 3107976728054134790L;
    ObjectID a;
    boolean b;

    public X509ExtensionInitException(ObjectID objectID, boolean z, String str) {
        super(str);
        this.a = objectID;
        this.b = z;
    }

    public ObjectID getExtensionID() {
        return this.a;
    }

    public boolean isCriticalExtension() {
        return this.b;
    }
}

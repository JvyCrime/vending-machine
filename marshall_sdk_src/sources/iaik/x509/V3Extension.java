package iaik.x509;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public abstract class V3Extension {
    protected boolean critical = false;

    public String getName() {
        return getObjectID().getName();
    }

    public abstract ObjectID getObjectID();

    public abstract int hashCode();

    public abstract void init(ASN1Object aSN1Object) throws X509ExtensionException;

    public boolean isCritical() {
        return this.critical;
    }

    public V3Extension setCritical(boolean z) {
        this.critical = z;
        return this;
    }

    public abstract ASN1Object toASN1Object() throws X509ExtensionException;
}

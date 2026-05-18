package iaik.asn1.structures;

import iaik.asn1.ASN1Type;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes.dex */
public abstract class AttributeValue implements ASN1Type {
    public abstract ObjectID getAttributeType();

    public String getName() {
        return getAttributeType().getName();
    }

    public boolean multipleAllowed() {
        return true;
    }

    public abstract String toString();
}

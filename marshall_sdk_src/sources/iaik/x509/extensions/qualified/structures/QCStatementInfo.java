package iaik.x509.extensions.qualified.structures;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public abstract class QCStatementInfo {
    public abstract void decode(ASN1Object aSN1Object) throws CodingException;

    public String getName() {
        return getStatementID().getName();
    }

    public abstract ObjectID getStatementID();

    public abstract ASN1Object toASN1Object() throws CodingException;

    public abstract String toString();
}

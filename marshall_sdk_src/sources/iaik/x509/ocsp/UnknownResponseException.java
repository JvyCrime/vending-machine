package iaik.x509.ocsp;

import iaik.asn1.ASN1;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public class UnknownResponseException extends OCSPException {
    private static final long serialVersionUID = 3288927483195345892L;
    ObjectID a;
    ASN1 b;

    public UnknownResponseException(ObjectID objectID, ASN1 asn1) {
        this.a = objectID;
        this.b = asn1;
    }

    public UnknownResponseException(String str, ObjectID objectID, ASN1 asn1) {
        super(str);
        this.a = objectID;
        this.b = asn1;
    }

    public ObjectID getResponseType() {
        return this.a;
    }

    public ASN1 getUnknownResponse() {
        return this.b;
    }
}

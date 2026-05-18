package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.pkcs.PKCSParsingException;

/* JADX INFO: loaded from: classes.dex */
public interface Content extends ContentStream {
    void decode(ASN1Object aSN1Object) throws PKCSParsingException;
}

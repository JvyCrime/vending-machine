package iaik.asn1;

/* JADX INFO: loaded from: classes.dex */
public interface ASN1Type {
    void decode(ASN1Object aSN1Object) throws CodingException;

    ASN1Object toASN1Object() throws CodingException;
}

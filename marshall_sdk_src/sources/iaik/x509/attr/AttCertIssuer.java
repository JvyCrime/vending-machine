package iaik.x509.attr;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;

/* JADX INFO: loaded from: classes2.dex */
public interface AttCertIssuer {
    public static final int V1_FORM = 1;
    public static final int V2_FORM = 2;

    void decode(ASN1Object aSN1Object) throws CodingException;

    boolean equals(Object obj);

    int getVForm();

    int hashCode();

    ASN1Object toASN1Object();

    String toString();
}

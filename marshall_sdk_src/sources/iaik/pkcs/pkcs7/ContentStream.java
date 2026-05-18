package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public interface ContentStream {
    void decode(InputStream inputStream) throws PKCSParsingException, IOException;

    int getBlockSize();

    ObjectID getContentType();

    void setBlockSize(int i);

    ASN1Object toASN1Object() throws PKCSException;

    String toString(boolean z);
}

package iaik.asn1;

import java.util.EventListener;

/* JADX INFO: loaded from: classes.dex */
public interface EncodeListener extends EventListener {
    void encodeCalled(ASN1Object aSN1Object, int i) throws CodingException;
}

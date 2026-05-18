package iaik.pkcs.pkcs7;

import iaik.asn1.structures.AlgorithmID;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
interface a {
    byte[] getMessageDigest(AlgorithmID algorithmID) throws NoSuchAlgorithmException;
}

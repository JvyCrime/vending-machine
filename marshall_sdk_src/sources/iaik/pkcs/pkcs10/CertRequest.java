package iaik.pkcs.pkcs10;

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.SignatureException;

/* JADX INFO: loaded from: classes.dex */
public interface CertRequest {
    PublicKey getPublicKey() throws InvalidKeyException;

    boolean verify() throws SignatureException;
}

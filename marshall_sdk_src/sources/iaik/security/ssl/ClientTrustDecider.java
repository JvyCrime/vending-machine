package iaik.security.ssl;

import java.security.Principal;
import java.security.PrivateKey;

/* JADX INFO: loaded from: classes.dex */
public interface ClientTrustDecider extends TrustDecider {
    public static final byte dss_ephemeral_dh = 6;
    public static final byte dss_fixed_dh = 4;
    public static final byte dss_sign = 2;
    public static final byte fortezza_dms = 20;
    public static final byte rsa_ephemeral_dh = 5;
    public static final byte rsa_fixed_dh = 3;
    public static final byte rsa_sign = 1;

    SSLCertificate getCertificate(byte[] bArr, Principal[] principalArr, String str);

    PrivateKey getPrivateKey();
}

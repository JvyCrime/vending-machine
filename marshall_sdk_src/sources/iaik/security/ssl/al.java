package iaik.security.ssl;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

/* JADX INFO: loaded from: classes.dex */
class al extends Signature {
    private MessageDigest a;
    private MessageDigest b;
    private Key c;
    private SecureRandom d;

    al() throws Exception {
        this(null);
    }

    al(SecureRandom secureRandom) throws Exception {
        super("SSL/RSA Signature");
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = secureRandom;
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        this.a = securityProvider.getMessageDigest(SecurityProvider.ALG_DIGEST_MD5);
        this.b = securityProvider.getMessageDigest("SHA");
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) {
        this.c = publicKey;
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) {
        this.c = privateKey;
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        try {
            return SecurityProvider.getSecurityProvider().calculateRawSignature(SecurityProvider.ALG_CIPHER_RSA_SIGN, Utils.a(this.a.digest(), this.b.digest()), (PrivateKey) this.c, this.d);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error RSA encrypting digest: ");
            stringBuffer.append(e.toString());
            throw new SignatureException(stringBuffer.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        byte[] bArr2 = new byte[36];
        System.arraycopy(this.a.digest(), 0, bArr2, 0, 16);
        System.arraycopy(this.b.digest(), 0, bArr2, 16, 20);
        try {
            return SecurityProvider.getSecurityProvider().verifyRawSignature(SecurityProvider.ALG_CIPHER_RSA_VERIFY, bArr2, bArr, (PublicKey) this.c);
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) {
        this.a.update(b);
        this.b.update(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
        this.b.update(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) {
        throw new RuntimeException("Method not supported!");
    }

    @Override // java.security.SignatureSpi
    protected Object engineGetParameter(String str) {
        throw new RuntimeException("Method not supported!");
    }
}

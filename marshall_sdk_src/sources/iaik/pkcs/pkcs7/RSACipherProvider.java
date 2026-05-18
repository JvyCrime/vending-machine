package iaik.pkcs.pkcs7;

/* JADX INFO: loaded from: classes.dex */
public class RSACipherProvider {
    public static final int DECRYPT_MODE = 2;
    public static final int ENCRYPT_MODE = 1;
    private static RSACipherProvider a;
    protected String cipherDecryptProvider_;
    protected String cipherEncryptProvider_;

    public RSACipherProvider() {
        this.cipherEncryptProvider_ = null;
        this.cipherDecryptProvider_ = null;
    }

    public RSACipherProvider(String str, String str2) {
        this.cipherEncryptProvider_ = str;
        this.cipherDecryptProvider_ = str2;
    }

    public static RSACipherProvider getDefault() {
        if (a == null) {
            a = new RSACipherProvider();
        }
        return a;
    }

    public static void setDefault(RSACipherProvider rSACipherProvider) {
        a = rSACipherProvider;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0009  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x000e A[PHI: r1
  0x000e: PHI (r1v3 java.lang.String) = (r1v2 java.lang.String), (r1v4 java.lang.String) binds: [B:11:0x0018, B:5:0x0007] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected byte[] cipher(int r3, java.security.Key r4, byte[] r5) throws java.security.GeneralSecurityException {
        /*
            r2 = this;
            java.lang.String r0 = "RSA/ECB/PKCS1Padding"
            r1 = 1
            if (r3 != r1) goto L13
            java.lang.String r1 = r2.cipherEncryptProvider_
            if (r1 != 0) goto Le
        L9:
            javax.crypto.Cipher r0 = javax.crypto.Cipher.getInstance(r0)
            goto L1b
        Le:
            javax.crypto.Cipher r0 = javax.crypto.Cipher.getInstance(r0, r1)
            goto L1b
        L13:
            r1 = 2
            if (r3 != r1) goto L23
            java.lang.String r1 = r2.cipherDecryptProvider_
            if (r1 != 0) goto Le
            goto L9
        L1b:
            r0.init(r3, r4)
            byte[] r3 = r0.doFinal(r5)
            return r3
        L23:
            java.security.NoSuchAlgorithmException r4 = new java.security.NoSuchAlgorithmException
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            r5.<init>()
            java.lang.String r0 = "Illegal mode for RSA cipher algorithm: "
            r5.append(r0)
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            r4.<init>(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.pkcs.pkcs7.RSACipherProvider.cipher(int, java.security.Key, byte[]):byte[]");
    }

    public void setCipherProvider(int i, String str) throws IllegalArgumentException {
        if (i == 1) {
            this.cipherEncryptProvider_ = str;
        } else {
            if (i == 1) {
                this.cipherDecryptProvider_ = str;
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal cipher mode: ");
            stringBuffer.append(i);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }
}

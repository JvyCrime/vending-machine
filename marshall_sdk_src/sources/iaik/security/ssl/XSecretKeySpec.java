package iaik.security.ssl;

import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
class XSecretKeySpec extends SecretKeySpec {
    private static final byte[] a = new byte[8];
    private static final byte[] b = new byte[0];
    private boolean c;

    public XSecretKeySpec(byte[] bArr, String str) {
        super(a(bArr), str);
        this.c = bArr.length == 0;
    }

    private static byte[] a(byte[] bArr) {
        return bArr.length == 0 ? a : bArr;
    }

    @Override // javax.crypto.spec.SecretKeySpec, java.security.Key
    public byte[] getEncoded() {
        if (this.c) {
            return b;
        }
        return super.getEncoded();
    }
}

package iaik.security.cipher;

import javax.crypto.spec.PBEKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class PBEKeyBMP implements javax.crypto.SecretKey {
    private static final long serialVersionUID = -5257488068330070385L;
    private char[] a;

    public PBEKeyBMP(String str) {
        this.a = str.toCharArray();
    }

    public PBEKeyBMP(PBEKeySpec pBEKeySpec) {
        this.a = pBEKeySpec.getPassword();
    }

    public PBEKeyBMP(char[] cArr) {
        this.a = cArr;
    }

    public static final byte[] getEncoded(char[] cArr) {
        if (cArr == null) {
            return new byte[0];
        }
        byte[] bArr = new byte[(cArr.length + 1) * 2];
        for (int i = 0; i < cArr.length; i++) {
            int i2 = i * 2;
            bArr[i2] = 0;
            bArr[i2 + 1] = (byte) cArr[i];
        }
        return bArr;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return "PBE";
    }

    @Override // java.security.Key
    public final byte[] getEncoded() {
        return getEncoded(this.a);
    }

    @Override // java.security.Key
    public String getFormat() {
        return "RAW_BMP";
    }

    public char[] getKey() {
        return this.a;
    }
}

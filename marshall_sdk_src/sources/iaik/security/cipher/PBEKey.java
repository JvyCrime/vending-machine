package iaik.security.cipher;

import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import javax.crypto.spec.PBEKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class PBEKey implements javax.crypto.SecretKey {
    private static final long serialVersionUID = 2093852756169455302L;
    private char[] a;
    private String b;

    public PBEKey(String str) {
        this.a = str.toCharArray();
    }

    public PBEKey(PBEKeySpec pBEKeySpec) {
        this.a = pBEKeySpec.getPassword();
    }

    public PBEKey(char[] cArr) {
        this.a = cArr;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        if (this.b == null) {
            this.b = "PBE";
        }
        return this.b;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        try {
            return Util.getUTF8EncodingFromCharArray(this.a);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error encoding password: ");
            stringBuffer.append(e.toString());
            throw new InternalErrorException(stringBuffer.toString(), e);
        }
    }

    @Override // java.security.Key
    public String getFormat() {
        return "RAW";
    }

    public char[] getKey() {
        return this.a;
    }

    public void setAlgorithm(String str) {
        this.b = str;
    }
}

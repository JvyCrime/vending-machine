package iaik.security.cipher;

import iaik.utils.CriticalObject;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.security.InvalidKeyException;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class SecretKey extends SecretKeySpec {
    private static final byte[] a = new byte[8];
    private static final long serialVersionUID = -5718762160527928937L;
    private byte[] b;
    private String c;

    private SecretKey() {
        super(a, "DUMMY");
        this.b = null;
    }

    public SecretKey(javax.crypto.SecretKey secretKey) throws InvalidKeyException {
        this();
        if (secretKey.getFormat().equals("RAW")) {
            this.b = secretKey.getEncoded();
            this.c = secretKey.getAlgorithm();
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Key format must be RAW but is ");
            stringBuffer.append(secretKey.getFormat());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public SecretKey(byte[] bArr, int i, int i2, String str) {
        this();
        byte[] bArr2 = new byte[i2];
        this.b = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, i2);
        this.c = str;
    }

    public SecretKey(byte[] bArr, String str) {
        this(bArr, 0, bArr.length, str);
    }

    public void destroyCriticalData() {
        CriticalObject.destroy(this.b);
        this.c = null;
    }

    @Override // javax.crypto.spec.SecretKeySpec
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof javax.crypto.SecretKey)) {
            return false;
        }
        javax.crypto.SecretKey secretKey = (javax.crypto.SecretKey) obj;
        if (secretKey.getAlgorithm().equals(this.c)) {
            return CryptoUtils.secureEqualsBlock(secretKey.getEncoded(), this.b);
        }
        return false;
    }

    @Override // javax.crypto.spec.SecretKeySpec, java.security.Key
    public String getAlgorithm() {
        return this.c;
    }

    @Override // javax.crypto.spec.SecretKeySpec, java.security.Key
    public byte[] getEncoded() {
        return (byte[]) this.b.clone();
    }

    @Override // javax.crypto.spec.SecretKeySpec, java.security.Key
    public String getFormat() {
        return "RAW";
    }

    @Override // javax.crypto.spec.SecretKeySpec
    public int hashCode() {
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i >= bArr.length) {
                return this.c.toLowerCase().hashCode() ^ i2;
            }
            i2 += bArr[i] * i;
            i++;
        }
    }

    public void setAlgorithm(String str) {
        this.c = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.c);
        stringBuffer.append(" key: ");
        stringBuffer.append(Util.toString(this.b));
        return stringBuffer.toString();
    }
}

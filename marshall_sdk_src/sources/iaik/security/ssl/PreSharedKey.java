package iaik.security.ssl;

import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class PreSharedKey extends SecretKeySpec {
    private static final byte[] a = new byte[8];
    private byte[] b;

    @Override // javax.crypto.spec.SecretKeySpec, java.security.Key
    public String getAlgorithm() {
        return "PSK";
    }

    @Override // javax.crypto.spec.SecretKeySpec, java.security.Key
    public String getFormat() {
        return "RAW";
    }

    private PreSharedKey() {
        super(a, "PSK");
        this.b = null;
    }

    public PreSharedKey(byte[] bArr) {
        this();
        Objects.requireNonNull(bArr, "Cannot create null PSK!");
        this.b = (byte[]) bArr.clone();
    }

    @Override // javax.crypto.spec.SecretKeySpec, java.security.Key
    public byte[] getEncoded() {
        return (byte[]) this.b.clone();
    }

    public void destroyCriticalData() {
        byte[] bArr = this.b;
        if (bArr != null) {
            int length = bArr.length;
            for (int i = 0; i < length; i++) {
                this.b[i] = 0;
            }
        }
    }

    @Override // javax.crypto.spec.SecretKeySpec
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PreSharedKey) {
            return Utils.equalsBlock(((PreSharedKey) obj).getEncoded(), this.b);
        }
        return false;
    }

    @Override // javax.crypto.spec.SecretKeySpec
    public int hashCode() {
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i >= bArr.length) {
                return 79528 ^ i2;
            }
            i2 += bArr[i] * i;
            i++;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("PSK key: ");
        stringBuffer.append(Utils.toString(this.b));
        return stringBuffer.toString();
    }
}

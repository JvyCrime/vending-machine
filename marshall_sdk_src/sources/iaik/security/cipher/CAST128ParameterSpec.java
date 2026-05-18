package iaik.security.cipher;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class CAST128ParameterSpec implements AlgorithmParameterSpec {
    private byte[] a;
    private int b;

    public CAST128ParameterSpec(int i) {
        this.b = i;
    }

    public CAST128ParameterSpec(int i, byte[] bArr) {
        this.b = i;
        if (bArr != null) {
            byte[] bArr2 = new byte[8];
            this.a = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, 8);
        }
    }

    public CAST128ParameterSpec(int i, byte[] bArr, int i2) {
        this.b = i;
        if (bArr != null) {
            byte[] bArr2 = new byte[8];
            this.a = bArr2;
            System.arraycopy(bArr, i2, bArr2, 0, 8);
        }
    }

    public byte[] getIV() {
        byte[] bArr = this.a;
        return bArr == null ? new byte[8] : (byte[]) bArr.clone();
    }

    public int getKeyLength() {
        return this.b;
    }
}

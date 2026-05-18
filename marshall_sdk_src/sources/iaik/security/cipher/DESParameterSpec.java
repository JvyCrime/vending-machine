package iaik.security.cipher;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class DESParameterSpec implements AlgorithmParameterSpec {
    private byte[] a;
    private byte[] b;
    private byte[] c;

    public DESParameterSpec(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.b = bArr;
        this.c = bArr2;
        byte[] bArr4 = new byte[8];
        this.a = bArr4;
        System.arraycopy(bArr3, 0, bArr4, 0, 8);
    }

    public DESParameterSpec(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        this.b = bArr;
        this.c = bArr2;
        byte[] bArr4 = new byte[8];
        this.a = bArr4;
        System.arraycopy(bArr3, i, bArr4, 0, 8);
    }

    public byte[] getIV() {
        byte[] bArr = this.a;
        if (bArr == null) {
            return null;
        }
        return (byte[]) bArr.clone();
    }

    public byte[] getPBox() {
        return this.c;
    }

    public byte[] getSBoxes() {
        return this.b;
    }
}

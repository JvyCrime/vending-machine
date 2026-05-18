package iaik.security.cipher;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class GOSTParameterSpec implements AlgorithmParameterSpec {
    private byte[] a;
    private byte[] b;

    public GOSTParameterSpec(byte[] bArr, byte[] bArr2) {
        this.b = bArr;
        byte[] bArr3 = new byte[8];
        this.a = bArr3;
        System.arraycopy(bArr2, 0, bArr3, 0, 8);
    }

    public GOSTParameterSpec(byte[] bArr, byte[] bArr2, int i) {
        this.b = bArr;
        byte[] bArr3 = new byte[8];
        this.a = bArr3;
        System.arraycopy(bArr2, i, bArr3, 0, 8);
    }

    public byte[] getIV() {
        byte[] bArr = this.a;
        if (bArr == null) {
            return null;
        }
        return (byte[]) bArr.clone();
    }

    public byte[] getSBoxes() {
        return this.b;
    }
}

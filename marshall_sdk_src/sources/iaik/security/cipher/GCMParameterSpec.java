package iaik.security.cipher;

import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class GCMParameterSpec implements AlgorithmParameterSpec {
    private byte[] a;
    private byte[] b;
    private int c;
    private byte[] d;

    public GCMParameterSpec() throws InvalidAlgorithmParameterException {
        this(null, null, null, -1);
    }

    public GCMParameterSpec(byte[] bArr, byte[] bArr2) throws InvalidAlgorithmParameterException {
        this(bArr, bArr2, null, -1);
    }

    public GCMParameterSpec(byte[] bArr, byte[] bArr2, int i) throws InvalidAlgorithmParameterException {
        this(bArr, bArr2, null, i);
    }

    public GCMParameterSpec(byte[] bArr, byte[] bArr2, byte[] bArr3) throws InvalidAlgorithmParameterException {
        this(bArr, bArr2, bArr3, -1);
    }

    private GCMParameterSpec(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) throws InvalidAlgorithmParameterException {
        this.a = bArr;
        this.b = bArr2;
        this.d = bArr3;
        this.c = i;
        if ((bArr3 == null || bArr3.length == 0) && i == -1) {
            this.c = 12;
            return;
        }
        if (i == -1) {
            this.c = bArr3.length;
        }
        int i2 = this.c;
        if ((i2 < 12 || i2 > 16) && i2 != 4 && i2 != 8) {
            throw new InvalidAlgorithmParameterException("Invalid MAC length - valid length values: 4,8,12,13,14,15,16 bytes!");
        }
    }

    public byte[] getAAD() {
        return this.a;
    }

    public byte[] getMac() {
        return this.d;
    }

    public int getMacLength() {
        return this.c;
    }

    public byte[] getNonce() {
        return this.b;
    }

    public void setMacLength(int i) throws InvalidAlgorithmParameterException {
        if ((i < 12 || i > 16) && i != 4 && i != 8) {
            throw new InvalidAlgorithmParameterException("Invalid MAC length - valid length values: 4,8,12,13,14,15,16 bytes!");
        }
        this.c = i;
    }
}

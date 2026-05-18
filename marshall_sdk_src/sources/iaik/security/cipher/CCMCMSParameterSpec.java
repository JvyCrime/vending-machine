package iaik.security.cipher;

import iaik.security.random.SecRandom;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class CCMCMSParameterSpec implements AlgorithmParameterSpec {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private long e;

    public CCMCMSParameterSpec() throws Exception {
        this(-1L, null, null, null, -1);
    }

    public CCMCMSParameterSpec(long j, byte[] bArr, byte[] bArr2, int i) throws Exception {
        this(j, bArr, bArr2, null, i);
    }

    public CCMCMSParameterSpec(long j, byte[] bArr, byte[] bArr2, byte[] bArr3) throws Exception {
        this(j, bArr, bArr2, bArr3, -1);
    }

    private CCMCMSParameterSpec(long j, byte[] bArr, byte[] bArr2, byte[] bArr3, int i) throws InvalidAlgorithmParameterException {
        this.a = bArr;
        this.b = bArr2;
        this.c = bArr3;
        this.d = i;
        this.e = j;
        if (bArr2 == null || bArr2.length == 0) {
            if (j > -1) {
                this.b = j < 2147483647L ? new byte[11] : new byte[7];
            } else {
                this.b = new byte[7];
            }
            SecRandom.getDefault().nextBytes(this.b);
        } else if (bArr2.length < 7 || bArr2.length > 13) {
            throw new InvalidAlgorithmParameterException("nonce length not between 7 and 13 bytes!");
        }
        long j2 = this.e;
        if (j2 != -1) {
            if (j2 < -1) {
                throw new InvalidAlgorithmParameterException("invalid value for inputLength");
            }
            if (this.e - ((long) this.d) > ((long) (Math.pow(2.0d, (15 - this.b.length) * 8) - 1.0d))) {
                throw new InvalidAlgorithmParameterException("parameter inputLength or nonce too long");
            }
        }
        byte[] bArr4 = this.c;
        if ((bArr4 == null || bArr4.length == 0) && this.d == -1) {
            this.d = 12;
            return;
        }
        if (this.d == -1) {
            this.d = bArr4.length;
        }
        int i2 = this.d;
        if (i2 < 4 || i2 > 16) {
            throw new InvalidAlgorithmParameterException("Specified MAC-length not between 4 and 16 bytes!");
        }
    }

    public CCMCMSParameterSpec(byte[] bArr, byte[] bArr2, int i) throws Exception {
        this(-1L, bArr, bArr2, null, i);
    }

    public CCMCMSParameterSpec(byte[] bArr, byte[] bArr2, byte[] bArr3) throws Exception {
        this(-1L, bArr, bArr2, bArr3, -1);
    }

    public byte[] getAssociatedData() {
        return this.a;
    }

    public long getInputLength() {
        return this.e;
    }

    public byte[] getMac() {
        return this.c;
    }

    public int getMacLength() {
        return this.d;
    }

    public byte[] getNonce() {
        return this.b;
    }

    public void setInputLength(long j) throws InvalidAlgorithmParameterException {
        this.e = j;
        if (j < 0) {
            throw new InvalidAlgorithmParameterException("invalid value for inputLength");
        }
        if (j - ((long) this.d) > ((long) (Math.pow(2.0d, (15 - this.b.length) * 8) - 1.0d))) {
            throw new InvalidAlgorithmParameterException("parameter inputLength or nonce too long");
        }
    }

    public void setMacLength(int i) throws InvalidAlgorithmParameterException {
        this.d = i;
        if (i < 4 || i > 16) {
            throw new InvalidAlgorithmParameterException("Specified MAC-length not between 4 and 16 bytes!");
        }
    }
}

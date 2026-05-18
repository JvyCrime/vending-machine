package iaik.iso.iso9796;

import iaik.pkcs.pkcs1.MaskGenerationAlgorithm;
import iaik.utils.Util;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class ISO9796P2S2S3ParameterSpec extends ISO9796P2ParameterSpec {
    private byte[] c;
    private MaskGenerationAlgorithm e;
    private int b = -1;
    private int d = 0;

    public int getCMinus() {
        return this.d;
    }

    public MaskGenerationAlgorithm getMGFEngine() {
        return this.e;
    }

    public byte[] getSalt() {
        return this.c;
    }

    public int getSaltLength() {
        return this.b;
    }

    public void setCMinus(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("cMinus value must not be negative!");
        }
        this.d = i;
    }

    public void setMGFEngine(MaskGenerationAlgorithm maskGenerationAlgorithm) {
        Objects.requireNonNull(maskGenerationAlgorithm, "MaskGenerationAlgorithm engine must not be null!");
        this.e = maskGenerationAlgorithm;
    }

    public void setSalt(byte[] bArr) {
        int i = this.b;
        if (i > -1 && bArr != null && i != bArr.length) {
            throw new IllegalArgumentException("Length of supplied salt does not match to saltLen value.");
        }
        this.c = bArr;
        if (bArr != null) {
            this.b = bArr.length;
        } else {
            this.b = -1;
        }
    }

    public void setSaltLength(int i) {
        byte[] bArr = this.c;
        if (bArr != null) {
            if (i != bArr.length) {
                throw new IllegalArgumentException("Supplied salt length does not match to salt value.");
            }
        } else if (this.a == null) {
            if (i < 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Salt length (");
                stringBuffer.append(i);
                stringBuffer.append(") must be not negative!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        } else if (i <= 0) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Salt length (");
            stringBuffer2.append(i);
            stringBuffer2.append(") must be positive (> 0)!");
            throw new IllegalArgumentException(stringBuffer2.toString());
        }
        this.b = i;
    }

    @Override // iaik.iso.iso9796.ISO9796P2ParameterSpec
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Hash generation algorithm: ");
        MaskGenerationAlgorithm maskGenerationAlgorithm = this.e;
        stringBuffer2.append(maskGenerationAlgorithm == null ? "not set" : maskGenerationAlgorithm.getAlgorithm());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Salt length: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("Salt value: ");
        byte[] bArr = this.c;
        stringBuffer4.append(bArr != null ? Util.toString(bArr) : "not set");
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append("CMinus: ");
        stringBuffer5.append(this.d);
        stringBuffer5.append("\n");
        stringBuffer.append(stringBuffer5.toString());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

package iaik.security.dsa;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class SHA2withDSAGenParameterSpec implements AlgorithmParameterSpec {
    private int a;
    private int b;
    private int c;

    public SHA2withDSAGenParameterSpec(int i, int i2) throws IllegalArgumentException {
        if (i != 1024) {
            if (i != 2048) {
                if (i != 3072) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid (L,N) pair: (");
                    stringBuffer.append(i);
                    stringBuffer.append("/");
                    stringBuffer.append(i2);
                    stringBuffer.append(")");
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
                if (i2 != 256) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Invalid (L,N) pair: (");
                    stringBuffer2.append(i);
                    stringBuffer2.append("/");
                    stringBuffer2.append(i2);
                    stringBuffer2.append(")");
                    throw new IllegalArgumentException(stringBuffer2.toString());
                }
            } else if (i2 != 224 && i2 != 256) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid (L,N) pair: (");
                stringBuffer3.append(i);
                stringBuffer3.append("/");
                stringBuffer3.append(i2);
                stringBuffer3.append(")");
                throw new IllegalArgumentException(stringBuffer3.toString());
            }
        } else if (i2 != 160) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Invalid (L,N) pair: (");
            stringBuffer4.append(i);
            stringBuffer4.append("/");
            stringBuffer4.append(i2);
            stringBuffer4.append(")");
            throw new IllegalArgumentException(stringBuffer4.toString());
        }
        this.a = i;
        this.b = i2;
        this.c = -1;
    }

    public int getL() {
        return this.a;
    }

    public int getN() {
        return this.b;
    }

    public int getSeedlen() {
        return this.c;
    }

    public void setSeedlen(int i) {
        if (i < this.b) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Seedlen (");
            stringBuffer.append(i);
            stringBuffer.append(") must not be shorter than N (");
            stringBuffer.append(this.b);
            stringBuffer.append(")");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (i % 8 == 0) {
            this.c = i;
            return;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Seedlen (");
        stringBuffer2.append(i);
        stringBuffer2.append(") must be multiple of 8!");
        throw new IllegalArgumentException(stringBuffer2.toString());
    }
}

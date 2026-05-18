package iaik.security.elgamal;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class ElGamalParameterSpec implements AlgorithmParameterSpec {
    private BigInteger a;
    private BigInteger b;
    private int c;

    public ElGamalParameterSpec(BigInteger bigInteger, BigInteger bigInteger2) {
        this.a = bigInteger;
        this.b = bigInteger2;
    }

    public ElGamalParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, int i) {
        this.a = bigInteger;
        this.b = bigInteger2;
        this.c = i;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ElGamalParameterSpec)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ElGamalParameterSpec elGamalParameterSpec = (ElGamalParameterSpec) obj;
        return this.a.equals(elGamalParameterSpec.getP()) && this.b.equals(elGamalParameterSpec.getG()) && this.c == elGamalParameterSpec.getL();
    }

    public BigInteger getG() {
        return this.b;
    }

    public int getL() {
        return this.c;
    }

    public BigInteger getP() {
        return this.a;
    }

    public int hashCode() {
        return (this.b.hashCode() << 16) ^ this.a.hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("p: ");
        stringBuffer2.append(this.a.toString(16));
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("\ng: ");
        stringBuffer3.append(this.b.toString(16));
        stringBuffer.append(stringBuffer3.toString());
        if (this.c > 0) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("\nl: ");
            stringBuffer4.append(this.c);
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }
}

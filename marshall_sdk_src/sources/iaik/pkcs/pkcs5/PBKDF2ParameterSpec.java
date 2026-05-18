package iaik.pkcs.pkcs5;

import iaik.asn1.structures.AlgorithmID;
import iaik.utils.Util;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PBKDF2ParameterSpec implements Cloneable, AlgorithmParameterSpec {
    private byte[] a;
    private int b;
    private int c;
    private AlgorithmID d;

    public PBKDF2ParameterSpec(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("salt must not be null!");
        }
        this.a = (byte[]) bArr.clone();
        if (i < 1) {
            throw new IllegalArgumentException("iterationCount must be >= 1!");
        }
        this.b = i;
        if (i2 < 1) {
            throw new IllegalArgumentException("derivedKeyLength must be >= 1!");
        }
        this.c = i2;
        this.d = (AlgorithmID) PBKDF2.a.clone();
    }

    public Object clone() {
        PBKDF2ParameterSpec pBKDF2ParameterSpec;
        PBKDF2ParameterSpec pBKDF2ParameterSpec2 = null;
        try {
            pBKDF2ParameterSpec = (PBKDF2ParameterSpec) super.clone();
        } catch (CloneNotSupportedException unused) {
        }
        try {
            pBKDF2ParameterSpec.a = (byte[]) this.a.clone();
            pBKDF2ParameterSpec.b = this.b;
            pBKDF2ParameterSpec.c = this.c;
            AlgorithmID algorithmID = this.d;
            if (algorithmID == null) {
                return pBKDF2ParameterSpec;
            }
            pBKDF2ParameterSpec.d = (AlgorithmID) algorithmID.clone();
            return pBKDF2ParameterSpec;
        } catch (CloneNotSupportedException unused2) {
            pBKDF2ParameterSpec2 = pBKDF2ParameterSpec;
            return pBKDF2ParameterSpec2;
        }
    }

    public final int getDerivedKeyLength() {
        return this.c;
    }

    public int getIterationCount() {
        return this.b;
    }

    public AlgorithmID getPrf() {
        return this.d;
    }

    public final byte[] getSalt() {
        return (byte[]) this.a.clone();
    }

    public void setPrf(AlgorithmID algorithmID) {
        if (algorithmID == null) {
            this.d = (AlgorithmID) PBKDF2.a.clone();
        }
        this.d = algorithmID;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("salt: ");
        stringBuffer2.append(Util.toString(this.a));
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("iterationCount: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("keyLength: ");
        stringBuffer4.append(this.c);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        if (this.d != null) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("prf: ");
            stringBuffer5.append(this.d);
            stringBuffer5.append("\n");
            stringBuffer.append(stringBuffer5.toString());
        }
        return stringBuffer.toString();
    }
}

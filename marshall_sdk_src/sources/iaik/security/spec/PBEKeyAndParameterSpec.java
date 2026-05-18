package iaik.security.spec;

import iaik.utils.CriticalObject;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PBEKeyAndParameterSpec implements AlgorithmParameterSpec {
    private byte[] a;
    private byte[] b;
    private int c;
    private int d;

    public PBEKeyAndParameterSpec(byte[] bArr, byte[] bArr2, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("password must not be null!");
        }
        if (bArr2 == null) {
            throw new IllegalArgumentException("salt must not be null!");
        }
        this.a = (byte[]) bArr.clone();
        this.b = (byte[]) bArr2.clone();
        this.c = i;
        this.d = i2;
    }

    protected void finalize() throws Throwable {
        try {
            byte[] bArr = this.a;
            if (bArr != null) {
                CriticalObject.destroy(bArr);
                this.a = null;
            }
        } finally {
            super.finalize();
        }
    }

    public final int getDerivedKeyLength() {
        return this.d;
    }

    public final int getIterationCount() {
        return this.c;
    }

    public final byte[] getPassword() {
        return (byte[]) this.a.clone();
    }

    public final byte[] getSalt() {
        return (byte[]) this.b.clone();
    }
}

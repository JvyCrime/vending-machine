package iaik.pkcs.pkcs5;

import iaik.asn1.structures.AlgorithmID;
import iaik.security.spec.PBEKeyAndParameterSpec;
import iaik.utils.UTF8CodingException;
import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class PBKDF2KeyAndParameterSpec extends PBEKeyAndParameterSpec {
    private AlgorithmID a;

    public PBKDF2KeyAndParameterSpec(byte[] bArr, PBKDF2ParameterSpec pBKDF2ParameterSpec) {
        super(bArr, pBKDF2ParameterSpec.getSalt(), pBKDF2ParameterSpec.getIterationCount(), pBKDF2ParameterSpec.getDerivedKeyLength());
        setPrf(pBKDF2ParameterSpec.getPrf());
    }

    public PBKDF2KeyAndParameterSpec(byte[] bArr, byte[] bArr2, int i, int i2) {
        this(bArr, new PBKDF2ParameterSpec(bArr2, i, i2));
    }

    public PBKDF2KeyAndParameterSpec(char[] cArr, PBKDF2ParameterSpec pBKDF2ParameterSpec) throws UTF8CodingException {
        this(cArr == null ? null : Util.getUTF8EncodingFromCharArray(cArr), pBKDF2ParameterSpec);
    }

    public AlgorithmID getPrf() {
        return this.a;
    }

    public void setPrf(AlgorithmID algorithmID) {
        if (algorithmID == null) {
            this.a = (AlgorithmID) PBKDF2.a.clone();
        }
        this.a = algorithmID;
    }
}

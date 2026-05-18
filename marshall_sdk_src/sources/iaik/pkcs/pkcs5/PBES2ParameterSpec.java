package iaik.pkcs.pkcs5;

import iaik.asn1.structures.AlgorithmID;
import iaik.utils.Util;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class PBES2ParameterSpec extends PBKDF2ParameterSpec implements Cloneable {
    AlgorithmID a;
    AlgorithmParameterSpec b;

    public PBES2ParameterSpec(byte[] bArr, int i, int i2, AlgorithmID algorithmID) {
        super(bArr, i, i2);
        a(algorithmID);
    }

    private void a(AlgorithmID algorithmID) {
        Objects.requireNonNull(algorithmID, "encryptionScheme must not be null!");
        this.a = algorithmID;
    }

    @Override // iaik.pkcs.pkcs5.PBKDF2ParameterSpec
    public Object clone() {
        PBES2ParameterSpec pBES2ParameterSpec = (PBES2ParameterSpec) super.clone();
        pBES2ParameterSpec.a = (AlgorithmID) this.a.clone();
        return pBES2ParameterSpec;
    }

    public AlgorithmID getEncryptionScheme() {
        return this.a;
    }

    public AlgorithmParameterSpec getEncryptionSchemeParameters() throws InvalidAlgorithmParameterException {
        if (this.b == null) {
            this.b = this.a.getAlgorithmParameterSpec("IAIK");
        }
        return this.b;
    }

    public void setEncryptionSchemeParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.a.setAlgorithmParameterSpec(algorithmParameterSpec, "IAIK");
        this.b = algorithmParameterSpec;
    }

    @Override // iaik.pkcs.pkcs5.PBKDF2ParameterSpec
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("keyDerivationFunction (PBKDF2): {\n");
        Util.printIndented(super.toString(), true, stringBuffer);
        stringBuffer.append("}\n");
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("encryptionScheme: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        return stringBuffer.toString();
    }
}

package iaik.security.cipher;

import iaik.security.random.SecRandom;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;

/* JADX INFO: loaded from: classes.dex */
public abstract class VarLengthKeyGenerator extends KeyGeneratorSpi {
    private String a;
    private int b;
    private int c;
    private int d;
    private int e;
    private SecureRandom f;
    private int g;

    VarLengthKeyGenerator(String str, int i) {
        this(str, i, i, i);
    }

    protected VarLengthKeyGenerator(String str, int i, int i2, int i3) {
        this(str, i, i2, i3, 8);
    }

    VarLengthKeyGenerator(String str, int i, int i2, int i3, int i4) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.g = i3;
    }

    int a() {
        return this.c;
    }

    int b() {
        return this.b;
    }

    int c() {
        return this.d;
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected javax.crypto.SecretKey engineGenerateKey() {
        if (this.f == null) {
            this.f = SecRandom.getDefault();
        }
        int i = this.g;
        int i2 = this.e;
        int i3 = (i + i2) - 1;
        byte[] bArr = new byte[(i3 - (i3 % i2)) / 8];
        this.f.nextBytes(bArr);
        return new SecretKey(bArr, this.a);
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(int i, SecureRandom secureRandom) {
        if (i == -1) {
            i = this.d;
        }
        int i2 = this.b;
        if (i < i2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Specifed key length (");
            stringBuffer.append(i);
            stringBuffer.append(") too short. Minimum required length is ");
            stringBuffer.append(this.b);
            stringBuffer.append("!");
            throw new InvalidParameterException(stringBuffer.toString());
        }
        int i3 = this.c;
        if (i3 > 0 && i > i3) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Specifed key length (");
            stringBuffer2.append(i);
            stringBuffer2.append(") too long. Maximum allowed length is ");
            stringBuffer2.append(this.c);
            stringBuffer2.append("!");
            throw new InvalidParameterException(stringBuffer2.toString());
        }
        if ((i - i2) % this.e == 0) {
            this.g = i;
            this.f = secureRandom;
            return;
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Invalid key length (");
        stringBuffer3.append(i);
        stringBuffer3.append("). Must be a multiple of ");
        stringBuffer3.append(this.e);
        stringBuffer3.append("!");
        throw new InvalidParameterException(stringBuffer3.toString());
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(SecureRandom secureRandom) {
        engineInit(this.d, secureRandom);
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            throw new InvalidAlgorithmParameterException("No parameters are needed.");
        }
        engineInit(this.d, secureRandom);
    }
}

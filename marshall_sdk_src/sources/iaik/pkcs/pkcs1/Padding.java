package iaik.pkcs.pkcs1;

import iaik.security.random.SecRandom;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;

/* JADX INFO: loaded from: classes.dex */
public abstract class Padding {
    public static final String PADDING_NONE = "NoPadding";
    public static final String PADDING_OAEP = "OAEP";
    public static final String PADDING_PKCS1 = "PKCS1Padding";
    public static final String PADDING_PKCS1_SSL2 = "PKCS1PaddingSSL2";
    private String a;
    private SecureRandom b;
    protected int modLen_;
    protected int opMode_;
    protected PrivateKey privateKey_;
    protected PublicKey publicKey_;

    Padding(String str) {
        this.a = str;
    }

    void a() {
    }

    void a(SecureRandom secureRandom) {
        this.b = secureRandom;
    }

    SecureRandom b() {
        if (this.b == null) {
            this.b = SecRandom.getDefault();
        }
        return this.b;
    }

    final String c() {
        return this.a;
    }

    public abstract AlgorithmParameters getParameters();

    public void init(int i, Key key, int i2, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.publicKey_ = null;
        this.privateKey_ = null;
        a();
        if (key instanceof PublicKey) {
            this.publicKey_ = (PublicKey) key;
        } else {
            this.privateKey_ = (PrivateKey) key;
        }
        this.modLen_ = i2;
        if (i == 1 || i == 2) {
            this.opMode_ = i;
            this.b = secureRandom;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid padding mode: ");
            stringBuffer.append(i);
            throw new InvalidAlgorithmParameterException(stringBuffer.toString());
        }
    }

    public abstract byte[] pad(byte[] bArr) throws BadPaddingException;

    public abstract byte[] unpad(byte[] bArr) throws BadPaddingException;
}

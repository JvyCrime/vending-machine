package iaik.pkcs.pkcs1;

import iaik.asn1.structures.AlgorithmID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class MGF1ParameterSpec extends PKCS1AlgorithmParameterSpec {
    private MessageDigest a;
    private AlgorithmID b;

    public MGF1ParameterSpec() {
        this.b = (AlgorithmID) AlgorithmID.sha1.clone();
    }

    public MGF1ParameterSpec(AlgorithmID algorithmID) {
        if (algorithmID == null) {
            throw new IllegalArgumentException("Argument \"hashAlgorithm\" must be not null");
        }
        this.b = algorithmID;
    }

    public AlgorithmID getHashAlgorithm() {
        return this.b;
    }

    public MessageDigest getHashEngine() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = this.a;
        if (messageDigest == null) {
            try {
                this.a = this.b.getMessageDigestInstance("IAIK");
            } catch (NoSuchAlgorithmException unused) {
                this.a = this.b.getMessageDigestInstance();
            }
        } else {
            messageDigest.reset();
        }
        return this.a;
    }

    public void setHashEngine(MessageDigest messageDigest) {
        this.a = messageDigest;
    }

    public String toString() {
        return this.b.toString();
    }
}

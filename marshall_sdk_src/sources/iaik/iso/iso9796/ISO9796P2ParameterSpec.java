package iaik.iso.iso9796;

import iaik.utils.Util;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class ISO9796P2ParameterSpec implements AlgorithmParameterSpec {
    SecureRandom a;
    private MessageDigest b;
    private int d;
    private boolean e = false;
    private int c = -1;
    private boolean f = true;

    public MessageDigest getHashEngine() {
        return this.b;
    }

    public int getHashID() {
        return this.c;
    }

    public int getHashLen() {
        return this.d;
    }

    public SecureRandom getSecureRandom() {
        return this.a;
    }

    public boolean getUseAlternativeSignatureFunction() {
        return this.f;
    }

    public boolean getUseExplicitTrailer() {
        return this.e;
    }

    public void setHashEngine(MessageDigest messageDigest, int i) {
        Objects.requireNonNull(messageDigest, "MessageDigest engine must not be null!");
        if (i < 0) {
            throw new IllegalArgumentException("Hash length must not be negative!");
        }
        this.b = messageDigest;
        this.d = i;
    }

    public void setHashID(int i) {
        if (i >= 0 && i <= 255) {
            this.c = i;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid hash id (");
        stringBuffer.append(i);
        stringBuffer.append("). Has to be in the range from 0 to 255");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public void setSecureRandom(SecureRandom secureRandom) {
        this.a = secureRandom;
    }

    public void setUseAlternativeSignatureFunction(boolean z) {
        this.f = z;
    }

    public void setUseExplicitTrailer(boolean z) {
        this.e = z;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Hash engine: ");
        MessageDigest messageDigest = this.b;
        stringBuffer2.append(messageDigest == null ? "not set" : messageDigest.getAlgorithm());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Hash output length: ");
        stringBuffer3.append(this.d);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        if (this.c > -1) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Hash id: ");
            stringBuffer4.append(Util.toString(this.c));
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        stringBuffer.append(this.e ? "Explicit trailer\n" : "Implicit trailer\n");
        if (this.a != null) {
            stringBuffer.append("Random source: set\n");
        }
        return stringBuffer.toString();
    }
}

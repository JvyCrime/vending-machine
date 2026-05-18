package iaik.iso.iso9796;

import iaik.utils.PretendedMessageDigest;
import iaik.utils.Util;
import java.security.MessageDigest;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RawISO9796P2S2S3ParameterSpec extends ISO9796P2S2S3ParameterSpec {
    private byte[] b;
    private int c;
    private MessageDigest d;

    public RawISO9796P2S2S3ParameterSpec(String str, int i, byte[] bArr, int i2) {
        Objects.requireNonNull(bArr, "Mr must not be null!");
        if (i2 < 0) {
            throw new NullPointerException("msgLen must not be negative!");
        }
        if (i < 0) {
            throw new IllegalArgumentException("Hash length must not be negative!");
        }
        this.b = (byte[]) bArr.clone();
        this.c = i2;
        this.d = new PretendedMessageDigest(str, i);
        try {
            setHashEngine(MessageDigest.getInstance(str), i);
        } catch (Exception unused) {
        }
    }

    MessageDigest a() {
        return this.d;
    }

    public byte[] getMr() {
        return this.b;
    }

    public int getMsgLen() {
        return this.c;
    }

    @Override // iaik.iso.iso9796.ISO9796P2S2S3ParameterSpec, iaik.iso.iso9796.ISO9796P2ParameterSpec
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Mr: ");
        stringBuffer2.append(Util.toString(this.b));
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

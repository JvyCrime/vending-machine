package iaik.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class PretendedMessageDigest extends MessageDigest implements Cloneable {
    private ByteArrayOutputStream a;
    private int b;

    public PretendedMessageDigest() {
        super("DUMMY");
        this.a = new ByteArrayOutputStream(64);
    }

    public PretendedMessageDigest(String str, int i) {
        super(str);
        Objects.requireNonNull(str, "MessageDigest engine name must not be null!");
        if (i < 0) {
            throw new IllegalArgumentException("Digest length must not be negative!");
        }
        this.b = i;
        this.a = new ByteArrayOutputStream(this.b);
    }

    @Override // java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        PretendedMessageDigest pretendedMessageDigest;
        PretendedMessageDigest pretendedMessageDigest2 = null;
        try {
            pretendedMessageDigest = (PretendedMessageDigest) super.clone();
        } catch (CloneNotSupportedException unused) {
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.a.toByteArray().length);
            pretendedMessageDigest.a = byteArrayOutputStream;
            try {
                byteArrayOutputStream.write(this.a.toByteArray());
                pretendedMessageDigest.b = this.b;
                return pretendedMessageDigest;
            } catch (IOException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error copying buffer: ");
                stringBuffer.append(e.getMessage());
                throw new InternalErrorException(stringBuffer.toString(), e);
            }
        } catch (CloneNotSupportedException unused2) {
            pretendedMessageDigest2 = pretendedMessageDigest;
            return pretendedMessageDigest2;
        }
    }

    @Override // java.security.MessageDigestSpi
    protected byte[] engineDigest() {
        byte[] byteArray = this.a.toByteArray();
        int i = this.b;
        if (i <= 0 || byteArray.length == i) {
            if (byteArray.length == 0) {
                throw new IllegalArgumentException("No hash value has been set!");
            }
            this.b = byteArray.length;
            engineReset();
            return byteArray;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Total input hash length ");
        stringBuffer.append(byteArray.length);
        stringBuffer.append(" not equal to digest size ");
        stringBuffer.append(this.b);
        stringBuffer.append(".");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    @Override // java.security.MessageDigestSpi
    public int engineGetDigestLength() {
        return this.b;
    }

    @Override // java.security.MessageDigestSpi
    protected void engineReset() {
        this.a.reset();
    }

    @Override // java.security.MessageDigestSpi
    protected final void engineUpdate(byte b) {
        int size;
        if (this.b <= 0 || (size = this.a.size() + 1) <= this.b) {
            this.a.write(b);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Total input data length ");
        stringBuffer.append(size);
        stringBuffer.append(" exceeds digest size ");
        stringBuffer.append(this.b);
        stringBuffer.append(".");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    @Override // java.security.MessageDigestSpi
    protected final void engineUpdate(byte[] bArr, int i, int i2) {
        int size;
        if (this.b <= 0 || (size = this.a.size() + i2) <= this.b) {
            this.a.write(bArr, i, i2);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Total input data length ");
        stringBuffer.append(size);
        stringBuffer.append(" exceeds digest size ");
        stringBuffer.append(this.b);
        stringBuffer.append(".");
        throw new IllegalArgumentException(stringBuffer.toString());
    }
}

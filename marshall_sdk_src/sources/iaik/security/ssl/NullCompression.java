package iaik.security.ssl;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class NullCompression extends CompressionMethod implements Serializable {
    private static final long serialVersionUID = 6783550761816603455L;

    @Override // iaik.security.ssl.CompressionMethod
    public int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        return i2;
    }

    @Override // iaik.security.ssl.CompressionMethod
    public int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        return i2;
    }

    NullCompression() {
        super("NULL", 0);
    }

    NullCompression(String str, int i) {
        super(str, i);
    }
}

package iaik.security.ssl;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.zip.DataFormatException;

/* JADX INFO: loaded from: classes.dex */
public abstract class CompressionMethod implements Serializable {
    public static final CompressionMethod NULL_COMPRESSION;
    static final CompressionMethod[] a;
    private static final HashMap b = new HashMap();
    private static final HashMap c = new HashMap();
    private static final long serialVersionUID = 5726950260836068618L;
    private int d;
    private String e;

    protected abstract int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataFormatException;

    protected abstract int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataFormatException;

    static {
        NullCompression nullCompression = new NullCompression();
        NULL_COMPRESSION = nullCompression;
        a = new CompressionMethod[]{nullCompression};
    }

    CompressionMethod() {
    }

    protected CompressionMethod(String str, int i) {
        if ((i & 255) != i) {
            throw new IllegalArgumentException("CompressionMethod id must be an 8 bit entity");
        }
        this.e = str;
        this.d = i;
        b.put(new Integer(i), this);
        if (str != null) {
            c.put(str.toLowerCase(), this);
        }
    }

    public final String getName() {
        return this.e;
    }

    public final int getID() {
        return this.d;
    }

    public static CompressionMethod[] getDefault() {
        return (CompressionMethod[]) a.clone();
    }

    static CompressionMethod a(int i) {
        CompressionMethod compressionMethod = (CompressionMethod) b.get(new Integer(i));
        if (compressionMethod != null) {
            return compressionMethod;
        }
        StringBuffer stringBuffer = new StringBuffer("Unknown CompressionMethod ");
        stringBuffer.append(Utils.toString(i));
        return new NullCompression(stringBuffer.toString(), i);
    }

    static CompressionMethod a(String str) {
        return (CompressionMethod) c.get(str.toLowerCase());
    }

    static CompressionMethod[] a(ab abVar) throws IOException {
        byte[] bArrL = abVar.l();
        int length = bArrL.length;
        CompressionMethod[] compressionMethodArr = new CompressionMethod[length];
        for (int i = 0; i < length; i++) {
            compressionMethodArr[i] = a(bArrL[i] & 255);
        }
        return compressionMethodArr;
    }

    static void a(ag agVar, CompressionMethod[] compressionMethodArr) throws IOException {
        int length = compressionMethodArr.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) compressionMethodArr[i].getID();
        }
        agVar.c(bArr);
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof CompressionMethod) && this.d == ((CompressionMethod) obj).d;
    }

    public int hashCode() {
        return this.d;
    }

    public String toString() {
        return getName();
    }
}

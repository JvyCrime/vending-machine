package iaik.utils;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes2.dex */
public class PemOutputStream extends Base64OutputStream {
    private byte[] a;
    private String b;
    private boolean c;

    public PemOutputStream(OutputStream outputStream, String str, String str2) throws IOException {
        this(outputStream, str, str2, null);
    }

    public PemOutputStream(OutputStream outputStream, String str, String str2, byte[] bArr) throws IOException {
        super(outputStream, bArr);
        this.c = false;
        if (bArr == null) {
            this.a = getLineBreak();
        } else {
            this.a = bArr;
        }
        outputStream.write(Util.toASCIIBytes(str));
        outputStream.write(this.a);
        this.b = str2;
    }

    @Override // iaik.utils.Base64OutputStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        if (this.c) {
            return;
        }
        this.c = true;
        super.a(false);
        this.out.write(this.a);
        this.out.write(Util.toASCIIBytes(this.b));
        this.out.write(this.a);
        this.out.flush();
    }
}

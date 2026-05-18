package iaik.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes2.dex */
public class LineOutputStream extends FilterOutputStream {
    private static final byte[] a = {13, 10};

    public LineOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void print(String str) throws IOException {
        write(Util.toASCIIBytes(str));
    }

    public void println() throws IOException {
        write(a);
    }

    public void println(String str) throws IOException {
        write(Util.toASCIIBytes(str));
        write(a);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        this.out.write(i);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        this.out.write(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
    }
}

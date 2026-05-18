package iaik.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes2.dex */
public class Base64OutputStream extends FilterOutputStream {
    private static final byte[] a;
    private static byte[] b = {13, 10};
    private byte[] c;
    private int d;
    private byte[] e;
    private int f;
    private int g;
    private byte[] h;

    static {
        try {
            a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes("ASCII");
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("ASCII encoding unsupported");
        }
    }

    public Base64OutputStream(OutputStream outputStream) {
        this(outputStream, b);
    }

    public Base64OutputStream(OutputStream outputStream, byte[] bArr) {
        super(outputStream);
        this.c = new byte[3];
        this.e = new byte[512];
        this.h = bArr == null ? b : bArr;
    }

    private void a() throws IOException {
        this.out.write(this.e, 0, this.f);
        this.f = 0;
    }

    private void a(byte[] bArr, int i) throws IOException {
        byte[] bArr2 = this.h;
        if (bArr2 != null) {
            int length = bArr2.length;
            if (this.f + length + 3 >= 512) {
                a();
            }
            if (this.g >= 64) {
                System.arraycopy(this.h, 0, this.e, this.f, length);
                this.f += length;
                this.g = 0;
            }
        }
        byte[] bArr3 = this.e;
        int i2 = this.f;
        int i3 = i2 + 1;
        this.f = i3;
        byte[] bArr4 = a;
        int i4 = i + 0;
        bArr3[i2] = bArr4[(bArr[i4] >>> 2) & 63];
        int i5 = i3 + 1;
        this.f = i5;
        int i6 = i + 1;
        bArr3[i3] = bArr4[((bArr[i4] << 4) & 48) | ((bArr[i6] >>> 4) & 15)];
        int i7 = i5 + 1;
        this.f = i7;
        int i8 = i + 2;
        bArr3[i5] = bArr4[((bArr[i6] << 2) & 60) | ((bArr[i8] >>> 6) & 3)];
        this.f = i7 + 1;
        bArr3[i7] = bArr4[bArr[i8] & 63];
        this.g += 4;
    }

    public static byte[] getLineBreak() {
        return (byte[]) b.clone();
    }

    public static void setLineBreak(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Line break must not be null!");
        }
        b = (byte[]) bArr.clone();
    }

    void a(boolean z) throws IOException {
        if (this.d > 0) {
            OutputStream outputStream = this.out;
            byte[] bArr = a;
            outputStream.write(bArr[(this.c[0] >>> 2) & 63]);
            if (this.d > 1) {
                OutputStream outputStream2 = this.out;
                byte[] bArr2 = this.c;
                outputStream2.write(bArr[((bArr2[1] >>> 4) & 15) | ((bArr2[0] << 4) & 48)]);
                this.out.write(bArr[(this.c[1] << 2) & 60]);
            } else {
                this.out.write(bArr[(this.c[0] << 4) & 48]);
                this.out.write(61);
            }
            this.out.write(61);
        }
        if (z) {
            this.out.flush();
        }
        this.d = 0;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public synchronized void flush() throws IOException {
        a(true);
    }

    public byte[] getInstanceLineBreak() {
        return (byte[]) this.h.clone();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public synchronized void write(int i) throws IOException {
        write(new byte[]{(byte) i}, 0, 1);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public synchronized void write(byte[] bArr, int i, int i2) throws IOException {
        int i3 = i2 + i;
        if ((i | i2 | (bArr.length - i3) | i3) < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i4 = this.d;
        if (i4 > 0 && i2 + i4 >= 3) {
            int i5 = 3 - i4;
            System.arraycopy(bArr, i, this.c, i4, i5);
            i += i5;
            a(this.c, 0);
            this.d = 0;
        }
        int i6 = i3 - 2;
        while (i < i6) {
            a(bArr, i);
            i += 3;
        }
        a();
        int i7 = i3 - i;
        System.arraycopy(bArr, i, this.c, this.d, i7);
        this.d += i7;
    }
}

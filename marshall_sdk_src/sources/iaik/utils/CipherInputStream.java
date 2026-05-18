package iaik.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.NullCipher;

/* JADX INFO: loaded from: classes2.dex */
public class CipherInputStream extends FilterInputStream {
    private Cipher a;
    private byte[] b;
    private int c;
    private byte[] d;
    private int e;
    private int f;
    private boolean g;

    protected CipherInputStream(InputStream inputStream) {
        this(inputStream, new NullCipher(), 1024);
    }

    public CipherInputStream(InputStream inputStream, Cipher cipher) {
        this(inputStream, cipher, 1024);
    }

    public CipherInputStream(InputStream inputStream, Cipher cipher, int i) {
        super(inputStream);
        this.e = 0;
        this.f = 0;
        this.g = false;
        this.a = cipher;
        int blockSize = cipher.getBlockSize();
        if (i < blockSize) {
            i = blockSize;
        } else if (blockSize > 0) {
            i -= i % blockSize;
        }
        this.b = new byte[i];
    }

    private int a(int i) throws IOException {
        int i2;
        if (this.g) {
            return -1;
        }
        int length = this.b.length - this.c;
        if (i > 0) {
            if (length < i) {
                i = length;
            }
            length = i;
        }
        do {
            i2 = this.in.read(this.b, this.c, length);
        } while (i2 == 0);
        if (i2 == -1) {
            this.g = true;
            i2 = 0;
        }
        int i3 = this.c + i2;
        this.c = i3;
        try {
            if (this.g) {
                this.d = this.a.doFinal();
            } else {
                this.d = this.a.update(this.b, 0, i3);
                this.c = 0;
            }
            int length2 = this.d.length;
            this.e = length2;
            this.f = 0;
            return length2;
        } catch (GeneralSecurityException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("en/decryption error: ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        int iAvailable;
        if (this.e == 0 && (iAvailable = this.in.available()) != 0) {
            a(iAvailable);
        }
        return this.e;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        do {
            int i = this.e;
            if (i > 0) {
                this.e = i - 1;
                byte[] bArr = this.d;
                int i2 = this.f;
                this.f = i2 + 1;
                return bArr[i2] & 255;
            }
        } while (a(-1) != -1);
        return -1;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        do {
            int i4 = this.e;
            if (i4 >= i2) {
                System.arraycopy(this.d, this.f, bArr, i + i3, i2);
                this.e -= i2;
                this.f += i2;
                return i3 + i2;
            }
            if (i4 > 0) {
                System.arraycopy(this.d, this.f, bArr, i + i3, i4);
                int i5 = this.e;
                i2 -= i5;
                i3 += i5;
                this.e = 0;
            }
        } while (a(-1) != -1);
        if (i3 == 0) {
            return -1;
        }
        return i3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        return read(new byte[(int) j]);
    }
}

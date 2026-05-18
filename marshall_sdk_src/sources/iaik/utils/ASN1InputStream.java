package iaik.utils;

import iaik.asn1.ASN1;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class ASN1InputStream extends Base64InputStream {
    boolean a;
    boolean b;
    StringBuffer c;

    public ASN1InputStream(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 100);
        boolean z = true;
        super(bufferedInputStream, true);
        this.decoding[45] = -3;
        this.c = new StringBuffer();
        this.in.mark(1);
        int i = this.in.read();
        this.in.reset();
        if (i != 45 && ((i < 65 || i > 77) && (i < 103 || i > 122))) {
            z = false;
        }
        this.a = z;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        int i;
        if (!this.a) {
            return this.in.available();
        }
        if (this.b) {
            this.in.mark(80);
            while (true) {
                i = this.in.read();
                if (i != 10 && i != 13) {
                    break;
                }
            }
            if (i == 45) {
                while (this.in.read() != 10) {
                }
                int iAvailable = this.in.available();
                this.in.mark(-1);
                this.b = false;
                return iAvailable;
            }
            this.in.reset();
        }
        return this.in.available();
    }

    @Override // iaik.utils.Base64InputStream
    protected void notify(byte[] bArr) throws IOException {
        int i;
        this.c.setLength(0);
        this.c.append("----");
        do {
            i = this.in.read();
            this.c.append((char) i);
            if (i == 10 || i == 13) {
                break;
            }
        } while (i != -1);
        String upperCase = this.c.toString().toUpperCase(Locale.US);
        if (upperCase.startsWith(ASN1.startLine)) {
            this.b = true;
        } else if (upperCase.startsWith("-----END")) {
            this.b = false;
        }
    }

    @Override // iaik.utils.Base64InputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        return this.a ? super.read() : this.in.read();
    }

    @Override // iaik.utils.Base64InputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.a ? super.read(bArr, i, i2) : this.in.read(bArr, i, i2);
    }
}

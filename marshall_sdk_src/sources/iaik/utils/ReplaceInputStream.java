package iaik.utils;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes2.dex */
public class ReplaceInputStream extends FilterInputStream {
    private static String h = System.getProperty("line.separator", "\n");
    private byte[] a;
    private int b;
    private int c;
    private boolean d;
    private String[][] e;
    private BufferedReader f;
    private int g;

    public ReplaceInputStream(InputStream inputStream, String[][] strArr) {
        super(inputStream);
        this.b = 0;
        this.c = 0;
        this.d = false;
        this.f = null;
        this.g = 0;
        this.e = strArr;
        this.f = Util.getASCIIReader(this.in);
    }

    private int a() throws IOException {
        if (this.d) {
            return -1;
        }
        String line = this.f.readLine();
        if (line == null) {
            this.d = true;
            return -1;
        }
        for (int i = 0; i < this.e.length; i++) {
            int length = 0;
            while (true) {
                int iIndexOf = line.indexOf(this.e[i][0], length);
                if (iIndexOf != -1) {
                    String strSubstring = line.substring(0, iIndexOf);
                    String strSubstring2 = line.substring(this.e[i][0].length() + iIndexOf);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(strSubstring);
                    stringBuffer.append(this.e[i][1]);
                    stringBuffer.append(strSubstring2);
                    line = stringBuffer.toString();
                    this.g++;
                    length = iIndexOf + this.e[i][1].length();
                }
            }
        }
        byte[] aSCIIBytes = Util.toASCIIBytes(line.concat(h));
        this.a = aSCIIBytes;
        this.c = 0;
        int length2 = aSCIIBytes.length;
        this.b = length2;
        return length2;
    }

    public static void setLineSeparator(String str) {
        h = str;
    }

    public int getReplaceCount() {
        return this.g;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        do {
            int i = this.b;
            if (i > 0) {
                this.b = i - 1;
                byte[] bArr = this.a;
                int i2 = this.c;
                this.c = i2 + 1;
                return bArr[i2] & 255;
            }
        } while (a() != -1);
        return -1;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        do {
            int i4 = this.b;
            if (i4 >= i2) {
                System.arraycopy(this.a, this.c, bArr, i + i3, i2);
                this.b -= i2;
                this.c += i2;
                return i3 + i2;
            }
            if (i4 > 0) {
                System.arraycopy(this.a, this.c, bArr, i + i3, i4);
                int i5 = this.b;
                i2 -= i5;
                i3 += i5;
                this.b = 0;
            }
        } while (a() != -1);
        if (i3 == 0) {
            return -1;
        }
        return i3;
    }
}

package iaik.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class NotifyEOFInputStream extends FilterInputStream {
    Vector a;
    boolean b;

    public NotifyEOFInputStream(InputStream inputStream) {
        super(inputStream);
        this.b = false;
        this.a = new Vector();
    }

    private void a() throws IOException {
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            ((EOFListener) enumerationElements.nextElement()).notifyEOF();
        }
    }

    public void addEOFListener(EOFListener eOFListener) {
        this.a.addElement(eOFListener);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (this.b) {
            return -1;
        }
        int i = this.in.read();
        if (i == -1) {
            this.b = true;
            a();
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.b) {
            return -1;
        }
        int i3 = this.in.read(bArr, i, i2);
        if (i3 == -1) {
            this.b = true;
            a();
        }
        return i3;
    }
}

package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class MaxFragmentLength extends Extension implements Cloneable {
    public static final int L_1024 = 2;
    public static final int L_2048 = 3;
    public static final int L_4096 = 4;
    public static final int L_512 = 1;
    public static final ExtensionType TYPE = new ExtensionType(1, "max_fragment_length");
    private int b;
    private int c;

    static int b(int i) throws IllegalArgumentException {
        if (i == 1) {
            return 512;
        }
        if (i == 2) {
            return 1024;
        }
        if (i == 3) {
            return 2048;
        }
        if (i == 4) {
            return 4096;
        }
        StringBuffer stringBuffer = new StringBuffer("Invalid maximum fragment length id: ");
        stringBuffer.append(i);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    static int c(int i) throws IllegalArgumentException {
        if (i == 512) {
            return 1;
        }
        if (i == 1024) {
            return 2;
        }
        if (i == 2048) {
            return 3;
        }
        if (i == 4096) {
            return 4;
        }
        StringBuffer stringBuffer = new StringBuffer("Invalid maximum fragment length: ");
        stringBuffer.append(i);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public MaxFragmentLength() {
        super(TYPE);
    }

    public MaxFragmentLength(int i) throws IllegalArgumentException {
        this();
        this.c = b(i);
        this.b = i;
    }

    public int getMflId() {
        return this.b;
    }

    public int getLength() {
        return this.c;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        MaxFragmentLength maxFragmentLength = (MaxFragmentLength) super.clone();
        maxFragmentLength.b = this.b;
        maxFragmentLength.c = this.c;
        return maxFragmentLength;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(" Maximum fragment length: ");
        stringBuffer.append(this.c);
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        int iF = abVar.f();
        if (iF != 1) {
            StringBuffer stringBuffer = new StringBuffer("Invalid length ");
            stringBuffer.append(iF);
            stringBuffer.append(" of max_fragment_extension! Expected 1");
            throw new SSLException(stringBuffer.toString(), 2, 50, false);
        }
        int iK = abVar.k();
        try {
            this.c = b(iK);
            this.b = iK;
            return 3;
        } catch (IllegalArgumentException unused) {
            StringBuffer stringBuffer2 = new StringBuffer("Invalid length ");
            stringBuffer2.append(iF);
            stringBuffer2.append(" of max_fragment_extension! Expected 1");
            throw new SSLException(stringBuffer2.toString(), 2, 47, false);
        }
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        if (this.b == 0) {
            throw new SSLException("Cannot write max_fragment_length extension. Max fragment length not set!", 2, 80, false);
        }
        vVar.a(1);
        vVar.d(this.b);
    }

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        int mflId;
        if (this.b == 0 || this.b == (mflId = ((MaxFragmentLength) extension).getMflId())) {
            return extension;
        }
        StringBuffer stringBuffer = new StringBuffer("Peer sent wrong max fragment value (");
        stringBuffer.append(mflId);
        stringBuffer.append("). Expected ");
        stringBuffer.append(this.b);
        stringBuffer.append(".");
        throw new SSLException(stringBuffer.toString(), 2, 47, false);
    }

    int a(int i, int i2) {
        int i3 = this.c;
        return i3 != 0 ? i2 == -1 ? i3 + 325 : i2 + i3 : i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MaxFragmentLength) {
            MaxFragmentLength maxFragmentLength = (MaxFragmentLength) obj;
            if (this.b == maxFragmentLength.b && this.c == maxFragmentLength.c) {
                return true;
            }
        }
        return false;
    }
}

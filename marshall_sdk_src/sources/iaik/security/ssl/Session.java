package iaik.security.ssl;

import java.io.Serializable;
import java.security.cert.X509Certificate;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class Session implements Serializable {
    CipherSuite a;
    CompressionMethod b;
    SessionID c;
    X509Certificate[] d;
    String e;
    byte[] f;
    int g;
    boolean h;
    long i;
    byte[] j;
    private Object k;
    private boolean l;
    private int m;
    private boolean n;

    Session() {
        this.a = CipherSuite.e;
        this.b = new NullCompression();
        this.h = false;
        this.i = System.currentTimeMillis();
        this.m = 0;
        this.l = false;
        this.n = false;
    }

    Session(Random random, int i) {
        this();
        this.c = new SessionID(random);
        this.g = i;
    }

    Session(int i) {
        this();
        this.c = new SessionID();
        this.g = i;
    }

    SessionID c() {
        return this.c;
    }

    void a(SessionID sessionID) {
        this.c = sessionID;
    }

    public Object getID() {
        return this.c;
    }

    public long getCreationTime() {
        return this.i;
    }

    public X509Certificate[] getPeerCertificateChain() {
        return this.d;
    }

    public String getPSKIdentity() {
        return this.e;
    }

    public byte[] getMasterSecret() {
        return this.f;
    }

    public CipherSuite getCipherSuite() {
        return this.a;
    }

    public CompressionMethod getCompressionMethod() {
        return this.b;
    }

    public int getVersion() {
        return this.g;
    }

    public boolean isValid() {
        return this.h;
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public boolean equals(Object obj) {
        boolean zEquals;
        boolean z;
        byte[] bArr;
        byte[] bArr2;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Session)) {
            return false;
        }
        Session session = (Session) obj;
        if (this.c.b() || session.c.b()) {
            zEquals = true;
            z = false;
        } else {
            zEquals = this.c.equals(session.c);
            z = zEquals;
        }
        return (!zEquals || (bArr = this.f) == null || (bArr2 = session.f) == null) ? z : Utils.equalsBlock(bArr, bArr2);
    }

    public void invalidate() {
        this.h = false;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.d != null) {
            StringBuffer stringBuffer2 = new StringBuffer("Peer Certificate: ");
            stringBuffer2.append(this.d.length);
            stringBuffer2.append(" cert(s)\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.e != null) {
            StringBuffer stringBuffer3 = new StringBuffer("PSK identity: ");
            stringBuffer3.append(this.e);
            stringBuffer.append(stringBuffer3.toString());
        }
        StringBuffer stringBuffer4 = new StringBuffer("Creation time: ");
        stringBuffer4.append(this.i);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        StringBuffer stringBuffer5 = new StringBuffer("Valid: ");
        stringBuffer5.append(this.h ? "yes" : "no");
        stringBuffer5.append("\n");
        stringBuffer.append(stringBuffer5.toString());
        StringBuffer stringBuffer6 = new StringBuffer("Cipher suite: ");
        stringBuffer6.append(this.a.getName());
        stringBuffer6.append("\n");
        stringBuffer.append(stringBuffer6.toString());
        StringBuffer stringBuffer7 = new StringBuffer("Compression method: ");
        stringBuffer7.append(this.b.getName());
        stringBuffer7.append("\n");
        stringBuffer.append(stringBuffer7.toString());
        StringBuffer stringBuffer8 = new StringBuffer("SessionID: ");
        stringBuffer8.append(this.c);
        stringBuffer8.append("\n");
        stringBuffer.append(stringBuffer8.toString());
        return stringBuffer.toString();
    }

    void a(Object obj) {
        this.k = obj;
    }

    Object b() {
        return this.k;
    }

    boolean a(long j, long j2) {
        if (j2 - this.i <= j) {
            return true;
        }
        invalidate();
        return false;
    }

    void a(boolean z) {
        this.l = z;
    }

    boolean d() {
        return this.l;
    }

    void a(int i) {
        this.m = i;
    }

    int a() {
        return this.m;
    }

    void b(boolean z) {
        this.n = z;
    }

    boolean e() {
        return this.n;
    }

    void a(ExtensionList extensionList) {
        if (extensionList.getExtension(TruncatedHMAC.TYPE) != null) {
            a(true);
        }
        MaxFragmentLength maxFragmentLength = (MaxFragmentLength) extensionList.getExtension(MaxFragmentLength.TYPE);
        if (maxFragmentLength != null) {
            a(maxFragmentLength.getLength());
        }
        if (extensionList.getExtension(ExtendedMasterSecret.TYPE) != null) {
            b(true);
        }
    }
}

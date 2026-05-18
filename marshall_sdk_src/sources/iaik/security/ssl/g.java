package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class g extends x {
    private int a;
    private byte[] b;

    public g(int i, byte[] bArr) throws SSLException {
        super(22);
        if (i < 1 || i > 255) {
            StringBuffer stringBuffer = new StringBuffer("Status type (");
            stringBuffer.append(i);
            stringBuffer.append(") out of range. Must be between 0 and 255");
            throw new SSLException(stringBuffer.toString());
        }
        if (bArr == null) {
            throw new IllegalArgumentException("Status response must not be null!");
        }
        this.a = i;
        this.b = bArr;
    }

    g(ab abVar) throws IOException {
        super(22);
        a(abVar);
    }

    public int b() {
        return this.a;
    }

    public byte[] a() {
        return this.b;
    }

    void a(ab abVar) throws IOException {
        int iH = abVar.h();
        if (iH == 0) {
            throw new SSLException("Certificate Status message must not be empty!", 2, 50, false);
        }
        int iK = abVar.k();
        this.a = iK;
        if (iK < 1 || iK > 255) {
            StringBuffer stringBuffer = new StringBuffer("Status type (");
            stringBuffer.append(this.a);
            stringBuffer.append(") out of range. Must be between 0 and 255");
            throw new SSLException(stringBuffer.toString(), 2, 50, false);
        }
        int i = iH - 1;
        if (i > 0) {
            byte[] bArr = new byte[i];
            this.b = bArr;
            if (abVar.a(bArr) != this.b.length) {
                throw new SSLException("Invalid status length", 2, 50, false);
            }
        } else {
            this.b = new byte[0];
        }
        if (i - this.b.length != 0) {
            throw new SSLException("Invalid length field in CertificateStatus message!", 2, 50, false);
        }
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        int length = this.b.length + 1;
        agVar.g(22);
        agVar.e(length);
        agVar.g(this.a);
        agVar.write(this.b);
    }
}

package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class CertificateStatusRequest extends Extension implements Cloneable {
    public static final int STATUS_TYPE_OCSP = 1;
    public static final ExtensionType TYPE = new ExtensionType(5, "status_request");
    private int b;
    private byte[] c;

    public CertificateStatusRequest() {
        super(TYPE);
        this.b = 1;
    }

    public CertificateStatusRequest(int i, byte[] bArr) {
        this();
        if (i < 1 || i > 255) {
            StringBuffer stringBuffer = new StringBuffer("Status type (");
            stringBuffer.append(i);
            stringBuffer.append(") out of range. Must be between 0 and 255");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.b = i;
        this.c = bArr;
    }

    public int getStatusType() {
        return this.b;
    }

    public byte[] getStatusRequest() {
        return this.c;
    }

    void a(byte[] bArr) {
        this.c = bArr;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        CertificateStatusRequest certificateStatusRequest = (CertificateStatusRequest) super.clone();
        certificateStatusRequest.b = this.b;
        byte[] bArr = this.c;
        if (bArr != null) {
            certificateStatusRequest.c = (byte[]) bArr.clone();
        }
        return certificateStatusRequest;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer(" Status type: ");
        stringBuffer2.append(this.b);
        stringBuffer.append(stringBuffer2.toString());
        if (this.b == 1) {
            stringBuffer.append(" (ocsp)");
        }
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        int iF = abVar.f();
        if (iF > 0) {
            int iK = abVar.k();
            this.b = iK;
            if (iK < 1 || iK > 255) {
                StringBuffer stringBuffer = new StringBuffer("Status type (");
                stringBuffer.append(this.b);
                stringBuffer.append(") out of range. Must be between 0 and 255");
                throw new SSLException(stringBuffer.toString(), 2, 50, false);
            }
            if (iF > 1) {
                byte[] bArr = new byte[iF - 1];
                this.c = bArr;
                if (abVar.a(bArr) != this.c.length) {
                    throw new SSLException("Invalid status request length", 2, 50, false);
                }
            }
        } else if (!d()) {
            throw new SSLException("Status request sent by client must not be empty!", 2, 50, false);
        }
        return iF + 2;
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        if (!d()) {
            vVar.a(0);
            return;
        }
        int size = vVar.size();
        vVar.write(v.a);
        vVar.d(this.b);
        byte[] bArr = this.c;
        if (bArr != null) {
            vVar.write(bArr);
        }
        vVar.b((vVar.size() - size) - 2, size);
    }

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return d() ? this : extension;
    }
}

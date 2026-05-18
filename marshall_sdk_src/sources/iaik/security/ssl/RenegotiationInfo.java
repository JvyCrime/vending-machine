package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class RenegotiationInfo extends Extension implements Cloneable {
    public static final ExtensionType b = new ExtensionType(65281, "renegotiation_info");
    private byte[] c;

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return extension;
    }

    public RenegotiationInfo() {
        super(b);
    }

    RenegotiationInfo(byte[] bArr) {
        this();
        this.c = bArr;
    }

    byte[] e() {
        return this.c;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        RenegotiationInfo renegotiationInfo = (RenegotiationInfo) super.clone();
        byte[] bArr = this.c;
        if (bArr != null) {
            renegotiationInfo.c = (byte[]) bArr.clone();
        }
        return renegotiationInfo;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.c != null) {
            stringBuffer.append(" Verify Data included");
        } else {
            stringBuffer.append(" empty");
        }
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        int iF = abVar.f();
        if (iF > 0) {
            int iK = abVar.k();
            if (iK > 0) {
                byte[] bArr = new byte[iK];
                this.c = bArr;
                abVar.a(bArr);
                if (this.c.length != iK) {
                    throw new SSLException("Invalid length of RenegotiationInfo extension!", 2, 50, false);
                }
            }
            return iF + 2;
        }
        throw new SSLException("Invalid length of RenegotiationInfo extension!", 2, 50, false);
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        byte[] bArr = this.c;
        if (bArr == null) {
            vVar.a(1);
            vVar.d(0);
        } else {
            vVar.a(bArr.length + 1);
            vVar.c(this.c);
        }
    }
}

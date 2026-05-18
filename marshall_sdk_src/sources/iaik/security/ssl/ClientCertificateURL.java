package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class ClientCertificateURL extends Extension implements Cloneable {
    public static final ExtensionType TYPE = new ExtensionType(2, "client_certificate_url");

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return this;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        return " empty";
    }

    public ClientCertificateURL() {
        super(TYPE);
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        return (ClientCertificateURL) super.clone();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        int iF = abVar.f();
        if (iF == 0) {
            return 2;
        }
        StringBuffer stringBuffer = new StringBuffer("Invalid length ");
        stringBuffer.append(iF);
        stringBuffer.append(" of client_certificate_url! Expected 0");
        throw new SSLException(stringBuffer.toString(), 2, 50, false);
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        vVar.a(0);
    }
}

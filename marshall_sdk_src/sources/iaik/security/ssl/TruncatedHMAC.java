package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class TruncatedHMAC extends Extension implements Cloneable {
    public static final ExtensionType TYPE = new ExtensionType(4, "truncated_hmac");

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return this;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        return " empty";
    }

    public TruncatedHMAC() {
        super(TYPE);
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        return (TruncatedHMAC) super.clone();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        int iF = abVar.f();
        if (iF == 0) {
            return 2;
        }
        StringBuffer stringBuffer = new StringBuffer("Invalid length ");
        stringBuffer.append(iF);
        stringBuffer.append(" of truncated_hmac! Expected 0");
        throw new SSLException(stringBuffer.toString(), 2, 50, false);
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        vVar.a(0);
    }
}

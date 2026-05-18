package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class ExtendedMasterSecret extends Extension implements Cloneable {
    public static final ExtensionType TYPE = new ExtensionType(23, "extended_master_secret");

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return this;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        return " empty";
    }

    public ExtendedMasterSecret() {
        super(TYPE);
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        return (ExtendedMasterSecret) super.clone();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        int iF = abVar.f();
        if (iF == 0) {
            return iF + 2;
        }
        throw new SSLException("Invalid length of ExtendedMasterSecret extension! Must be 0.", 2, 50, false);
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        vVar.a(0);
    }
}

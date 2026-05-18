package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class UnknownExtension extends Extension implements Cloneable {
    private byte[] b;

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) {
        return null;
    }

    UnknownExtension(ExtensionType extensionType) {
        super(extensionType);
    }

    public byte[] getData() {
        return this.b;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        UnknownExtension unknownExtension = (UnknownExtension) super.clone();
        unknownExtension.b = (byte[]) this.b.clone();
        return unknownExtension;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        byte[] bArr = this.b;
        return bArr != null ? Utils.toString(bArr) : "";
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        byte[] bArrG = abVar.g();
        this.b = bArrG;
        return bArrG.length + 2;
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        vVar.a(this.b);
    }
}

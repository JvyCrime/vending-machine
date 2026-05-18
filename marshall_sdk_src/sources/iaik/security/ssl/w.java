package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class w extends x {
    private byte[] a;

    w(byte[] bArr) {
        super(20);
        this.a = null;
        this.a = bArr;
    }

    w(ab abVar) throws IOException {
        super(20);
        this.a = null;
        a(abVar);
    }

    boolean a(byte[] bArr) {
        return Utils.equalsBlock(this.a, bArr);
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(20);
        agVar.b(this.a);
    }

    void a(ab abVar) throws IOException {
        this.a = abVar.i();
    }
}

package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class af extends x {
    private int a;
    private byte[] b;

    af() {
        super(4);
    }

    af(int i, byte[] bArr) {
        this();
        if (i < 0) {
            throw new IllegalArgumentException("Ticket lifetime hint must be >= 0!");
        }
        if (bArr == null) {
            throw new IllegalArgumentException("ticket must not be null!");
        }
        this.a = i;
        this.b = bArr;
    }

    af(ab abVar) throws IOException {
        this();
        a(abVar);
    }

    int b() {
        return this.a;
    }

    byte[] a() {
        return this.b;
    }

    void a(ab abVar) throws IOException {
        int iH = abVar.h();
        this.a = abVar.j();
        int iF = abVar.f();
        if (iF > 0) {
            byte[] bArr = new byte[iF];
            this.b = bArr;
            abVar.a(bArr);
        }
        if (iF + 6 != iH) {
            throw new SSLException("Invalid length value in NewSessionTicket message!", 2, 50, false);
        }
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        byte[] bArr = this.b;
        int length = bArr == null ? 0 : bArr.length;
        agVar.g(4);
        agVar.e(length + 6);
        agVar.f(this.a);
        byte[] bArr2 = this.b;
        if (bArr2 == null) {
            agVar.a(0);
        } else {
            agVar.a(bArr2);
        }
    }
}

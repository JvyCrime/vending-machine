package iaik.security.ssl;

/* JADX INFO: loaded from: classes.dex */
class TicketSession extends Session {
    private byte[] k;
    private long l;

    TicketSession(int i) {
        super(i);
    }

    TicketSession(SessionID sessionID, int i) {
        this.c = sessionID;
        this.g = i;
    }

    void a(byte[] bArr) {
        this.k = bArr;
    }

    byte[] f() {
        return this.k;
    }

    void a(int i, int i2) {
        long j = i * 1000;
        long j2 = i2 * 1000;
        if (j2 > 0 && j > 0) {
            this.l = Math.min(j2, j);
            return;
        }
        if (j2 > 0) {
            j = j2;
        }
        this.l = j;
    }

    int g() {
        return ((int) this.l) / 1000;
    }

    @Override // iaik.security.ssl.Session
    boolean a(long j, long j2) {
        long j3 = this.l;
        if (j3 > 0) {
            j = j3;
        }
        if (j2 - this.i <= j) {
            return true;
        }
        invalidate();
        return false;
    }
}

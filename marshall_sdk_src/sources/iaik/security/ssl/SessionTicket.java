package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class SessionTicket extends Extension implements Cloneable {
    public static final ExtensionType TYPE = new ExtensionType(35, "session_ticket");
    private TicketKeysManager b;
    private int c;
    private TicketKeyBag d;
    private byte[] e;

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return extension;
    }

    public SessionTicket() {
        super(TYPE);
        this.c = 0;
    }

    public SessionTicket(TicketKeyBag ticketKeyBag) {
        this();
        this.d = ticketKeyBag;
    }

    SessionTicket(byte[] bArr) {
        this();
        this.e = bArr;
    }

    public void setTicketLifetime(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Ticket lifetime must be >= 0!");
        }
        this.c = i;
    }

    public void setTicketKeysManager(TicketKeysManager ticketKeysManager) {
        this.b = ticketKeysManager;
    }

    TicketKeysManager g() {
        return this.b;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        SessionTicket sessionTicket = (SessionTicket) super.clone();
        sessionTicket.c = this.c;
        TicketKeyBag ticketKeyBag = this.d;
        if (ticketKeyBag != null) {
            sessionTicket.d = (TicketKeyBag) ticketKeyBag.clone();
        }
        return sessionTicket;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.e != null) {
            stringBuffer.append(" Ticket included");
        } else {
            stringBuffer.append(" empty");
        }
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        int iF = abVar.f();
        if (iF > 0) {
            byte[] bArr = new byte[iF];
            this.e = bArr;
            abVar.a(bArr);
            if (this.e.length != iF) {
                throw new SSLException("Invalid length of SessionTicket extension!", 2, 50, false);
            }
        }
        return iF + 2;
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        if (this.e == null || !d()) {
            vVar.a(0);
        } else {
            vVar.a(this.e);
        }
    }

    int h() {
        return this.c;
    }

    TicketKeyBag f() {
        return this.d;
    }

    byte[] e() {
        return this.e;
    }
}

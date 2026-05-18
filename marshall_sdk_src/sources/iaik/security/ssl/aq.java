package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class aq extends x {
    int a;
    am b;
    SessionID c;
    CipherSuite d;
    CompressionMethod e;
    SSLTransport f;
    ExtensionList g;
    ExtensionList h;
    boolean i;

    aq(Session session, SSLContext sSLContext, SSLTransport sSLTransport, boolean z) {
        super(2);
        this.a = session.g;
        this.b = new am(sSLContext.getRandomGenerator(), this.a);
        this.c = session.c();
        this.d = session.getCipherSuite();
        this.e = session.getCompressionMethod();
        this.i = z;
        this.f = sSLTransport;
    }

    aq(ab abVar, SSLContext sSLContext, SSLTransport sSLTransport, ExtensionList extensionList) throws IOException {
        super(2);
        this.f = sSLTransport;
        this.g = extensionList;
        a(abVar);
    }

    byte[] d() {
        return this.b.a();
    }

    SessionID e() {
        return this.c;
    }

    CipherSuite a() {
        return this.d;
    }

    CompressionMethod b() {
        return this.e;
    }

    int f() {
        return this.a;
    }

    ExtensionList c() {
        return this.h;
    }

    void a(ExtensionList extensionList) {
        this.g = extensionList;
    }

    @Override // iaik.security.ssl.aj
    public void a(ag agVar) throws IOException {
        agVar.g(2);
        int iA = this.c.a() + 38;
        ExtensionList extensionListA = this.g;
        v vVar = null;
        if (this.i) {
            if (extensionListA == null) {
                extensionListA = new ExtensionList();
                extensionListA.addExtension(new RenegotiationInfo());
            } else if (extensionListA.getExtension(RenegotiationInfo.b) == null) {
                extensionListA = this.g.a((Extension) new RenegotiationInfo(), true);
            }
        } else if (extensionListA == null || !extensionListA.hasExtensions()) {
            extensionListA = null;
        }
        if (extensionListA != null && extensionListA.hasExtensions()) {
            vVar = new v(Math.min(agVar.b(), 4096));
            extensionListA.a(vVar);
            iA += vVar.size();
            SSLTransport sSLTransport = this.f;
            StringBuffer stringBuffer = new StringBuffer("Selecting extensions: ");
            stringBuffer.append(extensionListA);
            sSLTransport.debug(stringBuffer.toString());
        }
        agVar.e(iA);
        agVar.a(this.a);
        this.b.a(agVar);
        this.c.a(agVar);
        this.d.a(agVar);
        agVar.g(this.e.getID());
        if (vVar != null) {
            agVar.write(vVar.a(), 0, vVar.size());
        }
    }

    void a(ab abVar) throws IOException {
        int iH = abVar.h();
        this.a = abVar.f();
        this.b = new am(abVar);
        this.c = new SessionID(abVar);
        this.d = CipherSuite.a(abVar.f());
        this.e = CompressionMethod.a(abVar.k());
        int iC = this.b.c() + 6 + this.c.a();
        if (iC < iH) {
            ExtensionList extensionList = new ExtensionList();
            this.h = extensionList;
            if (iC + extensionList.a(abVar, this.g, this.f) != iH) {
                throw new SSLException("ServerHello size does not match to length field!", 2, 50, false);
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer("Protocol Version: ");
        stringBuffer2.append(Utils.c(this.a));
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer("SSLRandom: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer("SessionID: ");
        stringBuffer4.append(this.c);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        StringBuffer stringBuffer5 = new StringBuffer("CipherSuite: ");
        stringBuffer5.append(this.d.getName());
        stringBuffer.append(stringBuffer5.toString());
        StringBuffer stringBuffer6 = new StringBuffer("\nCompressionMethods: ");
        stringBuffer6.append(this.e.getName());
        stringBuffer.append(stringBuffer6.toString());
        if (this.g != null) {
            StringBuffer stringBuffer7 = new StringBuffer("\nLocal extensions: ");
            stringBuffer7.append(this.g.toString());
            stringBuffer.append(stringBuffer7.toString());
        }
        if (this.h != null) {
            StringBuffer stringBuffer8 = new StringBuffer("\nPeer extensions: ");
            stringBuffer8.append(this.h.toString());
            stringBuffer.append(stringBuffer8.toString());
        }
        return stringBuffer.toString();
    }
}

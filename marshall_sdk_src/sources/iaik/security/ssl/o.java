package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class o extends x {
    int a;
    am b;
    SessionID c;
    CipherSuiteList d;
    CompressionMethod[] e;
    int f;
    SSLTransport g;
    ExtensionList h;
    ExtensionList i;

    o(Session session, SSLContext sSLContext, int i, int i2, SSLTransport sSLTransport, ExtensionList extensionList) {
        super(1);
        this.f = i;
        this.a = i2;
        this.b = new am(sSLContext.getRandomGenerator(), i);
        this.c = session.c();
        this.d = sSLContext.getEnabledCipherSuiteList();
        this.e = sSLContext.getEnabledCompressionMethods();
        this.g = sSLTransport;
        this.h = extensionList;
    }

    o(ab abVar, int i, SSLContext sSLContext, SSLTransport sSLTransport, ExtensionList extensionList) throws IOException {
        super(1);
        this.f = i;
        this.g = sSLTransport;
        this.h = extensionList;
        a(abVar);
    }

    byte[] d() {
        return this.b.a();
    }

    byte[] e() {
        return this.b.b();
    }

    SessionID f() {
        return this.c;
    }

    CipherSuiteList a() {
        return this.d;
    }

    CompressionMethod[] b() {
        return this.e;
    }

    int g() {
        return this.a;
    }

    ExtensionList c() {
        return this.i;
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        if (this.f < 768) {
            b(agVar);
        } else {
            c(agVar);
        }
    }

    void b(ag agVar) throws IOException {
        CipherSuiteList cipherSuiteListB = this.d.b();
        int size = cipherSuiteListB.size();
        boolean z = this.a > 2;
        if (z) {
            size += this.d.size();
        }
        byte[] id = this.c.getID();
        byte[] bArrB = this.b.b();
        agVar.write(1);
        agVar.a(this.a);
        agVar.a(size * 3);
        agVar.a(id.length);
        agVar.a(bArrB.length);
        cipherSuiteListB.a(agVar);
        if (z) {
            this.d.a(agVar);
        }
        agVar.write(id);
        agVar.write(bArrB);
    }

    void c(ag agVar) throws IOException {
        v vVar;
        int size;
        int i = !this.g.f.z ? 1 : 0;
        ExtensionList extensionListA = this.h;
        if (extensionListA == null || !extensionListA.hasExtensions()) {
            vVar = null;
            size = 0;
        } else {
            v vVar2 = new v(Math.min(agVar.b(), 4096));
            if (this.h.getExtension(RenegotiationInfo.b) == null) {
                extensionListA = this.h.a((Extension) new RenegotiationInfo(), true);
            }
            extensionListA.a(vVar2);
            size = vVar2.size();
            SSLTransport sSLTransport = this.g;
            StringBuffer stringBuffer = new StringBuffer("Sending extensions: ");
            stringBuffer.append(extensionListA);
            sSLTransport.debug(stringBuffer.toString());
            vVar = vVar2;
            i = 0;
        }
        agVar.g(1);
        agVar.e(this.c.a() + 38 + ((this.d.size() + i) * 2) + this.e.length + size);
        agVar.a(this.a);
        this.b.a(agVar);
        this.c.a(agVar);
        this.d.a(agVar, i > 0);
        CompressionMethod.a(agVar, this.e);
        if (vVar != null) {
            agVar.write(vVar.a(), 0, vVar.size());
        }
    }

    void a(ab abVar) throws IOException {
        if (this.f < 768) {
            b(abVar);
        } else {
            c(abVar);
        }
    }

    void b(ab abVar) throws IOException {
        int iK = abVar.k();
        if (iK != 1) {
            StringBuffer stringBuffer = new StringBuffer("Unexpected message, not a client hello: ");
            stringBuffer.append(iK);
            throw new SSLException(stringBuffer.toString());
        }
        this.a = abVar.f();
        int iF = abVar.f();
        int iF2 = abVar.f();
        int iF3 = abVar.f();
        this.d = new CipherSuiteList(abVar, iF, false);
        this.e = CompressionMethod.a;
        byte[] bArr = new byte[iF2];
        abVar.a(bArr);
        this.c = new SessionID(bArr);
        this.b = new am(abVar, iF3);
    }

    void c(ab abVar) throws IOException {
        int iH = abVar.h();
        this.a = abVar.f();
        this.b = new am(abVar);
        this.c = new SessionID(abVar);
        this.d = new CipherSuiteList(abVar);
        this.e = CompressionMethod.a(abVar);
        int iC = this.b.c() + 6 + this.c.a() + (this.d.size() * 2) + this.e.length;
        if (iC < iH) {
            ExtensionList extensionList = new ExtensionList();
            this.i = extensionList;
            if (iC + extensionList.a(abVar, this.h, this.g) != iH) {
                throw new SSLException("ClientHello size does not match to length field!", 2, 50, false);
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
        StringBuffer stringBuffer5 = new StringBuffer("CipherSuites: ");
        stringBuffer5.append(this.d.toString());
        stringBuffer.append(stringBuffer5.toString());
        StringBuffer stringBuffer6 = new StringBuffer("\nCompressionMethods: ");
        stringBuffer6.append(this.e.length);
        stringBuffer.append(stringBuffer6.toString());
        for (int i = 0; i < this.e.length; i++) {
            StringBuffer stringBuffer7 = new StringBuffer("   ");
            stringBuffer7.append(this.e[i].getName());
            stringBuffer.append(stringBuffer7.toString());
        }
        if (this.h != null) {
            StringBuffer stringBuffer8 = new StringBuffer("\nLocal extensions: ");
            stringBuffer8.append(this.h.toString());
            stringBuffer.append(stringBuffer8.toString());
        }
        if (this.i != null) {
            StringBuffer stringBuffer9 = new StringBuffer("\nPeer extensions: ");
            stringBuffer9.append(this.i.toString());
            stringBuffer.append(stringBuffer9.toString());
        }
        return stringBuffer.toString();
    }
}

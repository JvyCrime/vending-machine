package iaik.security.ssl;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.cert.X509Certificate;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SSLTransport implements SSLCommunication {
    private static int t;
    int a;
    PrintWriter b;
    boolean c;
    SSLContext d;
    SSLTransportSpi e;
    y f;
    ag g;
    ab h;
    SSLOutputStream i;
    ai j;
    Session k;
    CipherSuiteList l;
    CompressionMethod[] m;
    X509Certificate[] n = null;
    String o;
    private boolean p;
    private ExtensionList q;
    private ExtensionList r;
    private String s;

    public SSLTransport(SSLTransportSpi sSLTransportSpi) throws IOException {
        this.c = true;
        this.e = sSLTransportSpi;
        this.c = true;
        g();
    }

    void g() throws IOException {
        ExtensionList extensionListI;
        MaxFragmentLength maxFragmentLength;
        int iE;
        ExtensionList extensionListI2;
        boolean zEngineGetUseClientMode = this.e.engineGetUseClientMode();
        SSLContext sSLContextEngineGetContext = this.e.engineGetContext();
        this.d = sSLContextEngineGetContext;
        Objects.requireNonNull(sSLContextEngineGetContext, "SSLContext may not be null!");
        this.b = sSLContextEngineGetContext.getDebugStream();
        int iK = this.d.k();
        int iA = iK == -1 ? 19013 : iK + 16384;
        String strD = d();
        if (zEngineGetUseClientMode) {
            this.d = ((SSLClientContext) this.d).b(strD);
        }
        int[] allowedProtocolVersions = this.d.getAllowedProtocolVersions();
        if (!this.d.g() && (iE = this.d.e()) != -1 && allowedProtocolVersions[1] >= iE && ((extensionListI2 = this.d.i()) == null || !extensionListI2.a())) {
            if (extensionListI2 == null) {
                extensionListI2 = new ExtensionList();
                extensionListI2.a(false);
            }
            extensionListI2.addExtension(new ServerNameList());
            extensionListI2.addExtension(new SignatureAlgorithms());
            try {
                extensionListI2.addExtension(SupportedEllipticCurves.e());
            } catch (IllegalArgumentException unused) {
            }
            try {
                extensionListI2.addExtension(SupportedPointFormats.e());
            } catch (IllegalArgumentException unused2) {
            }
            this.d.setExtensions(extensionListI2);
        }
        int i = 16384;
        if (allowedProtocolVersions[0] > 768 && (extensionListI = this.d.i()) != null && (maxFragmentLength = (MaxFragmentLength) extensionListI.getExtension(MaxFragmentLength.TYPE)) != null) {
            if (maxFragmentLength.c() == 0) {
                iA = maxFragmentLength.a(iA, iK);
            }
            int length = maxFragmentLength.getLength();
            if (length != 0) {
                i = length;
            }
        }
        this.g = new ag(this, iA, i, this.d.h());
        this.h = new ab(this, iA, this.d.l());
        this.i = new SSLOutputStream(this);
        this.j = new ai(this);
        setUseClientMode(zEngineGetUseClientMode);
        this.a = 1;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void setUseClientMode(boolean z) throws IOException {
        if (this.a > 1) {
            throw new SSLException("Mode can only be set before the initial handshake!");
        }
        if (!z && !(this.d instanceof SSLServerContext)) {
            throw new SSLException("SSLServerContext required in server mode!");
        }
        this.p = z;
        if (z) {
            this.f = new ah(this);
        } else {
            this.f = new ap(this);
        }
    }

    @Override // iaik.security.ssl.SSLCommunication
    public boolean getUseClientMode() {
        return this.p;
    }

    public String getRemotePeerName() {
        return this.e.engineGetRemotePeerName();
    }

    public InetAddress getRemoteInetAddress() {
        return this.e.engineGetRemoteInetAddress();
    }

    public Object getRemotePeerId() {
        return this.e.engineGetRemotePeerId();
    }

    String d() {
        SSLTransportSpi sSLTransportSpi = this.e;
        if (sSLTransportSpi instanceof SSLSocketTransport) {
            return ((SSLSocketTransport) sSLTransportSpi).a();
        }
        return null;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public SSLContext getContext() {
        return this.d;
    }

    ag c() {
        return this.g;
    }

    ab b() {
        return this.h;
    }

    OutputStream f() throws IOException {
        return this.e.engineGetOutputStream();
    }

    InputStream e() throws IOException {
        return this.e.engineGetInputStream();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void setAutoHandshake(boolean z) {
        this.c = z;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public X509Certificate[] getPeerCertificateChain() {
        Session session = this.k;
        if (session == null || session.d == null) {
            return this.n;
        }
        return this.k.d;
    }

    public String getPSKIdentity() {
        Session session = this.k;
        String str = session != null ? session.e : null;
        return str == null ? this.o : str;
    }

    ExtensionList a(boolean z) {
        ExtensionList extensionList = this.q;
        return (extensionList == null || !z) ? extensionList : (ExtensionList) extensionList.clone();
    }

    public ExtensionList getActiveExtensions() {
        return a(true);
    }

    void a(ExtensionList extensionList) {
        this.q = extensionList;
    }

    public ExtensionList getPeerExtensions() {
        ExtensionList extensionList = this.r;
        if (extensionList == null) {
            return null;
        }
        return (ExtensionList) extensionList.clone();
    }

    void b(ExtensionList extensionList) {
        this.r = extensionList;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public int getActiveProtocolVersion() {
        Session session = this.k;
        if (session == null) {
            return 0;
        }
        return session.g;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public CipherSuite getActiveCipherSuite() {
        Session session = this.k;
        if (session == null) {
            return null;
        }
        return session.a;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public CompressionMethod getActiveCompressionMethod() {
        Session session = this.k;
        if (session == null) {
            return null;
        }
        return session.b;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public CipherSuiteList getPeerSupportedCipherSuiteList() {
        CipherSuiteList cipherSuiteList = this.l;
        return cipherSuiteList != null ? cipherSuiteList : new CipherSuiteList(getActiveCipherSuite());
    }

    @Override // iaik.security.ssl.SSLCommunication
    public CompressionMethod[] getPeerSupportedCompressionMethods() {
        CompressionMethod[] compressionMethodArr = this.m;
        if (compressionMethodArr != null) {
            return compressionMethodArr;
        }
        CompressionMethod activeCompressionMethod = getActiveCompressionMethod();
        return activeCompressionMethod == null ? new CompressionMethod[0] : new CompressionMethod[]{activeCompressionMethod};
    }

    @Override // iaik.security.ssl.SSLCommunication
    public Session getSession() {
        return this.k;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public synchronized void renegotiate() throws IOException {
        if (this.d.getDisableRenegotiation()) {
            ag agVar = this.g;
            if (agVar != null) {
                agVar.a(2, 80);
            }
            throw new SSLException("Renegotiation disabled!");
        }
        b(getUseClientMode());
    }

    void b(boolean z) throws IOException {
        if (this.a < 3) {
            startHandshake();
            return;
        }
        if (a()) {
            return;
        }
        if (getActiveProtocolVersion() < 768) {
            throw new SSLException("Renegotiation not available for SSL 2.0!");
        }
        if (this.b != null) {
            a("Acquiring locks for renegotiation...");
        }
        synchronized (this.h) {
            synchronized (this.g) {
                this.a = 4;
                try {
                    if (this.b != null) {
                        a("Starting renegotiation...");
                    }
                    this.f.b(z);
                    this.a = 3;
                    if (this.b != null) {
                        a("Renegotiation completed, statistics:");
                        i();
                    }
                } catch (Exception e) {
                    a(e);
                }
            }
        }
    }

    @Override // iaik.security.ssl.SSLCommunication
    public synchronized void startHandshake() throws IOException {
        if (this.a >= 3) {
            return;
        }
        if (a()) {
            return;
        }
        synchronized (this.j) {
            synchronized (this.i) {
                this.a = 2;
                try {
                    if (this.b != null) {
                        a("Starting handshake (iSaSiLk 5.105 Evaluation Version)...");
                    }
                    this.f.d();
                    this.i.a(this.g);
                    this.j.a(this.h);
                    this.k.h = true;
                    this.a = 3;
                    if (this.b != null) {
                        a("Handshake completed, statistics:");
                        i();
                    }
                } catch (Exception e) {
                    a(e);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void a(java.lang.Throwable r9) throws java.io.IOException {
        /*
            r8 = this;
            boolean r0 = r9 instanceof java.io.IOException
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L3e
            boolean r4 = r9 instanceof iaik.security.ssl.SSLException
            java.lang.String r5 = "SSL"
            if (r4 == 0) goto L1e
            r4 = r9
            iaik.security.ssl.SSLException r4 = (iaik.security.ssl.SSLException) r4
            iaik.security.ssl.b r4 = r4.a()
            if (r4 == 0) goto L20
            int r4 = r4.b()
            if (r4 != r1) goto L20
            r4 = 0
            goto L21
        L1e:
            java.lang.String r5 = "IO"
        L20:
            r4 = 1
        L21:
            java.io.PrintWriter r6 = r8.b
            if (r6 == 0) goto L4b
            java.lang.StringBuffer r6 = new java.lang.StringBuffer
            r6.<init>(r5)
            java.lang.String r5 = "Exception while handshaking: "
            r6.append(r5)
            java.lang.String r5 = r9.getMessage()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r8.a(r5)
            goto L4b
        L3e:
            java.io.PrintWriter r4 = r8.b
            if (r4 == 0) goto L47
            java.lang.String r4 = "Exception while handshaking:"
            r8.a(r4)
        L47:
            r8.debug(r9)
            r4 = 1
        L4b:
            if (r4 == 0) goto L80
            int r4 = r8.a
            r5 = 6
            if (r4 >= r5) goto L80
            r4 = 40
            boolean r5 = r9 instanceof iaik.security.ssl.SSLException     // Catch: java.lang.Exception -> L7f
            if (r5 == 0) goto L79
            r5 = r9
            iaik.security.ssl.SSLException r5 = (iaik.security.ssl.SSLException) r5     // Catch: java.lang.Exception -> L7f
            int r6 = r5.getAlertDescription()     // Catch: java.lang.Exception -> L7f
            r7 = -1
            if (r6 != r7) goto L66
            r5.a(r1, r4, r3)     // Catch: java.lang.Exception -> L7f
            goto L79
        L66:
            boolean r6 = r5.alertFromPeer()     // Catch: java.lang.Exception -> L7f
            if (r6 != 0) goto L79
            int r1 = r5.getAlertLevel()     // Catch: java.lang.Exception -> L7f
            int r4 = r5.getAlertDescription()     // Catch: java.lang.Exception -> L7f
            if (r1 != r2) goto L79
            if (r4 == 0) goto L79
            r2 = 0
        L79:
            iaik.security.ssl.ag r5 = r8.g     // Catch: java.lang.Exception -> L7f
            r5.a(r1, r4)     // Catch: java.lang.Exception -> L7f
            goto L80
        L7f:
        L80:
            if (r2 == 0) goto L9e
            r8.a(r3, r3)
            if (r0 != 0) goto L9b
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            java.lang.String r2 = "Fatal SSL handshake error: "
            r1.<init>(r2)
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            r0.<init>(r9)
            throw r0
        L9b:
            java.io.IOException r9 = (java.io.IOException) r9
            throw r9
        L9e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.SSLTransport.a(java.lang.Throwable):void");
    }

    @Override // iaik.security.ssl.SSLCommunication
    public synchronized OutputStream getOutputStream() throws IOException {
        if (this.a < 3 && this.c) {
            startHandshake();
        }
        return this.i;
    }

    @Override // iaik.security.ssl.SSLCommunication
    public synchronized InputStream getInputStream() throws IOException {
        if (this.a < 3 && this.c) {
            startHandshake();
        }
        return this.j;
    }

    @Override // iaik.security.ssl.SSLCommunication, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        shutdown();
        if (this.b != null) {
            a("Closing transport...");
        }
        this.a = 7;
        this.e.engineClose();
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void shutdown() throws IOException {
        a(true, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0064 A[Catch: all -> 0x006c, TryCatch #1 {, blocks: (B:6:0x0007, B:8:0x000e, B:10:0x0012, B:15:0x001a, B:17:0x001e, B:20:0x0026, B:24:0x0030, B:26:0x0035, B:27:0x003b, B:29:0x0043, B:35:0x0064, B:31:0x0049, B:33:0x004d, B:36:0x0067), top: B:43:0x0007, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void a(boolean r4, boolean r5) throws java.io.IOException {
        /*
            r3 = this;
            int r0 = r3.a
            r1 = 5
            if (r0 != r1) goto L6
            return
        L6:
            monitor-enter(r3)
            int r0 = r3.a     // Catch: java.lang.Throwable -> L6c
            r3.a = r1     // Catch: java.lang.Throwable -> L6c
            r1 = 0
            if (r4 != 0) goto L14
            iaik.security.ssl.Session r4 = r3.k     // Catch: java.lang.Throwable -> L6c
            if (r4 == 0) goto L14
            r4.h = r1     // Catch: java.lang.Throwable -> L6c
        L14:
            r4 = 2
            if (r0 < r4) goto L67
            r4 = 4
            if (r0 > r4) goto L67
            java.io.PrintWriter r4 = r3.b     // Catch: java.lang.Throwable -> L6c
            if (r4 == 0) goto L23
            java.lang.String r4 = "Shutting down SSL layer..."
            r3.a(r4)     // Catch: java.lang.Throwable -> L6c
        L23:
            r4 = 3
            if (r0 != r4) goto L2e
            iaik.security.ssl.ag r2 = r3.g     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            r2.flush()     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            goto L2e
        L2c:
            r5 = move-exception
            goto L49
        L2e:
            if (r5 == 0) goto L62
            boolean r5 = r3.p     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            r2 = 1
            if (r5 == 0) goto L3b
            iaik.security.ssl.ag r5 = r3.g     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            r5.a(r2, r1)     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            goto L62
        L3b:
            iaik.security.ssl.SSLContext r5 = r3.d     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            boolean r5 = r5.getDoNotSendServerCloseNotify()     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            if (r5 != 0) goto L62
            iaik.security.ssl.ag r5 = r3.g     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            r5.a(r2, r1)     // Catch: java.lang.Exception -> L2c java.lang.Throwable -> L6c
            goto L62
        L49:
            java.io.PrintWriter r1 = r3.b     // Catch: java.lang.Throwable -> L6c
            if (r1 == 0) goto L62
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> L6c
            java.lang.String r2 = "Ignoring exception shutting down: "
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L6c
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L6c
            r1.append(r5)     // Catch: java.lang.Throwable -> L6c
            java.lang.String r5 = r1.toString()     // Catch: java.lang.Throwable -> L6c
            r3.a(r5)     // Catch: java.lang.Throwable -> L6c
        L62:
            if (r0 < r4) goto L67
            r3.h()     // Catch: java.lang.Throwable -> L6c
        L67:
            r4 = 6
            r3.a = r4     // Catch: java.lang.Throwable -> L6c
            monitor-exit(r3)
            return
        L6c:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.SSLTransport.a(boolean, boolean):void");
    }

    boolean a() throws IOException {
        int i = this.a;
        if (i < 7) {
            return i >= 6;
        }
        throw new SocketException("Socket already closed.");
    }

    private void i() {
        if (this.b == null) {
            return;
        }
        Session session = this.k;
        if (session == null || session.g < 768) {
            debug("Statistics not available in SSL 2.0 mode.");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer("Read ");
        stringBuffer.append(this.h.g);
        stringBuffer.append(" bytes in ");
        stringBuffer.append(this.h.f);
        stringBuffer.append(" records, ");
        StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(stringBuffer.toString()));
        stringBuffer2.append("wrote ");
        stringBuffer2.append(this.g.e);
        stringBuffer2.append(" bytes in ");
        stringBuffer2.append(this.g.d);
        stringBuffer2.append(" records.");
        debug(stringBuffer2.toString());
        this.h.g = 0;
        this.h.f = 0;
        this.g.e = 0;
        this.g.d = 0;
    }

    private void h() {
        Session session;
        if (this.b == null || (session = this.k) == null || session.g < 768) {
            return;
        }
        int i = this.h.c == 0 ? 0 : this.h.e / this.h.c;
        StringBuffer stringBuffer = new StringBuffer("Read ");
        stringBuffer.append(this.h.d);
        stringBuffer.append(" bytes in ");
        stringBuffer.append(this.h.c);
        stringBuffer.append(" records, ");
        StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(stringBuffer.toString()));
        stringBuffer2.append(this.h.e);
        stringBuffer2.append(" bytes net, ");
        stringBuffer2.append(i);
        stringBuffer2.append(" average.");
        debug(stringBuffer2.toString());
        int i2 = this.g.a != 0 ? this.g.c / this.g.a : 0;
        StringBuffer stringBuffer3 = new StringBuffer("Wrote ");
        stringBuffer3.append(this.g.b);
        stringBuffer3.append(" bytes in ");
        stringBuffer3.append(this.g.a);
        stringBuffer3.append(" records, ");
        StringBuffer stringBuffer4 = new StringBuffer(String.valueOf(stringBuffer3.toString()));
        stringBuffer4.append(this.g.c);
        stringBuffer4.append(" bytes net, ");
        stringBuffer4.append(i2);
        stringBuffer4.append(" average.");
        debug(stringBuffer4.toString());
    }

    public void debug(String str) {
        if (this.b != null) {
            a(str);
        }
    }

    void a(String str) {
        if (this.s == null) {
            StringBuffer stringBuffer = new StringBuffer("ssl_debug(");
            int i = t + 1;
            t = i;
            stringBuffer.append(i);
            stringBuffer.append("): ");
            this.s = stringBuffer.toString();
        }
        this.b.println(this.s.concat(str));
    }

    public void debug(Throwable th) {
        if (this.b == null) {
            return;
        }
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        PrintWriter printWriter = new PrintWriter(charArrayWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(charArrayWriter.toCharArray()));
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return;
                } else {
                    a(line);
                }
            } catch (IOException unused) {
                return;
            }
        }
    }

    void a(String str, StringBuffer stringBuffer) {
        if (this.s == null) {
            StringBuffer stringBuffer2 = new StringBuffer("ssl_debug(");
            int i = t + 1;
            t = i;
            stringBuffer2.append(i);
            stringBuffer2.append("): ");
            this.s = stringBuffer2.toString();
        }
        stringBuffer.append(this.s.concat(str));
        stringBuffer.append(Utils.a);
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void setDebugStream(Writer writer) {
        if (writer == null) {
            this.b = null;
            c(false);
        } else {
            if (writer instanceof PrintWriter) {
                this.b = (PrintWriter) writer;
            } else {
                this.b = new PrintWriter(writer, true);
            }
            c(true);
        }
    }

    @Override // iaik.security.ssl.SSLCommunication
    public void setDebugStream(OutputStream outputStream) {
        setDebugStream(outputStream == null ? null : new PrintWriter(outputStream, true));
    }

    private void c(boolean z) {
        y yVar = this.f;
        if (yVar != null) {
            yVar.T = z;
        }
        ag agVar = this.g;
        if (agVar != null) {
            agVar.f = z;
        }
        ab abVar = this.h;
        if (abVar != null) {
            abVar.h = z;
        }
        ai aiVar = this.j;
        if (aiVar != null) {
            aiVar.a = z;
        }
    }

    static {
        Utils.a();
    }
}

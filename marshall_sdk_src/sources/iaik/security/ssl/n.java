package iaik.security.ssl;

import com.felhr.usbserial.UsbSerialDebugger;
import iaik.security.jsse.net.KeyTypeNames;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes.dex */
class n extends y {
    private f V;
    private SSLClientContext W;
    private byte[] X;
    private boolean Y;
    PublicKey a;
    boolean b;

    n(SSLTransport sSLTransport) throws SSLException {
        super(sSLTransport);
        this.a = null;
        this.V = null;
        this.X = null;
        this.b = false;
        this.Y = false;
        this.W = (SSLClientContext) this.D;
        if (this.o != null && !(this.o instanceof ClientTrustDecider)) {
            throw new SSLException("TrustDecider for client SSLSocket must be a ClientTrustDecider.");
        }
    }

    void a(p pVar) throws IOException {
        a((x) pVar);
        if (this.p.e()) {
            this.w = a(6, this.A.getPrfAlgorithm());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:299:0x0696  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x06e4  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x06fc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0704  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0717  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x073b  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0763  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0805  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x080c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void c() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 2123
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.n.c():void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v45 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    boolean b() throws IOException {
        SupportedEllipticCurves supportedEllipticCurves;
        SupportedPointFormats supportedPointFormats;
        X509Certificate[] peerCertificateChain;
        as anVar;
        String str;
        boolean z = false;
        X509Certificate[] certificateChain = null;
        CertificateStatusRequest certificateStatusRequest = null;
        byte[] bArrA = null;
        do {
            int iF = f();
            ?? r9 = 1;
            if (iF == 257) {
                if (this.D.getAllowedProtocolVersions()[0] > 2) {
                    this.r.a(2, 10);
                    throw new SSLException("Received unexpected SSL version 2 message.", 2, 10, false);
                }
                ((ah) this).g();
                return true;
            }
            if (iF == 22) {
                while (this.q.available() > 0) {
                    int iK = this.q.k();
                    if (this.k != r9) {
                        if (this.k == 2) {
                            if (iK != 22) {
                                switch (iK) {
                                    case 11:
                                        if (this.T) {
                                            this.n.a("Received certificate handshake message with server certificate.");
                                        }
                                        if (this.b && !this.B.startsWith("RSA")) {
                                            this.r.a(2, 10);
                                            throw new SSLException("Server Certificate message not expected for non-RSA PSK cipher suites.", 2, 10, false);
                                        }
                                        SSLCertificate sSLCertificate = new SSLCertificate(this.q);
                                        if (sSLCertificate.a() == 0) {
                                            this.r.a(2, 42);
                                            throw new SSLException("Server sends empty certificate chain!", 2, 42, false);
                                        }
                                        certificateChain = sSLCertificate.getCertificateChain();
                                        if (this.T) {
                                            int certificateType = Utils.getCertificateType(certificateChain[0]);
                                            SSLTransport sSLTransport = this.n;
                                            StringBuffer stringBuffer = new StringBuffer("Server sent a ");
                                            stringBuffer.append(Utils.a(certificateChain[0], false));
                                            stringBuffer.append(" ");
                                            stringBuffer.append(Utils.certTypeToString(certificateType));
                                            stringBuffer.append(" certificate, chain has ");
                                            stringBuffer.append(certificateChain.length);
                                            stringBuffer.append(" elements.");
                                            sSLTransport.a(stringBuffer.toString());
                                        }
                                        if (this.z && !this.D.getAllowIdentityChangeDuringRenegotiation() && (peerCertificateChain = this.n.getPeerCertificateChain()) != null && peerCertificateChain.length > 0 && !peerCertificateChain[0].equals(certificateChain[0])) {
                                            this.r.a(2, 40);
                                            throw new SSLException("Server certificate change not allowed during renegotiation!", 2, 40, false);
                                        }
                                        this.p.d = certificateChain;
                                        this.n.n = certificateChain;
                                        PublicKey publicKeyB = sSLCertificate.b();
                                        this.a = publicKeyB;
                                        if (publicKeyB.getAlgorithm().toUpperCase().startsWith(KeyTypeNames.EC)) {
                                            if (this.R != null) {
                                                supportedPointFormats = (SupportedPointFormats) this.R.getExtension(SupportedPointFormats.TYPE);
                                                supportedEllipticCurves = (SupportedEllipticCurves) this.R.getExtension(SupportedEllipticCurves.TYPE);
                                            } else {
                                                supportedEllipticCurves = null;
                                                supportedPointFormats = null;
                                            }
                                            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
                                            if (!securityProvider.checkKeyECPointFormat(this.a, supportedPointFormats)) {
                                                throw new SSLException("Server certificate public key uses not supported point format!", 2, 42, false);
                                            }
                                            if (!securityProvider.checkKeyEllipticCurve(this.a, supportedEllipticCurves)) {
                                                throw new SSLException("Server certificate public key uses not supported elliptic curve!", 2, 42, false);
                                            }
                                        }
                                        ExtensionList extensionListA = this.n.a(false);
                                        if (extensionListA != null) {
                                            certificateStatusRequest = (CertificateStatusRequest) extensionListA.getExtension(CertificateStatusRequest.TYPE);
                                        }
                                        if (certificateStatusRequest == null) {
                                            try {
                                                a(certificateChain, -1, null, null);
                                            } catch (SSLCertificateException e) {
                                                if (e.getAlertLevel() == -1 && e.getAlertDescription() == -1) {
                                                    this.r.a(2, 42);
                                                    e.a(2, 42, false);
                                                    throw e;
                                                }
                                                this.r.a(e.getAlertLevel(), e.getAlertDescription());
                                                throw e;
                                            }
                                        }
                                        break;
                                        break;
                                    case 12:
                                        if (this.T) {
                                            this.n.a("Received server_key_exchange handshake message.");
                                        }
                                        if (this.b) {
                                            anVar = new at(this.A, this.n);
                                        } else if (this.B.startsWith("RSA")) {
                                            if (this.l > 769) {
                                                this.r.a(2, 10);
                                                throw new SSLException("RSA ServerKeyExchange message not allowed!", 2, 10, false);
                                            }
                                            anVar = new au(this.A, this.M, this.O, this.n);
                                        } else if (this.B.startsWith("DHE") || this.B.startsWith("DH_anon")) {
                                            anVar = new an(this.A, this.M, this.O, this.n);
                                        } else if (this.B.startsWith("ECDHE") || this.B.startsWith("ECDH_anon")) {
                                            anVar = new ao(this.A, this.M, this.O, this.n);
                                            if (this.R != null) {
                                                ao aoVar = (ao) anVar;
                                                aoVar.a((SupportedEllipticCurves) this.R.getExtension(SupportedEllipticCurves.TYPE));
                                                aoVar.a((SupportedPointFormats) this.R.getExtension(SupportedPointFormats.TYPE));
                                            }
                                        } else {
                                            if (this.B.startsWith("DH")) {
                                                this.r.a(2, 10);
                                                throw new SSLException("Diffie-Hellman key exchange error. Server sends ServerKeyExchange message.", 2, 10, false);
                                            }
                                            if (this.B.startsWith(SecurityProvider.ALG_KEYEX_ECDH)) {
                                                this.r.a(2, 10);
                                                throw new SSLException("ECDH key exchange error. Server sends ServerKeyExchange message.", 2, 10, false);
                                            }
                                            this.r.a(2, 80);
                                            StringBuffer stringBuffer2 = new StringBuffer("Unsupported key exchange algorithm: ");
                                            stringBuffer2.append(this.B);
                                            throw new SSLException(stringBuffer2.toString(), 2, 80, false);
                                        }
                                        anVar.a(this.q);
                                        if (this.a == null && !this.B.startsWith("DH_anon") && !this.B.startsWith("ECDH_anon") && !this.B.startsWith("DHE_PSK") && !this.B.startsWith("ECDHE_PSK") && !this.B.startsWith("PSK")) {
                                            this.r.a(2, 40);
                                            throw new SSLException("Server didn't send his Certificate.", 2, 40, false);
                                        }
                                        if (!anVar.a(this.a)) {
                                            this.r.a(2, 51);
                                            throw new SSLException("ServerKeyExchange signature error.", 2, 51, false);
                                        }
                                        this.a = (PublicKey) anVar.b();
                                        if (this.T && anVar.a() != null) {
                                            SSLTransport sSLTransport2 = this.n;
                                            StringBuffer stringBuffer3 = new StringBuffer("Server sent a ");
                                            stringBuffer3.append(anVar.a());
                                            stringBuffer3.append(" key");
                                            sSLTransport2.a(stringBuffer3.toString());
                                        }
                                        if (this.b) {
                                            byte[] bArrC = ((at) anVar).c();
                                            this.X = bArrC;
                                            if (bArrC != null) {
                                                boolean ignorePSKIdentityHint = ((SSLClientContext) this.D).getIgnorePSKIdentityHint();
                                                if (this.T) {
                                                    try {
                                                        try {
                                                            str = new String(this.X, "UTF8");
                                                        } catch (UnsupportedEncodingException unused) {
                                                            str = new String(this.X, UsbSerialDebugger.ENCODING);
                                                        }
                                                    } catch (Throwable unused2) {
                                                        str = null;
                                                    }
                                                    SSLTransport sSLTransport3 = this.n;
                                                    StringBuffer stringBuffer4 = new StringBuffer("Server sent PSK identity hint: ");
                                                    stringBuffer4.append(str);
                                                    stringBuffer4.append(ignorePSKIdentityHint ? "; ignoring" : "");
                                                    sSLTransport3.a(stringBuffer4.toString());
                                                }
                                                if (ignorePSKIdentityHint) {
                                                    this.X = null;
                                                }
                                            }
                                        }
                                        this.q.m();
                                        r9 = 1;
                                        break;
                                        break;
                                    case 13:
                                        if (this.T) {
                                            this.n.a("Received certificate_request handshake message.");
                                        }
                                        if (this.b == r9) {
                                            this.r.a(2, 10);
                                            throw new SSLException("Certificate Request message not allowed for PSK cipher suites.", 2, 10, false);
                                        }
                                        f fVar = new f(this.q, this.l);
                                        this.V = fVar;
                                        fVar.a(this.n);
                                        if (this.B.startsWith("DH_anon")) {
                                            this.r.a(2, 40);
                                            throw new SSLException("An anonymous server requested a client certificate.", 2, 40, false);
                                        }
                                        this.j = 2;
                                        break;
                                        break;
                                    case 14:
                                        if (certificateChain == null && this.A.d()) {
                                            this.r.a(2, 10);
                                            throw new SSLException("Server did not send certificate message.", 2, 10, false);
                                        }
                                        if (certificateStatusRequest != null && certificateChain != null) {
                                            if (certificateStatusRequest.c() == 0 && bArrA == null) {
                                                this.r.a(2, 113);
                                                throw new SSLException("Did not receive requested certificate_status response from server.", 2, 113, false);
                                            }
                                            try {
                                                a(certificateChain, certificateStatusRequest.getStatusType(), certificateStatusRequest.getStatusRequest(), bArrA);
                                            } catch (SSLCertificateException e2) {
                                                if (e2.getAlertLevel() == -1 && e2.getAlertDescription() == -1) {
                                                    this.r.a(2, 42);
                                                    e2.a(2, 42, false);
                                                } else {
                                                    this.r.a(e2.getAlertLevel(), e2.getAlertDescription());
                                                }
                                                throw e2;
                                            }
                                        }
                                        if (this.T) {
                                            this.n.a("Received server_hello_done handshake message.");
                                        }
                                        new ar(this.q);
                                        z = true;
                                        break;
                                        break;
                                }
                            } else {
                                if (this.T) {
                                    this.n.a("Received certificate_status handshake message.");
                                }
                                if (certificateStatusRequest == null) {
                                    this.r.a(2, 10);
                                    throw new SSLException("certificate_status message not allowed without having negotiated certificate status_request.", 2, 10, false);
                                }
                                g gVar = new g(this.q);
                                if (gVar.b() != certificateStatusRequest.getStatusType()) {
                                    this.r.a(2, 47);
                                    throw new SSLException("certificate_status message does not have negotiated status type.", 47, 10, false);
                                }
                                bArrA = gVar.a();
                            }
                        } else if (this.k != 3) {
                            if (this.k != 5) {
                                this.r.a(2, 80);
                                throw new SSLException("Internal state error.", 2, 80, false);
                            }
                            if (iK == 20) {
                                this.k = 4;
                                if (this.T) {
                                    this.n.a("Received finished message.");
                                }
                                w wVar = new w(this.q);
                                byte[] bArrA2 = a(1, this.A.getPrfAlgorithm());
                                if (this.S) {
                                    if (this.p.g == 768) {
                                        if (this.Q == null || this.Q.length < 72) {
                                            this.Q = new byte[72];
                                        }
                                        System.arraycopy(bArrA2, 0, this.Q, 36, 36);
                                    } else {
                                        if (this.Q == null || this.Q.length != 24) {
                                            this.Q = new byte[24];
                                        }
                                        System.arraycopy(bArrA2, 0, this.Q, 12, 12);
                                    }
                                }
                                if (!wVar.a(bArrA2)) {
                                    this.r.a(2, 51);
                                    throw new SSLException("Bad hash in Finished message.", 2, 51, false);
                                }
                                z = true;
                                this.q.m();
                                r9 = 1;
                            } else {
                                this.r.a(2, 10);
                                throw new SSLException("Expected finished handshake message.", 2, 10, false);
                            }
                        } else if (iK == 4) {
                            if (this.T) {
                                this.n.a("Received new_session_ticket message.");
                            }
                            af afVar = new af(this.q);
                            ((TicketSession) this.p).a(afVar.a());
                            SessionManager sessionManager = this.D.getSessionManager();
                            ((TicketSession) this.p).a((sessionManager == null || !(sessionManager instanceof DefaultSessionManager)) ? 0 : (int) ((DefaultSessionManager) sessionManager).getResumePeriod(), afVar.b());
                            this.k = 4;
                        } else {
                            this.r.a(2, 10);
                            throw new SSLException("Expected new_session_ticket handshake message.", 2, 10, false);
                        }
                        this.q.m();
                        r9 = 1;
                    } else if (iK == 2) {
                        if (this.T) {
                            this.n.a("Received v3 server_hello handshake message.");
                        }
                        aq aqVar = new aq(this.q, this.W, this.n, this.R);
                        int iF2 = aqVar.f();
                        if (this.T) {
                            SSLTransport sSLTransport4 = this.n;
                            StringBuffer stringBuffer5 = new StringBuffer("Server selected SSL version ");
                            stringBuffer5.append(Utils.c(iF2));
                            stringBuffer5.append(".");
                            sSLTransport4.a(stringBuffer5.toString());
                        }
                        a(iF2);
                        int[] iArrA = ((SSLClientContext) this.D).a(this.n.d());
                        if (iF2 < iArrA[0] || iF2 > iArrA[r9]) {
                            this.r.a(2, 70);
                            StringBuffer stringBuffer6 = new StringBuffer("Server selected SSL version ");
                            stringBuffer6.append(Utils.c(iF2));
                            stringBuffer6.append(" not enabled or not valid!");
                            throw new SSLException(stringBuffer6.toString(), 2, 70, false);
                        }
                        ExtensionList extensionListC = aqVar.c();
                        a(extensionListC);
                        this.O = aqVar.d();
                        if (this.y == r9 && !this.p.getID().equals(aqVar.e())) {
                            this.y = false;
                        }
                        if (this.y) {
                            if (this.p.g != iF2) {
                                this.r.a(2, 40);
                                throw new SSLException("Server tried to resume a session with a different protocol version!", 2, 40, false);
                            }
                            this.k = 4;
                            if (this.T) {
                                this.n.a("Server also wants to resume a previous session.");
                            }
                            this.A = this.p.getCipherSuite();
                            this.B = this.A.getKeyExchangeAlgorithm();
                            this.C = this.p.getCompressionMethod();
                            if (this.T) {
                                SSLTransport sSLTransport5 = this.n;
                                StringBuffer stringBuffer7 = new StringBuffer("Continuing with CipherSuite: ");
                                stringBuffer7.append(this.A.getName());
                                sSLTransport5.a(stringBuffer7.toString());
                                SSLTransport sSLTransport6 = this.n;
                                StringBuffer stringBuffer8 = new StringBuffer("Continuing with CompressionMethod: ");
                                stringBuffer8.append(this.C.getName());
                                sSLTransport6.a(stringBuffer8.toString());
                            }
                            a(this.R, extensionListC);
                            this.P = this.p.getMasterSecret();
                            e();
                        } else {
                            this.k = 2;
                            if (this.T) {
                                SSLTransport sSLTransport7 = this.n;
                                StringBuffer stringBuffer9 = new StringBuffer("Server created new session ");
                                stringBuffer9.append(aqVar.e().c());
                                sSLTransport7.a(stringBuffer9.toString());
                            }
                            if (this.p.getCipherSuite().equals(CipherSuite.e)) {
                                this.p.g = iF2;
                                this.p.a(0);
                                this.p.a(false);
                            } else {
                                this.p.invalidate();
                                if (extensionListC != null && extensionListC.getExtension(SessionTicket.TYPE) != null) {
                                    this.p = new TicketSession(iF2);
                                } else {
                                    this.p = new Session(iF2);
                                }
                            }
                            this.A = aqVar.a();
                            if (this.A.c() && iF2 < 771) {
                                this.r.a(2, 40);
                                throw new SSLException("Server selected AEAD suite not supported for versions < TLS 1.2!", 2, 40, false);
                            }
                            this.B = this.A.getKeyExchangeAlgorithm();
                            this.C = aqVar.b();
                            this.p.a = this.A;
                            this.p.b = this.C;
                            if (this.T) {
                                SSLTransport sSLTransport8 = this.n;
                                StringBuffer stringBuffer10 = new StringBuffer("CipherSuite selected by server: ");
                                stringBuffer10.append(this.A.getName());
                                sSLTransport8.a(stringBuffer10.toString());
                                SSLTransport sSLTransport9 = this.n;
                                StringBuffer stringBuffer11 = new StringBuffer("CompressionMethod selected by server: ");
                                stringBuffer11.append(this.C.getName());
                                sSLTransport9.a(stringBuffer11.toString());
                            }
                            if (this.A.getKeySizeLimit() != -1 && iF2 > 769) {
                                this.r.a(2, 40);
                                throw new SSLException("Server selected a exportable ciphersuite! Not allowed for TLS 1.1 or later.", 2, 40, false);
                            }
                            if (!this.D.getEnabledCipherSuiteList().contains(this.A)) {
                                this.r.a(2, 40);
                                throw new SSLException("Server selected a not enabled cipher suite?!", 2, 40, false);
                            }
                            if (this.B.equals("NULL")) {
                                this.E = null;
                                this.F = null;
                                this.G = null;
                                this.H = null;
                                this.I = null;
                                this.J = null;
                                this.k = 4;
                            }
                        }
                        if (extensionListC != null && extensionListC.hasExtensions()) {
                            if (this.T) {
                                SSLTransport sSLTransport10 = this.n;
                                StringBuffer stringBuffer12 = new StringBuffer("TLS extensions sent by the server: ");
                                stringBuffer12.append(extensionListC);
                                sSLTransport10.a(stringBuffer12.toString());
                            }
                            extensionListC.removeExtension(RenegotiationInfo.b);
                        }
                        if (this.T) {
                            if (this.S) {
                                this.n.a("Server supports secure renegotiation.");
                            } else {
                                this.n.a("Server does not support secure renegotiation.");
                            }
                        }
                        if (!this.y) {
                            if (this.Y && this.R != null) {
                                ExtensionList extensionListA2 = this.R.a(extensionListC, (boolean) r9);
                                if (extensionListA2 != null && extensionListA2.getExtension(TruncatedHMAC.TYPE) != null && this.A.c()) {
                                    this.r.a(2, 47);
                                    throw new SSLException("TruncatedHMAC extension not supported for AEAD cipher suite.", 2, 47, false);
                                }
                                this.n.a(extensionListA2);
                                this.p.a(extensionListA2);
                            }
                            int iA = this.p.a();
                            ag agVar = this.r;
                            if (iA == 0) {
                                iA = 16384;
                            }
                            agVar.c(iA);
                        }
                        this.p.c = aqVar.e();
                        if (extensionListC != null && extensionListC.hasExtensions()) {
                            SessionTicket sessionTicket = (SessionTicket) extensionListC.getExtension(SessionTicket.TYPE);
                            this.n.b(extensionListC);
                            if (sessionTicket != null) {
                                if (!this.y) {
                                    this.p.a(new SessionID(this.D.getRandomGenerator()));
                                } else {
                                    this.k = 3;
                                }
                            }
                        }
                        if (this.B.endsWith("PSK") == r9) {
                            this.b = r9;
                            if (iF2 < 769) {
                                this.r.a(2, 70);
                                StringBuffer stringBuffer13 = new StringBuffer("SSL version ");
                                stringBuffer13.append(Utils.c(iF2));
                                stringBuffer13.append(" not valid for PSK cipher suite (must be TLS)!");
                                throw new SSLException(stringBuffer13.toString(), 2, 70, false);
                            }
                        }
                    } else {
                        this.r.a(2, 10);
                        throw new SSLException("First message not ServerHello.", 2, 10, false);
                    }
                    this.q.m();
                    r9 = 1;
                }
            }
        } while (!z);
        return false;
    }

    o a(Session session, SSLContext sSLContext, int i, int i2, SSLTransport sSLTransport, ExtensionList extensionList) {
        return new o(session, sSLContext, i, i2, sSLTransport, extensionList);
    }

    KeyAndCert a(f fVar, PublicKey publicKey, SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) throws IOException {
        SupportedPointFormats supportedPointFormats;
        SupportedEllipticCurves supportedEllipticCurves;
        KeyAndCert keyAndCert = null;
        if (this.o != null) {
            ClientTrustDecider clientTrustDecider = (ClientTrustDecider) this.o;
            synchronized (clientTrustDecider) {
                byte[] bArrB = fVar.b();
                Utils.a(bArrB, this.B, signatureAndHashAlgorithmList);
                SSLCertificate certificate = clientTrustDecider.getCertificate(bArrB, fVar.a(), this.B);
                if (certificate == null || certificate.getCertificateChain() == null) {
                    return null;
                }
                PrivateKey privateKey = clientTrustDecider.getPrivateKey();
                if (privateKey == null) {
                    this.r.a(2, 80);
                    throw new SSLException("No private key returned by ClientTrustDecider", 2, 80, false);
                }
                return new KeyAndCert(certificate.getCertificateChain(), privateKey);
            }
        }
        Principal[] principalArrA = fVar.a();
        byte[] bArrB2 = fVar.b();
        Utils.a(bArrB2, this.B, signatureAndHashAlgorithmList);
        KeyAndCert[] clientCredentials = this.W.getClientCredentials(principalArrA, bArrB2, publicKey);
        if (clientCredentials == null || clientCredentials.length <= 0) {
            return null;
        }
        ExtensionList extensionListA = this.n.a(false);
        ClientCertificateURL clientCertificateURL = extensionListA != null ? (ClientCertificateURL) extensionListA.getExtension(ClientCertificateURL.TYPE) : null;
        if (clientCertificateURL != null) {
            int i = 0;
            while (true) {
                if (i >= clientCredentials.length) {
                    break;
                }
                KeyAndCert keyAndCert2 = clientCredentials[i];
                if (keyAndCert2 instanceof KeyAndCertURL) {
                    keyAndCert = keyAndCert2;
                    break;
                }
                i++;
            }
            return (keyAndCert == null && clientCertificateURL.c() == 1) ? clientCredentials[0] : keyAndCert;
        }
        if (this.R != null) {
            supportedPointFormats = (SupportedPointFormats) this.R.getExtension(SupportedPointFormats.TYPE);
            supportedEllipticCurves = (SupportedEllipticCurves) this.R.getExtension(SupportedEllipticCurves.TYPE);
        } else {
            supportedPointFormats = null;
            supportedEllipticCurves = null;
        }
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        KeyAndCert keyAndCert3 = null;
        for (int i2 = 0; i2 < clientCredentials.length; i2++) {
            keyAndCert3 = clientCredentials[i2];
            X509Certificate[] certificateChain = keyAndCert3.getCertificateChain();
            if (certificateChain != null) {
                PublicKey publicKey2 = certificateChain[0].getPublicKey();
                if (!publicKey2.getAlgorithm().toUpperCase().startsWith(KeyTypeNames.EC) || (securityProvider.checkKeyECPointFormat(publicKey2, supportedPointFormats) && securityProvider.checkKeyEllipticCurve(publicKey2, supportedEllipticCurves))) {
                    break;
                }
            } else {
                keyAndCert3 = null;
            }
        }
        return keyAndCert3;
    }

    @Override // iaik.security.ssl.y
    void a() throws IOException {
        c();
        if (b()) {
            return;
        }
        c();
        if (this.y || this.B.equals("NULL")) {
            return;
        }
        b();
    }

    @Override // iaik.security.ssl.y
    void a(boolean z) throws IOException {
        if (this.D.getDisableRenegotiation()) {
            if (this.n.getActiveProtocolVersion() < 769 || !this.D.getUseNoRenegotiationWarnings()) {
                this.r.a(2, 40);
                throw new SSLException("Renegotiation requested but disabled!", 2, 40, false);
            }
            throw new SSLException("Renegotiation requested but disabled!", 1, 100, false);
        }
        if (!this.S) {
            SSLException e = null;
            try {
                SecurityProvider.getSecurityProvider().continueIfPeerDoesNotSupportSecureRenegotiation(this.n, true);
            } catch (SSLException e2) {
                e = e2;
                e.a(2, 40, false);
            } catch (Throwable th) {
                e = new SSLException(th.getMessage(), 2, 40, false);
            }
            if (e != null) {
                if (this.n.getActiveProtocolVersion() < 769 || !this.D.getUseNoRenegotiationWarnings()) {
                    this.r.a(2, 40);
                    throw e;
                }
                throw e;
            }
        }
        this.r.flush();
        d();
    }

    void a(ExtensionList extensionList) throws IOException {
        RenegotiationInfo renegotiationInfo = extensionList != null ? (RenegotiationInfo) extensionList.getExtension(RenegotiationInfo.b) : null;
        if (!this.z) {
            if (renegotiationInfo != null) {
                this.S = true;
                byte[] bArrE = renegotiationInfo.e();
                if (bArrE != null && bArrE.length > 0) {
                    this.r.a(2, 40);
                    throw new SSLException("Server RenegotiationInfo extension must be empty on initial handshake.", 2, 40, false);
                }
            } else {
                this.S = false;
            }
            if (this.S) {
                return;
            }
            try {
                SecurityProvider.getSecurityProvider().continueIfPeerDoesNotSupportSecureRenegotiation(this.n, false);
                return;
            } catch (SSLException e) {
                this.r.a(2, 40);
                e.a(2, 40, false);
                throw e;
            } catch (Throwable th) {
                this.r.a(2, 40);
                throw new SSLException(th.getMessage(), 2, 40, false);
            }
        }
        if (renegotiationInfo != null) {
            if (this.S) {
                byte[] bArrE2 = renegotiationInfo.e();
                if (bArrE2 == null) {
                    this.r.a(2, 40);
                    throw new SSLException("Server sent empty RenegotiationInfo extension!", 2, 40, false);
                }
                if (this.Q == null) {
                    this.r.a(2, 40);
                    throw new SSLException("Cannot check RenegotiationInfo extension received from server! No local verify data!", 2, 40, false);
                }
                if (bArrE2.length != (this.l > 768 ? 24 : 72)) {
                    this.r.a(2, 40);
                    throw new SSLException("Server RenegotiationInfo extension contains invalid verify data!", 2, 40, false);
                }
                if (Utils.equalsBlock(this.Q, bArrE2)) {
                    return;
                }
                this.r.a(2, 40);
                throw new SSLException("Server RenegotiationInfo extension contains invalid verify data!", 2, 40, false);
            }
            this.r.a(2, 40);
            throw new SSLException("Server sent RenegotiationInfo extension although it has not been negotiated!", 2, 40, false);
        }
        if (this.S) {
            this.r.a(2, 40);
            throw new SSLException("Server did not send RenegotiationInfo extension although it has been negotiated!", 2, 40, false);
        }
    }

    private void a(ExtensionList extensionList, ExtensionList extensionList2) throws SSLException {
        ExtendedMasterSecret extendedMasterSecret = this.R != null ? (ExtendedMasterSecret) extensionList.getExtension(ExtendedMasterSecret.TYPE) : null;
        ExtendedMasterSecret extendedMasterSecret2 = extensionList2 != null ? (ExtendedMasterSecret) extensionList2.getExtension(ExtendedMasterSecret.TYPE) : null;
        if (!this.p.e()) {
            if (extendedMasterSecret2 != null && extendedMasterSecret != null) {
                throw new SSLException("Extended master secret not used in original session but server has sent ExtendedMasterSecret extension!", 2, 40, false);
            }
            if (extendedMasterSecret != null) {
                throw new SSLException("Client want to use extended master secret but did not use it for original session!", 2, 40, false);
            }
            return;
        }
        if (extendedMasterSecret2 == null) {
            throw new SSLException("Extended master secret used in original session but server has not sent ExtendedMasterSecret extension!", 2, 40, false);
        }
        if (extendedMasterSecret == null) {
            throw new SSLException("Client does not want to resume a session with extended master secret!", 2, 40, false);
        }
        ExtensionList extensionList3 = new ExtensionList();
        extensionList3.addExtension(extendedMasterSecret);
        this.n.a(extensionList3);
        this.p.a(extensionList3);
    }
}

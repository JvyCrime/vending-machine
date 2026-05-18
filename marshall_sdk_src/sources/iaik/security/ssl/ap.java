package iaik.security.ssl;

import iaik.security.jsse.net.KeyTypeNames;
import java.io.IOException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes.dex */
class ap extends y {
    private PrivateKey V;
    private boolean W;
    private SSLServerContext X;
    private byte[] Y;
    private boolean Z;
    private CipherSuiteList a;
    private boolean aa;
    private CompressionMethod[] b;

    ap(SSLTransport sSLTransport) {
        super(sSLTransport);
        this.V = null;
        this.W = false;
        this.Y = null;
        this.Z = false;
        this.aa = false;
        SSLServerContext sSLServerContext = (SSLServerContext) this.D;
        this.X = sSLServerContext;
        if (sSLServerContext.getRequestClientCertificate()) {
            this.j = 2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0324 A[Catch: Exception -> 0x0377, TryCatch #0 {Exception -> 0x0377, blocks: (B:144:0x031b, B:146:0x0324, B:148:0x0328, B:149:0x032f, B:151:0x0345, B:152:0x034d, B:154:0x0351, B:155:0x0358, B:157:0x035e, B:159:0x0362, B:160:0x0367, B:161:0x036d, B:162:0x0376), top: B:371:0x031b }] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x034d A[Catch: Exception -> 0x0377, TryCatch #0 {Exception -> 0x0377, blocks: (B:144:0x031b, B:146:0x0324, B:148:0x0328, B:149:0x032f, B:151:0x0345, B:152:0x034d, B:154:0x0351, B:155:0x0358, B:157:0x035e, B:159:0x0362, B:160:0x0367, B:161:0x036d, B:162:0x0376), top: B:371:0x031b }] */
    /* JADX WARN: Removed duplicated region for block: B:293:0x066b A[PHI: r0 r15
  0x066b: PHI (r0v23 ??) = (r0v4 ??), (r0v24 ??) binds: [B:288:0x064f, B:291:0x0663] A[DONT_GENERATE, DONT_INLINE]
  0x066b: PHI (r15v6 int) = (r15v1 int), (r15v7 int) binds: [B:288:0x064f, B:291:0x0663] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:308:0x06e6  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x078c  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x07d9  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0804  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x081a  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x014a  */
    /* JADX WARN: Type inference failed for: r0v126 */
    /* JADX WARN: Type inference failed for: r0v127 */
    /* JADX WARN: Type inference failed for: r0v128 */
    /* JADX WARN: Type inference failed for: r0v129 */
    /* JADX WARN: Type inference failed for: r0v13, types: [iaik.security.ssl.KeyAndCert] */
    /* JADX WARN: Type inference failed for: r0v130 */
    /* JADX WARN: Type inference failed for: r0v131 */
    /* JADX WARN: Type inference failed for: r0v132 */
    /* JADX WARN: Type inference failed for: r0v133 */
    /* JADX WARN: Type inference failed for: r0v134 */
    /* JADX WARN: Type inference failed for: r0v135 */
    /* JADX WARN: Type inference failed for: r0v136 */
    /* JADX WARN: Type inference failed for: r0v137 */
    /* JADX WARN: Type inference failed for: r0v138 */
    /* JADX WARN: Type inference failed for: r0v139 */
    /* JADX WARN: Type inference failed for: r0v140 */
    /* JADX WARN: Type inference failed for: r0v141 */
    /* JADX WARN: Type inference failed for: r0v142 */
    /* JADX WARN: Type inference failed for: r0v143 */
    /* JADX WARN: Type inference failed for: r0v144 */
    /* JADX WARN: Type inference failed for: r0v145 */
    /* JADX WARN: Type inference failed for: r0v146 */
    /* JADX WARN: Type inference failed for: r0v147 */
    /* JADX WARN: Type inference failed for: r0v148 */
    /* JADX WARN: Type inference failed for: r0v149 */
    /* JADX WARN: Type inference failed for: r0v150 */
    /* JADX WARN: Type inference failed for: r0v151 */
    /* JADX WARN: Type inference failed for: r0v152 */
    /* JADX WARN: Type inference failed for: r0v153 */
    /* JADX WARN: Type inference failed for: r0v154 */
    /* JADX WARN: Type inference failed for: r0v16, types: [iaik.security.ssl.KeyAndCert] */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24, types: [iaik.security.ssl.KeyAndCert] */
    /* JADX WARN: Type inference failed for: r0v27, types: [iaik.security.ssl.KeyAndCert] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v36, types: [iaik.security.ssl.KeyAndCert] */
    /* JADX WARN: Type inference failed for: r0v39 */
    /* JADX WARN: Type inference failed for: r0v4, types: [iaik.security.ssl.KeyAndCert] */
    /* JADX WARN: Type inference failed for: r0v40, types: [iaik.security.ssl.KeyAndCert] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v66, types: [iaik.security.ssl.KeyAndCert] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v84, types: [java.util.Enumeration] */
    /* JADX WARN: Type inference failed for: r0v87 */
    /* JADX WARN: Type inference failed for: r0v88 */
    /* JADX WARN: Type inference failed for: r0v89 */
    /* JADX WARN: Type inference failed for: r0v90 */
    /* JADX WARN: Type inference failed for: r13v3, types: [iaik.security.ssl.CompressionMethod] */
    /* JADX WARN: Type inference failed for: r18v0, types: [iaik.security.ssl.ap, iaik.security.ssl.y] */
    /* JADX WARN: Type inference failed for: r2v102 */
    /* JADX WARN: Type inference failed for: r2v103 */
    /* JADX WARN: Type inference failed for: r2v104 */
    /* JADX WARN: Type inference failed for: r2v105 */
    /* JADX WARN: Type inference failed for: r2v106 */
    /* JADX WARN: Type inference failed for: r2v36, types: [iaik.security.ssl.ao, iaik.security.ssl.as] */
    /* JADX WARN: Type inference failed for: r2v43 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v54, types: [iaik.security.ssl.an, iaik.security.ssl.as] */
    /* JADX WARN: Type inference failed for: r2v57 */
    /* JADX WARN: Type inference failed for: r2v58, types: [iaik.security.ssl.as, iaik.security.ssl.x] */
    /* JADX WARN: Type inference failed for: r2v72, types: [iaik.security.ssl.as, iaik.security.ssl.at] */
    /* JADX WARN: Type inference failed for: r3v121, types: [iaik.security.ssl.CipherSuiteList] */
    /* JADX WARN: Type inference failed for: r3v123 */
    /* JADX WARN: Type inference failed for: r3v124 */
    /* JADX WARN: Type inference failed for: r3v127, types: [iaik.security.ssl.CompressionMethod[]] */
    /* JADX WARN: Type inference failed for: r3v138 */
    /* JADX WARN: Type inference failed for: r3v139 */
    /* JADX WARN: Type inference failed for: r5v11, types: [iaik.security.ssl.ao, iaik.security.ssl.as] */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v22, types: [iaik.security.ssl.an, iaik.security.ssl.as] */
    /* JADX WARN: Type inference failed for: r5v84 */
    /* JADX WARN: Type inference failed for: r5v85 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void b() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 2124
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.ap.b():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:147:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0194 A[Catch: IOException -> 0x01c3, TRY_ENTER, TryCatch #2 {IOException -> 0x01c3, blocks: (B:65:0x0194, B:66:0x01a0, B:68:0x01b3, B:70:0x01b7, B:71:0x01be, B:72:0x01c2), top: B:141:0x015f }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01b3 A[Catch: IOException -> 0x01c3, TryCatch #2 {IOException -> 0x01c3, blocks: (B:65:0x0194, B:66:0x01a0, B:68:0x01b3, B:70:0x01b7, B:71:0x01be, B:72:0x01c2), top: B:141:0x015f }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01c2 A[Catch: IOException -> 0x01c3, TRY_LEAVE, TryCatch #2 {IOException -> 0x01c3, blocks: (B:65:0x0194, B:66:0x01a0, B:68:0x01b3, B:70:0x01b7, B:71:0x01be, B:72:0x01c2), top: B:141:0x015f }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x021d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void a(iaik.security.ssl.o r19) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 846
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.ap.a(iaik.security.ssl.o):void");
    }

    private void a(ExtendedMasterSecret extendedMasterSecret, ExtendedMasterSecret extendedMasterSecret2) throws SSLException {
        if (!this.p.e()) {
            if (extendedMasterSecret != null) {
                if (extendedMasterSecret2 != null) {
                    this.p = null;
                    return;
                }
                return;
            } else {
                if (extendedMasterSecret2 != null && extendedMasterSecret2.c() == 0) {
                    throw new SSLException("Client tries to resume a session without extended master secret but server configured to use extended mester secret!", 2, 40, false);
                }
                return;
            }
        }
        if (extendedMasterSecret == null) {
            throw new SSLException("Client tries to resume an extended master secret session without sending extended master secret extension!", 2, 40, false);
        }
        if (extendedMasterSecret2 == null) {
            this.p = null;
            return;
        }
        ExtensionList extensionList = new ExtensionList();
        extensionList.addExtension(extendedMasterSecret2);
        this.n.a(extensionList);
        this.p.a(extensionList);
    }

    void a(ExtensionList extensionList, CipherSuiteList cipherSuiteList) throws IOException {
        boolean zEquals;
        SSLException sSLException = null;
        RenegotiationInfo renegotiationInfo = extensionList != null ? (RenegotiationInfo) extensionList.getExtension(RenegotiationInfo.b) : null;
        boolean zContains = cipherSuiteList.contains(CipherSuite.d);
        if (!this.z) {
            if (renegotiationInfo != null) {
                this.S = true;
                byte[] bArrE = renegotiationInfo.e();
                if (bArrE != null && bArrE.length > 0) {
                    this.r.a(2, 40);
                    throw new SSLException("Client RenegotiationInfo extension must be empty on initial handshake.", 2, 40, false);
                }
            } else if (zContains) {
                this.S = true;
            } else {
                this.S = false;
                try {
                    SecurityProvider.getSecurityProvider().continueIfPeerDoesNotSupportSecureRenegotiation(this.n, false);
                } catch (SSLException e) {
                    this.r.a(2, 40);
                    e.a(2, 40, false);
                    throw e;
                } catch (Throwable th) {
                    this.r.a(2, 40);
                    throw new SSLException(th.getMessage(), 2, 40, false);
                }
            }
            if (this.T) {
                if (this.S) {
                    this.n.a("Client supports secure renegotiation.");
                    return;
                } else {
                    this.n.a("Client does not support secure renegotiation.");
                    return;
                }
            }
            return;
        }
        if (zContains) {
            this.r.a(2, 40);
            throw new SSLException("Renegotiating ClientHello is not allowed to contain SCSV cipher suite!", 2, 40, false);
        }
        if (!this.D.getAllowIdentityChangeDuringRenegotiation()) {
            ServerNameList serverNameList = extensionList != null ? (ServerNameList) extensionList.getExtension(ServerNameList.TYPE) : null;
            ExtensionList activeExtensions = this.n.getActiveExtensions();
            ServerNameList serverNameList2 = activeExtensions != null ? (ServerNameList) activeExtensions.getExtension(ServerNameList.TYPE) : null;
            if (serverNameList == null || serverNameList2 == null) {
                zEquals = serverNameList == null && serverNameList2 == null;
            } else {
                zEquals = serverNameList.equals(serverNameList2);
            }
            if (!zEquals) {
                this.r.a(2, 40);
                throw new SSLException("ServerName extension change not allowed during renegotiation!", 2, 40, false);
            }
        }
        if (renegotiationInfo != null) {
            if (this.S) {
                byte[] bArrE2 = renegotiationInfo.e();
                if (bArrE2 == null) {
                    throw new SSLException("Client sent empty RenegotiationInfo extension!", 2, 40, false);
                }
                if (this.Q == null) {
                    throw new SSLException("Cannot check RenegotiationInfo extension received from client! No local verify data!", 2, 40, false);
                }
                int length = this.Q.length / 2;
                if (bArrE2.length != length) {
                    throw new SSLException("Client RenegotiationInfo extension contains invalid verify data!", 2, 40, false);
                }
                if (!Utils.equalsBlock(this.Q, 0, bArrE2, 0, length)) {
                    throw new SSLException("Client RenegotiationInfo extension contains invalid verify data!", 2, 40, false);
                }
                return;
            }
            this.r.a(2, 40);
            throw new SSLException("Client sent RenegotiationInfo extension although it has not been negotiated!", 2, 40, false);
        }
        if (this.S) {
            throw new SSLException("Client did not send RenegotiationInfo extension!", 2, 40, false);
        }
        try {
            SecurityProvider.getSecurityProvider().continueIfPeerDoesNotSupportSecureRenegotiation(this.n, true);
        } catch (SSLException e2) {
            sSLException = e2;
            sSLException.a(2, 40, false);
        } catch (Throwable th2) {
            sSLException = new SSLException(th2.getMessage(), 2, 40, false);
        }
        if (sSLException != null) {
            if (this.n.getActiveProtocolVersion() < 769 || !this.D.getUseNoRenegotiationWarnings()) {
                this.r.a(2, 40);
                throw sSLException;
            }
            throw sSLException;
        }
    }

    aq a(Session session, SSLContext sSLContext, SSLTransport sSLTransport, boolean z) {
        return new aq(session, sSLContext, sSLTransport, this.S);
    }

    /* JADX WARN: Removed duplicated region for block: B:231:0x04ff  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0501  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void c(boolean r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 2007
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.ap.c(boolean):void");
    }

    private f c() throws IOException {
        SignatureAndHashAlgorithmList supportedAlgorithms;
        SignatureAlgorithms signatureAlgorithms;
        if (this.l >= 771) {
            ExtensionList activeExtensions = this.n.getActiveExtensions();
            if (activeExtensions == null) {
                activeExtensions = this.X.i();
            }
            supportedAlgorithms = (activeExtensions == null || (signatureAlgorithms = (SignatureAlgorithms) activeExtensions.getExtension(SignatureAlgorithms.TYPE)) == null) ? null : signatureAlgorithms.getSupportedAlgorithms();
            if (supportedAlgorithms == null) {
                supportedAlgorithms = SignatureAndHashAlgorithmList.getDefault();
            }
        } else {
            supportedAlgorithms = null;
        }
        if (this.o != null) {
            Principal[] principalArr = this.X.g;
            byte[] bArr = this.X.f;
            if (bArr == null || principalArr == null) {
                this.r.a(2, 80);
                throw new SSLException("Configuration error: CertificateRequest information missing.", 2, 80, false);
            }
            this.Y = Utils.a(Utils.b(bArr), this.B, supportedAlgorithms);
            return new f(bArr, principalArr, supportedAlgorithms);
        }
        ChainVerifier chainVerifier = this.D.getChainVerifier();
        Principal[] trustedPrincipalsArray = chainVerifier != null ? chainVerifier.getTrustedPrincipalsArray() : null;
        if (trustedPrincipalsArray == null) {
            trustedPrincipalsArray = new Principal[0];
        }
        byte[] bArrA = Utils.a(this.X.getAllowedCertificateTypes(), this.B, supportedAlgorithms);
        this.Y = bArrA;
        return new f(bArrA, trustedPrincipalsArray, supportedAlgorithms);
    }

    void a(X509Certificate[] x509CertificateArr) throws IOException {
        SupportedPointFormats supportedPointFormats;
        SupportedEllipticCurves supportedEllipticCurves;
        X509Certificate[] peerCertificateChain;
        if (this.z && !this.D.getAllowIdentityChangeDuringRenegotiation() && (peerCertificateChain = this.n.getPeerCertificateChain()) != null && peerCertificateChain.length > 0 && (x509CertificateArr == null || !peerCertificateChain[0].equals(x509CertificateArr[0]))) {
            this.r.a(2, 40);
            throw new SSLException("Client certificate change not allowed during renegotiation!", 2, 40, false);
        }
        if (x509CertificateArr != null && x509CertificateArr.length > 0) {
            PublicKey publicKey = x509CertificateArr[0].getPublicKey();
            if (publicKey.getAlgorithm().toUpperCase().startsWith(KeyTypeNames.EC)) {
                if (this.R != null) {
                    supportedPointFormats = (SupportedPointFormats) this.R.getExtension(SupportedPointFormats.TYPE);
                    supportedEllipticCurves = (SupportedEllipticCurves) this.R.getExtension(SupportedEllipticCurves.TYPE);
                } else {
                    supportedPointFormats = null;
                    supportedEllipticCurves = null;
                }
                SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
                if (!securityProvider.checkKeyECPointFormat(publicKey, supportedPointFormats)) {
                    throw new SSLException("Client certificate public key uses not supported point format!", 2, 42, false);
                }
                if (supportedEllipticCurves != null && supportedEllipticCurves.c() == 0 && !supportedEllipticCurves.f() && !securityProvider.checkKeyEllipticCurve(publicKey, supportedEllipticCurves)) {
                    throw new SSLException("Client certificate public key uses not supported elliptic curve!", 2, 42, false);
                }
            }
        }
        try {
            a(x509CertificateArr, -1, (byte[]) null, (byte[]) null);
            this.p.d = x509CertificateArr;
            this.n.n = x509CertificateArr;
            this.j = 4;
        } catch (SSLCertificateException e) {
            this.n.n = x509CertificateArr;
            this.j = 5;
            if (e.getAlertLevel() == -1 && e.getAlertDescription() == -1) {
                this.r.a(2, 42);
                e.a(2, 42, false);
            } else {
                this.r.a(e.getAlertLevel(), e.getAlertDescription());
            }
            throw e;
        }
    }

    @Override // iaik.security.ssl.y
    void a() throws Throwable {
        c(!this.W);
        b();
        c(true);
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
        this.W = z;
        if (!z) {
            if (this.T) {
                this.n.a("Sending hello_request handshake message to initiate renegotiation...");
            }
            a(new z());
            a(22, true, true);
        }
        this.r.flush();
        d();
    }
}

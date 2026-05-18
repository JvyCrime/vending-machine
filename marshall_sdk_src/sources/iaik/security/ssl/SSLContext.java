package iaik.security.ssl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Objects;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public abstract class SSLContext implements Cloneable {
    public static final String AVAIL_MODE_DECRYPTED = "decrypted";
    public static final String AVAIL_MODE_ENCRYPTED = "encrypted";
    public static final String AVAIL_MODE_ONE_BYTE = "onebyte";
    public static final int CERTTYPE_DSS_FIXED_DH = 4;
    public static final int CERTTYPE_DSS_SIGN = 2;
    public static final int CERTTYPE_ECDSA_EC = 258;
    public static final int CERTTYPE_ECDSA_FIXED_ECDH = 66;
    public static final int CERTTYPE_ECDSA_SIGN = 64;
    public static final int CERTTYPE_RSA_ENCRYPT = 257;
    public static final int CERTTYPE_RSA_FIXED_DH = 3;
    public static final int CERTTYPE_RSA_FIXED_ECDH = 65;
    public static final int CERTTYPE_RSA_SIGN = 1;
    public static final int CERTTYPE_UNKNOWN = 256;
    public static final double LIBRARY_VERSION = 5.105d;
    public static final String LIBRARY_VERSION_STRING = "5.105 Evaluation Version";
    public static final String SEND_EMPTY_FRAGMENT = "isasilk.send_empty_fragment";
    public static final int VERSION_NOT_CONNECTED = 0;
    public static final int VERSION_SSL20 = 2;
    public static final int VERSION_SSL30 = 768;
    public static final int VERSION_TLS10 = 769;
    public static final int VERSION_TLS11 = 770;
    public static final int VERSION_TLS12 = 771;
    static Class e;
    private static ae f;
    private static Throwable g;
    private int A;
    private boolean B;
    private TrustDecider C;
    int[] a;
    int b;
    ExtensionList c;
    boolean d;
    private CipherSuiteList h;
    private CompressionMethod[] i;
    private SessionManager j;
    private SecureRandom k;
    private int l;
    private ChainVerifier m;
    private PrintWriter n;
    private PSKManager o;
    private boolean p;
    private PSKCredential q;
    private boolean r;
    private boolean s;
    private int t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y;
    private boolean z;

    static boolean a(int i) {
        return i >= 1 && i <= 66;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    private static ae a(URL url) throws Throwable {
        InputStream inputStreamOpenStream = null;
        try {
            ae aeVar = new ae();
            inputStreamOpenStream = url.openStream();
            aeVar.a(inputStreamOpenStream);
            if (inputStreamOpenStream != null) {
                try {
                    inputStreamOpenStream.close();
                } catch (Exception unused) {
                }
            }
            return aeVar;
        } catch (Throwable th) {
            if (inputStreamOpenStream != null) {
                try {
                    inputStreamOpenStream.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    public static String[] getAllSupportedProtocolVersionNames() {
        return new String[]{"SSL30", "TLS10", "TLS11", "TLS12"};
    }

    public static int[] getAllSupportedProtocolVersions() {
        return new int[]{VERSION_SSL30, VERSION_TLS10, VERSION_TLS11, VERSION_TLS12};
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:111:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x008e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00a8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    static {
        /*
            Method dump skipped, instruction units count: 356
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.SSLContext.<clinit>():void");
    }

    SSLContext(int i) {
    }

    SSLContext() {
    }

    SSLContext(SecureRandom secureRandom, CipherSuiteList cipherSuiteList) {
        this.k = secureRandom;
        this.h = cipherSuiteList;
        Throwable th = g;
        if (th != null) {
            if (th instanceof PropertyInitException) {
                throw ((PropertyInitException) th);
            }
            StringBuffer stringBuffer = new StringBuffer("Error loading config file: ");
            stringBuffer.append(g.toString());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        c();
        d();
    }

    void c() {
        if (this.k == null) {
            this.k = SecurityProvider.getSecurityProvider().getSecureRandom();
        }
        if (this.h == null) {
            this.h = new CipherSuiteList(2);
        }
        this.i = CompressionMethod.getDefault();
        this.j = SessionManager.getDefault();
        this.a = new int[]{VERSION_TLS10, VERSION_TLS12};
        this.m = new ChainVerifier();
        try {
            this.r = System.getProperty(SEND_EMPTY_FRAGMENT) != null;
        } catch (Throwable unused) {
        }
        this.l = 1;
        this.t = -1;
        this.u = true;
        this.v = false;
        this.w = false;
        this.x = false;
        this.y = false;
        this.z = false;
        this.A = 1;
        this.B = true;
        this.C = null;
        this.b = -1;
    }

    t d() throws IllegalArgumentException, PropertyInitException {
        ae aeVar = f;
        if (aeVar == null) {
            return null;
        }
        boolean z = true;
        boolean z2 = !(this instanceof SSLServerContext);
        t tVar = new t(aeVar, z2);
        try {
            try {
                String strA = tVar.a("sessionManager");
                if (strA != null) {
                    try {
                        setSessionManager((SessionManager) Class.forName(strA).newInstance());
                    } catch (Throwable th) {
                        StringBuffer stringBuffer = new StringBuffer("Error instantiating SessionManager class \"");
                        stringBuffer.append(strA);
                        stringBuffer.append("\" : ");
                        stringBuffer.append(th.getMessage());
                        throw new PropertyInitException(stringBuffer.toString(), th);
                    }
                }
                String strA2 = tVar.a("chainVerifier");
                if (strA2 != null) {
                    try {
                        setChainVerifier((ChainVerifier) Class.forName(strA2).newInstance());
                    } catch (Throwable th2) {
                        StringBuffer stringBuffer2 = new StringBuffer("Error instantiating ChainVerifier class \"");
                        stringBuffer2.append(strA2);
                        stringBuffer2.append("\" : ");
                        stringBuffer2.append(th2.getMessage());
                        throw new PropertyInitException(stringBuffer2.toString(), th2);
                    }
                }
                if (z2) {
                    getChainVerifier().setCheckServerName(tVar.a("chainVerifier.checkServerName", false));
                }
                String[] strArrA = tVar.a("cipherSuite", (String[]) null);
                if (strArrA != null) {
                    try {
                        this.h = new CipherSuiteList(strArrA);
                    } catch (IllegalArgumentException e2) {
                        StringBuffer stringBuffer3 = new StringBuffer("Error initializing cipher suites from property file: ");
                        stringBuffer3.append(e2.getMessage());
                        throw new PropertyInitException(stringBuffer3.toString());
                    }
                }
                String[] strArrA2 = tVar.a("compressionMethod", (String[]) null);
                if (strArrA2 != null) {
                    Vector vector = new Vector();
                    for (String str : strArrA2) {
                        CompressionMethod compressionMethodA = CompressionMethod.a(str);
                        if (compressionMethodA == null) {
                            StringBuffer stringBuffer4 = new StringBuffer("Error initializing compression methods from property file: Unsupported compression method: ");
                            stringBuffer4.append(str);
                            throw new PropertyInitException(stringBuffer4.toString());
                        }
                        vector.add(compressionMethodA);
                    }
                    int size = vector.size();
                    if (size > 0) {
                        CompressionMethod[] compressionMethodArr = new CompressionMethod[size];
                        this.i = compressionMethodArr;
                        vector.copyInto(compressionMethodArr);
                    }
                }
                String strA3 = tVar.a("minProtocolVersion");
                String strA4 = tVar.a("maxProtocolVersion");
                if (strA3 != null || strA4 != null) {
                    if (strA3 == null) {
                        strA3 = "TLS10";
                    }
                    if (strA4 == null) {
                        strA4 = "TLS12";
                    }
                    setAllowedProtocolVersions(strA3, strA4);
                }
                this.r = tVar.a("sendEmptyFragment", this.r);
                String strA5 = tVar.a("cacheTerminatedSessions");
                if (strA5 != null) {
                    String lowerCase = strA5.toLowerCase();
                    if (!lowerCase.equals("true") && !lowerCase.equals("on") && !lowerCase.equals("yes")) {
                        z = false;
                    }
                    setCacheTerminatedSessions(z);
                }
                int iA = tVar.a("recordOverheadSize", this.t);
                this.t = iA;
                if (iA < -1) {
                    StringBuffer stringBuffer5 = new StringBuffer("Illegal recordOverheadSize property: ");
                    stringBuffer5.append(this.t);
                    throw new PropertyInitException(stringBuffer5.toString());
                }
                setSendRecordOverflowAlert(tVar.a("sendRecordOverflowAlert", this.u));
                setDoNotPackHandshakeMessages(tVar.a("doNotPackHandshakeMessages", this.v));
                setInputStreamAvailableMode(tVar.a("inputStreamAvailableMode"));
                setUseRecordSplitting(tVar.a("useRecordSplitting", this.B));
                setDisableRenegotiation(tVar.a("disableRenegotiation", this.w));
                setAllowLegacyRenegotiation(tVar.a("allowLegacyRenegotiation", this.x));
                setUseNoRenegotiationWarnings(tVar.a("useNoRenegotiationWarnings", this.y));
                setAllowIdentityChangeDuringRenegotiation(tVar.a("allowIdentityChangeDuringRenegotiation", this.z));
                String strA6 = tVar.a("keyStore");
                if (strA6 != null) {
                    String strA7 = tVar.a("keyStorePassword");
                    char[] charArray = strA7 == null ? null : strA7.toCharArray();
                    String strA8 = tVar.a("keyStoreProvider");
                    String strA9 = tVar.a("keyStoreType");
                    try {
                        if (z2) {
                            ((SSLClientContext) this).addClientCredentials(strA6, charArray, strA9, strA8);
                        } else {
                            ((SSLServerContext) this).addServerCredentials(strA6, charArray, strA9, strA8);
                        }
                    } catch (KeyStoreException e3) {
                        StringBuffer stringBuffer6 = new StringBuffer("Error loading KeyStore from \"");
                        stringBuffer6.append(strA6);
                        stringBuffer6.append("\" : ");
                        stringBuffer6.append(e3.getMessage());
                        throw new PropertyInitException(stringBuffer6.toString(), e3);
                    }
                }
                String strA10 = tVar.a("trustStore");
                if (strA10 != null) {
                    String strA11 = tVar.a("trustStorePassword");
                    try {
                        addTrustedCertificates(strA10, strA11 == null ? null : strA11.toCharArray(), tVar.a("trustStoreType"), tVar.a("trustStoreProvider"));
                    } catch (KeyStoreException e4) {
                        StringBuffer stringBuffer7 = new StringBuffer("Error loading KeyStore from \"");
                        stringBuffer7.append(strA6);
                        stringBuffer7.append("\" : ");
                        stringBuffer7.append(e4.getMessage());
                        throw new PropertyInitException(stringBuffer7.toString(), e4);
                    }
                }
                if (tVar.a("debug", false)) {
                    setDebugStream(System.out);
                }
                String strA12 = tVar.a("autoEnableExtensionsVersion");
                if (strA12 != null) {
                    try {
                        d(strA12);
                    } catch (IllegalArgumentException e5) {
                        StringBuffer stringBuffer8 = new StringBuffer("SSLContext Properties file contains invalid autoEnableExtensionsVersion property: ");
                        stringBuffer8.append(e5.getMessage());
                        throw new PropertyInitException(stringBuffer8.toString());
                    }
                }
                String[] strArrA3 = tVar.a("extension", (String[]) null);
                if (strArrA3 != null) {
                    a(strArrA3);
                }
                return tVar;
            } catch (PropertyInitException e6) {
                throw e6;
            }
        } catch (IllegalArgumentException e7) {
            StringBuffer stringBuffer9 = new StringBuffer("SSLContext Properties file contains invalid property: ");
            stringBuffer9.append(e7.getMessage());
            throw new IllegalArgumentException(stringBuffer9.toString());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:211:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00cc, code lost:
    
        if (r2.countExtensions() <= 0) goto L211;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ce, code lost:
    
        setExtensions(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00d1, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0504  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x00c2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0507 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void a(java.lang.String[] r19) throws iaik.security.ssl.PropertyInitException {
        /*
            Method dump skipped, instruction units count: 1383
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.SSLContext.a(java.lang.String[]):void");
    }

    void a(SSLContext sSLContext) {
        this.k = sSLContext.k;
        this.h = (CipherSuiteList) sSLContext.h.clone();
        this.i = (CompressionMethod[]) sSLContext.i.clone();
        this.j = sSLContext.j;
        this.o = sSLContext.o;
        this.p = sSLContext.p;
        if (this.q != null) {
            this.q = (PSKCredential) sSLContext.q.clone();
        }
        this.l = sSLContext.l;
        this.a = (int[]) sSLContext.a.clone();
        this.m = sSLContext.m;
        this.n = sSLContext.n;
        this.r = sSLContext.r;
        this.d = sSLContext.d;
        this.t = sSLContext.t;
        this.u = sSLContext.u;
        this.v = sSLContext.v;
        this.w = sSLContext.w;
        this.x = sSLContext.x;
        this.y = sSLContext.y;
        this.z = sSLContext.z;
        this.A = sSLContext.A;
        this.B = sSLContext.B;
        this.c = sSLContext.c;
        this.s = sSLContext.s;
        this.C = sSLContext.C;
        this.b = sSLContext.b;
    }

    public void updateCipherSuites() {
        Extension extension;
        SignatureAlgorithms signatureAlgorithms;
        SignatureAlgorithms signatureAlgorithms2;
        SignatureAndHashAlgorithmList supportedAlgorithms = null;
        if (this.a[1] >= 771) {
            ExtensionList extensionList = this.c;
            if (extensionList != null && (signatureAlgorithms2 = (SignatureAlgorithms) extensionList.getExtension(SignatureAlgorithms.TYPE)) != null) {
                supportedAlgorithms = signatureAlgorithms2.getSupportedAlgorithms();
            }
            if (supportedAlgorithms == null) {
                supportedAlgorithms = SignatureAndHashAlgorithmList.getDefault();
            }
        } else {
            ExtensionList extensionList2 = this.c;
            if (extensionList2 != null && (extension = extensionList2.getExtension(SignatureAlgorithms.TYPE)) != null && !extension.a()) {
                this.c.removeExtension(SignatureAlgorithms.TYPE);
                if (this.c.countExtensions() == 0) {
                    this.c = null;
                }
            }
        }
        CipherSuiteList cipherSuiteList = this.h;
        int[] iArr = this.a;
        cipherSuiteList.a(iArr[0], iArr[1], supportedAlgorithms);
        if (supportedAlgorithms != null && !this.s) {
            if (this.c == null) {
                ExtensionList extensionList3 = new ExtensionList();
                this.c = extensionList3;
                extensionList3.a(false);
            }
            if (this.c.getExtension(SignatureAlgorithms.TYPE) == null) {
                if (supportedAlgorithms.size() == 0 && !this.h.a()) {
                    signatureAlgorithms = new SignatureAlgorithms(1);
                } else {
                    signatureAlgorithms = new SignatureAlgorithms(supportedAlgorithms);
                }
                signatureAlgorithms.a(false);
                this.c.addExtension(signatureAlgorithms);
            }
        }
        ExtensionList extensionList4 = this.c;
        if (extensionList4 != null && extensionList4.getExtension(TruncatedHMAC.TYPE) != null && this.a[1] >= 771) {
            this.h.remove(CipherSuite.CS_AEAD_GCM);
        }
        if (this.h.size() == 0) {
            throw new NullPointerException("Cannot enable any cipher suite!");
        }
    }

    public ChainVerifier getChainVerifier() {
        return this.m;
    }

    public void setChainVerifier(ChainVerifier chainVerifier) {
        this.m = chainVerifier;
    }

    public void addTrustedCertificate(X509Certificate x509Certificate) {
        ChainVerifier chainVerifier = this.m;
        if (chainVerifier == null) {
            return;
        }
        chainVerifier.addTrustedCertificate(x509Certificate);
    }

    public int addTrustedCertificates(String str, char[] cArr, String str2, String str3) throws KeyStoreException {
        try {
            return addTrustedCertificates(SecurityProvider.getSecurityProvider().loadKeyStore(str, cArr, str2, str3));
        } catch (KeyStoreException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new KeyStoreException(e3, e3.toString()) { // from class: iaik.security.ssl.SSLContext.1
                private final Exception a;

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }

                {
                    super(str);
                    this.a = e3;
                }
            };
        }
    }

    public int addTrustedCertificates(KeyStore keyStore) throws KeyStoreException {
        Certificate certificate;
        Objects.requireNonNull(keyStore, "trustStore must not be null!");
        Enumeration<String> enumerationAliases = keyStore.aliases();
        int i = 0;
        while (enumerationAliases.hasMoreElements()) {
            try {
                String strNextElement = enumerationAliases.nextElement();
                if (keyStore.isCertificateEntry(strNextElement) && (certificate = keyStore.getCertificate(strNextElement)) != null && (certificate instanceof X509Certificate)) {
                    addTrustedCertificate((X509Certificate) certificate);
                    i++;
                }
            } catch (Exception e2) {
                StringBuffer stringBuffer = new StringBuffer("Error getting trust store entry: ");
                stringBuffer.append(e2.toString());
                throw new KeyStoreException(stringBuffer.toString());
            }
        }
        return i;
    }

    public int addTrustedCertificates(KeyStore keyStore, String[] strArr) throws KeyStoreException {
        Objects.requireNonNull(keyStore, "trustStore must not be null!");
        if (strArr == null || strArr.length == 0) {
            return addTrustedCertificates(keyStore);
        }
        int i = 0;
        for (String str : strArr) {
            try {
                if (keyStore.isCertificateEntry(str)) {
                    Certificate certificate = keyStore.getCertificate(str);
                    if (certificate != null && (certificate instanceof X509Certificate)) {
                        addTrustedCertificate((X509Certificate) certificate);
                        i++;
                    } else {
                        StringBuffer stringBuffer = new StringBuffer("No certificate entry available for alias \"");
                        stringBuffer.append(str);
                        stringBuffer.append("\"");
                        throw new KeyStoreException(stringBuffer.toString());
                    }
                } else {
                    StringBuffer stringBuffer2 = new StringBuffer("No certificate entry available for alias \"");
                    stringBuffer2.append(str);
                    stringBuffer2.append("\"");
                    throw new KeyStoreException(stringBuffer2.toString());
                }
            } catch (KeyStoreException e2) {
                throw e2;
            } catch (Exception e3) {
                StringBuffer stringBuffer3 = new StringBuffer("Error getting key store entry: ");
                stringBuffer3.append(e3.toString());
                throw new KeyStoreException(stringBuffer3.toString());
            }
        }
        return i;
    }

    public SecureRandom getRandomGenerator() {
        return this.k;
    }

    public void setRandomGenerator(SecureRandom secureRandom) {
        this.k = secureRandom;
    }

    public SessionManager getSessionManager() {
        return this.j;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.j = sessionManager;
    }

    public PSKManager getPSKManager() {
        if (!this.p && this.o == null) {
            this.o = PSKManager.getDefault();
        }
        return this.o;
    }

    public void setPSKManager(PSKManager pSKManager) {
        this.o = pSKManager;
        if (pSKManager == null) {
            this.p = true;
        }
    }

    public void addPSKCredential(PSKCredential pSKCredential) {
        PSKManager pSKManager = getPSKManager();
        if (pSKManager != null) {
            pSKManager.addPSKCredential(pSKCredential);
            PSKCredential pSKCredential2 = this.q;
            if (pSKCredential2 != null) {
                try {
                    if (pSKManager.getPSKCredential(pSKCredential2.getIdentity(), (SSLTransport) null) == null) {
                        pSKManager.addPSKCredential(this.q);
                    }
                } catch (SSLException unused) {
                }
            }
        }
    }

    public void setPSKCredential(PSKCredential pSKCredential) {
        this.q = pSKCredential;
    }

    public PSKCredential getPSKCredential(byte[] bArr, SSLTransport sSLTransport) throws SSLException {
        PSKManager pSKManager;
        PSKCredential pSKCredential = this.q;
        return (pSKCredential != null || (pSKManager = getPSKManager()) == null) ? pSKCredential : pSKManager.getPSKCredential(bArr, sSLTransport);
    }

    public void clearPSKCredentials() {
        this.q = null;
        PSKManager pSKManager = this.o;
        if (pSKManager != null) {
            pSKManager.removeAll();
        }
    }

    public CipherSuiteList getEnabledCipherSuiteList() {
        return this.h;
    }

    public void setEnabledCipherSuiteList(CipherSuiteList cipherSuiteList) {
        this.h = cipherSuiteList;
    }

    public void setEnabledCipherSuites(CipherSuite[] cipherSuiteArr) {
        this.h = new CipherSuiteList(cipherSuiteArr);
    }

    public CompressionMethod[] getEnabledCompressionMethods() {
        return this.i;
    }

    public void setEnabledCompressionMethods(CompressionMethod[] compressionMethodArr) {
        this.i = compressionMethodArr;
    }

    public boolean getCacheTerminatedSessions() {
        int i = this.l;
        if (i != 2) {
            if (i == 3) {
                return true;
            }
            int[] iArr = this.a;
            if (iArr[0] >= 770 && iArr[1] >= 770) {
                return true;
            }
        }
        return false;
    }

    int f() {
        return this.l;
    }

    public void setCacheTerminatedSessions(boolean z) {
        this.l = z ? 3 : 2;
    }

    private static void b(int i) throws IllegalArgumentException {
        if (i > 771) {
            throw new IllegalArgumentException("Only versions SSLContext.VERSION_SSL20, SSLContext.VERSION_SSL30, SSLContext.VERSION_TLS10 and SSLContext.VERSION_TLS11 and SSLContext.VERSION_TLS12 allowed!");
        }
        if (i < 768 && i != 2) {
            throw new IllegalArgumentException("Only versions SSLContext.VERSION_SSL20, SSLContext.VERSION_SSL30, SSLContext.VERSION_TLS10 and SSLContext.VERSION_TLS11 and SSLContext.VERSION_TLS12 allowed!");
        }
    }

    public void setAllowedProtocolVersions(int i, int i2) {
        if (i > i2) {
            throw new IllegalArgumentException("The minimum version must be less than or equal to the maximum version!");
        }
        b(i);
        b(i2);
        int[] iArr = this.a;
        iArr[0] = i;
        iArr[1] = i2;
    }

    public void setAllowedProtocolVersions(String str, String str2) throws IllegalArgumentException {
        int[] iArrA = Utils.a(str, str2);
        int[] iArr = this.a;
        iArr[0] = iArrA[0];
        iArr[1] = iArrA[1];
    }

    public int[] getAllowedProtocolVersions() {
        return (int[]) this.a.clone();
    }

    public String[] getAllowedProtocolVersionNames() {
        return new String[]{Utils.b(this.a[0]), Utils.b(this.a[1])};
    }

    int e() {
        return this.b;
    }

    void d(String str) {
        int iA = Utils.a(str);
        b(iA);
        this.b = iA;
    }

    public final void setExtensions(ExtensionList extensionList) throws IllegalArgumentException {
        X509Certificate[] trustedCertificatesArray;
        TrustedAuthority[] trustedAuthorities;
        if (extensionList == null) {
            this.c = null;
            this.s = true;
            return;
        }
        SignatureAlgorithms signatureAlgorithms = (SignatureAlgorithms) extensionList.getExtension(SignatureAlgorithms.TYPE);
        if (signatureAlgorithms == null) {
            ExtensionList extensionList2 = this.c;
            if (extensionList2 != null && this.a[1] >= 771) {
                signatureAlgorithms = (SignatureAlgorithms) extensionList2.getExtension(SignatureAlgorithms.TYPE);
            }
        } else {
            signatureAlgorithms = null;
        }
        this.c = new ExtensionList();
        Enumeration enumerationListExtensions = extensionList.listExtensions();
        while (enumerationListExtensions.hasMoreElements()) {
            Extension extension = (Extension) ((Extension) enumerationListExtensions.nextElement()).clone();
            int type = extension.getExtensionType().getType();
            if (this instanceof SSLServerContext) {
                extension.b(false);
                if (extension.c() == -1) {
                    extension.setCritical(false);
                }
                if (type == 35) {
                    ((SSLServerContext) this).a((SessionTicket) extension);
                } else if (type == 3 && (trustedAuthorities = ((TrustedAuthorities) extension).getTrustedAuthorities()) != null && trustedAuthorities.length > 0) {
                    throw new IllegalArgumentException("Server side TrustedAuthorities extension must be empty!");
                }
            } else {
                extension.b(true);
                if (extension.c() == -1) {
                    extension.setCritical(true);
                }
                if (type == 1) {
                    if (((MaxFragmentLength) extension).getMflId() == 0) {
                        throw new IllegalArgumentException("Client side MaxFragmentLength extension must not be empty!");
                    }
                } else if (type == 0) {
                    if (this.m != null && ((ServerNameList) extension).c() != 1) {
                        this.m.setCheckServerName(true);
                    }
                } else if (type == 3) {
                    TrustedAuthorities trustedAuthorities2 = (TrustedAuthorities) extension;
                    if (trustedAuthorities2.getTrustedAuthorities() == null) {
                        ChainVerifier chainVerifier = this.m;
                        if (chainVerifier == null || (trustedCertificatesArray = chainVerifier.getTrustedCertificatesArray()) == null || trustedCertificatesArray.length == 0) {
                            extension = null;
                        } else {
                            try {
                                trustedAuthorities2.a(trustedCertificatesArray);
                            } catch (Exception e2) {
                                StringBuffer stringBuffer = new StringBuffer("Cannot set TrustedAuthorities extension: ");
                                stringBuffer.append(e2.toString());
                                throw new IllegalArgumentException(stringBuffer.toString());
                            }
                        }
                    }
                }
            }
            if (extension != null) {
                this.c.addExtension(extension);
            }
        }
        if (this.a[1] < 771) {
            this.c.removeExtension(SignatureAlgorithms.TYPE);
        } else if (signatureAlgorithms != null) {
            this.c.addExtension(signatureAlgorithms);
        }
    }

    ExtensionList i() {
        return this.c;
    }

    Extension a(ExtensionType extensionType) {
        ExtensionList extensionList = this.c;
        if (extensionList != null) {
            return extensionList.getExtension(extensionType);
        }
        return null;
    }

    boolean g() {
        return this.s;
    }

    public void setDebugStream(OutputStream outputStream) {
        if (outputStream == null) {
            this.n = null;
        } else {
            this.n = new PrintWriter(outputStream, true);
        }
    }

    public void setDebugStream(Writer writer) {
        if (writer == null) {
            this.n = null;
        } else if (writer instanceof PrintWriter) {
            this.n = (PrintWriter) writer;
        } else {
            this.n = new PrintWriter(writer, true);
        }
    }

    public PrintWriter getDebugStream() {
        return this.n;
    }

    void c(String str) {
        PrintWriter printWriter = this.n;
        if (printWriter != null) {
            printWriter.println(str);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Enabled cipher suites: \n");
        stringBuffer.append(this.h.a("  "));
        stringBuffer.append("Enabled compression methods: ");
        int i = 0;
        if (this.i == null) {
            stringBuffer.append("None\n");
        } else {
            stringBuffer.append("\n");
            for (int i2 = 0; i2 < this.i.length; i2++) {
                stringBuffer.append("  ");
                stringBuffer.append(this.i[i2].getName());
                stringBuffer.append("\n");
            }
        }
        if (this.q != null) {
            StringBuffer stringBuffer2 = new StringBuffer("Explicit PSK credential set for identity \"");
            stringBuffer2.append(this.q.getIdentityString());
            stringBuffer2.append("\"\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        PSKManager pSKManager = this.o;
        if (pSKManager != null && pSKManager.size() > 0) {
            stringBuffer.append("\nPre-shared keys available for:\n");
            Enumeration all = this.o.getAll();
            while (all.hasMoreElements()) {
                i++;
                if (i > 1) {
                    stringBuffer.append(", ");
                }
                StringBuffer stringBuffer3 = new StringBuffer("  ");
                stringBuffer3.append(((PSKCredential) all.nextElement()).getIdentityString());
                stringBuffer.append(stringBuffer3.toString());
            }
            stringBuffer.append("\n");
        }
        if (this.c != null) {
            stringBuffer.append("Extensions: ");
            stringBuffer.append(this.c);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public CipherSuite[] getEnabledCipherSuites() {
        return this.h.toArray();
    }

    public TrustDecider getTrustDecider() {
        return this.C;
    }

    public void setTrustDecider(TrustDecider trustDecider) {
        this.C = trustDecider;
    }

    public static X509Certificate[] convertCertificateChain(Certificate[] certificateArr) throws CertificateException {
        if (certificateArr == null) {
            return null;
        }
        X509Certificate[] x509CertificateArr = new X509Certificate[certificateArr.length];
        for (int i = 0; i < certificateArr.length; i++) {
            if (certificateArr[i] instanceof X509Certificate) {
                x509CertificateArr[i] = (X509Certificate) certificateArr[i];
            } else {
                try {
                    x509CertificateArr[i] = SecurityProvider.getSecurityProvider().getX509Certificate(certificateArr[i].getEncoded());
                } catch (Exception e2) {
                    throw new CertificateException(e2.toString());
                }
            }
        }
        return x509CertificateArr;
    }

    public void setSendEmptyFragment(boolean z) {
        this.r = z;
        if (z) {
            this.B = false;
        }
    }

    public boolean getSendEmptyFragment() {
        return this.r;
    }

    public void setUseRecordSplitting(boolean z) {
        this.B = z;
        if (z) {
            this.r = false;
        }
    }

    boolean m() {
        return this.B;
    }

    public void setDoNotSendServerCloseNotify(boolean z) {
        this.d = z;
    }

    public boolean getDoNotSendServerCloseNotify() {
        return this.d;
    }

    public void setRecordOverheadSize(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Record overhead size must not be negative!");
        }
        this.t = i;
    }

    int k() {
        return this.t;
    }

    public void setSendRecordOverflowAlert(boolean z) {
        this.u = z;
    }

    boolean l() {
        return this.u;
    }

    public void setDoNotPackHandshakeMessages(boolean z) {
        this.v = z;
    }

    boolean h() {
        return this.v;
    }

    public void setDisableRenegotiation(boolean z) {
        this.w = z;
    }

    public boolean getDisableRenegotiation() {
        return this.w;
    }

    public void setAllowLegacyRenegotiation(boolean z) {
        this.x = z;
    }

    public boolean getAllowLegacyRenegotiation() {
        return this.x;
    }

    public void setUseNoRenegotiationWarnings(boolean z) {
        this.y = z;
    }

    public boolean getUseNoRenegotiationWarnings() {
        return this.y;
    }

    public void setAllowIdentityChangeDuringRenegotiation(boolean z) {
        this.z = z;
    }

    public boolean getAllowIdentityChangeDuringRenegotiation() {
        return this.z;
    }

    public void setInputStreamAvailableMode(String str) {
        if (str != null) {
            String lowerCase = str.toLowerCase(Locale.US);
            if (lowerCase.equals(AVAIL_MODE_ONE_BYTE)) {
                this.A = 0;
                return;
            }
            if (lowerCase.equals(AVAIL_MODE_ENCRYPTED)) {
                this.A = 1;
            } else if (lowerCase.equals(AVAIL_MODE_DECRYPTED)) {
                this.A = 2;
            } else {
                StringBuffer stringBuffer = new StringBuffer("Illegal inputStreamAvailableMode: ");
                stringBuffer.append(lowerCase);
                throw new PropertyInitException(stringBuffer.toString());
            }
        }
    }

    int j() {
        return this.A;
    }
}

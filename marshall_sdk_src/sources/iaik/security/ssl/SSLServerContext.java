package iaik.security.ssl;

import iaik.security.ssl.SupportedEllipticCurves;
import iaik.security.ssl.SupportedPointFormats;
import iaik.security.ssl.TicketKeyBag;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Vector;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class SSLServerContext extends SSLClientContext implements Cloneable {
    private static Object[] n;
    byte[] f;
    Principal[] g;
    private Hashtable[] k;
    private Hashtable[] l;
    private Object[] m;
    private u o;
    private boolean p;
    private int[] q;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean u;
    private TicketKeysManager w;
    private static final String[] h = {"RSA SIGN", "RSA ENCRYPT", "DSA", "RSA signed DH", "DSA signed DH", "ECDSA", "RSA signed ECDH", "ECDSA signed ECDH"};
    private static final ServerName i = ServerName.a();
    private static final String[] j = {"512 bit RSA keypair", "1024 bit RSA keypair", "512 bit export DH parameters", "1024 bit export DH parameters", "domestic DH parameters"};
    private static int v = 14;
    public static final CipherSuite[] rsa = CipherSuite.CS_RSA;
    public static final CipherSuite[] rsa_export = CipherSuite.CS_RSA_EXPORT;
    public static final CipherSuite[] dhe_rsa = CipherSuite.CS_DHE_RSA;
    public static final CipherSuite[] dhe_dss = CipherSuite.CS_DHE_DSS;
    public static final CipherSuite[] dh_rsa = CipherSuite.CS_DH_RSA;
    public static final CipherSuite[] dh_dss = CipherSuite.CS_DH_DSS;
    public static final CipherSuite[] anon = CipherSuite.CS_DH_ANON;

    private static int b(int i2) {
        if (i2 == 1) {
            return 0;
        }
        int i3 = 2;
        if (i2 != 2) {
            i3 = 3;
            if (i2 != 3) {
                i3 = 4;
                if (i2 != 4) {
                    if (i2 == 257) {
                        return 1;
                    }
                    if (i2 != 258) {
                        switch (i2) {
                            case 64:
                                break;
                            case 65:
                                return 6;
                            case 66:
                                return 7;
                            default:
                                return -1;
                        }
                    }
                    return 5;
                }
            }
        }
        return i3;
    }

    public static void setDHModpID(int i2) {
        if (i2 != 0 && i2 != 2 && i2 != 5) {
            switch (i2) {
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    StringBuffer stringBuffer = new StringBuffer("Invalid MODP group ID (");
                    stringBuffer.append(i2);
                    stringBuffer.append("). Must be 0, 5, 14, 15 or 16!");
                    throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
        v = i2;
    }

    SSLServerContext(int i2) {
        super(i2);
    }

    public SSLServerContext() {
        this(null, null);
    }

    public SSLServerContext(SecureRandom secureRandom) {
        this(secureRandom, null);
    }

    public SSLServerContext(CipherSuiteList cipherSuiteList) {
        this(null, cipherSuiteList);
    }

    public SSLServerContext(SecureRandom secureRandom, CipherSuiteList cipherSuiteList) {
        super(secureRandom, cipherSuiteList);
    }

    public SSLServerContext(SSLServerContext sSLServerContext) {
        this(0);
        a(sSLServerContext, true);
    }

    @Override // iaik.security.ssl.SSLClientContext, iaik.security.ssl.SSLContext
    void c() {
        super.c();
        this.k = new Hashtable[8];
        this.m = new Object[5];
        this.q = new int[]{1, 2, 3, 4, 64, 65, 66};
        this.p = false;
        this.r = false;
        this.s = false;
        this.t = false;
        this.u = false;
    }

    @Override // iaik.security.ssl.SSLClientContext, iaik.security.ssl.SSLContext
    t d() throws IllegalArgumentException, PropertyInitException {
        t tVarD = super.d();
        if (tVarD != null) {
            try {
                setDoNotSendServerCloseNotify(tVarD.a("doNotSendServerCloseNotify", this.d));
                setRequestClientCertificate(tVarD.a("requestClientCertificate", this.p));
                setCheckVersionInRSAPremasterSecret(tVarD.a("checkVersionInRSAPremasterSecret", this.s));
                setIgnoreClientCipherSuitePreferenceOrder(tVarD.a("ignoreClientCipherSuitePreferenceOrder", this.t));
            } catch (PropertyInitException e) {
                throw e;
            } catch (IllegalArgumentException e2) {
                StringBuffer stringBuffer = new StringBuffer("SSLContext Properties file contains invalid property: ");
                stringBuffer.append(e2.getMessage());
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
        return tVarD;
    }

    void a(SSLServerContext sSLServerContext, boolean z) {
        Object[] objArr;
        TicketKeysManager ticketKeysManager;
        Hashtable[] hashtableArr;
        Hashtable[] hashtableArr2;
        super.a((SSLClientContext) sSLServerContext);
        int i2 = 0;
        if (z && (hashtableArr2 = sSLServerContext.k) != null) {
            this.k = (Hashtable[]) hashtableArr2.clone();
            int i3 = 0;
            while (true) {
                Hashtable[] hashtableArr3 = this.k;
                if (i3 >= hashtableArr3.length) {
                    break;
                }
                if (hashtableArr3[i3] != null) {
                    hashtableArr3[i3] = (Hashtable) hashtableArr3[i3].clone();
                }
                i3++;
            }
        } else {
            this.k = sSLServerContext.k;
        }
        if (z && (hashtableArr = sSLServerContext.l) != null) {
            this.l = (Hashtable[]) hashtableArr.clone();
            while (true) {
                Hashtable[] hashtableArr4 = this.l;
                if (i2 >= hashtableArr4.length) {
                    break;
                }
                if (hashtableArr4[i2] != null) {
                    hashtableArr4[i2] = (Hashtable) hashtableArr4[i2].clone();
                }
                i2++;
            }
        } else {
            this.l = sSLServerContext.l;
        }
        if (!z && sSLServerContext.o != null) {
            objArr = sSLServerContext.m;
        } else {
            objArr = (Object[]) sSLServerContext.m.clone();
        }
        this.m = objArr;
        this.p = sSLServerContext.p;
        this.q = sSLServerContext.q;
        this.r = sSLServerContext.r;
        this.s = sSLServerContext.s;
        this.u = sSLServerContext.u;
        this.t = sSLServerContext.t;
        if (z && (ticketKeysManager = sSLServerContext.w) != null) {
            this.w = (TicketKeysManager) ticketKeysManager.clone();
        } else {
            this.w = sSLServerContext.w;
        }
        u uVar = sSLServerContext.o;
        if (uVar != null) {
            if (z) {
                uVar = new u(this, uVar.b(), sSLServerContext.o.c());
            }
            this.o = uVar;
        }
        this.f = sSLServerContext.f;
        this.g = sSLServerContext.g;
    }

    public int[] getAllowedCertificateTypes() {
        return this.q;
    }

    public void setAllowedCertificateTypes(int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (!SSLContext.a(iArr[i2])) {
                StringBuffer stringBuffer = new StringBuffer("Invalid certificate type: ");
                stringBuffer.append(iArr[i2]);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
        this.q = iArr;
    }

    public void setCheckVersionInRSAPremasterSecret(boolean z) {
        this.s = z;
    }

    boolean n() {
        return this.s;
    }

    public void setSendPSKIdentityHint(boolean z) {
        this.r = z;
    }

    public boolean getSendIdentityHint() {
        return this.r;
    }

    public void clearServerCredentials() {
        for (int i2 = 0; i2 <= 7; i2++) {
            this.k[i2] = null;
        }
        if (this.l != null) {
            for (int i3 = 0; i3 <= 7; i3++) {
                this.l[i3] = null;
            }
        }
    }

    public void addServerCredentials(X509Certificate[] x509CertificateArr, PrivateKey privateKey) {
        addServerCredentials(new KeyAndCert(x509CertificateArr, privateKey));
    }

    public void addServerCredentials(KeyAndCert keyAndCert) {
        int certificateType = keyAndCert.getCertificateType();
        if (certificateType == 258) {
            addServerCredentials(keyAndCert, 64, true);
            addServerCredentials(keyAndCert, 66, true);
        } else {
            if (certificateType == 1) {
                addServerCredentials(keyAndCert, 257, true);
            }
            addServerCredentials(keyAndCert, certificateType, true);
        }
    }

    public void addServerCredentials(KeyAndCert keyAndCert, int i2) {
        addServerCredentials(keyAndCert, i2, true);
    }

    public void addServerCredentials(KeyAndCert keyAndCert, int i2, boolean z) {
        Objects.requireNonNull(keyAndCert, "keyAndCert must not be null!");
        if (keyAndCert instanceof KeyAndCertURL) {
            throw new IllegalArgumentException("KeyAndCertUrl not allowed for ServerContext");
        }
        if (ChainVerifier.a()) {
            try {
                if (!SecurityProvider.getSecurityProvider().checkExtendedKeyUsage(keyAndCert.getCertificateChain()[0], false)) {
                    throw new IllegalArgumentException("No a server cert.");
                }
            } catch (Exception unused) {
            }
        }
        int iB = b(i2);
        if (iB < 0) {
            throw new IllegalArgumentException("Unknown server certificate type!");
        }
        Hashtable[] hashtableArr = this.k;
        if (hashtableArr[iB] == null) {
            hashtableArr[iB] = new Hashtable(5);
        }
        SupportedEllipticCurves.NamedCurve namedCurveB = null;
        if (iB >= 5 && (namedCurveB = keyAndCert.b()) == null) {
            throw new IllegalArgumentException("Cannot add ECC credentials. Curve not supported!");
        }
        if (z) {
            if (iB < 5) {
                this.k[iB].put(i, keyAndCert);
            } else {
                Hashtable hashtable = this.k[iB];
                ServerName serverName = i;
                Hashtable hashtable2 = (Hashtable) hashtable.get(serverName);
                if (hashtable2 == null) {
                    hashtable2 = new Hashtable(5);
                    this.k[iB].put(serverName, hashtable2);
                }
                hashtable2.put(namedCurveB, keyAndCert);
            }
        }
        ServerName[] tLSServerNames = keyAndCert.getTLSServerNames();
        if (tLSServerNames != null) {
            for (int i3 = 0; i3 < tLSServerNames.length; i3++) {
                if (iB < 5) {
                    this.k[iB].put(tLSServerNames[i3], keyAndCert);
                } else {
                    Hashtable hashtable3 = (Hashtable) this.k[iB].get(tLSServerNames[i3]);
                    if (hashtable3 == null) {
                        hashtable3 = new Hashtable(5);
                        this.k[iB].put(tLSServerNames[i3], hashtable3);
                    }
                    hashtable3.put(namedCurveB, keyAndCert);
                }
            }
        }
        TrustedAuthority[] trustedAuthorityArrC = keyAndCert.c();
        if (trustedAuthorityArrC == null || trustedAuthorityArrC.length <= 0) {
            return;
        }
        if (this.l == null) {
            this.l = new Hashtable[8];
        }
        Hashtable[] hashtableArr2 = this.l;
        if (hashtableArr2[iB] == null) {
            hashtableArr2[iB] = new Hashtable(trustedAuthorityArrC.length + 5);
        }
        for (TrustedAuthority trustedAuthority : trustedAuthorityArrC) {
            this.l[iB].put(trustedAuthority, keyAndCert);
        }
    }

    public int addServerCredentials(String str, char[] cArr, String str2, String str3) throws KeyStoreException {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        try {
            StringBuffer stringBuffer = new StringBuffer("Adding server credentials from keyStore ");
            stringBuffer.append(str);
            c(stringBuffer.toString());
            return addServerCredentials(securityProvider.loadKeyStore(str, cArr, str2, str3), cArr);
        } catch (KeyStoreException e) {
            throw e;
        } catch (Exception e2) {
            throw new KeyStoreException(e2, e2.toString()) { // from class: iaik.security.ssl.SSLServerContext.1
                private final Exception a;

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }

                {
                    super(str);
                    this.a = e2;
                }
            };
        }
    }

    public int addServerCredentials(KeyStore keyStore, char[] cArr) throws KeyStoreException {
        Objects.requireNonNull(keyStore, "keyStore must not be null!");
        Objects.requireNonNull(cArr, "password must not be null!");
        Enumeration<String> enumerationAliases = keyStore.aliases();
        int i2 = 0;
        int i3 = 0;
        while (enumerationAliases.hasMoreElements()) {
            try {
                String strNextElement = enumerationAliases.nextElement();
                if (keyStore.isKeyEntry(strNextElement)) {
                    i3++;
                    try {
                        Key key = keyStore.getKey(strNextElement, cArr);
                        if (key instanceof PrivateKey) {
                            Certificate[] certificateChain = keyStore.getCertificateChain(strNextElement);
                            if (certificateChain != null && certificateChain.length > 0) {
                                addServerCredentials(Utils.a(certificateChain), (PrivateKey) key);
                                i2++;
                            }
                            StringBuffer stringBuffer = new StringBuffer("Added ");
                            stringBuffer.append(strNextElement);
                            c(stringBuffer.toString());
                        } else if (key instanceof SecretKey) {
                            addPSKCredential(new PSKCredential(strNextElement, new PreSharedKey(key.getEncoded())));
                            i2++;
                            StringBuffer stringBuffer2 = new StringBuffer("Added ");
                            stringBuffer2.append(strNextElement);
                            stringBuffer2.append(" (PSK)");
                            c(stringBuffer2.toString());
                        }
                    } catch (Exception e) {
                        StringBuffer stringBuffer3 = new StringBuffer("Skipped ");
                        stringBuffer3.append(strNextElement);
                        stringBuffer3.append(": ");
                        stringBuffer3.append(e.getMessage());
                        c(stringBuffer3.toString());
                    }
                }
            } catch (Exception e2) {
                StringBuffer stringBuffer4 = new StringBuffer("Error getting key store entry: ");
                stringBuffer4.append(e2.toString());
                throw new KeyStoreException(stringBuffer4.toString());
            }
        }
        StringBuffer stringBuffer5 = new StringBuffer("Added ");
        stringBuffer5.append(i2);
        stringBuffer5.append(" of ");
        stringBuffer5.append(i3);
        stringBuffer5.append(" entries.");
        c(stringBuffer5.toString());
        return i2;
    }

    public int addServerCredentials(KeyStore keyStore, char[] cArr, String[] strArr) throws KeyStoreException {
        Objects.requireNonNull(keyStore, "keyStore must not be null!");
        Objects.requireNonNull(cArr, "password must not be null!");
        if (strArr == null || strArr.length == 0) {
            return addServerCredentials(keyStore, cArr);
        }
        int i2 = 0;
        for (String str : strArr) {
            try {
                if (keyStore.isKeyEntry(str)) {
                    Key key = keyStore.getKey(str, cArr);
                    if (key == null) {
                        StringBuffer stringBuffer = new StringBuffer("No key entry available for alias \"");
                        stringBuffer.append(str);
                        stringBuffer.append("\"");
                        throw new KeyStoreException(stringBuffer.toString());
                    }
                    if (key instanceof PrivateKey) {
                        Certificate[] certificateChain = keyStore.getCertificateChain(str);
                        if (certificateChain != null && certificateChain.length > 0) {
                            addServerCredentials(Utils.a(certificateChain), (PrivateKey) key);
                        } else {
                            StringBuffer stringBuffer2 = new StringBuffer("No certificate chain available for alias \"");
                            stringBuffer2.append(str);
                            stringBuffer2.append("\"");
                            throw new KeyStoreException(stringBuffer2.toString());
                        }
                    } else if (key instanceof SecretKey) {
                        addPSKCredential(new PSKCredential(str, new PreSharedKey(key.getEncoded())));
                    }
                    i2++;
                } else {
                    StringBuffer stringBuffer3 = new StringBuffer("No key entry available for alias \"");
                    stringBuffer3.append(str);
                    stringBuffer3.append("\"");
                    throw new KeyStoreException(stringBuffer3.toString());
                }
            } catch (KeyStoreException e) {
                throw e;
            } catch (Exception e2) {
                StringBuffer stringBuffer4 = new StringBuffer("Error getting key store entry: ");
                stringBuffer4.append(e2.toString());
                throw new KeyStoreException(stringBuffer4.toString());
            }
        }
        return i2;
    }

    public KeyAndCert getServerCredentials(int i2) {
        return getServerCredentials(i2, null, true, null);
    }

    private KeyAndCert a(int i2, ServerName serverName, SupportedEllipticCurves.NamedCurve[] namedCurveArr, SupportedPointFormats.ECPointFormat[] eCPointFormatArr, boolean z) {
        KeyAndCert keyAndCertA;
        Hashtable hashtable = this.k[i2];
        if (hashtable == null) {
            keyAndCertA = null;
        } else if (serverName != null) {
            keyAndCertA = a(i2, serverName, namedCurveArr, eCPointFormatArr, hashtable);
            if (keyAndCertA == null && z) {
                keyAndCertA = a(i2, i, namedCurveArr, eCPointFormatArr, hashtable);
            }
        } else {
            keyAndCertA = a(i2, i, namedCurveArr, eCPointFormatArr, hashtable);
        }
        Objects.requireNonNull(keyAndCertA, "Required credentials not available!");
        return keyAndCertA;
    }

    private static KeyAndCert a(int i2, ServerName serverName, SupportedEllipticCurves.NamedCurve[] namedCurveArr, SupportedPointFormats.ECPointFormat[] eCPointFormatArr, Hashtable hashtable) {
        KeyAndCert keyAndCertA;
        if (i2 < 5) {
            return (KeyAndCert) hashtable.get(serverName);
        }
        Hashtable hashtable2 = (Hashtable) hashtable.get(serverName);
        if (hashtable2 == null) {
            return null;
        }
        if (namedCurveArr != null && namedCurveArr.length > 0) {
            for (SupportedEllipticCurves.NamedCurve namedCurve : namedCurveArr) {
                if (namedCurve.equals(SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_PRIME)) {
                    keyAndCertA = a(hashtable2.elements(), eCPointFormatArr, false);
                } else if (namedCurve.equals(SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_CHAR2)) {
                    keyAndCertA = a(hashtable2.elements(), eCPointFormatArr, true);
                } else {
                    keyAndCertA = (KeyAndCert) hashtable2.get(namedCurve);
                    if (keyAndCertA != null && !a(keyAndCertA, eCPointFormatArr)) {
                        keyAndCertA = null;
                    }
                }
                if (keyAndCertA != null) {
                    return keyAndCertA;
                }
            }
            return null;
        }
        KeyAndCert keyAndCert = (KeyAndCert) hashtable2.get(SecurityProvider.getSecurityProvider().getDefaultCurve(false));
        KeyAndCert keyAndCert2 = (keyAndCert == null || a(keyAndCert, eCPointFormatArr)) ? keyAndCert : null;
        if (keyAndCert2 != null) {
            return keyAndCert2;
        }
        Enumeration enumerationElements = hashtable2.elements();
        while (enumerationElements.hasMoreElements()) {
            KeyAndCert keyAndCert3 = (KeyAndCert) enumerationElements.nextElement();
            if (a(keyAndCert3, eCPointFormatArr)) {
                return keyAndCert3;
            }
        }
        return keyAndCert2;
    }

    private static final boolean a(KeyAndCert keyAndCert, SupportedPointFormats.ECPointFormat[] eCPointFormatArr) {
        SupportedPointFormats.ECPointFormat eCPointFormatA = keyAndCert.a();
        if (eCPointFormatA == null) {
            return false;
        }
        for (SupportedPointFormats.ECPointFormat eCPointFormat : eCPointFormatArr) {
            if (eCPointFormat.equals(eCPointFormatA)) {
                return true;
            }
        }
        return false;
    }

    private static final KeyAndCert a(Enumeration enumeration, SupportedPointFormats.ECPointFormat[] eCPointFormatArr, boolean z) {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        while (enumeration.hasMoreElements()) {
            KeyAndCert keyAndCert = (KeyAndCert) enumeration.nextElement();
            if (securityProvider.isBinary(keyAndCert.getCertificateChain()[0].getPublicKey()) == z && a(keyAndCert, eCPointFormatArr)) {
                return keyAndCert;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x0187  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected iaik.security.ssl.KeyAndCert getServerCredentials(int r21, iaik.security.ssl.ExtensionList r22, boolean r23, iaik.security.ssl.SSLTransport r24) {
        /*
            Method dump skipped, instruction units count: 451
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.SSLServerContext.getServerCredentials(int, iaik.security.ssl.ExtensionList, boolean, iaik.security.ssl.SSLTransport):iaik.security.ssl.KeyAndCert");
    }

    PrivateKey a(int i2, ExtensionList extensionList, SSLTransport sSLTransport) {
        return getServerCredentials(i2, extensionList, true, sSLTransport).getPrivateKey();
    }

    @Override // iaik.security.ssl.SSLContext
    public void updateCipherSuites() {
        SignatureAlgorithms signatureAlgorithms;
        SignatureAlgorithms signatureAlgorithms2;
        CipherSuiteList enabledCipherSuiteList = getEnabledCipherSuiteList();
        SignatureAndHashAlgorithmList supportedAlgorithms = null;
        if (this.a[1] >= 771) {
            if (this.c != null && (signatureAlgorithms2 = (SignatureAlgorithms) this.c.getExtension(SignatureAlgorithms.TYPE)) != null) {
                supportedAlgorithms = signatureAlgorithms2.getSupportedAlgorithms();
            }
            if (supportedAlgorithms == null) {
                supportedAlgorithms = SignatureAndHashAlgorithmList.getDefault();
            }
        }
        Hashtable[] hashtableArr = this.k;
        if (hashtableArr[0] == null || hashtableArr[0].size() == 0) {
            enabledCipherSuiteList.remove(CipherSuite.CS_RSA_EXPORT);
            enabledCipherSuiteList.remove(CipherSuite.CS_RSA_EXPORT1024);
            enabledCipherSuiteList.remove(CipherSuite.CS_DHE_RSA);
            enabledCipherSuiteList.remove(CipherSuite.CS_DHE_RSA_EXPORT);
            enabledCipherSuiteList.remove(CipherSuite.CS_ECDHE_RSA);
            enabledCipherSuiteList.remove(CipherSuite.CS_RSA_PSK);
            enabledCipherSuiteList.remove(CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA);
            if (this.k[1] == null) {
                enabledCipherSuiteList.remove(CipherSuite.CS_RSA);
                enabledCipherSuiteList.remove(CipherSuite.CS_RSA_WITH_NULL);
            }
            if (supportedAlgorithms != null) {
                supportedAlgorithms.b(1);
            }
        }
        Hashtable[] hashtableArr2 = this.k;
        if (hashtableArr2[2] == null || hashtableArr2[2].size() == 0) {
            enabledCipherSuiteList.remove(CipherSuite.CS_DHE_DSS);
            enabledCipherSuiteList.remove(CipherSuite.CS_DHE_DSS_EXPORT);
            enabledCipherSuiteList.remove(CipherSuite.CS_DHE_DSS_EXPORT1024);
            if (supportedAlgorithms != null) {
                supportedAlgorithms.b(2);
            }
        }
        Hashtable[] hashtableArr3 = this.k;
        if (hashtableArr3[3] == null || hashtableArr3[3].size() == 0) {
            enabledCipherSuiteList.remove(CipherSuite.CS_DH_RSA);
            enabledCipherSuiteList.remove(CipherSuite.CS_DH_RSA_EXPORT);
        }
        Hashtable[] hashtableArr4 = this.k;
        if (hashtableArr4[4] == null || hashtableArr4[4].size() == 0) {
            enabledCipherSuiteList.remove(CipherSuite.CS_DH_DSS);
            enabledCipherSuiteList.remove(CipherSuite.CS_DH_DSS_EXPORT);
        }
        Hashtable[] hashtableArr5 = this.k;
        if (hashtableArr5[5] == null || hashtableArr5[5].size() == 0) {
            enabledCipherSuiteList.remove(CipherSuite.CS_ECDHE_ECDSA);
            if (supportedAlgorithms != null) {
                supportedAlgorithms.b(3);
            }
        }
        Hashtable[] hashtableArr6 = this.k;
        if (hashtableArr6[6] == null || hashtableArr6[6].size() == 0) {
            enabledCipherSuiteList.remove(CipherSuite.CS_ECDH_RSA);
        }
        Hashtable[] hashtableArr7 = this.k;
        if (hashtableArr7[7] == null || hashtableArr7[7].size() == 0) {
            enabledCipherSuiteList.remove(CipherSuite.CS_ECDH_ECDSA);
        }
        if (supportedAlgorithms != null) {
            if (this.c == null) {
                this.c = new ExtensionList();
                this.c.a(false);
            }
            if (this.c.getExtension(SignatureAlgorithms.TYPE) == null) {
                if (supportedAlgorithms.size() == 0 && !enabledCipherSuiteList.a()) {
                    signatureAlgorithms = new SignatureAlgorithms(1);
                } else {
                    signatureAlgorithms = new SignatureAlgorithms(supportedAlgorithms);
                }
                this.c.addExtension(signatureAlgorithms);
            }
        }
        super.updateCipherSuites();
    }

    private static synchronized void r() {
        BigInteger bigInteger;
        if (n != null) {
            return;
        }
        n = new Object[5];
        BigInteger bigInteger2 = new BigInteger("kzyd2bb8x0kwy2z5hrvbn8joniex7clevyp14mijp8ne5uf9y9p5p2mstfl2nzy1rdaq88b1hnmz86uyyjsshxdnp3g6og5fxo7", 36);
        BigInteger bigInteger3 = new BigInteger("agapx2dmn89tcarxtghfhmpjwx7stcgv2iy0vgb8r8byqmxiw1w3u5ut72mh98vrxj1alw8l2lgnjsgfjrf552nemony9kblzjo", 36);
        BigInteger bigInteger4 = new BigInteger("vjyxwyawcutef13zkgcsts7hkolgnmn5861w1cd9scjej51zqe3lfswg3n6qqe821lkx27pvoa8tkoglb928hex0s3jvepqx1q1bw4dubjagqtwpga0g2gmitt8f6r5h8tr1j62vavnyyaw2q2ow2gu9mmeuat2mcq9kryffc1em5xjn316z1yoaki3lj6kcfex2ah", 36);
        BigInteger bigInteger5 = new BigInteger("ea9zl437khcwgmicelj0fwhslq3peca6wvdektn0yc25cspjuqzn9qo5psa2s6c3jgzarrel15wwodtpwqos2dqjs2ndyil06iufnousb6gjflwk5zw6hlzk63b7x8xawhannq1r6gstizkft5agiu8svsxl0tei74idyv16hw3st5di2vcdy525nc2s2useigytm2", 36);
        DHParameterSpec dHParameterSpec = new DHParameterSpec(bigInteger2, bigInteger3);
        DHParameterSpec dHParameterSpec2 = new DHParameterSpec(bigInteger4, bigInteger5);
        Object[] objArr = n;
        objArr[2] = dHParameterSpec;
        objArr[3] = dHParameterSpec2;
        int i2 = v;
        if (i2 != 0) {
            if (i2 == 2) {
                bigInteger = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381FFFFFFFFFFFFFFFF", 16);
            } else {
                switch (i2) {
                    case 14:
                        bigInteger = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AACAA68FFFFFFFFFFFFFFFF", 16);
                        break;
                    case 15:
                        bigInteger = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A93AD2CAFFFFFFFFFFFFFFFF", 16);
                        break;
                    case 16:
                        bigInteger = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A92108011A723C12A787E6D788719A10BDBA5B2699C327186AF4E23C1A946834B6150BDA2583E9CA2AD44CE8DBBBC2DB04DE8EF92E8EFC141FBECAA6287C59474E6BC05D99B2964FA090C3A2233BA186515BE7ED1F612970CEE2D7AFB81BDD762170481CD0069127D5B05AA993B4EA988D8FDDC186FFB7DC90A6C08F4DF435C934063199FFFFFFFFFFFFFFFF", 16);
                        break;
                    default:
                        bigInteger = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA237327FFFFFFFFFFFFFFFF", 16);
                        break;
                }
            }
            n[4] = new DHParameterSpec(bigInteger, BigInteger.valueOf(2L));
        } else {
            objArr[4] = dHParameterSpec2;
        }
    }

    static DHParameterSpec o() {
        if (n == null) {
            r();
        }
        return (DHParameterSpec) n[2];
    }

    public void addTemporaryParameter(KeyPair keyPair) {
        int iA = Utils.a(keyPair.getPublic());
        if (iA <= 512) {
            this.m[0] = keyPair;
        } else {
            if (iA <= 1024) {
                this.m[1] = keyPair;
                return;
            }
            throw new IllegalArgumentException("Temporary RSA keys should be 512 or 1024 bits long!");
        }
    }

    public void addTemporaryParameter(DHParameterSpec dHParameterSpec) {
        addTemporaryParameter(dHParameterSpec, dHParameterSpec.getP().bitLength());
    }

    public void addTemporaryParameter(DHParameterSpec dHParameterSpec, int i2) {
        if (i2 <= 512) {
            this.m[2] = dHParameterSpec;
            return;
        }
        if (i2 <= 1024) {
            Object[] objArr = this.m;
            objArr[3] = dHParameterSpec;
            if (objArr[4] == null) {
                objArr[4] = dHParameterSpec;
                return;
            }
            return;
        }
        this.m[4] = dHParameterSpec;
    }

    void a(DHParameterSpec dHParameterSpec) {
        this.m[4] = dHParameterSpec;
    }

    public void setTemporaryParameterScheduling(DHGenParameterSpec dHGenParameterSpec, long j2) {
        if (dHGenParameterSpec == null) {
            this.o = null;
        }
        if (j2 <= 0) {
            StringBuffer stringBuffer = new StringBuffer("Invalid updateInterval value (");
            stringBuffer.append(j2);
            stringBuffer.append("). Must be > 0!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        int primeSize = dHGenParameterSpec.getPrimeSize();
        if (primeSize < 1024) {
            StringBuffer stringBuffer2 = new StringBuffer("Invalid prime size (");
            stringBuffer2.append(primeSize);
            stringBuffer2.append("). Must be >= 1024!");
            throw new IllegalArgumentException(stringBuffer2.toString());
        }
        int primeSize2 = dHGenParameterSpec.getPrimeSize();
        if (primeSize2 < 300) {
            StringBuffer stringBuffer3 = new StringBuffer("Invalid exponent size (");
            stringBuffer3.append(primeSize2);
            stringBuffer3.append("). Must be >= 300!");
            throw new IllegalArgumentException(stringBuffer3.toString());
        }
        this.o = new u(this, dHGenParameterSpec, j2);
    }

    private void a(KeyAndCert keyAndCert) {
        if (keyAndCert == null) {
            return;
        }
        addTemporaryParameter(((DHPublicKey) keyAndCert.getCertificateChain()[0].getPublicKey()).getParams());
    }

    synchronized Object a(SSLTransport sSLTransport, int i2) {
        boolean z;
        u uVar;
        if (sSLTransport != null) {
            try {
                z = sSLTransport.b != null;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.m[i2] == null) {
            Hashtable hashtable = this.k[3];
            if (hashtable != null) {
                a((KeyAndCert) hashtable.get(i));
            }
            Hashtable hashtable2 = this.k[4];
            if (hashtable2 != null) {
                a((KeyAndCert) hashtable2.get(i));
            }
            if (this.m[i2] == null) {
                String[] strArr = j;
                synchronized (strArr[i2]) {
                    if (z) {
                        StringBuffer stringBuffer = new StringBuffer("Temporary ");
                        stringBuffer.append(strArr[i2]);
                        stringBuffer.append(" not set, using defaults.");
                        sSLTransport.a(stringBuffer.toString());
                    }
                    if (n == null) {
                        r();
                    }
                    Object[] objArr = n;
                    if (objArr[i2] == null) {
                        objArr[i2] = a(sSLTransport, i2 == 0 ? 512 : 1024, this);
                    }
                    this.m[i2] = n[i2];
                }
            } else if (z) {
                StringBuffer stringBuffer2 = new StringBuffer("Temporary ");
                stringBuffer2.append(j[i2]);
                stringBuffer2.append(" not set, using defaults from certificates.");
                sSLTransport.a(stringBuffer2.toString());
            }
        }
        if (i2 == 4 && (uVar = this.o) != null) {
            uVar.a();
        }
        return this.m[i2];
    }

    private static KeyPair a(SSLTransport sSLTransport, int i2, SSLContext sSLContext) {
        if (sSLTransport != null) {
            try {
                if (sSLTransport.b != null) {
                    StringBuffer stringBuffer = new StringBuffer("Generating ");
                    stringBuffer.append(i2);
                    stringBuffer.append(" bit temporary RSA keypair...");
                    sSLTransport.a(stringBuffer.toString());
                }
            } catch (Exception e) {
                StringBuffer stringBuffer2 = new StringBuffer("Error generating temporary RSA keypair: ");
                stringBuffer2.append(e.toString());
                throw new NullPointerException(stringBuffer2.toString());
            }
        }
        SecureRandom randomGenerator = sSLContext.getRandomGenerator();
        KeyPairGenerator keyPairGenerator = SecurityProvider.getSecurityProvider().getKeyPairGenerator("RSA");
        keyPairGenerator.initialize(i2, randomGenerator);
        return Utils.generateKeyPair(keyPairGenerator);
    }

    public void setRequestClientCertificate(boolean z) {
        this.p = z;
    }

    public boolean getRequestClientCertificate() {
        return this.p;
    }

    public void setIgnoreClientCipherSuitePreferenceOrder(boolean z) {
        this.t = z;
    }

    boolean p() {
        return this.t;
    }

    public void setSendEmptySessionID(boolean z) {
        this.u = z;
    }

    boolean q() {
        return this.u;
    }

    @Override // iaik.security.ssl.SSLContext
    public void setAllowedProtocolVersions(int i2, int i3) {
        if (i2 == 2) {
            throw new IllegalArgumentException("SSL 2.0 not supported on the server side!");
        }
        super.setAllowedProtocolVersions(i2, i3);
    }

    void a(SessionTicket sessionTicket) {
        TicketKeyBag ticketKeyBagF = sessionTicket.f();
        TicketKeysManager ticketKeysManagerG = sessionTicket.g();
        if (ticketKeysManagerG == null) {
            ticketKeysManagerG = TicketKeysManager.getDefault();
        }
        if (ticketKeysManagerG instanceof DefaultTicketKeysManager) {
            SessionManager sessionManager = getSessionManager();
            long jH = sessionTicket.h();
            if (jH == 0 && sessionManager != null && (sessionManager instanceof DefaultSessionManager)) {
                long resumePeriod = ((DefaultSessionManager) sessionManager).getResumePeriod();
                if (resumePeriod > 0) {
                    sessionTicket.setTicketLifetime((int) resumePeriod);
                    jH = resumePeriod;
                }
            }
            if (ticketKeyBagF == null) {
                try {
                    ticketKeyBagF = new TicketKeyBag();
                } catch (NoSuchAlgorithmException e) {
                    StringBuffer stringBuffer = new StringBuffer("Error creating ticket keys for empty SessionTicket: ");
                    stringBuffer.append(e.toString());
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
            long validityPeriod = ticketKeyBagF.getValidityPeriod();
            if (validityPeriod > 0) {
                ticketKeyBagF.setValidityPeriod(validityPeriod + jH);
            }
        }
        ticketKeysManagerG.setTicketKeys(ticketKeyBagF);
        this.w = ticketKeysManagerG;
    }

    TicketKeyBag a(TicketKeyBag.KeyName keyName) throws NoSuchAlgorithmException {
        TicketKeysManager ticketKeysManager = this.w;
        if (ticketKeysManager != null) {
            return ticketKeysManager.getTicketKeys(keyName);
        }
        return null;
    }

    @Override // iaik.security.ssl.SSLClientContext, iaik.security.ssl.SSLContext
    public Object clone() {
        return a(true);
    }

    SSLServerContext a(boolean z) {
        SSLServerContext sSLServerContext = (SSLServerContext) super.clone();
        sSLServerContext.a(this, z);
        return sSLServerContext;
    }

    @Override // iaik.security.ssl.SSLClientContext, iaik.security.ssl.SSLContext
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append("\nAvailable certificates:\n");
        Vector vector = new Vector();
        for (int i2 = 0; i2 <= 7; i2++) {
            Hashtable[] hashtableArr = this.k;
            if (hashtableArr[i2] != null) {
                Enumeration enumerationElements = hashtableArr[i2].elements();
                while (enumerationElements.hasMoreElements()) {
                    Object objNextElement = enumerationElements.nextElement();
                    if (objNextElement instanceof Hashtable) {
                        Enumeration enumerationElements2 = ((Hashtable) objNextElement).elements();
                        while (enumerationElements2.hasMoreElements()) {
                            KeyAndCert keyAndCert = (KeyAndCert) enumerationElements2.nextElement();
                            if (!vector.contains(keyAndCert)) {
                                vector.addElement(keyAndCert);
                            }
                        }
                    } else {
                        KeyAndCert keyAndCert2 = (KeyAndCert) enumerationElements.nextElement();
                        if (!vector.contains(keyAndCert2)) {
                            vector.addElement(keyAndCert2);
                        }
                    }
                }
            }
        }
        if (vector.size() == 0) {
            stringBuffer.append("None\n");
        } else {
            Enumeration enumerationElements3 = vector.elements();
            while (enumerationElements3.hasMoreElements()) {
                stringBuffer.append(enumerationElements3.nextElement());
            }
        }
        stringBuffer.append("\nTemporary Parameters:\n");
        for (int i3 = 0; i3 <= 4; i3++) {
            a(stringBuffer, i3);
        }
        stringBuffer.append("\nRequest client certificate: ");
        stringBuffer.append(this.p ? "yes" : "no");
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    private void a(StringBuffer stringBuffer, int i2) {
        stringBuffer.append("  ");
        stringBuffer.append(j[i2]);
        stringBuffer.append(": ");
        stringBuffer.append(this.m[i2] != null ? "available" : "not set");
        stringBuffer.append("\n");
    }

    public void setRequireClientCertificate(byte[] bArr, Principal[] principalArr) {
        if (bArr == null && principalArr == null) {
            this.p = false;
            return;
        }
        this.p = true;
        this.f = bArr;
        this.g = principalArr;
    }

    public void setRSACertificate(Certificate[] certificateArr, PrivateKey privateKey) throws CertificateException {
        addServerCredentials(SSLContext.convertCertificateChain(certificateArr), privateKey);
    }

    public void setDSACertificate(Certificate[] certificateArr, PrivateKey privateKey) throws CertificateException {
        addServerCredentials(SSLContext.convertCertificateChain(certificateArr), privateKey);
    }

    public void setDHCertificate(Certificate[] certificateArr, PrivateKey privateKey) throws CertificateException {
        addServerCredentials(SSLContext.convertCertificateChain(certificateArr), privateKey);
    }

    public void setDHParameter(DHParameterSpec dHParameterSpec) {
        addTemporaryParameter(dHParameterSpec);
    }

    public void setRSATempKeyPair(KeyPair keyPair) {
        addTemporaryParameter(keyPair);
    }

    public boolean getRequireClientCertificate() {
        return getRequestClientCertificate();
    }
}

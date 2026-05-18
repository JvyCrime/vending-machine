package iaik.security.ssl;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class SSLClientContext extends SSLContext implements Cloneable {
    private Vector f;
    private boolean g;
    private boolean h;
    private boolean i;
    private HashMap j;

    SSLClientContext(int i) {
        super(i);
    }

    public SSLClientContext() {
        this(null, null);
    }

    public SSLClientContext(SecureRandom secureRandom) {
        this(secureRandom, null);
    }

    public SSLClientContext(CipherSuiteList cipherSuiteList) {
        this(null, cipherSuiteList);
    }

    public SSLClientContext(SecureRandom secureRandom, CipherSuiteList cipherSuiteList) {
        super(secureRandom, cipherSuiteList);
    }

    public SSLClientContext(SSLClientContext sSLClientContext) {
        this(0);
        a(sSLClientContext, false);
    }

    @Override // iaik.security.ssl.SSLContext
    void c() {
        super.c();
        this.i = true;
        this.h = true;
        if (this.f == null) {
            this.f = new Vector();
        }
    }

    void a(SSLClientContext sSLClientContext, boolean z) {
        Vector vector;
        super.a((SSLContext) sSLClientContext);
        if (z && (vector = sSLClientContext.f) != null) {
            this.f = (Vector) vector.clone();
        } else {
            this.f = sSLClientContext.f;
        }
        this.g = sSLClientContext.g;
        this.i = sSLClientContext.i;
        this.h = sSLClientContext.h;
        this.j = sSLClientContext.j;
    }

    void a(SSLClientContext sSLClientContext) {
        a(sSLClientContext, false);
    }

    @Override // iaik.security.ssl.SSLContext
    public Object clone() {
        SSLClientContext sSLClientContext = (SSLClientContext) super.clone();
        sSLClientContext.a(this);
        return sSLClientContext;
    }

    public void setCheckExportRestrictions(boolean z) {
        this.g = z;
    }

    boolean a() {
        return this.g;
    }

    public void addClientCredentials(X509Certificate[] x509CertificateArr, PrivateKey privateKey) {
        addClientCredentials(new KeyAndCert(x509CertificateArr, privateKey));
    }

    public void addClientCredentials(KeyAndCert keyAndCert) throws IllegalArgumentException {
        if (ChainVerifier.a()) {
            try {
                if (!SecurityProvider.getSecurityProvider().checkExtendedKeyUsage(keyAndCert.getCertificateChain()[0], true)) {
                    throw new IllegalArgumentException("No a client cert.");
                }
            } catch (Exception unused) {
            }
        }
        if (keyAndCert.getCertificateType() == 256) {
            throw new IllegalArgumentException("Unknown client certificate type!");
        }
        this.f.addElement(keyAndCert);
    }

    public int addClientCredentials(String str, char[] cArr, String str2, String str3) throws KeyStoreException {
        try {
            return addClientCredentials(SecurityProvider.getSecurityProvider().loadKeyStore(str, cArr, str2, str3), cArr);
        } catch (KeyStoreException e) {
            throw e;
        } catch (Exception e2) {
            throw new KeyStoreException(e2, e2.toString()) { // from class: iaik.security.ssl.SSLClientContext.1
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

    public int addClientCredentials(KeyStore keyStore, char[] cArr) throws KeyStoreException {
        Objects.requireNonNull(keyStore, "keyStore must not be null!");
        Objects.requireNonNull(cArr, "password must not be null!");
        Enumeration<String> enumerationAliases = keyStore.aliases();
        int i = 0;
        int i2 = 0;
        while (enumerationAliases.hasMoreElements()) {
            try {
                String strNextElement = enumerationAliases.nextElement();
                i++;
                try {
                    if (keyStore.isKeyEntry(strNextElement)) {
                        Key key = keyStore.getKey(strNextElement, cArr);
                        if (key instanceof PrivateKey) {
                            Certificate[] certificateChain = keyStore.getCertificateChain(strNextElement);
                            if (certificateChain != null && certificateChain.length > 0) {
                                addClientCredentials(Utils.a(certificateChain), (PrivateKey) key);
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
                    }
                } catch (Exception e) {
                    StringBuffer stringBuffer3 = new StringBuffer("Skipped ");
                    stringBuffer3.append(strNextElement);
                    stringBuffer3.append(": ");
                    stringBuffer3.append(e.getMessage());
                    c(stringBuffer3.toString());
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
        stringBuffer5.append(i);
        stringBuffer5.append(" entries.");
        c(stringBuffer5.toString());
        return i2;
    }

    public int addClientCredentials(KeyStore keyStore, char[] cArr, String[] strArr) throws KeyStoreException {
        Key key;
        Objects.requireNonNull(keyStore, "keyStore must not be null!");
        Objects.requireNonNull(cArr, "password must not be null!");
        if (strArr == null || strArr.length == 0) {
            return addClientCredentials(keyStore, cArr);
        }
        int i = 0;
        int i2 = 0;
        for (String str : strArr) {
            try {
                try {
                    i++;
                    if (keyStore.isKeyEntry(str)) {
                        try {
                            key = keyStore.getKey(str, cArr);
                        } catch (Exception e) {
                            StringBuffer stringBuffer = new StringBuffer("Skipped ");
                            stringBuffer.append(str);
                            stringBuffer.append(": ");
                            stringBuffer.append(e.getMessage());
                            c(stringBuffer.toString());
                        }
                        if (key == null) {
                            StringBuffer stringBuffer2 = new StringBuffer("No key entry available for alias \"");
                            stringBuffer2.append(str);
                            stringBuffer2.append("\"");
                            throw new KeyStoreException(stringBuffer2.toString());
                        }
                        if (key instanceof PrivateKey) {
                            Certificate[] certificateChain = keyStore.getCertificateChain(str);
                            if (certificateChain != null && certificateChain.length > 0) {
                                addClientCredentials(Utils.a(certificateChain), (PrivateKey) key);
                                i2++;
                                StringBuffer stringBuffer3 = new StringBuffer("Added ");
                                stringBuffer3.append(str);
                                c(stringBuffer3.toString());
                            } else {
                                StringBuffer stringBuffer4 = new StringBuffer("No certificate chain available for alias \"");
                                stringBuffer4.append(str);
                                stringBuffer4.append("\"");
                                throw new KeyStoreException(stringBuffer4.toString());
                            }
                        } else if (key instanceof SecretKey) {
                            addPSKCredential(new PSKCredential(str, new PreSharedKey(key.getEncoded())));
                            i2++;
                            StringBuffer stringBuffer5 = new StringBuffer("Added ");
                            stringBuffer5.append(str);
                            stringBuffer5.append(" (PSK)");
                            c(stringBuffer5.toString());
                        }
                    } else {
                        StringBuffer stringBuffer6 = new StringBuffer("No key entry available for alias \"");
                        stringBuffer6.append(str);
                        stringBuffer6.append("\"");
                        throw new KeyStoreException(stringBuffer6.toString());
                    }
                } catch (KeyStoreException e2) {
                    throw e2;
                }
            } catch (Exception e3) {
                StringBuffer stringBuffer7 = new StringBuffer("Error getting key store entry: ");
                stringBuffer7.append(e3.toString());
                throw new KeyStoreException(stringBuffer7.toString());
            }
        }
        StringBuffer stringBuffer8 = new StringBuffer("Added ");
        stringBuffer8.append(i2);
        stringBuffer8.append(" of ");
        stringBuffer8.append(i);
        stringBuffer8.append(" entries.");
        c(stringBuffer8.toString());
        return i2;
    }

    public void clearClientCredentials() {
        this.f.removeAllElements();
    }

    public void setUseMaxVersionForRSAPremasterSecret(boolean z) {
        this.h = z;
    }

    boolean b() {
        return this.h;
    }

    public boolean getIgnorePSKIdentityHint() {
        return this.i;
    }

    public void setIgnorePSKIdentityHint(boolean z) {
        this.i = z;
    }

    private static boolean a(X509Certificate[] x509CertificateArr, Principal principal) {
        for (X509Certificate x509Certificate : x509CertificateArr) {
            if (x509Certificate.getIssuerDN().equals(principal)) {
                return true;
            }
        }
        return false;
    }

    protected KeyAndCert[] getClientCredentials(Principal[] principalArr, byte[] bArr, PublicKey publicKey) {
        if (this.f.size() == 0) {
            return new KeyAndCert[0];
        }
        Vector vector = new Vector();
        boolean z = (principalArr == null || principalArr.length == 0) ? false : true;
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        Enumeration enumerationElements = this.f.elements();
        while (enumerationElements.hasMoreElements()) {
            KeyAndCert keyAndCert = (KeyAndCert) enumerationElements.nextElement();
            int iA = Utils.a(keyAndCert, bArr);
            if (iA != -1 && Utils.a(iA, (SignatureAndHashAlgorithmList) null)) {
                X509Certificate[] certificateChain = keyAndCert.getCertificateChain();
                if (certificateChain != null) {
                    if (publicKey != null && (publicKey instanceof DHPublicKey) && (iA == 3 || iA == 4)) {
                        DHParameterSpec params = ((DHPublicKey) publicKey).getParams();
                        DHParameterSpec params2 = ((DHPublicKey) certificateChain[0].getPublicKey()).getParams();
                        if (!params.getG().equals(params2.getG()) || !params.getP().equals(params2.getP())) {
                        }
                    }
                    if ((iA == 65 || iA == 66) && !securityProvider.checkIfOnSameCurve(publicKey, certificateChain[0].getPublicKey())) {
                    }
                }
                if (!z || certificateChain == null) {
                    vector.addElement(Utils.a(keyAndCert, iA));
                } else {
                    X509Certificate[] certificateChain2 = keyAndCert.getCertificateChain();
                    int i = 0;
                    while (true) {
                        if (i < principalArr.length) {
                            if (a(certificateChain2, principalArr[i])) {
                                vector.addElement(Utils.a(keyAndCert, iA));
                                break;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        KeyAndCert[] keyAndCertArr = new KeyAndCert[vector.size()];
        vector.copyInto(keyAndCertArr);
        return keyAndCertArr;
    }

    protected KeyAndCert[] getClientCredentials(Principal[] principalArr, byte[] bArr) {
        return getClientCredentials(principalArr, bArr, null);
    }

    int[] a(String str) {
        HashMap map = this.j;
        int[] iArr = (map == null || str == null) ? null : (int[]) map.get(str);
        if (iArr == null) {
            iArr = this.a;
        }
        return (int[]) iArr.clone();
    }

    SSLClientContext b(String str) {
        int[] iArr;
        HashMap map = this.j;
        if (map == null || str == null || (iArr = (int[]) map.get(str)) == null || (iArr[0] == this.a[0] && iArr[1] == this.a[1])) {
            return this;
        }
        SSLClientContext sSLClientContext = (SSLClientContext) clone();
        sSLClientContext.setAllowedProtocolVersions(iArr[0], iArr[1]);
        if (iArr[0] <= 768) {
            sSLClientContext.setExtensions(null);
        }
        sSLClientContext.updateCipherSuites();
        return sSLClientContext;
    }

    @Override // iaik.security.ssl.SSLContext
    t d() throws IllegalArgumentException, PropertyInitException {
        t tVarD = super.d();
        if (!(this instanceof SSLServerContext) && tVarD != null) {
            try {
                try {
                    String[] strArrA = tVarD.a("protocolVersions", (String[]) null);
                    if (strArrA != null) {
                        if (this.j == null) {
                            this.j = new HashMap(strArrA.length);
                        }
                        for (String str : strArrA) {
                            try {
                                int iIndexOf = str.indexOf("(");
                                if (iIndexOf == -1) {
                                    StringBuffer stringBuffer = new StringBuffer("Invalid format in protocolVersions property: \"");
                                    stringBuffer.append(str);
                                    stringBuffer.append("\". Must be <serverName>[:<serverPort>](<minVersion>,<maxVersion>)");
                                    throw new PropertyInitException(stringBuffer.toString());
                                }
                                int iIndexOf2 = str.indexOf(")");
                                if (iIndexOf2 == -1) {
                                    StringBuffer stringBuffer2 = new StringBuffer("Invalid format in protocolVersions property: \"");
                                    stringBuffer2.append(str);
                                    stringBuffer2.append("\". Must be <serverName>[:<serverPort>](<minVersion>,<maxVersion>)");
                                    throw new PropertyInitException(stringBuffer2.toString());
                                }
                                if (iIndexOf2 <= iIndexOf) {
                                    StringBuffer stringBuffer3 = new StringBuffer("Invalid format in protocolVersions property: \"");
                                    stringBuffer3.append(str);
                                    stringBuffer3.append("\". Must be <serverName>[:<serverPort>](<minVersion>,<maxVersion>)");
                                    throw new PropertyInitException(stringBuffer3.toString());
                                }
                                StringTokenizer stringTokenizer = new StringTokenizer(str.substring(0, iIndexOf), ":");
                                if (stringTokenizer.countTokens() > 2) {
                                    StringBuffer stringBuffer4 = new StringBuffer("Invalid format in protocolVersions property: \"");
                                    stringBuffer4.append(str);
                                    stringBuffer4.append("\". Must be <serverName>[:<serverPort>](<minVersion>,<maxVersion>)");
                                    throw new PropertyInitException(stringBuffer4.toString());
                                }
                                String strTrim = stringTokenizer.nextToken().trim();
                                String string = ":443";
                                if (stringTokenizer.hasMoreTokens()) {
                                    String strTrim2 = stringTokenizer.nextToken().trim();
                                    try {
                                        if (Integer.parseInt(strTrim2) < 0) {
                                            StringBuffer stringBuffer5 = new StringBuffer("Invalid port number in protocolVersions property: \"");
                                            stringBuffer5.append(str);
                                            stringBuffer5.append("\"!");
                                            throw new PropertyInitException(stringBuffer5.toString());
                                        }
                                        StringBuffer stringBuffer6 = new StringBuffer(":");
                                        stringBuffer6.append(strTrim2);
                                        string = stringBuffer6.toString();
                                    } catch (NumberFormatException unused) {
                                        StringBuffer stringBuffer7 = new StringBuffer("Invalid port number in protocolVersions property: \"");
                                        stringBuffer7.append(str);
                                        stringBuffer7.append("\"!");
                                        throw new PropertyInitException(stringBuffer7.toString());
                                    }
                                }
                                StringBuffer stringBuffer8 = new StringBuffer(String.valueOf(strTrim));
                                stringBuffer8.append(string);
                                String string2 = stringBuffer8.toString();
                                StringTokenizer stringTokenizer2 = new StringTokenizer(str.substring(iIndexOf + 1, iIndexOf2), ",");
                                if (stringTokenizer2.countTokens() != 2) {
                                    StringBuffer stringBuffer9 = new StringBuffer("Invalid format in protocolVersions property: \"");
                                    stringBuffer9.append(str);
                                    stringBuffer9.append("\". Must be <serverName>[:<serverPort>]:<minVersion>:<maxVersion>");
                                    throw new PropertyInitException(stringBuffer9.toString());
                                }
                                this.j.put(string2, Utils.a(stringTokenizer2.nextToken().trim(), stringTokenizer2.nextToken().trim()));
                            } catch (IllegalArgumentException e) {
                                StringBuffer stringBuffer10 = new StringBuffer("Error initializing server specific protocol versions from property file: ");
                                stringBuffer10.append(e.getMessage());
                                throw new PropertyInitException(stringBuffer10.toString());
                            }
                        }
                    }
                } catch (IllegalArgumentException e2) {
                    StringBuffer stringBuffer11 = new StringBuffer("SSLContext Properties file contains invalid property: ");
                    stringBuffer11.append(e2.getMessage());
                    throw new IllegalArgumentException(stringBuffer11.toString());
                }
            } catch (PropertyInitException e3) {
                throw e3;
            }
        }
        return tVarD;
    }

    @Override // iaik.security.ssl.SSLContext
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        if (!(this instanceof SSLServerContext)) {
            stringBuffer.append("\nAvailable certificates:\n");
            if (this.f.size() == 0) {
                stringBuffer.append("None\n");
            } else {
                Enumeration enumerationElements = this.f.elements();
                while (enumerationElements.hasMoreElements()) {
                    stringBuffer.append(enumerationElements.nextElement().toString());
                }
            }
        }
        return stringBuffer.toString();
    }
}

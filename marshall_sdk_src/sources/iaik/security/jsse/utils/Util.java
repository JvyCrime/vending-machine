package iaik.security.jsse.utils;

import iaik.security.ssl.CipherSuite;
import iaik.security.ssl.CipherSuiteList;
import iaik.security.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Vector;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public class Util {
    protected static CipherSuiteList CIPHER_SUITES = null;
    public static final String DEFAULT_KEY_STORE = "javax.net.ssl.keyStore";
    public static final String DEFAULT_KEY_STORE_PASSWORD = "javax.net.ssl.keyStorePassword";
    public static final String DEFAULT_KEY_STORE_TYPE = "javax.net.ssl.keyStoreType";
    public static final String DEFAULT_TRUST_STORE = "javax.net.ssl.trustStore";
    public static final String DEFAULT_TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
    public static final String DEFAULT_TRUST_STORE_TYPE = "javax.net.ssl.trustStoreType";
    public static final String SSLv2 = "SSLv2Hello";
    public static final String SSLv3 = "SSLv3";
    public static final String TLSv1 = "TLSv1";
    public static final String TLSv11 = "TLSv1.1";
    public static final String TLSv12 = "TLSv1.2";
    static boolean a;
    private static final Hashtable b;
    private static final Hashtable c;
    public static final String[] supportedProtocolVersions_;

    public static String toProtocol(int i) {
        if (i == 2) {
            return SSLv2;
        }
        switch (i) {
            case SSLContext.VERSION_SSL30 /* 768 */:
                return SSLv3;
            case SSLContext.VERSION_TLS10 /* 769 */:
                return TLSv1;
            case SSLContext.VERSION_TLS11 /* 770 */:
                return TLSv11;
            case SSLContext.VERSION_TLS12 /* 771 */:
                return TLSv12;
            default:
                return "NONE";
        }
    }

    static {
        Hashtable hashtable = new Hashtable(60);
        b = hashtable;
        c = new Hashtable(60);
        String property = System.getProperty("java.version");
        a = property != null && property.startsWith("1.5");
        hashtable.put("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", CipherSuite.SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_DHE_DSS_EXPORT_WITH_DES_40_CBC_SHA", CipherSuite.SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", CipherSuite.SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA);
        hashtable.put("SSL_DHE_DSS_WITH_DES_CBC_SHA", CipherSuite.SSL_DHE_DSS_WITH_DES_CBC_SHA);
        hashtable.put("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", CipherSuite.SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_DHE_RSA_EXPORT_WITH_DES_40_CBC_SHA", CipherSuite.SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", CipherSuite.SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA);
        hashtable.put("SSL_DHE_RSA_WITH_DES_CBC_SHA", CipherSuite.SSL_DHE_RSA_WITH_DES_CBC_SHA);
        hashtable.put("SSL_DH_ANON_EXPORT_WITH_DES40_CBC_SHA", CipherSuite.SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_DH_ANON_EXPORT_WITH_DES_40_CBC_SHA", CipherSuite.SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_DH_ANON_EXPORT_WITH_RC4_40_MD5", CipherSuite.SSL_DH_anon_EXPORT_WITH_RC4_40_MD5);
        hashtable.put("SSL_DH_ANON_WITH_3DES_EDE_CBC_SHA", CipherSuite.SSL_DH_anon_WITH_3DES_EDE_CBC_SHA);
        hashtable.put("SSL_DH_ANON_WITH_DES_CBC_SHA", CipherSuite.SSL_DH_anon_WITH_DES_CBC_SHA);
        hashtable.put("SSL_DH_ANON_WITH_RC4_128_MD5", CipherSuite.SSL_DH_anon_WITH_RC4_128_MD5);
        hashtable.put("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", CipherSuite.SSL_RSA_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_RSA_EXPORT_WITH_DES_40_CBC_SHA", CipherSuite.SSL_RSA_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_RSA_EXPORT_WITH_RC4_40_MD5", CipherSuite.SSL_RSA_EXPORT_WITH_RC4_40_MD5);
        hashtable.put("SSL_RSA_WITH_3DES_EDE_CBC_SHA", CipherSuite.SSL_RSA_WITH_3DES_EDE_CBC_SHA);
        hashtable.put("SSL_RSA_WITH_DES_CBC_SHA", CipherSuite.SSL_RSA_WITH_DES_CBC_SHA);
        hashtable.put("SSL_RSA_WITH_NULL_MD5", CipherSuite.SSL_RSA_WITH_NULL_MD5);
        hashtable.put("SSL_RSA_WITH_NULL_SHA", CipherSuite.SSL_RSA_WITH_NULL_SHA);
        hashtable.put("SSL_RSA_WITH_RC4_128_MD5", CipherSuite.SSL_RSA_WITH_RC4_128_MD5);
        hashtable.put("SSL_RSA_WITH_RC4_128_SHA", CipherSuite.SSL_RSA_WITH_RC4_128_SHA);
        hashtable.put("SSL_DHE_DSS_WITH_AES_128_CBC_SHA", CipherSuite.SSL_DHE_DSS_WITH_AES_128_CBC_SHA);
        hashtable.put("SSL_DHE_DSS_WITH_AES_256_CBC_SHA", CipherSuite.SSL_DHE_DSS_WITH_AES_256_CBC_SHA);
        hashtable.put("SSL_DHE_RSA_WITH_AES_128_CBC_SHA", CipherSuite.SSL_DHE_RSA_WITH_AES_128_CBC_SHA);
        hashtable.put("SSL_DHE_RSA_WITH_AES_256_CBC_SHA", CipherSuite.SSL_DHE_RSA_WITH_AES_256_CBC_SHA);
        hashtable.put("SSL_DH_ANON_WITH_AES_128_CBC_SHA", CipherSuite.SSL_DH_anon_WITH_AES_128_CBC_SHA);
        hashtable.put("SSL_DH_ANON_WITH_AES_256_CBC_SHA", CipherSuite.SSL_DH_anon_WITH_AES_256_CBC_SHA);
        hashtable.put("SSL_RSA_WITH_AES_128_CBC_SHA", CipherSuite.SSL_RSA_WITH_AES_128_CBC_SHA);
        hashtable.put("SSL_RSA_WITH_AES_256_CBC_SHA", CipherSuite.SSL_RSA_WITH_AES_256_CBC_SHA);
        hashtable.put("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA);
        hashtable.put("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", CipherSuite.TLS_DHE_DSS_WITH_AES_256_CBC_SHA);
        hashtable.put("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA);
        hashtable.put("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA);
        hashtable.put("TLS_DH_ANON_WITH_AES_128_CBC_SHA", CipherSuite.TLS_DH_anon_WITH_AES_128_CBC_SHA);
        hashtable.put("TLS_DH_ANON_WITH_AES_256_CBC_SHA", CipherSuite.TLS_DH_anon_WITH_AES_256_CBC_SHA);
        hashtable.put("TLS_RSA_WITH_AES_128_CBC_SHA", CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA);
        hashtable.put("TLS_RSA_WITH_AES_256_CBC_SHA", CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA);
        hashtable.put("TLS_DH_DSS_WITH_AES_128_CBC_SHA", CipherSuite.TLS_DH_DSS_WITH_AES_128_CBC_SHA);
        hashtable.put("TLS_DH_DSS_WITH_AES_256_CBC_SHA", CipherSuite.TLS_DH_DSS_WITH_AES_256_CBC_SHA);
        hashtable.put("TLS_DH_RSA_WITH_AES_128_CBC_SHA", CipherSuite.TLS_DH_RSA_WITH_AES_128_CBC_SHA);
        hashtable.put("TLS_DH_RSA_WITH_AES_256_CBC_SHA", CipherSuite.TLS_DH_RSA_WITH_AES_256_CBC_SHA);
        hashtable.put("SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5", CipherSuite.SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5);
        hashtable.put("SSL_DH_ANON_WITH_RC4_MD5", CipherSuite.SSL_DH_anon_WITH_RC4_MD5);
        hashtable.put("SSL_RSA_WITH_RC4_MD5", CipherSuite.SSL_RSA_WITH_RC4_MD5);
        hashtable.put("SSL_RSA_WITH_RC4_SHA", CipherSuite.SSL_RSA_WITH_RC4_SHA);
        hashtable.put("SSL_DH_DSS_WITH_3DES_EDE_CBC_SHA", CipherSuite.SSL_DH_DSS_WITH_3DES_EDE_CBC_SHA);
        hashtable.put("SSL_DH_DSS_WITH_DES_CBC_SHA", CipherSuite.SSL_DH_DSS_WITH_DES_CBC_SHA);
        hashtable.put("SSL_DH_RSA_WITH_3DES_EDE_CBC_SHA", CipherSuite.SSL_DH_RSA_WITH_3DES_EDE_CBC_SHA);
        hashtable.put("SSL_DH_RSA_WITH_DES_CBC_SHA", CipherSuite.SSL_DH_RSA_WITH_DES_CBC_SHA);
        hashtable.put("SSL_DH_DSS_EXPORT_WITH_DES_40_CBC_SHA", CipherSuite.SSL_DH_DSS_EXPORT_WITH_DES40_CBC_SHA);
        hashtable.put("SSL_DH_RSA_EXPORT_WITH_DES_40_CBC_SHA", CipherSuite.SSL_DH_RSA_EXPORT_WITH_DES40_CBC_SHA);
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            c.put((CipherSuite) b.get(str), str);
        }
        supportedProtocolVersions_ = new String[]{SSLv2, SSLv3, TLSv1, TLSv11, TLSv12};
        CIPHER_SUITES = new CipherSuiteList(3);
    }

    public static X509Certificate recode(javax.security.cert.X509Certificate x509Certificate) {
        try {
            return new iaik.x509.X509Certificate(x509Certificate.getEncoded());
        } catch (Exception unused) {
            return null;
        }
    }

    public static javax.security.cert.X509Certificate recode(X509Certificate x509Certificate) {
        try {
            return javax.security.cert.X509Certificate.getInstance(((iaik.x509.X509Certificate) x509Certificate).toByteArray());
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static X509Certificate[] convert(javax.security.cert.X509Certificate[] x509CertificateArr) {
        iaik.x509.X509Certificate[] x509CertificateArr2 = new iaik.x509.X509Certificate[x509CertificateArr.length];
        for (int i = 0; i < x509CertificateArr.length; i++) {
            x509CertificateArr2[i] = recode(x509CertificateArr[i]);
        }
        return x509CertificateArr2;
    }

    public static javax.security.cert.X509Certificate[] convert(X509Certificate[] x509CertificateArr) {
        javax.security.cert.X509Certificate[] x509CertificateArr2 = new javax.security.cert.X509Certificate[x509CertificateArr.length];
        for (int i = 0; i < x509CertificateArr.length; i++) {
            x509CertificateArr2[i] = recode(x509CertificateArr[i]);
        }
        return x509CertificateArr2;
    }

    public static String[] getJSSEPlugableCipherSuites() {
        Enumeration enumerationKeys = b.keys();
        Vector vector = new Vector();
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            if (((CipherSuite) b.get(str)).isAvailable()) {
                vector.addElement(str);
            }
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    public static String[] getImplementedCipherSuites() {
        CipherSuiteList cipherSuiteList = new CipherSuiteList(3);
        String[] strArr = new String[cipherSuiteList.size()];
        for (int i = 0; i < cipherSuiteList.size(); i++) {
            strArr[i] = cipherSuiteList.elementAt(i).getName();
        }
        return strArr;
    }

    public static String isPlugable(CipherSuite cipherSuite) {
        return a ? (String) c.get(cipherSuite) : cipherSuite.getName();
    }

    public static CipherSuiteList fromStringArray(String[] strArr) {
        CipherSuiteList cipherSuiteList = new CipherSuiteList();
        for (String str : strArr) {
            CipherSuite cipherSuiteFromString = fromString(str);
            if (cipherSuiteFromString != null) {
                cipherSuiteList.add(cipherSuiteFromString);
            }
        }
        return cipherSuiteList;
    }

    public static CipherSuite fromString(String str) {
        CipherSuite cipherSuiteElementAt;
        CipherSuite cipherSuiteElementAt2 = null;
        if (str == null) {
            return null;
        }
        CipherSuite cipherSuite = (CipherSuite) b.get(str.toUpperCase());
        if (cipherSuite != null) {
            return cipherSuite;
        }
        int i = 0;
        while (true) {
            if (i >= CIPHER_SUITES.size()) {
                cipherSuiteElementAt = cipherSuite;
                break;
            }
            String upperCase = CIPHER_SUITES.elementAt(i).toString().toUpperCase();
            String upperCase2 = str.toUpperCase();
            if (upperCase.equals(upperCase2)) {
                cipherSuiteElementAt = CIPHER_SUITES.elementAt(i);
                break;
            }
            if (upperCase2.length() > 4 && upperCase.length() > 4 && upperCase2.substring(4).equals(upperCase.substring(4)) && ((upperCase2.startsWith("SSL") || upperCase2.startsWith("TLS")) && (upperCase.startsWith("SSL") || upperCase.startsWith("TLS")))) {
                cipherSuiteElementAt2 = CIPHER_SUITES.elementAt(i);
            }
            i++;
        }
        return (cipherSuiteElementAt != null || cipherSuiteElementAt2 == null) ? cipherSuiteElementAt : cipherSuiteElementAt2;
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x006b: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:25:0x006b */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static javax.net.ssl.X509KeyManager getDefaultKeyManager() throws java.lang.Throwable {
        /*
            java.lang.String r0 = "javax.net.ssl.keyStorePassword"
            iaik.security.jsse.utils.Debug r1 = iaik.security.jsse.utils.Debug.getInstance()
            r2 = 0
            java.lang.String r3 = "javax.net.ssl.keyStoreType"
            java.lang.String r3 = java.lang.System.getProperty(r3)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.security.KeyStore r3 = java.security.KeyStore.getInstance(r3)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.lang.String r5 = "javax.net.ssl.keyStore"
            java.lang.String r5 = java.lang.System.getProperty(r5)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.lang.String r5 = java.lang.System.getProperty(r0)     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            char[] r5 = r5.toCharArray()     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            r3.load(r4, r5)     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            java.lang.String r5 = "SunX509"
            javax.net.ssl.KeyManagerFactory r5 = javax.net.ssl.KeyManagerFactory.getInstance(r5)     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            java.lang.String r0 = java.lang.System.getProperty(r0)     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            char[] r0 = r0.toCharArray()     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            r5.init(r3, r0)     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            javax.net.ssl.KeyManager[] r0 = r5.getKeyManagers()     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            r3 = 0
        L3d:
            int r5 = r0.length     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            if (r3 < r5) goto L41
            goto L64
        L41:
            r5 = r0[r3]     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            boolean r5 = r5 instanceof javax.net.ssl.X509KeyManager     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            if (r5 == 0) goto L4f
            r0 = r0[r3]     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            javax.net.ssl.X509KeyManager r0 = (javax.net.ssl.X509KeyManager) r0     // Catch: java.lang.Exception -> L52 java.lang.Throwable -> L6a
            r4.close()     // Catch: java.io.IOException -> L4e
        L4e:
            return r0
        L4f:
            int r3 = r3 + 1
            goto L3d
        L52:
            r0 = move-exception
            goto L58
        L54:
            r0 = move-exception
            goto L6c
        L56:
            r0 = move-exception
            r4 = r2
        L58:
            java.lang.String r3 = "Could not load default keystore"
            r1.println(r3)     // Catch: java.lang.Throwable -> L6a
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L6a
            r1.println(r0)     // Catch: java.lang.Throwable -> L6a
        L64:
            if (r4 == 0) goto L69
            r4.close()     // Catch: java.io.IOException -> L69
        L69:
            return r2
        L6a:
            r0 = move-exception
            r2 = r4
        L6c:
            if (r2 == 0) goto L71
            r2.close()     // Catch: java.io.IOException -> L71
        L71:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.jsse.utils.Util.getDefaultKeyManager():javax.net.ssl.X509KeyManager");
    }

    public static X509TrustManager getDefaultTrustManager() {
        KeyStore keyStore;
        Debug debug = Debug.getInstance();
        String property = System.getProperty(DEFAULT_TRUST_STORE);
        String property2 = System.getProperty(DEFAULT_TRUST_STORE_TYPE);
        String property3 = System.getProperty(DEFAULT_TRUST_STORE_PASSWORD);
        try {
            if (property2 != null) {
                keyStore = KeyStore.getInstance(System.getProperty(DEFAULT_TRUST_STORE_TYPE));
            } else {
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            }
            if (property3 == null) {
                property3 = "";
            }
            if (property != null) {
                StringBuffer stringBuffer = new StringBuffer("Using Truststore :");
                stringBuffer.append(property);
                debug.println(stringBuffer.toString());
                if (a(keyStore, property, property3)) {
                    return a(keyStore);
                }
                debug.println("Failed -> using null");
                return null;
            }
            String property4 = System.getProperty("java.home");
            StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(property4));
            stringBuffer2.append("/lib/security/jssecacerts");
            if (a(keyStore, stringBuffer2.toString(), property3)) {
                return a(keyStore);
            }
            StringBuffer stringBuffer3 = new StringBuffer(String.valueOf(property4));
            stringBuffer3.append("/lib/security/cacerts");
            if (a(keyStore, stringBuffer3.toString(), property3)) {
                return a(keyStore);
            }
            return null;
        } catch (Exception e) {
            debug.println(e.getMessage());
            return null;
        }
    }

    private static X509TrustManager a(KeyStore keyStore) throws Exception {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        for (int i = 0; i < trustManagers.length; i++) {
            if (trustManagers[i] instanceof X509TrustManager) {
                Debug.getInstance().println("Found X509 TrustManager");
                return (X509TrustManager) trustManagers[i];
            }
        }
        return null;
    }

    public static String[] toProtocol(int[] iArr) {
        if (iArr == null) {
            throw new IllegalArgumentException("protocolVersions must not be null!");
        }
        String[] strArr = {"NONE"};
        int i = iArr[0];
        int i2 = iArr[1];
        if (i2 == 2) {
            return new String[]{SSLv2};
        }
        if (i2 == 768) {
            if (i == 2) {
                return new String[]{SSLv2, SSLv3};
            }
            return new String[]{SSLv3};
        }
        if (i2 == 769) {
            if (i == 2) {
                return new String[]{SSLv2, SSLv3, TLSv1};
            }
            if (i == 768) {
                return new String[]{SSLv3, TLSv1};
            }
            return new String[]{TLSv1};
        }
        if (i2 == 770) {
            if (i == 2) {
                return new String[]{SSLv2, SSLv3, TLSv1, TLSv11};
            }
            if (i == 768) {
                return new String[]{SSLv3, TLSv1, TLSv11};
            }
            if (i == 769) {
                return new String[]{TLSv1, TLSv11};
            }
            return new String[]{TLSv11};
        }
        if (i2 != 771) {
            return strArr;
        }
        if (i == 768) {
            return new String[]{SSLv3, TLSv1, TLSv11, TLSv12};
        }
        if (i == 769) {
            return new String[]{TLSv1, TLSv11, TLSv12};
        }
        if (i == 770) {
            return new String[]{TLSv11, TLSv12};
        }
        return new String[]{TLSv12};
    }

    public static int[] toProtocolVersion(String[] strArr) {
        if (strArr == null) {
            throw new IllegalArgumentException("protocols must not be null!");
        }
        int iA = a(strArr[0]);
        int iA2 = a(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            int iA3 = a(strArr[i]);
            if (iA3 < iA) {
                iA = iA3;
            } else if (iA3 > iA2) {
                iA2 = iA3;
            }
        }
        return new int[]{iA, iA2};
    }

    private static int a(String str) {
        Objects.requireNonNull(str, "protocol must not be null!");
        String upperCase = str.toUpperCase();
        if (upperCase.equals("SSLV2HELLO")) {
            return 2;
        }
        if (upperCase.equals("SSLV3")) {
            return SSLContext.VERSION_SSL30;
        }
        if (upperCase.equals("TLSV1")) {
            return SSLContext.VERSION_TLS10;
        }
        if (upperCase.equals("TLSV1.1")) {
            return SSLContext.VERSION_TLS11;
        }
        if (upperCase.equals("TLSV1.2")) {
            return SSLContext.VERSION_TLS12;
        }
        StringBuffer stringBuffer = new StringBuffer("Protocol ");
        stringBuffer.append(str);
        stringBuffer.append(" not supported!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    private static boolean a(KeyStore keyStore, String str, String str2) throws Throwable {
        FileInputStream fileInputStream;
        Debug debug = Debug.getInstance();
        StringBuffer stringBuffer = new StringBuffer("Checking ");
        stringBuffer.append(str);
        stringBuffer.append(" for truststore");
        debug.println(stringBuffer.toString());
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            keyStore.load(fileInputStream, str2.toCharArray());
            try {
                fileInputStream.close();
            } catch (IOException unused) {
            }
            return true;
        } catch (Exception e2) {
            e = e2;
            fileInputStream2 = fileInputStream;
            debug.println("Could not load default trust store");
            debug.println(e.getMessage());
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException unused2) {
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException unused3) {
                }
            }
            throw th;
        }
    }
}

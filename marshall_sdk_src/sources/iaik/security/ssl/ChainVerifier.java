package iaik.security.ssl;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public class ChainVerifier {
    public static final int CACHE_SIZE = 64;
    private static boolean a = true;
    protected int cacheSize;
    protected Hashtable cachedCerts;
    protected boolean checkServerName;
    protected boolean nullTrusted;
    protected Hashtable trustedCerts;

    boolean b() {
        return true;
    }

    static void a(boolean z) {
        a = z;
    }

    static boolean a() {
        return a;
    }

    protected ChainVerifier(int i) {
    }

    public ChainVerifier() {
        this.trustedCerts = new Hashtable(10);
        this.nullTrusted = false;
        this.cacheSize = 64;
        this.cachedCerts = new Hashtable(this.cacheSize);
        this.checkServerName = false;
    }

    public void addTrustedCertificate(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            this.nullTrusted = true;
        } else {
            this.trustedCerts.put(x509Certificate.getSubjectDN(), x509Certificate);
        }
    }

    public void removeTrustedCertificate(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            this.nullTrusted = false;
        } else {
            this.trustedCerts.remove(x509Certificate.getSubjectDN());
            clearCachedCertificates();
        }
    }

    public void clearTrustedCertificates() {
        this.trustedCerts.clear();
        clearCachedCertificates();
    }

    public int size() {
        return this.trustedCerts.size();
    }

    public Enumeration getTrustedPrincipals() {
        return this.trustedCerts.keys();
    }

    public Principal[] getTrustedPrincipalsArray() {
        Enumeration trustedPrincipals = getTrustedPrincipals();
        if (trustedPrincipals == null) {
            return new Principal[0];
        }
        Vector vector = new Vector();
        while (trustedPrincipals.hasMoreElements()) {
            vector.addElement((Principal) trustedPrincipals.nextElement());
        }
        Principal[] principalArr = new Principal[vector.size()];
        vector.copyInto(principalArr);
        return principalArr;
    }

    public Enumeration getTrustedCertificates() {
        return this.trustedCerts.elements();
    }

    public X509Certificate[] getTrustedCertificatesArray() {
        Enumeration trustedCertificates = getTrustedCertificates();
        if (trustedCertificates == null) {
            return new X509Certificate[0];
        }
        Vector vector = new Vector();
        while (trustedCertificates.hasMoreElements()) {
            vector.addElement((X509Certificate) trustedCertificates.nextElement());
        }
        X509Certificate[] x509CertificateArr = new X509Certificate[vector.size()];
        vector.copyInto(x509CertificateArr);
        return x509CertificateArr;
    }

    protected boolean isTrustedCertificate(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            return this.nullTrusted;
        }
        X509Certificate x509Certificate2 = (X509Certificate) this.trustedCerts.get(x509Certificate.getSubjectDN());
        if (x509Certificate2 == null) {
            return false;
        }
        return x509Certificate.equals(x509Certificate2);
    }

    protected boolean verifyCertificate(X509Certificate x509Certificate, X509Certificate x509Certificate2) throws Exception {
        x509Certificate.checkValidity();
        if (isCachedCertificate(x509Certificate) || isTrustedCertificate(x509Certificate)) {
            return true;
        }
        if (x509Certificate2 == null) {
            return false;
        }
        x509Certificate.verify(x509Certificate2.getPublicKey());
        if (!Utils.equalsBlock(x509Certificate.getSignature(), x509Certificate2.getSignature()) || x509Certificate.equals(x509Certificate2)) {
            return false;
        }
        StringBuffer stringBuffer = new StringBuffer("Cert ");
        stringBuffer.append(x509Certificate.getSubjectDN());
        stringBuffer.append(" and ");
        stringBuffer.append(x509Certificate2.getSubjectDN());
        stringBuffer.append(" have same signature value!");
        throw new CertificateException(stringBuffer.toString());
    }

    protected X509Certificate getIssuerCertificate(X509Certificate x509Certificate) {
        return (X509Certificate) this.trustedCerts.get(x509Certificate.getIssuerDN());
    }

    public boolean verifyChain(X509Certificate[] x509CertificateArr, SSLTransport sSLTransport) throws SSLCertificateRuntimeException {
        return a(x509CertificateArr, sSLTransport, true);
    }

    boolean a(X509Certificate[] x509CertificateArr, SSLTransport sSLTransport, boolean z) throws SSLCertificateRuntimeException {
        Hashtable hashtable;
        boolean z2 = sSLTransport.b != null;
        if (x509CertificateArr == null) {
            if (z2) {
                StringBuffer stringBuffer = new StringBuffer("ChainVerifier: Empty peer certificate chain, ");
                stringBuffer.append(this.nullTrusted ? "OK" : "NOT OK");
                sSLTransport.a(stringBuffer.toString());
            }
            return this.nullTrusted;
        }
        if (z) {
            dumpCertificateChain(x509CertificateArr, sSLTransport);
        }
        if (!b() && ((hashtable = this.trustedCerts) == null || hashtable.isEmpty())) {
            if (z2) {
                sSLTransport.a("ChainVerifier: No trusted certificates set, rejected.");
            }
            return false;
        }
        if (z) {
            if (sSLTransport.getUseClientMode()) {
                if (!verifyServer(x509CertificateArr, sSLTransport)) {
                    return false;
                }
            } else if (!verifyClient(x509CertificateArr, sSLTransport)) {
                return false;
            }
        }
        try {
            int length = x509CertificateArr.length;
            int i = 0;
            while (true) {
                int i2 = length - 1;
                if (i < i2) {
                    int i3 = i + 1;
                    if (verifyCertificate(x509CertificateArr[i], x509CertificateArr[i3])) {
                        if (i > 0) {
                            cacheCertificates(x509CertificateArr, 0, i - 1);
                        }
                        a(sSLTransport, z2);
                        return true;
                    }
                    i = i3;
                } else {
                    X509Certificate x509Certificate = x509CertificateArr[i2];
                    if (x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN())) {
                        if (verifyCertificate(x509Certificate, x509Certificate)) {
                            if (length > 1) {
                                cacheCertificates(x509CertificateArr, 0, length - 2);
                            }
                            a(sSLTransport, z2);
                            return true;
                        }
                    } else {
                        X509Certificate issuerCertificate = getIssuerCertificate(x509Certificate);
                        if (verifyCertificate(x509Certificate, issuerCertificate)) {
                            cacheCertificates(x509CertificateArr, 0, i2);
                            a(sSLTransport, z2);
                            return true;
                        }
                        if (issuerCertificate != null && isTrustedCertificate(issuerCertificate)) {
                            cacheCertificates(x509CertificateArr, 0, i2);
                            a(sSLTransport, z2);
                            return true;
                        }
                    }
                    if (size() == 0) {
                        if (z2) {
                            sSLTransport.a("ChainVerifier: No trusted certificate found, OK anyway.");
                        }
                        return true;
                    }
                    if (z2) {
                        sSLTransport.a("ChainVerifier: No trusted certificate found, rejected.");
                    }
                    return false;
                }
            }
        } catch (Exception e) {
            if (z2) {
                StringBuffer stringBuffer2 = new StringBuffer("ChainVerifier: Error verifying certificate chain: ");
                stringBuffer2.append(e);
                sSLTransport.a(stringBuffer2.toString());
            }
            return false;
        }
    }

    public boolean verifyChain(X509Certificate[] x509CertificateArr, SSLTransport sSLTransport, int i, byte[] bArr, byte[] bArr2) {
        return verifyChain(x509CertificateArr, sSLTransport);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ab A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean verifyServer(java.security.cert.X509Certificate[] r9, iaik.security.ssl.SSLTransport r10) {
        /*
            Method dump skipped, instruction units count: 278
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.ChainVerifier.verifyServer(java.security.cert.X509Certificate[], iaik.security.ssl.SSLTransport):boolean");
    }

    private static boolean a(String[] strArr, String str) {
        for (String str2 : strArr) {
            String lowerCase = str2.toLowerCase();
            if (lowerCase.startsWith(Marker.ANY_MARKER)) {
                String strSubstring = lowerCase.substring(1);
                if (strSubstring.length() == 0 || (str != null && str.endsWith(strSubstring))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean a(X509Certificate x509Certificate, SSLTransport sSLTransport, ServerName[] serverNameArr, boolean z) {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        int i = 0;
        boolean z2 = false;
        while (true) {
            if (i >= serverNameArr.length) {
                break;
            }
            ServerName serverName = serverNameArr[i];
            ServerName[] tLSServerName = securityProvider.getTLSServerName(serverName.getType(), x509Certificate);
            if (tLSServerName != null) {
                Vector vector = new Vector(tLSServerName.length);
                int i2 = 0;
                while (true) {
                    if (i2 >= tLSServerName.length) {
                        break;
                    }
                    if (serverName.equals(tLSServerName[i2])) {
                        z2 = true;
                        break;
                    }
                    try {
                        vector.add(tLSServerName[i2].getName());
                    } catch (UnsupportedEncodingException unused) {
                    }
                    i2++;
                }
                if (z2) {
                    break;
                }
                String[] strArr = new String[vector.size()];
                vector.copyInto(strArr);
                try {
                    if (a(strArr, serverName.getName())) {
                        z2 = true;
                        break;
                    }
                } catch (UnsupportedEncodingException unused2) {
                    continue;
                }
            }
            i++;
        }
        if (z2) {
            if (z) {
                sSLTransport.a("ChainVerifier: Server name verified.");
            }
        } else if (z) {
            sSLTransport.a("ChainVerifier: Server certificate does not match to requested server names.");
        }
        return z2;
    }

    protected boolean verifyClient(X509Certificate[] x509CertificateArr, SSLTransport sSLTransport) {
        if (!a()) {
            return true;
        }
        boolean z = sSLTransport.b != null;
        try {
            if (SecurityProvider.getSecurityProvider().checkExtendedKeyUsage(x509CertificateArr[0], true)) {
                return true;
            }
            if (z) {
                sSLTransport.a("Certificate not appropriate for client authentication! Inappropriate ExtendedKeyUsage extension!");
            }
        } catch (CertificateException e) {
            if (z) {
                sSLTransport.a(e.toString());
            }
        }
        return false;
    }

    public void setCheckServerName(boolean z) {
        this.checkServerName = z;
    }

    public boolean getCheckServerName() {
        return this.checkServerName;
    }

    protected void cacheCertificate(X509Certificate x509Certificate) {
        if (this.cachedCerts.size() >= this.cacheSize) {
            clearCachedCertificates();
        }
        this.cachedCerts.put(x509Certificate, x509Certificate);
    }

    protected void cacheCertificates(X509Certificate[] x509CertificateArr, int i, int i2) {
        while (i <= i2) {
            cacheCertificate(x509CertificateArr[i]);
            i++;
        }
    }

    protected boolean isCachedCertificate(X509Certificate x509Certificate) {
        return this.cachedCerts.containsKey(x509Certificate);
    }

    protected void clearCachedCertificates() {
        this.cachedCerts.clear();
    }

    protected void setCacheSize(int i) {
        this.cacheSize = i;
    }

    protected void dumpCertificateChain(X509Certificate[] x509CertificateArr, SSLTransport sSLTransport) {
        if (sSLTransport.b != null) {
            a(x509CertificateArr, sSLTransport);
        }
    }

    public X509Certificate[] getCertificateChain(int i, URLAndOptionalHash[] uRLAndOptionalHashArr) throws Throwable {
        InputStream inputStreamA;
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        InputStream inputStream = null;
        if (i == 0) {
            X509Certificate[] x509CertificateArr = new X509Certificate[uRLAndOptionalHashArr.length];
            for (int i2 = 0; i2 < uRLAndOptionalHashArr.length; i2++) {
                URLAndOptionalHash uRLAndOptionalHash = uRLAndOptionalHashArr[i2];
                try {
                    try {
                        inputStreamA = a(i, uRLAndOptionalHash);
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (SSLCertificateException e) {
                    throw e;
                } catch (IOException e2) {
                    e = e2;
                }
                try {
                    try {
                        X509Certificate x509Certificate = securityProvider.getX509Certificate(inputStreamA);
                        byte[] hash = uRLAndOptionalHash.getHash();
                        if (hash != null) {
                            try {
                                byte[] encoded = x509Certificate.getEncoded();
                                a(encoded, 0, encoded.length, hash, uRLAndOptionalHash.getURL());
                            } catch (Exception e3) {
                                StringBuffer stringBuffer = new StringBuffer("Cannot check hash for certificate from url ");
                                stringBuffer.append(uRLAndOptionalHash.getURL());
                                stringBuffer.append(": ");
                                stringBuffer.append(e3.toString());
                                throw new SSLCertificateException(stringBuffer.toString(), 2, 80, false, null);
                            }
                        }
                        x509CertificateArr[i2] = x509Certificate;
                        if (inputStreamA != null) {
                            try {
                                inputStreamA.close();
                            } catch (Exception unused) {
                            }
                        }
                    } catch (Exception e4) {
                        StringBuffer stringBuffer2 = new StringBuffer("Cannot parse certificate from url ");
                        stringBuffer2.append(uRLAndOptionalHash.getURL());
                        stringBuffer2.append(": ");
                        stringBuffer2.append(e4.toString());
                        throw new SSLCertificateException(stringBuffer2.toString(), 2, 111, false, null);
                    }
                } catch (SSLCertificateException e5) {
                    throw e5;
                } catch (IOException e6) {
                    e = e6;
                    StringBuffer stringBuffer3 = new StringBuffer("Cannot load certificate from url ");
                    stringBuffer3.append(uRLAndOptionalHash.getURL());
                    stringBuffer3.append(": ");
                    stringBuffer3.append(e.toString());
                    throw new SSLCertificateException(stringBuffer3.toString(), 2, 111, false, null);
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStreamA;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception unused2) {
                        }
                    }
                    throw th;
                }
            }
            return x509CertificateArr;
        }
        URLAndOptionalHash uRLAndOptionalHash2 = uRLAndOptionalHashArr[0];
        try {
            try {
                try {
                    InputStream inputStreamA2 = a(i, uRLAndOptionalHash2);
                    v vVar = new v(4096);
                    int iA = Utils.a(inputStreamA2, vVar, new byte[4096]);
                    byte[] bArrA = vVar.a();
                    try {
                        X509Certificate[] x509Certificates = securityProvider.getX509Certificates(bArrA);
                        byte[] hash2 = uRLAndOptionalHash2.getHash();
                        if (hash2 != null) {
                            a(bArrA, 0, iA, hash2, uRLAndOptionalHash2.getURL());
                        }
                        if (inputStreamA2 != null) {
                            try {
                                inputStreamA2.close();
                            } catch (Exception unused3) {
                            }
                        }
                        return x509Certificates;
                    } catch (Exception e7) {
                        StringBuffer stringBuffer4 = new StringBuffer("Cannot parse pki path from url ");
                        stringBuffer4.append(uRLAndOptionalHash2.getURL());
                        stringBuffer4.append(": ");
                        stringBuffer4.append(e7.toString());
                        throw new SSLCertificateException(stringBuffer4.toString(), 2, 111, false, null);
                    }
                } catch (Throwable th3) {
                    if (0 != 0) {
                        try {
                            inputStream.close();
                        } catch (Exception unused4) {
                        }
                    }
                    throw th3;
                }
            } catch (IOException e8) {
                StringBuffer stringBuffer5 = new StringBuffer("Cannot load pki path from url ");
                stringBuffer5.append(uRLAndOptionalHash2.getURL());
                stringBuffer5.append(": ");
                stringBuffer5.append(e8.toString());
                throw new SSLCertificateException(stringBuffer5.toString(), 2, 111, false, null);
            }
        } catch (SSLCertificateException e9) {
            throw e9;
        }
    }

    private static final InputStream a(int i, URLAndOptionalHash uRLAndOptionalHash) throws IOException {
        URLConnection uRLConnectionOpenConnection = new URL(uRLAndOptionalHash.getURL()).openConnection();
        if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
            if (i == 0) {
                uRLConnectionOpenConnection.setRequestProperty("Accept", "application/pkix-cert,*/*");
            } else {
                uRLConnectionOpenConnection.setRequestProperty("Accept", "application/pkix-pkipath,*/*");
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            int responseCode = httpURLConnection.getResponseCode();
            String responseMessage = httpURLConnection.getResponseMessage();
            if (responseCode / 100 != 2) {
                StringBuffer stringBuffer = new StringBuffer("Server responded: ");
                stringBuffer.append(responseCode);
                stringBuffer.append(" ");
                stringBuffer.append(responseMessage);
                throw new IOException(stringBuffer.toString());
            }
        }
        return uRLConnectionOpenConnection.getInputStream();
    }

    private static final void a(byte[] bArr, int i, int i2, byte[] bArr2, String str) throws SSLCertificateException {
        try {
            MessageDigest messageDigest = SecurityProvider.getSecurityProvider().getMessageDigest("SHA");
            messageDigest.update(bArr, i, i2);
            if (Utils.equalsBlock(bArr2, messageDigest.digest())) {
                return;
            }
            StringBuffer stringBuffer = new StringBuffer("Invalid hash for certificate got from url ");
            stringBuffer.append(str);
            throw new SSLCertificateException(stringBuffer.toString(), 2, 114, false, null);
        } catch (Exception e) {
            StringBuffer stringBuffer2 = new StringBuffer("Cannot verify hash: ");
            stringBuffer2.append(e.toString());
            throw new SSLCertificateException(stringBuffer2.toString(), 2, 80, false, null);
        }
    }

    private static void a(X509Certificate[] x509CertificateArr, SSLTransport sSLTransport) {
        try {
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            StringBuffer stringBuffer = new StringBuffer(PathInterpolatorCompat.MAX_NUM_POINTS);
            String str = sSLTransport.getUseClientMode() ? "ServerCertChain" : "ClientCertChain";
            for (int i = 0; i < x509CertificateArr.length; i++) {
                StringBuffer stringBuffer2 = new StringBuffer(str);
                stringBuffer2.append("[");
                stringBuffer2.append(i);
                stringBuffer2.append("]:");
                String string = stringBuffer2.toString();
                if (i == 0) {
                    StringBuffer stringBuffer3 = new StringBuffer(String.valueOf(string));
                    stringBuffer3.append(Utils.a);
                    stringBuffer.append(stringBuffer3.toString());
                } else {
                    sSLTransport.a(string, stringBuffer);
                }
                a(x509CertificateArr[i], sSLTransport, stringBuffer, securityProvider);
            }
            sSLTransport.a(stringBuffer.toString());
        } catch (Throwable unused) {
        }
    }

    private static void a(X509Certificate x509Certificate, SSLTransport sSLTransport, StringBuffer stringBuffer, SecurityProvider securityProvider) {
        try {
            StringBuffer stringBuffer2 = new StringBuffer("  Version: ");
            stringBuffer2.append(x509Certificate.getVersion());
            sSLTransport.a(stringBuffer2.toString(), stringBuffer);
            StringBuffer stringBuffer3 = new StringBuffer("  Serial Number: 0x");
            stringBuffer3.append(x509Certificate.getSerialNumber().toString(16));
            sSLTransport.a(stringBuffer3.toString(), stringBuffer);
            StringBuffer stringBuffer4 = new StringBuffer("  Signature Algorithm: ");
            stringBuffer4.append(x509Certificate.getSigAlgName());
            sSLTransport.a(stringBuffer4.toString(), stringBuffer);
            securityProvider.a("  Issuer:  ", x509Certificate.getIssuerDN(), sSLTransport, stringBuffer);
            sSLTransport.a("  Validity: ", stringBuffer);
            StringBuffer stringBuffer5 = new StringBuffer("    Not Before: ");
            stringBuffer5.append(x509Certificate.getNotBefore());
            sSLTransport.a(stringBuffer5.toString(), stringBuffer);
            StringBuffer stringBuffer6 = new StringBuffer("    Not After:  ");
            stringBuffer6.append(x509Certificate.getNotAfter());
            sSLTransport.a(stringBuffer6.toString(), stringBuffer);
            securityProvider.a("  Subject: ", x509Certificate.getSubjectDN(), sSLTransport, stringBuffer);
            StringBuffer stringBuffer7 = new StringBuffer("  Public Key: ");
            stringBuffer7.append(x509Certificate.getPublicKey().getAlgorithm());
            stringBuffer7.append(", ");
            stringBuffer7.append(Utils.a(x509Certificate, false));
            sSLTransport.a(stringBuffer7.toString(), stringBuffer);
        } catch (Exception e) {
            StringBuffer stringBuffer8 = new StringBuffer("  Error dumping certificate: ");
            stringBuffer8.append(e.getMessage());
            sSLTransport.a(stringBuffer8.toString());
        }
    }

    private static void a(SSLTransport sSLTransport, boolean z) {
        if (z) {
            sSLTransport.a("ChainVerifier: Found a trusted certificate, returning true");
        }
    }
}

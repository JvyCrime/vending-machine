package iaik.security.ssl;

import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.security.jsse.net.KeyTypeNames;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;
import javax.crypto.Mac;
import javax.crypto.interfaces.DHPublicKey;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static final String PROPERTYNAME_HTTPS_NON_PROXY_HOSTS = "https.nonProxyHosts";
    public static final String PROPERTYNAME_HTTPS_PROXY_HOST = "https.proxyHost";
    public static final String PROPERTYNAME_HTTPS_PROXY_PASSWORD = "https.proxyPassword";
    public static final String PROPERTYNAME_HTTPS_PROXY_PORT = "https.proxyPort";
    public static final String PROPERTYNAME_HTTPS_PROXY_USER = "https.proxyUser";
    static String a;
    private static String f;
    private static String g;
    private static final char[] b = "0123456789ABCDEF".toCharArray();
    private static final String[] c = {"ASCII", "Cp1252", "ISO8859_1", "iso-8859-1", "ISO-8859-1", "iso_8859-1", "iso8859-1", "iso_8859_1", "iso8859_1", "Default"};
    private static final String[] d = {"ISO8859_1", "iso-8859-1", "ISO-8859-1", "iso_8859-1", "iso8859-1", "iso_8859_1", "iso8859_1"};
    private static final byte[] e = {97, marshall_t.status_vpos_processing_error, 122, 90, 48, 57, 43, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, marshall_t.status_vpos_insert_card};
    private static final Class[] h = new Class[0];
    private static boolean i = false;

    private static int a(int i2, byte b2) {
        int i3 = i2 == b2 ? i2 : -1;
        return (i3 == -1 && i2 == 258 && (b2 == 64 || b2 == 66)) ? b2 : i3;
    }

    static int a(int i2, int i3) {
        return i2 <= i3 ? i2 : i3;
    }

    private Utils() {
    }

    public static boolean equalsBlock(byte[] bArr, int i2, byte[] bArr2, int i3, int i4) {
        int i5 = i2;
        byte b2 = 0;
        while (i5 < i2 + i4) {
            int i6 = i5 + 1;
            try {
                int i7 = i3 + 1;
                b2 = (byte) ((bArr2[i3] ^ bArr[i5]) | b2);
                i5 = i6;
                i3 = i7;
            } catch (Exception unused) {
                return false;
            }
        }
        return b2 == 0;
    }

    public static boolean equalsBlock(byte[] bArr, byte[] bArr2) {
        if (bArr == bArr2) {
            return true;
        }
        if (bArr == null || bArr2 == null || bArr.length != bArr2.length) {
            return false;
        }
        return equalsBlock(bArr, 0, bArr2, 0, bArr.length);
    }

    static void c(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = 0;
        }
    }

    static byte[] a(String str, byte[] bArr, byte[] bArr2, int i2) {
        try {
            Mac mac = SecurityProvider.getSecurityProvider().getMac(str, new XSecretKeySpec(bArr, "MAC"));
            int macLength = mac.getMacLength();
            int i3 = ((i2 - 1) / macLength) + 1;
            byte[] bArr3 = new byte[macLength * i3];
            int i4 = 0;
            byte[] bArrDoFinal = bArr2;
            do {
                bArrDoFinal = mac.doFinal(bArrDoFinal);
                mac.update(bArrDoFinal);
                mac.update(bArr2);
                mac.doFinal(bArr3, i4);
                i4 += macLength;
                i3--;
            } while (i3 > 0);
            return bArr3;
        } catch (Exception e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    static byte[] a(int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, int i3, String str) {
        String string;
        byte[] bArrA = a(bArr2, bArr3);
        byte[] bArr4 = new byte[i3];
        if (i2 < 771) {
            int length = (bArr.length + 1) >> 1;
            byte[] bArr5 = new byte[length];
            System.arraycopy(bArr, 0, bArr5, 0, length);
            byte[] bArrA2 = a(SecurityProvider.ALG_HMAC_MD5, bArr5, bArrA, i3);
            System.arraycopy(bArr, bArr.length - length, bArr5, 0, length);
            byte[] bArrA3 = a("HmacSHA1", bArr5, bArrA, i3);
            for (int i4 = 0; i4 < i3; i4++) {
                bArr4[i4] = (byte) (bArrA2[i4] ^ bArrA3[i4]);
            }
            c(bArrA2);
            c(bArrA3);
        } else {
            if (str != null) {
                StringBuffer stringBuffer = new StringBuffer("Hmac");
                stringBuffer.append(str);
                string = stringBuffer.toString();
            } else {
                string = "HmacSHA256";
            }
            byte[] bArrA4 = a(string, bArr, bArrA, i3);
            System.arraycopy(bArrA4, 0, bArr4, 0, i3);
            c(bArrA4);
        }
        return bArr4;
    }

    static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    static byte[] a(int i2) {
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) (i2 + 64);
        }
        return bArr;
    }

    static {
        try {
            a = System.getProperty("line.separator");
        } catch (Throwable unused) {
        }
        if (a == null) {
            a = "\r\n";
        }
        a();
    }

    static String a(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            return "(null)";
        }
        StringBuffer stringBuffer = new StringBuffer(i3 * 3);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = bArr[i2 + i4] & 255;
            char[] cArr = b;
            stringBuffer.append(cArr[i5 >> 4]);
            stringBuffer.append(cArr[i5 & 15]);
            if (i4 != i3 - 1) {
                stringBuffer.append(':');
            }
        }
        return stringBuffer.toString();
    }

    public static String toString(byte[] bArr) {
        return bArr == null ? "(null)" : a(bArr, 0, bArr.length);
    }

    public static String toString(int i2) {
        if (i2 == 0) {
            return "00";
        }
        int i3 = 4;
        byte[] bArr = new byte[4];
        while (i2 != 0) {
            i3--;
            bArr[i3] = (byte) i2;
            i2 >>>= 8;
        }
        return a(bArr, i3, 4 - i3);
    }

    static int a(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        int i4 = i3;
        int i5 = i4;
        while (i4 > 0) {
            int i6 = inputStream.read(bArr, i2, i4);
            if (i6 == -1) {
                throw new EOFException("Connection closed by remote host.");
            }
            i4 -= i6;
            i2 += i6;
            int i7 = i5 - 1;
            if (i5 == 0) {
                throw new IOException("Could not read data!");
            }
            i5 = i7;
        }
        return i3;
    }

    static int a(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        Objects.requireNonNull(inputStream, "Argument \"source\" must not be null.");
        if (bArr == null) {
            bArr = new byte[4096];
        }
        int i2 = 0;
        if (outputStream != null) {
            int i3 = 0;
            while (true) {
                int i4 = inputStream.read(bArr);
                if (i4 < 0) {
                    return i3;
                }
                outputStream.write(bArr, 0, i4);
                i3 += i4;
            }
        } else {
            while (true) {
                int i5 = inputStream.read(bArr);
                if (i5 < 0) {
                    return i2;
                }
                i2 += i5;
            }
        }
    }

    static String c(int i2) {
        if (i2 == 2) {
            return "2.0";
        }
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(i2 >> 8));
        stringBuffer.append(".");
        stringBuffer.append(i2 & 255);
        return stringBuffer.toString();
    }

    static String b(int i2) {
        if (i2 == 2) {
            return "SSL20";
        }
        switch (i2) {
            case SSLContext.VERSION_SSL30 /* 768 */:
                return "SSL30";
            case SSLContext.VERSION_TLS10 /* 769 */:
                return "TLS10";
            case SSLContext.VERSION_TLS11 /* 770 */:
                return "TLS11";
            case SSLContext.VERSION_TLS12 /* 771 */:
                return "TLS12";
            default:
                StringBuffer stringBuffer = new StringBuffer("Protocol version ");
                stringBuffer.append(i2);
                stringBuffer.append(" not supported!");
                throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    static int a(String str) {
        Objects.requireNonNull(str, "Version string must not be null!");
        String upperCase = str.toUpperCase();
        if (upperCase.equals("SSL20")) {
            return 2;
        }
        if (upperCase.equals("SSL30")) {
            return SSLContext.VERSION_SSL30;
        }
        if (upperCase.equals("TLS10")) {
            return SSLContext.VERSION_TLS10;
        }
        if (upperCase.equals("TLS11")) {
            return SSLContext.VERSION_TLS11;
        }
        if (upperCase.equals("TLS12")) {
            return SSLContext.VERSION_TLS12;
        }
        StringBuffer stringBuffer = new StringBuffer("Protocol version ");
        stringBuffer.append(str);
        stringBuffer.append(" not supported!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    static int[] a(String str, String str2) throws IllegalArgumentException {
        try {
            int iA = a(str);
            try {
                int iA2 = a(str2);
                if (iA <= iA2) {
                    return new int[]{iA, iA2};
                }
                throw new IllegalArgumentException("The minimum version must be less than or equal to the maximum version!");
            } catch (IllegalArgumentException unused) {
                StringBuffer stringBuffer = new StringBuffer("Unsupported maximum protocol version: ");
                stringBuffer.append(str2);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        } catch (IllegalArgumentException unused2) {
            StringBuffer stringBuffer2 = new StringBuffer("Unsupported minumum protocol version: ");
            stringBuffer2.append(str);
            throw new IllegalArgumentException(stringBuffer2.toString());
        }
    }

    private static InetAddress d() throws IOException {
        String property = System.getProperty(PROPERTYNAME_HTTPS_PROXY_HOST, "");
        if (property.length() == 0) {
            return null;
        }
        return InetAddress.getByName(property);
    }

    private static int f() throws IOException {
        try {
            return Integer.parseInt(System.getProperty(PROPERTYNAME_HTTPS_PROXY_PORT, ""));
        } catch (NumberFormatException e2) {
            StringBuffer stringBuffer = new StringBuffer("Invalid proxy port: ");
            stringBuffer.append(e2.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    private static String g() {
        String property = System.getProperty(PROPERTYNAME_HTTPS_PROXY_USER, "");
        if (property.length() == 0) {
            return null;
        }
        return property;
    }

    private static String e() {
        String property = System.getProperty(PROPERTYNAME_HTTPS_PROXY_PASSWORD, "");
        if (property.length() == 0) {
            return null;
        }
        return property;
    }

    private static String c() {
        String property = System.getProperty(PROPERTYNAME_HTTPS_NON_PROXY_HOSTS, "");
        if (property.length() == 0) {
            return null;
        }
        return property;
    }

    public static SSLSocket proxyConnect(InetAddress inetAddress, int i2, SSLContext sSLContext) throws IOException {
        InetAddress inetAddressD = d();
        if (inetAddressD == null || c(inetAddress.getHostName(), c())) {
            return new SSLSocket(inetAddress, i2, sSLContext);
        }
        return proxyConnect(inetAddress, i2, sSLContext, inetAddressD, f());
    }

    public static SSLSocket proxyConnect(String str, int i2, SSLContext sSLContext) throws IOException {
        InetAddress inetAddressD = d();
        if (inetAddressD == null || c(str, c())) {
            return new SSLSocket(str, i2, sSLContext);
        }
        return proxyConnect(str, i2, sSLContext, inetAddressD, f());
    }

    public static SSLSocket proxyConnect(InetAddress inetAddress, int i2, SSLContext sSLContext, InetAddress inetAddress2, int i3) throws IOException {
        if (inetAddress2 == null) {
            return new SSLSocket(inetAddress, i2, sSLContext);
        }
        return proxyConnect(inetAddress.getHostName(), i2, sSLContext, inetAddress2, i3);
    }

    public static SSLSocket proxyConnect(String str, int i2, SSLContext sSLContext, InetAddress inetAddress, int i3) throws IOException {
        if (inetAddress == null) {
            return new SSLSocket(str, i2, sSLContext);
        }
        return proxyConnect(str, i2, sSLContext, new Socket(inetAddress, i3));
    }

    public static SSLSocket proxyConnect(String str, int i2, SSLContext sSLContext, Socket socket) throws IOException {
        return proxyConnect(str, i2, sSLContext, socket, null, null, 30000);
    }

    public static SSLSocket proxyConnect(String str, int i2, SSLContext sSLContext, Socket socket, String str2, String str3, int i3) throws IOException {
        String string;
        String strTrim;
        if (socket == null) {
            return new SSLSocket(str, i2, sSLContext);
        }
        socket.setSoTimeout(i3);
        PrintWriter aSCIIWriter = getASCIIWriter(socket.getOutputStream());
        StringBuffer stringBuffer = new StringBuffer("CONNECT ");
        stringBuffer.append(str);
        stringBuffer.append(":");
        stringBuffer.append(i2);
        stringBuffer.append(" HTTP/1.0");
        aSCIIWriter.println(stringBuffer.toString());
        if (str2 != null) {
            String strC = c(str2);
            string = (str3 == null || str3.toUpperCase().startsWith(strC.toUpperCase())) ? str3 : null;
            if ((string == null || string.length() == 0) && str2.toUpperCase().startsWith("BASIC")) {
                String strG = g();
                String strE = e();
                if (strG != null && strE != null) {
                    StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(strG));
                    stringBuffer2.append(":");
                    stringBuffer2.append(strE);
                    String string2 = stringBuffer2.toString();
                    StringBuffer stringBuffer3 = new StringBuffer(String.valueOf(strC));
                    stringBuffer3.append(" ");
                    stringBuffer3.append(b(string2));
                    string = stringBuffer3.toString();
                }
            }
            if (string != null) {
                StringBuffer stringBuffer4 = new StringBuffer("Proxy-Authorization: ");
                stringBuffer4.append(string);
                aSCIIWriter.println(stringBuffer4.toString());
            }
        } else {
            string = str3;
        }
        aSCIIWriter.println();
        aSCIIWriter.flush();
        BufferedReader aSCIIReader = getASCIIReader(socket.getInputStream());
        String line = aSCIIReader.readLine();
        loop0: while (true) {
            strTrim = null;
            while (true) {
                String line2 = aSCIIReader.readLine();
                if (line2 == null || line2.length() == 0) {
                    break loop0;
                }
                if (strTrim == null) {
                    line2.indexOf("Proxy-Authenticate:");
                    if (line2.startsWith("Proxy-Authenticate: ")) {
                        strTrim = line2.substring(19).trim();
                        if (!b(strTrim, string)) {
                            break;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        if (line == null || !line.startsWith("HTTP/1.")) {
            StringBuffer stringBuffer5 = new StringBuffer("Invalid response from proxy: ");
            stringBuffer5.append(line);
            throw new IOException(stringBuffer5.toString());
        }
        if (line.substring(8).trim().startsWith("407") && strTrim != null) {
            if (str2 != null) {
                throw new IOException("Proxy authentication failed. Wrong user or password.");
            }
            return proxyConnect(str, i2, sSLContext, new Socket(socket.getInetAddress(), socket.getPort()), strTrim, string, i3);
        }
        if (!line.substring(9).trim().startsWith("2")) {
            StringBuffer stringBuffer6 = new StringBuffer("Proxy responded: ");
            stringBuffer6.append(line);
            throw new IOException(stringBuffer6.toString());
        }
        SSLSocket sSLSocket = new SSLSocket(socket, sSLContext, str, i2);
        sSLSocket.setAutoHandshake(true);
        sSLSocket.setSoTimeout(0);
        return sSLSocket;
    }

    private static boolean b(String str, String str2) {
        String upperCase = c(str).toUpperCase();
        if (str2 == null || str2.length() == 0 || !str2.toUpperCase().startsWith(upperCase)) {
            return (!upperCase.equals("BASIC") || g() == null || e() == null) ? false : true;
        }
        return true;
    }

    private static String c(String str) {
        return new StringTokenizer(str).nextToken();
    }

    private static boolean c(String str, String str2) {
        if (str != null && str2 != null) {
            String lowerCase = str.toLowerCase();
            StringTokenizer stringTokenizer = new StringTokenizer(str2.toLowerCase(), " |", false);
            while (stringTokenizer.hasMoreTokens()) {
                String strNextToken = stringTokenizer.nextToken();
                if (strNextToken.startsWith(Marker.ANY_MARKER)) {
                    if (lowerCase.endsWith(strNextToken.substring(1))) {
                        return true;
                    }
                } else if (lowerCase.equals(strNextToken)) {
                    return true;
                }
            }
        }
        return false;
    }

    static int a(PublicKey publicKey) {
        return SecurityProvider.getSecurityProvider().getKeyLength(publicKey);
    }

    static int a(PrivateKey privateKey) {
        return SecurityProvider.getSecurityProvider().getKeyLength(privateKey);
    }

    static String a(X509Certificate x509Certificate, boolean z) {
        return a(x509Certificate.getPublicKey(), z);
    }

    static String a(PublicKey publicKey, boolean z) {
        String algorithm = publicKey.getAlgorithm();
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        String string = null;
        try {
            if (algorithm.startsWith(KeyTypeNames.EC)) {
                algorithm = "ECC";
                string = securityProvider.getCurveName(publicKey);
            }
        } catch (Throwable unused) {
        }
        if (string == null) {
            StringBuffer stringBuffer = new StringBuffer(String.valueOf(SecurityProvider.getSecurityProvider().getKeyLength(publicKey)));
            stringBuffer.append(" bit");
            string = stringBuffer.toString();
        }
        return z ? string.concat(" ").concat(algorithm) : string;
    }

    static boolean a(CipherSuite cipherSuite, PublicKey publicKey) {
        int keySizeLimit = cipherSuite.getKeySizeLimit();
        return keySizeLimit != -1 && a(publicKey) > keySizeLimit;
    }

    static int a(X509Certificate x509Certificate) {
        String lowerCase = x509Certificate.getSigAlgName().toLowerCase(Locale.US);
        if (lowerCase.indexOf("rsa") != -1) {
            return 1;
        }
        if (lowerCase.indexOf("ecdsa") != -1) {
            return 3;
        }
        return (lowerCase.indexOf("dsa") == -1 && lowerCase.indexOf("dss") == -1) ? 0 : 2;
    }

    static int a(byte[] bArr) {
        int i2 = 0;
        for (byte b2 : bArr) {
            int i3 = i2 + (b2 & 255);
            i2 = (i3 >> 29) | (i3 << 3);
        }
        return i2;
    }

    public static int getCertificateType(X509Certificate x509Certificate) {
        boolean z;
        boolean z2;
        boolean z3;
        if (!ChainVerifier.a()) {
            return getCertificateTypeWithoutKeyUsage(x509Certificate);
        }
        boolean[] keyUsage = x509Certificate.getKeyUsage();
        if (keyUsage != null) {
            z = false;
            z2 = keyUsage[0] || keyUsage[1];
            z3 = keyUsage[4];
            if (keyUsage[2] || keyUsage[3]) {
                z = true;
            }
        } else {
            z = true;
            z2 = true;
            z3 = true;
        }
        PublicKey publicKey = x509Certificate.getPublicKey();
        if (publicKey instanceof RSAPublicKey) {
            if (z2) {
                return 1;
            }
            if (z) {
                return 257;
            }
        } else if (publicKey instanceof DSAPublicKey) {
            if (z2) {
                return 2;
            }
        } else if (publicKey instanceof DHPublicKey) {
            int iA = a(x509Certificate);
            if (iA == 1) {
                if (z3) {
                    return 3;
                }
            } else if (iA == 2 && z3) {
                return 4;
            }
        } else if (publicKey.getAlgorithm().toUpperCase(Locale.US).startsWith(KeyTypeNames.EC)) {
            int iA2 = a(x509Certificate);
            if (iA2 == 1) {
                if (z3) {
                    return 65;
                }
            } else if (iA2 == 3) {
                if (keyUsage != null) {
                    if (z2) {
                        if (!z3) {
                            return 64;
                        }
                    } else if (z3) {
                        return 66;
                    }
                }
                return SSLContext.CERTTYPE_ECDSA_EC;
            }
        }
        return 256;
    }

    public static int getCertificateTypeWithoutKeyUsage(X509Certificate x509Certificate) {
        PublicKey publicKey = x509Certificate.getPublicKey();
        boolean z = true;
        if (publicKey instanceof RSAPublicKey) {
            return 1;
        }
        if (publicKey instanceof DSAPublicKey) {
            return 2;
        }
        if (publicKey instanceof DHPublicKey) {
            int iA = a(x509Certificate);
            if (iA == 1) {
                return 3;
            }
            if (iA == 2) {
                return 4;
            }
        } else if (publicKey.getAlgorithm().toUpperCase(Locale.US).startsWith(KeyTypeNames.EC)) {
            int iA2 = a(x509Certificate);
            if (iA2 == 1) {
                return 65;
            }
            if (iA2 == 3) {
                boolean[] keyUsage = x509Certificate.getKeyUsage();
                if (keyUsage == null) {
                    return SSLContext.CERTTYPE_ECDSA_EC;
                }
                boolean z2 = keyUsage[0] || keyUsage[1];
                if (!keyUsage[2] && !keyUsage[3] && !keyUsage[4]) {
                    z = false;
                }
                if (z2) {
                    if (z) {
                        return SSLContext.CERTTYPE_ECDSA_EC;
                    }
                    return 64;
                }
                if (z) {
                    return 66;
                }
            }
        }
        return 256;
    }

    static void a(X509Certificate x509Certificate, String str) throws SSLCertificateException {
        boolean z;
        boolean z2;
        boolean z3;
        if (str != null) {
            boolean[] keyUsage = x509Certificate.getKeyUsage();
            if (keyUsage != null) {
                z2 = keyUsage[0] || keyUsage[1];
                z3 = keyUsage[4];
                z = keyUsage[2] || keyUsage[3];
            } else {
                z = true;
                z2 = true;
                z3 = true;
            }
            PublicKey publicKey = x509Certificate.getPublicKey();
            if (publicKey instanceof RSAPublicKey) {
                if (str.startsWith("RSA")) {
                    if (!z) {
                        StringBuffer stringBuffer = new StringBuffer("RSA server certificate for ");
                        stringBuffer.append(str);
                        stringBuffer.append(" key exchange algorithm not capable for encryption!");
                        throw new SSLCertificateException(stringBuffer.toString());
                    }
                } else {
                    if (!str.startsWith("DHE_RSA") && !str.startsWith("ECDHE_RSA")) {
                        StringBuffer stringBuffer2 = new StringBuffer("RSA server certificate not appropriate for ");
                        stringBuffer2.append(str);
                        stringBuffer2.append(" key exchange algorithm!");
                        throw new SSLCertificateException(stringBuffer2.toString());
                    }
                    if (!z2) {
                        StringBuffer stringBuffer3 = new StringBuffer("RSA server certificate for ");
                        stringBuffer3.append(str);
                        stringBuffer3.append(" key exchange algorithm not capable for signing!");
                        throw new SSLCertificateException(stringBuffer3.toString());
                    }
                }
            } else if (publicKey instanceof DSAPublicKey) {
                if (!str.startsWith("DHE_DSS")) {
                    StringBuffer stringBuffer4 = new StringBuffer("DSA server certificate not appropriate for ");
                    stringBuffer4.append(str);
                    stringBuffer4.append(" key exchange algorithm!");
                    throw new SSLCertificateException(stringBuffer4.toString());
                }
                if (!z2) {
                    StringBuffer stringBuffer5 = new StringBuffer("DSA server certificate for ");
                    stringBuffer5.append(str);
                    stringBuffer5.append(" key exchange algorithm not capable for encryption!");
                    throw new SSLCertificateException(stringBuffer5.toString());
                }
            } else if (publicKey instanceof DHPublicKey) {
                if (!str.startsWith(KeyTypeNames.DH_DSS) && !str.startsWith(KeyTypeNames.DH_RSA)) {
                    StringBuffer stringBuffer6 = new StringBuffer("DH server certificate not appropriate for ");
                    stringBuffer6.append(str);
                    stringBuffer6.append(" key exchange algorithm!");
                    throw new SSLCertificateException(stringBuffer6.toString());
                }
                if (!z3) {
                    StringBuffer stringBuffer7 = new StringBuffer("DH server certificate for ");
                    stringBuffer7.append(str);
                    stringBuffer7.append(" key exchange algorithm not capable for key agreement!");
                    throw new SSLCertificateException(stringBuffer7.toString());
                }
                int iA = a(x509Certificate);
                if (iA == 1) {
                    if (str.startsWith(KeyTypeNames.DH_DSS)) {
                        StringBuffer stringBuffer8 = new StringBuffer("DH/RSA server certificate not capable for ");
                        stringBuffer8.append(str);
                        stringBuffer8.append(" key exchange algorithm!");
                        throw new SSLCertificateException(stringBuffer8.toString());
                    }
                } else if (iA == 2) {
                    if (str.startsWith(KeyTypeNames.DH_RSA)) {
                        StringBuffer stringBuffer9 = new StringBuffer("DH/DSS server certificate not capable for ");
                        stringBuffer9.append(str);
                        stringBuffer9.append(" key exchange algorithm!");
                        throw new SSLCertificateException(stringBuffer9.toString());
                    }
                } else {
                    StringBuffer stringBuffer10 = new StringBuffer("DH server certificate not appropriate for ");
                    stringBuffer10.append(str);
                    stringBuffer10.append(" key exchange algorithm! Wrong signature algorithm!");
                    throw new SSLCertificateException(stringBuffer10.toString());
                }
            } else if (publicKey.getAlgorithm().toUpperCase(Locale.US).startsWith(KeyTypeNames.EC)) {
                if (str.startsWith("ECDH_ECDSA") || str.startsWith("ECDH_RSA")) {
                    if (!z3) {
                        StringBuffer stringBuffer11 = new StringBuffer("EC server certificate for ");
                        stringBuffer11.append(str);
                        stringBuffer11.append(" key exchange algorithm not capable for key agreement!");
                        throw new SSLCertificateException(stringBuffer11.toString());
                    }
                    int iA2 = a(x509Certificate);
                    if (iA2 == 1) {
                        if (str.startsWith("ECDH_ECDSA")) {
                            StringBuffer stringBuffer12 = new StringBuffer("ECDH/RSA server certificate server certificate not capable for ");
                            stringBuffer12.append(str);
                            stringBuffer12.append(" key exchange algorithm!");
                            throw new SSLCertificateException(stringBuffer12.toString());
                        }
                    } else if (iA2 == 3) {
                        if (str.startsWith("ECDH_RSA")) {
                            StringBuffer stringBuffer13 = new StringBuffer("ECDH/DSS server certificate server certificate not capable for ");
                            stringBuffer13.append(str);
                            stringBuffer13.append(" key exchange algorithm!");
                            throw new SSLCertificateException(stringBuffer13.toString());
                        }
                    } else {
                        StringBuffer stringBuffer14 = new StringBuffer("ECDH server certificate not appropriate for ");
                        stringBuffer14.append(str);
                        stringBuffer14.append(" key exchange algorithm! Wrong signature algorithm!");
                        throw new SSLCertificateException(stringBuffer14.toString());
                    }
                } else {
                    if (!str.startsWith("ECDHE_ECDSA")) {
                        StringBuffer stringBuffer15 = new StringBuffer("EC server certificate not appropriate for ");
                        stringBuffer15.append(str);
                        stringBuffer15.append(" key exchange algorithm!");
                        throw new SSLCertificateException(stringBuffer15.toString());
                    }
                    if (!z2) {
                        StringBuffer stringBuffer16 = new StringBuffer("EC server certificate for ");
                        stringBuffer16.append(str);
                        stringBuffer16.append(" key exchange algorithm not capable for signing!");
                        throw new SSLCertificateException(stringBuffer16.toString());
                    }
                    if (a(x509Certificate) != 3) {
                        StringBuffer stringBuffer17 = new StringBuffer("EC signed ");
                        stringBuffer17.append(x509Certificate.getSigAlgName());
                        stringBuffer17.append(" server certificate server certificate not capable for ");
                        stringBuffer17.append(str);
                        stringBuffer17.append(" key exchange algorithm!");
                        throw new SSLCertificateException(stringBuffer17.toString());
                    }
                }
            }
        }
        SSLCertificateException sSLCertificateException = null;
        try {
            if (!SecurityProvider.getSecurityProvider().checkExtendedKeyUsage(x509Certificate, false)) {
                sSLCertificateException = new SSLCertificateException("Certificate not appropriate for server authentication! Inappropriate ExtendedKeyUsage extension!");
                sSLCertificateException.a(2, 42, false);
            }
        } catch (CertificateException e2) {
            sSLCertificateException = new SSLCertificateException(e2.toString());
            sSLCertificateException.a(2, 42, false);
        }
        if (sSLCertificateException != null) {
            throw sSLCertificateException;
        }
    }

    static X509Certificate[] a(Certificate[] certificateArr) {
        Objects.requireNonNull(certificateArr, "certificates must not be null!");
        X509Certificate[] x509CertificateArr = new X509Certificate[certificateArr.length];
        for (int i2 = 0; i2 < certificateArr.length; i2++) {
            Certificate certificate = certificateArr[i2];
            if (!(certificate instanceof X509Certificate)) {
                StringBuffer stringBuffer = new StringBuffer("Certificate ");
                stringBuffer.append(i2);
                stringBuffer.append(" is not a X509Certificate!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            x509CertificateArr[i2] = (X509Certificate) certificate;
        }
        return x509CertificateArr;
    }

    public static String certTypeToString(int i2) {
        if (i2 == 20) {
            return "SSLv3 Fortezza";
        }
        if (i2 == 257) {
            return "RSA";
        }
        if (i2 == 258) {
            return "ECDSA signed EC";
        }
        switch (i2) {
            case 1:
                return "RSA";
            case 2:
                return "DSA";
            case 3:
                return "RSA signed Diffie-Hellman";
            case 4:
                return "DSA signed Diffie-Hellman";
            case 5:
                return "SSLv3 ephemeral RSA";
            case 6:
                return "SSLv3 ephemeral DSA";
            default:
                switch (i2) {
                    case 64:
                        return "ECDSA";
                    case 65:
                        return "RSA signed ECDH";
                    case 66:
                        return "ECDSA signed ECDH";
                    default:
                        StringBuffer stringBuffer = new StringBuffer("Unknown (");
                        stringBuffer.append(i2);
                        stringBuffer.append(")");
                        return stringBuffer.toString();
                }
        }
    }

    static int a(KeyAndCert keyAndCert, byte[] bArr) {
        return a(keyAndCert.getCertificateType(), bArr);
    }

    static int a(X509Certificate x509Certificate, byte[] bArr) {
        return a(getCertificateType(x509Certificate), bArr);
    }

    static int a(int i2, byte[] bArr) {
        if (bArr == null) {
            return -1;
        }
        int iA = -1;
        for (int i3 = 0; i3 < bArr.length && (iA = a(i2, bArr[i3])) == -1; i3++) {
        }
        return iA;
    }

    static KeyAndCert a(KeyAndCert keyAndCert, int i2) {
        if (keyAndCert.getCertificateType() == i2) {
            return keyAndCert;
        }
        KeyAndCert keyAndCert2 = (KeyAndCert) keyAndCert.clone();
        keyAndCert2.b(i2);
        return keyAndCert2;
    }

    static boolean a(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return true;
        }
        for (Object obj : objArr) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    static byte[] a(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray[0] != 0) {
            return byteArray;
        }
        int length = byteArray.length - 1;
        byte[] bArr = new byte[length];
        System.arraycopy(byteArray, 1, bArr, 0, length);
        return bArr;
    }

    private static synchronized String b() {
        if (f == null) {
            int i2 = 0;
            while (true) {
                String[] strArr = c;
                if (i2 >= strArr.length) {
                    break;
                }
                try {
                    String str = strArr[i2];
                    if (equalsBlock("aAzZ09+/=".getBytes(str), e)) {
                        f = str;
                        break;
                    }
                    continue;
                } catch (UnsupportedEncodingException unused) {
                }
                i2++;
            }
            if (f == null) {
                throw new RuntimeException("No ASCII compatible encoding found!");
            }
        }
        return f;
    }

    public static BufferedReader getASCIIReader(InputStream inputStream) {
        try {
            return new BufferedReader(new InputStreamReader(inputStream, b()));
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public static PrintWriter getASCIIWriter(OutputStream outputStream) {
        return getASCIIWriter(outputStream, false);
    }

    public static PrintWriter getASCIIWriter(OutputStream outputStream, boolean z) {
        try {
            return new ExtendedPrintWriter(new OutputStreamWriter(outputStream, b()), z, ExtendedPrintWriter.CRLF);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public static String getVersionString(int i2) {
        if (i2 == 0) {
            return "(no secure connection established)";
        }
        if (i2 == 2) {
            return "SSL 2.0";
        }
        switch (i2) {
            case SSLContext.VERSION_SSL30 /* 768 */:
                return "SSL 3.0";
            case SSLContext.VERSION_TLS10 /* 769 */:
                return "TLS 1.0";
            case SSLContext.VERSION_TLS11 /* 770 */:
                return "TLS 1.1";
            case SSLContext.VERSION_TLS12 /* 771 */:
                return "TLS 1.2";
            default:
                StringBuffer stringBuffer = new StringBuffer("Unknown protocol version ");
                stringBuffer.append(i2 >> 8);
                stringBuffer.append(".");
                stringBuffer.append(i2 & 255);
                return stringBuffer.toString();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0016 A[PHI: r6
  0x0016: PHI (r6v23 boolean) = (r6v18 boolean), (r6v20 boolean), (r6v25 boolean) binds: [B:24:0x0044, B:17:0x002e, B:7:0x0013] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static boolean a(int r6, iaik.security.ssl.SignatureAndHashAlgorithmList r7) {
        /*
            java.lang.String r0 = "RSA"
            r1 = 0
            r2 = 1
            r3 = 0
            if (r6 != r2) goto L1d
            int r6 = iaik.security.ssl.SecurityProvider.b
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r0, r6, r3)
            if (r7 == 0) goto L1a
            iaik.security.ssl.SignatureAndHashAlgorithm r7 = r7.a(r2)
            if (r7 != 0) goto L16
            goto L17
        L16:
            r1 = 1
        L17:
            r1 = r1 & r6
            goto La1
        L1a:
            r1 = r6
            goto La1
        L1d:
            r4 = 2
            if (r6 != r4) goto L31
            int r6 = iaik.security.ssl.SecurityProvider.b
            java.lang.String r0 = "DSAClient"
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r0, r6, r3)
            if (r7 == 0) goto L1a
            iaik.security.ssl.SignatureAndHashAlgorithm r7 = r7.a(r4)
            if (r7 != 0) goto L16
            goto L17
        L31:
            r4 = 64
            r5 = 3
            if (r6 != r4) goto L47
            int r6 = iaik.security.ssl.SecurityProvider.b
            java.lang.String r0 = "ECDSAClient"
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r0, r6, r3)
            if (r7 == 0) goto L1a
            iaik.security.ssl.SignatureAndHashAlgorithm r7 = r7.a(r5)
            if (r7 != 0) goto L16
            goto L17
        L47:
            java.lang.String r7 = "DH"
            if (r6 != r5) goto L5e
            int r6 = iaik.security.ssl.SecurityProvider.b
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r7, r6, r3)
            if (r6 == 0) goto La1
            int r6 = iaik.security.ssl.SecurityProvider.b
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r0, r6, r3)
            if (r6 != 0) goto L5c
            goto La1
        L5c:
            r1 = 1
            goto La1
        L5e:
            r4 = 4
            if (r6 != r4) goto L74
            int r6 = iaik.security.ssl.SecurityProvider.b
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r7, r6, r3)
            if (r6 == 0) goto La1
            int r6 = iaik.security.ssl.SecurityProvider.b
            java.lang.String r7 = "DSA"
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r7, r6, r3)
            if (r6 != 0) goto L5c
            goto La1
        L74:
            r7 = 65
            java.lang.String r4 = "ECDH"
            if (r6 != r7) goto L8b
            int r6 = iaik.security.ssl.SecurityProvider.b
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r4, r6, r3)
            if (r6 == 0) goto La1
            int r6 = iaik.security.ssl.SecurityProvider.b
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r0, r6, r3)
            if (r6 != 0) goto L5c
            goto La1
        L8b:
            r7 = 66
            if (r6 != r7) goto La1
            int r6 = iaik.security.ssl.SecurityProvider.b
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r4, r6, r3)
            if (r6 == 0) goto La1
            int r6 = iaik.security.ssl.SecurityProvider.b
            java.lang.String r7 = "ECDSA"
            boolean r6 = iaik.security.ssl.SecurityProvider.a(r7, r6, r3)
            if (r6 != 0) goto L5c
        La1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.Utils.a(int, iaik.security.ssl.SignatureAndHashAlgorithmList):boolean");
    }

    static int[] b(byte[] bArr) {
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = bArr[i2] & 255;
        }
        return iArr;
    }

    static byte[] a(int[] iArr, String str, SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) throws IOException {
        int[] iArr2 = new int[iArr.length];
        int i2 = 0;
        for (int i3 : iArr) {
            if (a(i3, signatureAndHashAlgorithmList)) {
                boolean z = true;
                if (!str.startsWith("RSA") ? !(i3 == 65 || i3 == 66 ? str.startsWith("ECDH_") : (i3 != 3 && i3 != 4) || str.startsWith("DH_")) : !(i3 == 1 || i3 == 2 || i3 == 64)) {
                    z = false;
                }
                if (z) {
                    iArr2[i2] = i3;
                    i2++;
                }
            }
        }
        byte[] bArr = new byte[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            bArr[i4] = (byte) iArr2[i4];
        }
        return bArr;
    }

    static void a(byte[] bArr, String str, SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (bArr[i2] == 66 || bArr[i2] == 65) {
                if (!str.startsWith("ECDH_")) {
                    bArr[i2] = 0;
                }
            } else if ((bArr[i2] == 4 || bArr[i2] == 3) && !str.startsWith("DH_")) {
                bArr[i2] = 0;
            }
            if (signatureAndHashAlgorithmList != null) {
                if (bArr[i2] == 1) {
                    if (signatureAndHashAlgorithmList.a(1) == null) {
                        bArr[i2] = 0;
                    }
                } else if (bArr[i2] == 2) {
                    if (signatureAndHashAlgorithmList.a(2) == null) {
                        bArr[i2] = 0;
                    }
                } else if (bArr[i2] == 64 && signatureAndHashAlgorithmList.a(3) == null) {
                    bArr[i2] = 0;
                }
            }
        }
    }

    private static KeyPair a(KeyPairGenerator keyPairGenerator, String str) {
        try {
            Class<?> cls = keyPairGenerator.getClass();
            Class<?>[] clsArr = h;
            return (KeyPair) cls.getMethod(str, clsArr).invoke(keyPairGenerator, clsArr);
        } catch (Throwable th) {
            StringBuffer stringBuffer = new StringBuffer("Could not generate keypair: ");
            stringBuffer.append(th.toString());
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    public static KeyPair generateKeyPair(KeyPairGenerator keyPairGenerator) {
        try {
            return a(keyPairGenerator, "generateKeyPair");
        } catch (RuntimeException unused) {
            return a(keyPairGenerator, "genKeyPair");
        }
    }

    public static byte[] base64Encode(byte[] bArr) throws IOException {
        try {
            return e.a(bArr);
        } catch (Exception e2) {
            throw new IOException(e2.toString());
        }
    }

    private static String b(String str) throws IOException {
        byte[] bytes;
        String str2 = g;
        if (str2 == null) {
            int i2 = 0;
            bytes = null;
            while (true) {
                String[] strArr = d;
                if (i2 >= strArr.length) {
                    break;
                }
                try {
                    bytes = str.getBytes(strArr[i2]);
                    g = strArr[i2];
                    break;
                } catch (UnsupportedEncodingException unused) {
                    i2++;
                }
            }
        } else {
            bytes = str.getBytes(str2);
        }
        if (g == null) {
            throw new IOException("Cannot encode user:password string. ISO-8859-1 encoding not supported!");
        }
        return new String(base64Encode(bytes), g);
    }

    static synchronized void a() {
        if (i) {
            return;
        }
        i = true;
        String[] strArr = {"******************************************************************************", "***                                                                       ***", "***         Welcome to the IAIK SSL/TLS (iSaSiLk) Library                 ***", "***                                                                       ***", "*** This version of iSaSiLk is licensed for educational and research use  ***", "*** and evaluation only. Commercial use of this software is prohibited.   ***", "*** For details please see http://jce.iaik.tugraz.at/sic/sales/licences/. ***", "*** This message does not appear in the registered commercial version.    ***", "***                                                                       ***", "******************************************************************************", ""};
        for (int i2 = 0; i2 < 11; i2++) {
            System.err.println(strArr[i2]);
        }
    }
}

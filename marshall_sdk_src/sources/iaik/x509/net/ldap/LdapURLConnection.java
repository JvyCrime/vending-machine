package iaik.x509.net.ldap;

import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import iaik.utils.Util;
import iaik.x509.X509CRL;
import iaik.x509.X509CertPath;
import iaik.x509.X509Certificate;
import iaik.x509.attr.AttributeCertificate;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

/* JADX INFO: loaded from: classes2.dex */
public class LdapURLConnection extends URLConnection {
    public static final String AD_ATTRIBUTE_CERTIFICATE = "attributeCertificate;binary";
    public static final String AD_AUTHORITY_REVOCATION_LIST = "authorityRevocationList;binary";
    public static final String AD_CA_CERTIFICATE = "caCertificate;binary";
    public static final String AD_CERTIFICATE = "caCertificate;binary,userCertificate;binary";
    public static final String AD_CERTIFICATE_REVOCATION_LIST = "certificateRevocationList;binary";
    public static final String AD_DELTA_REVOCATION_LIST = "deltaRevocationList;binary";
    public static final String AD_REVOCATION_LIST = "authorityRevocationList;binary,certificateRevocationList;binary";
    public static final String AD_USER_CERTIFICATE = "userCertificate;binary";
    public static final String RP_ATTRIBUTE_DESCRIPTION = "attributeDescription";
    public static final String RP_BASE_DN = "dn";
    public static final String RP_EXTENSIONS = "extensions";
    public static final String RP_FILTER = "filter";
    public static final String RP_SEARCH_SCOPE = "scope";
    public static final String RP_SECURITY_AUTHENTICATION = "securityAuthentication";
    public static final String RP_SECURITY_CREDENTIALS = "securityCredentials";
    public static final String RP_SECURITY_PRINCIPAL = "securityPrincipal";
    public static final String RP_SIZE_LIMIT = "sizeLimit";
    public static final String SEARCH_SCOPE_BASE = "base";
    public static final String SEARCH_SCOPE_ONELEVEL = "one";
    public static final String SEARCH_SCOPE_SUBTREE = "sub";
    private static Vector c;
    private static int d;
    private static int e;
    DirContext a;
    DirContext b;
    private String f;
    private String[] g;
    private int h;
    private int i;
    private String j;
    private String k;
    private int l;
    private int m;
    private int n;
    private String o;
    private String p;
    private String q;
    private InputStream r;

    static {
        Vector vector = new Vector();
        c = vector;
        vector.addElement("com.sun.jndi.ldap.connect.timeout");
        d = -1;
        e = 0;
        Util.toString((byte[]) null, -1, 1);
    }

    public LdapURLConnection(URL url) throws IOException {
        super(url);
        this.i = 0;
        this.l = 0;
        this.m = d;
        this.n = e;
        this.h = -1;
        this.doInput = true;
        this.doOutput = false;
        this.allowUserInteraction = false;
        this.useCaches = false;
        this.ifModifiedSince = 0L;
        a();
    }

    private static int a(String str) throws IllegalArgumentException {
        String strTrim = str.toLowerCase().trim();
        if (strTrim.length() > 0) {
            if (strTrim.equals(SEARCH_SCOPE_ONELEVEL)) {
                return 1;
            }
            if (strTrim.equals(SEARCH_SCOPE_SUBTREE)) {
                return 2;
            }
            if (!strTrim.equals(SEARCH_SCOPE_BASE)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid search scope \"");
                stringBuffer.append(str);
                stringBuffer.append("\"! ");
                stringBuffer.append("Must be \"base\", \"one\" or \"sub\".");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
        return 0;
    }

    private static String a(int i) {
        return i == 1 ? SEARCH_SCOPE_ONELEVEL : i == 2 ? SEARCH_SCOPE_SUBTREE : SEARCH_SCOPE_BASE;
    }

    private String a(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (strArr != null) {
            for (int i = 0; i < strArr.length; i++) {
                if (i > 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(strArr[i]);
            }
        }
        String string = stringBuffer.toString();
        if (string.length() == 0) {
            return null;
        }
        return string;
    }

    private void a() throws IOException {
        Objects.requireNonNull(this.url, "URL must not be null!");
        String strUnescape = unescape(this.url.getFile());
        int iIndexOf = strUnescape.indexOf(63);
        int length = iIndexOf == -1 ? strUnescape.length() : iIndexOf;
        if (length > 1) {
            this.f = strUnescape.substring(1, length);
        }
        String strSubstring = iIndexOf != -1 ? strUnescape.substring(iIndexOf + 1) : null;
        if (strSubstring != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(strSubstring, "?", true);
            if (stringTokenizer.hasMoreTokens()) {
                String strNextToken = stringTokenizer.nextToken();
                if (strNextToken.compareTo("?") != 0) {
                    try {
                        this.g = a(strNextToken, true);
                        if (stringTokenizer.hasMoreTokens()) {
                            stringTokenizer.nextToken();
                        }
                    } catch (IllegalArgumentException e2) {
                        throw new MalformedURLException(e2.getMessage());
                    }
                }
                if (stringTokenizer.hasMoreTokens()) {
                    String strNextToken2 = stringTokenizer.nextToken();
                    if (strNextToken2.compareTo("?") != 0) {
                        try {
                            this.i = a(strNextToken2);
                            if (stringTokenizer.hasMoreTokens()) {
                                stringTokenizer.nextToken();
                            }
                        } catch (IllegalArgumentException e3) {
                            throw new MalformedURLException(e3.getMessage());
                        }
                    }
                    if (stringTokenizer.hasMoreTokens()) {
                        String strNextToken3 = stringTokenizer.nextToken();
                        if (strNextToken3.compareTo("?") != 0) {
                            this.j = strNextToken3;
                            if (stringTokenizer.hasMoreTokens()) {
                                stringTokenizer.nextToken();
                            }
                        }
                        if (stringTokenizer.hasMoreTokens()) {
                            String strNextToken4 = stringTokenizer.nextToken();
                            this.k = strNextToken4;
                            if (strNextToken4.length() > 0) {
                                StringTokenizer stringTokenizer2 = new StringTokenizer(this.k, ",");
                                while (stringTokenizer2.hasMoreTokens()) {
                                    String strNextToken5 = stringTokenizer2.nextToken();
                                    if (strNextToken5.startsWith("!")) {
                                        StringBuffer stringBuffer = new StringBuffer();
                                        stringBuffer.append("LDAP url contains unsupported critical extension: ");
                                        stringBuffer.append(strNextToken5);
                                        throw new MalformedURLException(stringBuffer.toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private String[] a(String str, boolean z) throws IllegalArgumentException {
        int i = this.h;
        if (z) {
            this.h = -1;
        }
        Vector vector = new Vector();
        try {
            if (str.length() > 0) {
                StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    String strNextToken = stringTokenizer.nextToken();
                    b(strNextToken);
                    vector.addElement(strNextToken);
                }
            }
            String[] strArr = new String[vector.size()];
            vector.copyInto(strArr);
            return strArr;
        } catch (IllegalArgumentException e2) {
            this.h = i;
            throw e2;
        }
    }

    public static void addConnectTimeoutEnvPropKey(String str) {
        Objects.requireNonNull(str, "Property key must not be null!");
        c.addElement(str);
    }

    private String b(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (strArr != null) {
            if (strArr.length > 1) {
                stringBuffer.append("(|");
            }
            for (String str : strArr) {
                if (str.length() > 0) {
                    stringBuffer.append("(");
                    stringBuffer.append(str);
                    stringBuffer.append("=*)");
                }
            }
            if (strArr.length > 1) {
                stringBuffer.append(")");
            }
        }
        String string = stringBuffer.toString();
        return string.length() <= 3 ? "(objectClass=*)" : string;
    }

    private void b(String str) throws IllegalArgumentException {
        int i;
        String lowerCase = str.toLowerCase();
        if (lowerCase.indexOf("revocation") != -1) {
            i = 2;
        } else {
            if (lowerCase.indexOf("certificate") == -1) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid attribute description: \"");
                stringBuffer.append(str);
                stringBuffer.append("\". ");
                stringBuffer.append("Only certificate and revocation list attributes allowed!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            i = lowerCase.indexOf("attributecertificate") != -1 ? 1 : 0;
        }
        int i2 = this.h;
        if (i2 == -1) {
            this.h = i;
            return;
        }
        if (i2 == i) {
            return;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Invalid attribute description: \"");
        stringBuffer2.append(str);
        stringBuffer2.append("\". ");
        stringBuffer2.append("All attributes must have same type!");
        throw new IllegalArgumentException(stringBuffer2.toString());
    }

    public static int getDefaultConnectTimeout() {
        return d;
    }

    public static int getDefaultReadTimeOut() {
        return e;
    }

    public static void setDefaultConnectTimeout(int i) {
        if (i < 0) {
            i = -1;
        }
        d = i;
    }

    public static void setDefaultReadTimeOut(int i) {
        if (i >= 0) {
            e = i;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("timeout (");
        stringBuffer.append(i);
        stringBuffer.append(") cannot be negative");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    @Override // java.net.URLConnection
    public void addRequestProperty(String str, String str2) {
        String[] strArrA;
        boolean z;
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        Objects.requireNonNull(str, "key must not be null");
        if (!str.equalsIgnoreCase(RP_ATTRIBUTE_DESCRIPTION)) {
            setRequestProperty(str, str2);
            return;
        }
        if (str2 == null || str2.length() <= 0 || (strArrA = a(str2, false)) == null || strArrA.length <= 0) {
            return;
        }
        String[] strArr = this.g;
        if (strArr == null || strArr.length == 0) {
            this.g = strArrA;
            return;
        }
        Vector vector = new Vector();
        for (int i = 0; i < strArrA.length; i++) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.g;
                if (i2 >= strArr2.length) {
                    z = true;
                    break;
                } else {
                    if (strArrA[i].equals(strArr2[i2])) {
                        z = false;
                        break;
                    }
                    i2++;
                }
            }
            if (z) {
                vector.addElement(strArrA[i]);
            }
        }
        if (vector.size() > 0) {
            String[] strArr3 = this.g;
            int length = strArr3.length;
            this.g = (String[]) Util.resizeArray(strArr3, strArr3.length + vector.size());
            Enumeration enumerationElements = vector.elements();
            while (enumerationElements.hasMoreElements()) {
                this.g[length] = (String) enumerationElements.nextElement();
                length++;
            }
        }
    }

    @Override // java.net.URLConnection
    public void connect() throws IOException {
        Hashtable hashtable;
        if (this.connected) {
            return;
        }
        String[] strArr = this.g;
        if (strArr == null || strArr.length == 0) {
            throw new IOException("No attributes specified!");
        }
        StringBuffer stringBuffer = new StringBuffer(32);
        stringBuffer.append(this.url.getProtocol());
        stringBuffer.append("://");
        stringBuffer.append(this.url.getHost());
        int port = this.url.getPort();
        if (port > 0) {
            stringBuffer.append(':');
            stringBuffer.append(port);
        }
        try {
            int size = c.size();
            if (this.o != null) {
                hashtable = new Hashtable(size + 3, 1.0f);
                hashtable.put("java.naming.security.authentication", this.o);
                if (!this.o.toLowerCase().equals("none")) {
                    String str = this.p;
                    if (str != null) {
                        hashtable.put("java.naming.security.principal", str);
                    }
                    String str2 = this.q;
                    if (str2 != null) {
                        hashtable.put("java.naming.security.credentials", str2);
                    }
                }
            } else {
                hashtable = null;
            }
            if (this.m > -1) {
                if (hashtable == null) {
                    hashtable = new Hashtable(size, 1.0f);
                }
                Enumeration enumerationElements = c.elements();
                while (enumerationElements.hasMoreElements()) {
                    hashtable.put(enumerationElements.nextElement(), String.valueOf(this.m));
                }
            }
            if (this.n > -1) {
                if (hashtable == null) {
                    hashtable = new Hashtable(size, 1.0f);
                }
                hashtable.put("com.sun.jndi.ldap.read.timeout", String.valueOf(this.n));
            }
            this.a = hashtable != null ? new InitialDirContext(hashtable) : new InitialDirContext();
            this.b = (DirContext) this.a.lookup(stringBuffer.toString());
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(this.i);
            searchControls.setTimeLimit(this.n);
            searchControls.setCountLimit(this.l);
            searchControls.setReturningAttributes(this.g);
            String str3 = this.f;
            if (str3 == null || str3.length() == 0) {
                str3 = "";
            }
            String strB = this.j;
            if (strB == null) {
                strB = b(this.g);
            }
            this.r = new a(this, this.b.search(str3, strB, searchControls));
            this.connected = true;
        } catch (NamingException e2) {
            DirContext dirContext = this.b;
            if (dirContext != null) {
                try {
                    dirContext.close();
                } catch (NamingException unused) {
                }
            }
            DirContext dirContext2 = this.a;
            if (dirContext2 != null) {
                try {
                    dirContext2.close();
                } catch (NamingException unused2) {
                }
            }
            this.b = null;
            this.a = null;
            throw new IOException(this, e2.toString(), e2) { // from class: iaik.x509.net.ldap.LdapURLConnection.1
                private static final long serialVersionUID = 5607903656478303144L;
                private final NamingException a;
                private final LdapURLConnection b;

                {
                    this.b = this;
                    this.a = e2;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        }
    }

    public void disconnect() {
        DirContext dirContext = this.b;
        if (dirContext != null) {
            try {
                dirContext.close();
            } catch (NamingException unused) {
            }
        }
        DirContext dirContext2 = this.a;
        if (dirContext2 != null) {
            try {
                dirContext2.close();
            } catch (NamingException unused2) {
            }
        }
        this.b = null;
        this.a = null;
        this.connected = false;
    }

    @Override // java.net.URLConnection
    public int getConnectTimeout() {
        return this.m;
    }

    @Override // java.net.URLConnection
    public Object getContent() throws IOException {
        int i;
        connect();
        Vector vector = new Vector();
        InputStream inputStream = this.r;
        if (inputStream != null && (i = this.h) != -1) {
            if (i == 0) {
                while (true) {
                    try {
                        try {
                            vector.addElement(new X509Certificate(inputStream));
                        } catch (Throwable th) {
                            InputStream inputStream2 = this.r;
                            if (inputStream2 != null) {
                                try {
                                    inputStream2.close();
                                } catch (Exception unused) {
                                }
                            }
                            throw th;
                        }
                    } catch (EOFException unused2) {
                        InputStream inputStream3 = this.r;
                        if (inputStream3 != null) {
                            try {
                                inputStream3.close();
                            } catch (Exception unused3) {
                            }
                        }
                        X509Certificate[] x509CertificateArr = new X509Certificate[vector.size()];
                        vector.copyInto(x509CertificateArr);
                        return x509CertificateArr;
                    } catch (Exception e2) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Error parsing certificate: ");
                        stringBuffer.append(e2.toString());
                        throw new IOException(stringBuffer.toString());
                    }
                }
            } else if (i == 1) {
                while (true) {
                    try {
                        try {
                            vector.addElement(new AttributeCertificate(inputStream));
                        } catch (Throwable th2) {
                            InputStream inputStream4 = this.r;
                            if (inputStream4 != null) {
                                try {
                                    inputStream4.close();
                                } catch (Exception unused4) {
                                }
                            }
                            throw th2;
                        }
                    } catch (EOFException unused5) {
                        InputStream inputStream5 = this.r;
                        if (inputStream5 != null) {
                            try {
                                inputStream5.close();
                            } catch (Exception unused6) {
                            }
                        }
                        AttributeCertificate[] attributeCertificateArr = new AttributeCertificate[vector.size()];
                        vector.copyInto(attributeCertificateArr);
                        return attributeCertificateArr;
                    } catch (Exception e3) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Error parsing certificate: ");
                        stringBuffer2.append(e3.toString());
                        throw new IOException(stringBuffer2.toString());
                    }
                }
            } else if (i == 2) {
                while (true) {
                    try {
                        try {
                            vector.addElement(new X509CRL(inputStream));
                        } catch (Throwable th3) {
                            InputStream inputStream6 = this.r;
                            if (inputStream6 != null) {
                                try {
                                    inputStream6.close();
                                } catch (Exception unused7) {
                                }
                            }
                            throw th3;
                        }
                    } catch (EOFException unused8) {
                        InputStream inputStream7 = this.r;
                        if (inputStream7 != null) {
                            try {
                                inputStream7.close();
                            } catch (Exception unused9) {
                            }
                        }
                        X509CRL[] x509crlArr = new X509CRL[vector.size()];
                        vector.copyInto(x509crlArr);
                        return x509crlArr;
                    } catch (Exception e4) {
                        StringBuffer stringBuffer3 = new StringBuffer();
                        stringBuffer3.append("Error parsing crl: ");
                        stringBuffer3.append(e4.toString());
                        throw new IOException(stringBuffer3.toString());
                    }
                }
            }
        }
        return null;
    }

    @Override // java.net.URLConnection
    public String getHeaderField(String str) {
        if (str != null) {
            String lowerCase = str.toLowerCase();
            if (lowerCase.equals("content-type")) {
                int i = this.h;
                if (i != -1) {
                    if (i == 0) {
                        return "certificate;binary";
                    }
                    if (i == 1) {
                        return AD_ATTRIBUTE_CERTIFICATE;
                    }
                    if (i == 2) {
                        return "revocationList;binary";
                    }
                }
            } else if (lowerCase.equals("content-encoding")) {
                return X509CertPath.DER;
            }
        }
        return null;
    }

    @Override // java.net.URLConnection
    public InputStream getInputStream() throws IOException {
        connect();
        return this.r;
    }

    @Override // java.net.URLConnection
    public int getReadTimeout() {
        return this.n;
    }

    @Override // java.net.URLConnection
    public String getRequestProperty(String str) {
        super.getRequestProperty(str);
        if (str != null) {
            if (str.equalsIgnoreCase(RP_BASE_DN)) {
                return this.f;
            }
            if (str.equalsIgnoreCase(RP_SEARCH_SCOPE)) {
                return a(this.i);
            }
            if (str.equalsIgnoreCase(RP_SIZE_LIMIT)) {
                return String.valueOf(this.l);
            }
            if (str.equalsIgnoreCase(RP_FILTER)) {
                String str2 = this.j;
                return str2 != null ? str2 : b(this.g);
            }
            if (str.equalsIgnoreCase(RP_ATTRIBUTE_DESCRIPTION)) {
                return a(this.g);
            }
            if (str.equalsIgnoreCase(RP_EXTENSIONS)) {
                return this.k;
            }
            if (str.equalsIgnoreCase(RP_SECURITY_AUTHENTICATION)) {
                return this.o;
            }
            if (str.equalsIgnoreCase(RP_SECURITY_PRINCIPAL)) {
                return this.p;
            }
            if (str.equalsIgnoreCase(RP_SECURITY_CREDENTIALS)) {
                return this.q;
            }
        }
        return null;
    }

    @Override // java.net.URLConnection
    public void setAllowUserInteraction(boolean z) {
    }

    @Override // java.net.URLConnection
    public void setConnectTimeout(int i) {
        if (i < -1) {
            this.m = -1;
        } else {
            this.m = i;
        }
    }

    @Override // java.net.URLConnection
    public void setDoInput(boolean z) {
    }

    @Override // java.net.URLConnection
    public void setDoOutput(boolean z) {
    }

    @Override // java.net.URLConnection
    public void setIfModifiedSince(long j) {
    }

    @Override // java.net.URLConnection
    public void setReadTimeout(int i) {
        if (i >= 0) {
            this.n = i;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("timeout (");
        stringBuffer.append(i);
        stringBuffer.append(") cannot be negative");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    @Override // java.net.URLConnection
    public void setRequestProperty(String str, String str2) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        }
        Objects.requireNonNull(str, "key must not be null");
        if (str.equalsIgnoreCase(RP_BASE_DN)) {
            if (str2 == null) {
                this.f = null;
                return;
            }
            try {
                if (str2.length() != 0) {
                    str2 = unescape(str2);
                }
                this.f = str2;
                return;
            } catch (Exception e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid dn encoding: ");
                stringBuffer.append(e2.getMessage());
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
        if (str.equalsIgnoreCase(RP_SEARCH_SCOPE)) {
            if (str2 == null) {
                this.i = 0;
                return;
            } else {
                this.i = a(str2);
                return;
            }
        }
        if (str.equalsIgnoreCase(RP_SIZE_LIMIT)) {
            try {
                if (str2 == null) {
                    this.l = 0;
                    return;
                }
                int i = Integer.parseInt(str2);
                if (i >= 0) {
                    this.l = i;
                    return;
                }
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("sizeLimit value (");
                stringBuffer2.append(str2);
                stringBuffer2.append(") ");
                stringBuffer2.append("must not be negative!");
                throw new IllegalArgumentException(stringBuffer2.toString());
            } catch (NumberFormatException unused) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid sizeLimit value (");
                stringBuffer3.append(str2);
                stringBuffer3.append("). ");
                stringBuffer3.append("Must be numeric value!");
                throw new IllegalArgumentException(stringBuffer3.toString());
            }
        }
        if (str.equalsIgnoreCase(RP_FILTER)) {
            if (str2 == null) {
                this.j = null;
                return;
            }
            try {
                if (str2.length() != 0) {
                    str2 = unescape(str2);
                }
                this.j = str2;
                return;
            } catch (Exception e3) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Invalid filter encoding: ");
                stringBuffer4.append(e3.getMessage());
                throw new IllegalArgumentException(stringBuffer4.toString());
            }
        }
        if (str.equalsIgnoreCase(RP_ATTRIBUTE_DESCRIPTION)) {
            if (str2 != null) {
                this.g = a(str2, true);
                return;
            } else {
                this.g = null;
                this.h = -1;
                return;
            }
        }
        if (str.equalsIgnoreCase(RP_SECURITY_AUTHENTICATION)) {
            this.o = str2;
            return;
        }
        if (str.equalsIgnoreCase(RP_SECURITY_PRINCIPAL)) {
            this.p = str2;
        } else {
            if (str.equalsIgnoreCase(RP_SECURITY_CREDENTIALS)) {
                this.q = str2;
                return;
            }
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Unsupported request property: ");
            stringBuffer5.append(str);
            throw new IllegalArgumentException(stringBuffer5.toString());
        }
    }

    @Override // java.net.URLConnection
    public void setUseCaches(boolean z) {
    }

    @Override // java.net.URLConnection
    public String toString() {
        StringBuffer stringBuffer;
        String strB;
        String strA;
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("url: ");
        stringBuffer3.append(this.url);
        stringBuffer2.append(stringBuffer3.toString());
        if (this.f != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("\ndn: ");
            stringBuffer4.append(this.f);
            stringBuffer2.append(stringBuffer4.toString());
        }
        String[] strArr = this.g;
        if (strArr != null && (strA = a(strArr)) != null) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("\nattribute description: ");
            stringBuffer5.append(strA);
            stringBuffer2.append(stringBuffer5.toString());
        }
        StringBuffer stringBuffer6 = new StringBuffer();
        stringBuffer6.append("\nscope: \"");
        stringBuffer6.append(a(this.i));
        stringBuffer6.append("\"");
        stringBuffer2.append(stringBuffer6.toString());
        if (this.j != null) {
            stringBuffer = new StringBuffer();
            stringBuffer.append("\nfilter: ");
            strB = this.j;
        } else {
            stringBuffer = new StringBuffer();
            stringBuffer.append("\nfilter: ");
            strB = b(this.g);
        }
        stringBuffer.append(strB);
        stringBuffer2.append(stringBuffer.toString());
        if (this.k != null) {
            StringBuffer stringBuffer7 = new StringBuffer();
            stringBuffer7.append("\nextensions (not supported): ");
            stringBuffer7.append(this.k);
            stringBuffer2.append(stringBuffer7.toString());
        }
        StringBuffer stringBuffer8 = new StringBuffer();
        stringBuffer8.append("\nsize limit: ");
        stringBuffer8.append(this.l);
        stringBuffer2.append(stringBuffer8.toString());
        StringBuffer stringBuffer9 = new StringBuffer();
        stringBuffer9.append("\nconnect time out (ms): ");
        stringBuffer9.append(this.m);
        stringBuffer2.append(stringBuffer9.toString());
        if (this.m == -1) {
            stringBuffer2.append(" (not specified)");
        }
        StringBuffer stringBuffer10 = new StringBuffer();
        stringBuffer10.append("\nread time out (ms): ");
        stringBuffer10.append(this.n);
        stringBuffer2.append(stringBuffer10.toString());
        if (this.o != null) {
            StringBuffer stringBuffer11 = new StringBuffer();
            stringBuffer11.append("\nsecurity authentication method: \"");
            stringBuffer11.append(this.o);
            stringBuffer11.append("\"");
            stringBuffer2.append(stringBuffer11.toString());
        }
        return stringBuffer2.toString();
    }

    protected String unescape(String str) throws IOException {
        Objects.requireNonNull(str, "Cannot unescape null String!");
        StringBuffer stringBuffer = new StringBuffer(str.length());
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '%') {
                stringBuffer.append(cCharAt);
            } else {
                int[] iArr = new int[3];
                iArr[0] = Integer.parseInt(str.substring(i + 1, i + 3), 16);
                if ((iArr[0] & DataTypeTag.CONSTRUCTIVE) == 224) {
                    iArr[1] = Integer.parseInt(str.substring(i + 4, i + 6), 16);
                    iArr[2] = Integer.parseInt(str.substring(i + 7, i + 9), 16);
                    stringBuffer.append((char) (((iArr[0] & 15) << 12) + ((iArr[1] & 63) << 6) + (iArr[2] & 63)));
                    i += 8;
                } else if ((iArr[0] & 192) == 192) {
                    iArr[1] = Integer.parseInt(str.substring(i + 4, i + 6), 16);
                    stringBuffer.append((char) (((iArr[0] & 31) << 6) + (iArr[1] & 63)));
                    i += 5;
                } else {
                    if ((iArr[0] & 128) != 0) {
                        throw new IOException("invlaid encoding");
                    }
                    stringBuffer.append((char) iArr[0]);
                    i += 2;
                }
            }
            i++;
        }
        return stringBuffer.toString();
    }
}

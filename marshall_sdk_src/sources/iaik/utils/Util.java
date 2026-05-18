package iaik.utils;

import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Name;
import iaik.pkcs.pkcs10.CertificateRequest;
import iaik.pkcs.pkcs8.EncryptedPrivateKeyInfo;
import iaik.pkcs.pkcs8.PrivateKeyInfo;
import iaik.security.ssl.SecurityProvider;
import iaik.x509.PublicKeyInfo;
import iaik.x509.X509CRL;
import iaik.x509.X509Certificate;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.attr.AttributeCertificate;
import iaik.x509.extensions.AuthorityKeyIdentifier;
import iaik.x509.extensions.SubjectKeyIdentifier;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes2.dex */
public class Util {
    public static final int DEFAULT_JVM_DATA_MODEL = 32;
    static Class a = null;
    static Class b = null;
    private static boolean c = true;
    private static String f;
    private static final String[] d = {"ISO8859_1", "iso-8859-1", "ISO-8859-1", "iso_8859-1", "iso8859-1", "iso_8859_1", "iso8859_1", "Cp1252", "ASCII", "Default"};
    private static final byte[] e = {97, marshall_t.status_vpos_processing_error, 122, 90, 48, 57, 43, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, marshall_t.status_vpos_insert_card};
    private static boolean g = false;

    private static class IaikVector extends Vector {
        private static final long serialVersionUID = -1508109320737291997L;

        public IaikVector(Object[] objArr) {
            super((objArr.length * 11) / 10);
            System.arraycopy(objArr, 0, this.elementData, 0, objArr.length);
            this.elementCount = objArr.length;
        }
    }

    static {
        toString((byte[]) null, -1, 1);
    }

    private Util() {
    }

    public static byte[] Base64Decode(byte[] bArr) throws Base64Exception {
        Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(bArr));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[1024];
        while (true) {
            try {
                int i = base64InputStream.read(bArr2);
                if (i <= 0) {
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr2, 0, i);
            } catch (IOException e2) {
                throw new Base64Exception(e2.getMessage());
            }
        }
    }

    public static byte[] Base64Encode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream);
        try {
            base64OutputStream.write(bArr);
            base64OutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new InternalErrorException(e2);
        }
    }

    private static String a(byte[] bArr) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr)));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
            String str = null;
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("\"");
                    stringBuffer.append(str);
                    stringBuffer.append("\";");
                    printWriter.println(stringBuffer.toString());
                    printWriter.flush();
                    return toASCIIString(byteArrayOutputStream.toByteArray());
                }
                if (str != null) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("\"");
                    stringBuffer2.append(str);
                    stringBuffer2.append("\" +");
                    printWriter.println(stringBuffer2.toString());
                }
                str = line;
            }
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x00ae, code lost:
    
        r11 = new java.lang.StringBuffer();
        r11.append("Cannot parse UTF8 encoding! Character ");
        r11.append((char) r4);
        r11.append(" out of range!");
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ca, code lost:
    
        throw new iaik.utils.UTF8CodingException(r11.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00cb, code lost:
    
        return r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017 A[Catch: Exception -> 0x00cc, PHI: r3 r4
  0x0017: PHI (r3v7 int) = (r3v3 int), (r3v4 int), (r3v1 int) binds: [B:54:0x009a, B:24:0x0048, B:7:0x0015] A[DONT_GENERATE, DONT_INLINE]
  0x0017: PHI (r4v6 int) = (r4v3 int), (r4v5 int), (r4v1 int) binds: [B:54:0x009a, B:24:0x0048, B:7:0x0015] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x00cc, blocks: (B:3:0x0002, B:5:0x000c, B:8:0x0017, B:61:0x00aa, B:12:0x0024, B:16:0x002c, B:17:0x0033, B:18:0x0034, B:20:0x0038, B:27:0x004d, B:28:0x0054, B:32:0x0059, B:36:0x0060, B:40:0x0067, B:41:0x006e, B:42:0x006f, B:46:0x007a, B:47:0x0081, B:48:0x0082, B:50:0x0087, B:57:0x009f, B:58:0x00a6, B:62:0x00ad, B:63:0x00ae, B:64:0x00ca), top: B:75:0x0002, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.StringBuffer a(byte[] r10, boolean r11) throws iaik.utils.UTF8CodingException {
        /*
            Method dump skipped, instruction units count: 223
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.utils.Util.a(byte[], boolean):java.lang.StringBuffer");
    }

    private static synchronized void a() {
        if (f != null) {
            return;
        }
        int i = 0;
        while (true) {
            String[] strArr = d;
            if (i >= strArr.length) {
                throw new RuntimeException("Could not find a ASCII compatible encoding!");
            }
            try {
                String str = strArr[i];
                if (CryptoUtils.equalsBlock("aAzZ09+/=".getBytes(str), e)) {
                    f = str;
                    return;
                }
                continue;
            } catch (Throwable unused) {
            }
            i++;
        }
    }

    private static void a(byte b2, char[] cArr) {
        int i = (b2 & marshall_t.marshall_packet_option_rfu_mask) >> 4;
        int i2 = b2 & 15;
        cArr[1] = (char) (i > 9 ? (i + 65) - 10 : i + 48);
        cArr[2] = (char) (i2 > 9 ? (i2 + 65) - 10 : i2 + 48);
    }

    private static void a(int i, int i2, OutputStream outputStream) throws UTF8CodingException {
        if (outputStream == null) {
            throw new UTF8CodingException("Cannot write to a null stream!");
        }
        if (i2 < 1 || i2 > 6) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid number ");
            stringBuffer.append(i2);
            stringBuffer.append(" of octets for UTF8 encoding. A character ");
            stringBuffer.append("only can be encoded as a sequence of 1 to 6 octets!");
            throw new UTF8CodingException(stringBuffer.toString());
        }
        try {
            if (i2 == 1) {
                outputStream.write(i);
                return;
            }
            outputStream.write(((255 << (8 - i2)) & 255) | ((i >> ((i2 - 1) * 6)) & (255 >> (i2 + 1))));
            for (int i3 = i2 - 2; i3 >= 0; i3--) {
                outputStream.write(((i >> (i3 * 6)) & 63) | 128);
            }
        } catch (IOException e2) {
            throw new UTF8CodingException(e2.getMessage());
        }
    }

    private static void a(int i, OutputStream outputStream) throws UTF8CodingException {
        int i2;
        if (outputStream == null) {
            throw new UTF8CodingException("Cannot write to a null stream!");
        }
        if (i >= 0 && i <= 127) {
            try {
                outputStream.write(i);
                return;
            } catch (IOException e2) {
                throw new UTF8CodingException(e2.getMessage());
            }
        }
        if (i >= 128 && i <= 2047) {
            i2 = 2;
        } else {
            if (i < 2048 || i > 65535) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot perform UTF8 encoding! Character ");
                stringBuffer.append((char) i);
                stringBuffer.append(" out of range!");
                throw new UTF8CodingException(stringBuffer.toString());
            }
            i2 = 3;
        }
        a(i, i2, outputStream);
    }

    public static X509Certificate[] arrangeCertificateChain(X509Certificate[] x509CertificateArr, boolean z) {
        X509Certificate[] x509CertificateArr2 = null;
        if (x509CertificateArr != null && x509CertificateArr.length > 0) {
            if (x509CertificateArr[0] != null) {
                Vector vector = new Vector();
                Vector vector2 = new Vector();
                vector.addElement(x509CertificateArr[0]);
                for (int i = 1; i < x509CertificateArr.length; i++) {
                    vector2.addElement(x509CertificateArr[i]);
                }
                boolean z2 = false;
                boolean z3 = false;
                while (vector2.size() > 0 && (!z2 || !z3)) {
                    boolean z4 = false;
                    for (int i2 = 0; i2 < vector2.size() && !z4 && !z3; i2++) {
                        X509Certificate x509Certificate = (X509Certificate) vector.elementAt(vector.size() - 1);
                        X509Certificate x509Certificate2 = (X509Certificate) vector2.elementAt(i2);
                        Name name = (Name) x509Certificate.getSubjectDN();
                        Name name2 = (Name) x509Certificate2.getIssuerDN();
                        if (name == null || !name.elements().hasMoreElements()) {
                            break;
                        }
                        if (name2.equals(name)) {
                            try {
                                x509Certificate2.verify(x509Certificate.getPublicKey());
                                try {
                                    vector2.removeElementAt(i2);
                                    vector.addElement(x509Certificate2);
                                } catch (Exception unused) {
                                }
                                z4 = true;
                            } catch (Exception unused2) {
                            }
                        }
                    }
                    if (!z4) {
                        z3 = true;
                    }
                    boolean z5 = false;
                    for (int i3 = 0; i3 < vector2.size() && !z5 && !z2; i3++) {
                        X509Certificate x509Certificate3 = (X509Certificate) vector2.elementAt(i3);
                        X509Certificate x509Certificate4 = (X509Certificate) vector.elementAt(0);
                        if (((Name) x509Certificate4.getIssuerDN()).equals((Name) x509Certificate3.getSubjectDN())) {
                            try {
                                x509Certificate4.verify(x509Certificate3.getPublicKey());
                                try {
                                    vector2.removeElementAt(i3);
                                    vector.insertElementAt(x509Certificate3, 0);
                                } catch (Exception unused3) {
                                }
                                z5 = true;
                            } catch (Exception unused4) {
                            }
                        }
                    }
                    if (!z5) {
                        z2 = true;
                    }
                }
                if (vector2.size() == 0) {
                    int size = vector.size();
                    x509CertificateArr2 = new X509Certificate[size];
                    for (int i4 = 0; i4 < size; i4++) {
                        if (z) {
                            x509CertificateArr2[i4] = (X509Certificate) vector.elementAt(i4);
                        } else {
                            x509CertificateArr2[i4] = (X509Certificate) vector.elementAt((vector.size() - 1) - i4);
                        }
                    }
                }
            }
        }
        return x509CertificateArr2;
    }

    private static synchronized void b() {
        if (g) {
            return;
        }
        g = true;
        String[] strArr = {"*****************************************************************************", "***                                                                       ***", "***                    Welcome to the IAIK JCE Library                    ***", "***                                                                       ***", "*** This version of IAIK-JCE is licensed for evaluation, education,       ***", "*** research, and use in open-source projects only.                       ***", "*** Commercial use of this software is prohibited.                        ***", "*** For details please see http://jce.iaik.tugraz.at/sales/.              ***", "*** This message does not appear in the registered commercial version.    ***", "***                                                                       ***", "*****************************************************************************", ""};
        for (int i = 0; i < 12; i++) {
            System.err.println(strArr[i]);
        }
    }

    public static int[] bubbleSort(int[] iArr) {
        boolean z;
        do {
            z = false;
            int i = 0;
            int i2 = 1;
            while (i2 < iArr.length) {
                if (iArr[i] > iArr[i2]) {
                    iArr[i2] = iArr[i2] ^ iArr[i];
                    iArr[i] = iArr[i] ^ iArr[i2];
                    iArr[i2] = iArr[i2] ^ iArr[i];
                    z = true;
                }
                i2++;
                i++;
            }
        } while (z);
        return iArr;
    }

    public static long[] bubbleSort(long[] jArr) {
        boolean z;
        do {
            z = false;
            int i = 0;
            int i2 = 1;
            while (i2 < jArr.length) {
                if (jArr[i] > jArr[i2]) {
                    jArr[i2] = jArr[i2] ^ jArr[i];
                    jArr[i] = jArr[i] ^ jArr[i2];
                    jArr[i2] = jArr[i2] ^ jArr[i];
                    z = true;
                }
                i2++;
                i++;
            }
        } while (z);
        return jArr;
    }

    public static int calculateHashCode(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i += bArr[i2] * i2;
        }
        return i;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static void compareTables(String str, int[] iArr, int[] iArr2) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (iArr[i] != iArr2[i]) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error in ");
                stringBuffer.append(str);
                stringBuffer.append(" index ");
                stringBuffer.append(i);
                stringBuffer.append(": ");
                stringBuffer.append(toString(iArr[i]));
                stringBuffer.append(" != ");
                stringBuffer.append(toString(iArr2[i]));
                throw new RuntimeException(stringBuffer.toString());
            }
        }
    }

    public static X509Certificate convertCertificate(Certificate certificate) throws CertificateException {
        if (certificate != null) {
            return certificate instanceof X509Certificate ? (X509Certificate) certificate : new X509Certificate(certificate.getEncoded());
        }
        return null;
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
                x509CertificateArr[i] = new X509Certificate(certificateArr[i].getEncoded());
            }
        }
        return x509CertificateArr;
    }

    public static byte[] convertEndian32(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        int i = 0;
        while (i < length) {
            int i2 = i + 3;
            int i3 = i + 1;
            int i4 = i2 - 1;
            bArr2[i] = bArr[i2];
            int i5 = i3 + 1;
            int i6 = i4 - 1;
            bArr2[i3] = bArr[i4];
            int i7 = i5 + 1;
            bArr2[i5] = bArr[i6];
            bArr2[i7] = bArr[i6 - 1];
            i = i7 + 1;
        }
        return bArr2;
    }

    public static AttributeCertificate convertToAttributeCertificate(Certificate certificate) throws CertificateException {
        if (certificate != null) {
            return certificate instanceof AttributeCertificate ? (AttributeCertificate) certificate : new AttributeCertificate(certificate.getEncoded());
        }
        return null;
    }

    public static AttributeCertificate[] convertToAttributeCertificateChain(Certificate[] certificateArr) throws CertificateException {
        if (certificateArr == null) {
            return null;
        }
        AttributeCertificate[] attributeCertificateArr = new AttributeCertificate[certificateArr.length];
        for (int i = 0; i < certificateArr.length; i++) {
            if (certificateArr[i] instanceof AttributeCertificate) {
                attributeCertificateArr[i] = (AttributeCertificate) certificateArr[i];
            } else {
                attributeCertificateArr[i] = new AttributeCertificate(certificateArr[i].getEncoded());
            }
        }
        return attributeCertificateArr;
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        Objects.requireNonNull(inputStream, "Argument \"source\" must not be null.");
        if (bArr == null) {
            bArr = new byte[4096];
        }
        if (outputStream == null) {
            while (inputStream.read(bArr) >= 0) {
            }
            return;
        }
        while (true) {
            int i = inputStream.read(bArr);
            if (i < 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    public static X509Certificate[] createCertificateChain(X509Certificate x509Certificate, Certificate[] certificateArr) throws CertificateException {
        Objects.requireNonNull(x509Certificate, "Argument \"userCertificate\" must not be null.");
        if (certificateArr == null || certificateArr.length == 0) {
            return new X509Certificate[]{x509Certificate};
        }
        Vector vector = new Vector(4);
        try {
            Hashtable hashtable = new Hashtable(certificateArr.length + 2);
            for (int i = 0; i < certificateArr.length; i++) {
                if (certificateArr[i] instanceof X509Certificate) {
                    X509Certificate x509CertificateConvertCertificate = convertCertificate(certificateArr[i]);
                    hashtable.put(((Name) x509CertificateConvertCertificate.getSubjectDN()).getRFC2253String(), x509CertificateConvertCertificate);
                }
            }
            vector.addElement(x509Certificate);
            X509Certificate x509Certificate2 = (X509Certificate) hashtable.get(((Name) x509Certificate.getIssuerDN()).getRFC2253String());
            int i2 = 20;
            while (true) {
                X509Certificate x509Certificate3 = x509Certificate2;
                X509Certificate x509Certificate4 = x509Certificate;
                x509Certificate = x509Certificate3;
                if (x509Certificate == null) {
                    break;
                }
                if (x509Certificate4.equals(x509Certificate)) {
                    break;
                }
                int i3 = i2 - 1;
                if (i2 <= 0) {
                    break;
                }
                AuthorityKeyIdentifier authorityKeyIdentifier = (AuthorityKeyIdentifier) x509Certificate4.getExtension(AuthorityKeyIdentifier.oid);
                SubjectKeyIdentifier subjectKeyIdentifier = (SubjectKeyIdentifier) x509Certificate.getExtension(SubjectKeyIdentifier.oid);
                if (authorityKeyIdentifier != null && subjectKeyIdentifier != null && !CryptoUtils.equalsBlock(authorityKeyIdentifier.getKeyIdentifier(), subjectKeyIdentifier.get())) {
                    break;
                }
                vector.addElement(x509Certificate);
                x509Certificate2 = (X509Certificate) hashtable.get(((Name) x509Certificate.getIssuerDN()).getRFC2253String());
                i2 = i3;
            }
            X509Certificate[] x509CertificateArr = new X509Certificate[vector.size()];
            vector.copyInto(x509CertificateArr);
            return x509CertificateArr;
        } catch (RFC2253NameParserException e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error parsing dn: ");
            stringBuffer.append(e2.toString());
            throw new CertificateException(stringBuffer.toString());
        } catch (X509ExtensionInitException e3) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Error parsing cert extension: ");
            stringBuffer2.append(e3.toString());
            throw new CertificateException(stringBuffer2.toString());
        } catch (CertificateException e4) {
            throw e4;
        }
    }

    public static byte[] decodeByteArray(String str) throws RuntimeException {
        try {
            return Base64Decode(toASCIIBytes(str));
        } catch (Base64Exception e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Fatal decoding error: ");
            stringBuffer.append(e2);
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    public static int[] decodeIntArray(String str) throws RuntimeException {
        try {
            byte[] bArrBase64Decode = Base64Decode(toASCIIBytes(str));
            int length = bArrBase64Decode.length >>> 2;
            int[] iArr = new int[length];
            CryptoUtils.squashBytesToInts(bArrBase64Decode, 0, iArr, 0, length);
            return iArr;
        } catch (Base64Exception e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Fatal decoding error: ");
            stringBuffer.append(e2);
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    public static Key decodeKey(int i, String str, byte[] bArr) throws NoSuchAlgorithmException, InvalidKeyException {
        if (i == 3) {
            return new SecretKeySpec(bArr, str);
        }
        if (i == 2) {
            try {
                return KeyFactory.getInstance(str).generatePrivate(new PKCS8EncodedKeySpec(bArr));
            } catch (InvalidKeySpecException e2) {
                throw new InvalidKeyException(e2.toString());
            }
        }
        if (i == 1) {
            try {
                return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(bArr));
            } catch (InvalidKeySpecException e3) {
                throw new InvalidKeyException(e3.toString());
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unknown key type ");
        stringBuffer.append(i);
        throw new InvalidKeyException(stringBuffer.toString());
    }

    public static X509Certificate[] decodePkiPath(byte[] bArr) throws CertificateException {
        Objects.requireNonNull(bArr, "pkiPath must not be null!");
        try {
            ASN1Object aSN1Object = new ASN1(bArr).toASN1Object();
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.x509.X509Certificate");
                a = clsClass$;
            }
            return (X509Certificate[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
        } catch (CodingException e2) {
            throw new CertificateException(e2.toString());
        }
    }

    public static byte[] encodeAsPkiPath(X509Certificate[] x509CertificateArr, boolean z) throws CertificateException {
        Objects.requireNonNull(x509CertificateArr, "certificates must not be null!");
        if (z && (x509CertificateArr = arrangeCertificateChain(x509CertificateArr, true)) == null) {
            throw new CertificateException("Could not sort certificates!");
        }
        try {
            return DerCoder.encode(ASN.createSequenceOf(x509CertificateArr));
        } catch (CodingException e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error creating pki path: ");
            stringBuffer.append(e2.toString());
            throw new CertificateException(stringBuffer.toString());
        }
    }

    public static String encodeByteArray(byte[] bArr) {
        return a(Base64Encode(bArr));
    }

    public static String encodeIntArray(int[] iArr) {
        int length = iArr.length;
        byte[] bArr = new byte[length << 2];
        CryptoUtils.spreadIntsToBytes(iArr, 0, bArr, 0, length);
        return a(Base64Encode(bArr));
    }

    public static void fillArray(byte[] bArr, InputStream inputStream) throws IOException {
        int length = bArr.length;
        int i = 0;
        while (length > 0) {
            int i2 = inputStream.read(bArr, i, length);
            if (i2 < 0) {
                throw new EOFException();
            }
            i += i2;
            length -= i2;
        }
    }

    public static byte[] fromBase64String(String str) throws Base64Exception {
        Objects.requireNonNull(str, "Base64 encoded data must not be null!");
        return Base64Decode(toASCIIBytes(str));
    }

    public static String fromBooleanArray(boolean[] zArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (boolean z : zArr) {
            stringBuffer.append(z ? '1' : '0');
        }
        return stringBuffer.toString();
    }

    public static BufferedReader getASCIIReader(InputStream inputStream) {
        if (f == null) {
            a();
        }
        try {
            return new BufferedReader(new InputStreamReader(inputStream, f));
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public static PrintWriter getASCIIWriter(OutputStream outputStream) {
        return getASCIIWriter(outputStream, false);
    }

    public static PrintWriter getASCIIWriter(OutputStream outputStream, boolean z) {
        if (f == null) {
            a();
        }
        try {
            return new PrintWriter(new OutputStreamWriter(outputStream, f), z);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public static int getCharFromUTF8Array(int[] iArr) throws UTF8CodingException {
        if (iArr == null) {
            throw new UTF8CodingException("Cannot decode from a null value!");
        }
        int length = iArr.length;
        if (length < 1 || length > 6) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid number ");
            stringBuffer.append(length);
            stringBuffer.append(" of octets for UTF8 encoding. A character ");
            stringBuffer.append("only can be encoded as a sequence of 1 to 6 octets!");
            throw new UTF8CodingException(stringBuffer.toString());
        }
        if (length == 1) {
            return iArr[0];
        }
        int i = length - 1;
        int i2 = (iArr[0] & (255 >> (length + 1))) << (i * 6);
        for (int i3 = 1; i3 < length; i3++) {
            int i4 = iArr[i3];
            if ((i4 & 192) != 128) {
                throw new UTF8CodingException("Error in UTF8 encoding! Invalid  10xxxxxx sequence!");
            }
            i2 |= (i4 & 63) << ((i - i3) * 6);
        }
        return i2;
    }

    public static char[] getCharsFromUTF8Encoding(byte[] bArr) throws UTF8CodingException {
        return getCharsFromUTF8Encoding(bArr, false);
    }

    public static char[] getCharsFromUTF8Encoding(byte[] bArr, boolean z) throws UTF8CodingException {
        StringBuffer stringBufferA = a(bArr, z);
        int length = stringBufferA.length();
        char[] cArr = new char[length];
        stringBufferA.getChars(0, length, cArr, 0);
        return cArr;
    }

    public static boolean getDefaultRFC2253StringEscaping() {
        return c;
    }

    public static int getDigestLength(AlgorithmID algorithmID) {
        ObjectID algorithm = algorithmID.getAlgorithm();
        if (!algorithm.equals(AlgorithmID.sha1.getAlgorithm()) && !algorithm.equals(AlgorithmID.sha.getAlgorithm())) {
            if (!algorithm.equals(AlgorithmID.md5.getAlgorithm())) {
                if (!algorithm.equals(AlgorithmID.sha256.getAlgorithm())) {
                    if (algorithm.equals(AlgorithmID.sha384.getAlgorithm())) {
                        return 48;
                    }
                    if (algorithm.equals(AlgorithmID.sha512.getAlgorithm())) {
                        return 64;
                    }
                    if (algorithm.equals(AlgorithmID.sha224.getAlgorithm())) {
                        return 28;
                    }
                    if (!algorithm.equals(AlgorithmID.ripeMd160.getAlgorithm()) && !algorithm.equals(AlgorithmID.ripeMd160_ISO.getAlgorithm())) {
                        if (!algorithm.equals(AlgorithmID.ripeMd128.getAlgorithm()) && !algorithm.equals(AlgorithmID.ripeMd128_ISO.getAlgorithm())) {
                            if (!algorithm.equals(AlgorithmID.ripeMd256.getAlgorithm())) {
                                if (algorithm.equals(AlgorithmID.whirlpool.getAlgorithm())) {
                                    return 64;
                                }
                                if (!algorithm.equals(AlgorithmID.gost3411.getAlgorithm())) {
                                    if (!algorithm.equals(AlgorithmID.md2.getAlgorithm()) && !algorithm.equals(AlgorithmID.md4.getAlgorithm())) {
                                        try {
                                            return algorithmID.getMessageDigestInstance().digest().length;
                                        } catch (Exception unused) {
                                            return 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return 32;
            }
            return 16;
        }
        return 20;
    }

    public static int getDigestLength(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        if (!upperCase.equals("SHA-1") && !upperCase.equals("SHA1") && !upperCase.equals("SHA")) {
            if (!upperCase.equals(SecurityProvider.ALG_DIGEST_MD5)) {
                if (!upperCase.equals("SHA-256") && !upperCase.equals(SecurityProvider.ALG_DIGEST_SHA256)) {
                    if (upperCase.equals("SHA-384") || upperCase.equals(SecurityProvider.ALG_DIGEST_SHA384)) {
                        return 48;
                    }
                    if (upperCase.equals("SHA-512") || upperCase.equals(SecurityProvider.ALG_DIGEST_SHA512)) {
                        return 64;
                    }
                    if (upperCase.equals("SHA-224") || upperCase.equals(SecurityProvider.ALG_DIGEST_SHA224)) {
                        return 28;
                    }
                    if (!upperCase.equals("RIPEMD-160") && !upperCase.equals("RIPEMD160")) {
                        if (!upperCase.equals("RIPEMD-128") && !upperCase.equals("RIPEMD128")) {
                            if (!upperCase.equals("RIPEMD-256") && !upperCase.equals("RIPEMD256")) {
                                if (upperCase.equals("RIPEMD-320") || upperCase.equals("RIPEMD320")) {
                                    return 40;
                                }
                                if (upperCase.equals("WHIRLPOOL")) {
                                    return 64;
                                }
                                if (!upperCase.equals("GOST3411")) {
                                    if (!upperCase.equals("MD2") && !upperCase.equals("MD4")) {
                                        try {
                                            return MessageDigest.getInstance(str).digest().length;
                                        } catch (Exception unused) {
                                            return 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return 32;
            }
            return 16;
        }
        return 20;
    }

    public static int getJVMDataMode() {
        try {
            String property = System.getProperty("sun.arch.data.model");
            if (property == null) {
                property = System.getProperty("oracle.arch.data.model");
            }
            return Integer.parseInt(property);
        } catch (Throwable unused) {
            return 32;
        }
    }

    public static int getKeyLength(PrivateKey privateKey) {
        BigInteger p;
        if (privateKey instanceof RSAPrivateKey) {
            p = ((RSAPrivateKey) privateKey).getModulus();
        } else if (privateKey instanceof DSAPrivateKey) {
            p = ((DSAPrivateKey) privateKey).getParams().getP();
        } else {
            if (!(privateKey instanceof DHPrivateKey)) {
                return -1;
            }
            p = ((DHPrivateKey) privateKey).getParams().getP();
        }
        return p.bitLength();
    }

    public static int getKeyLength(PublicKey publicKey) {
        BigInteger p;
        if (publicKey instanceof RSAPublicKey) {
            p = ((RSAPublicKey) publicKey).getModulus();
        } else if (publicKey instanceof DSAPublicKey) {
            p = ((DSAPublicKey) publicKey).getParams().getP();
        } else {
            if (!(publicKey instanceof DHPublicKey)) {
                return -1;
            }
            p = ((DHPublicKey) publicKey).getParams().getP();
        }
        return p.bitLength();
    }

    public static String getRFC2253String(String str) throws UTF8CodingException {
        return getRFC2253String(str.toCharArray());
    }

    public static String getRFC2253String(String str, boolean z) throws UTF8CodingException {
        return getRFC2253String(str.toCharArray(), z);
    }

    public static String getRFC2253String(char[] cArr) throws UTF8CodingException {
        return getRFC2253String(cArr, getDefaultRFC2253StringEscaping());
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0051 A[Catch: Exception -> 0x009c, TryCatch #0 {Exception -> 0x009c, blocks: (B:5:0x000f, B:7:0x0014, B:23:0x0051, B:25:0x005a, B:18:0x0037, B:36:0x0093, B:26:0x006a, B:17:0x0028, B:22:0x0041, B:30:0x0074, B:32:0x007d, B:33:0x0085, B:35:0x0088, B:31:0x007a, B:37:0x0097), top: B:44:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getRFC2253String(char[] r11, boolean r12) throws iaik.utils.UTF8CodingException {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            if (r11 == 0) goto La7
            r1 = 3
            char[] r2 = new char[r1]
            r3 = 92
            r4 = 0
            r2[r4] = r3
            int r3 = r11.length     // Catch: java.lang.Exception -> L9c
            r5 = 0
            r6 = 0
        L12:
            if (r6 >= r3) goto L97
            char r7 = r11[r6]     // Catch: java.lang.Exception -> L9c
            r8 = 32
            if (r7 < r8) goto L6e
            r9 = 126(0x7e, float:1.77E-43)
            if (r7 > r9) goto L6e
            java.lang.String r9 = "\\"
            if (r6 == 0) goto L26
            int r10 = r3 + (-1)
            if (r6 != r10) goto L51
        L26:
            if (r7 != r8) goto L3b
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch: java.lang.Exception -> L9c
            r8.<init>()     // Catch: java.lang.Exception -> L9c
            r8.append(r9)     // Catch: java.lang.Exception -> L9c
            r8.append(r7)     // Catch: java.lang.Exception -> L9c
            java.lang.String r7 = r8.toString()     // Catch: java.lang.Exception -> L9c
        L37:
            r0.append(r7)     // Catch: java.lang.Exception -> L9c
            goto L93
        L3b:
            if (r6 != 0) goto L51
            r8 = 35
            if (r7 != r8) goto L51
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch: java.lang.Exception -> L9c
            r8.<init>()     // Catch: java.lang.Exception -> L9c
            r8.append(r9)     // Catch: java.lang.Exception -> L9c
            r8.append(r7)     // Catch: java.lang.Exception -> L9c
            java.lang.String r7 = r8.toString()     // Catch: java.lang.Exception -> L9c
            goto L37
        L51:
            java.lang.String r8 = ",+\"\\<>;"
            int r8 = r8.indexOf(r7)     // Catch: java.lang.Exception -> L9c
            r10 = -1
            if (r8 == r10) goto L6a
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch: java.lang.Exception -> L9c
            r8.<init>()     // Catch: java.lang.Exception -> L9c
            r8.append(r9)     // Catch: java.lang.Exception -> L9c
            r8.append(r7)     // Catch: java.lang.Exception -> L9c
            java.lang.String r7 = r8.toString()     // Catch: java.lang.Exception -> L9c
            goto L37
        L6a:
            r0.append(r7)     // Catch: java.lang.Exception -> L9c
            goto L93
        L6e:
            if (r12 != 0) goto L72
            if (r7 != 0) goto L6a
        L72:
            if (r5 != 0) goto L7a
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Exception -> L9c
            r5.<init>(r1)     // Catch: java.lang.Exception -> L9c
            goto L7d
        L7a:
            r5.reset()     // Catch: java.lang.Exception -> L9c
        L7d:
            a(r7, r5)     // Catch: java.lang.Exception -> L9c
            byte[] r7 = r5.toByteArray()     // Catch: java.lang.Exception -> L9c
            r8 = 0
        L85:
            int r9 = r7.length     // Catch: java.lang.Exception -> L9c
            if (r8 >= r9) goto L93
            r9 = r7[r8]     // Catch: java.lang.Exception -> L9c
            a(r9, r2)     // Catch: java.lang.Exception -> L9c
            r0.append(r2)     // Catch: java.lang.Exception -> L9c
            int r8 = r8 + 1
            goto L85
        L93:
            int r6 = r6 + 1
            goto L12
        L97:
            java.lang.String r11 = r0.toString()     // Catch: java.lang.Exception -> L9c
            return r11
        L9c:
            r11 = move-exception
            iaik.utils.UTF8CodingException r12 = new iaik.utils.UTF8CodingException
            java.lang.String r11 = r11.getMessage()
            r12.<init>(r11)
            throw r12
        La7:
            iaik.utils.UTF8CodingException r11 = new iaik.utils.UTF8CodingException
            java.lang.String r12 = "Cannot UTF-8 encode a null array!"
            r11.<init>(r12)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.utils.Util.getRFC2253String(char[], boolean):java.lang.String");
    }

    public static iaik.security.rsa.RSAPrivateKey getRSAPrivateKey(Key key) throws InvalidKeyException {
        if (key instanceof iaik.security.rsa.RSAPrivateKey) {
            return (iaik.security.rsa.RSAPrivateKey) key;
        }
        if (!(key instanceof PrivateKey)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Class does not represent an RSA private key: ");
            stringBuffer.append(key.getClass().getName());
            throw new InvalidKeyException(stringBuffer.toString());
        }
        PrivateKey privateKey = PrivateKeyInfo.getPrivateKey(key.getEncoded(), "IAIK");
        if (privateKey instanceof iaik.security.rsa.RSAPrivateKey) {
            return (iaik.security.rsa.RSAPrivateKey) privateKey;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Key is not an RSA private key: ");
        stringBuffer2.append(privateKey.getClass().getName());
        throw new InvalidKeyException(stringBuffer2.toString());
    }

    public static iaik.security.rsa.RSAPublicKey getRSAPublicKey(Key key) throws InvalidKeyException {
        if (key instanceof iaik.security.rsa.RSAPublicKey) {
            return (iaik.security.rsa.RSAPublicKey) key;
        }
        if (!(key instanceof PublicKey)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Class does not represent an RSA public key: ");
            stringBuffer.append(key.getClass().getName());
            throw new InvalidKeyException(stringBuffer.toString());
        }
        PublicKey publicKey = PublicKeyInfo.getPublicKey(key.getEncoded(), "IAIK");
        if (publicKey instanceof iaik.security.rsa.RSAPublicKey) {
            return (iaik.security.rsa.RSAPublicKey) publicKey;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Key is not an RSA public key: ");
        stringBuffer2.append(publicKey.getClass().getName());
        throw new InvalidKeyException(stringBuffer2.toString());
    }

    public static String getRawCipherName(String str) {
        int iIndexOf = str.indexOf("/");
        return iIndexOf == -1 ? str : str.substring(0, iIndexOf);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.security.AlgorithmParameters getSignatureParameters(java.security.Signature r4) {
        /*
            java.util.Properties r0 = java.lang.System.getProperties()
            java.lang.String r1 = "java.version"
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L34
            java.lang.String r3 = "1.4"
            int r0 = r0.compareTo(r3)     // Catch: java.lang.Throwable -> L34
            if (r0 < 0) goto L34
            java.lang.Class r0 = iaik.utils.Util.b     // Catch: java.lang.Throwable -> L31
            if (r0 != 0) goto L24
            java.lang.String r0 = "java.security.Signature"
            java.lang.Class r0 = class$(r0)     // Catch: java.lang.Throwable -> L31
            iaik.utils.Util.b = r0     // Catch: java.lang.Throwable -> L31
        L24:
            java.lang.String r2 = "getParameters"
            java.lang.reflect.Method r0 = r0.getDeclaredMethod(r2, r1)     // Catch: java.lang.Throwable -> L31
            java.lang.Object r0 = r0.invoke(r4, r1)     // Catch: java.lang.Throwable -> L31
            java.security.AlgorithmParameters r0 = (java.security.AlgorithmParameters) r0     // Catch: java.lang.Throwable -> L31
            goto L32
        L31:
            r0 = r1
        L32:
            r2 = 1
            goto L35
        L34:
            r0 = r1
        L35:
            if (r2 != 0) goto L47
            java.lang.Object r0 = r4.getParameter(r1)     // Catch: java.lang.Throwable -> L3e
            java.security.AlgorithmParameters r0 = (java.security.AlgorithmParameters) r0     // Catch: java.lang.Throwable -> L3e
            goto L47
        L3e:
            java.lang.String r0 = ""
            java.lang.Object r4 = r4.getParameter(r0)
            r0 = r4
            java.security.AlgorithmParameters r0 = (java.security.AlgorithmParameters) r0
        L47:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.utils.Util.getSignatureParameters(java.security.Signature):java.security.AlgorithmParameters");
    }

    public static String getStringFromUTF8Encoding(byte[] bArr) throws UTF8CodingException {
        return a(bArr, false).toString();
    }

    public static String getStringFromUTF8Encoding(byte[] bArr, boolean z) throws UTF8CodingException {
        return a(bArr, z).toString();
    }

    public static byte[] getUTF8EncodingFromCharArray(char[] cArr) throws UTF8CodingException {
        if (cArr == null) {
            throw new UTF8CodingException("Cannot UTF-8 encode a null array!");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(cArr.length + 4);
        for (char c2 : cArr) {
            a(c2, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getUTF8EncodingFromString(String str) throws UTF8CodingException {
        if (str != null) {
            return getUTF8EncodingFromCharArray(str.toCharArray());
        }
        throw new UTF8CodingException("Cannot UTF-8 encode a null string!");
    }

    public static Vector getVector(Object[] objArr) {
        return new IaikVector(objArr);
    }

    public static boolean loadClass(String str, boolean z) {
        try {
            Class<?> cls = Class.forName(str);
            if (!z) {
                return true;
            }
            cls.newInstance();
            return true;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
            return false;
        }
    }

    public static String printIndented(String str, boolean z) {
        return printIndented(str, z, " ");
    }

    public static String printIndented(String str, boolean z, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        printIndented(str, z, str2, stringBuffer);
        return stringBuffer.toString();
    }

    public static void printIndented(String str, boolean z, String str2, StringBuffer stringBuffer) {
        if (str2 == null) {
            str2 = "";
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\n");
        String strNextToken = stringTokenizer.nextToken();
        if (z) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(str2);
            stringBuffer2.append(strNextToken);
            strNextToken = stringBuffer2.toString();
        }
        while (true) {
            stringBuffer.append(strNextToken);
            if (!stringTokenizer.hasMoreTokens()) {
                return;
            }
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\n");
            stringBuffer3.append(str2);
            stringBuffer3.append(stringTokenizer.nextToken());
            strNextToken = stringBuffer3.toString();
        }
    }

    public static void printIndented(String str, boolean z, StringBuffer stringBuffer) {
        printIndented(str, z, " ", stringBuffer);
    }

    public static void printTable(String str, byte[] bArr) {
        int length = bArr.length >> 2;
        int[] iArr = new int[length];
        CryptoUtils.squashBytesToInts(bArr, 0, iArr, 0, length);
        printTable(str, iArr);
    }

    public static void printTable(String str, int[] iArr) {
        PrintStream printStream = System.out;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("*** ");
        stringBuffer.append(str);
        printStream.println(stringBuffer.toString());
        int i = 0;
        for (int i2 : iArr) {
            System.out.print(toString(i2));
            int i3 = i + 1;
            if (i == 5) {
                System.out.println();
                i = 0;
            } else {
                System.out.print("  ");
                i = i3;
            }
        }
        System.out.println();
    }

    public static AttributeCertificate[] readAttributeCertificateChain(InputStream inputStream) throws IOException {
        Vector vector = new Vector();
        while (inputStream.available() > 10) {
            try {
                vector.addElement(new AttributeCertificate(inputStream));
            } catch (CertificateException e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unable to decode certificate: ");
                stringBuffer.append(e2.toString());
                throw new IOException(stringBuffer.toString());
            }
        }
        AttributeCertificate[] attributeCertificateArr = new AttributeCertificate[vector.size()];
        vector.copyInto(attributeCertificateArr);
        return attributeCertificateArr;
    }

    public static X509CRL[] readCRLChain(InputStream inputStream) throws IOException {
        Vector vector = new Vector();
        while (inputStream.available() > 10) {
            try {
                vector.addElement(new X509CRL(inputStream));
            } catch (CRLException e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unable to decode crl: ");
                stringBuffer.append(e2.toString());
                throw new IOException(stringBuffer.toString());
            }
        }
        X509CRL[] x509crlArr = new X509CRL[vector.size()];
        vector.copyInto(x509crlArr);
        return x509crlArr;
    }

    public static X509Certificate[] readCertificateChain(InputStream inputStream) throws IOException {
        Vector vector = new Vector();
        while (inputStream.available() > 10) {
            try {
                vector.addElement(new X509Certificate(inputStream));
            } catch (CertificateException e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unable to decode certificate: ");
                stringBuffer.append(e2.toString());
                throw new IOException(stringBuffer.toString());
            }
        }
        X509Certificate[] x509CertificateArr = new X509Certificate[vector.size()];
        vector.copyInto(x509CertificateArr);
        return x509CertificateArr;
    }

    public static byte[] readFile(String str) throws Throwable {
        File file = new File(str);
        byte[] bArr = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                fileInputStream2.read(bArr);
                try {
                    fileInputStream2.close();
                } catch (IOException unused) {
                }
                return bArr;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static X509Certificate[] readPkiPath(InputStream inputStream) throws IOException, CertificateException {
        Objects.requireNonNull(inputStream, "certificates must not be null!");
        try {
            ASN1Object aSN1Object = new ASN1(inputStream).toASN1Object();
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.x509.X509Certificate");
                a = clsClass$;
            }
            return (X509Certificate[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
        } catch (CodingException e2) {
            throw new CertificateException(e2.toString());
        }
    }

    public static byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        byte[] bArr = new byte[2048];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }

    public static byte[] resizeArray(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(i, bArr.length));
        return bArr2;
    }

    public static Object[] resizeArray(Object[] objArr, int i) {
        Object[] objArr2 = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        System.arraycopy(objArr, 0, objArr2, 0, Math.min(i, objArr.length));
        return objArr2;
    }

    public static void reverseArray(Object[] objArr, int i, int i2) {
        if (objArr == null || objArr.length <= 0) {
            return;
        }
        int i3 = (i + i2) - 1;
        int i4 = (i2 >>> 1) + i;
        while (i < i4) {
            Object obj = objArr[i];
            objArr[i] = objArr[i3];
            objArr[i3] = obj;
            i++;
            i3--;
        }
    }

    public static void saveToFile(byte[] bArr, String str) throws Throwable {
        File file = new File(str);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(bArr);
                try {
                    fileOutputStream2.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void setDefaultRFC2253StringEscaping(boolean z) {
        c = z;
    }

    public static void setEncoding(String str) {
        f = str;
    }

    public static byte[] toASCIIBytes(String str) {
        if (f == null) {
            a();
        }
        try {
            return str.getBytes(f);
        } catch (UnsupportedEncodingException e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("ASCII encoding not supported?!: ");
            stringBuffer.append(e2.toString());
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    public static String toASCIIString(byte[] bArr) {
        return toASCIIString(bArr, 0, bArr.length);
    }

    public static String toASCIIString(byte[] bArr, int i, int i2) {
        if (f == null) {
            a();
        }
        try {
            return new String(bArr, i, i2, f);
        } catch (UnsupportedEncodingException e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("ASCII encoding not supported?!: ");
            stringBuffer.append(e2.toString());
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    public static Object[] toArray(Vector vector) {
        Object[] objArr = new Object[vector.size()];
        vector.copyInto(objArr);
        return objArr;
    }

    public static Object[] toArray(Vector vector, Class cls) {
        Object[] objArr = (Object[]) Array.newInstance((Class<?>) cls, vector.size());
        vector.copyInto(objArr);
        return objArr;
    }

    public static String toBase64String(byte[] bArr) {
        Objects.requireNonNull(bArr, "Data input must not be null!");
        return toASCIIString(Base64Encode(bArr));
    }

    public static boolean[] toBooleanArray(String str) {
        int length = str.length();
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = str.charAt(i) == '1';
        }
        return zArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0013 A[PHI: r1
  0x0013: PHI (r1v3 int) = (r1v2 int), (r1v1 int) binds: [B:15:0x001d, B:10:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int toByte(char r3, int r4) {
        /*
            r0 = -1
            r1 = 48
            if (r3 < r1) goto Lb
            r2 = 57
            if (r3 > r2) goto Lb
            int r3 = r3 - r1
            goto L21
        Lb:
            r1 = 65
            if (r3 < r1) goto L17
            r2 = 90
            if (r3 > r2) goto L17
        L13:
            int r3 = r3 - r1
            int r3 = r3 + 10
            goto L21
        L17:
            r1 = 97
            if (r3 < r1) goto L20
            r2 = 122(0x7a, float:1.71E-43)
            if (r3 > r2) goto L20
            goto L13
        L20:
            r3 = -1
        L21:
            if (r3 < 0) goto L27
            if (r3 < r4) goto L26
            goto L27
        L26:
            return r3
        L27:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.utils.Util.toByte(char, int):int");
    }

    public static byte[] toByteArray(int i) {
        return new byte[]{(byte) (i >>> 24), (byte) (i >>> 16), (byte) (i >>> 8), (byte) i};
    }

    public static byte[] toByteArray(long j) {
        return new byte[]{(byte) (j >>> 56), (byte) (j >>> 48), (byte) (j >>> 40), (byte) (j >>> 32), (byte) (j >>> 24), (byte) (j >>> 16), (byte) (j >>> 8), (byte) j};
    }

    public static byte[] toByteArray(String str) {
        int length = str.length();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            if (i == -1) {
                i = toByte(str.charAt(i3), 16);
            } else {
                i2 = toByte(str.charAt(i3), 16);
            }
            if (i != -1 && i2 != -1) {
                byteArrayOutputStream.write((i << 4) | i2);
                i = -1;
                i2 = -1;
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] toPemArray(CertificateRequest certificateRequest) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        byte[] lineBreak = Base64OutputStream.getLineBreak();
        try {
            byteArrayOutputStream.write(toASCIIBytes("-----BEGIN NEW CERTIFICATE REQUEST-----"));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(Base64Encode(certificateRequest.toByteArray()));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(toASCIIBytes("-----END NEW CERTIFICATE REQUEST-----"));
            byteArrayOutputStream.write(lineBreak);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            return null;
        }
    }

    public static byte[] toPemArray(AttributeCertificate attributeCertificate) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        byte[] lineBreak = Base64OutputStream.getLineBreak();
        try {
            byteArrayOutputStream.write(toASCIIBytes("-----BEGIN ATTRIBUTE CERTIFICATE-----"));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(Base64Encode(attributeCertificate.getEncoded()));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(toASCIIBytes("-----END ATTRIBUTE CERTIFICATE-----"));
            byteArrayOutputStream.write(lineBreak);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | CertificateEncodingException unused) {
            return null;
        }
    }

    public static byte[] toPemArray(PrivateKey privateKey) {
        String str;
        String str2;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        byte[] lineBreak = Base64OutputStream.getLineBreak();
        try {
            if (privateKey instanceof EncryptedPrivateKeyInfo) {
                str = "-----BEGIN ENCRYPTED PRIVATE KEY-----";
                str2 = "-----END ENCRYPTED PRIVATE KEY-----";
            } else {
                str = "-----BEGIN PRIVATE KEY-----";
                str2 = "-----END PRIVATE KEY-----";
            }
            byteArrayOutputStream.write(toASCIIBytes(str));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(Base64Encode(privateKey.getEncoded()));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(toASCIIBytes(str2));
            byteArrayOutputStream.write(lineBreak);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            return null;
        }
    }

    public static byte[] toPemArray(Certificate certificate) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        byte[] lineBreak = Base64OutputStream.getLineBreak();
        try {
            byteArrayOutputStream.write(toASCIIBytes("-----BEGIN CERTIFICATE-----"));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(Base64Encode(certificate.getEncoded()));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(toASCIIBytes("-----END CERTIFICATE-----"));
            byteArrayOutputStream.write(lineBreak);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | CertificateEncodingException unused) {
            return null;
        }
    }

    public static byte[] toPemArray(java.security.cert.X509CRL x509crl) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        byte[] lineBreak = Base64OutputStream.getLineBreak();
        try {
            byteArrayOutputStream.write(toASCIIBytes("-----BEGIN X509 CRL-----"));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(Base64Encode(x509crl.getEncoded()));
            byteArrayOutputStream.write(lineBreak);
            byteArrayOutputStream.write(toASCIIBytes("-----END X509 CRL-----"));
            byteArrayOutputStream.write(lineBreak);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | CRLException unused) {
            return null;
        }
    }

    public static byte[] toPemArray(byte[] bArr, String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("-----BEGIN ");
            stringBuffer.append(str);
            stringBuffer.append("-----");
            String string = stringBuffer.toString();
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("-----END ");
            stringBuffer2.append(str);
            stringBuffer2.append("-----");
            PemOutputStream pemOutputStream = new PemOutputStream(byteArrayOutputStream, string, stringBuffer2.toString());
            pemOutputStream.write(bArr);
            pemOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            return null;
        }
    }

    public static String toPemString(CertificateRequest certificateRequest) {
        byte[] pemArray = toPemArray(certificateRequest);
        if (pemArray != null) {
            return toASCIIString(pemArray);
        }
        return null;
    }

    public static String toPemString(AttributeCertificate attributeCertificate) {
        byte[] pemArray = toPemArray(attributeCertificate);
        if (pemArray != null) {
            return toASCIIString(pemArray);
        }
        return null;
    }

    public static String toPemString(PrivateKey privateKey) {
        byte[] pemArray = toPemArray(privateKey);
        if (pemArray != null) {
            return toASCIIString(pemArray);
        }
        return null;
    }

    public static String toPemString(Certificate certificate) {
        byte[] pemArray = toPemArray(certificate);
        if (pemArray != null) {
            return toASCIIString(pemArray);
        }
        return null;
    }

    public static String toPemString(java.security.cert.X509CRL x509crl) {
        byte[] pemArray = toPemArray(x509crl);
        if (pemArray != null) {
            return toASCIIString(pemArray);
        }
        return null;
    }

    public static String toPemString(byte[] bArr, String str) {
        byte[] pemArray = toPemArray(bArr, str);
        if (pemArray != null) {
            return toASCIIString(pemArray);
        }
        return null;
    }

    public static String toString(byte b2) {
        StringBuffer stringBuffer = new StringBuffer(2);
        int i = (b2 & marshall_t.marshall_packet_option_rfu_mask) >> 4;
        int i2 = b2 & 15;
        stringBuffer.append(new Character((char) (i > 9 ? (i + 65) - 10 : i + 48)));
        stringBuffer.append(new Character((char) (i2 > 9 ? (i2 + 65) - 10 : i2 + 48)));
        return stringBuffer.toString();
    }

    public static String toString(int i) {
        return toString(i, ":");
    }

    public static String toString(int i, String str) {
        return toString(new byte[]{(byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i}, str);
    }

    public static String toString(long j) {
        return toString(j, ":");
    }

    public static String toString(long j, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(toString((int) (j >> 32), str));
        stringBuffer.append(str);
        stringBuffer.append(toString((int) j, str));
        return stringBuffer.toString();
    }

    public static String toString(byte[] bArr) {
        return toString(bArr, 0, bArr.length);
    }

    public static String toString(byte[] bArr, int i, int i2) {
        return toString(bArr, i, i2, ":");
    }

    public static String toString(byte[] bArr, int i, int i2, String str) {
        boolean z = true;
        if (bArr == null && i == -1 && i2 == 1) {
            b();
            return null;
        }
        if (bArr.length < i + i2) {
            i2 = bArr.length - i;
        }
        StringBuffer stringBuffer = new StringBuffer(i2 << 1);
        int i3 = i2 + i;
        while (i < i3) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(str);
            }
            stringBuffer.append(toString(bArr[i]));
            i++;
        }
        return stringBuffer.toString();
    }

    public static String toString(byte[] bArr, String str) {
        return toString(bArr, 0, bArr.length, str);
    }

    public static String toString(int[] iArr) {
        return toString(iArr, 0, iArr.length, ":");
    }

    public static String toString(int[] iArr, int i, int i2) {
        return toString(iArr, i, i2, ":");
    }

    public static String toString(int[] iArr, int i, int i2, String str) {
        boolean z = true;
        if (iArr == null && i == -1 && i2 == 1) {
            b();
            return null;
        }
        if (iArr.length < i + i2) {
            i2 = iArr.length - i;
        }
        StringBuffer stringBuffer = new StringBuffer(i2 << 1);
        int i3 = i2 + i;
        while (i < i3) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(str);
            }
            stringBuffer.append(toString(iArr[i], str));
            i++;
        }
        return stringBuffer.toString();
    }

    public static String toString(int[] iArr, String str) {
        return toString(iArr, 0, iArr.length, str);
    }

    public static String toString(long[] jArr) {
        return toString(jArr, 0, jArr.length, ":");
    }

    public static String toString(long[] jArr, int i, int i2) {
        return toString(jArr, i, i2, ":");
    }

    public static String toString(long[] jArr, int i, int i2, String str) {
        boolean z = true;
        if (jArr == null && i == -1 && i2 == 1) {
            b();
            return null;
        }
        if (jArr.length < i + i2) {
            i2 = jArr.length - i;
        }
        StringBuffer stringBuffer = new StringBuffer(i2 << 1);
        int i3 = i2 + i;
        while (i < i3) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(str);
            }
            stringBuffer.append(toString(jArr[i], str));
            i++;
        }
        return stringBuffer.toString();
    }

    public static String toString(long[] jArr, String str) {
        return toString(jArr, 0, jArr.length, str);
    }

    public static void waitKey() {
        try {
            System.out.println("Hit the <RETURN> key.");
            do {
                System.in.read();
            } while (System.in.available() > 0);
        } catch (IOException unused) {
        }
    }

    public void writePkiPath(X509Certificate[] x509CertificateArr, boolean z, OutputStream outputStream) throws IOException, CertificateException {
        outputStream.write(encodeAsPkiPath(x509CertificateArr, z));
    }
}

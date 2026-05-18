package iaik.security.ssl;

import com.felhr.usbserial.UsbSerialDebugger;
import com.google.android.gms.stats.CodePackage;
import iaik.security.ssl.SupportedEllipticCurves;
import iaik.security.ssl.SupportedPointFormats;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.spec.DHPublicKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class SecurityProvider {
    public static final String ALG_CIPHER_3DES = "DESede/CBC/NoPadding";
    public static final String ALG_CIPHER_AES = "AES/CBC/NoPadding";
    public static final String ALG_CIPHER_AES_GCM = "AES/GCM/NoPadding";
    public static final String ALG_CIPHER_AES_PKCS5 = "AES/CBC/PKCS5Padding";
    public static final String ALG_CIPHER_CAMELLIA = "Camellia/CBC/NoPadding";
    public static final String ALG_CIPHER_CAMELLIA_GCM = "Camellia/GCM/NoPadding";
    public static final String ALG_CIPHER_DES = "DES/CBC/NoPadding";
    public static final String ALG_CIPHER_IDEA = "IDEA/CBC/NoPadding";
    public static final String ALG_CIPHER_RC2 = "RC2/CBC/NoPadding";
    public static final String ALG_CIPHER_RC4 = "RC4/ECB/NoPadding";
    public static final String ALG_CIPHER_RSA = "RSA/ECB/PKCS1Padding";
    public static final String ALG_CIPHER_RSA_DECRYPT = "RSA/ECB/PKCS1Padding/Decrypt";
    public static final String ALG_CIPHER_RSA_ENCRYPT = "RSA/ECB/PKCS1Padding/Encrypt";
    public static final String ALG_CIPHER_RSA_ENCRYPT_SSL2 = "RSA/ECB/PKCS1PaddingSSL2";
    public static final String ALG_CIPHER_RSA_SIGN = "RSA/ECB/PKCS1Padding/Sign";
    public static final String ALG_CIPHER_RSA_VERIFY = "RSA/ECB/PKCS1Padding/Verify";
    public static final String ALG_DIGEST_MD5 = "MD5";
    public static final String ALG_DIGEST_SHA = "SHA";
    public static final String ALG_DIGEST_SHA1 = "SHA";
    public static final String ALG_DIGEST_SHA224 = "SHA224";
    public static final String ALG_DIGEST_SHA256 = "SHA256";
    public static final String ALG_DIGEST_SHA384 = "SHA384";
    public static final String ALG_DIGEST_SHA512 = "SHA512";
    public static final String ALG_HMAC_MD5 = "HmacMD5";
    public static final String ALG_HMAC_SHA = "HmacSHA1";
    public static final String ALG_HMAC_SHA256 = "HmacSHA256";
    public static final String ALG_HMAC_SHA384 = "HmacSHA384";
    public static final String ALG_HMAC_SHA512 = "HmacSHA512";
    public static final String ALG_KEYEX_DH = "DH";
    public static final String ALG_KEYEX_DSA = "DSA";
    public static final String ALG_KEYEX_DSA_CLIENT = "DSAClient";
    public static final String ALG_KEYEX_ECDH = "ECDH";
    public static final String ALG_KEYEX_ECDSA = "ECDSA";
    public static final String ALG_KEYEX_ECDSA_CLIENT = "ECDSAClient";
    public static final String ALG_KEYEX_RSA = "RSA";
    public static final String ALG_KEYGEN_AES = "AES";
    public static final String ALG_KEYGEN_HMAC_SHA = "HmacSHA1";
    public static final String ALG_KEYGEN_HMAC_SHA256 = "HmacSHA256";
    public static final String ALG_KEYGEN_PBKDF2 = "PBKDF2";
    public static final String ALG_KEYPAIR_RSA = "RSA";
    public static final String ALG_SIGNATURE_MD5RSA = "MD5withRSA";
    public static final String ALG_SIGNATURE_RAWDSA = "RawDSA";
    public static final String ALG_SIGNATURE_RAWECDSA = "RawECDSA";
    public static final String ALG_SIGNATURE_SHA1ECDSA = "SHA1withECDSA";
    public static final String ALG_SIGNATURE_SHA1RSA = "SHA1withRSA";
    public static final String ALG_SIGNATURE_SHA224ECDSA = "SHA224withECDSA";
    public static final String ALG_SIGNATURE_SHA224RSA = "SHA224withRSA";
    public static final String ALG_SIGNATURE_SHA256ECDSA = "SHA256withECDSA";
    public static final String ALG_SIGNATURE_SHA256RSA = "SHA256withRSA";
    public static final String ALG_SIGNATURE_SHA384ECDSA = "SHA384withECDSA";
    public static final String ALG_SIGNATURE_SHA384RSA = "SHA384withRSA";
    public static final String ALG_SIGNATURE_SHA512ECDSA = "SHA512withECDSA";
    public static final String ALG_SIGNATURE_SHA512RSA = "SHA512withRSA";
    public static final String ALG_SIGNATURE_SHADSA = "SHA1withDSA";
    public static final String ALG_SIGNATURE_SHAECDSA = "SHA1withECDSA";
    public static final int CIPHER_DECRYPT = 2;
    public static final int CIPHER_ENCRYPT = 1;
    public static final int CIPHER_NONE = 0;
    protected static final String CONFIGURATION_PROPERTIES = "iaik/security/ssl/SecurityProvider.properties";
    public static final int KEYAGREEMENT_INIT = 1;
    public static final int KEYAGREEMENT_NONE = 0;
    public static final int SIGNATURE_NONE = 0;
    public static final int SIGNATURE_SIGN = 1;
    public static final int SIGNATURE_VERIFY = 2;
    protected static Properties configuration_;
    static Class f;
    private static SecurityProvider g;
    private d i;
    protected String providerName;
    private static final BigInteger a = BigInteger.valueOf(2);
    private static HashMap h = new HashMap();
    static int b = 0;
    static int c = 1;
    static int d = 2;
    static int e = 3;

    protected boolean checkCreatedRSAServerKeyExchangeSignature() {
        return false;
    }

    public boolean checkExtendedKeyUsage(X509Certificate x509Certificate, boolean z) throws CertificateException {
        return true;
    }

    public boolean checkIfOnSameCurve(PublicKey publicKey, PublicKey publicKey2) {
        return false;
    }

    public boolean checkKeyECPointFormat(PublicKey publicKey, SupportedPointFormats supportedPointFormats) {
        return false;
    }

    public boolean checkKeyEllipticCurve(PublicKey publicKey, SupportedEllipticCurves supportedEllipticCurves) {
        return supportedEllipticCurves == null;
    }

    public byte[] createCertStatusRequest(int i) throws Exception {
        return null;
    }

    public byte[] createPkiPath(X509Certificate[] x509CertificateArr) throws Exception {
        return null;
    }

    public SecretKey deriveKey(String str, char[] cArr, byte[] bArr, int i, int i2, String str2, SecureRandom secureRandom) throws Exception {
        return null;
    }

    public SupportedEllipticCurves.NamedCurve getCurve(PublicKey publicKey) {
        return null;
    }

    public String getCurveName(PublicKey publicKey) {
        return null;
    }

    public SupportedPointFormats.ECPointFormat getECPointFormat(PublicKey publicKey) {
        return null;
    }

    protected byte[] getEncodedPrincipal(Principal principal) {
        return null;
    }

    protected Principal getPrincipal(byte[] bArr) throws Exception {
        return null;
    }

    public boolean isNamedCurveSupported(SupportedEllipticCurves.NamedCurve namedCurve) {
        return false;
    }

    public boolean isPointFormatSupported(SupportedPointFormats.ECPointFormat eCPointFormat) {
        return false;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    static {
        Utils.a();
        try {
            Properties properties = new Properties();
            configuration_ = properties;
            Class clsClass$ = f;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.security.ssl.SecurityProvider");
                f = clsClass$;
            }
            properties.load(clsClass$.getClassLoader().getResourceAsStream(CONFIGURATION_PROPERTIES));
        } catch (Throwable unused) {
            configuration_ = null;
        }
    }

    private static String a(String str) throws NoSuchAlgorithmException {
        int iIndexOf = str.indexOf("/");
        return iIndexOf == -1 ? str : str.substring(0, iIndexOf);
    }

    public static SecurityProvider getSecurityProvider() {
        if (g == null) {
            Properties properties = configuration_;
            if (properties != null) {
                try {
                    g = (SecurityProvider) Class.forName(properties.getProperty("class")).newInstance();
                } catch (Throwable unused) {
                }
            }
            if (g == null) {
                try {
                    g = (SecurityProvider) Class.forName("iaik.security.ssl.IaikProvider").newInstance();
                } catch (Throwable unused2) {
                }
            }
            if (g == null) {
                g = new SecurityProvider();
            }
        }
        return g;
    }

    public static void setSecurityProvider(SecurityProvider securityProvider) {
        g = securityProvider;
        h.clear();
    }

    static boolean a(String str, int i, CipherSuite cipherSuite) {
        String string;
        boolean zIsImplemented;
        Boolean bool;
        if (i == c) {
            StringBuffer stringBuffer = new StringBuffer(String.valueOf(str));
            stringBuffer.append(cipherSuite.getExpandedKeyLength());
            string = stringBuffer.toString();
        } else {
            string = str;
        }
        Boolean bool2 = (Boolean) h.get(string);
        if (bool2 == null) {
            SecurityProvider securityProvider = getSecurityProvider();
            if (i == e) {
                zIsImplemented = securityProvider.isImplementedSignatureAlgorithm(string);
            } else {
                zIsImplemented = securityProvider.isImplemented(str, cipherSuite);
            }
            if (zIsImplemented) {
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            bool2 = bool;
            h.put(string, bool2);
        }
        return bool2.booleanValue();
    }

    public SecurityProvider() {
        this(null);
    }

    public SecurityProvider(String str) {
        this.providerName = str;
        this.i = new d();
    }

    protected boolean isImplemented(String str) {
        return isImplemented(str, null);
    }

    protected boolean isImplemented(String str, CipherSuite cipherSuite) {
        AlgorithmParameterSpec ivParameterSpec;
        getSecurityProvider();
        try {
            Object cipher = null;
            if (str.equals("RSA")) {
                cipher = g.getCipher(ALG_CIPHER_RSA_SIGN, 0, null, null, null);
            } else if (str.equals(ALG_KEYEX_DSA_CLIENT)) {
                cipher = g.getSignature(ALG_SIGNATURE_RAWDSA, 0, null, null);
            } else if (str.equals("DSA")) {
                cipher = g.getSignature(ALG_SIGNATURE_SHADSA, 0, null, null);
            } else if (str.equals(ALG_KEYEX_ECDSA_CLIENT)) {
                cipher = g.getSignature(ALG_SIGNATURE_RAWECDSA, 0, null, null);
            } else if (str.equals("ECDSA")) {
                cipher = g.getSignature("SHA1withECDSA", 0, null, null);
            } else if (str.equals("DH")) {
                DHParameterSpec dHParameterSpecO = SSLServerContext.o();
                cipher = g.getDHPrivateKey(new BigInteger(160, new Random()), dHParameterSpecO.getP(), dHParameterSpecO.getG());
            } else if (str.equals(ALG_KEYEX_ECDH)) {
                if (g.isPointFormatSupported(SupportedPointFormats.PF_UNCOMPRESSED)) {
                    cipher = g.getKeyAgreement(ALG_KEYEX_ECDH, 0, null, null, null);
                }
            } else {
                int expandedKeyLength = cipherSuite.getExpandedKeyLength();
                int iVSize = cipherSuite.getIVSize();
                if (expandedKeyLength > 0) {
                    byte[] bArr = new byte[expandedKeyLength];
                    if (iVSize <= 0) {
                        ivParameterSpec = null;
                    } else if (str.equals(ALG_CIPHER_RC2)) {
                        ivParameterSpec = new RC2ParameterSpec(expandedKeyLength, new byte[iVSize]);
                    } else {
                        ivParameterSpec = new IvParameterSpec(new byte[iVSize]);
                    }
                    Object cipher2 = g.getCipher(str, 1, new SecretKeySpec(bArr, a(str)), ivParameterSpec, null);
                    if (str.indexOf(CodePackage.GCM) != -1) {
                        try {
                            g.aeadEncrypt(null, null, null, -1, -1, null, -1, null, null, -1, null);
                        } catch (NoSuchAlgorithmException unused) {
                        } catch (Throwable unused2) {
                        }
                        cipher = cipher2;
                    } else {
                        cipher = cipher2;
                    }
                } else {
                    cipher = g.getCipher(str, 0, null, null, null);
                }
            }
            return cipher != null;
        } catch (Exception unused3) {
            return false;
        }
    }

    protected boolean isImplementedSignatureAlgorithm(String str) {
        getSecurityProvider();
        try {
            return g.getSignature(str, 0, null, null) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    protected DHPublicKey getDHPublicKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) throws Exception {
        DHPublicKeySpec dHPublicKeySpec = new DHPublicKeySpec(bigInteger, bigInteger2, bigInteger3);
        String str = this.providerName;
        return (DHPublicKey) (str == null ? KeyFactory.getInstance("DH") : KeyFactory.getInstance("DH", str)).generatePublic(dHPublicKeySpec);
    }

    protected void validateDHPublicKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) throws InvalidKeyException {
        BigInteger bigIntegerSubtract = bigInteger2.subtract(BigInteger.ONE);
        BigInteger bigInteger4 = a;
        if (bigInteger.compareTo(bigInteger4) < 0 || bigInteger.compareTo(bigIntegerSubtract) > 0) {
            throw new InvalidKeyException("DH public key value out of range!");
        }
        if (bigInteger3 != null) {
            BigInteger bigIntegerSubtract2 = bigInteger2.subtract(bigInteger4);
            if (bigInteger3.compareTo(bigInteger4) < 0 || bigInteger3.compareTo(bigIntegerSubtract2) > 0) {
                throw new InvalidKeyException("DH base generator out of range!");
            }
        }
    }

    protected DHPrivateKey getDHPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) throws Exception {
        DHPrivateKeySpec dHPrivateKeySpec = new DHPrivateKeySpec(bigInteger, bigInteger2, bigInteger3);
        String str = this.providerName;
        return (DHPrivateKey) (str == null ? KeyFactory.getInstance("DH") : KeyFactory.getInstance("DH", str)).generatePrivate(dHPrivateKeySpec);
    }

    protected RSAPublicKey getRSAPublicKey(BigInteger bigInteger, BigInteger bigInteger2) throws Exception {
        RSAPublicKeySpec rSAPublicKeySpec = new RSAPublicKeySpec(bigInteger, bigInteger2);
        String str = this.providerName;
        return (RSAPublicKey) (str == null ? KeyFactory.getInstance("RSA") : KeyFactory.getInstance("RSA", str)).generatePublic(rSAPublicKeySpec);
    }

    protected X509Certificate getX509Certificate(byte[] bArr) throws Exception {
        return getX509Certificate(new ByteArrayInputStream(bArr));
    }

    public X509Certificate getX509Certificate(InputStream inputStream) throws Exception {
        CertificateFactory certificateFactory;
        String str = this.providerName;
        if (str == null) {
            certificateFactory = CertificateFactory.getInstance("X.509");
        } else {
            certificateFactory = CertificateFactory.getInstance("X.509", str);
        }
        return (X509Certificate) certificateFactory.generateCertificate(inputStream);
    }

    public X509Certificate[] getX509Certificates(byte[] bArr) throws Exception {
        String str = this.providerName;
        Object[] array = (str == null ? CertificateFactory.getInstance("X.509") : CertificateFactory.getInstance("X.509", str)).generateCertificates(new ByteArrayInputStream(bArr)).toArray();
        int length = array.length;
        X509Certificate[] x509CertificateArr = new X509Certificate[length];
        for (int i = 0; i < length; i++) {
            x509CertificateArr[i] = (X509Certificate) array[(length - i) - 1];
        }
        return x509CertificateArr;
    }

    public KeyStore loadKeyStore(String str, char[] cArr, String str2, String str3) throws Exception {
        Objects.requireNonNull(str, "keyStoreFile must not be null!");
        Objects.requireNonNull(cArr, "keyStorePassword must not be null!");
        Objects.requireNonNull(str3, "keyStoreProvider must not be null!");
        Objects.requireNonNull(str2, "keyStoreType must not be null!");
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                KeyStore keyStore = KeyStore.getInstance(str2, str3);
                keyStore.load(fileInputStream2, cArr);
                try {
                    fileInputStream2.close();
                } catch (IOException unused) {
                }
                return keyStore;
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

    protected MessageDigest getMessageDigest(String str) throws Exception {
        if (str.equals("SHA")) {
            str = "SHA-1";
        } else if (str.startsWith("SHA") && str.indexOf(3) != 45) {
            StringBuffer stringBuffer = new StringBuffer("SHA-");
            stringBuffer.append(str.substring(3, str.length()));
            str = stringBuffer.toString();
        }
        String str2 = this.providerName;
        return str2 == null ? MessageDigest.getInstance(str) : MessageDigest.getInstance(str, str2);
    }

    protected Mac getMac(String str, Key key) throws Exception {
        String str2 = this.providerName;
        Mac mac = str2 == null ? Mac.getInstance(str) : Mac.getInstance(str, str2);
        if (key != null) {
            mac.init(key);
        }
        return mac;
    }

    protected Signature getSignature(String str, int i, Key key, SecureRandom secureRandom) throws Exception {
        String str2 = this.providerName;
        Signature signature = str2 == null ? Signature.getInstance(str) : Signature.getInstance(str, str2);
        if (i == 1) {
            if (secureRandom == null) {
                signature.initSign((PrivateKey) key);
            } else {
                signature.initSign((PrivateKey) key, secureRandom);
            }
        } else if (i == 2) {
            signature.initVerify((PublicKey) key);
        }
        return signature;
    }

    protected Cipher getCipher(String str, int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws Exception {
        if (str.startsWith(ALG_CIPHER_RSA)) {
            str = ALG_CIPHER_RSA;
        }
        String str2 = this.providerName;
        Cipher cipher = str2 == null ? Cipher.getInstance(str) : Cipher.getInstance(str, str2);
        if (i != 0) {
            cipher.init(i != 1 ? 2 : 1, key, algorithmParameterSpec, secureRandom);
        }
        return cipher;
    }

    protected int aeadEncrypt(Cipher cipher, SecretKey secretKey, byte[] bArr, int i, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4, SecureRandom secureRandom) throws Exception {
        throw new NoSuchAlgorithmException("Cannot encrypt data. No AEAD implementation available!");
    }

    protected int aeadDecrypt(Cipher cipher, SecretKey secretKey, byte[] bArr, int i, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4) throws Exception {
        throw new NoSuchAlgorithmException("Cannot encrypt data. No AEAD implementation available!");
    }

    protected byte[] calculateRawSignature(String str, byte[] bArr, PrivateKey privateKey, SecureRandom secureRandom) throws Exception {
        return getCipher(str, 1, privateKey, null, secureRandom).doFinal(bArr);
    }

    protected boolean verifyRawSignature(String str, byte[] bArr, byte[] bArr2, PublicKey publicKey) throws Exception {
        return Utils.equalsBlock(bArr, getCipher(str, 2, publicKey, null, null).doFinal(bArr2));
    }

    protected KeyPairGenerator getKeyPairGenerator(String str) throws Exception {
        String str2 = this.providerName;
        return str2 == null ? KeyPairGenerator.getInstance(str) : KeyPairGenerator.getInstance(str, str2);
    }

    protected KeyGenerator getKeyGenerator(String str) throws Exception {
        String str2 = this.providerName;
        return str2 == null ? KeyGenerator.getInstance(str) : KeyGenerator.getInstance(str, str2);
    }

    public AlgorithmParameterGenerator getAlgorithmParameterGenerator(String str) throws Exception {
        String str2 = this.providerName;
        return str2 == null ? AlgorithmParameterGenerator.getInstance(str) : AlgorithmParameterGenerator.getInstance(str, str2);
    }

    protected SecureRandom getSecureRandom() {
        return new SecureRandom();
    }

    public byte[] generateMasterSecret(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) throws Exception {
        if (i >= 769) {
            return Utils.a(i, bArr, y.c, Utils.a(bArr2, bArr3), 48, null);
        }
        byte[] bArr4 = new byte[48];
        MessageDigest messageDigest = getMessageDigest("SHA");
        MessageDigest messageDigest2 = getMessageDigest(ALG_DIGEST_MD5);
        int i2 = 0;
        while (i2 < 3) {
            messageDigest2.update(bArr);
            int i3 = i2 + 1;
            messageDigest.update(Utils.a(i3));
            messageDigest.update(bArr);
            messageDigest.update(bArr2);
            messageDigest.update(bArr3);
            messageDigest2.update(messageDigest.digest());
            System.arraycopy(messageDigest2.digest(), 0, bArr4, i2 * 16, 16);
            i2 = i3;
        }
        return bArr4;
    }

    public byte[] generateMasterSecret(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, String str) throws Exception {
        if (i < 771) {
            return generateMasterSecret(bArr, bArr2, bArr3, i);
        }
        return Utils.a(i, bArr, y.c, Utils.a(bArr2, bArr3), 48, str);
    }

    public byte[] generateExtendedMasterSecret(byte[] bArr, byte[] bArr2, int i, String str) throws Exception {
        if (i < 771) {
            return Utils.a(i, bArr, y.d, bArr2, 48, null);
        }
        return Utils.a(i, bArr, y.d, bArr2, 48, str);
    }

    protected String[] getTLSServerName(X509Certificate x509Certificate) {
        String name;
        int iIndexOf;
        String[] strArr = new String[0];
        Principal subjectDN = x509Certificate.getSubjectDN();
        if (subjectDN == null || (name = subjectDN.getName()) == null || (iIndexOf = name.indexOf("CN=")) < 0) {
            return strArr;
        }
        String strSubstring = name.substring(iIndexOf + 3);
        int iIndexOf2 = strSubstring.indexOf(",");
        if (iIndexOf2 > 0) {
            strSubstring = strSubstring.substring(0, iIndexOf2);
        }
        return new String[]{strSubstring};
    }

    public ServerName[] getTLSServerName(int i, X509Certificate x509Certificate) {
        Vector vector = new Vector();
        String[] tLSServerName = getTLSServerName(x509Certificate);
        if (tLSServerName != null) {
            for (String str : tLSServerName) {
                try {
                    vector.addElement(new ServerName(i, str, null));
                } catch (UnsupportedEncodingException unused) {
                }
            }
        }
        ServerName[] serverNameArr = new ServerName[vector.size()];
        vector.copyInto(serverNameArr);
        return serverNameArr;
    }

    public ServerName getTLSServerName(int i, byte[] bArr) {
        try {
            return new ServerName(i, null, bArr);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public ServerName getTLSServerName(int i, String str) throws Exception {
        return new ServerName(i, str, null);
    }

    public byte[] calculateTrustedAuthorityIdentifier(int i, X509Certificate x509Certificate) throws Exception {
        if (x509Certificate == null) {
            throw new IllegalArgumentException("certificate must not be null!");
        }
        if (i == 0) {
            return new byte[0];
        }
        if (i == 1) {
            PublicKey publicKey = x509Certificate.getPublicKey();
            if (!(publicKey instanceof RSAPublicKey)) {
                return null;
            }
            return getMessageDigest("SHA").digest(((RSAPublicKey) publicKey).getModulus().toByteArray());
        }
        if (i == 2) {
            return getEncodedPrincipal(x509Certificate.getSubjectDN());
        }
        if (i == 3) {
            try {
                return getMessageDigest("SHA").digest(x509Certificate.getEncoded());
            } catch (Exception e2) {
                StringBuffer stringBuffer = new StringBuffer("Cannot calculate cert_sha1_hash identifier: ");
                stringBuffer.append(e2.toString());
                throw new Exception(stringBuffer.toString());
            }
        }
        StringBuffer stringBuffer2 = new StringBuffer("identifier type (");
        stringBuffer2.append(i);
        stringBuffer2.append(") out of scope! ");
        stringBuffer2.append("Must be between 0 and 3!");
        throw new IllegalArgumentException(stringBuffer2.toString());
    }

    public byte[] encodeURL(String str) throws Exception {
        byte[] bytes;
        Objects.requireNonNull(str, "Cannot encode NULL URL!");
        try {
            try {
                bytes = str.getBytes("UTF8");
            } catch (UnsupportedEncodingException unused) {
                bytes = str.getBytes(UsbSerialDebugger.ENCODING);
            }
            return bytes;
        } catch (Throwable th) {
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 encode url: ");
            stringBuffer.append(th.toString());
            throw new Exception(stringBuffer.toString());
        }
    }

    public String decodeURL(byte[] bArr) throws Exception {
        Objects.requireNonNull(bArr, "Cannot decode NULL URL!");
        try {
            try {
                return new String(bArr, "UTF8");
            } catch (UnsupportedEncodingException unused) {
                return new String(bArr, UsbSerialDebugger.ENCODING);
            }
        } catch (Throwable th) {
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 decode url: ");
            stringBuffer.append(th.toString());
            throw new Exception(stringBuffer.toString());
        }
    }

    public int getKeyLength(PublicKey publicKey) {
        if (publicKey instanceof RSAPublicKey) {
            return ((RSAPublicKey) publicKey).getModulus().bitLength();
        }
        if (publicKey instanceof DHPublicKey) {
            return ((DHPublicKey) publicKey).getParams().getP().bitLength();
        }
        if (publicKey instanceof DSAPublicKey) {
            return ((DSAPublicKey) publicKey).getParams().getP().bitLength();
        }
        StringBuffer stringBuffer = new StringBuffer("Unsupported public key type: ");
        stringBuffer.append(publicKey.getAlgorithm());
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public int getKeyLength(PrivateKey privateKey) {
        if (privateKey instanceof RSAPrivateKey) {
            return ((RSAPrivateKey) privateKey).getModulus().bitLength();
        }
        if (privateKey instanceof DHPrivateKey) {
            return ((DHPrivateKey) privateKey).getParams().getP().bitLength();
        }
        if (privateKey instanceof DSAPrivateKey) {
            return ((DSAPrivateKey) privateKey).getParams().getP().bitLength();
        }
        StringBuffer stringBuffer = new StringBuffer("Unsupported private key type: ");
        stringBuffer.append(privateKey.getAlgorithm());
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public int getKeyLength(Key key) {
        if (key instanceof PrivateKey) {
            return getKeyLength((PrivateKey) key);
        }
        if (key instanceof PublicKey) {
            return getKeyLength((PublicKey) key);
        }
        if (key instanceof SecretKey) {
            return key.getEncoded().length << 3;
        }
        StringBuffer stringBuffer = new StringBuffer("Unsupported key type: ");
        stringBuffer.append(key.getAlgorithm());
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public void checkKeyLength(Key key) throws Exception {
        d dVar = this.i;
        if (dVar != null) {
            dVar.a(key);
        }
    }

    public void continueIfPeerDoesNotSupportSecureRenegotiation(SSLTransport sSLTransport, boolean z) throws SSLException {
        SSLContext context = sSLTransport.getContext();
        if (context.getAllowLegacyRenegotiation()) {
            return;
        }
        if (sSLTransport.getUseClientMode()) {
            throw new SSLException("Server does not support secure renegotiation!", 2, 40, false);
        }
        if (z || !context.getUseNoRenegotiationWarnings()) {
            throw new SSLException("Client does not support secure renegotiation!", 2, 40, false);
        }
    }

    void a(d dVar) {
        Objects.requireNonNull(dVar, "AlgorithmConstraints must not be null!");
        this.i = dVar;
    }

    void a(String str, Principal principal, SSLTransport sSLTransport, StringBuffer stringBuffer) {
        String name = principal.getName();
        if (name.length() < 120) {
            StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(str));
            stringBuffer2.append(principal);
            sSLTransport.a(stringBuffer2.toString(), stringBuffer);
            return;
        }
        int length = name.length();
        int iMax = Math.max(length / 3, 20);
        StringBuffer stringBuffer3 = new StringBuffer(iMax);
        StringBuffer stringBuffer4 = new StringBuffer(iMax);
        int i = 0;
        boolean z = false;
        boolean z2 = true;
        while (true) {
            if (i >= length) {
                break;
            }
            char cCharAt = name.charAt(i);
            if (!z) {
                stringBuffer3.append(cCharAt);
                if (cCharAt == ',') {
                    z = true;
                }
            } else {
                stringBuffer4.append(cCharAt);
                if (cCharAt == ',') {
                    stringBuffer3.append((Object) stringBuffer4);
                    stringBuffer4 = new StringBuffer(iMax);
                } else if (cCharAt == '=') {
                    a(z2 ? str : null, stringBuffer3.toString(), sSLTransport, stringBuffer);
                    z = false;
                    z2 = false;
                    StringBuffer stringBuffer5 = stringBuffer4;
                    stringBuffer4 = new StringBuffer(iMax);
                    stringBuffer3 = stringBuffer5;
                }
            }
            i++;
        }
        a(z2 ? str : null, stringBuffer3.toString(), sSLTransport, stringBuffer);
    }

    private static void a(String str, String str2, SSLTransport sSLTransport, StringBuffer stringBuffer) {
        if (str != null) {
            StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(str));
            stringBuffer2.append(str2.trim());
            sSLTransport.a(stringBuffer2.toString(), stringBuffer);
        } else {
            StringBuffer stringBuffer3 = new StringBuffer("           ");
            stringBuffer3.append(str2.trim());
            sSLTransport.a(stringBuffer3.toString(), stringBuffer);
        }
    }

    public byte[] encodeECPublicKey(PublicKey publicKey, SupportedPointFormats supportedPointFormats) throws Exception {
        throw new Exception("Cannot encode EC public key. No implementation available!");
    }

    public PublicKey decodeECPublicKey(byte[] bArr, SupportedEllipticCurves.NamedCurve namedCurve, SupportedPointFormats supportedPointFormats, SupportedEllipticCurves supportedEllipticCurves) throws Exception {
        throw new Exception("Cannot decode EC public key. No implementation available!");
    }

    public PublicKey decodeECPublicKey(byte[] bArr, PrivateKey privateKey, SupportedPointFormats supportedPointFormats) throws Exception {
        throw new Exception("Cannot decode EC public key. No implementation available!");
    }

    public KeyPair generateECKeyPair(SupportedEllipticCurves supportedEllipticCurves, SupportedPointFormats supportedPointFormats) throws Exception {
        throw new Exception("Cannot generate EC key pair. No implementation available!");
    }

    public KeyPair generateECKeyPair(PublicKey publicKey) throws Exception {
        throw new Exception("Cannot generate EC key pair. No implementation available!");
    }

    public byte[] createSharedECDHSecret(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        KeyAgreement keyAgreement = getKeyAgreement(ALG_KEYEX_ECDH, 1, privateKey, null, getSecureRandom());
        keyAgreement.doPhase(publicKey, true);
        return keyAgreement.generateSecret();
    }

    public KeyAgreement getKeyAgreement(String str, int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws Exception {
        String str2 = this.providerName;
        KeyAgreement keyAgreement = str2 == null ? KeyAgreement.getInstance(str) : KeyAgreement.getInstance(str, str2);
        if (i != 0) {
            keyAgreement.init(key, algorithmParameterSpec, secureRandom);
        }
        return keyAgreement;
    }

    public boolean isBinary(PublicKey publicKey) throws Exception {
        throw new Exception("Cannot determine underlying field. No ECC implementation available!");
    }

    public SupportedEllipticCurves.NamedCurve getDefaultCurve(boolean z) {
        if (z) {
            return SupportedEllipticCurves.NC_CHAR2_SECT283K1;
        }
        return SupportedEllipticCurves.NC_PRIME_SECP256R1;
    }
}

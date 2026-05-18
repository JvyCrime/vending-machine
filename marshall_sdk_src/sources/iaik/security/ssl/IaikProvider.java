package iaik.security.ssl;

import com.google.android.gms.stats.CodePackage;
import iaik.asn1.ObjectID;
import iaik.asn1.UTF8String;
import iaik.asn1.structures.Name;
import iaik.security.cipher.GCMParameterSpec;
import iaik.security.provider.IAIK;
import iaik.security.random.SecRandom;
import iaik.security.spec.PBEKeyAndParameterSpec;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.extensions.ExtendedKeyUsage;
import iaik.x509.ocsp.ResponderID;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.spec.DHPublicKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class IaikProvider extends SecurityProvider {
    static Class a;
    private static ac g = ac.a();
    private double h;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public IaikProvider() {
        super("IAIK");
        if (Security.getProvider("IAIK") == null) {
            IAIK.addAsJDK14Provider(false);
        }
        Provider provider = Security.getProvider("IAIK");
        if (provider == null) {
            System.err.println("Could not add IAIK provider!");
            throw new RuntimeException("Could not add IAIK provider!");
        }
        double version = provider.getVersion();
        this.h = version;
        if (version < 2.5098999999999996d) {
            System.err.println("WARNING: This version of iSaSiLk should only be used with IAIK JCE");
            PrintStream printStream = System.err;
            StringBuffer stringBuffer = new StringBuffer("WARNING: version 2.51 and later! You are using IAIK JCE ");
            stringBuffer.append(this.h);
            stringBuffer.append(".");
            printStream.println(stringBuffer.toString());
        }
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected DHPublicKey getDHPublicKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) throws Exception {
        return new iaik.security.dh.DHPublicKey(new DHPublicKeySpec(bigInteger, bigInteger2, bigInteger3));
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected DHPrivateKey getDHPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        return new iaik.security.dh.DHPrivateKey(new DHPrivateKeySpec(bigInteger, bigInteger2, bigInteger3));
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected RSAPublicKey getRSAPublicKey(BigInteger bigInteger, BigInteger bigInteger2) {
        return new iaik.security.rsa.RSAPublicKey(bigInteger, bigInteger2);
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected X509Certificate getX509Certificate(byte[] bArr) throws CertificateException {
        return new iaik.x509.X509Certificate(bArr);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public X509Certificate getX509Certificate(InputStream inputStream) throws Exception {
        return new iaik.x509.X509Certificate(inputStream);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public X509Certificate[] getX509Certificates(byte[] bArr) throws Exception {
        return aa.a(bArr);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public byte[] createPkiPath(X509Certificate[] x509CertificateArr) throws Exception {
        return aa.a(x509CertificateArr);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public boolean checkExtendedKeyUsage(X509Certificate x509Certificate, boolean z) throws CertificateException {
        iaik.x509.X509Certificate x509Certificate2;
        if (x509Certificate instanceof iaik.x509.X509Certificate) {
            x509Certificate2 = (iaik.x509.X509Certificate) x509Certificate;
        } else {
            x509Certificate2 = new iaik.x509.X509Certificate(x509Certificate.getEncoded());
        }
        ExtendedKeyUsage extendedKeyUsage = null;
        try {
            extendedKeyUsage = (ExtendedKeyUsage) x509Certificate2.getExtension(ExtendedKeyUsage.oid);
        } catch (X509ExtensionInitException e) {
            if (e.isCriticalExtension()) {
                throw new CertificateException(e.toString());
            }
        }
        if (extendedKeyUsage != null && !a(extendedKeyUsage, ExtendedKeyUsage.anyExtendedKeyUsage)) {
            if (z) {
                if (!a(extendedKeyUsage, ExtendedKeyUsage.clientAuth)) {
                    return false;
                }
            } else if (!a(extendedKeyUsage, ExtendedKeyUsage.serverAuth)) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(ExtendedKeyUsage extendedKeyUsage, ObjectID objectID) {
        for (ObjectID objectID2 : extendedKeyUsage.getKeyPurposeIDs()) {
            if (objectID2.equals(objectID)) {
                return true;
            }
        }
        return false;
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected Principal getPrincipal(byte[] bArr) throws Exception {
        return new Name(bArr);
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected byte[] getEncodedPrincipal(Principal principal) {
        return ((Name) principal).getEncoded();
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected MessageDigest getMessageDigest(String str) throws Exception {
        return this.providerName == null ? MessageDigest.getInstance(str) : MessageDigest.getInstance(str, this.providerName);
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected Signature getSignature(String str, int i, Key key, SecureRandom secureRandom) throws Exception {
        if (str.equals(SecurityProvider.ALG_SIGNATURE_MD5RSA)) {
            str = "MD5WithRSA";
        }
        Signature signature = super.getSignature(str, i, key, secureRandom);
        if ((str.equals(SecurityProvider.ALG_SIGNATURE_RAWDSA) || str.equals(SecurityProvider.ALG_SIGNATURE_SHADSA)) && i == 1) {
            try {
                signature.setParameter(null, getSecureRandom());
            } catch (Throwable unused) {
            }
        }
        return signature;
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected Cipher getCipher(String str, int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws Exception {
        if (!str.equals(SecurityProvider.ALG_CIPHER_RSA_ENCRYPT_SSL2) ? str.startsWith(SecurityProvider.ALG_CIPHER_RSA) : this.h < 2.59d) {
            str = SecurityProvider.ALG_CIPHER_RSA;
        }
        Cipher cipher = g.getCipher(str, "IAIK");
        if (i != 0) {
            cipher.init(i != 1 ? 2 : 1, key, algorithmParameterSpec, secureRandom);
        }
        return cipher;
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected int aeadEncrypt(Cipher cipher, SecretKey secretKey, byte[] bArr, int i, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4, SecureRandom secureRandom) throws Exception {
        if (cipher.getAlgorithm().indexOf(CodePackage.GCM) == -1) {
            throw new Exception("Only GCM AEAD mode supported!");
        }
        cipher.init(1, secretKey, new GCMParameterSpec(bArr3, bArr4, i4), secureRandom == null ? SecurityProvider.getSecurityProvider().getSecureRandom() : secureRandom);
        int iDoFinal = cipher.doFinal(bArr, i, i2, bArr2, i3);
        AlgorithmParameters parameters = cipher.getParameters();
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.security.cipher.GCMParameterSpec");
            a = clsClass$;
        }
        byte[] mac = ((GCMParameterSpec) parameters.getParameterSpec(clsClass$)).getMac();
        System.arraycopy(mac, 0, bArr2, i3 + iDoFinal, mac.length);
        return iDoFinal + mac.length;
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected int aeadDecrypt(Cipher cipher, SecretKey secretKey, byte[] bArr, int i, int i2, byte[] bArr2, int i3, byte[] bArr3, byte[] bArr4, int i4) throws Exception {
        if (cipher.getAlgorithm().indexOf(CodePackage.GCM) == -1) {
            throw new Exception("Only GCM AEAD mode supported!");
        }
        byte[] bArr5 = new byte[i4];
        System.arraycopy(bArr, (i + i2) - i4, bArr5, 0, i4);
        cipher.init(2, secretKey, new GCMParameterSpec(bArr3, bArr4, bArr5));
        return cipher.doFinal(bArr, i, i2 - i4, bArr2, i3);
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected String[] getTLSServerName(X509Certificate x509Certificate) {
        if (!(x509Certificate instanceof iaik.x509.X509Certificate)) {
            try {
                x509Certificate = getX509Certificate(x509Certificate.getEncoded());
            } catch (Exception unused) {
                return new String[0];
            }
        }
        return aa.a((iaik.x509.X509Certificate) x509Certificate, true);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public ServerName[] getTLSServerName(int i, X509Certificate x509Certificate) {
        if (!(x509Certificate instanceof iaik.x509.X509Certificate)) {
            try {
                x509Certificate = getX509Certificate(x509Certificate.getEncoded());
            } catch (Exception unused) {
                return new ServerName[0];
            }
        }
        String[] strArrA = aa.a((iaik.x509.X509Certificate) x509Certificate, i != 0);
        Vector vector = new Vector();
        if (strArrA != null) {
            for (String str : strArrA) {
                try {
                    vector.addElement(new ServerName(i, str, null));
                } catch (UnsupportedEncodingException unused2) {
                }
            }
        }
        ServerName[] serverNameArr = new ServerName[vector.size()];
        vector.copyInto(serverNameArr);
        return serverNameArr;
    }

    @Override // iaik.security.ssl.SecurityProvider
    public byte[] calculateTrustedAuthorityIdentifier(int i, X509Certificate x509Certificate) throws Exception {
        byte[] bArrCalculateTrustedAuthorityIdentifier = super.calculateTrustedAuthorityIdentifier(i, x509Certificate);
        return (bArrCalculateTrustedAuthorityIdentifier == null && i == 1) ? aa.a(x509Certificate.getPublicKey()) : bArrCalculateTrustedAuthorityIdentifier;
    }

    @Override // iaik.security.ssl.SecurityProvider
    public byte[] createCertStatusRequest(int i) throws Exception {
        if (i != 1) {
            StringBuffer stringBuffer = new StringBuffer("Unsupported status type ");
            stringBuffer.append(i);
            throw new Exception(stringBuffer.toString());
        }
        byte[] bArr = new byte[16];
        getSecureRandom().nextBytes(bArr);
        return new OCSPStatusRequest((ResponderID[]) null, bArr).getEncoded();
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected SecureRandom getSecureRandom() {
        return SecRandom.getDefault();
    }

    @Override // iaik.security.ssl.SecurityProvider
    public SecretKey deriveKey(String str, char[] cArr, byte[] bArr, int i, int i2, String str2, SecureRandom secureRandom) throws Exception {
        byte[] uTF8EncodingFromCharArray;
        Objects.requireNonNull(str, "algorithm must not be null!");
        if (!str.toUpperCase().equals(SecurityProvider.ALG_KEYGEN_PBKDF2)) {
            StringBuffer stringBuffer = new StringBuffer("Key derivation function ");
            stringBuffer.append(str);
            stringBuffer.append(" not supported! Only PBKDF2 is used.");
            throw new NoSuchAlgorithmException(stringBuffer.toString());
        }
        Objects.requireNonNull(cArr, "password must not be null!");
        Objects.requireNonNull(bArr, "salt must not be null!");
        if (i2 <= 0) {
            throw new NullPointerException("keyLen must be positive!");
        }
        PBEKeyAndParameterSpec pBEKeyAndParameterSpec = null;
        try {
            uTF8EncodingFromCharArray = UTF8String.getUTF8EncodingFromCharArray(cArr);
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(str, "IAIK");
                PBEKeyAndParameterSpec pBEKeyAndParameterSpec2 = new PBEKeyAndParameterSpec(uTF8EncodingFromCharArray, bArr, i, i2);
                try {
                    keyGenerator.init(pBEKeyAndParameterSpec2, (SecureRandom) null);
                    SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
                    if (secretKeyGenerateKey instanceof iaik.security.cipher.SecretKey) {
                        ((iaik.security.cipher.SecretKey) secretKeyGenerateKey).setAlgorithm(str2);
                    }
                    for (int i3 = 0; i3 < uTF8EncodingFromCharArray.length; i3++) {
                        uTF8EncodingFromCharArray[i3] = 0;
                    }
                    byte[] password = pBEKeyAndParameterSpec2.getPassword();
                    for (int i4 = 0; i4 < password.length; i4++) {
                        password[i4] = 0;
                    }
                    return secretKeyGenerateKey;
                } catch (Throwable th) {
                    th = th;
                    pBEKeyAndParameterSpec = pBEKeyAndParameterSpec2;
                    if (pBEKeyAndParameterSpec != null) {
                        for (int i5 = 0; i5 < uTF8EncodingFromCharArray.length; i5++) {
                            uTF8EncodingFromCharArray[i5] = 0;
                        }
                        byte[] password2 = pBEKeyAndParameterSpec.getPassword();
                        for (int i6 = 0; i6 < password2.length; i6++) {
                            password2[i6] = 0;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            uTF8EncodingFromCharArray = null;
        }
    }

    @Override // iaik.security.ssl.SecurityProvider
    public KeyStore loadKeyStore(String str, char[] cArr, String str2, String str3) throws Exception {
        Objects.requireNonNull(str, "keyStoreFile must not be null!");
        Objects.requireNonNull(cArr, "keyStorePassword must not be null!");
        if (str2 == null) {
            str2 = "IAIKKeyStore";
        }
        if (str3 == null) {
            str3 = "IAIK";
        }
        return super.loadKeyStore(str, cArr, str2, str3);
    }

    @Override // iaik.security.ssl.SecurityProvider
    protected boolean checkCreatedRSAServerKeyExchangeSignature() {
        return this.h <= 5.25d;
    }

    @Override // iaik.security.ssl.SecurityProvider
    void a(String str, Principal principal, SSLTransport sSLTransport, StringBuffer stringBuffer) {
        if (principal instanceof Name) {
            aa.a(str, (Name) principal, sSLTransport, stringBuffer);
        } else {
            super.a(str, principal, sSLTransport, stringBuffer);
        }
    }
}

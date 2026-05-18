package iaik.security.keystore;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.UTF8String;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs8.EncryptedPrivateKeyInfo;
import iaik.pkcs.pkcs8.PrivateKeyInfo;
import iaik.security.cipher.SecretKey;
import iaik.security.random.SecRandom;
import iaik.security.spec.PBEKeyAndParameterSpec;
import iaik.security.ssl.SecurityProvider;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import iaik.x509.X509Certificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public final class IAIKKeyStore extends KeyStoreSpi {
    private int a = 32;
    private int b = 1000;
    private int c = 16;
    private SecureRandom d = SecRandom.getDefault();
    private Hashtable e = new Hashtable();

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    private int a(SEQUENCE sequence) {
        int iCountComponents;
        if (sequence != null && (iCountComponents = sequence.countComponents()) <= 3 && iCountComponents >= 1) {
            try {
                sequence.getComponentAt(0);
                if (iCountComponents == 1) {
                    return 11;
                }
                int tag = ((CON_SPEC) sequence.getComponentAt(1)).getAsnType().getTag();
                if (tag == 0) {
                    return 12;
                }
                if (tag == 1) {
                    return 13;
                }
                if (tag != 2) {
                    return tag != 3 ? 10 : 15;
                }
                return 14;
            } catch (Exception unused) {
            }
        }
        return 10;
    }

    private ASN1Object a(byte[] bArr) {
        return new AlgorithmID(new ObjectID("1.2.840.113549.1.5.9", null, null, false), new OCTET_STRING(bArr)).toASN1Object();
    }

    private SecretKey a(char[] cArr, byte[] bArr) throws Throwable {
        KeyGenerator keyGenerator;
        PBEKeyAndParameterSpec pBEKeyAndParameterSpec;
        byte[] bArrA = a(cArr);
        PBEKeyAndParameterSpec pBEKeyAndParameterSpec2 = null;
        try {
            try {
                keyGenerator = KeyGenerator.getInstance(SecurityProvider.ALG_KEYGEN_PBKDF2, "IAIK");
                pBEKeyAndParameterSpec = new PBEKeyAndParameterSpec(bArrA, bArr, this.b, this.a);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            keyGenerator.init(pBEKeyAndParameterSpec, (SecureRandom) null);
            SecretKey secretKey = (SecretKey) keyGenerator.generateKey();
            byte[] password = pBEKeyAndParameterSpec.getPassword();
            for (int i = 0; i < password.length; i++) {
                password[i] = 0;
            }
            return secretKey;
        } catch (Exception e2) {
            e = e2;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not generate key: ");
            stringBuffer.append(e.toString());
            throw new InternalErrorException(stringBuffer.toString());
        } catch (Throwable th2) {
            th = th2;
            pBEKeyAndParameterSpec2 = pBEKeyAndParameterSpec;
            if (pBEKeyAndParameterSpec2 != null) {
                byte[] password2 = pBEKeyAndParameterSpec2.getPassword();
                for (int i2 = 0; i2 < password2.length; i2++) {
                    password2[i2] = 0;
                }
            }
            throw th;
        }
    }

    private Date a(INTEGER integer) {
        return new Date(((BigInteger) integer.getValue()).longValue());
    }

    private byte[] a() {
        byte[] bArr = new byte[this.c];
        this.d.nextBytes(bArr);
        return bArr;
    }

    private byte[] a(int i, Key key, byte[] bArr) throws KeyStoreException {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr, 24, 8);
            SecretKey secretKey = new SecretKey(bArr, 0, 24, "DESede");
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "IAIK");
            cipher.init(i == 0 ? 1 : 2, secretKey, ivParameterSpec, (SecureRandom) null);
            return cipher.doFinal(key.getEncoded());
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Crypt failed: ");
            stringBuffer.append(e.toString());
            throw new KeyStoreException(stringBuffer.toString());
        }
    }

    private static byte[] a(String str) {
        try {
            return UTF8String.getUTF8EncodingFromString(str);
        } catch (Exception unused) {
            throw new RuntimeException("Error in UTF8 decoding");
        }
    }

    private static byte[] a(char[] cArr) {
        try {
            return UTF8String.getUTF8EncodingFromCharArray(cArr);
        } catch (Exception unused) {
            throw new RuntimeException("Error in UTF8 decoding");
        }
    }

    private INTEGER b() {
        return new INTEGER(BigInteger.valueOf(new Date().getTime()));
    }

    private static String b(byte[] bArr) {
        try {
            return UTF8String.getStringFromUTF8Encoding(bArr);
        } catch (Exception unused) {
            throw new RuntimeException("Error in UTF8 decoding");
        }
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration engineAliases() {
        return this.e.keys();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        if (str == null) {
            return false;
        }
        return this.e.containsKey(str);
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) throws KeyStoreException {
        if (str != null) {
            this.e.remove(str);
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        SEQUENCE sequence;
        int i;
        if (str == null || (sequence = (SEQUENCE) this.e.get(str)) == null) {
            return null;
        }
        int iCountComponents = sequence.countComponents();
        if (a(sequence) == 15) {
            i = 1;
        } else {
            if (iCountComponents != 3) {
                return null;
            }
            i = 2;
        }
        ASN1Object componentAt = ((SEQUENCE) ((CON_SPEC) sequence.getComponentAt(i)).getComponentAt(0)).getComponentAt(0);
        X509Certificate x509Certificate = new X509Certificate();
        try {
            x509Certificate.decode(componentAt);
            return x509Certificate;
        } catch (CodingException e) {
            throw new InternalErrorException(e.getMessage());
        }
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) {
        if (certificate == null) {
            return null;
        }
        try {
            byte[] encoded = certificate.getEncoded();
            Enumeration enumerationEngineAliases = engineAliases();
            while (enumerationEngineAliases.hasMoreElements()) {
                String str = (String) enumerationEngineAliases.nextElement();
                Certificate certificateEngineGetCertificate = engineGetCertificate(str);
                if (certificateEngineGetCertificate != null && CryptoUtils.equalsBlock(encoded, certificateEngineGetCertificate.getEncoded())) {
                    return str;
                }
            }
        } catch (CertificateEncodingException unused) {
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String str) {
        SEQUENCE sequence;
        if (str == null || (sequence = (SEQUENCE) this.e.get(str)) == null || sequence.countComponents() != 3) {
            return null;
        }
        int iA = a(sequence);
        if (iA != 13 && iA != 14) {
            return null;
        }
        SEQUENCE sequence2 = (SEQUENCE) ((CON_SPEC) sequence.getComponentAt(2)).getComponentAt(0);
        int iCountComponents = sequence2.countComponents();
        X509Certificate[] x509CertificateArr = new X509Certificate[iCountComponents];
        for (int i = 0; i < iCountComponents; i++) {
            try {
                ASN1Object componentAt = sequence2.getComponentAt(i);
                X509Certificate x509Certificate = new X509Certificate();
                x509Certificate.decode(componentAt);
                x509CertificateArr[i] = x509Certificate;
            } catch (CodingException unused) {
                return null;
            }
        }
        return x509CertificateArr;
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        SEQUENCE sequence;
        if (str == null || (sequence = (SEQUENCE) this.e.get(str)) == null || a(sequence) == 10) {
            return null;
        }
        return a((INTEGER) sequence.getComponentAt(0));
    }

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr) throws Throwable {
        if (str == null) {
            throw new UnrecoverableKeyException("Alias has to be specified.");
        }
        SEQUENCE sequence = (SEQUENCE) this.e.get(str);
        if (sequence == null) {
            return null;
        }
        int iA = a(sequence);
        if (iA == 10) {
            throw new UnrecoverableKeyException("Invalid keystore record.");
        }
        if (iA == 15) {
            throw new UnrecoverableKeyException("This alias specifies a certificate entry.");
        }
        if (iA == 11) {
            throw new UnrecoverableKeyException("This alias specifies a date entry.");
        }
        CON_SPEC con_spec = (CON_SPEC) sequence.getComponentAt(1);
        int tag = con_spec.getAsnType().getTag();
        if (tag == 2) {
            ASN1Object componentAt = con_spec.getComponentAt(0);
            if (cArr != null) {
                try {
                    if (cArr.length != 0) {
                        return new EncryptedPrivateKeyInfo(componentAt).decrypt(cArr);
                    }
                } catch (Exception e) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Wrong password: ");
                    stringBuffer.append(e.toString());
                    throw new UnrecoverableKeyException(stringBuffer.toString());
                }
            }
            return new EncryptedPrivateKeyInfo(componentAt);
        }
        if (cArr == null || cArr.length == 0) {
            throw new UnrecoverableKeyException("Password has to be specified!");
        }
        SEQUENCE sequence2 = (SEQUENCE) con_spec.getComponentAt(0);
        SEQUENCE sequence3 = (SEQUENCE) sequence2.getComponentAt(0);
        try {
            ObjectID objectID = (ObjectID) sequence3.getComponentAt(0);
            if (!objectID.getID().equals("1.2.840.113549.1.5.9")) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Unknown OID: ");
                stringBuffer2.append(objectID);
                throw new UnrecoverableKeyException(stringBuffer2.toString());
            }
            byte[] wholeValue = ((OCTET_STRING) sequence3.getComponentAt(1)).getWholeValue();
            if (wholeValue.length != this.c) {
                throw new UnrecoverableKeyException("Invalid salt");
            }
            SecretKey secretKeyA = a(cArr, wholeValue);
            try {
                try {
                    byte[] bArrA = a(1, new SecretKey(((OCTET_STRING) sequence2.getComponentAt(1)).getWholeValue(), "RAW"), secretKeyA.getEncoded());
                    if (tag == 0) {
                        return new SecretKey(bArrA, "RAW");
                    }
                    try {
                        return PrivateKeyInfo.getPrivateKey(bArrA);
                    } catch (Exception e2) {
                        throw new UnrecoverableKeyException(e2.getMessage());
                    }
                } catch (KeyStoreException e3) {
                    throw new UnrecoverableKeyException(e3.getMessage());
                }
            } catch (IOException e4) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Cannot parse key: ");
                stringBuffer3.append(e4.toString());
                throw new UnrecoverableKeyException(stringBuffer3.toString());
            }
        } catch (Exception e5) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Cannot parse key: ");
            stringBuffer4.append(e5.toString());
            throw new UnrecoverableKeyException(stringBuffer4.toString());
        }
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        return str != null && a((SEQUENCE) this.e.get(str)) == 15;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        if (str == null) {
            return false;
        }
        switch (a((SEQUENCE) this.e.get(str))) {
        }
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(InputStream inputStream, char[] cArr) throws Throwable {
        this.e.clear();
        if (inputStream == null) {
            return;
        }
        try {
            ASN1 asn1 = new ASN1(inputStream);
            SEQUENCE sequence = (SEQUENCE) asn1.getComponentAt(0);
            if (cArr == null) {
                throw new IOException("Password must be specified.");
            }
            SEQUENCE sequence2 = (SEQUENCE) asn1.getComponentAt(1);
            OCTET_STRING octet_string = (OCTET_STRING) sequence2.getComponentAt(1);
            OCTET_STRING octet_string2 = (OCTET_STRING) asn1.getComponentAt(2);
            SecretKey secretKeyA = a(cArr, (byte[]) octet_string.getValue());
            try {
                Mac mac = Mac.getInstance("HMAC/SHA", "IAIK");
                mac.init(secretKeyA);
                mac.update(asn1.getFirstObject());
                if (!CryptoUtils.secureEqualsBlock((byte[]) octet_string2.getValue(), mac.doFinal(DerCoder.encode(sequence2)))) {
                    throw new IOException(this, "Integrity verification failed! HMAC not valid. ") { // from class: iaik.security.keystore.IAIKKeyStore.1
                        private static final long serialVersionUID = 1811236715520167575L;
                        private final IAIKKeyStore a;

                        {
                            this.a = this;
                        }

                        @Override // java.lang.Throwable
                        public Throwable getCause() {
                            return new UnrecoverableKeyException("Integrity verification failed! HMAC not valid. Wrong password!");
                        }
                    };
                }
                for (int i = 0; i < sequence.countComponents(); i++) {
                    SEQUENCE sequence3 = (SEQUENCE) sequence.getComponentAt(i);
                    OCTET_STRING octet_string3 = (OCTET_STRING) sequence3.getComponentAt(0);
                    this.e.put(b((byte[]) octet_string3.getValue()), (SEQUENCE) sequence3.getComponentAt(1));
                }
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Could not initialize HMAC: ");
                stringBuffer.append(e.toString());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (CodingException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("CodingException: ");
            stringBuffer2.append(e2.getMessage());
            throw new IOException(stringBuffer2.toString());
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate) throws KeyStoreException {
        if (str == null) {
            throw new KeyStoreException("Alias has to be specified.");
        }
        if (certificate == null) {
            throw new KeyStoreException("Certificate has to be specified.");
        }
        if (engineIsKeyEntry(str)) {
            throw new KeyStoreException("This alias is already used by a key.");
        }
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(b());
        SEQUENCE sequence2 = new SEQUENCE();
        try {
            sequence2.addComponent(DerCoder.decode(certificate.getEncoded()));
            sequence.addComponent(new CON_SPEC(3, sequence2, false));
            this.e.put(str, sequence);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not encoding certificate: ");
            stringBuffer.append(e.toString());
            throw new InternalErrorException(stringBuffer.toString());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0088 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.security.KeyStoreSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void engineSetKeyEntry(java.lang.String r7, java.security.Key r8, char[] r9, java.security.cert.Certificate[] r10) throws java.security.KeyStoreException {
        /*
            Method dump skipped, instruction units count: 227
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.keystore.IAIKKeyStore.engineSetKeyEntry(java.lang.String, java.security.Key, char[], java.security.cert.Certificate[]):void");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) throws KeyStoreException {
        if (str == null) {
            throw new KeyStoreException("Alias has to be specified. ");
        }
        if (bArr == null || bArr.length == 0) {
            throw new KeyStoreException("Key has to be specified. ");
        }
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(b());
        try {
            sequence.addComponent(new CON_SPEC(2, new EncryptedPrivateKeyInfo(bArr).toASN1Object(), false));
            SEQUENCE sequence2 = new SEQUENCE();
            if (certificateArr != null && certificateArr[0] != null) {
                for (Certificate certificate : certificateArr) {
                    try {
                        sequence2.addComponent(DerCoder.decode(certificate.getEncoded()));
                    } catch (Exception e) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Could not decode certificate: ");
                        stringBuffer.append(e.toString());
                        throw new InternalErrorException(stringBuffer.toString());
                    }
                }
                sequence.addComponent(new CON_SPEC(3, sequence2, false));
            }
            this.e.put(str, sequence);
        } catch (InvalidKeyException unused) {
            throw new KeyStoreException("Key is not a PKCS#8-EncryptedPrivateKeyInfo. ");
        }
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        return this.e.size();
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr) throws Throwable {
        if (cArr == null || cArr.length == 0) {
            throw new IOException("Password must be specified.");
        }
        byte[] bArrA = a();
        SecretKey secretKeyA = a(cArr, bArrA);
        try {
            Mac mac = Mac.getInstance("HMAC/SHA", "IAIK");
            mac.init(secretKeyA);
            SEQUENCE sequence = new SEQUENCE();
            Enumeration enumerationKeys = this.e.keys();
            while (enumerationKeys.hasMoreElements()) {
                String str = (String) enumerationKeys.nextElement();
                OCTET_STRING octet_string = new OCTET_STRING(a(str));
                SEQUENCE sequence2 = new SEQUENCE();
                sequence2.addComponent(octet_string);
                sequence2.addComponent((ASN1Object) this.e.get(str));
                sequence.addComponent(sequence2);
            }
            mac.update(DerCoder.encode(sequence));
            ASN1Object aSN1ObjectA = a(bArrA);
            byte[] bArrDoFinal = mac.doFinal(DerCoder.encode(aSN1ObjectA));
            SEQUENCE sequence3 = new SEQUENCE();
            sequence3.addComponent(sequence);
            sequence3.addComponent(aSN1ObjectA);
            sequence3.addComponent(new OCTET_STRING(bArrDoFinal));
            outputStream.write(DerCoder.encode(sequence3));
        } catch (Exception unused) {
            throw new NoSuchAlgorithmException("Could not initialize HMAC. ");
        }
    }
}

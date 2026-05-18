package iaik.pkcs.pkcs12;

import com.felhr.usbserial.UsbSerialDebugger;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.pkcs8.EncryptedPrivateKeyInfo;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import iaik.x509.X509Certificate;
import iaik.x509.extensions.SubjectKeyIdentifier;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class PKCS12KeyStore extends KeyStoreSpi {
    private Hashtable a = new Hashtable();
    private Hashtable b = new Hashtable();

    private static class a {
        private X509Certificate a;

        a(X509Certificate x509Certificate) {
            this.a = x509Certificate;
        }

        public X509Certificate a() {
            return this.a;
        }
    }

    private static class b {
        private PKCS8ShroudedKeyBag a;
        private X509Certificate[] b;
        private byte[] c;

        b(PKCS8ShroudedKeyBag pKCS8ShroudedKeyBag, X509Certificate[] x509CertificateArr, byte[] bArr) {
            this.a = pKCS8ShroudedKeyBag;
            this.b = x509CertificateArr;
            this.c = bArr;
        }

        public PKCS8ShroudedKeyBag a() {
            return this.a;
        }

        public X509Certificate[] b() {
            return this.b;
        }

        public byte[] c() {
            return this.c;
        }
    }

    private byte[] a(Certificate certificate, String str) {
        byte[] bytes;
        try {
            bytes = Long.toString(System.currentTimeMillis()).getBytes("ASCII");
        } catch (Exception unused) {
            bytes = null;
        }
        if (bytes == null) {
            try {
                bytes = new SubjectKeyIdentifier(certificate.getPublicKey()).get();
            } catch (Exception unused2) {
            }
        }
        if (bytes == null && str != null) {
            try {
                try {
                    bytes = str.getBytes(UsbSerialDebugger.ENCODING);
                } catch (UnsupportedEncodingException unused3) {
                    bytes = str.getBytes();
                }
            } catch (UnsupportedEncodingException unused4) {
                bytes = str.getBytes("UTF8");
            }
        }
        if (bytes != null) {
            return bytes;
        }
        byte[] bArr = new byte[10];
        new Random().nextBytes(bArr);
        return bArr;
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration engineAliases() {
        Vector vector = new Vector(this.a.size() + this.b.size());
        vector.addAll(this.a.keySet());
        vector.addAll(this.b.keySet());
        return vector.elements();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        if (str != null) {
            return this.a.containsKey(str) || this.b.containsKey(str);
        }
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) throws KeyStoreException {
        if (str != null) {
            this.a.remove(str);
            this.b.remove(str);
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        b bVar;
        X509Certificate[] x509CertificateArrB;
        if (str == null) {
            return null;
        }
        a aVar = (a) this.b.get(str);
        X509Certificate x509CertificateA = aVar != null ? aVar.a() : null;
        return (x509CertificateA != null || (bVar = (b) this.a.get(str)) == null || (x509CertificateArrB = bVar.b()) == null || x509CertificateArrB.length <= 0) ? x509CertificateA : x509CertificateArrB[0];
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) {
        Enumeration enumerationKeys = this.a.keys();
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            Certificate[] certificateArrEngineGetCertificateChain = engineGetCertificateChain(str);
            if (certificateArrEngineGetCertificateChain.length > 0 && certificateArrEngineGetCertificateChain[0].equals(certificate)) {
                return str;
            }
        }
        Enumeration enumerationKeys2 = this.b.keys();
        while (enumerationKeys2.hasMoreElements()) {
            String str2 = (String) enumerationKeys2.nextElement();
            Certificate certificateEngineGetCertificate = engineGetCertificate(str2);
            if (certificateEngineGetCertificate != null && certificateEngineGetCertificate.equals(certificate)) {
                return str2;
            }
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String str) {
        b bVar;
        if (str == null || (bVar = (b) this.a.get(str)) == null) {
            return null;
        }
        return bVar.b();
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        Date notBefore;
        if (!engineContainsAlias(str)) {
            return null;
        }
        b bVar = (b) this.a.get(str);
        if (bVar == null) {
            return ((a) this.b.get(str)).a().getNotBefore();
        }
        try {
            notBefore = new Date(Long.parseLong(new String(bVar.c(), "ASCII")));
        } catch (UnsupportedEncodingException e) {
            throw new ProviderException(e.toString());
        } catch (NumberFormatException unused) {
            X509Certificate[] x509CertificateArrB = bVar.b();
            if (x509CertificateArrB == null || x509CertificateArrB.length <= 0) {
                return null;
            }
            notBefore = x509CertificateArrB[0].getNotBefore();
        }
        return notBefore;
    }

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr) throws UnrecoverableKeyException, NoSuchAlgorithmException {
        b bVar;
        if (str == null || (bVar = (b) this.a.get(str)) == null) {
            return null;
        }
        try {
            ASN1Object aSN1Object = bVar.a().toASN1Object();
            PKCS8ShroudedKeyBag pKCS8ShroudedKeyBag = new PKCS8ShroudedKeyBag();
            pKCS8ShroudedKeyBag.decode(aSN1Object);
            pKCS8ShroudedKeyBag.decrypt(cArr);
            return pKCS8ShroudedKeyBag.getPrivateKey();
        } catch (CodingException e) {
            throw new UnrecoverableKeyException(e.toString());
        } catch (GeneralSecurityException e2) {
            throw new UnrecoverableKeyException(e2.toString());
        }
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        if (str != null) {
            return this.b.containsKey(str);
        }
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        if (str != null) {
            return this.a.containsKey(str);
        }
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(InputStream inputStream, char[] cArr) throws NoSuchAlgorithmException, IOException, CertificateException {
        boolean z;
        CertificateBag certificateBag;
        X509Certificate certificate;
        Principal subjectDN;
        String friendlyName;
        byte[] localKeyID;
        this.a.clear();
        this.b.clear();
        if (inputStream != null) {
            try {
                PKCS12 pkcs12 = new PKCS12(inputStream);
                if (!pkcs12.verify(cArr)) {
                    throw new IOException(this, "PKCS12 verification error, incorrect password") { // from class: iaik.pkcs.pkcs12.PKCS12KeyStore.1
                        private static final long serialVersionUID = -6777747744926386667L;
                        private final PKCS12KeyStore a;

                        {
                            this.a = this;
                        }

                        @Override // java.lang.Throwable
                        public Throwable getCause() {
                            return new UnrecoverableKeyException("PKCS12 verification error, incorrect password");
                        }
                    };
                }
                AuthenticatedSafe[] authenticatedSafes = pkcs12.getAuthenticatedSafes();
                Vector vector = new Vector();
                Vector vector2 = new Vector();
                for (int i = 0; i < authenticatedSafes.length; i++) {
                    if (authenticatedSafes[i].a() == 2) {
                        try {
                            authenticatedSafes[i].decrypt(cArr);
                            SafeBag[] safeBags = authenticatedSafes[i].getSafeBags();
                            for (int i2 = 0; i2 < safeBags.length; i2++) {
                                if (safeBags[i2] instanceof CertificateBag) {
                                    vector2.addElement(safeBags[i2]);
                                } else if (safeBags[i2] instanceof PKCS8ShroudedKeyBag) {
                                    try {
                                        ((PKCS8ShroudedKeyBag) safeBags[i2]).decrypt(cArr);
                                        ASN1Object aSN1Object = ((PKCS8ShroudedKeyBag) safeBags[i2]).toASN1Object();
                                        PKCS8ShroudedKeyBag pKCS8ShroudedKeyBag = new PKCS8ShroudedKeyBag();
                                        pKCS8ShroudedKeyBag.decode(aSN1Object);
                                        pKCS8ShroudedKeyBag.setFriendlyName(safeBags[i2].getFriendlyName());
                                        pKCS8ShroudedKeyBag.setLocalKeyID(safeBags[i2].getLocalKeyID());
                                        vector.addElement(pKCS8ShroudedKeyBag);
                                    } catch (CodingException e) {
                                        throw new CertificateException(e.toString());
                                    } catch (GeneralSecurityException e2) {
                                        throw new CertificateException(e2.toString());
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } catch (Exception e3) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("Decryption error: ");
                            stringBuffer.append(e3.toString());
                            throw new CertificateException(stringBuffer.toString());
                        }
                    } else {
                        SafeBag[] safeBags2 = authenticatedSafes[i].getSafeBags();
                        for (int i3 = 0; i3 < safeBags2.length; i3++) {
                            if (safeBags2[i3] instanceof PKCS8ShroudedKeyBag) {
                                vector.addElement(safeBags2[i3]);
                            } else if (safeBags2[i3] instanceof CertificateBag) {
                                vector2.addElement(safeBags2[i3]);
                            }
                        }
                    }
                }
                int size = vector.size();
                PKCS8ShroudedKeyBag[] pKCS8ShroudedKeyBagArr = new PKCS8ShroudedKeyBag[size];
                vector.copyInto(pKCS8ShroudedKeyBagArr);
                int size2 = vector2.size();
                CertificateBag[] certificateBagArr = new CertificateBag[size2];
                vector2.copyInto(certificateBagArr);
                X509Certificate[] certificates = CertificateBag.getCertificates(certificateBagArr);
                for (int i4 = 0; i4 < size; i4++) {
                    PKCS8ShroudedKeyBag pKCS8ShroudedKeyBag2 = pKCS8ShroudedKeyBagArr[i4];
                    byte[] localKeyID2 = pKCS8ShroudedKeyBag2.getLocalKeyID();
                    boolean z2 = true;
                    if (localKeyID2 != null) {
                        for (int i5 = 0; i5 < size2; i5++) {
                            try {
                                byte[] localKeyID3 = certificateBagArr[i5].getLocalKeyID();
                                if (localKeyID3 != null && CryptoUtils.equalsBlock(localKeyID3, localKeyID2)) {
                                    certificateBag = certificateBagArr[i5];
                                    certificate = certificateBag.getCertificate();
                                    String friendlyName2 = pKCS8ShroudedKeyBag2.getFriendlyName();
                                    if (friendlyName2 != null) {
                                        int i6 = 0;
                                        while (true) {
                                            if (i6 >= size2) {
                                                break;
                                            }
                                            String friendlyName3 = certificateBagArr[i6].getFriendlyName();
                                            if (friendlyName3 != null && friendlyName2.equals(friendlyName3) && (localKeyID = certificateBagArr[i6].getLocalKeyID()) != null && CryptoUtils.equalsBlock(localKeyID, localKeyID2)) {
                                                certificateBag = certificateBagArr[i6];
                                                certificate = certificateBag.getCertificate();
                                                break;
                                            }
                                            i6++;
                                        }
                                    }
                                    z = true;
                                }
                            } catch (Exception e4) {
                                throw new CertificateException(e4.toString());
                            }
                        }
                        z = false;
                        certificateBag = null;
                        certificate = null;
                    } else {
                        z = false;
                        certificateBag = null;
                        certificate = null;
                    }
                    if (z || (friendlyName = pKCS8ShroudedKeyBag2.getFriendlyName()) == null) {
                        z2 = z;
                    } else {
                        for (int i7 = 0; i7 < size2; i7++) {
                            String friendlyName4 = certificateBagArr[i7].getFriendlyName();
                            if (friendlyName4 != null && friendlyName.equals(friendlyName4)) {
                                certificateBag = certificateBagArr[i7];
                                certificate = certificateBag.getCertificate();
                                break;
                            }
                        }
                        z2 = z;
                    }
                    if (!z2) {
                        throw new Exception("No localKeyID or friendlyName match!");
                    }
                    b bVar = new b(pKCS8ShroudedKeyBag2, certificate != null ? Util.createCertificateChain(certificate, certificates) : null, localKeyID2);
                    String friendlyName5 = pKCS8ShroudedKeyBag2.getFriendlyName();
                    if (friendlyName5 == null && certificateBag != null) {
                        String friendlyName6 = certificateBag.getFriendlyName();
                        if (friendlyName6 != null) {
                            friendlyName5 = friendlyName6;
                        } else if (certificate != null && (subjectDN = certificate.getSubjectDN()) != null) {
                            friendlyName5 = subjectDN.getName();
                        }
                    }
                    if (friendlyName5 == null || this.a.containsKey(friendlyName5)) {
                        friendlyName5 = Integer.toString(i4);
                        for (int i8 = i4; this.a.containsKey(friendlyName5) && i8 < Integer.MAX_VALUE; i8++) {
                            friendlyName5 = Integer.toString(i8);
                        }
                    }
                    this.a.put(friendlyName5, bVar);
                }
                for (int i9 = 0; i9 < size2; i9++) {
                    String friendlyName7 = certificateBagArr[i9].getFriendlyName();
                    if (friendlyName7 != null && !this.a.containsKey(friendlyName7)) {
                        this.b.put(friendlyName7, new a(certificateBagArr[i9].getCertificate()));
                    }
                }
            } catch (PKCSException e5) {
                throw new IOException(e5.toString());
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate) throws KeyStoreException {
        Objects.requireNonNull(str, "alias must not be null");
        Objects.requireNonNull(certificate, "cert must not be null");
        if (this.a.get(str) == null) {
            this.b.put(str, new a((X509Certificate) certificate));
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Alias \"");
        stringBuffer.append(str);
        stringBuffer.append("\" already used for KeyEntry!");
        throw new KeyStoreException(stringBuffer.toString());
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) throws KeyStoreException {
        Objects.requireNonNull(str, "alias must not be null!");
        Objects.requireNonNull(key, "key must not be null!");
        Objects.requireNonNull(certificateArr, "chain must not be null!");
        if (this.b.get(str) != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Alias \"");
            stringBuffer.append(str);
            stringBuffer.append("\" already used for CertEntry!");
            throw new KeyStoreException(stringBuffer.toString());
        }
        byte[] bArrA = a(certificateArr[0], str);
        try {
            KeyBag keyBag = new KeyBag((PrivateKey) key, str, bArrA);
            keyBag.setLocalKeyID(bArrA);
            PKCS8ShroudedKeyBag pKCS8ShroudedKeyBag = new PKCS8ShroudedKeyBag(keyBag);
            pKCS8ShroudedKeyBag.encrypt(cArr, (AlgorithmID) AlgorithmID.pbeWithSHAAnd3_KeyTripleDES_CBC.clone(), 2000);
            this.a.put(str, new b(pKCS8ShroudedKeyBag, Util.convertCertificateChain(certificateArr), bArrA));
        } catch (NoSuchAlgorithmException e) {
            throw new KeyStoreException(e.toString());
        } catch (CertificateException e2) {
            throw new KeyStoreException(e2.toString());
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) throws KeyStoreException {
        Objects.requireNonNull(str, "alias must not be null");
        Objects.requireNonNull(bArr, "key must not be null");
        Objects.requireNonNull(certificateArr, "chain must not be null");
        if (certificateArr.length == 0) {
            throw new NullPointerException("chain must not be empty");
        }
        if (this.b.get(str) != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Alias \"");
            stringBuffer.append(str);
            stringBuffer.append("\" already used for CertEntry!");
            throw new KeyStoreException(stringBuffer.toString());
        }
        byte[] bArrA = a(certificateArr[0], str);
        try {
            ASN1Object aSN1Object = new EncryptedPrivateKeyInfo(bArr).toASN1Object();
            PKCS8ShroudedKeyBag pKCS8ShroudedKeyBag = new PKCS8ShroudedKeyBag();
            pKCS8ShroudedKeyBag.decode(aSN1Object);
            pKCS8ShroudedKeyBag.setFriendlyName(str);
            pKCS8ShroudedKeyBag.setLocalKeyID(bArrA);
            this.a.put(str, new b(pKCS8ShroudedKeyBag, Util.convertCertificateChain(certificateArr), bArrA));
        } catch (CodingException e) {
            throw new KeyStoreException(e.toString());
        } catch (InvalidKeyException e2) {
            throw new KeyStoreException(e2.toString());
        } catch (CertificateException e3) {
            throw new KeyStoreException(e3.toString());
        }
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        return this.a.size() + this.b.size();
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr) throws NoSuchAlgorithmException, IOException, CertificateException {
        Vector vector = new Vector(2);
        Vector vector2 = new Vector(4);
        Enumeration enumerationKeys = this.a.keys();
        while (true) {
            if (!enumerationKeys.hasMoreElements()) {
                break;
            }
            String str = (String) enumerationKeys.nextElement();
            b bVar = (b) this.a.get(str);
            vector.addElement(bVar.a());
            X509Certificate[] x509CertificateArrB = bVar.b();
            vector2.addElement(new CertificateBag(x509CertificateArrB[0], str, bVar.c()));
            for (int i = 1; i < x509CertificateArrB.length; i++) {
                vector2.addElement(new CertificateBag(x509CertificateArrB[i]));
            }
        }
        Enumeration enumerationKeys2 = this.b.keys();
        while (enumerationKeys2.hasMoreElements()) {
            String str2 = (String) enumerationKeys2.nextElement();
            CertificateBag certificateBag = new CertificateBag(((a) this.b.get(str2)).a());
            certificateBag.setFriendlyName(str2);
            vector2.addElement(certificateBag);
        }
        KeyBag[] keyBagArr = new KeyBag[vector.size()];
        vector.copyInto(keyBagArr);
        CertificateBag[] certificateBagArr = new CertificateBag[vector2.size()];
        vector2.copyInto(certificateBagArr);
        try {
            PKCS12 pkcs12 = new PKCS12(keyBagArr, certificateBagArr, true);
            pkcs12.encrypt(cArr);
            pkcs12.toASN1Object();
            pkcs12.writeTo(outputStream);
        } catch (PKCSException e) {
            throw new CertificateException(e.toString());
        }
    }
}

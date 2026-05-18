package iaik.utils;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs8.EncryptedPrivateKeyInfo;
import iaik.pkcs.pkcs8.PrivateKeyInfo;
import iaik.security.random.SecRandom;
import iaik.x509.X509Certificate;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class KeyAndCertificate {
    X509Certificate[] a;
    PrivateKey b;

    public KeyAndCertificate(InputStream inputStream) throws IOException {
        this(Util.readStream(inputStream));
    }

    private KeyAndCertificate(InputStream inputStream, boolean z) throws IOException {
        try {
            ASN1Object aSN1Object = new ASN1(inputStream).toASN1Object();
            if (aSN1Object.countComponents() == 2) {
                this.b = new EncryptedPrivateKeyInfo(aSN1Object);
            } else {
                this.b = PrivateKeyInfo.getPrivateKey(aSN1Object);
            }
            Vector vector = new Vector();
            while (inputStream.available() > 10) {
                try {
                    vector.addElement(new X509Certificate(inputStream));
                } catch (CertificateException e) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Unable to decode certificate: ");
                    stringBuffer.append(e.toString());
                    throw new IOException(stringBuffer.toString());
                }
            }
            X509Certificate[] x509CertificateArr = new X509Certificate[vector.size()];
            this.a = x509CertificateArr;
            vector.copyInto(x509CertificateArr);
            inputStream.close();
        } catch (CodingException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Unable to decode private key: ");
            stringBuffer2.append(e2.toString());
            throw new IOException(stringBuffer2.toString());
        } catch (InvalidKeyException e3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Unable to decode private key: ");
            stringBuffer3.append(e3.toString());
            throw new IOException(stringBuffer3.toString());
        }
    }

    public KeyAndCertificate(String str) throws IOException {
        this((InputStream) new FileInputStream(str), true);
    }

    public KeyAndCertificate(PrivateKey privateKey, X509Certificate[] x509CertificateArr) {
        this.a = x509CertificateArr;
        this.b = privateKey;
    }

    public KeyAndCertificate(byte[] bArr) throws IOException {
        this((InputStream) new ByteArrayInputStream(bArr), true);
    }

    public PrivateKey decrypt(char[] cArr) throws NoSuchAlgorithmException {
        if (isEncrypted()) {
            try {
                this.b = ((EncryptedPrivateKeyInfo) this.b).decrypt(cArr);
            } catch (NoSuchAlgorithmException e) {
                throw e;
            } catch (GeneralSecurityException e2) {
                throw new ProviderException(e2.toString());
            }
        }
        return this.b;
    }

    public void encrypt(char[] cArr, AlgorithmID algorithmID, SecureRandom secureRandom) throws NoSuchAlgorithmException {
        if (isEncrypted()) {
            return;
        }
        EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(this.b);
        if (algorithmID == null) {
            algorithmID = AlgorithmID.pbeWithSHAAnd3_KeyTripleDES_CBC;
        }
        if (secureRandom == null) {
            secureRandom = SecRandom.getDefault();
        }
        encryptedPrivateKeyInfo.encrypt(cArr, algorithmID, secureRandom);
        this.b = encryptedPrivateKeyInfo;
    }

    public X509Certificate[] getCertificateChain() {
        return this.a;
    }

    public PrivateKey getPrivateKey() {
        return this.b;
    }

    public boolean isEncrypted() {
        return this.b instanceof EncryptedPrivateKeyInfo;
    }

    public void saveTo(String str, int i) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(str);
        } catch (Throwable th) {
            th = th;
        }
        try {
            writeTo(fileOutputStream, i);
            try {
                fileOutputStream.close();
            } catch (IOException unused) {
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    public void writeTo(OutputStream outputStream, int i) throws IOException {
        int i2 = 0;
        if (i != 1) {
            outputStream.write(Util.toASCIIBytes(Util.toPemString(this.b)));
            while (true) {
                X509Certificate[] x509CertificateArr = this.a;
                if (i2 >= x509CertificateArr.length) {
                    break;
                }
                outputStream.write(Util.toASCIIBytes(Util.toPemString(x509CertificateArr[i2])));
                i2++;
            }
        } else {
            outputStream.write(this.b.getEncoded());
            while (true) {
                X509Certificate[] x509CertificateArr2 = this.a;
                if (i2 >= x509CertificateArr2.length) {
                    break;
                }
                x509CertificateArr2[i2].writeTo(outputStream);
                i2++;
            }
        }
        outputStream.close();
    }
}

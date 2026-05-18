package iaik.pkcs;

import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.pkcs.pkcs7.ContentInfo;
import iaik.pkcs.pkcs7.SignedData;
import iaik.x509.X509CRL;
import iaik.x509.X509Certificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PKCS7CertList implements Serializable {
    private static final long serialVersionUID = -1620487539577645157L;
    private SignedData a;

    public PKCS7CertList() {
        this.a = new SignedData(ObjectID.pkcs7_data);
    }

    public PKCS7CertList(InputStream inputStream) throws PKCSParsingException, IOException {
        this.a = (SignedData) new ContentInfo(inputStream).getContent();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            this.a = (SignedData) new ContentInfo(objectInputStream).getContent();
        } catch (PKCSParsingException e) {
            throw new IOException(e.toString());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            objectOutputStream.write(toByteArray());
        } catch (PKCSException e) {
            throw new IOException(e.toString());
        }
    }

    public X509CRL[] getCRLList() {
        return this.a.getCRLs();
    }

    public X509Certificate[] getCertificateList() {
        return this.a.getCertificates();
    }

    public void setCRLList(X509CRL[] x509crlArr) {
        this.a.setCRLs(x509crlArr);
    }

    public void setCertificateList(X509Certificate[] x509CertificateArr) {
        this.a.setCertificates(x509CertificateArr);
    }

    public byte[] toByteArray() throws PKCSException {
        return new ContentInfo(this.a).toByteArray();
    }

    public String toString() {
        return this.a.toString();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        try {
            DerCoder.encodeTo(new ContentInfo(this.a).toASN1Object(), outputStream);
        } catch (PKCSException e) {
            throw new IOException(e.toString());
        }
    }
}

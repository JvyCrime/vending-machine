package iaik.pkcs.pkcs12;

import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.x509.X509Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

/* JADX INFO: loaded from: classes.dex */
public class CertificateBag extends SafeBag {
    X509Certificate c;
    ASN1Object d;

    protected CertificateBag() {
        this.d = ObjectID.x509Certificate;
        this.e = ObjectID.pkcs12_certBag;
    }

    public CertificateBag(X509Certificate x509Certificate) {
        this.d = ObjectID.x509Certificate;
        this.c = x509Certificate;
        this.e = ObjectID.pkcs12_certBag;
    }

    public CertificateBag(X509Certificate x509Certificate, String str, byte[] bArr) {
        super(str, bArr);
        this.d = ObjectID.x509Certificate;
        this.c = x509Certificate;
        this.e = ObjectID.pkcs12_certBag;
    }

    public static X509Certificate[] getCertificates(CertificateBag[] certificateBagArr) {
        X509Certificate[] x509CertificateArr = new X509Certificate[certificateBagArr.length];
        for (int i = 0; i < certificateBagArr.length; i++) {
            x509CertificateArr[i] = certificateBagArr[i].getCertificate();
        }
        return x509CertificateArr;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.d = aSN1Object.getComponentAt(0);
        try {
            this.c = new X509Certificate((byte[]) ((ASN1Object) aSN1Object.getComponentAt(1).getValue()).getValue());
        } catch (CertificateException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error decoding certifcate! ");
            stringBuffer.append(e);
            throw new CodingException(stringBuffer.toString());
        }
    }

    public X509Certificate getCertificate() {
        return this.c;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.d);
        try {
            sequence.addComponent(new CON_SPEC(0, new OCTET_STRING(this.c.getEncoded())));
            return sequence;
        } catch (CertificateEncodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error encoding certificate!");
            stringBuffer.append(e);
            throw new CodingException(stringBuffer.toString());
        }
    }

    @Override // iaik.pkcs.pkcs12.SafeBag, iaik.pkcs.pkcs12.Attributes
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Certificate type: ");
        stringBuffer2.append(this.c.getType());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(super.toString());
        return stringBuffer.toString();
    }
}

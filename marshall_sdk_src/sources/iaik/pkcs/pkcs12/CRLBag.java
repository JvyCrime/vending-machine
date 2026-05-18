package iaik.pkcs.pkcs12;

import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.x509.X509CRL;
import java.security.cert.CRLException;

/* JADX INFO: loaded from: classes.dex */
public class CRLBag extends SafeBag {
    protected X509CRL crl;
    protected ASN1Object crl_type;

    protected CRLBag() {
        this.crl_type = ObjectID.x509Crl;
        this.e = ObjectID.pkcs12_crlBag;
    }

    public CRLBag(X509CRL x509crl) {
        this.crl_type = ObjectID.x509Crl;
        this.crl = x509crl;
        this.e = ObjectID.pkcs12_crlBag;
    }

    public CRLBag(X509CRL x509crl, String str, byte[] bArr) {
        super(str, bArr);
        this.crl_type = ObjectID.x509Crl;
        this.crl = x509crl;
        this.e = ObjectID.pkcs12_crlBag;
    }

    public static X509CRL[] getCertificates(CRLBag[] cRLBagArr) {
        int length = cRLBagArr.length;
        X509CRL[] x509crlArr = new X509CRL[length];
        for (int i = 0; i < length; i++) {
            x509crlArr[i] = cRLBagArr[i].getCRL();
        }
        return x509crlArr;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.crl_type = aSN1Object.getComponentAt(0);
        ASN1Object aSN1Object2 = (ASN1Object) aSN1Object.getComponentAt(1).getValue();
        X509CRL x509crl = new X509CRL();
        x509crl.decode(aSN1Object2);
        this.crl = x509crl;
    }

    public X509CRL getCRL() {
        return this.crl;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.crl_type);
        try {
            sequence.addComponent(new CON_SPEC(0, new OCTET_STRING(this.crl.getEncoded())));
            return sequence;
        } catch (CRLException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error encoding CRL!");
            stringBuffer.append(e);
            throw new CodingException(stringBuffer.toString());
        }
    }

    @Override // iaik.pkcs.pkcs12.SafeBag, iaik.pkcs.pkcs12.Attributes
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("CRL type: ");
        stringBuffer2.append(this.crl.getType());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(super.toString());
        return stringBuffer.toString();
    }
}

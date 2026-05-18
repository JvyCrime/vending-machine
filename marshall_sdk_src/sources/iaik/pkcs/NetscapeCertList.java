package iaik.pkcs;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.Util;
import iaik.x509.X509Certificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class NetscapeCertList {
    static Class a;
    private X509Certificate[] b = null;

    public NetscapeCertList() {
    }

    public NetscapeCertList(InputStream inputStream) throws PKCSParsingException, IOException {
        try {
            a(inputStream);
        } catch (CodingException e) {
            throw new PKCSParsingException(e.getMessage());
        }
    }

    private ASN1Object a() throws CodingException {
        SEQUENCE sequence = new SEQUENCE(true);
        sequence.addComponent(ObjectID.netscapeCertSequence);
        sequence.addComponent(new CON_SPEC(0, ASN.createSequenceOf(this.b)));
        return sequence;
    }

    private void a(InputStream inputStream) throws CodingException, IOException {
        ASN1 asn1 = new ASN1(Util.readStream(inputStream));
        ASN1Object componentAt = asn1.getComponentAt(0);
        if (!(componentAt instanceof ObjectID)) {
            throw new CodingException("Invalid Netscape cert sequence. First component has to be an object identifier!");
        }
        if (!((ObjectID) componentAt).equals(ObjectID.netscapeCertSequence)) {
            throw new CodingException("Invalid Netscape cert sequence. Wrong object identifier!");
        }
        ASN1Object componentAt2 = asn1.getComponentAt(1).getComponentAt(0);
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.X509Certificate");
            a = clsClass$;
        }
        this.b = (X509Certificate[]) ASN.parseSequenceOf(componentAt2, clsClass$);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public X509Certificate[] getCertificateList() {
        return this.b;
    }

    public void setCertificateList(X509Certificate[] x509CertificateArr) {
        this.b = x509CertificateArr;
    }

    public byte[] toByteArray() throws PKCSException {
        try {
            return DerCoder.encode(a());
        } catch (CodingException e) {
            throw new PKCSException(e.getMessage());
        }
    }

    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ContentType: netscapeCertSequence  {\n");
        if (this.b == null) {
            string = "No certificates!\n}";
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.b.length);
            stringBuffer2.append(" certificates\n}");
            string = stringBuffer2.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        try {
            DerCoder.encodeTo(a(), outputStream);
        } catch (CodingException e) {
            throw new IOException(e.toString());
        }
    }
}

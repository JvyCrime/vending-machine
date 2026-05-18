package iaik.x509.ocsp;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.asn1.structures.GeneralName;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509Certificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.ocsp.extensions.Nonce;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes2.dex */
public class BasicOCSPResponse extends Response {
    static Class a;
    static Class b;
    public static final ObjectID responseType = ObjectID.basicOcspResponse;
    private ASN1 c;
    private int d;
    private ResponderID e;
    private ChoiceOfTime f;
    private SingleResponse[] g;
    private OCSPExtensions h;
    private AlgorithmID i;
    private X509Certificate[] j;
    private byte[] k;
    private boolean l;

    public BasicOCSPResponse() {
        this.d = 1;
        a();
        this.c = new ASN1();
    }

    public BasicOCSPResponse(InputStream inputStream) throws IOException, CodingException {
        decode(inputStream);
    }

    public BasicOCSPResponse(byte[] bArr) throws CodingException {
        decode(bArr);
    }

    private void a() {
        this.l = true;
        this.c = null;
    }

    private void b() {
        if (this.l) {
            throw new RuntimeException("Cannot perform operation, certificate has to be signed first");
        }
    }

    private void c() {
        this.l = false;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private void d() throws X509ExtensionException, CodingException {
        int i = 0;
        ASN1Object componentAt = this.c.getComponentAt(0);
        this.i = new AlgorithmID(this.c.getComponentAt(1));
        this.k = (byte[]) ((BIT_STRING) this.c.getComponentAt(2)).getValue();
        if (this.c.countComponents() == 4) {
            ASN1Object aSN1Object = (ASN1Object) this.c.getComponentAt(3).getValue();
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.x509.X509Certificate");
                a = clsClass$;
            }
            this.j = (X509Certificate[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
        }
        ASN1Object componentAt2 = componentAt.getComponentAt(0);
        if (componentAt2.isA(ASN.CON_SPEC) && componentAt2.getAsnType().getTag() == 0) {
            this.d = ((BigInteger) ((ASN1Object) componentAt2.getValue()).getValue()).intValue() + 1;
            i = 1;
        }
        this.e = new ResponderID(componentAt.getComponentAt(i + 0));
        this.f = new ChoiceOfTime(componentAt.getComponentAt(i + 1));
        ASN1Object componentAt3 = componentAt.getComponentAt(i + 2);
        Class clsClass$2 = b;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.x509.ocsp.SingleResponse");
            b = clsClass$2;
        }
        this.g = (SingleResponse[]) ASN.parseSequenceOf(componentAt3, clsClass$2);
        int i2 = i + 3;
        if (i2 < componentAt.countComponents()) {
            this.h = new OCSPExtensions((ASN1Object) componentAt.getComponentAt(i2).getValue());
        }
        this.c.clearASN1Object();
        c();
    }

    private ASN1Object e() throws OCSPException {
        if (this.e == null) {
            throw new OCSPException("Responder ID not set!");
        }
        if (this.f == null) {
            throw new OCSPException("ProducedAt date not set!");
        }
        SingleResponse[] singleResponseArr = this.g;
        if (singleResponseArr == null || singleResponseArr.length == 0) {
            throw new OCSPException("No single responses set!");
        }
        try {
            SEQUENCE sequence = new SEQUENCE();
            if (this.d > 1) {
                sequence.addComponent(new CON_SPEC(0, new INTEGER(this.d - 1)));
            }
            sequence.addComponent(this.e.toASN1Object());
            sequence.addComponent(this.f.toASN1Object());
            sequence.addComponent(ASN.createSequenceOf(this.g));
            OCSPExtensions oCSPExtensions = this.h;
            if (oCSPExtensions != null && oCSPExtensions.countExtensions() > 0) {
                sequence.addComponent(new CON_SPEC(1, this.h.toASN1Object()));
            }
            return sequence;
        } catch (Exception e) {
            throw new OCSPException(e.toString());
        }
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        if (this.h == null) {
            this.h = new OCSPExtensions();
        }
        this.h.addExtension(v3Extension);
        a();
    }

    public boolean containsCertificates() {
        X509Certificate[] x509CertificateArr = this.j;
        return x509CertificateArr != null && x509CertificateArr.length > 0;
    }

    public int countExtensions() {
        OCSPExtensions oCSPExtensions = this.h;
        if (oCSPExtensions == null) {
            return 0;
        }
        return oCSPExtensions.countExtensions();
    }

    public int countSingleResponses() {
        return this.g.length;
    }

    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.c = new ASN1(aSN1Object);
        try {
            d();
        } catch (Exception e) {
            throw new CodingException(e.toString());
        }
    }

    public void decode(InputStream inputStream) throws IOException {
        try {
            this.c = new ASN1(inputStream);
            d();
        } catch (CodingException e) {
            throw new IOException(e.toString());
        } catch (X509ExtensionException e2) {
            throw new IOException(e2.toString());
        }
    }

    @Override // iaik.x509.ocsp.Response
    public void decode(byte[] bArr) throws CodingException {
        try {
            this.c = new ASN1(bArr);
            d();
        } catch (X509ExtensionException e) {
            throw new CodingException(e.toString());
        }
    }

    @Override // iaik.x509.ocsp.Response
    public CertificateResponse getCertificateResponse(X509Certificate x509Certificate, X509Certificate x509Certificate2, GeneralName generalName) throws OCSPException {
        if (this.g != null) {
            int i = 0;
            OCSPException oCSPException = null;
            while (true) {
                SingleResponse[] singleResponseArr = this.g;
                if (i < singleResponseArr.length) {
                    SingleResponse singleResponse = singleResponseArr[i];
                    try {
                    } catch (OCSPException e) {
                        if (oCSPException == null) {
                            oCSPException = e;
                        }
                    }
                    if (singleResponse.isResponseFor(x509Certificate, x509Certificate2, generalName)) {
                        return singleResponse;
                    }
                    i++;
                } else if (oCSPException != null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Cannot check single responses. ");
                    stringBuffer.append(oCSPException.getMessage());
                    throw new OCSPException(stringBuffer.toString());
                }
            }
        }
        return null;
    }

    @Override // iaik.x509.ocsp.Response
    public CertificateResponse getCertificateResponse(ReqCert reqCert) throws OCSPException {
        if (this.g == null) {
            return null;
        }
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            SingleResponse[] singleResponseArr = this.g;
            if (i >= singleResponseArr.length) {
                if (!z && !z2) {
                    return null;
                }
                String string = "";
                String str = z2 ? "certIDs with different hash algorithms" : "";
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No response found, but some responses have ");
                if (z) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("different ReqCert types ");
                    if (z2) {
                        StringBuffer stringBuffer3 = new StringBuffer();
                        stringBuffer3.append("or ");
                        stringBuffer3.append(str);
                        string = stringBuffer3.toString();
                    }
                    stringBuffer2.append(string);
                    string = stringBuffer2.toString();
                } else if (z2) {
                    string = str;
                }
                stringBuffer.append(string);
                throw new OCSPException(stringBuffer.toString());
            }
            SingleResponse singleResponse = singleResponseArr[i];
            if (singleResponse.isResponseFor(reqCert)) {
                return singleResponse;
            }
            if (reqCert.getType() != singleResponse.getReqCert().getType()) {
                z = true;
            } else if (!z2 && reqCert.getType() == 0) {
                if (!((CertID) reqCert.getReqCert()).getHashAlgorithm().equals(((CertID) singleResponse.getReqCert().getReqCert()).getHashAlgorithm())) {
                    z2 = true;
                }
            }
            i++;
        }
    }

    public X509Certificate[] getCertificates() {
        return this.j;
    }

    @Override // iaik.x509.ocsp.Response
    public byte[] getEncoded() {
        b();
        return this.c.toByteArray();
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        OCSPExtensions oCSPExtensions = this.h;
        if (oCSPExtensions == null) {
            return null;
        }
        return oCSPExtensions.getExtension(objectID);
    }

    public byte[] getNonce() throws X509ExtensionInitException {
        Nonce nonce = (Nonce) getExtension(Nonce.oid);
        if (nonce == null) {
            return null;
        }
        return nonce.getValue();
    }

    public Date getProducedAt() {
        ChoiceOfTime choiceOfTime = this.f;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    public ResponderID getResponderID() {
        return this.e;
    }

    @Override // iaik.x509.ocsp.Response
    public ObjectID getResponseType() {
        return responseType;
    }

    public byte[] getSignature() {
        return this.k;
    }

    public AlgorithmID getSignatureAlgorithm() {
        return this.i;
    }

    public X509Certificate getSignerCertificate() {
        if (this.j != null && this.e != null) {
            int i = 0;
            while (true) {
                X509Certificate[] x509CertificateArr = this.j;
                if (i >= x509CertificateArr.length) {
                    break;
                }
                try {
                    if (this.e.isResponderIdFor(x509CertificateArr[i])) {
                        return this.j[i];
                    }
                    continue;
                } catch (Exception unused) {
                }
                i++;
            }
        }
        return null;
    }

    public SingleResponse getSingleResponse(X509Certificate x509Certificate, X509Certificate x509Certificate2, GeneralName generalName) throws OCSPException {
        return (SingleResponse) getCertificateResponse(x509Certificate, x509Certificate2, generalName);
    }

    public SingleResponse getSingleResponse(ReqCert reqCert) throws OCSPException {
        return (SingleResponse) getCertificateResponse(reqCert);
    }

    public SingleResponse[] getSingleResponses() {
        return this.g;
    }

    public byte[] getTBSResponseData() throws CodingException {
        try {
            ASN1 asn1 = this.c;
            return (asn1 == null || asn1.toByteArray() == null) ? DerCoder.encode(e()) : this.c.getFirstObject();
        } catch (OCSPException e) {
            throw new CodingException(e.toString());
        }
    }

    public int getVersion() {
        return this.d;
    }

    public boolean hasExtensions() {
        OCSPExtensions oCSPExtensions = this.h;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.hasExtensions();
    }

    public boolean hasUnsupportedCriticalExtension() {
        OCSPExtensions oCSPExtensions = this.h;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.hasUnsupportedCriticalExtension();
    }

    public Enumeration listExtensions() {
        OCSPExtensions oCSPExtensions = this.h;
        if (oCSPExtensions == null) {
            return null;
        }
        return oCSPExtensions.listExtensions();
    }

    public void removeAllExtensions() {
        OCSPExtensions oCSPExtensions = this.h;
        if (oCSPExtensions != null) {
            oCSPExtensions.removeAllExtensions();
            a();
        }
        this.h = null;
    }

    public boolean removeExtension(ObjectID objectID) {
        OCSPExtensions oCSPExtensions = this.h;
        boolean zRemoveExtension = oCSPExtensions == null ? false : oCSPExtensions.removeExtension(objectID);
        if (zRemoveExtension) {
            a();
        }
        return zRemoveExtension;
    }

    public void setCertificates(X509Certificate[] x509CertificateArr) {
        this.j = x509CertificateArr;
        a();
    }

    public void setNonce(byte[] bArr) throws X509ExtensionException {
        addExtension(new Nonce(bArr));
    }

    public void setProducedAt(Date date) {
        this.f = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
        a();
    }

    public void setResponderID(ResponderID responderID) {
        this.e = responderID;
        a();
    }

    public void setSignature(AlgorithmID algorithmID, byte[] bArr) throws OCSPException {
        if (algorithmID == null) {
            throw new OCSPException("Cannot set signature! No signature algorithm specified!");
        }
        if (bArr == null || bArr.length == 0) {
            throw new OCSPException("Cannot set empty signature value!");
        }
        this.i = algorithmID;
        this.k = bArr;
        ASN1Object aSN1ObjectE = e();
        try {
            BIT_STRING bit_string = new BIT_STRING(this.k);
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectE);
            sequence.addComponent(this.i.toASN1Object());
            sequence.addComponent(bit_string);
            X509Certificate[] x509CertificateArr = this.j;
            if (x509CertificateArr != null && x509CertificateArr.length > 0) {
                sequence.addComponent(new CON_SPEC(0, ASN.createSequenceOf(this.j)));
            }
            this.c = new ASN1(sequence);
            c();
        } catch (CodingException e) {
            throw new OCSPException(e.toString());
        }
    }

    public void setSingleResponses(SingleResponse[] singleResponseArr) {
        this.g = singleResponseArr;
        a();
        if (this.g == null) {
            return;
        }
        int i = 0;
        while (true) {
            SingleResponse[] singleResponseArr2 = this.g;
            if (i >= singleResponseArr2.length) {
                return;
            }
            if (singleResponseArr2[i].getReqCert().getType() != 0) {
                this.d = 2;
                return;
            }
            i++;
        }
    }

    public void sign(AlgorithmID algorithmID, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, OCSPException {
        sign(algorithmID, privateKey, null);
    }

    public void sign(AlgorithmID algorithmID, PrivateKey privateKey, String str) throws NoSuchAlgorithmException, InvalidKeyException, OCSPException {
        AlgorithmParameters signatureParameters;
        if (algorithmID == null) {
            throw new OCSPException("Cannot sign response! No signature algorithm specified!");
        }
        this.i = algorithmID;
        Signature signatureInstance = algorithmID.getSignatureInstance(str);
        signatureInstance.initSign(privateKey);
        try {
            if (!AlgorithmID.getDoNotIncludeParameters(this.i) && !this.i.hasParameters() && (signatureParameters = Util.getSignatureParameters(signatureInstance)) != null) {
                this.i.setAlgorithmParameters(signatureParameters);
            }
        } catch (Exception unused) {
        }
        ASN1Object aSN1ObjectE = e();
        try {
            signatureInstance.update(DerCoder.encode(aSN1ObjectE));
            this.k = signatureInstance.sign();
            BIT_STRING bit_string = new BIT_STRING(this.k);
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectE);
            sequence.addComponent(this.i.toASN1Object());
            sequence.addComponent(bit_string);
            X509Certificate[] x509CertificateArr = this.j;
            if (x509CertificateArr != null && x509CertificateArr.length > 0) {
                sequence.addComponent(new CON_SPEC(0, ASN.createSequenceOf(this.j)));
            }
            this.c = new ASN1(sequence);
            c();
        } catch (CodingException e) {
            throw new OCSPException(e.toString());
        } catch (SignatureException e2) {
            throw new OCSPException(e2.toString());
        }
    }

    public ASN1Object toASN1Object() {
        b();
        return this.c.toASN1Object();
    }

    @Override // iaik.x509.ocsp.Response
    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.d);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("ResponderID: ");
        stringBuffer3.append(this.e);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("ProducedAt: ");
        stringBuffer4.append(this.f);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        if (z) {
            for (int i = 0; i < this.g.length; i++) {
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append("singleResponse ");
                stringBuffer5.append(i);
                stringBuffer5.append(": {\n");
                stringBuffer.append(stringBuffer5.toString());
                Util.printIndented(this.g[i].toString(true), true, "  ", stringBuffer);
                stringBuffer.append("\n}");
            }
        } else {
            StringBuffer stringBuffer6 = new StringBuffer();
            stringBuffer6.append("singleResponses: ");
            stringBuffer6.append(this.g.length);
            stringBuffer.append(stringBuffer6.toString());
        }
        stringBuffer.append("\n");
        OCSPExtensions oCSPExtensions = this.h;
        if (oCSPExtensions != null) {
            if (z) {
                stringBuffer.append(oCSPExtensions);
            } else {
                StringBuffer stringBuffer7 = new StringBuffer();
                stringBuffer7.append("Extensions: ");
                stringBuffer7.append(this.h.countExtensions());
                stringBuffer.append(stringBuffer7.toString());
                stringBuffer.append("\n");
            }
        }
        StringBuffer stringBuffer8 = new StringBuffer();
        stringBuffer8.append("Signature algorithm: ");
        stringBuffer8.append(this.i);
        stringBuffer8.append("\n");
        stringBuffer.append(stringBuffer8.toString());
        if (this.j != null) {
            StringBuffer stringBuffer9 = new StringBuffer();
            stringBuffer9.append("certificates: ");
            stringBuffer9.append(this.j.length);
            stringBuffer9.append("\n");
            stringBuffer.append(stringBuffer9.toString());
        }
        return stringBuffer.toString();
    }

    public X509Certificate verify() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, OCSPException {
        X509Certificate[] x509CertificateArr = this.j;
        if (x509CertificateArr == null || x509CertificateArr.length == 0) {
            throw new OCSPException("Cannot verify request. No certificates included.");
        }
        X509Certificate signerCertificate = getSignerCertificate();
        if (signerCertificate == null) {
            X509Certificate[] x509CertificateArrArrangeCertificateChain = Util.arrangeCertificateChain(this.j, false);
            signerCertificate = (x509CertificateArrArrangeCertificateChain == null || x509CertificateArrArrangeCertificateChain.length <= 0) ? this.j[0] : x509CertificateArrArrangeCertificateChain[0];
        }
        verify(signerCertificate.getPublicKey());
        return signerCertificate;
    }

    public void verify(PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        verify(publicKey, null);
    }

    public void verify(PublicKey publicKey, String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        b();
        Signature signatureInstance = this.i.getSignatureInstance(str);
        try {
            byte[] firstObject = this.c.getFirstObject();
            signatureInstance.initVerify(publicKey);
            signatureInstance.update(firstObject);
            if (!signatureInstance.verify(this.k)) {
                throw new SignatureException("Signature verification error!");
            }
        } catch (CodingException e) {
            throw new SignatureException(e.toString());
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        b();
        this.c.writeTo(outputStream);
    }
}

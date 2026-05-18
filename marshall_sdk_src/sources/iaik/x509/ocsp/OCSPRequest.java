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
import iaik.asn1.structures.GeneralName;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509Certificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.ocsp.extensions.AcceptableResponses;
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
import java.util.Enumeration;

/* JADX INFO: loaded from: classes2.dex */
public class OCSPRequest {
    static Class a;
    static Class b;
    private ASN1 c;
    private int d;
    private GeneralName e;
    private Request[] f;
    private OCSPExtensions g;
    private AlgorithmID h;
    private byte[] i;
    private X509Certificate[] j;
    private boolean k;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    public OCSPRequest() {
        this.d = 1;
        this.k = true;
        this.c = new ASN1();
    }

    public OCSPRequest(InputStream inputStream) throws IOException {
        this();
        decode(inputStream);
    }

    public OCSPRequest(byte[] bArr) throws CodingException {
        this();
        decode(bArr);
    }

    private void a() throws X509ExtensionException, CodingException {
        int i;
        ASN1Object componentAt = this.c.getComponentAt(0);
        ASN1Object componentAt2 = componentAt.getComponentAt(0);
        if (componentAt2.isA(ASN.CON_SPEC) && componentAt2.getAsnType().getTag() == 0) {
            this.d = ((BigInteger) ((ASN1Object) componentAt2.getValue()).getValue()).intValue() + 1;
            i = 1;
        } else {
            i = 0;
        }
        ASN1Object componentAt3 = componentAt.getComponentAt(i + 0);
        if (componentAt3.isA(ASN.CON_SPEC) && componentAt3.getAsnType().getTag() == 1) {
            this.e = new GeneralName((ASN1Object) componentAt3.getValue());
            i++;
        }
        ASN1Object componentAt4 = componentAt.getComponentAt(i + 0);
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.ocsp.Request");
            a = clsClass$;
        }
        this.f = (Request[]) ASN.parseSequenceOf(componentAt4, clsClass$);
        int i2 = i + 1;
        if (i2 < componentAt.countComponents()) {
            this.g = new OCSPExtensions((ASN1Object) componentAt.getComponentAt(i2).getValue());
        }
        if (this.c.countComponents() == 2) {
            ASN1Object aSN1Object = (ASN1Object) this.c.getComponentAt(1).getValue();
            this.h = new AlgorithmID(aSN1Object.getComponentAt(0));
            byte[] bArr = (byte[]) ((BIT_STRING) aSN1Object.getComponentAt(1)).getValue();
            this.i = bArr;
            if (bArr == null) {
                throw new CodingException("Request is signed, but signature value is missing!");
            }
            if (aSN1Object.countComponents() == 3) {
                ASN1Object aSN1Object2 = (ASN1Object) aSN1Object.getComponentAt(2).getValue();
                Class clsClass$2 = b;
                if (clsClass$2 == null) {
                    clsClass$2 = class$("iaik.x509.X509Certificate");
                    b = clsClass$2;
                }
                this.j = (X509Certificate[]) ASN.parseSequenceOf(aSN1Object2, clsClass$2);
            }
            this.k = false;
        }
        this.c.clearASN1Object();
    }

    private ASN1Object b() throws OCSPException {
        Request[] requestArr = this.f;
        if (requestArr == null || requestArr.length == 0) {
            throw new OCSPException("No single requests set!");
        }
        try {
            SEQUENCE sequence = new SEQUENCE();
            if (this.d > 1) {
                sequence.addComponent(new CON_SPEC(0, new INTEGER(this.d - 1)));
            }
            if (this.e != null) {
                sequence.addComponent(new CON_SPEC(1, this.e.toASN1Object()));
            }
            sequence.addComponent(ASN.createSequenceOf(this.f));
            OCSPExtensions oCSPExtensions = this.g;
            if (oCSPExtensions != null && oCSPExtensions.countExtensions() > 0) {
                sequence.addComponent(new CON_SPEC(2, this.g.toASN1Object()));
            }
            return sequence;
        } catch (Exception e) {
            throw new OCSPException(e.getMessage());
        }
    }

    private void c() throws OCSPException {
        try {
            b();
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(b());
            this.c = new ASN1(sequence);
        } catch (CodingException e) {
            throw new OCSPException(e.getMessage());
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        if (this.g == null) {
            this.g = new OCSPExtensions();
        }
        this.g.addExtension(v3Extension);
        this.k = true;
    }

    public boolean containsCertificates() {
        X509Certificate[] x509CertificateArr = this.j;
        return x509CertificateArr != null && x509CertificateArr.length > 0;
    }

    public boolean containsSignature() {
        return this.i != null;
    }

    public int countExtensions() {
        OCSPExtensions oCSPExtensions = this.g;
        if (oCSPExtensions == null) {
            return 0;
        }
        return oCSPExtensions.countExtensions();
    }

    public int countRequests() {
        return this.f.length;
    }

    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.c = new ASN1(aSN1Object);
        try {
            a();
        } catch (Exception e) {
            throw new CodingException(e.toString());
        }
    }

    public void decode(InputStream inputStream) throws IOException {
        try {
            this.c = new ASN1(inputStream);
            a();
        } catch (CodingException e) {
            throw new IOException(e.getMessage());
        } catch (X509ExtensionException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public void decode(byte[] bArr) throws CodingException {
        try {
            this.c = new ASN1(bArr);
            a();
        } catch (X509ExtensionException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public ObjectID[] getAccepatableResponseTypes() throws X509ExtensionInitException {
        AcceptableResponses acceptableResponses = (AcceptableResponses) getExtension(AcceptableResponses.oid);
        if (acceptableResponses == null) {
            return null;
        }
        return acceptableResponses.getAcceptableResponseTypes();
    }

    public X509Certificate[] getCertifcates() {
        return this.j;
    }

    public byte[] getEncoded() throws CodingException {
        if (this.k && this.i != null) {
            throw new RuntimeException("Cannot encode this request. First it has to be resigned.");
        }
        if (this.i == null && this.c.toByteArray() == null) {
            try {
                c();
            } catch (OCSPException e) {
                throw new CodingException(e.getMessage());
            }
        }
        return this.c.toByteArray();
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        OCSPExtensions oCSPExtensions = this.g;
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

    public Request[] getRequestList() {
        return this.f;
    }

    public GeneralName getRequestorName() {
        return this.e;
    }

    public byte[] getSignature() {
        return this.i;
    }

    public AlgorithmID getSignatureAlgorithm() {
        return this.h;
    }

    public byte[] getTBSRequest() throws CodingException {
        try {
            ASN1 asn1 = this.c;
            return (asn1 == null || asn1.toByteArray() == null) ? DerCoder.encode(b()) : this.c.getFirstObject();
        } catch (OCSPException e) {
            throw new CodingException(e.toString());
        }
    }

    public int getVersion() {
        return this.d;
    }

    public boolean hasExtensions() {
        OCSPExtensions oCSPExtensions = this.g;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.hasExtensions();
    }

    public boolean hasUnsupportedCriticalExtension() {
        OCSPExtensions oCSPExtensions = this.g;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.hasUnsupportedCriticalExtension();
    }

    public Enumeration listExtensions() {
        OCSPExtensions oCSPExtensions = this.g;
        if (oCSPExtensions == null) {
            return null;
        }
        return oCSPExtensions.listExtensions();
    }

    public void removeAllExtensions() {
        OCSPExtensions oCSPExtensions = this.g;
        if (oCSPExtensions != null) {
            oCSPExtensions.removeAllExtensions();
            this.k = true;
        }
        this.g = null;
    }

    public boolean removeExtension(ObjectID objectID) {
        OCSPExtensions oCSPExtensions = this.g;
        boolean zRemoveExtension = oCSPExtensions == null ? false : oCSPExtensions.removeExtension(objectID);
        if (zRemoveExtension) {
            this.k = true;
        }
        return zRemoveExtension;
    }

    public void setAcceptableResponseTypes(ObjectID[] objectIDArr) throws X509ExtensionException {
        addExtension(new AcceptableResponses(objectIDArr));
    }

    public void setCertificates(X509Certificate[] x509CertificateArr) {
        this.j = x509CertificateArr;
        this.k = true;
    }

    public void setNonce(byte[] bArr) throws X509ExtensionException {
        addExtension(new Nonce(bArr));
    }

    public void setRequestList(Request[] requestArr) {
        this.f = requestArr;
        this.k = true;
        if (requestArr != null) {
            for (Request request : requestArr) {
                if (request.getReqCert().getType() != 0) {
                    this.d = 2;
                    return;
                }
            }
        }
    }

    public void setRequestorName(GeneralName generalName) {
        this.e = generalName;
        this.k = true;
    }

    public void setSignature(AlgorithmID algorithmID, byte[] bArr) throws OCSPException {
        if (algorithmID == null) {
            throw new OCSPException("Cannot sign request! No signature algorithm specified!");
        }
        if (bArr == null || bArr.length == 0) {
            throw new OCSPException("Cannot set empty signature value!");
        }
        this.h = algorithmID;
        this.i = bArr;
        ASN1Object aSN1ObjectB = b();
        try {
            BIT_STRING bit_string = new BIT_STRING(this.i);
            SEQUENCE sequence = new SEQUENCE();
            SEQUENCE sequence2 = new SEQUENCE();
            sequence2.addComponent(this.h.toASN1Object());
            sequence2.addComponent(bit_string);
            X509Certificate[] x509CertificateArr = this.j;
            if (x509CertificateArr != null && x509CertificateArr.length > 0) {
                sequence2.addComponent(new CON_SPEC(0, ASN.createSequenceOf(this.j)));
            }
            sequence.addComponent(aSN1ObjectB);
            sequence.addComponent(new CON_SPEC(0, sequence2));
            this.c = new ASN1(sequence);
            this.k = false;
        } catch (CodingException e) {
            throw new OCSPException(e.getMessage());
        }
    }

    public void sign(AlgorithmID algorithmID, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, OCSPException {
        sign(algorithmID, privateKey, null);
    }

    public void sign(AlgorithmID algorithmID, PrivateKey privateKey, String str) throws NoSuchAlgorithmException, InvalidKeyException, OCSPException {
        AlgorithmParameters signatureParameters;
        if (algorithmID == null) {
            throw new OCSPException("Cannot sign request! No signature algorithm specified!");
        }
        this.h = algorithmID;
        Signature signatureInstance = algorithmID.getSignatureInstance(str);
        signatureInstance.initSign(privateKey);
        try {
            if (!AlgorithmID.getDoNotIncludeParameters(this.h) && !this.h.hasParameters() && (signatureParameters = Util.getSignatureParameters(signatureInstance)) != null) {
                this.h.setAlgorithmParameters(signatureParameters);
            }
        } catch (Exception unused) {
        }
        ASN1Object aSN1ObjectB = b();
        try {
            signatureInstance.update(DerCoder.encode(aSN1ObjectB));
            this.i = signatureInstance.sign();
            BIT_STRING bit_string = new BIT_STRING(this.i);
            SEQUENCE sequence = new SEQUENCE();
            SEQUENCE sequence2 = new SEQUENCE();
            sequence2.addComponent(this.h.toASN1Object());
            sequence2.addComponent(bit_string);
            X509Certificate[] x509CertificateArr = this.j;
            if (x509CertificateArr != null && x509CertificateArr.length > 0) {
                sequence2.addComponent(new CON_SPEC(0, ASN.createSequenceOf(this.j)));
            }
            sequence.addComponent(aSN1ObjectB);
            sequence.addComponent(new CON_SPEC(0, sequence2));
            this.c = new ASN1(sequence);
            this.k = false;
        } catch (CodingException e) {
            throw new OCSPException(e.getMessage());
        } catch (SignatureException e2) {
            throw new OCSPException(e2.getMessage());
        }
    }

    public ASN1Object toASN1Object() throws CodingException {
        if (this.k && this.i != null) {
            throw new RuntimeException("Cannot give an ASN.1 representation of this request. First it has to be resigned.");
        }
        if (this.i == null && this.c.toByteArray() == null) {
            try {
                c();
            } catch (OCSPException e) {
                throw new CodingException(e.getMessage());
            }
        }
        return this.c.toASN1Object();
    }

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
        if (this.e != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("requestorName: ");
            stringBuffer3.append(this.e);
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        if (z) {
            for (int i = 0; i < this.f.length; i++) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("request ");
                stringBuffer4.append(i);
                stringBuffer4.append(": {\n");
                stringBuffer.append(stringBuffer4.toString());
                Util.printIndented(this.f[i].toString(true), true, "  ", stringBuffer);
                stringBuffer.append("\n}");
            }
        } else {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("requests: ");
            stringBuffer5.append(this.f.length);
            stringBuffer.append(stringBuffer5.toString());
        }
        if (this.g != null) {
            stringBuffer.append("\n");
            if (z) {
                stringBuffer.append(this.g);
                stringBuffer.setLength(stringBuffer.length() - 1);
            } else {
                StringBuffer stringBuffer6 = new StringBuffer();
                stringBuffer6.append("Extensions: ");
                stringBuffer6.append(this.g.countExtensions());
                stringBuffer.append(stringBuffer6.toString());
            }
        }
        if (this.h != null) {
            StringBuffer stringBuffer7 = new StringBuffer();
            stringBuffer7.append("\nSignature algorithm: ");
            stringBuffer7.append(this.h);
            stringBuffer.append(stringBuffer7.toString());
        }
        if (this.j != null) {
            StringBuffer stringBuffer8 = new StringBuffer();
            stringBuffer8.append("\ncertificates: ");
            stringBuffer8.append(this.j.length);
            stringBuffer.append(stringBuffer8.toString());
        }
        return stringBuffer.toString();
    }

    public X509Certificate verify() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, OCSPException {
        X509Certificate[] x509CertificateArr = this.j;
        if (x509CertificateArr == null || x509CertificateArr.length <= 0) {
            throw new OCSPException("Cannot verify request. No certificates included.");
        }
        X509Certificate[] x509CertificateArrArrangeCertificateChain = Util.arrangeCertificateChain(x509CertificateArr, false);
        if (x509CertificateArrArrangeCertificateChain == null || x509CertificateArrArrangeCertificateChain.length <= 0) {
            throw new OCSPException("Cannot verify request. Cannot build chain from included certs.");
        }
        verify(x509CertificateArrArrangeCertificateChain[0].getPublicKey());
        return x509CertificateArrArrangeCertificateChain[0];
    }

    public void verify(PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        verify(publicKey, null);
    }

    public void verify(PublicKey publicKey, String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (this.k) {
            throw new RuntimeException("Cannot verify this request. First it has to be signed.");
        }
        AlgorithmID algorithmID = this.h;
        if (algorithmID == null) {
            throw new NoSuchAlgorithmException("Cannot verify request! No signature algorithm set.");
        }
        Signature signatureInstance = algorithmID.getSignatureInstance(str);
        try {
            byte[] firstObject = this.c.getFirstObject();
            signatureInstance.initVerify(publicKey);
            signatureInstance.update(firstObject);
            if (!signatureInstance.verify(this.i)) {
                throw new SignatureException("Signature verification error!");
            }
        } catch (CodingException e) {
            throw new SignatureException(e.toString());
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        if (this.k && this.i != null) {
            throw new RuntimeException("Cannot encode this response. First it has to be signed.");
        }
        try {
            outputStream.write(getEncoded());
        } catch (CodingException e) {
            throw new IOException(e.getMessage());
        }
    }
}

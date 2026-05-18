package iaik.x509.ocsp.utils;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CON_SPEC;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Name;
import iaik.pkcs.PKCSException;
import iaik.pkcs.pkcs12.CertificateBag;
import iaik.pkcs.pkcs12.PKCS12;
import iaik.pkcs.pkcs7.IssuerAndSerialNumber;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import iaik.x509.RevokedCertificate;
import iaik.x509.X509CRL;
import iaik.x509.X509Certificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.extensions.ExtendedKeyUsage;
import iaik.x509.extensions.ReasonCode;
import iaik.x509.ocsp.CertID;
import iaik.x509.ocsp.CertStatus;
import iaik.x509.ocsp.OCSPException;
import iaik.x509.ocsp.ReqCert;
import iaik.x509.ocsp.Request;
import iaik.x509.ocsp.ResponderID;
import iaik.x509.ocsp.RevokedInfo;
import iaik.x509.ocsp.SingleResponse;
import iaik.x509.ocsp.UnknownInfo;
import iaik.x509.ocsp.extensions.ServiceLocator;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class ResponseGenerator {
    private PrintWriter a;
    private int b;
    private PrivateKey c;
    private X509Certificate[] d;
    private Vector e;
    private Vector f;
    private boolean g;

    ResponseGenerator() {
        this.e = new Vector();
        this.f = new Vector();
        this.a = null;
        this.g = false;
        this.b = 0;
    }

    public ResponseGenerator(PKCS12 pkcs12, char[] cArr) throws PKCSException {
        this();
        if (!pkcs12.verify(cArr)) {
            throw new PKCSException("Verification error!");
        }
        pkcs12.decrypt(cArr);
        this.c = pkcs12.getKeyBag().getPrivateKey();
        try {
            X509Certificate[] x509CertificateArrConvertCertificateChain = Util.convertCertificateChain(CertificateBag.getCertificates(pkcs12.getCertificateBags()));
            this.d = x509CertificateArrConvertCertificateChain;
            X509Certificate[] x509CertificateArrArrangeCertificateChain = Util.arrangeCertificateChain(x509CertificateArrConvertCertificateChain, false);
            this.d = x509CertificateArrArrangeCertificateChain;
            if (x509CertificateArrArrangeCertificateChain == null) {
                throw new PKCSException("Cannot sort certificates included in PKCS#12 object!");
            }
            if (this.c == null) {
                throw new IllegalArgumentException("Cannot create ResponseGenerator. Missing responder key!");
            }
            if (x509CertificateArrArrangeCertificateChain == null || x509CertificateArrArrangeCertificateChain.length < 1) {
                throw new IllegalArgumentException("Cannot create ResponseGenerator. Missing responder certs!");
            }
            this.g = a(x509CertificateArrArrangeCertificateChain[0]);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error reading certificates: ");
            stringBuffer.append(e.getMessage());
            throw new PKCSException(stringBuffer.toString());
        }
    }

    public ResponseGenerator(PrivateKey privateKey, X509Certificate[] x509CertificateArr) {
        this();
        if (privateKey == null) {
            throw new IllegalArgumentException("Cannot create ResponseGenerator. Missing responder key!");
        }
        if (x509CertificateArr == null || x509CertificateArr.length < 1) {
            throw new IllegalArgumentException("Cannot create ResponseGenerator. Missing responder certs!");
        }
        this.c = privateKey;
        this.d = x509CertificateArr;
        this.g = a(x509CertificateArr[0]);
    }

    private static Name a(Object obj) {
        if (obj instanceof IssuerAndSerialNumber) {
            return ((IssuerAndSerialNumber) obj).getIssuer();
        }
        if (obj instanceof X509Certificate) {
            return (Name) ((X509Certificate) obj).getIssuerDN();
        }
        return null;
    }

    private static SingleResponse a(RevokedCertificate revokedCertificate, PublicKey publicKey, Name name, Date date, Date date2, int i) throws Exception {
        RevokedInfo revokedInfo = new RevokedInfo(revokedCertificate.getRevocationDate());
        try {
            ReasonCode reasonCode = (ReasonCode) revokedCertificate.getExtension(ReasonCode.oid);
            if (reasonCode != null) {
                revokedInfo.setRevocationReason(reasonCode);
            }
        } catch (X509ExtensionException unused) {
        }
        SingleResponse singleResponse = new SingleResponse(i == 0 ? new ReqCert(0, new CertID(AlgorithmID.sha1, name, publicKey, revokedCertificate.getSerialNumber())) : new ReqCert(1, new IssuerAndSerialNumber(name, revokedCertificate.getSerialNumber())), new CertStatus(revokedInfo), date);
        if (date2 != null) {
            singleResponse.setNextUpdate(date2);
        }
        return singleResponse;
    }

    private SingleResponse a(Request request, int i, int i2) {
        CertStatus certStatus;
        String str;
        try {
            ServiceLocator serviceLocator = request.getServiceLocator();
            if (serviceLocator != null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Request contains the ServiceLocator extension with issuer ");
                stringBuffer.append(serviceLocator.getIssuer());
                stringBuffer.append(".");
                a(stringBuffer.toString(), i, i2);
                if (!serviceLocator.getIssuer().equals(this.d[0].getSubjectDN())) {
                    a("Service Locator does not reference this reponder: sending unknown response.", i, i2);
                    return new SingleResponse(request.getReqCert(), new CertStatus(new UnknownInfo()), new Date());
                }
            }
        } catch (X509ExtensionException unused) {
        }
        Enumeration enumerationElements = this.e.elements();
        while (enumerationElements.hasMoreElements()) {
            SingleResponse singleResponse = (SingleResponse) enumerationElements.nextElement();
            if (singleResponse.isResponseFor(request.getReqCert())) {
                a("Cached response detected.", i, i2);
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Status of response is: ");
                stringBuffer2.append(singleResponse.getCertStatus());
                a(stringBuffer2.toString());
                return singleResponse;
            }
        }
        a("No single response cached. Creating new...", i, i2);
        a("Searching for authorized issuer...", i, i2);
        ReqCert reqCert = request.getReqCert();
        if (a(reqCert)) {
            certStatus = new CertStatus();
            str = "Authorized issuer found. CertStatus good.";
        } else {
            certStatus = new CertStatus(new UnknownInfo());
            str = "No authorized issuer found. CertStatus unknown.";
        }
        a(str, i, i2);
        return new SingleResponse(reqCert, certStatus, new Date());
    }

    private void a(String str) {
        a(str, -1, -1);
    }

    private void a(String str, int i) {
        PrintWriter printWriter = this.a;
        if (printWriter != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("(rg_debug");
            String string = ") ";
            if (i > 0) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(" ");
                stringBuffer2.append(i);
                stringBuffer2.append(") ");
                string = stringBuffer2.toString();
            }
            stringBuffer.append(string);
            stringBuffer.append(str);
            printWriter.println(stringBuffer.toString());
        }
    }

    private void a(String str, int i, int i2) {
        PrintWriter printWriter = this.a;
        if (printWriter != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("(rg_debug");
            String string = ") ";
            if (i > 0) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(" ");
                stringBuffer2.append(i);
                stringBuffer2.append("_");
                stringBuffer2.append(i2);
                stringBuffer2.append(") ");
                string = stringBuffer2.toString();
            }
            stringBuffer.append(string);
            stringBuffer.append(str);
            printWriter.println(stringBuffer.toString());
        }
    }

    private boolean a(X509Certificate x509Certificate) {
        try {
            ExtendedKeyUsage extendedKeyUsage = (ExtendedKeyUsage) x509Certificate.getExtension(ExtendedKeyUsage.oid);
            if (extendedKeyUsage != null) {
                for (ObjectID objectID : extendedKeyUsage.getKeyPurposeIDs()) {
                    if (objectID.equals(ExtendedKeyUsage.ocspSigning)) {
                        a("Responder cert is allowed to sign OCSP responses (ExtendenKeyUsage purpose ocspSigning is set).");
                        return true;
                    }
                }
            }
        } catch (X509ExtensionException unused) {
        }
        return false;
    }

    private boolean a(ReqCert reqCert) {
        int type = reqCert.getType();
        Name name = null;
        try {
            if (type == 0) {
                CertID certID = (CertID) reqCert.getReqCert();
                byte[] issuerNameHash = certID.getIssuerNameHash();
                if (CryptoUtils.equalsBlock(issuerNameHash, CertID.calculateIssuerNameHash((Name) this.d[0].getSubjectDN(), certID.getHashAlgorithm()))) {
                    return true;
                }
                if (!CryptoUtils.equalsBlock(issuerNameHash, CertID.calculateIssuerNameHash((Name) this.d[0].getIssuerDN(), certID.getHashAlgorithm())) || !this.g) {
                    Enumeration enumerationElements = this.f.elements();
                    while (true) {
                        if (!enumerationElements.hasMoreElements()) {
                            break;
                        }
                        Name name2 = (Name) enumerationElements.nextElement();
                        if (CryptoUtils.equalsBlock(issuerNameHash, CertID.calculateIssuerNameHash(name2, certID.getHashAlgorithm()))) {
                            name = name2;
                            break;
                        }
                    }
                } else {
                    return true;
                }
            } else if (type == 1 || type == 2) {
                Name nameA = a(reqCert.getReqCert());
                if (nameA.equals(this.d[0].getSubjectDN())) {
                    return true;
                }
                if (!nameA.equals(this.d[0].getIssuerDN()) || !this.g) {
                    Enumeration enumerationElements2 = this.f.elements();
                    while (true) {
                        if (!enumerationElements2.hasMoreElements()) {
                            break;
                        }
                        Name name3 = (Name) enumerationElements2.nextElement();
                        if (nameA.equals(name3)) {
                            name = name3;
                            break;
                        }
                    }
                } else {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        if (name != null) {
            if (name.equals(this.d[0].getSubjectDN())) {
                return true;
            }
            if (name.equals(this.d[0].getIssuerDN()) && this.g) {
                return true;
            }
        }
        return false;
    }

    public void addCertificateIssuer(Name name) {
        int size = this.f.size();
        for (int i = 0; i < size; i++) {
            if (name.equals(this.f.elementAt(i))) {
                return;
            }
        }
        this.f.addElement(name);
    }

    public void addResponseEntries(X509CRL x509crl, X509Certificate x509Certificate, int i) throws SignatureException, OCSPException {
        addResponseEntries(x509crl, x509Certificate, i, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void addResponseEntries(iaik.x509.X509CRL r19, iaik.x509.X509Certificate r20, int r21, iaik.x509.ocsp.extensions.CrlID r22) throws java.security.SignatureException, iaik.x509.ocsp.OCSPException {
        /*
            Method dump skipped, instruction units count: 575
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.x509.ocsp.utils.ResponseGenerator.addResponseEntries(iaik.x509.X509CRL, iaik.x509.X509Certificate, int, iaik.x509.ocsp.extensions.CrlID):void");
    }

    public void addResponseEntry(ReqCert reqCert, CertStatus certStatus, Date date, Date date2) {
        SingleResponse singleResponse = new SingleResponse(reqCert, certStatus, date);
        if (date2 != null) {
            singleResponse.setNextUpdate(date2);
        }
        addResponseEntry(singleResponse);
    }

    public void addResponseEntry(SingleResponse singleResponse) {
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            if (((SingleResponse) this.e.elementAt(i)).isResponseFor(singleResponse.getReqCert())) {
                this.e.setElementAt(singleResponse, i);
                return;
            }
        }
        this.e.addElement(singleResponse);
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x0117 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0062 A[Catch: Exception -> 0x0072, SignatureException -> 0x0075, InvalidKeyException -> 0x0078, OCSPException -> 0x007b, NoSuchAlgorithmException -> 0x007e, IOException -> 0x0081, TryCatch #7 {Exception -> 0x0072, blocks: (B:8:0x0019, B:10:0x001f, B:12:0x0026, B:18:0x0038, B:20:0x003e, B:22:0x0062, B:24:0x006c), top: B:116:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0111  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public iaik.x509.ocsp.OCSPResponse createOCSPResponse(java.io.InputStream r10, java.security.PublicKey r11, iaik.asn1.structures.AlgorithmID r12, iaik.x509.V3Extension[] r13) {
        /*
            Method dump skipped, instruction units count: 618
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.x509.ocsp.utils.ResponseGenerator.createOCSPResponse(java.io.InputStream, java.security.PublicKey, iaik.asn1.structures.AlgorithmID, iaik.x509.V3Extension[]):iaik.x509.ocsp.OCSPResponse");
    }

    public X509Certificate[] getResponderCertificates() {
        return this.d;
    }

    public PrivateKey getResponderKey() {
        return this.c;
    }

    public SingleResponse getSingleResponse(ReqCert reqCert) {
        Enumeration enumerationElements = this.e.elements();
        while (enumerationElements.hasMoreElements()) {
            SingleResponse singleResponse = (SingleResponse) enumerationElements.nextElement();
            if (singleResponse.isResponseFor(reqCert)) {
                return singleResponse;
            }
        }
        return null;
    }

    public SingleResponse getSingleResponse(Request request) {
        return a(request, -1, -1);
    }

    public Enumeration getSingleResponses() {
        return this.e.elements();
    }

    public void init(InputStream inputStream) throws IOException {
        try {
            this.e.removeAllElements();
            this.f.removeAllElements();
            ASN1 asn1 = new ASN1(inputStream);
            byte[] firstObject = asn1.getFirstObject();
            ASN1Object componentAt = asn1.getComponentAt(0);
            if (!new ResponderID(componentAt.getComponentAt(0)).isResponderIdFor(this.d[0])) {
                throw new IOException("Initialization failed. Response not created by this generator!");
            }
            for (int i = 1; i < componentAt.countComponents(); i++) {
                ASN1Object componentAt2 = componentAt.getComponentAt(i);
                if (componentAt2.isA(ASN.CON_SPEC)) {
                    SEQUENCE sequence = (SEQUENCE) componentAt2.getValue();
                    for (int i2 = 0; i2 < sequence.countComponents(); i2++) {
                        this.e.addElement(new SingleResponse(sequence.getComponentAt(i2)));
                    }
                } else {
                    for (int i3 = 0; i3 < componentAt2.countComponents(); i3++) {
                        this.f.addElement(new Name(componentAt2.getComponentAt(i3)));
                    }
                }
            }
            byte[] bArr = (byte[]) asn1.getComponentAt(1).getValue();
            AlgorithmID algorithmID = AlgorithmID.sha1WithRSAEncryption;
            PrivateKey privateKey = this.c;
            if (!(privateKey instanceof RSAPrivateKey)) {
                if (!(privateKey instanceof DSAPrivateKey)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Cannot verify basic response. Unknown key algorithm: ");
                    stringBuffer.append(this.c.getAlgorithm());
                    throw new IOException(stringBuffer.toString());
                }
                algorithmID = AlgorithmID.dsa;
            }
            Signature signatureInstance = algorithmID.getSignatureInstance();
            signatureInstance.initVerify(this.d[0].getPublicKey());
            signatureInstance.update(firstObject);
            if (!signatureInstance.verify(bArr)) {
                throw new IOException("Cannot init generator. Signture verification error!");
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void printDebug(boolean z) {
        setDebugStream(z ? System.out : null);
    }

    public void removeAllCertIssuers() {
        this.f.removeAllElements();
    }

    public void removeAllSingleResponses() {
        this.e.removeAllElements();
    }

    public boolean removeCertificateIssuer(Name name) {
        return this.f.removeElement(name);
    }

    public SingleResponse removeSingleResponse(ReqCert reqCert) {
        Enumeration enumerationElements = this.e.elements();
        while (enumerationElements.hasMoreElements()) {
            SingleResponse singleResponse = (SingleResponse) enumerationElements.nextElement();
            if (singleResponse.isResponseFor(reqCert) && this.e.removeElement(singleResponse)) {
                return singleResponse;
            }
        }
        return null;
    }

    public void setDebugStream(OutputStream outputStream) {
        if (outputStream == null) {
            this.a = null;
        } else {
            this.a = new PrintWriter(outputStream, true);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Responder: ");
        stringBuffer2.append(this.d[0].getSubjectDN());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Cert Issuers: ");
        stringBuffer3.append(this.f.size());
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("Cached responses: ");
        stringBuffer4.append(this.e.size());
        stringBuffer.append(stringBuffer4.toString());
        return stringBuffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        try {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(new ResponderID((Name) this.d[0].getSubjectDN()).toASN1Object());
            if (this.f.size() > 0) {
                Enumeration enumerationElements = this.f.elements();
                SEQUENCE sequence2 = new SEQUENCE();
                while (enumerationElements.hasMoreElements()) {
                    sequence2.addComponent(((Name) enumerationElements.nextElement()).toASN1Object());
                }
                sequence.addComponent(sequence2);
            }
            if (this.e.size() > 0) {
                Enumeration enumerationElements2 = this.e.elements();
                SEQUENCE sequence3 = new SEQUENCE();
                while (enumerationElements2.hasMoreElements()) {
                    sequence3.addComponent(((SingleResponse) enumerationElements2.nextElement()).toASN1Object());
                }
                sequence.addComponent(new CON_SPEC(0, sequence3));
            }
            ASN1 asn1 = new ASN1(sequence);
            AlgorithmID algorithmID = AlgorithmID.sha1WithRSAEncryption;
            PrivateKey privateKey = this.c;
            if (!(privateKey instanceof RSAPrivateKey)) {
                if (!(privateKey instanceof DSAPrivateKey)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Cannot sign basic response. Unknown key algorithm: ");
                    stringBuffer.append(this.c.getAlgorithm());
                    throw new IOException(stringBuffer.toString());
                }
                algorithmID = AlgorithmID.dsa;
            }
            Signature signatureInstance = algorithmID.getSignatureInstance();
            signatureInstance.initSign(this.c);
            signatureInstance.update(asn1.toByteArray());
            SEQUENCE sequence4 = new SEQUENCE();
            sequence4.addComponent(asn1.toASN1Object());
            sequence4.addComponent(new BIT_STRING(signatureInstance.sign()));
            DerCoder.encodeTo(sequence4, outputStream);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}

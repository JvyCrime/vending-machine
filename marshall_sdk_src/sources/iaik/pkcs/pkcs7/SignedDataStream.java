package iaik.pkcs.pkcs7;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Attribute;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.utils.CryptoUtils;
import iaik.utils.EOFListener;
import iaik.utils.InternalErrorException;
import iaik.utils.NotifyEOFInputStream;
import iaik.utils.Util;
import iaik.x509.X509CRL;
import iaik.x509.X509Certificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class SignedDataStream implements ContentStream, iaik.pkcs.pkcs7.a, EOFListener {
    public static final int EXPLICIT = 2;
    public static final int IMPLICIT = 1;
    static Class j;
    static Class k;
    protected int block_size;
    protected X509Certificate[] certificates;
    protected ContentInfoStream content_info;
    protected ObjectID content_type;
    protected X509CRL[] crls;
    DigestVector h;
    boolean i;
    protected InputStream input_stream;
    protected int mode;
    protected Vector signer_infos;
    protected DerInputStream this_object;
    protected int version;

    class DigestVector extends Vector {
        private static final long serialVersionUID = -2696408453792159702L;
        private final SignedDataStream a;

        DigestVector(SignedDataStream signedDataStream) {
            this.a = signedDataStream;
        }

        DigestVector(SignedDataStream signedDataStream, AlgorithmID[] algorithmIDArr) {
            this.a = signedDataStream;
            a(algorithmIDArr);
        }

        private a d(AlgorithmID algorithmID) throws NoSuchAlgorithmException {
            for (int i = 0; i < this.elementCount; i++) {
                a aVar = (a) this.elementData[i];
                if (aVar.equals(algorithmID)) {
                    return aVar;
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No such MessageDigest: ");
            stringBuffer.append(algorithmID.getName());
            throw new NoSuchAlgorithmException(stringBuffer.toString());
        }

        InputStream a(InputStream inputStream, boolean z) throws NoSuchAlgorithmException {
            for (int i = 0; i < this.elementCount; i++) {
                a aVar = (a) this.elementData[i];
                if (z || aVar.b == null) {
                    aVar.b = aVar.a.getMessageDigestInstance();
                    inputStream = new DigestInputStream(inputStream, aVar.b);
                }
            }
            return inputStream;
        }

        void a(AlgorithmID algorithmID) {
            if (c(algorithmID)) {
                return;
            }
            addElement(new a(this.a, algorithmID));
        }

        void a(AlgorithmID algorithmID, byte[] bArr) {
            addElement(new a(this.a, algorithmID, bArr));
        }

        void a(byte[] bArr) throws NoSuchAlgorithmException {
            for (int i = 0; i < this.elementCount; i++) {
                a aVar = (a) this.elementData[i];
                aVar.c = aVar.a.getMessageDigestInstance().digest(bArr);
            }
        }

        void a(AlgorithmID[] algorithmIDArr) {
            for (AlgorithmID algorithmID : algorithmIDArr) {
                a(algorithmID);
            }
        }

        AlgorithmID[] a() {
            AlgorithmID[] algorithmIDArr = new AlgorithmID[this.elementCount];
            for (int i = 0; i < this.elementCount; i++) {
                algorithmIDArr[i] = ((a) this.elementData[i]).a;
            }
            return algorithmIDArr;
        }

        void b(AlgorithmID algorithmID, byte[] bArr) throws NoSuchAlgorithmException {
            d(algorithmID).a(bArr);
        }

        byte[] b(AlgorithmID algorithmID) throws NoSuchAlgorithmException {
            return d(algorithmID).a();
        }

        boolean c(AlgorithmID algorithmID) {
            try {
                d(algorithmID);
                return true;
            } catch (Exception unused) {
                return false;
            }
        }
    }

    class a {
        AlgorithmID a;
        MessageDigest b;
        byte[] c;
        private final SignedDataStream d;

        a(SignedDataStream signedDataStream, AlgorithmID algorithmID) {
            this.d = signedDataStream;
            this.a = algorithmID;
        }

        a(SignedDataStream signedDataStream, AlgorithmID algorithmID, byte[] bArr) {
            this.d = signedDataStream;
            this.a = algorithmID;
            this.c = bArr;
        }

        void a(byte[] bArr) {
            this.c = bArr;
        }

        byte[] a() {
            if (this.c == null) {
                if (this.b == null) {
                    throw new InternalErrorException("MessageDigest not initialized yet!");
                }
                try {
                    this.d.input_stream.available();
                    this.c = this.b.digest();
                } catch (Exception unused) {
                    throw new InternalErrorException("InputStream not set yet!");
                }
            }
            return this.c;
        }

        public boolean equals(Object obj) {
            try {
                if (obj instanceof a) {
                    return this.a.getImplementationName().equals(((a) obj).a.getImplementationName());
                }
                if (!(obj instanceof AlgorithmID)) {
                    return false;
                }
                return this.a.getImplementationName().equals(((AlgorithmID) obj).getImplementationName());
            } catch (Exception unused) {
                return false;
            }
        }

        public int hashCode() {
            return this.a.getAlgorithm().hashCode();
        }
    }

    protected SignedDataStream() {
        this.version = 1;
        this.h = new DigestVector(this);
        this.signer_infos = new Vector();
        this.block_size = 2048;
        this.mode = 1;
        this.i = true;
    }

    public SignedDataStream(ObjectID objectID) {
        this();
        this.mode = 2;
        this.content_type = objectID;
    }

    public SignedDataStream(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public SignedDataStream(InputStream inputStream, int i) {
        this();
        this.content_type = ObjectID.pkcs7_data;
        this.input_stream = inputStream;
        this.mode = i;
    }

    public SignedDataStream(InputStream inputStream, AlgorithmID[] algorithmIDArr) throws IOException {
        this();
        this.input_stream = inputStream;
        this.mode = 2;
        DigestVector digestVector = new DigestVector(this, algorithmIDArr);
        this.h = digestVector;
        try {
            this.input_stream = digestVector.a(this.input_stream, true);
        } catch (NoSuchAlgorithmException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No implementation for hash algorithm: ");
            stringBuffer.append(e.getMessage());
            throw new IOException(stringBuffer.toString());
        }
    }

    private int a(X509Certificate x509Certificate) {
        Enumeration enumerationElements = this.signer_infos.elements();
        int i = 0;
        while (enumerationElements.hasMoreElements()) {
            if (((SignerInfo) enumerationElements.nextElement()).getIssuerAndSerialNumber().isIssuerOf(x509Certificate)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public void addSignerInfo(SignerInfo signerInfo) throws NoSuchAlgorithmException {
        signerInfo.a = this;
        AlgorithmID digestAlgorithm = signerInfo.getDigestAlgorithm();
        if (!this.h.c(digestAlgorithm)) {
            this.h.a(digestAlgorithm);
        }
        if (this.mode == 2) {
            this.input_stream = this.h.a(this.input_stream, false);
        }
        this.signer_infos.addElement(signerInfo);
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        this.i = false;
        if (!(inputStream instanceof DerInputStream)) {
            inputStream = new DerInputStream(inputStream);
        }
        DerInputStream sequence = ((DerInputStream) inputStream).readSequence();
        this.this_object = sequence;
        this.version = sequence.readInteger().intValue();
        try {
            DigestVector digestVector = this.h;
            ASN1Object aSN1ObjectDecode = DerCoder.decode(this.this_object);
            Class clsClass$ = j;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.asn1.structures.AlgorithmID");
                j = clsClass$;
            }
            digestVector.a((AlgorithmID[]) ASN.parseSequenceOf(aSN1ObjectDecode, clsClass$));
            ContentInfoStream contentInfoStream = new ContentInfoStream(this.this_object);
            this.content_info = contentInfoStream;
            this.content_type = contentInfoStream.getContentType();
            if (this.mode == 2 || !this.content_info.hasContent()) {
                if (this.content_info.hasContent()) {
                    try {
                        do {
                        } while (((DataStream) this.content_info.getContent()).getInputStream().read(new byte[1024]) > -1);
                    } catch (Throwable unused) {
                    }
                }
                this.mode = 2;
                notifyEOF();
                return;
            }
            if (!this.content_type.equals(ObjectID.pkcs7_data)) {
                throw new IOException("SignedData only for content type Data at this time!");
            }
            InputStream inputStream2 = ((DataStream) this.content_info.getContent()).getInputStream();
            this.input_stream = inputStream2;
            try {
                this.input_stream = this.h.a(inputStream2, true);
                NotifyEOFInputStream notifyEOFInputStream = new NotifyEOFInputStream(this.input_stream);
                this.input_stream = notifyEOFInputStream;
                notifyEOFInputStream.addEOFListener(this);
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for hash algorithm: ");
                stringBuffer.append(e.getMessage());
                throw new IOException(stringBuffer.toString());
            }
        } catch (CodingException unused2) {
            throw new IOException("Error parsing digest algorithms!");
        }
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public int getBlockSize() {
        return this.block_size;
    }

    public X509CRL[] getCRLs() {
        return this.crls;
    }

    public X509Certificate getCertificate(IssuerAndSerialNumber issuerAndSerialNumber) throws PKCSException {
        if (this.certificates == null) {
            throw new PKCSException("Certificate not found!");
        }
        int i = 0;
        while (true) {
            X509Certificate[] x509CertificateArr = this.certificates;
            if (i >= x509CertificateArr.length || issuerAndSerialNumber.isIssuerOf(x509CertificateArr[i])) {
                break;
            }
            i++;
        }
        X509Certificate[] x509CertificateArr2 = this.certificates;
        if (i != x509CertificateArr2.length) {
            return x509CertificateArr2[i];
        }
        throw new PKCSException("Certificate not found!");
    }

    public X509Certificate[] getCertificates() {
        return this.certificates;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ObjectID getContentType() {
        return ObjectID.pkcs7_signedData;
    }

    public AlgorithmID[] getDigestAlgorithms() {
        return this.h.a();
    }

    public InputStream getInputStream() {
        return this.input_stream;
    }

    @Override // iaik.pkcs.pkcs7.a
    public byte[] getMessageDigest(AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        return this.h.b(algorithmID);
    }

    public int getMode() {
        return this.mode;
    }

    public byte[] getSignedDigest(int i) throws PKCSException {
        Attribute[] authenticatedAttributes = ((SignerInfo) this.signer_infos.elementAt(i)).getAuthenticatedAttributes();
        if (authenticatedAttributes == null) {
            throw new PKCSException("No authenticated attributes included in SignerInfo!");
        }
        for (int i2 = 0; i2 < authenticatedAttributes.length; i2++) {
            if (authenticatedAttributes[i2].getType().equals(ObjectID.messageDigest)) {
                return (byte[]) authenticatedAttributes[i2].getValue()[0].getValue();
            }
        }
        throw new PKCSException("Message digest not included in authenticated attributes!");
    }

    public SignerInfo[] getSignerInfos() {
        Vector vector = this.signer_infos;
        Class clsClass$ = k;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.pkcs.pkcs7.SignerInfo");
            k = clsClass$;
        }
        return (SignerInfo[]) Util.toArray(vector, clsClass$);
    }

    public int getVersion() {
        return this.version;
    }

    @Override // iaik.utils.EOFListener
    public void notifyEOF() throws IOException {
        while (this.this_object.nextIsContextSpecific()) {
            try {
                int contextSpecific = this.this_object.readContextSpecific(17);
                DerInputStream set = this.this_object.readSet();
                if (contextSpecific != 0) {
                    if (contextSpecific != 1) {
                        if (contextSpecific != 2) {
                            if (contextSpecific != 3) {
                            }
                        }
                    }
                    Vector vector = new Vector();
                    while (set.nextTag() > -1) {
                        try {
                            vector.addElement(new X509CRL(set));
                        } catch (CRLException e) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("Cannot parse crl: ");
                            stringBuffer.append(e.getMessage());
                            throw new IOException(stringBuffer.toString());
                        }
                    }
                    X509CRL[] x509crlArr = new X509CRL[vector.size()];
                    this.crls = x509crlArr;
                    vector.copyInto(x509crlArr);
                }
                Vector vector2 = new Vector();
                while (set.nextTag() > -1) {
                    try {
                        vector2.addElement(new X509Certificate(set));
                    } catch (CertificateException e2) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Cannot parse certificate: ");
                        stringBuffer2.append(e2.getMessage());
                        throw new IOException(stringBuffer2.toString());
                    }
                }
                X509Certificate[] x509CertificateArr = new X509Certificate[vector2.size()];
                this.certificates = x509CertificateArr;
                vector2.copyInto(x509CertificateArr);
            } catch (CodingException e3) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Error parsing Object! ");
                stringBuffer3.append(e3.getMessage());
                throw new IOException(stringBuffer3.toString());
            }
        }
        ASN1Object aSN1ObjectDecode = DerCoder.decode(this.this_object);
        Class clsClass$ = k;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.pkcs.pkcs7.SignerInfo");
            k = clsClass$;
        }
        Vector vector3 = Util.getVector(ASN.parseSequenceOf(aSN1ObjectDecode, clsClass$));
        this.signer_infos = vector3;
        for (Object obj : Util.toArray(vector3)) {
            ((SignerInfo) obj).a = this;
        }
        this.this_object.readEOC();
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void setBlockSize(int i) {
        this.block_size = i;
    }

    public void setCRLs(X509CRL[] x509crlArr) {
        this.crls = x509crlArr;
    }

    public void setCertificates(X509Certificate[] x509CertificateArr) {
        this.certificates = x509CertificateArr;
    }

    public void setInputStream(InputStream inputStream) {
        this.input_stream = inputStream;
    }

    public void setMessageDigest(AlgorithmID algorithmID, byte[] bArr) throws NoSuchAlgorithmException {
        this.h.b(algorithmID, bArr);
    }

    public void setSignerInfos(SignerInfo[] signerInfoArr) throws NoSuchAlgorithmException {
        for (SignerInfo signerInfo : signerInfoArr) {
            addSignerInfo(signerInfo);
        }
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ASN1Object toASN1Object() throws PKCSException {
        return toASN1Object(-1);
    }

    protected ASN1Object toASN1Object(int i) throws PKCSException {
        if (i <= 0) {
            i = this.block_size;
        }
        if (this.signer_infos == null) {
            throw new PKCSException("No SignerInfo specified!");
        }
        if (this.mode == 1) {
            try {
                this.input_stream = this.h.a(this.input_stream, true);
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for hash algorithm: ");
                stringBuffer.append(e.getMessage());
                throw new PKCSException(stringBuffer.toString());
            }
        }
        if (this.mode != 1 || this.input_stream == null) {
            this.content_info = new ContentInfoStream(ObjectID.pkcs7_data);
        } else {
            this.content_info = new ContentInfoStream(new DataStream(this.input_stream, i));
        }
        SEQUENCE sequence = new SEQUENCE(true);
        try {
            sequence.addComponent(new INTEGER(this.version));
            sequence.addComponent(ASN.createSetOf(this.h.a()));
            sequence.addComponent(this.content_info.toASN1Object());
            X509Certificate[] x509CertificateArr = this.certificates;
            if (x509CertificateArr != null) {
                sequence.addComponent(new CON_SPEC(0, ASN.createSetOf(x509CertificateArr), true));
            }
            X509CRL[] x509crlArr = this.crls;
            if (x509crlArr != null) {
                sequence.addComponent(new CON_SPEC(1, ASN.createSetOf(x509crlArr), true));
            }
            sequence.addComponent(ASN.createSetOf(this.signer_infos));
            return sequence;
        } catch (CodingException e2) {
            throw new PKCSException(e2.toString());
        }
    }

    public String toString() {
        return toString(false);
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.version);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        AlgorithmID[] algorithmIDArrA = this.h.a();
        if (algorithmIDArrA.length > 0) {
            stringBuffer.append("digestAlgorithms: ");
            for (AlgorithmID algorithmID : algorithmIDArrA) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append(algorithmID.getName());
                stringBuffer3.append(",");
                stringBuffer.append(stringBuffer3.toString());
            }
            stringBuffer.setLength(stringBuffer.length() - 1);
            stringBuffer.append("\n");
        }
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("ContentInfo: {\n");
        ContentInfoStream contentInfoStream = this.content_info;
        stringBuffer4.append(contentInfoStream != null ? contentInfoStream.toString(z) : "");
        stringBuffer.append(stringBuffer4.toString());
        stringBuffer.append("\n}\n");
        if (this.certificates != null) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("certificates: ");
            stringBuffer5.append(this.certificates.length);
            stringBuffer5.append("\n");
            stringBuffer.append(stringBuffer5.toString());
        }
        if (this.crls != null) {
            StringBuffer stringBuffer6 = new StringBuffer();
            stringBuffer6.append("crls: ");
            stringBuffer6.append(this.crls.length);
            stringBuffer6.append("\n");
            stringBuffer.append(stringBuffer6.toString());
        }
        if (z) {
            Enumeration enumerationElements = this.signer_infos.elements();
            int i = 1;
            while (enumerationElements.hasMoreElements()) {
                StringBuffer stringBuffer7 = new StringBuffer();
                stringBuffer7.append("signerInfo ");
                stringBuffer7.append(i);
                stringBuffer7.append(": {\n");
                stringBuffer.append(stringBuffer7.toString());
                StringBuffer stringBuffer8 = new StringBuffer();
                stringBuffer8.append(((SignerInfo) enumerationElements.nextElement()).toString(true));
                stringBuffer8.append("}");
                stringBuffer.append(stringBuffer8.toString());
                i++;
            }
        } else {
            StringBuffer stringBuffer9 = new StringBuffer();
            stringBuffer9.append("signerInfos: ");
            stringBuffer9.append(this.signer_infos.size());
            stringBuffer9.append("\n");
            stringBuffer.append(stringBuffer9.toString());
        }
        return stringBuffer.toString();
    }

    public SignerInfo verify(X509Certificate x509Certificate) throws SignatureException {
        int iA = a(x509Certificate);
        if (iA == -1) {
            throw new SignatureException("Cannot do verification. No signer for this certificate!");
        }
        verify(x509Certificate.getPublicKey(), iA);
        return (SignerInfo) this.signer_infos.elementAt(iA);
    }

    public X509Certificate verify(int i) throws SignatureException {
        if (i < 0 || i >= this.signer_infos.size()) {
            throw new SignatureException("SignerInfo does not exist. Wrong index.");
        }
        try {
            X509Certificate certificate = getCertificate(((SignerInfo) this.signer_infos.elementAt(i)).getIssuerAndSerialNumber());
            if (certificate == null) {
                throw new SignatureException("Certificate for verifying the signature not found!");
            }
            verify(certificate.getPublicKey(), i);
            return certificate;
        } catch (Exception e) {
            throw new SignatureException(e.getMessage());
        }
    }

    public void verify(PublicKey publicKey, int i) throws SignatureException {
        if (i < 0 || i >= this.signer_infos.size()) {
            throw new SignatureException("SignerInfo does not exist. Wrong index.");
        }
        Attribute[] authenticatedAttributes = ((SignerInfo) this.signer_infos.elementAt(i)).getAuthenticatedAttributes();
        try {
            AlgorithmID digestAlgorithm = ((SignerInfo) this.signer_infos.elementAt(i)).getDigestAlgorithm();
            byte[] messageDigest = getMessageDigest(digestAlgorithm);
            MessageDigest messageDigestInstance = null;
            if (authenticatedAttributes != null) {
                if (!CryptoUtils.secureEqualsBlock(messageDigest, getSignedDigest(i))) {
                    throw new SignatureException("Signature verification error: message hash!");
                }
                messageDigestInstance = digestAlgorithm.getMessageDigestInstance();
                messageDigest = this.version == 2 ? messageDigestInstance.digest(DerCoder.encode(ASN.createSequenceOf(authenticatedAttributes))) : messageDigestInstance.digest(DerCoder.encode(ASN.createSetOf(authenticatedAttributes, this.i)));
            }
            byte[] digest = ((SignerInfo) this.signer_infos.elementAt(i)).getDigest(publicKey);
            if (CryptoUtils.secureEqualsBlock(messageDigest, digest)) {
                return;
            }
            boolean z = false;
            if (!this.i && this.version != 2 && CryptoUtils.secureEqualsBlock(messageDigestInstance.digest(DerCoder.encode(ASN.createSetOf(authenticatedAttributes, true))), digest)) {
                z = true;
            }
            if (!z) {
                throw new SignatureException("Signature verification error: signature value!");
            }
        } catch (SignatureException e) {
            throw e;
        } catch (Exception e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error verifying the signature: ");
            stringBuffer.append(e2.getMessage());
            throw new SignatureException(stringBuffer.toString());
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        try {
            DerCoder.encodeTo(toASN1Object(), outputStream);
        } catch (PKCSException e) {
            throw new IOException(e.toString());
        }
    }

    public void writeTo(OutputStream outputStream, int i) throws IOException {
        try {
            DerCoder.encodeTo(toASN1Object(i), outputStream);
        } catch (PKCSException e) {
            throw new IOException(e.toString());
        }
    }
}

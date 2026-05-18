package iaik.pkcs.pkcs7;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.pkcs.pkcs7.SignedDataStream;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public class SignedData extends SignedDataStream implements Content {
    static Class g;
    private ContentInfo a;
    byte[] f;

    protected SignedData() {
        this.block_size = -1;
    }

    public SignedData(ASN1Object aSN1Object) throws PKCSParsingException {
        this();
        decode(aSN1Object);
    }

    public SignedData(ObjectID objectID) {
        this();
        this.content_type = objectID;
    }

    public SignedData(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public SignedData(byte[] bArr, int i) {
        this();
        this.f = bArr;
        this.mode = i;
        this.content_type = ObjectID.pkcs7_data;
    }

    public SignedData(byte[] bArr, AlgorithmID[] algorithmIDArr) throws NoSuchAlgorithmException {
        this();
        this.f = bArr;
        this.mode = 2;
        this.h.a(algorithmIDArr);
        setupMessageDigests();
    }

    public SignedData(byte[] bArr, AlgorithmID[] algorithmIDArr, int i) throws NoSuchAlgorithmException {
        this();
        this.f = bArr;
        this.mode = 2;
        if (i == 2) {
            this.version = 2;
        } else if (i != 1) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid version: ");
            stringBuffer.append(i);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.h.a(algorithmIDArr);
        setupMessageDigests();
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream
    public void addSignerInfo(SignerInfo signerInfo) throws NoSuchAlgorithmException {
        signerInfo.a = this;
        AlgorithmID digestAlgorithm = signerInfo.getDigestAlgorithm();
        if (!this.h.c(digestAlgorithm)) {
            if (this.f != null) {
                MessageDigest messageDigestInstance = digestAlgorithm.getMessageDigestInstance();
                this.h.a(digestAlgorithm, this.version == 2 ? messageDigestInstance.digest(DerCoder.encode(new OCTET_STRING(this.f))) : messageDigestInstance.digest(this.f));
            } else {
                this.h.a(digestAlgorithm);
            }
        }
        this.signer_infos.addElement(signerInfo);
    }

    public void decode(ASN1Object aSN1Object) throws PKCSParsingException {
        try {
            decode(new ByteArrayInputStream(DerCoder.encode(aSN1Object)));
        } catch (IOException e) {
            throw new PKCSParsingException(e.getMessage());
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream, iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        this.i = false;
        if (!(inputStream instanceof DerInputStream)) {
            inputStream = new DerInputStream(inputStream);
        }
        this.this_object = ((DerInputStream) inputStream).readSequence();
        this.version = this.this_object.readInteger().intValue();
        try {
            SignedDataStream.DigestVector digestVector = this.h;
            ASN1Object aSN1ObjectDecode = DerCoder.decode(this.this_object);
            Class clsClass$ = g;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.asn1.structures.AlgorithmID");
                g = clsClass$;
            }
            digestVector.a((AlgorithmID[]) ASN.parseSequenceOf(aSN1ObjectDecode, clsClass$));
            ContentInfo contentInfo = new ContentInfo(this.this_object);
            this.a = contentInfo;
            this.content_type = contentInfo.getContentType();
            if (!this.a.hasContent()) {
                this.mode = 2;
                notifyEOF();
            } else {
                if (!this.content_type.equals(ObjectID.pkcs7_data)) {
                    throw new PKCSParsingException("SignedData only for content type Data at this time!");
                }
                this.f = ((Data) this.a.getContent()).getData();
                try {
                    setupMessageDigests();
                    notifyEOF();
                } catch (NoSuchAlgorithmException e) {
                    throw new PKCSParsingException(e.getMessage());
                }
            }
        } catch (CodingException unused) {
            throw new IOException("Error parsing digest algorithms!");
        }
    }

    public byte[] getContent() {
        return this.f;
    }

    public byte[] getEncoded() throws PKCSException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DerCoder.encodeTo(toASN1Object(), byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new PKCSException(e.getMessage());
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream
    public InputStream getInputStream() {
        if (this.f == null) {
            return null;
        }
        return new ByteArrayInputStream(this.f);
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream
    public void setInputStream(InputStream inputStream) {
        this.input_stream = inputStream;
        if (inputStream != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Util.copyStream(this.input_stream, byteArrayOutputStream, null);
                this.f = byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                throw new InternalErrorException("Error reading from stream!", e);
            }
        }
    }

    public void setVersion(int i) {
        if (i == 1 || i == 2) {
            this.version = i;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid version number: ");
        stringBuffer.append(i);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    protected void setupMessageDigests() throws NoSuchAlgorithmException {
        if (this.version == 2) {
            this.h.a(DerCoder.encode(new OCTET_STRING(this.f)));
        } else {
            this.h.a(this.f);
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream
    protected ASN1Object toASN1Object(int i) throws PKCSException {
        ASN1Object aSN1ObjectCreateSetOf;
        if (i <= 0) {
            i = this.block_size;
        }
        if (this.signer_infos == null) {
            throw new PKCSException("No SignerInfo specified!");
        }
        this.a = (this.mode != 1 || this.f == null) ? new ContentInfo(ObjectID.pkcs7_data) : new ContentInfo(new Data(this.f, i));
        SEQUENCE sequence = new SEQUENCE(i > 0);
        try {
            sequence.addComponent(new INTEGER(this.version));
            if (this.version == 2) {
                sequence.addComponent(ASN.createSequenceOf(this.h.a()));
                sequence.addComponent(this.a.toASN1Object());
                if (this.certificates != null) {
                    sequence.addComponent(new CON_SPEC(2, ASN.createSequenceOf(this.certificates), true));
                }
                if (this.crls != null) {
                    sequence.addComponent(new CON_SPEC(3, ASN.createSequenceOf(this.crls), true));
                }
                Enumeration enumerationElements = this.signer_infos.elements();
                while (enumerationElements.hasMoreElements()) {
                    ((SignerInfo) enumerationElements.nextElement()).b = 2;
                }
                aSN1ObjectCreateSetOf = ASN.createSequenceOf(this.signer_infos);
            } else {
                sequence.addComponent(ASN.createSetOf(this.h.a()));
                sequence.addComponent(this.a.toASN1Object());
                if (this.certificates != null) {
                    sequence.addComponent(new CON_SPEC(0, ASN.createSetOf(this.certificates), true));
                }
                if (this.crls != null) {
                    sequence.addComponent(new CON_SPEC(1, ASN.createSetOf(this.crls), true));
                }
                aSN1ObjectCreateSetOf = ASN.createSetOf(this.signer_infos);
            }
            sequence.addComponent(aSN1ObjectCreateSetOf);
            return sequence;
        } catch (CodingException e) {
            throw new PKCSException(e.toString());
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream, iaik.pkcs.pkcs7.ContentStream
    public String toString(boolean z) {
        String str;
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
        if (this.a == null) {
            str = "ContentInfo: yet not set\n";
        } else {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("ContentInfo: {\n");
            stringBuffer4.append(this.a.toString(z));
            stringBuffer.append(stringBuffer4.toString());
            str = "\n}\n";
        }
        stringBuffer.append(str);
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
}

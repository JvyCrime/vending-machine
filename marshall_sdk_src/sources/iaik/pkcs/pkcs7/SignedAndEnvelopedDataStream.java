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
import iaik.pkcs.pkcs7.SignedDataStream;
import iaik.utils.CryptoUtils;
import iaik.utils.NotifyEOFInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Enumeration;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class SignedAndEnvelopedDataStream extends SignedDataStream {
    static Class d;
    static Class e;
    RecipientInfo[] a;
    EncryptedContentInfoStream b;
    SecretKey c;

    protected SignedAndEnvelopedDataStream() {
        this.version = 1;
    }

    public SignedAndEnvelopedDataStream(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public SignedAndEnvelopedDataStream(InputStream inputStream, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        this(inputStream, algorithmID, -1);
    }

    public SignedAndEnvelopedDataStream(InputStream inputStream, AlgorithmID algorithmID, int i) throws NoSuchAlgorithmException {
        this();
        this.input_stream = inputStream;
        EncryptedContentInfoStream encryptedContentInfoStream = new EncryptedContentInfoStream(ObjectID.pkcs7_data, this.input_stream);
        this.b = encryptedContentInfoStream;
        this.c = encryptedContentInfoStream.setupCipher(algorithmID, i);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public void addRecipientInfo(RecipientInfo recipientInfo) {
        if (recipientInfo == null) {
            return;
        }
        RecipientInfo[] recipientInfoArr = this.a;
        if (recipientInfoArr == null) {
            this.a = new RecipientInfo[]{recipientInfo};
            return;
        }
        RecipientInfo[] recipientInfoArr2 = new RecipientInfo[recipientInfoArr.length + 1];
        System.arraycopy(recipientInfoArr, 0, recipientInfoArr2, 0, recipientInfoArr.length);
        recipientInfoArr2[this.a.length] = recipientInfo;
        this.a = recipientInfoArr2;
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
            ASN1Object aSN1ObjectDecode = DerCoder.decode(this.this_object);
            Class clsClass$ = d;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.pkcs.pkcs7.RecipientInfo");
                d = clsClass$;
            }
            this.a = (RecipientInfo[]) ASN.parseSequenceOf(aSN1ObjectDecode, clsClass$);
            SignedDataStream.DigestVector digestVector = this.h;
            ASN1Object aSN1ObjectDecode2 = DerCoder.decode(this.this_object);
            Class clsClass$2 = e;
            if (clsClass$2 == null) {
                clsClass$2 = class$("iaik.asn1.structures.AlgorithmID");
                e = clsClass$2;
            }
            digestVector.a((AlgorithmID[]) ASN.parseSequenceOf(aSN1ObjectDecode2, clsClass$2));
            EncryptedContentInfoStream encryptedContentInfoStream = new EncryptedContentInfoStream(this.this_object);
            this.b = encryptedContentInfoStream;
            this.content_type = encryptedContentInfoStream.getContentType();
            if (this.b.hasContent()) {
                return;
            }
            notifyEOF();
        } catch (CodingException unused) {
            throw new PKCSParsingException("Error parsing recipient infos!");
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream, iaik.pkcs.pkcs7.ContentStream
    public ObjectID getContentType() {
        return ObjectID.pkcs7_signedAndEnvelopedData;
    }

    public EncryptedContentInfoStream getEncryptedContentInfo() {
        return this.b;
    }

    public RecipientInfo[] getRecipientInfos() {
        return this.a;
    }

    public void setRecipientInfos(RecipientInfo[] recipientInfoArr) {
        this.a = recipientInfoArr;
    }

    public void setupCipher(PrivateKey privateKey, int i) throws PKCSException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKeyDecryptKey = this.a[i].decryptKey(privateKey);
        this.c = secretKeyDecryptKey;
        this.b.setupCipher(secretKeyDecryptKey);
        this.input_stream = this.b.getInputStream();
        this.input_stream = this.h.a(this.input_stream, true);
        this.input_stream = new NotifyEOFInputStream(this.input_stream);
        ((NotifyEOFInputStream) this.input_stream).addEOFListener(this);
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream, iaik.pkcs.pkcs7.ContentStream
    public ASN1Object toASN1Object() throws PKCSException {
        return toASN1Object(-1);
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream
    protected ASN1Object toASN1Object(int i) throws PKCSException {
        if (i <= 0) {
            i = this.block_size;
        }
        int i2 = 0;
        while (true) {
            RecipientInfo[] recipientInfoArr = this.a;
            if (i2 >= recipientInfoArr.length) {
                break;
            }
            recipientInfoArr[i2].encryptKey(this.c);
            i2++;
        }
        if (this.signer_infos == null) {
            throw new PKCSException("No SignerInfo specified!");
        }
        if (this.mode == 1) {
            try {
                this.input_stream = this.h.a(this.input_stream, true);
            } catch (NoSuchAlgorithmException e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for hash algorithm: ");
                stringBuffer.append(e2.getMessage());
                throw new PKCSException(stringBuffer.toString());
            }
        }
        this.b.f = this.input_stream;
        this.b.i = i;
        SEQUENCE sequence = new SEQUENCE(true);
        try {
            sequence.addComponent(new INTEGER(this.version));
            sequence.addComponent(ASN.createSetOf(this.a));
            sequence.addComponent(ASN.createSetOf(this.h.a()));
            sequence.addComponent(this.b.toASN1Object());
            if (this.certificates != null) {
                sequence.addComponent(new CON_SPEC(0, ASN.createSetOf(this.certificates), true));
            }
            if (this.crls != null) {
                sequence.addComponent(new CON_SPEC(1, ASN.createSetOf(this.crls), true));
            }
            sequence.addComponent(ASN.createSetOf(this.signer_infos));
            return sequence;
        } catch (CodingException e3) {
            throw new PKCSException(e3.toString());
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream
    public String toString() {
        return toString(false);
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream, iaik.pkcs.pkcs7.ContentStream
    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.version);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        if (this.a.length > 0) {
            int i = 0;
            while (i < this.a.length) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("\nRecipientInfo ");
                int i2 = i + 1;
                stringBuffer3.append(i2);
                stringBuffer3.append(":{\n");
                stringBuffer.append(stringBuffer3.toString());
                stringBuffer.append(this.a[i].toString());
                stringBuffer.append("\n}");
                i = i2;
            }
        }
        AlgorithmID[] algorithmIDArrA = this.h.a();
        if (algorithmIDArrA.length > 0) {
            stringBuffer.append("digestAlgorithms: ");
            for (AlgorithmID algorithmID : algorithmIDArrA) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append(algorithmID.getName());
                stringBuffer4.append(",");
                stringBuffer.append(stringBuffer4.toString());
            }
            stringBuffer.setLength(stringBuffer.length() - 1);
            stringBuffer.append("\n");
        }
        stringBuffer.append("\nEncryptedContentInfo: {\n");
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append(this.b.toString());
        stringBuffer5.append("}");
        stringBuffer.append(stringBuffer5.toString());
        stringBuffer.append("\n\n");
        if (this.certificates != null) {
            StringBuffer stringBuffer6 = new StringBuffer();
            stringBuffer6.append("certificates: ");
            stringBuffer6.append(this.certificates.length);
            stringBuffer6.append("\n");
            stringBuffer.append(stringBuffer6.toString());
        }
        if (this.crls != null) {
            StringBuffer stringBuffer7 = new StringBuffer();
            stringBuffer7.append("crls: ");
            stringBuffer7.append(this.crls.length);
            stringBuffer7.append("\n");
            stringBuffer.append(stringBuffer7.toString());
        }
        if (z) {
            Enumeration enumerationElements = this.signer_infos.elements();
            int i3 = 1;
            while (enumerationElements.hasMoreElements()) {
                StringBuffer stringBuffer8 = new StringBuffer();
                stringBuffer8.append("signerInfo ");
                stringBuffer8.append(i3);
                stringBuffer8.append(": {\n");
                stringBuffer.append(stringBuffer8.toString());
                StringBuffer stringBuffer9 = new StringBuffer();
                stringBuffer9.append(((SignerInfo) enumerationElements.nextElement()).toString(true));
                stringBuffer9.append("}");
                stringBuffer.append(stringBuffer9.toString());
                i3++;
            }
        } else {
            StringBuffer stringBuffer10 = new StringBuffer();
            stringBuffer10.append("signerInfos: ");
            stringBuffer10.append(this.signer_infos.size());
            stringBuffer10.append("\n");
            stringBuffer.append(stringBuffer10.toString());
        }
        return stringBuffer.toString();
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream
    public void verify(PublicKey publicKey, int i) throws SignatureException {
        AlgorithmParameterSpec ivParameterSpec;
        ASN1Object parameter;
        if (i < 0 || i >= this.signer_infos.size()) {
            throw new SignatureException("SignerInfo does not exist. Wrong index.");
        }
        SignerInfo signerInfo = (SignerInfo) this.signer_infos.elementAt(i);
        Attribute[] authenticatedAttributes = signerInfo.getAuthenticatedAttributes();
        try {
            AlgorithmID digestAlgorithm = signerInfo.getDigestAlgorithm();
            byte[] messageDigest = getMessageDigest(digestAlgorithm);
            if (authenticatedAttributes != null) {
                if (!CryptoUtils.secureEqualsBlock(messageDigest, getSignedDigest(i))) {
                    throw new SignatureException("Signature verification error!");
                }
                messageDigest = digestAlgorithm.getMessageDigestInstance().digest(DerCoder.encode(ASN.createSetOf(authenticatedAttributes)));
            }
            AlgorithmID contentEncryptionAlgorithm = this.b.getContentEncryptionAlgorithm();
            try {
                ivParameterSpec = contentEncryptionAlgorithm.getAlgorithmParameterSpec();
            } catch (InvalidAlgorithmParameterException unused) {
                ivParameterSpec = null;
            }
            if (ivParameterSpec == null && (parameter = contentEncryptionAlgorithm.getParameter()) != null && parameter.isA(ASN.OCTET_STRING)) {
                ivParameterSpec = new IvParameterSpec((byte[]) parameter.getValue());
            }
            Cipher cipherInstance = contentEncryptionAlgorithm.getCipherInstance();
            cipherInstance.init(2, this.c, ivParameterSpec, (SecureRandom) null);
            signerInfo.g = cipherInstance.doFinal(signerInfo.g);
            if (!CryptoUtils.secureEqualsBlock(messageDigest, signerInfo.getDigest(publicKey))) {
                throw new SignatureException("Signature verification error!");
            }
        } catch (Exception e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error verifying the signature: ");
            stringBuffer.append(e2.getMessage());
            throw new SignatureException(stringBuffer.toString());
        }
    }
}

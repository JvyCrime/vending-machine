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
import java.io.ByteArrayInputStream;
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
public class SignedAndEnvelopedData extends SignedData {
    static Class d;
    static Class e;
    RecipientInfo[] a;
    EncryptedContentInfo b;
    SecretKey c;

    protected SignedAndEnvelopedData() {
        this.version = 1;
    }

    public SignedAndEnvelopedData(ASN1Object aSN1Object) throws PKCSParsingException {
        this();
        decode(aSN1Object);
    }

    public SignedAndEnvelopedData(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public SignedAndEnvelopedData(byte[] bArr, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        this();
        this.f = bArr;
        this.content_type = ObjectID.pkcs7_data;
        EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo(ObjectID.pkcs7_data, bArr);
        this.b = encryptedContentInfo;
        this.c = encryptedContentInfo.setupCipher(algorithmID);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SignedAndEnvelopedData(byte[] bArr, AlgorithmID algorithmID, int i) throws NoSuchAlgorithmException {
        int i2 = (i == -1 || i > 2) ? i : -1;
        if (i != 1 && i != 2) {
            i = 1;
        }
        this(bArr, algorithmID, i2, i);
    }

    public SignedAndEnvelopedData(byte[] bArr, AlgorithmID algorithmID, int i, int i2) throws NoSuchAlgorithmException {
        this();
        this.f = bArr;
        this.content_type = ObjectID.pkcs7_data;
        this.b = new EncryptedContentInfo(ObjectID.pkcs7_data, bArr);
        if (i2 == 2) {
            this.version = 2;
            this.b.c = 1;
        } else if (i2 != 1) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid version number: ");
            stringBuffer.append(i2);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.c = this.b.setupCipher(algorithmID, i);
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

    @Override // iaik.pkcs.pkcs7.SignedData, iaik.pkcs.pkcs7.Content
    public void decode(ASN1Object aSN1Object) throws PKCSParsingException {
        try {
            decode(new ByteArrayInputStream(DerCoder.encode(aSN1Object)));
        } catch (IOException e2) {
            throw new PKCSParsingException(e2.getMessage());
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedData, iaik.pkcs.pkcs7.SignedDataStream, iaik.pkcs.pkcs7.ContentStream
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
            EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo();
            this.b = encryptedContentInfo;
            encryptedContentInfo.c = this.version - 1;
            this.b.decode(this.this_object);
            this.content_type = this.b.getContentType();
            notifyEOF();
        } catch (CodingException unused) {
            throw new PKCSParsingException("Error parsing recipient infos!");
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedData
    public byte[] getContent() {
        return this.b.getContent();
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream, iaik.pkcs.pkcs7.ContentStream
    public ObjectID getContentType() {
        return ObjectID.pkcs7_signedAndEnvelopedData;
    }

    public EncryptedContentInfo getEncryptedContentInfo() {
        return this.b;
    }

    @Override // iaik.pkcs.pkcs7.SignedData, iaik.pkcs.pkcs7.SignedDataStream
    public InputStream getInputStream() {
        return this.b.getInputStream();
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
        this.f = this.b.getContent();
        setupMessageDigests();
    }

    @Override // iaik.pkcs.pkcs7.SignedData, iaik.pkcs.pkcs7.SignedDataStream
    protected ASN1Object toASN1Object(int i) throws PKCSException {
        ASN1Object aSN1ObjectCreateSetOf;
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
        this.b.setBlockSize(i);
        SEQUENCE sequence = new SEQUENCE(i > 0);
        try {
            sequence.addComponent(new INTEGER(this.version));
            if (this.version == 2) {
                sequence.addComponent(ASN.createSequenceOf(this.a));
                sequence.addComponent(ASN.createSequenceOf(this.h.a()));
                sequence.addComponent(this.b.toASN1Object());
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
                sequence.addComponent(ASN.createSetOf(this.a));
                sequence.addComponent(ASN.createSetOf(this.h.a()));
                sequence.addComponent(this.b.toASN1Object());
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
        } catch (CodingException e2) {
            throw new PKCSException(e2.toString());
        }
    }

    @Override // iaik.pkcs.pkcs7.SignedDataStream
    public String toString() {
        return toString(false);
    }

    @Override // iaik.pkcs.pkcs7.SignedData, iaik.pkcs.pkcs7.SignedDataStream, iaik.pkcs.pkcs7.ContentStream
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
            byte[] bArr = signerInfo.g;
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
            byte[] digest = signerInfo.getDigest(publicKey);
            signerInfo.g = bArr;
            if (!CryptoUtils.secureEqualsBlock(messageDigest, digest)) {
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

package iaik.pkcs.pkcs7;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public class EnvelopedData extends EnvelopedDataStream implements Content {
    static Class a;
    private EncryptedContentInfo c;

    protected EnvelopedData() {
        this.block_size = -1;
    }

    public EnvelopedData(ASN1Object aSN1Object) throws PKCSParsingException {
        this();
        decode(aSN1Object);
    }

    public EnvelopedData(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public EnvelopedData(byte[] bArr, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        this();
        EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo(ObjectID.pkcs7_data, bArr);
        this.c = encryptedContentInfo;
        this.symmetric_key = encryptedContentInfo.setupCipher(algorithmID);
    }

    public EnvelopedData(byte[] bArr, AlgorithmID algorithmID, int i) throws NoSuchAlgorithmException {
        this();
        EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo(ObjectID.pkcs7_data, bArr);
        this.c = encryptedContentInfo;
        this.symmetric_key = encryptedContentInfo.setupCipher(algorithmID, i);
    }

    public EnvelopedData(byte[] bArr, AlgorithmID algorithmID, int i, int i2) throws NoSuchAlgorithmException {
        this();
        this.c = new EncryptedContentInfo(ObjectID.pkcs7_data, bArr);
        if (i2 == 1) {
            this.version = 1;
            this.c.c = 1;
        } else if (i2 != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid version number: ");
            stringBuffer.append(i2);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.symmetric_key = this.c.setupCipher(algorithmID, i);
    }

    public EnvelopedData(RecipientInfo[] recipientInfoArr, EncryptedContentInfo encryptedContentInfo) {
        this();
        this.recipient_infos = Util.getVector(recipientInfoArr);
        this.c = encryptedContentInfo;
    }

    public EnvelopedData(RecipientInfo[] recipientInfoArr, EncryptedContentInfo encryptedContentInfo, int i) {
        this();
        this.recipient_infos = Util.getVector(recipientInfoArr);
        this.c = encryptedContentInfo;
        if (i == 1) {
            this.version = 1;
            this.c.c = 1;
        } else {
            if (i == 0) {
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid version number: ");
            stringBuffer.append(i);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.pkcs.pkcs7.Content
    public void decode(ASN1Object aSN1Object) throws PKCSParsingException {
        try {
            decode(new ByteArrayInputStream(DerCoder.encode(aSN1Object)));
        } catch (IOException e) {
            throw new PKCSParsingException(e.getMessage());
        }
    }

    @Override // iaik.pkcs.pkcs7.EnvelopedDataStream, iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        if (!(inputStream instanceof DerInputStream)) {
            inputStream = new DerInputStream(inputStream);
        }
        DerInputStream sequence = ((DerInputStream) inputStream).readSequence();
        this.version = sequence.readInteger().intValue();
        try {
            ASN1Object aSN1ObjectDecode = DerCoder.decode(sequence);
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.pkcs.pkcs7.RecipientInfo");
                a = clsClass$;
            }
            this.recipient_infos = Util.getVector(ASN.parseSequenceOf(aSN1ObjectDecode, clsClass$));
            EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo();
            this.c = encryptedContentInfo;
            encryptedContentInfo.c = this.version;
            this.c.decode(sequence);
        } catch (CodingException unused) {
            throw new PKCSParsingException("Error parsing recipient infos!");
        }
    }

    public byte[] getContent() {
        return this.c.getContent();
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

    @Override // iaik.pkcs.pkcs7.EnvelopedDataStream
    public Object getEncryptedContentInfo() {
        return this.c;
    }

    @Override // iaik.pkcs.pkcs7.EnvelopedDataStream
    public InputStream getInputStream() {
        return this.c.getInputStream();
    }

    @Override // iaik.pkcs.pkcs7.EnvelopedDataStream
    public void setupCipher(Key key) throws PKCSException, NoSuchAlgorithmException, InvalidKeyException {
        this.c.setupCipher(key);
    }

    @Override // iaik.pkcs.pkcs7.EnvelopedDataStream
    public void setupCipher(PrivateKey privateKey, int i) throws PKCSException, NoSuchAlgorithmException, InvalidKeyException {
        this.c.setupCipher(((RecipientInfo) this.recipient_infos.elementAt(i)).decryptKey(privateKey, this.c.getContentEncryptionAlgorithm().getRawImplementationName()));
    }

    @Override // iaik.pkcs.pkcs7.EnvelopedDataStream
    protected ASN1Object toASN1Object(int i) throws PKCSException {
        if (i <= 0) {
            i = this.block_size;
        }
        if (this.recipient_infos == null) {
            throw new PKCSException("No recipients specified.");
        }
        if (this.symmetric_key != null) {
            Enumeration enumerationElements = this.recipient_infos.elements();
            while (enumerationElements.hasMoreElements()) {
                ((RecipientInfo) enumerationElements.nextElement()).encryptKey(this.symmetric_key);
            }
        }
        this.c.setBlockSize(i);
        SEQUENCE sequence = new SEQUENCE(i > 0);
        try {
            sequence.addComponent(new INTEGER(this.version));
            sequence.addComponent(this.version == 1 ? ASN.createSequenceOf(this.recipient_infos) : ASN.createSetOf(this.recipient_infos));
            sequence.addComponent(this.c.toASN1Object());
            return sequence;
        } catch (CodingException e) {
            throw new PKCSException(e.toString());
        }
    }

    @Override // iaik.pkcs.pkcs7.EnvelopedDataStream, iaik.pkcs.pkcs7.ContentStream
    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.version);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        if (z) {
            int i = 0;
            while (i < this.recipient_infos.size()) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("\nRecipientInfo ");
                int i2 = i + 1;
                stringBuffer3.append(i2);
                stringBuffer3.append(":{\n");
                stringBuffer.append(stringBuffer3.toString());
                stringBuffer.append(((RecipientInfo) this.recipient_infos.elementAt(i)).toString());
                stringBuffer.append("\n}");
                i = i2;
            }
        } else {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("RecipientInfos: ");
            stringBuffer4.append(this.recipient_infos.size());
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        stringBuffer.append("\nEncryptedContentInfo: {\n");
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append(this.c.toString());
        stringBuffer5.append("}");
        stringBuffer.append(stringBuffer5.toString());
        return stringBuffer.toString();
    }
}

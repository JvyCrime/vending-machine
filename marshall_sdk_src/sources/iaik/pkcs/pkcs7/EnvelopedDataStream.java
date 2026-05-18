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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Vector;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
public class EnvelopedDataStream implements ContentStream {
    static Class b;
    protected int block_size;
    protected EncryptedContentInfoStream encrypted_content_info;
    protected Vector recipient_infos;
    protected SecretKey symmetric_key;
    protected int version;

    protected EnvelopedDataStream() {
        this.version = 0;
        this.recipient_infos = new Vector();
        this.block_size = 2048;
    }

    public EnvelopedDataStream(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public EnvelopedDataStream(InputStream inputStream, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        this();
        EncryptedContentInfoStream encryptedContentInfoStream = new EncryptedContentInfoStream(ObjectID.pkcs7_data, inputStream);
        this.encrypted_content_info = encryptedContentInfoStream;
        this.symmetric_key = encryptedContentInfoStream.setupCipher(algorithmID);
    }

    public EnvelopedDataStream(InputStream inputStream, AlgorithmID algorithmID, int i) throws NoSuchAlgorithmException {
        this();
        EncryptedContentInfoStream encryptedContentInfoStream = new EncryptedContentInfoStream(ObjectID.pkcs7_data, inputStream);
        this.encrypted_content_info = encryptedContentInfoStream;
        this.symmetric_key = encryptedContentInfoStream.setupCipher(algorithmID, i);
    }

    public EnvelopedDataStream(RecipientInfo[] recipientInfoArr, EncryptedContentInfoStream encryptedContentInfoStream) {
        this();
        this.recipient_infos = Util.getVector(recipientInfoArr);
        this.encrypted_content_info = encryptedContentInfoStream;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public void addRecipientInfo(RecipientInfo recipientInfo) {
        this.recipient_infos.addElement(recipientInfo);
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        if (!(inputStream instanceof DerInputStream)) {
            inputStream = new DerInputStream(inputStream);
        }
        DerInputStream sequence = ((DerInputStream) inputStream).readSequence();
        this.version = sequence.readInteger().intValue();
        try {
            ASN1Object aSN1ObjectDecode = DerCoder.decode(sequence);
            Class clsClass$ = b;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.pkcs.pkcs7.RecipientInfo");
                b = clsClass$;
            }
            this.recipient_infos = Util.getVector(ASN.parseSequenceOf(aSN1ObjectDecode, clsClass$));
            this.encrypted_content_info = new EncryptedContentInfoStream(sequence);
        } catch (CodingException unused) {
            throw new PKCSParsingException("Error parsing recipient infos!");
        }
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public int getBlockSize() {
        return this.block_size;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ObjectID getContentType() {
        return ObjectID.pkcs7_envelopedData;
    }

    public Object getEncryptedContentInfo() {
        return this.encrypted_content_info;
    }

    public InputStream getInputStream() {
        return this.encrypted_content_info.getInputStream();
    }

    public RecipientInfo getRecipientInfo(X509Certificate x509Certificate) {
        Enumeration enumerationElements = this.recipient_infos.elements();
        while (enumerationElements.hasMoreElements()) {
            RecipientInfo recipientInfo = (RecipientInfo) enumerationElements.nextElement();
            IssuerAndSerialNumber issuerAndSerialNumber = recipientInfo.getIssuerAndSerialNumber();
            if (issuerAndSerialNumber != null && issuerAndSerialNumber.isIssuerOf(x509Certificate)) {
                return recipientInfo;
            }
        }
        return null;
    }

    public RecipientInfo[] getRecipientInfos() {
        Vector vector = this.recipient_infos;
        Class clsClass$ = b;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.pkcs.pkcs7.RecipientInfo");
            b = clsClass$;
        }
        return (RecipientInfo[]) Util.toArray(vector, clsClass$);
    }

    public int getVersion() {
        return this.version;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void setBlockSize(int i) {
        this.block_size = i;
    }

    public void setRecipientInfos(RecipientInfo[] recipientInfoArr) {
        this.recipient_infos = Util.getVector(recipientInfoArr);
    }

    public void setupCipher(Key key) throws NoSuchAlgorithmException, PKCSException, InvalidKeyException {
        this.encrypted_content_info.setupCipher(key);
    }

    public void setupCipher(PrivateKey privateKey, int i) throws PKCSException, NoSuchAlgorithmException, InvalidKeyException {
        this.encrypted_content_info.setupCipher(((RecipientInfo) this.recipient_infos.elementAt(i)).decryptKey(privateKey, this.encrypted_content_info.getContentEncryptionAlgorithm().getRawImplementationName()));
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ASN1Object toASN1Object() throws PKCSException {
        return toASN1Object(-1);
    }

    protected ASN1Object toASN1Object(int i) throws PKCSException {
        if (i <= 0) {
            i = this.block_size;
        }
        Vector vector = this.recipient_infos;
        if (vector == null) {
            throw new PKCSException("No recipients specified.");
        }
        if (this.symmetric_key != null) {
            Enumeration enumerationElements = vector.elements();
            while (enumerationElements.hasMoreElements()) {
                ((RecipientInfo) enumerationElements.nextElement()).encryptKey(this.symmetric_key);
            }
        }
        this.encrypted_content_info.setBlockSize(i);
        SEQUENCE sequence = new SEQUENCE(true);
        try {
            sequence.addComponent(new INTEGER(this.version));
            sequence.addComponent(ASN.createSetOf(this.recipient_infos));
            sequence.addComponent(this.encrypted_content_info.toASN1Object());
            return sequence;
        } catch (CodingException e) {
            throw new PKCSException(e.toString());
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
        stringBuffer5.append(this.encrypted_content_info.toString());
        stringBuffer5.append("}");
        stringBuffer.append(stringBuffer5.toString());
        return stringBuffer.toString();
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

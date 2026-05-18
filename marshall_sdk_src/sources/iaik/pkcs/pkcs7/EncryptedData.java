package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.utils.InternalErrorException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class EncryptedData extends EncryptedDataStream implements Content {
    static Class a;
    private EncryptedContentInfo c;

    protected EncryptedData() {
        this.block_size = -1;
    }

    public EncryptedData(ASN1Object aSN1Object) throws PKCSParsingException {
        this();
        decode(aSN1Object);
    }

    public EncryptedData(EncryptedContentInfo encryptedContentInfo) {
        this();
        this.c = encryptedContentInfo;
    }

    public EncryptedData(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public EncryptedData(byte[] bArr) {
        this();
        this.c = new EncryptedContentInfo(ObjectID.pkcs7_data, bArr);
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

    @Override // iaik.pkcs.pkcs7.EncryptedDataStream, iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        if (!(inputStream instanceof DerInputStream)) {
            inputStream = new DerInputStream(inputStream);
        }
        DerInputStream sequence = ((DerInputStream) inputStream).readSequence();
        this.version = sequence.readInteger().intValue();
        EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo();
        this.c = encryptedContentInfo;
        encryptedContentInfo.decode(sequence);
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

    @Override // iaik.pkcs.pkcs7.EncryptedDataStream
    public Object getEncryptedContentInfo() {
        return this.c;
    }

    @Override // iaik.pkcs.pkcs7.EncryptedDataStream
    public InputStream getInputStream() {
        return this.c.getInputStream();
    }

    @Override // iaik.pkcs.pkcs7.EncryptedDataStream
    public void setupCipher(AlgorithmID algorithmID, char[] cArr, int i) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            AlgorithmParameterSpec algorithmParameterSpecA = a(algorithmID, i);
            this.c.setupCipher(algorithmID, a(cArr, algorithmID), algorithmParameterSpecA);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InternalErrorException(e);
        } catch (InvalidParameterSpecException e2) {
            throw new InternalErrorException(e2);
        }
    }

    @Override // iaik.pkcs.pkcs7.EncryptedDataStream
    public void setupCipher(char[] cArr) throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameters algorithmParameters = this.c.getContentEncryptionAlgorithm().getAlgorithmParameters("PBE");
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("javax.crypto.spec.PBEParameterSpec");
            a = clsClass$;
        }
        AlgorithmParameterSpec parameterSpec = algorithmParameters.getParameterSpec(clsClass$);
        this.c.setupCipher(a(cArr, this.c.getContentEncryptionAlgorithm()), parameterSpec);
    }

    @Override // iaik.pkcs.pkcs7.EncryptedDataStream
    protected ASN1Object toASN1Object(int i) throws PKCSException {
        if (i <= 0) {
            i = this.block_size;
        }
        this.c.setBlockSize(i);
        SEQUENCE sequence = new SEQUENCE(i > 0);
        sequence.addComponent(new INTEGER(this.version));
        sequence.addComponent(this.c.toASN1Object());
        return sequence;
    }

    @Override // iaik.pkcs.pkcs7.EncryptedDataStream, iaik.pkcs.pkcs7.ContentStream
    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.version);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append("EncryptedContentInfo:\n");
        stringBuffer.append(this.c.toString());
        return stringBuffer.toString();
    }
}

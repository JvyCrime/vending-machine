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
import iaik.security.cipher.PBEKey;
import iaik.security.cipher.PBEKeyBMP;
import iaik.security.pbe.PBEGenParameterSpec;
import iaik.utils.InternalErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class EncryptedDataStream implements ContentStream {
    static Class b;
    private EncryptedContentInfoStream a;
    protected int block_size;
    protected int version;

    protected EncryptedDataStream() {
        this.version = 0;
        this.block_size = 2048;
    }

    public EncryptedDataStream(EncryptedContentInfoStream encryptedContentInfoStream) {
        this();
        this.a = encryptedContentInfoStream;
    }

    public EncryptedDataStream(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public EncryptedDataStream(InputStream inputStream, int i) {
        this();
        this.a = new EncryptedContentInfoStream(ObjectID.pkcs7_data, inputStream);
        this.block_size = i;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    Key a(char[] cArr, AlgorithmID algorithmID) {
        return algorithmID.equals(AlgorithmID.pbeWithMD5AndDES_CBC) ? new PBEKey(cArr) : new PBEKeyBMP(cArr);
    }

    AlgorithmParameterSpec a(AlgorithmID algorithmID, int i) throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
        PBEGenParameterSpec pBEGenParameterSpec = new PBEGenParameterSpec(8, i);
        AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("PBE");
        algorithmParameterGenerator.init(pBEGenParameterSpec);
        AlgorithmParameters algorithmParametersGenerateParameters = algorithmParameterGenerator.generateParameters();
        Class clsClass$ = b;
        if (clsClass$ == null) {
            clsClass$ = class$("javax.crypto.spec.PBEParameterSpec");
            b = clsClass$;
        }
        AlgorithmParameterSpec parameterSpec = algorithmParametersGenerateParameters.getParameterSpec(clsClass$);
        algorithmID.setAlgorithmParameters(algorithmParametersGenerateParameters);
        return parameterSpec;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        if (!(inputStream instanceof DerInputStream)) {
            inputStream = new DerInputStream(inputStream);
        }
        DerInputStream sequence = ((DerInputStream) inputStream).readSequence();
        this.version = sequence.readInteger().intValue();
        this.a = new EncryptedContentInfoStream(sequence);
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public int getBlockSize() {
        return this.block_size;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ObjectID getContentType() {
        return ObjectID.pkcs7_encryptedData;
    }

    public Object getEncryptedContentInfo() {
        return this.a;
    }

    public InputStream getInputStream() {
        return this.a.getInputStream();
    }

    public int getVersion() {
        return this.version;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void setBlockSize(int i) {
        this.block_size = i;
    }

    public void setupCipher(AlgorithmID algorithmID, char[] cArr) throws NoSuchAlgorithmException, InvalidKeyException {
        setupCipher(algorithmID, cArr, 2000);
    }

    public void setupCipher(AlgorithmID algorithmID, char[] cArr, int i) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            AlgorithmParameterSpec algorithmParameterSpecA = a(algorithmID, i);
            this.a.setupCipher(algorithmID, a(cArr, algorithmID), algorithmParameterSpecA);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InternalErrorException(e);
        } catch (InvalidParameterSpecException e2) {
            throw new InternalErrorException(e2);
        }
    }

    public void setupCipher(char[] cArr) throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameters algorithmParameters = this.a.getContentEncryptionAlgorithm().getAlgorithmParameters("PBE");
        Class clsClass$ = b;
        if (clsClass$ == null) {
            clsClass$ = class$("javax.crypto.spec.PBEParameterSpec");
            b = clsClass$;
        }
        AlgorithmParameterSpec parameterSpec = algorithmParameters.getParameterSpec(clsClass$);
        this.a.setupCipher(a(cArr, this.a.getContentEncryptionAlgorithm()), parameterSpec);
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ASN1Object toASN1Object() throws PKCSException {
        return toASN1Object(-1);
    }

    protected ASN1Object toASN1Object(int i) throws PKCSException {
        if (i <= 0) {
            i = this.block_size;
        }
        this.a.setBlockSize(i);
        SEQUENCE sequence = new SEQUENCE(true);
        sequence.addComponent(new INTEGER(this.version));
        sequence.addComponent(this.a.toASN1Object());
        return sequence;
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
        stringBuffer.append("EncryptedContentInfo:\n");
        stringBuffer.append(this.a.toString());
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

package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.EncodeListener;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.utils.CryptoUtils;
import iaik.utils.EOFListener;
import iaik.utils.NotifyEOFInputStream;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class DigestedDataStream implements EncodeListener, ContentStream, EOFListener {
    public static final int EXPLICIT = 2;
    public static final int IMPLICIT = 1;
    private ContentInfoStream a;
    int b;
    protected int block_size;
    AlgorithmID c;
    protected ObjectID content_type;
    MessageDigest d;
    byte[] e;
    byte[] f;
    protected InputStream input_stream;
    protected int mode;
    protected DerInputStream this_object;

    protected DigestedDataStream() {
        this.mode = 1;
        this.b = 0;
        this.block_size = 2048;
    }

    public DigestedDataStream(ObjectID objectID, AlgorithmID algorithmID, byte[] bArr) {
        this();
        this.mode = 2;
        this.content_type = objectID;
        this.a = new ContentInfoStream(objectID);
        this.c = algorithmID;
        this.e = bArr;
    }

    public DigestedDataStream(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public DigestedDataStream(InputStream inputStream, AlgorithmID algorithmID) throws IOException {
        this();
        if (algorithmID == null) {
            throw new IOException("No digestAlgorithm specified!");
        }
        if (inputStream == null) {
            throw new IOException("No input stream supplied!");
        }
        this.input_stream = inputStream;
        this.mode = 2;
        this.c = algorithmID;
        try {
            setupMessageDigest(algorithmID, true);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e.getMessage());
        }
    }

    public DigestedDataStream(InputStream inputStream, AlgorithmID algorithmID, int i) throws PKCSException {
        this();
        if (algorithmID == null) {
            throw new PKCSException("No digestAlgorithm specified!");
        }
        if (i != 1 && i != 2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal mode specification: ");
            stringBuffer.append(i);
            stringBuffer.append("!");
            throw new PKCSException(stringBuffer.toString());
        }
        if (inputStream == null) {
            throw new PKCSException("No data input stream specified!");
        }
        this.content_type = ObjectID.pkcs7_data;
        this.mode = i;
        this.input_stream = inputStream;
        AlgorithmID algorithmID2 = (AlgorithmID) algorithmID.clone();
        this.c = algorithmID2;
        try {
            setupMessageDigest(algorithmID2, false);
        } catch (NoSuchAlgorithmException e) {
            throw new PKCSException(e.getMessage());
        }
    }

    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        if (!(inputStream instanceof DerInputStream)) {
            inputStream = new DerInputStream(inputStream);
        }
        DerInputStream sequence = ((DerInputStream) inputStream).readSequence();
        this.this_object = sequence;
        this.b = sequence.readInteger().intValue();
        this.c = new AlgorithmID(this.this_object);
        ContentInfoStream contentInfoStream = new ContentInfoStream(this.this_object);
        this.a = contentInfoStream;
        this.content_type = contentInfoStream.getContentType();
        if (!this.a.hasContent()) {
            this.mode = 2;
            notifyEOF();
            return;
        }
        if (!this.content_type.equals(ObjectID.pkcs7_data)) {
            throw new IOException("DigestedData only for content type Data at this time!");
        }
        this.input_stream = ((DataStream) this.a.getContent()).getInputStream();
        try {
            setupMessageDigest(this.c, true);
            NotifyEOFInputStream notifyEOFInputStream = new NotifyEOFInputStream(this.input_stream);
            this.input_stream = notifyEOFInputStream;
            notifyEOFInputStream.addEOFListener(this);
        } catch (NoSuchAlgorithmException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No implementation for hash algorithm: ");
            stringBuffer.append(e.getMessage());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // iaik.asn1.EncodeListener
    public void encodeCalled(ASN1Object aSN1Object, int i) throws CodingException {
        try {
            Object obj = this.e;
            if (obj != null) {
                aSN1Object.setValue(obj);
                return;
            }
            MessageDigest messageDigest = this.d;
            if (messageDigest == null) {
                throw new CodingException("Message Digest not initialized!");
            }
            byte[] bArrDigest = messageDigest.digest();
            this.e = bArrDigest;
            aSN1Object.setValue(bArrDigest);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to calculate digest: ");
            stringBuffer.append(e.getMessage());
            throw new CodingException(stringBuffer.toString());
        }
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public int getBlockSize() {
        return this.block_size;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ObjectID getContentType() {
        return ObjectID.pkcs7_digestedData;
    }

    public byte[] getDigest() {
        return this.e;
    }

    public AlgorithmID getDigestAlgorithm() {
        return this.c;
    }

    public InputStream getInputStream() {
        return this.input_stream;
    }

    public int getVersion() {
        return this.b;
    }

    @Override // iaik.utils.EOFListener
    public void notifyEOF() throws IOException {
        this.e = this.this_object.readOctetStringByteArray();
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void setBlockSize(int i) {
        this.block_size = i;
    }

    public void setDigest(byte[] bArr) {
        this.e = bArr;
    }

    void setupMessageDigest(AlgorithmID algorithmID, boolean z) throws NoSuchAlgorithmException {
        this.d = this.c.getMessageDigestInstance();
        this.input_stream = new DigestInputStream(this.input_stream, this.d);
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ASN1Object toASN1Object() throws PKCSException {
        return toASN1Object(-1);
    }

    protected ASN1Object toASN1Object(int i) throws PKCSException {
        OCTET_STRING octet_string;
        if (i <= 0) {
            i = this.block_size;
        }
        if (this.c == null) {
            throw new PKCSException("digestAlgorithm not set!");
        }
        if (this.mode == 1) {
            if (this.input_stream == null) {
                throw new PKCSException("InputStream not set!");
            }
            if (this.d == null) {
                throw new PKCSException("Message Digest not initialized for digest computation!");
            }
            this.a = new ContentInfoStream(new DataStream(this.input_stream, i));
        } else if (this.a == null) {
            this.a = new ContentInfoStream(ObjectID.pkcs7_data);
        }
        SEQUENCE sequence = new SEQUENCE(true);
        sequence.addComponent(new INTEGER(this.b));
        sequence.addComponent(this.c.toASN1Object());
        sequence.addComponent(this.a.toASN1Object());
        if (this.e != null) {
            octet_string = new OCTET_STRING(this.e);
        } else {
            octet_string = new OCTET_STRING();
            octet_string.addEncodeListener(this, 1);
        }
        sequence.addComponent(octet_string);
        return sequence;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.b);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        if (this.c != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("digestAlgorithm: ");
            stringBuffer3.append(this.c.getName());
            stringBuffer.append(stringBuffer3.toString());
        }
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("ContentInfo: ");
        stringBuffer4.append(this.a);
        stringBuffer.append(stringBuffer4.toString());
        stringBuffer.append("\n");
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append("digest: ");
        stringBuffer5.append(Util.toString(this.e));
        stringBuffer.append(stringBuffer5.toString());
        return stringBuffer.toString();
    }

    public boolean verify() throws PKCSException {
        byte[] bArr = this.e;
        if (bArr == null) {
            throw new PKCSException("digest value not parsed from encoding!");
        }
        byte[] bArr2 = this.f;
        if (bArr2 != null) {
            return CryptoUtils.secureEqualsBlock(bArr2, bArr);
        }
        MessageDigest messageDigest = this.d;
        if (messageDigest == null) {
            throw new PKCSException("MessageDigest not initialized for digest computation!");
        }
        byte[] bArrDigest = messageDigest.digest();
        this.f = bArrDigest;
        return CryptoUtils.secureEqualsBlock(bArrDigest, this.e);
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

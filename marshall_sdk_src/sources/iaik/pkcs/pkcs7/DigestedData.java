package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
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
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class DigestedData extends DigestedDataStream implements Content {
    byte[] a;
    private ContentInfo g;

    protected DigestedData() {
        this.block_size = -1;
    }

    public DigestedData(ASN1Object aSN1Object) throws PKCSParsingException {
        this();
        decode(aSN1Object);
    }

    public DigestedData(ObjectID objectID, AlgorithmID algorithmID, byte[] bArr) {
        this();
        this.mode = 2;
        this.content_type = objectID;
        this.g = new ContentInfo(objectID);
        this.c = algorithmID;
        this.e = bArr;
    }

    public DigestedData(Content content, AlgorithmID algorithmID, byte[] bArr) throws PKCSException {
        this();
        ContentInfo contentInfo = new ContentInfo(content);
        this.g = contentInfo;
        this.content_type = contentInfo.getContentType();
        if (this.content_type.equals(ObjectID.pkcs7_data)) {
            this.a = ((Data) content).getData();
        }
        this.c = algorithmID;
        this.e = bArr;
    }

    public DigestedData(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public DigestedData(byte[] bArr, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        this.a = bArr;
        this.mode = 2;
        this.content_type = ObjectID.pkcs7_data;
        this.c = algorithmID;
        setupMessageDigest(algorithmID, true);
    }

    public DigestedData(byte[] bArr, AlgorithmID algorithmID, int i) throws PKCSException {
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
        this.content_type = ObjectID.pkcs7_data;
        this.mode = i;
        this.a = bArr;
        if (i == 1) {
            this.g = new ContentInfo(new Data(bArr));
        } else {
            this.g = new ContentInfo(this.content_type);
        }
        this.c = (AlgorithmID) algorithmID.clone();
        try {
            setupMessageDigest(this.c, false);
        } catch (NoSuchAlgorithmException e) {
            throw new PKCSException(e.getMessage());
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

    @Override // iaik.pkcs.pkcs7.DigestedDataStream, iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        if (!(inputStream instanceof DerInputStream)) {
            inputStream = new DerInputStream(inputStream);
        }
        DerInputStream sequence = ((DerInputStream) inputStream).readSequence();
        this.b = sequence.readInteger().intValue();
        this.c = new AlgorithmID(sequence);
        ContentInfo contentInfo = new ContentInfo(sequence);
        this.g = contentInfo;
        this.content_type = contentInfo.getContentType();
        if (this.g.hasContent()) {
            if (this.content_type.equals(ObjectID.pkcs7_data)) {
                this.a = ((Data) this.g.getContent()).getData();
            }
            try {
                setupMessageDigest(this.c, true);
            } catch (NoSuchAlgorithmException e) {
                throw new PKCSParsingException(e.getMessage());
            }
        } else {
            this.mode = 2;
        }
        this.e = sequence.readOctetStringByteArray();
    }

    public byte[] getContent() {
        return this.a;
    }

    public Object getContentInfo() {
        return this.g;
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

    @Override // iaik.pkcs.pkcs7.DigestedDataStream
    public InputStream getInputStream() {
        if (this.a == null) {
            return null;
        }
        return new ByteArrayInputStream(this.a);
    }

    @Override // iaik.pkcs.pkcs7.DigestedDataStream
    protected void setupMessageDigest(AlgorithmID algorithmID, boolean z) throws NoSuchAlgorithmException {
        this.d = algorithmID.getMessageDigestInstance();
        if (this.a == null) {
            throw new NoSuchAlgorithmException("No content to digest!");
        }
        this.d.update(this.a);
        byte[] bArrDigest = this.d.digest();
        if (z) {
            this.f = bArrDigest;
        } else {
            this.e = bArrDigest;
        }
    }

    @Override // iaik.pkcs.pkcs7.DigestedDataStream
    protected ASN1Object toASN1Object(int i) throws PKCSException {
        if (this.c == null) {
            throw new PKCSException("No digest algorithm set!");
        }
        if (this.g == null) {
            throw new PKCSException("No content info set!");
        }
        if (i <= 0) {
            i = this.block_size;
        }
        Content content = this.g.getContent();
        if (content != null) {
            content.setBlockSize(i);
        }
        SEQUENCE sequence = new SEQUENCE(i > 0);
        sequence.addComponent(new INTEGER(this.b));
        sequence.addComponent(this.c.toASN1Object());
        sequence.addComponent(this.g.toASN1Object());
        sequence.addComponent(new OCTET_STRING(this.e));
        return sequence;
    }

    @Override // iaik.pkcs.pkcs7.DigestedDataStream, iaik.pkcs.pkcs7.ContentStream
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
        stringBuffer4.append(this.g);
        stringBuffer.append(stringBuffer4.toString());
        stringBuffer.append("\n");
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append("digest: ");
        stringBuffer5.append(Util.toString(this.e));
        stringBuffer.append(stringBuffer5.toString());
        return stringBuffer.toString();
    }
}

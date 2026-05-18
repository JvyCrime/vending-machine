package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class DataStream implements ContentStream {
    InputStream b;
    int c;

    protected DataStream() {
        this.c = 2048;
    }

    public DataStream(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public DataStream(InputStream inputStream, int i) {
        this();
        this.b = inputStream;
        this.c = i;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        if (inputStream == null) {
            throw new IOException("Cannot decode a null object!");
        }
        DerInputStream derInputStream = inputStream instanceof DerInputStream ? (DerInputStream) inputStream : new DerInputStream(inputStream);
        if (derInputStream.nextTag() != 4) {
            throw new IOException("Next tag no OCTET STRING!");
        }
        this.b = derInputStream.readOctetString();
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public int getBlockSize() {
        return this.c;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ObjectID getContentType() {
        return ObjectID.pkcs7_data;
    }

    public InputStream getInputStream() {
        return this.b;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public void setBlockSize(int i) {
        this.c = i;
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public ASN1Object toASN1Object() throws PKCSException {
        return this.c > 0 ? new OCTET_STRING(this.b, this.c) : new OCTET_STRING(this.b);
    }

    public String toString() {
        return toString(false);
    }

    @Override // iaik.pkcs.pkcs7.ContentStream
    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PKCS#7 Data: ");
        if (this.b != null) {
            try {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(this.b.available());
                stringBuffer2.append(" bytes available from input stream ");
                stringBuffer.append(stringBuffer2.toString());
            } catch (IOException unused) {
            }
            if (z) {
                stringBuffer.append(this.b);
            }
        } else {
            stringBuffer.append("no content!");
        }
        return stringBuffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws PKCSException, IOException {
        DerCoder.encodeTo(toASN1Object(), outputStream);
    }
}

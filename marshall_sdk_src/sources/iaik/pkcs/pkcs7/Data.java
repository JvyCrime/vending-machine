package iaik.pkcs.pkcs7;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.OCTET_STRING;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class Data extends DataStream implements Content {
    byte[] a;

    protected Data() {
        this.c = -1;
    }

    public Data(ASN1Object aSN1Object) throws PKCSParsingException {
        this();
        decode(aSN1Object);
    }

    public Data(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public Data(byte[] bArr) {
        this();
        this.a = bArr;
    }

    public Data(byte[] bArr, int i) {
        this();
        this.a = bArr;
        this.c = i;
    }

    @Override // iaik.pkcs.pkcs7.Content
    public void decode(ASN1Object aSN1Object) throws PKCSParsingException {
        if (aSN1Object == null) {
            throw new PKCSParsingException("Cannot decode a null object!");
        }
        if (!aSN1Object.isA(ASN.OCTET_STRING)) {
            throw new PKCSParsingException("Data must be an OCTET STRING!");
        }
        try {
            this.a = ((OCTET_STRING) aSN1Object).getWholeValue();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error decoding octet string: ");
            stringBuffer.append(e.toString());
            throw new PKCSParsingException(stringBuffer.toString());
        }
    }

    @Override // iaik.pkcs.pkcs7.DataStream, iaik.pkcs.pkcs7.ContentStream
    public void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        try {
            decode(DerCoder.decode(inputStream));
        } catch (CodingException e) {
            throw new PKCSParsingException(e.toString());
        } catch (IOException e2) {
            throw e2;
        }
    }

    public byte[] getData() {
        return this.a;
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

    @Override // iaik.pkcs.pkcs7.DataStream
    public InputStream getInputStream() {
        if (this.a != null) {
            this.b = new ByteArrayInputStream(this.a);
        }
        return this.b;
    }

    @Override // iaik.pkcs.pkcs7.DataStream, iaik.pkcs.pkcs7.ContentStream
    public ASN1Object toASN1Object() throws PKCSException {
        return this.c > 0 ? new OCTET_STRING(this.a, this.c) : new OCTET_STRING(this.a);
    }

    @Override // iaik.pkcs.pkcs7.DataStream, iaik.pkcs.pkcs7.ContentStream
    public String toString(boolean z) {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PKCS#7 Data: ");
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.a.length);
            stringBuffer2.append(" bytes");
            stringBuffer.append(stringBuffer2.toString());
            if (z) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append(": ");
                stringBuffer3.append(Util.toString(this.a, 0, 15));
                stringBuffer.append(stringBuffer3.toString());
                str = this.a.length > 15 ? "..." : "No content!";
            }
            return stringBuffer.toString();
        }
        stringBuffer.append(str);
        return stringBuffer.toString();
    }
}

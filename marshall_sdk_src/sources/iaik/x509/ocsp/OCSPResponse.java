package iaik.x509.ocsp;

import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ENUMERATED;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes2.dex */
public class OCSPResponse {
    public static final int internalError = 2;
    public static final int malformedRequest = 1;
    public static final int noMoreData = 7;
    public static final int sigRequired = 5;
    public static final int successful = 0;
    public static final int tryLater = 3;
    public static final int unauthorized = 6;
    int a;
    ResponseBytes b;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    public OCSPResponse(int i) throws IllegalArgumentException {
        if (i >= 0 && i != 4 && i <= 7) {
            this.a = i;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot create OCSPResponse: Illegal response status: ");
        stringBuffer.append(i);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public OCSPResponse(ASN1Object aSN1Object) throws CodingException, UnknownResponseException {
        decode(aSN1Object);
    }

    public OCSPResponse(Response response) {
        this.a = 0;
        this.b = new ResponseBytes(response);
    }

    public OCSPResponse(ResponseBytes responseBytes) {
        this.a = 0;
        this.b = responseBytes;
    }

    public OCSPResponse(InputStream inputStream) throws IOException, UnknownResponseException {
        decode(inputStream);
    }

    public OCSPResponse(byte[] bArr) throws CodingException, UnknownResponseException {
        decode(DerCoder.decode(bArr));
    }

    public void decode(ASN1Object aSN1Object) throws CodingException, UnknownResponseException {
        int iIntValue = ((Integer) aSN1Object.getComponentAt(0).getValue()).intValue();
        this.a = iIntValue;
        if (iIntValue < 0 || iIntValue == 4 || iIntValue > 7) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot create OCSPResponse: Illegal response status: ");
            stringBuffer.append(this.a);
            throw new CodingException(stringBuffer.toString());
        }
        if (aSN1Object.countComponents() == 2) {
            this.b = new ResponseBytes((ASN1Object) aSN1Object.getComponentAt(1).getValue());
        }
    }

    public void decode(InputStream inputStream) throws IOException, UnknownResponseException {
        try {
            decode(DerCoder.decode(inputStream));
        } catch (CodingException e) {
            throw new IOException(e.getMessage());
        }
    }

    public byte[] getEncoded() {
        return DerCoder.encode(toASN1Object());
    }

    public byte[] getFingerprint(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(getEncoded());
        return messageDigest.digest();
    }

    public Response getResponse() {
        ResponseBytes responseBytes = this.b;
        if (responseBytes == null) {
            return null;
        }
        return responseBytes.getResponse();
    }

    public ResponseBytes getResponseBytes() {
        return this.b;
    }

    public int getResponseStatus() {
        return this.a;
    }

    public String getResponseStatusName() {
        int i = this.a;
        if (i == 0) {
            return "successful";
        }
        if (i == 1) {
            return "malformedRequest";
        }
        if (i == 2) {
            return "internalError";
        }
        if (i == 3) {
            return "tryLater";
        }
        if (i == 5) {
            return "sigRequired";
        }
        if (i == 6) {
            return "unauthorized";
        }
        if (i == 7) {
            return "noMoreData";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("undefined response status: ");
        stringBuffer.append(this.a);
        return stringBuffer.toString();
    }

    public ObjectID getResponseType() {
        ResponseBytes responseBytes = this.b;
        if (responseBytes == null) {
            return null;
        }
        return responseBytes.getResponseType();
    }

    public void setResponse(Response response) {
        this.a = 0;
        this.b = new ResponseBytes(response);
    }

    public void setResponseBytes(ResponseBytes responseBytes) {
        this.a = 0;
        this.b = responseBytes;
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new ENUMERATED(this.a));
        if (this.b != null) {
            sequence.addComponent(new CON_SPEC(0, this.b.toASN1Object()));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("responseStatus: ");
        stringBuffer2.append(getResponseStatusName());
        stringBuffer.append(stringBuffer2.toString());
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\n");
            stringBuffer3.append(this.b.toString());
            stringBuffer.append(stringBuffer3.toString());
        }
        return stringBuffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        DerCoder.encodeTo(toASN1Object(), outputStream);
    }
}

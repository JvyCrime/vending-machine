package iaik.x509.extensions.qualified.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.IA5String;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes2.dex */
public class BiometricData implements ASN1Type {
    private static boolean e = false;
    public static final int handwritten_signature = 1;
    public static final int picture = 0;
    ASN1Object a;
    AlgorithmID b;
    byte[] c;
    String d;

    public BiometricData() {
    }

    public BiometricData(int i) throws IllegalArgumentException {
        if (i >= 0 || i <= 1) {
            this.a = new INTEGER(i);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unknown predefined biometric type: ");
        stringBuffer.append(i);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public BiometricData(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public BiometricData(ObjectID objectID) {
        this.a = objectID;
    }

    private InputStream a(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.connect();
        int responseCode = httpURLConnection.getResponseCode();
        if (e) {
            PrintStream printStream = System.out;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Response code: ");
            stringBuffer.append(responseCode);
            printStream.println(stringBuffer.toString());
        }
        if (responseCode / 100 != 2) {
            throw new IOException("Cannot access source data!");
        }
        int contentLength = httpURLConnection.getContentLength();
        if (e) {
            PrintStream printStream2 = System.out;
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Content length: ");
            stringBuffer2.append(contentLength);
            printStream2.println(stringBuffer2.toString());
        }
        if (httpURLConnection.getContentLength() != 0) {
            return httpURLConnection.getInputStream();
        }
        throw new IOException("Cannot access source data!");
    }

    private byte[] a(AlgorithmID algorithmID, InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigestInstance = algorithmID.getMessageDigestInstance();
        while (new DigestInputStream(inputStream, messageDigestInstance).read(new byte[2048]) > 0) {
        }
        return messageDigestInstance.digest();
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        int iIntValue;
        ASN1Object componentAt = aSN1Object.getComponentAt(0);
        this.a = componentAt;
        if (componentAt == null) {
            throw new CodingException("Inavlid BiometricData. No biometricData type!");
        }
        if (componentAt.isA(ASN.INTEGER) && (iIntValue = ((BigInteger) this.a.getValue()).intValue()) < 0 && iIntValue > 1) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unknown predefined biometric type: ");
            stringBuffer.append(iIntValue);
            throw new CodingException(stringBuffer.toString());
        }
        this.b = new AlgorithmID(aSN1Object.getComponentAt(1));
        byte[] bArr = (byte[]) aSN1Object.getComponentAt(2).getValue();
        this.c = bArr;
        if (bArr == null) {
            throw new CodingException("Cannot initialize this BiometricData. Missing biometric data hash!");
        }
        if (aSN1Object.countComponents() == 4) {
            this.d = (String) aSN1Object.getComponentAt(3).getValue();
        }
    }

    public byte[] getBiometricDataHash() {
        return this.c;
    }

    public String getBiometricDataHashAsString() {
        return Util.toString(this.c);
    }

    public AlgorithmID getHashAlgorithm() {
        return this.b;
    }

    public String getSourceDataUri() {
        return this.d;
    }

    public ASN1Object getTypeOfBiometricData() {
        return this.a;
    }

    public void setBiometricDataHash(AlgorithmID algorithmID, InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        this.b = algorithmID;
        this.c = a(algorithmID, inputStream);
    }

    public void setBiometricDataHash(AlgorithmID algorithmID, String str) throws Throwable {
        InputStream inputStreamA;
        this.d = str;
        try {
            inputStreamA = a(str);
        } catch (Throwable th) {
            th = th;
            inputStreamA = null;
        }
        try {
            setBiometricDataHash(algorithmID, inputStreamA);
            if (inputStreamA != null) {
                inputStreamA.close();
            }
        } catch (Throwable th2) {
            th = th2;
            if (inputStreamA != null) {
                inputStreamA.close();
            }
            throw th;
        }
    }

    public void setBiometricDataHash(AlgorithmID algorithmID, byte[] bArr) {
        this.b = algorithmID;
        this.c = bArr;
    }

    public void setSourceDataUri(String str) {
        this.d = str;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        if (this.a == null) {
            throw new CodingException("Cannot create ASN.1 object. Missing biometricDataOid!");
        }
        if (this.b == null) {
            throw new CodingException("Cannot create ASN.1 object. Missing hash algorithm!");
        }
        if (this.c == null) {
            throw new CodingException("Cannot create ASN.1 object. Missing biometric data hash!");
        }
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a);
        sequence.addComponent(this.b.toASN1Object());
        sequence.addComponent(new OCTET_STRING(this.c));
        if (this.d != null) {
            sequence.addComponent(new IA5String(this.d));
        }
        return sequence;
    }

    public String toString() {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("typeOfBiometricData: ");
        if (this.a.isA(ASN.INTEGER)) {
            int iIntValue = ((BigInteger) this.a.getValue()).intValue();
            if (iIntValue != 0) {
                str = iIntValue == 1 ? "handwritten-signature" : "picture";
            }
            stringBuffer.append(str);
        } else {
            stringBuffer.append(this.a);
        }
        stringBuffer.append("\n");
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("hashAlgorithm: ");
        stringBuffer2.append(this.b.getName());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("biometricDataHash: ");
        stringBuffer3.append(getBiometricDataHashAsString());
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        if (this.d != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("sourceDataUri: ");
            stringBuffer4.append(this.d);
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }

    public boolean verifyBiometricDataHash() throws IOException {
        String str = this.d;
        if (str == null) {
            throw new IOException("Cannot obtain data. No source data uri included!");
        }
        InputStream inputStreamA = null;
        try {
            inputStreamA = a(str);
            return verifyBiometricDataHash(inputStreamA);
        } finally {
            if (inputStreamA != null) {
                inputStreamA.close();
            }
        }
    }

    public boolean verifyBiometricDataHash(InputStream inputStream) throws IOException {
        try {
            return CryptoUtils.equalsBlock(a(this.b, inputStream), this.c);
        } catch (NoSuchAlgorithmException e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot verify hash: ");
            stringBuffer.append(e2.getMessage());
            throw new IOException(stringBuffer.toString());
        }
    }
}

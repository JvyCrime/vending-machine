package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/* JADX INFO: loaded from: classes.dex */
public class EncryptedContentInfo extends EncryptedContentInfoStream {
    byte[] a;
    boolean b;
    int c;

    protected EncryptedContentInfo() {
        this.c = 0;
        this.i = -1;
    }

    public EncryptedContentInfo(ASN1Object aSN1Object) throws PKCSParsingException {
        this();
        decode(aSN1Object);
        this.b = true;
    }

    public EncryptedContentInfo(ObjectID objectID, AlgorithmID algorithmID) {
        this();
        this.d = objectID;
        this.e = algorithmID;
        this.b = false;
    }

    public EncryptedContentInfo(ObjectID objectID, byte[] bArr) {
        this();
        this.a = bArr;
        this.d = objectID;
        this.h = 1;
    }

    public EncryptedContentInfo(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    public void decode(ASN1Object aSN1Object) throws PKCSParsingException {
        this.b = true;
        if (aSN1Object == null) {
            throw new PKCSParsingException("Cannot decode a null object!");
        }
        try {
            byte[] bArrEncode = DerCoder.encode(aSN1Object);
            if (bArrEncode == null) {
                throw new PKCSParsingException("Error parsing ASN.1 object!");
            }
            decode(new ByteArrayInputStream(bArrEncode));
        } catch (IOException e) {
            throw new PKCSParsingException(e.getMessage());
        }
    }

    @Override // iaik.pkcs.pkcs7.EncryptedContentInfoStream
    protected void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        this.b = true;
        super.decode(inputStream);
        if (this.f != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Util.copyStream(this.f, byteArrayOutputStream, null);
            this.a = byteArrayOutputStream.toByteArray();
        }
    }

    public byte[] getContent() {
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

    @Override // iaik.pkcs.pkcs7.EncryptedContentInfoStream
    public InputStream getInputStream() {
        byte[] content = getContent();
        if (content != null) {
            return new ByteArrayInputStream(content);
        }
        return null;
    }

    @Override // iaik.pkcs.pkcs7.EncryptedContentInfoStream
    public boolean hasContent() {
        return this.a != null;
    }

    public void setVersion(int i) {
        if (i == 1) {
            this.c = 1;
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

    @Override // iaik.pkcs.pkcs7.EncryptedContentInfoStream
    public void setupCipher(AlgorithmID algorithmID, Key key, AlgorithmParameterSpec algorithmParameterSpec) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        super.setupCipher(algorithmID, key, algorithmParameterSpec);
        if (this.g == null || this.a == null) {
            return;
        }
        try {
            this.a = this.c == 1 ? this.g.doFinal(DerCoder.encode(new OCTET_STRING(this.a))) : this.g.doFinal(this.a);
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Padding error at content encryption: ");
            stringBuffer.append(e.getMessage());
            throw new InvalidAlgorithmParameterException(stringBuffer.toString());
        } catch (IllegalBlockSizeException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Illegal block size for content encryption: ");
            stringBuffer2.append(e2.getMessage());
            throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
        }
    }

    @Override // iaik.pkcs.pkcs7.EncryptedContentInfoStream
    public void setupCipher(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        super.setupCipher(key, algorithmParameterSpec);
        byte[] bArr = this.a;
        if (bArr != null) {
            try {
                byte[] bArrDoFinal = this.g.doFinal(bArr);
                this.a = bArrDoFinal;
                if (this.c == 1) {
                    this.a = (byte[]) ((OCTET_STRING) DerCoder.decode(bArrDoFinal)).getValue();
                }
            } catch (CodingException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error decoding PKCS#7v1.6 decrypted content: ");
                stringBuffer.append(e.getMessage());
                throw new InvalidAlgorithmParameterException(stringBuffer.toString());
            } catch (BadPaddingException e2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Padding error: ");
                stringBuffer2.append(e2.toString());
                throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
            } catch (IllegalBlockSizeException e3) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Illegal blocksize: ");
                stringBuffer3.append(e3.toString());
                throw new InvalidAlgorithmParameterException(stringBuffer3.toString());
            }
        }
    }

    @Override // iaik.pkcs.pkcs7.EncryptedContentInfoStream
    public ASN1Object toASN1Object() throws PKCSException {
        if (this.e == null) {
            throw new PKCSException("contentEncryptionAlgorithm field not set!");
        }
        SEQUENCE sequence = new SEQUENCE(this.i > 0);
        sequence.addComponent(this.d);
        sequence.addComponent(this.e.toASN1Object());
        byte[] bArr = this.a;
        if (bArr != null && bArr.length > 0) {
            sequence.addComponent(new CON_SPEC(0, this.i > 0 ? new OCTET_STRING(new ByteArrayInputStream(this.a), this.i) : new OCTET_STRING(this.a), true));
        }
        return sequence;
    }
}

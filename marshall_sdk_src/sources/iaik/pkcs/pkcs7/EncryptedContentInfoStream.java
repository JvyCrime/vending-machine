package iaik.pkcs.pkcs7;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.utils.CipherInputStream;
import iaik.utils.InternalErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class EncryptedContentInfoStream {
    ObjectID d;
    AlgorithmID e;
    InputStream f;
    Cipher g;
    int h;
    int i;

    protected EncryptedContentInfoStream() {
        this.h = -1;
        this.i = 2048;
    }

    public EncryptedContentInfoStream(ObjectID objectID, AlgorithmID algorithmID) {
        this();
        this.d = objectID;
        this.e = algorithmID;
    }

    public EncryptedContentInfoStream(ObjectID objectID, InputStream inputStream) {
        this();
        this.f = inputStream;
        this.d = objectID;
        this.h = 1;
    }

    public EncryptedContentInfoStream(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        decode(inputStream);
    }

    protected void decode(InputStream inputStream) throws PKCSParsingException, IOException {
        this.h = 2;
        DerInputStream sequence = inputStream instanceof DerInputStream ? ((DerInputStream) inputStream).readSequence() : new DerInputStream(inputStream).readSequence();
        this.d = sequence.readObjectID();
        this.e = new AlgorithmID(sequence);
        if (sequence.nextTag() == -1) {
            return;
        }
        if (sequence.readContextSpecific(4) != 0) {
            throw new IOException("Error parsing encrypted content!");
        }
        this.f = sequence.readOctetString();
    }

    public int getBlockSize() {
        return this.i;
    }

    public AlgorithmID getContentEncryptionAlgorithm() {
        return this.e;
    }

    public ObjectID getContentType() {
        return this.d;
    }

    public InputStream getInputStream() {
        if (this.g == null) {
            throw new InternalErrorException("Cipher yet not initialized!");
        }
        if (this.f == null) {
            return null;
        }
        return new CipherInputStream(this.f, this.g);
    }

    public boolean hasContent() {
        return this.f != null;
    }

    public void setBlockSize(int i) {
        this.i = i;
    }

    public SecretKey setupCipher(AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        return setupCipher(algorithmID, -1);
    }

    public SecretKey setupCipher(AlgorithmID algorithmID, int i) throws NoSuchAlgorithmException {
        String implementationName = algorithmID.getImplementationName();
        int iIndexOf = implementationName.indexOf(47);
        if (iIndexOf == -1) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Algorithm ");
            stringBuffer.append(implementationName);
            stringBuffer.append(" cannot be used with this method. ");
            stringBuffer.append("Please try another setup method!");
            throw new NoSuchAlgorithmException(stringBuffer.toString());
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(implementationName.substring(0, iIndexOf));
        if (i == -1) {
            if (algorithmID.equals(AlgorithmID.aes256_CBC)) {
                i = 256;
            } else if (algorithmID.equals(AlgorithmID.aes192_CBC)) {
                i = 192;
            } else if (algorithmID.equals(AlgorithmID.aes128_CBC)) {
                i = 128;
            }
        }
        if (i < 0) {
            keyGenerator.init((SecureRandom) null);
        } else {
            keyGenerator.init(i);
        }
        SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
        try {
            setupCipher(algorithmID, secretKeyGenerateKey, null);
            return secretKeyGenerateKey;
        } catch (InvalidAlgorithmParameterException unused) {
            throw new InternalErrorException("We don't set an AlgorithmParameter.");
        } catch (InvalidKeyException unused2) {
            throw new InternalErrorException("Internal Key problem.");
        }
    }

    public void setupCipher(AlgorithmID algorithmID, Key key, AlgorithmParameterSpec algorithmParameterSpec) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] wholeValue;
        this.e = (AlgorithmID) algorithmID.clone();
        ASN1Object parameter = algorithmID.getParameter();
        if (algorithmParameterSpec == null || parameter != null) {
            if (algorithmParameterSpec == null && parameter != null && (parameter instanceof OCTET_STRING)) {
                try {
                    wholeValue = ((OCTET_STRING) parameter).getWholeValue();
                } catch (IOException unused) {
                    wholeValue = null;
                }
                if (wholeValue != null) {
                    algorithmParameterSpec = new IvParameterSpec(wholeValue);
                }
            }
        } else if (algorithmParameterSpec instanceof IvParameterSpec) {
            OCTET_STRING octet_string = new OCTET_STRING(((IvParameterSpec) algorithmParameterSpec).getIV());
            this.e.setParameter(octet_string);
            parameter = octet_string;
        }
        Cipher cipherInstance = this.e.getCipherInstance();
        this.g = cipherInstance;
        cipherInstance.init(1, key, algorithmParameterSpec, (SecureRandom) null);
        if (parameter == null) {
            AlgorithmParameters parameters = this.g.getParameters();
            if (parameters != null) {
                this.e.setAlgorithmParameters(parameters);
                return;
            }
            byte[] iv = this.g.getIV();
            if (iv != null) {
                this.e.setParameter(new OCTET_STRING(iv));
            }
        }
    }

    public void setupCipher(Key key) throws NoSuchAlgorithmException, PKCSException, InvalidKeyException {
        AlgorithmParameterSpec ivParameterSpec;
        try {
            ivParameterSpec = this.e.getAlgorithmParameterSpec();
        } catch (Exception unused) {
            ivParameterSpec = null;
        }
        if (ivParameterSpec == null) {
            try {
                ASN1Object parameter = this.e.getParameter();
                if (parameter != null && parameter.isA(ASN.OCTET_STRING)) {
                    ivParameterSpec = new IvParameterSpec((byte[]) parameter.getValue());
                }
            } catch (Exception unused2) {
                throw new PKCSException("Unable to get algorithm parameter!");
            }
        }
        try {
            setupCipher(key, ivParameterSpec);
        } catch (InvalidAlgorithmParameterException unused3) {
            throw new PKCSException("Wrong algorithm parameter!");
        }
    }

    public void setupCipher(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.h == -1) {
            throw new InternalErrorException("Object not configured for en/decrypting!");
        }
        Cipher cipherInstance = this.e.getCipherInstance();
        this.g = cipherInstance;
        cipherInstance.init(this.h, key, algorithmParameterSpec, (SecureRandom) null);
    }

    public ASN1Object toASN1Object() throws PKCSException {
        if (this.e == null) {
            throw new PKCSException("contentEncryptionAlgorithm field not set!");
        }
        SEQUENCE sequence = new SEQUENCE(true);
        sequence.addComponent(this.d);
        sequence.addComponent(this.e.toASN1Object());
        try {
            if (this.f != null) {
                if (this.g == null) {
                    throw new PKCSException("Cipher not initialized!");
                }
                this.f = new CipherInputStream(this.f, this.g, this.i);
                sequence.addComponent(new CON_SPEC(0, new OCTET_STRING(this.f, this.i), true));
            }
            return sequence;
        } catch (Exception e) {
            throw new PKCSException(e.getMessage());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("ContentType: ");
        stringBuffer2.append(this.d.getName());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("contentEncryptionAlgorithm: ");
        stringBuffer3.append(this.e);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws PKCSException, IOException {
        DerCoder.encodeTo(toASN1Object(), outputStream);
    }
}

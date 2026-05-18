package iaik.pkcs.pkcs8;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.random.SecRandom;
import iaik.utils.InternalErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class EncryptedPrivateKeyInfo implements ASN1Type, PrivateKey {
    private static final Vector d;
    private static final long serialVersionUID = -6227472653361186792L;
    PrivateKey a;
    AlgorithmID b;
    ASN1 c;

    static {
        Vector vector = new Vector();
        d = vector;
        vector.add("PBES2");
        vector.add("1.2.840.113549.1.5.13");
        vector.add("PBES2WithHmacSHA1AndAES");
        vector.add("PBES2WithHmacSHA1AndAES128");
        vector.add("PBEWithHmacSHA1AndAES");
        vector.add("PBEWithHmacSHA1AndAES128");
        vector.add("PBES2WithHmacSHA256AndAES");
        vector.add("PBES2WithHmacSHA256AndAES128");
        vector.add("PBEWithHmacSHA256AndAES");
        vector.add("PBEWithHmacSHA256AndAES128");
        vector.add("PBES2WithHmacSHA384AndAES192");
        vector.add("PBEWithHmacSHA384AndAES192");
        vector.add("PBES2WithHmacSHA512AndAES256");
        vector.add("PBEWithHmacSHA512AndAES256");
        vector.add("PBES2WithHmacSHA1AndDESede");
        vector.add("PBEWithHmacSHA1AndDESede");
        vector.add("PBES2WithHmacSHA1AndTripleDES");
        vector.add("PBEWithHmacSHA1AndTripleDES");
    }

    public EncryptedPrivateKeyInfo(ASN1Object aSN1Object) throws InvalidKeyException {
        try {
            this.c = new ASN1(aSN1Object);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public EncryptedPrivateKeyInfo(InputStream inputStream) throws InvalidKeyException, IOException {
        try {
            this.c = new ASN1(inputStream);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public EncryptedPrivateKeyInfo(PrivateKey privateKey) {
        this.a = privateKey;
    }

    public EncryptedPrivateKeyInfo(byte[] bArr) throws InvalidKeyException {
        try {
            this.c = new ASN1(bArr);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    private static final Key a(char[] cArr, AlgorithmID algorithmID) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return algorithmID.getSecretKeyFactoryInstance("IAIK").generateSecret(new PBEKeySpec(cArr));
    }

    private static final AlgorithmParameterSpec a(AlgorithmID algorithmID, int i, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        int iterationCount;
        AlgorithmParameterSpec algorithmParameterSpec = algorithmID.getAlgorithmParameterSpec();
        if (algorithmParameterSpec == null) {
            if (i < 0) {
                i = 2000;
            }
            byte[] bArr = new byte[algorithmID.equals(AlgorithmID.pbes2) ? 32 : 8];
            if (secureRandom == null) {
                secureRandom = SecRandom.getDefault();
            }
            secureRandom.nextBytes(bArr);
            return new PBEParameterSpec(bArr, i);
        }
        if (i <= 0 || !(algorithmParameterSpec instanceof PBEParameterSpec) || i == (iterationCount = ((PBEParameterSpec) algorithmParameterSpec).getIterationCount())) {
            return algorithmParameterSpec;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("iterationCount (");
        stringBuffer.append(i);
        stringBuffer.append(") differs");
        stringBuffer.append(" from algorithm parameters iteration count (");
        stringBuffer.append(iterationCount);
        stringBuffer.append(")!");
        throw new InvalidAlgorithmParameterException(stringBuffer.toString());
    }

    private void a() throws InvalidKeyException {
        try {
            this.b = new AlgorithmID(this.c.getComponentAt(0));
        } catch (CodingException e) {
            throw new InvalidKeyException(e.toString());
        }
    }

    private void a(char[] cArr, AlgorithmID algorithmID, String str, SecureRandom secureRandom, AlgorithmParameterSpec algorithmParameterSpec) throws NoSuchAlgorithmException {
        if (this.a == null) {
            Objects.requireNonNull(this.c, "Nothing to encrypt. Private key not set!");
            return;
        }
        this.b = (AlgorithmID) algorithmID.clone();
        try {
            Cipher cipher = Cipher.getInstance(str);
            cipher.init(1, a(cArr, this.b), algorithmParameterSpec, secureRandom);
            byte[] bArrDoFinal = cipher.doFinal(this.a.getEncoded());
            this.b.setAlgorithmParameters(cipher.getParameters());
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(this.b.toASN1Object());
            sequence.addComponent(new OCTET_STRING(bArrDoFinal));
            this.c = new ASN1(sequence);
            this.a = null;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            this.c = new ASN1(objectInputStream);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to restore PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        } catch (InvalidKeyException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Unable to restore PrivateKeyInfo: ");
            stringBuffer2.append(e2.toString());
            throw new IOException(stringBuffer2.toString());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.c == null) {
            throw new IOException("Private key not encrypted yet.");
        }
        objectOutputStream.write(getEncoded());
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.c = new ASN1(aSN1Object);
        try {
            a();
        } catch (InvalidKeyException e) {
            throw new CodingException(e.toString());
        }
    }

    public PrivateKey decrypt(String str) throws GeneralSecurityException {
        return decrypt(str.toCharArray());
    }

    public PrivateKey decrypt(char[] cArr) throws GeneralSecurityException {
        AlgorithmParameterSpec algorithmParameterSpec = this.b.getAlgorithmParameterSpec();
        Cipher cipherInstance = this.b.getCipherInstance();
        try {
            cipherInstance.init(2, a(cArr, this.b), algorithmParameterSpec);
            PrivateKey privateKey = PrivateKeyInfo.getPrivateKey(cipherInstance.doFinal((byte[]) this.c.getComponentAt(1).getValue()));
            this.a = privateKey;
            return privateKey;
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error decrypting private key: ");
            stringBuffer.append(e.toString());
            throw new GeneralSecurityException(this, stringBuffer.toString(), e) { // from class: iaik.pkcs.pkcs8.EncryptedPrivateKeyInfo.1
                private static final long serialVersionUID = -712183313106196366L;
                private final Exception a;
                private final EncryptedPrivateKeyInfo b;

                {
                    this.b = this;
                    this.a = e;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        }
    }

    public void encrypt(String str, AlgorithmID algorithmID, SecureRandom secureRandom) throws NoSuchAlgorithmException {
        encrypt(str.toCharArray(), algorithmID, secureRandom);
    }

    public void encrypt(char[] cArr, AlgorithmID algorithmID, SecureRandom secureRandom) throws NoSuchAlgorithmException {
        try {
            a(cArr, algorithmID, algorithmID.getImplementationName(), secureRandom, a(algorithmID, -1, secureRandom));
        } catch (InvalidAlgorithmParameterException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid algorithm parameters: ");
            stringBuffer.append(e.toString());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public void encrypt(char[] cArr, AlgorithmID algorithmID, SecureRandom secureRandom, int i) throws NoSuchAlgorithmException {
        if (i <= 0) {
            throw new IllegalArgumentException("iterationCount must not be negative!");
        }
        try {
            a(cArr, algorithmID, algorithmID.getImplementationName(), secureRandom, a(algorithmID, i, secureRandom));
        } catch (InvalidAlgorithmParameterException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid algorithm parameters: ");
            stringBuffer.append(e.toString());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public void encrypt(char[] cArr, String str) throws NoSuchAlgorithmException {
        encrypt(cArr, str, SecRandom.getDefault());
    }

    public void encrypt(char[] cArr, String str, SecureRandom secureRandom) throws NoSuchAlgorithmException {
        AlgorithmID algorithmID = AlgorithmID.getAlgorithmID(str);
        if (algorithmID == null && d.contains(str)) {
            algorithmID = AlgorithmID.pbes2;
        }
        AlgorithmID algorithmID2 = algorithmID;
        if (algorithmID2 != null) {
            a(cArr, algorithmID2, str, secureRandom, null);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Algorithm ");
        stringBuffer.append(str);
        stringBuffer.append(" not supported!");
        throw new NoSuchAlgorithmException(stringBuffer.toString());
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return "ENCRYPTED";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return this.c.toByteArray();
    }

    @Override // java.security.Key
    public String getFormat() {
        return "PKCS#8";
    }

    public PrivateKey getPrivateKeyInfo() {
        return this.a;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return this.c.toASN1Object();
    }

    public String toString() {
        StringBuffer stringBuffer;
        String name;
        StringBuffer stringBuffer2 = new StringBuffer();
        if (this.b != null) {
            stringBuffer = new StringBuffer();
            stringBuffer.append("Private key is encrypted with algorithm: ");
            name = this.b.getName();
        } else {
            stringBuffer = new StringBuffer();
            stringBuffer.append(this.a.getAlgorithm());
            name = " private key is not encrypted yet.";
        }
        stringBuffer.append(name);
        stringBuffer2.append(stringBuffer.toString());
        return stringBuffer2.toString();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.c.writeTo(outputStream);
    }
}

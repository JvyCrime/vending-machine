package iaik.x509;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.Md5;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/* JADX INFO: loaded from: classes2.dex */
public abstract class PublicKeyInfo implements ASN1Type, Cloneable, PublicKey {
    ASN1 b;
    protected AlgorithmID public_key_algorithm;

    protected PublicKeyInfo() {
    }

    public PublicKeyInfo(ASN1Object aSN1Object) throws InvalidKeyException {
        try {
            this.b = new ASN1(aSN1Object);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PublicKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public PublicKeyInfo(InputStream inputStream) throws InvalidKeyException, IOException {
        try {
            this.b = new ASN1(inputStream);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PublicKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public PublicKeyInfo(byte[] bArr) throws InvalidKeyException {
        try {
            this.b = new ASN1(bArr);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PublicKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    private void a() throws InvalidKeyException {
        try {
            this.public_key_algorithm = new AlgorithmID(this.b.getComponentAt(0));
            decode((byte[]) this.b.getComponentAt(1).getValue());
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PublicKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public static PublicKey getPublicKey(ASN1Object aSN1Object) throws InvalidKeyException {
        return getPublicKey(aSN1Object, (String) null);
    }

    public static PublicKey getPublicKey(ASN1Object aSN1Object, String str) throws InvalidKeyException {
        try {
            return new AlgorithmID(aSN1Object.getComponentAt(0)).getKeyFactoryInstance(str).generatePublic(new X509EncodedKeySpec(DerCoder.encode(aSN1Object)));
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Can't parse PublicKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        } catch (NoSuchAlgorithmException unused) {
            return new RawPublicKey(aSN1Object);
        } catch (InvalidKeySpecException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Can't parse PublicKeyInfo: ");
            stringBuffer2.append(e2.toString());
            throw new InvalidKeyException(stringBuffer2.toString());
        }
    }

    public static PublicKey getPublicKey(byte[] bArr) throws InvalidKeyException {
        return getPublicKey(bArr, (String) null);
    }

    public static PublicKey getPublicKey(byte[] bArr, String str) throws InvalidKeyException {
        try {
            return getPublicKey(DerCoder.decode(bArr), str);
        } catch (CodingException unused) {
            throw new InvalidKeyException("Can't parse PublicKeyInfo.");
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            this.b = new ASN1(objectInputStream);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to restore PublicKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        } catch (InvalidKeyException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Unable to restore PublicKeyInfo: ");
            stringBuffer2.append(e2.toString());
            throw new IOException(stringBuffer2.toString());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.write(getEncoded());
    }

    public Object clone() {
        try {
            return (PublicKeyInfo) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    protected void createPublicKeyInfo() {
        try {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(this.public_key_algorithm.toASN1Object());
            sequence.addComponent(new BIT_STRING(encode()));
            this.b = new ASN1(sequence);
        } catch (CodingException unused) {
            throw new InternalErrorException("CodingException!");
        }
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.b = new ASN1(aSN1Object);
        try {
            a();
        } catch (InvalidKeyException e) {
            throw new CodingException(e.toString());
        }
    }

    protected abstract void decode(byte[] bArr) throws InvalidKeyException;

    protected abstract byte[] encode();

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PublicKey) {
            return CryptoUtils.equalsBlock(getEncoded(), ((PublicKey) obj).getEncoded());
        }
        return false;
    }

    public abstract String getAlgorithm();

    public AlgorithmID getAlgorithmID() {
        return this.public_key_algorithm;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return this.b.toByteArray();
    }

    public byte[] getFingerprint() {
        Md5 md5 = new Md5();
        md5.update(encode());
        return md5.digest();
    }

    @Override // java.security.Key
    public String getFormat() {
        return "X.509";
    }

    public int hashCode() {
        return Util.calculateHashCode(getEncoded());
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return this.b.toASN1Object();
    }

    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("This PublicKeyInfo contains a ");
        if (this.public_key_algorithm.equals(AlgorithmID.rsaEncryption)) {
            string = "RSA public key.";
        } else if (this.public_key_algorithm.equals(AlgorithmID.dsa)) {
            string = "DSA public key.";
        } else if (this.public_key_algorithm.equals(AlgorithmID.dhKeyAgreement)) {
            string = "DH public key.";
        } else if (this.public_key_algorithm.equals(AlgorithmID.ecPublicKey)) {
            string = "EC public key.";
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.public_key_algorithm.getName());
            stringBuffer2.append(" key.");
            string = stringBuffer2.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.b.writeTo(outputStream);
    }
}

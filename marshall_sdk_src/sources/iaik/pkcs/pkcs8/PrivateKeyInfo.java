package iaik.pkcs.pkcs8;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/* JADX INFO: loaded from: classes.dex */
public abstract class PrivateKeyInfo implements ASN1Type, Cloneable, PrivateKey {
    int a = 0;
    ASN1 b;
    protected AlgorithmID private_key_algorithm;

    protected PrivateKeyInfo() {
    }

    public PrivateKeyInfo(ASN1Object aSN1Object) throws InvalidKeyException {
        try {
            this.b = new ASN1(aSN1Object);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public PrivateKeyInfo(InputStream inputStream) throws InvalidKeyException, IOException {
        try {
            this.b = new ASN1(inputStream);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public PrivateKeyInfo(byte[] bArr) throws InvalidKeyException {
        try {
            this.b = new ASN1(bArr);
            a();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    private void a() throws InvalidKeyException {
        try {
            this.a = ((BigInteger) this.b.getComponentAt(0).getValue()).intValue();
            this.private_key_algorithm = new AlgorithmID(this.b.getComponentAt(1));
            decode((byte[]) this.b.getComponentAt(2).getValue());
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    public static PrivateKey getPrivateKey(ASN1Object aSN1Object) throws InvalidKeyException {
        return getPrivateKey(aSN1Object, (String) null);
    }

    public static PrivateKey getPrivateKey(ASN1Object aSN1Object, String str) throws InvalidKeyException {
        try {
            return new AlgorithmID(aSN1Object.getComponentAt(1)).getKeyFactoryInstance(str).generatePrivate(new PKCS8EncodedKeySpec(DerCoder.encode(aSN1Object)));
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Can't parse PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        } catch (NoSuchAlgorithmException unused) {
            return new RawPrivateKey(aSN1Object);
        } catch (InvalidKeySpecException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Can't parse PrivateKeyInfo: ");
            stringBuffer2.append(e2.toString());
            throw new InvalidKeyException(stringBuffer2.toString());
        } catch (Exception e3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Can't parse PrivateKeyInfo: ");
            stringBuffer3.append(e3.toString());
            throw new InvalidKeyException(stringBuffer3.toString());
        }
    }

    public static PrivateKey getPrivateKey(byte[] bArr) throws InvalidKeyException {
        return getPrivateKey(bArr, (String) null);
    }

    public static PrivateKey getPrivateKey(byte[] bArr, String str) throws InvalidKeyException {
        try {
            return getPrivateKey(DerCoder.decode(bArr), str);
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Can't parse PrivateKeyInfo: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            this.b = new ASN1(objectInputStream);
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
        objectOutputStream.write(getEncoded());
    }

    public Object clone() {
        try {
            return (PrivateKeyInfo) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    protected void createPrivateKeyInfo() {
        try {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(new INTEGER(this.a));
            sequence.addComponent(this.private_key_algorithm.toASN1Object());
            sequence.addComponent(new OCTET_STRING(encode()));
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
        if (obj instanceof PrivateKey) {
            return CryptoUtils.secureEqualsBlock(getEncoded(), ((PrivateKey) obj).getEncoded());
        }
        return false;
    }

    @Override // java.security.Key
    public abstract String getAlgorithm();

    public AlgorithmID getAlgorithmID() {
        return this.private_key_algorithm;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return this.b.toByteArray();
    }

    @Override // java.security.Key
    public String getFormat() {
        return "PKCS#8";
    }

    public int hashCode() {
        return Util.calculateHashCode(getEncoded());
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return this.b.toASN1Object();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("This PrivateKeyInfo contains a ");
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(this.private_key_algorithm.getName());
        stringBuffer2.append(" key.");
        stringBuffer.append(stringBuffer2.toString());
        return stringBuffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.b.writeTo(outputStream);
    }
}

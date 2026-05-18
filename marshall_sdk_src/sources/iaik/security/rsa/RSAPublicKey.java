package iaik.security.rsa;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.RSACipher;
import iaik.utils.InternalErrorException;
import iaik.x509.PublicKeyInfo;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.spec.RSAPublicKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class RSAPublicKey extends PublicKeyInfo implements java.security.interfaces.RSAPublicKey {
    private transient ASN1 a;
    private BigInteger c;
    private BigInteger d;

    RSAPublicKey() {
    }

    public RSAPublicKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public RSAPublicKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public RSAPublicKey(BigInteger bigInteger, BigInteger bigInteger2) {
        a(bigInteger, bigInteger2);
        a(true);
    }

    public RSAPublicKey(java.security.interfaces.RSAPublicKey rSAPublicKey) {
        this(rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
    }

    public RSAPublicKey(RSAPublicKeySpec rSAPublicKeySpec) {
        this(rSAPublicKeySpec.getModulus(), rSAPublicKeySpec.getPublicExponent());
    }

    public RSAPublicKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    public static RSAPublicKey parse(byte[] bArr) throws InvalidKeyException {
        RSAPublicKey rSAPublicKey = new RSAPublicKey();
        rSAPublicKey.decode(bArr);
        rSAPublicKey.a(true);
        return rSAPublicKey;
    }

    void a(BigInteger bigInteger, BigInteger bigInteger2) {
        this.c = bigInteger;
        this.d = bigInteger2;
    }

    void a(boolean z) {
        try {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(new INTEGER(this.c));
            sequence.addComponent(new INTEGER(this.d));
            this.a = new ASN1(sequence);
            if (z) {
                this.public_key_algorithm = (AlgorithmID) AlgorithmID.rsaEncryption.clone();
                createPublicKeyInfo();
            }
        } catch (CodingException e) {
            throw new InternalErrorException(e);
        }
    }

    public BigInteger crypt(BigInteger bigInteger) {
        return new RSACipher().rawPublicRSA(bigInteger, this);
    }

    @Override // iaik.x509.PublicKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        try {
            ASN1 asn1 = new ASN1(bArr);
            this.a = asn1;
            this.c = (BigInteger) asn1.getComponentAt(0).getValue();
            this.d = (BigInteger) this.a.getComponentAt(1).getValue();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No RSA Public Key: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // iaik.x509.PublicKeyInfo
    public byte[] encode() {
        return this.a.toByteArray();
    }

    @Override // iaik.x509.PublicKeyInfo
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RSAPublicKey)) {
            return false;
        }
        RSAPublicKey rSAPublicKey = (RSAPublicKey) obj;
        return this.c.equals(rSAPublicKey.c) && this.d.equals(rSAPublicKey.d);
    }

    @Override // iaik.x509.PublicKeyInfo, java.security.Key
    public String getAlgorithm() {
        return "RSA";
    }

    @Override // iaik.x509.PublicKeyInfo
    public byte[] getFingerprint() {
        return this.a.fingerprint();
    }

    @Override // java.security.interfaces.RSAKey
    public BigInteger getModulus() {
        return this.c;
    }

    @Override // java.security.interfaces.RSAPublicKey
    public BigInteger getPublicExponent() {
        return this.d;
    }

    @Override // iaik.x509.PublicKeyInfo
    public int hashCode() {
        return this.d.hashCode() ^ this.c.hashCode();
    }

    @Override // iaik.x509.PublicKeyInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(getAlgorithm());
        stringBuffer2.append(" public key (");
        stringBuffer2.append(this.c.bitLength());
        stringBuffer2.append(" bits):\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("public exponent: ");
        stringBuffer3.append(this.d.toString(16));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("modulus: ");
        stringBuffer4.append(this.c.toString(16));
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        return stringBuffer.toString();
    }
}

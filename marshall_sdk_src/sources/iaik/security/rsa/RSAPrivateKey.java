package iaik.security.rsa;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.RSACipher;
import iaik.pkcs.pkcs8.PrivateKeyInfo;
import iaik.utils.InternalErrorException;
import iaik.utils.NumberTheory;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class RSAPrivateKey extends PrivateKeyInfo implements RSAPrivateCrtKey {
    private transient ASN1 c;
    int d;
    private BigInteger e;
    private BigInteger f;
    private BigInteger g;
    private BigInteger h;
    private BigInteger i;
    private BigInteger j;
    private BigInteger k;
    private BigInteger l;

    protected RSAPrivateKey() {
    }

    public RSAPrivateKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public RSAPrivateKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public RSAPrivateKey(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, NumberTheory.ZERO, bigInteger2, NumberTheory.ZERO, NumberTheory.ZERO, NumberTheory.ZERO, NumberTheory.ZERO, NumberTheory.ZERO);
    }

    public RSAPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        a(bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigInteger6, bigInteger7, bigInteger8);
        a(true);
    }

    public RSAPrivateKey(java.security.interfaces.RSAPrivateKey rSAPrivateKey) {
        if (rSAPrivateKey instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) rSAPrivateKey;
            a(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent(), rSAPrivateCrtKey.getPrivateExponent(), rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ(), rSAPrivateCrtKey.getPrimeExponentP(), rSAPrivateCrtKey.getPrimeExponentQ(), rSAPrivateCrtKey.getCrtCoefficient());
        } else {
            a(rSAPrivateKey.getModulus(), rSAPrivateKey.getPrivateExponent());
        }
        a(true);
    }

    public RSAPrivateKey(RSAPrivateKeySpec rSAPrivateKeySpec) {
        if (rSAPrivateKeySpec instanceof RSAPrivateCrtKeySpec) {
            RSAPrivateCrtKeySpec rSAPrivateCrtKeySpec = (RSAPrivateCrtKeySpec) rSAPrivateKeySpec;
            a(rSAPrivateCrtKeySpec.getModulus(), rSAPrivateCrtKeySpec.getPublicExponent(), rSAPrivateCrtKeySpec.getPrivateExponent(), rSAPrivateCrtKeySpec.getPrimeP(), rSAPrivateCrtKeySpec.getPrimeQ(), rSAPrivateCrtKeySpec.getPrimeExponentP(), rSAPrivateCrtKeySpec.getPrimeExponentQ(), rSAPrivateCrtKeySpec.getCrtCoefficient());
        } else {
            a(rSAPrivateKeySpec.getModulus(), rSAPrivateKeySpec.getPrivateExponent());
        }
        a(true);
    }

    public RSAPrivateKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    public static RSAPrivateKey parse(byte[] bArr) throws InvalidKeyException {
        RSAPrivateKey rSAPrivateKey = new RSAPrivateKey();
        rSAPrivateKey.decode(bArr);
        rSAPrivateKey.a(true);
        return rSAPrivateKey;
    }

    void a(BigInteger bigInteger, BigInteger bigInteger2) {
        a(bigInteger, NumberTheory.ZERO, bigInteger2, NumberTheory.ZERO, NumberTheory.ZERO, NumberTheory.ZERO, NumberTheory.ZERO, NumberTheory.ZERO);
    }

    void a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        this.g = bigInteger3;
        this.f = bigInteger2;
        this.e = bigInteger;
        this.h = bigInteger4;
        this.i = bigInteger5;
        this.j = bigInteger6;
        this.k = bigInteger7;
        this.l = bigInteger8;
    }

    void a(boolean z) {
        try {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(new INTEGER(this.d));
            sequence.addComponent(new INTEGER(this.e));
            sequence.addComponent(new INTEGER(this.f));
            sequence.addComponent(new INTEGER(this.g));
            sequence.addComponent(new INTEGER(this.h));
            sequence.addComponent(new INTEGER(this.i));
            sequence.addComponent(new INTEGER(this.j));
            sequence.addComponent(new INTEGER(this.k));
            sequence.addComponent(new INTEGER(this.l));
            this.c = new ASN1(sequence);
            if (z) {
                this.private_key_algorithm = (AlgorithmID) AlgorithmID.rsaEncryption.clone();
                createPrivateKeyInfo();
            }
        } catch (CodingException e) {
            throw new InternalErrorException(e);
        }
    }

    public BigInteger crypt(BigInteger bigInteger) {
        return new RSACipher().rawPrivateRSA(bigInteger, this, null);
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        try {
            ASN1 asn1 = new ASN1(bArr);
            this.c = asn1;
            this.d = ((BigInteger) asn1.getComponentAt(0).getValue()).intValue();
            this.e = (BigInteger) this.c.getComponentAt(1).getValue();
            this.f = (BigInteger) this.c.getComponentAt(2).getValue();
            this.g = (BigInteger) this.c.getComponentAt(3).getValue();
            this.h = (BigInteger) this.c.getComponentAt(4).getValue();
            this.i = (BigInteger) this.c.getComponentAt(5).getValue();
            this.j = (BigInteger) this.c.getComponentAt(6).getValue();
            this.k = (BigInteger) this.c.getComponentAt(7).getValue();
            this.l = (BigInteger) this.c.getComponentAt(8).getValue();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No RSA Private Key: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public byte[] encode() {
        return this.c.toByteArray();
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo, java.security.Key
    public String getAlgorithm() {
        return "RSA";
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getCrtCoefficient() {
        return this.l;
    }

    @Override // java.security.interfaces.RSAKey
    public BigInteger getModulus() {
        return this.e;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPrimeExponentP() {
        return this.j;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPrimeExponentQ() {
        return this.k;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPrimeP() {
        return this.h;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPrimeQ() {
        return this.i;
    }

    @Override // java.security.interfaces.RSAPrivateKey
    public BigInteger getPrivateExponent() {
        return this.g;
    }

    @Override // java.security.interfaces.RSAPrivateCrtKey
    public BigInteger getPublicExponent() {
        return this.f;
    }

    public PublicKey getPublicKey() {
        return new RSAPublicKey(this.e, this.f);
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public int hashCode() {
        return ((((((this.e.hashCode() ^ this.f.hashCode()) ^ this.g.hashCode()) ^ this.h.hashCode()) ^ this.i.hashCode()) ^ this.j.hashCode()) ^ this.k.hashCode()) ^ this.l.hashCode();
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(getAlgorithm());
        stringBuffer2.append(" private key (");
        stringBuffer2.append(this.e.bitLength());
        stringBuffer2.append(" bits):\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("modulus: ");
        stringBuffer3.append(this.e.toString(16));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("public exponent: ");
        stringBuffer4.append(this.f.toString(16));
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append("private exponent: ");
        stringBuffer5.append(this.g.toString(16));
        stringBuffer5.append("\n");
        stringBuffer.append(stringBuffer5.toString());
        StringBuffer stringBuffer6 = new StringBuffer();
        stringBuffer6.append("primeP: ");
        stringBuffer6.append(this.h.toString(16));
        stringBuffer6.append("\n");
        stringBuffer.append(stringBuffer6.toString());
        StringBuffer stringBuffer7 = new StringBuffer();
        stringBuffer7.append("primeQ: ");
        stringBuffer7.append(this.i.toString(16));
        stringBuffer7.append("\n");
        stringBuffer.append(stringBuffer7.toString());
        StringBuffer stringBuffer8 = new StringBuffer();
        stringBuffer8.append("primeExponentP: ");
        stringBuffer8.append(this.j.toString(16));
        stringBuffer8.append("\n");
        stringBuffer.append(stringBuffer8.toString());
        StringBuffer stringBuffer9 = new StringBuffer();
        stringBuffer9.append("primeExponentQ: ");
        stringBuffer9.append(this.k.toString(16));
        stringBuffer9.append("\n");
        stringBuffer.append(stringBuffer9.toString());
        StringBuffer stringBuffer10 = new StringBuffer();
        stringBuffer10.append("crt_coefficient: ");
        stringBuffer10.append(this.l.toString(16));
        stringBuffer10.append("\n");
        stringBuffer.append(stringBuffer10.toString());
        return stringBuffer.toString();
    }
}

package iaik.security.dsa;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import iaik.security.random.SecRandom;
import iaik.utils.NumberTheory;
import iaik.utils.Util;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public final class RawDSA extends SignatureSpi {
    private BigInteger a;
    private BigInteger b;
    private BigInteger c;
    private BigInteger d;
    protected ByteArrayOutputStream dataBuffer_ = new ByteArrayOutputStream(32);
    private BigInteger e;
    private SecureRandom f;
    private byte[] g;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    private BigInteger a() {
        BigInteger bigIntegerSubtract;
        if (this.g == null) {
            if (this.f == null) {
                this.f = SecRandom.getDefault();
            }
            bigIntegerSubtract = new BigInteger(this.d.bitLength() + 64, this.f);
        } else {
            bigIntegerSubtract = new BigInteger(1, this.g).subtract(NumberTheory.ONE);
        }
        return bigIntegerSubtract.mod(this.d.subtract(NumberTheory.ONE)).add(NumberTheory.ONE);
    }

    private BigInteger a(BigInteger bigInteger) {
        return this.e.modPow(bigInteger, this.c).mod(this.d);
    }

    private BigInteger a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        byte[] byteArray = this.dataBuffer_.toByteArray();
        int length = byteArray.length * 8;
        int iBitLength = this.d.bitLength();
        BigInteger bigInteger4 = new BigInteger(1, byteArray);
        if (iBitLength < length) {
            bigInteger4 = bigInteger4.shiftRight(length - iBitLength);
        }
        return bigInteger4.multiply(bigInteger3).mod(this.d);
    }

    private void a(java.security.interfaces.DSAParams dSAParams) {
        this.c = dSAParams.getP();
        this.d = dSAParams.getQ();
        this.e = dSAParams.getG();
    }

    private BigInteger b(BigInteger bigInteger, BigInteger bigInteger2) {
        byte[] byteArray = this.dataBuffer_.toByteArray();
        int length = byteArray.length * 8;
        int iBitLength = this.d.bitLength();
        BigInteger bigInteger3 = new BigInteger(1, byteArray);
        if (iBitLength < length) {
            bigInteger3 = bigInteger3.shiftRight(length - iBitLength);
        }
        return bigInteger.modInverse(this.d).multiply(bigInteger3.add(this.a.multiply(bigInteger2))).mod(this.d);
    }

    private BigInteger b(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        return bigInteger.multiply(bigInteger3).mod(this.d);
    }

    private BigInteger c(BigInteger bigInteger, BigInteger bigInteger2) {
        return bigInteger2.modInverse(this.d);
    }

    private BigInteger d(BigInteger bigInteger, BigInteger bigInteger2) {
        return this.e.modPow(bigInteger, this.c).multiply(this.b.modPow(bigInteger2, this.c)).mod(this.c).mod(this.d);
    }

    void a(SecureRandom secureRandom) {
        this.f = secureRandom;
    }

    byte[] a(BigInteger bigInteger, BigInteger bigInteger2) throws SignatureException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new INTEGER(bigInteger));
        sequence.addComponent(new INTEGER(bigInteger2));
        try {
            return new ASN1(sequence).toByteArray();
        } catch (CodingException unused) {
            throw new SignatureException();
        }
    }

    byte[] a(BigInteger[] bigIntegerArr) throws SignatureException {
        return a(bigIntegerArr[0], bigIntegerArr[1]);
    }

    BigInteger[] a(byte[] bArr) throws SignatureException {
        try {
            ASN1 asn1 = new ASN1(bArr);
            if (asn1.toASN1Object().isA(ASN.SEQUENCE) && asn1.countComponents() == 2) {
                return new BigInteger[]{(BigInteger) asn1.getComponentAt(0).getValue(), (BigInteger) asn1.getComponentAt(1).getValue()};
            }
        } catch (Exception unused) {
        }
        throw new SignatureException();
    }

    public BigInteger[] dsaSignRS() {
        while (true) {
            BigInteger bigIntegerA = a();
            BigInteger bigIntegerA2 = a(bigIntegerA);
            if (!bigIntegerA2.equals(NumberTheory.ZERO)) {
                BigInteger bigIntegerB = b(bigIntegerA, bigIntegerA2);
                if (!bigIntegerB.equals(NumberTheory.ZERO)) {
                    return new BigInteger[]{bigIntegerA2, bigIntegerB};
                }
            }
        }
    }

    public boolean dsaVerifyRS(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger.signum() != 1 || bigInteger2.signum() != 1 || bigInteger.compareTo(this.d) >= 0 || bigInteger2.compareTo(this.d) >= 0) {
            return false;
        }
        BigInteger bigIntegerC = c(bigInteger, bigInteger2);
        return bigInteger.equals(d(a(bigInteger, bigInteger2, bigIntegerC), b(bigInteger, bigInteger2, bigIntegerC)));
    }

    public boolean dsaVerifyRS(BigInteger[] bigIntegerArr) {
        return dsaVerifyRS(bigIntegerArr[0], bigIntegerArr[1]);
    }

    @Override // java.security.SignatureSpi
    protected Object engineGetParameter(String str) throws InvalidParameterException {
        return (str == null || !str.equals("KSEED")) ? engineGetParameters() : this.g;
    }

    @Override // java.security.SignatureSpi
    protected AlgorithmParameters engineGetParameters() {
        AlgorithmParameters algorithmParameters = null;
        if (this.c == null || this.d == null || this.e == null) {
            return null;
        }
        try {
            DSAParameterSpec dSAParameterSpec = new DSAParameterSpec(this.c, this.d, this.e);
            algorithmParameters = AlgorithmParameters.getInstance("DSA", "IAIK");
            algorithmParameters.init(dSAParameterSpec);
            return algorithmParameters;
        } catch (Exception unused) {
            return algorithmParameters;
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (!(privateKey instanceof java.security.interfaces.DSAPrivateKey)) {
            throw new InvalidKeyException("Key must be an instance of java.security.interfaces.DSAPrivateKey!");
        }
        java.security.interfaces.DSAPrivateKey dSAPrivateKey = (java.security.interfaces.DSAPrivateKey) privateKey;
        this.a = dSAPrivateKey.getX();
        a(dSAPrivateKey.getParams());
        this.dataBuffer_.reset();
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        this.f = secureRandom;
        engineInitSign(privateKey);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        if (!(publicKey instanceof java.security.interfaces.DSAPublicKey)) {
            throw new InvalidKeyException("Key must be an instance of java.security.interfaces.DSAPublicKey!");
        }
        java.security.interfaces.DSAPublicKey dSAPublicKey = (java.security.interfaces.DSAPublicKey) publicKey;
        this.b = dSAPublicKey.getY();
        java.security.interfaces.DSAParams params = dSAPublicKey.getParams();
        if (params != null) {
            a(params);
        }
        this.dataBuffer_.reset();
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) throws InvalidParameterException {
        if (str != null && str.equals("KSEED")) {
            if (obj == null) {
                this.g = null;
                return;
            } else if (obj instanceof byte[]) {
                this.g = (byte[]) obj;
                return;
            } else {
                if (!(obj instanceof BigInteger)) {
                    throw new InvalidParameterException("KSEED must be a either a byte array or a BigInteger");
                }
                this.g = ((BigInteger) obj).toByteArray();
                return;
            }
        }
        if (obj instanceof SecureRandom) {
            this.f = (SecureRandom) obj;
            return;
        }
        if (!(obj instanceof DSAParameterSpec)) {
            throw new InvalidParameterException("Invalid parameter; only KSEED, SecureRandom or DSAParameterSpec are allowed!");
        }
        if (str == null || str.equals("DSAParameterSpec")) {
            try {
                engineSetParameter((DSAParameterSpec) obj);
            } catch (Exception e) {
                throw new InvalidParameterException(e.getMessage());
            }
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid param name for DSAParameterSpec: ");
            stringBuffer.append(str);
            throw new InvalidParameterException(stringBuffer.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof DSAParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Not a DSAParameterSpec");
        }
        a((DSAParameterSpec) algorithmParameterSpec);
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        try {
            return a(dsaSignRS());
        } finally {
            this.dataBuffer_.reset();
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) throws SignatureException {
        this.dataBuffer_.write(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) throws SignatureException {
        this.dataBuffer_.write(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        try {
            return dsaVerifyRS(a(bArr));
        } finally {
            this.dataBuffer_.reset();
        }
    }
}

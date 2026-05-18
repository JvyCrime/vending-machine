package iaik.security.dsa;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs8.PrivateKeyInfo;
import iaik.utils.InternalErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.spec.DSAPrivateKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class DSAPrivateKey extends PrivateKeyInfo implements java.security.interfaces.DSAPrivateKey {
    private transient ASN1 c;
    private BigInteger d;
    private transient DSAParams e;

    public DSAPrivateKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public DSAPrivateKey(InputStream inputStream) throws InvalidKeyException, IOException {
        super(inputStream);
    }

    public DSAPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.d = bigInteger;
        this.e = new DSAParams(bigInteger2, bigInteger3, bigInteger4);
        a();
    }

    public DSAPrivateKey(BigInteger bigInteger, java.security.interfaces.DSAParams dSAParams) {
        this.d = bigInteger;
        if (dSAParams instanceof DSAParams) {
            this.e = (DSAParams) dSAParams;
        } else {
            this.e = new DSAParams(dSAParams);
        }
        a();
    }

    public DSAPrivateKey(java.security.interfaces.DSAPrivateKey dSAPrivateKey) {
        this(dSAPrivateKey.getX(), dSAPrivateKey.getParams());
    }

    public DSAPrivateKey(DSAPrivateKeySpec dSAPrivateKeySpec) {
        this(dSAPrivateKeySpec.getX(), dSAPrivateKeySpec.getP(), dSAPrivateKeySpec.getQ(), dSAPrivateKeySpec.getG());
    }

    public DSAPrivateKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    private void a() {
        try {
            this.private_key_algorithm = (AlgorithmID) AlgorithmID.dsa.clone();
            this.private_key_algorithm.setParameter(this.e.toASN1Object());
            this.c = new ASN1(new INTEGER(this.d));
            createPrivateKeyInfo();
        } catch (CodingException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        try {
            ASN1 asn1 = new ASN1(bArr);
            this.c = asn1;
            this.d = (BigInteger) asn1.toASN1Object().getValue();
            this.e = new DSAParams(this.private_key_algorithm.getParameter());
        } catch (Exception unused) {
            throw new InvalidKeyException("No DSA private key.");
        }
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public byte[] encode() {
        return this.c.toByteArray();
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DSAPrivateKey)) {
            return false;
        }
        DSAPrivateKey dSAPrivateKey = (DSAPrivateKey) obj;
        return this.d.equals(dSAPrivateKey.getX()) && this.e.equals(dSAPrivateKey.getParams());
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo, java.security.Key
    public String getAlgorithm() {
        return "DSA";
    }

    @Override // java.security.interfaces.DSAKey
    public java.security.interfaces.DSAParams getParams() {
        return this.e;
    }

    @Override // java.security.interfaces.DSAPrivateKey
    public BigInteger getX() {
        return this.d;
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public int hashCode() {
        return this.e.hashCode() ^ this.d.hashCode();
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DSA private key");
        if (this.e == null) {
            string = ":\n";
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(" (");
            stringBuffer2.append(this.e.getP().bitLength());
            stringBuffer2.append(" bits):\n");
            string = stringBuffer2.toString();
        }
        stringBuffer.append(string);
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("x: ");
        stringBuffer3.append(this.d.toString(16));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        DSAParams dSAParams = this.e;
        if (dSAParams != null) {
            stringBuffer.append(dSAParams.toString());
        }
        return stringBuffer.toString();
    }
}

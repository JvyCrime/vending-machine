package iaik.security.dsa;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.InternalErrorException;
import iaik.x509.PublicKeyInfo;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.spec.DSAPublicKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class DSAPublicKey extends PublicKeyInfo implements java.security.interfaces.DSAPublicKey {
    private transient ASN1 a;
    private BigInteger c;
    private transient DSAParams d;

    public DSAPublicKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public DSAPublicKey(InputStream inputStream) throws InvalidKeyException, IOException {
        super(inputStream);
    }

    public DSAPublicKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.c = bigInteger;
        this.d = new DSAParams(bigInteger2, bigInteger3, bigInteger4);
        a();
    }

    public DSAPublicKey(BigInteger bigInteger, java.security.interfaces.DSAParams dSAParams) {
        this.c = bigInteger;
        if (dSAParams != null) {
            if (dSAParams instanceof DSAParams) {
                this.d = (DSAParams) dSAParams;
            } else {
                this.d = new DSAParams(dSAParams);
            }
        }
        a();
    }

    public DSAPublicKey(java.security.interfaces.DSAPublicKey dSAPublicKey) {
        this(dSAPublicKey.getY(), dSAPublicKey.getParams());
    }

    public DSAPublicKey(DSAPublicKeySpec dSAPublicKeySpec) {
        this(dSAPublicKeySpec.getY(), dSAPublicKeySpec.getP(), dSAPublicKeySpec.getQ(), dSAPublicKeySpec.getG());
    }

    public DSAPublicKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    private void a() {
        try {
            this.public_key_algorithm = (AlgorithmID) AlgorithmID.dsa.clone();
            if (this.d != null) {
                this.public_key_algorithm.setParameter(this.d.toASN1Object());
            } else {
                this.public_key_algorithm.encodeAbsentParametersAsNull(false);
            }
            this.a = new ASN1(new INTEGER(this.c));
            createPublicKeyInfo();
        } catch (CodingException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override // iaik.x509.PublicKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        try {
            ASN1 asn1 = new ASN1(bArr);
            this.a = asn1;
            this.c = (BigInteger) asn1.toASN1Object().getValue();
            ASN1Object parameter = this.public_key_algorithm.getParameter();
            if (parameter == null || parameter.isA(ASN.NULL)) {
                return;
            }
            this.d = new DSAParams(parameter);
        } catch (Exception unused) {
            throw new InvalidKeyException("No DSA public key.");
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
        if (obj == null || !(obj instanceof DSAPublicKey)) {
            return false;
        }
        DSAPublicKey dSAPublicKey = (DSAPublicKey) obj;
        return this.c.equals(dSAPublicKey.getY()) && this.d.equals(dSAPublicKey.getParams());
    }

    @Override // iaik.x509.PublicKeyInfo, java.security.Key
    public String getAlgorithm() {
        return "DSA";
    }

    @Override // java.security.interfaces.DSAKey
    public java.security.interfaces.DSAParams getParams() {
        return this.d;
    }

    @Override // java.security.interfaces.DSAPublicKey
    public BigInteger getY() {
        return this.c;
    }

    @Override // iaik.x509.PublicKeyInfo
    public int hashCode() {
        return this.d.hashCode() ^ this.c.hashCode();
    }

    @Override // iaik.x509.PublicKeyInfo
    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DSA public key");
        if (this.d == null) {
            string = ":\n";
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(" (");
            stringBuffer2.append(this.d.getP().bitLength());
            stringBuffer2.append(" bits):\n");
            string = stringBuffer2.toString();
        }
        stringBuffer.append(string);
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("y: ");
        stringBuffer3.append(this.c.toString(16));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        DSAParams dSAParams = this.d;
        if (dSAParams != null) {
            stringBuffer.append(dSAParams.toString());
        }
        return stringBuffer.toString();
    }
}

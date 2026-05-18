package iaik.security.elgamal;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.InternalErrorException;
import iaik.x509.PublicKeyInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
public class ElGamalPublicKey extends PublicKeyInfo implements a {
    private static final long serialVersionUID = -2646786905086846081L;
    private transient ASN1 a;
    private BigInteger c;
    private transient ElGamalParameterSpec d;

    public ElGamalPublicKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public ElGamalPublicKey(ElGamalPublicKeySpec elGamalPublicKeySpec) {
        this.c = elGamalPublicKeySpec.getY();
        this.d = new ElGamalParameterSpec(elGamalPublicKeySpec.getP(), elGamalPublicKeySpec.getG());
        a();
    }

    public ElGamalPublicKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public ElGamalPublicKey(BigInteger bigInteger, ElGamalParameterSpec elGamalParameterSpec) {
        this.c = bigInteger;
        this.d = elGamalParameterSpec;
        a();
    }

    public ElGamalPublicKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    private void a() {
        try {
            this.a = new ASN1(new INTEGER(this.c));
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(new INTEGER(this.d.getP()));
            sequence.addComponent(new INTEGER(this.d.getG()));
            if (this.d.getL() > 0) {
                sequence.addComponent(new INTEGER(this.d.getL()));
            }
            this.public_key_algorithm = (AlgorithmID) AlgorithmID.elGamal.clone();
            this.public_key_algorithm.setParameter(sequence);
            createPublicKeyInfo();
        } catch (CodingException e) {
            throw new InternalErrorException(e);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
    }

    @Override // iaik.x509.PublicKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        try {
            ASN1 asn1 = new ASN1(bArr);
            this.a = asn1;
            this.c = (BigInteger) asn1.toASN1Object().getValue();
            ASN1Object parameter = this.public_key_algorithm.getParameter();
            BigInteger bigInteger = (BigInteger) parameter.getComponentAt(0).getValue();
            BigInteger bigInteger2 = (BigInteger) parameter.getComponentAt(1).getValue();
            if (parameter.countComponents() > 2) {
                this.d = new ElGamalParameterSpec(bigInteger, bigInteger2, ((BigInteger) parameter.getComponentAt(2).getValue()).intValue());
            } else {
                this.d = new ElGamalParameterSpec(bigInteger, bigInteger2);
            }
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No ElGamal Public Key: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // iaik.x509.PublicKeyInfo
    public byte[] encode() {
        return this.a.toByteArray();
    }

    @Override // iaik.x509.PublicKeyInfo, java.security.Key
    public String getAlgorithm() {
        return "ElGamal";
    }

    @Override // iaik.x509.PublicKeyInfo
    public byte[] getFingerprint() {
        return this.a.fingerprint();
    }

    @Override // iaik.security.elgamal.a
    public ElGamalParameterSpec getParams() {
        return this.d;
    }

    public BigInteger getY() {
        return this.c;
    }

    @Override // iaik.x509.PublicKeyInfo
    public int hashCode() {
        return this.c.hashCode() ^ this.d.hashCode();
    }

    @Override // iaik.x509.PublicKeyInfo
    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ElGamal public key");
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
        stringBuffer3.append("Y: ");
        stringBuffer3.append(this.c.toString(16));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        if (this.d != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("key parameters:\n");
            stringBuffer4.append(this.d.toString());
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }
}

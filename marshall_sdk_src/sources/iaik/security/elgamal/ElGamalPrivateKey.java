package iaik.security.elgamal;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs8.PrivateKeyInfo;
import iaik.utils.InternalErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
public class ElGamalPrivateKey extends PrivateKeyInfo implements a {
    private static final long serialVersionUID = -7361449127322895958L;
    private transient ASN1 c;
    private BigInteger d;
    private transient ElGamalParameterSpec e;

    public ElGamalPrivateKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public ElGamalPrivateKey(ElGamalPrivateKeySpec elGamalPrivateKeySpec) {
        this.d = elGamalPrivateKeySpec.getX();
        this.e = new ElGamalParameterSpec(elGamalPrivateKeySpec.getP(), elGamalPrivateKeySpec.getG());
        a();
    }

    public ElGamalPrivateKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public ElGamalPrivateKey(BigInteger bigInteger, ElGamalParameterSpec elGamalParameterSpec) {
        this.d = bigInteger;
        this.e = elGamalParameterSpec;
        a();
    }

    public ElGamalPrivateKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    private void a() {
        try {
            this.c = new ASN1(new INTEGER(this.d));
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(new INTEGER(this.e.getP()));
            sequence.addComponent(new INTEGER(this.e.getG()));
            if (this.e.getL() > 0) {
                sequence.addComponent(new INTEGER(this.e.getL()));
            }
            this.private_key_algorithm = (AlgorithmID) AlgorithmID.elGamal.clone();
            this.private_key_algorithm.setParameter(sequence);
            createPrivateKeyInfo();
        } catch (CodingException e) {
            throw new InternalErrorException(e);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        try {
            ASN1 asn1 = new ASN1(bArr);
            this.c = asn1;
            this.d = (BigInteger) asn1.toASN1Object().getValue();
            ASN1Object parameter = this.private_key_algorithm.getParameter();
            BigInteger bigInteger = (BigInteger) parameter.getComponentAt(0).getValue();
            BigInteger bigInteger2 = (BigInteger) parameter.getComponentAt(1).getValue();
            if (parameter.countComponents() > 2) {
                this.e = new ElGamalParameterSpec(bigInteger, bigInteger2, ((BigInteger) parameter.getComponentAt(2).getValue()).intValue());
            } else {
                this.e = new ElGamalParameterSpec(bigInteger, bigInteger2);
            }
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No ElGamal Private Key: ");
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
        return "ElGamal";
    }

    @Override // iaik.security.elgamal.a
    public ElGamalParameterSpec getParams() {
        return this.e;
    }

    public BigInteger getX() {
        return this.d;
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public int hashCode() {
        return this.d.hashCode() ^ this.e.hashCode();
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ElGamal private key");
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
        if (this.e != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("key parameters:\n");
            stringBuffer4.append(this.e.toString());
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }
}

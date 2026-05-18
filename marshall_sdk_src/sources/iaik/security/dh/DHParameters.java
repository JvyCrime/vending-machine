package iaik.security.dh;

import iaik.asn1.ASN1;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class DHParameters extends AlgorithmParametersSpi {
    BigInteger a;
    BigInteger b;
    private int c = -1;

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new INTEGER(this.a));
        sequence.addComponent(new INTEGER(this.b));
        if (this.c > 0) {
            sequence.addComponent(new INTEGER(this.c));
        }
        try {
            return new ASN1(sequence).toByteArray();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Encoding error. ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        DHParameterSpec dHParameterSpec = new DHParameterSpec(this.a, this.b, this.c);
        if (dHParameterSpec.getClass().isAssignableFrom(cls)) {
            return dHParameterSpec;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can not convert to class ");
        stringBuffer.append(cls.getName());
        throw new InvalidParameterSpecException(stringBuffer.toString());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (!(algorithmParameterSpec instanceof DHParameterSpec)) {
            throw new InvalidParameterSpecException("Parameter must be DHParameterSpec.");
        }
        DHParameterSpec dHParameterSpec = (DHParameterSpec) algorithmParameterSpec;
        this.a = dHParameterSpec.getP();
        this.b = dHParameterSpec.getG();
        this.c = dHParameterSpec.getL();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            ASN1 asn1 = new ASN1(bArr);
            this.a = (BigInteger) asn1.getComponentAt(0).getValue();
            this.b = (BigInteger) asn1.getComponentAt(1).getValue();
            if (asn1.countComponents() > 2) {
                this.c = ((BigInteger) asn1.getComponentAt(0).getValue()).intValue();
            }
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Decoding error. ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr, String str) throws IOException {
        engineInit(bArr);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected String engineToString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("p: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("g: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        if (this.c <= 0) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("l: ");
            stringBuffer4.append(this.c);
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }

    public int hashCode() {
        return (this.a.hashCode() ^ this.b.hashCode()) ^ this.c;
    }
}

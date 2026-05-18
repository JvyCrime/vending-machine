package iaik.security.pbe;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.security.spec.PBEKeyAndParameterSpec;
import iaik.utils.Util;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PBEParameters extends AlgorithmParametersSpi {
    private byte[] a;
    private int b = 1;

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new OCTET_STRING(this.a));
        sequence.addComponent(new INTEGER(BigInteger.valueOf(this.b)));
        return DerCoder.encode(sequence);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(this.a, this.b);
        if (pBEParameterSpec.getClass().isAssignableFrom(cls)) {
            return pBEParameterSpec;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can not convert to class ");
        stringBuffer.append(cls.getName());
        throw new InvalidParameterSpecException(stringBuffer.toString());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        int iterationCount;
        if (algorithmParameterSpec instanceof PBEParameterSpec) {
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            this.a = pBEParameterSpec.getSalt();
            iterationCount = pBEParameterSpec.getIterationCount();
        } else {
            if (!(algorithmParameterSpec instanceof PBEKeyAndParameterSpec)) {
                throw new InvalidParameterSpecException("Parameter must be PBEParameterSpec.");
            }
            PBEKeyAndParameterSpec pBEKeyAndParameterSpec = (PBEKeyAndParameterSpec) algorithmParameterSpec;
            this.a = pBEKeyAndParameterSpec.getSalt();
            iterationCount = pBEKeyAndParameterSpec.getIterationCount();
        }
        this.b = iterationCount;
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            ASN1Object aSN1ObjectDecode = DerCoder.decode(bArr);
            this.a = (byte[]) aSN1ObjectDecode.getComponentAt(0).getValue();
            if (aSN1ObjectDecode.countComponents() == 2) {
                this.b = ((BigInteger) aSN1ObjectDecode.getComponentAt(1).getValue()).intValue();
            }
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("DER decoding error. ");
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
        stringBuffer2.append("salt: ");
        stringBuffer2.append(Util.toString(this.a));
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("iteration count: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }
}

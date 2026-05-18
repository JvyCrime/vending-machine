package iaik.security.cipher;

import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class RC2WrapParameters extends RC2Parameters {
    @Override // iaik.security.cipher.RC2Parameters, iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        return DerCoder.encode(new INTEGER(this.d));
    }

    @Override // iaik.security.cipher.RC2Parameters, iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // iaik.security.cipher.RC2Parameters, iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        RC2WrapParameterSpec rC2WrapParameterSpec = new RC2WrapParameterSpec(b(this.d));
        if (rC2WrapParameterSpec.getClass().isAssignableFrom(cls)) {
            return rC2WrapParameterSpec;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can not convert to class ");
        stringBuffer.append(cls.getName());
        throw new InvalidParameterSpecException(stringBuffer.toString());
    }

    @Override // iaik.security.cipher.RC2Parameters, iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (!(algorithmParameterSpec instanceof RC2WrapParameterSpec)) {
            throw new InvalidParameterSpecException("Parameter must be RC2WrapParameterspec.");
        }
        this.d = a(((RC2WrapParameterSpec) algorithmParameterSpec).getEffectiveKeyBits());
    }

    @Override // iaik.security.cipher.RC2Parameters, iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            this.d = ((BigInteger) DerCoder.decode(bArr).getValue()).intValue();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("DER decoding error. ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // iaik.security.cipher.RC2Parameters, iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr, String str) throws IOException {
        engineInit(bArr);
    }

    @Override // iaik.security.cipher.RC2Parameters, iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected String engineToString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("rc2ParameterVersion: ");
        stringBuffer2.append(this.d);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        return stringBuffer.toString();
    }
}

package iaik.security.dsa;

import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class DSAParameters extends AlgorithmParametersSpi {
    DSAParams a;

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        return DerCoder.encode(this.a.toASN1Object());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        AlgorithmParameterSpec dSAParameterSpec;
        if (this.a instanceof SHA2withDSAParams) {
            dSAParameterSpec = new SHA2withDSAParameterSpec(this.a.getP(), this.a.getQ(), this.a.getG());
            SHA2withDSAParameterSpec sHA2withDSAParameterSpec = (SHA2withDSAParameterSpec) dSAParameterSpec;
            sHA2withDSAParameterSpec.a(((SHA2withDSAParams) this.a).getCounter());
            sHA2withDSAParameterSpec.a(((SHA2withDSAParams) this.a).getSeed());
        } else {
            dSAParameterSpec = new DSAParameterSpec(this.a.getP(), this.a.getQ(), this.a.getG());
        }
        if (cls.isInstance(dSAParameterSpec)) {
            return dSAParameterSpec;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can not convert to class ");
        stringBuffer.append(cls.getName());
        throw new InvalidParameterSpecException(stringBuffer.toString());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (!(algorithmParameterSpec instanceof DSAParameterSpec)) {
            throw new InvalidParameterSpecException("Parameter must be DSAParameterSpec.");
        }
        if (!(algorithmParameterSpec instanceof SHA2withDSAParameterSpec)) {
            DSAParameterSpec dSAParameterSpec = (DSAParameterSpec) algorithmParameterSpec;
            this.a = new DSAParams(dSAParameterSpec.getP(), dSAParameterSpec.getQ(), dSAParameterSpec.getG());
            return;
        }
        SHA2withDSAParameterSpec sHA2withDSAParameterSpec = (SHA2withDSAParameterSpec) algorithmParameterSpec;
        SHA2withDSAParams sHA2withDSAParams = new SHA2withDSAParams(sHA2withDSAParameterSpec.getP(), sHA2withDSAParameterSpec.getQ(), sHA2withDSAParameterSpec.getG());
        this.a = sHA2withDSAParams;
        sHA2withDSAParams.a(sHA2withDSAParameterSpec.getCounter());
        ((SHA2withDSAParams) this.a).a(sHA2withDSAParameterSpec.getSeed());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            this.a = new DSAParams(DerCoder.decode(bArr));
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
        return this.a.toString();
    }
}

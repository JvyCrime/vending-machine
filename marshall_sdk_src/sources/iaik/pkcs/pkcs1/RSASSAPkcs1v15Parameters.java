package iaik.pkcs.pkcs1;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.structures.AlgorithmID;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class RSASSAPkcs1v15Parameters extends PKCS1AlgorithmParameters {
    private AlgorithmID a;

    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = new AlgorithmID(aSN1Object);
    }

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
        RSASSAPkcs1v15ParameterSpec rSASSAPkcs1v15ParameterSpec = new RSASSAPkcs1v15ParameterSpec((AlgorithmID) this.a.clone());
        if (!rSASSAPkcs1v15ParameterSpec.getClass().isAssignableFrom(cls)) {
            try {
                if (!cls.isAssignableFrom(Class.forName("iaik.pkcs.pkcs1.PKCS1AlgorithmParameterSpec"))) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Can not convert to class ");
                    stringBuffer.append(cls.getName());
                    throw new InvalidParameterSpecException(stringBuffer.toString());
                }
            } catch (ClassNotFoundException unused) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Can not convert to class ");
                stringBuffer2.append(cls.getName());
                throw new InvalidParameterSpecException(stringBuffer2.toString());
            }
        }
        return rSASSAPkcs1v15ParameterSpec;
    }

    @Override // iaik.pkcs.pkcs1.PKCS1AlgorithmParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        super.engineInit(algorithmParameterSpec);
        if (!(algorithmParameterSpec instanceof RSASSAPkcs1v15ParameterSpec)) {
            throw new InvalidParameterSpecException("Parameter must be a RSASSAPkcs1v15ParameterSpec.");
        }
        this.a = ((RSASSAPkcs1v15ParameterSpec) algorithmParameterSpec).getHashAlgorithm();
    }

    @Override // iaik.pkcs.pkcs1.PKCS1AlgorithmParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        super.engineInit(bArr);
        try {
            decode(DerCoder.decode(bArr));
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

    @Override // iaik.pkcs.pkcs1.PKCS1AlgorithmParameters
    public ASN1Object toASN1Object() {
        ASN1Object aSN1Object = super.toASN1Object();
        return aSN1Object == null ? this.a.toASN1Object() : aSN1Object;
    }
}

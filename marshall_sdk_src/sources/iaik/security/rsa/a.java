package iaik.security.rsa;

import iaik.pkcs.pkcs1.PKCS1AlgorithmParameterSpec;
import iaik.pkcs.pkcs1.RSAPssParameterSpec;
import iaik.pkcs.pkcs1.RSAPssSaltParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
abstract class a extends RSAPssSignature {
    protected a(String str) {
        super(str);
    }

    @Override // iaik.security.rsa.RSAPssSignature, iaik.security.rsa.b, java.security.SignatureSpi
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        SecureRandom secureRandom;
        if (algorithmParameterSpec != null) {
            if (algorithmParameterSpec instanceof RSAPssSaltParameterSpec) {
                RSAPssSaltParameterSpec rSAPssSaltParameterSpec = (RSAPssSaltParameterSpec) algorithmParameterSpec;
                this.d = rSAPssSaltParameterSpec.getSaltLength();
                if (this.d < 0) {
                    throw new InvalidAlgorithmParameterException("Cannot set saltLength parameter; must not be negative.");
                }
                this.e = rSAPssSaltParameterSpec.getSalt();
                secureRandom = rSAPssSaltParameterSpec.getSecureRandom();
                if (algorithmParameterSpec instanceof RSAPssParameterSpec) {
                    RSAPssParameterSpec rSAPssParameterSpec = (RSAPssParameterSpec) algorithmParameterSpec;
                    if (!this.c.equals(rSAPssParameterSpec.getHashAlgorithm())) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Invalid hash algorithm parameter. Only ");
                        stringBuffer.append(this.c.getName());
                        stringBuffer.append(" allowed!");
                        throw new InvalidAlgorithmParameterException(stringBuffer.toString());
                    }
                    if (!this.a.equals(rSAPssParameterSpec.getMaskGenAlgorithm(), true)) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Invalid mgf parameter. Only ");
                        stringBuffer2.append(this.a.getName());
                        stringBuffer2.append(" allowed!");
                        throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
                    }
                    int trailerField = rSAPssParameterSpec.getTrailerField();
                    if (trailerField != 1) {
                        StringBuffer stringBuffer3 = new StringBuffer();
                        stringBuffer3.append("Trailer field number ");
                        stringBuffer3.append(trailerField);
                        stringBuffer3.append(" not supported by RSASSA-PSS. Expected ");
                        stringBuffer3.append(1);
                        stringBuffer3.append("!");
                        throw new InvalidAlgorithmParameterException(stringBuffer3.toString());
                    }
                }
            } else {
                if (!(algorithmParameterSpec instanceof PKCS1AlgorithmParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("Params must be a RSAPssSaltParameterSpec, RSAPssParameterSpec or PKCS1AlgorithmParameterSpec!");
                }
                secureRandom = ((PKCS1AlgorithmParameterSpec) algorithmParameterSpec).getSecureRandom();
            }
            if (secureRandom != null) {
                a(secureRandom);
            }
        }
    }
}

package iaik.security.cipher;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.OCTET_STRING;
import iaik.utils.Util;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class IvParameters extends AlgorithmParametersSpi {
    byte[] a;

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        return DerCoder.encode(new OCTET_STRING(this.a));
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        if (this.a == null) {
            throw new InvalidParameterSpecException("IV is null!");
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.a);
        if (ivParameterSpec.getClass().isAssignableFrom(cls)) {
            return ivParameterSpec;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can not convert to class ");
        stringBuffer.append(cls.getName());
        throw new InvalidParameterSpecException(stringBuffer.toString());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        try {
            byte[] bArrA = t.a(1, algorithmParameterSpec, (SecureRandom) null, -1);
            this.a = bArrA;
            if (bArrA != null) {
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unsupported type of AlgorithmParameterSpec: ");
            stringBuffer.append(algorithmParameterSpec.getClass().getName());
            throw new InvalidParameterSpecException(stringBuffer.toString());
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidParameterSpecException(e.toString());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            ASN1Object aSN1ObjectDecode = DerCoder.decode(bArr);
            if (!aSN1ObjectDecode.isA(ASN.OCTET_STRING)) {
                throw new IOException("IV has to be encoded as ASN.1 OCTET STRING.");
            }
            this.a = (byte[]) aSN1ObjectDecode.getValue();
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
        stringBuffer2.append("IV: ");
        stringBuffer2.append(Util.toString(this.a));
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        return stringBuffer.toString();
    }
}

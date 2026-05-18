package iaik.pkcs.pkcs5;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class PBKDF2Parameters extends AlgorithmParametersSpi {
    static Class a;
    private PBKDF2ParameterSpec b;

    private static ASN1Object a(PBKDF2ParameterSpec pBKDF2ParameterSpec) {
        return a(pBKDF2ParameterSpec.getSalt(), pBKDF2ParameterSpec.getIterationCount(), pBKDF2ParameterSpec.getDerivedKeyLength(), pBKDF2ParameterSpec.getPrf());
    }

    static ASN1Object a(byte[] bArr, int i, int i2, AlgorithmID algorithmID) {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new OCTET_STRING(bArr));
        sequence.addComponent(new INTEGER(i));
        if (i2 > 0) {
            sequence.addComponent(new INTEGER(i2));
        }
        if (algorithmID != null && !algorithmID.equals(PBKDF2.a)) {
            sequence.addComponent(algorithmID.toASN1Object());
        }
        return sequence;
    }

    static PBKDF2ParameterSpec a(ASN1Object aSN1Object) throws CodingException {
        if (aSN1Object == null) {
            throw new CodingException("PBKDF2 algorithm parameters must not be null!");
        }
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new CodingException("PBKDF2 parameters must be ASN.1 SEQUENCE!");
        }
        int iCountComponents = aSN1Object.countComponents();
        int i = 2;
        if (iCountComponents < 2 || iCountComponents > 4) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid number of PBKDF2 parameter components (");
            stringBuffer.append(iCountComponents);
            stringBuffer.append("). Must be 2, 3 or 4!");
            throw new CodingException(stringBuffer.toString());
        }
        int iIntValue = 32;
        AlgorithmID algorithmID = null;
        ASN1Object componentAt = aSN1Object.getComponentAt(0);
        if (!componentAt.isA(ASN.OCTET_STRING)) {
            throw new CodingException("Invalid PBKDF2 param salt component. Only OCTET STRING supported!");
        }
        byte[] bArr = (byte[]) componentAt.getValue();
        ASN1Object componentAt2 = aSN1Object.getComponentAt(1);
        if (!componentAt2.isA(ASN.INTEGER)) {
            throw new CodingException("Invalid PBKDF2 param iteration count component. Must be INTEGER!");
        }
        int iIntValue2 = ((BigInteger) componentAt2.getValue()).intValue();
        while (i < iCountComponents) {
            int i2 = i + 1;
            ASN1Object componentAt3 = aSN1Object.getComponentAt(i);
            if (componentAt3.isA(ASN.INTEGER)) {
                iIntValue = ((BigInteger) componentAt3.getValue()).intValue();
            } else if (componentAt3.isA(ASN.SEQUENCE)) {
                algorithmID = new AlgorithmID(componentAt3);
            }
            i = i2;
        }
        PBKDF2ParameterSpec pBKDF2ParameterSpec = new PBKDF2ParameterSpec(bArr, iIntValue2, iIntValue);
        if (algorithmID != null) {
            pBKDF2ParameterSpec.setPrf(algorithmID);
        }
        return pBKDF2ParameterSpec;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        PBKDF2ParameterSpec pBKDF2ParameterSpec = this.b;
        if (pBKDF2ParameterSpec != null) {
            return DerCoder.encode(a(pBKDF2ParameterSpec));
        }
        throw new IllegalStateException("PBKDF2Parameters not initialized yet!");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        if (this.b == null) {
            throw new IllegalStateException("PBKDF2Parameters not initialized yet!");
        }
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.pkcs.pkcs5.PBKDF2ParameterSpec");
            a = clsClass$;
        }
        if (clsClass$.isAssignableFrom(cls)) {
            return this.b;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can not convert to class ");
        stringBuffer.append(cls.getName());
        throw new InvalidParameterSpecException(stringBuffer.toString());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (!(algorithmParameterSpec instanceof PBKDF2ParameterSpec)) {
            throw new InvalidParameterSpecException("paramSpec must be PBKDF2ParameterSpec.");
        }
        this.b = (PBKDF2ParameterSpec) algorithmParameterSpec;
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            this.b = a(DerCoder.decode(bArr));
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error decoding PBKDF2 parameters: ");
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
        PBKDF2ParameterSpec pBKDF2ParameterSpec = this.b;
        return pBKDF2ParameterSpec == null ? "" : pBKDF2ParameterSpec.toString();
    }
}

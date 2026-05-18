package iaik.pkcs.pkcs5;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class PBES2Parameters extends AlgorithmParametersSpi {
    static Class a;
    private PBES2ParameterSpec b;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        if (this.b == null) {
            throw new IllegalStateException("PBES2Parameters not initialized yet!");
        }
        SEQUENCE sequence = new SEQUENCE();
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.pbkdf2.clone();
        algorithmID.setParameter(PBKDF2Parameters.a(this.b.getSalt(), this.b.getIterationCount(), this.b.getDerivedKeyLength(), this.b.getPrf()));
        sequence.addComponent(algorithmID.toASN1Object());
        sequence.addComponent(this.b.getEncryptionScheme().toASN1Object());
        return DerCoder.encode(sequence);
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
            clsClass$ = class$("iaik.pkcs.pkcs5.PBES2ParameterSpec");
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
        if (!(algorithmParameterSpec instanceof PBES2ParameterSpec)) {
            throw new InvalidParameterSpecException("paramSpec must be PBKDF2ParameterSpec.");
        }
        this.b = (PBES2ParameterSpec) algorithmParameterSpec;
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            ASN1Object aSN1ObjectDecode = DerCoder.decode(bArr);
            if (!aSN1ObjectDecode.isA(ASN.SEQUENCE)) {
                throw new CodingException("PBKDF2 parameters must be ASN.1 SEQUENCE!");
            }
            int iCountComponents = aSN1ObjectDecode.countComponents();
            if (iCountComponents != 2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid number of PBES2 parameter components (");
                stringBuffer.append(iCountComponents);
                stringBuffer.append("). Must be 2!");
                throw new CodingException(stringBuffer.toString());
            }
            AlgorithmID algorithmID = new AlgorithmID(aSN1ObjectDecode.getComponentAt(0));
            if (!AlgorithmID.pbkdf2.equals(algorithmID)) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Unsupported KDF (");
                stringBuffer2.append(algorithmID.getAlgorithm());
                stringBuffer2.append("). Only PBKDF2 supported!");
                throw new IOException(stringBuffer2.toString());
            }
            PBKDF2ParameterSpec pBKDF2ParameterSpecA = PBKDF2Parameters.a(algorithmID.getParameter());
            PBES2ParameterSpec pBES2ParameterSpec = new PBES2ParameterSpec(pBKDF2ParameterSpecA.getSalt(), pBKDF2ParameterSpecA.getIterationCount(), pBKDF2ParameterSpecA.getDerivedKeyLength(), new AlgorithmID(aSN1ObjectDecode.getComponentAt(1)));
            this.b = pBES2ParameterSpec;
            pBES2ParameterSpec.setPrf(pBKDF2ParameterSpecA.getPrf());
        } catch (CodingException e) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Error decoding PBES2 parameters: ");
            stringBuffer3.append(e.toString());
            throw new IOException(stringBuffer3.toString());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr, String str) throws IOException {
        engineInit(bArr);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected String engineToString() {
        PBES2ParameterSpec pBES2ParameterSpec = this.b;
        return pBES2ParameterSpec == null ? "" : pBES2ParameterSpec.toString();
    }
}

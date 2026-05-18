package iaik.pkcs.pkcs1;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RSAPssParameters extends PKCS1AlgorithmParameters {
    private static boolean a;
    private RSAPssParameterSpec b;

    public static boolean getEncodeDefaultValues() {
        return a;
    }

    public static void setEncodeDefaultValues(boolean z) {
        a = z;
    }

    public void decode(ASN1Object aSN1Object) throws CodingException {
        AlgorithmID algorithmID = null;
        this.b = null;
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new CodingException("Invalid PSS parameter encoding. Expected ASN.1 SEQUENCE!");
        }
        AlgorithmID algorithmID2 = null;
        int iIntValue = RSAPssParameterSpec.DEFAULT_TRAILER_FIELD;
        int iIntValue2 = 20;
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
            int tag = con_spec.getAsnType().getTag();
            if (tag == 0) {
                algorithmID = new AlgorithmID((ASN1Object) con_spec.getValue());
            } else if (tag == 1) {
                algorithmID2 = new AlgorithmID((ASN1Object) con_spec.getValue());
            } else if (tag == 2) {
                iIntValue2 = ((BigInteger) ((INTEGER) con_spec.getValue()).getValue()).intValue();
            } else {
                if (tag != 3) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid tagged (");
                    stringBuffer.append(tag);
                    stringBuffer.append(") in PSS parameter component number ");
                    stringBuffer.append(i);
                    stringBuffer.append(".");
                    throw new CodingException(stringBuffer.toString());
                }
                iIntValue = ((BigInteger) ((INTEGER) con_spec.getValue()).getValue()).intValue();
            }
        }
        if (algorithmID == null) {
            algorithmID = RSAPssParameterSpec.DEFAULT_HASH_ALGORITHM;
        }
        if (algorithmID2 == null) {
            algorithmID2 = RSAPssParameterSpec.DEFAULT_MASK_GEN_ALGORITHM;
        }
        RSAPssParameterSpec rSAPssParameterSpec = new RSAPssParameterSpec(algorithmID, algorithmID2, iIntValue2);
        this.b = rSAPssParameterSpec;
        rSAPssParameterSpec.setTrailerField(iIntValue);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        return DerCoder.encode(toASN1Object());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        if (!this.b.getClass().isAssignableFrom(cls)) {
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
        return (RSAPssParameterSpec) this.b.clone();
    }

    @Override // iaik.pkcs.pkcs1.PKCS1AlgorithmParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        super.engineInit(algorithmParameterSpec);
        this.b = null;
        if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
            throw new InvalidParameterSpecException("Parameter must be a RSAPssParameterSpec.");
        }
        this.b = (RSAPssParameterSpec) ((RSAPssParameterSpec) algorithmParameterSpec).clone();
    }

    @Override // iaik.pkcs.pkcs1.PKCS1AlgorithmParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        super.engineInit(bArr);
        try {
            decode(DerCoder.decode(bArr));
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Parsing error: ");
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
        RSAPssParameterSpec rSAPssParameterSpec = this.b;
        return rSAPssParameterSpec == null ? "" : rSAPssParameterSpec.toString();
    }

    @Override // iaik.pkcs.pkcs1.PKCS1AlgorithmParameters
    public ASN1Object toASN1Object() {
        ASN1Object aSN1Object = super.toASN1Object();
        if (aSN1Object != null) {
            return aSN1Object;
        }
        RSAPssParameterSpec rSAPssParameterSpec = this.b;
        Objects.requireNonNull(rSAPssParameterSpec, "Cannot encode null params!");
        AlgorithmID hashAlgorithm = rSAPssParameterSpec.getHashAlgorithm();
        AlgorithmID maskGenAlgorithm = this.b.getMaskGenAlgorithm();
        int saltLength = this.b.getSaltLength();
        int trailerField = this.b.getTrailerField();
        Boolean encodeDefaultValues = this.b.getEncodeDefaultValues();
        boolean zBooleanValue = encodeDefaultValues != null ? encodeDefaultValues.booleanValue() : a;
        SEQUENCE sequence = new SEQUENCE();
        if (hashAlgorithm != null && (zBooleanValue || !hashAlgorithm.equals(RSAPssParameterSpec.DEFAULT_HASH_ALGORITHM))) {
            sequence.addComponent(new CON_SPEC(0, hashAlgorithm.toASN1Object()));
        }
        if (maskGenAlgorithm != null) {
            ASN1Object componentAt = null;
            ASN1Object parameter = maskGenAlgorithm.getParameter();
            if (parameter != null && parameter.isA(ASN.SEQUENCE)) {
                try {
                    componentAt = parameter.getComponentAt(0);
                } catch (CodingException unused) {
                }
            }
            if (zBooleanValue || !maskGenAlgorithm.equals(RSAPssParameterSpec.DEFAULT_MASK_GEN_ALGORITHM) || !RSAPssParameterSpec.DEFAULT_HASH_ALGORITHM.getAlgorithm().equals(componentAt)) {
                sequence.addComponent(new CON_SPEC(1, maskGenAlgorithm.toASN1Object()));
            }
        }
        if (saltLength != -1 && (zBooleanValue || saltLength != 20)) {
            sequence.addComponent(new CON_SPEC(2, new INTEGER(saltLength)));
        }
        if (trailerField != -1 && (zBooleanValue || trailerField != RSAPssParameterSpec.DEFAULT_TRAILER_FIELD)) {
            sequence.addComponent(new CON_SPEC(3, new INTEGER(trailerField)));
        }
        return sequence;
    }
}

package iaik.pkcs.pkcs1;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RSAOaepParameters extends PKCS1AlgorithmParameters {
    private static boolean a;
    private RSAOaepParameterSpec b;

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
            throw new CodingException("Invalid OAEP parameter encoding. Expected ASN.1 SEQUENCE!");
        }
        AlgorithmID algorithmID2 = null;
        AlgorithmID algorithmID3 = null;
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
            int tag = con_spec.getAsnType().getTag();
            if (tag == 0) {
                algorithmID = new AlgorithmID((ASN1Object) con_spec.getValue());
            } else if (tag == 1) {
                algorithmID2 = new AlgorithmID((ASN1Object) con_spec.getValue());
            } else {
                if (tag != 2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid tagged (");
                    stringBuffer.append(tag);
                    stringBuffer.append(") in OAEP parameter component number ");
                    stringBuffer.append(i);
                    stringBuffer.append(".");
                    throw new CodingException(stringBuffer.toString());
                }
                algorithmID3 = new AlgorithmID((ASN1Object) con_spec.getValue());
            }
        }
        if (algorithmID == null) {
            algorithmID = RSAOaepParameterSpec.DEFAULT_HASH_ALGORITHM;
        }
        if (algorithmID2 == null) {
            algorithmID2 = RSAOaepParameterSpec.DEFAULT_MASK_GEN_ALGORITHM;
        }
        if (algorithmID3 == null) {
            algorithmID3 = RSAOaepPSourceParameterSpec.DEFAULT_PSOURCE_ALGORITHM;
        }
        try {
            this.b = new RSAOaepParameterSpec(algorithmID, algorithmID2, algorithmID3);
        } catch (Exception e) {
            throw new CodingException(e.getMessage());
        }
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
        RSAOaepParameterSpec rSAOaepParameterSpec = this.b;
        if (rSAOaepParameterSpec == null) {
            return null;
        }
        if (!rSAOaepParameterSpec.getClass().isAssignableFrom(cls)) {
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
        return (RSAOaepParameterSpec) this.b.clone();
    }

    @Override // iaik.pkcs.pkcs1.PKCS1AlgorithmParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        super.engineInit(algorithmParameterSpec);
        this.b = null;
        if (!(algorithmParameterSpec instanceof RSAOaepParameterSpec)) {
            throw new InvalidParameterSpecException("Parameter must be a RSAOaepParameterSpec.");
        }
        this.b = (RSAOaepParameterSpec) ((RSAOaepParameterSpec) algorithmParameterSpec).clone();
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
        RSAOaepParameterSpec rSAOaepParameterSpec = this.b;
        return rSAOaepParameterSpec == null ? "" : rSAOaepParameterSpec.toString();
    }

    @Override // iaik.pkcs.pkcs1.PKCS1AlgorithmParameters
    public ASN1Object toASN1Object() {
        ASN1Object aSN1Object = super.toASN1Object();
        if (aSN1Object != null) {
            return aSN1Object;
        }
        RSAOaepParameterSpec rSAOaepParameterSpec = this.b;
        Objects.requireNonNull(rSAOaepParameterSpec, "Cannot encode null params!");
        AlgorithmID hashAlgorithm = rSAOaepParameterSpec.getHashAlgorithm();
        AlgorithmID maskGenAlgorithm = this.b.getMaskGenAlgorithm();
        AlgorithmID pSourceAlgorithm = this.b.getPSourceAlgorithm();
        Boolean encodeDefaultValues = this.b.getEncodeDefaultValues();
        boolean zBooleanValue = encodeDefaultValues != null ? encodeDefaultValues.booleanValue() : a;
        SEQUENCE sequence = new SEQUENCE();
        if (hashAlgorithm != null && (zBooleanValue || !hashAlgorithm.equals(RSAOaepParameterSpec.DEFAULT_HASH_ALGORITHM))) {
            sequence.addComponent(new CON_SPEC(0, hashAlgorithm.toASN1Object()));
        }
        boolean z = true;
        if (maskGenAlgorithm != null) {
            ASN1Object componentAt = null;
            ASN1Object parameter = maskGenAlgorithm.getParameter();
            if (parameter != null && parameter.isA(ASN.SEQUENCE)) {
                try {
                    componentAt = parameter.getComponentAt(0);
                } catch (CodingException unused) {
                }
            }
            if (zBooleanValue || !maskGenAlgorithm.equals(RSAOaepParameterSpec.DEFAULT_MASK_GEN_ALGORITHM) || !RSAOaepParameterSpec.DEFAULT_HASH_ALGORITHM.getAlgorithm().equals(componentAt)) {
                sequence.addComponent(new CON_SPEC(1, maskGenAlgorithm.toASN1Object()));
            }
        }
        if (pSourceAlgorithm != null && (zBooleanValue || !pSourceAlgorithm.equals(RSAOaepPSourceParameterSpec.DEFAULT_PSOURCE_ALGORITHM) || !RSAOaepPSourceParameterSpec.DEFAULT_PSOURCE_ALGORITHM.getParameter().equals(pSourceAlgorithm.getParameter()))) {
            sequence.addComponent(new CON_SPEC(2, pSourceAlgorithm.toASN1Object()));
        }
        if (pSourceAlgorithm != null && !zBooleanValue) {
            if (pSourceAlgorithm.equals(RSAOaepPSourceParameterSpec.DEFAULT_PSOURCE_ALGORITHM)) {
                ASN1Object parameter2 = pSourceAlgorithm.getParameter();
                if (parameter2 != null && parameter2.isA(ASN.OCTET_STRING) && ((byte[]) parameter2.getValue()).length > 0) {
                    zBooleanValue = true;
                }
                z = zBooleanValue;
            }
            if (z) {
                sequence.addComponent(new CON_SPEC(2, pSourceAlgorithm.toASN1Object()));
            }
        }
        return sequence;
    }
}

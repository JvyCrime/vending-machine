package iaik.security.rsa;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.RSAOaepParameterSpec;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RSAOaepPublicKey extends RSAPublicKey {
    static Class a;
    private transient RSAOaepParameterSpec c;

    RSAOaepPublicKey() {
    }

    public RSAOaepPublicKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public RSAOaepPublicKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public RSAOaepPublicKey(BigInteger bigInteger, BigInteger bigInteger2) {
        a(bigInteger, bigInteger2);
        try {
            a();
        } catch (InvalidParameterSpecException unused) {
        }
    }

    public RSAOaepPublicKey(BigInteger bigInteger, BigInteger bigInteger2, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        a(bigInteger, bigInteger2);
        if (algorithmParameterSpec != null) {
            if (!(algorithmParameterSpec instanceof RSAOaepParameterSpec)) {
                throw new InvalidParameterSpecException("Parameters must be RSAOaepParameterSpec!");
            }
            this.c = (RSAOaepParameterSpec) algorithmParameterSpec;
        }
        a();
    }

    public RSAOaepPublicKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    private void a() throws InvalidParameterSpecException {
        super.a(false);
        this.public_key_algorithm = (AlgorithmID) AlgorithmID.rsaesOAEP.clone();
        if (this.c != null) {
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("RSAES-OAEP", "IAIK");
                algorithmParameters.init(this.c);
                this.public_key_algorithm.setAlgorithmParameters(algorithmParameters);
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid OAEP parameters: ");
                stringBuffer.append(e.toString());
                throw new InvalidParameterSpecException(stringBuffer.toString());
            }
        }
        createPublicKeyInfo();
    }

    static boolean a(RSAOaepParameterSpec rSAOaepParameterSpec, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        Objects.requireNonNull(algorithmParameterSpec, "Cannot validate null parameters!");
        if (!(algorithmParameterSpec instanceof RSAOaepParameterSpec)) {
            throw new InvalidParameterSpecException("ParamSpec must be RSAOaepParameterSpec!");
        }
        RSAOaepParameterSpec rSAOaepParameterSpec2 = (RSAOaepParameterSpec) algorithmParameterSpec;
        if (rSAOaepParameterSpec == null) {
            return true;
        }
        return rSAOaepParameterSpec.getHashAlgorithm().equals(rSAOaepParameterSpec2.getHashAlgorithm()) && rSAOaepParameterSpec.getMaskGenAlgorithm().equals(rSAOaepParameterSpec2.getMaskGenAlgorithm());
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.security.rsa.RSAPublicKey, iaik.x509.PublicKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        super.decode(bArr);
        if (!this.public_key_algorithm.equals(AlgorithmID.rsaesOAEP)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid algorithm id (");
            stringBuffer.append(this.public_key_algorithm.getAlgorithm().getID());
            stringBuffer.append("). Not an OAEP key!");
            throw new InvalidKeyException(stringBuffer.toString());
        }
        ASN1Object parameter = this.public_key_algorithm.getParameter();
        if (parameter == null || parameter.isA(ASN.NULL)) {
            return;
        }
        try {
            AlgorithmParameters algorithmParameters = this.public_key_algorithm.getAlgorithmParameters("RSAES-OAEP", "IAIK");
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.pkcs.pkcs1.RSAOaepParameterSpec");
                a = clsClass$;
            }
            this.c = (RSAOaepParameterSpec) algorithmParameters.getParameterSpec(clsClass$);
        } catch (Exception e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Error parsing OAEP parameters: ");
            stringBuffer2.append(e.toString());
            throw new InvalidKeyException(stringBuffer2.toString());
        }
    }

    @Override // iaik.security.rsa.RSAPublicKey, iaik.x509.PublicKeyInfo
    public boolean equals(Object obj) {
        RSAOaepParameterSpec rSAOaepParameterSpec;
        if (this == obj) {
            return true;
        }
        if (obj instanceof RSAOaepPublicKey) {
            boolean zEquals = super.equals(obj);
            if (!zEquals) {
                return zEquals;
            }
            RSAOaepPublicKey rSAOaepPublicKey = (RSAOaepPublicKey) obj;
            RSAOaepParameterSpec rSAOaepParameterSpec2 = this.c;
            if (rSAOaepParameterSpec2 != null && (rSAOaepParameterSpec = rSAOaepPublicKey.c) != null) {
                return rSAOaepParameterSpec2.equals(rSAOaepParameterSpec);
            }
            if (rSAOaepParameterSpec2 == null && rSAOaepPublicKey.c == null) {
                return true;
            }
        }
        return false;
    }

    @Override // iaik.security.rsa.RSAPublicKey, iaik.x509.PublicKeyInfo, java.security.Key
    public String getAlgorithm() {
        return "RSAES-OAEP";
    }

    public AlgorithmParameterSpec getParams() {
        return this.c;
    }

    @Override // iaik.security.rsa.RSAPublicKey, iaik.x509.PublicKeyInfo
    public int hashCode() {
        int iHashCode = super.hashCode();
        RSAOaepParameterSpec rSAOaepParameterSpec = this.c;
        return rSAOaepParameterSpec != null ? iHashCode ^ rSAOaepParameterSpec.hashCode() : iHashCode;
    }

    @Override // iaik.security.rsa.RSAPublicKey, iaik.x509.PublicKeyInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        if (this.c != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("parameters:\n");
            stringBuffer2.append(this.c);
            stringBuffer.append(stringBuffer2.toString());
        }
        return stringBuffer.toString();
    }

    public boolean validateParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        return a(this.c, algorithmParameterSpec);
    }
}

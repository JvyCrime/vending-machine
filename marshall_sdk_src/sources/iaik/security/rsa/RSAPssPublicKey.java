package iaik.security.rsa;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.RSAPssParameterSpec;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RSAPssPublicKey extends RSAPublicKey {
    static Class a;
    private transient RSAPssParameterSpec c;

    RSAPssPublicKey() {
    }

    public RSAPssPublicKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public RSAPssPublicKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public RSAPssPublicKey(BigInteger bigInteger, BigInteger bigInteger2) {
        a(bigInteger, bigInteger2);
        try {
            a();
        } catch (InvalidParameterSpecException unused) {
        }
    }

    public RSAPssPublicKey(BigInteger bigInteger, BigInteger bigInteger2, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        a(bigInteger, bigInteger2);
        if (algorithmParameterSpec != null) {
            if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
                throw new InvalidParameterSpecException("Parameters must be RSAPssParameterSpec!");
            }
            this.c = (RSAPssParameterSpec) algorithmParameterSpec;
        }
        a();
    }

    public RSAPssPublicKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    private void a() throws InvalidParameterSpecException {
        super.a(false);
        this.public_key_algorithm = (AlgorithmID) AlgorithmID.rsassaPss.clone();
        if (this.c != null) {
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("RSASSA-PSS", "IAIK");
                algorithmParameters.init(this.c);
                this.public_key_algorithm.setAlgorithmParameters(algorithmParameters);
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid PSS parameters: ");
                stringBuffer.append(e.toString());
                throw new InvalidParameterSpecException(stringBuffer.toString());
            }
        }
        createPublicKeyInfo();
    }

    static boolean a(RSAPssParameterSpec rSAPssParameterSpec, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        Objects.requireNonNull(algorithmParameterSpec, "Cannot validate null parameters!");
        if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
            throw new InvalidParameterSpecException("ParamSpec must be RSAPssParameterSpec!");
        }
        RSAPssParameterSpec rSAPssParameterSpec2 = (RSAPssParameterSpec) algorithmParameterSpec;
        if (rSAPssParameterSpec == null) {
            return true;
        }
        return rSAPssParameterSpec.getHashAlgorithm().equals(rSAPssParameterSpec2.getHashAlgorithm()) && rSAPssParameterSpec.getMaskGenAlgorithm().equals(rSAPssParameterSpec2.getMaskGenAlgorithm()) && rSAPssParameterSpec.getTrailerField() == rSAPssParameterSpec2.getTrailerField();
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
        if (!this.public_key_algorithm.equals(AlgorithmID.rsassaPss)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid algorithm id (");
            stringBuffer.append(this.public_key_algorithm.getAlgorithm().getID());
            stringBuffer.append("). Not a PSS key!");
            throw new InvalidKeyException(stringBuffer.toString());
        }
        ASN1Object parameter = this.public_key_algorithm.getParameter();
        if (parameter == null || parameter.isA(ASN.NULL)) {
            return;
        }
        try {
            AlgorithmParameters algorithmParameters = this.public_key_algorithm.getAlgorithmParameters("RSASSA-PSS", "IAIK");
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.pkcs.pkcs1.RSAPssParameterSpec");
                a = clsClass$;
            }
            this.c = (RSAPssParameterSpec) algorithmParameters.getParameterSpec(clsClass$);
        } catch (Exception e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Error parsing PSS parameters: ");
            stringBuffer2.append(e.toString());
            throw new InvalidKeyException(stringBuffer2.toString());
        }
    }

    @Override // iaik.security.rsa.RSAPublicKey, iaik.x509.PublicKeyInfo
    public boolean equals(Object obj) {
        RSAPssParameterSpec rSAPssParameterSpec;
        if (this == obj) {
            return true;
        }
        if (obj instanceof RSAPssPublicKey) {
            boolean zEquals = super.equals(obj);
            if (!zEquals) {
                return zEquals;
            }
            RSAPssPublicKey rSAPssPublicKey = (RSAPssPublicKey) obj;
            RSAPssParameterSpec rSAPssParameterSpec2 = this.c;
            if (rSAPssParameterSpec2 != null && (rSAPssParameterSpec = rSAPssPublicKey.c) != null) {
                return rSAPssParameterSpec2.equals(rSAPssParameterSpec);
            }
            if (rSAPssParameterSpec2 == null && rSAPssPublicKey.c == null) {
                return true;
            }
        }
        return false;
    }

    @Override // iaik.security.rsa.RSAPublicKey, iaik.x509.PublicKeyInfo, java.security.Key
    public String getAlgorithm() {
        return "RSASSA-PSS";
    }

    public AlgorithmParameterSpec getParams() {
        return this.c;
    }

    @Override // iaik.security.rsa.RSAPublicKey, iaik.x509.PublicKeyInfo
    public int hashCode() {
        int iHashCode = super.hashCode();
        RSAPssParameterSpec rSAPssParameterSpec = this.c;
        return rSAPssParameterSpec != null ? iHashCode ^ rSAPssParameterSpec.hashCode() : iHashCode;
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

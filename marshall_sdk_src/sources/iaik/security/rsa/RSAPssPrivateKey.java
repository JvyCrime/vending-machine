package iaik.security.rsa;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.RSAPssParameterSpec;
import iaik.utils.InternalErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class RSAPssPrivateKey extends RSAPrivateKey {
    static Class c;
    private transient RSAPssParameterSpec e;

    RSAPssPrivateKey() {
    }

    public RSAPssPrivateKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public RSAPssPrivateKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public RSAPssPrivateKey(BigInteger bigInteger, BigInteger bigInteger2) {
        a(bigInteger, bigInteger2);
        try {
            a();
        } catch (InvalidParameterSpecException unused) {
        }
    }

    public RSAPssPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        a(bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigInteger6, bigInteger7, bigInteger8);
        try {
            a();
        } catch (InvalidParameterSpecException unused) {
        }
    }

    public RSAPssPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        a(bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigInteger6, bigInteger7, bigInteger8);
        if (algorithmParameterSpec != null) {
            if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
                throw new InvalidParameterSpecException("Parameters must be RSAPssParameterSpec!");
            }
            this.e = (RSAPssParameterSpec) algorithmParameterSpec;
        }
        a();
    }

    public RSAPssPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        a(bigInteger, bigInteger2);
        if (algorithmParameterSpec != null) {
            if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
                throw new InvalidParameterSpecException("Parameters must be RSAPssParameterSpec!");
            }
            this.e = (RSAPssParameterSpec) algorithmParameterSpec;
        }
        a();
    }

    public RSAPssPrivateKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    private void a() throws InvalidParameterSpecException {
        super.a(false);
        this.private_key_algorithm = (AlgorithmID) AlgorithmID.rsassaPss.clone();
        if (this.e != null) {
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("RSASSA-PSS", "IAIK");
                algorithmParameters.init(this.e);
                this.private_key_algorithm.setAlgorithmParameters(algorithmParameters);
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid PSS parameters: ");
                stringBuffer.append(e.toString());
                throw new InvalidParameterSpecException(stringBuffer.toString());
            }
        }
        createPrivateKeyInfo();
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.security.rsa.RSAPrivateKey, iaik.pkcs.pkcs8.PrivateKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        super.decode(bArr);
        if (!this.private_key_algorithm.equals(AlgorithmID.rsassaPss)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid algorithm id (");
            stringBuffer.append(this.private_key_algorithm.getAlgorithm().getID());
            stringBuffer.append(") Not an PSS key!");
            throw new InvalidKeyException(stringBuffer.toString());
        }
        ASN1Object parameter = this.private_key_algorithm.getParameter();
        if (parameter == null || parameter.isA(ASN.NULL)) {
            return;
        }
        try {
            AlgorithmParameters algorithmParameters = this.private_key_algorithm.getAlgorithmParameters("RSASSA-PSS", "IAIK");
            Class clsClass$ = c;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.pkcs.pkcs1.RSAPssParameterSpec");
                c = clsClass$;
            }
            this.e = (RSAPssParameterSpec) algorithmParameters.getParameterSpec(clsClass$);
        } catch (Exception e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Error parsing PSS parameters: ");
            stringBuffer2.append(e.toString());
            throw new InvalidKeyException(stringBuffer2.toString());
        }
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public boolean equals(Object obj) {
        RSAPssParameterSpec rSAPssParameterSpec;
        if (this == obj) {
            return true;
        }
        if (obj instanceof RSAPssPrivateKey) {
            boolean zEquals = super.equals(obj);
            if (!zEquals) {
                return zEquals;
            }
            RSAPssPrivateKey rSAPssPrivateKey = (RSAPssPrivateKey) obj;
            RSAPssParameterSpec rSAPssParameterSpec2 = this.e;
            if (rSAPssParameterSpec2 != null && (rSAPssParameterSpec = rSAPssPrivateKey.e) != null) {
                return rSAPssParameterSpec2.equals(rSAPssParameterSpec);
            }
            if (rSAPssParameterSpec2 == null && rSAPssPrivateKey.e == null) {
                return true;
            }
        }
        return false;
    }

    @Override // iaik.security.rsa.RSAPrivateKey, iaik.pkcs.pkcs8.PrivateKeyInfo, java.security.Key
    public String getAlgorithm() {
        return "RSASSA-PSS";
    }

    public AlgorithmParameterSpec getParams() {
        return this.e;
    }

    @Override // iaik.security.rsa.RSAPrivateKey
    public PublicKey getPublicKey() {
        try {
            return new RSAPssPublicKey(getModulus(), getPublicExponent(), this.e);
        } catch (InvalidParameterSpecException e) {
            throw new InternalErrorException(e.toString());
        }
    }

    @Override // iaik.security.rsa.RSAPrivateKey, iaik.pkcs.pkcs8.PrivateKeyInfo
    public int hashCode() {
        int iHashCode = super.hashCode();
        RSAPssParameterSpec rSAPssParameterSpec = this.e;
        return rSAPssParameterSpec != null ? iHashCode ^ rSAPssParameterSpec.hashCode() : iHashCode;
    }

    @Override // iaik.security.rsa.RSAPrivateKey, iaik.pkcs.pkcs8.PrivateKeyInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        if (this.e != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("parameters:\n");
            stringBuffer2.append(this.e);
            stringBuffer.append(stringBuffer2.toString());
        }
        return stringBuffer.toString();
    }

    public boolean validateParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        return RSAPssPublicKey.a(this.e, algorithmParameterSpec);
    }
}

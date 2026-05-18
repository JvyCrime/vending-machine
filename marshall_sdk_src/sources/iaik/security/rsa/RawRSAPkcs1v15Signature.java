package iaik.security.rsa;

import iaik.pkcs.pkcs1.RSASSAPkcs1v15ParameterSpec;
import iaik.utils.PretendedMessageDigest;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class RawRSAPkcs1v15Signature extends RSASignature {
    private static final HashMap a;

    static {
        HashMap map = new HashMap();
        a = map;
        map.put(Md2RSASignature.a.getAlgorithm(), Md2RSASignature.b);
        map.put(Md5RSASignature.a.getAlgorithm(), Md5RSASignature.b);
        map.put(ShaRSASignature.a.getAlgorithm(), ShaRSASignature.b);
        map.put(Sha224RSASignature.a.getAlgorithm(), Sha224RSASignature.b);
        map.put(Sha256RSASignature.a.getAlgorithm(), Sha256RSASignature.b);
        map.put(Sha384RSASignature.a.getAlgorithm(), Sha384RSASignature.b);
        map.put(Sha512RSASignature.a.getAlgorithm(), Sha512RSASignature.b);
        map.put(RipeMd128RSASignature.a.getAlgorithm(), RipeMd128RSASignature.d);
        map.put(RipeMd128RSASignature.b.getAlgorithm(), RipeMd128RSASignature.d);
        map.put(RipeMd160RSASignature.a.getAlgorithm(), RipeMd160RSASignature.d);
        map.put(RipeMd160RSASignature.b.getAlgorithm(), RipeMd160RSASignature.d);
        map.put(RipeMd256RSASignature.a.getAlgorithm(), RipeMd256RSASignature.b);
        map.put(WhirlpoolRSASignature.a.getAlgorithm(), WhirlpoolRSASignature.b);
    }

    public RawRSAPkcs1v15Signature() {
        super("Raw RSASSA-PKCS1-v1_5", new PretendedMessageDigest());
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected Object engineGetParameter(String str) throws InvalidParameterException {
        return engineGetParameters();
    }

    @Override // java.security.SignatureSpi
    protected AlgorithmParameters engineGetParameters() {
        AlgorithmParameters algorithmParameters = null;
        if (this.c == null) {
            return null;
        }
        try {
            RSASSAPkcs1v15ParameterSpec rSASSAPkcs1v15ParameterSpec = new RSASSAPkcs1v15ParameterSpec(this.c);
            algorithmParameters = AlgorithmParameters.getInstance("RSASSA-PKCS1-v1_5", "IAIK");
            algorithmParameters.init(rSASSAPkcs1v15ParameterSpec);
            return algorithmParameters;
        } catch (Exception unused) {
            return algorithmParameters;
        }
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) throws InvalidParameterException {
        if (!(obj instanceof RSASSAPkcs1v15ParameterSpec)) {
            throw new InvalidParameterException("Parameters must be a RSASSAPkcs1v15ParameterSpec!");
        }
        try {
            engineSetParameter((RSASSAPkcs1v15ParameterSpec) obj);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof RSASSAPkcs1v15ParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Parameters must be a RSASSAPkcs1v15ParameterSpec!");
        }
        this.c = ((RSASSAPkcs1v15ParameterSpec) algorithmParameterSpec).getHashAlgorithm();
        a((byte[][]) a.get(this.c.getAlgorithm()));
    }
}

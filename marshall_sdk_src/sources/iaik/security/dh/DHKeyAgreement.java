package iaik.security.dh;

import iaik.utils.Util;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class DHKeyAgreement extends KeyAgreementSpi {
    private javax.crypto.interfaces.DHPrivateKey a;
    private BigInteger b;
    private BigInteger c;
    private byte[] d;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected Key engineDoPhase(Key key, boolean z) throws IllegalStateException, InvalidKeyException {
        if (!(key instanceof javax.crypto.interfaces.DHPublicKey)) {
            throw new InvalidKeyException("DH public key needed.");
        }
        javax.crypto.interfaces.DHPublicKey dHPublicKey = (javax.crypto.interfaces.DHPublicKey) key;
        DHParameterSpec params = dHPublicKey.getParams();
        if (!this.b.equals(params.getP()) || !this.c.equals(params.getG())) {
            throw new InvalidKeyException("DH parameters are not equal.");
        }
        BigInteger bigIntegerModPow = dHPublicKey.getY().modPow(this.a.getX(), this.b);
        if (!z) {
            return new DHPublicKey(bigIntegerModPow, this.a.getParams());
        }
        byte[] byteArray = bigIntegerModPow.toByteArray();
        this.d = byteArray;
        if (byteArray[0] != 0) {
            return null;
        }
        byte[] bArr = new byte[byteArray.length - 1];
        System.arraycopy(byteArray, 1, bArr, 0, byteArray.length - 1);
        this.d = bArr;
        return null;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected int engineGenerateSecret(byte[] bArr, int i) throws IllegalStateException, ShortBufferException {
        int length = bArr.length - i;
        byte[] bArr2 = this.d;
        if (length < bArr2.length) {
            throw new ShortBufferException("Output buffer is to small for holding the secret.");
        }
        System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
        return this.d.length;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected SecretKey engineGenerateSecret(String str) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = null;
        try {
            try {
                SecretKeySpec secretKeySpec2 = new SecretKeySpec(this.d, str);
                try {
                    return SecretKeyFactory.getInstance(str).generateSecret(secretKeySpec2);
                } catch (NoSuchAlgorithmException unused) {
                    secretKeySpec = secretKeySpec2;
                    return secretKeySpec;
                }
            } catch (InvalidKeySpecException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid key: ");
                stringBuffer.append(e.toString());
                throw new InvalidKeyException(stringBuffer.toString());
            }
        } catch (NoSuchAlgorithmException unused2) {
        }
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected byte[] engineGenerateSecret() throws IllegalStateException {
        return this.d;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        engineInit(key, null, secureRandom);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException {
        if (!(key instanceof javax.crypto.interfaces.DHPrivateKey)) {
            throw new InvalidKeyException("Only DH private keys are allowed.");
        }
        javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) key;
        this.a = dHPrivateKey;
        this.b = dHPrivateKey.getParams().getP();
        this.c = this.a.getParams().getG();
    }
}

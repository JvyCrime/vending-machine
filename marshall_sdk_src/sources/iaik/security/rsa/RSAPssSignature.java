package iaik.security.rsa;

import androidx.core.view.MotionEventCompat;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.pkcs.pkcs1.MaskGenerationAlgorithm;
import iaik.pkcs.pkcs1.Padding;
import iaik.pkcs.pkcs1.RSAPssParameterSpec;
import iaik.security.md.SHA;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RSAPssSignature extends b {
    private static boolean f = false;
    AlgorithmID a;
    MaskGenerationAlgorithm b;
    int d;
    byte[] e;
    private byte g;
    private RSAPssParameterSpec h;
    private RSAPssParameterSpec i;

    public RSAPssSignature() {
        this("RSASSA-PSS");
        e();
    }

    protected RSAPssSignature(String str) {
        super(str, Padding.PADDING_NONE);
        this.g = (byte) -68;
    }

    public static void setValidateAgainstPssKeyParameters(boolean z) {
        f = z;
    }

    @Override // iaik.security.rsa.b
    int d() {
        return 100;
    }

    void e() {
        this.c = (AlgorithmID) AlgorithmID.sha1.clone();
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.mgf1.clone();
        this.a = algorithmID;
        algorithmID.setParameter(this.c.toASN1Object());
        this.hash = new SHA();
        this.b = new MGF1(this.c, this.hash);
        this.d = 20;
        this.g = (byte) -68;
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected Object engineGetParameter(String str) throws InvalidParameterException {
        return engineGetParameters();
    }

    @Override // java.security.SignatureSpi
    protected AlgorithmParameters engineGetParameters() {
        AlgorithmParameters algorithmParameters = null;
        if (this.c == null || this.a == null) {
            return null;
        }
        try {
            RSAPssParameterSpec rSAPssParameterSpec = new RSAPssParameterSpec(this.c, this.a, this.d);
            algorithmParameters = AlgorithmParameters.getInstance("RSASSA-PSS", "IAIK");
            algorithmParameters.init(rSAPssParameterSpec);
            return algorithmParameters;
        } catch (Exception unused) {
            return algorithmParameters;
        }
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey.getClass().getName().indexOf("IAIKPKCS11RsaPrivateKey") != -1) {
            super.engineInitSign(privateKey);
            MaskGenerationAlgorithm maskGenerationAlgorithm = this.b;
            if (maskGenerationAlgorithm != null) {
                maskGenerationAlgorithm.reset();
                return;
            }
            return;
        }
        RSAPrivateKey rSAPrivateKey = Util.getRSAPrivateKey(privateKey);
        if (rSAPrivateKey instanceof RSAPssPrivateKey) {
            try {
                AlgorithmParameterSpec params = ((RSAPssPrivateKey) rSAPrivateKey).getParams();
                if (params != null) {
                    RSAPssParameterSpec rSAPssParameterSpec = this.i;
                    if (rSAPssParameterSpec == null) {
                        engineSetParameter(params);
                    } else if (f) {
                        try {
                            if (!RSAPssPublicKey.a((RSAPssParameterSpec) params, rSAPssParameterSpec)) {
                                throw new InvalidKeyException("Application set parameters are not valid for PSS-Key used with this engine!");
                            }
                        } catch (InvalidParameterSpecException unused) {
                        }
                    }
                    this.h = (RSAPssParameterSpec) ((RSAPssParameterSpec) params).clone();
                }
            } catch (InvalidKeyException e) {
                throw e;
            } catch (Exception e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("RSA-PSS key contains invalid parameters: ");
                stringBuffer.append(e2.toString());
                throw new InvalidKeyException(stringBuffer.toString());
            }
        }
        super.engineInitSign(rSAPrivateKey);
        MaskGenerationAlgorithm maskGenerationAlgorithm2 = this.b;
        if (maskGenerationAlgorithm2 != null) {
            maskGenerationAlgorithm2.reset();
        }
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        RSAPublicKey rSAPublicKey = Util.getRSAPublicKey(publicKey);
        if (rSAPublicKey instanceof RSAPssPublicKey) {
            try {
                AlgorithmParameterSpec params = ((RSAPssPublicKey) rSAPublicKey).getParams();
                if (params != null) {
                    RSAPssParameterSpec rSAPssParameterSpec = this.i;
                    if (rSAPssParameterSpec == null) {
                        engineSetParameter(params);
                    } else if (f) {
                        try {
                            if (!RSAPssPublicKey.a((RSAPssParameterSpec) params, rSAPssParameterSpec)) {
                                throw new InvalidKeyException("Application set parameters are not valid for PSS-Key used with this engine!");
                            }
                        } catch (InvalidParameterSpecException unused) {
                        }
                    }
                    this.h = (RSAPssParameterSpec) ((RSAPssParameterSpec) params).clone();
                }
            } catch (InvalidKeyException e) {
                throw e;
            } catch (Exception e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("RSA-PSS key contains invalid parameters: ");
                stringBuffer.append(e2.toString());
                throw new InvalidKeyException(stringBuffer.toString());
            }
        }
        super.engineInitVerify(rSAPublicKey);
        MaskGenerationAlgorithm maskGenerationAlgorithm = this.b;
        if (maskGenerationAlgorithm != null) {
            maskGenerationAlgorithm.reset();
        }
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null) {
            this.i = null;
            e();
            return;
        }
        if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Params must be a RSAPssParameterSpec!");
        }
        RSAPssParameterSpec rSAPssParameterSpec = (RSAPssParameterSpec) algorithmParameterSpec;
        int trailerField = rSAPssParameterSpec.getTrailerField();
        if (trailerField != 1) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Trailer field number ");
            stringBuffer.append(trailerField);
            stringBuffer.append(" not supported by RSASSA-PSS. Expected ");
            stringBuffer.append(1);
            stringBuffer.append("!");
            throw new InvalidAlgorithmParameterException(stringBuffer.toString());
        }
        RSAPssParameterSpec rSAPssParameterSpec2 = this.h;
        if (rSAPssParameterSpec2 != null && f) {
            try {
                if (!RSAPssPublicKey.a(rSAPssParameterSpec2, rSAPssParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("Parameters are not valid for PSS-Key used with this engine!");
                }
            } catch (InvalidParameterSpecException unused) {
            }
        }
        this.c = rSAPssParameterSpec.getHashAlgorithm();
        try {
            this.hash = rSAPssParameterSpec.getHashEngine();
            this.a = rSAPssParameterSpec.getMaskGenAlgorithm();
            try {
                this.b = rSAPssParameterSpec.getMGFEngine();
                int saltLength = rSAPssParameterSpec.getSaltLength();
                this.d = saltLength;
                if (saltLength < 0) {
                    throw new InvalidAlgorithmParameterException("Cannot set saltLength parameter; must not be negative.");
                }
                this.e = rSAPssParameterSpec.getSalt();
                SecureRandom secureRandom = rSAPssParameterSpec.getSecureRandom();
                if (secureRandom != null) {
                    a(secureRandom);
                }
                this.i = (RSAPssParameterSpec) rSAPssParameterSpec.clone();
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Cannot set mask generation algorithm parameter; no mgf engine available: ");
                stringBuffer2.append(e.getMessage());
                throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
            }
        } catch (NoSuchAlgorithmException e2) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Cannot set hash algorithm parameter; no hash engine available: ");
            stringBuffer3.append(e2.getMessage());
            throw new InvalidAlgorithmParameterException(stringBuffer3.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        Objects.requireNonNull(this.hash, "Cannot calculate signature. Digest engine must not be null!");
        Objects.requireNonNull(this.b, "Cannot calculate signature. MGF engine must not be null!");
        int iB = b();
        int i = iB - 1;
        int i2 = (i + 7) / 8;
        byte[] bArr = new byte[i2];
        byte[] bArrA = a();
        int length = bArrA.length;
        if (this.d < 0) {
            this.d = 20;
        }
        int i3 = this.d;
        if (i2 < length + i3 + 2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Encoding error: emLen (");
            stringBuffer.append(i2);
            stringBuffer.append(") shorter than hashLen + saltLen + 2!");
            throw new SignatureException(stringBuffer.toString());
        }
        byte[] bArr2 = this.e;
        int i4 = length + 8;
        byte[] bArr3 = new byte[i3 + i4];
        System.arraycopy(bArrA, 0, bArr3, 8, length);
        if (this.d > 0) {
            if (bArr2 == null) {
                SecureRandom secureRandomC = c();
                Objects.requireNonNull(secureRandomC, "Cannot calculate signature. No SecureRandom available!");
                bArr2 = new byte[this.d];
                secureRandomC.nextBytes(bArr2);
            }
            System.arraycopy(bArr2, 0, bArr3, i4, this.d);
        }
        byte[] bArrDigest = this.hash.digest(bArr3);
        int i5 = (i2 - length) - 1;
        int i6 = this.d;
        bArr[(i5 - i6) - 1] = 1;
        if (i6 > 0) {
            System.arraycopy(bArr2, 0, bArr, i5 - i6, i6);
        }
        this.b.mask(bArrDigest, 0, bArrDigest.length, i5, bArr, 0);
        bArr[0] = (byte) ((255 >> ((i2 * 8) - i)) & bArr[0]);
        System.arraycopy(bArrDigest, 0, bArr, i5, length);
        bArr[i2 - 1] = this.g;
        try {
            byte[] bArrA2 = a(bArr);
            int i7 = (iB + 7) / 8;
            if (bArrA2.length >= i7) {
                return bArrA2;
            }
            byte[] bArr4 = new byte[i7];
            System.arraycopy(bArrA2, 0, bArr4, i7 - bArrA2.length, bArrA2.length);
            CryptoUtils.zeroBlock(bArrA2);
            return bArr4;
        } catch (Exception e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Signing error: ");
            stringBuffer2.append(e.toString());
            throw new SignatureException(stringBuffer2.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        Objects.requireNonNull(this.hash, "Cannot verify signature. Digest engine must not be null!");
        Objects.requireNonNull(this.b, "Cannot verify signature. MGF engine must not be null!");
        int iB = b();
        int i = (iB + 7) / 8;
        int i2 = iB - 1;
        if (bArr.length != i) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid signature (length is not k (");
            stringBuffer.append(i);
            stringBuffer.append(") octets!");
            throw new SignatureException(stringBuffer.toString());
        }
        try {
            byte[] bArrA = a(bArr);
            int i3 = (i2 + 7) / 8;
            if (i3 < bArrA.length) {
                if (i2 % 8 != 0 || i3 != bArrA.length - 1) {
                    CryptoUtils.zeroBlock(bArrA);
                    throw new SignatureException("Invalid signature. Decrypted message too long");
                }
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArrA, bArrA.length - i3, bArr2, 0, i3);
                CryptoUtils.zeroBlock(bArrA);
                bArrA = bArr2;
            }
            byte[] bArrA2 = a();
            int length = bArrA2.length;
            if (this.d < 0) {
                this.d = 20;
            }
            if (i3 < this.d + length + 2) {
                CryptoUtils.zeroBlock(bArrA);
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Inconsitent length: emLen (");
                stringBuffer2.append(i3);
                stringBuffer2.append(") shorter than hashLen + saltLen + 2!");
                throw new SignatureException(stringBuffer2.toString());
            }
            if (bArrA[bArrA.length - 1] != this.g) {
                CryptoUtils.zeroBlock(bArrA);
                throw new SignatureException("Invalid signature. Inconsistent trailer field.");
            }
            int i4 = (i3 * 8) - i2;
            if ((bArrA[0] & (MotionEventCompat.ACTION_POINTER_INDEX_MASK >> i4) & 255) != 0) {
                CryptoUtils.zeroBlock(bArrA);
                throw new SignatureException("Invalid signature. Leftmost 8emLen - emBits not all zero.");
            }
            int i5 = i3 - length;
            int i6 = i5 - 1;
            this.b.mask(bArrA, i6, length, i6, bArrA, 0);
            bArrA[0] = (byte) ((255 >> i4) & bArrA[0]);
            int i7 = (i5 - this.d) - 2;
            for (int i8 = 0; i8 < i7; i8++) {
                if (bArrA[i8] != 0) {
                    CryptoUtils.zeroBlock(bArrA);
                    throw new SignatureException("Invalid signature. Not all leftmost octets of DB are zero");
                }
            }
            if (bArrA[i7] != 1) {
                CryptoUtils.zeroBlock(bArrA);
                throw new SignatureException("Invalid signature. Missing 0x01 octet");
            }
            int i9 = length + 8;
            byte[] bArr3 = new byte[this.d + i9];
            System.arraycopy(bArrA2, 0, bArr3, 8, length);
            int i10 = this.d;
            if (i10 > 0) {
                System.arraycopy(bArrA, (i5 - i10) - 1, bArr3, i9, i10);
            }
            boolean zSecureEqualsBlock = CryptoUtils.secureEqualsBlock(this.hash.digest(bArr3), 0, bArrA, i6, length);
            CryptoUtils.zeroBlock(bArrA);
            CryptoUtils.zeroBlock(bArr3);
            return zSecureEqualsBlock;
        } catch (Exception e) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Signature decryption error: ");
            stringBuffer3.append(e.toString());
            throw new SignatureException(stringBuffer3.toString());
        }
    }
}

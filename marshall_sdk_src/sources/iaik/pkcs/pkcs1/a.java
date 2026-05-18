package iaik.pkcs.pkcs1;

import iaik.asn1.OCTET_STRING;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.SHA;
import iaik.security.rsa.RSAOaepPrivateKey;
import iaik.security.rsa.RSAOaepPublicKey;
import iaik.security.ssl.SecurityProvider;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Locale;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
class a extends Padding {
    private static final BadPaddingException a = new BadPaddingException("Invalid OAEP padding!");
    private static boolean b = false;
    private AlgorithmID c;
    private MessageDigest d;
    private AlgorithmID e;
    private MaskGenerationAlgorithm f;
    private AlgorithmID g;
    private byte[] h;

    a() {
        super(Padding.PADDING_OAEP);
        this.c = (AlgorithmID) RSAOaepParameterSpec.DEFAULT_HASH_ALGORITHM.clone();
        this.d = new SHA();
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.mgf1.clone();
        this.e = algorithmID;
        algorithmID.setParameter(this.c.toASN1Object());
        this.f = new MGF1(this.c, this.d);
        AlgorithmID algorithmID2 = (AlgorithmID) AlgorithmID.pSpecified.clone();
        this.g = algorithmID2;
        algorithmID2.setParameter(new OCTET_STRING());
        this.h = new byte[0];
    }

    a(String str, AlgorithmID algorithmID, AlgorithmID algorithmID2, AlgorithmID algorithmID3, MessageDigest messageDigest, MaskGenerationAlgorithm maskGenerationAlgorithm, byte[] bArr) {
        super(str);
        this.c = (AlgorithmID) algorithmID.clone();
        this.e = (AlgorithmID) algorithmID2.clone();
        this.g = (AlgorithmID) algorithmID3.clone();
        this.d = messageDigest;
        this.f = maskGenerationAlgorithm;
        this.h = (byte[]) bArr.clone();
    }

    static a a(String str) throws NoSuchPaddingException {
        MessageDigest messageDigestInstance;
        MaskGenerationAlgorithm maskGenerationAlgorithmInstance;
        MaskGenerationAlgorithm mgf1;
        AlgorithmID algorithmID;
        if (str.length() == 4) {
            return new a();
        }
        if (str.length() < 21) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid OAEP padding name: ");
            stringBuffer.append(str);
            stringBuffer.append("!");
            throw new NoSuchPaddingException(stringBuffer.toString());
        }
        String upperCase = str.substring(8, str.length() - 7).toUpperCase(Locale.US);
        int iIndexOf = upperCase.indexOf("AND");
        String strSubstring = upperCase.substring(0, iIndexOf);
        String strSubstring2 = upperCase.substring(iIndexOf + 3);
        AlgorithmID algorithmIDB = b(strSubstring.toUpperCase(Locale.US));
        if (algorithmIDB == null) {
            algorithmIDB = AlgorithmID.getAlgorithmID(strSubstring);
        }
        if (algorithmIDB == null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("No AlgorithmID available for hash algorithm \"");
            stringBuffer2.append(strSubstring);
            stringBuffer2.append("\" of padding scheme ");
            stringBuffer2.append(str);
            throw new NoSuchPaddingException(stringBuffer2.toString());
        }
        AlgorithmID algorithmID2 = (AlgorithmID) algorithmIDB.clone();
        try {
            try {
                messageDigestInstance = algorithmID2.getMessageDigestInstance("IAIK");
            } catch (NoSuchAlgorithmException unused) {
                messageDigestInstance = algorithmID2.getMessageDigestInstance();
            }
            MessageDigest messageDigest = messageDigestInstance;
            if (strSubstring2.equals("MGF1")) {
                AlgorithmID algorithmID3 = (AlgorithmID) AlgorithmID.mgf1.clone();
                algorithmID3.setParameter(algorithmID2.toASN1Object());
                algorithmID = algorithmID3;
                mgf1 = new MGF1(algorithmID2, messageDigest);
            } else {
                AlgorithmID algorithmID4 = AlgorithmID.getAlgorithmID(strSubstring2);
                if (algorithmID4 == null) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("No AlgorithmID available for MGF algorithm \"");
                    stringBuffer3.append(strSubstring2);
                    stringBuffer3.append("\" of padding scheme ");
                    stringBuffer3.append(str);
                    throw new NoSuchPaddingException(stringBuffer3.toString());
                }
                AlgorithmID algorithmID5 = (AlgorithmID) algorithmID4.clone();
                try {
                    try {
                        maskGenerationAlgorithmInstance = algorithmID5.getMaskGenerationAlgorithmInstance("IAIK");
                    } catch (NoSuchAlgorithmException unused2) {
                        maskGenerationAlgorithmInstance = algorithmID5.getMaskGenerationAlgorithmInstance();
                    }
                    mgf1 = maskGenerationAlgorithmInstance;
                    algorithmID = algorithmID5;
                } catch (NoSuchAlgorithmException unused3) {
                    StringBuffer stringBuffer4 = new StringBuffer();
                    stringBuffer4.append("No engine available for MGF algorithm \"");
                    stringBuffer4.append(strSubstring2);
                    stringBuffer4.append("\" of padding scheme ");
                    stringBuffer4.append(str);
                    throw new NoSuchPaddingException(stringBuffer4.toString());
                }
            }
            AlgorithmID algorithmID6 = (AlgorithmID) AlgorithmID.pSpecified.clone();
            algorithmID6.setParameter(new OCTET_STRING());
            return new a(str, algorithmID2, algorithmID, algorithmID6, messageDigest, mgf1, new byte[0]);
        } catch (NoSuchAlgorithmException unused4) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("No engine available for hash algorithm \"");
            stringBuffer5.append(strSubstring);
            stringBuffer5.append("\" of padding scheme ");
            stringBuffer5.append(str);
            throw new NoSuchPaddingException(stringBuffer5.toString());
        }
    }

    private void a(RSAOaepParameterSpec rSAOaepParameterSpec) throws InvalidAlgorithmParameterException {
        this.c = rSAOaepParameterSpec.getHashAlgorithm();
        try {
            this.d = rSAOaepParameterSpec.getHashEngine();
            this.e = rSAOaepParameterSpec.getMaskGenAlgorithm();
            try {
                this.f = rSAOaepParameterSpec.getMGFEngine();
                this.g = rSAOaepParameterSpec.getPSourceAlgorithm();
                this.h = rSAOaepParameterSpec.getLabel();
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot set mask generation algorithm parameter; no mgf engine available: ");
                stringBuffer.append(e.getMessage());
                throw new InvalidAlgorithmParameterException(stringBuffer.toString());
            }
        } catch (NoSuchAlgorithmException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Cannot set hash algorithm parameter; no hash engine available: ");
            stringBuffer2.append(e2.getMessage());
            throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
        }
    }

    static void a(boolean z) {
        b = z;
    }

    private static AlgorithmID b(String str) {
        AlgorithmID algorithmID;
        String upperCase = str.toUpperCase(Locale.US);
        if (upperCase.equals("SHA") || upperCase.equals("SHA1") || upperCase.equals("SHA-1")) {
            algorithmID = AlgorithmID.sha;
        } else if (upperCase.equals(SecurityProvider.ALG_DIGEST_MD5)) {
            algorithmID = AlgorithmID.md5;
        } else if (upperCase.equals("RIPEMD160") || upperCase.equals("RIPEMD-160")) {
            algorithmID = AlgorithmID.ripeMd160;
        } else if (upperCase.equals("RIPEMD128") || upperCase.equals("RIPEMD-128")) {
            algorithmID = AlgorithmID.ripeMd128;
        } else if (upperCase.equals("SHA-256") || upperCase.equals(SecurityProvider.ALG_DIGEST_SHA256)) {
            algorithmID = AlgorithmID.sha256;
        } else if (upperCase.equals("SHA-384") || upperCase.equals(SecurityProvider.ALG_DIGEST_SHA384)) {
            algorithmID = AlgorithmID.sha384;
        } else if (upperCase.equals("SHA-512") || upperCase.equals(SecurityProvider.ALG_DIGEST_SHA512)) {
            algorithmID = AlgorithmID.sha512;
        } else if (upperCase.equals("MD2")) {
            algorithmID = AlgorithmID.md2;
        } else {
            if (!upperCase.equals("WHIRLPOOL")) {
                return null;
            }
            algorithmID = AlgorithmID.whirlpool;
        }
        return (AlgorithmID) algorithmID.clone();
    }

    private void d() throws InvalidAlgorithmParameterException {
        AlgorithmParameterSpec params;
        RSAOaepParameterSpec rSAOaepParameterSpec;
        if (this.publicKey_ != null) {
            if (this.publicKey_ instanceof RSAOaepPublicKey) {
                params = ((RSAOaepPublicKey) this.publicKey_).getParams();
                rSAOaepParameterSpec = (RSAOaepParameterSpec) params;
            }
            rSAOaepParameterSpec = null;
        } else {
            if (this.privateKey_ != null && (this.privateKey_ instanceof RSAOaepPrivateKey)) {
                params = ((RSAOaepPrivateKey) this.privateKey_).getParams();
                rSAOaepParameterSpec = (RSAOaepParameterSpec) params;
            }
            rSAOaepParameterSpec = null;
        }
        if (rSAOaepParameterSpec != null) {
            a(rSAOaepParameterSpec);
        }
    }

    @Override // iaik.pkcs.pkcs1.Padding
    void a() {
        MessageDigest messageDigest = this.d;
        if (messageDigest != null) {
            messageDigest.reset();
        }
        MaskGenerationAlgorithm maskGenerationAlgorithm = this.f;
        if (maskGenerationAlgorithm != null) {
            maskGenerationAlgorithm.reset();
        }
    }

    @Override // iaik.pkcs.pkcs1.Padding
    public AlgorithmParameters getParameters() {
        AlgorithmID algorithmID;
        AlgorithmParameters algorithmParameters = null;
        if (this.c == null || this.e == null || (algorithmID = this.g) == null) {
            return null;
        }
        if (this.h != null) {
            algorithmID.setParameter(new OCTET_STRING((byte[]) this.h.clone()));
        }
        try {
            RSAOaepParameterSpec rSAOaepParameterSpec = new RSAOaepParameterSpec(this.c, this.e, this.g);
            algorithmParameters = AlgorithmParameters.getInstance(Padding.PADDING_OAEP, "IAIK");
            algorithmParameters.init(rSAOaepParameterSpec);
            return algorithmParameters;
        } catch (Exception unused) {
            return algorithmParameters;
        }
    }

    @Override // iaik.pkcs.pkcs1.Padding
    public void init(int i, Key key, int i2, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        super.init(i, key, i2, algorithmParameterSpec, secureRandom);
        if (this.modLen_ < 42) {
            throw new InvalidKeyException("OAEP requires a modulus of at least 336 bits!");
        }
        if (this.opMode_ == 1) {
            if (this.publicKey_ == null) {
                throw new InvalidKeyException("OAEP cannot be used to generate signatures");
            }
        } else if (this.privateKey_ == null) {
            throw new InvalidKeyException("OAEP cannot be used to verify signatures");
        }
        if (algorithmParameterSpec == null) {
            d();
            return;
        }
        if (!(algorithmParameterSpec instanceof RSAOaepParameterSpec)) {
            if (!(algorithmParameterSpec instanceof RSAOaepPSourceParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Invalid parameters. Expected RSAOaepParameterSpec or RSAOaepPSourceParameterSpec!");
            }
            d();
            RSAOaepPSourceParameterSpec rSAOaepPSourceParameterSpec = (RSAOaepPSourceParameterSpec) algorithmParameterSpec;
            this.g = rSAOaepPSourceParameterSpec.getPSourceAlgorithm();
            this.h = rSAOaepPSourceParameterSpec.getLabel();
            return;
        }
        RSAOaepParameterSpec rSAOaepParameterSpec = (RSAOaepParameterSpec) algorithmParameterSpec;
        if (b) {
            try {
                if (this.publicKey_ != null) {
                    if ((this.publicKey_ instanceof RSAOaepPublicKey) && !((RSAOaepPublicKey) this.publicKey_).validateParameters(algorithmParameterSpec)) {
                        throw new InvalidAlgorithmParameterException("Parameters are not valid for OAEP-Key used with this engine!");
                    }
                } else if (this.privateKey_ != null && (this.privateKey_ instanceof RSAOaepPrivateKey) && !((RSAOaepPrivateKey) this.privateKey_).validateParameters(algorithmParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("Parameters are not valid for OAEP-Key used with this engine!");
                }
            } catch (InvalidParameterSpecException unused) {
            }
        }
        a(rSAOaepParameterSpec);
    }

    @Override // iaik.pkcs.pkcs1.Padding
    public byte[] pad(byte[] bArr) throws BadPaddingException {
        Objects.requireNonNull(this.d, "Cannot do OAEP padding. Hash engine must not be null!");
        Objects.requireNonNull(this.f, "Cannot do OAEP padding. MGF engine must not be null!");
        SecureRandom secureRandomB = b();
        Objects.requireNonNull(secureRandomB, "Cannot do OAEP padding. No SecureRandom available!");
        if (this.h == null) {
            this.h = new byte[0];
        }
        byte[] bArrDigest = this.d.digest(this.h);
        int length = bArrDigest.length;
        int i = this.modLen_;
        if (bArr.length > (this.modLen_ - (length * 2)) - 2) {
            throw new BadPaddingException("Message to long for OAEP, must be (2*hLen)-2 bytes less than modulus");
        }
        byte[] bArr2 = new byte[length];
        secureRandomB.nextBytes(bArr2);
        byte[] bArr3 = new byte[i];
        int i2 = (i - length) - 1;
        int i3 = length + 1;
        System.arraycopy(bArr2, 0, bArr3, 1, length);
        System.arraycopy(bArrDigest, 0, bArr3, i3, length);
        System.arraycopy(bArr, 0, bArr3, i - bArr.length, bArr.length);
        bArr3[(i - bArr.length) - 1] = 1;
        this.f.mask(bArr3, 1, length, i2, bArr3, i3);
        this.f.mask(bArr3, i3, i2, length, bArr3, 1);
        a();
        return bArr3;
    }

    @Override // iaik.pkcs.pkcs1.Padding
    public byte[] unpad(byte[] bArr) throws BadPaddingException {
        int i;
        MessageDigest messageDigest = this.d;
        Objects.requireNonNull(messageDigest, "Cannot do OAEP padding. Hash engine must not be null!");
        Objects.requireNonNull(this.f, "Cannot do OAEP padding. MGF engine must not be null!");
        int length = bArr.length;
        if (this.h == null) {
            this.h = new byte[0];
        }
        byte[] bArrDigest = messageDigest.digest(this.h);
        int length2 = bArrDigest.length;
        if (this.modLen_ < (length2 * 2) + 2) {
            throw new BadPaddingException("Invalid OAEP: decrypted message too short");
        }
        boolean z = true;
        int i2 = (length - length2) - 1;
        int i3 = length2 + 1;
        this.f.mask(bArr, i3, i2, length2, bArr, 1);
        this.f.mask(bArr, 1, length2, i2, bArr, i3);
        int i4 = 0;
        boolean z2 = false;
        while (i4 < length2) {
            if (bArr[i3 + i4] != bArrDigest[i4]) {
                z2 = true;
            }
            i4++;
        }
        int i5 = i3 + i4;
        int i6 = i4;
        int i7 = -1;
        while (i4 < i2) {
            if (bArr[i3 + i6] == 0) {
                i6++;
            } else {
                i7++;
            }
            i4++;
        }
        if (i6 != i2) {
            i = i6 + 1;
            if (bArr[i6 + i3] != 1) {
                i6 = i;
                i = i6;
            } else {
                z = z2;
            }
        } else {
            i = i6;
        }
        if (i7 == -1) {
            i7++;
        }
        int i8 = i3 + i;
        int i9 = i8 - i5;
        byte[] bArr2 = new byte[i9];
        System.arraycopy(bArr, i5, bArr2, 0, i9);
        byte[] bArr3 = new byte[i7];
        System.arraycopy(bArr, i8, bArr3, 0, i7);
        System.arraycopy(bArr3, 0, bArr, 0, i7);
        System.arraycopy(bArr2, 0, bArr, i7, i9);
        a();
        if (z) {
            throw a;
        }
        return bArr3;
    }
}

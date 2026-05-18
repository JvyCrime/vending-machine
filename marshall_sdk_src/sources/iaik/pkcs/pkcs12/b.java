package iaik.pkcs.pkcs12;

import iaik.security.spec.PBEKeyAndParameterSpec;
import iaik.security.ssl.SecurityProvider;
import iaik.utils.InternalErrorException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
abstract class b extends KeyGeneratorSpi {
    protected int ID;
    private byte[] a;
    private byte[] b;
    private int c = 1;
    private int d;
    protected String hash_algorithm;

    b() {
    }

    private static final int a(int i, int i2) {
        int i3 = i % i2;
        int i4 = i / i2;
        return i3 == 0 ? i4 : i4 + 1;
    }

    private byte[] a() {
        int i;
        int i2;
        if (this.a == null || this.b == null) {
            throw new IllegalStateException("Not initialized with PBE params");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(this.hash_algorithm);
            int length = this.b.length * 8;
            int length2 = this.a.length * 8;
            int iA = a(length, 512) * 512;
            int iA2 = a(length2, 512) * 512;
            byte[] bArr = new byte[64];
            int i3 = iA / 8;
            byte[] bArr2 = new byte[i3];
            int i4 = iA2 / 8;
            byte[] bArr3 = new byte[i4];
            byte[] bArr4 = new byte[(iA + iA2) / 8];
            if (this.hash_algorithm.equals(SecurityProvider.ALG_DIGEST_MD5)) {
                i = 128;
            } else {
                if (!this.hash_algorithm.startsWith("SHA")) {
                    throw new InternalErrorException("Unknown hash algorithm.");
                }
                i = 160;
            }
            int i5 = i / 8;
            int i6 = 0;
            for (int i7 = 64; i6 < i7; i7 = 64) {
                bArr[i6] = (byte) this.ID;
                i6++;
            }
            int i8 = 0;
            while (i8 < iA / length) {
                byte[] bArr5 = this.b;
                System.arraycopy(bArr5, 0, bArr2, bArr5.length * i8, bArr5.length);
                i8++;
                messageDigest = messageDigest;
                bArr = bArr;
                i5 = i5;
            }
            MessageDigest messageDigest2 = messageDigest;
            byte[] bArr6 = bArr;
            int i9 = i5;
            int i10 = 0;
            byte[] bArr7 = this.b;
            System.arraycopy(bArr7, 0, bArr2, bArr7.length * i8, (iA - (i8 * length)) / 8);
            if (length2 > 0) {
                int i11 = 0;
                while (i11 < iA2 / length2) {
                    byte[] bArr8 = this.a;
                    System.arraycopy(bArr8, i10, bArr3, bArr8.length * i11, bArr8.length);
                    i11++;
                    i10 = 0;
                }
                i8 = i11;
            }
            byte[] bArr9 = this.a;
            System.arraycopy(bArr9, 0, bArr3, bArr9.length * i8, (iA2 - (i8 * length2)) / 8);
            System.arraycopy(bArr2, 0, bArr4, 0, i3);
            System.arraycopy(bArr3, 0, bArr4, i3, i4);
            int iA3 = a(this.d * 8, i);
            byte[] bArr10 = new byte[iA3 * i9];
            int iA4 = a(length, 512) + a(length2, 512);
            int i12 = 0;
            while (i12 < iA3) {
                MessageDigest messageDigest3 = messageDigest2;
                byte[] bArr11 = bArr6;
                messageDigest3.update(bArr11);
                messageDigest3.update(bArr4);
                for (int i13 = 0; i13 < this.c - 1; i13++) {
                    messageDigest3.update(messageDigest3.digest());
                }
                byte[] bArrDigest = messageDigest3.digest();
                int i14 = i9;
                int i15 = 0;
                System.arraycopy(bArrDigest, 0, bArr10, i12 * i9, i14);
                byte[] bArr12 = new byte[64];
                int i16 = 0;
                while (i16 < 512 / i) {
                    System.arraycopy(bArrDigest, 0, bArr12, i16 * i14, i14);
                    i16++;
                }
                System.arraycopy(bArrDigest, 0, bArr12, i16 * i14, (512 - (i16 * i)) / 8);
                int i17 = 0;
                while (i17 < iA4) {
                    byte[] bArr13 = new byte[64];
                    int i18 = i17 * 64;
                    System.arraycopy(bArr4, i18, bArr13, i15, 64);
                    byte[] byteArray = new BigInteger(1, bArr13).add(new BigInteger(1, bArr12)).add(BigInteger.valueOf(1L)).mod(BigInteger.valueOf(0L).setBit(512)).toByteArray();
                    int length3 = byteArray.length;
                    int i19 = iA3;
                    if (length3 > 64) {
                        System.arraycopy(byteArray, 1, bArr4, i18, 64);
                        i2 = iA4;
                        i15 = 0;
                    } else if (length3 < 64) {
                        i2 = iA4;
                        i15 = 0;
                        System.arraycopy(byteArray, 0, bArr4, (i18 + 64) - length3, Math.min(64, length3));
                        for (int i20 = 0; i20 < 64 - length3; i20++) {
                            bArr4[i18 + i20] = 0;
                        }
                    } else {
                        i2 = iA4;
                        i15 = 0;
                        System.arraycopy(byteArray, 0, bArr4, i18, 64);
                    }
                    i17++;
                    iA3 = i19;
                    iA4 = i2;
                }
                i12++;
                messageDigest2 = messageDigest3;
                iA3 = iA3;
                bArr6 = bArr11;
                i9 = i14;
            }
            int i21 = this.d;
            byte[] bArr14 = new byte[i21];
            System.arraycopy(bArr10, 0, bArr14, 0, i21);
            return bArr14;
        } catch (NoSuchAlgorithmException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected SecretKey engineGenerateKey() {
        return new iaik.security.cipher.SecretKey(a(), "RAW");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(int i, SecureRandom secureRandom) {
        this.d = i;
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(SecureRandom secureRandom) {
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof PBEKeyAndParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Only instance of PBEKeyAndParameterSpec can be used!");
        }
        PBEKeyAndParameterSpec pBEKeyAndParameterSpec = (PBEKeyAndParameterSpec) algorithmParameterSpec;
        this.a = pBEKeyAndParameterSpec.getPassword();
        this.b = pBEKeyAndParameterSpec.getSalt();
        this.c = pBEKeyAndParameterSpec.getIterationCount();
        this.d = pBEKeyAndParameterSpec.getDerivedKeyLength();
    }
}

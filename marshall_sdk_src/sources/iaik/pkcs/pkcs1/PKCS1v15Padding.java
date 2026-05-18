package iaik.pkcs.pkcs1;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
public final class PKCS1v15Padding extends Padding {
    private static final BadPaddingException a = new BadPaddingException("Invalid padding!");
    private int b;
    private boolean c;

    PKCS1v15Padding() {
        super(Padding.PADDING_PKCS1);
        this.b = -1;
    }

    public PKCS1v15Padding(String str, String str2) throws NoSuchPaddingException {
        int i;
        super(str);
        if (str.equalsIgnoreCase(Padding.PADDING_PKCS1_SSL2)) {
            this.c = true;
        }
        if (str2 == null || str2.equalsIgnoreCase("ECB") || str2.equalsIgnoreCase("SSL") || str2.equalsIgnoreCase("NONE")) {
            i = -1;
        } else {
            try {
                i = Integer.parseInt(str2);
                if (i != 0 && i != 1 && i != 2) {
                    throw new NoSuchPaddingException("Block type for PKCS1Padding must be 0, 1 or 2.");
                }
            } catch (NumberFormatException unused) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Bad block type (");
                stringBuffer.append(str2);
                stringBuffer.append(") for PKCS1Padding");
                throw new NoSuchPaddingException(stringBuffer.toString());
            }
        }
        this.b = i;
    }

    @Override // iaik.pkcs.pkcs1.Padding
    public AlgorithmParameters getParameters() {
        return null;
    }

    @Override // iaik.pkcs.pkcs1.Padding
    public void init(int i, Key key, int i2, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        super.init(i, key, i2, algorithmParameterSpec, secureRandom);
        if (this.opMode_ == 2 && this.c) {
            throw new InvalidAlgorithmParameterException("SSL2 variant of PKCS#1 padding not supported in decryption mode!");
        }
        if (this.b != -1) {
            if (this.opMode_ == 1) {
                if (this.b == 2) {
                    if (this.privateKey_ != null) {
                        throw new InvalidKeyException("Must use public key for encryption with block type 2!");
                    }
                } else if (this.publicKey_ != null) {
                    throw new InvalidKeyException("Must use private key for encryption with block type 0 and 1!");
                }
            } else if (this.b == 2) {
                if (this.publicKey_ != null) {
                    throw new InvalidKeyException("Must use private key for decryption with block type 2!");
                }
            } else if (this.privateKey_ != null) {
                throw new InvalidKeyException("Must use public key for decryption with block type 0 and 1!");
            }
        }
        if (this.modLen_ < 41) {
            throw new InvalidKeyException("PKCS#1 requires a modulus of at least 328 bits!");
        }
    }

    @Override // iaik.pkcs.pkcs1.Padding
    public byte[] pad(byte[] bArr) throws BadPaddingException {
        int i;
        int length = bArr.length;
        int i2 = (this.modLen_ - 3) - length;
        if (i2 < 8) {
            throw new BadPaddingException("PKCS#1 requires data at least 11 bytes shorter than the modulus!");
        }
        int i3 = this.b;
        int i4 = 2;
        if (i3 == -1) {
            i3 = this.publicKey_ != null ? 2 : 1;
        }
        byte[] bArr2 = new byte[this.modLen_];
        bArr2[0] = 0;
        bArr2[1] = (byte) i3;
        if (i3 == 2) {
            SecureRandom secureRandomB = b();
            Objects.requireNonNull(secureRandomB, "Cannot do PKCS1 padding. No SecureRandom available!");
            byte[] bArr3 = new byte[1];
            int i5 = 0;
            while (i5 < i2) {
                if (!this.c || i2 - i5 > 8) {
                    do {
                        secureRandomB.nextBytes(bArr3);
                    } while (bArr3[0] == 0);
                } else {
                    bArr3[0] = 3;
                }
                bArr2[i4] = bArr3[0];
                i5++;
                i4++;
            }
        } else {
            if (i3 != 0) {
                i = 255;
            } else {
                if (bArr[0] == 0) {
                    throw new BadPaddingException("For block type 0 data must begin with nonzero value");
                }
                i = 0;
            }
            int i6 = 0;
            while (i6 < i2) {
                bArr2[i4] = (byte) i;
                i6++;
                i4++;
            }
        }
        bArr2[i4] = 0;
        System.arraycopy(bArr, 0, bArr2, i4 + 1, length);
        return bArr2;
    }

    /* JADX WARN: Removed duplicated region for block: B:79:0x009a A[PHI: r2 r8
  0x009a: PHI (r2v17 boolean) = (r2v10 boolean), (r2v19 boolean), (r2v26 boolean) binds: [B:77:0x0097, B:59:0x0075, B:41:0x0052] A[DONT_GENERATE, DONT_INLINE]
  0x009a: PHI (r8v5 int) = (r8v1 int), (r8v7 int), (r8v11 int) binds: [B:77:0x0097, B:59:0x0075, B:41:0x0052] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // iaik.pkcs.pkcs1.Padding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public byte[] unpad(byte[] r11) throws javax.crypto.BadPaddingException {
        /*
            r10 = this;
            int r0 = r11.length
            r1 = 0
            r2 = r11[r1]
            r3 = 1
            if (r2 == 0) goto L9
            r2 = 1
            goto La
        L9:
            r2 = 0
        La:
            r4 = r11[r3]
            int r5 = r10.b
            r6 = -1
            if (r5 == r6) goto L14
            if (r5 == r4) goto L14
            r2 = 1
        L14:
            r5 = 2
            if (r4 < 0) goto L19
            if (r4 <= r5) goto L2a
        L19:
            java.security.PublicKey r2 = r10.publicKey_
            if (r2 == 0) goto L1f
            r4 = 1
            goto L20
        L1f:
            r4 = 2
        L20:
            if (r4 != r3) goto L29
            r2 = r11[r5]
            if (r2 != 0) goto L29
            r2 = 1
            r4 = 0
            goto L2a
        L29:
            r2 = 1
        L2a:
            r6 = 8
            r7 = 255(0xff, float:3.57E-43)
            if (r4 == 0) goto L78
            if (r4 == r3) goto L55
            java.security.PublicKey r4 = r10.publicKey_
            if (r4 == 0) goto L37
            r2 = 1
        L37:
            r4 = 2
            r8 = 2
        L39:
            if (r4 >= r0) goto L50
            r9 = r11[r4]
            r9 = r9 & r7
            if (r9 != 0) goto L47
            if (r8 != r5) goto L47
            int r8 = r4 + 1
            if (r4 != r5) goto L47
            r2 = 1
        L47:
            if (r9 == r7) goto L4d
            if (r8 != r5) goto L4d
            r2 = r2 | 0
        L4d:
            int r4 = r4 + 1
            goto L39
        L50:
            int r4 = r8 + (-3)
            if (r4 >= r6) goto L9a
            goto L9b
        L55:
            java.security.PrivateKey r4 = r10.privateKey_
            if (r4 == 0) goto L5a
            r2 = 1
        L5a:
            r4 = 2
            r8 = 2
        L5c:
            if (r4 >= r0) goto L73
            r9 = r11[r4]
            r9 = r9 & r7
            if (r9 != 0) goto L6a
            if (r8 != r5) goto L6a
            int r8 = r4 + 1
            if (r4 != r5) goto L6a
            r2 = 1
        L6a:
            if (r9 == r7) goto L70
            if (r8 != r5) goto L70
            r2 = r2 | 1
        L70:
            int r4 = r4 + 1
            goto L5c
        L73:
            int r4 = r8 + (-3)
            if (r4 >= r6) goto L9a
            goto L9b
        L78:
            java.security.PrivateKey r4 = r10.privateKey_
            if (r4 == 0) goto L7d
            r2 = 1
        L7d:
            r4 = 2
            r8 = 2
        L7f:
            if (r4 >= r0) goto L95
            r9 = r11[r4]
            r9 = r9 & r7
            if (r9 == 0) goto L8c
            if (r8 != r5) goto L8c
            r8 = r4
            if (r4 != r5) goto L8c
            r2 = 1
        L8c:
            if (r9 == r7) goto L92
            if (r8 != r5) goto L92
            r2 = r2 | 0
        L92:
            int r4 = r4 + 1
            goto L7f
        L95:
            int r4 = r8 + (-2)
            if (r4 >= r6) goto L9a
            goto L9b
        L9a:
            r3 = r2
        L9b:
            byte[] r2 = new byte[r8]
            java.lang.System.arraycopy(r11, r1, r2, r1, r8)
            int r0 = r0 - r8
            byte[] r4 = new byte[r0]
            java.lang.System.arraycopy(r11, r8, r4, r1, r0)
            java.lang.System.arraycopy(r4, r1, r11, r1, r0)
            java.lang.System.arraycopy(r2, r1, r11, r0, r8)
            if (r3 != 0) goto Laf
            return r4
        Laf:
            javax.crypto.BadPaddingException r11 = iaik.pkcs.pkcs1.PKCS1v15Padding.a
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.pkcs.pkcs1.PKCS1v15Padding.unpad(byte[]):byte[]");
    }
}

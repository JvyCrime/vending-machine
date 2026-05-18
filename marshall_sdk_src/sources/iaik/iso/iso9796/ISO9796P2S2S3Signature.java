package iaik.iso.iso9796;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.pkcs.pkcs1.MaskGenerationAlgorithm;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.Objects;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class ISO9796P2S2S3Signature extends ISO9796P2Signature {
    int a;
    byte[] b;
    protected MaskGenerationAlgorithm mgfEngine_;
    private int p;

    protected ISO9796P2S2S3Signature(String str) {
        super(str);
        this.mgfEngine_ = new MGF1((AlgorithmID) AlgorithmID.sha1.clone(), this.hashEngine_);
        this.p = 0;
        this.a = 20;
    }

    protected ISO9796P2S2S3Signature(String str, int i, byte b) {
        super(str, i, b);
        this.p = 0;
        this.a = i;
    }

    public static int calculateCapacity(int i, int i2, int i3, boolean z) {
        int i4 = (((i - i2) - i3) - (z ? 16 : 8)) - 2;
        if (i4 >= 7) {
            return i4;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid capacity (");
        stringBuffer.append(i4);
        stringBuffer.append("). Must be >= 7!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static int calculateCapacity(int i, int i2, boolean z) {
        throw new IllegalStateException("Method not supported. Use calculateCapacity(modLen, hashLen, saltLen, explicit)!");
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    int a() {
        return calculateCapacity(this.h, this.d * 8, this.a * 8, this.f) - this.p;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    @Override // iaik.iso.iso9796.ISO9796P2Signature, java.security.SignatureSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void engineSetParameter(java.security.spec.AlgorithmParameterSpec r5) throws java.security.InvalidAlgorithmParameterException {
        /*
            r4 = this;
            boolean r0 = r5 instanceof iaik.iso.iso9796.ISO9796P2S2S3ParameterSpec
            if (r0 == 0) goto L85
            super.engineSetParameter(r5)
            r0 = r5
            iaik.iso.iso9796.ISO9796P2S2S3ParameterSpec r0 = (iaik.iso.iso9796.ISO9796P2S2S3ParameterSpec) r0
            iaik.pkcs.pkcs1.MaskGenerationAlgorithm r1 = r0.getMGFEngine()
            if (r1 == 0) goto L55
            boolean r2 = r4.m
            if (r2 == 0) goto L53
            iaik.pkcs.pkcs1.MaskGenerationAlgorithm r2 = r4.mgfEngine_
            java.lang.String r2 = r2.getAlgorithm()
            java.lang.String r3 = r1.getAlgorithm()
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L25
            goto L53
        L25:
            java.security.InvalidAlgorithmParameterException r5 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r2 = "Invalid mgf engine parameter ("
            r0.append(r2)
            java.lang.String r1 = r1.getAlgorithm()
            r0.append(r1)
            java.lang.String r1 = "). Expected "
            r0.append(r1)
            iaik.pkcs.pkcs1.MaskGenerationAlgorithm r1 = r4.mgfEngine_
            java.lang.String r1 = r1.getAlgorithm()
            r0.append(r1)
            java.lang.String r1 = "."
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L53:
            r4.mgfEngine_ = r1
        L55:
            byte[] r1 = r0.getSalt()
            if (r1 == 0) goto L5f
            int r2 = r1.length
        L5c:
            r4.a = r2
            goto L67
        L5f:
            int r2 = r0.getSaltLength()
            r3 = -1
            if (r2 <= r3) goto L67
            goto L5c
        L67:
            r4.b = r1
            int r0 = r0.getCMinus()
            r4.p = r0
            boolean r0 = r5 instanceof iaik.iso.iso9796.RawISO9796P2S2S3ParameterSpec
            if (r0 == 0) goto L84
            iaik.iso.iso9796.RawISO9796P2S2S3ParameterSpec r5 = (iaik.iso.iso9796.RawISO9796P2S2S3ParameterSpec) r5
            byte[] r0 = r5.getMr()
            int r1 = r5.getMsgLen()
            java.security.MessageDigest r5 = r5.a()
            r4.a(r0, r1, r5)
        L84:
            return
        L85:
            java.security.InvalidAlgorithmParameterException r5 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r0 = "Parameters must be a ISO9796P2S2S3ParameterSpec!"
            r5.<init>(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.iso.iso9796.ISO9796P2S2S3Signature.engineSetParameter(java.security.spec.AlgorithmParameterSpec):void");
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature, java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        byte[] bArrDigest;
        int i;
        int i2;
        int i3;
        Objects.requireNonNull(this.hashEngine_, "MessageDigest engine must not be null!");
        Objects.requireNonNull(this.mgfEngine_, "MGF engine must not be null!");
        if (this.i == null) {
            b();
        }
        this.j = null;
        byte[] bArr = new byte[8];
        CryptoUtils.spreadIntsToBytes(new int[]{this.k * 8}, 0, bArr, 4, 1);
        byte[] bArr2 = this.b;
        if (bArr2 == null && (i3 = this.a) > 0) {
            bArr2 = new byte[i3];
            getSecureRandom().nextBytes(bArr2);
        }
        if (this.o != null) {
            bArrDigest = this.o.digest();
            this.hashEngine_.reset();
        } else {
            bArrDigest = this.hashEngine_.digest();
        }
        this.hashEngine_.update(bArr);
        if (this.k > 0) {
            this.hashEngine_.update(this.i, 0, this.k);
        }
        this.hashEngine_.update(bArrDigest);
        byte[] bArrDigest2 = this.a > 0 ? this.hashEngine_.digest(bArr2) : this.hashEngine_.digest();
        int length = bArrDigest2.length;
        int i4 = (this.h + 7) / 8;
        byte[] bArr3 = new byte[i4];
        int i5 = i4 - 1;
        if (this.f) {
            bArr3[i5] = -52;
            i5--;
            bArr3[i5] = this.e;
            i = 2;
        } else {
            bArr3[i5] = -68;
            i = 1;
        }
        if (i4 < this.k + 1 + this.a + length + i) {
            CryptoUtils.zeroBlock(bArrDigest2);
            CryptoUtils.zeroBlock(bArr3);
            throw new SignatureException("Modulus too short!");
        }
        int length2 = i5 - length;
        System.arraycopy(bArrDigest2, 0, bArr3, length2, length);
        if (bArr2 != null && (i2 = this.a) > 0) {
            length2 -= bArr2.length;
            System.arraycopy(bArr2, 0, bArr3, length2, i2);
        }
        int i6 = length2 - this.k;
        System.arraycopy(this.i, 0, bArr3, i6, this.k);
        bArr3[i6 - 1] = 1;
        this.mgfEngine_.mask(bArrDigest2, 0, length, (i4 - length) - i, bArr3, 0);
        CryptoUtils.zeroBlock(bArrDigest2);
        bArr3[0] = (byte) (bArr3[0] & ByteCompanionObject.MAX_VALUE);
        byte[] bArrA = a(bArr3);
        reset(null);
        CryptoUtils.zeroBlock(bArr3);
        return bArrA;
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature, java.security.SignatureSpi
    protected void engineUpdate(byte b) {
        MessageDigest messageDigest;
        this.j = null;
        if (this.hashEngine_ == null) {
            Objects.requireNonNull(this.o, "MessageDigest engine must not be null!");
        }
        if (this.i == null) {
            b();
        }
        if (this.o != null) {
            messageDigest = this.o;
        } else {
            this.l++;
            if (this.k < this.i.length) {
                byte[] bArr = this.i;
                int i = this.k;
                this.k = i + 1;
                bArr[i] = b;
                return;
            }
            messageDigest = this.hashEngine_;
        }
        messageDigest.update(b);
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature, java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.j = null;
        if (this.hashEngine_ == null) {
            Objects.requireNonNull(this.o, "MessageDigest engine must not be null!");
        }
        if (this.i == null) {
            b();
        }
        if (this.o != null) {
            this.o.update(bArr, i, i2);
            return;
        }
        if (this.k < this.i.length) {
            int iMin = Math.min(this.i.length - this.k, i2);
            System.arraycopy(bArr, i, this.i, this.k, iMin);
            this.k += iMin;
            int i3 = i2 - iMin;
            if (i3 > 0) {
                this.hashEngine_.update(bArr, i + iMin, i3);
            }
        } else {
            this.hashEngine_.update(bArr, i, i2);
        }
        this.l += i2;
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature, java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        byte[] bArrDigest;
        this.j = null;
        Objects.requireNonNull(this.mgfEngine_, "Cannot verify signature. MGF engine must not be null!");
        if (this.h == 0) {
            throw new IllegalStateException("Length of public parameter not set");
        }
        byte[] bArrB = b(bArr);
        if (bArrB.length * 8 != this.h && (bArrB[0] & 128) != 0) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid signature. Sr not k-1 bits long.");
        }
        int length = bArrB.length - 1;
        if (bArrB[length] == -52) {
            length--;
            if (bArrB[length] != this.e) {
                MessageDigest messageDigestB = b(bArrB[1]);
                if (messageDigestB == null) {
                    CryptoUtils.zeroBlock(bArrB);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid hash id. Expected ");
                    stringBuffer.append(Util.toString(this.e));
                    throw new SignatureException(stringBuffer.toString());
                }
                this.hashEngine_ = messageDigestB;
            }
        } else if (bArrB[length] != -68) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid trailer byte. Expected 0xBC or 0xCC.");
        }
        if (this.hashEngine_ == null) {
            CryptoUtils.zeroBlock(bArrB);
            throw new NullPointerException("MessageDigest engine must not be null!");
        }
        if (this.o != null) {
            bArrDigest = this.o.digest();
            this.hashEngine_.reset();
        } else {
            bArrDigest = this.hashEngine_.digest();
        }
        byte[] bArr2 = bArrDigest;
        int length2 = bArr2.length;
        int i = length - length2;
        if (i <= this.a + 2) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid formatted signature string!");
        }
        this.mgfEngine_.mask(bArrB, i, length2, i, bArrB, 0);
        bArrB[0] = (byte) (bArrB[0] & ByteCompanionObject.MAX_VALUE);
        int i2 = 0;
        while (i2 < i && bArrB[i2] == 0) {
            i2++;
        }
        if (i2 == i) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid padding!");
        }
        if (bArrB[i2] != 1) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid border byte. Expected 0x01!");
        }
        int i3 = i - this.a;
        int i4 = i2 + 1;
        int i5 = i3 - i4;
        if (i5 < 0) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid padding!");
        }
        if (this.i != null && !CryptoUtils.secureEqualsBlock(bArrB, i4, this.i, 0, i5)) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid message recovery.");
        }
        this.j = new byte[i5];
        System.arraycopy(bArrB, i4, this.j, 0, i5);
        byte[] bArr3 = new byte[8];
        CryptoUtils.spreadIntsToBytes(new int[]{i5 * 8}, 0, bArr3, 4, 1);
        this.hashEngine_.update(bArr3);
        if (i5 > 0) {
            this.hashEngine_.update(this.j);
        }
        this.hashEngine_.update(bArr2);
        if (this.a > 0) {
            this.hashEngine_.update(bArrB, i3, this.a);
        }
        boolean zSecureEqualsBlock = CryptoUtils.secureEqualsBlock(bArrB, i, this.hashEngine_.digest(), 0, length2);
        CryptoUtils.zeroBlock(bArrB);
        if (!zSecureEqualsBlock) {
            CryptoUtils.zeroBlock(this.j);
            this.j = null;
        }
        reset(null);
        return zSecureEqualsBlock;
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected void reset(BigInteger bigInteger) {
        super.reset(bigInteger);
        MaskGenerationAlgorithm maskGenerationAlgorithm = this.mgfEngine_;
        if (maskGenerationAlgorithm != null) {
            maskGenerationAlgorithm.reset();
        }
    }
}

package iaik.iso.iso9796;

import com.bitmick.marshall.vmc.marshall_t;
import iaik.security.md.SHA;
import iaik.security.random.SecRandom;
import iaik.security.ssl.SecurityProvider;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class ISO9796P2Signature extends SignatureSpi {
    private static final BigInteger a = BigInteger.valueOf(12);
    private static final BigInteger b = BigInteger.valueOf(16);
    static HashMap c;
    int d;
    byte e;
    boolean f;
    int g;
    int h;
    protected MessageDigest hashEngine_;
    byte[] i;
    byte[] j;
    int k;
    int l;
    boolean m;
    boolean n;
    MessageDigest o;
    private BigInteger p;
    private BigInteger q;
    protected SecureRandom secureRandom_;

    static {
        HashMap map = new HashMap(10);
        c = map;
        map.put(new Byte((byte) 49), "RIPEMD160");
        c.put(new Byte((byte) 50), "RIPEMD128");
        c.put(new Byte((byte) 51), "SHA1");
        c.put(new Byte((byte) 52), SecurityProvider.ALG_DIGEST_SHA256);
        c.put(new Byte((byte) 54), SecurityProvider.ALG_DIGEST_SHA384);
        c.put(new Byte((byte) 53), SecurityProvider.ALG_DIGEST_SHA512);
        c.put(new Byte((byte) 55), "WHIRLPOOL");
    }

    protected ISO9796P2Signature(String str) {
        this.hashEngine_ = new SHA();
        this.e = (byte) 51;
        this.d = 20;
        this.f = true;
        this.m = false;
        this.o = null;
        this.n = true;
    }

    protected ISO9796P2Signature(String str, int i, byte b2) {
        this.e = b2;
        this.d = i;
        this.f = false;
        this.m = true;
        this.o = null;
        this.n = true;
    }

    static String a(byte b2) {
        return (String) c.get(new Byte(b2));
    }

    private static byte[] a(byte[] bArr, int i) {
        if (bArr.length == i) {
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        if (bArr.length > i) {
            System.arraycopy(bArr, bArr.length - i, bArr2, 0, i);
        } else if (bArr.length < i) {
            System.arraycopy(bArr, 0, bArr2, i - bArr.length, bArr.length);
        }
        return bArr2;
    }

    public static int calculateCapacity(int i, int i2, boolean z) {
        int i3 = ((i - i2) - (z ? 16 : 8)) - 4;
        if (i3 >= 7) {
            return i3;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid capacity (");
        stringBuffer.append(i3);
        stringBuffer.append("). Must be >= 7!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static void registerHashEngine(byte b2, String str) {
        c.put(new Byte(b2), str);
    }

    int a() {
        return calculateCapacity(this.h, this.d * 8, this.f);
    }

    void a(byte[] bArr, int i, MessageDigest messageDigest) throws InvalidAlgorithmParameterException {
        if (messageDigest != null && this.m && !checkHashEngineName(messageDigest.getAlgorithm())) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid hash engine parameter (");
            stringBuffer.append(messageDigest.getAlgorithm());
            stringBuffer.append("). Expected ");
            stringBuffer.append(this.hashEngine_.getAlgorithm());
            stringBuffer.append(".");
            throw new InvalidAlgorithmParameterException(stringBuffer.toString());
        }
        this.l = i;
        this.o = messageDigest;
        if (this.i == null) {
            b();
        }
        int iMin = Math.min(this.i.length, bArr.length);
        this.k = iMin;
        System.arraycopy(bArr, 0, this.i, 0, iMin);
    }

    byte[] a(byte[] bArr) throws SignatureException {
        byte[] bArrProduceSignature = produceSignature(bArr);
        if (this.n) {
            return bArrProduceSignature;
        }
        BigInteger bigInteger = new BigInteger(1, bArrProduceSignature);
        return a(bigInteger.min(this.p.subtract(bigInteger)).toByteArray(), (this.h + 7) / 8);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0014, code lost:
    
        r2 = a(r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    java.security.MessageDigest b(byte r2) {
        /*
            r1 = this;
            boolean r0 = r1.m
            if (r0 != 0) goto L27
            r0 = 51
            if (r2 != r0) goto L27
            byte[] r0 = r1.i
            if (r0 == 0) goto L27
            int r0 = r1.k
            if (r0 <= 0) goto L27
            java.security.MessageDigest r0 = r1.o
            if (r0 == 0) goto L27
            java.lang.String r2 = a(r2)
            if (r2 == 0) goto L27
            java.lang.String r0 = "IAIK"
            java.security.MessageDigest r2 = java.security.MessageDigest.getInstance(r2, r0)     // Catch: java.lang.Exception -> L21
            goto L28
        L21:
            java.security.MessageDigest r2 = java.security.MessageDigest.getInstance(r2)     // Catch: java.lang.Exception -> L27
            r1.hashEngine_ = r2     // Catch: java.lang.Exception -> L27
        L27:
            r2 = 0
        L28:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.iso.iso9796.ISO9796P2Signature.b(byte):java.security.MessageDigest");
    }

    void b() {
        if (this.h == 0) {
            throw new IllegalStateException("Engine not initialized. Missing modulus length!");
        }
        if (this.d <= 0) {
            MessageDigest messageDigest = this.hashEngine_;
            if (messageDigest == null) {
                messageDigest = this.o;
            }
            this.d = Util.getDigestLength(messageDigest.getAlgorithm());
        }
        if (this.g == 0) {
            this.g = a();
        }
        this.i = new byte[this.g / 8];
    }

    byte[] b(byte[] bArr) throws SignatureException {
        boolean z;
        byte[] bArrOpenSignature = openSignature(bArr);
        BigInteger bigInteger = new BigInteger(1, bArrOpenSignature);
        BigInteger bigInteger2 = b;
        BigInteger bigIntegerMod = bigInteger.mod(bigInteger2);
        BigInteger bigInteger3 = a;
        if (bigIntegerMod.compareTo(bigInteger3) != 0) {
            if (this.n) {
                z = false;
            } else {
                if (this.q == null) {
                    this.q = this.p.subtract(bigInteger3).mod(bigInteger2);
                }
                if (bigIntegerMod.compareTo(this.q) != 0) {
                    CryptoUtils.zeroBlock(bArrOpenSignature);
                    throw new SignatureException("Signature out of range!");
                }
                BigInteger bigIntegerSubtract = this.p.subtract(bigInteger);
                z = bigIntegerSubtract.mod(bigInteger2).compareTo(bigInteger3) == 0;
                bArrOpenSignature = a(bigIntegerSubtract.toByteArray(), (this.h + 7) / 8);
            }
            if (!z && (bArrOpenSignature[0] & 128) != 0) {
                CryptoUtils.zeroBlock(bArrOpenSignature);
                throw new SignatureException("Signature out of range!");
            }
        }
        return bArrOpenSignature;
    }

    protected boolean checkHashEngineName(String str) {
        MessageDigest messageDigest;
        if (str == null) {
            return true;
        }
        MessageDigest messageDigest2 = this.hashEngine_;
        boolean zEqualsIgnoreCase = messageDigest2 != null ? str.equalsIgnoreCase(messageDigest2.getAlgorithm()) : true;
        return (zEqualsIgnoreCase || (messageDigest = this.o) == null) ? zEqualsIgnoreCase : str.equalsIgnoreCase(messageDigest.getAlgorithm());
    }

    @Override // java.security.SignatureSpi
    protected Object engineGetParameter(String str) throws InvalidParameterException {
        return engineGetParameters();
    }

    @Override // java.security.SignatureSpi
    protected AlgorithmParameters engineGetParameters() {
        if (this.j == null) {
            return null;
        }
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("ISO9796-2-RM", "IAIK");
            try {
                algorithmParameters.init(this.j);
                return algorithmParameters;
            } catch (Exception unused) {
                return algorithmParameters;
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) throws InvalidParameterException {
        this.j = null;
        try {
            if (!(obj instanceof AlgorithmParameterSpec)) {
                throw new InvalidParameterException("value must be an instance of AlgorithmParameterSpec.");
            }
            engineSetParameter((AlgorithmParameterSpec) obj);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof ISO9796P2ParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Parameters must be a ISO9796P2ParameterSpec!");
        }
        this.j = null;
        ISO9796P2ParameterSpec iSO9796P2ParameterSpec = (ISO9796P2ParameterSpec) algorithmParameterSpec;
        MessageDigest hashEngine = iSO9796P2ParameterSpec.getHashEngine();
        if (hashEngine != null) {
            if (this.m) {
                if (!checkHashEngineName(hashEngine.getAlgorithm())) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid hash engine parameter (");
                    stringBuffer.append(hashEngine.getAlgorithm());
                    stringBuffer.append("). Expected ");
                    stringBuffer.append(this.hashEngine_.getAlgorithm());
                    stringBuffer.append(".");
                    throw new InvalidAlgorithmParameterException(stringBuffer.toString());
                }
                if (this.d != iSO9796P2ParameterSpec.getHashLen()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Invalid hash length parameter (");
                    stringBuffer2.append(iSO9796P2ParameterSpec.getHashLen());
                    stringBuffer2.append("). Expected ");
                    stringBuffer2.append(this.d);
                    stringBuffer2.append(".");
                    throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
                }
            }
            this.hashEngine_ = hashEngine;
            this.d = iSO9796P2ParameterSpec.getHashLen();
        }
        if (iSO9796P2ParameterSpec.getHashID() > -1) {
            byte hashID = (byte) iSO9796P2ParameterSpec.getHashID();
            if (this.m && this.e != hashID) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid hash id parameter (");
                stringBuffer3.append(Util.toString(hashID));
                stringBuffer3.append("). Expected ");
                stringBuffer3.append(Util.toString(this.e));
                stringBuffer3.append(".");
                throw new InvalidAlgorithmParameterException(stringBuffer3.toString());
            }
            this.e = hashID;
        }
        this.f = iSO9796P2ParameterSpec.getUseExplicitTrailer();
        this.n = iSO9796P2ParameterSpec.getUseAlternativeSignatureFunction();
        setSecureRandom(iSO9796P2ParameterSpec.getSecureRandom());
        if (algorithmParameterSpec instanceof RawISO9796P2ParameterSpec) {
            RawISO9796P2ParameterSpec rawISO9796P2ParameterSpec = (RawISO9796P2ParameterSpec) algorithmParameterSpec;
            a(rawISO9796P2ParameterSpec.getMr(), rawISO9796P2ParameterSpec.getMsgLen(), rawISO9796P2ParameterSpec.a());
        }
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        byte[] bArrDigest;
        if (this.hashEngine_ == null) {
            Objects.requireNonNull(this.o, "MessageDigest engine must not be null!");
        }
        if (this.i == null) {
            b();
        }
        if (this.h == 0) {
            throw new IllegalStateException("Length of public parameter not set");
        }
        this.j = null;
        MessageDigest messageDigest = this.o;
        if (messageDigest != null) {
            bArrDigest = messageDigest.digest();
            this.hashEngine_.reset();
        } else {
            bArrDigest = this.hashEngine_.digest();
        }
        int i = (this.h + 7) / 8;
        byte[] bArr = new byte[i];
        int i2 = i - 1;
        if (this.f) {
            bArr[i2] = -52;
            i2--;
            bArr[i2] = this.e;
        } else {
            bArr[i2] = -68;
        }
        int length = i2 - bArrDigest.length;
        if (length < 1) {
            throw new SignatureException("Modulus too short!");
        }
        System.arraycopy(bArrDigest, 0, bArr, length, bArrDigest.length);
        int i3 = this.l;
        int i4 = i3 * 8;
        int i5 = this.g;
        int i6 = (i5 - i4) % 8;
        if (i6 < 0) {
            i6 += 8;
        }
        int i7 = i5 - i6;
        if (i7 < i4) {
            i3 = i7 / 8;
            bArr[0] = 107;
        } else {
            bArr[0] = marshall_t.status_vpos_please_present_one_card_only;
        }
        if (i3 > 0) {
            byte[] bArr2 = this.i;
            if (i3 > bArr2.length) {
                CryptoUtils.zeroBlock(bArr);
                throw new SignatureException("Partial message to short.");
            }
            length -= i3;
            if (length < 1) {
                CryptoUtils.zeroBlock(bArr);
                throw new SignatureException("Modulus too short!");
            }
            System.arraycopy(bArr2, 0, bArr, length, i3);
        }
        int i8 = length - 1;
        for (int i9 = i8; i9 > 0; i9--) {
            bArr[i9] = -69;
        }
        bArr[i8] = (byte) (bArr[i8] ^ 1);
        byte[] bArrA = a(bArr);
        reset(null);
        CryptoUtils.zeroBlock(bArr);
        return bArrA;
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b2) {
        this.j = null;
        if (this.hashEngine_ == null) {
            Objects.requireNonNull(this.o, "MessageDigest engine must not be null!");
        }
        if (this.i == null) {
            b();
        }
        MessageDigest messageDigest = this.o;
        if (messageDigest != null) {
            messageDigest.update(b2);
            return;
        }
        this.hashEngine_.update(b2);
        int i = this.k;
        byte[] bArr = this.i;
        if (i < bArr.length) {
            this.k = i + 1;
            bArr[i] = b2;
        }
        this.l++;
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.j = null;
        if (this.hashEngine_ == null) {
            Objects.requireNonNull(this.o, "MessageDigest engine must not be null!");
        }
        if (this.i == null) {
            b();
        }
        MessageDigest messageDigest = this.o;
        if (messageDigest != null) {
            messageDigest.update(bArr, i, i2);
            return;
        }
        this.hashEngine_.update(bArr, i, i2);
        int i3 = this.k;
        byte[] bArr2 = this.i;
        if (i3 < bArr2.length) {
            int iMin = Math.min(bArr2.length - i3, i2);
            System.arraycopy(bArr, i, this.i, this.k, iMin);
            this.k += iMin;
        }
        this.l += i2;
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        boolean z;
        int i;
        byte[] bArrDigest;
        this.j = null;
        if (this.h == 0) {
            throw new IllegalStateException("Length of public parameter not set");
        }
        byte[] bArrB = b(bArr);
        if (bArrB.length * 8 != this.h) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid signature. Message representative not k bits long.");
        }
        byte b2 = (byte) (bArrB[0] & marshall_t.marshall_func_code_ev_ext_ev_info_req);
        if (b2 == 96) {
            z = false;
        } else {
            if (b2 != 64) {
                CryptoUtils.zeroBlock(bArrB);
                throw new SignatureException("Invalid signature. Leftmost bits are not 01.");
            }
            z = true;
        }
        if ((bArrB[bArrB.length - 1] & 15) != 12) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid signature. Final trailer nibble not 1100.");
        }
        int length = bArrB.length - 1;
        if (bArrB[length] == -52) {
            length--;
            if (bArrB[length] != this.e) {
                MessageDigest messageDigestB = b(bArrB[length]);
                if (messageDigestB == null) {
                    CryptoUtils.zeroBlock(bArrB);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid hash id. Expected ");
                    stringBuffer.append(Util.toString(this.e));
                    throw new SignatureException(stringBuffer.toString());
                }
                this.hashEngine_ = messageDigestB;
                this.d = Util.getDigestLength(messageDigestB.getAlgorithm());
            }
        } else if (bArrB[length] != -68) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid trailer byte. Expected 0xBC or 0xCC.");
        }
        MessageDigest messageDigest = this.hashEngine_;
        if (messageDigest == null && this.o == null) {
            CryptoUtils.zeroBlock(bArrB);
            throw new NullPointerException("MessageDigest engine must not be null!");
        }
        if (this.d <= 0) {
            if (messageDigest == null) {
                messageDigest = this.o;
            }
            this.d = Util.getDigestLength(messageDigest.getAlgorithm());
        }
        byte b3 = (byte) (bArrB[0] & 15);
        if (b3 == 10) {
            i = 1;
        } else {
            if (b3 != 11) {
                CryptoUtils.zeroBlock(bArrB);
                throw new SignatureException("Invalid padding. Must only contain zero bits");
            }
            if (!z) {
                CryptoUtils.zeroBlock(bArrB);
                throw new SignatureException("Too many padding bits for partial recovery.");
            }
            int i2 = 1;
            while (bArrB[i2] == -69 && i2 < length) {
                i2++;
            }
            if (length <= i2) {
                CryptoUtils.zeroBlock(bArrB);
                throw new SignatureException("Invalid padding.");
            }
            i = i2 + 1;
            if (bArrB[i2] != -70) {
                CryptoUtils.zeroBlock(bArrB);
                throw new SignatureException("Invalid padding. Wrong border nibble.");
            }
        }
        int i3 = length - this.d;
        if (i3 < i) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid padding.");
        }
        int i4 = i3 - i;
        int i5 = this.l;
        int i6 = i5 > 0 ? i5 - i4 : 0;
        byte[] bArr2 = this.i;
        if (bArr2 != null && !CryptoUtils.secureEqualsBlock(bArrB, i, bArr2, 0, i4)) {
            CryptoUtils.zeroBlock(bArrB);
            throw new SignatureException("Invalid message recovery.");
        }
        byte[] bArr3 = new byte[i4];
        this.j = bArr3;
        System.arraycopy(bArrB, i, bArr3, 0, i4);
        if (z) {
            if (i6 != 0) {
                CryptoUtils.zeroBlock(bArrB);
                CryptoUtils.zeroBlock(this.j);
                this.j = null;
                throw new SignatureException("Invalid padding. Mn must be empty for total recovery");
            }
            MessageDigest messageDigest2 = this.hashEngine_;
            if (messageDigest2 == null) {
                CryptoUtils.zeroBlock(bArrB);
                CryptoUtils.zeroBlock(this.j);
                throw new NullPointerException("MessageDigest engine must not be null!");
            }
            messageDigest2.reset();
            bArrDigest = this.hashEngine_.digest(this.j);
        } else {
            if (i6 < 1) {
                CryptoUtils.zeroBlock(bArrB);
                CryptoUtils.zeroBlock(this.j);
                this.j = null;
                throw new SignatureException("Invalid padding. Mn cannot be empty for partial recovery");
            }
            if (this.i == null) {
                CryptoUtils.zeroBlock(bArrB);
                CryptoUtils.zeroBlock(this.j);
                this.j = null;
                throw new NullPointerException("Recovered message part not initialized!");
            }
            MessageDigest messageDigest3 = this.o;
            if (messageDigest3 == null) {
                messageDigest3 = this.hashEngine_;
            }
            bArrDigest = messageDigest3.digest();
        }
        boolean zSecureEqualsBlock = CryptoUtils.secureEqualsBlock(bArrB, i3, bArrDigest, 0, this.d);
        CryptoUtils.zeroBlock(bArrB);
        if (!zSecureEqualsBlock) {
            CryptoUtils.zeroBlock(this.j);
            this.j = null;
        }
        reset(null);
        return zSecureEqualsBlock;
    }

    protected SecureRandom getSecureRandom() {
        if (this.secureRandom_ == null) {
            setSecureRandom(this.appRandom == null ? SecRandom.getDefault() : this.appRandom);
        }
        return this.secureRandom_;
    }

    protected abstract byte[] openSignature(byte[] bArr) throws SignatureException;

    protected abstract byte[] produceSignature(byte[] bArr) throws SignatureException;

    protected void reset(BigInteger bigInteger) {
        MessageDigest messageDigest = this.hashEngine_;
        if (messageDigest != null) {
            messageDigest.reset();
        }
        MessageDigest messageDigest2 = this.o;
        if (messageDigest2 != null) {
            messageDigest2.reset();
        }
        this.l = 0;
        this.k = 0;
        if (bigInteger == null) {
            byte[] bArr = this.i;
            if (bArr != null) {
                CryptoUtils.zeroBlock(bArr);
                return;
            }
            return;
        }
        this.i = null;
        this.g = 0;
        this.p = bigInteger;
        this.h = bigInteger.bitLength();
        this.q = null;
        this.j = null;
    }

    protected void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom_ = secureRandom;
    }
}

package iaik.security.ssl;

import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
abstract class y {
    CipherSuite A;
    String B;
    CompressionMethod C;
    SSLContext D;
    Cipher E;
    Cipher F;
    ad G;
    ad H;
    a I;
    a J;
    byte[] M;
    byte[] N;
    byte[] O;
    byte[] P;
    byte[] Q;
    ExtensionList R;
    boolean T;
    boolean U;
    boolean i;
    int m;
    SSLTransport n;
    TrustDecider o;
    Session p;
    ab q;
    ag r;
    ByteArrayOutputStream s;
    av t;
    MessageDigest u;
    MessageDigest v;
    byte[] w;
    boolean x;
    static final byte[] c = {109, 97, 115, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 32, 115, marshall_t.marshalll_display_control_button_id_menu, 99, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_RIGHT};
    static final byte[] d = {marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_ENTER, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, marshall_t.marshalll_display_control_button_id_menu, 110, marshall_t.marshalll_display_control_button_id_left_arrow, marshall_t.marshalll_display_control_button_id_menu, marshall_t.marshalll_display_control_button_id_left_arrow, 32, 109, 97, 115, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 32, 115, marshall_t.marshalll_display_control_button_id_menu, 99, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_RIGHT};
    private static final byte[] a = {107, marshall_t.marshalll_display_control_button_id_menu, 121, 32, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_ENTER, 112, 97, 110, 115, 105, 111, 110};
    private static final byte[] b = {99, 108, 105, marshall_t.marshalll_display_control_button_id_menu, 110, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, 32, 119, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 105, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, marshall_t.marshalll_display_control_button_id_menu, 32, 107, marshall_t.marshalll_display_control_button_id_menu, 121};
    private static final byte[] V = {115, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 118, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 32, 119, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 105, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, marshall_t.marshalll_display_control_button_id_menu, 32, 107, marshall_t.marshalll_display_control_button_id_menu, 121};
    private static final byte[] W = {73, 86, 32, 98, 108, 111, 99, 107};
    private static final byte[] X = {99, 108, 105, marshall_t.marshalll_display_control_button_id_menu, 110, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, 32, marshall_t.marshalll_display_control_button_id_right_arrow, 105, 110, 105, 115, marshall_t.marshalll_display_control_button_id_back, marshall_t.marshalll_display_control_button_id_menu, marshall_t.marshalll_display_control_button_id_left_arrow};
    private static final byte[] Y = {115, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 118, marshall_t.marshalll_display_control_button_id_menu, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 32, marshall_t.marshalll_display_control_button_id_right_arrow, 105, 110, 105, 115, marshall_t.marshalll_display_control_button_id_back, marshall_t.marshalll_display_control_button_id_menu, marshall_t.marshalll_display_control_button_id_left_arrow};
    static final byte[] e = a((byte) 54, 48);
    static final byte[] f = a((byte) 92, 48);
    static final byte[] g = a((byte) 54, 40);
    static final byte[] h = a((byte) 92, 40);
    int j = 1;
    int k = 1;
    int l = 0;
    boolean z = false;
    byte[] K = {67, 76, marshall_t.status_vpos_try_another_card, marshall_t.status_vpos_insert_or_swipe_another_card};
    byte[] L = {marshall_t.status_vpos_present_card_again, marshall_t.status_vpos_see_phone_for_instructions, 86, marshall_t.status_vpos_see_phone_for_instructions};
    boolean y = false;
    boolean S = false;

    abstract void a() throws IOException;

    abstract void a(boolean z) throws IOException;

    y(SSLTransport sSLTransport) {
        this.n = sSLTransport;
        this.x = true ^ sSLTransport.getUseClientMode();
        this.q = sSLTransport.b();
        this.r = sSLTransport.c();
        a(sSLTransport.getContext());
        try {
            av avVar = new av();
            this.t = avVar;
            this.q.a(avVar);
            this.r.a(this.t);
        } catch (Exception e2) {
            StringBuffer stringBuffer = new StringBuffer("No implementation for SHA or MD5: ");
            stringBuffer.append(e2.toString());
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    void a(SSLContext sSLContext) {
        this.D = sSLContext;
        this.o = sSLContext.getTrustDecider();
        this.R = sSLContext.i();
        this.U = sSLContext.h();
    }

    void a(int i) {
        this.l = i;
        this.r.d(i);
        this.q.a(i);
        this.t.a(i);
    }

    byte[] a(int i, String str) {
        byte[] bArrA;
        byte[] bArr;
        try {
            MessageDigest messageDigestA = this.t.a();
            MessageDigest messageDigestB = this.t.b();
            int i2 = this.l;
            if (i2 >= 769) {
                if (i2 < 771) {
                    byte[] bArrDigest = messageDigestB.digest();
                    if (i == 4 || i == 5) {
                        return bArrDigest;
                    }
                    bArrA = Utils.a(messageDigestA.digest(), bArrDigest);
                    if (i == 3 || i == 6) {
                        return bArrA;
                    }
                } else {
                    try {
                        bArrA = this.t.a(str);
                        if (i == 4 || i == 5 || i == 3 || i == 6) {
                            return bArrA;
                        }
                    } catch (NoSuchAlgorithmException e2) {
                        StringBuffer stringBuffer = new StringBuffer("Error calculating handshake hash: ");
                        stringBuffer.append(e2);
                        throw new RuntimeException(stringBuffer.toString());
                    }
                }
                byte[] bArr2 = bArrA;
                if (i == 1) {
                    bArr = Y;
                } else {
                    bArr = X;
                }
                return Utils.a(this.l, this.P, bArr, bArr2, 12, str);
            }
            if (i == 1) {
                messageDigestA.update(this.L);
                messageDigestB.update(this.L);
            } else if (i == 2) {
                messageDigestA.update(this.K);
                messageDigestB.update(this.K);
            }
            messageDigestA.update(this.P);
            messageDigestA.update(e);
            messageDigestB.update(this.P);
            messageDigestB.update(g);
            byte[] bArrDigest2 = messageDigestA.digest();
            messageDigestA.reset();
            messageDigestA.update(this.P);
            messageDigestA.update(f);
            messageDigestA.update(bArrDigest2);
            byte[] bArrDigest3 = messageDigestB.digest();
            messageDigestB.reset();
            messageDigestB.update(this.P);
            messageDigestB.update(h);
            messageDigestB.update(bArrDigest3);
            if (i == 4 || i == 5) {
                return messageDigestB.digest();
            }
            return Utils.a(messageDigestA.digest(), messageDigestB.digest());
        } catch (CloneNotSupportedException e3) {
            StringBuffer stringBuffer2 = new StringBuffer("MD5 or SHA does not support cloning: ");
            stringBuffer2.append(e3);
            throw new RuntimeException(stringBuffer2.toString());
        }
    }

    void a(x xVar) throws IOException {
        xVar.a(this.r);
        this.r.e();
    }

    void a(int i, boolean z, boolean z2) throws IOException {
        this.r.a(i, z, z2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x00fd, code lost:
    
        r3 = new java.lang.StringBuffer("Peer sent alert: ");
        r3.append(r0.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0112, code lost:
    
        throw new iaik.security.ssl.SSLException(r3.toString(), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x014a, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    int f() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 344
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.y.f():int");
    }

    void a(byte[] bArr) throws SSLException {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        try {
            if (this.p.e()) {
                this.P = securityProvider.generateExtendedMasterSecret(bArr, this.w, this.l, this.A.getPrfAlgorithm());
            } else {
                this.P = securityProvider.generateMasterSecret(bArr, this.M, this.O, this.l, this.A.getPrfAlgorithm());
            }
            this.p.f = this.P;
        } catch (Exception e2) {
            StringBuffer stringBuffer = new StringBuffer("Error generating master secret: ");
            stringBuffer.append(e2.toString());
            throw new SSLException(stringBuffer.toString());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v10, types: [javax.crypto.spec.IvParameterSpec] */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v19 */
    /* JADX WARN: Type inference failed for: r15v20 */
    /* JADX WARN: Type inference failed for: r15v21 */
    /* JADX WARN: Type inference failed for: r15v22 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r26v0, types: [java.security.spec.AlgorithmParameterSpec] */
    /* JADX WARN: Type inference failed for: r26v1, types: [java.security.spec.AlgorithmParameterSpec] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v18, types: [iaik.security.ssl.SecurityProvider] */
    /* JADX WARN: Type inference failed for: r5v30 */
    /* JADX WARN: Type inference failed for: r5v31 */
    /* JADX WARN: Type inference failed for: r5v33, types: [javax.crypto.spec.IvParameterSpec] */
    /* JADX WARN: Type inference failed for: r5v35 */
    /* JADX WARN: Type inference failed for: r5v46 */
    /* JADX WARN: Type inference failed for: r5v47 */
    /* JADX WARN: Type inference failed for: r5v48 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8, types: [iaik.security.ssl.a, javax.crypto.Cipher] */
    /* JADX WARN: Type inference failed for: r9v9 */
    void e() {
        byte[] bArrA;
        byte[] bArr;
        int i;
        byte[] bArr2;
        String str;
        byte[] bArr3;
        String str2;
        byte[] bArr4;
        int i2;
        ?? r9;
        ?? r15;
        ?? ivParameterSpec;
        SecretKeySpec secretKeySpec;
        SecretKeySpec secretKeySpec2;
        ?? r152;
        ?? r5;
        ?? r153;
        ?? ivParameterSpec2;
        byte[] bArr5;
        byte[] bArr6;
        String string;
        String str3 = "MAC";
        int hashSize = this.A.getHashSize();
        int keyLength = this.A.getKeyLength();
        int expandedKeyLength = this.A.getExpandedKeyLength();
        int iVSize = this.A.getIVSize();
        String prfAlgorithm = this.A.getPrfAlgorithm();
        boolean zC = this.A.c();
        int i3 = 0;
        int i4 = ((zC ? 0 : hashSize) + keyLength) * 2;
        int i5 = SSLContext.VERSION_TLS10;
        if (zC || (!this.A.isExportable() && this.l <= 769)) {
            i4 += iVSize * 2;
        }
        int i6 = i4;
        int i7 = this.l;
        String str4 = SecurityProvider.ALG_DIGEST_MD5;
        String str5 = "SHA";
        if (i7 >= 769) {
            bArrA = Utils.a(this.l, this.P, a, Utils.a(this.O, this.M), i6, prfAlgorithm);
        } else {
            if (this.u == null) {
                SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
                try {
                    this.v = securityProvider.getMessageDigest("SHA");
                    this.u = securityProvider.getMessageDigest(SecurityProvider.ALG_DIGEST_MD5);
                } catch (Exception e2) {
                    StringBuffer stringBuffer = new StringBuffer("No implementation for SHA or MD5: ");
                    stringBuffer.append(e2.toString());
                    throw new RuntimeException(stringBuffer.toString());
                }
            }
            int i8 = ((i6 - 1) / 16) + 1;
            byte[] bArr7 = new byte[i8 * 16];
            int i9 = 0;
            while (i9 < i8) {
                this.u.reset();
                this.v.reset();
                this.u.update(this.P);
                int i10 = i9 + 1;
                this.v.update(Utils.a(i10));
                this.v.update(this.P);
                this.v.update(this.O);
                this.v.update(this.M);
                this.u.update(this.v.digest());
                System.arraycopy(this.u.digest(), 0, bArr7, i9 * 16, 16);
                hashSize = hashSize;
                i9 = i10;
                i3 = 0;
                str5 = str5;
                str3 = str3;
                i5 = SSLContext.VERSION_TLS10;
            }
            bArrA = bArr7;
        }
        if (zC) {
            bArr = null;
            i = 0;
            bArr2 = null;
        } else {
            byte[] bArr8 = new byte[hashSize];
            System.arraycopy(bArrA, i3, bArr8, i3, hashSize);
            int i11 = hashSize + 0;
            byte[] bArr9 = new byte[hashSize];
            System.arraycopy(bArrA, i11, bArr9, i3, hashSize);
            i = i11 + hashSize;
            bArr = bArr8;
            bArr2 = bArr9;
        }
        byte[] bArr10 = new byte[keyLength];
        System.arraycopy(bArrA, i, bArr10, i3, keyLength);
        int i12 = i + keyLength;
        byte[] bArr11 = new byte[keyLength];
        System.arraycopy(bArrA, i12, bArr11, i3, keyLength);
        int i13 = i12 + keyLength;
        byte[] bArr12 = new byte[iVSize];
        byte[] bArr13 = new byte[iVSize];
        if (!this.A.isExportable()) {
            if (iVSize > 0 && (this.l <= i5 || zC)) {
                System.arraycopy(bArrA, i13, bArr12, 0, iVSize);
                System.arraycopy(bArrA, i13 + iVSize, bArr13, 0, iVSize);
            }
            str = str3;
            bArr3 = bArr;
            str2 = str5;
            bArr4 = bArr2;
            i2 = 0;
            r9 = 0;
        } else if (this.l >= i5) {
            byte[] bArrA2 = Utils.a(this.M, this.O);
            str = str3;
            str2 = str5;
            bArr3 = bArr;
            i2 = 0;
            byte[] bArrA3 = Utils.a(this.l, bArr10, b, bArrA2, expandedKeyLength, prfAlgorithm);
            byte[] bArrA4 = Utils.a(this.l, bArr11, V, bArrA2, expandedKeyLength, prfAlgorithm);
            str4 = SecurityProvider.ALG_DIGEST_MD5;
            bArr4 = bArr2;
            bArr13 = bArr13;
            r9 = 0;
            byte[] bArrA5 = Utils.a(this.l, new byte[0], W, bArrA2, iVSize * 2, prfAlgorithm);
            System.arraycopy(bArrA5, 0, bArr12, 0, iVSize);
            System.arraycopy(bArrA5, iVSize, bArr13, 0, iVSize);
            bArr11 = bArrA4;
            bArr10 = bArrA3;
        } else {
            str = str3;
            bArr3 = bArr;
            str2 = str5;
            bArr4 = bArr2;
            i2 = 0;
            r9 = 0;
            r9 = 0;
            this.u.reset();
            this.u.update(bArr10);
            this.u.update(this.M);
            this.u.update(this.O);
            bArr10 = new byte[expandedKeyLength];
            System.arraycopy(this.u.digest(), 0, bArr10, 0, expandedKeyLength);
            this.u.reset();
            this.u.update(bArr11);
            this.u.update(this.O);
            this.u.update(this.M);
            bArr11 = new byte[expandedKeyLength];
            System.arraycopy(this.u.digest(), 0, bArr11, 0, expandedKeyLength);
            if (iVSize > 0) {
                this.u.reset();
                this.u.update(this.M);
                this.u.update(this.O);
                System.arraycopy(this.u.digest(), 0, bArr12, 0, iVSize);
                this.u.reset();
                this.u.update(this.O);
                this.u.update(this.M);
                System.arraycopy(this.u.digest(), 0, bArr13, 0, iVSize);
            }
        }
        try {
            ?? securityProvider2 = SecurityProvider.getSecurityProvider();
            if (!zC) {
                String macAlgorithm = this.A.getMacAlgorithm();
                if (this.l >= 769) {
                    if (macAlgorithm.equals(str2)) {
                        string = "HmacSHA1";
                    } else {
                        StringBuffer stringBuffer2 = new StringBuffer("Hmac");
                        stringBuffer2.append(macAlgorithm);
                        string = stringBuffer2.toString();
                    }
                    String str6 = str;
                    SecretKeySpec secretKeySpec3 = new SecretKeySpec(bArr3, str6);
                    SecretKeySpec secretKeySpec4 = new SecretKeySpec(bArr4, str6);
                    if (this.x) {
                        this.G = new ad(securityProvider2.getMac(string, secretKeySpec3));
                        this.H = new ad(securityProvider2.getMac(string, secretKeySpec4));
                    } else {
                        this.G = new ad(securityProvider2.getMac(string, secretKeySpec4));
                        this.H = new ad(securityProvider2.getMac(string, secretKeySpec3));
                    }
                    if (this.p.d()) {
                        this.G.a(10);
                        this.H.a(10);
                    }
                } else {
                    byte[] bArr14 = bArr3;
                    if (macAlgorithm.equals(str4)) {
                        bArr5 = e;
                        bArr6 = f;
                    } else if (macAlgorithm.equals(str2)) {
                        str4 = str2;
                        bArr5 = g;
                        bArr6 = h;
                    } else {
                        StringBuffer stringBuffer3 = new StringBuffer("Unsupported SSL MAC algorithm: ");
                        stringBuffer3.append(macAlgorithm);
                        throw new RuntimeException(stringBuffer3.toString());
                    }
                    this.G = new ad(securityProvider2.getMessageDigest(str4), bArr5, bArr6);
                    this.H = new ad(securityProvider2.getMessageDigest(str4), bArr5, bArr6);
                    if (this.x) {
                        this.G.a(bArr14);
                        this.H.a(bArr4);
                    } else {
                        this.G.a(bArr4);
                        this.H.a(bArr14);
                    }
                }
            }
            String cipherAlgorithm = this.A.getCipherAlgorithm();
            if (!cipherAlgorithm.equals("NULL")) {
                String strSubstring = cipherAlgorithm.substring(i2, cipherAlgorithm.indexOf(47));
                if (this.x) {
                    if (iVSize > 0) {
                        IvParameterSpec ivParameterSpec3 = new IvParameterSpec(bArr12);
                        ivParameterSpec2 = new IvParameterSpec(bArr13);
                        r153 = ivParameterSpec3;
                    } else {
                        ?? r3 = r9;
                        r153 = r3;
                        ivParameterSpec2 = r3;
                    }
                    SecretKeySpec secretKeySpec5 = new SecretKeySpec(bArr10, strSubstring);
                    secretKeySpec2 = new SecretKeySpec(bArr11, strSubstring);
                    r5 = ivParameterSpec2;
                    secretKeySpec = secretKeySpec5;
                    r152 = r153;
                } else {
                    if (iVSize > 0) {
                        IvParameterSpec ivParameterSpec4 = new IvParameterSpec(bArr13);
                        ivParameterSpec = new IvParameterSpec(bArr12);
                        r15 = ivParameterSpec4;
                    } else {
                        ?? r52 = r9;
                        r15 = r52;
                        ivParameterSpec = r52;
                    }
                    secretKeySpec = new SecretKeySpec(bArr11, strSubstring);
                    secretKeySpec2 = new SecretKeySpec(bArr10, strSubstring);
                    r5 = ivParameterSpec;
                    r152 = r15;
                }
                if (zC) {
                    Cipher cipher = securityProvider2.getCipher(cipherAlgorithm, 0, null, null, null);
                    Cipher cipher2 = securityProvider2.getCipher(cipherAlgorithm, 0, null, null, null);
                    this.I = new a(cipher, secretKeySpec, r152.getIV());
                    this.J = new a(cipher2, secretKeySpec2, r5.getIV());
                    return;
                }
                this.E = securityProvider2.getCipher(cipherAlgorithm, 2, secretKeySpec, r152, null);
                this.F = securityProvider2.getCipher(cipherAlgorithm, 1, secretKeySpec2, r5, null);
                return;
            }
            this.E = r9;
            this.F = r9;
            this.I = r9;
            this.J = r9;
        } catch (Exception e3) {
            StringBuffer stringBuffer4 = new StringBuffer("Unable to create cipher ");
            stringBuffer4.append(this.A.getCipherAlgorithm());
            stringBuffer4.append(": ");
            stringBuffer4.append(e3);
            throw new RuntimeException(stringBuffer4.toString());
        }
    }

    private static byte[] a(byte b2, int i) {
        byte[] bArr = new byte[i];
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return bArr;
            }
            bArr[i2] = b2;
            i = i2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void d() throws java.io.IOException {
        /*
            r6 = this;
            iaik.security.ssl.SSLTransport r0 = r6.n
            java.io.PrintWriter r0 = r0.b
            r1 = 0
            r2 = 1
            if (r0 != 0) goto La
            r0 = 0
            goto Lb
        La:
            r0 = 1
        Lb:
            r6.T = r0
            iaik.security.ssl.av r0 = r6.t
            r0.reset()
            r6.a()
            r6.j = r2
            r6.k = r2
            iaik.security.ssl.Session r0 = r6.p
            r0.h = r2
            java.lang.String r0 = r6.B
            java.lang.String r3 = "NULL"
            boolean r0 = r0.equals(r3)
            if (r0 != 0) goto L94
            iaik.security.ssl.SSLContext r0 = r6.D
            iaik.security.ssl.SessionManager r0 = r0.getSessionManager()
            if (r0 == 0) goto L94
            iaik.security.ssl.Session r3 = r6.p
            boolean r4 = r3 instanceof iaik.security.ssl.TicketSession
            java.lang.String r5 = "Session not cached (empty session id)."
            if (r4 == 0) goto L6b
            iaik.security.ssl.TicketSession r3 = (iaik.security.ssl.TicketSession) r3
            byte[] r3 = r3.f()
            iaik.security.ssl.SSLTransport r4 = r6.n
            boolean r4 = r4.getUseClientMode()
            if (r4 == 0) goto L5d
            iaik.security.ssl.Session r4 = r6.p
            iaik.security.ssl.SessionID r4 = r4.c()
            boolean r4 = r4.b()
            if (r4 == 0) goto L7f
            if (r3 != 0) goto L7f
            boolean r2 = r6.T
            if (r2 == 0) goto L80
            iaik.security.ssl.SSLTransport r2 = r6.n
            r2.a(r5)
            goto L80
        L5d:
            if (r3 == 0) goto L7f
            boolean r2 = r6.T
            if (r2 == 0) goto L80
            iaik.security.ssl.SSLTransport r2 = r6.n
            java.lang.String r3 = "Session not cached (using session tickets)."
            r2.a(r3)
            goto L80
        L6b:
            iaik.security.ssl.SessionID r3 = r3.c()
            boolean r3 = r3.b()
            if (r3 == 0) goto L7f
            boolean r2 = r6.T
            if (r2 == 0) goto L80
            iaik.security.ssl.SSLTransport r2 = r6.n
            r2.a(r5)
            goto L80
        L7f:
            r1 = 1
        L80:
            if (r1 == 0) goto L94
            iaik.security.ssl.SSLTransport r1 = r6.n
            iaik.security.ssl.Session r2 = r6.p
            r0.cacheSession(r1, r2)
            boolean r0 = r6.T
            if (r0 == 0) goto L94
            iaik.security.ssl.SSLTransport r0 = r6.n
            java.lang.String r1 = "Session added to session cache."
            r0.a(r1)
        L94:
            iaik.security.ssl.SSLTransport r0 = r6.n
            iaik.security.ssl.Session r1 = r6.p
            r0.k = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.y.d():void");
    }

    void b(boolean z) throws IOException {
        a(this.D);
        this.T = this.n.b != null;
        this.z = true;
        this.r.flush();
        this.s = new ByteArrayOutputStream();
        int iAvailable = this.q.available();
        if ((!this.x || !z) && iAvailable > 0) {
            byte[] bArr = new byte[iAvailable];
            this.q.read(bArr);
            this.s.write(bArr);
        }
        a(z);
        this.z = false;
        if (this.s.size() > 0) {
            this.q.b(this.s.toByteArray());
            this.s = null;
        }
    }

    protected void a(X509Certificate[] x509CertificateArr, int i, byte[] bArr, byte[] bArr2) throws SSLCertificateException {
        TrustDecider trustDecider = this.o;
        if (trustDecider != null) {
            if (!trustDecider.isTrustedPeer(new SSLCertificate(x509CertificateArr))) {
                throw new SSLCertificateException("Peer certificate rejected by TrustDecider");
            }
            return;
        }
        ChainVerifier chainVerifier = this.D.getChainVerifier();
        if (chainVerifier != null) {
            try {
                if (chainVerifier.verifyChain(x509CertificateArr, this.n, i, bArr, bArr2)) {
                } else {
                    throw new SSLCertificateException("Peer certificate rejected by ChainVerifier");
                }
            } catch (SSLCertificateRuntimeException e2) {
                Exception wrappedException = e2.getWrappedException();
                if (wrappedException != null) {
                    if (wrappedException instanceof SSLCertificateException) {
                        throw ((SSLCertificateException) wrappedException);
                    }
                    throw new SSLCertificateException(wrappedException);
                }
                String message = e2.getMessage();
                if (message == null) {
                    throw new SSLCertificateException("Peer certificate rejected by ChainVerifier!");
                }
                StringBuffer stringBuffer = new StringBuffer("Peer certificate rejected by ChainVerifier: ");
                stringBuffer.append(message);
                throw new SSLCertificateException(stringBuffer.toString());
            }
        }
    }
}

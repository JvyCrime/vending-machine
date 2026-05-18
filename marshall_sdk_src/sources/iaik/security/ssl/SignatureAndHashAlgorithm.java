package iaik.security.ssl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SignatureAndHashAlgorithm implements Cloneable {
    public static final SignatureAndHashAlgorithm MD5withRSA;
    public static final SignatureAndHashAlgorithm SHA1withDSA;
    public static final SignatureAndHashAlgorithm SHA1withECDSA;
    public static final SignatureAndHashAlgorithm SHA1withRSA;
    public static final SignatureAndHashAlgorithm SHA224withDSA;
    public static final SignatureAndHashAlgorithm SHA224withECDSA;
    public static final SignatureAndHashAlgorithm SHA224withRSA;
    public static final SignatureAndHashAlgorithm SHA256withDSA;
    public static final SignatureAndHashAlgorithm SHA256withECDSA;
    public static final SignatureAndHashAlgorithm SHA256withRSA;
    public static final SignatureAndHashAlgorithm SHA384withDSA;
    public static final SignatureAndHashAlgorithm SHA384withECDSA;
    public static final SignatureAndHashAlgorithm SHA384withRSA;
    public static final SignatureAndHashAlgorithm SHA512withDSA;
    public static final SignatureAndHashAlgorithm SHA512withECDSA;
    public static final SignatureAndHashAlgorithm SHA512withRSA;
    static final List a;
    static final List b;
    private static final HashMap c = new HashMap(16);
    private static final HashMap d = new HashMap(16);
    private static List e;
    private static List f;
    private a g;
    private String h;
    private String i;
    private String j;

    static {
        SignatureAndHashAlgorithm signatureAndHashAlgorithm = new SignatureAndHashAlgorithm(1, 1, SecurityProvider.ALG_SIGNATURE_MD5RSA);
        MD5withRSA = signatureAndHashAlgorithm;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm2 = new SignatureAndHashAlgorithm(2, 1, SecurityProvider.ALG_SIGNATURE_SHA1RSA);
        SHA1withRSA = signatureAndHashAlgorithm2;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm3 = new SignatureAndHashAlgorithm(3, 1, SecurityProvider.ALG_SIGNATURE_SHA224RSA);
        SHA224withRSA = signatureAndHashAlgorithm3;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm4 = new SignatureAndHashAlgorithm(4, 1, SecurityProvider.ALG_SIGNATURE_SHA256RSA);
        SHA256withRSA = signatureAndHashAlgorithm4;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm5 = new SignatureAndHashAlgorithm(5, 1, SecurityProvider.ALG_SIGNATURE_SHA384RSA);
        SHA384withRSA = signatureAndHashAlgorithm5;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm6 = new SignatureAndHashAlgorithm(6, 1, SecurityProvider.ALG_SIGNATURE_SHA512RSA);
        SHA512withRSA = signatureAndHashAlgorithm6;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm7 = new SignatureAndHashAlgorithm(2, 2, SecurityProvider.ALG_SIGNATURE_SHADSA);
        SHA1withDSA = signatureAndHashAlgorithm7;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm8 = new SignatureAndHashAlgorithm(3, 2, "SHA224withDSA");
        SHA224withDSA = signatureAndHashAlgorithm8;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm9 = new SignatureAndHashAlgorithm(4, 2, "SHA256withDSA");
        SHA256withDSA = signatureAndHashAlgorithm9;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm10 = new SignatureAndHashAlgorithm(5, 2, "SHA384withDSA");
        SHA384withDSA = signatureAndHashAlgorithm10;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm11 = new SignatureAndHashAlgorithm(6, 2, "SHA512withDSA");
        SHA512withDSA = signatureAndHashAlgorithm11;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm12 = new SignatureAndHashAlgorithm(2, 3, "SHA1withECDSA");
        SHA1withECDSA = signatureAndHashAlgorithm12;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm13 = new SignatureAndHashAlgorithm(3, 3, SecurityProvider.ALG_SIGNATURE_SHA224ECDSA);
        SHA224withECDSA = signatureAndHashAlgorithm13;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm14 = new SignatureAndHashAlgorithm(4, 3, SecurityProvider.ALG_SIGNATURE_SHA256ECDSA);
        SHA256withECDSA = signatureAndHashAlgorithm14;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm15 = new SignatureAndHashAlgorithm(5, 3, SecurityProvider.ALG_SIGNATURE_SHA384ECDSA);
        SHA384withECDSA = signatureAndHashAlgorithm15;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm16 = new SignatureAndHashAlgorithm(6, 3, SecurityProvider.ALG_SIGNATURE_SHA512ECDSA);
        SHA512withECDSA = signatureAndHashAlgorithm16;
        e = new ArrayList(16);
        f = new ArrayList(12);
        e.add(signatureAndHashAlgorithm4);
        e.add(signatureAndHashAlgorithm14);
        e.add(signatureAndHashAlgorithm9);
        e.add(signatureAndHashAlgorithm6);
        e.add(signatureAndHashAlgorithm16);
        e.add(signatureAndHashAlgorithm11);
        e.add(signatureAndHashAlgorithm5);
        e.add(signatureAndHashAlgorithm15);
        e.add(signatureAndHashAlgorithm10);
        e.add(signatureAndHashAlgorithm3);
        e.add(signatureAndHashAlgorithm13);
        e.add(signatureAndHashAlgorithm8);
        e.add(signatureAndHashAlgorithm2);
        e.add(signatureAndHashAlgorithm12);
        e.add(signatureAndHashAlgorithm7);
        e.add(signatureAndHashAlgorithm);
        f.add(signatureAndHashAlgorithm4);
        f.add(signatureAndHashAlgorithm14);
        f.add(signatureAndHashAlgorithm9);
        f.add(signatureAndHashAlgorithm5);
        f.add(signatureAndHashAlgorithm15);
        f.add(signatureAndHashAlgorithm10);
        f.add(signatureAndHashAlgorithm6);
        f.add(signatureAndHashAlgorithm16);
        f.add(signatureAndHashAlgorithm11);
        f.add(signatureAndHashAlgorithm2);
        f.add(signatureAndHashAlgorithm12);
        f.add(signatureAndHashAlgorithm7);
        a = Collections.unmodifiableList(e);
        b = Collections.unmodifiableList(f);
    }

    static final class a implements Cloneable {
        private int a;
        private String b;

        public a(int i, String str) {
            if (i < 0 || i > 65535) {
                StringBuffer stringBuffer = new StringBuffer("SigAndHashAlgId ");
                stringBuffer.append(i);
                stringBuffer.append(" out of range. Must be between 0 and 65535!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            this.a = i;
            this.b = str;
        }

        public a(int i, int i2, String str) {
            if (i < 0 || i > 255) {
                StringBuffer stringBuffer = new StringBuffer("Hash Algorithm Id ");
                stringBuffer.append(i);
                stringBuffer.append(" out of range. Must be between 0 and 255!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            if (i2 < 0 || i2 > 255) {
                StringBuffer stringBuffer2 = new StringBuffer("Signature Algorithm Id ");
                stringBuffer2.append(i2);
                stringBuffer2.append(" out of range. Must be between 0 and 255!");
                throw new IllegalArgumentException(stringBuffer2.toString());
            }
            int i3 = ((i & 255) << 8) | (i2 & 255);
            this.a = i3;
            if (i3 < 0 || i3 > 65535) {
                StringBuffer stringBuffer3 = new StringBuffer("SigAndHashAlgId ");
                stringBuffer3.append(this.a);
                stringBuffer3.append(" out of range. Must be between 0 and 65535!");
                throw new IllegalArgumentException(stringBuffer3.toString());
            }
            this.b = str;
        }

        a(ab abVar) throws IOException {
            a(abVar);
        }

        public int a() {
            return this.a;
        }

        public String b() {
            if (this.b == null) {
                this.b = "UNKNOWNwithUNKNOWN";
            }
            return this.b;
        }

        void c() {
            String str;
            if (this.b == null) {
                int i = this.a;
                int i2 = (i >> 8) & 255;
                int i3 = i & 255;
                String str2 = "UNKNWON";
                switch (i2) {
                    case 0:
                        str = "NONE";
                        break;
                    case 1:
                        str = SecurityProvider.ALG_DIGEST_MD5;
                        break;
                    case 2:
                        str = "SHA1";
                        break;
                    case 3:
                        str = SecurityProvider.ALG_DIGEST_SHA224;
                        break;
                    case 4:
                        str = SecurityProvider.ALG_DIGEST_SHA256;
                        break;
                    case 5:
                        str = SecurityProvider.ALG_DIGEST_SHA384;
                        break;
                    case 6:
                        str = SecurityProvider.ALG_DIGEST_SHA512;
                        break;
                    default:
                        str = "UNKNWON";
                        break;
                }
                if (i3 == 0) {
                    str2 = "ANONYMOUS";
                } else if (i3 == 1) {
                    str2 = "RSA";
                } else if (i3 == 2) {
                    str2 = "DSA";
                } else if (i3 == 3) {
                    str2 = "ECDSA";
                }
                StringBuffer stringBuffer = new StringBuffer(str);
                stringBuffer.append("with");
                stringBuffer.append(str2);
                this.b = stringBuffer.toString();
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && this.a == ((a) obj).a;
        }

        public int hashCode() {
            return this.a;
        }

        public Object clone() {
            try {
                a aVar = (a) super.clone();
                aVar.a = this.a;
                String str = this.b;
                if (str != null) {
                    aVar.b = str;
                }
                return aVar;
            } catch (CloneNotSupportedException unused) {
                return null;
            }
        }

        public String toString() {
            return a(false);
        }

        public String a(boolean z) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(b());
            if (z) {
                stringBuffer.append(" (0x");
                stringBuffer.append(Utils.toString((this.a >> 8) & 255));
                stringBuffer.append(Utils.toString(this.a & 255));
                stringBuffer.append(")");
            }
            return stringBuffer.toString();
        }

        void a(ab abVar) throws IOException {
            this.a = abVar.f();
        }
    }

    static final void a(a aVar, SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        c.put(aVar, signatureAndHashAlgorithm);
        d.put(aVar.b(), signatureAndHashAlgorithm);
    }

    static final SignatureAndHashAlgorithm a(a aVar) {
        SignatureAndHashAlgorithm signatureAndHashAlgorithm = (SignatureAndHashAlgorithm) c.get(aVar);
        if (signatureAndHashAlgorithm != null) {
            return signatureAndHashAlgorithm;
        }
        aVar.c();
        return new SignatureAndHashAlgorithm(aVar);
    }

    static final SignatureAndHashAlgorithm a(String str) {
        return (SignatureAndHashAlgorithm) d.get(str);
    }

    static final SignatureAndHashAlgorithm a(ab abVar) throws IOException {
        return a(new a(abVar));
    }

    static final ArrayList a(ab abVar, int i) throws IOException {
        int iF = abVar.f();
        if (i != -1 && iF != i - 2) {
            throw new SSLException("Invalid length of SignatureAlgorithms extension!", 2, 50, false);
        }
        if (iF < 2) {
            StringBuffer stringBuffer = new StringBuffer("Invalid length (");
            stringBuffer.append(iF);
            stringBuffer.append(") of supported algorithms list length (too short)");
            throw new SSLException(stringBuffer.toString(), 2, 50, false);
        }
        int i2 = iF / 2;
        if (iF % 2 != 0) {
            StringBuffer stringBuffer2 = new StringBuffer("Invalid length (");
            stringBuffer2.append(iF);
            stringBuffer2.append(") of supported algorithms list length (not even)");
            throw new SSLException(stringBuffer2.toString(), 2, 50, false);
        }
        ArrayList arrayList = new ArrayList(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(a(abVar));
            iF -= 2;
        }
        if (iF == 0) {
            return arrayList;
        }
        throw new SSLException("Supported signature algorithms size does not match to length field!", 2, 50, false);
    }

    static final void a(List list, int i, ak akVar) throws IOException {
        akVar.a(i);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((SignatureAndHashAlgorithm) it.next()).a(akVar);
        }
    }

    SignatureAndHashAlgorithm(a aVar) {
        b(aVar.b());
        this.g = aVar;
    }

    public SignatureAndHashAlgorithm(int i, int i2, String str) {
        b(str);
        a aVar = new a(i, i2, str);
        this.g = aVar;
        a(aVar, this);
    }

    public SignatureAndHashAlgorithm(int i, String str) {
        b(str);
        a aVar = new a(i, str);
        this.g = aVar;
        a(aVar, this);
    }

    private void b(String str) {
        Objects.requireNonNull(str, "algorithm name must not be null!");
        String upperCase = str.toUpperCase(Locale.US);
        int iIndexOf = upperCase.indexOf("WITH");
        if (iIndexOf < 1) {
            throw new IllegalArgumentException("Algorithm name must have format <HASH ALG>with<SIG ALG>");
        }
        this.h = upperCase.substring(0, iIndexOf);
        this.i = upperCase.substring(iIndexOf + 4);
        StringBuffer stringBuffer = new StringBuffer("Raw");
        stringBuffer.append(this.i);
        this.j = stringBuffer.toString();
    }

    public int getId() {
        return this.g.a();
    }

    public String getName() {
        return this.g.b();
    }

    String a() {
        return this.h;
    }

    boolean b() {
        return SecurityProvider.a(getName(), SecurityProvider.e, null) && SecurityProvider.a(this.j, SecurityProvider.e, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SignatureAndHashAlgorithm) {
            return this.g.equals(((SignatureAndHashAlgorithm) obj).g);
        }
        return false;
    }

    public int hashCode() {
        return this.g.hashCode();
    }

    public Object clone() {
        try {
            SignatureAndHashAlgorithm signatureAndHashAlgorithm = (SignatureAndHashAlgorithm) super.clone();
            signatureAndHashAlgorithm.g = (a) this.g.clone();
            signatureAndHashAlgorithm.h = this.h;
            signatureAndHashAlgorithm.i = this.i;
            signatureAndHashAlgorithm.j = this.j;
            return signatureAndHashAlgorithm;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        return this.g.a(z);
    }

    void a(ak akVar) throws IOException {
        akVar.a(this.g.a());
    }
}

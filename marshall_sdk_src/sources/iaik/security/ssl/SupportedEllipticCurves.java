package iaik.security.ssl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class SupportedEllipticCurves extends Extension implements Cloneable {
    private NamedCurve[] e;
    private boolean f;
    private boolean g;
    public static final ExtensionType TYPE = new ExtensionType(10, "elliptic_curves");
    static final HashMap b = new HashMap(30);
    static final HashMap c = new HashMap(30);
    static final HashMap d = new HashMap(30);
    public static final NamedCurve NC_CHAR2_SECT163K1 = new NamedCurve("sect163k1", "1.3.132.0.1", 1, true, true);
    public static final NamedCurve NC_CHAR2_SECT163R1 = new NamedCurve("sect163r1", "1.3.132.0.2", 2, true, true);
    public static final NamedCurve NC_CHAR2_SECT163R2 = new NamedCurve("sect163r2", "1.3.132.0.15", 3, true, true);
    public static final NamedCurve NC_CHAR2_SECT193R1 = new NamedCurve("sect193r1", "1.3.132.0.24", 4, true, true);
    public static final NamedCurve NC_CHAR2_SECT193R2 = new NamedCurve("sect193r2", "1.3.132.0.25", 5, true, true);
    public static final NamedCurve NC_CHAR2_SECT233K1 = new NamedCurve("sect233k1", "1.3.132.0.26", 6, true, true);
    public static final NamedCurve NC_CHAR2_SECT233R1 = new NamedCurve("sect233r1", "1.3.132.0.27", 7, true, true);
    public static final NamedCurve NC_CHAR2_SECT239K1 = new NamedCurve("sect239k1", "1.3.132.0.3", 8, true, true);
    public static final NamedCurve NC_CHAR2_SECT283K1 = new NamedCurve(IaikEccProvider.EC_DEFAULT_BINARY, "1.3.132.0.16", 9, true, true);
    public static final NamedCurve NC_CHAR2_SECT283R1 = new NamedCurve("sect283r1", "1.3.132.0.17", 10, true, true);
    public static final NamedCurve NC_CHAR2_SECT409K1 = new NamedCurve("sect409k1", "1.3.132.0.36", 11, true, true);
    public static final NamedCurve NC_CHAR2_SECT409R1 = new NamedCurve("sect409r1", "1.3.132.0.37", 12, true, true);
    public static final NamedCurve NC_CHAR2_SECT571K1 = new NamedCurve("sect571k1", "1.3.132.0.38", 13, true, true);
    public static final NamedCurve NC_CHAR2_SECT571R1 = new NamedCurve("sect571r1", "1.3.132.0.39", 14, true, true);
    public static final NamedCurve NC_PRIME_SECP160K1 = new NamedCurve("secp160k1", "1.3.132.0.9", 15, true, false);
    public static final NamedCurve NC_PRIME_SECP160R1 = new NamedCurve("secp160r1", "1.3.132.0.8", 16, true, false);
    public static final NamedCurve NC_PRIME_SECP160R2 = new NamedCurve("secp160r2", "1.3.132.0.30", 17, true, false);
    public static final NamedCurve NC_PRIME_SECP192K1 = new NamedCurve("secp192k1", "1.3.132.0.31", 18, true, false);
    public static final NamedCurve NC_PRIME_SECP192R1 = new NamedCurve("secp192r1", "1.2.840.10045.3.1.1", 19, true, false);
    public static final NamedCurve NC_PRIME_SECP224K1 = new NamedCurve("secp224k1", "1.3.132.0.32", 20, true, false);
    public static final NamedCurve NC_PRIME_SECP224R1 = new NamedCurve("secp224r1", "1.3.132.0.33", 21, true, false);
    public static final NamedCurve NC_PRIME_SECP256K1 = new NamedCurve("secp256k1", "1.3.132.0.10", 22, true, false);
    public static final NamedCurve NC_PRIME_SECP256R1 = new NamedCurve(IaikEccProvider.EC_DEFAULT_PRIME, "1.2.840.10045.3.1.7", 23, true, false);
    public static final NamedCurve NC_PRIME_SECP384R1 = new NamedCurve("secp384r1", "1.3.132.0.34", 24, true, false);
    public static final NamedCurve NC_PRIME_SECP521R1 = new NamedCurve("secp521r1", "1.3.132.0.35", 25, true, false);
    public static final NamedCurve NC_ARBITRARY_EXPLICIT_PRIME = new NamedCurve("arbitrary_explicit_prime_curves", null, 65281, true, false);
    public static final NamedCurve NC_ARBITRARY_EXPLICIT_CHAR2 = new NamedCurve("arbitrary_explicit_char2_curves", null, 65282, true, true);
    public static final NamedCurve NC_PRIME_BRAINPOOLP256R1 = new NamedCurve("brainpoolP256r1", "1.3.36.3.3.2.8.1.1.7", 26, true, false);
    public static final NamedCurve NC_PRIME_BRAINPOOLP384R1 = new NamedCurve("brainpoolP384r1", " 1.3.36.3.3.2.8.1.1.11", 27, true, false);
    public static final NamedCurve NC_PRIME_BRAINPOOLP512R1 = new NamedCurve("brainpoolP512r1", " 1.3.36.3.3.2.8.1.1.13", 28, true, false);

    public static final class NamedCurve implements Cloneable {
        protected static final int ARBITRARY_EXPLICIT_CHAR2 = 65282;
        protected static final int ARBITRARY_EXPLICIT_PRIME = 65281;
        private int a;
        private String b;
        private String c;
        private Boolean d;

        public NamedCurve(String str, String str2, int i) {
            this(str, str2, i, false);
        }

        public NamedCurve(String str, String str2, int i, boolean z) {
            if (str == null) {
                throw new IllegalArgumentException("name must not be null!");
            }
            if (i < 1 || i > 65535) {
                StringBuffer stringBuffer = new StringBuffer("Invalid curve id (");
                stringBuffer.append(i);
                stringBuffer.append(") Must be between ");
                stringBuffer.append(1);
                stringBuffer.append(" and ");
                stringBuffer.append(65535);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            this.b = str;
            this.c = str2;
            this.a = i;
            if (z) {
                SupportedEllipticCurves.b.put(new Integer(this.a), this);
                SupportedEllipticCurves.c.put(this.b, this);
                if (this.c != null) {
                    SupportedEllipticCurves.d.put(this.c, this);
                }
            }
        }

        NamedCurve(String str, String str2, int i, boolean z, boolean z2) {
            this(str, str2, i, z);
            this.d = new Boolean(z2);
        }

        NamedCurve(int i) {
            this.a = i;
            if (i < 1 || i > 65535) {
                StringBuffer stringBuffer = new StringBuffer("Invalid curve id (");
                stringBuffer.append(i);
                stringBuffer.append(") Must be between ");
                stringBuffer.append(1);
                stringBuffer.append(" and ");
                stringBuffer.append(65535);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            switch (i) {
                case ARBITRARY_EXPLICIT_PRIME /* 65281 */:
                    this.b = "arbitrary_explicit_prime_curves";
                    return;
                case ARBITRARY_EXPLICIT_CHAR2 /* 65282 */:
                    this.b = "arbitrary_explicit_char2_curves";
                    return;
                default:
                    this.b = "UNKNOWN";
                    return;
            }
        }

        public String getName() {
            return this.b;
        }

        public String getOID() {
            return this.c;
        }

        public int getID() {
            return this.a;
        }

        public int hashCode() {
            return this.a;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof NamedCurve) && this.a == ((NamedCurve) obj).getID();
        }

        public Object clone() {
            try {
                NamedCurve namedCurve = (NamedCurve) super.clone();
                try {
                    namedCurve.a = this.a;
                    namedCurve.b = this.b;
                    namedCurve.c = this.c;
                    return namedCurve;
                } catch (CloneNotSupportedException unused) {
                    return namedCurve;
                }
            } catch (CloneNotSupportedException unused2) {
                return null;
            }
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.b);
            stringBuffer.append(" (");
            stringBuffer.append(this.a);
            if (this.c != null) {
                stringBuffer.append(", ");
                stringBuffer.append(this.c);
            }
            stringBuffer.append(")");
            return stringBuffer.toString();
        }
    }

    static final NamedCurve[] a(String[] strArr) {
        NamedCurve[] namedCurveArr = new NamedCurve[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            NamedCurve registeredCurveByName = getRegisteredCurveByName(str);
            if (registeredCurveByName == null) {
                StringBuffer stringBuffer = new StringBuffer("Elliptic curve \"");
                stringBuffer.append(str);
                stringBuffer.append("\" not supported!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            namedCurveArr[i] = registeredCurveByName;
        }
        return namedCurveArr;
    }

    public static final NamedCurve getRegisteredCurveByName(String str) {
        return (NamedCurve) c.get(str);
    }

    public static final NamedCurve getRegisteredCurveByOID(String str) {
        return (NamedCurve) d.get(str);
    }

    public static final NamedCurve getRegisteredCurveByID(int i) {
        return (NamedCurve) b.get(new Integer(i));
    }

    public static final NamedCurve[] getAllRegisteredNamedCurves() {
        Object[] array = c.values().toArray();
        NamedCurve[] namedCurveArr = new NamedCurve[array.length];
        for (int i = 0; i < array.length; i++) {
            namedCurveArr[i] = (NamedCurve) array[i];
        }
        return namedCurveArr;
    }

    private static final void a(NamedCurve[] namedCurveArr) {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        for (int i = 0; i < namedCurveArr.length; i++) {
            if (!securityProvider.isNamedCurveSupported(namedCurveArr[i])) {
                StringBuffer stringBuffer = new StringBuffer("Curve ");
                stringBuffer.append(namedCurveArr[i]);
                stringBuffer.append(" not supported!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
    }

    static final SupportedEllipticCurves e() {
        SupportedEllipticCurves supportedEllipticCurves = new SupportedEllipticCurves(new NamedCurve[]{NC_PRIME_SECP256R1, NC_PRIME_SECP384R1, NC_PRIME_SECP521R1});
        supportedEllipticCurves.a(1);
        return supportedEllipticCurves;
    }

    public SupportedEllipticCurves() {
        this(false);
    }

    SupportedEllipticCurves(boolean z) {
        super(TYPE);
        if (z) {
            this.e = new NamedCurve[0];
            this.f = false;
        } else {
            this.e = new NamedCurve[]{NC_PRIME_SECP256R1, NC_PRIME_SECP384R1, NC_PRIME_SECP521R1};
            this.f = true;
        }
        a(1);
    }

    public SupportedEllipticCurves(NamedCurve[] namedCurveArr) {
        super(TYPE);
        if (namedCurveArr == null || namedCurveArr.length == 0) {
            throw new IllegalArgumentException("Elliptic curve list must not be empty!");
        }
        a(namedCurveArr);
        this.e = namedCurveArr;
        a(1);
        this.f = false;
    }

    public NamedCurve[] getEllipticCurveList() {
        return this.e;
    }

    boolean g() {
        NamedCurve[] namedCurveArr = this.e;
        return namedCurveArr == null || namedCurveArr.length == 0;
    }

    boolean f() {
        return this.f;
    }

    public NamedCurve getCurve(int i) {
        if (this.e != null) {
            int i2 = 0;
            while (true) {
                NamedCurve[] namedCurveArr = this.e;
                if (i2 >= namedCurveArr.length) {
                    break;
                }
                if (namedCurveArr[i2].getID() == i) {
                    return this.e[i2];
                }
                i2++;
            }
        }
        return null;
    }

    public void setIgnorePeerPreferenceOrder(boolean z) {
        this.g = z;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        SupportedEllipticCurves supportedEllipticCurves = (SupportedEllipticCurves) super.clone();
        NamedCurve[] namedCurveArr = this.e;
        if (namedCurveArr != null) {
            supportedEllipticCurves.e = new NamedCurve[namedCurveArr.length];
            int i = 0;
            while (true) {
                NamedCurve[] namedCurveArr2 = this.e;
                if (i >= namedCurveArr2.length) {
                    break;
                }
                supportedEllipticCurves.e[i] = (NamedCurve) namedCurveArr2[i].clone();
                i++;
            }
        }
        return supportedEllipticCurves;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        NamedCurve[] namedCurveArr = this.e;
        if (namedCurveArr != null && namedCurveArr.length > 0) {
            for (int i = 0; i < this.e.length; i++) {
                stringBuffer.append("\n ");
                stringBuffer.append(this.e[i]);
            }
        }
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        Vector vector = new Vector(5);
        int iF = abVar.f();
        if (iF > 0) {
            int iF2 = abVar.f();
            if (iF2 != iF - 2) {
                throw new SSLException("Invalid length of SupportedEllipticCurves extension!", 2, 50, false);
            }
            while (iF2 > 0) {
                int iF3 = abVar.f();
                iF2 -= 2;
                NamedCurve namedCurve = (NamedCurve) b.get(new Integer(iF3));
                if (namedCurve == null) {
                    try {
                        namedCurve = new NamedCurve(iF3);
                    } catch (Exception e) {
                        StringBuffer stringBuffer = new StringBuffer("Error decoding named curve: ");
                        stringBuffer.append(e.getMessage());
                        throw new SSLException(stringBuffer.toString(), 2, 50, false);
                    }
                }
                vector.addElement(namedCurve);
            }
            if (iF2 != 0) {
                throw new SSLException("Elliptic curve list size does not match to length field!", 2, 50, false);
            }
        }
        NamedCurve[] namedCurveArr = new NamedCurve[vector.size()];
        this.e = namedCurveArr;
        vector.copyInto(namedCurveArr);
        return iF + 2;
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        NamedCurve[] namedCurveArr = this.e;
        int i = 0;
        if (namedCurveArr == null || namedCurveArr.length == 0) {
            vVar.a(0);
            return;
        }
        int size = vVar.size();
        vVar.write(v.a);
        vVar.write(v.a);
        while (true) {
            NamedCurve[] namedCurveArr2 = this.e;
            if (i < namedCurveArr2.length) {
                vVar.a(namedCurveArr2[i].getID());
                i++;
            } else {
                int size2 = (vVar.size() - size) - 2;
                vVar.b(size2, size);
                vVar.b(size2 - 2, size + 2);
                return;
            }
        }
    }

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        SupportedEllipticCurves supportedEllipticCurves;
        SupportedEllipticCurves supportedEllipticCurves2;
        if (d()) {
            return this;
        }
        SupportedEllipticCurves supportedEllipticCurves3 = (SupportedEllipticCurves) extension;
        if (this.f) {
            return supportedEllipticCurves3;
        }
        if (this.g) {
            supportedEllipticCurves2 = this;
            supportedEllipticCurves = supportedEllipticCurves3;
        } else {
            supportedEllipticCurves = this;
            supportedEllipticCurves2 = supportedEllipticCurves3;
        }
        NamedCurve[] ellipticCurveList = supportedEllipticCurves2.getEllipticCurveList();
        ArrayList arrayList = new ArrayList();
        for (NamedCurve namedCurve : ellipticCurveList) {
            if (supportedEllipticCurves.getCurve(namedCurve.getID()) != null) {
                arrayList.add(namedCurve);
            }
        }
        if (arrayList.isEmpty()) {
            return c() == 0 ? new SupportedEllipticCurves(true) : supportedEllipticCurves3;
        }
        return new SupportedEllipticCurves((NamedCurve[]) arrayList.toArray(new NamedCurve[0]));
    }
}

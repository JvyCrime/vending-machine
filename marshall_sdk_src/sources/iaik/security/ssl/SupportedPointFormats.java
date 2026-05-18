package iaik.security.ssl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class SupportedPointFormats extends Extension implements Cloneable {
    private ECPointFormat[] d;
    public static final ExtensionType TYPE = new ExtensionType(11, "ec_point_formats");
    static final HashMap b = new HashMap(5);
    static HashMap c = new HashMap(5);
    public static final ECPointFormat PF_UNCOMPRESSED = new ECPointFormat("uncompressed", 0, true);
    public static final ECPointFormat PF_COMPRESSED_PRIME = new ECPointFormat("compressed_prime", 1, true);
    public static final ECPointFormat PF_COMPRESSED_CHAR2 = new ECPointFormat("compressed_char2", 2, true);

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return extension;
    }

    public static final class ECPointFormat implements Cloneable {
        public static final byte COMPRESSED_CHAR2 = 2;
        public static final byte COMPRESSED_PRIME = 1;
        public static final byte UNCOMPRESSED = 0;
        private int a;
        private String b;

        public ECPointFormat(String str, int i) {
            this(str, i, false);
        }

        public ECPointFormat(String str, int i, boolean z) {
            if (str == null) {
                throw new IllegalArgumentException("name must not be null!");
            }
            if (i < 0 || i > 255) {
                StringBuffer stringBuffer = new StringBuffer("Invalid point format id (");
                stringBuffer.append(i);
                stringBuffer.append(") Must be between ");
                stringBuffer.append(0);
                stringBuffer.append(" and ");
                stringBuffer.append(255);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            this.b = str;
            this.a = i;
            if (z) {
                SupportedPointFormats.b.put(new Integer(this.a), this);
                SupportedPointFormats.c.put(this.b, this);
            }
        }

        ECPointFormat(int i) {
            this.a = i;
            if (i < 0 || i > 255) {
                StringBuffer stringBuffer = new StringBuffer("Invalid point format id (");
                stringBuffer.append(i);
                stringBuffer.append(") Must be between ");
                stringBuffer.append(0);
                stringBuffer.append(" and ");
                stringBuffer.append(255);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            this.b = "UNKNOWN";
        }

        public String getName() {
            return this.b;
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
            return (obj instanceof ECPointFormat) && this.a == ((ECPointFormat) obj).getID();
        }

        public Object clone() {
            try {
                ECPointFormat eCPointFormat = (ECPointFormat) super.clone();
                try {
                    eCPointFormat.a = this.a;
                    eCPointFormat.b = this.b;
                    return eCPointFormat;
                } catch (CloneNotSupportedException unused) {
                    return eCPointFormat;
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
            stringBuffer.append(")");
            return stringBuffer.toString();
        }
    }

    static final ECPointFormat a(String str) {
        ECPointFormat eCPointFormat = PF_UNCOMPRESSED;
        if (str.equals(eCPointFormat.getName())) {
            return eCPointFormat;
        }
        ECPointFormat eCPointFormat2 = PF_COMPRESSED_PRIME;
        if (str.equals(eCPointFormat2.getName())) {
            return eCPointFormat2;
        }
        ECPointFormat eCPointFormat3 = PF_COMPRESSED_CHAR2;
        if (str.equals(eCPointFormat3.getName())) {
            return eCPointFormat3;
        }
        return null;
    }

    static final ECPointFormat[] a(String[] strArr) {
        ECPointFormat[] eCPointFormatArr = new ECPointFormat[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            ECPointFormat eCPointFormatA = a(str);
            if (eCPointFormatA == null) {
                StringBuffer stringBuffer = new StringBuffer("ECPointFormat \"");
                stringBuffer.append(str);
                stringBuffer.append("\" not supported!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            eCPointFormatArr[i] = eCPointFormatA;
        }
        return eCPointFormatArr;
    }

    static final SupportedPointFormats e() {
        SupportedPointFormats supportedPointFormats = new SupportedPointFormats(new ECPointFormat[]{PF_UNCOMPRESSED});
        supportedPointFormats.a(1);
        return supportedPointFormats;
    }

    public SupportedPointFormats() {
        super(TYPE);
        this.d = new ECPointFormat[]{PF_UNCOMPRESSED};
        a(1);
    }

    public SupportedPointFormats(ECPointFormat[] eCPointFormatArr) {
        super(TYPE);
        if (eCPointFormatArr == null || eCPointFormatArr.length == 0) {
            throw new IllegalArgumentException("Point format list must not be empty!");
        }
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        boolean z = false;
        for (ECPointFormat eCPointFormat : eCPointFormatArr) {
            if (!securityProvider.isPointFormatSupported(eCPointFormat)) {
                StringBuffer stringBuffer = new StringBuffer("Point format ");
                stringBuffer.append(eCPointFormat);
                stringBuffer.append(" not supported!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            if (eCPointFormat.equals(PF_UNCOMPRESSED)) {
                z = true;
            }
        }
        if (!z) {
            throw new IllegalArgumentException("Point format list must contain the uncompressed format!");
        }
        this.d = eCPointFormatArr;
        a(1);
    }

    public ECPointFormat[] getPointFormatList() {
        return this.d;
    }

    public ECPointFormat getPointFormat(int i) {
        ECPointFormat eCPointFormat = null;
        if (this.d != null) {
            int i2 = 0;
            while (true) {
                ECPointFormat[] eCPointFormatArr = this.d;
                if (i2 >= eCPointFormatArr.length) {
                    break;
                }
                if (eCPointFormatArr[i2].getID() == i) {
                    eCPointFormat = this.d[i2];
                }
                i2++;
            }
        }
        return eCPointFormat;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        SupportedPointFormats supportedPointFormats = (SupportedPointFormats) super.clone();
        ECPointFormat[] eCPointFormatArr = this.d;
        if (eCPointFormatArr != null) {
            supportedPointFormats.d = new ECPointFormat[eCPointFormatArr.length];
            int i = 0;
            while (true) {
                ECPointFormat[] eCPointFormatArr2 = this.d;
                if (i >= eCPointFormatArr2.length) {
                    break;
                }
                supportedPointFormats.d[i] = (ECPointFormat) eCPointFormatArr2[i].clone();
                i++;
            }
        }
        return supportedPointFormats;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        ECPointFormat[] eCPointFormatArr = this.d;
        if (eCPointFormatArr != null && eCPointFormatArr.length > 0) {
            for (int i = 0; i < this.d.length; i++) {
                stringBuffer.append("\n ");
                stringBuffer.append(this.d[i]);
            }
        }
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        boolean z;
        Vector vector = new Vector(5);
        int iF = abVar.f();
        if (iF > 0) {
            int iK = abVar.k();
            if (iK != iF - 1) {
                throw new SSLException("Invalid length of SupportedPointFormats extension!", 2, 50, false);
            }
            while (iK > 0) {
                int iK2 = abVar.k();
                iK--;
                ECPointFormat eCPointFormat = (ECPointFormat) b.get(new Integer(iK2));
                if (eCPointFormat == null) {
                    try {
                        eCPointFormat = new ECPointFormat(iK2);
                    } catch (Exception e) {
                        StringBuffer stringBuffer = new StringBuffer("Error decoding ec point format: ");
                        stringBuffer.append(e.getMessage());
                        throw new SSLException(stringBuffer.toString(), 2, 50, false);
                    }
                }
                vector.addElement(eCPointFormat);
            }
            if (iK != 0) {
                throw new SSLException("Elliptic point format list size does not match to length field!", 2, 50, false);
            }
        }
        int size = vector.size();
        if (size == 0) {
            throw new SSLException("Elliptic point format list must not be empty!", 2, 50, false);
        }
        ECPointFormat[] eCPointFormatArr = new ECPointFormat[size];
        this.d = eCPointFormatArr;
        vector.copyInto(eCPointFormatArr);
        int i = 0;
        while (true) {
            ECPointFormat[] eCPointFormatArr2 = this.d;
            if (i >= eCPointFormatArr2.length) {
                z = false;
                break;
            }
            if (eCPointFormatArr2[i].equals(PF_UNCOMPRESSED)) {
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            return iF + 2;
        }
        throw new SSLException("Elliptic point format list must contain uncompressed point format!", 2, 50, false);
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        ECPointFormat[] eCPointFormatArr = this.d;
        int i = 0;
        if (eCPointFormatArr == null || eCPointFormatArr.length == 0) {
            vVar.a(0);
            return;
        }
        int size = vVar.size();
        vVar.write(v.a);
        vVar.write(0);
        while (true) {
            ECPointFormat[] eCPointFormatArr2 = this.d;
            if (i < eCPointFormatArr2.length) {
                vVar.d(eCPointFormatArr2[i].getID());
                i++;
            } else {
                int size2 = (vVar.size() - size) - 2;
                vVar.b(size2, size);
                vVar.c(size2 - 1, size + 2);
                return;
            }
        }
    }
}

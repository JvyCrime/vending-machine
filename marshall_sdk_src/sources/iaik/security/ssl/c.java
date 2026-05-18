package iaik.security.ssl;

import iaik.security.jsse.net.KeyTypeNames;
import java.util.Locale;
import java.util.StringTokenizer;

/* JADX INFO: loaded from: classes.dex */
class c {
    String a;
    private int b;
    private boolean c;
    private int d;
    private boolean e;
    private int f;
    private int g;

    static String c(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.startsWith("RSA") ? "RSA" : upperCase.startsWith("DSA") ? "DSA" : upperCase.startsWith("DH") ? "DH" : upperCase.startsWith(KeyTypeNames.EC) ? KeyTypeNames.EC : upperCase;
    }

    public c(String str) {
        this.a = str.toUpperCase(Locale.US);
        b();
    }

    public String a() {
        return this.a;
    }

    public void b(String str) throws Exception {
        if (this.a.equals(str.toUpperCase(Locale.US))) {
            StringBuffer stringBuffer = new StringBuffer("Not allowed algorithm: ");
            stringBuffer.append(str);
            throw new Exception(stringBuffer.toString());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0060, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009b, code lost:
    
        r0 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(java.security.Key r9) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 286
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.c.a(java.security.Key):void");
    }

    void a(String str) throws IllegalArgumentException {
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        if (stringTokenizer.countTokens() != 4) {
            StringBuffer stringBuffer = new StringBuffer("Illegal key size constraint format in: \"");
            stringBuffer.append(str);
            stringBuffer.append("\"! Must be <algorithm> \"keySize\" <operator> <value>.");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        String strC = c(stringTokenizer.nextToken());
        if (!this.a.equals(strC)) {
            StringBuffer stringBuffer2 = new StringBuffer("Cannot add \"");
            stringBuffer2.append(strC);
            stringBuffer2.append("\" constraint to \"");
            stringBuffer2.append(this.a);
            stringBuffer2.append("\" key constraints. Same algorithm required!");
            throw new IllegalArgumentException(stringBuffer2.toString());
        }
        if (!"KEYSIZE".equals(stringTokenizer.nextToken().toUpperCase(Locale.US))) {
            StringBuffer stringBuffer3 = new StringBuffer("Illegal key size constraint format in: \"");
            stringBuffer3.append(str);
            stringBuffer3.append("\"! Missing \"keySize\" term.");
            throw new IllegalArgumentException(stringBuffer3.toString());
        }
        String strNextToken = stringTokenizer.nextToken();
        try {
            int i = Integer.parseInt(stringTokenizer.nextToken());
            if (i < 0) {
                StringBuffer stringBuffer4 = new StringBuffer("Illegal key size constraint format in: \"");
                stringBuffer4.append(str);
                stringBuffer4.append("\"! Illegal key size value (");
                stringBuffer4.append(i);
                stringBuffer4.append("). Must be >= 0.");
                throw new IllegalArgumentException(stringBuffer4.toString());
            }
            if (strNextToken.equals(">")) {
                this.b = i;
                this.c = false;
                return;
            }
            if (strNextToken.equals(">=")) {
                this.b = i;
                this.c = true;
                return;
            }
            if (strNextToken.equals("<")) {
                this.d = i;
                this.e = false;
                return;
            }
            if (strNextToken.equals("<=")) {
                this.d = i;
                this.e = true;
            } else if (strNextToken.equals("==")) {
                this.f = i;
            } else {
                if (strNextToken.equals("!=")) {
                    this.g = i;
                    return;
                }
                StringBuffer stringBuffer5 = new StringBuffer("Illegal operator: \"");
                stringBuffer5.append(strNextToken);
                stringBuffer5.append("\"! Must be one of \"<\",\"<=\",\">\",\">=\",\"==\",\"!=\".");
                throw new IllegalArgumentException(stringBuffer5.toString());
            }
        } catch (NumberFormatException e) {
            StringBuffer stringBuffer6 = new StringBuffer("Illegal key size constraint format in: \"");
            stringBuffer6.append(str);
            stringBuffer6.append("\"! Illegal key size value: ");
            stringBuffer6.append(e.toString());
            throw new IllegalArgumentException(stringBuffer6.toString());
        }
    }

    void b() {
        this.b = -1;
        this.d = -1;
        this.f = -1;
        this.g = -1;
        this.c = false;
        this.e = false;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.d != -1) {
            StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(this.a));
            stringBuffer2.append(this.e ? " keySize <= " : " keySize < ");
            stringBuffer2.append(this.d);
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.b != -1) {
            StringBuffer stringBuffer3 = new StringBuffer(String.valueOf(this.a));
            stringBuffer3.append(this.c ? " keySize >= " : " keySize > ");
            stringBuffer3.append(this.b);
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        if (this.f != -1) {
            StringBuffer stringBuffer4 = new StringBuffer(String.valueOf(this.a));
            stringBuffer4.append(" keySize == ");
            stringBuffer4.append(this.f);
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        if (this.g != -1) {
            StringBuffer stringBuffer5 = new StringBuffer(String.valueOf(this.a));
            stringBuffer5.append(" keySize != ");
            stringBuffer5.append(this.g);
            stringBuffer5.append("\n");
            stringBuffer.append(stringBuffer5.toString());
        }
        if (stringBuffer.length() == 0) {
            StringBuffer stringBuffer6 = new StringBuffer(String.valueOf(this.a));
            stringBuffer6.append("\n");
            stringBuffer.append(stringBuffer6.toString());
        }
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj instanceof c) {
            c cVar = (c) obj;
            boolean zEquals = this.a.equals(cVar.a);
            if (!zEquals) {
                return zEquals;
            }
            if (this.d == cVar.d && this.e == cVar.e && this.b == cVar.b && (z = this.c) == (z2 = cVar.c) && this.f == cVar.f && z == z2) {
                return true;
            }
        }
        return false;
    }
}

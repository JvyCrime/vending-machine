package iaik.security.ssl;

import iaik.security.jsse.net.KeyTypeNames;
import java.security.Key;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

/* JADX INFO: loaded from: classes.dex */
class d {
    private HashMap a = new HashMap();

    public d() {
        c cVar = new c("RSA");
        cVar.a("RSA keySize < 1024");
        a(cVar);
        c cVar2 = new c("DSA");
        cVar2.a("DSA keySize < 1024");
        a(cVar2);
        c cVar3 = new c("DH");
        cVar3.a("DH keySize < 1024");
        a(cVar3);
        c cVar4 = new c(KeyTypeNames.EC);
        cVar4.a("EC keySize < 192");
        a(cVar4);
    }

    d(String[] strArr) {
        if (strArr != null) {
            for (String str : strArr) {
                StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
                while (stringTokenizer.hasMoreElements()) {
                    String str2 = (String) stringTokenizer.nextElement();
                    StringTokenizer stringTokenizer2 = new StringTokenizer(str2);
                    String str3 = (String) stringTokenizer2.nextElement();
                    c cVarA = a(str3);
                    if (cVarA == null) {
                        cVarA = new c(str3);
                        a(cVarA);
                    }
                    if (stringTokenizer2.hasMoreTokens()) {
                        cVarA.a(str2);
                    }
                }
            }
        }
    }

    void a(c cVar) {
        this.a.put(cVar.a(), cVar);
    }

    c a(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        c cVar = (c) this.a.get(upperCase);
        return cVar == null ? (c) this.a.get(c.c(upperCase)) : cVar;
    }

    c[] a() {
        Collection collectionValues = this.a.values();
        c[] cVarArr = new c[this.a.size()];
        Iterator it = collectionValues.iterator();
        int i = 0;
        while (it.hasNext()) {
            cVarArr[i] = (c) it.next();
            i++;
        }
        return cVarArr;
    }

    public String toString() {
        c[] cVarArrA = a();
        StringBuffer stringBuffer = new StringBuffer();
        for (c cVar : cVarArrA) {
            stringBuffer.append(cVar);
        }
        return stringBuffer.toString();
    }

    public void a(Key key) throws Exception {
        c cVarA = a(key.getAlgorithm());
        if (cVarA != null) {
            cVarA.a(key);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof d) {
            c[] cVarArrA = a();
            c[] cVarArrA2 = ((d) obj).a();
            boolean z = cVarArrA.length == cVarArrA2.length;
            if (!z) {
                return z;
            }
            for (int i = 0; i < cVarArrA.length; i++) {
                if (cVarArrA[i].equals(cVarArrA2[i])) {
                }
            }
            return z;
        }
        return false;
    }
}

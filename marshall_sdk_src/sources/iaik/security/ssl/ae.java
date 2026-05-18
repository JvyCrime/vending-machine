package iaik.security.ssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class ae {
    private HashMap a;

    public ae() {
        this.a = new HashMap();
    }

    public ae(ae aeVar) {
        this.a = aeVar.a;
    }

    public Object a(Object obj, Object obj2) {
        Object[] objArr;
        if (obj instanceof String) {
            obj = ((String) obj).toLowerCase();
        }
        Object objPut = this.a.put(obj, obj2);
        if (objPut == null) {
            return obj2;
        }
        if (objPut instanceof Object[]) {
            int length = ((Object[]) objPut).length;
            objArr = (Object[]) Array.newInstance(obj2.getClass(), length + 1);
            System.arraycopy(objPut, 0, objArr, 0, length);
            objArr[length] = obj2;
        } else {
            objArr = (Object[]) Array.newInstance(obj2.getClass(), 2);
            objArr[0] = objPut;
            objArr[1] = obj2;
        }
        this.a.put(obj, objArr);
        return objArr;
    }

    public Object a(Object obj) {
        if (obj instanceof String) {
            obj = ((String) obj).toLowerCase();
        }
        return this.a.get(obj);
    }

    public boolean a(String str, boolean z) {
        String strA = a(str, (String) null);
        if (strA == null) {
            return z;
        }
        String lowerCase = strA.toLowerCase();
        return lowerCase.equals("true") || lowerCase.equals("on") || lowerCase.equals("yes");
    }

    public String[] a(String str, String[] strArr) {
        Object objA = a((Object) str);
        return objA == null ? strArr : objA instanceof String ? new String[]{(String) objA} : objA instanceof String[] ? (String[]) objA : strArr;
    }

    public int a(String str, int i) {
        String strA = a(str, (String) null);
        if (strA != null) {
            try {
                if (strA.startsWith("0x")) {
                    return Integer.valueOf(strA.substring(2), 16).intValue();
                }
                if (strA.startsWith("#")) {
                    return Integer.valueOf(strA.substring(1), 16).intValue();
                }
                return Integer.valueOf(strA).intValue();
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public String a(String str) {
        return a(str, (String) null);
    }

    private String a(String str, String str2) {
        Object objA = a((Object) str);
        if (objA == null) {
            return str2;
        }
        if (objA instanceof Object[]) {
            return ((Object[]) objA)[r1.length - 1].toString();
        }
        return objA.toString();
    }

    public void a(InputStream inputStream) throws IOException {
        int iIndexOf;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return;
            }
            if (!line.startsWith("#") && !line.startsWith("!")) {
                String strTrim = line.trim();
                if (strTrim.length() != 0 && (iIndexOf = strTrim.indexOf(61)) > 0) {
                    String strTrim2 = strTrim.substring(0, iIndexOf).trim();
                    Object objTrim = strTrim.substring(iIndexOf + 1).trim();
                    int i = 0;
                    while (true) {
                        int iIndexOf2 = strTrim.indexOf("\\\\", i);
                        if (iIndexOf2 == -1) {
                            break;
                        }
                        String strSubstring = strTrim.substring(0, iIndexOf2);
                        String strSubstring2 = strTrim.substring(iIndexOf2 + 2);
                        StringBuffer stringBuffer = new StringBuffer(String.valueOf(strSubstring));
                        stringBuffer.append("\\");
                        stringBuffer.append(strSubstring2);
                        strTrim = stringBuffer.toString();
                        i = iIndexOf2 + 1;
                    }
                    if (strTrim2.length() != 0) {
                        a(strTrim2, objTrim);
                    }
                }
            }
        }
    }

    public Set a() {
        return this.a.keySet();
    }

    public Object b(Object obj) {
        return this.a.remove(obj);
    }
}

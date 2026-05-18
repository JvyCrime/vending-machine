package iaik.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Properties;

/* JADX INFO: loaded from: classes2.dex */
public class ExtendedProperties extends Properties {
    private static final long serialVersionUID = -8925871198937045377L;

    public ExtendedProperties() {
    }

    public ExtendedProperties(Properties properties) {
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public synchronized Object get(Object obj) {
        if (obj instanceof String) {
            obj = ((String) obj).toLowerCase();
        }
        return super.get(obj);
    }

    public boolean getBoolean(String str, boolean z) {
        String property = getProperty(str, null);
        if (property == null) {
            return z;
        }
        String lowerCase = property.toLowerCase();
        return lowerCase.equals("true") || lowerCase.equals("on") || lowerCase.equals("yes");
    }

    public double getDouble(String str, double d) {
        String property = getProperty(str, null);
        if (property != null) {
            try {
                return Double.valueOf(property).doubleValue();
            } catch (NumberFormatException unused) {
            }
        }
        return d;
    }

    public File getFile(String str, File file) {
        String property = getProperty(str, null);
        return property != null ? new File(property) : file;
    }

    public int getInteger(String str, int i) {
        String property = getProperty(str, null);
        if (property != null) {
            try {
                return property.startsWith("0x") ? Integer.valueOf(property.substring(2), 16).intValue() : property.startsWith("#") ? Integer.valueOf(property.substring(1), 16).intValue() : Integer.valueOf(property).intValue();
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public Object[] getObjectArray(String str, Object[] objArr) {
        Object obj = get(str);
        return obj == null ? objArr : obj instanceof Object[] ? (Object[]) obj : new Object[]{obj};
    }

    @Override // java.util.Properties
    public String getProperty(String str) {
        return getProperty(str, null);
    }

    @Override // java.util.Properties
    public String getProperty(String str, String str2) {
        Object obj = get(str);
        if (obj == null) {
            return str2;
        }
        if (!(obj instanceof Object[])) {
            return obj.toString();
        }
        return ((Object[]) obj)[r1.length - 1].toString();
    }

    public String getString(String str, String str2) {
        String property = getProperty(str, null);
        return property != null ? property : str2;
    }

    public String[] getStringArray(String str, String[] strArr) {
        Object obj = get(str);
        return obj == null ? strArr : obj instanceof String ? new String[]{(String) obj} : obj instanceof String[] ? (String[]) obj : strArr;
    }

    @Override // java.util.Properties
    public synchronized void load(InputStream inputStream) throws IOException {
        int iIndexOf;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
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
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(strSubstring);
                            stringBuffer.append("\\");
                            stringBuffer.append(strSubstring2);
                            strTrim = stringBuffer.toString();
                            i = iIndexOf2 + 1;
                        }
                        if (strTrim2.length() != 0) {
                            put(strTrim2, objTrim);
                        }
                    }
                }
            }
        }
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public synchronized Object put(Object obj, Object obj2) {
        Object[] objArr;
        if (obj instanceof String) {
            obj = ((String) obj).toLowerCase();
        }
        Object objPut = super.put(obj, obj2);
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
        super.put(obj, objArr);
        return objArr;
    }

    @Override // java.util.Properties
    public synchronized void save(OutputStream outputStream, String str) {
        throw new RuntimeException("Save not supported!");
    }

    @Override // java.util.Properties
    public void store(OutputStream outputStream, String str) throws IOException {
        throw new IOException("Save not supported!");
    }
}

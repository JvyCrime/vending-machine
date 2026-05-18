package iaik.utils;

import iaik.security.provider.IAIK;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes2.dex */
public class ConvertKeyStore {
    private static final String[] a = {"IAIKKeyStore", "IAIK"};
    private static final String[] b = {"PKCS12", "IAIK"};
    private static final String[] c = {"JKS", "SUN"};
    private static final String[] d = {"JCEKS", "SunJCE"};
    private String e;
    private String f;
    private String g;
    private String h;
    private boolean i;

    public ConvertKeyStore(String str, String str2, String str3, String str4) {
        this.e = str;
        this.f = str2;
        this.g = str3;
        this.h = str4;
    }

    private static String a(int i, String str, String[] strArr) throws ArrayIndexOutOfBoundsException {
        if (i < strArr.length) {
            return strArr[i];
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Missing ");
        stringBuffer.append(str);
        stringBuffer.append(" argument");
        throw new ArrayIndexOutOfBoundsException(stringBuffer.toString());
    }

    private KeyStore a(File file, String str, String str2, char[] cArr, boolean z) throws IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = str2 == null ? KeyStore.getInstance(str) : KeyStore.getInstance(str, str2);
            BufferedInputStream bufferedInputStream = null;
            try {
                if (z) {
                    try {
                        bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    } catch (NoSuchAlgorithmException e) {
                        throw new KeyStoreException(e.toString());
                    }
                }
                keyStore.load(bufferedInputStream, cArr);
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused) {
                    }
                }
                return keyStore;
            } catch (Throwable th) {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (NoSuchProviderException e2) {
            throw new KeyStoreException(e2.toString());
        }
    }

    private KeyStore a(KeyStore keyStore, String str, char[] cArr) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str);
            } catch (Throwable th) {
                th = th;
            }
        } catch (NoSuchAlgorithmException e) {
            e = e;
        } catch (CertificateException e2) {
            e = e2;
        }
        try {
            keyStore.store(fileOutputStream, cArr);
            try {
                fileOutputStream.close();
            } catch (IOException unused) {
            }
            return keyStore;
        } catch (NoSuchAlgorithmException e3) {
            e = e3;
            throw new KeyStoreException(e.toString());
        } catch (CertificateException e4) {
            e = e4;
            throw new KeyStoreException(e.toString());
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    private static void a() {
        System.out.println();
        System.out.println("Usage: java ConvertKeyStore <SourceKeyStoreFile> <TargetKeyStoreFile> [<Password>]");
        System.out.println(" e.g.: java ConvertKeyStore -skstype IAIKKeyStore -tkstype PKCS12 iaikKeyStore.ks pkcs12KeyStore.p12 topSecret");
        System.out.println("");
        System.out.println("Options:");
        System.out.println("  -skstype (source keystore type)");
        System.out.println("  -sksprov (source keystore provider)");
        System.out.println("  -tkstype (target keystore type)");
        System.out.println("  -tksprov (target keystore provider)");
        System.out.println("  -v (verbose: dump progress information)");
        System.out.println();
        System.exit(1);
    }

    private static void a(String str) {
        System.out.println(str);
    }

    private static void a(String str, Exception exc) {
        System.out.println(str);
        if (exc != null) {
            exc.printStackTrace(System.out);
        }
        System.out.println();
        System.exit(1);
    }

    private void a(String str, boolean z) {
        if (!this.i) {
            System.out.print(".");
        } else if (z) {
            System.out.println(str);
        } else {
            System.out.print(str);
        }
    }

    private int[] a(KeyStore keyStore, KeyStore keyStore2, char[] cArr) throws KeyStoreException {
        String string;
        String string2;
        StringBuffer stringBuffer;
        Key key;
        String string3;
        Enumeration<String> enumerationAliases = keyStore.aliases();
        StringBuffer stringBuffer2 = new StringBuffer();
        boolean z = false;
        int i = 0;
        int i2 = 0;
        while (enumerationAliases.hasMoreElements()) {
            String strNextElement = enumerationAliases.nextElement();
            boolean zIsKeyEntry = keyStore.isKeyEntry(strNextElement);
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Processing ");
            stringBuffer3.append(zIsKeyEntry ? "key " : "cert");
            stringBuffer3.append(" entry: ");
            stringBuffer3.append(strNextElement);
            a(stringBuffer3.toString(), z);
            int i3 = i + 1;
            Enumeration<String> enumeration = enumerationAliases;
            if (zIsKeyEntry) {
                try {
                    key = keyStore.getKey(strNextElement, cArr);
                } catch (Exception e) {
                    e = e;
                    StringBuffer stringBuffer4 = new StringBuffer();
                    stringBuffer4.append(" -skipped (");
                    stringBuffer4.append(e.toString());
                    stringBuffer4.append(")");
                    b(stringBuffer4.toString());
                    stringBuffer = new StringBuffer();
                    stringBuffer.append("     ");
                    stringBuffer.append(strNextElement);
                    stringBuffer.append(" (");
                    stringBuffer.append(e.getMessage());
                    stringBuffer.append(")\n");
                    string3 = stringBuffer.toString();
                    stringBuffer2.append(string3);
                }
                if (key != null) {
                    Certificate[] certificateChain = null;
                    if ((key instanceof PrivateKey) && (certificateChain = keyStore.getCertificateChain(strNextElement)) == null) {
                        throw new KeyStoreException("Cannot get certificate chain!");
                    }
                    keyStore2.setKeyEntry(strNextElement, key, cArr, certificateChain);
                    b("");
                    enumerationAliases = enumeration;
                    i = i3;
                    z = false;
                } else {
                    b("  -empty");
                    StringBuffer stringBuffer5 = new StringBuffer();
                    stringBuffer5.append("     ");
                    stringBuffer5.append(strNextElement);
                    stringBuffer5.append(" (empty)\n");
                    stringBuffer2.append(stringBuffer5.toString());
                }
                i2++;
                enumerationAliases = enumeration;
                i = i3;
                z = false;
            } else {
                Certificate certificate = keyStore.getCertificate(strNextElement);
                if (certificate != null) {
                    try {
                        keyStore2.setCertificateEntry(strNextElement, certificate);
                        b("");
                    } catch (Exception e2) {
                        e = e2;
                        StringBuffer stringBuffer6 = new StringBuffer();
                        stringBuffer6.append(" -skipped (");
                        stringBuffer6.append(e.toString());
                        stringBuffer6.append(")");
                        b(stringBuffer6.toString());
                        stringBuffer = new StringBuffer();
                        stringBuffer.append("     ");
                        stringBuffer.append(strNextElement);
                        stringBuffer.append(" (");
                        stringBuffer.append(e.getMessage());
                        stringBuffer.append(")\n");
                        string3 = stringBuffer.toString();
                        stringBuffer2.append(string3);
                        i2++;
                    }
                    enumerationAliases = enumeration;
                    i = i3;
                    z = false;
                } else {
                    b("  -empty");
                    StringBuffer stringBuffer7 = new StringBuffer();
                    stringBuffer7.append("     ");
                    stringBuffer7.append(strNextElement);
                    stringBuffer7.append(" (empty)\n");
                    string3 = stringBuffer7.toString();
                }
            }
            stringBuffer2.append(string3);
            i2++;
            enumerationAliases = enumeration;
            i = i3;
            z = false;
        }
        int i4 = i - i2;
        StringBuffer stringBuffer8 = new StringBuffer("\n");
        if (i4 == 0) {
            string = "No entries copied. ";
        } else if (i4 != 1) {
            StringBuffer stringBuffer9 = new StringBuffer();
            stringBuffer9.append(i4);
            stringBuffer9.append(" entries copied. ");
            string = stringBuffer9.toString();
        } else {
            string = "1 entry copied. ";
        }
        stringBuffer8.append(string);
        if (i2 > 0) {
            if (i2 == 1) {
                string2 = "\n1 entry skipped:\n";
            } else {
                StringBuffer stringBuffer10 = new StringBuffer();
                stringBuffer10.append("\n");
                stringBuffer10.append(i2);
                stringBuffer10.append(" entries skipped:\n");
                string2 = stringBuffer10.toString();
            }
            stringBuffer8.append(string2);
            stringBuffer8.append(stringBuffer2.toString());
        }
        a(stringBuffer8.toString());
        return new int[]{i, i2};
    }

    private static String[] a(String str, String str2) {
        return str2 == null ? d(str) : new String[]{e(str2), str2};
    }

    private static void b() {
        Provider provider;
        IAIK.addAsProvider();
        try {
            Class<?> cls = Class.forName("iaik.security.ec.provider.ECCelerate");
            provider = (Provider) cls.newInstance();
            try {
                cls.getDeclaredMethod("enforceSP80057Recommendations", Boolean.TYPE).invoke(cls, Boolean.FALSE);
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            provider = null;
        }
        if (provider == null) {
            try {
                provider = (Provider) Class.forName("iaik.security.ecc.provider.ECCProvider").newInstance();
            } catch (Throwable unused3) {
            }
        }
        if (provider != null) {
            Security.insertProviderAt(provider, 1);
        }
    }

    private void b(String str) {
        if (this.i) {
            System.out.println(str);
        } else {
            System.out.print(".");
        }
    }

    private static void c(String str) {
        a(str, (Exception) null);
    }

    public static void copyFile(File file, File file2) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            if (file2.isDirectory()) {
                file2 = new File(file2, file.getName());
            }
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedOutputStream = null;
        }
        try {
            Util.copyStream(bufferedInputStream, bufferedOutputStream, null);
            try {
                bufferedInputStream.close();
            } catch (IOException unused) {
            }
            try {
                bufferedOutputStream.close();
            } catch (IOException unused2) {
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream2 = bufferedInputStream;
            if (bufferedInputStream2 != null) {
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused3) {
                }
            }
            if (bufferedOutputStream == null) {
                throw th;
            }
            try {
                bufferedOutputStream.close();
                throw th;
            } catch (IOException unused4) {
                throw th;
            }
        }
    }

    private static String[] d(String str) {
        String[] strArr = a;
        String upperCase = str.toUpperCase();
        String[] strArr2 = c;
        if (!upperCase.endsWith(strArr2[0])) {
            if (upperCase.endsWith(".p12") || upperCase.endsWith(".pfx")) {
                return b;
            }
            strArr2 = d;
            if (!upperCase.endsWith(strArr2[0])) {
                return strArr;
            }
        }
        return strArr2;
    }

    private static String e(String str) {
        String[] strArr = a;
        if (str.equals(strArr[1])) {
            return strArr[0];
        }
        String[] strArr2 = c;
        if (str.equals(strArr2[1])) {
            return strArr2[0];
        }
        String[] strArr3 = d;
        if (str.equals(strArr3[1])) {
            return strArr3[0];
        }
        return null;
    }

    public static void main(String[] strArr) {
        if (strArr.length < 2 || strArr.length > 12) {
            a();
        }
        String strA = null;
        String str = null;
        String strA2 = null;
        String str2 = null;
        String strA3 = null;
        String strA4 = null;
        char[] charArray = null;
        int i = 0;
        boolean z = false;
        while (i < strArr.length) {
            try {
                String lowerCase = strArr[i].toLowerCase();
                if (lowerCase.equals("-v")) {
                    z = true;
                } else if (lowerCase.equals("-skstype")) {
                    i++;
                    strA = a(i, lowerCase, strArr);
                } else if (lowerCase.equals("-sksprov")) {
                    i++;
                    strA2 = a(i, lowerCase, strArr);
                } else if (lowerCase.equals("-tkstype")) {
                    i++;
                    strA3 = a(i, lowerCase, strArr);
                } else if (lowerCase.equals("-tksprov")) {
                    i++;
                    strA4 = a(i, lowerCase, strArr);
                } else {
                    if (lowerCase.startsWith("-")) {
                        PrintStream printStream = System.out;
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("\nInvalid argumet: ");
                        stringBuffer.append(lowerCase);
                        printStream.println(stringBuffer.toString());
                    } else if (str == null) {
                        str = strArr[i];
                    } else if (str2 == null) {
                        str2 = strArr[i];
                    } else if (charArray == null) {
                        charArray = strArr[i].toCharArray();
                    }
                    a();
                }
                i++;
            } catch (ArrayIndexOutOfBoundsException e) {
                PrintStream printStream2 = System.out;
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("\n");
                stringBuffer2.append(e.getMessage());
                printStream2.println(stringBuffer2.toString());
                a();
            }
        }
        if (strA == null) {
            a("Type of source keystore not specified. Trying to determine it...");
            String[] strArrA = a(str, strA2);
            String str3 = strArrA[0];
            if (str3 == null) {
                a("Cannot not determine type of source key store");
                a();
            }
            String str4 = strArrA[1];
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Source keystore type is ");
            stringBuffer3.append(str3);
            stringBuffer3.append(".");
            a(stringBuffer3.toString());
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Source keystore provider is ");
            stringBuffer4.append(str4);
            stringBuffer4.append(".");
            a(stringBuffer4.toString());
            strA2 = str4;
            strA = str3;
        }
        if (strA3 == null) {
            a("Type of target keystore not specified. Trying to determine it...");
            String[] strArrA2 = a(str2, strA4);
            String str5 = strArrA2[0];
            if (str5 == null) {
                a("Cannot not determine type of target key store");
                a();
            }
            String str6 = strArrA2[1];
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Target keystore type is ");
            stringBuffer5.append(str5);
            stringBuffer5.append(".");
            a(stringBuffer5.toString());
            StringBuffer stringBuffer6 = new StringBuffer();
            stringBuffer6.append("Target keystore provider is ");
            stringBuffer6.append(str6);
            stringBuffer6.append(".");
            a(stringBuffer6.toString());
            strA4 = str6;
            strA3 = str5;
        }
        b();
        ConvertKeyStore convertKeyStore = new ConvertKeyStore(strA, strA2, strA3, strA4);
        convertKeyStore.setVerbose(z);
        try {
            convertKeyStore.a(str, str2, charArray);
            if (charArray != null) {
                for (int i2 = 0; i2 < charArray.length; i2++) {
                    charArray[i2] = 0;
                }
            }
        } catch (Throwable th) {
            if (charArray == null) {
                throw th;
            }
            for (int i3 = 0; i3 < charArray.length; i3++) {
                charArray[i3] = 0;
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x0216 A[LOOP:0: B:12:0x007f->B:64:0x0216, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00eb A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void a(java.lang.String r19, java.lang.String r20, char[] r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 537
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.utils.ConvertKeyStore.a(java.lang.String, java.lang.String, char[]):void");
    }

    public void setVerbose(boolean z) {
        this.i = z;
    }
}

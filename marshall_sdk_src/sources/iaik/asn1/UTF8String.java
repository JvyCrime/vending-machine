package iaik.asn1;

import iaik.utils.UTF8CodingException;
import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class UTF8String extends ASN1String {
    private static boolean a = false;

    protected UTF8String() {
        this.asnType = ASN.UTF8String;
    }

    public UTF8String(String str) {
        this();
        setValue(str);
    }

    public static char[] getCharsFromUTF8Encoding(byte[] bArr) throws CodingException {
        try {
            return Util.getCharsFromUTF8Encoding(bArr, a);
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static char[] getCharsFromUTF8Encoding(byte[] bArr, boolean z) throws CodingException {
        try {
            return Util.getCharsFromUTF8Encoding(bArr, z);
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static String getRFC2253String(String str) throws CodingException {
        try {
            return Util.getRFC2253String(str.toCharArray());
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static String getRFC2253String(String str, boolean z) throws CodingException {
        try {
            return Util.getRFC2253String(str.toCharArray(), z);
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static String getRFC2253String(char[] cArr) throws CodingException {
        try {
            return Util.getRFC2253String(cArr);
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static String getRFC2253String(char[] cArr, boolean z) throws CodingException {
        try {
            return Util.getRFC2253String(cArr, z);
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static String getStringFromUTF8Encoding(byte[] bArr) throws CodingException {
        try {
            return Util.getStringFromUTF8Encoding(bArr, a);
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static String getStringFromUTF8Encoding(byte[] bArr, boolean z) throws CodingException {
        try {
            return Util.getStringFromUTF8Encoding(bArr, z);
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static byte[] getUTF8EncodingFromCharArray(char[] cArr) throws CodingException {
        try {
            return Util.getUTF8EncodingFromCharArray(cArr);
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static byte[] getUTF8EncodingFromString(String str) throws CodingException {
        try {
            return Util.getUTF8EncodingFromCharArray(str.toCharArray());
        } catch (UTF8CodingException e) {
            throw new CodingException(e.getMessage());
        }
    }

    public static void setSkipInvalidEncodedUTF8Characters(boolean z) {
        a = z;
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        try {
            return getStringFromUTF8Encoding(this.value, a);
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error in UTF8 conversion: ");
            stringBuffer.append(e.getMessage());
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        try {
            this.value = getUTF8EncodingFromString((String) obj);
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error in UTF8 conversion: ");
            stringBuffer.append(e.getMessage());
            throw new RuntimeException(stringBuffer.toString());
        }
    }
}

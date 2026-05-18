package iaik.utils;

import iaik.security.random.SecRandom;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes2.dex */
public final class PasswordGenerator {
    public static final int USE_ALL = 31;
    public static final int USE_CHARS_LOWER = 1;
    public static final int USE_CHARS_UPPER = 2;
    public static final int USE_NUMBERS = 4;
    public static final int USE_SPECIAL_BASIC = 8;
    public static final int USE_SPECIAL_EXTENDED = 16;
    private static final String a = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    private final SecureRandom b;

    public PasswordGenerator() {
        this(SecRandom.getDefault());
    }

    public PasswordGenerator(SecureRandom secureRandom) {
        this.b = secureRandom;
    }

    public char[] generate(int i) {
        return generate(i, 31);
    }

    public char[] generate(int i, int i2) {
        return generate(i, i2, null);
    }

    public char[] generate(int i, int i2, String str) {
        String string = "";
        if ((i2 & 1) != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("");
            stringBuffer.append("abcdefghijklmnopqrstuvwxyz");
            string = stringBuffer.toString();
        }
        if ((i2 & 2) != 0) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(string);
            stringBuffer2.append(a);
            string = stringBuffer2.toString();
        }
        if ((i2 & 4) != 0) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append(string);
            stringBuffer3.append("0123456789");
            string = stringBuffer3.toString();
        }
        if ((i2 & 8) != 0) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append(string);
            stringBuffer4.append(".-_");
            string = stringBuffer4.toString();
        }
        if ((i2 & 16) != 0) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append(string);
            stringBuffer5.append("~!@#$%^&*()_-+={}[]|\\:;â\u0080\u0099<,>.?/");
            string = stringBuffer5.toString();
        }
        if (str != null) {
            StringBuffer stringBuffer6 = new StringBuffer();
            stringBuffer6.append(string);
            stringBuffer6.append(str);
            string = stringBuffer6.toString();
        }
        int length = string.length();
        char[] cArr = new char[i];
        for (int i3 = 0; i3 < i; i3++) {
            cArr[i3] = string.charAt(this.b.nextInt(length));
        }
        return cArr;
    }

    public char[] generateCharOnly(int i) {
        return generate(i, 3);
    }

    public char[] generateNumberOnly(int i) {
        return generate(i, 4);
    }
}

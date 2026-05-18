package iaik.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes2.dex */
public final class PasswordStrengthChecker {
    private static int a(CharSequence charSequence) {
        int i = 0;
        int i2 = 0;
        while (true) {
            boolean z = false;
            while (i < charSequence.length() - 1) {
                char cCharAt = charSequence.charAt(i);
                i++;
                char cCharAt2 = charSequence.charAt(i);
                if (cCharAt == cCharAt2 - 1 || cCharAt == cCharAt2 + 1) {
                    i2++;
                    z = true;
                } else if (z) {
                    break;
                }
            }
            return i2;
            i2--;
        }
    }

    private static int a(CharSequence charSequence, String str) {
        Matcher matcher = Pattern.compile(str).matcher(charSequence);
        int length = 0;
        while (matcher.find()) {
            length += matcher.group().length() - 1;
        }
        return length;
    }

    private static int b(CharSequence charSequence, String str) {
        Matcher matcher = Pattern.compile(str).matcher(charSequence);
        int i = 0;
        while (matcher.find()) {
            i++;
        }
        return i;
    }

    public static int getPasswordStrength(CharSequence charSequence) {
        int length = charSequence.length();
        int iB = b(charSequence, "\\d");
        int iB2 = b(charSequence, "[a-z]");
        int iB3 = b(charSequence, "[A-Z]");
        int iA = a(charSequence, "([^a-zA-Z0-9])\\1+");
        int i = iB2 + iB3 + iB;
        int i2 = length - i;
        int iA2 = (((i - (a(charSequence, "(.)\\1+") - iA)) - a(charSequence)) + ((i2 - iA) * 4)) * 6;
        int i3 = iB2 > 0 ? 1 : 0;
        if (iB3 > 0) {
            i3++;
        }
        if (iB > 0) {
            i3++;
        }
        if (i2 > 0) {
            i3++;
        }
        int i4 = iA2 + ((i3 - 1) * 8);
        if (i4 > 100) {
            return 100;
        }
        if (i4 < 0) {
            return 0;
        }
        return i4;
    }
}

package iaik.asn1;

import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class PrintableString extends ASN1String {
    private static boolean a = false;
    private static final byte[] b = new byte[123];

    static {
        for (int i = 65; i <= 90; i++) {
            b[i] = (byte) (i + 32);
        }
        for (int i2 = 97; i2 <= 122; i2++) {
            b[i2] = (byte) i2;
        }
    }

    protected PrintableString() {
        this.asnType = ASN.PrintableString;
    }

    public PrintableString(String str) {
        this();
        setValue(str);
    }

    private static byte a(byte b2) {
        if (b2 > 122 || b2 < 0) {
            return (byte) 0;
        }
        return b[b2];
    }

    private static int a(byte[] bArr, int i, int i2) {
        while (i < i2 && bArr[i] == 32) {
            i++;
        }
        return i;
    }

    private static boolean a(int i) {
        if (i >= 65 && i <= 90) {
            return true;
        }
        if ((i < 97 || i > 122) && i != 32) {
            return (i < 39 || i > 58) ? i == 61 || i == 63 : i != 42;
        }
        return true;
    }

    private static boolean a(byte[] bArr, byte[] bArr2) {
        byte bA;
        int length = bArr.length;
        int length2 = bArr2.length;
        int iA = a(bArr, 0, length);
        int iA2 = a(bArr2, 0, length2);
        while (iA < length && iA2 < length2) {
            try {
                byte b2 = bArr[iA];
                byte b3 = bArr2[iA2];
                if (b2 != b3) {
                    byte bA2 = a(b2);
                    if (bA2 == 0 || (bA = a(b3)) == 0 || bA2 != bA) {
                        return false;
                    }
                } else if (b2 == 32) {
                    iA = a(bArr, iA, length) - 1;
                    iA2 = a(bArr2, iA2, length2) - 1;
                }
                iA++;
                iA2++;
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error comparing printable Strings: ");
                stringBuffer.append(e.toString());
                throw new InternalErrorException(stringBuffer.toString());
            }
        }
        return a(bArr, iA, length) == length && a(bArr2, iA2, length2) == length2;
    }

    public static void checkCaseInsensitive(boolean z) {
        a = z;
    }

    public static boolean isPrintableString(String str) {
        for (char c : str.toCharArray()) {
            if (!a(c)) {
                return false;
            }
        }
        return true;
    }

    @Override // iaik.asn1.ASN1String
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PrintableString) {
            PrintableString printableString = (PrintableString) obj;
            return a ? a(this.value, printableString.value) : CryptoUtils.equalsBlock(this.value, printableString.value);
        }
        if (obj instanceof ASN1String) {
            return super.equals(obj);
        }
        return false;
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return Util.toASCIIString(this.value);
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.value = Util.toASCIIBytes((String) obj);
    }
}

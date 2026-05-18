package iaik.asn1;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class BMPString extends ASN1String {
    protected BMPString() {
        this.asnType = ASN.BMPString;
    }

    public BMPString(String str) {
        this();
        setValue(str);
    }

    private static String a(byte[] bArr) {
        int length = bArr.length >> 1;
        char[] cArr = new char[length];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            cArr[i] = (char) (((bArr[i2] & 255) << 8) | (bArr[i2 + 1] & 255));
            i++;
            i2 += 2;
        }
        return new String(cArr);
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        try {
            return new String(this.value, "UnicodeBig");
        } catch (UnsupportedEncodingException unused) {
            return a(this.value);
        }
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        try {
            this.value = ((String) obj).getBytes("UnicodeBigUnmarked");
        } catch (UnsupportedEncodingException unused) {
            int i = 0;
            try {
                byte[] bytes = ((String) obj).getBytes("UnicodeBig");
                int length = bytes.length - 2;
                byte[] bArr = new byte[length];
                System.arraycopy(bytes, 2, bArr, 0, length);
                this.value = bArr;
            } catch (UnsupportedEncodingException unused2) {
                String str = (String) obj;
                int length2 = str.length();
                this.value = new byte[length2 << 1];
                int i2 = 0;
                while (i < length2) {
                    char cCharAt = str.charAt(i);
                    this.value[i2] = (byte) (cCharAt >>> '\b');
                    this.value[i2 + 1] = (byte) cCharAt;
                    i++;
                    i2 += 2;
                }
            }
        }
    }
}

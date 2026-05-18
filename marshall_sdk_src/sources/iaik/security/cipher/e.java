package iaik.security.cipher;

import iaik.utils.Util;
import javax.crypto.BadPaddingException;

/* JADX INFO: loaded from: classes.dex */
class e extends AbstractC0026g {
    e() {
        super("ISO78164Padding");
    }

    @Override // iaik.security.cipher.AbstractC0026g
    int a(byte[] bArr, int i, int i2) {
        int iB = b(i2);
        int i3 = i + i2;
        bArr[i3] = -128;
        for (int i4 = 1; i4 < iB - 1; i4++) {
            bArr[i3 + i4] = 0;
        }
        return iB;
    }

    @Override // iaik.security.cipher.AbstractC0026g
    int b(byte[] bArr, int i, int i2) throws BadPaddingException {
        int i3 = (i + i2) - 1;
        int i4 = 1;
        while (bArr[i3] != -128 && i3 > i) {
            int i5 = i3 - 1;
            if (bArr[i3] != 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid character \"0x");
                stringBuffer.append(Util.toString(bArr[i5 + 1]));
                stringBuffer.append("\" in ISO 7816-4 Padding. Expected zero (\"0x00\")!");
                throw new BadPaddingException(stringBuffer.toString());
            }
            i4++;
            i3 = i5;
        }
        if (bArr[i3] != -128) {
            throw new BadPaddingException("Invalid ISO 7816-4 Padding. Missing leading one (\"0x80\")!");
        }
        if (i4 >= 1 && i4 <= this.a) {
            return i2 - i4;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Invalid ISO 7816-4 padding length: ");
        stringBuffer2.append(i4);
        throw new BadPaddingException(stringBuffer2.toString());
    }
}

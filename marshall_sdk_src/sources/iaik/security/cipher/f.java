package iaik.security.cipher;

import iaik.security.provider.IAIK;
import javax.crypto.BadPaddingException;

/* JADX INFO: loaded from: classes.dex */
class f extends AbstractC0026g {
    f() {
        super("PKCS5Padding");
    }

    @Override // iaik.security.cipher.AbstractC0026g
    int a(byte[] bArr, int i, int i2) {
        int iB = b(i2);
        for (int i3 = 0; i3 < iB; i3++) {
            bArr[i + i2 + i3] = (byte) iB;
        }
        return iB;
    }

    @Override // iaik.security.cipher.AbstractC0026g
    int b(byte[] bArr, int i, int i2) throws BadPaddingException {
        int i3 = (i + i2) - 1;
        int i4 = bArr[i3];
        int i5 = i4 & 255;
        if (i5 < 1 || i5 > this.a) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid PKCS#5 padding length: ");
            stringBuffer.append(i5);
            throw new BadPaddingException(stringBuffer.toString());
        }
        int i6 = i2 - i5;
        if (i6 < 0) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Invalid PKCS#5 padding: too long padding: ");
            stringBuffer2.append(i5);
            throw new BadPaddingException(stringBuffer2.toString());
        }
        if (IAIK.getCheckPKCS5PaddingBytes()) {
            for (int i7 = i + i6; i7 < i3; i7++) {
                if (bArr[i7] != i4) {
                    throw new BadPaddingException("Invalid PKCS#5 padding: not all padding bytes are equal!");
                }
            }
        }
        return i6;
    }
}

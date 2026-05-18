package iaik.security.cipher;

import javax.crypto.BadPaddingException;

/* JADX INFO: loaded from: classes.dex */
class L extends AbstractC0026g {
    L() {
        super("SSL3Padding");
    }

    @Override // iaik.security.cipher.AbstractC0026g
    int a(byte[] bArr, int i, int i2) {
        int iB = b(i2) - 1;
        for (int i3 = 0; i3 < iB; i3++) {
            bArr[i + i2 + i3] = 0;
        }
        bArr[i + i2 + iB] = (byte) iB;
        return iB + 1;
    }

    @Override // iaik.security.cipher.AbstractC0026g
    int b(byte[] bArr, int i, int i2) throws BadPaddingException {
        return i2 - (bArr[(i + i2) - 1] + 1);
    }
}

package iaik.security.cipher;

import iaik.security.random.SecRandom;
import javax.crypto.BadPaddingException;

/* JADX INFO: renamed from: iaik.security.cipher.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0025d extends AbstractC0026g {
    public C0025d() {
        super("ISO10126-2");
    }

    @Override // iaik.security.cipher.AbstractC0026g
    int a(byte[] bArr, int i, int i2) {
        int iB = b(i2);
        if (this.b == null) {
            this.b = SecRandom.getDefault();
        }
        int i3 = iB - 1;
        byte[] bArr2 = new byte[i3];
        this.b.nextBytes(bArr2);
        System.arraycopy(bArr2, 0, bArr, i + i2, i3);
        bArr[(r6 + iB) - 1] = (byte) iB;
        return iB;
    }

    @Override // iaik.security.cipher.AbstractC0026g
    int b(byte[] bArr, int i, int i2) throws BadPaddingException {
        int i3 = bArr[(i + i2) - 1] & 255;
        if (i3 >= 1 && i3 <= this.a) {
            return i2 - i3;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid ISO10126-2 padding length: ");
        stringBuffer.append(i3);
        throw new BadPaddingException(stringBuffer.toString());
    }
}

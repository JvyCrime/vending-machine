package iaik.security.cipher;

import java.security.SecureRandom;
import javax.crypto.BadPaddingException;

/* JADX INFO: renamed from: iaik.security.cipher.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0026g {
    int a;
    SecureRandom b;
    private String c;

    AbstractC0026g(String str) {
        this.c = str;
    }

    abstract int a(byte[] bArr, int i, int i2);

    final String a() {
        return this.c;
    }

    void a(int i) {
        this.a = i;
    }

    void a(SecureRandom secureRandom) {
        this.b = secureRandom;
    }

    int b(int i) {
        int i2 = this.a;
        int i3 = i2 - (i % i2);
        return i3 == 0 ? i2 : i3;
    }

    abstract int b(byte[] bArr, int i, int i2) throws BadPaddingException;
}

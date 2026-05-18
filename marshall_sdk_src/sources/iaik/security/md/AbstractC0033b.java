package iaik.security.md;

import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.util.Locale;

/* JADX INFO: renamed from: iaik.security.md.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0033b extends AbstractMessageDigest {
    static boolean g;
    transient A h;
    final transient byte[] i;

    static {
        try {
            int jVMDataMode = Util.getJVMDataMode();
            String property = System.getProperty("os.name");
            if (property != null) {
                property = property.toLowerCase(Locale.US);
            }
            g = jVMDataMode == 32 && property != null && property.toLowerCase().indexOf("windows") >= 0;
        } catch (Throwable unused) {
            g = false;
        }
    }

    public AbstractC0033b(String str, int i, int i2) {
        super(str, i, i2);
        this.i = new byte[8];
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        long j = this.f << 3;
        for (int i = 0; i < 8; i++) {
            this.i[i] = (byte) (j >>> (i << 3));
        }
        int i2 = (int) (this.f & 63);
        engineUpdate(a, 0, i2 < 56 ? 56 - i2 : 120 - i2);
        engineUpdate(this.i, 0, 8);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i) {
        this.h.a(bArr, i);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        this.h.b(bArr, i);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        this.f = 0L;
        this.h.a();
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.zeroBlock(this.i);
    }
}

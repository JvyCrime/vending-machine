package iaik.security.cipher;

import java.security.InvalidKeyException;
import javax.crypto.spec.DESKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class TripleDESKeyGenerator extends VarLengthKeyGenerator {
    public TripleDESKeyGenerator() {
        super("DESede", 128, 192, 192);
    }

    @Override // iaik.security.cipher.VarLengthKeyGenerator, javax.crypto.KeyGeneratorSpi
    protected javax.crypto.SecretKey engineGenerateKey() {
        try {
            byte[] encoded = super.engineGenerateKey().getEncoded();
            for (int i = 0; i < encoded.length; i += 8) {
                DESKeyGenerator.adjustParity(encoded, i);
                DESKeySpec.isWeak(encoded, i);
            }
            return new SecretKey(encoded, "DESede");
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.toString());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0007  */
    @Override // iaik.security.cipher.VarLengthKeyGenerator, javax.crypto.KeyGeneratorSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void engineInit(int r4, java.security.SecureRandom r5) {
        /*
            r3 = this;
            r0 = 128(0x80, float:1.8E-43)
            r1 = 192(0xc0, float:2.69E-43)
            r2 = -1
            if (r4 != r2) goto La
        L7:
            r4 = 192(0xc0, float:2.69E-43)
            goto L16
        La:
            r2 = 112(0x70, float:1.57E-43)
            if (r4 != r2) goto L11
            r4 = 128(0x80, float:1.8E-43)
            goto L16
        L11:
            r2 = 168(0xa8, float:2.35E-43)
            if (r4 != r2) goto L16
            goto L7
        L16:
            if (r4 == r0) goto L37
            if (r4 != r1) goto L1b
            goto L37
        L1b:
            java.security.InvalidParameterException r5 = new java.security.InvalidParameterException
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "Invalid 3DES key length ("
            r0.append(r1)
            r0.append(r4)
            java.lang.String r4 = "). Only 128 or 192 allowed!"
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r5.<init>(r4)
            throw r5
        L37:
            super.engineInit(r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.cipher.TripleDESKeyGenerator.engineInit(int, java.security.SecureRandom):void");
    }
}

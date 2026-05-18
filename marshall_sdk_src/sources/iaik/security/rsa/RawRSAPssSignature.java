package iaik.security.rsa;

import iaik.utils.PretendedMessageDigest;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public class RawRSAPssSignature extends RSAPssSignature {
    private MessageDigest f;

    public RawRSAPssSignature() {
        super("RawRSASSA-PSS");
        this.f = new PretendedMessageDigest();
        e();
    }

    @Override // iaik.security.rsa.b
    byte[] a() {
        return this.f.digest();
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected void engineUpdate(byte b) {
        this.f.update(b);
    }

    @Override // iaik.security.rsa.b, java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.f.update(bArr, i, i2);
    }
}

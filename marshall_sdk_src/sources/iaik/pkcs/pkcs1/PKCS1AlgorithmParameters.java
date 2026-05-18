package iaik.pkcs.pkcs1;

import iaik.asn1.ASN1Object;
import iaik.asn1.EncodedASN1Object;
import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public abstract class PKCS1AlgorithmParameters extends AlgorithmParametersSpi {
    private byte[] a;

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        this.a = null;
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        this.a = bArr;
    }

    ASN1Object toASN1Object() {
        if (this.a == null) {
            return null;
        }
        return new EncodedASN1Object(this.a);
    }
}

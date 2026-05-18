package iaik.x509;

import iaik.asn1.ASN1Object;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes2.dex */
public class RawPublicKey extends PublicKeyInfo implements Serializable, Cloneable {
    private transient byte[] a;

    RawPublicKey() {
    }

    public RawPublicKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public RawPublicKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public RawPublicKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    @Override // iaik.x509.PublicKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        this.a = bArr;
    }

    @Override // iaik.x509.PublicKeyInfo
    public byte[] encode() {
        return this.a;
    }

    @Override // iaik.x509.PublicKeyInfo, java.security.Key
    public String getAlgorithm() {
        return this.public_key_algorithm.getName();
    }

    @Override // iaik.x509.PublicKeyInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(getAlgorithm());
        stringBuffer2.append(" public key (");
        stringBuffer2.append(this.a.length);
        stringBuffer2.append(" bytes):\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append(Util.toString(this.a));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }
}

package iaik.pkcs.pkcs8;

import iaik.asn1.ASN1Object;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
public class RawPrivateKey extends PrivateKeyInfo implements Serializable, Cloneable {
    private transient byte[] c;

    RawPrivateKey() {
    }

    public RawPrivateKey(ASN1Object aSN1Object) throws InvalidKeyException {
        super(aSN1Object);
    }

    public RawPrivateKey(InputStream inputStream) throws IOException, InvalidKeyException {
        super(inputStream);
    }

    public RawPrivateKey(byte[] bArr) throws InvalidKeyException {
        super(bArr);
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    protected void decode(byte[] bArr) throws InvalidKeyException {
        this.c = bArr;
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public byte[] encode() {
        return this.c;
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo, java.security.Key
    public String getAlgorithm() {
        return this.private_key_algorithm.getName();
    }

    @Override // iaik.pkcs.pkcs8.PrivateKeyInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(getAlgorithm());
        stringBuffer2.append(" private key (");
        stringBuffer2.append(this.c.length);
        stringBuffer2.append(" bytes):\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append(Util.toString(this.c));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }
}

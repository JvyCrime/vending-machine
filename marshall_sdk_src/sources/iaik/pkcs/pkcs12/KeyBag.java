package iaik.pkcs.pkcs12;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.pkcs.pkcs8.PrivateKeyInfo;
import java.security.InvalidKeyException;
import java.security.PrivateKey;

/* JADX INFO: loaded from: classes.dex */
public class KeyBag extends SafeBag {
    PrivateKey c;

    protected KeyBag() {
        this.e = ObjectID.pkcs12_keyBag;
    }

    public KeyBag(PrivateKey privateKey) {
        this.c = privateKey;
        this.e = ObjectID.pkcs12_keyBag;
    }

    public KeyBag(PrivateKey privateKey, String str, byte[] bArr) {
        super(str, bArr);
        this.c = privateKey;
        this.e = ObjectID.pkcs12_keyBag;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        try {
            this.c = PrivateKeyInfo.getPrivateKey(aSN1Object);
        } catch (InvalidKeyException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error creating private key: ");
            stringBuffer.append(e.getMessage());
            throw new CodingException(stringBuffer.toString());
        }
    }

    public PrivateKey getPrivateKey() {
        return this.c;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        return DerCoder.decode(this.c.getEncoded());
    }

    @Override // iaik.pkcs.pkcs12.SafeBag, iaik.pkcs.pkcs12.Attributes
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("PrivateKey algorithm: ");
        stringBuffer2.append(this.c.getAlgorithm());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(super.toString());
        return stringBuffer.toString();
    }
}

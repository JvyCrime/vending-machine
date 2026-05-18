package iaik.pkcs.pkcs12;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs8.EncryptedPrivateKeyInfo;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class PKCS8ShroudedKeyBag extends KeyBag {
    private EncryptedPrivateKeyInfo d;

    protected PKCS8ShroudedKeyBag() {
        this.e = ObjectID.pkcs12_pkcs8ShroudedKeyBag;
    }

    protected PKCS8ShroudedKeyBag(KeyBag keyBag) {
        this.c = keyBag.c;
        this.e = keyBag.e;
        this.a = keyBag.a;
        this.b = keyBag.b;
        this.d = keyBag instanceof PKCS8ShroudedKeyBag ? ((PKCS8ShroudedKeyBag) keyBag).d : new EncryptedPrivateKeyInfo(this.c);
        this.e = ObjectID.pkcs12_pkcs8ShroudedKeyBag;
    }

    @Override // iaik.pkcs.pkcs12.KeyBag, iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        try {
            this.d = new EncryptedPrivateKeyInfo(aSN1Object);
        } catch (InvalidKeyException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error creating private key: ");
            stringBuffer.append(e.getMessage());
            throw new CodingException(stringBuffer.toString());
        }
    }

    public void decrypt(char[] cArr) throws GeneralSecurityException {
        this.c = this.d.decrypt(cArr);
    }

    public void encrypt(char[] cArr, AlgorithmID algorithmID, int i) throws NoSuchAlgorithmException {
        encrypt(cArr, algorithmID, null, i);
    }

    public void encrypt(char[] cArr, AlgorithmID algorithmID, SecureRandom secureRandom, int i) throws NoSuchAlgorithmException {
        this.d.encrypt(cArr, algorithmID, secureRandom, i);
    }

    @Override // iaik.pkcs.pkcs12.KeyBag, iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        return this.d.toASN1Object();
    }

    @Override // iaik.pkcs.pkcs12.KeyBag, iaik.pkcs.pkcs12.SafeBag, iaik.pkcs.pkcs12.Attributes
    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PKCS8ShroudedKeyBag: ");
        if (this.c != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("\n");
            stringBuffer2.append(super.toString());
            string = stringBuffer2.toString();
        } else {
            string = "not decrypted yet!\n";
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

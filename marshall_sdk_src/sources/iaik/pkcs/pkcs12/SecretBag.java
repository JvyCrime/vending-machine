package iaik.pkcs.pkcs12;

import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;

/* JADX INFO: loaded from: classes.dex */
public class SecretBag extends SafeBag {
    protected ASN1Object secret_object;
    protected ObjectID secret_type;

    protected SecretBag() {
        this.e = ObjectID.pkcs12_secretBag;
    }

    public SecretBag(ASN1Object aSN1Object, ObjectID objectID) {
        this.secret_object = aSN1Object;
        this.e = ObjectID.pkcs12_secretBag;
        this.secret_type = objectID;
    }

    public SecretBag(ASN1Object aSN1Object, ObjectID objectID, String str, byte[] bArr) {
        super(str, bArr);
        this.secret_object = aSN1Object;
        this.secret_type = objectID;
        this.e = ObjectID.pkcs12_secretBag;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.secret_type = (ObjectID) aSN1Object.getComponentAt(0);
        this.secret_object = (ASN1Object) aSN1Object.getComponentAt(1).getValue();
    }

    public ASN1Object getSecretObject() {
        return this.secret_object;
    }

    public ObjectID getSecretType() {
        return this.secret_type;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.secret_type);
        sequence.addComponent(new CON_SPEC(0, this.secret_object));
        return sequence;
    }

    @Override // iaik.pkcs.pkcs12.SafeBag, iaik.pkcs.pkcs12.Attributes
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Secret type: ");
        stringBuffer2.append(this.secret_type);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(super.toString());
        return stringBuffer.toString();
    }
}

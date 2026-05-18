package iaik.pkcs.pkcs9;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1String;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.PrintableString;
import iaik.asn1.UTF8String;
import iaik.asn1.structures.AttributeValue;

/* JADX INFO: loaded from: classes.dex */
public class ChallengePassword extends AttributeValue {
    public static final ObjectID oid = ObjectID.challengePassword;
    private ASN1String a;

    public ChallengePassword() {
    }

    public ChallengePassword(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public ChallengePassword(ASN1String aSN1String) {
        this.a = aSN1String;
    }

    public ChallengePassword(String str) {
        this.a = PrintableString.isPrintableString(str) ? new PrintableString(str) : new UTF8String(str);
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        if (!(aSN1Object instanceof ASN1String)) {
            throw new CodingException("ChallengePassword must be an ASN.1 String");
        }
        this.a = (ASN1String) aSN1Object;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public ObjectID getAttributeType() {
        return oid;
    }

    public String getPassword() {
        return (String) this.a.getValue();
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return this.a;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public String toString() {
        return getPassword();
    }
}

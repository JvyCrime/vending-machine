package iaik.pkcs.pkcs12;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SafeContentsBag extends SafeBag {
    protected SafeBag[] bags;

    protected SafeContentsBag() {
        this.e = ObjectID.pkcs12_safeContentsBag;
    }

    public SafeContentsBag(SafeBag[] safeBagArr) {
        Objects.requireNonNull(safeBagArr, "Argument \"bags\" must not be null.");
        this.bags = safeBagArr;
        this.e = ObjectID.pkcs12_safeContentsBag;
    }

    public SafeContentsBag(SafeBag[] safeBagArr, String str, byte[] bArr) {
        super(str, bArr);
        Objects.requireNonNull(safeBagArr, "Argument \"bags\" must not be null.");
        this.bags = safeBagArr;
        this.e = ObjectID.pkcs12_safeContentsBag;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.bags = SafeBag.parseSafeContents(aSN1Object);
    }

    public SafeBag[] getSafeBags() {
        return this.bags;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        return SafeBag.encodeSafeContentsAsASN1Object(this.bags);
    }

    @Override // iaik.pkcs.pkcs12.SafeBag, iaik.pkcs.pkcs12.Attributes
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        for (int i = 0; i < this.bags.length; i++) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Safe bag #");
            stringBuffer2.append(i);
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
            stringBuffer.append(this.bags[i].toString());
        }
        return stringBuffer.toString();
    }
}

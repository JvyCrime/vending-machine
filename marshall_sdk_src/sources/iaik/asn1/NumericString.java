package iaik.asn1;

import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class NumericString extends ASN1String {
    protected NumericString() {
        this.asnType = ASN.NumericString;
    }

    public NumericString(String str) {
        this();
        setValue(str);
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return Util.toASCIIString(this.value);
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.value = Util.toASCIIBytes((String) obj);
    }
}

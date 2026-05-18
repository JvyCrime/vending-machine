package iaik.asn1;

import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class VisibleString extends ASN1String {
    protected VisibleString() {
        this.asnType = ASN.VisibleString;
    }

    public VisibleString(String str) {
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

package iaik.asn1;

import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class IA5String extends ASN1String {
    protected IA5String() {
        this.asnType = ASN.IA5String;
    }

    public IA5String(String str) {
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

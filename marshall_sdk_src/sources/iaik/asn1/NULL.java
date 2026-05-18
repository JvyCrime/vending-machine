package iaik.asn1;

import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class NULL extends ASN1Object {
    public NULL() {
        this.asnType = ASN.NULL;
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) {
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) {
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return null;
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append("null");
        return stringBuffer.toString();
    }
}

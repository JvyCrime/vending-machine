package iaik.asn1;

/* JADX INFO: loaded from: classes.dex */
public class SEQUENCE extends ConstructedType {
    public SEQUENCE() {
        this.asnType = ASN.SEQUENCE;
        this.constructed = true;
    }

    public SEQUENCE(boolean z) {
        this();
        this.indefinite_length = z;
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(this.content_count);
        stringBuffer.append(" elements");
        return stringBuffer.toString();
    }
}

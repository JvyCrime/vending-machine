package iaik.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class BOOLEAN extends ASN1Object {
    boolean a;
    public static final BOOLEAN TRUE = new BOOLEAN(true);
    public static final BOOLEAN FALSE = new BOOLEAN(false);

    protected BOOLEAN() {
        this.a = false;
        this.asnType = ASN.BOOLEAN;
    }

    public BOOLEAN(boolean z) {
        this();
        this.a = z;
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws CodingException, IOException {
        if (i == 1) {
            this.a = inputStream.read() != 0;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Boolean length not one, length=");
        stringBuffer.append(i);
        throw new CodingException(stringBuffer.toString());
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        outputStream.write(this.a ? -1 : 0);
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return new Boolean(this.a);
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.a = ((Boolean) obj).booleanValue();
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(this.a);
        return stringBuffer.toString();
    }
}

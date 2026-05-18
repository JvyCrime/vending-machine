package iaik.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class ENUMERATED extends ASN1Object {
    int a;

    protected ENUMERATED() {
        this.a = -1;
        this.asnType = ASN.ENUMERATED;
    }

    public ENUMERATED(int i) {
        this();
        this.a = i;
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws CodingException, IOException {
        if (i > 5) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Too long integer in ASN.1 type Enumerated, length=");
            stringBuffer.append(i);
            throw new CodingException(stringBuffer.toString());
        }
        int i2 = 0;
        while (true) {
            this.a = i2;
            i--;
            if (i < 0) {
                return;
            } else {
                i2 = (this.a << 8) | (inputStream.read() & 255);
            }
        }
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        byte b;
        int i = this.a;
        do {
            outputStream.write(i);
            b = (byte) i;
            i >>= 8;
            if (i == 0) {
                break;
            }
        } while (i != -1);
        if ((i != 0 || b >= 0) && (i != -1 || b < 0)) {
            return;
        }
        outputStream.write(i);
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return new Integer(this.a);
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.a = ((Integer) obj).intValue();
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(this.a);
        return stringBuffer.toString();
    }
}

package iaik.asn1;

import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class GeneralizedTime extends ASN1Object {
    String a;

    protected GeneralizedTime() {
        this.a = null;
        this.asnType = ASN.GeneralizedTime;
    }

    public GeneralizedTime(String str) {
        this();
        this.a = str;
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws IOException {
        try {
            byte[] bArr = new byte[i];
            Util.fillArray(bArr, inputStream);
            this.a = Util.toASCIIString(bArr);
        } catch (OutOfMemoryError unused) {
            throw new IOException("Invalid GenerailzedTime encoding! Not enough memory for decoding value!");
        }
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        outputStream.write(Util.toASCIIBytes(this.a));
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return this.a;
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.a = (String) obj;
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(this.a);
        return stringBuffer.toString();
    }
}

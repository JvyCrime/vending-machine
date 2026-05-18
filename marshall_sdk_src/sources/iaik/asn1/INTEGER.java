package iaik.asn1;

import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class INTEGER extends ASN1Object {
    byte[] a;

    protected INTEGER() {
        this.a = null;
        this.asnType = ASN.INTEGER;
    }

    public INTEGER(int i) {
        this();
        this.a = BigInteger.valueOf(i).toByteArray();
    }

    public INTEGER(BigInteger bigInteger) {
        this();
        Objects.requireNonNull(bigInteger, "value must not be null!");
        this.a = bigInteger.toByteArray();
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws IOException {
        if (i == -1) {
            throw new IOException("Invalid indefinite length encoding for primitive encoded INTEGER type!");
        }
        try {
            byte[] bArr = new byte[i];
            this.a = bArr;
            Util.fillArray(bArr, inputStream);
        } catch (OutOfMemoryError unused) {
            throw new IOException("Not enough memory for decoding ASN.1 INTEGER value!");
        }
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        outputStream.write(this.a);
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return new BigInteger(this.a);
    }

    @Override // iaik.asn1.ASN1Object
    public void setIndefiniteLength(boolean z) {
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.a = ((BigInteger) obj).toByteArray();
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(getValue().toString());
        return stringBuffer.toString();
    }
}

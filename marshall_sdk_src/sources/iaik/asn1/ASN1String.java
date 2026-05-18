package iaik.asn1;

import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class ASN1String extends ASN1Object {
    protected byte[] value;

    protected ASN1String() {
        this.isStringType = true;
    }

    @Override // iaik.asn1.ASN1Object
    public Object clone() {
        ASN1String aSN1String = (ASN1String) super.clone();
        byte[] bArr = this.value;
        if (bArr != null) {
            aSN1String.value = (byte[]) bArr.clone();
        }
        return aSN1String;
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws IOException {
        if (i == -1) {
            throw new IOException("Invalid indefinite length encoding for primitive encoded String type!");
        }
        try {
            byte[] bArr = new byte[i];
            this.value = bArr;
            Util.fillArray(bArr, inputStream);
        } catch (OutOfMemoryError unused) {
            throw new IOException("Not enough memory for decoding ASN.1 STRING value!");
        }
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        outputStream.write(this.value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ASN1String) {
            return CryptoUtils.equalsBlock(this.value, ((ASN1String) obj).value);
        }
        return false;
    }

    public byte[] getByteValue() {
        return this.value;
    }

    public int hashCode() {
        return this.asnType.hashCode() ^ Util.calculateHashCode(this.value);
    }

    @Override // iaik.asn1.ASN1Object
    public void setIndefiniteLength(boolean z) {
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append("\"");
        stringBuffer.append(getValue());
        stringBuffer.append("\"");
        return stringBuffer.toString();
    }
}

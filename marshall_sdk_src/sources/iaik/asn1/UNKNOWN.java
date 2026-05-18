package iaik.asn1;

import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class UNKNOWN extends ConstructedType {
    byte[] a;
    ASN b;

    public UNKNOWN() {
        this.asnType = ASN.UNKNOWN;
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    public Object clone() {
        UNKNOWN unknown = (UNKNOWN) super.clone();
        byte[] bArr = this.a;
        if (bArr != null) {
            unknown.a = (byte[]) bArr.clone();
        }
        return unknown;
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws IOException {
        if (this.constructed) {
            try {
                super.decode(i, inputStream);
            } catch (CodingException e) {
                throw new IOException(e.toString());
            }
        } else {
            try {
                byte[] bArr = new byte[i];
                this.a = bArr;
                Util.fillArray(bArr, inputStream);
            } catch (OutOfMemoryError unused) {
                throw new IOException("Not enough memory for decoding UNKNOWN ASN.1 value!");
            }
        }
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        if (this.constructed) {
            super.encode(outputStream);
        } else {
            outputStream.write(this.a);
        }
    }

    public ASN getBaseAsnType() {
        return this.b;
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    public Object getValue() {
        return !this.constructed ? this.a : super.getValue();
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.a = (byte[]) obj;
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer;
        String str;
        StringBuffer stringBuffer2 = new StringBuffer();
        if (this.constructed) {
            stringBuffer = new StringBuffer();
            stringBuffer.append(super.toString());
            stringBuffer.append("[");
            stringBuffer.append(this.asnType.tag);
            stringBuffer.append("] ");
            stringBuffer.append(this.content_count);
            str = " elements";
        } else {
            stringBuffer = new StringBuffer();
            stringBuffer.append(super.toString());
            stringBuffer.append("[");
            stringBuffer.append(this.asnType.tag);
            str = "]";
        }
        stringBuffer.append(str);
        stringBuffer2.append(stringBuffer.toString());
        return stringBuffer2.toString();
    }
}

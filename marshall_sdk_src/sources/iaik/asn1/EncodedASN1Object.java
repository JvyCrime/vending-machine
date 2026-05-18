package iaik.asn1;

import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class EncodedASN1Object extends ASN1Object {
    byte[] a;
    InputStream b;
    int c;
    int d;

    public EncodedASN1Object() {
        this.asnType = null;
        this.c = -1;
    }

    public EncodedASN1Object(InputStream inputStream) {
        this();
        this.b = inputStream;
    }

    public EncodedASN1Object(InputStream inputStream, int i) {
        this();
        this.b = inputStream;
        this.c = i;
    }

    public EncodedASN1Object(byte[] bArr) {
        this();
        this.a = bArr;
    }

    void a() throws IOException {
        try {
            if (this.a != null) {
                b bVar = new b(new ByteArrayInputStream(this.a));
                DerCoder.a(bVar, new int[0], this, false);
                this.d = bVar.a();
            } else if (this.b != null) {
                DerCoder.a(new b(this.b), new int[0], this, false);
            }
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("EncodedASN1Object coding error: ");
            stringBuffer.append(e.getMessage());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws CodingException {
        throw new CodingException("Not supported by this ASN.1 object!");
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        byte[] bArr = this.a;
        if (bArr != null) {
            int i = this.d;
            outputStream.write(bArr, i, bArr.length - i);
            return;
        }
        InputStream inputStream = this.b;
        if (inputStream != null) {
            if (!this.stream_mode) {
                outputStream.write(Util.readStream(inputStream));
            } else {
                int i2 = this.c;
                Util.copyStream(inputStream, outputStream, i2 > 0 ? new byte[i2] : null);
            }
        }
    }

    public int getBlockSize() {
        return this.c;
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        byte[] bArr = this.a;
        return bArr != null ? bArr : this.b;
    }

    @Override // iaik.asn1.ASN1Object
    public void setIndefiniteLength(boolean z) {
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.a = null;
        this.b = null;
        if (obj instanceof byte[]) {
            this.a = (byte[]) obj;
            this.indefinite_length = false;
            this.constructed = false;
            this.c = -1;
            return;
        }
        if (obj instanceof InputStream) {
            this.b = (InputStream) obj;
            if (this.c == -1) {
                this.indefinite_length = false;
                this.constructed = false;
            }
        }
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Encoded ASN.1 Object: ");
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.a.length);
            stringBuffer2.append(" bytes: ");
            stringBuffer2.append(Util.toString(this.a, 0, 5));
            stringBuffer.append(stringBuffer2.toString());
            if (this.a.length > 5) {
                stringBuffer.append("...");
            }
        } else {
            stringBuffer.append(this.b);
        }
        return stringBuffer.toString();
    }
}

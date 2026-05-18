package iaik.asn1;

import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class CON_SPEC extends ConstructedType {
    byte[] a;
    boolean b;

    protected CON_SPEC() {
        this.a = null;
        this.b = false;
        this.asnType = (ASN) ASN.CON_SPEC.clone();
    }

    public CON_SPEC(int i, ASN1Object aSN1Object) {
        this(i, aSN1Object, false);
    }

    public CON_SPEC(int i, ASN1Object aSN1Object, boolean z) {
        this();
        addComponent(aSN1Object);
        this.asnType.tag = i;
        this.b = z;
        if ((aSN1Object instanceof EncodedASN1Object) && z) {
            try {
                ((EncodedASN1Object) aSN1Object).a();
            } catch (IOException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error adding EncodedASN1Object: ");
                stringBuffer.append(e.getMessage());
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
        if (this.b) {
            this.constructed = aSN1Object.constructed;
        } else {
            this.constructed = true;
        }
        this.indefinite_length = aSN1Object.indefinite_length;
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    public Object clone() {
        CON_SPEC con_spec = (CON_SPEC) super.clone();
        byte[] bArr = this.a;
        if (bArr != null) {
            con_spec.a = (byte[]) bArr.clone();
        }
        return con_spec;
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws CodingException, IOException {
        if (this.constructed) {
            super.decode(i, inputStream);
            return;
        }
        try {
            byte[] bArr = new byte[i];
            this.a = bArr;
            Util.fillArray(bArr, inputStream);
            this.b = true;
        } catch (OutOfMemoryError unused) {
            throw new IOException("Not enough memory for decoding ASN.1 CON_SPEC value!");
        }
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        if (this.a != null) {
            if (this.constructed) {
                throw new InternalErrorException("Constructed and value != null!");
            }
            outputStream.write(this.a);
        } else {
            if (this.stream_mode) {
                for (int i = 0; i < this.content_count; i++) {
                    DerCoder.encodeTo(this.content_data[i], outputStream, this.b);
                }
                return;
            }
            for (int i2 = this.content_count - 1; i2 >= 0; i2--) {
                DerCoder.a(this.content_data[i2], outputStream, this.b);
            }
        }
    }

    public void forceImplicitlyTagged(ASN asn) throws CodingException {
        try {
            if (this.b && this.a == null) {
                return;
            }
            ASN1Object aSN1ObjectCreate = ASN.create(asn);
            if (this.b) {
                aSN1ObjectCreate.decode(this.a.length, new ByteArrayInputStream(this.a));
                addComponent(aSN1ObjectCreate);
                this.constructed = aSN1ObjectCreate.constructed;
                this.a = null;
                return;
            }
            if (!(aSN1ObjectCreate instanceof ConstructedType) && this.content_data != null && this.content_count == 1) {
                if (!this.content_data[0].isA(asn)) {
                    throw new CodingException("Invalid implicit tagging!");
                }
                this.constructed = false;
                this.a = null;
                return;
            }
            ((ConstructedType) aSN1ObjectCreate).content_data = this.content_data;
            ((ConstructedType) aSN1ObjectCreate).content_count = this.content_count;
            aSN1ObjectCreate.constructed = true;
            this.content_data = new ASN1Object[1];
            this.content_data[0] = aSN1ObjectCreate;
            this.content_count = 1;
            this.constructed = true;
            this.b = true;
        } catch (Exception unused) {
            throw new CodingException("Error creating instance of type.");
        }
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    public Object getValue() {
        return this.content_count == 1 ? this.content_data[0] : this.content_count == 0 ? this.a : this.content_data;
    }

    public boolean isImplicitlyTagged() {
        return this.b;
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("[");
        stringBuffer2.append(this.asnType.tag);
        stringBuffer2.append("] ");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(this.b ? "IMPLICIT" : "EXPLICIT");
        return stringBuffer.toString();
    }
}

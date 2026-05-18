package iaik.asn1;

import iaik.utils.ArrayEnumeration;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public abstract class ConstructedType extends ASN1Object {
    protected int content_count;
    boolean c = true;
    protected ASN1Object[] content_data = new ASN1Object[5];

    @Override // iaik.asn1.ASN1Object
    public void addComponent(ASN1Object aSN1Object) {
        addComponent(aSN1Object, this.content_count);
        this.constructed = true;
    }

    public synchronized void addComponent(ASN1Object aSN1Object, int i) {
        int i2 = this.content_count;
        ASN1Object[] aSN1ObjectArr = this.content_data;
        if (i2 >= aSN1ObjectArr.length) {
            ASN1Object[] aSN1ObjectArr2 = new ASN1Object[aSN1ObjectArr.length * 2];
            this.content_data = aSN1ObjectArr2;
            System.arraycopy(aSN1ObjectArr, 0, aSN1ObjectArr2, 0, aSN1ObjectArr.length);
        }
        int i3 = this.content_count;
        if (i != i3) {
            ASN1Object[] aSN1ObjectArr3 = this.content_data;
            System.arraycopy(aSN1ObjectArr3, i, aSN1ObjectArr3, i + 1, i3 - i);
        }
        this.content_data[i] = aSN1Object;
        this.content_count++;
        this.constructed = true;
    }

    public void addEncodeListener(EncodeListener encodeListener, int i, int i2) {
        d dVar = new d(encodeListener, i, i2);
        if (this.encode_listener == null) {
            this.encode_listener = new d[]{dVar};
        } else {
            this.encode_listener = (d[]) Util.resizeArray(this.encode_listener, this.encode_listener.length + 1);
            this.encode_listener[this.encode_listener.length - 1] = dVar;
        }
    }

    @Override // iaik.asn1.ASN1Object
    public Object clone() {
        ConstructedType constructedType = (ConstructedType) super.clone();
        ASN1Object[] aSN1ObjectArr = this.content_data;
        if (aSN1ObjectArr != null) {
            int length = aSN1ObjectArr.length;
            constructedType.content_data = new ASN1Object[length];
            for (int i = 0; i < length; i++) {
                ASN1Object[] aSN1ObjectArr2 = constructedType.content_data;
                ASN1Object[] aSN1ObjectArr3 = this.content_data;
                aSN1ObjectArr2[i] = aSN1ObjectArr3[i] != null ? (ASN1Object) aSN1ObjectArr3[i].clone() : null;
            }
        }
        return constructedType;
    }

    @Override // iaik.asn1.ASN1Object
    public int countComponents() {
        return this.content_count;
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws CodingException, IOException {
        int[] iArr = new int[1];
        this.constructed = true;
        if (i >= 0) {
            for (int i2 = 0; i2 < i; i2 += iArr[0]) {
                ASN1Object aSN1ObjectA = DerCoder.a(inputStream, iArr, this.c);
                if (this.c) {
                    addComponent(aSN1ObjectA);
                }
            }
            return;
        }
        this.indefinite_length = true;
        while (true) {
            int i3 = inputStream.read();
            if (i3 == 0) {
                if (inputStream.read() != 0) {
                    throw new CodingException("DER decode: infinite length second byte not 0!");
                }
                return;
            } else {
                if (i3 == -1) {
                    throw new CodingException("Unexpected EOF in indefinite encoding!");
                }
                ((b) inputStream).unread(i3);
                ASN1Object aSN1ObjectA2 = DerCoder.a(inputStream, iArr, this.c);
                if (this.c) {
                    addComponent(aSN1ObjectA2);
                }
            }
        }
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        if (!this.stream_mode) {
            for (int i = this.content_count - 1; i >= 0; i--) {
                DerCoder.a(this.content_data[i], outputStream);
            }
            return;
        }
        if (this.encode_listener == null) {
            for (int i2 = 0; i2 < this.content_count; i2++) {
                DerCoder.encodeTo(this.content_data[i2], outputStream, false);
            }
            return;
        }
        for (int i3 = 0; i3 < this.content_count; i3++) {
            DerCoder.encodeTo(this.content_data[i3], outputStream, false);
            for (int i4 = 0; i4 < this.encode_listener.length; i4++) {
                try {
                    d dVar = this.encode_listener[i4];
                    if (dVar.c() == i3) {
                        dVar.a().encodeCalled(this, dVar.b());
                    }
                } catch (CodingException e) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Error from EncodeListener: ");
                    stringBuffer.append(e.getMessage());
                    throw new IOException(stringBuffer.toString());
                }
            }
        }
    }

    @Override // iaik.asn1.ASN1Object
    public ASN1Object getComponentAt(int i) {
        if (i < this.content_count) {
            return this.content_data[i];
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Index out of range: ");
        stringBuffer.append(i);
        stringBuffer.append(" >= ");
        stringBuffer.append(this.content_count);
        throw new ArrayIndexOutOfBoundsException(stringBuffer.toString());
    }

    public synchronized Enumeration getComponents() {
        return new ArrayEnumeration(this.content_data, this.content_count);
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return this.content_data;
    }

    public synchronized void removeComponent(int i) {
        if (i >= this.content_count) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Index out of range: ");
            stringBuffer.append(i);
            stringBuffer.append(" >= ");
            stringBuffer.append(this.content_count);
            throw new ArrayIndexOutOfBoundsException(stringBuffer.toString());
        }
        ASN1Object[] aSN1ObjectArr = this.content_data;
        aSN1ObjectArr[i] = null;
        if ((r0 - i) - 1 > 0) {
            System.arraycopy(aSN1ObjectArr, i + 1, aSN1ObjectArr, i, (r0 - i) - 1);
        }
        int i2 = this.content_count - 1;
        this.content_count = i2;
        this.content_data[i2] = null;
    }

    public synchronized void removeComponent(ASN1Object aSN1Object) {
        for (int i = 0; i < this.content_count; i++) {
            if (aSN1Object.equals(this.content_data[i])) {
                removeComponent(i);
                return;
            }
        }
    }

    public void setComponent(int i, ASN1Object aSN1Object) {
        if (i < this.content_count) {
            this.content_data[i] = aSN1Object;
            this.constructed = true;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Index out of range: ");
        stringBuffer.append(i);
        stringBuffer.append(" >= ");
        stringBuffer.append(this.content_count);
        throw new ArrayIndexOutOfBoundsException(stringBuffer.toString());
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        ASN1Object[] aSN1ObjectArr = (ASN1Object[]) obj;
        this.content_data = aSN1ObjectArr;
        this.content_count = aSN1ObjectArr.length;
        this.constructed = true;
    }
}

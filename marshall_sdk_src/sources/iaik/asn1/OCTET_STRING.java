package iaik.asn1;

import iaik.utils.ExtByteArrayOutputStream;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public class OCTET_STRING extends ConstructedType {
    byte[] a;
    InputStream b;
    int d;

    public OCTET_STRING() {
        this.d = -1;
        this.asnType = ASN.OCTET_STRING;
    }

    public OCTET_STRING(InputStream inputStream) {
        this();
        this.b = inputStream;
        this.constructed = false;
    }

    public OCTET_STRING(InputStream inputStream, int i) {
        this();
        this.b = inputStream;
        if (i > 0) {
            this.d = i;
            this.indefinite_length = true;
            this.constructed = true;
        }
    }

    public OCTET_STRING(byte[] bArr) {
        this();
        this.a = bArr;
        this.constructed = false;
    }

    public OCTET_STRING(byte[] bArr, int i) {
        this();
        this.a = bArr;
        if (i > 0) {
            this.d = i;
            this.indefinite_length = true;
            this.constructed = true;
        }
    }

    private static final int a(InputStream inputStream, byte[] bArr) throws IOException {
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            i2 = inputStream.read(bArr, i, length - i);
            if (i2 < 0) {
                break;
            }
            i += i2;
        }
        return i == 0 ? i2 : i;
    }

    private String a(boolean z, String str) {
        StringBuffer stringBuffer;
        String string;
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append(str);
        stringBuffer3.append(super.toString());
        stringBuffer2.append(stringBuffer3.toString());
        if (this.constructed) {
            if (this.content_count > 0) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append(str);
                stringBuffer4.append("  ");
                String string2 = stringBuffer4.toString();
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append(this.content_count);
                stringBuffer5.append(" elements\n");
                stringBuffer2.append(stringBuffer5.toString());
                for (int i = 0; i < this.content_count; i++) {
                    stringBuffer2.append(((OCTET_STRING) this.content_data[i]).a(true, string2));
                }
                return stringBuffer2.toString();
            }
            stringBuffer = new StringBuffer();
            stringBuffer.append("block size: ");
            stringBuffer.append(this.d);
            stringBuffer.append(" source: ");
            stringBuffer.append(this.b);
        } else if (this.a == null) {
            stringBuffer = new StringBuffer();
            stringBuffer.append(this.b);
        } else {
            if (!z) {
                StringBuffer stringBuffer6 = new StringBuffer();
                stringBuffer6.append(this.a.length);
                stringBuffer6.append(" bytes: ");
                stringBuffer6.append(Util.toString(this.a, 0, 5));
                stringBuffer2.append(stringBuffer6.toString());
                if (this.a.length <= 5) {
                    stringBuffer2.append("\n");
                    return stringBuffer2.toString();
                }
                string = "...\n";
                stringBuffer2.append(string);
                return stringBuffer2.toString();
            }
            stringBuffer = new StringBuffer();
            stringBuffer.append(this.a.length);
            stringBuffer.append(" bytes: ");
            byte[] bArr = this.a;
            stringBuffer.append(Util.toString(bArr, 0, bArr.length));
        }
        stringBuffer.append("\n");
        string = stringBuffer.toString();
        stringBuffer2.append(string);
        return stringBuffer2.toString();
    }

    private void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        Util.copyStream(inputStream, outputStream, null);
    }

    private void a(InputStream inputStream, OutputStream outputStream, ExtByteArrayOutputStream extByteArrayOutputStream, int i, byte[] bArr) throws IOException {
        int i2;
        int size = extByteArrayOutputStream.size();
        if (size > 0 && (i2 = inputStream.read(bArr, 0, i - size)) != -1) {
            if (i2 + size < i) {
                extByteArrayOutputStream.write(bArr, 0, i2);
                return;
            }
            outputStream.write(4);
            DerCoder.a((ASN1Object) null, i, outputStream);
            outputStream.write(extByteArrayOutputStream.getInternalByteArray(), 0, size);
            outputStream.write(bArr, 0, i2);
            extByteArrayOutputStream.reset();
        }
        while (true) {
            int i3 = inputStream.read(bArr);
            if (i3 == -1) {
                return;
            }
            if (i3 < i) {
                extByteArrayOutputStream.write(bArr, 0, i3);
            } else {
                outputStream.write(4);
                DerCoder.a((ASN1Object) null, i3, outputStream);
                outputStream.write(bArr, 0, i3);
            }
        }
    }

    private void a(OutputStream outputStream, OCTET_STRING octet_string) throws IOException {
        if (!octet_string.isConstructed()) {
            byte[] bArr = octet_string.a;
            if (bArr != null) {
                outputStream.write(bArr);
                return;
            } else {
                a(octet_string.b, outputStream);
                return;
            }
        }
        InputStream inputStream = octet_string.b;
        if (inputStream != null) {
            a(inputStream, outputStream);
            return;
        }
        Enumeration components = octet_string.getComponents();
        while (components.hasMoreElements()) {
            a(outputStream, (OCTET_STRING) components.nextElement());
        }
    }

    private void a(OutputStream outputStream, OCTET_STRING octet_string, ExtByteArrayOutputStream extByteArrayOutputStream, int i, byte[] bArr) throws IOException {
        if (!octet_string.isConstructed()) {
            byte[] bArr2 = octet_string.a;
            if (bArr2 != null) {
                a(new ByteArrayInputStream(bArr2), outputStream, extByteArrayOutputStream, i, bArr);
                return;
            } else {
                a(octet_string.b, outputStream, extByteArrayOutputStream, i, bArr);
                return;
            }
        }
        InputStream inputStream = octet_string.b;
        if (inputStream != null) {
            a(inputStream, outputStream, extByteArrayOutputStream, i, bArr);
            return;
        }
        Enumeration components = octet_string.getComponents();
        while (components.hasMoreElements()) {
            a(outputStream, (OCTET_STRING) components.nextElement(), extByteArrayOutputStream, i, bArr);
        }
    }

    @Override // iaik.asn1.ConstructedType
    public synchronized void addComponent(ASN1Object aSN1Object, int i) {
        if (!(aSN1Object instanceof OCTET_STRING)) {
            throw new IllegalArgumentException("Only instances of OCTET_STRING are allowed to be added to an OCTET_STRING object!");
        }
        if (this.a != null || this.b != null) {
            throw new IllegalArgumentException("Cannot add component to a simple OCTET_STRING object!");
        }
        super.addComponent(aSN1Object, i);
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    public Object clone() {
        OCTET_STRING octet_string = (OCTET_STRING) super.clone();
        byte[] bArr = this.a;
        if (bArr != null) {
            octet_string.a = (byte[]) bArr.clone();
        } else {
            InputStream inputStream = this.b;
            if (inputStream != null) {
                try {
                    byte[] stream = Util.readStream(inputStream);
                    octet_string.a = stream;
                    this.a = stream;
                    this.b = null;
                } catch (IOException e) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Error reading input stream for cloning: ");
                    stringBuffer.append(e);
                    throw new RuntimeException(stringBuffer.toString());
                }
            }
        }
        return octet_string;
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws CodingException, IOException {
        try {
            if (this.constructed) {
                super.decode(i, inputStream);
            } else {
                if (i < 0) {
                    throw new IOException("DER decode ERROR: indefinite length encoding not allowed for primitive octet strings!");
                }
                byte[] bArr = new byte[i];
                this.a = bArr;
                Util.fillArray(bArr, inputStream);
                this.constructed = false;
            }
        } catch (OutOfMemoryError unused) {
            throw new IOException("Not enough memory for decoding ASN.1 OCTET STRING value!");
        }
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        int i;
        byte[] bArr = this.a;
        if (bArr != null) {
            if (this.d <= 0) {
                outputStream.write(bArr);
                return;
            }
            InputStream inputStream = this.b;
            if (inputStream == null) {
                this.b = new ByteArrayInputStream(this.a);
            } else {
                ((ByteArrayInputStream) inputStream).reset();
            }
            if (!this.stream_mode) {
                byte[] bArr2 = new byte[this.d];
                ExtByteArrayOutputStream extByteArrayOutputStream = new ExtByteArrayOutputStream();
                while (true) {
                    int iA = a(this.b, bArr2);
                    if (iA < 0) {
                        outputStream.write(extByteArrayOutputStream.getInternalByteArray(), 0, extByteArrayOutputStream.size());
                        return;
                    } else if (iA > 0) {
                        extByteArrayOutputStream.write(4);
                        DerCoder.a((ASN1Object) null, iA, extByteArrayOutputStream);
                        extByteArrayOutputStream.write(bArr2, 0, iA);
                    }
                }
            }
        }
        if (this.constructed && this.b == null) {
            super.encode(outputStream);
            return;
        }
        if (this.b == null) {
            return;
        }
        if (!this.constructed) {
            if (this.stream_mode) {
                Util.copyStream(this.b, outputStream, null);
                return;
            } else {
                outputStream.write(Util.readStream(this.b));
                return;
            }
        }
        if (!this.stream_mode || (i = this.d) <= 0) {
            byte[] stream = Util.readStream(this.b);
            outputStream.write(stream);
            DerCoder.a((ASN1Object) null, stream.length, outputStream);
            outputStream.write(4);
            return;
        }
        byte[] bArr3 = new byte[i];
        while (true) {
            int iA2 = a(this.b, bArr3);
            if (iA2 < 0) {
                return;
            }
            if (iA2 > 0) {
                outputStream.write(4);
                DerCoder.a((ASN1Object) null, iA2, outputStream);
                outputStream.write(bArr3, 0, iA2);
            }
        }
    }

    public void encodeAsIndefiniteConstructedOctetString(OutputStream outputStream, int i) throws IOException {
        if (i <= 0) {
            i = this.d;
        }
        int i2 = i <= 0 ? 1024 : i;
        outputStream.write(36);
        outputStream.write(128);
        ExtByteArrayOutputStream extByteArrayOutputStream = new ExtByteArrayOutputStream(i2);
        a(outputStream, this, extByteArrayOutputStream, i2, new byte[i2]);
        int size = extByteArrayOutputStream.size();
        if (size > 0) {
            outputStream.write(4);
            DerCoder.a((ASN1Object) null, size, outputStream);
            outputStream.write(extByteArrayOutputStream.getInternalByteArray(), 0, extByteArrayOutputStream.size());
        }
        outputStream.write(0);
        outputStream.write(0);
    }

    public int getBlockSize() {
        return this.d;
    }

    public OCTET_STRING getSimpleOctetString() throws IOException {
        return (this.b == null || countComponents() != 0) ? new OCTET_STRING(getWholeValue()) : new OCTET_STRING(this.b);
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    public Object getValue() {
        try {
            return getWholeValue();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error reading octet string value: ");
            stringBuffer.append(e.toString());
            throw new InternalErrorException(stringBuffer.toString(), e);
        }
    }

    public byte[] getWholeValue() throws IOException {
        byte[] byteArray = this.a;
        if (byteArray == null && (this.b != null || this.content_count > 0)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            a(byteArrayOutputStream, this);
            byteArray = byteArrayOutputStream.toByteArray();
        }
        return byteArray == null ? new byte[0] : byteArray;
    }

    @Override // iaik.asn1.ASN1Object
    public void setIndefiniteLength(boolean z) {
        this.indefinite_length = z;
        if (this.d > 0) {
            this.indefinite_length = true;
        }
        if (isConstructed()) {
            return;
        }
        if (this.a == null && this.b == null) {
            return;
        }
        this.indefinite_length = false;
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.a = null;
        this.b = null;
        this.content_data = null;
        this.content_count = 0;
        if (obj instanceof byte[]) {
            this.a = (byte[]) obj;
            this.indefinite_length = false;
            this.constructed = false;
            this.d = -1;
            return;
        }
        if (obj instanceof InputStream) {
            this.b = (InputStream) obj;
            if (this.d == -1) {
                this.indefinite_length = false;
                this.constructed = false;
            }
        }
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        String string;
        StringBuffer stringBuffer;
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(super.toString());
        if (!this.constructed) {
            if (this.a != null) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append(this.a.length);
                stringBuffer3.append(" bytes: ");
                stringBuffer3.append(Util.toString(this.a, 0, 5));
                stringBuffer2.append(stringBuffer3.toString());
                if (this.a.length > 5) {
                    string = "...";
                }
            } else {
                stringBuffer2.append(this.b);
            }
            return stringBuffer2.toString();
        }
        if (this.content_count > 0) {
            stringBuffer = new StringBuffer();
            stringBuffer.append(this.content_count);
            stringBuffer.append(" elements");
        } else {
            stringBuffer = new StringBuffer();
            stringBuffer.append("block size: ");
            stringBuffer.append(this.d);
            stringBuffer.append(" source: ");
            stringBuffer.append(this.b);
        }
        string = stringBuffer.toString();
        stringBuffer2.append(string);
        return stringBuffer2.toString();
    }

    public String toString(boolean z) {
        return a(true, "");
    }

    public void writeWholeValueToStream(OutputStream outputStream) throws IOException {
        byte[] bArr = this.a;
        if (bArr != null) {
            outputStream.write(bArr);
        } else {
            a(outputStream, this);
        }
    }
}

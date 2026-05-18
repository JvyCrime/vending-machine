package iaik.asn1;

import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class BIT_STRING extends ASN1Object {
    protected int bits_not_valid;
    protected byte[] value;

    protected BIT_STRING() {
        this.asnType = ASN.BIT_STRING;
        this.bits_not_valid = 0;
    }

    public BIT_STRING(String str) {
        this();
        int length = str.length();
        this.bits_not_valid = (8 - (length % 8)) % 8;
        this.value = new byte[(length + 7) / 8];
        int i = 0;
        while (i < length) {
            byte[] bArr = this.value;
            int i2 = i / 8;
            bArr[i2] = (byte) (bArr[i2] << 1);
            if (str.charAt(i) == '1') {
                byte[] bArr2 = this.value;
                bArr2[i2] = (byte) (bArr2[i2] + 1);
            }
            i++;
        }
        int i3 = this.bits_not_valid;
        if (i3 > 0) {
            byte[] bArr3 = this.value;
            int i4 = i / 8;
            bArr3[i4] = (byte) (bArr3[i4] << i3);
        }
    }

    public BIT_STRING(byte[] bArr) {
        this();
        this.value = bArr;
        this.bits_not_valid = 0;
    }

    public BIT_STRING(byte[] bArr, int i) {
        this();
        if (i < -1 || i > 7) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("bitsNotValid (");
            stringBuffer.append(i);
            stringBuffer.append(") out of range! ");
            stringBuffer.append("Must be between -1 and 7");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.value = bArr;
        if (i == -1) {
            int length = bArr.length;
            i = 0;
            while (length > 0 && this.value[length - 1] == 0) {
                length--;
            }
            if (length > 0) {
                int i2 = this.value[length - 1] & 255;
                while ((i2 & 1) == 0) {
                    i2 >>= 1;
                    i++;
                }
            }
        }
        this.bits_not_valid = i;
    }

    public BIT_STRING(boolean[] zArr) {
        this();
        int length = zArr.length;
        this.bits_not_valid = 8 - (length % 8);
        this.value = new byte[(length + 7) / 8];
        int i = 0;
        while (i < length) {
            byte[] bArr = this.value;
            int i2 = i / 8;
            bArr[i2] = (byte) (bArr[i2] << 1);
            if (zArr[i]) {
                bArr[i2] = (byte) (bArr[i2] + 1);
            }
            i++;
        }
        int i3 = this.bits_not_valid;
        if (i3 > 0) {
            byte[] bArr2 = this.value;
            int i4 = i / 8;
            bArr2[i4] = (byte) (bArr2[i4] << i3);
        }
    }

    public int bitsNotValid() {
        return this.bits_not_valid;
    }

    @Override // iaik.asn1.ASN1Object
    public Object clone() {
        BIT_STRING bit_string = (BIT_STRING) super.clone();
        byte[] bArr = this.value;
        if (bArr != null) {
            bit_string.value = (byte[]) bArr.clone();
        }
        return bit_string;
    }

    @Override // iaik.asn1.ASN1Object
    protected void decode(int i, InputStream inputStream) throws IOException {
        try {
            this.bits_not_valid = inputStream.read();
            byte[] bArr = new byte[i - 1];
            this.value = bArr;
            Util.fillArray(bArr, inputStream);
        } catch (OutOfMemoryError unused) {
            throw new IOException("Not enough memory for decoding ASN.1 BIT STRING value!");
        }
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        outputStream.write(this.value);
        outputStream.write(this.bits_not_valid);
    }

    public String getBinaryString() {
        int i;
        StringBuffer stringBuffer = new StringBuffer();
        int i2 = 0;
        while (true) {
            i = 7;
            if (i2 >= this.value.length - 1) {
                break;
            }
            while (i >= 0) {
                if ((this.value[i2] & (1 << i)) != 0) {
                    stringBuffer.append('1');
                } else {
                    stringBuffer.append('0');
                }
                i--;
            }
            i2++;
        }
        while (i >= this.bits_not_valid) {
            if ((this.value[i2] & (1 << i)) != 0) {
                stringBuffer.append('1');
            } else {
                stringBuffer.append('0');
            }
            i--;
        }
        return stringBuffer.toString();
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return this.value;
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        this.value = (byte[]) obj;
        this.bits_not_valid = 0;
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(this.value.length);
        stringBuffer.append(" byte(s); ");
        stringBuffer.append(this.bits_not_valid);
        stringBuffer.append(" bit(s) not valid");
        return stringBuffer.toString();
    }
}

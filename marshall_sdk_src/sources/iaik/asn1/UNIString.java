package iaik.asn1;

import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class UNIString extends ASN1String {
    protected UNIString() {
        this.asnType = ASN.UNIString;
    }

    public UNIString(String str) {
        this();
        setValue(str);
    }

    static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i += 4) {
            int i2 = 0;
            for (int i3 = 0; i3 < 4; i3++) {
                i2 |= bArr[i + i3] & 255;
            }
            stringBuffer.append((char) i2);
        }
        return stringBuffer.toString();
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return a(this.value);
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        byte[] aSCIIBytes = Util.toASCIIBytes((String) obj);
        this.value = new byte[aSCIIBytes.length * 4];
        for (int i = 0; i < aSCIIBytes.length; i++) {
            this.value[(i * 4) + 3] = aSCIIBytes[i];
        }
    }
}

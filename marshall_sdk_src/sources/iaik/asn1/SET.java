package iaik.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class SET extends ConstructedType {
    protected boolean sorted;

    public SET() {
        this(false);
    }

    public SET(boolean z) {
        this.sorted = z;
        this.asnType = ASN.SET;
        this.constructed = true;
    }

    private static void a(byte[][] bArr) {
        boolean z;
        int length = bArr.length;
        do {
            z = false;
            for (int i = 1; i < length; i++) {
                int i2 = i - 1;
                if (!a(bArr[i2], bArr[i])) {
                    byte[] bArr2 = bArr[i2];
                    bArr[i2] = bArr[i];
                    bArr[i] = bArr2;
                    z = true;
                }
            }
        } while (z);
    }

    private static boolean a(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        if (length > bArr2.length) {
            length = bArr2.length;
        }
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return (bArr[i] & 255) > (bArr2[i] & 255);
            }
        }
        return bArr.length >= bArr2.length;
    }

    @Override // iaik.asn1.ConstructedType, iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        if (!this.sorted) {
            super.encode(outputStream);
            return;
        }
        int i = this.content_count;
        byte[][] bArr = new byte[i][];
        if (this.stream_mode) {
            int i2 = 0;
            int i3 = 0;
            while (i2 < this.content_count) {
                c cVar = new c(1024);
                DerCoder.encodeTo(this.content_data[i2], cVar, false);
                cVar.flush();
                bArr[i3] = cVar.toByteArray();
                i2++;
                i3++;
            }
        } else {
            int i4 = this.content_count - 1;
            int i5 = 0;
            while (i4 >= 0) {
                c cVar2 = new c(1024);
                DerCoder.a(this.content_data[i4], cVar2);
                cVar2.flush();
                bArr[i5] = cVar2.toByteArray();
                i4--;
                i5++;
            }
        }
        a(bArr);
        for (int i6 = 0; i6 < i; i6++) {
            outputStream.write(bArr[i6]);
        }
        outputStream.flush();
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append(this.content_count);
        stringBuffer.append(" elements");
        return stringBuffer.toString();
    }
}

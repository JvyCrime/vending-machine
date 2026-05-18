package iaik.asn1;

import com.bitmick.marshall.vmc.marshall_t;
import iaik.security.md.Md5;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class ASN1 implements Cloneable {
    public static final int DER = 1;
    public static final int PEM = 2;
    private static final byte[] a = {45, 45, 45, 45, 45, marshall_t.status_vpos_please_remove_card, marshall_t.status_vpos_try_again, marshall_t.status_vpos_present_card, 73, marshall_t.status_vpos_try_another_card};
    public static final String startLine = "-----BEGIN";
    private ASN1Object b;
    private byte[] c;
    private int d;

    public ASN1() {
        this.d = 1;
    }

    public ASN1(ASN1Object aSN1Object) throws CodingException {
        this.d = 1;
        if (aSN1Object == null) {
            throw new CodingException("Cannot parse an ASN1 object from a null object.");
        }
        this.b = aSN1Object;
    }

    public ASN1(InputStream inputStream) throws IOException, CodingException {
        this(inputStream, true);
    }

    ASN1(InputStream inputStream, boolean z) throws IOException, CodingException {
        this.d = 1;
        if (inputStream == null) {
            throw new IOException("Cannot parse an ASN1 object from a null input stream!");
        }
        a(inputStream, z);
    }

    public ASN1(byte[] bArr) throws CodingException {
        this.d = 1;
        if (bArr == null) {
            throw new CodingException("Cannot parse an ASN1 object from a null byte array!");
        }
        try {
            a(new ByteArrayInputStream(bArr));
        } catch (IOException e) {
            throw new CodingException(this, e.getMessage(), e) { // from class: iaik.asn1.ASN1.1
                private static final long serialVersionUID = -5532766842768993177L;
                private final IOException a;
                private final ASN1 b;

                {
                    this.b = this;
                    this.a = e;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        }
    }

    private static int a(byte[] bArr) {
        int i;
        int i2 = 0;
        if ((bArr[0] & 255 & 31) == 31) {
            return -1;
        }
        int i3 = bArr[1] & 255;
        int i4 = 2;
        if (i3 < 128) {
            return i3 + 2;
        }
        if (i3 == 128 || (i = i3 & 127) < 1 || i > 4) {
            return -1;
        }
        while (true) {
            int i5 = i - 1;
            if (i <= 0) {
                return i2 + i4;
            }
            i2 = (i2 << 8) | (bArr[i4] & 255);
            i4++;
            i = i5;
        }
    }

    private static int a(byte[] bArr, int i, int[] iArr) throws CodingException {
        int i2 = i + 1;
        int i3 = bArr[i] & 255;
        if (i3 <= 127) {
            iArr[0] = i2;
            return i3;
        }
        if (i3 == 128) {
            iArr[0] = i2;
            int i4 = i2;
            while (true) {
                try {
                    if (bArr[i4] != 0) {
                        i4++;
                    } else {
                        int i5 = i4 + 1;
                        if (bArr[i5] == 0) {
                            return i4 - i2;
                        }
                        i4 = i5;
                    }
                } catch (Exception e) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Error decoding ASN1 indefinite format: ");
                    stringBuffer.append(e.toString());
                    throw new CodingException(stringBuffer.toString());
                }
            }
        } else {
            int i6 = i3 & 127;
            if (i6 > 4) {
                throw new CodingException("Too large ASN.1 object");
            }
            int i7 = 0;
            while (true) {
                i6--;
                if (i6 < 0) {
                    iArr[0] = i2;
                    return i7;
                }
                i7 = (i7 << 8) | (bArr[i2] & 255);
                i2++;
            }
        }
    }

    private static String a(ASN1Object aSN1Object, String str) {
        String strConcat = str.concat("  ");
        if (aSN1Object == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(aSN1Object.toString());
        stringBuffer.append("\n");
        if (aSN1Object instanceof ConstructedType) {
            for (int i = 0; i < aSN1Object.countComponents(); i++) {
                try {
                    stringBuffer.append(strConcat);
                    stringBuffer.append(a(aSN1Object.getComponentAt(i), strConcat));
                } catch (CodingException unused) {
                }
            }
        }
        return stringBuffer.toString();
    }

    private void a(InputStream inputStream) throws IOException, CodingException {
        a(inputStream, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void a(java.io.InputStream r11, boolean r12) throws java.io.IOException, iaik.asn1.CodingException {
        /*
            r10 = this;
            java.io.PushbackInputStream r0 = new java.io.PushbackInputStream
            r1 = 20
            r0.<init>(r11, r1)
            r11 = 10
            byte[] r1 = new byte[r11]
            int r2 = r0.read(r1)
            r3 = -1
            if (r2 == r3) goto La1
            r4 = 6
            if (r2 < r4) goto L1a
            int r4 = a(r1)
            goto L1b
        L1a:
            r4 = -1
        L1b:
            byte[] r5 = iaik.asn1.ASN1.a
            boolean r5 = iaik.utils.CryptoUtils.equalsBlock(r1, r5)
            r6 = 2
            r7 = 1
            r8 = 13
            if (r5 == 0) goto L48
        L27:
            int r1 = r0.read()
            if (r1 == r8) goto L31
            if (r1 == r11) goto L31
            if (r1 != r3) goto L27
        L31:
            if (r1 == r3) goto L40
            if (r1 != r8) goto L38
            r0.read()
        L38:
            r10.d = r6
            iaik.utils.Base64InputStream r1 = new iaik.utils.Base64InputStream
            r1.<init>(r0)
            goto L6a
        L40:
            java.io.IOException r11 = new java.io.IOException
            java.lang.String r12 = "Unexpected EOF in encoding!"
            r11.<init>(r12)
            throw r11
        L48:
            r9 = 0
            r0.unread(r1, r9, r2)
            int r1 = r0.read()
            r0.unread(r1)
            r2 = 65
            if (r1 < r2) goto L5b
            r2 = 77
            if (r1 <= r2) goto L63
        L5b:
            r2 = 103(0x67, float:1.44E-43)
            if (r1 < r2) goto L71
            r2 = 122(0x7a, float:1.71E-43)
            if (r1 > r2) goto L71
        L63:
            r10.d = r6
            iaik.utils.Base64InputStream r1 = new iaik.utils.Base64InputStream
            r1.<init>(r0)
        L6a:
            r2 = r1
            iaik.utils.Base64InputStream r2 = (iaik.utils.Base64InputStream) r2
            r2.setIgnoreInvalidCharacters(r7)
            goto L74
        L71:
            r10.d = r7
            r1 = r0
        L74:
            iaik.utils.TracedInputStream r2 = new iaik.utils.TracedInputStream
            r2.<init>(r1, r4)
            iaik.asn1.ASN1Object r1 = iaik.asn1.DerCoder.a(r2, r12)
            r10.b = r1
            byte[] r1 = r2.getTracedData()
            r10.c = r1
            if (r12 != 0) goto L8a
            r12 = 0
            r10.b = r12
        L8a:
            if (r5 == 0) goto La0
            r1 = 10
            r0.skip(r1)
        L91:
            int r12 = r0.read()
            if (r12 == r8) goto L9b
            if (r12 == r11) goto L9b
            if (r12 != r3) goto L91
        L9b:
            if (r12 != r8) goto La0
            r0.read()
        La0:
            return
        La1:
            java.io.EOFException r11 = new java.io.EOFException
            r11.<init>()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.asn1.ASN1.a(java.io.InputStream, boolean):void");
    }

    public static String print(ASN1Object aSN1Object) {
        return a(aSN1Object, "");
    }

    public static byte[] readEncoded(InputStream inputStream) throws IOException, CodingException {
        return new ASN1(inputStream, false).toByteArray();
    }

    public void clearASN1Object() {
        toByteArray();
        this.b = null;
    }

    public void clearByteArray() {
        toASN1Object();
        this.c = null;
    }

    public Object clone() {
        ASN1 asn1 = null;
        try {
            ASN1 asn12 = (ASN1) super.clone();
            try {
                ASN1Object aSN1Object = this.b;
                if (aSN1Object != null) {
                    asn12.b = (ASN1Object) aSN1Object.clone();
                }
                byte[] bArr = this.c;
                if (bArr != null) {
                    asn12.c = (byte[]) bArr.clone();
                }
                asn12.d = this.d;
                return asn12;
            } catch (CloneNotSupportedException unused) {
                asn1 = asn12;
                return asn1;
            }
        } catch (CloneNotSupportedException unused2) {
        }
    }

    public int countComponents() throws CodingException {
        return toASN1Object().countComponents();
    }

    public byte[] fingerprint() {
        Md5 md5 = new Md5();
        md5.update(toByteArray());
        return md5.digest();
    }

    public ASN1Object getComponentAt(int i) throws CodingException {
        return toASN1Object().getComponentAt(i);
    }

    public byte[] getFirstObject() throws CodingException {
        byte[] byteArray = toByteArray();
        if (byteArray == null) {
            throw new CodingException("Cannot parse data from a null object!");
        }
        if (byteArray[0] != 48) {
            throw new CodingException("getFirstObject: No SEQUENCE!");
        }
        int[] iArr = new int[1];
        a(byteArray, 1, iArr);
        int i = iArr[0];
        if (byteArray[i] != 48) {
            throw new CodingException("getFirstObject: No SEQUENCE in a SEQUENCE!");
        }
        int iA = a(byteArray, i + 1, iArr);
        byte[] bArr = new byte[(iArr[0] + iA) - i];
        System.arraycopy(byteArray, i, bArr, 0, (iA + iArr[0]) - i);
        return bArr;
    }

    public int getFormat() {
        return this.d;
    }

    public ASN1Object toASN1Object() {
        try {
            if (this.b == null) {
                byte[] bArr = this.c;
                if (bArr == null) {
                    throw new NullPointerException("ASN1 array not set");
                }
                this.b = DerCoder.decode(bArr);
            }
            return this.b;
        } catch (CodingException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public byte[] toByteArray() {
        if (this.c == null) {
            ASN1Object aSN1Object = this.b;
            if (aSN1Object == null) {
                return null;
            }
            this.c = DerCoder.encode(aSN1Object);
        }
        return this.c;
    }

    public String toString() {
        return a(toASN1Object(), "");
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(toByteArray());
    }
}

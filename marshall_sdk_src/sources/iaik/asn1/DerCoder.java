package iaik.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class DerCoder {
    private static boolean a = false;
    private static final byte[] b = {0, 0};

    private DerCoder() {
    }

    static int a(InputStream inputStream) throws IOException, CodingException {
        int i = inputStream.read();
        if (i == -1) {
            throw new IOException("Error in length encoding: Unexpected EOF!");
        }
        int i2 = i & 255;
        if (i2 <= 127) {
            return i2;
        }
        if (i2 == 128) {
            if (a) {
                System.out.println("Indefinite format detected.");
            }
            return -1;
        }
        int i3 = i2 & 127;
        if (i3 > 4) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Length: Too large ASN.1 object: ");
            stringBuffer.append(i3);
            throw new CodingException(stringBuffer.toString());
        }
        int i4 = 0;
        while (true) {
            i3--;
            if (i3 < 0) {
                return i4;
            }
            i4 = (i4 << 8) | (inputStream.read() & 255);
        }
    }

    static ASN1Object a(InputStream inputStream, boolean z) throws CodingException, IOException {
        try {
            return a(new b(inputStream), new int[1], null, z);
        } catch (OutOfMemoryError unused) {
            throw new CodingException("Not enough memory for decoding ASN.1 object!");
        }
    }

    static ASN1Object a(InputStream inputStream, int[] iArr, ASN1Object aSN1Object, boolean z) throws IOException, CodingException {
        int i;
        ASN1Object aSN1ObjectCreate = aSN1Object;
        try {
            int iA = ((b) inputStream).a();
            int i2 = inputStream.read();
            if (i2 == -1) {
                throw new CodingException("Cannot read tag. Unexpected EOF!");
            }
            int i3 = i2 & 255;
            boolean z2 = (i3 & 32) > 0;
            int i4 = i3 & 192;
            int i5 = 128;
            if (i4 == 64) {
                i5 = 64;
            } else if (i4 != 128) {
                i5 = i4 != 192 ? 0 : 192;
            }
            int i6 = i3 & 31;
            if (i6 == 31) {
                i6 = 0;
                do {
                    int i7 = i6 << 7;
                    i = inputStream.read();
                    if (i == -1) {
                        throw new CodingException("Unexpected EOF when reading long tag!");
                    }
                    i6 = i7 | (i & 127);
                } while ((i & 128) != 0);
            }
            int iA2 = a(inputStream);
            if (a && iA2 < 0) {
                PrintStream printStream = System.out;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("INFINITE LENGTH - tag: ");
                stringBuffer.append(i6);
                printStream.println(stringBuffer.toString());
            }
            if (aSN1ObjectCreate == null) {
                try {
                    aSN1ObjectCreate = ASN.create(new ASN(i6, "Decoded value", i5));
                } catch (InstantiationException unused) {
                    if (i5 != 192 && i5 != 64) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Invalid ASN.1 object: Unknown tag class/value: ");
                        stringBuffer2.append(i5);
                        stringBuffer2.append("/");
                        stringBuffer2.append(i6);
                        throw new CodingException(stringBuffer2.toString());
                    }
                    aSN1ObjectCreate = ASN.create(new ASN(0, "Unknown Tag"));
                    if (aSN1ObjectCreate instanceof UNKNOWN) {
                        ((UNKNOWN) aSN1ObjectCreate).b = new ASN(i6, "Unknown Tag", i5);
                    }
                }
                aSN1ObjectCreate.constructed = z2;
                if (aSN1ObjectCreate instanceof ConstructedType) {
                    ((ConstructedType) aSN1ObjectCreate).c = z;
                }
                aSN1ObjectCreate.decode(iA2, inputStream);
                iArr[0] = ((b) inputStream).a() - iA;
            } else {
                aSN1ObjectCreate.asnType = new ASN(i6, "Decoded value", i5);
                aSN1ObjectCreate.constructed = z2;
            }
            if (iA2 < 0) {
                aSN1ObjectCreate.indefinite_length = true;
            }
            return aSN1ObjectCreate;
        } catch (IOException e) {
            throw e;
        } catch (Exception e2) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("ASN.1 creation error: ");
            stringBuffer3.append(e2.toString());
            throw new CodingException(stringBuffer3.toString(), e2) { // from class: iaik.asn1.DerCoder.2
                private static final long serialVersionUID = -2750278872950301254L;
                private final Exception a;

                {
                    this.a = e2;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        }
    }

    static ASN1Object a(InputStream inputStream, int[] iArr, boolean z) throws CodingException, IOException {
        return a(inputStream, iArr, null, z);
    }

    static void a(ASN1Object aSN1Object, int i, OutputStream outputStream) throws IOException {
        if (aSN1Object != null && aSN1Object.indefiniteLength()) {
            outputStream.write(128);
            return;
        }
        if (i >= 0 && i <= 127) {
            outputStream.write(i);
            return;
        }
        byte[] bArr = new byte[5];
        int i2 = 1;
        for (int i3 = 3; i3 >= 0; i3--) {
            byte b2 = (byte) ((i >>> (i3 * 8)) & 255);
            if (b2 != 0 || i2 != 1) {
                bArr[i2] = b2;
                i2++;
            }
        }
        bArr[0] = (byte) ((i2 - 1) | 128);
        outputStream.write(bArr, 0, i2);
    }

    static void a(ASN1Object aSN1Object, OutputStream outputStream) throws IOException {
        a(aSN1Object, outputStream, false);
    }

    static void a(ASN1Object aSN1Object, OutputStream outputStream, boolean z) throws IOException {
        if (!z && !(aSN1Object instanceof EncodedASN1Object)) {
            c(aSN1Object, outputStream);
        }
        c cVar = (c) outputStream;
        int size = cVar.size();
        aSN1Object.encodeObject(outputStream, false);
        if (z || (aSN1Object instanceof EncodedASN1Object)) {
            return;
        }
        a(aSN1Object, cVar.size() - size, outputStream);
        b(aSN1Object, outputStream);
    }

    private static byte[] a(ASN1Object aSN1Object, boolean z) {
        c cVar = new c(8192);
        synchronized (aSN1Object) {
            try {
                a(aSN1Object, cVar, z);
            } catch (IOException e) {
                throw new RuntimeException(e.toString());
            }
        }
        return cVar.toByteArray();
    }

    static void b(ASN1Object aSN1Object, OutputStream outputStream) throws IOException {
        ASN baseAsnType = aSN1Object instanceof UNKNOWN ? ((UNKNOWN) aSN1Object).getBaseAsnType() : aSN1Object.getAsnType();
        int i = baseAsnType.tag;
        int i2 = i <= 30 ? i : 31;
        if (aSN1Object.isConstructed()) {
            i2 |= 32;
        }
        int i3 = baseAsnType.tag_class | i2;
        if (i > 30) {
            if (!(outputStream instanceof c)) {
                outputStream.write(i3);
                int i4 = 5;
                byte[] bArr = new byte[5];
                while (i != 0) {
                    i4--;
                    bArr[i4] = (byte) ((i & 127) | 128);
                    i >>>= 7;
                }
                bArr[4] = (byte) (bArr[4] & ByteCompanionObject.MAX_VALUE);
                outputStream.write(bArr, i4, 5 - i4);
                return;
            }
            int i5 = 0;
            while (i != 0) {
                outputStream.write(i5 | (i & 127));
                i >>>= 7;
                i5 = 128;
            }
        }
        outputStream.write(i3);
    }

    static void c(ASN1Object aSN1Object, OutputStream outputStream) throws IOException {
        if (aSN1Object.indefiniteLength()) {
            if (!(aSN1Object instanceof CON_SPEC) || !((CON_SPEC) aSN1Object).isImplicitlyTagged()) {
                outputStream.write(b);
                return;
            }
            Object value = aSN1Object.getValue();
            if ((value instanceof EncodedASN1Object) && ((EncodedASN1Object) value).indefiniteLength()) {
                return;
            }
            outputStream.write(b);
        }
    }

    public static ASN1Object decode(InputStream inputStream) throws CodingException, IOException {
        return a(inputStream, true);
    }

    public static ASN1Object decode(byte[] bArr) throws CodingException {
        if (bArr == null || bArr.length == 0) {
            throw new CodingException("Cannot decode null data!");
        }
        try {
            return a(new b(new ByteArrayInputStream(bArr)), new int[1], null, true);
        } catch (IOException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error reading ASN.1 datastructure: ");
            stringBuffer.append(e.toString());
            throw new CodingException(stringBuffer.toString(), e) { // from class: iaik.asn1.DerCoder.1
                private static final long serialVersionUID = -1887369766602152882L;
                private final IOException a;

                {
                    this.a = e;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        } catch (OutOfMemoryError unused) {
            throw new CodingException("Not enough memory for decoding ASN.1 object!");
        }
    }

    public static byte[] encode(ASN1Object aSN1Object) {
        c cVar = new c(8192);
        synchronized (aSN1Object) {
            try {
                a(aSN1Object, cVar);
            } catch (IOException e) {
                throw new RuntimeException(e.toString());
            }
        }
        return cVar.toByteArray();
    }

    public static void encodeTo(ASN1Object aSN1Object, OutputStream outputStream) throws IOException {
        encodeTo(aSN1Object, outputStream, false);
        outputStream.flush();
    }

    protected static void encodeTo(ASN1Object aSN1Object, OutputStream outputStream, boolean z) throws IOException {
        if (!aSN1Object.indefiniteLength()) {
            outputStream.write(a(aSN1Object, z));
            return;
        }
        if (aSN1Object instanceof EncodedASN1Object) {
            aSN1Object.encodeObject(outputStream, true);
            return;
        }
        if (!z) {
            b(aSN1Object, outputStream);
            outputStream.write(128);
        }
        aSN1Object.encodeObject(outputStream, true);
        if (z) {
            return;
        }
        if (!(aSN1Object instanceof CON_SPEC) || !((CON_SPEC) aSN1Object).isImplicitlyTagged()) {
            outputStream.write(b);
            return;
        }
        Object value = aSN1Object.getValue();
        if ((value instanceof EncodedASN1Object) && ((EncodedASN1Object) value).indefiniteLength()) {
            return;
        }
        outputStream.write(b);
    }
}

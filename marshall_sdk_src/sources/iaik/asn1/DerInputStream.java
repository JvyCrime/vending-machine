package iaik.asn1;

import iaik.utils.ExtByteArrayOutputStream;
import iaik.utils.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public class DerInputStream extends InputStream {
    public static final int APPLICATION = 64;
    public static final int BIT_STRING = 3;
    public static final int BMPString = 30;
    public static final int BOOLEAN = 1;
    public static final int CONSTRUCTED = 32;
    public static final int CONTEXT_SPECIFIC = 128;
    public static final int ENUMERATED = 10;
    public static final int EXTERNAL = 8;
    public static final int GeneralString = 27;
    public static final int GeneralizedTime = 24;
    public static final int IA5String = 22;
    public static final int INTEGER = 2;
    public static final int NULL = 5;
    public static final int NumericString = 18;
    public static final int OBJECT_DESCRIPTOR = 7;
    public static final int OBJECT_ID = 6;
    public static final int OCTET_STRING = 4;
    public static final int PRIVATE = 192;
    public static final int PrintableString = 19;
    public static final int SEQUENCE = 16;
    public static final int SET = 17;
    public static final int T61String = 20;
    public static final int UNIString = 28;
    public static final int UNIVERSAL = 0;
    public static final int UTCTime = 23;
    public static final int UTF8String = 12;
    public static final int VisibleString = 26;
    int a;
    boolean b;
    InputStream c;
    int d;
    DerInputStream e;
    InputStream f;

    static class a extends InputStream {
        private byte[] a;
        private int b;
        private int c;
        private final DerInputStream f;
        private InputStream g;
        private ExtByteArrayOutputStream i;
        private final boolean j;
        private boolean d = false;
        private boolean e = false;
        private int h = 0;

        public a(DerInputStream derInputStream, boolean z) {
            this.f = derInputStream;
            this.j = z;
        }

        private int a() throws IOException {
            if (this.d) {
                return -1;
            }
            byte[] octetStringByteArray = this.f.readOctetStringByteArray();
            this.a = octetStringByteArray;
            if (octetStringByteArray == null) {
                this.d = true;
                return -1;
            }
            int length = octetStringByteArray.length;
            this.c = length;
            this.b = 0;
            return length;
        }

        private int a(ExtByteArrayOutputStream extByteArrayOutputStream, int i) throws IOException {
            int size = extByteArrayOutputStream.size();
            int i2 = 0;
            if (i <= size) {
                return 0;
            }
            int i3 = this.f.read();
            extByteArrayOutputStream.write(i3);
            int i4 = i3 & 255;
            if (i4 == 128) {
                throw new DerInputException("DER decode ERROR: indefinite length encoding not allowed for primitive octet strings!");
            }
            if (i4 < 128) {
                return i4;
            }
            int i5 = size + 1;
            int i6 = i4 & 127;
            if (i6 > 4) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Length: Too large ASN.1 object: ");
                stringBuffer.append(i6);
                throw new DerInputException(stringBuffer.toString());
            }
            if (i6 > i - i5) {
                return -1;
            }
            while (true) {
                i6--;
                if (i6 < 0) {
                    return i2;
                }
                int i7 = this.f.read();
                if (i7 == -1) {
                    throw new EOFException("Unexpected EOF while reading length field!");
                }
                extByteArrayOutputStream.write(i7);
                i2 = (i2 << 8) | (i7 & 255);
            }
        }

        private int b() throws IOException {
            int i = this.f.read();
            if (i == -1) {
                this.e = true;
            } else if (i == 0) {
                if (this.f.a >= 0) {
                    throw new DerInputException("Found EOC 0 tag but no indefinite length encoding!");
                }
                if (this.f.read() != 0) {
                    throw new DerInputException("Indefinite length: second EOC octet not 0!");
                }
                this.f.a(new byte[]{0, 0}, 2);
                this.e = true;
            } else if (i != 4) {
                throw new DerInputException("Not an octet string!");
            }
            return i;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            if (this.d || this.e) {
                return 0;
            }
            if (this.j) {
                if (this.i == null) {
                    this.i = new ExtByteArrayOutputStream(32);
                }
                while (this.c == 0 && this.f.available() != 0) {
                    if (this.g == null) {
                        this.g = this.f.a();
                    }
                    int iAvailable = this.g.available();
                    if (iAvailable <= 1) {
                        break;
                    }
                    if (this.h == 0) {
                        this.i.reset();
                        int iB = b();
                        if (iB == 4) {
                            this.i.write(iB);
                            int iA = a(this.i, iAvailable);
                            if (iA > -1) {
                                this.h = this.i.size() + iA;
                            }
                            this.f.a(this.i.getInternalByteArray(), this.i.size());
                        }
                    }
                    int i = this.h;
                    if (i <= 0 || i > iAvailable) {
                        break;
                    }
                    a();
                    this.h = 0;
                    if (this.d || this.e) {
                        break;
                    }
                }
            } else {
                int iAvailable2 = this.f.available();
                this.c = iAvailable2;
                if (iAvailable2 != 0) {
                    if (this.g == null) {
                        this.g = this.f.a();
                    }
                    int iAvailable3 = this.g.available();
                    if (iAvailable3 >= 0) {
                        int i2 = this.c;
                        if (i2 <= 0 || iAvailable3 < i2) {
                            this.c = iAvailable3;
                        }
                    } else if (this.c < 0) {
                        this.c = 0;
                    }
                }
            }
            return this.c;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.f.close();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (!this.j) {
                int i = this.f.read();
                if (i == -1) {
                    this.f.a(true);
                }
                return i;
            }
            do {
                int i2 = this.c;
                if (i2 > 0) {
                    this.c = i2 - 1;
                    byte[] bArr = this.a;
                    int i3 = this.b;
                    this.b = i3 + 1;
                    return bArr[i3] & 255;
                }
            } while (a() != -1);
            return -1;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            if (!this.j) {
                int i3 = this.f.read(bArr, i, i2);
                if (i3 == -1) {
                    this.f.a(true);
                }
                return i3;
            }
            int i4 = 0;
            do {
                int i5 = this.c;
                if (i5 >= i2) {
                    System.arraycopy(this.a, this.b, bArr, i + i4, i2);
                    this.c -= i2;
                    this.b += i2;
                    return i4 + i2;
                }
                if (i5 > 0) {
                    System.arraycopy(this.a, this.b, bArr, i + i4, i5);
                    int i6 = this.c;
                    i2 -= i6;
                    i4 += i6;
                    this.c = 0;
                }
            } while (a() != -1);
            if (i4 == 0) {
                return -1;
            }
            return i4;
        }
    }

    private DerInputStream(DerInputStream derInputStream, int i, int i2) {
        this.b = false;
        this.c = derInputStream;
        this.a = i;
        this.d = i2;
        derInputStream.e = this;
        this.f = derInputStream.a();
    }

    public DerInputStream(InputStream inputStream) {
        this.b = false;
        this.a = -2;
        this.d = -2;
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, 32);
        this.c = pushbackInputStream;
        this.f = pushbackInputStream;
    }

    private void b() throws IOException {
        InputStream inputStream;
        if (this.b) {
            return;
        }
        int i = this.a;
        if (i == -1) {
            int i2 = read();
            if (i2 > 0) {
                a(i2);
                return;
            }
            if (i2 == 0) {
                if (this.a >= 0) {
                    throw new DerInputException("Found 0 tag but no indefinite length encoding!");
                }
                int i3 = read();
                this.b = true;
                if (i3 != 0) {
                    if (i3 != -1) {
                        throw new DerInputException("Indefinite length: second byte not 0!");
                    }
                    throw new EOFException("Indefinite length: Missing second EOC byte!");
                }
            }
            inputStream = this.c;
            if (!(inputStream instanceof DerInputStream)) {
                return;
            }
        } else {
            if (i != 0) {
                return;
            }
            inputStream = this.c;
            if (!(inputStream instanceof DerInputStream)) {
                return;
            }
        }
        ((DerInputStream) inputStream).b();
    }

    private boolean b(boolean z) throws IOException {
        DerInputStream derInputStream = this.e;
        if (derInputStream != null) {
            derInputStream.readEOC();
        }
        int i = read(true);
        if (i == -1 && z) {
            throw new EOFException();
        }
        return (i & 192) == 0;
    }

    private int c() throws IOException {
        int i = read();
        if (i == -1) {
            throw new EOFException("Unexpected EOF while reading length field!");
        }
        int i2 = i & 255;
        if (i2 < 128) {
            return i2;
        }
        if (i2 == 128) {
            return -1;
        }
        int i3 = i2 & 127;
        if (i3 > 4) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Length: Too large ASN.1 object: ");
            stringBuffer.append(i3);
            throw new DerInputException(stringBuffer.toString());
        }
        int i4 = 0;
        while (true) {
            i3--;
            if (i3 < 0) {
                return i4;
            }
            int i5 = read();
            if (i5 == -1) {
                throw new EOFException("Unexpected EOF while reading length field!");
            }
            i4 = (i4 << 8) | (i5 & 255);
        }
    }

    private boolean c(boolean z) throws IOException {
        DerInputStream derInputStream = this.e;
        if (derInputStream != null) {
            derInputStream.readEOC();
        }
        int i = read(true);
        if (i == -1 && z) {
            throw new EOFException();
        }
        return (i & 192) == 64;
    }

    private boolean d(boolean z) throws IOException {
        DerInputStream derInputStream = this.e;
        if (derInputStream != null) {
            derInputStream.readEOC();
        }
        int i = read(true);
        if (i == -1 && z) {
            throw new EOFException();
        }
        return (i & 192) == 128;
    }

    private boolean e(boolean z) throws IOException {
        DerInputStream derInputStream = this.e;
        if (derInputStream != null) {
            derInputStream.readEOC();
        }
        int i = read(true);
        if (i == -1 && z) {
            throw new EOFException();
        }
        return (i & 192) == 192;
    }

    private boolean f(boolean z) throws IOException {
        DerInputStream derInputStream = this.e;
        if (derInputStream != null) {
            derInputStream.readEOC();
        }
        int i = read(true);
        if (i == -1 && z) {
            throw new EOFException();
        }
        return (i & 32) == 32;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    int a(boolean r9) throws java.io.IOException {
        /*
            r8 = this;
            iaik.asn1.DerInputStream r0 = r8.e
            if (r0 == 0) goto L7
            r0.readEOC()
        L7:
            boolean r0 = r8.b
            r1 = -1
            if (r0 == 0) goto Ld
            return r1
        Ld:
            int r0 = r8.read()
            r2 = 0
            r3 = 1
            if (r0 != r1) goto L18
            r1 = r0
        L16:
            r2 = 1
            goto L64
        L18:
            if (r0 != 0) goto L35
            int r9 = r8.a
            if (r9 >= 0) goto L2d
            int r9 = r8.read()
            if (r9 != 0) goto L25
            goto L16
        L25:
            iaik.asn1.DerInputException r9 = new iaik.asn1.DerInputException
            java.lang.String r0 = "Indefinite length: second fin byte not 0!"
            r9.<init>(r0)
            throw r9
        L2d:
            iaik.asn1.DerInputException r9 = new iaik.asn1.DerInputException
            java.lang.String r0 = "Found EOC 0 tag but no indefinite length encoding!"
            r9.<init>(r0)
            throw r9
        L35:
            r1 = r0 & 31
            r4 = 31
            if (r1 != r4) goto L5f
            r1 = 10
            byte[] r4 = new byte[r1]
            byte r0 = (byte) r0
            r4[r2] = r0
            r0 = 0
            r1 = 1
        L44:
            int r0 = r0 << 7
            int r5 = r8.read()
            int r6 = r1 + 1
            byte r7 = (byte) r5
            r4[r1] = r7
            r1 = r5 & 127(0x7f, float:1.78E-43)
            r0 = r0 | r1
            r1 = r5 & 128(0x80, float:1.8E-43)
            if (r1 != 0) goto L5d
            if (r9 == 0) goto L5b
            r8.a(r4, r6)
        L5b:
            r1 = r0
            goto L64
        L5d:
            r1 = r6
            goto L44
        L5f:
            if (r9 == 0) goto L64
            r8.a(r0)
        L64:
            if (r2 == 0) goto L72
            int r9 = r8.a
            r0 = -2
            if (r9 == r0) goto L72
            java.io.InputStream r9 = r8.c
            iaik.asn1.DerInputStream r9 = (iaik.asn1.DerInputStream) r9
            r9.b()
        L72:
            if (r2 == 0) goto L76
            r8.b = r3
        L76:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.asn1.DerInputStream.a(boolean):int");
    }

    InputStream a() {
        return this.f;
    }

    void a(int i) throws IOException {
        InputStream inputStream = this.c;
        if (inputStream instanceof PushbackInputStream) {
            ((PushbackInputStream) inputStream).unread(i);
        } else {
            ((DerInputStream) inputStream).a(i);
        }
        int i2 = this.a;
        if (i2 >= 0) {
            this.a = i2 + 1;
        }
    }

    void a(byte[] bArr, int i) throws IOException {
        InputStream inputStream = this.c;
        if (inputStream instanceof PushbackInputStream) {
            ((PushbackInputStream) inputStream).unread(bArr, 0, i);
        } else {
            ((DerInputStream) inputStream).a(bArr, i);
        }
        int i2 = this.a;
        if (i2 >= 0) {
            this.a = i2 + i;
        }
    }

    @Override // java.io.InputStream
    public int available() {
        if (this.b) {
            return 0;
        }
        return this.a;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.c.close();
    }

    public int getTag() {
        return this.d;
    }

    public boolean nextIsApplication() throws IOException {
        return c(false);
    }

    public boolean nextIsConstructed() throws IOException {
        return f(false);
    }

    public boolean nextIsContextSpecific() throws IOException {
        return d(false);
    }

    public boolean nextIsPrivate() throws IOException {
        return e(false);
    }

    public boolean nextIsUniversal() throws IOException {
        return b(false);
    }

    public int nextTag() throws IOException {
        return a(true);
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (!this.b) {
            int i = this.a;
            if (i >= 0) {
                if (i > 0) {
                    this.a = i - 1;
                }
            }
            return this.c.read();
        }
        return -1;
    }

    public int read(boolean z) throws IOException {
        int i = read();
        if (z && i != -1) {
            a(i);
        }
        return i;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (!this.b) {
            int i3 = this.a;
            if (i3 < 0) {
                return this.c.read(bArr, i, i2);
            }
            if (i3 > 0) {
                int iMin = Math.min(i2, i3);
                this.a -= iMin;
                int i4 = iMin;
                while (i4 > 0) {
                    int i5 = this.c.read(bArr, i, i4);
                    if (i5 < 0) {
                        throw new EOFException();
                    }
                    i4 -= i5;
                    i += i5;
                }
                return iMin;
            }
        }
        return -1;
    }

    public BIT_STRING readBitString() throws IOException {
        int iA = a(false);
        if (iA != 3) {
            if (iA == -1) {
                throw new EOFException("Unexpected EOF when trying to read BIT STRING tag!");
            }
            throw new DerInputException("Next ASN.1 object is no BIT_STRING!");
        }
        BIT_STRING bit_string = new BIT_STRING();
        bit_string.decode(c(), this);
        return bit_string;
    }

    public boolean readBoolean() throws IOException {
        int iA = a(false);
        if (iA != 1) {
            if (iA == -1) {
                throw new EOFException("Unexpected EOF when trying to read BOOLEAN tag!");
            }
            throw new DerInputException("Next ASN.1 object is no BOOLEAN!");
        }
        if (c() == 1) {
            return read() != 0;
        }
        throw new IOException("Boolean length not 1!");
    }

    public DerInputStream readConstructed() throws IOException {
        if (!f(true)) {
            throw new DerInputException("Next ASN.1 object is not CONSTRUCTED!");
        }
        return new DerInputStream(this, c(), a(false));
    }

    public int readContextSpecific(int i) throws IOException {
        if (!d(true)) {
            throw new DerInputException("Next ASN.1 object is no CONTEXT SPECIFIC!");
        }
        if (f(true)) {
            i |= 32;
        }
        int iA = a(false);
        a(i);
        return iA;
    }

    public DerInputStream readContextSpecific() throws IOException {
        if (!d(true)) {
            throw new DerInputException("Next ASN.1 object is no CONTEXT SPECIFIC!");
        }
        return new DerInputStream(this, c(), a(false));
    }

    public void readEOC() throws IOException {
        if (this.b || this.a != -1) {
            return;
        }
        int i = read();
        if (i != 0) {
            if (i != -1) {
                throw new DerInputException("Indefinite length encoding not closed by EOC!");
            }
            throw new EOFException("Indefinite length: Missing EOC octets!");
        }
        int i2 = read();
        if (i2 == -1) {
            throw new EOFException("Indefinite length: Missing second EOC byte!");
        }
        if (i2 != 0) {
            throw new DerInputException("Indefinite length: second byte not 0!");
        }
        ((DerInputStream) this.c).b();
        this.b = true;
    }

    public GeneralizedTime readGeneralizedTime() throws IOException {
        int iA = a(false);
        if (iA != 24) {
            if (iA == -1) {
                throw new EOFException("Unexpected EOF when trying to read GeneralizedTime tag!");
            }
            throw new DerInputException("Next ASN.1 object is no GeneralizedTime!");
        }
        GeneralizedTime generalizedTime = new GeneralizedTime();
        generalizedTime.decode(c(), this);
        return generalizedTime;
    }

    public BigInteger readInteger() throws IOException {
        int iA = a(false);
        if (iA != 2) {
            if (iA == -1) {
                throw new EOFException("Unexpected EOF when trying to read INTEGER tag!");
            }
            throw new DerInputException("Next ASN.1 object is no INTEGER!");
        }
        try {
            byte[] bArr = new byte[c()];
            Util.fillArray(bArr, this);
            return new BigInteger(bArr);
        } catch (OutOfMemoryError unused) {
            throw new IOException("Not enough memory for decoding ASN.1 INTEGER!");
        }
    }

    public void readNull() throws IOException {
        int iA = a(false);
        if (iA == 5) {
            c();
        } else {
            if (iA != -1) {
                throw new DerInputException("Next ASN.1 object is not NULL!");
            }
            throw new EOFException("Unexpected EOF when trying to read NULL tag!");
        }
    }

    public ObjectID readObjectID() throws IOException {
        int iA = a(false);
        if (iA != 6) {
            if (iA == -1) {
                throw new EOFException("Unexpected EOF when trying to read OID tag!");
            }
            throw new DerInputException("Next ASN.1 object is no OBJECT IDENTIFIER!");
        }
        ObjectID objectID = new ObjectID();
        objectID.decode(c(), this);
        return objectID;
    }

    public InputStream readOctetString() throws IOException {
        DerInputStream derInputStream = this.e;
        if (derInputStream != null) {
            derInputStream.readEOC();
        }
        int i = read();
        if (i == -1) {
            return null;
        }
        if ((i & 31) != 4) {
            throw new DerInputException("Next ASN.1 object is no OCTET_STRING!");
        }
        int iC = c();
        if ((i & 32) != 0) {
            return f(false) ? new DerInputStream(this, iC, i) : new a(new DerInputStream(this, iC, i), true);
        }
        if (iC != -1) {
            return new a(new DerInputStream(this, iC, i), false);
        }
        throw new DerInputException("DER decode ERROR: indefinite length encoding not allowed for primitive octet strings!");
    }

    public InputStream readOctetString(boolean z) throws IOException {
        if (!z) {
            return readOctetString();
        }
        DerInputStream derInputStream = this;
        while (true) {
            InputStream octetString = derInputStream.readOctetString();
            if (!(octetString instanceof DerInputStream)) {
                return octetString;
            }
            derInputStream = (DerInputStream) octetString;
        }
    }

    public byte[] readOctetStringByteArray() throws IOException {
        int iA = a(false);
        if (iA == -1) {
            return null;
        }
        if (iA != 4) {
            throw new DerInputException("Next ASN.1 object is no OCTET_STRING!");
        }
        try {
            int iC = c();
            if (iC < 0) {
                throw new DerInputException("DER decode ERROR: indefinite length encoding not allowed for primitive octet strings!");
            }
            byte[] bArr = new byte[iC];
            Util.fillArray(bArr, this);
            return bArr;
        } catch (OutOfMemoryError unused) {
            throw new IOException("Not enough memory for decoding ASN.1 OCTET STRING!");
        }
    }

    public DerInputStream readSequence() throws IOException {
        int iA = a(false);
        if (iA == 16) {
            return new DerInputStream(this, c(), iA);
        }
        if (iA == -1) {
            throw new EOFException("Unexpected EOF when trying to read SEQUENCE tag!");
        }
        throw new DerInputException("Next ASN.1 object is no SEQUENCE!");
    }

    public DerInputStream readSet() throws IOException {
        int iA = a(false);
        if (iA == 17) {
            return new DerInputStream(this, c(), iA);
        }
        if (iA == -1) {
            throw new EOFException("Unexpected EOF when trying to read SET tag!");
        }
        throw new DerInputException("Next ASN.1 object is no SET!");
    }

    public String readString() throws IOException {
        int iA = a(false);
        if (iA == -1) {
            throw new EOFException("Unexpected EOF when trying to read String tag!");
        }
        try {
            byte[] bArr = new byte[c()];
            Util.fillArray(bArr, this);
            if (iA == 12) {
                try {
                    return UTF8String.getStringFromUTF8Encoding(bArr);
                } catch (CodingException e) {
                    throw new DerInputException(e.getMessage());
                }
            }
            if (iA != 22) {
                if (iA == 30) {
                    return new String(bArr, "UnicodeBig");
                }
                switch (iA) {
                    case 18:
                    case 19:
                    case 20:
                        break;
                    default:
                        switch (iA) {
                            case 26:
                            case 27:
                                break;
                            case 28:
                                return UNIString.a(bArr);
                            default:
                                throw new DerInputException("Next ASN.1 object is no STRING type!");
                        }
                        break;
                }
            }
            return Util.toASCIIString(bArr);
        } catch (OutOfMemoryError unused) {
            throw new IOException("Not enough memory for decoding ASN.1 String value!");
        }
    }

    public UTCTime readUTCTime() throws IOException {
        int iA = a(false);
        if (iA != 23) {
            if (iA == -1) {
                throw new EOFException("Unexpected EOF when trying to read UTCTime tag!");
            }
            throw new DerInputException("Next ASN.1 object is no UTC TIME!");
        }
        UTCTime uTCTime = new UTCTime();
        uTCTime.decode(c(), this);
        return uTCTime;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        int i = this.a;
        if (i < 0) {
            return this.c.skip(j);
        }
        int iMin = Math.min((int) j, i);
        long j2 = iMin;
        this.c.skip(j2);
        this.a -= iMin;
        return j2;
    }

    public int skipObjects(int i) throws Throwable {
        if (i < 0) {
            i = Integer.MAX_VALUE;
        }
        DerInputStream derInputStream = null;
        int i2 = 0;
        while (i2 < i) {
            try {
                int iA = a(false);
                this.d = iA;
                if (iA <= 0) {
                    break;
                }
                int iC = c();
                if (iC >= 0) {
                    skip(iC);
                } else {
                    DerInputStream derInputStream2 = new DerInputStream(this, iC, this.d);
                    try {
                        derInputStream2.skipObjects(-1);
                        derInputStream = derInputStream2;
                    } catch (Throwable th) {
                        th = th;
                        derInputStream = derInputStream2;
                        if (derInputStream != null) {
                            try {
                                derInputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        throw th;
                    }
                }
                i2++;
            } catch (Throwable th2) {
                th = th2;
            }
        }
        if (derInputStream != null) {
            try {
                derInputStream.close();
            } catch (IOException unused2) {
            }
        }
        return i2;
    }
}

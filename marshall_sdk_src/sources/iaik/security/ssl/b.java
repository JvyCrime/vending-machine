package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class b extends aj {
    private int a;
    private int b;

    private static int c(int i) {
        if (i == 22 || i == 51 || i == 60 || i == 70 || i == 80) {
            return 40;
        }
        return i;
    }

    b(int i, int i2, int i3) {
        this(i2, a(i, i3));
    }

    b(int i, int i2) {
        super(21);
        this.a = i;
        this.b = i2;
    }

    b(ab abVar) throws IOException {
        super(21);
        a(abVar);
    }

    int b() {
        return this.a;
    }

    int a() {
        return this.b;
    }

    void a(ab abVar) throws IOException {
        this.a = abVar.k();
        this.b = abVar.k();
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(this.a);
        agVar.g(this.b);
    }

    private static int a(int i, int i2) {
        if (i >= 769) {
            return i2;
        }
        if (i == 768) {
            return c(i2);
        }
        StringBuffer stringBuffer = new StringBuffer("Invalid protocol version ");
        stringBuffer.append(Utils.toString(i));
        throw new RuntimeException(stringBuffer.toString());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int i = this.a;
        if (i == 1) {
            stringBuffer.append("Alert Warning: ");
        } else if (i == 2) {
            stringBuffer.append("Alert Fatal: ");
        } else {
            StringBuffer stringBuffer2 = new StringBuffer("Unknown alert level ");
            stringBuffer2.append(this.a);
            stringBuffer2.append(":");
            stringBuffer.append(stringBuffer2.toString());
        }
        int i2 = this.b;
        if (i2 == 0) {
            stringBuffer.append("close notify");
        } else if (i2 == 10) {
            stringBuffer.append("unexpected message");
        } else if (i2 == 30) {
            stringBuffer.append("decompression failure");
        } else if (i2 == 60) {
            stringBuffer.append("export restriction");
        } else if (i2 == 80) {
            stringBuffer.append("internal error");
        } else if (i2 == 86) {
            stringBuffer.append("inappropriate fallback");
        } else if (i2 == 90) {
            stringBuffer.append("user canceled");
        } else if (i2 == 100) {
            stringBuffer.append("no renegotiation");
        } else if (i2 == 70) {
            stringBuffer.append("protocol version");
        } else if (i2 != 71) {
            switch (i2) {
                case 20:
                    stringBuffer.append("bad record mac");
                    break;
                case 21:
                    stringBuffer.append("decryption failed");
                    break;
                case 22:
                    stringBuffer.append("record overflow");
                    break;
                default:
                    switch (i2) {
                        case 40:
                            stringBuffer.append("handshake failure");
                            break;
                        case 41:
                            stringBuffer.append("no certificate");
                            break;
                        case 42:
                            stringBuffer.append("bad certificate");
                            break;
                        case 43:
                            stringBuffer.append("unsupported certificate");
                            break;
                        case 44:
                            stringBuffer.append("certificate revoked");
                            break;
                        case 45:
                            stringBuffer.append("certificate expired");
                            break;
                        case 46:
                            stringBuffer.append("certificate unknown");
                            break;
                        case 47:
                            stringBuffer.append("illegal parameter");
                            break;
                        case 48:
                            stringBuffer.append("unknown ca");
                            break;
                        case 49:
                            stringBuffer.append("access denied");
                            break;
                        case 50:
                            stringBuffer.append("decode error");
                            break;
                        case 51:
                            stringBuffer.append("decrypt error");
                            break;
                        default:
                            switch (i2) {
                                case 110:
                                    stringBuffer.append("unsupported extension");
                                    break;
                                case 111:
                                    stringBuffer.append("certificate unobtainable");
                                    break;
                                case 112:
                                    stringBuffer.append("unrecognized name");
                                    break;
                                case 113:
                                    stringBuffer.append("bad certificate status response");
                                    break;
                                case 114:
                                    stringBuffer.append("bad certificate hash value");
                                    break;
                                case 115:
                                    stringBuffer.append("unknown psk identity");
                                    break;
                                default:
                                    StringBuffer stringBuffer3 = new StringBuffer("Unknown alert type ");
                                    stringBuffer3.append(this.b);
                                    stringBuffer.append(stringBuffer3.toString());
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            stringBuffer.append("insufficient security");
        }
        return stringBuffer.toString();
    }
}

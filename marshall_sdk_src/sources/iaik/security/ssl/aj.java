package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
abstract class aj {
    private int a;

    abstract void a(ag agVar) throws IOException;

    aj(int i) {
        this.a = i;
    }

    static String a(int i) {
        if (i == 257) {
            return "SSLv2 message";
        }
        switch (i) {
            case 20:
                return "change cipher spec";
            case 21:
                return "alert";
            case 22:
                return "handshake";
            case 23:
                return "application data";
            default:
                StringBuffer stringBuffer = new StringBuffer("(unknown: ");
                stringBuffer.append(i);
                stringBuffer.append(")");
                return stringBuffer.toString();
        }
    }

    static String b(int i) {
        switch (i) {
            case 0:
                return "error";
            case 1:
                return "client hello";
            case 2:
                return "client master key";
            case 3:
                return "client finish";
            case 4:
                return "server hello";
            case 5:
                return "server verify";
            case 6:
                return "server finish";
            case 7:
                return "request certificate";
            case 8:
                return "client certificate";
            default:
                StringBuffer stringBuffer = new StringBuffer("(unknown: ");
                stringBuffer.append(i);
                stringBuffer.append(")");
                return stringBuffer.toString();
        }
    }
}

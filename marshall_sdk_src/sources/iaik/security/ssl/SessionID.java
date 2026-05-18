package iaik.security.ssl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class SessionID implements Serializable {
    private byte[] a;

    SessionID() {
        this.a = new byte[0];
    }

    SessionID(Random random) {
        byte[] bArr = new byte[32];
        this.a = bArr;
        random.nextBytes(bArr);
    }

    SessionID(ab abVar) throws IOException {
        this.a = abVar.l();
    }

    SessionID(byte[] bArr) {
        this.a = bArr;
    }

    void a(ag agVar) throws IOException {
        agVar.c(this.a);
    }

    public byte[] getID() {
        return (byte[]) this.a.clone();
    }

    int a() {
        return this.a.length;
    }

    boolean b() {
        byte[] bArr = this.a;
        return bArr == null || bArr.length == 0;
    }

    public int hashCode() {
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.a;
            if (i >= bArr.length) {
                return i2;
            }
            int i3 = i2 + (bArr[i] & 255);
            i2 = (i3 >> 29) | (i3 << 3);
            i++;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SessionID) {
            return Utils.equalsBlock(this.a, ((SessionID) obj).a);
        }
        return false;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("id: ");
        stringBuffer.append(Utils.toString(this.a));
        return stringBuffer.toString();
    }

    String c() {
        byte[] bArr = this.a;
        int length = bArr.length;
        if (length > 8) {
            length = 8;
        }
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(Utils.a(bArr, 0, length)));
        stringBuffer.append("...");
        return stringBuffer.toString();
    }
}

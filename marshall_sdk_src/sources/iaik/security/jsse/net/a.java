package iaik.security.jsse.net;

import iaik.security.jsse.utils.Debug;
import iaik.security.ssl.SessionID;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class a {
    private static Debug a = Debug.getInstance();
    private Hashtable b = new Hashtable(100);
    private Hashtable c = new Hashtable(100);

    public Vector a(String str) {
        return (Vector) this.b.get(str);
    }

    public SSLSessionWrapper a(byte[] bArr) {
        return (SSLSessionWrapper) this.c.get(new b(bArr));
    }

    public void a(String str, Vector vector) {
        if (vector == null) {
            return;
        }
        for (int i = 0; i < vector.size(); i++) {
            SSLSessionWrapper sSLSessionWrapper = (SSLSessionWrapper) vector.elementAt(i);
            this.c.put(new b(((SessionID) sSLSessionWrapper.b().getID()).getID()), sSLSessionWrapper);
        }
        this.b.put(str, vector);
    }

    public void b(String str) {
        if (str == null) {
            return;
        }
        Vector vector = (Vector) this.b.remove(str);
        Debug debug = a;
        StringBuffer stringBuffer = new StringBuffer("DoubleHash: removeWithKey1( ");
        stringBuffer.append(str);
        stringBuffer.append(") corresponding sessions: ");
        debug.println(stringBuffer.toString());
        if (a.getDebugMode() == 0) {
            if (a.getDebugMode() == 0) {
                for (int i = 0; i < vector.size(); i++) {
                }
            }
            a.println("DoubleHash: remove corresponding sessions in table2: ");
        }
        if (vector == null) {
            return;
        }
        for (int i2 = 0; i2 < vector.size(); i2++) {
            SessionID sessionID = (SessionID) ((SSLSessionWrapper) vector.elementAt(i2)).b().getID();
            Debug debug2 = a;
            StringBuffer stringBuffer2 = new StringBuffer("****************\n");
            stringBuffer2.append(sessionID);
            debug2.println(stringBuffer2.toString());
            this.c.remove(new b(sessionID.getID()));
        }
    }

    public Enumeration a() {
        return this.b.keys();
    }

    public Enumeration b() {
        return this.c.keys();
    }

    public void b(b bVar) {
    }

    protected SSLSessionWrapper a(b bVar) {
        return (SSLSessionWrapper) this.c.get(bVar);
    }
}

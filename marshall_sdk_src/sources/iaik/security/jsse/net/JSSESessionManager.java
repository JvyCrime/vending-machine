package iaik.security.jsse.net;

import iaik.security.jsse.utils.Debug;
import iaik.security.ssl.SSLTransport;
import iaik.security.ssl.Session;
import iaik.security.ssl.SessionManager;
import java.util.Enumeration;
import java.util.Vector;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;

/* JADX INFO: loaded from: classes.dex */
public class JSSESessionManager extends SessionManager implements SSLSessionContext {
    private static Debug a = Debug.getInstance();
    private long b = -1;
    private iaik.security.jsse.net.a c = new iaik.security.jsse.net.a();
    private long d = 1200000;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private Vector h;

    static iaik.security.jsse.net.a a(JSSESessionManager jSSESessionManager) {
        return jSSESessionManager.c;
    }

    public void setResumePeriod(long j) {
        this.d = j * 1000;
    }

    public long getResumePeriod() {
        return this.d / 1000;
    }

    @Override // javax.net.ssl.SSLSessionContext
    public void setSessionTimeout(int i) {
        setResumePeriod(i);
    }

    @Override // javax.net.ssl.SSLSessionContext
    public int getSessionTimeout() {
        return (int) getResumePeriod();
    }

    @Override // javax.net.ssl.SSLSessionContext
    public synchronized void setSessionCacheSize(int i) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException("Session cache size must not be < 0!");
        }
        if (this.h == null) {
            this.h = new Vector(i);
        }
        this.g = i;
        if (i == 0) {
            this.h.removeAllElements();
        } else {
            a();
        }
    }

    @Override // javax.net.ssl.SSLSessionContext
    public synchronized int getSessionCacheSize() {
        return this.g;
    }

    private Vector a(String str) {
        Vector vectorA = this.c.a(str);
        return vectorA == null ? new Vector(2) : vectorA;
    }

    @Override // iaik.security.ssl.SessionManager
    protected synchronized void cacheSession(SSLTransport sSLTransport, Session session) {
        a();
        SSLSessionWrapper sSLSessionWrapper = new SSLSessionWrapper(session, this, sSLTransport);
        String str = (String) sSLTransport.getRemotePeerId();
        if (str == null) {
            return;
        }
        Vector vectorA = a(str);
        if (!sSLTransport.getUseClientMode()) {
            if (!vectorA.contains(sSLSessionWrapper)) {
                vectorA.addElement(sSLSessionWrapper);
                if (this.g > 0) {
                    this.h.addElement(sSLSessionWrapper);
                }
                this.e++;
                this.c.a(str, vectorA);
            }
        } else if (vectorA.size() == 0) {
            vectorA.addElement(sSLSessionWrapper);
            if (this.g > 0) {
                this.h.addElement(sSLSessionWrapper);
            }
            this.e++;
            this.c.a(str, vectorA);
        } else {
            vectorA.setElementAt(sSLSessionWrapper, 0);
            if (this.g > 0 && !this.h.contains(sSLSessionWrapper)) {
                this.h.addElement(sSLSessionWrapper);
            }
            this.c.a(str, vectorA);
        }
    }

    @Override // iaik.security.ssl.SessionManager
    protected synchronized Session getSession(SSLTransport sSLTransport, Object obj) {
        a();
        String str = (String) sSLTransport.getRemotePeerId();
        if (str == null) {
            a.println("\n ***** JSSESessionManager RETURNING NULL *****\n");
            return null;
        }
        Enumeration enumerationElements = a(str).elements();
        while (enumerationElements.hasMoreElements()) {
            SSLSessionWrapper sSLSessionWrapper = (SSLSessionWrapper) enumerationElements.nextElement();
            if (a(sSLSessionWrapper.b(), obj)) {
                sSLSessionWrapper.a(System.currentTimeMillis());
                return sSLSessionWrapper.b();
            }
        }
        a.println("\n ***** JSSESessionManager RETURNING NULL *****\n");
        return null;
    }

    private boolean a(Session session, Object obj) {
        if (!session.isValid() || !a(session)) {
            return false;
        }
        if (obj == null) {
            return true;
        }
        return obj.equals(session.getID());
    }

    private void a() {
        boolean z;
        boolean z2;
        Vector vectorA;
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = this.g;
        if (i <= 0 || this.e < i) {
            z = false;
            z2 = false;
        } else {
            z = true;
            z2 = true;
        }
        if (!z) {
            if (jCurrentTimeMillis - this.b < 300000) {
                Debug debug = a;
                StringBuffer stringBuffer = new StringBuffer("JSSESessionManager: no Deletion of old Sessions(EXPUNGE_INTERVAL > ");
                stringBuffer.append(jCurrentTimeMillis - this.b);
                stringBuffer.append(")");
                debug.println(stringBuffer.toString());
            } else {
                z = true;
            }
        }
        if (z) {
            this.b = jCurrentTimeMillis;
            Enumeration enumerationA = this.c.a();
            while (enumerationA.hasMoreElements()) {
                String str = (String) enumerationA.nextElement();
                Vector vectorA2 = this.c.a(str);
                a.println("JSSESessionManager: deleteOldSessions:");
                for (int size = vectorA2.size() - 1; size >= 0; size--) {
                    SSLSessionWrapper sSLSessionWrapper = (SSLSessionWrapper) vectorA2.elementAt(size);
                    if (!sSLSessionWrapper.b().isValid() || !a(sSLSessionWrapper.b(), jCurrentTimeMillis)) {
                        vectorA2.removeElementAt(size);
                        this.c.b(new b(sSLSessionWrapper.getId()));
                        if (this.g > 0) {
                            this.h.removeElement(sSLSessionWrapper);
                        }
                        this.e--;
                        this.f++;
                    }
                }
                if (vectorA2.size() == 0) {
                    Debug debug2 = a;
                    StringBuffer stringBuffer2 = new StringBuffer("JSSESessionManager: all sessions deleted for: ");
                    stringBuffer2.append(str);
                    debug2.println(stringBuffer2.toString());
                    this.c.b(str);
                }
            }
            Enumeration enumerationB = this.c.b();
            while (enumerationB.hasMoreElements()) {
                b bVar = (b) enumerationB.nextElement();
                SSLSessionWrapper sSLSessionWrapperA = this.c.a(bVar);
                if (!sSLSessionWrapperA.b().isValid() || !a(sSLSessionWrapperA.b(), jCurrentTimeMillis)) {
                    this.c.b(bVar);
                    if (this.g > 0) {
                        this.h.removeElement(sSLSessionWrapperA);
                    }
                }
            }
            if (z2) {
                while (this.e >= this.g && this.h.size() > 0) {
                    SSLSessionWrapper sSLSessionWrapper2 = (SSLSessionWrapper) this.h.firstElement();
                    String strA = sSLSessionWrapper2.a();
                    if (strA != null && (vectorA = this.c.a(strA)) != null && vectorA.removeElement(sSLSessionWrapper2)) {
                        this.e--;
                        this.f++;
                        if (vectorA.size() == 0) {
                            Debug debug3 = a;
                            StringBuffer stringBuffer3 = new StringBuffer("JSSESessionManager: all sessions deleted for: ");
                            stringBuffer3.append(strA);
                            debug3.println(stringBuffer3.toString());
                            this.c.b(strA);
                        }
                    }
                    this.c.b(new b(sSLSessionWrapper2.getId()));
                    this.h.removeElementAt(0);
                }
            }
        }
    }

    static boolean a(JSSESessionManager jSSESessionManager, Session session) {
        return jSSESessionManager.a(session);
    }

    private boolean a(Session session) {
        return a(session, System.currentTimeMillis());
    }

    private boolean a(Session session, long j) {
        if (j - session.getCreationTime() <= this.d) {
            return true;
        }
        session.invalidate();
        return false;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer("Active sessions in cache: ");
        stringBuffer2.append(this.e);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer("Removed sessions: ");
        stringBuffer3.append(this.f);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        Enumeration enumerationA = this.c.a();
        while (enumerationA.hasMoreElements()) {
            Object objNextElement = enumerationA.nextElement();
            stringBuffer.append(objNextElement);
            stringBuffer.append(" ->\n");
            Vector vectorA = this.c.a((String) objNextElement);
            Debug debug = a;
            StringBuffer stringBuffer4 = new StringBuffer("Vector corresponding to ");
            stringBuffer4.append(objNextElement);
            stringBuffer4.append("\nhas size ");
            stringBuffer4.append(vectorA.size());
            debug.println(stringBuffer4.toString());
            Enumeration enumerationElements = vectorA.elements();
            while (enumerationElements.hasMoreElements()) {
                Session sessionB = ((SSLSessionWrapper) enumerationElements.nextElement()).b();
                stringBuffer.append("  ");
                if (!sessionB.isValid() || !a(sessionB)) {
                    stringBuffer.append("Invalid/Timed out: ");
                }
                stringBuffer.append(sessionB.getID());
                stringBuffer.append('\n');
            }
        }
        return stringBuffer.toString();
    }

    @Override // javax.net.ssl.SSLSessionContext
    public Enumeration getIds() {
        return new a(this, this.c.b());
    }

    @Override // javax.net.ssl.SSLSessionContext
    public SSLSession getSession(byte[] bArr) {
        SSLSessionWrapper sSLSessionWrapperA = this.c.a(bArr);
        if (sSLSessionWrapperA == null) {
            return sSLSessionWrapperA;
        }
        Session sessionB = sSLSessionWrapperA.b();
        if (sessionB.isValid() && a(sessionB)) {
            return sSLSessionWrapperA;
        }
        return null;
    }

    class a implements Enumeration {
        private final JSSESessionManager a;
        private Enumeration b;

        public a(JSSESessionManager jSSESessionManager, Enumeration enumeration) {
            this.a = jSSESessionManager;
            Vector vector = new Vector();
            while (enumeration.hasMoreElements()) {
                b bVar = (b) enumeration.nextElement();
                Session sessionB = JSSESessionManager.a(jSSESessionManager).a(bVar).b();
                if (sessionB.isValid() && JSSESessionManager.a(jSSESessionManager, sessionB)) {
                    vector.addElement(bVar);
                }
            }
            this.b = vector.elements();
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.b.hasMoreElements();
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            b bVar = (b) this.b.nextElement();
            if (bVar != null) {
                return bVar.a();
            }
            return null;
        }
    }
}

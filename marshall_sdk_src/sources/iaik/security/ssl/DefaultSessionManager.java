package iaik.security.ssl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class DefaultSessionManager extends SessionManager {
    private long a = -1;
    private Hashtable b = new Hashtable(100);
    private long c = 1200000;
    private int d = 0;
    private int e = 0;
    private Vector f;

    public void setResumePeriod(long j) {
        this.c = j * 1000;
    }

    public long getResumePeriod() {
        return this.c / 1000;
    }

    private Vector a(Object obj) {
        Vector vector = (Vector) this.b.get(obj);
        if (vector != null) {
            return vector;
        }
        Vector vector2 = new Vector(2);
        this.b.put(obj, vector2);
        return vector2;
    }

    @Override // iaik.security.ssl.SessionManager
    protected synchronized void cacheSession(SSLTransport sSLTransport, Session session) {
        a();
        Object remotePeerId = sSLTransport.getRemotePeerId();
        if (remotePeerId == null) {
            return;
        }
        Vector vectorA = a(remotePeerId);
        if (!vectorA.contains(session)) {
            if (!sSLTransport.getUseClientMode()) {
                session.a(remotePeerId);
                vectorA.addElement(session);
                this.d++;
                if (this.cacheSizeLimit > 0) {
                    this.f.addElement(session);
                }
            } else if (vectorA.size() == 0) {
                session.a(remotePeerId);
                vectorA.addElement(session);
                this.d++;
                if (this.cacheSizeLimit > 0) {
                    this.f.addElement(session);
                }
            } else {
                session.a(remotePeerId);
                vectorA.setElementAt(session, 0);
                if (this.cacheSizeLimit > 0 && !this.f.contains(session)) {
                    this.f.addElement(session);
                }
            }
        }
    }

    @Override // iaik.security.ssl.SessionManager
    protected synchronized Session getSession(SSLTransport sSLTransport, Object obj) {
        a();
        Object remotePeerId = sSLTransport.getRemotePeerId();
        if (remotePeerId == null) {
            return null;
        }
        Enumeration enumerationElements = a(remotePeerId).elements();
        while (enumerationElements.hasMoreElements()) {
            Session session = (Session) enumerationElements.nextElement();
            if (a(session, obj)) {
                return session;
            }
        }
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
        Vector vector;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.cacheSizeLimit <= 0 || this.d < this.cacheSizeLimit) {
            z = false;
            z2 = false;
        } else {
            z = true;
            z2 = true;
        }
        if (!z && jCurrentTimeMillis - this.a >= 300000) {
            z = true;
        }
        if (z) {
            this.a = jCurrentTimeMillis;
            Enumeration enumerationKeys = this.b.keys();
            while (enumerationKeys.hasMoreElements()) {
                Object objNextElement = enumerationKeys.nextElement();
                Vector vector2 = (Vector) this.b.get(objNextElement);
                for (int size = vector2.size() - 1; size >= 0; size--) {
                    Session session = (Session) vector2.elementAt(size);
                    if (!session.isValid() || !session.a(this.c, jCurrentTimeMillis)) {
                        vector2.removeElementAt(size);
                        if (this.cacheSizeLimit > 0) {
                            this.f.removeElement(session);
                        }
                        this.d--;
                        this.e++;
                    }
                }
                if (vector2.size() == 0) {
                    this.b.remove(objNextElement);
                }
            }
            if (z2) {
                while (this.d >= this.cacheSizeLimit && this.f.size() > 0) {
                    Session session2 = (Session) this.f.firstElement();
                    Object objB = session2.b();
                    if (objB != null && (vector = (Vector) this.b.get(objB)) != null && vector.removeElement(session2)) {
                        this.d--;
                        this.e++;
                        if (vector.size() == 0) {
                            this.b.remove(objB);
                        }
                    }
                    this.f.removeElementAt(0);
                }
            }
        }
    }

    @Override // iaik.security.ssl.SessionManager
    public synchronized void setCacheSizeLimit(int i) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException("Session cache size must not be < 0!");
        }
        if (this.f == null) {
            this.f = new Vector(i);
        }
        this.cacheSizeLimit = i;
        if (i == 0) {
            this.f.removeAllElements();
        } else {
            a();
        }
    }

    @Override // iaik.security.ssl.SessionManager
    public synchronized int getCacheSizeLimit() {
        return this.cacheSizeLimit;
    }

    public synchronized void clear() {
        this.b.clear();
        this.d = 0;
        this.e = 0;
        Vector vector = this.f;
        if (vector != null) {
            vector.removeAllElements();
        }
    }

    private boolean a(Session session) {
        return session.a(this.c, System.currentTimeMillis());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer("Active sessions in cache: ");
        stringBuffer2.append(this.d);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer("Removed sessions: ");
        stringBuffer3.append(this.e);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        Enumeration enumerationKeys = this.b.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            stringBuffer.append(objNextElement);
            stringBuffer.append(" ->\n");
            Enumeration enumerationElements = ((Vector) this.b.get(objNextElement)).elements();
            while (enumerationElements.hasMoreElements()) {
                Object id = ((Session) enumerationElements.nextElement()).getID();
                stringBuffer.append("  ");
                stringBuffer.append(id);
                stringBuffer.append('\n');
            }
        }
        return stringBuffer.toString();
    }
}

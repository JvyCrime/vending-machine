package iaik.security.jsse.net;

import iaik.security.jsse.utils.Util;
import iaik.security.ssl.SSLTransport;
import iaik.security.ssl.Session;
import iaik.security.ssl.SessionID;
import java.net.InetAddress;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes.dex */
public class SSLSessionWrapper implements SSLSession {
    private Session a;
    private InetAddress b;
    private String c;
    private JSSESessionManager d;
    private Hashtable e = new Hashtable();
    private long f = System.currentTimeMillis();

    @Override // javax.net.ssl.SSLSession
    public int getApplicationBufferSize() {
        return 16384;
    }

    @Override // javax.net.ssl.SSLSession
    public Certificate[] getLocalCertificates() {
        return new Certificate[0];
    }

    @Override // javax.net.ssl.SSLSession
    public Principal getLocalPrincipal() {
        return null;
    }

    @Override // javax.net.ssl.SSLSession
    public int getPacketBufferSize() {
        return 18713;
    }

    @Override // javax.net.ssl.SSLSession
    public Principal getPeerPrincipal() {
        return null;
    }

    SSLSessionWrapper(Session session, JSSESessionManager jSSESessionManager, SSLTransport sSLTransport) {
        this.a = session;
        this.d = jSSESessionManager;
        this.b = sSLTransport.getRemoteInetAddress();
        this.c = (String) sSLTransport.getRemotePeerId();
    }

    @Override // javax.net.ssl.SSLSession
    public String getCipherSuite() {
        Session session = this.a;
        if (session != null) {
            return session.getCipherSuite().getName();
        }
        return null;
    }

    @Override // javax.net.ssl.SSLSession
    public long getCreationTime() {
        Session session = this.a;
        if (session != null) {
            return session.getCreationTime();
        }
        return -1L;
    }

    @Override // javax.net.ssl.SSLSession
    public byte[] getId() {
        Session session = this.a;
        if (session != null) {
            return ((SessionID) session.getID()).getID();
        }
        return null;
    }

    @Override // javax.net.ssl.SSLSession
    public long getLastAccessedTime() {
        return this.f;
    }

    @Override // javax.net.ssl.SSLSession
    public X509Certificate[] getPeerCertificateChain() {
        Session session = this.a;
        if (session != null) {
            return Util.convert(session.getPeerCertificateChain());
        }
        return null;
    }

    @Override // javax.net.ssl.SSLSession
    public Certificate[] getPeerCertificates() {
        return this.a.getPeerCertificateChain();
    }

    @Override // javax.net.ssl.SSLSession
    public String getPeerHost() {
        return this.b.getHostAddress();
    }

    @Override // javax.net.ssl.SSLSession
    public int getPeerPort() {
        int iIndexOf;
        String strSubstring;
        String str = this.c;
        if (str == null || (iIndexOf = str.indexOf(":")) == -1 || (strSubstring = str.substring(iIndexOf + 1)) == null) {
            return -1;
        }
        int iIndexOf2 = strSubstring.indexOf(":");
        if (iIndexOf2 != -1) {
            strSubstring = strSubstring.substring(0, iIndexOf2);
        }
        if (strSubstring == null) {
            return -1;
        }
        try {
            return Integer.parseInt(strSubstring);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    String a() {
        return this.c;
    }

    @Override // javax.net.ssl.SSLSession
    public SSLSessionContext getSessionContext() {
        return this.d;
    }

    @Override // javax.net.ssl.SSLSession
    public Object getValue(String str) {
        return this.e.get(str);
    }

    @Override // javax.net.ssl.SSLSession
    public String[] getValueNames() {
        String[] strArr = new String[this.e.size()];
        Enumeration enumerationKeys = this.e.keys();
        if (enumerationKeys != null) {
            int i = 0;
            while (enumerationKeys.hasMoreElements()) {
                strArr[i] = (String) enumerationKeys.nextElement();
                i++;
            }
        }
        return strArr;
    }

    @Override // javax.net.ssl.SSLSession
    public void invalidate() {
        Session session = this.a;
        if (session != null) {
            session.invalidate();
        }
    }

    @Override // javax.net.ssl.SSLSession
    public void putValue(String str, Object obj) {
        this.e.put(str, obj);
        if (obj instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener) obj).valueBound(new SSLSessionBindingEvent(this, str));
        }
    }

    @Override // javax.net.ssl.SSLSession
    public void removeValue(String str) {
        Object objRemove = this.e.remove(str);
        if (objRemove == null || !(objRemove instanceof SSLSessionBindingListener)) {
            return;
        }
        ((SSLSessionBindingListener) objRemove).valueUnbound(new SSLSessionBindingEvent(this, str));
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof SSLSessionWrapper) {
            return this.a.equals(((SSLSessionWrapper) obj).b());
        }
        if (obj instanceof Session) {
            return this.a.equals(obj);
        }
        return false;
    }

    Session b() {
        return this.a;
    }

    void a(long j) {
        this.f = j;
    }

    public String toString() {
        Session session = this.a;
        return session != null ? session.toString() : "";
    }

    @Override // javax.net.ssl.SSLSession
    public boolean isValid() {
        Session session = this.a;
        if (session != null) {
            return session.isValid();
        }
        return false;
    }

    @Override // javax.net.ssl.SSLSession
    public String getProtocol() {
        Session session = this.a;
        return session != null ? Util.toProtocol(session.getVersion()) : "NONE";
    }
}

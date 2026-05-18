package iaik.security.ssl;

/* JADX INFO: loaded from: classes.dex */
public abstract class SessionManager {
    private static SessionManager a = new DefaultSessionManager();
    protected int cacheSizeLimit = 0;

    protected abstract void cacheSession(SSLTransport sSLTransport, Session session);

    protected abstract Session getSession(SSLTransport sSLTransport, Object obj);

    public static SessionManager getDefault() {
        return a;
    }

    public static void setDefault(SessionManager sessionManager) {
        a = sessionManager;
    }

    protected SessionManager() {
    }

    public void setCacheSizeLimit(int i) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException("Session cache size must not be < 0!");
        }
        this.cacheSizeLimit = i;
    }

    public int getCacheSizeLimit() {
        return this.cacheSizeLimit;
    }
}

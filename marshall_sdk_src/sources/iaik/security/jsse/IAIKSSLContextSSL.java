package iaik.security.jsse;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLContextSSL extends IAIKSSLContext {
    protected int version_ = 0;

    @Override // iaik.security.jsse.IAIKSSLContext
    public int getSSLVersion() {
        return this.version_;
    }
}

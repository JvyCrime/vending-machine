package iaik.security.jsse;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLContextTLS extends IAIKSSLContext {
    protected int version_ = 6;

    @Override // iaik.security.jsse.IAIKSSLContext
    public int getSSLVersion() {
        return this.version_;
    }
}

package iaik.security.jsse;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLContextSSLv3 extends IAIKSSLContext {
    protected int version_ = 2;

    @Override // iaik.security.jsse.IAIKSSLContext
    public int getSSLVersion() {
        return this.version_;
    }
}

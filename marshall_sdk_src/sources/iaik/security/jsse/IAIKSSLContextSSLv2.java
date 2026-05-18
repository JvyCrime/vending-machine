package iaik.security.jsse;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLContextSSLv2 extends IAIKSSLContext {
    protected int version_ = 1;

    @Override // iaik.security.jsse.IAIKSSLContext
    public int getSSLVersion() {
        return this.version_;
    }
}

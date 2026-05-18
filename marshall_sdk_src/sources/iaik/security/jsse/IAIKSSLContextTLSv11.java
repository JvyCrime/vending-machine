package iaik.security.jsse;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLContextTLSv11 extends IAIKSSLContext {
    protected int version_ = 4;

    @Override // iaik.security.jsse.IAIKSSLContext
    public int getSSLVersion() {
        return this.version_;
    }
}

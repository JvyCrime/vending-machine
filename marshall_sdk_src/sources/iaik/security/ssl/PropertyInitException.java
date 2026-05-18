package iaik.security.ssl;

/* JADX INFO: loaded from: classes.dex */
public class PropertyInitException extends RuntimeException {
    private Throwable a;

    public PropertyInitException() {
    }

    public PropertyInitException(String str) {
        super(str);
    }

    public PropertyInitException(String str, Throwable th) {
        super(str);
        this.a = th;
    }

    public PropertyInitException(Throwable th) {
        super(th.getMessage());
        this.a = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.a;
    }
}

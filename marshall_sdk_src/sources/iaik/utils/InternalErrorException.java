package iaik.utils;

/* JADX INFO: loaded from: classes2.dex */
public class InternalErrorException extends RuntimeException {
    private static final long serialVersionUID = -6112136458102577469L;
    Exception a;

    public InternalErrorException() {
        this.a = null;
    }

    public InternalErrorException(Exception exc) {
        super(exc.getMessage());
        this.a = null;
        this.a = exc;
    }

    public InternalErrorException(String str) {
        super(str);
        this.a = null;
    }

    public InternalErrorException(String str, Exception exc) {
        super(str);
        this.a = null;
        this.a = exc;
    }

    public Exception getException() {
        return this.a;
    }
}

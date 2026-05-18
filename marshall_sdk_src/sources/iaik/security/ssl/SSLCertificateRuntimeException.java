package iaik.security.ssl;

/* JADX INFO: loaded from: classes.dex */
public class SSLCertificateRuntimeException extends RuntimeException {
    private Exception a;

    public SSLCertificateRuntimeException() {
    }

    public SSLCertificateRuntimeException(String str) {
        super(str);
    }

    public SSLCertificateRuntimeException(String str, Exception exc) {
        super(str);
        this.a = exc;
    }

    public SSLCertificateRuntimeException(Exception exc) {
        super(exc.getMessage());
        this.a = exc;
    }

    public Exception getWrappedException() {
        return this.a;
    }
}

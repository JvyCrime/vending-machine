package iaik.security.ssl;

/* JADX INFO: loaded from: classes.dex */
public class SSLCertificateException extends SSLException {
    private Exception a;

    public SSLCertificateException() {
    }

    public SSLCertificateException(String str) {
        super(str);
    }

    public SSLCertificateException(String str, Exception exc) {
        super(str);
        this.a = exc;
    }

    public SSLCertificateException(Exception exc) {
        super(exc.getMessage());
        this.a = exc;
    }

    SSLCertificateException(String str, int i, int i2, boolean z, Exception exc) {
        super(str, i, i2, z);
        this.a = exc;
    }

    public Exception getWrappedException() {
        return this.a;
    }
}

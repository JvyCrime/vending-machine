package iaik.utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes2.dex */
public class StreamCopier implements Runnable {
    private final byte[] a;
    private volatile Thread b;
    private IOException c;
    private final InputStream d;
    private final OutputStream e;
    private boolean f;
    private boolean g;
    private boolean h;

    public StreamCopier(InputStream inputStream, OutputStream outputStream) {
        this(inputStream, false, outputStream, false);
    }

    public StreamCopier(InputStream inputStream, boolean z, OutputStream outputStream, boolean z2) {
        this.h = true;
        this.d = inputStream;
        this.e = outputStream;
        setCloseIn(z);
        setCloseOut(z2);
        this.a = new byte[8192];
    }

    private void a(IOException iOException) {
        if (this.c == null) {
            this.c = iOException;
        }
    }

    private void a(Object obj) {
        try {
            if (obj instanceof InputStream) {
                ((InputStream) obj).close();
            }
            if (obj instanceof OutputStream) {
                ((OutputStream) obj).close();
            }
        } catch (IOException e) {
            a(e);
        }
    }

    public void copyStream() throws IOException {
        Thread thread = this.b;
        if (this.h) {
            while (true) {
                try {
                    int i = this.d.read(this.a);
                    if (i == -1) {
                        break;
                    } else {
                        this.e.write(this.a, 0, i);
                    }
                } catch (EOFException unused) {
                } catch (IOException e) {
                    a(e);
                }
            }
            if (this.f) {
                a(this.d);
            }
            if (this.g) {
                a(this.e);
            }
            this.h = false;
            if (thread != null) {
                thread.resume();
            }
            if (getException() != null) {
                throw getException();
            }
        }
    }

    public IOException getException() {
        return this.c;
    }

    public boolean isActive() {
        return this.h;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            copyStream();
        } catch (IOException unused) {
        }
    }

    public void setCloseIn(boolean z) {
        this.f = z;
    }

    public void setCloseOut(boolean z) {
        this.g = z;
    }

    public synchronized void waitFinished() {
        if (this.h) {
            this.b = Thread.currentThread();
            this.b.suspend();
        }
    }
}

package iaik.security.ssl;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

/* JADX INFO: loaded from: classes.dex */
class ai extends InputStream {
    boolean a;
    private SSLTransport b;
    private InputStream c;
    private ag e;
    private int g;
    private byte[] f = new byte[1];
    private ab d = null;

    ai(SSLTransport sSLTransport) throws IOException {
        this.b = sSLTransport;
        this.e = sSLTransport.c();
        this.c = sSLTransport.e();
        this.a = this.b.b != null;
        this.g = sSLTransport.getContext().j();
    }

    void a(InputStream inputStream) {
        this.c = inputStream;
        if (inputStream instanceof ab) {
            this.d = (ab) inputStream;
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        synchronized (this.c) {
            if (this.b.a()) {
                return -1;
            }
            if (read(this.f, 0, 1) != 1) {
                return -1;
            }
            return this.f[0] & 255;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        synchronized (this.c) {
            if (this.b.a()) {
                return -1;
            }
            ab abVar = this.d;
            if (abVar != null && abVar.available() <= 0 && !a()) {
                return -1;
            }
            return this.c.read(bArr, i, i2);
        }
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        synchronized (this.c) {
            if (this.b.a()) {
                return -1L;
            }
            return this.c.skip(j);
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        int iAvailable;
        ab abVar;
        synchronized (this.c) {
            iAvailable = -1;
            if (!this.b.a() && (iAvailable = this.c.available()) <= 0 && (abVar = this.d) != null && (iAvailable = abVar.c()) > 0) {
                int i = this.g;
                if (i == 0) {
                    iAvailable = 1;
                } else if (i == 2) {
                    a();
                    iAvailable = this.d.available();
                }
            }
        }
        return iAvailable;
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        synchronized (this.c) {
            this.c.mark(i);
        }
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        synchronized (this.c) {
            if (this.b.a()) {
                return;
            }
            this.c.reset();
        }
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        boolean zMarkSupported;
        synchronized (this.c) {
            zMarkSupported = this.c.markSupported();
        }
        return zMarkSupported;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this.c) {
            this.b.close();
        }
    }

    private boolean a() throws IOException {
        int iF;
        boolean z = true;
        try {
            b();
            return true;
        } catch (EOFException e) {
            if (this.a) {
                SSLTransport sSLTransport = this.b;
                StringBuffer stringBuffer = new StringBuffer("Exception reading SSL message: ");
                stringBuffer.append(e);
                sSLTransport.a(stringBuffer.toString());
            }
            if (!this.d.b || (iF = this.b.getContext().f()) == 2 || (iF != 3 && this.b.getActiveProtocolVersion() < 770)) {
                z = false;
            }
            this.b.a(z, false);
            if (z) {
                return false;
            }
            throw e;
        } catch (InterruptedIOException e2) {
            if (this.a) {
                SSLTransport sSLTransport2 = this.b;
                StringBuffer stringBuffer2 = new StringBuffer("Exception reading SSL message: ");
                stringBuffer2.append(e2);
                sSLTransport2.a(stringBuffer2.toString());
            }
            if (this.d.b) {
                throw e2;
            }
            this.b.a(false, true);
            throw new InterruptedIOException("Unrecoverable timeout!");
        } catch (IOException e3) {
            try {
                this.b.a();
                if (this.a) {
                    SSLTransport sSLTransport3 = this.b;
                    StringBuffer stringBuffer3 = new StringBuffer("Exception reading SSL message: ");
                    stringBuffer3.append(e3);
                    sSLTransport3.a(stringBuffer3.toString());
                }
                this.b.a(false, true);
                throw e3;
            } catch (IOException unused) {
                if (this.a) {
                    this.b.a("Socket closed by concurrent thread, returning EOF.");
                }
                return false;
            }
        } catch (Exception e4) {
            if (this.a) {
                SSLTransport sSLTransport4 = this.b;
                StringBuffer stringBuffer4 = new StringBuffer("Exception reading SSL message: ");
                stringBuffer4.append(e4);
                sSLTransport4.a(stringBuffer4.toString());
            }
            this.b.a(false, true);
            StringBuffer stringBuffer5 = new StringBuffer("Unexpected SSL error: ");
            stringBuffer5.append(e4.toString());
            throw new IOException(stringBuffer5.toString());
        }
    }

    private void b() throws IOException {
        int iE = this.d.e();
        if (iE != 257) {
            switch (iE) {
                case 21:
                    b bVar = new b(this.d);
                    if (this.a) {
                        SSLTransport sSLTransport = this.b;
                        StringBuffer stringBuffer = new StringBuffer("Received alert message: ");
                        stringBuffer.append(bVar.toString());
                        sSLTransport.a(stringBuffer.toString());
                    }
                    if (bVar.b() == 1 && bVar.a() == 0) {
                        this.b.a(true, true);
                        return;
                    } else {
                        StringBuffer stringBuffer2 = new StringBuffer("Peer sent alert: ");
                        stringBuffer2.append(bVar.toString());
                        throw new SSLException(stringBuffer2.toString(), bVar.b(), bVar.a(), true);
                    }
                case 22:
                    this.d.mark(1);
                    int iK = this.d.k();
                    if (iK == 0) {
                        new z(this.d);
                        if (!this.b.getUseClientMode()) {
                            this.e.a(2, 10);
                            throw new SSLException("Received hello_request from client, not allowed!", 2, 10, false);
                        }
                        if (this.a) {
                            this.b.a("Received hello_request handshake message from server, restarting handshake...");
                        }
                        SSLTransport sSLTransport2 = this.b;
                        sSLTransport2.b(sSLTransport2.getUseClientMode());
                        b();
                        return;
                    }
                    if (iK == 1) {
                        this.d.reset();
                        if (this.b.getUseClientMode()) {
                            this.e.a(2, 10);
                            throw new SSLException("Received client_hello from server, not allowed!", 2, 10, false);
                        }
                        if (this.a) {
                            this.b.a("Received client_hello handshake message, restarting handshake.");
                        }
                        this.b.b(true);
                        b();
                        return;
                    }
                    this.e.a(2, 10);
                    StringBuffer stringBuffer3 = new StringBuffer("Received unexpected handshake message: ");
                    stringBuffer3.append(iK);
                    throw new SSLException(stringBuffer3.toString(), 2, 10, false);
                case 23:
                    return;
                default:
                    this.e.a(2, 10);
                    StringBuffer stringBuffer4 = new StringBuffer("Received unexpected message: ");
                    stringBuffer4.append(aj.a(iE));
                    throw new SSLException(stringBuffer4.toString(), 2, 10, false);
            }
        }
    }
}

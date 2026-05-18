package iaik.security.ssl;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.Writer;

/* JADX INFO: loaded from: classes.dex */
public class ExtendedPrintWriter extends PrintWriter {
    protected boolean autoFlush_;
    protected char[] newLine_;
    protected Writer out_;
    public static final char[] CRLF = {'\r', '\n'};
    public static final char[] CR = {'\r'};
    public static final char[] LF = {'\n'};

    public ExtendedPrintWriter(Writer writer, boolean z, char[] cArr) {
        super(writer, z);
        setNewLine(cArr);
        this.out_ = writer;
    }

    protected void newLine() {
        try {
            synchronized (((Writer) this).lock) {
                Writer writer = this.out_;
                if (writer == null) {
                    throw new IOException("Stream closed");
                }
                writer.write(this.newLine_);
                if (this.autoFlush_) {
                    this.out_.flush();
                }
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException unused2) {
            setError();
        }
    }

    @Override // java.io.PrintWriter
    public void println() {
        synchronized (((Writer) this).lock) {
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(boolean z) {
        synchronized (((Writer) this).lock) {
            print(z);
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(char c) {
        synchronized (((Writer) this).lock) {
            print(c);
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(int i) {
        synchronized (((Writer) this).lock) {
            print(i);
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(long j) {
        synchronized (((Writer) this).lock) {
            print(j);
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(float f) {
        synchronized (((Writer) this).lock) {
            print(f);
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(double d) {
        synchronized (((Writer) this).lock) {
            print(d);
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(char[] cArr) {
        synchronized (((Writer) this).lock) {
            print(cArr);
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(String str) {
        synchronized (((Writer) this).lock) {
            print(str);
            newLine();
        }
    }

    @Override // java.io.PrintWriter
    public void println(Object obj) {
        synchronized (((Writer) this).lock) {
            print(obj);
            newLine();
        }
    }

    public synchronized char[] setNewLine(char[] cArr) {
        char[] cArr2;
        cArr2 = this.newLine_;
        if (cArr == null) {
            String property = System.getProperty("line.separator");
            this.newLine_ = property != null ? property.toCharArray() : CRLF;
        } else {
            this.newLine_ = cArr;
        }
        return cArr2;
    }

    public synchronized char[] getNewLine() {
        return (char[]) this.newLine_.clone();
    }

    public boolean getAutoFlush() {
        return this.autoFlush_;
    }

    public void setAutoFlush(boolean z) {
        this.autoFlush_ = z;
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (((Writer) this).lock) {
            super.close();
            this.out_ = null;
        }
    }
}

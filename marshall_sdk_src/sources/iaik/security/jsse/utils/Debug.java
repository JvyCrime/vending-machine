package iaik.security.jsse.utils;

import java.io.PrintStream;

/* JADX INFO: loaded from: classes.dex */
public class Debug {
    public static final int DEBUG_DISABLED = 1;
    public static final int DEBUG_ENABLED = 0;
    protected static Debug uniqueInstance_;
    protected PrintStream debugStream_;
    protected int debug_;
    protected String footer_;
    protected String header_;

    private Debug() {
        this.header_ = null;
        this.footer_ = null;
        this.debug_ = 1;
        this.debugStream_ = System.err;
    }

    private Debug(int i) {
        this.header_ = null;
        this.footer_ = null;
        this.debug_ = 1;
        this.debugStream_ = System.err;
        this.debug_ = i;
    }

    public synchronized void setDebugStream(PrintStream printStream) {
        this.debugStream_ = printStream;
    }

    public PrintStream getDebugStream() {
        return this.debugStream_;
    }

    public synchronized void setDebugMode(int i) {
        this.debug_ = i;
    }

    public int getDebugMode() {
        return this.debug_;
    }

    public synchronized void println(Object obj) {
        if (this.debug_ == 0) {
            String str = this.header_;
            if (str != null) {
                this.debugStream_.print(str);
            }
            String str2 = this.footer_;
            if (str2 != null) {
                this.debugStream_.print(str2);
            }
            this.debugStream_.println(obj);
        }
    }

    public static Debug getInstance() {
        Debug debug = uniqueInstance_;
        if (debug != null) {
            return debug;
        }
        int i = 1;
        try {
            String property = System.getProperty("javax.net.debug", null);
            if (property != null) {
                if (property.trim().length() > 0) {
                    i = 0;
                }
            }
        } catch (Throwable unused) {
        }
        Debug debug2 = new Debug(i);
        uniqueInstance_ = debug2;
        return debug2;
    }

    public synchronized void setHeader(String str) {
        this.header_ = str;
    }

    public synchronized void setFooter(String str) {
        this.footer_ = str;
    }
}

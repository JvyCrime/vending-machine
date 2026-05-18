package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class Extension implements Cloneable {
    SSLException a;
    private ExtensionType b;
    private int c = -1;
    private boolean d;
    private boolean e;

    abstract int a(ab abVar) throws IOException;

    abstract Extension a(Extension extension) throws SSLException;

    abstract void a(v vVar) throws IOException;

    public abstract String toString();

    Extension(ExtensionType extensionType) {
        this.b = extensionType;
    }

    public ExtensionType getExtensionType() {
        return this.b;
    }

    public int getType() {
        return getExtensionType().getType();
    }

    public String getName() {
        return getExtensionType().getName();
    }

    public void setCritical(boolean z) {
        a(!z ? 1 : 0);
    }

    void a(int i) {
        if (i < -1 || i > 1) {
            StringBuffer stringBuffer = new StringBuffer("Critical (");
            stringBuffer.append(i);
            stringBuffer.append(") Out of range!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.c = i;
    }

    int c() {
        return this.c;
    }

    public Object clone() {
        try {
            Extension extension = (Extension) super.clone();
            try {
                extension.b = (ExtensionType) this.b.clone();
                extension.c = this.c;
                extension.d = this.d;
                return extension;
            } catch (CloneNotSupportedException unused) {
                return extension;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    SSLException b() {
        return this.a;
    }

    void b(boolean z) {
        this.d = z;
    }

    boolean d() {
        return this.d;
    }

    void a(boolean z) {
        this.e = z;
    }

    boolean a() {
        return this.e;
    }
}

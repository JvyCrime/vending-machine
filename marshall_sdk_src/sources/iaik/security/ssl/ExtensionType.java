package iaik.security.ssl;

import androidx.core.os.EnvironmentCompat;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class ExtensionType implements Cloneable {
    private int a;
    private String b;

    public ExtensionType(int i, String str) {
        if (i < 0 || i > 65535) {
            StringBuffer stringBuffer = new StringBuffer("ExtensionType ");
            stringBuffer.append(i);
            stringBuffer.append(" out of range. Must be between 0 and 65535!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.a = i;
        this.b = str;
    }

    ExtensionType(ab abVar) throws IOException {
        a(abVar);
    }

    public int getType() {
        return this.a;
    }

    public String getName() {
        if (this.b == null) {
            this.b = EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ExtensionType) && this.a == ((ExtensionType) obj).a;
    }

    public int hashCode() {
        return this.a;
    }

    public Object clone() {
        try {
            ExtensionType extensionType = (ExtensionType) super.clone();
            extensionType.a = this.a;
            String str = this.b;
            if (str != null) {
                extensionType.b = str;
            }
            return extensionType;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getName());
        stringBuffer.append(" (");
        stringBuffer.append(this.a);
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    void a(ab abVar) throws IOException {
        this.a = abVar.f();
    }

    void a(v vVar) throws IOException {
        vVar.a(this.a);
    }
}

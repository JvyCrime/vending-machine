package iaik.security.provider;

import java.security.PrivilegedAction;

/* JADX INFO: loaded from: classes.dex */
class a implements PrivilegedAction {
    private final IAIK a;

    a(IAIK iaik2) {
        this.a = iaik2;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        this.a.a();
        return null;
    }
}

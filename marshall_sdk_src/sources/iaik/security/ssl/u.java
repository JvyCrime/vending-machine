package iaik.security.ssl;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;

/* JADX INFO: loaded from: classes.dex */
class u {
    static Class a;
    private SSLServerContext b;
    private DHGenParameterSpec c;
    private long d;
    private long e;
    private boolean f;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public u(SSLServerContext sSLServerContext, DHGenParameterSpec dHGenParameterSpec, long j) {
        this.b = sSLServerContext;
        this.c = dHGenParameterSpec;
        this.d = j;
    }

    DHGenParameterSpec b() {
        return this.c;
    }

    long c() {
        return this.d;
    }

    void d() {
        this.f = false;
    }

    void a() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.f || jCurrentTimeMillis - this.e < this.d) {
            return;
        }
        this.f = true;
        a aVar = new a(this.b, this.c, this);
        aVar.setPriority(1);
        aVar.start();
        this.e = jCurrentTimeMillis;
    }

    private static class a extends Thread {
        private SSLServerContext a;
        private DHGenParameterSpec b;
        private u c;

        public a(SSLServerContext sSLServerContext, DHGenParameterSpec dHGenParameterSpec, u uVar) {
            super("DHParameterGenThread");
            this.a = sSLServerContext;
            this.b = dHGenParameterSpec;
            this.c = uVar;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Class clsClass$;
            try {
                AlgorithmParameterGenerator algorithmParameterGenerator = SecurityProvider.getSecurityProvider().getAlgorithmParameterGenerator("DH");
                algorithmParameterGenerator.init(this.b);
                AlgorithmParameters algorithmParametersGenerateParameters = algorithmParameterGenerator.generateParameters();
                if (u.a != null) {
                    clsClass$ = u.a;
                } else {
                    clsClass$ = u.class$("javax.crypto.spec.DHParameterSpec");
                    u.a = clsClass$;
                }
                this.a.a((DHParameterSpec) algorithmParametersGenerateParameters.getParameterSpec(clsClass$));
                this.c.d();
            } catch (Exception unused) {
            }
        }
    }
}

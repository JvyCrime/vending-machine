package iaik.security.ssl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class f extends x {
    private byte[] a;
    private Principal[] b;
    private SignatureAndHashAlgorithmList c;

    f(byte[] bArr, Principal[] principalArr, SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        super(13);
        this.a = bArr;
        this.b = principalArr;
        this.c = signatureAndHashAlgorithmList;
    }

    f(ab abVar, int i) throws IOException {
        super(13);
        a(abVar, i);
    }

    byte[] b() {
        return this.a;
    }

    Principal[] a() {
        return this.b;
    }

    SignatureAndHashAlgorithmList c() {
        return this.c;
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        int i;
        byte[][] bArr;
        int size;
        agVar.g(13);
        int length = this.b.length;
        byte[][] bArr2 = (byte[][]) Array.newInstance((Class<?>) byte[].class, length);
        int i2 = 0;
        int length2 = 0;
        while (true) {
            if (i2 >= length) {
                i = length;
                bArr = bArr2;
                break;
            }
            bArr2[i2] = SecurityProvider.getSecurityProvider().getEncodedPrincipal(this.b[i2]);
            if (bArr2[i2] == null) {
                bArr = (byte[][]) Array.newInstance((Class<?>) byte[].class, 0);
                length2 = 0;
                i = 0;
                break;
            }
            length2 = length2 + bArr2[i2].length + 2;
            i2++;
        }
        int length3 = length2 + 2 + this.a.length + 1;
        SignatureAndHashAlgorithmList signatureAndHashAlgorithmList = this.c;
        if (signatureAndHashAlgorithmList != null) {
            size = signatureAndHashAlgorithmList.size() * 2;
            length3 += size + 2;
        } else {
            size = 0;
        }
        agVar.e(length3);
        agVar.c(this.a);
        SignatureAndHashAlgorithmList signatureAndHashAlgorithmList2 = this.c;
        if (signatureAndHashAlgorithmList2 != null) {
            signatureAndHashAlgorithmList2.a(agVar, size);
        }
        agVar.a(length2);
        for (int i3 = 0; i3 < i; i3++) {
            agVar.a(bArr[i3]);
        }
    }

    void a(ab abVar, int i) throws IOException {
        abVar.h();
        byte[] bArrL = abVar.l();
        this.a = bArrL;
        int length = bArrL.length;
        if (i >= 771) {
            this.c = new SignatureAndHashAlgorithmList(abVar);
        }
        int length2 = 0;
        Vector vector = new Vector();
        int iF = abVar.f();
        while (length2 < iF) {
            byte[] bArrG = abVar.g();
            length2 = length2 + bArrG.length + 2;
            try {
                Principal principal = SecurityProvider.getSecurityProvider().getPrincipal(bArrG);
                if (principal != null) {
                    vector.addElement(principal);
                }
            } catch (Throwable th) {
                StringBuffer stringBuffer = new StringBuffer("SecurityProvider error parsing principal: ");
                stringBuffer.append(th.toString());
                throw new SSLException(stringBuffer.toString());
            }
        }
        Principal[] principalArr = new Principal[vector.size()];
        this.b = principalArr;
        vector.copyInto(principalArr);
    }

    void a(SSLTransport sSLTransport) {
        if (sSLTransport.b != null) {
            StringBuffer stringBuffer = new StringBuffer("  Accepted certificate types: ");
            for (int i = 0; i < this.a.length; i++) {
                if (i != 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(Utils.certTypeToString(this.a[i]));
            }
            sSLTransport.a(stringBuffer.toString());
            if (this.c != null) {
                sSLTransport.a("  Supported signature algorithms:");
                StringBuffer stringBuffer2 = new StringBuffer("    ");
                stringBuffer2.append(this.c);
                sSLTransport.a(stringBuffer2.toString());
            }
            sSLTransport.a("  Accepted certificate authorities:");
            int length = this.b.length;
            if (length == 0) {
                sSLTransport.a("    (empty list)");
                return;
            }
            for (int i2 = 0; i2 < length; i2++) {
                StringBuffer stringBuffer3 = new StringBuffer("    ");
                stringBuffer3.append(this.b[i2]);
                sSLTransport.a(stringBuffer3.toString());
            }
        }
    }
}

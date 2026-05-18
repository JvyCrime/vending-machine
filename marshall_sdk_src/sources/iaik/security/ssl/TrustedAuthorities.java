package iaik.security.ssl;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class TrustedAuthorities extends Extension implements Cloneable {
    public static final ExtensionType TYPE = new ExtensionType(3, "trusted_ca_keys");
    private TrustedAuthority[] b;
    private int c;

    public TrustedAuthorities() {
        super(TYPE);
        this.c = 3;
    }

    public TrustedAuthorities(int i) {
        this();
        if (i < 0 || i > 3) {
            StringBuffer stringBuffer = new StringBuffer("identifier type (");
            stringBuffer.append(i);
            stringBuffer.append(") out of scope! ");
            stringBuffer.append("Must be between 0 and 3!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.c = i;
    }

    public TrustedAuthorities(int i, X509Certificate[] x509CertificateArr) throws Exception {
        this();
        this.c = i;
        a(x509CertificateArr);
    }

    public TrustedAuthorities(TrustedAuthority[] trustedAuthorityArr) {
        this();
        this.b = trustedAuthorityArr;
    }

    public TrustedAuthority[] getTrustedAuthorities() {
        return this.b;
    }

    public TrustedAuthority[] getTrustedAuthorities(int i) {
        if (this.b != null) {
            Vector vector = new Vector();
            int i2 = 0;
            while (true) {
                TrustedAuthority[] trustedAuthorityArr = this.b;
                if (i2 >= trustedAuthorityArr.length) {
                    break;
                }
                TrustedAuthority trustedAuthority = trustedAuthorityArr[i2];
                if (trustedAuthority.getIdentifierType() == i) {
                    vector.addElement(trustedAuthority);
                }
                i2++;
            }
            if (!vector.isEmpty()) {
                TrustedAuthority[] trustedAuthorityArr2 = new TrustedAuthority[vector.size()];
                vector.copyInto(trustedAuthorityArr2);
                return trustedAuthorityArr2;
            }
        }
        return null;
    }

    public boolean contains(TrustedAuthority trustedAuthority) {
        TrustedAuthority[] trustedAuthorityArr = this.b;
        if (trustedAuthorityArr == null) {
            return false;
        }
        for (TrustedAuthority trustedAuthority2 : trustedAuthorityArr) {
            if (trustedAuthority.equals(trustedAuthority2)) {
                return true;
            }
        }
        return false;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        TrustedAuthorities trustedAuthorities = (TrustedAuthorities) super.clone();
        trustedAuthorities.c = this.c;
        TrustedAuthority[] trustedAuthorityArr = this.b;
        if (trustedAuthorityArr != null) {
            trustedAuthorities.b = new TrustedAuthority[trustedAuthorityArr.length];
            int i = 0;
            while (true) {
                TrustedAuthority[] trustedAuthorityArr2 = this.b;
                if (i >= trustedAuthorityArr2.length) {
                    break;
                }
                trustedAuthorities.b[i] = (TrustedAuthority) trustedAuthorityArr2[i].clone();
                i++;
            }
        }
        return trustedAuthorities;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        TrustedAuthority[] trustedAuthorityArr = this.b;
        if (trustedAuthorityArr != null && trustedAuthorityArr.length > 0) {
            stringBuffer.append(" Trusted authorities: ");
            for (int i = 0; i < this.b.length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(this.b[i].getIdentifierTypeAsString());
            }
        } else {
            stringBuffer.append(" empty");
        }
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        Vector vector = new Vector(5);
        int iF = abVar.f();
        if (iF > 0) {
            int iF2 = abVar.f();
            if (iF2 != iF - 2) {
                throw new SSLException("Invalid length of trusted_ca_keys extension!", 2, 50, false);
            }
            while (iF2 > 0) {
                int iK = abVar.k();
                int i = iF2 - 1;
                byte[] bArrG = abVar.g();
                try {
                    vector.addElement(new TrustedAuthority(iK, bArrG));
                    iF2 = i - (bArrG.length + 2);
                } catch (Exception e) {
                    StringBuffer stringBuffer = new StringBuffer("Error initializing TrustedAuthority: ");
                    stringBuffer.append(e.getMessage());
                    throw new SSLException(stringBuffer.toString(), 2, 50, false);
                }
            }
            if (iF2 != 0) {
                throw new SSLException("TrustedAuthorities size does not match to length field!", 2, 50, false);
            }
        }
        if (vector.size() > 0) {
            TrustedAuthority[] trustedAuthorityArr = new TrustedAuthority[vector.size()];
            this.b = trustedAuthorityArr;
            vector.copyInto(trustedAuthorityArr);
        }
        return iF + 2;
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        TrustedAuthority[] trustedAuthorityArr;
        int i = 0;
        if (!d() || (trustedAuthorityArr = this.b) == null || trustedAuthorityArr.length == 0) {
            vVar.a(0);
            return;
        }
        int size = vVar.size();
        vVar.write(v.a);
        vVar.write(v.a);
        while (true) {
            TrustedAuthority[] trustedAuthorityArr2 = this.b;
            if (i < trustedAuthorityArr2.length) {
                vVar.d(trustedAuthorityArr2[i].getIdentifierType());
                vVar.a(this.b[i].getIdentifier());
                i++;
            } else {
                int size2 = (vVar.size() - size) - 2;
                vVar.b(size2, size);
                vVar.b(size2 - 2, size + 2);
                return;
            }
        }
    }

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return d() ? this : extension;
    }

    void a(X509Certificate[] x509CertificateArr) throws Exception {
        if (x509CertificateArr == null || x509CertificateArr.length <= 0) {
            return;
        }
        this.b = new TrustedAuthority[x509CertificateArr.length];
        for (int i = 0; i < x509CertificateArr.length; i++) {
            this.b[i] = new TrustedAuthority(this.c, x509CertificateArr[i]);
        }
    }
}

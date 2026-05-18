package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class SignatureAlgorithms extends Extension implements Cloneable {
    public static final ExtensionType TYPE = new ExtensionType(13, "signature_algorithms");
    private SignatureAndHashAlgorithmList b;
    private boolean c;

    public SignatureAlgorithms() {
        super(TYPE);
        setCritical(false);
    }

    public SignatureAlgorithms(SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        this();
        if (signatureAndHashAlgorithmList == null) {
            throw new IllegalArgumentException("Algorithms list must not be null!");
        }
        if (signatureAndHashAlgorithmList.size() == 0) {
            throw new IllegalArgumentException("Algorithms list must not be empty!");
        }
        this.b = signatureAndHashAlgorithmList;
    }

    SignatureAlgorithms(int i) {
        this();
        this.b = new SignatureAndHashAlgorithmList(i);
    }

    void a(SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        if (signatureAndHashAlgorithmList == null) {
            throw new IllegalArgumentException("Algorithms list must not be null!");
        }
        if (signatureAndHashAlgorithmList.size() == 0) {
            throw new IllegalArgumentException("Algorithms list must not be empty!");
        }
        this.b = signatureAndHashAlgorithmList;
    }

    public SignatureAndHashAlgorithmList getSupportedAlgorithms() {
        if (this.b == null) {
            this.b = new SignatureAndHashAlgorithmList(2);
        }
        return this.b;
    }

    public void setIgnorePeerPreferenceOrder(boolean z) {
        this.c = z;
    }

    boolean e() {
        return this.c;
    }

    public int hashCode() {
        getSupportedAlgorithms();
        return getType() + this.b.hashCode();
    }

    public boolean equals(Object obj) {
        getSupportedAlgorithms();
        if (this == obj) {
            return true;
        }
        if (obj instanceof SignatureAlgorithms) {
            return this.b.equals(((SignatureAlgorithms) obj).b);
        }
        return false;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        getSupportedAlgorithms();
        SignatureAlgorithms signatureAlgorithms = (SignatureAlgorithms) super.clone();
        signatureAlgorithms.b = new SignatureAndHashAlgorithmList(this.b);
        return signatureAlgorithms;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        getSupportedAlgorithms();
        String string = this.b.toString();
        StringBuffer stringBuffer = new StringBuffer("Supported algorithms: ");
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        int iF = abVar.f();
        if (iF < 4) {
            throw new SSLException("Supported signature algorithms list must not be empty!", 2, 50, false);
        }
        this.b = new SignatureAndHashAlgorithmList(abVar, iF);
        return iF + 2;
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        getSupportedAlgorithms();
        int size = this.b.size() * 2;
        vVar.a(size + 2);
        this.b.a(vVar, size);
    }

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        getSupportedAlgorithms();
        return new SignatureAlgorithms(a(this.b, ((SignatureAlgorithms) extension).getSupportedAlgorithms(), e(), c() == 0));
    }

    static SignatureAndHashAlgorithmList a(SignatureAndHashAlgorithmList signatureAndHashAlgorithmList, SignatureAndHashAlgorithmList signatureAndHashAlgorithmList2, boolean z, boolean z2) throws SSLException {
        SignatureAndHashAlgorithmList signatureAndHashAlgorithmList3;
        SignatureAndHashAlgorithmList signatureAndHashAlgorithmList4;
        if (z) {
            signatureAndHashAlgorithmList4 = (SignatureAndHashAlgorithmList) signatureAndHashAlgorithmList.clone();
            signatureAndHashAlgorithmList3 = signatureAndHashAlgorithmList2;
        } else {
            signatureAndHashAlgorithmList3 = signatureAndHashAlgorithmList;
            signatureAndHashAlgorithmList4 = (SignatureAndHashAlgorithmList) signatureAndHashAlgorithmList2.clone();
        }
        signatureAndHashAlgorithmList4.intersectWith(signatureAndHashAlgorithmList3);
        if (signatureAndHashAlgorithmList4.size() == 0 && !z2) {
            if (z) {
                signatureAndHashAlgorithmList4 = new SignatureAndHashAlgorithmList(3);
            } else {
                signatureAndHashAlgorithmList4 = (SignatureAndHashAlgorithmList) signatureAndHashAlgorithmList2.clone();
                signatureAndHashAlgorithmList2 = new SignatureAndHashAlgorithmList(3);
            }
            signatureAndHashAlgorithmList4.intersectWith(signatureAndHashAlgorithmList2);
        }
        if (signatureAndHashAlgorithmList4.size() != 0) {
            return signatureAndHashAlgorithmList4;
        }
        throw new SSLException("No common signature algorithms!", 2, 40, false);
    }
}

package iaik.security.ssl;

import iaik.security.ssl.SupportedEllipticCurves;
import iaik.security.ssl.SupportedPointFormats;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class KeyAndCert implements Cloneable {
    private X509Certificate[] a;
    private PrivateKey b;
    private int c;
    private ServerName[] d;
    private volatile Object[] e;
    private TrustedAuthorities f;
    private SupportedEllipticCurves.NamedCurve g;
    private SupportedPointFormats.ECPointFormat h;
    private Object i;

    public byte[] getCertificateStatus(int i, byte[] bArr, SSLTransport sSLTransport) throws SSLException {
        return null;
    }

    public KeyAndCert(X509Certificate[] x509CertificateArr, PrivateKey privateKey) {
        if (privateKey == null || Utils.a((Object[]) x509CertificateArr)) {
            throw new NullPointerException("CertificateChain and PrivateKey must not be null!");
        }
        this.a = x509CertificateArr;
        this.b = privateKey;
        this.c = Utils.getCertificateType(x509CertificateArr[0]);
        this.i = new Object();
    }

    public KeyAndCert(X509Certificate[] x509CertificateArr, PrivateKey privateKey, int i) {
        if (privateKey == null || Utils.a((Object[]) x509CertificateArr)) {
            throw new NullPointerException("CertificateChain and PrivateKey must not be null!");
        }
        if (!SSLContext.a(i)) {
            StringBuffer stringBuffer = new StringBuffer("Invalid cert type (");
            stringBuffer.append(i);
            stringBuffer.append("). ");
            stringBuffer.append("Must be between ");
            stringBuffer.append(1);
            stringBuffer.append(" and ");
            stringBuffer.append(66);
            stringBuffer.append(".");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.a = x509CertificateArr;
        this.b = privateKey;
        this.c = i;
        this.i = new Object();
    }

    KeyAndCert(int i, PrivateKey privateKey) {
        Objects.requireNonNull(privateKey, "PrivateKey must not be null!");
        if (!SSLContext.a(i)) {
            StringBuffer stringBuffer = new StringBuffer("Invalid cert type (");
            stringBuffer.append(i);
            stringBuffer.append("). ");
            stringBuffer.append("Must be between ");
            stringBuffer.append(1);
            stringBuffer.append(" and ");
            stringBuffer.append(66);
            stringBuffer.append(".");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.b = privateKey;
        this.c = i;
    }

    public final PrivateKey getPrivateKey() {
        return this.b;
    }

    public final X509Certificate[] getCertificateChain() {
        return this.a;
    }

    public final int getCertificateType() {
        return this.c;
    }

    final void b(int i) {
        this.c = i;
    }

    public int hashCode() {
        int iA = this.c;
        PrivateKey privateKey = this.b;
        if (privateKey != null) {
            try {
                iA += Utils.a(privateKey.getEncoded());
            } catch (Throwable unused) {
            }
        }
        X509Certificate[] x509CertificateArr = this.a;
        if (x509CertificateArr == null) {
            return iA;
        }
        try {
            return iA + Utils.a(x509CertificateArr[0].getEncoded());
        } catch (Throwable unused2) {
            return iA;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof KeyAndCert)) {
            return false;
        }
        KeyAndCert keyAndCert = (KeyAndCert) obj;
        if (!this.b.equals(keyAndCert.b) || this.a.length != keyAndCert.a.length) {
            return false;
        }
        int i = 0;
        while (true) {
            X509Certificate[] x509CertificateArr = this.a;
            if (i >= x509CertificateArr.length) {
                return true;
            }
            if (!x509CertificateArr[i].equals(keyAndCert.a[i])) {
                return false;
            }
            i++;
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Utils.certTypeToString(this.c));
        stringBuffer.append(" credentials:\n");
        StringBuffer stringBuffer2 = new StringBuffer("  ");
        int i = 0;
        stringBuffer2.append(Utils.a(this.a[0], false));
        stringBuffer2.append(" key, ");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer(String.valueOf(this.a.length));
        stringBuffer3.append(" certificates");
        stringBuffer.append(stringBuffer3.toString());
        ServerName[] serverNameArr = this.d;
        if (serverNameArr != null && serverNameArr.length > 0) {
            stringBuffer.append("; Server name(s): ");
            while (true) {
                ServerName[] serverNameArr2 = this.d;
                if (i >= serverNameArr2.length) {
                    break;
                }
                String name = null;
                try {
                    name = serverNameArr2[i].getName();
                } catch (Exception unused) {
                }
                if (name != null) {
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    stringBuffer.append(name);
                }
                i++;
            }
        }
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    public synchronized void setTLSServerNames(ServerName[] serverNameArr) {
        this.d = serverNameArr;
    }

    public synchronized ServerName[] getTLSServerNames() {
        c(0);
        return this.d;
    }

    boolean a(ServerName[] serverNameArr) {
        ServerName[] tLSServerNames;
        if (serverNameArr == null || (tLSServerNames = getTLSServerNames()) == null) {
            return false;
        }
        boolean z = false;
        for (ServerName serverName : tLSServerNames) {
            int i = 0;
            while (true) {
                if (i >= serverNameArr.length) {
                    break;
                }
                if (serverName.equals(serverNameArr[i])) {
                    z = true;
                    break;
                }
                i++;
            }
            if (z) {
                break;
            }
        }
        return z;
    }

    private void c(int i) {
        if (this.d != null || this.a == null) {
            return;
        }
        String[] tLSServerName = SecurityProvider.getSecurityProvider().getTLSServerName(this.a[0]);
        Vector vector = new Vector();
        if (tLSServerName != null) {
            for (String str : tLSServerName) {
                try {
                    vector.addElement(new ServerName(i, str, null, false));
                } catch (UnsupportedEncodingException unused) {
                }
            }
        }
        ServerName[] serverNameArr = new ServerName[vector.size()];
        vector.copyInto(serverNameArr);
        this.d = serverNameArr;
    }

    void a(int i) {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte[].class, this.a.length);
        for (int length = this.a.length - 1; length >= 0; length--) {
            try {
                bArr[length] = securityProvider.calculateTrustedAuthorityIdentifier(i, this.a[length]);
            } catch (Exception unused) {
            }
        }
        this.e[i] = bArr;
    }

    KeyAndCert a(TrustedAuthority trustedAuthority) {
        int identifierType = trustedAuthority.getIdentifierType();
        if (identifierType < 0 || identifierType >= 4) {
            StringBuffer stringBuffer = new StringBuffer("identifier type (");
            stringBuffer.append(identifierType);
            stringBuffer.append(") out of scope! ");
            stringBuffer.append("Must be between 0 and 3!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        byte[] identifier = trustedAuthority.getIdentifier();
        if (this.e == null) {
            synchronized (this.i) {
                if (this.e == null) {
                    this.e = new Object[4];
                }
            }
        }
        if (this.e[identifierType] == null) {
            synchronized (this.i) {
                if (this.e[identifierType] == null) {
                    a(identifierType);
                }
            }
        }
        byte[][] bArr = (byte[][]) this.e[identifierType];
        int length = this.a.length;
        while (true) {
            length--;
            if (length >= 0) {
                byte[] bArr2 = bArr[length];
                if (bArr2 != null && Utils.equalsBlock(identifier, bArr2)) {
                    break;
                }
            } else {
                length = -1;
                break;
            }
        }
        if (length == -1) {
            return null;
        }
        X509Certificate[] x509CertificateArr = this.a;
        if (length == x509CertificateArr.length - 1) {
            return this;
        }
        int i = length + 1;
        X509Certificate[] x509CertificateArr2 = new X509Certificate[i];
        System.arraycopy(x509CertificateArr, 0, x509CertificateArr2, 0, i);
        return new KeyAndCert(x509CertificateArr2, this.b);
    }

    protected KeyAndCert isTrustedBy(TrustedAuthorities trustedAuthorities) {
        KeyAndCert keyAndCertA;
        Objects.requireNonNull(trustedAuthorities, "trustedAuthorities must not be null!");
        TrustedAuthority[] trustedAuthorities2 = trustedAuthorities.getTrustedAuthorities();
        Vector vector = null;
        if (trustedAuthorities2 == null) {
            return null;
        }
        if (this.f != null) {
            for (TrustedAuthority trustedAuthority : trustedAuthorities2) {
                if (this.f.contains(trustedAuthority)) {
                    keyAndCertA = this;
                    break;
                }
            }
            keyAndCertA = null;
        } else {
            keyAndCertA = null;
        }
        if (keyAndCertA == null) {
            for (TrustedAuthority trustedAuthority2 : trustedAuthorities2) {
                if (trustedAuthority2.getIdentifierType() == 0) {
                    if (vector == null) {
                        vector = new Vector();
                    }
                    vector.addElement(trustedAuthority2);
                } else {
                    keyAndCertA = a(trustedAuthority2);
                    if (keyAndCertA != null) {
                        break;
                    }
                }
            }
            if (keyAndCertA == null && vector != null && !vector.isEmpty()) {
                Enumeration enumerationElements = vector.elements();
                KeyAndCert keyAndCertA2 = keyAndCertA;
                while (enumerationElements.hasMoreElements()) {
                    keyAndCertA2 = a((TrustedAuthority) enumerationElements.nextElement());
                    if (keyAndCertA2 != null) {
                        return keyAndCertA2;
                    }
                }
                return keyAndCertA2;
            }
        }
        return keyAndCertA;
    }

    public void setTrustedAuthorities(TrustedAuthorities trustedAuthorities) {
        this.f = trustedAuthorities;
    }

    TrustedAuthority[] c() {
        TrustedAuthorities trustedAuthorities = this.f;
        if (trustedAuthorities == null) {
            return null;
        }
        return trustedAuthorities.getTrustedAuthorities();
    }

    SupportedEllipticCurves.NamedCurve b() {
        if (this.g == null && this.a != null) {
            this.g = SecurityProvider.getSecurityProvider().getCurve(this.a[0].getPublicKey());
        }
        return this.g;
    }

    SupportedPointFormats.ECPointFormat a() {
        if (this.h == null && this.a != null) {
            this.h = SecurityProvider.getSecurityProvider().getECPointFormat(this.a[0].getPublicKey());
        }
        return this.h;
    }
}

package iaik.security.ssl;

import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes.dex */
public class TrustedAuthority implements Cloneable {
    public static final int ID_CERT_SHA1_HASH = 3;
    public static final int ID_KEY_SHA1_HASH = 1;
    public static final int ID_PRE_AGREED = 0;
    public static final int ID_X509_NAME = 2;
    private int a;
    private byte[] b;

    private static final String a(int i) {
        return i != 1 ? i != 2 ? i != 3 ? "pre_agreed" : "cert_sha1_hash" : "x509_name" : "key_sha1_hash";
    }

    public TrustedAuthority(int i, byte[] bArr) {
        if (i < 0 || i > 3) {
            StringBuffer stringBuffer = new StringBuffer("identifier type (");
            stringBuffer.append(i);
            stringBuffer.append(") out of scope! ");
            stringBuffer.append("Must be between 0 and 3!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (bArr == null) {
            throw new IllegalArgumentException("identifier must not be null!");
        }
        if ((i == 1 || i == 3) && bArr.length != 20) {
            throw new IllegalArgumentException("Invalid length of identifier. Must be 20 bytes long.");
        }
        this.a = i;
        this.b = bArr;
    }

    public TrustedAuthority(int i, X509Certificate x509Certificate) throws Exception {
        byte[] bArrCalculateTrustedAuthorityIdentifier = SecurityProvider.getSecurityProvider().calculateTrustedAuthorityIdentifier(i, x509Certificate);
        this.b = bArrCalculateTrustedAuthorityIdentifier;
        if (bArrCalculateTrustedAuthorityIdentifier == null) {
            StringBuffer stringBuffer = new StringBuffer("Cannot calculate identifier for type ");
            stringBuffer.append(a(i));
            stringBuffer.append(". Not supported!");
            throw new Exception(stringBuffer.toString());
        }
        this.a = i;
    }

    public int getIdentifierType() {
        return this.a;
    }

    public String getIdentifierTypeAsString() {
        return a(this.a);
    }

    public byte[] getIdentifier() {
        return this.b;
    }

    public boolean identifies(X509Certificate x509Certificate) throws Exception {
        byte[] bArrCalculateTrustedAuthorityIdentifier = SecurityProvider.getSecurityProvider().calculateTrustedAuthorityIdentifier(this.a, x509Certificate);
        if (bArrCalculateTrustedAuthorityIdentifier == null) {
            StringBuffer stringBuffer = new StringBuffer("Cannot calculate identifier of type ");
            stringBuffer.append(a(this.a));
            stringBuffer.append(" from certificate. Not supported!");
            throw new Exception(stringBuffer.toString());
        }
        return Utils.equalsBlock(this.b, bArrCalculateTrustedAuthorityIdentifier);
    }

    public Object clone() {
        try {
            TrustedAuthority trustedAuthority = (TrustedAuthority) super.clone();
            try {
                trustedAuthority.a = this.a;
                byte[] bArr = this.b;
                if (bArr == null) {
                    return trustedAuthority;
                }
                trustedAuthority.b = (byte[]) bArr.clone();
                return trustedAuthority;
            } catch (CloneNotSupportedException unused) {
                return trustedAuthority;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public int hashCode() {
        int i = this.a;
        if (this.b != null) {
            int i2 = 0;
            while (true) {
                byte[] bArr = this.b;
                if (i2 >= bArr.length) {
                    break;
                }
                int i3 = i + (bArr[i2] & 255);
                i = (i3 >> 29) | (i3 << 3);
                i2++;
            }
        }
        return i;
    }

    public boolean equals(Object obj) {
        byte[] bArr;
        if (this == obj) {
            return true;
        }
        if (obj instanceof TrustedAuthority) {
            TrustedAuthority trustedAuthority = (TrustedAuthority) obj;
            if (this.a == trustedAuthority.a) {
                byte[] bArr2 = this.b;
                if (bArr2 != null && (bArr = trustedAuthority.b) != null) {
                    return Utils.equalsBlock(bArr2, bArr);
                }
                if (bArr2 == null && trustedAuthority.b == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getIdentifierTypeAsString());
        stringBuffer.append(" (");
        stringBuffer.append(this.a);
        stringBuffer.append("): ");
        byte[] bArr = this.b;
        if (bArr != null) {
            stringBuffer.append(Utils.toString(bArr));
        }
        return stringBuffer.toString();
    }
}

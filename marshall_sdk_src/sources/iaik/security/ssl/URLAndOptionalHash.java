package iaik.security.ssl;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class URLAndOptionalHash {
    private String a;
    private byte[] b;
    private byte[] c;

    public URLAndOptionalHash(String str) throws UnsupportedEncodingException {
        if (str == null) {
            throw new IllegalArgumentException("url must not be NULL!");
        }
        try {
            this.b = SecurityProvider.getSecurityProvider().encodeURL(str);
            this.a = str;
        } catch (Exception e) {
            if (e instanceof UnsupportedEncodingException) {
                throw ((UnsupportedEncodingException) e);
            }
            StringBuffer stringBuffer = new StringBuffer("Cannot encode url: ");
            stringBuffer.append(e.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public void setHash(byte[] bArr) {
        if (bArr != null && bArr.length != 20) {
            throw new IllegalArgumentException("Invalid hash value. Must be 20 20 bytes long!");
        }
        this.c = bArr;
    }

    public String getURL() {
        return this.a;
    }

    public byte[] getEncodedURL() {
        return this.b;
    }

    public byte[] getHash() {
        return this.c;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Certificate URL: ");
        stringBuffer.append(this.a);
        if (this.c != null) {
            stringBuffer.append("\nSHA-1 Hash: ");
            stringBuffer.append(Utils.toString(this.c));
        }
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        byte[] bArr;
        if (this == obj) {
            return true;
        }
        if (obj instanceof URLAndOptionalHash) {
            URLAndOptionalHash uRLAndOptionalHash = (URLAndOptionalHash) obj;
            if (Utils.equalsBlock(this.b, uRLAndOptionalHash.b)) {
                byte[] bArr2 = this.c;
                if (bArr2 != null && (bArr = uRLAndOptionalHash.c) != null) {
                    return Utils.equalsBlock(bArr2, bArr);
                }
                if (bArr2 == null && uRLAndOptionalHash.c == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        int iA = Utils.a(this.b);
        byte[] bArr = this.c;
        return bArr != null ? iA + Utils.a(bArr) : iA;
    }
}

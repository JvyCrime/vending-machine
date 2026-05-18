package iaik.security.ssl;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes.dex */
public class KeyAndCertURL extends KeyAndCert {
    public static final int CHT_INDIVIDUAL_CERTS = 0;
    public static final int CHT_PKI_PATH = 1;
    private int a;
    private URLAndOptionalHash[] b;

    public KeyAndCertURL(int i, PrivateKey privateKey, int i2, URLAndOptionalHash[] uRLAndOptionalHashArr) {
        super(i, privateKey);
        if (Utils.a(uRLAndOptionalHashArr)) {
            throw new NullPointerException("urlAndOptionalHashList must not be null!");
        }
        if (i2 != 0 && i2 != 1) {
            StringBuffer stringBuffer = new StringBuffer("Invalid cert chain type (");
            stringBuffer.append(i2);
            stringBuffer.append("). ");
            stringBuffer.append("Must be CHT_INDIVIDUAL_CERTS (0) or CHT_PKI_PATH (1)!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (i2 == 1 && uRLAndOptionalHashArr.length > 1) {
            throw new IllegalArgumentException("Only one URLAndOptionalHash element allowed for PkiPath!");
        }
        this.a = i2;
        this.b = uRLAndOptionalHashArr;
    }

    public KeyAndCertURL(X509Certificate[] x509CertificateArr, PrivateKey privateKey, int i, String[] strArr, boolean z) throws Exception {
        super(x509CertificateArr, privateKey);
        if (Utils.a(strArr)) {
            throw new NullPointerException("urls must not be null!");
        }
        if (i != 0 && i != 1) {
            StringBuffer stringBuffer = new StringBuffer("Invalid cert chain type (");
            stringBuffer.append(i);
            stringBuffer.append("). ");
            stringBuffer.append("Must be CHT_INDIVIDUAL_CERTS (0) or CHT_PKI_PATH (1)!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        this.a = i;
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        MessageDigest messageDigest = z ? securityProvider.getMessageDigest("SHA") : null;
        if (this.a == 0) {
            if (strArr.length != x509CertificateArr.length) {
                throw new IllegalArgumentException("Number of certs and urls must be equal for individual_certs chain type!");
            }
            int length = strArr.length;
            this.b = new URLAndOptionalHash[length];
            for (int i2 = 0; i2 < length; i2++) {
                this.b[i2] = new URLAndOptionalHash(strArr[i2]);
                if (z) {
                    this.b[i2].setHash(messageDigest.digest(x509CertificateArr[i2].getEncoded()));
                }
            }
            return;
        }
        if (strArr.length != 1) {
            throw new IllegalArgumentException("Only one url allowed for pki_path chain type!");
        }
        this.b = new URLAndOptionalHash[]{new URLAndOptionalHash(strArr[0])};
        if (z) {
            this.b[0].setHash(messageDigest.digest(securityProvider.createPkiPath(x509CertificateArr)));
        }
    }

    public int getCertChainType() {
        return this.a;
    }

    public URLAndOptionalHash[] getURLAndOptionalHashList() {
        return this.b;
    }

    @Override // iaik.security.ssl.KeyAndCert
    public int hashCode() {
        int iHashCode = super.hashCode();
        if (this.b != null) {
            int i = 0;
            while (true) {
                URLAndOptionalHash[] uRLAndOptionalHashArr = this.b;
                if (i >= uRLAndOptionalHashArr.length) {
                    break;
                }
                iHashCode += uRLAndOptionalHashArr[i].hashCode();
                i++;
            }
        }
        return iHashCode;
    }

    @Override // iaik.security.ssl.KeyAndCert
    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof KeyAndCertURL)) {
                return false;
            }
            KeyAndCertURL keyAndCertURL = (KeyAndCertURL) obj;
            if (getCertificateType() != keyAndCertURL.getCertificateType() || !getPrivateKey().equals(keyAndCertURL.getPrivateKey())) {
                return false;
            }
            URLAndOptionalHash[] uRLAndOptionalHashArr = this.b;
            if (uRLAndOptionalHashArr.length != keyAndCertURL.b.length) {
                return false;
            }
            int length = uRLAndOptionalHashArr.length;
            int i = 0;
            while (true) {
                URLAndOptionalHash[] uRLAndOptionalHashArr2 = this.b;
                if (i >= uRLAndOptionalHashArr2.length || !uRLAndOptionalHashArr2[i].equals(keyAndCertURL.b[i])) {
                    break;
                }
                i++;
            }
            if (i != length) {
                return false;
            }
        }
        return true;
    }

    @Override // iaik.security.ssl.KeyAndCert
    public Object clone() {
        return super.clone();
    }

    @Override // iaik.security.ssl.KeyAndCert
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Utils.certTypeToString(getCertificateType()));
        stringBuffer.append(" credentials:\n");
        StringBuffer stringBuffer2 = new StringBuffer("  ");
        stringBuffer2.append(Utils.a(getPrivateKey()));
        stringBuffer2.append(" bit key, ");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer(String.valueOf(this.b.length));
        stringBuffer3.append(" urls");
        stringBuffer.append(stringBuffer3.toString());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

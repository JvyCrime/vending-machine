package iaik.x509.ocsp.utils;

import iaik.utils.Util;
import iaik.x509.X509Certificate;
import iaik.x509.ocsp.ResponderID;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes2.dex */
public class TrustedResponders {
    private Hashtable a = new Hashtable();

    public boolean addTrustedResponderEntry(ResponderID responderID, X509Certificate x509Certificate) {
        X509Certificate[] x509CertificateArr;
        X509Certificate[] x509CertificateArr2 = (X509Certificate[]) this.a.get(responderID);
        if (x509CertificateArr2 == null || x509CertificateArr2.length <= 0) {
            x509CertificateArr = new X509Certificate[]{x509Certificate};
        } else {
            for (X509Certificate x509Certificate2 : x509CertificateArr2) {
                if (x509Certificate2.equals(x509Certificate)) {
                    return false;
                }
            }
            x509CertificateArr = (X509Certificate[]) Util.resizeArray(x509CertificateArr2, x509CertificateArr2.length + 1);
            x509CertificateArr[x509CertificateArr.length - 1] = x509Certificate;
        }
        return (this.a.put(responderID, x509CertificateArr) != null) || x509CertificateArr.length == 1;
    }

    public void clearAllEntries() {
        this.a.clear();
    }

    public boolean isTrustedResponder(ResponderID responderID, X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        X509Certificate[] x509CertificateArr = (X509Certificate[]) this.a.get(responderID);
        if (x509CertificateArr != null && x509CertificateArr.length > 0) {
            for (X509Certificate x509Certificate3 : x509CertificateArr) {
                if (x509Certificate3.equals(x509Certificate2) && x509Certificate.getIssuerDN().equals(x509Certificate2.getSubjectDN())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeTrustedResponder(ResponderID responderID) {
        return this.a.remove(responderID) != null;
    }

    public boolean removeTrustedResponderEntry(ResponderID responderID, X509Certificate x509Certificate) {
        X509Certificate[] x509CertificateArr = (X509Certificate[]) this.a.get(responderID);
        if (x509CertificateArr != null && x509CertificateArr.length > 0) {
            int length = x509CertificateArr.length;
            if (length == 1) {
                if (x509CertificateArr[0].equals(x509Certificate)) {
                    return removeTrustedResponder(responderID);
                }
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (x509CertificateArr[i].equals(x509Certificate)) {
                    x509CertificateArr[i] = null;
                    int i2 = length - 1;
                    X509Certificate[] x509CertificateArr2 = new X509Certificate[i2];
                    for (int i3 = 0; i3 < i2; i3++) {
                        if (x509CertificateArr[i3] == null) {
                            x509CertificateArr2[i3] = x509CertificateArr[i3 + 1];
                        } else {
                            x509CertificateArr2[i3] = x509CertificateArr[i3];
                        }
                    }
                    this.a.put(responderID, x509CertificateArr2);
                    return true;
                }
            }
        }
        return false;
    }
}

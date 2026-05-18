package iaik.security.ssl;

import java.io.IOException;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class h extends x {
    private int a;
    private URLAndOptionalHash[] b;

    h(int i, URLAndOptionalHash[] uRLAndOptionalHashArr) {
        super(21);
        this.a = i;
        this.b = uRLAndOptionalHashArr;
    }

    h(ab abVar) throws IOException {
        super(21);
        a(abVar);
    }

    int a() {
        return this.a;
    }

    URLAndOptionalHash[] b() {
        return this.b;
    }

    void a(ab abVar) throws IOException {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        int iH = abVar.h();
        int iK = abVar.k();
        this.a = iK;
        if (iK != 0 && iK != 1) {
            StringBuffer stringBuffer = new StringBuffer("Invalid cert chain type (");
            stringBuffer.append(this.a);
            stringBuffer.append("). ");
            stringBuffer.append("Must be CHT_INDIVIDUAL_CERTS (0) or CHT_PKI_PATH (1)!");
            throw new SSLException(stringBuffer.toString());
        }
        int i = iH - 1;
        Vector vector = new Vector();
        while (i > 0) {
            byte[] bArrG = abVar.g();
            int length = i - (bArrG.length + 2);
            if (length <= 0) {
                throw new SSLException("Unexpected EOF while reading CertificateURL message!", 2, 50, false);
            }
            try {
                String strDecodeURL = securityProvider.decodeURL(bArrG);
                int iK2 = abVar.k();
                if (iK2 != 0) {
                    if (iK2 == 1) {
                        byte[] bArr = new byte[20];
                        if (abVar.a(bArr) != 20) {
                            throw new SSLException("Invalid hash value in URLAndOptionalHash list!", 2, 50, false);
                        }
                        URLAndOptionalHash uRLAndOptionalHash = new URLAndOptionalHash(strDecodeURL);
                        uRLAndOptionalHash.setHash(bArr);
                        vector.addElement(uRLAndOptionalHash);
                        i = length - 21;
                    } else {
                        StringBuffer stringBuffer2 = new StringBuffer("Invalid boolean hashPresent value (");
                        stringBuffer2.append(iK2);
                        stringBuffer2.append(") in URLAndOptionalHash list!");
                        throw new SSLException(stringBuffer2.toString(), 2, 50, false);
                    }
                } else {
                    if (abVar.k() != 0) {
                        throw new SSLException("Invalid hash value in URLAndOptionalHash list!", 2, 50, false);
                    }
                    i = length - 2;
                    vector.addElement(new URLAndOptionalHash(strDecodeURL));
                }
            } catch (Exception e) {
                StringBuffer stringBuffer3 = new StringBuffer("Cannot decode client certificate url: ");
                stringBuffer3.append(e.toString());
                throw new SSLException(stringBuffer3.toString(), 2, 80, false);
            }
        }
        if (i != 0) {
            throw new SSLException("Invalid length field in CertificateURL message!", 2, 50, false);
        }
        int size = vector.size();
        if (size == 0) {
            throw new SSLException("URLAndOptionalHash list is empty!", 2, 50, false);
        }
        URLAndOptionalHash[] uRLAndOptionalHashArr = new URLAndOptionalHash[size];
        this.b = uRLAndOptionalHashArr;
        vector.copyInto(uRLAndOptionalHashArr);
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        int i = 0;
        int i2 = 1;
        while (true) {
            URLAndOptionalHash[] uRLAndOptionalHashArr = this.b;
            int i3 = 21;
            if (i >= uRLAndOptionalHashArr.length) {
                break;
            }
            int length = i2 + uRLAndOptionalHashArr[i].getEncodedURL().length + 2;
            if (this.b[i].getHash() == null) {
                i3 = 2;
            }
            i2 = length + i3;
            i++;
        }
        agVar.g(21);
        agVar.e(i2);
        agVar.g(this.a);
        int i4 = 0;
        while (true) {
            URLAndOptionalHash[] uRLAndOptionalHashArr2 = this.b;
            if (i4 >= uRLAndOptionalHashArr2.length) {
                return;
            }
            URLAndOptionalHash uRLAndOptionalHash = uRLAndOptionalHashArr2[i4];
            agVar.a(uRLAndOptionalHash.getEncodedURL());
            byte[] hash = uRLAndOptionalHash.getHash();
            if (hash == null) {
                agVar.g(0);
                agVar.g(0);
            } else {
                agVar.g(1);
                agVar.write(hash);
            }
            i4++;
        }
    }
}

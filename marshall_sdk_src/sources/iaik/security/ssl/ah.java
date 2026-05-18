package iaik.security.ssl;

import java.io.IOException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
class ah extends n {
    private int V;
    private byte[] W;
    private byte[] X;
    private byte[] Y;

    ah(SSLTransport sSLTransport) throws SSLException {
        super(sSLTransport);
    }

    private void l() throws IOException {
        if (this.T) {
            this.n.a("Received v2 server hello, entering SSL 2.0 mode.");
        }
        a(2);
        this.p.g = 2;
        int iK = this.q.k();
        int iK2 = this.q.k();
        int iF = this.q.f();
        if (iF != 2) {
            this.r.b(2, 0);
            StringBuffer stringBuffer = new StringBuffer("Invalid server selected SSL version ");
            stringBuffer.append(Utils.c(iF));
            throw new SSLException(stringBuffer.toString());
        }
        int iF2 = this.q.f();
        int iF3 = this.q.f();
        int iF4 = this.q.f();
        if (iK != 0) {
            if (this.T) {
                this.n.a("Resuming previous session...");
            }
            if (this.p.c().b()) {
                this.r.b(2, 0);
                throw new SSLException("Server tried to resume a new session!");
            }
            if (iF2 != 0 || iF3 != 0) {
                this.r.b(2, 0);
                throw new SSLException("Invalid parameters for resume session!");
            }
            this.A = this.p.a;
            this.B = this.A.getKeyExchangeAlgorithm();
            this.C = this.p.b;
            this.P = this.p.f;
            this.O = new byte[iF4];
            this.q.a(this.O);
            this.k = 3;
            return;
        }
        if (iK2 != 1) {
            this.r.b(2, 0);
            StringBuffer stringBuffer2 = new StringBuffer("Invalid server certificate type: ");
            stringBuffer2.append(iK2);
            throw new SSLException(stringBuffer2.toString());
        }
        if (iF3 == 0) {
            this.r.b(2, 0);
            throw new SSLException("No common ciphersuites");
        }
        this.Y = new byte[iF2];
        this.q.a(this.Y);
        try {
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            X509Certificate x509Certificate = securityProvider.getX509Certificate(this.Y);
            this.a = x509Certificate.getPublicKey();
            securityProvider.checkKeyLength(this.a);
            X509Certificate[] x509CertificateArr = {x509Certificate};
            try {
                a(x509CertificateArr, -1, null, null);
                this.p.d = x509CertificateArr;
                this.n.n = x509CertificateArr;
                CipherSuiteList cipherSuiteList = new CipherSuiteList(this.q, iF3, true);
                this.n.l = cipherSuiteList;
                this.A = cipherSuiteList.elementAt(0);
                this.p.a = this.A;
                this.C = CompressionMethod.NULL_COMPRESSION;
                this.p.b = this.C;
                this.B = this.A.getKeyExchangeAlgorithm();
                if (this.T) {
                    this.n.a("Common ciphersuites:");
                    Enumeration enumerationElements = cipherSuiteList.elements();
                    while (enumerationElements.hasMoreElements()) {
                        this.n.a(enumerationElements.nextElement().toString());
                    }
                    SSLTransport sSLTransport = this.n;
                    StringBuffer stringBuffer3 = new StringBuffer("Selecting CipherSuite: ");
                    stringBuffer3.append(this.A);
                    sSLTransport.a(stringBuffer3.toString());
                }
                if (!this.D.getEnabledCipherSuiteList().b().contains(this.A)) {
                    this.r.b(2, 0);
                    throw new SSLException("Server returned a not enabled cipher suite?!");
                }
                this.O = new byte[iF4];
                this.q.a(this.O);
                this.k = 2;
            } catch (SSLCertificateException e) {
                this.n.n = x509CertificateArr;
                this.r.b(2, 0);
                throw e;
            }
        } catch (Exception e2) {
            this.r.b(2, 0);
            StringBuffer stringBuffer4 = new StringBuffer("Error decoding server certificate: ");
            stringBuffer4.append(e2);
            throw new SSLException(stringBuffer4.toString());
        }
    }

    private void p() throws IOException {
        SecureRandom randomGenerator = this.D.getRandomGenerator();
        int expandedKeyLength = this.A.getExpandedKeyLength();
        int keyLength = this.A.getKeyLength();
        int i = expandedKeyLength - keyLength;
        this.P = new byte[expandedKeyLength];
        randomGenerator.nextBytes(this.P);
        this.p.f = this.P;
        byte[] bArr = new byte[i];
        System.arraycopy(this.P, 0, bArr, 0, i);
        byte[] bArr2 = new byte[keyLength];
        System.arraycopy(this.P, i, bArr2, 0, keyLength);
        try {
            byte[] bArrDoFinal = SecurityProvider.getSecurityProvider().getCipher(this.D.getAllowedProtocolVersions()[1] > 2 ? SecurityProvider.ALG_CIPHER_RSA_ENCRYPT_SSL2 : SecurityProvider.ALG_CIPHER_RSA_ENCRYPT, 1, this.a, null, randomGenerator).doFinal(bArr2);
            int iVSize = this.A.getIVSize();
            byte[] bArr3 = new byte[iVSize];
            randomGenerator.nextBytes(bArr3);
            this.p.j = bArr3;
            if (this.T) {
                this.n.a("Sending client master key message...");
            }
            this.r.write(2);
            this.A.b(this.r);
            this.r.a(i);
            this.r.a(bArrDoFinal.length);
            this.r.a(iVSize);
            this.r.write(bArr);
            this.r.write(bArrDoFinal);
            this.r.write(bArr3);
            this.r.b(257);
            this.k = 3;
        } catch (Exception unused) {
            this.r.b(2, 0);
            throw new SSLException("Could not encrypt key data");
        }
    }

    private byte[] b(int i) {
        if (this.u == null) {
            try {
                this.u = SecurityProvider.getSecurityProvider().getMessageDigest(SecurityProvider.ALG_DIGEST_MD5);
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("No implementation for SHA or MD5: ");
                stringBuffer.append(e.toString());
                throw new RuntimeException(stringBuffer.toString());
            }
        }
        byte[] bArr = new byte[i];
        int i2 = 48;
        int i3 = 0;
        while (i3 < i) {
            this.u.update(this.P);
            this.u.update((byte) i2);
            this.u.update(this.N);
            this.u.update(this.O);
            byte[] bArrDigest = this.u.digest();
            int iA = Utils.a(bArrDigest.length, i - i3);
            System.arraycopy(bArrDigest, 0, bArr, i3, iA);
            i3 += iA;
            i2++;
        }
        return bArr;
    }

    private void o() throws IOException {
        String cipherAlgorithm = this.A.getCipherAlgorithm();
        String macAlgorithm = this.A.getMacAlgorithm();
        String strSubstring = cipherAlgorithm.substring(0, cipherAlgorithm.indexOf(47));
        int expandedKeyLength = this.A.getExpandedKeyLength();
        this.X = b(expandedKeyLength + expandedKeyLength);
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.X, 0, expandedKeyLength, strSubstring);
        SecretKeySpec secretKeySpec2 = new SecretKeySpec(this.X, expandedKeyLength, expandedKeyLength, strSubstring);
        int iA = this.A.a();
        IvParameterSpec ivParameterSpec = iA == 2 ? new IvParameterSpec(this.p.j) : null;
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        try {
            this.G = new ad(securityProvider.getMessageDigest(macAlgorithm), this.q.a());
            this.H = new ad(securityProvider.getMessageDigest(macAlgorithm), this.r.c());
            this.E = securityProvider.getCipher(cipherAlgorithm, 2, secretKeySpec, ivParameterSpec, null);
            this.F = securityProvider.getCipher(cipherAlgorithm, 1, secretKeySpec2, ivParameterSpec, null);
            this.G.a(secretKeySpec.getEncoded());
            this.H.a(secretKeySpec2.getEncoded());
            this.r.a(this.F, this.H, this.J, iA, this.C);
            this.q.a(this.E, this.G, this.I, iA, this.C);
            if (this.T) {
                this.n.a("Sending client finish message...");
            }
            this.r.write(3);
            this.r.write(this.O);
            this.r.b(257);
            this.k = 4;
        } catch (Exception e) {
            this.r.b(2, 0);
            StringBuffer stringBuffer = new StringBuffer("Exception during cipher init: ");
            stringBuffer.append(e);
            throw new SSLException(stringBuffer.toString());
        }
    }

    private void m() throws IOException {
        if (this.T) {
            this.n.a("Received server verify message.");
        }
        byte[] bArr = new byte[this.q.available()];
        this.q.a(bArr);
        if (!Utils.equalsBlock(this.N, bArr)) {
            throw new IOException("server verify error");
        }
        this.k = 5;
    }

    private void k() throws IOException {
        if (this.T) {
            this.n.a("Received server finish message.");
        }
        byte[] bArr = new byte[this.q.available()];
        this.q.a(bArr);
        this.p.c = new SessionID(bArr);
        this.k = 8;
    }

    private void j() throws IOException {
        if (this.T) {
            this.n.a("Received certificate request message.");
        }
        this.V = this.q.k();
        this.W = new byte[this.q.available()];
        this.q.a(this.W);
        this.k = 6;
    }

    private void n() throws IOException {
        if (this.V != 1) {
            if (this.T) {
                SSLTransport sSLTransport = this.n;
                StringBuffer stringBuffer = new StringBuffer("Sending unsupported certificate type (");
                stringBuffer.append(this.V);
                stringBuffer.append(") warning...");
                sSLTransport.a(stringBuffer.toString());
            }
            this.r.b(1, 6);
            this.k = 7;
            return;
        }
        KeyAndCert keyAndCertA = a(new f(new byte[]{1}, new Principal[0], null), (PublicKey) null, (SignatureAndHashAlgorithmList) null);
        if (keyAndCertA == null) {
            if (this.T) {
                this.n.a("Sending no certificate warning...");
            }
            this.r.b(1, 2);
            this.k = 7;
            return;
        }
        if (this.T) {
            this.n.a("Sending client certificate...");
        }
        try {
            byte[] encoded = keyAndCertA.getCertificateChain()[0].getEncoded();
            Signature signature = SecurityProvider.getSecurityProvider().getSignature(SecurityProvider.ALG_SIGNATURE_MD5RSA, 1, keyAndCertA.getPrivateKey(), this.D.getRandomGenerator());
            signature.update(this.X);
            signature.update(this.W);
            signature.update(this.Y);
            byte[] bArrSign = signature.sign();
            this.r.g(8);
            this.r.g(1);
            this.r.a(encoded.length);
            this.r.a(bArrSign.length);
            this.r.write(encoded);
            this.r.write(bArrSign);
            this.r.b(257);
            this.k = 7;
        } catch (Exception e) {
            this.r.b(2, 0);
            StringBuffer stringBuffer2 = new StringBuffer("Error creating client certificate message: ");
            stringBuffer2.append(e);
            throw new SSLException(stringBuffer2.toString());
        }
    }

    private void h() throws IOException {
        String string;
        if (this.T) {
            this.n.a("Received error message.");
        }
        int iF = this.q.f();
        if (iF == 0) {
            string = "undefined error";
        } else if (iF == 1) {
            string = "no cipher";
        } else if (iF == 2) {
            string = "no certificate";
        } else if (iF == 4) {
            string = "bad certificate";
        } else if (iF != 6) {
            StringBuffer stringBuffer = new StringBuffer("unknown error type ");
            stringBuffer.append(iF);
            string = stringBuffer.toString();
        } else {
            string = "unsupported certificate type";
        }
        this.k = 9;
        this.r.b(2, 0);
        StringBuffer stringBuffer2 = new StringBuffer("Received error message: ");
        stringBuffer2.append(string);
        throw new SSLException(stringBuffer2.toString());
    }

    private void i() throws IOException {
        if (this.k != 1) {
            this.q.e();
        }
        int iK = this.q.k();
        if (this.k == 1 && iK == 4) {
            l();
            return;
        }
        if (this.k == 4 && iK == 5) {
            m();
            return;
        }
        if (this.k == 5 && iK == 6) {
            k();
            return;
        }
        if (this.k == 5 && iK == 7) {
            j();
            return;
        }
        if (this.k == 7 && iK == 6) {
            k();
            return;
        }
        if (iK == 0) {
            h();
            return;
        }
        this.k = 9;
        this.r.b(2, 0);
        StringBuffer stringBuffer = new StringBuffer("Received unexpected V2 handshake message: ");
        stringBuffer.append(aj.b(iK));
        throw new SSLException(stringBuffer.toString());
    }

    void g() throws IOException {
        this.k = 1;
        while (true) {
            switch (this.k) {
                case 1:
                case 4:
                case 5:
                case 7:
                    i();
                    break;
                case 2:
                    p();
                    break;
                case 3:
                    o();
                    break;
                case 6:
                    n();
                    break;
                case 8:
                case 9:
                    return;
                default:
                    this.r.b(2, 0);
                    throw new SSLException("Internal state error.");
            }
        }
    }
}

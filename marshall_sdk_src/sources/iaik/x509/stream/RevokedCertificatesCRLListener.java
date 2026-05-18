package iaik.x509.stream;

import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.Name;
import iaik.utils.CryptoUtils;
import iaik.x509.RevokedCertificate;
import iaik.x509.X509Certificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509Extensions;
import iaik.x509.extensions.CertificateIssuer;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CRLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes2.dex */
public class RevokedCertificatesCRLListener implements CRLListener {
    private X509Certificate[] a;
    private PublicKey b;
    private Hashtable c = new Hashtable(2);
    private int d;
    private AlgorithmID e;
    private Name f;
    private Name g;
    private Date h;
    private Date i;
    private boolean j;
    private Hashtable k;
    private X509Extensions l;
    private AlgorithmID m;
    private byte[] n;
    private boolean o;
    private byte[][] p;

    public RevokedCertificatesCRLListener(X509Certificate[] x509CertificateArr, PublicKey publicKey) {
        this.a = x509CertificateArr;
        this.p = new byte[x509CertificateArr.length][];
        for (int i = 0; i < x509CertificateArr.length; i++) {
            Name name = (Name) x509CertificateArr[i].getIssuerDN();
            Hashtable hashtable = (Hashtable) this.c.get(name);
            if (hashtable == null) {
                hashtable = new Hashtable(256);
                this.c.put(name, hashtable);
            }
            BigInteger serialNumber = x509CertificateArr[i].getSerialNumber();
            hashtable.put(serialNumber, x509CertificateArr[i]);
            this.p[i] = serialNumber.toByteArray();
        }
        this.b = publicKey;
        this.k = new Hashtable();
    }

    @Override // iaik.x509.stream.CRLListener
    public void extensions(X509Extensions x509Extensions) {
        this.l = x509Extensions;
    }

    public X509Certificate[] getConsideredCertificates() {
        return this.a;
    }

    public X509Extensions getExtensions() {
        return this.l;
    }

    public Name getIssuer() {
        return this.f;
    }

    public PublicKey getIssuerKey() {
        return this.b;
    }

    public Date getNextUpdate() {
        return this.i;
    }

    public Hashtable getRevokedCertificates() {
        return this.k;
    }

    public AlgorithmID getSignature() {
        return this.m;
    }

    @Override // iaik.x509.stream.CRLListener
    public Signature getSignature(AlgorithmID algorithmID) throws NoSuchAlgorithmException, InvalidKeyException {
        if (this.b == null) {
            return null;
        }
        Signature signatureInstance = algorithmID.getSignatureInstance("IAIK");
        signatureInstance.initVerify(this.b);
        this.e = algorithmID;
        return signatureInstance;
    }

    public AlgorithmID getSignatureAlgorithm() {
        return this.e;
    }

    public byte[] getSignatureValue() {
        return this.n;
    }

    public Date getThisUpdate() {
        return this.h;
    }

    public int getVersion() {
        return this.d;
    }

    @Override // iaik.x509.stream.CRLListener
    public void header(int i, Name name, Date date, Date date2) {
        this.d = i;
        this.f = name;
        this.g = name;
        this.h = date;
        this.i = date2;
    }

    public boolean isIndirect() {
        return this.j;
    }

    public boolean isVerified() {
        return this.o;
    }

    @Override // iaik.x509.stream.CRLListener
    public void revokedCertificate(RevokedCertificate revokedCertificate) throws X509ExtensionException, CRLException {
        X509Certificate x509Certificate;
        CertificateIssuer certificateIssuer = (CertificateIssuer) revokedCertificate.getExtension(CertificateIssuer.oid);
        if (certificateIssuer != null) {
            Name name = null;
            Enumeration names = certificateIssuer.getIssuer().getNames();
            while (true) {
                if (!names.hasMoreElements()) {
                    break;
                }
                GeneralName generalName = (GeneralName) names.nextElement();
                if (generalName.getType() == 4) {
                    name = (Name) generalName.getName();
                    break;
                }
            }
            if (name == null) {
                throw new X509ExtensionException("CertificateIssuer extension does not contain a directoryName.");
            }
            this.g = name;
            this.j = true;
        }
        Hashtable hashtable = (Hashtable) this.c.get(this.g);
        if (hashtable == null || (x509Certificate = (X509Certificate) hashtable.get(revokedCertificate.getSerialNumber())) == null) {
            return;
        }
        this.k.put(x509Certificate, revokedCertificate);
    }

    public void revokedCertificate(byte[] bArr, int i, int i2, int i3, int i4) throws X509ExtensionException, CRLException {
        int i5 = 0;
        while (true) {
            try {
                byte[][] bArr2 = this.p;
                if (i5 >= bArr2.length) {
                    return;
                }
                if (bArr2[i5].length == i4 && CryptoUtils.equalsBlock(bArr2[i5], 0, bArr, i3, i4)) {
                    byte[] bArr3 = new byte[i2];
                    System.arraycopy(bArr, i, bArr3, 0, i2);
                    revokedCertificate(new RevokedCertificate(DerCoder.decode(bArr3)));
                    return;
                }
                i5++;
            } catch (CodingException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("CRL entry decoding error: ");
                stringBuffer.append(e);
                throw new CRLException(stringBuffer.toString());
            }
        }
    }

    @Override // iaik.x509.stream.CRLListener
    public void signature(AlgorithmID algorithmID, byte[] bArr, boolean z) throws CRLException {
        this.m = algorithmID;
        this.n = bArr;
        this.o = z;
    }
}

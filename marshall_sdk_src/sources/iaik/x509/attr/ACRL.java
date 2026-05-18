package iaik.x509.attr;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Name;
import iaik.utils.Util;
import iaik.x509.RevokedCertificate;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.extensions.CertificateIssuer;
import iaik.x509.extensions.ReasonCode;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class ACRL extends X509CRL implements ASN1Type {
    private iaik.x509.X509CRL a;

    public ACRL() {
        this.a = new iaik.x509.X509CRL();
    }

    public ACRL(ASN1Object aSN1Object) throws CRLException {
        this.a = new iaik.x509.X509CRL(aSN1Object);
    }

    public ACRL(InputStream inputStream) throws IOException, CRLException {
        this.a = new iaik.x509.X509CRL(inputStream);
    }

    public ACRL(byte[] bArr) throws CRLException {
        this.a = new iaik.x509.X509CRL(bArr);
    }

    private static Name a(AttributeCertificate attributeCertificate) {
        AttCertIssuer issuer = attributeCertificate.getIssuer();
        int vForm = issuer.getVForm();
        if (vForm == 2 || vForm == 2) {
            return ((V2Form) issuer).getIssuerDN();
        }
        return null;
    }

    private static boolean a(RevokedCertificate revokedCertificate) {
        if (revokedCertificate == null) {
            return false;
        }
        try {
            ReasonCode reasonCode = (ReasonCode) revokedCertificate.getExtension(ReasonCode.oid);
            if (reasonCode != null) {
                return reasonCode.getReasonCode() != 8;
            }
            return true;
        } catch (X509ExtensionInitException unused) {
            return true;
        }
    }

    public void addCertificate(AttributeCertificate attributeCertificate, Date date) throws CRLException {
        Name nameA = a(attributeCertificate);
        RevokedAttributeCertificate revokedAttributeCertificate = new RevokedAttributeCertificate(attributeCertificate, date);
        if (nameA != null && !nameA.equals(getIssuerDN())) {
            try {
                CertificateIssuer certificateIssuer = new CertificateIssuer(nameA);
                certificateIssuer.setCritical(true);
                revokedAttributeCertificate.addExtension(certificateIssuer);
            } catch (X509ExtensionException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot add certificate: ");
                stringBuffer.append(e.toString());
                throw new CRLException(stringBuffer.toString());
            }
        }
        try {
            addCertificate(revokedAttributeCertificate);
        } catch (Exception e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Cannot add certificate: ");
            stringBuffer2.append(e2.toString());
            throw new CRLException(stringBuffer2.toString());
        }
    }

    public void addCertificate(RevokedAttributeCertificate revokedAttributeCertificate) {
        this.a.addCertificate(revokedAttributeCertificate);
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        this.a.addExtension(v3Extension);
    }

    public RevokedCertificate containsCertificate(AttributeCertificate attributeCertificate) {
        Name nameA = a(attributeCertificate);
        BigInteger serialNumber = attributeCertificate.getSerialNumber();
        return nameA == null ? this.a.containsCertificate(serialNumber) : this.a.containsCertificate(nameA, serialNumber);
    }

    public RevokedCertificate containsCertificate(BigInteger bigInteger) {
        return this.a.containsCertificate(bigInteger);
    }

    public int countExtensions() {
        return this.a.countExtensions();
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a.decode(aSN1Object);
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        return this.a.getCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getEncoded() throws CRLException {
        return this.a.toByteArray();
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        return this.a.getExtension(objectID);
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        return this.a.getExtensionValue(str);
    }

    public byte[] getFingerprint() {
        return this.a.getFingerprint();
    }

    public byte[] getFingerprint(String str) throws NoSuchAlgorithmException {
        return this.a.getFingerprint();
    }

    public byte[] getFingerprintSHA() {
        return this.a.getFingerprintSHA();
    }

    @Override // java.security.cert.X509CRL
    public Principal getIssuerDN() {
        return this.a.getIssuerDN();
    }

    public Enumeration getIssuerDNs() {
        return this.a.getIssuerDNs();
    }

    @Override // java.security.cert.X509CRL
    public Date getNextUpdate() {
        return this.a.getNextUpdate();
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        return this.a.getNonCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509CRL
    public X509CRLEntry getRevokedCertificate(BigInteger bigInteger) {
        return this.a.getRevokedCertificate(bigInteger);
    }

    @Override // java.security.cert.X509CRL
    public Set getRevokedCertificates() {
        return this.a.getRevokedCertificates();
    }

    @Override // java.security.cert.X509CRL
    public String getSigAlgName() {
        return this.a.getSigAlgName();
    }

    @Override // java.security.cert.X509CRL
    public String getSigAlgOID() {
        return this.a.getSigAlgOID();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSigAlgParams() {
        return this.a.getSigAlgParams();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSignature() {
        return this.a.getSignature();
    }

    public AlgorithmID getSignatureAlgorithm() {
        return this.a.getSignatureAlgorithm();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getTBSCertList() throws CRLException {
        return this.a.getTBSCertList();
    }

    @Override // java.security.cert.X509CRL
    public Date getThisUpdate() {
        return this.a.getThisUpdate();
    }

    @Override // java.security.cert.X509CRL
    public int getVersion() {
        return this.a.getVersion();
    }

    public boolean hasExtensions() {
        return this.a.hasExtensions();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        return this.a.hasUnsupportedCriticalExtension();
    }

    public boolean isIndirectCRL() {
        return this.a.isIndirectCRL();
    }

    public boolean isRevoked(BigInteger bigInteger) {
        return this.a.isRevoked(bigInteger);
    }

    @Override // java.security.cert.CRL
    public boolean isRevoked(Certificate certificate) {
        try {
            return a(containsCertificate(Util.convertToAttributeCertificate(certificate)));
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid certificate format: ");
            stringBuffer.append(e.toString());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public Enumeration listCertificates() {
        return this.a.listCertificates();
    }

    public Enumeration listExtensions() {
        return this.a.listExtensions();
    }

    public void removeAllCertificates() {
        this.a.removeAllCertificates();
    }

    public void removeAllExtensions() {
        this.a.removeAllExtensions();
    }

    public boolean removeCertificate(AttributeCertificate attributeCertificate) {
        Name nameA = a(attributeCertificate);
        return nameA != null ? this.a.removeCertificate(nameA, attributeCertificate.getSerialNumber()) : this.a.removeCertificate(attributeCertificate.getSerialNumber());
    }

    public boolean removeCertificate(BigInteger bigInteger) {
        return this.a.removeCertificate(bigInteger);
    }

    public boolean removeExtension(ObjectID objectID) {
        return this.a.removeExtension(objectID);
    }

    public void setIssuerDN(Principal principal) throws IllegalArgumentException {
        this.a.setIssuerDN(principal);
    }

    public void setNextUpdate(Date date) {
        this.a.setNextUpdate(date);
    }

    public void setSignature(byte[] bArr) throws CRLException {
        this.a.setSignature(bArr);
    }

    public void setSignatureAlgorithm(AlgorithmID algorithmID) {
        this.a.setSignatureAlgorithm(algorithmID);
    }

    public void setThisUpdate(Date date) {
        this.a.setThisUpdate(date);
    }

    public void sign(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, CRLException {
        this.a.sign(privateKey, null);
    }

    public void sign(PrivateKey privateKey, String str) throws NoSuchAlgorithmException, InvalidKeyException, CRLException {
        this.a.sign(privateKey, str);
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return this.a.toASN1Object();
    }

    public byte[] toByteArray() {
        return this.a.toByteArray();
    }

    @Override // java.security.cert.CRL
    public String toString() {
        return this.a.toString(false);
    }

    public String toString(boolean z) {
        return this.a.toString(true);
    }

    @Override // java.security.cert.X509CRL
    public void verify(PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CRLException, NoSuchProviderException {
        this.a.verify(publicKey, (String) null);
    }

    @Override // java.security.cert.X509CRL
    public void verify(PublicKey publicKey, String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CRLException, NoSuchProviderException {
        this.a.verify(publicKey, str);
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.a.writeTo(outputStream);
    }
}

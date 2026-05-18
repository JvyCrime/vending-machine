package iaik.x509;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.Name;
import iaik.utils.Util;
import iaik.x509.extensions.BasicConstraints;
import iaik.x509.extensions.KeyUsage;
import iaik.x509.extensions.SubjectAltName;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class X509Certificate extends java.security.cert.X509Certificate implements ASN1Type {
    private transient ASN1 a;
    private int b;
    private BigInteger c;
    private AlgorithmID d;
    private Name e;
    private ChoiceOfTime f;
    private ChoiceOfTime g;
    private Name h;
    private PublicKey i;
    private BigInteger j;
    private BigInteger k;
    private byte[] l;
    private X509Extensions m;
    private boolean n;
    private byte[] o;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    public X509Certificate() {
        this.b = 1;
        a();
        this.a = new ASN1();
    }

    public X509Certificate(ASN1Object aSN1Object) throws CertificateException {
        try {
            decode(aSN1Object);
        } catch (CodingException e) {
            throw new CertificateException(e.toString());
        }
    }

    public X509Certificate(InputStream inputStream) throws IOException, CertificateException {
        Objects.requireNonNull(inputStream, "Cannot parse certificate from a null input stream!");
        decode(inputStream);
    }

    public X509Certificate(byte[] bArr) throws CertificateException {
        Objects.requireNonNull(bArr, "Cannot parse certificate from a null byte array!");
        try {
            this.a = new ASN1(bArr);
            d();
        } catch (CodingException e) {
            throw new CertificateException(e.toString());
        } catch (X509ExtensionException e2) {
            throw new CertificateException(e2.toString());
        }
    }

    private String a(boolean[] zArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (boolean z : zArr) {
            stringBuffer.append(z ? '1' : '0');
        }
        return stringBuffer.toString();
    }

    private void a() {
        this.n = true;
        this.o = null;
        this.a = null;
    }

    private boolean[] a(String str) {
        int length = str.length();
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = str.charAt(i) == '1';
        }
        return zArr;
    }

    private void b() {
        if (this.n) {
            throw new RuntimeException("Cannot perform operation, certificate has to be signed first");
        }
    }

    private void c() {
        this.n = false;
    }

    private void d() throws X509ExtensionException, CertificateException {
        int i;
        try {
            if (this.a.countComponents() != 3) {
                throw new CertificateException("Certificate SEQUENCE must have 3 components!");
            }
            ASN1Object componentAt = this.a.getComponentAt(0);
            AlgorithmID algorithmID = new AlgorithmID(this.a.getComponentAt(1));
            this.l = (byte[]) ((BIT_STRING) this.a.getComponentAt(2)).getValue();
            ASN1Object componentAt2 = componentAt.getComponentAt(0);
            if (componentAt2.isA(ASN.CON_SPEC)) {
                this.b = ((BigInteger) ((ASN1Object) componentAt2.getValue()).getValue()).intValue() + 1;
                i = 1;
            } else {
                this.b = 1;
                i = 0;
            }
            this.c = (BigInteger) componentAt.getComponentAt(i + 0).getValue();
            AlgorithmID algorithmID2 = new AlgorithmID(componentAt.getComponentAt(i + 1));
            this.d = algorithmID2;
            if (!algorithmID.equals(algorithmID2)) {
                throw new CertificateException("Certificate signature algorithm mismatch");
            }
            this.e = new Name(componentAt.getComponentAt(i + 2));
            ASN1 asn1 = new ASN1(componentAt.getComponentAt(i + 3));
            this.f = new ChoiceOfTime(asn1.getComponentAt(0));
            this.g = new ChoiceOfTime(asn1.getComponentAt(1));
            this.h = new Name(componentAt.getComponentAt(i + 4));
            ASN1Object componentAt3 = componentAt.getComponentAt(i + 5);
            int i2 = i + 6;
            while (i2 < componentAt.countComponents()) {
                int i3 = i2 + 1;
                CON_SPEC con_spec = (CON_SPEC) componentAt.getComponentAt(i2);
                if (con_spec.getAsnType().getTag() == 1) {
                    con_spec.forceImplicitlyTagged(ASN.BIT_STRING);
                    this.j = new BigInteger(1, (byte[]) ((ASN1Object) con_spec.getValue()).getValue());
                } else if (con_spec.getAsnType().getTag() == 2) {
                    con_spec.forceImplicitlyTagged(ASN.BIT_STRING);
                    this.k = new BigInteger(1, (byte[]) ((ASN1Object) con_spec.getValue()).getValue());
                } else if (con_spec.getAsnType().getTag() == 3) {
                    this.m = new X509Extensions((ASN1Object) con_spec.getValue());
                }
                i2 = i3;
            }
            try {
                this.i = PublicKeyInfo.getPublicKey(componentAt3);
                this.a.clearASN1Object();
                c();
            } catch (InvalidKeyException e) {
                throw new CertificateException(e.toString());
            }
        } catch (CodingException e2) {
            throw new CertificateException(e2.toString());
        } catch (RuntimeException e3) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Certificate format error: ");
            stringBuffer.append(e3.toString());
            throw new CertificateException(stringBuffer.toString());
        }
    }

    private ASN1Object e() throws CertificateEncodingException {
        this.b = 1;
        if (this.c == null) {
            throw new CertificateEncodingException("Serial number not set!");
        }
        if (this.d == null) {
            throw new CertificateEncodingException("Signature algorithm not set!");
        }
        if (this.e == null) {
            throw new CertificateEncodingException("Issuer not set!");
        }
        if (this.f == null) {
            throw new CertificateEncodingException("Valid not before not set!");
        }
        if (this.g == null) {
            throw new CertificateEncodingException("Valid not after not set!");
        }
        if (this.h == null) {
            throw new CertificateEncodingException("Subject not set!");
        }
        if (this.i == null) {
            throw new CertificateEncodingException("Public key not set!");
        }
        if (this.j != null || this.k != null) {
            this.b = 2;
        }
        X509Extensions x509Extensions = this.m;
        if (x509Extensions != null) {
            if (x509Extensions.hasExtensions()) {
                this.b = 3;
            } else {
                this.m = null;
            }
        }
        try {
            SEQUENCE sequence = new SEQUENCE();
            if (this.b > 1) {
                sequence.addComponent(new CON_SPEC(0, new INTEGER(this.b - 1)));
            }
            sequence.addComponent(new INTEGER(this.c));
            sequence.addComponent(this.d.toASN1Object());
            sequence.addComponent(this.e.toASN1Object());
            SEQUENCE sequence2 = new SEQUENCE();
            sequence2.addComponent(this.f.toASN1Object());
            sequence2.addComponent(this.g.toASN1Object());
            sequence.addComponent(sequence2);
            sequence.addComponent(this.h.toASN1Object());
            sequence.addComponent(DerCoder.decode(this.i.getEncoded()));
            if (this.j != null) {
                sequence.addComponent(new CON_SPEC(1, new BIT_STRING(this.j.toByteArray()), true));
            }
            if (this.k != null) {
                sequence.addComponent(new CON_SPEC(2, new BIT_STRING(this.k.toByteArray()), true));
            }
            X509Extensions x509Extensions2 = this.m;
            if (x509Extensions2 != null && x509Extensions2.countExtensions() > 0) {
                sequence.addComponent(new CON_SPEC(3, this.m.toASN1Object()));
            }
            return sequence;
        } catch (Exception e) {
            throw new CertificateEncodingException(e.toString());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.a = new ASN1(objectInputStream);
            d();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to restore Certificate: ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.write(toByteArray());
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        if (this.m == null) {
            this.m = new X509Extensions();
        }
        this.m.addExtension(v3Extension);
        this.b = 3;
        a();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity() throws CertificateNotYetValidException, CertificateExpiredException {
        checkValidity(new Date());
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity(Date date) throws CertificateNotYetValidException, CertificateExpiredException {
        ChoiceOfTime choiceOfTime = this.f;
        if (choiceOfTime == null) {
            throw new CertificateNotYetValidException("ValidNotBefore date not set!");
        }
        if (date.before(choiceOfTime.getDate())) {
            throw new CertificateNotYetValidException();
        }
        ChoiceOfTime choiceOfTime2 = this.g;
        if (choiceOfTime2 == null) {
            throw new CertificateExpiredException("ValidNotAfter date not set!");
        }
        if (date.after(choiceOfTime2.getDate())) {
            throw new CertificateExpiredException();
        }
    }

    public int countExtensions() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return 0;
        }
        return x509Extensions.countExtensions();
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        Objects.requireNonNull(aSN1Object, "Cannot parse certificate from a null object!");
        this.a = new ASN1(aSN1Object);
        try {
            d();
        } catch (Exception e) {
            throw new CodingException(e.toString());
        }
    }

    public void decode(InputStream inputStream) throws IOException, CertificateException {
        Objects.requireNonNull(inputStream, "Cannot parse certificate from a null input stream!");
        try {
            this.a = new ASN1(inputStream);
            d();
        } catch (CodingException e) {
            throw new CertificateException(e.toString());
        } catch (X509ExtensionException e2) {
            throw new CertificateException(e2.toString());
        }
    }

    @Override // java.security.cert.X509Certificate
    public int getBasicConstraints() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return -1;
        }
        try {
            BasicConstraints basicConstraints = (BasicConstraints) x509Extensions.getExtension(BasicConstraints.oid);
            if (basicConstraints == null || !basicConstraints.ca()) {
                return -1;
            }
            int pathLenConstraint = basicConstraints.getPathLenConstraint();
            if (pathLenConstraint == -1) {
                return Integer.MAX_VALUE;
            }
            return pathLenConstraint;
        } catch (Exception unused) {
            return -1;
        }
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getCriticalExtensionOIDs();
    }

    public String[] getEmailAddresses() {
        String[] rDNs;
        Vector vector = new Vector();
        Name name = this.h;
        if (name != null && (rDNs = name.getRDNs(ObjectID.emailAddress)) != null) {
            for (String str : rDNs) {
                vector.addElement(str.toLowerCase());
            }
        }
        try {
            SubjectAltName subjectAltName = (SubjectAltName) getExtension(SubjectAltName.oid);
            if (subjectAltName != null) {
                Enumeration names = subjectAltName.getGeneralNames().getNames();
                while (names.hasMoreElements()) {
                    GeneralName generalName = (GeneralName) names.nextElement();
                    if (generalName.getType() == 1) {
                        vector.addElement(((String) generalName.getName()).toLowerCase());
                    }
                }
            }
        } catch (Exception unused) {
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    @Override // java.security.cert.Certificate
    public byte[] getEncoded() throws CertificateEncodingException {
        b();
        return toByteArray();
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getExtension(objectID);
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getExtensionValue(str);
    }

    public byte[] getFingerprint() {
        b();
        return this.a.fingerprint();
    }

    public byte[] getFingerprint(String str) throws NoSuchAlgorithmException {
        b();
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(toByteArray());
        return messageDigest.digest();
    }

    public byte[] getFingerprintSHA() {
        if (this.o == null) {
            try {
                this.o = getFingerprint("SHA");
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Algorithm SHA not available: ");
                stringBuffer.append(e.toString());
                throw new RuntimeException(stringBuffer.toString());
            }
        }
        return this.o;
    }

    @Override // java.security.cert.X509Certificate
    public Principal getIssuerDN() {
        return this.e;
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getIssuerUniqueID() {
        BigInteger bigInteger = this.j;
        if (bigInteger == null) {
            return null;
        }
        return a(bigInteger.toString(2));
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getKeyUsage() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return null;
        }
        try {
            KeyUsage keyUsage = (KeyUsage) x509Extensions.getExtension(KeyUsage.oid);
            if (keyUsage != null) {
                return keyUsage.getBooleanArray();
            }
        } catch (Exception unused) {
        }
        return null;
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getNonCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509Certificate
    public Date getNotAfter() {
        ChoiceOfTime choiceOfTime = this.g;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    @Override // java.security.cert.X509Certificate
    public Date getNotBefore() {
        ChoiceOfTime choiceOfTime = this.f;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    @Override // java.security.cert.Certificate
    public PublicKey getPublicKey() {
        return this.i;
    }

    public byte[] getRawExtensionValue(String str) {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getRawExtensionValue(str);
    }

    @Override // java.security.cert.X509Certificate
    public BigInteger getSerialNumber() {
        return this.c;
    }

    @Override // java.security.cert.X509Certificate
    public String getSigAlgName() {
        try {
            AlgorithmID algorithmID = this.d;
            if (algorithmID == null) {
                return null;
            }
            return algorithmID.getImplementationName();
        } catch (Exception unused) {
            AlgorithmID algorithmID2 = this.d;
            if (algorithmID2 == null) {
                return null;
            }
            return algorithmID2.getName();
        }
    }

    @Override // java.security.cert.X509Certificate
    public String getSigAlgOID() {
        AlgorithmID algorithmID = this.d;
        if (algorithmID == null) {
            return null;
        }
        return algorithmID.getAlgorithm().getID();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSigAlgParams() {
        AlgorithmID algorithmID = this.d;
        if (algorithmID == null) {
            return null;
        }
        try {
            ASN1Object parameter = algorithmID.getParameter();
            if (parameter == null) {
                return null;
            }
            return new ASN1(parameter).toByteArray();
        } catch (CodingException e) {
            throw new RuntimeException(e.toString());
        }
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSignature() {
        return this.l;
    }

    public AlgorithmID getSignatureAlgorithm() {
        return this.d;
    }

    @Override // java.security.cert.X509Certificate
    public Principal getSubjectDN() {
        return this.h;
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getSubjectUniqueID() {
        BigInteger bigInteger = this.k;
        if (bigInteger == null) {
            return null;
        }
        return a(bigInteger.toString(2));
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        try {
            ASN1 asn1 = this.a;
            return (asn1 == null || asn1.toByteArray() == null) ? DerCoder.encode(e()) : this.a.getFirstObject();
        } catch (CodingException e) {
            throw new CertificateEncodingException(e.toString());
        }
    }

    @Override // java.security.cert.X509Certificate
    public int getVersion() {
        return this.b;
    }

    public boolean hasExtensions() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return false;
        }
        return x509Extensions.hasExtensions();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return false;
        }
        return x509Extensions.hasUnsupportedCriticalExtension();
    }

    public Enumeration listExtensions() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.listExtensions();
    }

    public void removeAllExtensions() {
        X509Extensions x509Extensions = this.m;
        if (x509Extensions != null) {
            x509Extensions.removeAllExtensions();
            a();
        }
        this.m = null;
    }

    public boolean removeExtension(ObjectID objectID) {
        X509Extensions x509Extensions = this.m;
        boolean zRemoveExtension = x509Extensions == null ? false : x509Extensions.removeExtension(objectID);
        if (zRemoveExtension) {
            a();
        }
        return zRemoveExtension;
    }

    public void setIssuerDN(Principal principal) throws IllegalArgumentException {
        try {
            this.e = (Name) principal;
            a();
        } catch (Exception unused) {
            throw new IllegalArgumentException("Issuer is not an instance of Name.");
        }
    }

    public void setIssuerUniqueID(boolean[] zArr) {
        this.j = new BigInteger(a(zArr), 2);
        this.b = 2;
        a();
    }

    public void setPublicKey(PublicKey publicKey) throws InvalidKeyException {
        this.i = publicKey;
        a();
    }

    public void setSerialNumber(BigInteger bigInteger) {
        this.c = bigInteger;
        a();
    }

    public void setSignature(byte[] bArr) throws CertificateException {
        if (bArr == null) {
            throw new CertificateException("Cannot sign certificate! No signature value specified!");
        }
        this.l = bArr;
        ASN1Object aSN1ObjectE = e();
        a();
        try {
            BIT_STRING bit_string = new BIT_STRING(bArr);
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectE);
            sequence.addComponent(this.d.toASN1Object());
            sequence.addComponent(bit_string);
            this.a = new ASN1(sequence);
            c();
        } catch (CodingException e) {
            throw new CertificateException(e.toString());
        }
    }

    public void setSignatureAlgorithm(AlgorithmID algorithmID) {
        this.d = algorithmID;
        a();
    }

    public void setSubjectDN(Principal principal) throws IllegalArgumentException {
        try {
            this.h = (Name) principal;
            a();
        } catch (Exception unused) {
            throw new IllegalArgumentException("Subject is not an instance of Name.");
        }
    }

    public void setSubjectUniqueID(boolean[] zArr) {
        this.k = new BigInteger(a(zArr), 2);
        this.b = 2;
        a();
    }

    public void setValidNotAfter(Date date) {
        this.g = new ChoiceOfTime(date, true, false);
        a();
    }

    public void setValidNotBefore(Date date) {
        this.f = new ChoiceOfTime(date, true, false);
        a();
    }

    public void sign(AlgorithmID algorithmID, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, CertificateException {
        sign(algorithmID, privateKey, null);
    }

    public void sign(AlgorithmID algorithmID, PrivateKey privateKey, String str) throws NoSuchAlgorithmException, InvalidKeyException, CertificateException {
        AlgorithmParameters signatureParameters;
        if (algorithmID == null) {
            throw new CertificateException("Cannot sign certificate! No signature algorithm specified!");
        }
        this.d = algorithmID;
        Signature signatureInstance = algorithmID.getSignatureInstance(str);
        signatureInstance.initSign(privateKey);
        try {
            if (!AlgorithmID.getDoNotIncludeParameters(this.d) && !this.d.hasParameters() && (signatureParameters = Util.getSignatureParameters(signatureInstance)) != null) {
                this.d.setAlgorithmParameters(signatureParameters);
            }
        } catch (Exception unused) {
        }
        ASN1Object aSN1ObjectE = e();
        a();
        try {
            signatureInstance.update(DerCoder.encode(aSN1ObjectE));
            this.l = signatureInstance.sign();
            BIT_STRING bit_string = new BIT_STRING(this.l);
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectE);
            sequence.addComponent(this.d.toASN1Object());
            sequence.addComponent(bit_string);
            this.a = new ASN1(sequence);
            c();
        } catch (CodingException e) {
            throw new CertificateException(e.toString());
        } catch (SignatureException e2) {
            throw new CertificateException(e2.toString());
        }
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        b();
        return this.a.toASN1Object();
    }

    public byte[] toByteArray() {
        b();
        return this.a.toByteArray();
    }

    @Override // java.security.cert.Certificate
    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.b);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        if (this.c != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Serial number: ");
            stringBuffer3.append(this.c);
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        if (this.d != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Signature algorithm: ");
            stringBuffer4.append(this.d);
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        if (this.e != null) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Issuer: ");
            stringBuffer5.append(this.e);
            stringBuffer5.append("\n");
            stringBuffer.append(stringBuffer5.toString());
        }
        if (this.f != null) {
            StringBuffer stringBuffer6 = new StringBuffer();
            stringBuffer6.append("Valid not before: ");
            stringBuffer6.append(this.f);
            stringBuffer6.append("\n");
            stringBuffer.append(stringBuffer6.toString());
        }
        if (this.g != null) {
            StringBuffer stringBuffer7 = new StringBuffer();
            stringBuffer7.append("      not after: ");
            stringBuffer7.append(this.g);
            stringBuffer7.append("\n");
            stringBuffer.append(stringBuffer7.toString());
        }
        if (this.h != null) {
            StringBuffer stringBuffer8 = new StringBuffer();
            stringBuffer8.append("Subject: ");
            stringBuffer8.append(this.h);
            stringBuffer8.append("\n");
            stringBuffer.append(stringBuffer8.toString());
        }
        PublicKey publicKey = this.i;
        if (publicKey != null) {
            stringBuffer.append(publicKey.toString());
        }
        if (this.j != null) {
            StringBuffer stringBuffer9 = new StringBuffer();
            stringBuffer9.append("Issuer Unique ID: ");
            stringBuffer9.append(this.j);
            stringBuffer9.append("\n");
            stringBuffer.append(stringBuffer9.toString());
        }
        if (this.k != null) {
            StringBuffer stringBuffer10 = new StringBuffer();
            stringBuffer10.append("Subject Unique ID: ");
            stringBuffer10.append(this.k);
            stringBuffer10.append("\n");
            stringBuffer.append(stringBuffer10.toString());
        }
        stringBuffer.append("\n");
        StringBuffer stringBuffer11 = new StringBuffer();
        stringBuffer11.append("Certificate Fingerprint (MD5)  : ");
        stringBuffer11.append(Util.toString(getFingerprint()));
        stringBuffer11.append("\n");
        stringBuffer.append(stringBuffer11.toString());
        StringBuffer stringBuffer12 = new StringBuffer();
        stringBuffer12.append("Certificate Fingerprint (SHA-1): ");
        stringBuffer12.append(Util.toString(getFingerprintSHA()));
        stringBuffer12.append("\n");
        stringBuffer.append(stringBuffer12.toString());
        stringBuffer.append("\n");
        X509Extensions x509Extensions = this.m;
        if (x509Extensions != null) {
            if (z) {
                stringBuffer.append(x509Extensions);
            } else {
                StringBuffer stringBuffer13 = new StringBuffer();
                stringBuffer13.append("Extensions: ");
                stringBuffer13.append(this.m.countExtensions());
                stringBuffer13.append("\n");
                stringBuffer.append(stringBuffer13.toString());
            }
        }
        return stringBuffer.toString();
    }

    public void verify() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        verify(this.i);
    }

    @Override // java.security.cert.Certificate
    public void verify(PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        verify(publicKey, (String) null);
    }

    @Override // java.security.cert.Certificate
    public void verify(PublicKey publicKey, String str) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, NoSuchProviderException {
        b();
        Signature signatureInstance = str == null ? this.d.getSignatureInstance() : this.d.getSignatureInstance(str);
        try {
            byte[] firstObject = this.a.getFirstObject();
            signatureInstance.initVerify(publicKey);
            signatureInstance.update(firstObject);
            if (!signatureInstance.verify(this.l)) {
                throw new SignatureException("Signature verification error!");
            }
        } catch (CodingException e) {
            throw new SignatureException(e.toString());
        }
    }

    public void verify(PublicKey publicKey, AlgorithmParameterSpec algorithmParameterSpec) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        b();
        Signature signatureInstance = this.d.getSignatureInstance("IAIK");
        try {
            byte[] firstObject = this.a.getFirstObject();
            signatureInstance.setParameter(algorithmParameterSpec instanceof DSAParameterSpec ? "DSAParameterSpec" : "AlgorithmParameterSpec", algorithmParameterSpec);
            signatureInstance.initVerify(publicKey);
            signatureInstance.update(firstObject);
            if (!signatureInstance.verify(this.l)) {
                throw new SignatureException("Signature verification error!");
            }
        } catch (CodingException e) {
            throw new SignatureException(e.toString());
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        b();
        this.a.writeTo(outputStream);
    }
}

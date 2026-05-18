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
import iaik.asn1.structures.GeneralNames;
import iaik.asn1.structures.Name;
import iaik.utils.ArrayEnumeration;
import iaik.utils.Util;
import iaik.x509.extensions.CertificateIssuer;
import iaik.x509.extensions.ReasonCode;
import java.io.IOException;
import java.io.InputStream;
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
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class X509CRL extends java.security.cert.X509CRL implements ASN1Type {
    private ASN1 a;
    private int b;
    private AlgorithmID c;
    private Name d;
    private ChoiceOfTime e;
    private ChoiceOfTime f;
    private byte[] g;
    private HashMap h;
    private X509Extensions i;
    private boolean j;
    private boolean k;
    private byte[] l;
    private int m;
    private int n;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    public X509CRL() {
        this.m = 2;
        this.n = 4;
        this.b = 1;
        a();
        this.a = new ASN1();
        this.h = new HashMap(10);
    }

    public X509CRL(ASN1Object aSN1Object) throws CRLException {
        this();
        try {
            decode(aSN1Object);
        } catch (CodingException e) {
            throw new CRLException(this, e.toString(), e) { // from class: iaik.x509.X509CRL.3
                private static final long serialVersionUID = -474340416029275471L;
                private final CodingException a;
                private final X509CRL b;

                {
                    this.b = this;
                    this.a = e;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        }
    }

    public X509CRL(InputStream inputStream) throws IOException, CRLException {
        this();
        try {
            this.a = new ASN1(inputStream);
            d();
        } catch (CodingException e) {
            throw new CRLException(this, e.toString(), e) { // from class: iaik.x509.X509CRL.1
                private static final long serialVersionUID = -534597188972819099L;
                private final CodingException a;
                private final X509CRL b;

                {
                    this.b = this;
                    this.a = e;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        }
    }

    public X509CRL(byte[] bArr) throws CRLException {
        this();
        try {
            this.a = new ASN1(bArr);
            d();
        } catch (CodingException e) {
            throw new CRLException(this, e.toString(), e) { // from class: iaik.x509.X509CRL.2
                private static final long serialVersionUID = -8036880454220918343L;
                private final CodingException a;
                private final X509CRL b;

                {
                    this.b = this;
                    this.a = e;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        }
    }

    private static Name a(RevokedCertificate revokedCertificate) throws CRLException {
        try {
            CertificateIssuer certificateIssuer = (CertificateIssuer) revokedCertificate.getExtension(CertificateIssuer.oid);
            if (certificateIssuer == null) {
                return null;
            }
            Enumeration names = certificateIssuer.getIssuer().getNames();
            while (names.hasMoreElements()) {
                GeneralName generalName = (GeneralName) names.nextElement();
                if (generalName.getType() == 4) {
                    return (Name) generalName.getName();
                }
            }
            throw new CRLException("Only certificate issuer extensions contains a directory name supported");
        } catch (X509ExtensionInitException e) {
            throw new CRLException(e.toString());
        }
    }

    private HashMap a(Principal principal, boolean z) {
        HashMap map = (HashMap) this.h.get(principal);
        if (map != null || !z) {
            return map;
        }
        HashMap map2 = new HashMap();
        this.h.put(principal, map2);
        return map2;
    }

    private void a() {
        this.k = true;
        this.l = null;
        this.a = null;
    }

    private void a(ASN1Object aSN1Object) throws CodingException, CRLException {
        int iCountComponents = aSN1Object.countComponents();
        HashMap map = new HashMap();
        this.h.put(this.d, map);
        for (int i = 0; i < iCountComponents; i++) {
            RevokedCertificate revokedCertificate = new RevokedCertificate(aSN1Object.getComponentAt(i));
            this.j |= revokedCertificate.hasUnsupportedCriticalExtension();
            Name nameA = a(revokedCertificate);
            if (nameA != null) {
                map = a(nameA, true);
            }
            map.put(revokedCertificate.getSerialNumber(), revokedCertificate);
        }
    }

    private void b() {
        if (this.k) {
            throw new RuntimeException("Cannot perform operation, CRL has to be signed first");
        }
    }

    private boolean b(RevokedCertificate revokedCertificate) {
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

    private void c() {
        this.k = false;
    }

    private void d() throws CRLException {
        try {
            int i = 0;
            ASN1Object componentAt = this.a.getComponentAt(0);
            AlgorithmID algorithmID = new AlgorithmID(this.a.getComponentAt(1));
            this.g = (byte[]) ((BIT_STRING) this.a.getComponentAt(2)).getValue();
            ASN1Object componentAt2 = componentAt.getComponentAt(0);
            if (componentAt2.isA(ASN.INTEGER)) {
                this.b = ((BigInteger) componentAt2.getValue()).intValue() + 1;
                i = 1;
            }
            int i2 = i + 1;
            AlgorithmID algorithmID2 = new AlgorithmID(componentAt.getComponentAt(i));
            this.c = algorithmID2;
            if (!algorithmID.equals(algorithmID2)) {
                throw new CRLException("CRL signature algorithm mismatch");
            }
            int i3 = i2 + 1;
            this.d = new Name(componentAt.getComponentAt(i2));
            int i4 = i3 + 1;
            this.e = new ChoiceOfTime(componentAt.getComponentAt(i3));
            while (i4 < componentAt.countComponents()) {
                int i5 = i4 + 1;
                ASN1Object componentAt3 = componentAt.getComponentAt(i4);
                if (componentAt3.isA(ASN.SEQUENCE)) {
                    a(componentAt3);
                } else if (!componentAt3.isA(ASN.CON_SPEC)) {
                    if (!componentAt3.isA(ASN.UTCTime) && !componentAt3.isA(ASN.GeneralizedTime)) {
                        throw new CRLException("Unknown CRL format.");
                    }
                    this.f = new ChoiceOfTime(componentAt3);
                } else {
                    if (componentAt3.getAsnType().getTag() != 0) {
                        throw new CRLException("Unknown CRL format.");
                    }
                    X509Extensions x509Extensions = new X509Extensions(this.m, this.n);
                    this.i = x509Extensions;
                    x509Extensions.decode((ASN1Object) componentAt3.getValue());
                }
                i4 = i5;
            }
            this.a.clearASN1Object();
            c();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("CRL format error: ");
            stringBuffer.append(e.toString());
            throw new CRLException(stringBuffer.toString());
        } catch (X509ExtensionException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("CRL format error: ");
            stringBuffer2.append(e2.toString());
            throw new CRLException(stringBuffer2.toString());
        } catch (RuntimeException e3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("CRL format error: ");
            stringBuffer3.append(e3.toString());
            throw new CRLException(stringBuffer3.toString());
        }
    }

    private ASN1Object e() throws X509ExtensionException, CRLException {
        HashMap mapA;
        SEQUENCE sequence = new SEQUENCE();
        HashMap mapA2 = a(this.d, false);
        if (mapA2 != null) {
            Iterator it = mapA2.values().iterator();
            while (it.hasNext()) {
                sequence.addComponent(((RevokedCertificate) it.next()).toASN1Object());
            }
        }
        for (Name name : this.h.keySet()) {
            if (!name.equals(this.d) && (mapA = a(name, false)) != null) {
                boolean z = true;
                for (RevokedCertificate revokedCertificate : mapA.values()) {
                    if (z) {
                        if (!revokedCertificate.a(CertificateIssuer.oid)) {
                            CertificateIssuer certificateIssuer = new CertificateIssuer(new GeneralNames(new GeneralName(4, name)));
                            certificateIssuer.setCritical(true);
                            revokedCertificate.addExtension(certificateIssuer);
                        }
                        z = false;
                    } else {
                        revokedCertificate.removeExtension(CertificateIssuer.oid);
                    }
                    sequence.addComponent(revokedCertificate.toASN1Object());
                }
            }
        }
        return sequence;
    }

    private ASN1Object f() throws CRLException {
        this.b = 1;
        if (this.c == null) {
            throw new CRLException("Signature algorithm not set!");
        }
        if (this.d == null) {
            throw new CRLException("Issuer not set!");
        }
        if (this.e == null) {
            throw new CRLException("ThisUpdate not set!");
        }
        if (this.i != null) {
            this.b = 2;
        }
        try {
            SEQUENCE sequence = new SEQUENCE();
            if (this.b > 1) {
                sequence.addComponent(new INTEGER(this.b - 1));
            }
            sequence.addComponent(this.c.toASN1Object());
            sequence.addComponent(this.d.toASN1Object());
            sequence.addComponent(this.e.toASN1Object());
            ChoiceOfTime choiceOfTime = this.f;
            if (choiceOfTime != null) {
                sequence.addComponent(choiceOfTime.toASN1Object());
            }
            if (this.h.size() > 0) {
                sequence.addComponent(e());
            }
            X509Extensions x509Extensions = this.i;
            if (x509Extensions != null && x509Extensions.countExtensions() > 0) {
                sequence.addComponent(new CON_SPEC(0, this.i.toASN1Object()));
            }
            return sequence;
        } catch (X509ExtensionException e) {
            throw new CRLException(e.toString());
        }
    }

    public void addCertificate(RevokedCertificate revokedCertificate) {
        try {
            synchronized (this.h) {
                Name nameA = a(revokedCertificate);
                if (nameA == null && (nameA = this.d) == null) {
                    throw new NullPointerException("CRL issuer must be set before adding a revoked certificate");
                }
                a(nameA, true).put(revokedCertificate.getSerialNumber(), revokedCertificate.clone());
            }
            a();
        } catch (CRLException e) {
            throw new IllegalArgumentException(e.toString());
        }
    }

    public void addCertificate(X509Certificate x509Certificate, Date date) {
        synchronized (this.h) {
            a(x509Certificate.getIssuerDN(), true).put(x509Certificate.getSerialNumber(), new RevokedCertificate(x509Certificate, date));
        }
        a();
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        if (this.i == null) {
            this.i = new X509Extensions(this.m, this.n);
        }
        this.i.addExtension(v3Extension);
        a();
    }

    public RevokedCertificate containsCertificate(X509Certificate x509Certificate) {
        return containsCertificate(x509Certificate.getIssuerDN(), x509Certificate.getSerialNumber());
    }

    public RevokedCertificate containsCertificate(BigInteger bigInteger) {
        HashMap mapA = a(this.d, false);
        if (mapA == null) {
            return null;
        }
        return (RevokedCertificate) mapA.get(bigInteger);
    }

    public RevokedCertificate containsCertificate(Principal principal, BigInteger bigInteger) {
        HashMap mapA = a(principal, false);
        if (mapA == null) {
            return null;
        }
        return (RevokedCertificate) mapA.get(bigInteger);
    }

    public int countExtensions() {
        X509Extensions x509Extensions = this.i;
        if (x509Extensions == null) {
            return 0;
        }
        return x509Extensions.countExtensions();
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = new ASN1(aSN1Object);
        try {
            d();
        } catch (CRLException e) {
            throw new CodingException(e.toString());
        }
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        X509Extensions x509Extensions = this.i;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getEncoded() throws CRLException {
        b();
        return toByteArray();
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        X509Extensions x509Extensions = this.i;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getExtension(objectID);
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        X509Extensions x509Extensions = this.i;
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
        if (this.l == null) {
            try {
                this.l = getFingerprint("SHA");
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Algorithm SHA not available: ");
                stringBuffer.append(e.toString());
                throw new RuntimeException(stringBuffer.toString());
            }
        }
        return this.l;
    }

    @Override // java.security.cert.X509CRL
    public Principal getIssuerDN() {
        return this.d;
    }

    public Enumeration getIssuerDNs() {
        Set setKeySet = this.h.keySet();
        Object[] array = setKeySet.toArray();
        if (!setKeySet.contains(this.d)) {
            Object[] objArr = new Object[array.length + 1];
            objArr[0] = this.d;
            System.arraycopy(array, 0, objArr, 1, array.length);
            array = objArr;
        }
        return new ArrayEnumeration(array);
    }

    @Override // java.security.cert.X509CRL
    public Date getNextUpdate() {
        ChoiceOfTime choiceOfTime = this.f;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        X509Extensions x509Extensions = this.i;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getNonCriticalExtensionOIDs();
    }

    public byte[] getRawExtensionValue(String str) {
        X509Extensions x509Extensions = this.i;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getRawExtensionValue(str);
    }

    @Override // java.security.cert.X509CRL
    public X509CRLEntry getRevokedCertificate(BigInteger bigInteger) {
        return containsCertificate(bigInteger);
    }

    @Override // java.security.cert.X509CRL
    public Set getRevokedCertificates() {
        HashSet hashSet = new HashSet();
        Enumeration enumerationListCertificates = listCertificates();
        CertificateIssuer certificateIssuer = null;
        while (enumerationListCertificates.hasMoreElements()) {
            RevokedCertificate revokedCertificate = (RevokedCertificate) enumerationListCertificates.nextElement();
            try {
                CertificateIssuer certificateIssuer2 = (CertificateIssuer) revokedCertificate.getExtension(CertificateIssuer.oid);
                if (certificateIssuer2 != null) {
                    certificateIssuer = certificateIssuer2;
                } else if (certificateIssuer != null) {
                    revokedCertificate = (RevokedCertificate) revokedCertificate.clone();
                    try {
                        revokedCertificate.addExtension(certificateIssuer);
                    } catch (X509ExtensionException e) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Error processing certificate issuer extension: ");
                        stringBuffer.append(e.toString());
                        throw new RuntimeException(stringBuffer.toString());
                    }
                } else {
                    continue;
                }
                hashSet.add(revokedCertificate);
            } catch (X509ExtensionInitException e2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Error processing certificate issuer extension: ");
                stringBuffer2.append(e2.toString());
                throw new RuntimeException(stringBuffer2.toString());
            }
        }
        return hashSet;
    }

    @Override // java.security.cert.X509CRL
    public String getSigAlgName() {
        try {
            AlgorithmID algorithmID = this.c;
            if (algorithmID == null) {
                return null;
            }
            return algorithmID.getImplementationName();
        } catch (Exception unused) {
            AlgorithmID algorithmID2 = this.c;
            if (algorithmID2 == null) {
                return null;
            }
            return algorithmID2.getName();
        }
    }

    @Override // java.security.cert.X509CRL
    public String getSigAlgOID() {
        return this.c.getAlgorithm().getID();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSigAlgParams() {
        try {
            ASN1Object parameter = this.c.getParameter();
            if (parameter == null) {
                return null;
            }
            return new ASN1(parameter).toByteArray();
        } catch (CodingException e) {
            throw new RuntimeException(e.toString());
        }
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSignature() {
        return this.g;
    }

    public AlgorithmID getSignatureAlgorithm() {
        return this.c;
    }

    @Override // java.security.cert.X509CRL
    public byte[] getTBSCertList() throws CRLException {
        try {
            ASN1 asn1 = this.a;
            return (asn1 == null || asn1.toByteArray() == null) ? DerCoder.encode(f()) : this.a.getFirstObject();
        } catch (CodingException e) {
            throw new CRLException(e.toString());
        }
    }

    @Override // java.security.cert.X509CRL
    public Date getThisUpdate() {
        return this.e.getDate();
    }

    @Override // java.security.cert.X509CRL
    public int getVersion() {
        return this.b;
    }

    public boolean hasExtensions() {
        X509Extensions x509Extensions = this.i;
        if (x509Extensions == null) {
            return false;
        }
        return x509Extensions.hasExtensions();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        if (this.j) {
            return true;
        }
        X509Extensions x509Extensions = this.i;
        if (x509Extensions == null) {
            return false;
        }
        return x509Extensions.hasUnsupportedCriticalExtension();
    }

    public boolean isIndirectCRL() {
        for (Name name : this.h.keySet()) {
            if (!name.equals(this.d) && ((HashMap) this.h.get(name)).size() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isRevoked(BigInteger bigInteger) {
        return b(containsCertificate(bigInteger));
    }

    @Override // java.security.cert.CRL
    public boolean isRevoked(Certificate certificate) {
        try {
            return b(containsCertificate(Util.convertCertificate(certificate)));
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid certificate format: ");
            stringBuffer.append(e.toString());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public Enumeration listCertificates() {
        HashMap mapA;
        Vector vector = new Vector(100);
        HashMap mapA2 = a(this.d, false);
        if (mapA2 != null) {
            Iterator it = mapA2.values().iterator();
            while (it.hasNext()) {
                vector.addElement(it.next());
            }
        }
        Vector vector2 = new Vector(20);
        for (Name name : this.h.keySet()) {
            if (!name.equals(this.d) && (mapA = a(name, false)) != null) {
                vector2.removeAllElements();
                boolean z = true;
                for (RevokedCertificate revokedCertificate : mapA.values()) {
                    try {
                    } catch (X509ExtensionInitException unused) {
                        vector2.addElement(revokedCertificate);
                    }
                    if (revokedCertificate.getExtension(CertificateIssuer.oid) == null && z) {
                        vector2.addElement(revokedCertificate);
                    } else {
                        vector.addElement(revokedCertificate);
                        z = false;
                    }
                }
                Enumeration enumerationElements = vector2.elements();
                while (enumerationElements.hasMoreElements()) {
                    vector.addElement(enumerationElements.nextElement());
                }
            }
        }
        return vector.elements();
    }

    public Enumeration listExtensions() {
        X509Extensions x509Extensions = this.i;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.listExtensions();
    }

    public void removeAllCertificates() {
        synchronized (this.h) {
            this.h.clear();
        }
        a();
    }

    public void removeAllExtensions() {
        X509Extensions x509Extensions = this.i;
        if (x509Extensions != null) {
            x509Extensions.removeAllExtensions();
            a();
        }
    }

    public boolean removeCertificate(X509Certificate x509Certificate) {
        return removeCertificate(x509Certificate.getIssuerDN(), x509Certificate.getSerialNumber());
    }

    public boolean removeCertificate(BigInteger bigInteger) {
        return removeCertificate(this.d, bigInteger);
    }

    public boolean removeCertificate(Principal principal, BigInteger bigInteger) {
        boolean z;
        synchronized (this.h) {
            z = false;
            HashMap mapA = a(principal, false);
            if (mapA != null && mapA.remove(bigInteger) != null) {
                if (mapA.isEmpty()) {
                    this.h.remove(principal);
                }
                a();
                z = true;
            }
        }
        return z;
    }

    public boolean removeExtension(ObjectID objectID) {
        X509Extensions x509Extensions = this.i;
        boolean zRemoveExtension = x509Extensions == null ? false : x509Extensions.removeExtension(objectID);
        if (zRemoveExtension) {
            a();
        }
        return zRemoveExtension;
    }

    public void setIssuerDN(Principal principal) throws IllegalArgumentException {
        try {
            this.d = (Name) principal;
            a();
        } catch (Exception unused) {
            throw new IllegalArgumentException("Issuer is not an instance of name.");
        }
    }

    public void setNextUpdate(Date date) {
        this.f = new ChoiceOfTime(date, true, false);
        a();
    }

    public void setSignature(byte[] bArr) throws CRLException {
        if (bArr == null) {
            throw new CRLException("Cannot sign crl. No signature value specified!");
        }
        this.g = bArr;
        ASN1Object aSN1ObjectF = f();
        BIT_STRING bit_string = new BIT_STRING(this.g);
        a();
        try {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectF);
            sequence.addComponent(this.c.toASN1Object());
            sequence.addComponent(bit_string);
            this.a = new ASN1(sequence);
            c();
        } catch (CodingException e) {
            throw new CRLException(e.toString());
        }
    }

    public void setSignatureAlgorithm(ObjectID objectID) {
        setSignatureAlgorithm(new AlgorithmID(objectID));
    }

    public void setSignatureAlgorithm(AlgorithmID algorithmID) {
        Objects.requireNonNull(algorithmID, "Cannot set null signature algorithm for this CRL!");
        this.c = algorithmID;
    }

    public void setThisUpdate(Date date) {
        this.e = new ChoiceOfTime(date, true, false);
        a();
    }

    public void sign(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, CRLException {
        sign(privateKey, null);
    }

    public void sign(PrivateKey privateKey, String str) throws NoSuchAlgorithmException, InvalidKeyException, CRLException {
        AlgorithmParameters signatureParameters;
        AlgorithmID algorithmID = this.c;
        if (algorithmID == null) {
            throw new CRLException("Signature algorithm not set!");
        }
        Signature signatureInstance = algorithmID.getSignatureInstance(str);
        signatureInstance.initSign(privateKey);
        try {
            if (!AlgorithmID.getDoNotIncludeParameters(this.c) && !this.c.hasParameters() && (signatureParameters = Util.getSignatureParameters(signatureInstance)) != null) {
                this.c.setAlgorithmParameters(signatureParameters);
            }
        } catch (Exception unused) {
        }
        ASN1Object aSN1ObjectF = f();
        try {
            signatureInstance.update(DerCoder.encode(aSN1ObjectF));
            this.g = signatureInstance.sign();
            BIT_STRING bit_string = new BIT_STRING(this.g);
            a();
            try {
                SEQUENCE sequence = new SEQUENCE();
                sequence.addComponent(aSN1ObjectF);
                sequence.addComponent(this.c.toASN1Object());
                sequence.addComponent(bit_string);
                this.a = new ASN1(sequence);
                c();
            } catch (CodingException e) {
                throw new CRLException(e.toString());
            }
        } catch (SignatureException e2) {
            throw new CRLException(e2.toString());
        }
    }

    public int size() {
        int size = 0;
        for (HashMap map : this.h.values()) {
            if (map != null) {
                size += map.size();
            }
        }
        return size;
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

    @Override // java.security.cert.CRL
    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        try {
            b();
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("X509 version ");
            stringBuffer2.append(this.b);
            stringBuffer2.append(" CRL\n");
            stringBuffer.append(stringBuffer2.toString());
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Signature Algorithm: ");
            stringBuffer3.append(this.c.getName());
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Issuer: ");
            stringBuffer4.append(this.d.toString());
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("this update: ");
            stringBuffer5.append(this.e.toString());
            stringBuffer5.append("\n");
            stringBuffer.append(stringBuffer5.toString());
            if (this.f != null) {
                StringBuffer stringBuffer6 = new StringBuffer();
                stringBuffer6.append("next update: ");
                stringBuffer6.append(this.f.toString());
                stringBuffer6.append("\n");
                stringBuffer.append(stringBuffer6.toString());
            }
            X509Extensions x509Extensions = this.i;
            if (x509Extensions != null) {
                if (z) {
                    stringBuffer.append(x509Extensions);
                } else {
                    StringBuffer stringBuffer7 = new StringBuffer();
                    stringBuffer7.append("Extensions: ");
                    stringBuffer7.append(this.i.countExtensions());
                    stringBuffer7.append("\n");
                    stringBuffer.append(stringBuffer7.toString());
                }
            }
            if (z) {
                Enumeration enumerationListCertificates = listCertificates();
                int i = 1;
                while (enumerationListCertificates.hasMoreElements()) {
                    RevokedCertificate revokedCertificate = (RevokedCertificate) enumerationListCertificates.nextElement();
                    StringBuffer stringBuffer8 = new StringBuffer();
                    stringBuffer8.append(i);
                    stringBuffer8.append(": ");
                    stringBuffer8.append(revokedCertificate.toString(z));
                    stringBuffer8.append("\n");
                    stringBuffer.append(stringBuffer8.toString());
                    i++;
                }
            } else {
                for (Name name : this.h.keySet()) {
                    HashMap map = (HashMap) this.h.get(name);
                    StringBuffer stringBuffer9 = new StringBuffer();
                    stringBuffer9.append(name.toString());
                    stringBuffer9.append(": ");
                    stringBuffer9.append(map.size());
                    stringBuffer9.append(" revoked certificates\n");
                    stringBuffer.append(stringBuffer9.toString());
                }
            }
            return stringBuffer.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // java.security.cert.X509CRL
    public void verify(PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CRLException, NoSuchProviderException {
        verify(publicKey, (String) null);
    }

    @Override // java.security.cert.X509CRL
    public void verify(PublicKey publicKey, String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CRLException, NoSuchProviderException {
        b();
        AlgorithmID algorithmID = this.c;
        if (algorithmID == null) {
            throw new NoSuchAlgorithmException("Cannot verify crl! No signature algorithm set.");
        }
        Signature signatureInstance = algorithmID.getSignatureInstance(str);
        try {
            byte[] firstObject = this.a.getFirstObject();
            signatureInstance.initVerify(publicKey);
            signatureInstance.update(firstObject);
            if (!signatureInstance.verify(this.g)) {
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

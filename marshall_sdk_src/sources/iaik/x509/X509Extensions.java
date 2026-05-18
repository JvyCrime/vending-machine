package iaik.x509;

import iaik.asn1.ASN1Object;
import iaik.asn1.BOOLEAN;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.EncodedASN1Object;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.InternalErrorException;
import iaik.utils.ObjectFactory;
import iaik.utils.Util;
import iaik.x509.extensions.ErrorExtension;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class X509Extensions {
    static Class A;
    static Class B;
    static Class C;
    static Class D;
    static Class E;
    static Class F;
    static Class G;
    static Class H;
    static Class I;
    static Class J;
    static Class K;
    static Class L;
    static Class M;
    static Class N;
    static Class O;
    private static final Class P;
    private static final ObjectFactory Q;
    static Class a;
    static Class b;
    static Class c;
    static Class d;
    static Class e;
    static Class f;
    static Class g;
    static Class h;
    static Class i;
    static Class j;
    static Class k;
    static Class l;
    static Class m;
    static Class n;
    static Class o;
    static Class p;
    static Class q;
    static Class r;
    static Class s;
    static Class t;
    static Class u;
    static Class v;
    static Class w;
    static Class x;
    static Class y;
    static Class z;
    private int R;
    private int S;
    private final Object T;
    protected volatile Hashtable critical_extensions;
    protected volatile Hashtable noncritical_extensions;

    static {
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.V3Extension");
            a = clsClass$;
        }
        P = clsClass$;
        Q = new ObjectFactory();
        ObjectID objectID = ObjectID.certExt_AuthorityKeyIdentifier;
        Class clsClass$2 = b;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.x509.extensions.AuthorityKeyIdentifier");
            b = clsClass$2;
        }
        register(objectID, clsClass$2);
        ObjectID objectID2 = ObjectID.certExt_BasicConstraints;
        Class clsClass$3 = c;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.x509.extensions.BasicConstraints");
            c = clsClass$3;
        }
        register(objectID2, clsClass$3);
        ObjectID objectID3 = ObjectID.certExt_CertificatePolicies;
        Class clsClass$4 = d;
        if (clsClass$4 == null) {
            clsClass$4 = class$("iaik.x509.extensions.CertificatePolicies");
            d = clsClass$4;
        }
        register(objectID3, clsClass$4);
        ObjectID objectID4 = ObjectID.certExt_CrlDistributionPoints;
        Class clsClass$5 = e;
        if (clsClass$5 == null) {
            clsClass$5 = class$("iaik.x509.extensions.CRLDistributionPoints");
            e = clsClass$5;
        }
        register(objectID4, clsClass$5);
        ObjectID objectID5 = ObjectID.certExt_ExtendedKeyUsage;
        Class clsClass$6 = f;
        if (clsClass$6 == null) {
            clsClass$6 = class$("iaik.x509.extensions.ExtendedKeyUsage");
            f = clsClass$6;
        }
        register(objectID5, clsClass$6);
        ObjectID objectID6 = ObjectID.certExt_IssuerAltName;
        Class clsClass$7 = g;
        if (clsClass$7 == null) {
            clsClass$7 = class$("iaik.x509.extensions.IssuerAltName");
            g = clsClass$7;
        }
        register(objectID6, clsClass$7);
        ObjectID objectID7 = ObjectID.certExt_KeyUsage;
        Class clsClass$8 = h;
        if (clsClass$8 == null) {
            clsClass$8 = class$("iaik.x509.extensions.KeyUsage");
            h = clsClass$8;
        }
        register(objectID7, clsClass$8);
        ObjectID objectID8 = ObjectID.certExt_NameConstraints;
        Class clsClass$9 = i;
        if (clsClass$9 == null) {
            clsClass$9 = class$("iaik.x509.extensions.NameConstraints");
            i = clsClass$9;
        }
        register(objectID8, clsClass$9);
        ObjectID objectID9 = ObjectID.certExt_PolicyConstraints;
        Class clsClass$10 = j;
        if (clsClass$10 == null) {
            clsClass$10 = class$("iaik.x509.extensions.PolicyConstraints");
            j = clsClass$10;
        }
        register(objectID9, clsClass$10);
        ObjectID objectID10 = ObjectID.certExt_PolicyMappings;
        Class clsClass$11 = k;
        if (clsClass$11 == null) {
            clsClass$11 = class$("iaik.x509.extensions.PolicyMappings");
            k = clsClass$11;
        }
        register(objectID10, clsClass$11);
        ObjectID objectID11 = ObjectID.certExt_PrivateKeyUsagePeriod;
        Class clsClass$12 = l;
        if (clsClass$12 == null) {
            clsClass$12 = class$("iaik.x509.extensions.PrivateKeyUsagePeriod");
            l = clsClass$12;
        }
        register(objectID11, clsClass$12);
        ObjectID objectID12 = ObjectID.certExt_SubjectAltName;
        Class clsClass$13 = m;
        if (clsClass$13 == null) {
            clsClass$13 = class$("iaik.x509.extensions.SubjectAltName");
            m = clsClass$13;
        }
        register(objectID12, clsClass$13);
        ObjectID objectID13 = ObjectID.certExt_SubjectKeyIdentifier;
        Class clsClass$14 = n;
        if (clsClass$14 == null) {
            clsClass$14 = class$("iaik.x509.extensions.SubjectKeyIdentifier");
            n = clsClass$14;
        }
        register(objectID13, clsClass$14);
        ObjectID objectID14 = ObjectID.certExt_SubjectDirectoryAttributes;
        Class clsClass$15 = o;
        if (clsClass$15 == null) {
            clsClass$15 = class$("iaik.x509.extensions.SubjectDirectoryAttributes");
            o = clsClass$15;
        }
        register(objectID14, clsClass$15);
        ObjectID objectID15 = ObjectID.certExt_AuthorityInfoAccess;
        Class clsClass$16 = p;
        if (clsClass$16 == null) {
            clsClass$16 = class$("iaik.x509.extensions.AuthorityInfoAccess");
            p = clsClass$16;
        }
        register(objectID15, clsClass$16);
        ObjectID objectID16 = ObjectID.certExt_SubjectInfoAccess;
        Class clsClass$17 = q;
        if (clsClass$17 == null) {
            clsClass$17 = class$("iaik.x509.extensions.SubjectInfoAccess");
            q = clsClass$17;
        }
        register(objectID16, clsClass$17);
        ObjectID objectID17 = ObjectID.certExt_InhibitAnyPolicy;
        Class clsClass$18 = r;
        if (clsClass$18 == null) {
            clsClass$18 = class$("iaik.x509.extensions.InhibitAnyPolicy");
            r = clsClass$18;
        }
        register(objectID17, clsClass$18);
        ObjectID objectID18 = ObjectID.certExt_FreshestCRL;
        Class clsClass$19 = s;
        if (clsClass$19 == null) {
            clsClass$19 = class$("iaik.x509.extensions.FreshestCRL");
            s = clsClass$19;
        }
        register(objectID18, clsClass$19);
        ObjectID objectID19 = ObjectID.smimeCapabilities;
        Class clsClass$20 = t;
        if (clsClass$20 == null) {
            clsClass$20 = class$("iaik.x509.extensions.smime.SMIMECapabilities");
            t = clsClass$20;
        }
        register(objectID19, clsClass$20);
        ObjectID objectID20 = ObjectID.crlExt_CrlNumber;
        Class clsClass$21 = u;
        if (clsClass$21 == null) {
            clsClass$21 = class$("iaik.x509.extensions.CRLNumber");
            u = clsClass$21;
        }
        register(objectID20, clsClass$21);
        ObjectID objectID21 = ObjectID.crlExt_DeltaCRLIndicator;
        Class clsClass$22 = v;
        if (clsClass$22 == null) {
            clsClass$22 = class$("iaik.x509.extensions.DeltaCRLIndicator");
            v = clsClass$22;
        }
        register(objectID21, clsClass$22);
        ObjectID objectID22 = ObjectID.crlExt_IssuingDistributionPoint;
        Class clsClass$23 = w;
        if (clsClass$23 == null) {
            clsClass$23 = class$("iaik.x509.extensions.IssuingDistributionPoint");
            w = clsClass$23;
        }
        register(objectID22, clsClass$23);
        ObjectID objectID23 = ObjectID.crlExt_ReasonCode;
        Class clsClass$24 = x;
        if (clsClass$24 == null) {
            clsClass$24 = class$("iaik.x509.extensions.ReasonCode");
            x = clsClass$24;
        }
        register(objectID23, clsClass$24);
        ObjectID objectID24 = ObjectID.crlExt_HoldInstructionCode;
        Class clsClass$25 = y;
        if (clsClass$25 == null) {
            clsClass$25 = class$("iaik.x509.extensions.HoldInstructionCode");
            y = clsClass$25;
        }
        register(objectID24, clsClass$25);
        ObjectID objectID25 = ObjectID.crlExt_InvalidityDate;
        Class clsClass$26 = z;
        if (clsClass$26 == null) {
            clsClass$26 = class$("iaik.x509.extensions.InvalidityDate");
            z = clsClass$26;
        }
        register(objectID25, clsClass$26);
        ObjectID objectID26 = ObjectID.crlExt_CertificateIssuer;
        Class clsClass$27 = A;
        if (clsClass$27 == null) {
            clsClass$27 = class$("iaik.x509.extensions.CertificateIssuer");
            A = clsClass$27;
        }
        register(objectID26, clsClass$27);
        ObjectID objectID27 = ObjectID.crlExt_ExpiredCertsOnCRL;
        Class clsClass$28 = B;
        if (clsClass$28 == null) {
            clsClass$28 = class$("iaik.x509.extensions.ExpiredCertsOnCRL");
            B = clsClass$28;
        }
        register(objectID27, clsClass$28);
        ObjectID objectID28 = ObjectID.certExt_NetscapeBaseUrl;
        Class clsClass$29 = C;
        if (clsClass$29 == null) {
            clsClass$29 = class$("iaik.x509.extensions.netscape.NetscapeBaseUrl");
            C = clsClass$29;
        }
        register(objectID28, clsClass$29);
        ObjectID objectID29 = ObjectID.certExt_NetscapeCaPolicyUrl;
        Class clsClass$30 = D;
        if (clsClass$30 == null) {
            clsClass$30 = class$("iaik.x509.extensions.netscape.NetscapeCaPolicyUrl");
            D = clsClass$30;
        }
        register(objectID29, clsClass$30);
        ObjectID objectID30 = ObjectID.certExt_NetscapeCaRevocationUrl;
        Class clsClass$31 = E;
        if (clsClass$31 == null) {
            clsClass$31 = class$("iaik.x509.extensions.netscape.NetscapeCaRevocationUrl");
            E = clsClass$31;
        }
        register(objectID30, clsClass$31);
        ObjectID objectID31 = ObjectID.certExt_NetscapeCertRenewalUrl;
        Class clsClass$32 = F;
        if (clsClass$32 == null) {
            clsClass$32 = class$("iaik.x509.extensions.netscape.NetscapeCertRenewalUrl");
            F = clsClass$32;
        }
        register(objectID31, clsClass$32);
        ObjectID objectID32 = ObjectID.certExt_NetscapeCertType;
        Class clsClass$33 = G;
        if (clsClass$33 == null) {
            clsClass$33 = class$("iaik.x509.extensions.netscape.NetscapeCertType");
            G = clsClass$33;
        }
        register(objectID32, clsClass$33);
        ObjectID objectID33 = ObjectID.certExt_NetscapeComment;
        Class clsClass$34 = H;
        if (clsClass$34 == null) {
            clsClass$34 = class$("iaik.x509.extensions.netscape.NetscapeComment");
            H = clsClass$34;
        }
        register(objectID33, clsClass$34);
        ObjectID objectID34 = ObjectID.certExt_NetscapeRevocationUrl;
        Class clsClass$35 = I;
        if (clsClass$35 == null) {
            clsClass$35 = class$("iaik.x509.extensions.netscape.NetscapeRevocationUrl");
            I = clsClass$35;
        }
        register(objectID34, clsClass$35);
        ObjectID objectID35 = ObjectID.certExt_NetscapeSSLServerName;
        Class clsClass$36 = J;
        if (clsClass$36 == null) {
            clsClass$36 = class$("iaik.x509.extensions.netscape.NetscapeSSLServerName");
            J = clsClass$36;
        }
        register(objectID35, clsClass$36);
        ObjectID objectID36 = ObjectID.certExt_BiometricInfo;
        Class clsClass$37 = K;
        if (clsClass$37 == null) {
            clsClass$37 = class$("iaik.x509.extensions.qualified.BiometricInfo");
            K = clsClass$37;
        }
        register(objectID36, clsClass$37);
        ObjectID objectID37 = ObjectID.certExt_QcStatements;
        Class clsClass$38 = L;
        if (clsClass$38 == null) {
            clsClass$38 = class$("iaik.x509.extensions.qualified.QCStatements");
            L = clsClass$38;
        }
        register(objectID37, clsClass$38);
        ObjectID objectID38 = ObjectID.certExt_NoCheck;
        Class clsClass$39 = M;
        if (clsClass$39 == null) {
            clsClass$39 = class$("iaik.x509.extensions.ocsp.NoCheck");
            M = clsClass$39;
        }
        register(objectID38, clsClass$39);
        ObjectID objectID39 = ObjectID.certExt_PublicAuthorityIdentifier;
        Class clsClass$40 = N;
        if (clsClass$40 == null) {
            clsClass$40 = class$("iaik.x509.extensions.priv.PublicAuthorityIdentifier");
            N = clsClass$40;
        }
        register(objectID39, clsClass$40);
        ObjectID objectID40 = ObjectID.certExt_PublicServiceProvider;
        Class clsClass$41 = O;
        if (clsClass$41 == null) {
            clsClass$41 = class$("iaik.x509.extensions.priv.PublicServiceProvider");
            O = clsClass$41;
        }
        register(objectID40, clsClass$41);
        Util.toString((byte[]) null, -1, 1);
    }

    public X509Extensions() {
        this.R = 4;
        this.S = 11;
        this.T = new Object();
    }

    public X509Extensions(int i2, int i3) {
        this();
        this.R = i2;
        this.S = i3;
    }

    public X509Extensions(ASN1Object aSN1Object) throws X509ExtensionException {
        this();
        parseExtensions(aSN1Object);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static V3Extension create(ObjectID objectID) throws InstantiationException {
        return (V3Extension) Q.create(objectID);
    }

    public static void register(ObjectID objectID, Class cls) {
        if (P.isAssignableFrom(cls)) {
            Q.register(objectID, cls);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Class ");
        stringBuffer.append(cls);
        stringBuffer.append(" is no V3Extension implementation!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    boolean a(ObjectID objectID) {
        boolean z2 = (this.critical_extensions == null || this.critical_extensions.get(objectID) == null) ? false : true;
        if (z2 || this.noncritical_extensions == null) {
            return z2;
        }
        return this.noncritical_extensions.get(objectID) != null;
    }

    public boolean addExtension(V3Extension v3Extension) throws X509ExtensionException {
        ASN1Object aSN1Object = v3Extension.toASN1Object();
        byte[] bArrEncode = aSN1Object == null ? new byte[0] : DerCoder.encode(aSN1Object);
        ObjectID objectID = v3Extension.getObjectID();
        if (v3Extension.isCritical()) {
            if (this.noncritical_extensions == null || this.noncritical_extensions.get(objectID) == null) {
                createExtensionsTable(true);
                return this.critical_extensions.put(objectID, bArrEncode) != null;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(objectID.getName());
            stringBuffer.append(" has been already added as non critical extension!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (this.critical_extensions == null || this.critical_extensions.get(objectID) == null) {
            createExtensionsTable(false);
            return this.noncritical_extensions.put(objectID, bArrEncode) != null;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(objectID.getName());
        stringBuffer2.append(" has been already added as critical extension!");
        throw new IllegalArgumentException(stringBuffer2.toString());
    }

    public int countExtensions() {
        return (this.critical_extensions == null ? 0 : this.critical_extensions.size()) + (this.noncritical_extensions != null ? this.noncritical_extensions.size() : 0);
    }

    protected void createExtensionsTable(boolean z2) {
        if (z2) {
            if (this.critical_extensions == null) {
                synchronized (this.T) {
                    if (this.critical_extensions == null) {
                        this.critical_extensions = new Hashtable(this.R, 0.75f);
                    }
                }
                return;
            }
            return;
        }
        if (this.noncritical_extensions == null) {
            synchronized (this.T) {
                if (this.noncritical_extensions == null) {
                    this.noncritical_extensions = new Hashtable(this.S, 0.75f);
                }
            }
        }
    }

    public void decode(ASN1Object aSN1Object) throws X509ExtensionException {
        parseExtensions(aSN1Object);
    }

    public Set getCriticalExtensionOIDs() {
        HashSet hashSet = new HashSet();
        if (this.critical_extensions != null) {
            Enumeration enumerationKeys = this.critical_extensions.keys();
            while (enumerationKeys.hasMoreElements()) {
                hashSet.add(((ObjectID) enumerationKeys.nextElement()).getID());
            }
        }
        return hashSet;
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        boolean z2;
        V3Extension unknownExtension;
        byte[] bArr = this.critical_extensions != null ? (byte[]) this.critical_extensions.get(objectID) : null;
        if (bArr == null) {
            if (this.noncritical_extensions != null) {
                bArr = (byte[]) this.noncritical_extensions.get(objectID);
            }
            if (bArr == null) {
                return null;
            }
            z2 = false;
        } else {
            z2 = true;
        }
        try {
            unknownExtension = create(objectID);
        } catch (InstantiationException unused) {
            unknownExtension = new UnknownExtension(objectID);
        }
        unknownExtension.setCritical(z2);
        if (bArr.length > 0) {
            try {
                unknownExtension.init(DerCoder.decode(bArr));
            } catch (CodingException e2) {
                if (!(unknownExtension instanceof UnknownExtension)) {
                    throw new X509ExtensionInitException(this, objectID, z2, e2.toString(), e2) { // from class: iaik.x509.X509Extensions.2
                        private static final long serialVersionUID = 8448508552538211036L;
                        private final CodingException c;
                        private final X509Extensions d;

                        {
                            this.d = this;
                            this.c = e2;
                        }

                        @Override // java.lang.Throwable
                        public Throwable getCause() {
                            return this.c;
                        }
                    };
                }
                try {
                    unknownExtension.init(new EncodedASN1Object(bArr));
                } catch (X509ExtensionException e3) {
                    throw new X509ExtensionInitException(objectID, z2, e3.toString());
                }
            } catch (X509ExtensionException e4) {
                throw new X509ExtensionInitException(this, objectID, z2, e4.toString(), e4) { // from class: iaik.x509.X509Extensions.1
                    private static final long serialVersionUID = 5967316399896097627L;
                    private final X509ExtensionException c;
                    private final X509Extensions d;

                    {
                        this.d = this;
                        this.c = e4;
                    }

                    @Override // java.lang.Throwable
                    public Throwable getCause() {
                        return this.c;
                    }
                };
            }
        }
        return unknownExtension;
    }

    public byte[] getExtensionValue(String str) {
        byte[] rawExtensionValue = getRawExtensionValue(str);
        return rawExtensionValue != null ? DerCoder.encode(new OCTET_STRING(rawExtensionValue)) : rawExtensionValue;
    }

    public Set getNonCriticalExtensionOIDs() {
        HashSet hashSet = new HashSet();
        if (this.noncritical_extensions != null) {
            Enumeration enumerationKeys = this.noncritical_extensions.keys();
            while (enumerationKeys.hasMoreElements()) {
                hashSet.add(((ObjectID) enumerationKeys.nextElement()).getID());
            }
        }
        return hashSet;
    }

    public byte[] getRawExtensionValue(String str) {
        ObjectID objectID = ObjectID.getObjectID(str);
        Object obj = this.critical_extensions != null ? this.critical_extensions.get(objectID) : null;
        if (obj != null) {
            return (byte[]) obj;
        }
        if (this.noncritical_extensions != null) {
            return (byte[]) this.noncritical_extensions.get(objectID);
        }
        return null;
    }

    public boolean hasExtensions() {
        boolean z2 = this.critical_extensions != null ? !this.critical_extensions.isEmpty() : false;
        return (z2 || this.noncritical_extensions == null) ? z2 : !this.noncritical_extensions.isEmpty();
    }

    public boolean hasUnsupportedCriticalExtension() {
        Enumeration enumerationKeys;
        if (this.critical_extensions == null) {
            return false;
        }
        try {
            enumerationKeys = this.critical_extensions.keys();
        } catch (X509ExtensionInitException unused) {
        }
        while (enumerationKeys.hasMoreElements()) {
            if (getExtension((ObjectID) enumerationKeys.nextElement()) instanceof UnknownExtension) {
                return true;
            }
        }
        return false;
    }

    public Enumeration listExtensions() {
        Vector vector = new Vector();
        if (this.critical_extensions != null) {
            Enumeration enumerationKeys = this.critical_extensions.keys();
            while (enumerationKeys.hasMoreElements()) {
                ObjectID objectID = (ObjectID) enumerationKeys.nextElement();
                try {
                    vector.addElement(getExtension(objectID));
                } catch (X509ExtensionInitException e2) {
                    vector.addElement(new ErrorExtension(objectID, true, e2.getMessage()));
                }
            }
        }
        if (this.noncritical_extensions != null) {
            Enumeration enumerationKeys2 = this.noncritical_extensions.keys();
            while (enumerationKeys2.hasMoreElements()) {
                ObjectID objectID2 = (ObjectID) enumerationKeys2.nextElement();
                try {
                    vector.addElement(getExtension(objectID2));
                } catch (X509ExtensionInitException e3) {
                    vector.addElement(new ErrorExtension(objectID2, false, e3.getMessage()));
                }
            }
        }
        return vector.elements();
    }

    protected void parseExtensions(ASN1Object aSN1Object) throws X509ExtensionException {
        Hashtable hashtable;
        for (int i2 = 0; i2 < aSN1Object.countComponents(); i2++) {
            try {
                ASN1Object componentAt = aSN1Object.getComponentAt(i2);
                ObjectID objectID = (ObjectID) componentAt.getComponentAt(0);
                int iCountComponents = componentAt.countComponents();
                boolean zBooleanValue = iCountComponents == 3 ? ((Boolean) componentAt.getComponentAt(1).getValue()).booleanValue() : false;
                byte[] bArr = (byte[]) componentAt.getComponentAt(iCountComponents - 1).getValue();
                if (zBooleanValue) {
                    createExtensionsTable(true);
                    hashtable = this.critical_extensions;
                } else {
                    createExtensionsTable(false);
                    hashtable = this.noncritical_extensions;
                }
                hashtable.put(objectID, bArr);
            } catch (CodingException e2) {
                throw new X509ExtensionException(e2.getMessage());
            }
        }
    }

    public void removeAllExtensions() {
        if (this.critical_extensions != null) {
            this.critical_extensions.clear();
            this.critical_extensions = null;
        }
        if (this.noncritical_extensions != null) {
            this.noncritical_extensions.clear();
            this.noncritical_extensions = null;
        }
    }

    public boolean removeExtension(ObjectID objectID) {
        if ((this.critical_extensions == null || this.critical_extensions.remove(objectID) == null) && this.noncritical_extensions != null && this.noncritical_extensions.remove(objectID) != null) {
        }
        return true;
    }

    public ASN1Object toASN1Object() throws X509ExtensionException {
        SEQUENCE sequence = new SEQUENCE();
        if (this.critical_extensions != null) {
            Enumeration enumerationKeys = this.critical_extensions.keys();
            while (enumerationKeys.hasMoreElements()) {
                SEQUENCE sequence2 = new SEQUENCE();
                ObjectID objectID = (ObjectID) enumerationKeys.nextElement();
                sequence2.addComponent(objectID);
                sequence2.addComponent(new BOOLEAN(true));
                byte[] bArr = (byte[]) this.critical_extensions.get(objectID);
                if (bArr == null) {
                    throw new InternalErrorException("Extension value = null!");
                }
                sequence2.addComponent(new OCTET_STRING(bArr));
                sequence.addComponent(sequence2);
            }
        }
        if (this.noncritical_extensions != null) {
            Enumeration enumerationKeys2 = this.noncritical_extensions.keys();
            while (enumerationKeys2.hasMoreElements()) {
                SEQUENCE sequence3 = new SEQUENCE();
                ObjectID objectID2 = (ObjectID) enumerationKeys2.nextElement();
                sequence3.addComponent(objectID2);
                byte[] bArr2 = (byte[]) this.noncritical_extensions.get(objectID2);
                if (bArr2 == null) {
                    throw new InternalErrorException("Extension value = null!");
                }
                sequence3.addComponent(new OCTET_STRING(bArr2));
                sequence.addComponent(sequence3);
            }
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration enumerationListExtensions = listExtensions();
        int i2 = 1;
        while (enumerationListExtensions.hasMoreElements()) {
            V3Extension v3Extension = (V3Extension) enumerationListExtensions.nextElement();
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Extension ");
            stringBuffer2.append(i2);
            stringBuffer2.append(":     ");
            stringBuffer.append(stringBuffer2.toString());
            stringBuffer.append(v3Extension.isCritical() ? "critical        " : "not critical    ");
            String name = v3Extension.getName();
            if (name == null || name.trim().length() == 0) {
                name = "Unknown Extension";
            }
            stringBuffer.append(name);
            stringBuffer.append("\n");
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append(v3Extension);
            stringBuffer3.append("\n\n");
            stringBuffer.append(stringBuffer3.toString());
            i2++;
        }
        return stringBuffer.toString();
    }
}

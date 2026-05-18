package iaik.x509.attr;

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
import iaik.asn1.structures.Attribute;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.attr.extensions.ProxyInfo;
import iaik.x509.attr.extensions.TargetInformation;
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
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class AttributeCertificate extends Certificate implements ASN1Type, X509Extension {
    static Class a;
    static Class b;
    static Class c;
    static Class d;
    static Class e;
    static Class f;
    private transient ASN1 g;
    private int h;
    private Holder i;
    private AttCertIssuer j;
    private AlgorithmID k;
    private BigInteger l;
    private ChoiceOfTime m;
    private ChoiceOfTime n;
    private Vector o;
    private BigInteger p;
    private AttributeCertificateExtensions q;
    private byte[] r;
    private boolean s;
    private byte[] t;

    static {
        registerStandardAttributeImplementations();
        Util.toString((byte[]) null, -1, 1);
    }

    public AttributeCertificate() {
        super("X.509 AC");
        this.h = 2;
        a();
        this.o = new Vector();
        this.g = new ASN1();
    }

    public AttributeCertificate(InputStream inputStream) throws IOException, CertificateException {
        this();
        Objects.requireNonNull(inputStream, "Cannot parse certificate from a null input stream!");
        decode(inputStream);
    }

    public AttributeCertificate(byte[] bArr) throws CertificateException {
        this();
        Objects.requireNonNull(bArr, "Cannot parse certificate from a null byte array!");
        try {
            this.g = new ASN1(bArr);
            d();
        } catch (CodingException e2) {
            throw new CertificateException(e2.getMessage());
        } catch (X509ExtensionException e3) {
            throw new CertificateException(e3.getMessage());
        }
    }

    private void a() {
        this.s = true;
        this.t = null;
        this.g = null;
    }

    private void b() {
        if (this.s) {
            throw new RuntimeException("Cannot perform operation, certificate has to be signed first");
        }
    }

    private void c() {
        this.s = false;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    private void d() throws X509ExtensionException, CertificateException {
        int i;
        AttCertIssuer v1Form;
        try {
            ASN1Object componentAt = this.g.getComponentAt(0);
            AlgorithmID algorithmID = new AlgorithmID(this.g.getComponentAt(1));
            this.r = (byte[]) ((BIT_STRING) this.g.getComponentAt(2)).getValue();
            ASN1Object componentAt2 = componentAt.getComponentAt(0);
            if (componentAt2.isA(ASN.INTEGER)) {
                this.h = ((BigInteger) componentAt2.getValue()).intValue() + 1;
                i = 1;
            } else {
                this.h = 1;
                i = 0;
            }
            this.i = new Holder(componentAt.getComponentAt(i + 0));
            ASN1Object componentAt3 = componentAt.getComponentAt(i + 1);
            if (componentAt3.isA(ASN.CON_SPEC)) {
                CON_SPEC con_spec = (CON_SPEC) componentAt3;
                con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                v1Form = new V2Form((ASN1Object) con_spec.getValue());
            } else {
                v1Form = new V1Form(componentAt3);
            }
            this.j = v1Form;
            AlgorithmID algorithmID2 = new AlgorithmID(componentAt.getComponentAt(i + 2));
            this.k = algorithmID2;
            if (!algorithmID.equals(algorithmID2)) {
                throw new CertificateException("Certificate signature algorithm mismatch");
            }
            this.l = (BigInteger) componentAt.getComponentAt(i + 3).getValue();
            ASN1 asn1 = new ASN1(componentAt.getComponentAt(i + 4));
            this.m = new ChoiceOfTime(asn1.getComponentAt(0));
            this.n = new ChoiceOfTime(asn1.getComponentAt(1));
            ASN1Object componentAt4 = componentAt.getComponentAt(i + 5);
            for (int i2 = 0; i2 < componentAt4.countComponents(); i2++) {
                this.o.addElement(new Attribute(componentAt4.getComponentAt(i2)));
            }
            int i3 = i + 6;
            while (i3 < componentAt.countComponents()) {
                int i4 = i3 + 1;
                ASN1Object componentAt5 = componentAt.getComponentAt(i3);
                if (componentAt5.isA(ASN.BIT_STRING)) {
                    this.p = new BigInteger(1, (byte[]) componentAt5.getValue());
                } else {
                    this.q = new AttributeCertificateExtensions(componentAt5);
                }
                i3 = i4;
            }
            this.g.clearASN1Object();
            c();
        } catch (CodingException e2) {
            throw new CertificateException(e2.getMessage());
        }
    }

    private ASN1Object e() throws CertificateEncodingException {
        if (this.i == null) {
            throw new CertificateEncodingException("Holder not set!");
        }
        if (this.j == null) {
            throw new CertificateEncodingException("Issuer not set!");
        }
        if (this.k == null) {
            throw new CertificateEncodingException("Signature algorithm not set!");
        }
        if (this.l == null) {
            throw new CertificateEncodingException("Serial number not set!");
        }
        if (this.m == null) {
            throw new CertificateEncodingException("Valid not before time not set!");
        }
        if (this.n == null) {
            throw new CertificateEncodingException("Valid not after time not set!");
        }
        if (this.o.isEmpty()) {
            throw new CertificateEncodingException("No Attributes set!");
        }
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions != null && !attributeCertificateExtensions.hasExtensions()) {
            this.q = null;
        }
        try {
            SEQUENCE sequence = new SEQUENCE();
            if (this.h > 1) {
                sequence.addComponent(new INTEGER(this.h - 1));
            }
            sequence.addComponent(this.i.toASN1Object());
            sequence.addComponent(this.j.getVForm() == 1 ? this.j.toASN1Object() : new CON_SPEC(0, this.j.toASN1Object(), true));
            sequence.addComponent(this.k.toASN1Object());
            sequence.addComponent(new INTEGER(this.l));
            SEQUENCE sequence2 = new SEQUENCE();
            sequence2.addComponent(this.m.toASN1Object());
            sequence2.addComponent(this.n.toASN1Object());
            sequence.addComponent(sequence2);
            SEQUENCE sequence3 = new SEQUENCE();
            Enumeration enumerationElements = this.o.elements();
            while (enumerationElements.hasMoreElements()) {
                sequence3.addComponent(((Attribute) enumerationElements.nextElement()).toASN1Object(true));
            }
            sequence.addComponent(sequence3);
            if (this.p != null) {
                sequence.addComponent(new BIT_STRING(this.p.toByteArray()));
            }
            AttributeCertificateExtensions attributeCertificateExtensions2 = this.q;
            if (attributeCertificateExtensions2 != null) {
                sequence.addComponent(attributeCertificateExtensions2.toASN1Object());
            }
            return sequence;
        } catch (Exception e2) {
            throw new CertificateEncodingException(e2.toString());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            this.g = new ASN1(objectInputStream);
            d();
        } catch (Exception e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to restore Certificate: ");
            stringBuffer.append(e2.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    public static void registerStandardAttributeImplementations() {
        ObjectID objectID = ObjectID.accessIdentity;
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.attr.attributes.AccessIdentity");
            a = clsClass$;
        }
        Attribute.register(objectID, clsClass$);
        ObjectID objectID2 = ObjectID.chargingIdentity;
        Class clsClass$2 = b;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.x509.attr.attributes.ChargingIdentity");
            b = clsClass$2;
        }
        Attribute.register(objectID2, clsClass$2);
        ObjectID objectID3 = ObjectID.clearance;
        Class clsClass$3 = c;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.x509.attr.attributes.Clearance");
            c = clsClass$3;
        }
        Attribute.register(objectID3, clsClass$3);
        ObjectID objectID4 = ObjectID.group;
        Class clsClass$4 = d;
        if (clsClass$4 == null) {
            clsClass$4 = class$("iaik.x509.attr.attributes.Group");
            d = clsClass$4;
        }
        Attribute.register(objectID4, clsClass$4);
        ObjectID objectID5 = ObjectID.role;
        Class clsClass$5 = e;
        if (clsClass$5 == null) {
            clsClass$5 = class$("iaik.x509.attr.attributes.Role");
            e = clsClass$5;
        }
        Attribute.register(objectID5, clsClass$5);
        ObjectID objectID6 = ObjectID.authenticationInfo;
        Class clsClass$6 = f;
        if (clsClass$6 == null) {
            clsClass$6 = class$("iaik.x509.attr.attributes.ServiceAuthenticationInfo");
            f = clsClass$6;
        }
        Attribute.register(objectID6, clsClass$6);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.write(toByteArray());
    }

    public void addAttribute(Attribute attribute) {
        if (attribute != null) {
            a();
            int size = this.o.size();
            for (int i = 0; i < size; i++) {
                if (attribute.getType().equals(((Attribute) this.o.elementAt(i)).getType())) {
                    this.o.setElementAt(attribute, i);
                    return;
                }
            }
            this.o.addElement(attribute);
        }
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        if (this.q == null) {
            this.q = new AttributeCertificateExtensions();
        }
        if (v3Extension instanceof ProxyInfo) {
            ((ProxyInfo) v3Extension).specifyHolder(this.i);
        }
        this.q.addExtension(v3Extension);
        a();
    }

    public void checkValidity() throws CertificateNotYetValidException, CertificateExpiredException {
        checkValidity(new Date());
    }

    public void checkValidity(Date date) throws CertificateNotYetValidException, CertificateExpiredException {
        ChoiceOfTime choiceOfTime = this.m;
        if (choiceOfTime == null) {
            throw new CertificateNotYetValidException("ValidNotBefore date not set!");
        }
        if (date.before(choiceOfTime.getDate())) {
            throw new CertificateNotYetValidException();
        }
        ChoiceOfTime choiceOfTime2 = this.n;
        if (choiceOfTime2 == null) {
            throw new CertificateExpiredException("ValidNotAfter date not set!");
        }
        if (date.after(choiceOfTime2.getDate())) {
            throw new CertificateExpiredException();
        }
    }

    public int countExtensions() {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return 0;
        }
        return attributeCertificateExtensions.countExtensions();
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        Objects.requireNonNull(aSN1Object, "Cannot parse certificate from a null object!");
        this.g = new ASN1(aSN1Object);
        try {
            d();
        } catch (Exception e2) {
            throw new CodingException(e2.toString());
        }
    }

    public void decode(InputStream inputStream) throws IOException, CertificateException {
        Objects.requireNonNull(inputStream, "Cannot parse certificate from a null input stream!");
        try {
            this.g = new ASN1(inputStream);
            d();
        } catch (CodingException e2) {
            throw new CertificateException(e2.toString());
        } catch (X509ExtensionException e3) {
            throw new CertificateException(e3.getMessage());
        }
    }

    public byte[] getAcInfo() throws CertificateEncodingException {
        try {
            ASN1 asn1 = this.g;
            return (asn1 == null || asn1.toByteArray() == null) ? DerCoder.encode(e()) : this.g.getFirstObject();
        } catch (CodingException e2) {
            throw new CertificateEncodingException(e2.toString());
        }
    }

    public Attribute getAttribute(ObjectID objectID) {
        Vector vector = this.o;
        if (vector == null) {
            return null;
        }
        Enumeration enumerationElements = vector.elements();
        while (enumerationElements.hasMoreElements()) {
            Attribute attribute = (Attribute) enumerationElements.nextElement();
            if (attribute.getType().equals(objectID)) {
                return attribute;
            }
        }
        return null;
    }

    public Enumeration getAttributes() {
        return this.o.elements();
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return null;
        }
        return attributeCertificateExtensions.getCriticalExtensionOIDs();
    }

    @Override // java.security.cert.Certificate
    public byte[] getEncoded() throws CertificateEncodingException {
        b();
        return toByteArray();
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return null;
        }
        V3Extension extension = attributeCertificateExtensions.getExtension(objectID);
        if (!(extension instanceof ProxyInfo)) {
            return extension;
        }
        ((ProxyInfo) extension).specifyHolder(this.i);
        return extension;
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return null;
        }
        return attributeCertificateExtensions.getExtensionValue(str);
    }

    public byte[] getFingerprint() {
        b();
        return this.g.fingerprint();
    }

    public byte[] getFingerprint(String str) throws NoSuchAlgorithmException {
        b();
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(toByteArray());
        return messageDigest.digest();
    }

    public byte[] getFingerprintSHA() {
        if (this.t == null) {
            try {
                this.t = getFingerprint("SHA");
            } catch (NoSuchAlgorithmException e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Algorithm SHA not available: ");
                stringBuffer.append(e2.toString());
                throw new RuntimeException(stringBuffer.toString());
            }
        }
        return this.t;
    }

    public Holder getHolder() {
        return this.i;
    }

    public AttCertIssuer getIssuer() {
        return this.j;
    }

    public boolean[] getIssuerUniqueID() {
        BigInteger bigInteger = this.p;
        if (bigInteger == null) {
            return null;
        }
        return Util.toBooleanArray(bigInteger.toString(2));
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return null;
        }
        return attributeCertificateExtensions.getNonCriticalExtensionOIDs();
    }

    public Date getNotAfterTime() {
        ChoiceOfTime choiceOfTime = this.n;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    public Date getNotBeforeTime() {
        ChoiceOfTime choiceOfTime = this.m;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    @Override // java.security.cert.Certificate
    public PublicKey getPublicKey() {
        return null;
    }

    public byte[] getRawExtensionValue(String str) {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return null;
        }
        return attributeCertificateExtensions.getRawExtensionValue(str);
    }

    public BigInteger getSerialNumber() {
        return this.l;
    }

    public String getSigAlgName() {
        AlgorithmID algorithmID = this.k;
        if (algorithmID == null) {
            return null;
        }
        return algorithmID.getName();
    }

    public String getSigAlgOID() {
        AlgorithmID algorithmID = this.k;
        if (algorithmID == null) {
            return null;
        }
        return algorithmID.getAlgorithm().getID();
    }

    public byte[] getSigAlgParams() {
        AlgorithmID algorithmID = this.k;
        if (algorithmID == null) {
            return null;
        }
        try {
            ASN1Object parameter = algorithmID.getParameter();
            if (parameter == null) {
                return null;
            }
            return new ASN1(parameter).toByteArray();
        } catch (CodingException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public byte[] getSignature() {
        return this.r;
    }

    public AlgorithmID getSignatureAlgorithm() {
        return this.k;
    }

    public int getVersion() {
        return this.h;
    }

    public boolean hasExtensions() {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return false;
        }
        return attributeCertificateExtensions.hasExtensions();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return false;
        }
        return attributeCertificateExtensions.hasUnsupportedCriticalExtension();
    }

    public boolean isTargetFor(Object obj) throws TargetException {
        try {
            return ((TargetInformation) getExtension(TargetInformation.oid)).isTargetFor(obj);
        } catch (X509ExtensionInitException e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error parsing TargetInformation extension: ");
            stringBuffer.append(e2.toString());
            throw new TargetException(stringBuffer.toString());
        }
    }

    public Enumeration listExtensions() {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions == null) {
            return null;
        }
        Enumeration enumerationListExtensions = attributeCertificateExtensions.listExtensions();
        if (!enumerationListExtensions.hasMoreElements()) {
            return enumerationListExtensions;
        }
        Vector vector = new Vector();
        while (enumerationListExtensions.hasMoreElements()) {
            V3Extension v3Extension = (V3Extension) enumerationListExtensions.nextElement();
            if (v3Extension instanceof ProxyInfo) {
                ((ProxyInfo) v3Extension).specifyHolder(this.i);
            }
            vector.addElement(v3Extension);
        }
        return vector.elements();
    }

    public void removeAllAttributes() {
        this.o.removeAllElements();
        a();
    }

    public void removeAllExtensions() {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions != null) {
            attributeCertificateExtensions.removeAllExtensions();
            a();
        }
        this.q = null;
    }

    public Attribute removeAttribute(ObjectID objectID) {
        if (objectID == null) {
            return null;
        }
        int size = this.o.size();
        for (int i = 0; i < size; i++) {
            if (objectID.equals(((Attribute) this.o.elementAt(i)).getType())) {
                Attribute attribute = (Attribute) this.o.elementAt(i);
                this.o.removeElementAt(i);
                a();
                return attribute;
            }
        }
        return null;
    }

    public boolean removeExtension(ObjectID objectID) {
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        boolean zRemoveExtension = attributeCertificateExtensions == null ? false : attributeCertificateExtensions.removeExtension(objectID);
        if (zRemoveExtension) {
            a();
        }
        return zRemoveExtension;
    }

    public void setAttributes(Attribute[] attributeArr) {
        if (attributeArr != null) {
            this.o.removeAllElements();
            for (Attribute attribute : attributeArr) {
                this.o.addElement(attribute);
            }
            a();
        }
    }

    public void setHolder(Holder holder) {
        this.i = holder;
        a();
    }

    public void setIssuer(AttCertIssuer attCertIssuer) {
        this.j = attCertIssuer;
        a();
    }

    public void setIssuerUniqueID(boolean[] zArr) {
        this.p = new BigInteger(Util.fromBooleanArray(zArr), 2);
        a();
    }

    public void setNotAfterTime(Date date) {
        this.n = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
        a();
    }

    public void setNotBeforeTime(Date date) {
        this.m = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
        a();
    }

    public void setSerialNumber(BigInteger bigInteger) {
        this.l = bigInteger;
        a();
    }

    public void setSignature(byte[] bArr) throws CertificateException {
        if (bArr == null) {
            throw new CertificateException("Cannot sign certificate! No signature value specified!");
        }
        this.r = bArr;
        ASN1Object aSN1ObjectE = e();
        a();
        try {
            BIT_STRING bit_string = new BIT_STRING(this.r);
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectE);
            sequence.addComponent(this.k.toASN1Object());
            sequence.addComponent(bit_string);
            this.g = new ASN1(sequence);
            c();
        } catch (CodingException e2) {
            throw new CertificateException(e2.toString());
        }
    }

    public void setSignatureAlgorithm(AlgorithmID algorithmID) {
        this.k = algorithmID;
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
        this.k = algorithmID;
        Signature signatureInstance = algorithmID.getSignatureInstance(str);
        signatureInstance.initSign(privateKey);
        try {
            if (!AlgorithmID.getDoNotIncludeParameters(this.k) && !this.k.hasParameters() && (signatureParameters = Util.getSignatureParameters(signatureInstance)) != null) {
                this.k.setAlgorithmParameters(signatureParameters);
            }
        } catch (Exception unused) {
        }
        a();
        ASN1Object aSN1ObjectE = e();
        try {
            signatureInstance.update(DerCoder.encode(aSN1ObjectE));
            this.r = signatureInstance.sign();
            BIT_STRING bit_string = new BIT_STRING(this.r);
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectE);
            sequence.addComponent(this.k.toASN1Object());
            sequence.addComponent(bit_string);
            this.g = new ASN1(sequence);
            c();
        } catch (CodingException e2) {
            throw new CertificateException(e2.toString());
        } catch (SignatureException e3) {
            throw new CertificateException(e3.toString());
        }
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        b();
        return this.g.toASN1Object();
    }

    public byte[] toByteArray() {
        b();
        return this.g.toByteArray();
    }

    @Override // java.security.cert.Certificate
    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.h);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Holder: {");
        stringBuffer3.append(this.i);
        stringBuffer3.append("}\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("Issuer: {");
        stringBuffer4.append(this.j);
        stringBuffer4.append("}\n");
        stringBuffer.append(stringBuffer4.toString());
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append("Signature algorithm: ");
        stringBuffer5.append(this.k);
        stringBuffer5.append("\n");
        stringBuffer.append(stringBuffer5.toString());
        StringBuffer stringBuffer6 = new StringBuffer();
        stringBuffer6.append("Serial number: ");
        stringBuffer6.append(this.l);
        stringBuffer6.append("\n");
        stringBuffer.append(stringBuffer6.toString());
        StringBuffer stringBuffer7 = new StringBuffer();
        stringBuffer7.append("Valid not before time: ");
        stringBuffer7.append(this.m);
        stringBuffer7.append("\n");
        stringBuffer.append(stringBuffer7.toString());
        StringBuffer stringBuffer8 = new StringBuffer();
        stringBuffer8.append("Valid not after  time: ");
        stringBuffer8.append(this.n);
        stringBuffer8.append("\n");
        stringBuffer.append(stringBuffer8.toString());
        stringBuffer.append("Attributes: ");
        Vector vector = this.o;
        if (z) {
            Enumeration enumerationElements = vector.elements();
            while (enumerationElements.hasMoreElements()) {
                StringBuffer stringBuffer9 = new StringBuffer();
                stringBuffer9.append("\n");
                stringBuffer9.append(enumerationElements.nextElement());
                stringBuffer.append(stringBuffer9.toString());
            }
        } else {
            stringBuffer.append(vector.size());
        }
        stringBuffer.append("\n");
        if (this.p != null) {
            StringBuffer stringBuffer10 = new StringBuffer();
            stringBuffer10.append("Issuer Unique ID: ");
            stringBuffer10.append(this.p);
            stringBuffer10.append("\n");
            stringBuffer.append(stringBuffer10.toString());
        }
        stringBuffer.append("\n");
        AttributeCertificateExtensions attributeCertificateExtensions = this.q;
        if (attributeCertificateExtensions != null) {
            if (z) {
                stringBuffer.append(attributeCertificateExtensions);
            } else {
                StringBuffer stringBuffer11 = new StringBuffer();
                stringBuffer11.append("Extensions: ");
                stringBuffer11.append(this.q.countExtensions());
                stringBuffer11.append("\n");
                stringBuffer.append(stringBuffer11.toString());
            }
        }
        StringBuffer stringBuffer12 = new StringBuffer();
        stringBuffer12.append("Certificate Fingerprint (MD5)  : ");
        stringBuffer12.append(Util.toString(getFingerprint()));
        stringBuffer12.append("\n");
        stringBuffer.append(stringBuffer12.toString());
        StringBuffer stringBuffer13 = new StringBuffer();
        stringBuffer13.append("Certificate Fingerprint (SHA-1): ");
        stringBuffer13.append(Util.toString(getFingerprintSHA()));
        stringBuffer13.append("\n");
        stringBuffer.append(stringBuffer13.toString());
        return stringBuffer.toString();
    }

    @Override // java.security.cert.Certificate
    public void verify(PublicKey publicKey) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, NoSuchProviderException {
        verify(publicKey, (String) null);
    }

    @Override // java.security.cert.Certificate
    public void verify(PublicKey publicKey, String str) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, NoSuchProviderException {
        b();
        Signature signatureInstance = str == null ? this.k.getSignatureInstance() : this.k.getSignatureInstance(str);
        try {
            byte[] firstObject = this.g.getFirstObject();
            signatureInstance.initVerify(publicKey);
            signatureInstance.update(firstObject);
            if (!signatureInstance.verify(this.r)) {
                throw new SignatureException("Signature verification error!");
            }
        } catch (CodingException e2) {
            throw new SignatureException(e2.toString());
        }
    }

    public void verify(DSAPublicKey dSAPublicKey, DSAParameterSpec dSAParameterSpec) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        b();
        Signature signature = Signature.getInstance("DSA", "IAIK");
        try {
            byte[] firstObject = this.g.getFirstObject();
            signature.setParameter("DSAParameterSpec", dSAParameterSpec);
            signature.initVerify(dSAPublicKey);
            signature.update(firstObject);
            if (!signature.verify(this.r)) {
                throw new SignatureException("Signature verification error!");
            }
        } catch (CodingException e2) {
            throw new SignatureException(e2.toString());
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        b();
        this.g.writeTo(outputStream);
    }
}

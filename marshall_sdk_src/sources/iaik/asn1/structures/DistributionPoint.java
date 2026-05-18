package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.SEQUENCE;
import iaik.utils.InternalErrorException;
import iaik.x509.X509CRL;
import iaik.x509.X509Certificate;
import iaik.x509.net.ldap.LdapURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CRLException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Objects;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class DistributionPoint {
    public static final int aACompromise = 256;
    public static final int affiliationChanged = 8;
    public static final int cACompromise = 4;
    public static final int certificateHold = 64;
    public static final int cessationOfOperation = 32;
    public static final int keyCompromise = 2;
    public static final int privilegeWithdrawn = 128;
    public static final int superseded = 16;
    public static final int unused = 1;
    X509CRL a;
    private ASN1Type b;
    private int c;
    private GeneralNames d;
    private String e;

    public DistributionPoint() {
        this.c = -1;
        this.e = LdapURLConnection.AD_REVOCATION_LIST;
    }

    public DistributionPoint(ASN1Object aSN1Object) throws CodingException {
        ASN1Type rdn;
        this();
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new CodingException("No DistributionPoint!");
        }
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            ASN1Object componentAt = aSN1Object.getComponentAt(i);
            if (!componentAt.isA(ASN.CON_SPEC)) {
                throw new CodingException("No DistributionPoint!");
            }
            int tag = componentAt.getAsnType().getTag();
            if (tag == 0) {
                ASN1Object aSN1Object2 = (ASN1Object) componentAt.getValue();
                if (!aSN1Object2.isA(ASN.CON_SPEC)) {
                    throw new CodingException("No DistributionPoint!");
                }
                CON_SPEC con_spec = (CON_SPEC) aSN1Object2;
                int tag2 = con_spec.getAsnType().getTag();
                if (tag2 == 0) {
                    if (con_spec.countComponents() > 1) {
                        con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                    }
                    rdn = new GeneralNames((ASN1Object) con_spec.getValue());
                } else {
                    if (tag2 != 1) {
                        throw new InternalErrorException("DistributionPointName: Unknown type [parse]!");
                    }
                    if (con_spec.countComponents() > 1 || (con_spec.countComponents() == 1 && !con_spec.getComponentAt(0).isA(ASN.SET))) {
                        con_spec.forceImplicitlyTagged(ASN.SET);
                    }
                    rdn = new RDN((ASN1Object) con_spec.getValue());
                }
                this.b = rdn;
            } else if (tag == 1) {
                CON_SPEC con_spec2 = (CON_SPEC) componentAt;
                if (con_spec2.isImplicitlyTagged()) {
                    con_spec2.forceImplicitlyTagged(ASN.BIT_STRING);
                }
                this.c = Integer.parseInt(new StringBuffer(((BIT_STRING) con_spec2.getValue()).getBinaryString()).reverse().toString(), 2);
            } else if (tag == 2) {
                CON_SPEC con_spec3 = (CON_SPEC) componentAt;
                if (con_spec3.countComponents() > 1) {
                    con_spec3.forceImplicitlyTagged(ASN.SEQUENCE);
                }
                this.d = new GeneralNames((ASN1Object) componentAt.getValue());
            }
        }
    }

    public DistributionPoint(ASN1Type aSN1Type) throws IllegalArgumentException {
        this();
        setDistributionPointName(aSN1Type);
    }

    public DistributionPoint(String[] strArr) {
        this();
        setDistributionPointNameURIs(strArr);
    }

    private InputStream a(String str, Name name, boolean z) throws IOException, CRLException {
        InputStream inputStreamA;
        Exception exc = null;
        this.a = null;
        ASN1Type aSN1Type = this.b;
        if (aSN1Type == null) {
            Name crlIssuerName = getCrlIssuerName();
            if (crlIssuerName != null) {
                if (str == null || !str.toLowerCase().startsWith("ldap")) {
                    throw new MalformedURLException("Need LDAP url if dp name is not set but crl issuer is set!");
                }
                str = a(str, crlIssuerName, null, this.e);
            }
            if (str == null) {
                throw new CRLException("Cannot build url from where to download crl");
            }
        } else {
            if (aSN1Type instanceof GeneralNames) {
                String[] distributionPointNameURIs = getDistributionPointNameURIs();
                if (distributionPointNameURIs.length == 0) {
                    throw new MalformedURLException("Only can handle distribution point general names of type uniformResourceIdentifier!");
                }
                int i = 0;
                Exception exc2 = null;
                while (true) {
                    if (i >= distributionPointNameURIs.length) {
                        inputStreamA = null;
                        exc = exc2;
                        break;
                    }
                    try {
                        inputStreamA = a(distributionPointNameURIs[i], z);
                        break;
                    } catch (Exception e) {
                        if (exc2 == null) {
                            exc2 = e;
                        }
                        i++;
                    }
                }
                if (exc == null) {
                    return inputStreamA;
                }
                if (exc instanceof MalformedURLException) {
                    throw ((MalformedURLException) exc);
                }
                if (exc instanceof IOException) {
                    throw ((IOException) exc);
                }
                if (exc instanceof CRLException) {
                    throw ((CRLException) exc);
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error loading crl: ");
                stringBuffer.append(exc.toString());
                throw new CRLException(stringBuffer.toString());
            }
            if (str == null || !str.toLowerCase().startsWith("ldap")) {
                throw new MalformedURLException("Need LDAP url for RDN distribution point name!");
            }
            Name crlIssuerName2 = getCrlIssuerName();
            if (crlIssuerName2 != null) {
                name = crlIssuerName2;
            }
            if (name == null) {
                throw new CRLException("Crl issuer required for RDN distribution point name!");
            }
            str = a(str, name, (RDN) this.b, this.e);
        }
        return a(str, z);
    }

    private InputStream a(String str, boolean z) throws IOException, CRLException {
        this.a = null;
        try {
            try {
                InputStream inputStream = new URL(str).openConnection().getInputStream();
                if (z) {
                    try {
                        this.a = new X509CRL(inputStream);
                    } catch (Exception e) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Error loading crl from ");
                        stringBuffer.append(str);
                        stringBuffer.append(": ");
                        stringBuffer.append(e.toString());
                        throw new CRLException(stringBuffer.toString());
                    }
                }
                return inputStream;
            } catch (IOException e2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Error loading crl from ");
                stringBuffer2.append(str);
                stringBuffer2.append(": ");
                stringBuffer2.append(e2.toString());
                throw new IOException(stringBuffer2.toString());
            }
        } catch (MalformedURLException e3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Error loading crl from ");
            stringBuffer3.append(str);
            stringBuffer3.append(": ");
            stringBuffer3.append(e3.toString());
            throw new MalformedURLException(stringBuffer3.toString());
        }
    }

    private static String a(String str, Name name, RDN rdn, String str2) throws CRLException {
        StringBuffer stringBuffer = new StringBuffer(str);
        if (!str.endsWith("/")) {
            stringBuffer.append("/");
        }
        if (rdn != null) {
            try {
                Name name2 = new Name(DerCoder.decode(name.getEncoded()));
                name2.addRDN(rdn);
                name = name2;
            } catch (Exception e) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Error building LDAP url: ");
                stringBuffer2.append(e.toString());
                throw new CRLException(stringBuffer2.toString());
            }
        }
        stringBuffer.append(name.getRFC2253String());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("?");
        stringBuffer3.append(str2);
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }

    public boolean containsUriDpName() {
        ASN1Type aSN1Type = this.b;
        if (aSN1Type == null || !(aSN1Type instanceof GeneralNames)) {
            return false;
        }
        return ((GeneralNames) aSN1Type).containsGeneralName(6);
    }

    public GeneralNames getCrlIssuer() {
        return this.d;
    }

    public Name getCrlIssuerName() {
        GeneralNames generalNames = this.d;
        Name name = null;
        if (generalNames != null) {
            Enumeration names = generalNames.getNames();
            while (names.hasMoreElements()) {
                GeneralName generalName = (GeneralName) names.nextElement();
                if (generalName.getType() == 4) {
                    name = (Name) generalName.getName();
                }
            }
        }
        return name;
    }

    public ASN1Type getDistributionPointName() {
        return this.b;
    }

    public String[] getDistributionPointNameURIs() {
        return getDistributionPointNameURIs(null);
    }

    public String[] getDistributionPointNameURIs(String str) {
        String[] strArr;
        String str2;
        if (this.b instanceof GeneralNames) {
            Vector vector = new Vector();
            Enumeration names = ((GeneralNames) this.b).getNames();
            while (names.hasMoreElements()) {
                GeneralName generalName = (GeneralName) names.nextElement();
                if (generalName.getType() == 6 && (str2 = (String) generalName.getName()) != null && (str == null || str2.toLowerCase(Locale.US).startsWith(str))) {
                    vector.addElement(str2);
                }
            }
            strArr = new String[vector.size()];
            vector.copyInto(strArr);
        } else {
            strArr = null;
        }
        return strArr == null ? new String[0] : strArr;
    }

    public int getReasonFlags() {
        return this.c;
    }

    public boolean isSet(int i) {
        int i2 = this.c;
        return (i2 == -1 || (i & i2) == 0) ? false : true;
    }

    public X509CRL loadCrl() throws IOException, CRLException {
        return loadCrl(null, null);
    }

    public X509CRL loadCrl(String str, Name name) throws Throwable {
        InputStream inputStreamA;
        try {
            inputStreamA = a(str, name, true);
            try {
                X509CRL x509crl = this.a;
                if (inputStreamA != null) {
                    try {
                        inputStreamA.close();
                    } catch (IOException unused2) {
                    }
                }
                return x509crl;
            } catch (Throwable th) {
                th = th;
                if (inputStreamA != null) {
                    try {
                        inputStreamA.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStreamA = null;
        }
    }

    public InputStream loadCrlStream() throws IOException {
        return loadCrlStream(null, null);
    }

    public InputStream loadCrlStream(String str, Name name) throws IOException {
        try {
            return a(str, name, false);
        } catch (CRLException unused2) {
            return null;
        }
    }

    public void setCrlIssuer(GeneralNames generalNames) {
        this.d = generalNames;
    }

    public void setCrlIssuerName(Name name) {
        if (name != null) {
            this.d = new GeneralNames(new GeneralName(4, name));
        } else {
            this.d = null;
        }
    }

    public void setCrlIssuerName(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            this.d = null;
            return;
        }
        Name name = (Name) x509Certificate.getSubjectDN();
        Objects.requireNonNull(name, "Subject field of the crl issuer cert must not be empty!");
        this.d = new GeneralNames(new GeneralName(4, name));
    }

    public void setDistributionPointName(ASN1Type aSN1Type) throws IllegalArgumentException {
        if (!(aSN1Type instanceof GeneralNames) && !(aSN1Type instanceof RDN)) {
            throw new IllegalArgumentException("Only instances of GeneralNames or RDN are accepted!");
        }
        this.b = aSN1Type;
    }

    public void setDistributionPointNameURIs(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new NullPointerException("No uri names specified");
        }
        if (this.b == null) {
            this.b = new GeneralNames();
        }
        for (String str : strArr) {
            ((GeneralNames) this.b).addName(new GeneralName(6, str));
        }
    }

    public void setLdapAttributeDescription(String str) {
        Objects.requireNonNull(str, "Attribute description may not be null!");
        this.e = str;
    }

    public void setReasonFlags(int i) {
        this.c = i;
    }

    public ASN1Object toASN1Object() throws CodingException {
        int i;
        SEQUENCE sequence = new SEQUENCE();
        ASN1Type aSN1Type = this.b;
        if (aSN1Type != null) {
            if (aSN1Type instanceof GeneralNames) {
                i = 0;
            } else {
                if (!(aSN1Type instanceof RDN)) {
                    throw new InternalErrorException("DistributionPointName: Unknown type [create]!");
                }
                i = 1;
            }
            sequence.addComponent(new CON_SPEC(0, new CON_SPEC(i, this.b.toASN1Object(), true)));
        }
        int i2 = this.c;
        if (i2 != -1) {
            StringBuffer stringBuffer = new StringBuffer(Integer.toBinaryString(i2));
            stringBuffer.reverse();
            sequence.addComponent(new CON_SPEC(1, new BIT_STRING(stringBuffer.toString()), true));
        }
        if (this.d != null) {
            sequence.addComponent(new CON_SPEC(2, this.d.toASN1Object(), true));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.b != null) {
            stringBuffer.append("DistributionPoint: ");
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.b.toString());
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.c > 0) {
            stringBuffer.append("reasonFlags: ");
            if ((this.c & 1) > 0) {
                stringBuffer.append("unused | ");
            }
            if ((this.c & 2) > 0) {
                stringBuffer.append("keyCompromise | ");
            }
            if ((this.c & 4) > 0) {
                stringBuffer.append("cACompromise | ");
            }
            if ((this.c & 8) > 0) {
                stringBuffer.append("affiliationChanged | ");
            }
            if ((this.c & 16) > 0) {
                stringBuffer.append("superseded | ");
            }
            if ((this.c & 32) > 0) {
                stringBuffer.append("cessationOfOperation | ");
            }
            if ((this.c & 64) > 0) {
                stringBuffer.append("certificateHold | ");
            }
            if ((this.c & 128) > 0) {
                stringBuffer.append("privilegeWithdrawn | ");
            }
            if ((this.c & 256) > 0) {
                stringBuffer.append("aACompromise | ");
            }
            stringBuffer.setLength(stringBuffer.length() - 3);
            stringBuffer.append("\n");
        }
        if (this.d != null) {
            stringBuffer.append("CRLIssuer:\n");
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append(this.d.toString());
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        int length = stringBuffer.length() - 1;
        if (length < 0) {
            length = 0;
        }
        stringBuffer.setLength(length);
        return stringBuffer.toString();
    }
}

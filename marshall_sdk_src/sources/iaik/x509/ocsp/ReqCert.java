package iaik.x509.ocsp;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.Name;
import iaik.pkcs.pkcs7.IssuerAndSerialNumber;
import iaik.security.md.SHA;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import iaik.x509.X509Certificate;

/* JADX INFO: loaded from: classes2.dex */
public class ReqCert {
    public static final int certHash = 4;
    public static final int certID = 0;
    public static final int issuerSerial = 1;
    public static final int name = 3;
    public static final int pKCert = 2;
    private int a;
    private Object b;

    public ReqCert(int i, Object obj) throws IllegalArgumentException {
        this.a = -1;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            throw new IllegalArgumentException("Cannot create ReqCert! Illegal type specification! Type has to be in the range of 0...4!");
                        }
                        if (obj instanceof X509Certificate) {
                            try {
                                this.b = a(((X509Certificate) obj).getEncoded());
                            } catch (Exception e) {
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append("Cannot create cert hash for cert: ");
                                stringBuffer.append(e.getMessage());
                                throw new IllegalArgumentException(stringBuffer.toString());
                            }
                        } else {
                            if (!(obj instanceof byte[])) {
                                throw new IllegalArgumentException("Cannot create ReqCert! Expecting X509Certificate or byte array for type certHash!");
                            }
                            this.b = obj;
                        }
                    } else if (!(obj instanceof GeneralName)) {
                        throw new IllegalArgumentException("Cannot create ReqCert! Expecting GeneralName object for type name!");
                    }
                } else if (!(obj instanceof X509Certificate)) {
                    throw new IllegalArgumentException("Cannot create ReqCert! Expecting X509Certificate object for type pKCert!");
                }
            } else if (!(obj instanceof IssuerAndSerialNumber)) {
                throw new IllegalArgumentException("Cannot create ReqCert! Expecting IssuerAndSerialNumber object for type issuerSerial!");
            }
        } else if (!(obj instanceof CertID)) {
            throw new IllegalArgumentException("Cannot create ReqCert! Expecting OCSP certID object for type certID!");
        }
        if (i != 4) {
            this.b = obj;
        }
        this.a = i;
    }

    public ReqCert(ASN1Object aSN1Object) throws CodingException {
        this.a = -1;
        a(aSN1Object);
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        Object issuerAndSerialNumber;
        int i;
        if (aSN1Object.isA(ASN.CON_SPEC)) {
            CON_SPEC con_spec = (CON_SPEC) aSN1Object;
            this.a = con_spec.getAsnType().getTag();
            ASN1Object aSN1Object2 = (ASN1Object) con_spec.getValue();
            int i2 = this.a;
            if (i2 != 0) {
                if (i2 == 1) {
                    X509Certificate x509Certificate = new X509Certificate();
                    this.b = x509Certificate;
                    x509Certificate.decode(aSN1Object2);
                } else if (i2 == 2) {
                    issuerAndSerialNumber = new GeneralName(aSN1Object2);
                } else {
                    if (i2 != 3) {
                        throw new CodingException("Cannot parse ReqCert! Illegal type specification: Type has to be in the range of 0...4!");
                    }
                    this.b = aSN1Object2.getValue();
                }
                i = this.a + 1;
            } else {
                issuerAndSerialNumber = new IssuerAndSerialNumber(aSN1Object2);
            }
            this.b = issuerAndSerialNumber;
            i = this.a + 1;
        } else {
            this.b = new CertID(aSN1Object);
            i = 0;
        }
        this.a = i;
    }

    private static byte[] a(byte[] bArr) {
        return new SHA().digest(bArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReqCert)) {
            return false;
        }
        ReqCert reqCert = (ReqCert) obj;
        int i = this.a;
        if (i != reqCert.a) {
            return false;
        }
        return i == 4 ? CryptoUtils.equalsBlock((byte[]) this.b, (byte[]) reqCert.b) : this.b.equals(reqCert.b);
    }

    public Object getReqCert() {
        return this.b;
    }

    public int getType() {
        return this.a;
    }

    public String getTypeName() {
        int i = this.a;
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "undefined" : "certHash" : AppMeasurementSdk.ConditionalUserProperty.NAME : "pKCert" : "issuerSerial" : "certID";
    }

    public int hashCode() {
        int i = this.a;
        return i + ((i == 0 || i == 1 || i == 2 || i == 3) ? this.b.hashCode() : i != 4 ? -1038130864 : Util.calculateHashCode((byte[]) this.b));
    }

    public boolean isReqCertFor(X509Certificate x509Certificate, X509Certificate x509Certificate2, GeneralName generalName) throws OCSPException {
        try {
            int i = this.a;
            if (i == 0) {
                if (x509Certificate == null || x509Certificate2 == null) {
                    return false;
                }
                return ((CertID) this.b).isCertIDFor((Name) x509Certificate2.getSubjectDN(), x509Certificate2.getPublicKey(), x509Certificate.getSerialNumber());
            }
            if (i == 1) {
                if (x509Certificate != null) {
                    return new IssuerAndSerialNumber(x509Certificate).equals(this.b);
                }
                return false;
            }
            if (i == 2) {
                if (x509Certificate != null) {
                    return x509Certificate.equals(this.b);
                }
                return false;
            }
            if (i != 3) {
                if (i != 4 || x509Certificate == null) {
                    return false;
                }
                return CryptoUtils.equalsBlock((byte[]) this.b, a(x509Certificate.getEncoded()));
            }
            if (generalName != null) {
                return generalName.equals(this.b);
            }
            if (x509Certificate != null) {
                return new GeneralName(4, x509Certificate.getSubjectDN()).equals(this.b);
            }
            return false;
        } catch (Exception e) {
            throw new OCSPException(e.getMessage());
        }
    }

    public ASN1Object toASN1Object() throws CodingException {
        int i = this.a;
        if (i == 0) {
            return ((CertID) this.b).toASN1Object();
        }
        if (i == 1) {
            return new CON_SPEC(0, ((IssuerAndSerialNumber) this.b).toASN1Object());
        }
        if (i == 2) {
            return new CON_SPEC(1, ((X509Certificate) this.b).toASN1Object());
        }
        if (i == 3) {
            return new CON_SPEC(2, ((GeneralName) this.b).toASN1Object());
        }
        if (i == 4) {
            return new CON_SPEC(3, new OCTET_STRING((byte[]) this.b));
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unknown reqCert type: ");
        stringBuffer.append(this.a);
        stringBuffer.append("!");
        throw new InternalErrorException(stringBuffer.toString());
    }

    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        int i = this.a;
        stringBuffer.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "undefined" : "certHash: " : "name:{\n" : "pKCert:{\n" : "issuerSerial:{\n" : "certID:{\n");
        int i2 = this.a;
        if (i2 >= 0) {
            if (i2 == 4) {
                string = Util.toString((byte[]) this.b);
            } else {
                Util.printIndented(this.b.toString(), true, "  ", stringBuffer);
                string = "\n}";
            }
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
}

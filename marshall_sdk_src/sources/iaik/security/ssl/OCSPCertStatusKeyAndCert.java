package iaik.security.ssl;

import iaik.asn1.ASN1;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.AccessDescription;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.Name;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.extensions.AuthorityInfoAccess;
import iaik.x509.ocsp.BasicOCSPResponse;
import iaik.x509.ocsp.CertID;
import iaik.x509.ocsp.OCSPException;
import iaik.x509.ocsp.OCSPExtensions;
import iaik.x509.ocsp.OCSPRequest;
import iaik.x509.ocsp.OCSPResponse;
import iaik.x509.ocsp.ReqCert;
import iaik.x509.ocsp.Request;
import iaik.x509.ocsp.ResponderID;
import iaik.x509.ocsp.SingleResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class OCSPCertStatusKeyAndCert extends KeyAndCert {
    private String a;
    private HashMap b;
    private ReqCert c;

    private static final ReqCert a(X509Certificate[] x509CertificateArr) throws Exception {
        X509Certificate x509Certificate = x509CertificateArr[0];
        iaik.x509.X509Certificate x509CertificateConvertCertificate = Util.convertCertificate(x509CertificateArr.length == 1 ? x509CertificateArr[0] : x509CertificateArr[1]);
        MessageDigest messageDigest = SecurityProvider.getSecurityProvider().getMessageDigest("SHA");
        return new ReqCert(0, new CertID(AlgorithmID.sha1, messageDigest.digest(((Name) x509CertificateConvertCertificate.getSubjectDN()).getEncoded()), messageDigest.digest((byte[]) ((BIT_STRING) DerCoder.decode(x509CertificateConvertCertificate.getPublicKey().getEncoded()).getComponentAt(1)).getValue()), x509Certificate.getSerialNumber()));
    }

    public OCSPCertStatusKeyAndCert(X509Certificate[] x509CertificateArr, PrivateKey privateKey, String str) {
        super(x509CertificateArr, privateKey);
        if (str != null && !str.toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException("Only http responders are supported by this implementation!");
        }
        this.a = str;
        if (x509CertificateArr.length == 1) {
            if (!x509CertificateArr[0].getSubjectDN().equals(x509CertificateArr[0].getIssuerDN())) {
                throw new IllegalArgumentException("Chain does contain only one element but server cert is not self signed. Cannot create OCSP CertID from it!");
            }
        } else if (!x509CertificateArr[1].getSubjectDN().equals(x509CertificateArr[0].getIssuerDN())) {
            throw new IllegalArgumentException("Server cert issuer dn does not match to subject of cahin[1]. Cannot create OCSP CertID for it!");
        }
        try {
            this.c = a(x509CertificateArr);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Cannot create OCSP CertID for server certificate: ");
            stringBuffer.append(e.toString());
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public synchronized void addOCSPResponder(ResponderID responderID, String str) {
        if (responderID == null) {
            throw new NullPointerException("responder if must not be null!");
        }
        if (str == null) {
            throw new NullPointerException("responder url must not be null!");
        }
        if (!str.toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException("Only http responders are supported by this implementation!");
        }
        if (this.b == null) {
            this.b = new HashMap(5);
        }
        this.b.put(responderID, str);
    }

    @Override // iaik.security.ssl.KeyAndCert
    public byte[] getCertificateStatus(int i, byte[] bArr, SSLTransport sSLTransport) throws Throwable {
        InputStream inputStream;
        ASN1 asn1;
        boolean z;
        SingleResponse singleResponse;
        boolean z2;
        boolean z3 = sSLTransport.b != null;
        if (i != 1) {
            StringBuffer stringBuffer = new StringBuffer("Unsupported cert status type: ");
            stringBuffer.append(i);
            throw new SSLException(stringBuffer.toString(), 2, 80, false);
        }
        try {
            try {
                OCSPStatusRequest oCSPStatusRequest = new OCSPStatusRequest(new ab(bArr));
                ResponderID[] responderIDs = oCSPStatusRequest.getResponderIDs();
                OCSPExtensions extensions = oCSPStatusRequest.getExtensions();
                try {
                    String strA = a(responderIDs);
                    if (strA == null) {
                        if (!z3) {
                            return null;
                        }
                        sSLTransport.a("No OCSP responder url available. Cannot send ocsp request.");
                        return null;
                    }
                    Request request = new Request(this.c);
                    OCSPRequest oCSPRequest = new OCSPRequest();
                    oCSPRequest.setRequestList(new Request[]{request});
                    if (extensions != null && extensions.hasExtensions()) {
                        Enumeration enumerationListExtensions = extensions.listExtensions();
                        while (enumerationListExtensions.hasMoreElements()) {
                            oCSPRequest.addExtension((V3Extension) enumerationListExtensions.nextElement());
                        }
                    }
                    if (z3) {
                        StringBuffer stringBuffer2 = new StringBuffer("Connecting to ocsp responder at ");
                        stringBuffer2.append(strA);
                        sSLTransport.a(stringBuffer2.toString());
                    }
                    URL url = new URL(strA);
                    try {
                        byte[] encoded = oCSPRequest.getEncoded();
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestProperty("Content-Type", "application/ocsp-request");
                        httpURLConnection.setRequestProperty("Accept", "application/ocsp-response");
                        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(encoded.length));
                        httpURLConnection.setDoOutput(true);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                        try {
                            bufferedOutputStream.write(encoded);
                            bufferedOutputStream.flush();
                            if (httpURLConnection.getResponseCode() / 100 != 2) {
                                if (z3) {
                                    StringBuffer stringBuffer3 = new StringBuffer("Error connecting to ocsp responder: ");
                                    stringBuffer3.append(httpURLConnection.getResponseMessage());
                                    sSLTransport.a(stringBuffer3.toString());
                                }
                                inputStream = null;
                                asn1 = null;
                            } else {
                                inputStream = httpURLConnection.getInputStream();
                                try {
                                    asn1 = new ASN1(new BufferedInputStream(inputStream));
                                } catch (Throwable th) {
                                    th = th;
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (IOException unused) {
                                        }
                                    }
                                    try {
                                        bufferedOutputStream.close();
                                        throw th;
                                    } catch (IOException unused2) {
                                        throw th;
                                    }
                                }
                            }
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException unused3) {
                                }
                            }
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException unused4) {
                            }
                            if (asn1 == null) {
                                return null;
                            }
                            OCSPResponse oCSPResponse = new OCSPResponse(asn1.toASN1Object());
                            if (oCSPResponse.getResponseStatus() != 0 || !oCSPResponse.getResponseType().equals(BasicOCSPResponse.responseType)) {
                                return null;
                            }
                            BasicOCSPResponse basicOCSPResponse = (BasicOCSPResponse) oCSPResponse.getResponse();
                            if (basicOCSPResponse.containsCertificates()) {
                                try {
                                    basicOCSPResponse.verify();
                                    z = true;
                                } catch (Exception e) {
                                    if (z3) {
                                        StringBuffer stringBuffer4 = new StringBuffer("Error verifiying ocsp response: ");
                                        stringBuffer4.append(e.toString());
                                        sSLTransport.a(stringBuffer4.toString());
                                    }
                                    z = false;
                                }
                            } else {
                                z = true;
                            }
                            if (!z) {
                                return null;
                            }
                            try {
                                singleResponse = basicOCSPResponse.getSingleResponse(this.c);
                            } catch (OCSPException unused5) {
                                singleResponse = null;
                            }
                            if (singleResponse == null) {
                                if (!z3) {
                                    return null;
                                }
                                sSLTransport.a("Got no single response for the server certificate.");
                                return null;
                            }
                            if (extensions == null || !extensions.hasExtensions()) {
                                z2 = true;
                            } else {
                                try {
                                    OCSPCertStatusChainVerifier.a(extensions, basicOCSPResponse);
                                    z2 = true;
                                } catch (SSLException e2) {
                                    if (z3) {
                                        sSLTransport.a(e2.getMessage());
                                    }
                                    z2 = false;
                                }
                            }
                            if (!z2) {
                                return null;
                            }
                            byte[] byteArray = asn1.toByteArray();
                            v vVar = new v(byteArray.length + 3);
                            vVar.b(byteArray);
                            return vVar.a();
                        } catch (Throwable th2) {
                            th = th2;
                            inputStream = null;
                        }
                    } catch (CodingException e3) {
                        StringBuffer stringBuffer5 = new StringBuffer("Request encoding error: ");
                        stringBuffer5.append(e3.getMessage());
                        throw new IOException(stringBuffer5.toString());
                    }
                } catch (Exception e4) {
                    if (!z3) {
                        return null;
                    }
                    StringBuffer stringBuffer6 = new StringBuffer("Could not get ocsp cert status: ");
                    stringBuffer6.append(e4.toString());
                    sSLTransport.a(stringBuffer6.toString());
                    return null;
                }
            } catch (SSLException e5) {
                throw e5;
            }
        } catch (IOException e6) {
            StringBuffer stringBuffer7 = new StringBuffer("Error decoding ocsp status request: ");
            stringBuffer7.append(e6.toString());
            throw new SSLException(stringBuffer7.toString(), 2, 50, false);
        }
    }

    private String a(ResponderID[] responderIDArr) {
        String str;
        String str2 = null;
        if (responderIDArr != null && this.b != null) {
            for (int i = 0; i < responderIDArr.length && (str2 = (String) this.b.get(responderIDArr[i])) == null; i++) {
            }
        }
        if (str2 == null && (str = this.a) != null) {
            str2 = str;
        }
        if (str2 != null) {
            return str2;
        }
        try {
            AuthorityInfoAccess authorityInfoAccess = (AuthorityInfoAccess) Util.convertCertificateChain(getCertificateChain())[0].getExtension(AuthorityInfoAccess.oid);
            if (authorityInfoAccess == null) {
                return str2;
            }
            Enumeration accessDescriptions = authorityInfoAccess.getAccessDescriptions();
            while (accessDescriptions.hasMoreElements()) {
                AccessDescription accessDescription = (AccessDescription) accessDescriptions.nextElement();
                if (accessDescription.getAccessMethod().equals(ObjectID.ocsp)) {
                    GeneralName accessLocation = accessDescription.getAccessLocation();
                    if (accessLocation.getType() == 6) {
                        return (String) accessLocation.getName();
                    }
                }
            }
            return str2;
        } catch (Exception unused) {
            return str2;
        }
    }

    @Override // iaik.security.ssl.KeyAndCert
    public Object clone() {
        return super.clone();
    }

    @Override // iaik.security.ssl.KeyAndCert
    public int hashCode() {
        int iHashCode = super.hashCode();
        String str = this.a;
        if (str != null) {
            iHashCode += str.hashCode();
        }
        ReqCert reqCert = this.c;
        return reqCert != null ? iHashCode + reqCert.hashCode() : iHashCode;
    }

    @Override // iaik.security.ssl.KeyAndCert
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OCSPCertStatusKeyAndCert) {
            boolean zEquals = super.equals(obj);
            if (zEquals) {
                OCSPCertStatusKeyAndCert oCSPCertStatusKeyAndCert = (OCSPCertStatusKeyAndCert) obj;
                String str = this.a;
                if (str != null) {
                    zEquals = str.equals(oCSPCertStatusKeyAndCert.a);
                } else {
                    zEquals = oCSPCertStatusKeyAndCert.a == null;
                }
                if (zEquals) {
                    HashMap map = this.b;
                    if (map != null && oCSPCertStatusKeyAndCert.b != null) {
                        if (map.size() == oCSPCertStatusKeyAndCert.b.size()) {
                            for (Object obj2 : this.b.keySet().toArray()) {
                                String str2 = (String) this.b.get(obj2);
                                String str3 = (String) oCSPCertStatusKeyAndCert.b.get(obj2);
                                if (str2 != null) {
                                    if (str2.equals(str3)) {
                                    }
                                } else if (str3 == null) {
                                }
                            }
                        }
                    } else if (map == null && oCSPCertStatusKeyAndCert.b == null) {
                        return true;
                    }
                }
            }
            return zEquals;
        }
        return false;
    }

    @Override // iaik.security.ssl.KeyAndCert
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer("Responder url: ");
            stringBuffer2.append(this.a);
            stringBuffer.append(stringBuffer2.toString());
        }
        stringBuffer.append("\n");
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer("Registered responders:\n ");
            stringBuffer3.append(this.b);
            stringBuffer.append(stringBuffer3.toString());
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}

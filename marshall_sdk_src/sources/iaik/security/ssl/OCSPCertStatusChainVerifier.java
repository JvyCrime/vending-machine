package iaik.security.ssl;

import iaik.asn1.ObjectID;
import iaik.utils.Util;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.extensions.ExtendedKeyUsage;
import iaik.x509.ocsp.BasicOCSPResponse;
import iaik.x509.ocsp.CertStatus;
import iaik.x509.ocsp.OCSPException;
import iaik.x509.ocsp.OCSPExtensions;
import iaik.x509.ocsp.OCSPResponse;
import iaik.x509.ocsp.ResponderID;
import iaik.x509.ocsp.SingleResponse;
import iaik.x509.ocsp.extensions.Nonce;
import iaik.x509.ocsp.utils.TrustedResponders;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class OCSPCertStatusChainVerifier extends ChainVerifier {
    private TrustedResponders a;
    private long b = 60000;

    static final void a(OCSPExtensions oCSPExtensions, BasicOCSPResponse basicOCSPResponse) throws SSLCertificateException {
        try {
            Nonce nonce = (Nonce) oCSPExtensions.getExtension(Nonce.oid);
            if (nonce != null) {
                byte[] nonce2 = basicOCSPResponse.getNonce();
                if (nonce2 == null) {
                    throw new SSLCertificateException("OCSP response does not contain requested Nonce extensions!", 2, 113, false, null);
                }
                if (!Utils.equalsBlock(nonce.getValue(), nonce2)) {
                    throw new SSLCertificateException("OCSP response nonce does not match to request nonce!", 2, 113, false, null);
                }
            }
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error checking nonce: ");
            stringBuffer.append(e.toString());
            throw new SSLCertificateException(stringBuffer.toString(), 2, 80, false, e);
        }
    }

    @Override // iaik.security.ssl.ChainVerifier
    public boolean verifyChain(X509Certificate[] x509CertificateArr, SSLTransport sSLTransport, int i, byte[] bArr, byte[] bArr2) throws SSLCertificateRuntimeException {
        X509Certificate issuerCertificate;
        iaik.x509.X509Certificate x509CertificateVerify;
        iaik.x509.X509Certificate x509Certificate;
        boolean z;
        boolean zVerifyChain = super.verifyChain(x509CertificateArr, sSLTransport);
        boolean z2 = false;
        if (!zVerifyChain || bArr == null || bArr2 == null) {
            return zVerifyChain;
        }
        boolean z3 = sSLTransport.b != null;
        if (i != 1) {
            StringBuffer stringBuffer = new StringBuffer("Unsupported cert status type: ");
            stringBuffer.append(i);
            String string = stringBuffer.toString();
            if (z3) {
                sSLTransport.a(string);
            }
            throw new SSLCertificateRuntimeException(new SSLCertificateException(string, 2, 113, false, null));
        }
        try {
            OCSPResponse oCSPResponse = new OCSPResponse(new ab(bArr2).i());
            if (oCSPResponse.getResponseStatus() != 0) {
                StringBuffer stringBuffer2 = new StringBuffer("OCSP response not successful; got response status: ");
                stringBuffer2.append(oCSPResponse.getResponseStatusName());
                String string2 = stringBuffer2.toString();
                if (z3) {
                    sSLTransport.a(string2);
                }
                throw new SSLCertificateRuntimeException(new SSLCertificateException(string2, 2, 113, false, null));
            }
            ObjectID responseType = oCSPResponse.getResponseType();
            if (!responseType.equals(BasicOCSPResponse.responseType)) {
                StringBuffer stringBuffer3 = new StringBuffer("Unsupported OCSP response type (");
                stringBuffer3.append(responseType);
                stringBuffer3.append("). Expected basic ocsp response.");
                String string3 = stringBuffer3.toString();
                if (z3) {
                    sSLTransport.a(string3);
                }
                throw new SSLCertificateRuntimeException(new SSLCertificateException(string3, 2, 113, false, null));
            }
            BasicOCSPResponse basicOCSPResponse = (BasicOCSPResponse) oCSPResponse.getResponse();
            X509Certificate x509Certificate2 = x509CertificateArr[0];
            if (x509CertificateArr.length == 1) {
                issuerCertificate = x509Certificate2.getSubjectDN().equals(x509Certificate2.getIssuerDN()) ? x509Certificate2 : null;
            } else {
                issuerCertificate = x509CertificateArr[1];
            }
            if (issuerCertificate == null) {
                issuerCertificate = getIssuerCertificate(x509Certificate2);
            }
            if (issuerCertificate == null) {
                if (z3) {
                    sSLTransport.a("Cannot check ocsp response. Server cert issuer not available.");
                }
                throw new SSLCertificateRuntimeException(new SSLCertificateException("Cannot check ocsp response. Server cert issuer not available.", 2, 113, false, null));
            }
            try {
                iaik.x509.X509Certificate x509CertificateConvertCertificate = Util.convertCertificate(issuerCertificate);
                iaik.x509.X509Certificate x509CertificateConvertCertificate2 = Util.convertCertificate(x509Certificate2);
                try {
                    try {
                        x509CertificateVerify = basicOCSPResponse.verify();
                        if (z3) {
                            try {
                                StringBuffer stringBuffer4 = new StringBuffer("Signature ok from response signer ");
                                stringBuffer4.append(x509CertificateVerify.getSubjectDN());
                                sSLTransport.a(stringBuffer4.toString());
                            } catch (OCSPException unused) {
                                x509Certificate = x509CertificateVerify;
                                z = false;
                            }
                        }
                        x509Certificate = x509CertificateVerify;
                        z = true;
                    } catch (Exception e) {
                        StringBuffer stringBuffer5 = new StringBuffer("Error verifying OCSP response: ");
                        stringBuffer5.append(e.toString());
                        String string4 = stringBuffer5.toString();
                        if (z3) {
                            sSLTransport.a(string4);
                        }
                        throw new SSLCertificateRuntimeException(new SSLCertificateException(string4, 2, 113, false, e));
                    }
                } catch (OCSPException unused2) {
                    x509CertificateVerify = null;
                }
                if (z) {
                    if (!x509Certificate.equals(x509CertificateConvertCertificate)) {
                        zVerifyChain = verifyDelegatedResponderCert(x509Certificate, basicOCSPResponse.getCertificates(), basicOCSPResponse.getResponderID(), x509CertificateConvertCertificate, sSLTransport);
                    }
                } else {
                    basicOCSPResponse.verify(x509CertificateConvertCertificate.getPublicKey());
                }
                if (!zVerifyChain) {
                    return zVerifyChain;
                }
                try {
                    SingleResponse singleResponse = basicOCSPResponse.getSingleResponse(x509CertificateConvertCertificate2, x509CertificateConvertCertificate, null);
                    if (singleResponse == null) {
                        if (z3) {
                            sSLTransport.a("Cannot get single response for server certificate!");
                        }
                        throw new SSLCertificateRuntimeException(new SSLCertificateException("Cannot get single response for server certificate!", 2, 113, false, null));
                    }
                    CertStatus certStatus = singleResponse.getCertStatus();
                    if (z3) {
                        StringBuffer stringBuffer6 = new StringBuffer("OCSP cert status for server certificate: ");
                        stringBuffer6.append(certStatus.toString());
                        sSLTransport.a(stringBuffer6.toString());
                    }
                    if (certStatus.getCertStatus() != 0) {
                        return false;
                    }
                    Date date = new Date();
                    Date nextUpdate = singleResponse.getNextUpdate();
                    if (nextUpdate != null && nextUpdate.before(date)) {
                        if (nextUpdate.getTime() < date.getTime() - this.b) {
                            if (z3) {
                                sSLTransport.a("There must be more recent status information available!");
                            }
                            throw new SSLCertificateRuntimeException(new SSLCertificateException("There must be more recent status information available!", 2, 113, false, null));
                        }
                    }
                    Date thisUpdate = singleResponse.getThisUpdate();
                    if (thisUpdate == null) {
                        if (z3) {
                            sSLTransport.a("Missing thisUpdate information in OCSP response!");
                        }
                        throw new SSLCertificateRuntimeException(new SSLCertificateException("Missing thisUpdate information in OCSP response!", 2, 113, false, null));
                    }
                    if (thisUpdate.after(date)) {
                        if (thisUpdate.getTime() > date.getTime() + this.b) {
                            StringBuffer stringBuffer7 = new StringBuffer("Response yet not valid! thisUpdate (");
                            stringBuffer7.append(thisUpdate);
                            stringBuffer7.append(") is somewhere in future (current date is: ");
                            stringBuffer7.append(date);
                            stringBuffer7.append(")!");
                            String string5 = stringBuffer7.toString();
                            if (z3) {
                                sSLTransport.a(string5);
                            }
                            throw new SSLCertificateRuntimeException(new SSLCertificateException(string5, 2, 113, false, null));
                        }
                    }
                    try {
                        OCSPStatusRequest oCSPStatusRequest = new OCSPStatusRequest(new ab(bArr));
                        ResponderID[] responderIDs = oCSPStatusRequest.getResponderIDs();
                        OCSPExtensions extensions = oCSPStatusRequest.getExtensions();
                        if (responderIDs != null && responderIDs.length > 0) {
                            ResponderID responderID = basicOCSPResponse.getResponderID();
                            int i2 = 0;
                            while (true) {
                                if (i2 >= responderIDs.length) {
                                    break;
                                }
                                if (responderIDs[i2].equals(responderID)) {
                                    z2 = true;
                                    break;
                                }
                                i2++;
                            }
                            if (!z2) {
                                StringBuffer stringBuffer8 = new StringBuffer("OCSP response signed from unexpected responder ");
                                stringBuffer8.append(responderID);
                                String string6 = stringBuffer8.toString();
                                if (z3) {
                                    sSLTransport.a(string6);
                                }
                                throw new SSLCertificateRuntimeException(new SSLCertificateException(string6, 2, 80, false, null));
                            }
                        }
                        if (extensions == null || !extensions.hasExtensions()) {
                            return zVerifyChain;
                        }
                        try {
                            a(extensions, basicOCSPResponse);
                            return zVerifyChain;
                        } catch (SSLException e2) {
                            if (z3) {
                                sSLTransport.a(e2.getMessage());
                            }
                            throw new SSLCertificateRuntimeException(e2);
                        }
                    } catch (IOException e3) {
                        StringBuffer stringBuffer9 = new StringBuffer("Error decoding OCSP status request message: ");
                        stringBuffer9.append(e3.toString());
                        String string7 = stringBuffer9.toString();
                        if (z3) {
                            sSLTransport.a(string7);
                        }
                        throw new SSLCertificateRuntimeException(new SSLCertificateException(string7, 2, 80, false, e3));
                    }
                } catch (OCSPException e4) {
                    StringBuffer stringBuffer10 = new StringBuffer("Cannot get single response for server certificate: ");
                    stringBuffer10.append(e4.toString());
                    String string8 = stringBuffer10.toString();
                    if (z3) {
                        sSLTransport.a(string8);
                    }
                    throw new SSLCertificateRuntimeException(new SSLCertificateException(string8, 2, 113, false, e4));
                }
            } catch (CertificateException e5) {
                StringBuffer stringBuffer11 = new StringBuffer("Cannot check trust status of responder cert. Conversion error: ");
                stringBuffer11.append(e5.toString());
                String string9 = stringBuffer11.toString();
                if (z3) {
                    sSLTransport.a(string9);
                }
                throw new SSLCertificateRuntimeException(new SSLCertificateException(string9, 2, 80, false, e5));
            }
        } catch (Exception e6) {
            if (z3) {
                StringBuffer stringBuffer12 = new StringBuffer("Error decoding OCSP response: ");
                stringBuffer12.append(e6.toString());
                sSLTransport.a(stringBuffer12.toString());
            }
            StringBuffer stringBuffer13 = new StringBuffer("Cannot decode OCSP response: ");
            stringBuffer13.append(e6.toString());
            throw new SSLCertificateRuntimeException(new SSLCertificateException(stringBuffer13.toString(), 2, 113, false, e6));
        }
    }

    public void setTrustedResponders(TrustedResponders trustedResponders) {
        this.a = trustedResponders;
    }

    public void setAccuracy(long j) {
        this.b = j;
    }

    protected boolean verifyDelegatedResponderCert(iaik.x509.X509Certificate x509Certificate, iaik.x509.X509Certificate[] x509CertificateArr, ResponderID responderID, iaik.x509.X509Certificate x509Certificate2, SSLTransport sSLTransport) throws SSLCertificateException {
        boolean z = true;
        boolean z2 = sSLTransport.b != null;
        try {
            ExtendedKeyUsage extendedKeyUsage = (ExtendedKeyUsage) x509Certificate.getExtension(ExtendedKeyUsage.oid);
            if (extendedKeyUsage != null) {
                for (ObjectID objectID : extendedKeyUsage.getKeyPurposeIDs()) {
                    if (objectID.equals(ExtendedKeyUsage.ocspSigning)) {
                        break;
                    }
                }
                z = false;
            } else {
                z = false;
            }
            TrustedResponders trustedResponders = this.a;
            if (trustedResponders != null) {
                if (!trustedResponders.isTrustedResponder(responderID, x509Certificate, x509Certificate2)) {
                    if (z2) {
                        sSLTransport.a("Responder cert not trusted!");
                    }
                    throw new SSLCertificateRuntimeException(new SSLCertificateException("Responder cert not trusted!", 2, 113, false, null));
                }
                if (!z) {
                    if (z2) {
                        sSLTransport.a("OCSP responder cert not authorized for OCSP signing!");
                    }
                    throw new SSLCertificateRuntimeException(new SSLCertificateException("OCSP responder cert not authorized for OCSP signing!", 2, 113, false, null));
                }
            } else if (z) {
                if (x509Certificate.getIssuerDN().equals(x509Certificate2.getSubjectDN())) {
                    if (z2) {
                        sSLTransport.a("WARNING: Responder authorized by target cert issuer, but no trust information available!");
                    }
                } else if (z2) {
                    sSLTransport.a("WARNING: Responder cert has ocspSigning ExtendedKeyUsage, but is not issued by target cert issuer!");
                }
            } else if (z2) {
                sSLTransport.a("WARNING: Responder not equal to target cert issuer and not authorized for OCSP signing!");
            }
            if (z2) {
                sSLTransport.a("Verify certificate chain of OCSP responder...");
            }
            iaik.x509.X509Certificate[] x509CertificateArrArrangeCertificateChain = Util.arrangeCertificateChain(x509CertificateArr, false);
            if (x509CertificateArrArrangeCertificateChain == null) {
                return false;
            }
            return super.a(x509CertificateArrArrangeCertificateChain, sSLTransport, false);
        } catch (X509ExtensionInitException e) {
            StringBuffer stringBuffer = new StringBuffer("Cannot check extended key usage of responder cert: ");
            stringBuffer.append(e.toString());
            String string = stringBuffer.toString();
            if (z2) {
                sSLTransport.a(string);
            }
            throw new SSLCertificateRuntimeException(new SSLCertificateException(string, 2, 113, false, e));
        }
    }
}

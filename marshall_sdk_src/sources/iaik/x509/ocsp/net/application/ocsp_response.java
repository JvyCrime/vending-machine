package iaik.x509.ocsp.net.application;

import iaik.utils.ASN1InputStream;
import iaik.x509.ocsp.OCSPRequest;
import iaik.x509.ocsp.OCSPResponse;
import iaik.x509.ocsp.UnknownResponseException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ContentHandler;
import java.net.URLConnection;

/* JADX INFO: loaded from: classes2.dex */
public class ocsp_response extends ContentHandler {
    @Override // java.net.ContentHandler
    public Object getContent(URLConnection uRLConnection) throws IOException {
        String contentType = uRLConnection.getContentType();
        ASN1InputStream aSN1InputStream = new ASN1InputStream(new BufferedInputStream(uRLConnection.getInputStream()));
        try {
            try {
                if (contentType.equalsIgnoreCase("application/ocsp-request")) {
                    return new OCSPRequest(aSN1InputStream);
                }
                if (contentType.equalsIgnoreCase("application/ocsp-response")) {
                    OCSPResponse oCSPResponse = new OCSPResponse(aSN1InputStream);
                    try {
                        aSN1InputStream.close();
                    } catch (IOException unused) {
                    }
                    return oCSPResponse;
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unsupported OCSP Type: ");
                stringBuffer.append(contentType);
                throw new IOException(stringBuffer.toString());
            } catch (UnknownResponseException e) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Unknown OCSP response type: ");
                stringBuffer2.append(e.getResponseType());
                throw new IOException(stringBuffer2.toString());
            } catch (IOException e2) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Error in OCSP encoding: ");
                stringBuffer3.append(e2.getMessage());
                throw new IOException(stringBuffer3.toString());
            }
        } finally {
            try {
                aSN1InputStream.close();
            } catch (IOException unused2) {
            }
        }
    }
}

package iaik.x509.ocsp.net;

import iaik.x509.ocsp.net.application.ocsp_response;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;

/* JADX INFO: loaded from: classes2.dex */
public class OCSPContentHandlerFactory implements ContentHandlerFactory {
    @Override // java.net.ContentHandlerFactory
    public ContentHandler createContentHandler(String str) {
        if (str.equalsIgnoreCase("application/ocsp-request") || str.equalsIgnoreCase("application/ocsp-response")) {
            return new ocsp_response();
        }
        return null;
    }
}

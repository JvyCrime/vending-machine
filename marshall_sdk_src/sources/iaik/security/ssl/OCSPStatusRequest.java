package iaik.security.ssl;

import iaik.asn1.DerCoder;
import iaik.asn1.OCTET_STRING;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.ocsp.OCSPException;
import iaik.x509.ocsp.OCSPExtensions;
import iaik.x509.ocsp.ResponderID;
import iaik.x509.ocsp.extensions.Nonce;
import java.io.IOException;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class OCSPStatusRequest {
    public static final int STATUS_TYPE = 1;
    private ResponderID[] a;
    private OCSPExtensions b;

    public OCSPStatusRequest(ResponderID[] responderIDArr, OCSPExtensions oCSPExtensions) {
        this.a = responderIDArr;
        this.b = oCSPExtensions;
    }

    public OCSPStatusRequest(ResponderID[] responderIDArr, byte[] bArr) throws OCSPException {
        this.a = responderIDArr;
        if (bArr != null) {
            try {
                Nonce nonce = new Nonce(DerCoder.encode(new OCTET_STRING(bArr)));
                OCSPExtensions oCSPExtensions = new OCSPExtensions();
                this.b = oCSPExtensions;
                oCSPExtensions.addExtension(nonce);
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("Error creating Nonce extension: ");
                stringBuffer.append(e.toString());
                throw new OCSPException(stringBuffer.toString());
            }
        }
    }

    public OCSPStatusRequest(byte[] bArr) throws IOException {
        a(new ab(bArr));
    }

    OCSPStatusRequest(ab abVar) throws IOException {
        a(abVar);
    }

    public ResponderID[] getResponderIDs() {
        return this.a;
    }

    public OCSPExtensions getExtensions() {
        return this.b;
    }

    public byte[] getNonce() {
        byte[] wrappedNonce = getWrappedNonce();
        if (wrappedNonce == null) {
            return wrappedNonce;
        }
        try {
            byte[] bArr = (byte[]) DerCoder.decode(wrappedNonce).getValue();
            return bArr != null ? bArr : wrappedNonce;
        } catch (Exception unused) {
            return wrappedNonce;
        }
    }

    public byte[] getWrappedNonce() {
        Nonce nonce;
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions == null) {
            return null;
        }
        try {
            nonce = (Nonce) oCSPExtensions.getExtension(Nonce.oid);
        } catch (X509ExtensionInitException unused) {
            nonce = null;
        }
        if (nonce != null) {
            return nonce.getValue();
        }
        return null;
    }

    public byte[] getEncoded() throws IOException {
        v vVar = new v(1024);
        a(vVar);
        return vVar.toByteArray();
    }

    void a(ab abVar) throws IOException {
        int iF = abVar.f();
        Vector vector = new Vector();
        while (iF > 0) {
            try {
                byte[] bArrG = abVar.g();
                vector.addElement(new ResponderID(DerCoder.decode(bArrG)));
                ResponderID[] responderIDArr = new ResponderID[vector.size()];
                this.a = responderIDArr;
                vector.copyInto(responderIDArr);
                iF -= bArrG.length + 2;
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("Error decoding ocsp responder ids: ");
                stringBuffer.append(e.toString());
                throw new SSLException(stringBuffer.toString(), 2, 50, false);
            }
        }
        byte[] bArrG2 = abVar.g();
        if (bArrG2 == null || bArrG2.length <= 0) {
            return;
        }
        bArrG2[0] = 48;
        if (bArrG2.length > 0) {
            try {
                this.b = new OCSPExtensions(DerCoder.decode(bArrG2));
            } catch (Exception e2) {
                StringBuffer stringBuffer2 = new StringBuffer("Error decoding ocsp request extensions: ");
                stringBuffer2.append(e2.toString());
                throw new SSLException(stringBuffer2.toString(), 2, 50, false);
            }
        }
    }

    void a(v vVar) throws IOException {
        int length;
        int size = vVar.size();
        vVar.write(v.a);
        ResponderID[] responderIDArr = this.a;
        if (responderIDArr != null && (length = responderIDArr.length) > 0) {
            for (int i = 0; i < length; i++) {
                try {
                    vVar.a(DerCoder.encode(this.a[i].toASN1Object()));
                } catch (Exception e) {
                    StringBuffer stringBuffer = new StringBuffer("Error encoding ocsp responder id: ");
                    stringBuffer.append(e.toString());
                    throw new SSLException(stringBuffer.toString(), 2, 80, false);
                }
            }
            vVar.b((vVar.size() - size) - 2, size);
        }
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions == null || !oCSPExtensions.hasExtensions()) {
            vVar.a(0);
            return;
        }
        try {
            vVar.a(DerCoder.encode(this.b.toASN1Object()));
        } catch (Exception e2) {
            StringBuffer stringBuffer2 = new StringBuffer("Error encoding ocsp request extensions: ");
            stringBuffer2.append(e2.toString());
            throw new SSLException(stringBuffer2.toString(), 2, 80, false);
        }
    }
}

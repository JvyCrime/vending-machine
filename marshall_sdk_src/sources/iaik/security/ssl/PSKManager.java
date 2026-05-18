package iaik.security.ssl;

import com.felhr.usbserial.UsbSerialDebugger;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public abstract class PSKManager {
    private static PSKManager a = new DefaultPSKManager();

    public abstract void addPSKCredential(PSKCredential pSKCredential);

    public abstract Enumeration getAll();

    public abstract PSKCredential getPSKCredential(String str, SSLTransport sSLTransport) throws SSLException;

    public abstract void removeAll();

    public abstract PSKCredential removePSKCredential(PSKCredential pSKCredential);

    public abstract PSKCredential removePSKCredential(String str);

    public abstract PSKCredential removePSKCredentialWithRemotePeerId(Object obj);

    public abstract int size();

    public static PSKManager getDefault() {
        return a;
    }

    public static void setDefault(PSKManager pSKManager) {
        a = pSKManager;
    }

    protected PSKManager() {
    }

    public PSKCredential removePSKCredential(byte[] bArr) throws UnsupportedEncodingException {
        PSKCredential pSKCredentialRemovePSKCredential;
        try {
            if (bArr == null) {
                return null;
            }
            try {
                pSKCredentialRemovePSKCredential = removePSKCredential(new String(bArr, "UTF8"));
            } catch (UnsupportedEncodingException unused) {
                pSKCredentialRemovePSKCredential = removePSKCredential(new String(bArr, UsbSerialDebugger.ENCODING));
            }
            return pSKCredentialRemovePSKCredential;
        } catch (Exception e) {
            if (e instanceof UnsupportedEncodingException) {
                throw ((UnsupportedEncodingException) e);
            }
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 decode identity: ");
            stringBuffer.append(e.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public PSKCredential getPSKCredential(byte[] bArr, SSLTransport sSLTransport) throws SSLException {
        String str;
        try {
            if (bArr != null) {
                try {
                    str = new String(bArr, "UTF8");
                } catch (UnsupportedEncodingException unused) {
                    str = new String(bArr, UsbSerialDebugger.ENCODING);
                }
            } else {
                str = null;
            }
            return getPSKCredential(str, sSLTransport);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 decode identity hint: ");
            stringBuffer.append(e.toString());
            throw new SSLException(stringBuffer.toString());
        }
    }
}

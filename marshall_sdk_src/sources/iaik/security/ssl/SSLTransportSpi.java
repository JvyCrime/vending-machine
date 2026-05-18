package iaik.security.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

/* JADX INFO: loaded from: classes.dex */
public abstract class SSLTransportSpi {
    protected SSLContext context;
    protected boolean useClientMode;

    protected abstract void engineClose() throws IOException;

    protected abstract InputStream engineGetInputStream() throws IOException;

    protected abstract OutputStream engineGetOutputStream() throws IOException;

    protected InetAddress engineGetRemoteInetAddress() {
        return null;
    }

    protected Object engineGetRemotePeerId() {
        return null;
    }

    protected String engineGetRemotePeerName() {
        return null;
    }

    protected SSLTransportSpi(SSLContext sSLContext, boolean z) {
        this.context = sSLContext;
        this.useClientMode = z;
    }

    protected SSLTransportSpi() {
    }

    protected SSLContext engineGetContext() {
        return this.context;
    }

    protected boolean engineGetUseClientMode() {
        return this.useClientMode;
    }
}

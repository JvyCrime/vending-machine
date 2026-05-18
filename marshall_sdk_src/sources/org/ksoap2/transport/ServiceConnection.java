package org.ksoap2.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface ServiceConnection {
    public static final int DEFAULT_TIMEOUT = 20000;

    void connect() throws IOException;

    void disconnect() throws IOException;

    InputStream getErrorStream();

    String getHost();

    String getPath();

    int getPort();

    List getResponseProperties() throws IOException;

    InputStream openInputStream() throws IOException;

    OutputStream openOutputStream() throws IOException;

    void setRequestMethod(String str) throws IOException;

    void setRequestProperty(String str, String str2) throws IOException;
}

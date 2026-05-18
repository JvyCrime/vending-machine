package iaik.x509.ocsp.net;

import iaik.asn1.CodingException;
import iaik.utils.ASN1InputStream;
import iaik.utils.Util;
import iaik.x509.ocsp.OCSPRequest;
import iaik.x509.ocsp.OCSPResponse;
import iaik.x509.ocsp.UnknownResponseException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class HttpOCSPRequest {
    HttpURLConnection a;
    private URL b;
    private OCSPResponse c;
    private String d;

    public HttpOCSPRequest(URL url) {
        this.b = url;
    }

    private int a(byte[] bArr) throws IOException, UnknownResponseException {
        InputStream inputStream = null;
        this.d = null;
        this.c = null;
        HttpURLConnection httpURLConnection = (HttpURLConnection) this.b.openConnection();
        this.a = httpURLConnection;
        httpURLConnection.setRequestProperty("Content-Type", "application/ocsp-request");
        this.a.setRequestProperty("Accept", "application/ocsp-response");
        this.a.setRequestProperty("Content-Length", String.valueOf(bArr.length));
        this.a.setDoOutput(true);
        OutputStream outputStream = this.a.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write(bArr);
        bufferedOutputStream.flush();
        int responseCode = this.a.getResponseCode();
        this.d = this.a.getResponseMessage();
        try {
            if (responseCode / 100 == 2) {
                String contentType = this.a.getContentType();
                if (contentType != null && !contentType.toLowerCase(Locale.US).startsWith("application/ocsp-response")) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Got response with invalid content type: ");
                    stringBuffer.append(contentType);
                    throw new IOException(stringBuffer.toString());
                }
                inputStream = this.a.getInputStream();
                this.c = new OCSPResponse(new ASN1InputStream(new BufferedInputStream(inputStream)));
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException unused) {
                }
            }
            try {
                bufferedOutputStream.close();
            } catch (IOException unused2) {
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused3) {
                }
            }
            return responseCode;
        } finally {
        }
    }

    public String getHeaderField(int i) {
        HttpURLConnection httpURLConnection = this.a;
        if (httpURLConnection == null) {
            return null;
        }
        return httpURLConnection.getHeaderField(i);
    }

    public String getHeaderField(String str) {
        HttpURLConnection httpURLConnection = this.a;
        if (httpURLConnection == null) {
            return null;
        }
        return httpURLConnection.getHeaderField(str);
    }

    public String getHeaderFieldKey(int i) {
        HttpURLConnection httpURLConnection = this.a;
        if (httpURLConnection == null) {
            return null;
        }
        return httpURLConnection.getHeaderFieldKey(i);
    }

    public OCSPResponse getOCSPResponse() {
        return this.c;
    }

    public String getResponseMessage() {
        return this.d;
    }

    public int postRequest(OCSPRequest oCSPRequest) throws IOException, UnknownResponseException {
        try {
            return a(oCSPRequest.getEncoded());
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Request encoding error: ");
            stringBuffer.append(e.getMessage());
            throw new IOException(stringBuffer.toString());
        }
    }

    public int sendGETRequest(OCSPRequest oCSPRequest) throws IOException, UnknownResponseException {
        InputStream inputStream = null;
        this.d = null;
        this.c = null;
        try {
            byte[] encoded = oCSPRequest.getEncoded();
            if (encoded.length < 250) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(this.b.toExternalForm());
                stringBuffer.append("/");
                stringBuffer.append(URLEncoder.encode(Util.toBase64String(encoded)));
                String string = stringBuffer.toString();
                if (string.getBytes().length < 255) {
                    URL url = new URL(string);
                    this.b = url;
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    this.a = httpURLConnection;
                    httpURLConnection.setRequestProperty("Accept", "application/ocsp-response");
                    int responseCode = this.a.getResponseCode();
                    this.d = this.a.getResponseMessage();
                    try {
                        if (responseCode / 100 == 2) {
                            String contentType = this.a.getContentType();
                            if (!"application/ocsp-response".equalsIgnoreCase(contentType)) {
                                StringBuffer stringBuffer2 = new StringBuffer();
                                stringBuffer2.append("Got response with invalid content type: ");
                                stringBuffer2.append(contentType);
                                throw new IOException(stringBuffer2.toString());
                            }
                            inputStream = this.a.getInputStream();
                            this.c = new OCSPResponse(new ASN1InputStream(new BufferedInputStream(inputStream)));
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        return responseCode;
                    } catch (Throwable th) {
                        if (0 != 0) {
                            try {
                                inputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                }
            }
            return a(encoded);
        } catch (CodingException e) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Request encoding error: ");
            stringBuffer3.append(e.getMessage());
            throw new IOException(stringBuffer3.toString());
        }
    }
}

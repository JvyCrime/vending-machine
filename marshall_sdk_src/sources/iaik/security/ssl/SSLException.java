package iaik.security.ssl;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class SSLException extends IOException {
    public static final int ALERT_ACCESS_DENIED = 49;
    public static final int ALERT_BAD_CERTIFICATE = 42;
    public static final int ALERT_BAD_CERTIFICATE_HASH_VALUE = 114;
    public static final int ALERT_BAD_CERTIFICATE_STATUS_RESPONSE = 113;
    public static final int ALERT_BAD_RECORD_MAC = 20;
    public static final int ALERT_CERTIFICATE_EXPIRED = 45;
    public static final int ALERT_CERTIFICATE_REVOKED = 44;
    public static final int ALERT_CERTIFICATE_UNKNOWN = 46;
    public static final int ALERT_CERTIFICATE_UNOBTAINABLE = 111;
    public static final int ALERT_CLOSE_NOTIFY = 0;
    public static final int ALERT_DECODE_ERROR = 50;
    public static final int ALERT_DECOMPRESSION_FAILURE = 30;
    public static final int ALERT_DECRYPTION_FAILED = 21;
    public static final int ALERT_DECRYPT_ERROR = 51;
    public static final int ALERT_EXPORT_RESTRICTION = 60;
    public static final int ALERT_HANDSHAKE_FAILURE = 40;
    public static final int ALERT_ILLEGAL_PARAMETER = 47;
    public static final int ALERT_INSUFFICIENT_SECURITY = 71;
    public static final int ALERT_INTERNAL_ERROR = 80;
    public static final int ALERT_LEVEL_FATAL = 2;
    public static final int ALERT_LEVEL_WARNING = 1;
    public static final int ALERT_NO_CERTIFICATE = 41;
    public static final int ALERT_NO_RENEGOTIATION = 100;
    public static final int ALERT_PROTOCOL_VERSION = 70;
    public static final int ALERT_RECORD_OVERFLOW = 22;
    public static final int ALERT_UNEXPECTED_MESSAGE = 10;
    public static final int ALERT_UNKNOWN_CA = 48;
    public static final int ALERT_UNKNOWN_PSK_IDENTITY = 115;
    public static final int ALERT_UNRECOGNIZED_NAME = 112;
    public static final int ALERT_UNSUPPORTED_CERTIFICATE = 43;
    public static final int ALERT_UNSUPPORTED_EXTENSION = 110;
    public static final int ALERT_USER_CANCELED = 90;
    public static final int ALERT_V2_BAD_CERTIFICATE = 4;
    public static final int ALERT_V2_NO_CERTIFICATE = 2;
    public static final int ALERT_V2_NO_CIPHER = 1;
    public static final int ALERT_V2_UNDEFINED_ERROR = 0;
    public static final int ALERT_V2_UNSUPPORTED_CERTIFICATE_TYPE = 6;
    private int a;
    private int b;
    private boolean c;
    private b d;

    SSLException() {
        this.a = -1;
        this.b = -1;
        this.c = false;
    }

    public SSLException(String str) {
        this(str, null);
    }

    SSLException(String str, b bVar) {
        super(str);
        this.d = bVar;
        if (bVar != null) {
            this.a = bVar.b();
            this.b = bVar.a();
            this.c = true;
        } else {
            this.a = -1;
            this.b = -1;
            this.c = false;
        }
    }

    SSLException(String str, int i, int i2, boolean z) {
        super(str);
        this.a = i;
        this.b = i2;
        this.c = z;
    }

    b a() {
        return this.d;
    }

    public int getAlertDescription() {
        return this.b;
    }

    public int getAlertLevel() {
        return this.a;
    }

    public boolean alertFromPeer() {
        return this.c;
    }

    void a(int i, int i2, boolean z) {
        this.a = i;
        this.b = i2;
        this.c = z;
    }
}

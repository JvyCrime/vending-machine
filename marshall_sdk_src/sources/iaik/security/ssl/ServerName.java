package iaik.security.ssl;

import com.bitmick.marshall.vmc.marshall_t;
import com.felhr.usbserial.UsbSerialDebugger;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.StringTokenizer;

/* JADX INFO: loaded from: classes.dex */
public class ServerName implements Cloneable {
    public static final int HOST_NAME = 0;
    private static final byte[] a = {marshall_t.status_vpos_please_use_mag, marshall_t.status_vpos_try_again, 70, marshall_t.status_vpos_processing_error, 85, 76, marshall_t.status_vpos_insert_or_swipe_another_card, 95, 67, marshall_t.status_vpos_see_phone_for_instructions, marshall_t.status_vpos_try_again, marshall_t.status_vpos_please_use_mag, marshall_t.status_vpos_try_again, marshall_t.status_vpos_try_another_card, marshall_t.status_vpos_insert_or_swipe_another_card, 73, marshall_t.status_vpos_processing_error, 76};
    private int b;
    private String c;
    private byte[] d;

    static ServerName a() {
        ServerName serverName = new ServerName(a);
        serverName.c = "DEFAULT_CREDENTIAL";
        return serverName;
    }

    private static boolean a(String str) {
        try {
            boolean z = true;
            if (str.indexOf(".") != -1) {
                StringTokenizer stringTokenizer = new StringTokenizer(str, "./");
                int iCountTokens = stringTokenizer.countTokens();
                if (iCountTokens != 4 && iCountTokens != 8) {
                    z = false;
                }
                byte[] bArr = new byte[iCountTokens];
                int i = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    int i2 = Integer.parseInt(stringTokenizer.nextToken());
                    if (i2 < 0 || i2 > 255) {
                        z = false;
                    }
                    bArr[i] = (byte) i2;
                    i++;
                }
            } else {
                StringTokenizer stringTokenizer2 = new StringTokenizer(str, ":/");
                int iCountTokens2 = stringTokenizer2.countTokens();
                if (iCountTokens2 != 8 && iCountTokens2 != 16) {
                    z = false;
                }
                byte[] bArr2 = new byte[iCountTokens2 * 2];
                int i3 = 0;
                while (stringTokenizer2.hasMoreTokens()) {
                    int i4 = Integer.parseInt(stringTokenizer2.nextToken(), 16);
                    if (i4 < 0 || i4 > 65535) {
                        z = false;
                    }
                    int i5 = i3 + 1;
                    bArr2[i3] = (byte) (i4 >> 8);
                    i3 = i5 + 1;
                    bArr2[i5] = (byte) i4;
                }
            }
            return z;
        } catch (Throwable unused) {
            return false;
        }
    }

    public ServerName(byte[] bArr) {
        Objects.requireNonNull(bArr, "encodedHostName must not be null!");
        if (bArr.length > 65535) {
            throw new IllegalArgumentException("Encoded name to long. Must be <= 2^16-1");
        }
        this.b = 0;
        this.d = bArr;
    }

    public ServerName(String str) throws UnsupportedEncodingException {
        Objects.requireNonNull(str, "hostName must not be null!");
        if (a(str)) {
            throw new IllegalArgumentException("IP Address not allowed for Server Name!");
        }
        this.b = 0;
        this.c = str;
        getEncodedName();
        if (this.d.length > 65535) {
            throw new IllegalArgumentException("Encoded name to long. Must be <= 2^16-1");
        }
    }

    public ServerName(int i, String str, byte[] bArr) throws UnsupportedEncodingException {
        this(i, str, bArr, true);
    }

    public ServerName(int i, String str, byte[] bArr, boolean z) throws UnsupportedEncodingException {
        if (i < 0 || i > 255) {
            StringBuffer stringBuffer = new StringBuffer("Type (");
            stringBuffer.append(i);
            stringBuffer.append(") out of range. Must be between 0 and 255");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (str == null && bArr == null) {
            throw new IllegalArgumentException("Both name and encoded name must not be null!");
        }
        if (i == 0 && str != null && z && a(str)) {
            throw new IllegalArgumentException("IP Address not allowed for Server Name!");
        }
        if (bArr == null) {
            this.c = str;
            getEncodedName();
        } else {
            this.d = bArr;
            this.c = null;
        }
        if (this.d.length > 65535) {
            throw new IllegalArgumentException("Encoded name to long. Must be <= 2^16-1");
        }
        this.b = i;
    }

    public int getType() {
        return this.b;
    }

    public boolean isTypeSupported() {
        return this.b == 0;
    }

    public String getTypeAsString() {
        return this.b == 0 ? "HostName" : "Unknown";
    }

    public String getName() throws UnsupportedEncodingException {
        try {
            if (this.c == null) {
                try {
                    this.c = new String(this.d, "UTF8");
                } catch (UnsupportedEncodingException unused) {
                    this.c = new String(this.d, "UTF8");
                }
            }
            return this.c;
        } catch (Throwable th) {
            if (th instanceof UnsupportedEncodingException) {
                throw th;
            }
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 decode name: ");
            stringBuffer.append(th.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public byte[] getEncodedName() throws UnsupportedEncodingException {
        if (this.d == null) {
            try {
                String strSubstring = this.c;
                if (strSubstring.endsWith(".")) {
                    strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
                }
                try {
                    this.d = strSubstring.getBytes("UTF8");
                } catch (UnsupportedEncodingException unused) {
                    this.d = strSubstring.getBytes(UsbSerialDebugger.ENCODING);
                }
            } catch (Throwable th) {
                if (th instanceof UnsupportedEncodingException) {
                    throw th;
                }
                StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 encode name: ");
                stringBuffer.append(th.toString());
                throw new UnsupportedEncodingException(stringBuffer.toString());
            }
        }
        return this.d;
    }

    public int hashCode() {
        int i = this.b;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.d;
            if (i2 >= bArr.length) {
                return i;
            }
            int i3 = i + (bArr[i2] & 255);
            i = (i3 >> 29) | (i3 << 3);
            i2++;
        }
    }

    public boolean equals(Object obj) {
        byte[] bArr;
        String str;
        if (this == obj) {
            return true;
        }
        if (obj instanceof ServerName) {
            ServerName serverName = (ServerName) obj;
            if (this.b == serverName.b) {
                try {
                    getName();
                    serverName.getName();
                } catch (Exception unused) {
                }
                String str2 = this.c;
                boolean z = (str2 == null || (str = serverName.c) == null || !str2.equalsIgnoreCase(str)) ? false : true;
                if (z) {
                    return z;
                }
                try {
                    getEncodedName();
                    serverName.getEncodedName();
                } catch (Exception unused2) {
                }
                byte[] bArr2 = this.d;
                return (bArr2 == null || (bArr = serverName.d) == null) ? z : Utils.equalsBlock(bArr2, bArr);
            }
        }
        return false;
    }

    public Object clone() {
        try {
            ServerName serverName = (ServerName) super.clone();
            try {
                serverName.b = this.b;
                byte[] bArr = this.d;
                if (bArr != null) {
                    serverName.d = (byte[]) bArr.clone();
                }
                serverName.c = this.c;
                return serverName;
            } catch (CloneNotSupportedException unused) {
                return serverName;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getTypeAsString());
        stringBuffer.append(" (");
        stringBuffer.append(this.b);
        stringBuffer.append("): ");
        try {
            getName();
        } catch (UnsupportedEncodingException unused) {
        }
        String str = this.c;
        if (str != null) {
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }
}

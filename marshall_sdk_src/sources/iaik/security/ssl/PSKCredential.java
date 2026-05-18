package iaik.security.ssl;

import com.felhr.usbserial.UsbSerialDebugger;
import java.io.UnsupportedEncodingException;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
public class PSKCredential implements Cloneable {
    private byte[] a;
    private String b;
    private byte[] c;
    private String d;
    private Object e;
    private SecretKey f;

    public PSKCredential(byte[] bArr, SecretKey secretKey) throws IllegalArgumentException, UnsupportedEncodingException {
        if (secretKey == null) {
            throw new IllegalArgumentException("Pre-shared key must not be null!");
        }
        if (bArr == null) {
            throw new IllegalArgumentException("Identity must not be null!");
        }
        if (bArr.length > 65535) {
            throw new IllegalArgumentException("Identity too long!");
        }
        this.a = bArr;
        try {
            try {
                this.b = new String(this.a, "UTF8");
            } catch (UnsupportedEncodingException unused) {
                this.b = new String(this.a, UsbSerialDebugger.ENCODING);
            }
            this.f = secretKey;
        } catch (Throwable th) {
            if (th instanceof UnsupportedEncodingException) {
                throw th;
            }
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 decode identity: ");
            stringBuffer.append(th.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public PSKCredential(String str, SecretKey secretKey) throws IllegalArgumentException, UnsupportedEncodingException {
        if (secretKey == null) {
            throw new IllegalArgumentException("Pre-shared key must not be null!");
        }
        try {
            if (str == null) {
                throw new IllegalArgumentException("Identity must not be null!");
            }
            try {
                this.a = str.getBytes("UTF8");
            } catch (UnsupportedEncodingException unused) {
                this.a = str.getBytes(UsbSerialDebugger.ENCODING);
            }
            if (this.a.length > 65535) {
                this.a = null;
                throw new IllegalArgumentException("Identity too long!");
            }
            this.b = str;
            this.f = secretKey;
        } catch (Throwable th) {
            if (th instanceof UnsupportedEncodingException) {
                throw th;
            }
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 encode identity: ");
            stringBuffer.append(th.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public void setIdentityHint(byte[] bArr) throws IllegalArgumentException, UnsupportedEncodingException {
        if (bArr == null) {
            this.c = null;
            this.d = null;
            return;
        }
        if (bArr.length > 65535) {
            throw new IllegalArgumentException("Identity hint too long!");
        }
        this.c = bArr;
        try {
            try {
                this.d = new String(this.c, "UTF8");
            } catch (UnsupportedEncodingException unused) {
                this.d = new String(this.c, UsbSerialDebugger.ENCODING);
            }
        } catch (Throwable th) {
            if (th instanceof UnsupportedEncodingException) {
                throw th;
            }
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 decode identity hint: ");
            stringBuffer.append(th.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public void setIdentityHint(String str) throws IllegalArgumentException, UnsupportedEncodingException {
        if (str == null) {
            this.c = null;
            this.d = null;
            return;
        }
        try {
            try {
                this.c = str.getBytes("UTF8");
            } catch (UnsupportedEncodingException unused) {
                this.c = str.getBytes(UsbSerialDebugger.ENCODING);
            }
            if (this.c.length > 65535) {
                this.c = null;
                throw new IllegalArgumentException("Identity Hint too long!");
            }
            this.d = str;
        } catch (Throwable th) {
            if (th instanceof UnsupportedEncodingException) {
                throw th;
            }
            StringBuffer stringBuffer = new StringBuffer("Cannot UTF-8 encode identity hint: ");
            stringBuffer.append(th.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public void setRemotePeerId(Object obj) {
        this.e = obj;
    }

    public SecretKey getPSK() {
        return this.f;
    }

    public byte[] getIdentity() {
        return (byte[]) this.a.clone();
    }

    public String getIdentityString() {
        return this.b;
    }

    public byte[] getIdentityHint() {
        byte[] bArr = this.c;
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }

    public String getIdentityHintString() {
        String str = this.d;
        if (str != null) {
            return str;
        }
        return null;
    }

    public Object getRemotePeerId() {
        return this.e;
    }

    public boolean equals(Object obj) {
        Object obj2;
        byte[] bArr;
        SecretKey secretKey;
        if (this == obj) {
            return true;
        }
        if (obj instanceof PSKCredential) {
            PSKCredential pSKCredential = (PSKCredential) obj;
            boolean zEqualsBlock = Utils.equalsBlock(this.a, pSKCredential.a);
            if (zEqualsBlock) {
                SecretKey secretKey2 = this.f;
                if (secretKey2 != null && (secretKey = pSKCredential.f) != null) {
                    zEqualsBlock = secretKey2.equals(secretKey);
                } else {
                    zEqualsBlock = secretKey2 == null && pSKCredential.f == null;
                }
                if (zEqualsBlock) {
                    byte[] bArr2 = this.c;
                    if (bArr2 != null && (bArr = pSKCredential.c) != null) {
                        zEqualsBlock = Utils.equalsBlock(bArr2, bArr);
                    } else {
                        zEqualsBlock = bArr2 == null && pSKCredential.c == null;
                    }
                    if (zEqualsBlock) {
                        Object obj3 = this.e;
                        if (obj3 != null && (obj2 = pSKCredential.e) != null) {
                            return obj3.equals(obj2);
                        }
                        if (obj3 == null && pSKCredential.e == null) {
                            return true;
                        }
                    }
                }
            }
            return zEqualsBlock;
        }
        return false;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public Object clone() {
        try {
            PSKCredential pSKCredential = (PSKCredential) super.clone();
            pSKCredential.f = this.f;
            pSKCredential.a = this.a;
            pSKCredential.b = this.b;
            pSKCredential.d = this.d;
            byte[] bArr = this.c;
            if (bArr != null) {
                pSKCredential.c = (byte[]) bArr.clone();
            }
            return pSKCredential;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer("Identity: ");
        stringBuffer2.append(this.b);
        stringBuffer.append(stringBuffer2.toString());
        if (this.d != null) {
            StringBuffer stringBuffer3 = new StringBuffer("\nIdentity Hint: ");
            stringBuffer3.append(this.d);
            stringBuffer.append(stringBuffer3.toString());
        }
        if (this.e != null) {
            StringBuffer stringBuffer4 = new StringBuffer("\nRemote Peer Id: ");
            stringBuffer4.append(this.e);
            stringBuffer.append(stringBuffer4.toString());
        }
        if (this.f != null) {
            StringBuffer stringBuffer5 = new StringBuffer("\nDefault psk: ");
            stringBuffer5.append(Utils.toString(this.f.getEncoded()));
            stringBuffer.append(stringBuffer5.toString());
        }
        return stringBuffer.toString();
    }
}

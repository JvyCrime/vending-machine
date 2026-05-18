package iaik.security.ssl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class av extends MessageDigest implements Cloneable {
    private ByteArrayOutputStream a;
    private MessageDigest b;
    private MessageDigest c;
    private int d;

    public av() {
        super("TLS");
        this.a = new ByteArrayOutputStream(4096);
        this.d = 0;
    }

    void a(int i) {
        this.d = i;
        if (i < 771) {
            if (this.b == null) {
                SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
                try {
                    this.b = securityProvider.getMessageDigest(SecurityProvider.ALG_DIGEST_MD5);
                    this.c = securityProvider.getMessageDigest("SHA");
                } catch (Exception e) {
                    StringBuffer stringBuffer = new StringBuffer("No implementation for SHA or MD5: ");
                    stringBuffer.append(e.toString());
                    throw new RuntimeException(stringBuffer.toString());
                }
            }
            byte[] byteArray = this.a.toByteArray();
            if (byteArray.length > 0) {
                this.b.update(byteArray);
                this.c.update(byteArray);
            }
        }
    }

    @Override // java.security.MessageDigestSpi
    protected final void engineUpdate(byte b) {
        int i = this.d;
        if (i == 0 || i >= 771) {
            this.a.write(b);
        } else {
            this.b.update(b);
            this.c.update(b);
        }
    }

    @Override // java.security.MessageDigestSpi
    protected final void engineUpdate(byte[] bArr, int i, int i2) {
        int i3 = this.d;
        if (i3 == 0 || i3 >= 771) {
            this.a.write(bArr, i, i2);
        } else {
            this.b.update(bArr, i, i2);
            this.c.update(bArr, i, i2);
        }
    }

    byte[] a(String str) throws NoSuchAlgorithmException {
        Objects.requireNonNull(str, "digestAlg must not be null!");
        try {
            MessageDigest messageDigest = SecurityProvider.getSecurityProvider().getMessageDigest(str);
            byte[] byteArray = this.a.toByteArray();
            if (byteArray.length == 0) {
                throw new IllegalArgumentException("No data to hash!");
            }
            return messageDigest.digest(byteArray);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("No implementation for digestAlg: ");
            stringBuffer.append(e.toString());
            throw new NoSuchAlgorithmException(stringBuffer.toString());
        }
    }

    @Override // java.security.MessageDigestSpi
    protected byte[] engineDigest() {
        return a(true);
    }

    private byte[] a(boolean z) {
        byte[] byteArray = this.a.toByteArray();
        if (byteArray.length == 0) {
            throw new IllegalArgumentException("No data to hash!");
        }
        if (z) {
            engineReset();
        }
        return byteArray;
    }

    @Override // java.security.MessageDigestSpi
    protected void engineReset() {
        this.a.reset();
        MessageDigest messageDigest = this.b;
        if (messageDigest != null) {
            messageDigest.reset();
            this.c.reset();
        }
        this.d = 0;
    }

    MessageDigest a() throws CloneNotSupportedException {
        MessageDigest messageDigest = this.b;
        if (messageDigest != null) {
            return (MessageDigest) messageDigest.clone();
        }
        return null;
    }

    MessageDigest b() throws CloneNotSupportedException {
        if (this.b != null) {
            return (MessageDigest) this.c.clone();
        }
        return null;
    }

    @Override // java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        try {
            av avVar = (av) super.clone();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.a.toByteArray().length);
                avVar.a = byteArrayOutputStream;
                try {
                    byteArrayOutputStream.write(this.a.toByteArray());
                    MessageDigest messageDigest = this.b;
                    if (messageDigest == null) {
                        return avVar;
                    }
                    avVar.b = (MessageDigest) messageDigest.clone();
                    avVar.c = (MessageDigest) this.c.clone();
                    return avVar;
                } catch (IOException e) {
                    StringBuffer stringBuffer = new StringBuffer("Error copying buffer: ");
                    stringBuffer.append(e.getMessage());
                    throw new RuntimeException(stringBuffer.toString());
                }
            } catch (CloneNotSupportedException unused) {
                return avVar;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }
}

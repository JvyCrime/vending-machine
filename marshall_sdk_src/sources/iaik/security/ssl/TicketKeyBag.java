package iaik.security.ssl;

import com.felhr.usbserial.UsbSerialDebugger;
import iaik.security.rsa.RSAKeyPairGeneratorFIPS;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class TicketKeyBag implements Cloneable {
    static final KeyName a = new KeyName(new byte[]{10});
    private KeyName b;
    private SecretKey c;
    private SecretKey d;
    private long e;
    private long f;

    public static final class KeyName implements Cloneable {
        private byte[] a;

        public KeyName(byte[] bArr) {
            this.a = bArr;
        }

        public byte[] getName() {
            return this.a;
        }

        public boolean equals(Object obj) {
            if (obj instanceof KeyName) {
                return this == obj || Utils.equalsBlock(this.a, ((KeyName) obj).a);
            }
            return false;
        }

        public int hashCode() {
            if (this.a == null) {
                return 0;
            }
            int i = 0;
            for (int i2 = 0; i2 < 4; i2++) {
                byte[] bArr = this.a;
                if (i2 >= bArr.length) {
                    break;
                }
                i ^= (bArr[i2] & 255) << ((i2 % 4) << 3);
            }
            return i;
        }

        public Object clone() {
            try {
                KeyName keyName = (KeyName) super.clone();
                try {
                    keyName.a = (byte[]) this.a.clone();
                    return keyName;
                } catch (CloneNotSupportedException unused) {
                    return keyName;
                }
            } catch (CloneNotSupportedException unused2) {
                return null;
            }
        }

        public String toString() {
            byte[] bArr = this.a;
            return bArr == null ? "" : Utils.toString(bArr);
        }
    }

    private static final Session a(ab abVar, SessionID sessionID) throws IOException {
        String str;
        TicketSession ticketSession = new TicketSession(sessionID, abVar.f());
        ticketSession.a = CipherSuite.a(abVar.f());
        ticketSession.b = CompressionMethod.a(abVar.k());
        byte[] bArr = new byte[48];
        if (abVar.a(bArr) != 48) {
            throw new SSLException("Ticket contains invalid master secret!", 2, 50, false);
        }
        ticketSession.f = bArr;
        int iK = abVar.k();
        if (iK != 0) {
            if (iK == 1) {
                int iH = abVar.h();
                Vector vector = new Vector();
                SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
                int length = 0;
                while (length < iH) {
                    try {
                        byte[] bArrI = abVar.i();
                        length = length + bArrI.length + 3;
                        vector.addElement(securityProvider.getX509Certificate(bArrI));
                    } catch (Exception e) {
                        StringBuffer stringBuffer = new StringBuffer("Error decoding Certificate: ");
                        stringBuffer.append(e);
                        throw new SSLException(stringBuffer.toString());
                    }
                }
                X509Certificate[] x509CertificateArr = new X509Certificate[vector.size()];
                vector.copyInto(x509CertificateArr);
                ticketSession.d = x509CertificateArr;
            } else if (iK == 2) {
                byte[] bArrG = abVar.g();
                try {
                    try {
                        str = new String(bArrG, "UTF8");
                    } catch (UnsupportedEncodingException unused) {
                        str = new String(bArrG, UsbSerialDebugger.ENCODING);
                    }
                    ticketSession.e = str;
                } catch (Throwable th) {
                    StringBuffer stringBuffer2 = new StringBuffer("Cannot UTF-8 decode psk identity: ");
                    stringBuffer2.append(th.toString());
                    throw new SSLException(stringBuffer2.toString(), 2, 80, false);
                }
            } else {
                StringBuffer stringBuffer3 = new StringBuffer("Invalid ticket client auth type: ");
                stringBuffer3.append(iK);
                stringBuffer3.append("!");
                throw new SSLException(stringBuffer3.toString(), 2, 47, false);
            }
        }
        int iK2 = abVar.k();
        if (iK2 == 0) {
            ticketSession.a(false);
        } else if (iK2 == 1) {
            ticketSession.a(true);
        } else {
            StringBuffer stringBuffer4 = new StringBuffer("Invalid truncated hmac value in ticket: ");
            stringBuffer4.append(iK2);
            stringBuffer4.append("!");
            throw new SSLException(stringBuffer4.toString(), 2, 47, false);
        }
        int iK3 = abVar.k();
        if (iK3 == 0) {
            ticketSession.a(iK3);
        } else {
            try {
                ticketSession.a(MaxFragmentLength.c(iK3));
            } catch (IllegalArgumentException unused2) {
                StringBuffer stringBuffer5 = new StringBuffer("Invalid max fragment length in ticket: ");
                stringBuffer5.append(iK3);
                stringBuffer5.append("!");
                throw new SSLException(stringBuffer5.toString(), 2, 47, false);
            }
        }
        ticketSession.i = ((long) abVar.j()) * 1000;
        int iAvailable = abVar.available();
        if (abVar.available() == 1) {
            int iK4 = abVar.k();
            if (iK4 == 0) {
                ticketSession.b(false);
            } else if (iK4 == 1) {
                ticketSession.b(true);
            } else {
                StringBuffer stringBuffer6 = new StringBuffer("Invalid extended master secret value in ticket: ");
                stringBuffer6.append(iK4);
                stringBuffer6.append("!");
                throw new SSLException(stringBuffer6.toString(), 2, 47, false);
            }
        } else if (iAvailable > 0) {
            throw new SSLException("Ticket contains unexpected data!", 2, 50, false);
        }
        return ticketSession;
    }

    private static final void a(Session session, v vVar) throws IOException {
        byte[] bytes;
        vVar.a(session.getVersion());
        vVar.a(session.getCipherSuite().getID());
        vVar.d(session.getCompressionMethod().getID());
        vVar.write(session.getMasterSecret());
        X509Certificate[] peerCertificateChain = session.getPeerCertificateChain();
        if (peerCertificateChain != null) {
            vVar.d(1);
            int length = peerCertificateChain.length;
            byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte[].class, length);
            int length2 = 0;
            for (int i = 0; i < length; i++) {
                try {
                    bArr[i] = peerCertificateChain[i].getEncoded();
                    length2 = length2 + bArr[i].length + 3;
                } catch (CertificateException e) {
                    StringBuffer stringBuffer = new StringBuffer("Unable to encode certificate: ");
                    stringBuffer.append(e);
                    throw new RuntimeException(stringBuffer.toString());
                }
            }
            vVar.b(length2);
            for (int i2 = 0; i2 < length; i2++) {
                vVar.b(bArr[i2]);
            }
        } else {
            String pSKIdentity = session.getPSKIdentity();
            if (pSKIdentity != null) {
                vVar.d(2);
                try {
                    try {
                        bytes = pSKIdentity.getBytes("UTF8");
                    } catch (UnsupportedEncodingException unused) {
                        bytes = pSKIdentity.getBytes(UsbSerialDebugger.ENCODING);
                    }
                    vVar.a(bytes);
                } catch (Throwable th) {
                    StringBuffer stringBuffer2 = new StringBuffer("Cannot UTF-8 encode identity: ");
                    stringBuffer2.append(th.toString());
                    throw new SSLException(stringBuffer2.toString(), 2, 80, false);
                }
            } else {
                vVar.d(0);
            }
        }
        vVar.d(session.d() ? 1 : 0);
        int iA = session.a();
        if (iA != 0) {
            iA = MaxFragmentLength.c(iA);
        }
        vVar.d(iA);
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        vVar.c(jCurrentTimeMillis < 2147483647L ? (int) jCurrentTimeMillis : Integer.MAX_VALUE);
        vVar.d(session.e() ? 1 : 0);
    }

    public TicketKeyBag(SecretKey secretKey, SecretKey secretKey2, byte[] bArr) {
        Objects.requireNonNull(secretKey, "cipherKey must not be null!");
        Objects.requireNonNull(secretKey2, "macKey must not be null!");
        Objects.requireNonNull(bArr, "keyName must not be null!");
        if (bArr.length != 16) {
            throw new IllegalArgumentException("keyName must be 16 bytes long!");
        }
        this.c = secretKey;
        this.d = secretKey2;
        this.b = new KeyName((byte[]) bArr.clone());
        setActivationTime(System.currentTimeMillis() / 1000);
        this.f = -1L;
    }

    public TicketKeyBag() throws NoSuchAlgorithmException {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        byte[] bArr = new byte[16];
        SecureRandom secureRandom = securityProvider.getSecureRandom();
        secureRandom.nextBytes(bArr);
        this.b = new KeyName(bArr);
        try {
            KeyGenerator keyGenerator = securityProvider.getKeyGenerator(SecurityProvider.ALG_KEYGEN_AES);
            keyGenerator.init(secureRandom);
            this.c = keyGenerator.generateKey();
            try {
                KeyGenerator keyGenerator2 = securityProvider.getKeyGenerator("HmacSHA256");
                keyGenerator2.init(256, secureRandom);
                this.d = keyGenerator2.generateKey();
                setActivationTime(System.currentTimeMillis() / 1000);
                this.f = -1L;
            } catch (NoSuchAlgorithmException e) {
                throw e;
            } catch (Exception e2) {
                StringBuffer stringBuffer = new StringBuffer("Cannot generate mac key: ");
                stringBuffer.append(e2.toString());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (NoSuchAlgorithmException e3) {
            throw e3;
        } catch (Exception e4) {
            StringBuffer stringBuffer2 = new StringBuffer("Cannot generate cipher key: ");
            stringBuffer2.append(e4.toString());
            throw new NoSuchAlgorithmException(stringBuffer2.toString());
        }
    }

    public Object clone() {
        try {
            TicketKeyBag ticketKeyBag = (TicketKeyBag) super.clone();
            try {
                ticketKeyBag.b = (KeyName) this.b.clone();
                ticketKeyBag.c = new SecretKeySpec((byte[]) this.c.getEncoded().clone(), this.c.getAlgorithm());
                ticketKeyBag.d = new SecretKeySpec((byte[]) this.d.getEncoded().clone(), this.d.getAlgorithm());
                ticketKeyBag.e = this.e;
                ticketKeyBag.f = this.f;
                return ticketKeyBag;
            } catch (CloneNotSupportedException unused) {
                return ticketKeyBag;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Key Name: ");
        stringBuffer.append(this.b);
        stringBuffer.append("\nActivation Time: ");
        stringBuffer.append(this.e);
        stringBuffer.append("\nValidity Period: ");
        stringBuffer.append(this.f);
        return stringBuffer.toString();
    }

    public SecretKey getCipherKey() {
        return this.c;
    }

    public SecretKey getMacKey() {
        return this.d;
    }

    public KeyName getKeyName() {
        return this.b;
    }

    static byte[] a(Session session, TicketKeyBag ticketKeyBag) throws IOException {
        Objects.requireNonNull(session, "Session must not be null!");
        Objects.requireNonNull(ticketKeyBag, "Ticket keys must not be null!");
        byte[] name = ticketKeyBag.getKeyName().getName();
        SecretKey cipherKey = ticketKeyBag.getCipherKey();
        SecretKey macKey = ticketKeyBag.getMacKey();
        v vVar = new v(RSAKeyPairGeneratorFIPS.KEYLENGTH_3072);
        a(session, vVar);
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        try {
            Cipher cipher = securityProvider.getCipher(SecurityProvider.ALG_CIPHER_AES_PKCS5, 1, cipherKey, null, null);
            byte[] bArrDoFinal = cipher.doFinal(vVar.a(), 0, vVar.size());
            byte[] iv = cipher.getIV();
            vVar.reset();
            try {
                Mac mac = securityProvider.getMac("HmacSHA256", macKey);
                vVar.write(name);
                mac.update(name);
                vVar.write(iv);
                mac.update(iv);
                int size = vVar.size();
                vVar.a(bArrDoFinal);
                mac.update(vVar.a(), size, bArrDoFinal.length + 2);
                vVar.write(mac.doFinal());
                return vVar.toByteArray();
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("Error calculating session ticket mac: ");
                stringBuffer.append(e.toString());
                throw new SSLException(stringBuffer.toString(), 2, 80, false);
            }
        } catch (Exception e2) {
            StringBuffer stringBuffer2 = new StringBuffer("Error encrypting session ticket: ");
            stringBuffer2.append(e2.toString());
            throw new SSLException(stringBuffer2.toString(), 2, 80, false);
        }
    }

    static Session a(byte[] bArr, SSLServerContext sSLServerContext, SessionID sessionID) throws IOException {
        Objects.requireNonNull(bArr, "Ticket must not be null!");
        Objects.requireNonNull(sSLServerContext, "ServerContext must not be null!");
        ab abVar = new ab(bArr);
        byte[] bArr2 = new byte[16];
        if (abVar.a(bArr2) != 16) {
            throw new SSLException("Ticket contains invalid key name!", 2, 50, false);
        }
        try {
            TicketKeyBag ticketKeyBagA = sSLServerContext.a(new KeyName(bArr2));
            if (ticketKeyBagA == null) {
                throw new SSLException("No keys for ticket key name available!", 2, 47, false);
            }
            SecretKey cipherKey = ticketKeyBagA.getCipherKey();
            SecretKey macKey = ticketKeyBagA.getMacKey();
            byte[] bArr3 = new byte[16];
            if (abVar.a(bArr3) != 16) {
                throw new SSLException("Ticket contains invalid iv!", 2, 50, false);
            }
            byte[] bArrG = abVar.g();
            byte[] bArr4 = new byte[32];
            if (abVar.a(bArr4) != 32) {
                throw new SSLException("Ticket contains invalid mac!", 2, 50, false);
            }
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            try {
                Mac mac = securityProvider.getMac("HmacSHA256", macKey);
                mac.update(bArr2);
                mac.update(bArr3);
                mac.update(new byte[]{(byte) (bArrG.length >> 8), (byte) bArrG.length});
                mac.update(bArrG);
                if (!Utils.equalsBlock(mac.doFinal(), bArr4)) {
                    throw new SSLException("Session ticket mac verification failed!", 2, 80, false);
                }
                try {
                    TicketSession ticketSession = (TicketSession) a(new ab(securityProvider.getCipher(SecurityProvider.ALG_CIPHER_AES_PKCS5, 2, cipherKey, new IvParameterSpec(bArr3), null).doFinal(bArrG)), sessionID);
                    ticketSession.a(bArr);
                    return ticketSession;
                } catch (Exception e) {
                    StringBuffer stringBuffer = new StringBuffer("Error decrypting session ticket: ");
                    stringBuffer.append(e.toString());
                    throw new SSLException(stringBuffer.toString(), 2, 80, false);
                }
            } catch (Exception e2) {
                StringBuffer stringBuffer2 = new StringBuffer("Error calculating session ticket mac: ");
                stringBuffer2.append(e2.toString());
                throw new SSLException(stringBuffer2.toString(), 2, 80, false);
            }
        } catch (NoSuchAlgorithmException e3) {
            StringBuffer stringBuffer3 = new StringBuffer("Cannot get ticket keys: ");
            stringBuffer3.append(e3.toString());
            throw new SSLException(stringBuffer3.toString(), 2, 80, false);
        }
    }

    public void setValidityPeriod(long j) {
        this.f = j;
    }

    public long getValidityPeriod() {
        return this.f;
    }

    public void setActivationTime(long j) {
        this.e = (int) j;
    }

    public long getActivationTime() {
        return this.e;
    }
}

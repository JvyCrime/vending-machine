package iaik.security.ssl;

import com.felhr.usbserial.UsbSerialDebugger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class DefaultPSKManager extends PSKManager {
    private HashMap a = new HashMap(20);
    private Vector b = new Vector(20);

    @Override // iaik.security.ssl.PSKManager
    public void addPSKCredential(PSKCredential pSKCredential) {
        if (pSKCredential == null) {
            throw new IllegalArgumentException("Cannot add null PSKCredential!");
        }
        this.a.put(pSKCredential.getIdentityString().toLowerCase(), pSKCredential);
        String identityHintString = pSKCredential.getIdentityHintString();
        if (identityHintString != null) {
            this.a.put(identityHintString.toLowerCase(), pSKCredential);
        }
        Object remotePeerId = pSKCredential.getRemotePeerId();
        if (remotePeerId != null) {
            if (remotePeerId instanceof String) {
                this.a.put(((String) remotePeerId).toLowerCase(), pSKCredential);
            } else {
                this.a.put(remotePeerId, pSKCredential);
            }
        }
        this.b.addElement(pSKCredential);
    }

    @Override // iaik.security.ssl.PSKManager
    public PSKCredential removePSKCredential(String str) {
        if (str == null) {
            return null;
        }
        PSKCredential pSKCredential = (PSKCredential) this.a.get(str.toLowerCase());
        return pSKCredential != null ? removePSKCredential(pSKCredential) : pSKCredential;
    }

    @Override // iaik.security.ssl.PSKManager
    public PSKCredential removePSKCredentialWithRemotePeerId(Object obj) {
        PSKCredential pSKCredential;
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            pSKCredential = (PSKCredential) this.a.get(((String) obj).toLowerCase());
        } else {
            pSKCredential = (PSKCredential) this.a.get(obj);
        }
        return pSKCredential != null ? removePSKCredential(pSKCredential) : pSKCredential;
    }

    @Override // iaik.security.ssl.PSKManager
    public PSKCredential removePSKCredential(PSKCredential pSKCredential) {
        if (pSKCredential == null) {
            return null;
        }
        PSKCredential pSKCredential2 = (PSKCredential) this.a.remove(pSKCredential.getIdentityString().toLowerCase());
        String identityHintString = pSKCredential.getIdentityHintString();
        if (identityHintString != null) {
            this.a.remove(identityHintString.toLowerCase());
        }
        Object remotePeerId = pSKCredential.getRemotePeerId();
        if (remotePeerId != null) {
            if (remotePeerId instanceof String) {
                this.a.remove(((String) remotePeerId).toLowerCase());
            } else {
                this.a.remove(remotePeerId);
            }
        }
        this.b.removeElement(pSKCredential);
        return pSKCredential2;
    }

    @Override // iaik.security.ssl.PSKManager
    public void removeAll() {
        this.a.clear();
        this.b.removeAllElements();
    }

    @Override // iaik.security.ssl.PSKManager
    public Enumeration getAll() {
        return this.b.elements();
    }

    @Override // iaik.security.ssl.PSKManager
    public int size() {
        return this.b.size();
    }

    @Override // iaik.security.ssl.PSKManager
    public PSKCredential getPSKCredential(String str, SSLTransport sSLTransport) throws SSLException {
        boolean z = (sSLTransport == null || sSLTransport.b == null) ? false : true;
        PSKCredential pSKCredential = null;
        if (str != null && (pSKCredential = (PSKCredential) this.a.get(str.toLowerCase())) != null && z) {
            StringBuffer stringBuffer = new StringBuffer("Found pre-shared key for ");
            stringBuffer.append(str);
            sSLTransport.a(stringBuffer.toString());
        }
        if (sSLTransport != null && (str == null || (pSKCredential == null && sSLTransport.getUseClientMode()))) {
            Object remotePeerName = sSLTransport.getRemotePeerName();
            if (remotePeerName != null) {
                pSKCredential = (PSKCredential) this.a.get(((String) remotePeerName).toLowerCase());
            }
            if (pSKCredential == null) {
                InetAddress remoteInetAddress = sSLTransport.getRemoteInetAddress();
                if (remoteInetAddress != null && (remotePeerName = remoteInetAddress.getHostAddress()) != null) {
                    pSKCredential = (PSKCredential) this.a.get(((String) remotePeerName).toLowerCase());
                }
                if (pSKCredential == null) {
                    remotePeerName = sSLTransport.getRemotePeerId();
                    if (remotePeerName == null) {
                        throw new SSLException("No pre-shared key found. Missing remote peer id!");
                    }
                    if (remotePeerName instanceof String) {
                        pSKCredential = (PSKCredential) this.a.get(((String) remotePeerName).toLowerCase());
                    } else {
                        pSKCredential = (PSKCredential) this.a.get(remotePeerName);
                    }
                }
            }
            if (pSKCredential != null && z) {
                StringBuffer stringBuffer2 = new StringBuffer("Found pre-shared key for ");
                stringBuffer2.append(remotePeerName);
                sSLTransport.a(stringBuffer2.toString());
            }
        }
        return pSKCredential;
    }

    public byte[] encodeRemotePeerId(Object obj) throws UnsupportedEncodingException {
        Objects.requireNonNull(obj, "Remote peerId must not be null!");
        if (!(obj instanceof String)) {
            throw new UnsupportedEncodingException("Only can encode String peer ids!");
        }
        try {
            try {
                return ((String) obj).getBytes("UTF8");
            } catch (UnsupportedEncodingException unused) {
                return ((String) obj).getBytes(UsbSerialDebugger.ENCODING);
            }
        } catch (Exception e) {
            if (e instanceof UnsupportedEncodingException) {
                throw ((UnsupportedEncodingException) e);
            }
            StringBuffer stringBuffer = new StringBuffer("Error encoding remote peer id: ");
            stringBuffer.append(e.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public Object decodeRemotePeerId(byte[] bArr) throws UnsupportedEncodingException {
        String str;
        Objects.requireNonNull(bArr, "Remote peerId must not be null!");
        try {
            try {
                str = new String(bArr, "UTF8");
            } catch (UnsupportedEncodingException unused) {
                str = new String(bArr, UsbSerialDebugger.ENCODING);
            }
            return str;
        } catch (Exception e) {
            if (e instanceof UnsupportedEncodingException) {
                throw ((UnsupportedEncodingException) e);
            }
            StringBuffer stringBuffer = new StringBuffer("Error decoding remote peer id: ");
            stringBuffer.append(e.toString());
            throw new UnsupportedEncodingException(stringBuffer.toString());
        }
    }

    public void load(InputStream inputStream, char[] cArr) throws IOException, InvalidKeyException, KeyStoreException {
        InputStream bufferedInputStream = inputStream;
        Objects.requireNonNull(bufferedInputStream, "Input stream must not be null");
        Objects.requireNonNull(cArr, "Password must not be null!");
        removeAll();
        if (!(bufferedInputStream instanceof BufferedInputStream)) {
            bufferedInputStream = new BufferedInputStream(bufferedInputStream);
        }
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        SecureRandom secureRandom = securityProvider.getSecureRandom();
        int iB = b(bufferedInputStream);
        if (iB != 256) {
            StringBuffer stringBuffer = new StringBuffer("Invalid version: ");
            stringBuffer.append(iB);
            stringBuffer.append("! Must be ");
            stringBuffer.append(256);
            throw new KeyStoreException(stringBuffer.toString());
        }
        byte[] bArr = new byte[16];
        if (bufferedInputStream.read(bArr) != 16) {
            throw new IOException("Error reading cipher salt: not complete!");
        }
        try {
            byte[] encoded = securityProvider.deriveKey(SecurityProvider.ALG_KEYGEN_PBKDF2, cArr, bArr, 2000, 32, SecurityProvider.ALG_KEYGEN_AES, secureRandom).getEncoded();
            try {
                Cipher cipher = securityProvider.getCipher(SecurityProvider.ALG_CIPHER_AES_PKCS5, 2, new SecretKeySpec(encoded, 0, 16, SecurityProvider.ALG_KEYGEN_AES), new IvParameterSpec(encoded, 16, 16), secureRandom);
                CipherInputStream cipherInputStream = new CipherInputStream(bufferedInputStream, cipher);
                byte[] bArr2 = new byte[16];
                if (a(cipherInputStream, bArr2) != 16) {
                    throw new IOException("Error reading mac salt: not complete!");
                }
                try {
                    try {
                        Mac mac = securityProvider.getMac("HmacSHA256", securityProvider.deriveKey(SecurityProvider.ALG_KEYGEN_PBKDF2, cArr, bArr2, 2000, 32, "HmacSHA256", secureRandom));
                        int iB2 = b(cipherInputStream);
                        if (iB2 > 65535) {
                            StringBuffer stringBuffer2 = new StringBuffer("Cannot load PSK manager. Too many entries (");
                            stringBuffer2.append(iB2);
                            stringBuffer2.append(")");
                            throw new KeyStoreException(stringBuffer2.toString());
                        }
                        mac.update(new byte[]{(byte) (iB2 >> 8), (byte) iB2});
                        for (int i = 0; i < iB2; i++) {
                            int iB3 = b(cipherInputStream);
                            mac.update(new byte[]{(byte) (iB3 >> 8), (byte) iB3});
                            if (iB3 > 0) {
                                byte[] bArr3 = new byte[iB3];
                                if (a(cipherInputStream, bArr3) != iB3) {
                                    throw new IOException("Error reading psk credential: not complete!");
                                }
                                mac.update(bArr3);
                                addPSKCredential(a(new ByteArrayInputStream(bArr3)));
                            }
                        }
                        byte[] bArr4 = new byte[32];
                        if (a(cipherInputStream, bArr4) != 32) {
                            throw new KeyStoreException("Cannot verify MAC: Missing mac value from psk manager encoding!");
                        }
                        if (!Utils.equalsBlock(bArr4, mac.doFinal())) {
                            throw new KeyStoreException("Cannot load psk manager: MAC verification error!");
                        }
                        try {
                            byte[] bArrDoFinal = cipher.doFinal();
                            if (bArrDoFinal != null && bArrDoFinal.length > 0) {
                                throw new BadPaddingException("Corrupted padding.");
                            }
                        } catch (Exception e) {
                            StringBuffer stringBuffer3 = new StringBuffer("Decryption error: ");
                            stringBuffer3.append(e.toString());
                            throw new KeyStoreException(stringBuffer3.toString());
                        }
                    } catch (Exception e2) {
                        StringBuffer stringBuffer4 = new StringBuffer("Error initializing Mac engine: ");
                        stringBuffer4.append(e2.toString());
                        throw new KeyStoreException(stringBuffer4.toString());
                    }
                } catch (Exception e3) {
                    StringBuffer stringBuffer5 = new StringBuffer("Error deriving key from password: ");
                    stringBuffer5.append(e3.toString());
                    throw new InvalidKeyException(stringBuffer5.toString());
                }
            } catch (Exception e4) {
                StringBuffer stringBuffer6 = new StringBuffer("Error initializing cipher for content decryption: ");
                stringBuffer6.append(e4.toString());
                throw new KeyStoreException(stringBuffer6.toString());
            }
        } catch (Exception e5) {
            StringBuffer stringBuffer7 = new StringBuffer("Error deriving key from password: ");
            stringBuffer7.append(e5.toString());
            throw new InvalidKeyException(stringBuffer7.toString());
        }
    }

    public void store(OutputStream outputStream, char[] cArr) throws IOException, InvalidKeyException, KeyStoreException {
        OutputStream bufferedOutputStream = outputStream;
        Objects.requireNonNull(bufferedOutputStream, "Output stream must not be null");
        Objects.requireNonNull(cArr, "Password must not be null!");
        if (!(bufferedOutputStream instanceof BufferedOutputStream)) {
            bufferedOutputStream = new BufferedOutputStream(bufferedOutputStream);
        }
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        a(256, bufferedOutputStream);
        SecureRandom secureRandom = securityProvider.getSecureRandom();
        byte[] bArr = new byte[16];
        secureRandom.nextBytes(bArr);
        bufferedOutputStream.write(bArr);
        byte[] bArr2 = new byte[16];
        secureRandom.nextBytes(bArr2);
        try {
            SecretKey secretKeyDeriveKey = securityProvider.deriveKey(SecurityProvider.ALG_KEYGEN_PBKDF2, cArr, bArr, 2000, 32, SecurityProvider.ALG_KEYGEN_AES, secureRandom);
            SecretKey secretKeyDeriveKey2 = securityProvider.deriveKey(SecurityProvider.ALG_KEYGEN_PBKDF2, cArr, bArr2, 2000, 32, "HmacSHA256", secureRandom);
            byte[] encoded = secretKeyDeriveKey.getEncoded();
            try {
                Cipher cipher = securityProvider.getCipher(SecurityProvider.ALG_CIPHER_AES_PKCS5, 1, new SecretKeySpec(encoded, 0, 16, SecurityProvider.ALG_KEYGEN_AES), new IvParameterSpec(encoded, 16, 16), secureRandom);
                try {
                    Mac mac = securityProvider.getMac("HmacSHA256", secretKeyDeriveKey2);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                    byteArrayOutputStream.write(bArr2);
                    int size = size();
                    if (size > 65535) {
                        StringBuffer stringBuffer = new StringBuffer("Cannot store PSK manager. Too many entries (");
                        stringBuffer.append(size);
                        stringBuffer.append(")");
                        throw new KeyStoreException(stringBuffer.toString());
                    }
                    a(size, byteArrayOutputStream);
                    try {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        mac.update(byteArray, byteArray.length - 2, 2);
                        byte[] bArrUpdate = cipher.update(byteArray);
                        if (bArrUpdate != null && bArrUpdate.length > 0) {
                            bufferedOutputStream.write(bArrUpdate);
                        }
                        Enumeration all = getAll();
                        while (all.hasMoreElements()) {
                            byteArrayOutputStream.reset();
                            a((PSKCredential) all.nextElement(), byteArrayOutputStream);
                            byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                            byteArrayOutputStream.reset();
                            a(byteArray2.length, byteArrayOutputStream);
                            byteArrayOutputStream.write(byteArray2);
                            byte[] byteArray3 = byteArrayOutputStream.toByteArray();
                            mac.update(byteArray3);
                            byte[] bArrUpdate2 = cipher.update(byteArray3);
                            if (bArrUpdate2 != null && bArrUpdate2.length > 0) {
                                bufferedOutputStream.write(bArrUpdate2);
                            }
                        }
                        bufferedOutputStream.write(cipher.doFinal(mac.doFinal()));
                        bufferedOutputStream.flush();
                    } catch (IOException e) {
                        throw e;
                    } catch (Exception e2) {
                        StringBuffer stringBuffer2 = new StringBuffer("Error storing psk manager contents: ");
                        stringBuffer2.append(e2.toString());
                        throw new KeyStoreException(stringBuffer2.toString());
                    }
                } catch (Exception e3) {
                    StringBuffer stringBuffer3 = new StringBuffer("Error initializing Mac engine: ");
                    stringBuffer3.append(e3.toString());
                    throw new KeyStoreException(stringBuffer3.toString());
                }
            } catch (Exception e4) {
                StringBuffer stringBuffer4 = new StringBuffer("Error initializing cipher for content encryption: ");
                stringBuffer4.append(e4.toString());
                throw new KeyStoreException(stringBuffer4.toString());
            }
        } catch (Exception e5) {
            StringBuffer stringBuffer5 = new StringBuffer("Error deriving key from password: ");
            stringBuffer5.append(e5.toString());
            throw new InvalidKeyException(stringBuffer5.toString());
        }
    }

    private PSKCredential a(InputStream inputStream) throws IOException {
        int iB = b(inputStream);
        if (iB <= 0) {
            throw new IOException("Invalid PSK Credential! Missing identity!");
        }
        byte[] bArr = new byte[iB];
        if (a(inputStream, bArr) != iB) {
            throw new IOException("Error reading identity: not complete!");
        }
        int iB2 = b(inputStream);
        if (iB2 <= 0) {
            throw new IOException("Parsed invalid PSKCredential: no psk set!");
        }
        byte[] bArr2 = new byte[iB2];
        if (a(inputStream, bArr2) != iB2) {
            throw new IOException("Error reading psk: not complete!");
        }
        PSKCredential pSKCredential = new PSKCredential(bArr, new PreSharedKey(bArr2));
        int iB3 = b(inputStream);
        if (iB3 > 0) {
            byte[] bArr3 = new byte[iB3];
            if (a(inputStream, bArr3) != iB3) {
                throw new IOException("Error reading identity hint: not complete!");
            }
            pSKCredential.setIdentityHint(bArr3);
        }
        int iB4 = b(inputStream);
        if (iB4 > 0) {
            byte[] bArr4 = new byte[iB4];
            if (a(inputStream, bArr4) != iB4) {
                throw new IOException("Error reading remote peer id: not complete!");
            }
            pSKCredential.setRemotePeerId(decodeRemotePeerId(bArr4));
        }
        return pSKCredential;
    }

    private void a(PSKCredential pSKCredential, OutputStream outputStream) throws IOException {
        byte[] identity = pSKCredential.getIdentity();
        a(identity.length, outputStream);
        outputStream.write(identity);
        byte[] encoded = pSKCredential.getPSK().getEncoded();
        a(encoded.length, outputStream);
        outputStream.write(encoded);
        byte[] identityHint = pSKCredential.getIdentityHint();
        if (identityHint == null) {
            a(0, outputStream);
        } else {
            a(identityHint.length, outputStream);
            outputStream.write(identityHint);
        }
        Object remotePeerId = pSKCredential.getRemotePeerId();
        if (remotePeerId == null) {
            a(0, outputStream);
            return;
        }
        byte[] bArrEncodeRemotePeerId = encodeRemotePeerId(remotePeerId);
        a(bArrEncodeRemotePeerId.length, outputStream);
        outputStream.write(bArrEncodeRemotePeerId);
    }

    private int b(InputStream inputStream) throws IOException {
        return inputStream.read() | (inputStream.read() << 8);
    }

    private void a(int i, OutputStream outputStream) throws IOException {
        outputStream.write((byte) (i >> 8));
        outputStream.write((byte) i);
    }

    private int a(InputStream inputStream, byte[] bArr) throws IOException {
        int length = bArr.length;
        if (length <= 0) {
            return 0;
        }
        int i = inputStream.read(bArr, 0, length);
        if (i == -1) {
            throw new EOFException("Unexpected EOF during reading psk manager contents!");
        }
        while (i < length) {
            int i2 = inputStream.read(bArr, 0 + i, length - i);
            if (i2 == -1) {
                throw new EOFException("Unexpected EOF during reading psk manager contents!");
            }
            i += i2;
        }
        return i;
    }
}

package iaik.utils;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.security.random.SecRandom;
import iaik.security.ssl.SecurityProvider;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import javax.crypto.Cipher;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes2.dex */
public class SSLeayPrivateKey implements PrivateKey {
    private static final long serialVersionUID = 8943736105225045343L;
    private int a;
    private String b;
    private int c;
    private byte[] d;
    private byte[] e;
    private boolean f;
    private PrivateKey g;

    public SSLeayPrivateKey(InputStream inputStream) throws IOException, InvalidKeyException {
        LineInputStream lineInputStream = new LineInputStream(inputStream);
        String line = lineInputStream.readLine();
        this.a = -1;
        if (line.startsWith("-----BEGIN ") && line.endsWith(" PRIVATE KEY-----")) {
            this.a = a(line.substring(11, line.length() - 17));
        }
        if (this.a < 0) {
            throw new IOException("No SSLeay private key!");
        }
        String line2 = lineInputStream.readLine();
        if (line2.equals("Proc-Type: 4,ENCRYPTED")) {
            this.f = true;
            String line3 = lineInputStream.readLine();
            if (!line3.startsWith("DEK-Info:")) {
                throw new IOException("Unknown SSLeay private key format!");
            }
            int iIndexOf = line3.indexOf(32);
            int iIndexOf2 = line3.indexOf(44);
            String strSubstring = line3.substring(iIndexOf + 1, iIndexOf2);
            byte[] byteArray = new BigInteger(line3.substring(iIndexOf2 + 1), 16).toByteArray();
            this.d = byteArray;
            if (byteArray.length > 8) {
                byte[] bArr = new byte[8];
                System.arraycopy(byteArray, 1, bArr, 0, 8);
                this.d = bArr;
            }
            b(strSubstring);
            do {
                line2 = lineInputStream.readLine();
            } while (line2.length() > 0);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (!this.f) {
            byteArrayOutputStream.write(Util.toASCIIBytes(line2));
        }
        while (true) {
            String line4 = lineInputStream.readLine();
            if (line4.startsWith("-----END ")) {
                this.e = Util.Base64Decode(byteArrayOutputStream.toByteArray());
                return;
            }
            byteArrayOutputStream.write(Util.toASCIIBytes(line4));
        }
    }

    public SSLeayPrivateKey(String str) throws IOException, InvalidKeyException {
        this(new FileInputStream(str));
    }

    public SSLeayPrivateKey(PrivateKey privateKey) throws CodingException, InvalidKeyException {
        int i;
        this.g = privateKey;
        if (privateKey instanceof RSAPrivateKey) {
            i = 1;
        } else if (privateKey instanceof DSAPrivateKey) {
            i = 2;
        } else {
            if (!(privateKey instanceof DHPrivateKey)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unsupported key type: ");
                stringBuffer.append(privateKey.getClass().getName());
                throw new InvalidKeyException(stringBuffer.toString());
            }
            i = 3;
        }
        this.a = i;
        a(privateKey);
    }

    private static final int a(String str) {
        if (str.equals("RSA")) {
            return 1;
        }
        if (str.equals("DSA")) {
            return 2;
        }
        return str.equals("DH") ? 3 : -1;
    }

    private String a() {
        if (this.b.equals("DESede/CBC/PKCS5Padding")) {
            return "DES-EDE3-CBC";
        }
        if (this.b.equals("DES/CBC/PKCS5Padding")) {
            return "DES-CBC";
        }
        if (this.b.equals("IDEA/CBC/PKCS5Padding")) {
            return "IDEA-CBC";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unknown algorithm: ");
        stringBuffer.append(this.b);
        throw new RuntimeException(stringBuffer.toString());
    }

    private static final String a(int i) {
        if (i == 1) {
            return "RSA";
        }
        if (i == 2) {
            return "DSA";
        }
        if (i == 3) {
            return "DH";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid algorithm: ");
        stringBuffer.append(i);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    private Cipher a(int i, String str) throws GeneralSecurityException {
        byte[] aSCIIBytes = Util.toASCIIBytes(str);
        Cipher cipher = Cipher.getInstance(this.b);
        if (this.d == null) {
            SecureRandom secureRandom = SecRandom.getDefault();
            int blockSize = cipher.getBlockSize();
            if (blockSize <= 0) {
                blockSize = 1;
            }
            byte[] bArr = new byte[blockSize];
            this.d = bArr;
            secureRandom.nextBytes(bArr);
        }
        byte[] bArr2 = this.d;
        MessageDigest messageDigest = MessageDigest.getInstance(SecurityProvider.ALG_DIGEST_MD5);
        byte[] bArr3 = new byte[this.c];
        int i2 = 0;
        while (i2 < this.c) {
            messageDigest.update(aSCIIBytes);
            messageDigest.update(bArr2);
            byte[] bArrDigest = messageDigest.digest();
            int iMin = Math.min(bArrDigest.length, this.c - i2);
            System.arraycopy(bArrDigest, 0, bArr3, i2, iMin);
            i2 += iMin;
            messageDigest.update(bArr3, 0, i2);
        }
        cipher.init(i, new SecretKeySpec(bArr3, Util.getRawCipherName(this.b)), new IvParameterSpec(this.d));
        return cipher;
    }

    private void a(PrivateKey privateKey) throws CodingException {
        this.e = (byte[]) new ASN1(privateKey.getEncoded()).getComponentAt(2).getValue();
    }

    private void b(String str) throws InvalidKeyException {
        int i;
        if (str == null || str.equals("DES-EDE3-CBC")) {
            this.b = "DESede/CBC/PKCS5Padding";
            i = 24;
        } else if (str.equals("DES-CBC")) {
            this.b = "DES/CBC/PKCS5Padding";
            i = 8;
        } else {
            if (!str.equals("IDEA-CBC")) {
                throw new InvalidKeyException("Unknown SSLeay encryption algorithm!");
            }
            this.b = "IDEA/CBC/PKCS5Padding";
            i = 16;
        }
        this.c = i;
    }

    public void decrypt(String str) throws InvalidKeyException {
        if (this.f) {
            try {
                this.e = a(2, str).doFinal(this.e);
                this.f = false;
            } catch (NoSuchAlgorithmException e) {
                throw new InvalidKeyException(e.toString());
            } catch (GeneralSecurityException e2) {
                throw new InvalidKeyException(e2.toString());
            }
        }
    }

    public void encrypt(String str, String str2, byte[] bArr) throws GeneralSecurityException {
        if (this.f) {
            return;
        }
        this.d = bArr;
        b(str2);
        this.e = a(1, str).doFinal(this.e);
        this.f = true;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.b;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        try {
            return (byte[]) new ASN1(this.g.getEncoded()).getComponentAt(2).getValue();
        } catch (CodingException e) {
            throw new RuntimeException(e.toString());
        }
    }

    @Override // java.security.Key
    public String getFormat() {
        return "SSLeay";
    }

    public PrivateKey getPrivateKey() throws InvalidKeyException {
        if (this.f) {
            throw new InvalidKeyException("Private key not decrypted yet.");
        }
        if (this.g == null) {
            int i = this.a;
            if (i == 1) {
                this.g = iaik.security.rsa.RSAPrivateKey.parse(this.e);
            } else {
                if (i != 2) {
                    throw new InvalidKeyException("Unknown private key algorithm.");
                }
                try {
                    ASN1Object aSN1ObjectDecode = DerCoder.decode(this.e);
                    this.g = new iaik.security.dsa.DSAPrivateKey((BigInteger) aSN1ObjectDecode.getComponentAt(5).getValue(), (BigInteger) aSN1ObjectDecode.getComponentAt(1).getValue(), (BigInteger) aSN1ObjectDecode.getComponentAt(2).getValue(), (BigInteger) aSN1ObjectDecode.getComponentAt(3).getValue());
                } catch (CodingException e) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Error parsing DSA private key: ");
                    stringBuffer.append(e.toString());
                    throw new InvalidKeyException(stringBuffer.toString());
                }
            }
        }
        return this.g;
    }

    public boolean isEncrypted() {
        return this.f;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        LineOutputStream lineOutputStream = new LineOutputStream(outputStream);
        String strA = a(this.a);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("-----BEGIN ");
        stringBuffer.append(strA);
        stringBuffer.append(" PRIVATE KEY-----");
        lineOutputStream.println(stringBuffer.toString());
        if (this.f) {
            lineOutputStream.println("Proc-Type: 4,ENCRYPTED");
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("DEK-Info: ");
            stringBuffer2.append(a());
            stringBuffer2.append(',');
            int i = 0;
            while (true) {
                byte[] bArr = this.d;
                if (i >= bArr.length) {
                    break;
                }
                stringBuffer2.append(Util.toString(bArr[i]));
                i++;
            }
            lineOutputStream.println(stringBuffer2.toString());
            lineOutputStream.println();
        }
        lineOutputStream.println(Util.toASCIIString(Util.Base64Encode(this.e)));
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("-----END ");
        stringBuffer3.append(strA);
        stringBuffer3.append(" PRIVATE KEY-----");
        lineOutputStream.println(stringBuffer3.toString());
        lineOutputStream.flush();
    }
}

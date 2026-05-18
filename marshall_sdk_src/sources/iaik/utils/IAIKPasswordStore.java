package iaik.utils;

import iaik.security.random.SecRandom;
import iaik.security.spec.PBEKeyAndParameterSpec;
import iaik.security.ssl.SecurityProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes2.dex */
public final class IAIKPasswordStore implements Externalizable {
    private static final long serialVersionUID = -910010526845530425L;
    private byte a;
    private final Cipher b;
    private final int c;
    private final Mac d;
    private final int e;
    private final SecureRandom f;
    private KeyGenerator g;
    private Hashtable h;
    private PasswordGenerator i;
    private char[] j;

    static final class SerializablePassword implements Serializable {
        private char[] a;

        public SerializablePassword(char[] cArr) {
            char[] cArr2 = new char[cArr.length];
            this.a = cArr2;
            System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            int i = objectInputStream.readInt();
            if (i < 0) {
                throw new IOException("Invalid format!");
            }
            byte[] bArr = new byte[i];
            objectInputStream.readFully(bArr);
            if (objectInputStream.available() > 0) {
                throw new IOException("Invalid format!");
            }
            try {
                try {
                    this.a = Util.getCharsFromUTF8Encoding(bArr);
                } catch (UTF8CodingException e) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid UTF8 encoding: ");
                    stringBuffer.append(e);
                    throw new IOException(stringBuffer.toString());
                }
            } finally {
                CryptoUtils.zeroBlock(bArr);
            }
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            byte[] uTF8EncodingFromCharArray = null;
            try {
                try {
                    uTF8EncodingFromCharArray = Util.getUTF8EncodingFromCharArray(this.a);
                    objectOutputStream.writeInt(uTF8EncodingFromCharArray.length);
                    objectOutputStream.write(uTF8EncodingFromCharArray);
                } catch (Exception e) {
                    throw new IOException(e.toString());
                }
            } finally {
                if (uTF8EncodingFromCharArray != null) {
                    CryptoUtils.zeroBlock(uTF8EncodingFromCharArray);
                }
            }
        }

        public void a() {
            IAIKPasswordStore.a(this.a);
            this.a = null;
        }

        public char[] b() {
            return this.a;
        }

        protected void finalize() throws Throwable {
            a();
            super.finalize();
        }
    }

    public IAIKPasswordStore() {
        this(SecRandom.getDefault());
    }

    public IAIKPasswordStore(SecureRandom secureRandom) throws ProviderException {
        this.a = (byte) 1;
        try {
            Cipher cipher = Cipher.getInstance(SecurityProvider.ALG_KEYGEN_AES, "IAIK");
            this.b = cipher;
            Mac mac = Mac.getInstance("CMAC/AES", "IAIK");
            this.d = mac;
            this.f = secureRandom;
            this.i = new PasswordGenerator(secureRandom);
            this.c = cipher.getBlockSize();
            this.e = mac.getMacLength();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not obtain cryptographic algorithms! Please register the IAIK provider: ");
            stringBuffer.append(e);
            throw new ProviderException(stringBuffer.toString());
        }
    }

    private Hashtable a() {
        if (this.h == null) {
            this.h = new Hashtable();
        }
        return this.h;
    }

    static void a(char[] cArr) {
        if (cArr != null) {
            for (int i = 0; i < cArr.length; i++) {
                cArr[i] = 0;
            }
        }
    }

    private byte[] a(char[] cArr, byte[] bArr) throws ProviderException {
        try {
            byte[] uTF8EncodingFromCharArray = Util.getUTF8EncodingFromCharArray(cArr);
            if (bArr == null) {
                bArr = new byte[32];
                this.f.nextBytes(bArr);
            }
            PBEKeyAndParameterSpec pBEKeyAndParameterSpec = new PBEKeyAndParameterSpec(uTF8EncodingFromCharArray, bArr, 2048, 16);
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(SecurityProvider.ALG_KEYGEN_PBKDF2, "IAIK");
                this.g = keyGenerator;
                keyGenerator.init(pBEKeyAndParameterSpec);
            } catch (InvalidAlgorithmParameterException unused) {
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Could not obtain cryptographic algorithms! Please register the IAIK provider: ");
                stringBuffer.append(e);
                throw new ProviderException(stringBuffer.toString());
            } catch (NoSuchProviderException e2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Could not obtain cryptographic algorithms! Please register the IAIK provider!");
                stringBuffer2.append(e2);
                throw new ProviderException(stringBuffer2.toString());
            }
            return bArr;
        } catch (UTF8CodingException unused2) {
            return null;
        }
    }

    public Enumeration aliases() {
        return a().keys();
    }

    public void clear() {
        Hashtable hashtable = this.h;
        if (hashtable == null || hashtable.size() == 0) {
            return;
        }
        Enumeration enumerationKeys = this.h.keys();
        while (enumerationKeys.hasMoreElements()) {
            ((SerializablePassword) this.h.get(enumerationKeys.nextElement())).a();
        }
        this.h.clear();
        this.h = null;
    }

    public boolean containsAlias(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("alias may not be null or empty!");
        }
        Hashtable hashtable = this.h;
        if (hashtable == null) {
            return false;
        }
        return hashtable.containsValue(str);
    }

    public void deleteEntry(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("alias may not be null or empty!");
        }
        Hashtable hashtable = this.h;
        if (hashtable == null) {
            return;
        }
        ((SerializablePassword) hashtable.remove(str)).a();
    }

    protected void finalize() throws Throwable {
        clear();
        a(this.j);
        super.finalize();
    }

    public char[] generatePasswordEntry(String str, int i, int i2) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("alias may not be null or empty!");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("length must be greater than 0!");
        }
        if ((i2 & 31) == 0) {
            throw new IllegalArgumentException("Invalid bit mask!");
        }
        char[] cArrGenerate = this.i.generate(i, i2);
        a().put(str, new SerializablePassword(cArrGenerate));
        return cArrGenerate;
    }

    public char[] getPassword(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("alias may not be null or empty!");
        }
        Hashtable hashtable = this.h;
        if (hashtable == null) {
            return null;
        }
        return ((SerializablePassword) hashtable.get(str)).b();
    }

    public void load(File file, char[] cArr) throws Throwable {
        FileInputStream fileInputStream;
        if (file == null) {
            throw new IllegalArgumentException("file may not be null!");
        }
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Throwable th) {
            th = th;
        }
        try {
            load(fileInputStream, cArr);
            try {
                fileInputStream.close();
            } catch (IOException unused) {
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    public void load(InputStream inputStream, char[] cArr) throws Throwable {
        ObjectInputStream objectInputStream;
        if (inputStream == null) {
            throw new IllegalArgumentException("in may not be null!");
        }
        if (cArr == null || cArr.length == 0) {
            throw new IllegalArgumentException("password may not be null or empty!");
        }
        clear();
        this.j = (char[]) cArr.clone();
        try {
            objectInputStream = new ObjectInputStream(inputStream);
            try {
                readExternal(objectInputStream);
                try {
                    objectInputStream.close();
                } catch (IOException unused) {
                }
                this.g = null;
                a(this.j);
            } catch (ClassNotFoundException unused2) {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException unused3) {
                    }
                }
                this.g = null;
                a(this.j);
            } catch (Throwable th) {
                th = th;
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException unused4) {
                    }
                }
                this.g = null;
                a(this.j);
                this.j = null;
                throw th;
            }
        } catch (ClassNotFoundException unused5) {
            objectInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            objectInputStream = null;
        }
        this.j = null;
    }

    public void load(String str, char[] cArr) throws Throwable {
        load(new File(str), cArr);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws Throwable {
        ObjectInputStream objectInputStream;
        if (objectInput == null) {
            throw new IllegalArgumentException("in may not be null!");
        }
        byte b = objectInput.readByte();
        if (b <= 0 || b > 1) {
            throw new IOException("Unknown file format version!");
        }
        byte[] bArr = new byte[this.c];
        objectInput.readFully(bArr);
        byte[] bArr2 = new byte[32];
        objectInput.readFully(bArr2);
        int i = objectInput.readInt();
        a(this.j, bArr2);
        SecretKey secretKeyGenerateKey = this.g.generateKey();
        byte[] bArr3 = new byte[i];
        objectInput.readFully(bArr3);
        try {
            this.d.init(secretKeyGenerateKey);
            this.b.init(2, secretKeyGenerateKey, new IvParameterSpec(bArr));
            byte[] bArrDoFinal = this.b.doFinal(bArr3);
            this.d.update(b);
            this.d.update(bArr);
            this.d.update(bArr2);
            this.d.update(Util.toByteArray(i));
            byte[] bArrDoFinal2 = this.d.doFinal(bArrDoFinal);
            ObjectInputStream objectInputStream2 = null;
            try {
                objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bArrDoFinal));
            } catch (Throwable th) {
                th = th;
            }
            try {
                Hashtable hashtable = (Hashtable) objectInputStream.readObject();
                try {
                    objectInputStream.close();
                } catch (IOException unused) {
                }
                byte[] bArr4 = new byte[this.e];
                objectInput.readFully(bArr4);
                if (objectInput.available() > 0) {
                    throw new IOException("Invalid format!");
                }
                if (!CryptoUtils.equalsBlock(bArr4, bArrDoFinal2)) {
                    throw new IOException("Invalid MAC!");
                }
                this.a = b;
                this.h = hashtable;
            } catch (Throwable th2) {
                th = th2;
                objectInputStream2 = objectInputStream;
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            throw new IOException(this, "Format error!", e) { // from class: iaik.utils.IAIKPasswordStore.2
                private static final long serialVersionUID = -1002259859108202234L;
                private final Exception a;
                private final IAIKPasswordStore b;

                {
                    this.b = this;
                    this.a = e;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        }
    }

    public void setPasswordEntry(String str, char[] cArr) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("alias may not be null or empty!");
        }
        if (cArr == null || cArr.length == 0) {
            throw new IllegalArgumentException("password may not be null or empty!");
        }
        a().put(str, new SerializablePassword(cArr));
    }

    public int size() {
        Hashtable hashtable = this.h;
        if (hashtable == null) {
            return 0;
        }
        return hashtable.size();
    }

    public void store(File file, char[] cArr) throws Throwable {
        if (file == null) {
            throw new IllegalArgumentException("file may not be null!");
        }
        if (cArr == null || cArr.length == 0) {
            throw new IllegalArgumentException("password may not be null or empty!");
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                store(fileOutputStream2, cArr);
                try {
                    fileOutputStream2.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void store(OutputStream outputStream, char[] cArr) throws Throwable {
        ObjectOutputStream objectOutputStream;
        if (outputStream == null) {
            throw new IllegalArgumentException("out may not be null!");
        }
        if (cArr == null || cArr.length == 0) {
            throw new IllegalArgumentException("password may not be null or empty!");
        }
        this.j = (char[]) cArr.clone();
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
            try {
                writeExternal(objectOutputStream);
                try {
                    objectOutputStream.close();
                } catch (IOException unused) {
                }
                this.g = null;
                a(this.j);
                this.j = null;
            } catch (Throwable th) {
                th = th;
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                this.g = null;
                a(this.j);
                this.j = null;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            objectOutputStream = null;
        }
    }

    public void store(String str, char[] cArr) throws Throwable {
        store(new File(str), cArr);
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput objectOutput) throws Throwable {
        if (objectOutput == null) {
            throw new IllegalArgumentException("out may not be null!");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream2.writeObject(a());
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                }
                try {
                    objectOutputStream2.close();
                } catch (IOException unused2) {
                }
                byte[] bArrA = a(this.j, null);
                SecretKey secretKeyGenerateKey = this.g.generateKey();
                byte[] bArr = new byte[this.c];
                this.f.nextBytes(bArr);
                objectOutput.writeByte(this.a);
                objectOutput.write(bArr);
                objectOutput.write(bArrA);
                try {
                    this.d.init(secretKeyGenerateKey);
                    this.b.init(1, secretKeyGenerateKey, new IvParameterSpec(bArr));
                    byte[] bArrDoFinal = this.b.doFinal(byteArray);
                    objectOutput.writeInt(bArrDoFinal.length);
                    objectOutput.write(bArrDoFinal);
                    this.d.update(this.a);
                    this.d.update(bArr);
                    this.d.update(bArrA);
                    this.d.update(Util.toByteArray(bArrDoFinal.length));
                    objectOutput.write(this.d.doFinal(byteArray));
                } catch (Exception e) {
                    throw new IOException(this, "Error encrypting the store!", e) { // from class: iaik.utils.IAIKPasswordStore.1
                        private static final long serialVersionUID = 4893922477189466128L;
                        private final Exception a;
                        private final IAIKPasswordStore b;

                        {
                            this.b = this;
                            this.a = e;
                        }

                        @Override // java.lang.Throwable
                        public Throwable getCause() {
                            return this.a;
                        }
                    };
                }
            } catch (Throwable th) {
                th = th;
                objectOutputStream = objectOutputStream2;
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused3) {
                }
                if (objectOutputStream == null) {
                    throw th;
                }
                try {
                    objectOutputStream.close();
                    throw th;
                } catch (IOException unused4) {
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}

package iaik.utils;

import iaik.security.cipher.SecretKey;
import iaik.security.random.SecRandom;
import iaik.security.ssl.SecurityProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes2.dex */
public class CriticalObject implements Serializable {
    public static final String cipher = "3DES/CBC/PKCS5Padding";
    public static final SecureRandom random = SecRandom.getDefault();
    private static final long serialVersionUID = -7485719270014681648L;
    byte[] a;
    byte[] b;

    public CriticalObject(Object obj) throws IOException {
        this(obj, true);
    }

    public CriticalObject(Object obj, boolean z) throws IOException {
        byte[] bArr = new byte[20];
        this.b = bArr;
        random.nextBytes(bArr);
        a(obj, this.b, z);
    }

    public CriticalObject(Object obj, byte[] bArr) throws IOException {
        a(obj, bArr, true);
    }

    public CriticalObject(Object obj, byte[] bArr, boolean z) throws IOException {
        a(obj, bArr, z);
    }

    private int a() throws IOException {
        return 24;
    }

    private void a(Object obj, byte[] bArr, boolean z) throws IOException {
        try {
            byte[] bArrSerialize = serialize(obj);
            MessageDigest messageDigest = MessageDigest.getInstance(SecurityProvider.ALG_DIGEST_MD5);
            MessageDigest messageDigest2 = MessageDigest.getInstance("SHA");
            byte[] bArrDigest = messageDigest.digest(bArr);
            byte[] bArr2 = new byte[36];
            System.arraycopy(messageDigest2.digest(bArr), 0, bArr2, 0, 20);
            System.arraycopy(bArrDigest, 0, bArr2, 20, 16);
            Cipher cipher2 = Cipher.getInstance(cipher);
            SecretKey secretKey = new SecretKey(bArr2, 0, a(), Util.getRawCipherName(cipher));
            cipher2.init(1, secretKey, new IvParameterSpec(bArrDigest, 0, 8));
            this.a = cipher2.doFinal(bArrSerialize);
            CryptoUtils.zeroBlock(bArrSerialize);
            CryptoUtils.zeroBlock(bArrDigest);
            secretKey.destroyCriticalData();
            if (z) {
                try {
                    obj.getClass().getMethod("destroyCriticalData", null).invoke(obj, null);
                } catch (IllegalAccessException e) {
                    throw new IOException(e.toString());
                } catch (NoSuchMethodException e2) {
                    throw new IOException(e2.toString());
                } catch (InvocationTargetException e3) {
                    throw new IOException(e3.toString());
                }
            }
        } catch (InvalidKeyException e4) {
            throw new IOException(this, e4.toString(), e4) { // from class: iaik.utils.CriticalObject.1
                private static final long serialVersionUID = 718429152709754872L;
                private final InvalidKeyException a;
                private final CriticalObject b;

                {
                    this.b = this;
                    this.a = e4;
                }

                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return this.a;
                }
            };
        } catch (NoSuchAlgorithmException e5) {
            throw new IOException(e5.toString());
        } catch (GeneralSecurityException e6) {
            throw new IOException(e6.toString());
        }
    }

    protected static Object deserialize(byte[] bArr) throws IOException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bArr));
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static void destroy(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            obj.getClass().getMethod("destroyCriticalData", null).invoke(obj, null);
        } catch (Exception unused) {
        }
    }

    public static void destroy(Enumeration enumeration) {
        if (enumeration == null) {
            return;
        }
        while (enumeration.hasMoreElements()) {
            destroy(enumeration.nextElement());
        }
    }

    public static void destroy(Hashtable hashtable) {
        if (hashtable == null) {
            return;
        }
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            Object objRemove = hashtable.remove(objNextElement);
            destroy(objNextElement);
            destroy(objRemove);
        }
        hashtable.clear();
    }

    public static void destroy(Vector vector) {
        if (vector == null) {
            return;
        }
        for (int i = 0; i < vector.size(); i++) {
            Object objElementAt = vector.elementAt(i);
            vector.setElementAt(null, i);
            destroy(objElementAt);
        }
        vector.setSize(0);
        vector.trimToSize();
    }

    public static void destroy(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            bArr[i] = 0;
        }
    }

    public static void destroy(double[] dArr) {
        if (dArr == null) {
            return;
        }
        int length = dArr.length;
        for (int i = 0; i < length; i++) {
            dArr[i] = 0.0d;
        }
    }

    public static void destroy(float[] fArr) {
        if (fArr == null) {
            return;
        }
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            fArr[i] = 0.0f;
        }
    }

    public static void destroy(int[] iArr) {
        if (iArr == null) {
            return;
        }
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            iArr[i] = 0;
        }
    }

    public static void destroy(long[] jArr) {
        if (jArr == null) {
            return;
        }
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            jArr[i] = 0;
        }
    }

    public static void destroy(Object[] objArr) {
        if (objArr == null) {
            return;
        }
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            destroy(objArr[i]);
            objArr[i] = null;
        }
    }

    public static void destroy(boolean[] zArr) {
        if (zArr == null) {
            return;
        }
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            zArr[i] = false;
        }
    }

    protected static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public Object getObject() throws IOException {
        return getObject(this.b);
    }

    public Object getObject(byte[] bArr) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SecurityProvider.ALG_DIGEST_MD5);
            MessageDigest messageDigest2 = MessageDigest.getInstance("SHA");
            byte[] bArrDigest = messageDigest.digest(bArr);
            byte[] bArr2 = new byte[36];
            System.arraycopy(messageDigest2.digest(bArr), 0, bArr2, 0, 20);
            System.arraycopy(bArrDigest, 0, bArr2, 20, 16);
            SecretKey secretKey = new SecretKey(bArr2, 0, a(), Util.getRawCipherName(cipher));
            Cipher cipher2 = Cipher.getInstance(cipher);
            cipher2.init(2, secretKey, new IvParameterSpec(bArrDigest, 0, 8));
            byte[] bArrDoFinal = cipher2.doFinal(this.a);
            Object objDeserialize = deserialize(bArrDoFinal);
            CryptoUtils.zeroBlock(bArrDoFinal);
            CryptoUtils.zeroBlock(bArrDigest);
            CryptoUtils.zeroBlock(bArr);
            secretKey.destroyCriticalData();
            return objDeserialize;
        } catch (InvalidKeyException e) {
            throw new IOException(e.toString());
        } catch (NoSuchAlgorithmException e2) {
            throw new IOException(e2.toString());
        } catch (GeneralSecurityException e3) {
            throw new IOException(e3.toString());
        }
    }
}

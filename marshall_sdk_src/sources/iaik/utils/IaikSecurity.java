package iaik.utils;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes2.dex */
public class IaikSecurity {
    String a;
    String b;
    String c;
    Provider d;

    public IaikSecurity(String str, String str2, String str3) {
        this.a = str;
        this.b = str3;
        this.c = str2;
    }

    private String a() throws NoSuchAlgorithmException, NoSuchProviderException {
        Provider[] providers;
        String str = this.b;
        if (str == null) {
            providers = Security.getProviders();
        } else {
            Provider[] providerArr = {Security.getProvider(str)};
            if (providerArr[0] == null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Provider \"");
                stringBuffer.append(this.b);
                stringBuffer.append("\" not found.");
                throw new NoSuchProviderException(stringBuffer.toString());
            }
            providers = providerArr;
        }
        for (int i = 0; i < providers.length; i++) {
            String strA = a(providers[i]);
            Provider provider = providers[i];
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.c);
            stringBuffer2.append(".");
            stringBuffer2.append(strA);
            String property = provider.getProperty(stringBuffer2.toString());
            if (property != null) {
                this.d = providers[i];
                return property;
            }
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Algorithm ");
        stringBuffer3.append(this.a);
        stringBuffer3.append(" not implemented.");
        throw new NoSuchAlgorithmException(stringBuffer3.toString());
    }

    private String a(Provider provider) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Alg.Alias.");
        stringBuffer.append(this.c);
        stringBuffer.append(".");
        stringBuffer.append(this.a);
        String property = provider.getProperty(stringBuffer.toString());
        return property == null ? this.a : property;
    }

    public static Cipher getCipher(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        return Cipher.getInstance(str, "IAIK");
    }

    public static Cipher getCipher(String str, int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Cipher cipher = getCipher(str);
        if (key != null) {
            if (algorithmParameters != null) {
                if (secureRandom != null) {
                    cipher.init(i, key, algorithmParameters, secureRandom);
                } else {
                    cipher.init(i, key, algorithmParameters);
                }
            } else if (secureRandom != null) {
                cipher.init(i, key, secureRandom);
            } else {
                cipher.init(i, key);
            }
        }
        return cipher;
    }

    public static Cipher getCipher(String str, int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Cipher cipher = getCipher(str);
        if (key != null) {
            if (algorithmParameterSpec != null) {
                if (secureRandom != null) {
                    cipher.init(i, key, algorithmParameterSpec, secureRandom);
                } else {
                    cipher.init(i, key, algorithmParameterSpec);
                }
            } else if (secureRandom != null) {
                cipher.init(i, key, secureRandom);
            } else {
                cipher.init(i, key);
            }
        }
        return cipher;
    }

    public Object getImplementation() throws NoSuchAlgorithmException, NoSuchProviderException {
        String strA = a();
        try {
            ClassLoader classLoader = this.d.getClass().getClassLoader();
            return (classLoader == null ? Class.forName(strA) : classLoader.loadClass(strA)).newInstance();
        } catch (ClassNotFoundException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Class cannot be found.\n");
            stringBuffer.append(e.toString());
            throw new NoSuchAlgorithmException(stringBuffer.toString());
        } catch (IllegalAccessException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Class cannot be accessed.\n");
            stringBuffer2.append(e2.toString());
            throw new NoSuchAlgorithmException(stringBuffer2.toString());
        } catch (InstantiationException e3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Class cannot be instantiated.\n");
            stringBuffer3.append(e3.toString());
            throw new NoSuchAlgorithmException(stringBuffer3.toString());
        }
    }

    public Provider getProvider() {
        return this.d;
    }
}

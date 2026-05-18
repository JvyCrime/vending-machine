package iaik.pkcs.pkcs12;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.pkcs.pkcs7.ContentInfo;
import iaik.pkcs.pkcs7.Data;
import iaik.pkcs.pkcs7.DigestInfo;
import iaik.security.cipher.PBEKeyBMP;
import iaik.security.pbe.PBEGenParameterSpec;
import iaik.security.random.SecRandom;
import iaik.security.spec.PBEKeyAndParameterSpec;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Vector;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PKCS12 {
    public static final int PASSWORD_INTEGRITY_MODE = 2;
    public static final int PUBLIC_KEY_INTEGRITY_MODE = 1;
    static Class f;
    SEQUENCE a;
    protected AuthenticatedSafe[] authenticated_safes;
    ASN1Object b;
    byte[] c;
    PKCS8ShroudedKeyBag[] d;
    boolean e;
    private int g;
    private int h;
    protected int mode;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    private PKCS12() {
        this.mode = 0;
        this.g = 3;
        this.a = null;
        this.b = null;
        this.h = 1024;
    }

    public PKCS12(ASN1Object aSN1Object) throws PKCSParsingException {
        this();
        this.b = aSN1Object;
        decode();
    }

    public PKCS12(KeyBag keyBag, CertificateBag[] certificateBagArr) throws PKCSException {
        this(keyBag, certificateBagArr, true);
    }

    public PKCS12(KeyBag keyBag, CertificateBag[] certificateBagArr, boolean z) throws PKCSException {
        this(new KeyBag[]{keyBag}, certificateBagArr, z);
    }

    public PKCS12(InputStream inputStream) throws PKCSParsingException, IOException {
        this();
        try {
            this.b = DerCoder.decode(inputStream);
            decode();
        } catch (CodingException e) {
            throw new PKCSParsingException(e.getMessage());
        }
    }

    public PKCS12(KeyBag[] keyBagArr, CertificateBag[] certificateBagArr, boolean z) throws PKCSException {
        this();
        this.authenticated_safes = new AuthenticatedSafe[2];
        if (keyBagArr != null && keyBagArr.length > 0) {
            Vector vector = new Vector();
            for (KeyBag keyBag : keyBagArr) {
                if (keyBag != null) {
                    if (keyBag instanceof PKCS8ShroudedKeyBag) {
                        vector.addElement(keyBag);
                    } else {
                        vector.addElement(new PKCS8ShroudedKeyBag(keyBag));
                    }
                }
            }
            PKCS8ShroudedKeyBag[] pKCS8ShroudedKeyBagArr = new PKCS8ShroudedKeyBag[vector.size()];
            this.d = pKCS8ShroudedKeyBagArr;
            vector.copyInto(pKCS8ShroudedKeyBagArr);
        }
        if (certificateBagArr != null && certificateBagArr.length > 0) {
            this.authenticated_safes[1] = new AuthenticatedSafe(2, certificateBagArr, this.h);
        }
        this.mode = 2;
        this.e = z;
    }

    private void a(char[] cArr) throws PKCSException {
        try {
            PBEGenParameterSpec pBEGenParameterSpec = new PBEGenParameterSpec(8, this.e ? 2000 : 1);
            AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("PBE");
            algorithmParameterGenerator.init(pBEGenParameterSpec);
            AlgorithmParameters algorithmParametersGenerateParameters = algorithmParameterGenerator.generateParameters();
            Class clsClass$ = f;
            if (clsClass$ == null) {
                clsClass$ = class$("javax.crypto.spec.PBEParameterSpec");
                f = clsClass$;
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParametersGenerateParameters.getParameterSpec(clsClass$);
            PBEKeyAndParameterSpec pBEKeyAndParameterSpec = new PBEKeyAndParameterSpec(PBEKeyBMP.getEncoded(cArr), pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount(), 20);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("PKCS#12-MAC");
            keyGenerator.init(pBEKeyAndParameterSpec);
            SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
            Mac mac = Mac.getInstance("HMAC/SHA");
            mac.init(secretKeyGenerateKey);
            DigestInfo digestInfo = new DigestInfo(AlgorithmID.sha, mac.doFinal(this.c));
            SEQUENCE sequence = new SEQUENCE();
            this.a = sequence;
            sequence.addComponent(digestInfo.toASN1Object());
            this.a.addComponent(new OCTET_STRING(pBEParameterSpec.getSalt()));
            if (pBEParameterSpec.getIterationCount() != 1) {
                this.a.addComponent(new INTEGER(pBEParameterSpec.getIterationCount()));
            }
        } catch (Exception e) {
            throw new PKCSException(this, e.getMessage(), e) { // from class: iaik.pkcs.pkcs12.PKCS12.2
                private final Exception a;
                private final PKCS12 b;

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

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    protected void decode() throws PKCSParsingException {
        try {
            if (this.b.getComponentAt(0).isA(ASN.CON_SPEC)) {
                throw new PKCSParsingException("Key format of Netscape 4.03 and earlier not supportet!");
            }
            this.g = ((BigInteger) this.b.getComponentAt(0).getValue()).intValue();
            ContentInfo contentInfo = new ContentInfo(this.b.getComponentAt(1));
            if (this.b.countComponents() == 3) {
                this.a = (SEQUENCE) this.b.getComponentAt(2);
            }
            if (!contentInfo.getContentType().equals(ObjectID.pkcs7_data)) {
                if (!contentInfo.getContentType().equals(ObjectID.pkcs7_signedData)) {
                    throw new PKCSParsingException("PKCS#12: Unknown mode!");
                }
                this.mode = 1;
                return;
            }
            this.mode = 2;
            byte[] data = ((Data) contentInfo.getContent()).getData();
            this.c = data;
            ASN1Object aSN1ObjectDecode = DerCoder.decode(data);
            this.authenticated_safes = new AuthenticatedSafe[aSN1ObjectDecode.countComponents()];
            for (int i = 0; i < aSN1ObjectDecode.countComponents(); i++) {
                this.authenticated_safes[i] = new AuthenticatedSafe(aSN1ObjectDecode.getComponentAt(i));
            }
        } catch (CodingException e) {
            throw new PKCSParsingException(e.toString());
        } catch (PKCSException e2) {
            throw new PKCSParsingException(e2.toString());
        } catch (ClassCastException e3) {
            throw new PKCSParsingException(e3.toString());
        }
    }

    public void decrypt(char[] cArr) throws PKCSException {
        int i = 0;
        while (true) {
            try {
                AuthenticatedSafe[] authenticatedSafeArr = this.authenticated_safes;
                if (i >= authenticatedSafeArr.length) {
                    return;
                }
                authenticatedSafeArr[i].decrypt(cArr);
                i++;
            } catch (NoSuchAlgorithmException e) {
                throw new PKCSException(e.toString());
            }
        }
    }

    public void encrypt(char[] cArr) throws PKCSException {
        encrypt(cArr, null, null);
    }

    public void encrypt(char[] cArr, AlgorithmID algorithmID, AlgorithmID algorithmID2) throws PKCSException {
        PKCS8ShroudedKeyBag[] pKCS8ShroudedKeyBagArr;
        if (algorithmID == null) {
            algorithmID = (AlgorithmID) AlgorithmID.pbeWithSHAAnd40BitRC2_CBC.clone();
        }
        if (algorithmID2 == null) {
            algorithmID2 = (AlgorithmID) AlgorithmID.pbeWithSHAAnd3_KeyTripleDES_CBC.clone();
        }
        try {
            boolean z = true;
            int i = this.e ? 2000 : 1;
            PBEGenParameterSpec pBEGenParameterSpec = new PBEGenParameterSpec(8, i);
            SecureRandom secureRandom = SecRandom.getDefault();
            AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("PBE");
            algorithmParameterGenerator.init(pBEGenParameterSpec, secureRandom);
            algorithmID.setAlgorithmParameters(algorithmParameterGenerator.generateParameters());
            PKCS8ShroudedKeyBag[] pKCS8ShroudedKeyBagArr2 = this.d;
            if (pKCS8ShroudedKeyBagArr2 != null && pKCS8ShroudedKeyBagArr2.length > 0) {
                int i2 = 0;
                while (true) {
                    pKCS8ShroudedKeyBagArr = this.d;
                    if (i2 >= pKCS8ShroudedKeyBagArr.length) {
                        break;
                    }
                    pKCS8ShroudedKeyBagArr[i2].encrypt(cArr, algorithmID2, secureRandom, i);
                    i2++;
                }
                this.authenticated_safes[0] = new AuthenticatedSafe(1, pKCS8ShroudedKeyBagArr, this.h);
            }
            int i3 = 0;
            while (true) {
                AuthenticatedSafe[] authenticatedSafeArr = this.authenticated_safes;
                if (i3 >= authenticatedSafeArr.length) {
                    break;
                }
                if (authenticatedSafeArr[i3] != null) {
                    authenticatedSafeArr[i3].setBlockSize(this.h);
                    this.authenticated_safes[i3].encrypt(cArr, algorithmID);
                }
                i3++;
            }
            if (this.mode == 2) {
                try {
                    SEQUENCE sequence = new SEQUENCE();
                    int i4 = 0;
                    while (true) {
                        AuthenticatedSafe[] authenticatedSafeArr2 = this.authenticated_safes;
                        if (i4 >= authenticatedSafeArr2.length) {
                            break;
                        }
                        if (authenticatedSafeArr2[i4] != null) {
                            sequence.addComponent(authenticatedSafeArr2[i4].toASN1Object());
                        }
                        i4++;
                    }
                    if (sequence.countComponents() == 0) {
                        throw new PKCSException("No key/cert bags set!");
                    }
                    sequence.setIndefiniteLength(this.h > 0);
                    byte[] bArrEncode = DerCoder.encode(sequence);
                    this.c = bArrEncode;
                    Data data = new Data(bArrEncode);
                    data.setBlockSize(this.h);
                    ContentInfo contentInfo = new ContentInfo(data);
                    a(cArr);
                    SEQUENCE sequence2 = new SEQUENCE();
                    this.b = sequence2;
                    sequence2.addComponent(new INTEGER(this.g));
                    this.b.addComponent(contentInfo.toASN1Object());
                    SEQUENCE sequence3 = this.a;
                    if (sequence3 != null) {
                        this.b.addComponent(sequence3);
                    }
                    ASN1Object aSN1Object = this.b;
                    if (this.h <= 0) {
                        z = false;
                    }
                    aSN1Object.setIndefiniteLength(z);
                } catch (CodingException e) {
                    throw new RuntimeException(e.toString());
                }
            }
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InternalErrorException(e2);
        } catch (NoSuchAlgorithmException e3) {
            throw new InternalErrorException(e3.toString());
        }
    }

    public AuthenticatedSafe[] getAuthenticatedSafes() {
        return this.authenticated_safes;
    }

    public CertificateBag[] getCertificateBags() {
        Vector vector = new Vector();
        int i = 0;
        while (true) {
            AuthenticatedSafe[] authenticatedSafeArr = this.authenticated_safes;
            if (i >= authenticatedSafeArr.length) {
                CertificateBag[] certificateBagArr = new CertificateBag[vector.size()];
                vector.copyInto(certificateBagArr);
                return certificateBagArr;
            }
            SafeBag[] safeBags = authenticatedSafeArr[i].getSafeBags();
            for (int i2 = 0; i2 < safeBags.length; i2++) {
                if (safeBags[i2] instanceof CertificateBag) {
                    vector.addElement(safeBags[i2]);
                }
            }
            i++;
        }
    }

    public KeyBag getKeyBag() {
        int i = 0;
        while (true) {
            AuthenticatedSafe[] authenticatedSafeArr = this.authenticated_safes;
            if (i >= authenticatedSafeArr.length) {
                return null;
            }
            SafeBag[] safeBags = authenticatedSafeArr[i].getSafeBags();
            for (int i2 = 0; i2 < safeBags.length; i2++) {
                if (safeBags[i2] instanceof KeyBag) {
                    return (KeyBag) safeBags[i2];
                }
            }
            i++;
        }
    }

    public KeyBag[] getKeyBags() {
        Vector vector = new Vector(2);
        int i = 0;
        while (true) {
            AuthenticatedSafe[] authenticatedSafeArr = this.authenticated_safes;
            if (i >= authenticatedSafeArr.length) {
                KeyBag[] keyBagArr = new KeyBag[vector.size()];
                vector.copyInto(keyBagArr);
                return keyBagArr;
            }
            SafeBag[] safeBags = authenticatedSafeArr[i].getSafeBags();
            for (int i2 = 0; i2 < safeBags.length; i2++) {
                if (safeBags[i2] instanceof KeyBag) {
                    vector.addElement(safeBags[i2]);
                }
            }
            i++;
        }
    }

    public void setBlockSize(int i) {
        this.h = i;
    }

    public ASN1Object toASN1Object() throws PKCSException {
        return this.b;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PKCS#12 object:\n");
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.g);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        if (this.authenticated_safes == null) {
            stringBuffer.append("No content.\n");
        } else {
            for (int i = 0; i < this.authenticated_safes.length; i++) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("AuthenticatedSafe: ");
                stringBuffer3.append(i);
                stringBuffer3.append("\n");
                stringBuffer.append(stringBuffer3.toString());
                stringBuffer.append(this.authenticated_safes[i].toString());
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }

    public boolean verify(char[] cArr) throws PKCSException {
        try {
            DigestInfo digestInfo = new DigestInfo(this.a.getComponentAt(0));
            PBEKeyAndParameterSpec pBEKeyAndParameterSpec = new PBEKeyAndParameterSpec(PBEKeyBMP.getEncoded(cArr), (byte[]) this.a.getComponentAt(1).getValue(), this.a.countComponents() > 2 ? ((BigInteger) this.a.getComponentAt(2).getValue()).intValue() : 1, 20);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("PKCS#12-MAC");
            keyGenerator.init(pBEKeyAndParameterSpec);
            SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
            Mac mac = Mac.getInstance("HMAC/SHA");
            mac.init(secretKeyGenerateKey);
            return CryptoUtils.secureEqualsBlock(mac.doFinal(this.c), digestInfo.getDigest());
        } catch (Exception e) {
            throw new PKCSException(this, e.getMessage(), e) { // from class: iaik.pkcs.pkcs12.PKCS12.1
                private final Exception a;
                private final PKCS12 b;

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

    public void writeTo(OutputStream outputStream) throws IOException {
        DerCoder.encodeTo(this.b, outputStream);
    }
}

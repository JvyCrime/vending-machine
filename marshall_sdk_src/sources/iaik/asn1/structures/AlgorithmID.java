package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.DerInputStream;
import iaik.asn1.NULL;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.pkcs.pkcs1.MaskGenerationAlgorithm;
import iaik.security.ssl.SecurityProvider;
import iaik.utils.CryptoUtils;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;

/* JADX INFO: loaded from: classes.dex */
public class AlgorithmID implements ASN1Type, Cloneable {
    static Class a = null;
    public static final AlgorithmID aes128_CBC;
    public static final AlgorithmID aes128_CCM;
    public static final AlgorithmID aes128_GCM;
    public static final AlgorithmID aes192_CBC;
    public static final AlgorithmID aes192_CCM;
    public static final AlgorithmID aes192_GCM;
    public static final AlgorithmID aes256_CBC;
    public static final AlgorithmID aes256_CCM;
    public static final AlgorithmID aes256_GCM;
    public static final AlgorithmID arcfour;
    static Class b = null;
    static Class c = null;
    public static final AlgorithmID camellia128_CBC;
    public static final AlgorithmID camellia192_CBC;
    public static final AlgorithmID camellia256_CBC;
    public static final AlgorithmID cast5_CBC;
    public static final AlgorithmID cms_3DES_wrap;
    public static final AlgorithmID cms_HMACwith3DES_wrap;
    public static final AlgorithmID cms_HMACwithAES_wrap;
    public static final AlgorithmID cms_aes128_wrap;
    public static final AlgorithmID cms_aes192_wrap;
    public static final AlgorithmID cms_aes256_wrap;
    public static final AlgorithmID cms_camellia128_wrap;
    public static final AlgorithmID cms_camellia192_wrap;
    public static final AlgorithmID cms_camellia256_wrap;
    public static final AlgorithmID cms_cast5_wrap;
    public static final AlgorithmID cms_idea_wrap;
    public static final AlgorithmID cms_rc2_wrap;
    static Class d = null;
    public static final AlgorithmID des_CBC;
    public static final AlgorithmID des_EDE3_CBC;
    public static final AlgorithmID dhKeyAgreement;
    public static AlgorithmID dhSinglePass_cofactorDH_sha1kdf_scheme = null;
    public static AlgorithmID dhSinglePass_stdDH_sha1kdf_scheme = null;
    public static AlgorithmID dhSinglePass_stdDH_sha256kdf_scheme = null;
    public static AlgorithmID dhSinglePass_stdDH_sha384kdf_scheme = null;
    public static final AlgorithmID dsa;
    public static final AlgorithmID dsaWithSHA;
    public static final AlgorithmID dsaWithSHA1;
    public static final AlgorithmID dsaWithSHA1_;
    public static final AlgorithmID dsaWithSHA224;
    public static final AlgorithmID dsaWithSHA256;
    public static final AlgorithmID dsa_;
    public static final AlgorithmID dsa_With_SHA1;
    static Class e = null;
    public static final AlgorithmID ecDH;
    public static final AlgorithmID ecPublicKey;
    public static final AlgorithmID ecdsa;
    public static final AlgorithmID ecdsa_With_SHA1;
    public static final AlgorithmID ecdsa_With_SHA224;
    public static final AlgorithmID ecdsa_With_SHA256;
    public static final AlgorithmID ecdsa_With_SHA384;
    public static final AlgorithmID ecdsa_With_SHA512;
    public static final AlgorithmID ecdsa_plain_With_RIPEMD160;
    public static final AlgorithmID ecdsa_plain_With_SHA1;
    public static final AlgorithmID ecdsa_plain_With_SHA224;
    public static final AlgorithmID ecdsa_plain_With_SHA256;
    public static final AlgorithmID ecdsa_plain_With_SHA384;
    public static final AlgorithmID ecdsa_plain_With_SHA512;
    public static final AlgorithmID elGamal;
    public static final AlgorithmID esdh;
    public static final AlgorithmID esdhKeyAgreement;
    static Class f = null;
    static Class g = null;
    public static final AlgorithmID gost3410;
    public static final AlgorithmID gost3411;
    static Class h = null;
    public static final AlgorithmID hMAC_GOST;
    public static final AlgorithmID hMAC_MD5;
    public static final AlgorithmID hMAC_RIPEMD160;
    public static final AlgorithmID hMAC_SHA1;
    public static final AlgorithmID hMAC_SHA1_;
    public static final AlgorithmID hMAC_SHA224;
    public static final AlgorithmID hMAC_SHA256;
    public static final AlgorithmID hMAC_SHA384;
    public static final AlgorithmID hMAC_SHA512;
    static Class i = null;
    public static final AlgorithmID idea_CBC;
    static Class j = null;
    static Class k = null;
    static Class l = null;
    static Class m = null;
    public static final AlgorithmID md2;
    public static final AlgorithmID md2WithRSAEncryption;
    public static final AlgorithmID md4;
    public static final AlgorithmID md4WithRSAEncryption;
    public static final AlgorithmID md5;
    public static final AlgorithmID md5WithRSAEncryption;
    public static final AlgorithmID mgf1;
    static Class n = null;
    public static final AlgorithmID pSpecified;
    public static final AlgorithmID pbeWithMD5AndDES_CBC;
    public static final AlgorithmID pbeWithSHAAnd128BitRC2_CBC;
    public static final AlgorithmID pbeWithSHAAnd128BitRC4;
    public static final AlgorithmID pbeWithSHAAnd2_KeyTripleDES_CBC;
    public static final AlgorithmID pbeWithSHAAnd3_KeyTripleDES_CBC;
    public static final AlgorithmID pbeWithSHAAnd40BitRC2_CBC;
    public static final AlgorithmID pbeWithSHAAnd40BitRC4;
    public static final AlgorithmID pbes2;
    public static final AlgorithmID pbkdf2;
    public static final AlgorithmID pwri_kek;
    public static final AlgorithmID rc2_CBC;
    public static final AlgorithmID rc4;
    public static final AlgorithmID rc5_CBC;
    public static final AlgorithmID ripeMd128;
    public static final AlgorithmID ripeMd128_ISO;
    public static final AlgorithmID ripeMd160;
    public static final AlgorithmID ripeMd160_ISO;
    public static final AlgorithmID ripeMd256;
    public static final AlgorithmID rsaEncryption;
    public static final AlgorithmID rsaSignatureWithRipemd128;
    public static final AlgorithmID rsaSignatureWithRipemd160;
    public static final AlgorithmID rsaSignatureWithRipemd256;
    public static final AlgorithmID rsaesOAEP;
    public static final AlgorithmID rsassaPss;
    public static final AlgorithmID sha;
    public static final AlgorithmID sha1;
    public static final AlgorithmID sha1WithRSAEncryption;
    public static final AlgorithmID sha1WithRSAEncryption_;
    public static final AlgorithmID sha224;
    public static final AlgorithmID sha224WithRSAEncryption;
    public static final AlgorithmID sha256;
    public static final AlgorithmID sha256WithRSAEncryption;
    public static final AlgorithmID sha384;
    public static final AlgorithmID sha384WithRSAEncryption;
    public static final AlgorithmID sha3_224;
    public static final AlgorithmID sha3_256;
    public static final AlgorithmID sha3_384;
    public static final AlgorithmID sha3_512;
    public static final AlgorithmID sha512;
    public static final AlgorithmID sha512WithRSAEncryption;
    public static final AlgorithmID sha512_224;
    public static final AlgorithmID sha512_256;
    public static final AlgorithmID shake128;
    public static final AlgorithmID shake256;
    public static final AlgorithmID sigS_ISO9796_2Withripemd160;
    public static final AlgorithmID sigS_ISO9796_2Withrsa;
    public static final AlgorithmID sigS_ISO9796_2Withsha1;
    public static final AlgorithmID sigS_ISO9796_2rndWithripemd160;
    public static final AlgorithmID sigS_ISO9796_2rndWithrsa;
    public static final AlgorithmID sigS_ISO9796_2rndWithsha1;
    public static final AlgorithmID ssdhKeyAgreement;
    public static final AlgorithmID whirlpool;
    public static final AlgorithmID zlib_compress;
    private ObjectID q;
    private ASN1 r;
    private boolean s;
    protected static final Hashtable implementations = new Hashtable(100);
    protected static final Hashtable algorithms = new Hashtable(100);
    protected static final Hashtable algorithmParameterSpecs = new Hashtable();
    private static Hashtable p = new Hashtable();
    private static boolean o = true;
    public static final AlgorithmID rsa = new AlgorithmID("2.5.8.1.1", "RSA", "RSA", (Class) null, o);

    static {
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.security.elgamal.ElGamalParameterSpec");
            a = clsClass$;
        }
        elGamal = new AlgorithmID("1.3.14.7.2.1.1", "ElGamal", "ElGamal", clsClass$);
        Class clsClass$2 = b;
        if (clsClass$2 == null) {
            clsClass$2 = class$("javax.crypto.spec.RC2ParameterSpec");
            b = clsClass$2;
        }
        rc2_CBC = new AlgorithmID("1.2.840.113549.3.2", "RC2-CBC", "RC2/CBC/PKCS5Padding", clsClass$2, o);
        AlgorithmID algorithmID = new AlgorithmID("1.2.840.113549.3.4", "ARCFOUR", "ARCFOUR/ECB/NoPadding", (Class) null, o);
        arcfour = algorithmID;
        rc4 = algorithmID;
        Class clsClass$3 = c;
        if (clsClass$3 == null) {
            clsClass$3 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$3;
        }
        des_EDE3_CBC = new AlgorithmID("1.2.840.113549.3.7", "DES-EDE3-CBC", "DESede/CBC/PKCS5Padding", clsClass$3, o);
        Class clsClass$4 = c;
        if (clsClass$4 == null) {
            clsClass$4 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$4;
        }
        des_CBC = new AlgorithmID("1.3.14.3.2.7", "DES-CBC", "DES/CBC/PKCS5Padding", clsClass$4, o);
        Class clsClass$5 = c;
        if (clsClass$5 == null) {
            clsClass$5 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$5;
        }
        idea_CBC = new AlgorithmID("1.3.6.1.4.1.188.7.1.1.2", "IDEA-CBC", "IDEA/CBC/PKCS5Padding", clsClass$5, o);
        String[] strArr = {"AES128/CBC/PKCS5Padding", SecurityProvider.ALG_CIPHER_AES_PKCS5};
        Class clsClass$6 = c;
        if (clsClass$6 == null) {
            clsClass$6 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$6;
        }
        aes128_CBC = new AlgorithmID("2.16.840.1.101.3.4.1.2", "AES128-CBC", strArr, clsClass$6, o);
        Class clsClass$7 = c;
        if (clsClass$7 == null) {
            clsClass$7 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$7;
        }
        aes192_CBC = new AlgorithmID("2.16.840.1.101.3.4.1.22", "AES192-CBC", "AES192/CBC/PKCS5Padding", clsClass$7, o);
        Class clsClass$8 = c;
        if (clsClass$8 == null) {
            clsClass$8 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$8;
        }
        aes256_CBC = new AlgorithmID("2.16.840.1.101.3.4.1.42", "AES256-CBC", "AES256/CBC/PKCS5Padding", clsClass$8, o);
        String[] strArr2 = {"Camellia128/CBC/PKCS5Padding", "Camellia/CBC/PKCS5Padding"};
        Class clsClass$9 = c;
        if (clsClass$9 == null) {
            clsClass$9 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$9;
        }
        camellia128_CBC = new AlgorithmID("1.2.392.200011.61.1.1.1.2", "Camellia128-CBC", strArr2, clsClass$9, o);
        Class clsClass$10 = c;
        if (clsClass$10 == null) {
            clsClass$10 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$10;
        }
        camellia192_CBC = new AlgorithmID("1.2.392.200011.61.1.1.1.3", "Camellia192-CBC", "Camellia192/CBC/PKCS5Padding", clsClass$10, o);
        Class clsClass$11 = c;
        if (clsClass$11 == null) {
            clsClass$11 = class$("javax.crypto.spec.IvParameterSpec");
            c = clsClass$11;
        }
        camellia256_CBC = new AlgorithmID("1.2.392.200011.61.1.1.1.4", "Camellia256-CBC", "Camellia256/CBC/PKCS5Padding", clsClass$11, o);
        Class clsClass$12 = d;
        if (clsClass$12 == null) {
            clsClass$12 = class$("iaik.security.cipher.CAST128ParameterSpec");
            d = clsClass$12;
        }
        cast5_CBC = new AlgorithmID("1.2.840.113533.7.66.10", "CAST5-CBC", "CAST5/CBC/PKCS5Padding", clsClass$12, o);
        Class clsClass$13 = e;
        if (clsClass$13 == null) {
            clsClass$13 = class$("javax.crypto.spec.RC5ParameterSpec");
            e = clsClass$13;
        }
        rc5_CBC = new AlgorithmID("1.2.840.113549.3.8", "RC5-CBC", "RC5/CBC/PKCS5Padding", clsClass$13, o);
        String[] strArr3 = {"AES128/CCM/NoPadding", "AES/CCM/NoPadding"};
        Class clsClass$14 = f;
        if (clsClass$14 == null) {
            clsClass$14 = class$("iaik.security.cipher.CCMParameterSpec");
            f = clsClass$14;
        }
        aes128_CCM = new AlgorithmID("2.16.840.1.101.3.4.1.7", "AES128-CCM", strArr3, clsClass$14, o);
        Class clsClass$15 = f;
        if (clsClass$15 == null) {
            clsClass$15 = class$("iaik.security.cipher.CCMParameterSpec");
            f = clsClass$15;
        }
        aes192_CCM = new AlgorithmID("2.16.840.1.101.3.4.1.27", "AES192-CCM", "AES192/CCM/NoPadding", clsClass$15, o);
        Class clsClass$16 = f;
        if (clsClass$16 == null) {
            clsClass$16 = class$("iaik.security.cipher.CCMParameterSpec");
            f = clsClass$16;
        }
        aes256_CCM = new AlgorithmID("2.16.840.1.101.3.4.1.47", "AES256-CCM", "AES256/CCM/NoPadding", clsClass$16, o);
        String[] strArr4 = {"AES128/GCM/NoPadding", SecurityProvider.ALG_CIPHER_AES_GCM};
        Class clsClass$17 = g;
        if (clsClass$17 == null) {
            clsClass$17 = class$("iaik.security.cipher.GCMParameterSpec");
            g = clsClass$17;
        }
        aes128_GCM = new AlgorithmID("2.16.840.1.101.3.4.1.6", "AES128-GCM", strArr4, clsClass$17, o);
        Class clsClass$18 = g;
        if (clsClass$18 == null) {
            clsClass$18 = class$("iaik.security.cipher.GCMParameterSpec");
            g = clsClass$18;
        }
        aes192_GCM = new AlgorithmID("2.16.840.1.101.3.4.1.26", "AES192-GCM", "AES192/GCM/NoPadding", clsClass$18, o);
        Class clsClass$19 = g;
        if (clsClass$19 == null) {
            clsClass$19 = class$("iaik.security.cipher.GCMParameterSpec");
            g = clsClass$19;
        }
        aes256_GCM = new AlgorithmID("2.16.840.1.101.3.4.1.46", "AES256-GCM", "AES256/GCM/NoPadding", clsClass$19, o);
        dhKeyAgreement = new AlgorithmID("1.2.840.113549.1.3.1", "DH Key Agreement", new String[]{"DH", "DiffieHellmann"}, (Class) null, o);
        esdhKeyAgreement = new AlgorithmID("1.2.840.113549.1.9.16.3.5", "ESDH Key Agreement", "ESDH", (Class) null, o);
        esdh = new AlgorithmID("1.2.840.10046.2.1", "ESDH", "ESDH", (Class) null, o);
        ssdhKeyAgreement = new AlgorithmID("1.2.840.113549.1.9.16.3.10", "SSDH Key Agreement", "SSDH", (Class) null, o);
        rsaEncryption = new AlgorithmID("1.2.840.113549.1.1.1", "rsaEncryption", "RSA", (Class) null, true);
        md2WithRSAEncryption = new AlgorithmID("1.2.840.113549.1.1.2", "md2WithRSAEncryption", new String[]{"MD2/RSA", "MD2withRSA"}, (Class) null, true);
        String str = (String) null;
        md4WithRSAEncryption = new AlgorithmID("1.2.840.113549.1.1.3", "md4WithRSAEncryption", str, (Class) null, true);
        md5WithRSAEncryption = new AlgorithmID("1.2.840.113549.1.1.4", "md5WithRSAEncryption", new String[]{"MD5/RSA", SecurityProvider.ALG_SIGNATURE_MD5RSA}, (Class) null, true);
        sha1WithRSAEncryption_ = new AlgorithmID("1.3.14.3.2.29", "sha1WithRSAEncryption", "SHA1/RSA", null, true, false);
        sha1WithRSAEncryption = new AlgorithmID("1.2.840.113549.1.1.5", "sha1WithRSAEncryption", new String[]{"SHA1/RSA", "SHA-1/RSA", "SHA/RSA", SecurityProvider.ALG_SIGNATURE_SHA1RSA}, (Class) null, true);
        sha224WithRSAEncryption = new AlgorithmID("1.2.840.113549.1.1.14", "sha224WithRSAEncryption", new String[]{"SHA224/RSA", "SHA-224/RSA", SecurityProvider.ALG_SIGNATURE_SHA224RSA}, (Class) null, true);
        sha256WithRSAEncryption = new AlgorithmID("1.2.840.113549.1.1.11", "sha256WithRSAEncryption", new String[]{"SHA256/RSA", "SHA-256/RSA", SecurityProvider.ALG_SIGNATURE_SHA256RSA}, (Class) null, true);
        sha384WithRSAEncryption = new AlgorithmID("1.2.840.113549.1.1.12", "sha384WithRSAEncryption", new String[]{"SHA384/RSA", "SHA-384/RSA", SecurityProvider.ALG_SIGNATURE_SHA384RSA}, (Class) null, true);
        sha512WithRSAEncryption = new AlgorithmID("1.2.840.113549.1.1.13", "sha512WithRSAEncryption", new String[]{"SHA512/RSA", "SHA-512/RSA", SecurityProvider.ALG_SIGNATURE_SHA512RSA}, (Class) null, true);
        Class clsClass$20 = h;
        if (clsClass$20 == null) {
            clsClass$20 = class$("java.security.spec.DSAParameterSpec");
            h = clsClass$20;
        }
        dsa_ = new AlgorithmID("1.3.14.3.2.12", "DSA", "DSA", clsClass$20, o, false);
        Class clsClass$21 = h;
        if (clsClass$21 == null) {
            clsClass$21 = class$("java.security.spec.DSAParameterSpec");
            h = clsClass$21;
        }
        dsa = new AlgorithmID("1.2.840.10040.4.1", "DSA", "DSA", clsClass$21, o);
        Class clsClass$22 = h;
        if (clsClass$22 == null) {
            clsClass$22 = class$("java.security.spec.DSAParameterSpec");
            h = clsClass$22;
        }
        dsa_With_SHA1 = new AlgorithmID("1.3.14.3.2.13", "dsaWithSHA1", "SHA-1/DSA", clsClass$22, false, false);
        String[] strArr5 = {"SHA-1/DSA", "SHA1/DSA", "SHA/DSA", SecurityProvider.ALG_SIGNATURE_SHADSA};
        Class clsClass$23 = h;
        if (clsClass$23 == null) {
            clsClass$23 = class$("java.security.spec.DSAParameterSpec");
            h = clsClass$23;
        }
        AlgorithmID algorithmID2 = new AlgorithmID("1.2.840.10040.4.3", "dsaWithSHA", strArr5, clsClass$23, false);
        dsaWithSHA = algorithmID2;
        dsaWithSHA1 = algorithmID2;
        Class clsClass$24 = h;
        if (clsClass$24 == null) {
            clsClass$24 = class$("java.security.spec.DSAParameterSpec");
            h = clsClass$24;
        }
        dsaWithSHA1_ = new AlgorithmID("1.3.14.3.2.27", "dsaWithSHA1", "SHA-1/DSA", clsClass$24, false, false);
        String[] strArr6 = {"SHA224withDSA", "SHA-224/DSA", "SHA224/DSA"};
        Class clsClass$25 = h;
        if (clsClass$25 == null) {
            clsClass$25 = class$("java.security.spec.DSAParameterSpec");
            h = clsClass$25;
        }
        dsaWithSHA224 = new AlgorithmID("2.16.840.1.101.3.4.3.1", "dsaWithSHA224", strArr6, clsClass$25, false);
        String[] strArr7 = {"SHA256withDSA", "SHA-256/DSA", "SHA256/DSA"};
        Class clsClass$26 = h;
        if (clsClass$26 == null) {
            clsClass$26 = class$("java.security.spec.DSAParameterSpec");
            h = clsClass$26;
        }
        dsaWithSHA256 = new AlgorithmID("2.16.840.1.101.3.4.3.2", "dsaWithSHA256", strArr7, clsClass$26, false);
        AlgorithmID algorithmID3 = new AlgorithmID("1.2.840.10045.2.1", "ECPublicKey", "ECDSA", (Class) null, o);
        ecdsa = algorithmID3;
        ecPublicKey = algorithmID3;
        ecDH = new AlgorithmID("1.3.132.1.12", "ecDH", SecurityProvider.ALG_KEYEX_ECDH, (Class) null, o);
        dhSinglePass_stdDH_sha1kdf_scheme = new AlgorithmID("1.3.133.16.840.63.0.2", "STD-ECDH", SecurityProvider.ALG_KEYEX_ECDH);
        dhSinglePass_cofactorDH_sha1kdf_scheme = new AlgorithmID("1.3.133.16.840.63.0.3", "COFACTOR-ECDH", "ECDHwithCofactor");
        dhSinglePass_stdDH_sha256kdf_scheme = new AlgorithmID("1.3.132.1.11.1", "STD-ECDH", SecurityProvider.ALG_KEYEX_ECDH);
        dhSinglePass_stdDH_sha384kdf_scheme = new AlgorithmID("1.3.132.1.11.2", "STD-ECDH", SecurityProvider.ALG_KEYEX_ECDH);
        gost3410 = new AlgorithmID("1.2.643.2.2.19", "gostR3410-2001", "GOST3410", (Class) null, false);
        ecdsa_With_SHA1 = new AlgorithmID("1.2.840.10045.4.1", "ecdsaWithSHA1", new String[]{"SHA1withECDSA", "SHA1/ECDSA", "SHA-1/ECDSA"}, (Class) null, false);
        ecdsa_With_SHA224 = new AlgorithmID("1.2.840.10045.4.3.1", "ecdsaWithSHA224", new String[]{SecurityProvider.ALG_SIGNATURE_SHA224ECDSA, "SHA224/ECDSA", "SHA-224/ECDSA"}, (Class) null, false);
        ecdsa_With_SHA256 = new AlgorithmID("1.2.840.10045.4.3.2", "ecdsaWithSHA256", new String[]{SecurityProvider.ALG_SIGNATURE_SHA256ECDSA, "SHA256/ECDSA", "SHA-256/ECDSA"}, (Class) null, false);
        ecdsa_With_SHA384 = new AlgorithmID("1.2.840.10045.4.3.3", "ecdsaWithSHA384", new String[]{SecurityProvider.ALG_SIGNATURE_SHA384ECDSA, "SHA384/ECDSA", "SHA-384/ECDSA"}, (Class) null, false);
        ecdsa_With_SHA512 = new AlgorithmID("1.2.840.10045.4.3.4", "ecdsaWithSHA512", new String[]{SecurityProvider.ALG_SIGNATURE_SHA512ECDSA, "SHA512/ECDSA", "SHA-512/ECDSA"}, (Class) null, false);
        ecdsa_plain_With_SHA1 = new AlgorithmID("0.4.0.127.0.7.1.1.4.1.1", "ecdsaPlainWithSHA1", "SHA1withECDSAPlain", (Class) null, false);
        ecdsa_plain_With_SHA224 = new AlgorithmID("0.4.0.127.0.7.1.1.4.1.2", "ecdsaPlainWithSHA224", "SHA224withECDSAPlain", (Class) null, false);
        ecdsa_plain_With_SHA256 = new AlgorithmID("0.4.0.127.0.7.1.1.4.1.3", "ecdsaPlainWithSHA256", "SHA256withECDSAPlain", (Class) null, false);
        ecdsa_plain_With_SHA384 = new AlgorithmID("0.4.0.127.0.7.1.1.4.1.4", "ecdsaPlainWithSHA384", "SHA384withECDSAPlain", (Class) null, false);
        ecdsa_plain_With_SHA512 = new AlgorithmID("0.4.0.127.0.7.1.1.4.1.5", "ecdsaPlainWithSHA512", "SHA512withECDSAPlain", (Class) null, false);
        ecdsa_plain_With_RIPEMD160 = new AlgorithmID("0.4.0.127.0.7.1.1.4.1.6", "ecdsaPlainWithRIPEMD160", "RIPEMD160withECDSAPlain", (Class) null, false);
        rsaSignatureWithRipemd160 = new AlgorithmID("1.3.36.3.3.1.2", "rsaSignatureWithRipemd160", "RIPEMD160/RSA", (Class) null, o);
        rsaSignatureWithRipemd128 = new AlgorithmID("1.3.36.3.3.1.3", "rsaSignatureWithRipemd128", "RIPEMD128/RSA", (Class) null, o);
        rsaSignatureWithRipemd256 = new AlgorithmID("1.3.36.3.3.1.4", "rsaSignatureWithRipemd256", "RIPEMD256/RSA", (Class) null, o);
        sigS_ISO9796_2Withrsa = new AlgorithmID("1.3.36.3.4.2.2", "RSA-ISO9796-2-2-3", "RSA-ISO9796-2-2-3", (Class) null, o);
        sigS_ISO9796_2Withsha1 = new AlgorithmID("1.3.36.3.4.2.2.1", "SHAandMGF1/RSA-ISO9796-2-2-3", "SHAandMGF1/RSA-ISO9796-2-2-3", (Class) null, o);
        sigS_ISO9796_2Withripemd160 = new AlgorithmID("1.3.36.3.4.2.2.2", "RSA-ISO9796_2Withripemd160", "RIPEMD160andMGF1/RSA-ISO9796-2-2-3", (Class) null, o);
        sigS_ISO9796_2rndWithrsa = new AlgorithmID("1.3.36.3.4.2.3", "RSA-ISO9796-2-2-3", "RSA-ISO9796-2-2-3", (Class) null, o);
        sigS_ISO9796_2rndWithsha1 = new AlgorithmID("1.3.36.3.4.2.3.1", "SHAandMGF1/RSA-ISO9796-2-2-3", "SHAandMGF1/RSA-ISO9796-2-2-3", (Class) null, o);
        sigS_ISO9796_2rndWithripemd160 = new AlgorithmID("1.3.36.3.4.2.3.2", "RSA-ISO9796_2Withripemd160", "RIPEMD160andMGF1/RSA-ISO9796-2-2-3", (Class) null, o);
        mgf1 = new AlgorithmID("1.2.840.113549.1.1.8", "MGF1", "MGF1", (Class) null, o);
        String[] strArr8 = {"PbeWithMD5AndDES_CBC", "PBEWithMD5AndDES"};
        Class clsClass$27 = i;
        if (clsClass$27 == null) {
            clsClass$27 = class$("javax.crypto.spec.PBEParameterSpec");
            i = clsClass$27;
        }
        pbeWithMD5AndDES_CBC = new AlgorithmID("1.2.840.113549.1.5.3", "PbeWithMD5AndDES-CBC", strArr8, clsClass$27, o);
        Class clsClass$28 = i;
        if (clsClass$28 == null) {
            clsClass$28 = class$("javax.crypto.spec.PBEParameterSpec");
            i = clsClass$28;
        }
        pbeWithSHAAnd128BitRC4 = new AlgorithmID("1.2.840.113549.1.12.1.1", "PbeWithSHAAnd128BitRC4", str, clsClass$28, o);
        Class clsClass$29 = i;
        if (clsClass$29 == null) {
            clsClass$29 = class$("javax.crypto.spec.PBEParameterSpec");
            i = clsClass$29;
        }
        pbeWithSHAAnd40BitRC4 = new AlgorithmID("1.2.840.113549.1.12.1.2", "PbeWithSHAAnd40BitRC4", str, clsClass$29, o);
        Class clsClass$30 = i;
        if (clsClass$30 == null) {
            clsClass$30 = class$("javax.crypto.spec.PBEParameterSpec");
            i = clsClass$30;
        }
        pbeWithSHAAnd3_KeyTripleDES_CBC = new AlgorithmID("1.2.840.113549.1.12.1.3", "PbeWithSHAAnd3-KeyTripleDES-CBC", "PbeWithSHAAnd3_KeyTripleDES_CBC", clsClass$30, o);
        Class clsClass$31 = i;
        if (clsClass$31 == null) {
            clsClass$31 = class$("javax.crypto.spec.PBEParameterSpec");
            i = clsClass$31;
        }
        pbeWithSHAAnd2_KeyTripleDES_CBC = new AlgorithmID("1.2.840.113549.1.12.1.4", "PbeWithSHAAnd2-KeyTripleDES-CBC", str, clsClass$31, o);
        Class clsClass$32 = i;
        if (clsClass$32 == null) {
            clsClass$32 = class$("javax.crypto.spec.PBEParameterSpec");
            i = clsClass$32;
        }
        pbeWithSHAAnd128BitRC2_CBC = new AlgorithmID("1.2.840.113549.1.12.1.5", "PbeWithSHAAnd128BitRC2-CBC", str, clsClass$32, o);
        Class clsClass$33 = i;
        if (clsClass$33 == null) {
            clsClass$33 = class$("javax.crypto.spec.PBEParameterSpec");
            i = clsClass$33;
        }
        pbeWithSHAAnd40BitRC2_CBC = new AlgorithmID("1.2.840.113549.1.12.1.6", "PbeWithSHAAnd40BitRC2-CBC", "PbeWithSHAAnd40BitRC2_CBC", clsClass$33, o);
        Class clsClass$34 = j;
        if (clsClass$34 == null) {
            clsClass$34 = class$("iaik.pkcs.pkcs5.PBKDF2ParameterSpec");
            j = clsClass$34;
        }
        pbkdf2 = new AlgorithmID("1.2.840.113549.1.5.12", SecurityProvider.ALG_KEYGEN_PBKDF2, SecurityProvider.ALG_KEYGEN_PBKDF2, clsClass$34, o);
        Class clsClass$35 = k;
        if (clsClass$35 == null) {
            clsClass$35 = class$("iaik.pkcs.pkcs5.PBES2ParameterSpec");
            k = clsClass$35;
        }
        pbes2 = new AlgorithmID("1.2.840.113549.1.5.13", "PBES2", "PBES2", clsClass$35, o);
        pSpecified = new AlgorithmID("1.2.840.113549.1.1.9", "pSpecified", "pSpecified", (Class) null, o);
        md2 = new AlgorithmID("1.2.840.113549.2.2", "MD2", "MD2", (Class) null, o);
        md4 = new AlgorithmID("1.2.840.113549.2.4", "MD4", str, (Class) null, o);
        md5 = new AlgorithmID("1.2.840.113549.2.5", SecurityProvider.ALG_DIGEST_MD5, SecurityProvider.ALG_DIGEST_MD5, (Class) null, o);
        AlgorithmID algorithmID4 = new AlgorithmID("1.3.14.3.2.26", "SHA1", new String[]{"SHA1", "SHA-1", "SHA"}, (Class) null, o);
        sha = algorithmID4;
        sha1 = algorithmID4;
        gost3411 = new AlgorithmID("1.2.643.2.2.9", "GOST-R-34.11-94", "GOST3411", (Class) null, o);
        sha256 = new AlgorithmID("2.16.840.1.101.3.4.2.1", SecurityProvider.ALG_DIGEST_SHA256, new String[]{SecurityProvider.ALG_DIGEST_SHA256, "SHA-256"}, (Class) null, o);
        sha224 = new AlgorithmID("2.16.840.1.101.3.4.2.4", SecurityProvider.ALG_DIGEST_SHA224, new String[]{SecurityProvider.ALG_DIGEST_SHA224, "SHA-224"}, (Class) null, o);
        sha384 = new AlgorithmID("2.16.840.1.101.3.4.2.2", SecurityProvider.ALG_DIGEST_SHA384, new String[]{SecurityProvider.ALG_DIGEST_SHA384, "SHA-384"}, (Class) null, o);
        sha512 = new AlgorithmID("2.16.840.1.101.3.4.2.3", SecurityProvider.ALG_DIGEST_SHA512, new String[]{SecurityProvider.ALG_DIGEST_SHA512, "SHA-512"}, (Class) null, o);
        sha512_224 = new AlgorithmID("2.16.840.1.101.3.4.2.5", "SHA512/224", "SHA512/224", (Class) null, o);
        sha512_256 = new AlgorithmID("2.16.840.1.101.3.4.2.6", "SHA512/256", "SHA512/256", (Class) null, o);
        sha3_224 = new AlgorithmID("2.16.840.1.101.3.4.2.7", "SHA3-224", new String[]{"SHA3-224"}, (Class) null, o);
        sha3_256 = new AlgorithmID("2.16.840.1.101.3.4.2.8", "SHA3-256", new String[]{"SHA3-256"}, (Class) null, o);
        sha3_384 = new AlgorithmID("2.16.840.1.101.3.4.2.9", "SHA3-384", new String[]{"SHA3-384"}, (Class) null, o);
        sha3_512 = new AlgorithmID("2.16.840.1.101.3.4.2.10", "SHA3-512", new String[]{"SHA3-512"}, (Class) null, o);
        shake128 = new AlgorithmID("2.16.840.1.101.3.4.2.11", "SHAKE128", new String[]{"SHAKE128", "SHAKE-128"}, (Class) null, o);
        shake256 = new AlgorithmID("2.16.840.1.101.3.4.2.12", "SHAKE256", new String[]{"SHAKE256", "SHAKE-256"}, (Class) null, o);
        ripeMd160_ISO = new AlgorithmID("1.0.10118.3.0.49", "RipeMd160", "RIPEMD160", null, o, false);
        ripeMd128_ISO = new AlgorithmID("1.0.10118.3.0.50", "RipeMd128", "RIPEMD128", null, o, false);
        ripeMd160 = new AlgorithmID("1.3.36.3.2.1", "RipeMd160", new String[]{"RIPEMD160", "RIPEMD-160"}, (Class) null, o);
        ripeMd128 = new AlgorithmID("1.3.36.3.2.2", "RipeMd128", new String[]{"RIPEMD128", "RIPEMD-128"}, (Class) null, o);
        ripeMd256 = new AlgorithmID("1.3.36.3.2.3", "RipeMd256", new String[]{"RIPEMD256", "RIPEMD-256"}, (Class) null, o);
        whirlpool = new AlgorithmID("1.0.10118.3.0.55", "Whirlpool", "Whirlpool", (Class) null, o);
        hMAC_GOST = new AlgorithmID("1.2.643.2.2.10", "HMAC_GOSTR3411", new String[]{"HMAC/GOST", "HmacGOST"}, (Class) null, o);
        hMAC_MD5 = new AlgorithmID("1.3.6.1.5.5.8.1.1", "hMAC-MD5", new String[]{"HMAC/MD5", SecurityProvider.ALG_HMAC_MD5}, (Class) null, o);
        hMAC_SHA1_ = new AlgorithmID("1.2.840.113549.2.7", "hMAC-SHA1", "HMAC/SHA1", (Class) null, o);
        hMAC_SHA1 = new AlgorithmID("1.3.6.1.5.5.8.1.2", "hMAC-SHA1", new String[]{"HMAC/SHA1", "HMAC/SHA-1", "HmacSHA1"}, (Class) null, o);
        hMAC_SHA224 = new AlgorithmID("1.2.840.113549.2.8", "hMAC-SHA224", new String[]{"HMAC/SHA224", "HMAC/SHA-224", "HmacSHA224"}, (Class) null, o);
        hMAC_SHA256 = new AlgorithmID("1.2.840.113549.2.9", "hMAC-SHA256", new String[]{"HMAC/SHA256", "HMAC/SHA-256", "HmacSHA256"}, (Class) null, o);
        hMAC_SHA384 = new AlgorithmID("1.2.840.113549.2.10", "hMAC-SHA384", new String[]{"HMAC/SHA384", "HMAC/SHA-384", SecurityProvider.ALG_HMAC_SHA384}, (Class) null, o);
        hMAC_SHA512 = new AlgorithmID("1.2.840.113549.2.11", "hMAC-SHA512", new String[]{"HMAC/SHA512", "HMAC/SHA-512", SecurityProvider.ALG_HMAC_SHA512}, (Class) null, o);
        hMAC_RIPEMD160 = new AlgorithmID("1.3.6.1.5.5.8.1.4", "hMAC-RIPEMD160", new String[]{"HMAC/RIPEMD160", "HmacRIPEMD160"}, (Class) null, o);
        cms_3DES_wrap = new AlgorithmID("1.2.840.113549.1.9.16.3.6", "CMS-3DES-Wrap", new String[]{"3DESWrap3DES", "DESedeWrapDESede", "DESedeWrap"}, (Class) null, o);
        cms_rc2_wrap = new AlgorithmID("1.2.840.113549.1.9.16.3.7", "CMS-RC2-Wrap", "RC2WrapRC2", (Class) null, o);
        cms_idea_wrap = new AlgorithmID("1.3.6.1.4.1.188.7.1.1.6", "CMS-IDEA-Wrap", "IDEAWrapIDEA", (Class) null, o);
        cms_cast5_wrap = new AlgorithmID("1.2.840.113533.7.66.15", "CMS-CAST128-Wrap", "CAST128WrapCAST128", (Class) null, o);
        cms_aes128_wrap = new AlgorithmID("2.16.840.1.101.3.4.1.5", "CMS-AES128-Wrap", new String[]{"AESWrapAES", "AESWrap"}, (Class) null, o);
        cms_aes192_wrap = new AlgorithmID("2.16.840.1.101.3.4.1.25", "CMS-AES192-Wrap", new String[]{"AES192WrapAES192", "AES192Wrap"}, (Class) null, o);
        cms_aes256_wrap = new AlgorithmID("2.16.840.1.101.3.4.1.45", "CMS-AES256-Wrap", new String[]{"AES256WrapAES256", "AES256Wrap"}, (Class) null, o);
        cms_camellia128_wrap = new AlgorithmID("1.2.392.200011.61.1.1.3.2", "CMS-Camellia128-Wrap", new String[]{"CamelliaWrapCamellia", "CamelliaWrap"}, (Class) null, false);
        cms_camellia192_wrap = new AlgorithmID("1.2.392.200011.61.1.1.3.3", "CMS-Camellia192-Wrap", new String[]{"Camellia192WrapCamellia192", "Camellia192Wrap"}, (Class) null, false);
        cms_camellia256_wrap = new AlgorithmID("1.2.392.200011.61.1.1.3.4", "CMS-Camellia256-Wrap", new String[]{"Camellia256WrapCamellia256", "Camellia256Wrap"}, (Class) null, false);
        cms_HMACwith3DES_wrap = new AlgorithmID("1.2.840.113549.1.9.16.3.11", "CMS-HMACwith3DES-Wrap", "3DESWrapHMAC", (Class) null, true);
        cms_HMACwithAES_wrap = new AlgorithmID("1.2.840.113549.1.9.16.3.12", "CMS-HMACwithAES-Wrap", "AESWrapHMAC", (Class) null, true);
        pwri_kek = new AlgorithmID("1.2.840.113549.1.9.16.3.9", "PWRI-KEK", "PWRI-KEK", (Class) null, o);
        zlib_compress = new AlgorithmID("1.2.840.113549.1.9.16.3.8", "ZLIB-COMPRESS", "ZLIB-COMPRESS", (Class) null, o);
        Class clsClass$36 = l;
        if (clsClass$36 == null) {
            clsClass$36 = class$("iaik.pkcs.pkcs1.RSAOaepParameterSpec");
            l = clsClass$36;
        }
        rsaesOAEP = new AlgorithmID("1.2.840.113549.1.1.7", "RSAES-OAEP", "RSA/ECB/OAEP", clsClass$36, o);
        Class clsClass$37 = m;
        if (clsClass$37 == null) {
            clsClass$37 = class$("iaik.pkcs.pkcs1.RSAPssParameterSpec");
            m = clsClass$37;
        }
        rsassaPss = new AlgorithmID("1.2.840.113549.1.1.10", "RSASSA-PSS", "RSASSA-PSS", clsClass$37, o);
        Hashtable hashtable = p;
        AlgorithmID algorithmID5 = dsa_With_SHA1;
        hashtable.put(algorithmID5, algorithmID5);
        Hashtable hashtable2 = p;
        AlgorithmID algorithmID6 = dsaWithSHA;
        hashtable2.put(algorithmID6, algorithmID6);
        Hashtable hashtable3 = p;
        AlgorithmID algorithmID7 = dsaWithSHA1;
        hashtable3.put(algorithmID7, algorithmID7);
        Hashtable hashtable4 = p;
        AlgorithmID algorithmID8 = dsa;
        hashtable4.put(algorithmID8, algorithmID8);
        Hashtable hashtable5 = p;
        AlgorithmID algorithmID9 = dsa_;
        hashtable5.put(algorithmID9, algorithmID9);
        Hashtable hashtable6 = p;
        AlgorithmID algorithmID10 = ecdsa_With_SHA1;
        hashtable6.put(algorithmID10, algorithmID10);
        Hashtable hashtable7 = p;
        AlgorithmID algorithmID11 = ecdsa;
        hashtable7.put(algorithmID11, algorithmID11);
    }

    public AlgorithmID() {
        this.q = null;
        this.r = null;
        this.s = o;
    }

    public AlgorithmID(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public AlgorithmID(DerInputStream derInputStream) throws IOException {
        this();
        DerInputStream sequence = derInputStream.readSequence();
        this.q = sequence.readObjectID();
        try {
            if (sequence.nextTag() != -1) {
                this.r = new ASN1(sequence);
            } else {
                this.s = false;
            }
        } catch (CodingException unused) {
            throw new IOException("Error decoding algorithm parameter.");
        }
    }

    public AlgorithmID(ObjectID objectID) {
        this();
        this.q = objectID;
        this.r = null;
    }

    public AlgorithmID(ObjectID objectID, ASN1Object aSN1Object) {
        this();
        this.q = objectID;
        if (aSN1Object != null) {
            try {
                this.r = new ASN1(aSN1Object);
            } catch (CodingException unused) {
            }
        }
    }

    public AlgorithmID(String str, String str2) {
        this();
        this.q = new ObjectID(str, str2);
    }

    public AlgorithmID(String str, String str2, String str3) {
        this();
        ObjectID objectID = new ObjectID(str, str2);
        this.q = objectID;
        if (str3 != null) {
            implementations.put(objectID, str3);
            algorithms.put(str3, this);
        }
    }

    public AlgorithmID(String str, String str2, String str3, Class cls) {
        this(str, str2, str3, cls, o);
    }

    public AlgorithmID(String str, String str2, String str3, Class cls, boolean z) {
        this(str, str2, str3, cls, z, true);
    }

    private AlgorithmID(String str, String str2, String str3, Class cls, boolean z, boolean z2) {
        this();
        if (cls != null) {
            Class clsClass$ = n;
            if (clsClass$ == null) {
                clsClass$ = class$("java.security.spec.AlgorithmParameterSpec");
                n = clsClass$;
            }
            if (!clsClass$.isAssignableFrom(cls)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("algorithmParameterSpec (");
                stringBuffer.append(cls.getName());
                stringBuffer.append(") does not implement AlgorithmParameterSpec!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
        ObjectID objectID = new ObjectID(str, str2, null, false);
        this.q = objectID;
        if (str3 != null) {
            implementations.put(objectID, str3);
            if (z2) {
                algorithms.put(str3, this);
            }
        }
        if (cls != null) {
            algorithmParameterSpecs.put(this, cls);
        }
        this.s = z;
    }

    public AlgorithmID(String str, String str2, String str3, boolean z) {
        this(str, str2, str3);
        this.s = z;
    }

    public AlgorithmID(String str, String str2, String[] strArr, Class cls) {
        this(str, str2, strArr, cls, o);
    }

    public AlgorithmID(String str, String str2, String[] strArr, Class cls, boolean z) {
        this();
        if (cls != null) {
            Class clsClass$ = n;
            if (clsClass$ == null) {
                clsClass$ = class$("java.security.spec.AlgorithmParameterSpec");
                n = clsClass$;
            }
            if (!clsClass$.isAssignableFrom(cls)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("algorithmParameterSpec (");
                stringBuffer.append(cls.getName());
                stringBuffer.append(") does not implement AlgorithmParameterSpec!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
        this.q = new ObjectID(str, str2, null, false);
        if (strArr != null && strArr.length > 0) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str3 = strArr[i2];
                if (str3 != null) {
                    if (i2 == 0) {
                        implementations.put(this.q, str3);
                    }
                    algorithms.put(str3, this);
                }
            }
        }
        if (cls != null) {
            algorithmParameterSpecs.put(this, cls);
        }
        this.s = z;
    }

    public AlgorithmID(String str, String str2, String[] strArr, boolean z) {
        this(str, str2, strArr, (Class) null, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.security.AlgorithmParameters] */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.security.AlgorithmParameters] */
    /* JADX WARN: Type inference failed for: r3v17, types: [java.security.AlgorithmParameters] */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v24 */
    private AlgorithmParameters a(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    try {
                        str = str == 0 ? AlgorithmParameters.getInstance(getImplementationName()) : AlgorithmParameters.getInstance(getImplementationName(), str);
                        return str;
                    } catch (Exception unused) {
                        return str == 0 ? AlgorithmParameters.getInstance(this.q.getName()) : AlgorithmParameters.getInstance(this.q.getName(), str);
                    }
                } catch (Exception unused2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No implementation for ");
                    stringBuffer.append(this.q.getName());
                    throw new NoSuchAlgorithmException(stringBuffer.toString());
                }
            } catch (Exception unused3) {
                str = str == 0 ? AlgorithmParameters.getInstance(this.q.getID()) : AlgorithmParameters.getInstance(this.q.getID(), str);
                return str;
            }
        } catch (Exception unused4) {
            str = str == 0 ? AlgorithmParameters.getInstance(getRawImplementationName()) : AlgorithmParameters.getInstance(getRawImplementationName(), str);
            return str;
        }
    }

    public static boolean changeOIDString(AlgorithmID algorithmID, String str) {
        try {
            String id = algorithmID.q.getID();
            String name = algorithmID.q.getName();
            String shortName = algorithmID.q.getShortName();
            Hashtable hashtable = implementations;
            String str2 = (String) hashtable.get(algorithmID.q);
            ObjectID objectID = name.equals(id) ? shortName.equals(id) ? new ObjectID(str) : new ObjectID(str, "", shortName) : shortName.equals(id) ? new ObjectID(str, name) : new ObjectID(str, name, shortName);
            algorithmID.q = objectID;
            hashtable.put(objectID, str2);
            algorithms.put(str2, algorithmID);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean changeObjectID(AlgorithmID algorithmID, ObjectID objectID) {
        try {
            Hashtable hashtable = implementations;
            String str = (String) hashtable.get(algorithmID.q);
            algorithmID.q = objectID;
            hashtable.put(objectID, str);
            algorithms.put(str, algorithmID);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static AlgorithmID getAlgorithmID(String str) {
        AlgorithmID algorithmID = (AlgorithmID) algorithms.get(str);
        if (algorithmID == null) {
            return null;
        }
        return (AlgorithmID) algorithmID.clone();
    }

    public static boolean getDoNotIncludeParameters(AlgorithmID algorithmID) {
        return p.get(algorithmID) != null;
    }

    public static String[] getImplementationNames(AlgorithmID algorithmID) {
        Vector vector = new Vector();
        try {
            vector.add(algorithmID.getImplementationName());
        } catch (NoSuchAlgorithmException unused) {
        }
        Enumeration enumerationKeys = algorithms.keys();
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            if (algorithmID.equals(algorithms.get(str)) && !vector.contains(str)) {
                vector.add(str);
            }
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    public static void register(String str, String str2, String str3) {
        new AlgorithmID(str, str2, str3);
    }

    public static void setDefaultEncodeAbsentParametersAsNull(boolean z) {
        o = z;
    }

    public static void setDoIncludeParameters(AlgorithmID algorithmID) {
        p.remove(algorithmID);
    }

    public static void setDoNotIncludeParameters(AlgorithmID algorithmID) {
        p.put(algorithmID, algorithmID);
    }

    public Object clone() {
        AlgorithmID algorithmID = null;
        try {
            AlgorithmID algorithmID2 = (AlgorithmID) super.clone();
            try {
                ObjectID objectID = this.q;
                if (objectID != null) {
                    algorithmID2.q = (ObjectID) objectID.clone();
                }
                ASN1 asn1 = this.r;
                if (asn1 != null) {
                    algorithmID2.r = (ASN1) asn1.clone();
                }
                algorithmID2.s = this.s;
                return algorithmID2;
            } catch (CloneNotSupportedException unused) {
                algorithmID = algorithmID2;
                return algorithmID;
            }
        } catch (CloneNotSupportedException unused2) {
        }
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new CodingException("AlgorithmID must be SEQUENCE!");
        }
        int iCountComponents = aSN1Object.countComponents();
        if (iCountComponents < 1 || iCountComponents > 2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid number of components (");
            stringBuffer.append(iCountComponents);
            stringBuffer.append(") in algorithm id!");
            throw new CodingException(stringBuffer.toString());
        }
        try {
            this.q = (ObjectID) aSN1Object.getComponentAt(0);
            if (aSN1Object.countComponents() == 2) {
                this.r = new ASN1(aSN1Object.getComponentAt(1));
            } else {
                this.s = false;
            }
        } catch (Exception e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Error parsing algorithmID: ");
            stringBuffer2.append(e2.toString());
            throw new CodingException(stringBuffer2.toString());
        }
    }

    public void encodeAbsentParametersAsNull(boolean z) {
        this.s = z;
    }

    public boolean equals(Object obj) {
        return equals(obj, false);
    }

    public boolean equals(Object obj, boolean z) {
        if (this != obj) {
            if (!(obj instanceof AlgorithmID)) {
                return false;
            }
            AlgorithmID algorithmID = (AlgorithmID) obj;
            boolean zEquals = this.q.equals(algorithmID.q);
            if (!zEquals || !z) {
                return zEquals;
            }
            ASN1 asn1 = this.r;
            if (asn1 != null && asn1.toASN1Object().isA(ASN.NULL)) {
                asn1 = null;
            }
            ASN1 asn12 = algorithmID.r;
            ASN1 asn13 = (asn12 == null || !asn12.toASN1Object().isA(ASN.NULL)) ? asn12 : null;
            if (asn1 != null && asn13 != null) {
                return CryptoUtils.equalsBlock(asn1.toByteArray(), asn13.toByteArray());
            }
            if (asn1 != null || asn13 != null) {
                return false;
            }
        }
        return true;
    }

    public ObjectID getAlgorithm() {
        return this.q;
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() throws InvalidAlgorithmParameterException {
        return getAlgorithmParameterSpec(null);
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec(Class cls, String str) throws InvalidAlgorithmParameterException {
        if (cls == null || !hasParameters()) {
            return null;
        }
        Class clsClass$ = n;
        if (clsClass$ == null) {
            clsClass$ = class$("java.security.spec.AlgorithmParameterSpec");
            n = clsClass$;
        }
        if (!clsClass$.isAssignableFrom(cls)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("parameterSpecClass (");
            stringBuffer.append(cls.getName());
            stringBuffer.append(") does not implement AlgorithmParameterSpec!");
            throw new InvalidAlgorithmParameterException(stringBuffer.toString());
        }
        try {
            return getAlgorithmParameters(getImplementationName(), str).getParameterSpec(cls);
        } catch (NoSuchAlgorithmException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("No AlgorithmParameters implementation available: ");
            stringBuffer2.append(e2);
            throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
        } catch (InvalidParameterSpecException e3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("No AlgorithmParameters implementation available: ");
            stringBuffer3.append(e3);
            throw new InvalidAlgorithmParameterException(stringBuffer3.toString());
        }
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec(String str) throws InvalidAlgorithmParameterException {
        return getAlgorithmParameterSpec((Class) algorithmParameterSpecs.get(this), str);
    }

    public AlgorithmParameters getAlgorithmParameters() throws NoSuchAlgorithmException {
        ASN1 asn1 = this.r;
        if (asn1 == null || asn1.toASN1Object().isA(ASN.NULL)) {
            return null;
        }
        return getAlgorithmParameters(null, null);
    }

    public AlgorithmParameters getAlgorithmParameters(String str) throws NoSuchAlgorithmException {
        return getAlgorithmParameters(str, null);
    }

    public AlgorithmParameters getAlgorithmParameters(String str, String str2) throws NoSuchAlgorithmException {
        try {
            ASN1 asn1 = this.r;
            if (asn1 == null || asn1.toASN1Object().isA(ASN.NULL)) {
                throw new NoSuchAlgorithmException("No algorithm parameters.");
            }
            AlgorithmParameters algorithmParameters = str != null ? str2 == null ? AlgorithmParameters.getInstance(str) : AlgorithmParameters.getInstance(str, str2) : a(str2);
            algorithmParameters.init(this.r.toByteArray());
            return algorithmParameters;
        } catch (IOException e2) {
            throw new RuntimeException(e2.toString());
        } catch (NoSuchProviderException unused) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No such provider: ");
            stringBuffer.append(str2);
            throw new NoSuchAlgorithmException(stringBuffer.toString());
        }
    }

    public Cipher getCipherInstance() throws NoSuchAlgorithmException {
        return getCipherInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [javax.crypto.Cipher] */
    /* JADX WARN: Type inference failed for: r3v13, types: [javax.crypto.Cipher] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    public Cipher getCipherInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    str = str == 0 ? Cipher.getInstance(getImplementationName()) : Cipher.getInstance(getImplementationName(), str);
                    return str;
                } catch (Exception unused) {
                    return str == 0 ? Cipher.getInstance(this.q.getName()) : Cipher.getInstance(this.q.getName(), str);
                }
            } catch (Exception unused2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for ");
                stringBuffer.append(this.q.getName());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (Exception unused3) {
            str = str == 0 ? Cipher.getInstance(this.q.getID()) : Cipher.getInstance(this.q.getID(), str);
            return str;
        }
    }

    public String getImplementationName() throws NoSuchAlgorithmException {
        String str = (String) implementations.get(this.q);
        if (str != null) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No implementation for: ");
        stringBuffer.append(getName());
        throw new NoSuchAlgorithmException(stringBuffer.toString());
    }

    public Object getInstance() throws NoSuchAlgorithmException {
        return getInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object getInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                str = str == 0 ? getMessageDigestInstance() : getMessageDigestInstance(str);
                                return str;
                            } catch (Exception unused) {
                                str = str == 0 ? getKeyFactoryInstance() : getKeyFactoryInstance(str);
                                return str;
                            }
                        } catch (Exception unused2) {
                            return str == 0 ? getMacInstance() : getMacInstance(str);
                        }
                    } catch (Exception unused3) {
                        str = str == 0 ? getSignatureInstance() : getSignatureInstance(str);
                        return str;
                    }
                } catch (Exception unused4) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No implementation for ");
                    stringBuffer.append(this.q.getName());
                    throw new NoSuchAlgorithmException(stringBuffer.toString());
                }
            } catch (Exception unused5) {
                str = str == 0 ? getCipherInstance() : getCipherInstance(str);
                return str;
            }
        } catch (Exception unused6) {
            str = str == 0 ? getKeyAgreementInstance() : getKeyAgreementInstance(str);
            return str;
        }
    }

    public KeyAgreement getKeyAgreementInstance() throws NoSuchAlgorithmException {
        return getKeyAgreementInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [javax.crypto.KeyAgreement] */
    /* JADX WARN: Type inference failed for: r3v13, types: [javax.crypto.KeyAgreement] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    public KeyAgreement getKeyAgreementInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    str = str == 0 ? KeyAgreement.getInstance(getImplementationName()) : KeyAgreement.getInstance(getImplementationName(), str);
                    return str;
                } catch (Exception unused) {
                    return str == 0 ? KeyAgreement.getInstance(this.q.getName()) : KeyAgreement.getInstance(this.q.getName(), str);
                }
            } catch (Exception unused2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for ");
                stringBuffer.append(this.q.getName());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (Exception unused3) {
            str = str == 0 ? KeyAgreement.getInstance(this.q.getID()) : KeyAgreement.getInstance(this.q.getID(), str);
            return str;
        }
    }

    public KeyFactory getKeyFactoryInstance() throws NoSuchAlgorithmException {
        return getKeyFactoryInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.security.KeyFactory] */
    /* JADX WARN: Type inference failed for: r3v13, types: [java.security.KeyFactory] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    public KeyFactory getKeyFactoryInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    str = str == 0 ? KeyFactory.getInstance(getImplementationName()) : KeyFactory.getInstance(getImplementationName(), str);
                    return str;
                } catch (Exception unused) {
                    return str == 0 ? KeyFactory.getInstance(this.q.getName()) : KeyFactory.getInstance(this.q.getName(), str);
                }
            } catch (Exception unused2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for ");
                stringBuffer.append(this.q.getName());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (Exception unused3) {
            str = str == 0 ? KeyFactory.getInstance(this.q.getID()) : KeyFactory.getInstance(this.q.getID(), str);
            return str;
        }
    }

    public KeyGenerator getKeyGeneratorInstance() throws NoSuchAlgorithmException {
        return getKeyGeneratorInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [javax.crypto.KeyGenerator] */
    /* JADX WARN: Type inference failed for: r3v14, types: [javax.crypto.KeyGenerator] */
    /* JADX WARN: Type inference failed for: r3v17, types: [javax.crypto.KeyGenerator] */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v24 */
    public KeyGenerator getKeyGeneratorInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    try {
                        str = str == 0 ? KeyGenerator.getInstance(getRawImplementationName()) : KeyGenerator.getInstance(getRawImplementationName(), str);
                        return str;
                    } catch (Exception unused) {
                        return str == 0 ? KeyGenerator.getInstance(this.q.getName()) : KeyGenerator.getInstance(this.q.getName(), str);
                    }
                } catch (Exception unused2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No implementation for ");
                    stringBuffer.append(this.q.getName());
                    throw new NoSuchAlgorithmException(stringBuffer.toString());
                }
            } catch (Exception unused3) {
                str = str == 0 ? KeyGenerator.getInstance(this.q.getID()) : KeyGenerator.getInstance(this.q.getID(), str);
                return str;
            }
        } catch (Exception unused4) {
            str = str == 0 ? KeyGenerator.getInstance(getImplementationName()) : KeyGenerator.getInstance(getImplementationName(), str);
            return str;
        }
    }

    public KeyPairGenerator getKeyPairGeneratorInstance() throws NoSuchAlgorithmException {
        return getKeyPairGeneratorInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.security.KeyPairGenerator] */
    /* JADX WARN: Type inference failed for: r3v13, types: [java.security.KeyPairGenerator] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    public KeyPairGenerator getKeyPairGeneratorInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    str = str == 0 ? KeyPairGenerator.getInstance(getImplementationName()) : KeyPairGenerator.getInstance(getImplementationName(), str);
                    return str;
                } catch (Exception unused) {
                    return str == 0 ? KeyPairGenerator.getInstance(this.q.getName()) : KeyPairGenerator.getInstance(this.q.getName(), str);
                }
            } catch (Exception unused2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for ");
                stringBuffer.append(this.q.getName());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (Exception unused3) {
            str = str == 0 ? KeyPairGenerator.getInstance(this.q.getID()) : KeyPairGenerator.getInstance(this.q.getID(), str);
            return str;
        }
    }

    public Mac getMacInstance() throws NoSuchAlgorithmException {
        return getMacInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [javax.crypto.Mac] */
    /* JADX WARN: Type inference failed for: r3v13, types: [javax.crypto.Mac] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    public Mac getMacInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    str = str == 0 ? Mac.getInstance(getImplementationName()) : Mac.getInstance(getImplementationName(), str);
                    return str;
                } catch (Exception unused) {
                    return str == 0 ? Mac.getInstance(this.q.getName()) : Mac.getInstance(this.q.getName(), str);
                }
            } catch (Exception unused2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for ");
                stringBuffer.append(this.q.getName());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (Exception unused3) {
            str = str == 0 ? Mac.getInstance(this.q.getID()) : Mac.getInstance(this.q.getID(), str);
            return str;
        }
    }

    public MaskGenerationAlgorithm getMaskGenerationAlgorithmInstance() throws NoSuchAlgorithmException {
        return getMaskGenerationAlgorithmInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [iaik.pkcs.pkcs1.MaskGenerationAlgorithm] */
    /* JADX WARN: Type inference failed for: r3v13, types: [iaik.pkcs.pkcs1.MaskGenerationAlgorithm] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    public MaskGenerationAlgorithm getMaskGenerationAlgorithmInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    str = str == 0 ? MaskGenerationAlgorithm.getInstance(getImplementationName()) : MaskGenerationAlgorithm.getInstance(getImplementationName(), str);
                    return str;
                } catch (Exception unused) {
                    return str == 0 ? MaskGenerationAlgorithm.getInstance(this.q.getName()) : MaskGenerationAlgorithm.getInstance(this.q.getName(), str);
                }
            } catch (Exception unused2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for ");
                stringBuffer.append(this.q.getName());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (Exception unused3) {
            str = str == 0 ? MaskGenerationAlgorithm.getInstance(this.q.getID()) : MaskGenerationAlgorithm.getInstance(this.q.getID(), str);
            return str;
        }
    }

    public MessageDigest getMessageDigestInstance() throws NoSuchAlgorithmException {
        return getMessageDigestInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.security.MessageDigest] */
    /* JADX WARN: Type inference failed for: r3v13, types: [java.security.MessageDigest] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    public MessageDigest getMessageDigestInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    str = str == 0 ? MessageDigest.getInstance(getImplementationName()) : MessageDigest.getInstance(getImplementationName(), str);
                    return str;
                } catch (Exception unused) {
                    return str == 0 ? MessageDigest.getInstance(this.q.getName()) : MessageDigest.getInstance(this.q.getName(), str);
                }
            } catch (Exception unused2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("No implementation for ");
                stringBuffer.append(this.q.getName());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        } catch (Exception unused3) {
            str = str == 0 ? MessageDigest.getInstance(this.q.getID()) : MessageDigest.getInstance(this.q.getID(), str);
            return str;
        }
    }

    public String getName() {
        return this.q.getName();
    }

    public ASN1Object getParameter() {
        ASN1 asn1 = this.r;
        if (asn1 != null) {
            return asn1.toASN1Object();
        }
        return null;
    }

    public String getRawImplementationName() throws NoSuchAlgorithmException {
        String implementationName = getImplementationName();
        int iIndexOf = implementationName.indexOf("/");
        return iIndexOf == -1 ? implementationName : implementationName.substring(0, iIndexOf);
    }

    public SecretKeyFactory getSecretKeyFactoryInstance() throws NoSuchAlgorithmException {
        return getSecretKeyFactoryInstance(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v13, types: [javax.crypto.SecretKeyFactory] */
    /* JADX WARN: Type inference failed for: r3v16, types: [javax.crypto.SecretKeyFactory] */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v9, types: [javax.crypto.SecretKeyFactory] */
    public SecretKeyFactory getSecretKeyFactoryInstance(String str) throws NoSuchAlgorithmException {
        try {
            try {
                try {
                    try {
                        str = str == 0 ? SecretKeyFactory.getInstance(getRawImplementationName()) : SecretKeyFactory.getInstance(getRawImplementationName(), str);
                        return str;
                    } catch (Exception unused) {
                        str = str == 0 ? SecretKeyFactory.getInstance(this.q.getName()) : SecretKeyFactory.getInstance(this.q.getName(), str);
                        return str;
                    }
                } catch (Exception unused2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No implementation for ");
                    stringBuffer.append(this.q.getName());
                    throw new NoSuchAlgorithmException(stringBuffer.toString());
                }
            } catch (Exception unused3) {
                return str == 0 ? SecretKeyFactory.getInstance(getImplementationName()) : SecretKeyFactory.getInstance(getImplementationName(), str);
            }
        } catch (Exception unused4) {
            str = str == 0 ? SecretKeyFactory.getInstance(this.q.getID()) : SecretKeyFactory.getInstance(this.q.getID(), str);
            return str;
        }
    }

    public Signature getSignatureInstance() throws NoSuchAlgorithmException {
        return getSignatureInstance(null);
    }

    public Signature getSignatureInstance(String str) throws NoSuchAlgorithmException {
        Signature signature;
        try {
            signature = str == null ? Signature.getInstance(getImplementationName()) : Signature.getInstance(getImplementationName(), str);
        } catch (Exception unused) {
            signature = null;
        }
        if (signature == null) {
            try {
                signature = str == null ? Signature.getInstance(this.q.getID()) : Signature.getInstance(this.q.getID(), str);
            } catch (Exception unused2) {
            }
        }
        if (signature == null) {
            try {
                signature = str == null ? Signature.getInstance(this.q.getName()) : Signature.getInstance(this.q.getName(), str);
            } catch (Exception unused3) {
            }
        }
        if (signature == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No implementation for ");
            stringBuffer.append(this.q.getName());
            throw new NoSuchAlgorithmException(stringBuffer.toString());
        }
        if (hasParameters()) {
            try {
                AlgorithmParameterSpec algorithmParameterSpec = getAlgorithmParameterSpec(str);
                if (algorithmParameterSpec != null) {
                    signature.setParameter(algorithmParameterSpec);
                }
            } catch (InvalidAlgorithmParameterException e2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Error converting parameters: ");
                stringBuffer2.append(e2);
                throw new NoSuchAlgorithmException(stringBuffer2.toString());
            }
        }
        return signature;
    }

    public boolean hasParameters() {
        ASN1 asn1 = this.r;
        return (asn1 == null || asn1.toASN1Object().isA(ASN.NULL)) ? false : true;
    }

    public int hashCode() {
        return this.q.hashCode();
    }

    public void setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        setAlgorithmParameterSpec(algorithmParameterSpec, null);
    }

    public void setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec, String str) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null) {
            this.r = null;
            return;
        }
        try {
            AlgorithmParameters algorithmParametersA = a(str);
            algorithmParametersA.init(algorithmParameterSpec);
            setAlgorithmParameters(algorithmParametersA);
        } catch (Exception e2) {
            throw new InvalidAlgorithmParameterException(e2.toString());
        }
    }

    public void setAlgorithmParameters(AlgorithmParameters algorithmParameters) {
        if (algorithmParameters == null) {
            this.r = null;
            return;
        }
        try {
            this.r = new ASN1(algorithmParameters.getEncoded());
        } catch (CodingException e2) {
            throw new RuntimeException(e2.toString());
        } catch (IOException e3) {
            throw new RuntimeException(e3.toString());
        }
    }

    public void setParameter(ASN1Object aSN1Object) {
        if (aSN1Object == null) {
            this.r = null;
        } else {
            try {
                this.r = new ASN1(aSN1Object);
            } catch (CodingException unused) {
            }
        }
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return toASN1Object(this.s);
    }

    public ASN1Object toASN1Object(boolean z) {
        ASN1Object aSN1Object;
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.q);
        ASN1 asn1 = this.r;
        if (asn1 == null) {
            if (z) {
                aSN1Object = new NULL();
                try {
                    this.r = new ASN1(aSN1Object);
                } catch (CodingException unused) {
                }
            }
            return sequence;
        }
        aSN1Object = asn1.toASN1Object();
        sequence.addComponent(aSN1Object);
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(this.q.getNameAndID());
        stringBuffer2.append(" ");
        stringBuffer.append(stringBuffer2.toString());
        ASN1 asn1 = this.r;
        if (asn1 != null && !asn1.toASN1Object().isA(ASN.NULL)) {
            stringBuffer.append(" with parameter");
        }
        return stringBuffer.toString();
    }
}

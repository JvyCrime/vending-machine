package iaik.security.ssl;

import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import com.magtek.mobile.android.mtusdk.cms.EMVL2CommandID;
import iaik.security.jsse.net.KeyTypeNames;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import org.kxml2.wap.Wbxml;

/* JADX INFO: loaded from: classes.dex */
public final class CipherSuite implements Serializable, Comparable {
    private static final CipherSuite A;
    private static final CipherSuite B;
    private static final CipherSuite C;
    public static final CipherSuite[] CS_AEAD_GCM;
    public static final CipherSuite[] CS_ALL_PSK;
    public static final CipherSuite[] CS_DHE_DSS;
    public static final CipherSuite[] CS_DHE_DSS_EXPORT;
    public static final CipherSuite[] CS_DHE_DSS_EXPORT1024;
    public static final CipherSuite[] CS_DHE_PSK;
    public static final CipherSuite[] CS_DHE_RSA;
    public static final CipherSuite[] CS_DHE_RSA_EXPORT;
    public static final CipherSuite[] CS_DH_ANON;
    public static final CipherSuite[] CS_DH_ANON_EXPORT;
    public static final CipherSuite[] CS_DH_DSS;
    public static final CipherSuite[] CS_DH_DSS_EXPORT;
    public static final CipherSuite[] CS_DH_RSA;
    public static final CipherSuite[] CS_DH_RSA_EXPORT;
    public static final CipherSuite[] CS_ECDHE_ECDSA;
    public static final CipherSuite[] CS_ECDHE_PSK;
    public static final CipherSuite[] CS_ECDHE_RSA;
    public static final CipherSuite[] CS_ECDH_ANON;
    public static final CipherSuite[] CS_ECDH_ECDSA;
    public static final CipherSuite[] CS_ECDH_RSA;
    public static final CipherSuite[] CS_PSK;
    public static final CipherSuite[] CS_RSA;
    public static final CipherSuite[] CS_RSA_EXPORT;
    public static final CipherSuite[] CS_RSA_EXPORT1024;
    public static final CipherSuite[] CS_RSA_PSK;
    public static final CipherSuite[] CS_RSA_WITH_NULL;
    public static final CipherSuite[] CS_SSL_V2;
    private static final CipherSuite D;
    private static final CipherSuite E;
    private static final CipherSuite F;
    private static final CipherSuite G;
    private static final CipherSuite H;
    private static final CipherSuite I;
    private static final CipherSuite J;
    private static final CipherSuite K;
    private static final CipherSuite L;
    public static final CipherSuite PRIVATE_RSA_WITH_RC2_CBC_MD5;
    public static final CipherSuite SSL_DHE_DSS_WITH_AES_128_CBC_SHA;
    public static final CipherSuite SSL_DHE_DSS_WITH_AES_256_CBC_SHA;
    public static final CipherSuite SSL_DHE_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite SSL_DHE_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite SSL_DH_DSS_WITH_AES_128_CBC_SHA;
    public static final CipherSuite SSL_DH_DSS_WITH_AES_256_CBC_SHA;
    public static final CipherSuite SSL_DH_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite SSL_DH_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite SSL_DH_anon_WITH_AES_128_CBC_SHA;
    public static final CipherSuite SSL_DH_anon_WITH_AES_256_CBC_SHA;
    public static final CipherSuite SSL_DH_anon_WITH_RC4_MD5;
    public static final CipherSuite SSL_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite SSL_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite SSL_RSA_WITH_RC4_MD5;
    public static final CipherSuite SSL_RSA_WITH_RC4_SHA;
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_CBC_SHA256;
    public static final CipherSuite TLS_DHE_DSS_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA;
    public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA;
    public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256;
    public static final CipherSuite TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_DHE_PSK_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_DHE_PSK_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_DHE_PSK_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_DHE_PSK_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_DHE_PSK_WITH_AES_256_CBC_SHA384;
    public static final CipherSuite TLS_DHE_PSK_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_DHE_PSK_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_DHE_PSK_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_DHE_PSK_WITH_CAMELLIA_256_CBC_SHA384;
    public static final CipherSuite TLS_DHE_PSK_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_DHE_PSK_WITH_NULL_SHA;
    public static final CipherSuite TLS_DHE_PSK_WITH_NULL_SHA256;
    public static final CipherSuite TLS_DHE_PSK_WITH_NULL_SHA384;
    public static final CipherSuite TLS_DHE_PSK_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_CBC_SHA256;
    public static final CipherSuite TLS_DHE_RSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA;
    public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA;
    public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256;
    public static final CipherSuite TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_DH_DSS_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_DH_DSS_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_DH_DSS_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_DH_DSS_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_DH_DSS_WITH_AES_256_CBC_SHA256;
    public static final CipherSuite TLS_DH_DSS_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA;
    public static final CipherSuite TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_DH_DSS_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA;
    public static final CipherSuite TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256;
    public static final CipherSuite TLS_DH_DSS_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_DH_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_DH_RSA_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_DH_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_DH_RSA_WITH_AES_256_CBC_SHA256;
    public static final CipherSuite TLS_DH_RSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA;
    public static final CipherSuite TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_DH_RSA_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA;
    public static final CipherSuite TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256;
    public static final CipherSuite TLS_DH_RSA_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_DH_anon_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_DH_anon_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_DH_anon_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_DH_anon_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_DH_anon_WITH_AES_256_CBC_SHA256;
    public static final CipherSuite TLS_DH_anon_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA;
    public static final CipherSuite TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_DH_anon_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA;
    public static final CipherSuite TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256;
    public static final CipherSuite TLS_DH_anon_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_NULL_SHA;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_CAMELLIA_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_NULL_SHA;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_NULL_SHA256;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_NULL_SHA384;
    public static final CipherSuite TLS_ECDHE_PSK_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_NULL_SHA;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_CAMELLIA_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_NULL_SHA;
    public static final CipherSuite TLS_ECDH_ECDSA_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDH_RSA_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_ECDH_RSA_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDH_RSA_WITH_CAMELLIA_256_CBC_SHA384;
    public static final CipherSuite TLS_ECDH_RSA_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDH_RSA_WITH_NULL_SHA;
    public static final CipherSuite TLS_ECDH_RSA_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_ECDH_anon_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_ECDH_anon_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_ECDH_anon_WITH_NULL_SHA;
    public static final CipherSuite TLS_ECDH_anon_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_PSK_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_PSK_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_PSK_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_PSK_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_PSK_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_PSK_WITH_AES_256_CBC_SHA384;
    public static final CipherSuite TLS_PSK_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_PSK_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_PSK_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_PSK_WITH_CAMELLIA_256_CBC_SHA384;
    public static final CipherSuite TLS_PSK_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_PSK_WITH_NULL_SHA;
    public static final CipherSuite TLS_PSK_WITH_NULL_SHA256;
    public static final CipherSuite TLS_PSK_WITH_NULL_SHA384;
    public static final CipherSuite TLS_PSK_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_RSA_PSK_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_RSA_PSK_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_RSA_PSK_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_RSA_PSK_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_RSA_PSK_WITH_AES_256_CBC_SHA384;
    public static final CipherSuite TLS_RSA_PSK_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_RSA_PSK_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_RSA_PSK_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_RSA_PSK_WITH_CAMELLIA_256_CBC_SHA384;
    public static final CipherSuite TLS_RSA_PSK_WITH_CAMELLIA_256_GCM_SHA384;
    public static final CipherSuite TLS_RSA_PSK_WITH_NULL_SHA;
    public static final CipherSuite TLS_RSA_PSK_WITH_NULL_SHA256;
    public static final CipherSuite TLS_RSA_PSK_WITH_NULL_SHA384;
    public static final CipherSuite TLS_RSA_PSK_WITH_RC4_128_SHA;
    public static final CipherSuite TLS_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_RSA_WITH_AES_128_CBC_SHA256;
    public static final CipherSuite TLS_RSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_RSA_WITH_AES_256_CBC_SHA256;
    public static final CipherSuite TLS_RSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_RSA_WITH_CAMELLIA_128_CBC_SHA;
    public static final CipherSuite TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256;
    public static final CipherSuite TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256;
    public static final CipherSuite TLS_RSA_WITH_CAMELLIA_256_CBC_SHA;
    public static final CipherSuite TLS_RSA_WITH_CAMELLIA_256_CBC_SHA256;
    public static final CipherSuite TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384;
    private static final CipherSuite[] Z;
    static final CipherSuite f;
    static final CipherSuite g;
    static final CipherSuite h;
    static final CipherSuite i;
    static final CipherSuite j;
    static final CipherSuite k;
    static final CipherSuite l;
    private static Hashtable r;
    private static final CipherSuite u;
    private static final CipherSuite v;
    private static final CipherSuite w;
    private static final CipherSuite x;
    private static final CipherSuite y;
    private static final CipherSuite z;
    private String M;
    private int N;
    private String O;
    private String P;
    private String Q;
    private int R;
    private int S;
    private int T;
    private int U;
    private boolean V;
    private String W;
    private int X;
    private int Y;
    private static final HashMap m = new HashMap(126);
    private static final HashMap n = new HashMap(252);
    static final CipherSuiteList a = new CipherSuiteList();
    static final CipherSuiteList b = new CipherSuiteList();
    static final CipherSuiteList c = new CipherSuiteList();
    private static final ArrayList o = new ArrayList(215);
    private static final HashMap p = new HashMap(23);
    private static final ArrayList q = new ArrayList(6);
    static final CipherSuite d = new CipherSuite("TLS_EMPTY_RENEGOTIATION_INFO_SCSV", "NULL", "NULL", "NULL", 255, 0, 0, 0, 0, 3);
    public static final CipherSuite TLS_FALLBACK_SCSV = new CipherSuite("TLS_FALLBACK_SCSV", "NULL", "NULL", "NULL", 22016, 0, 0, 0, 0, 3);
    static final CipherSuite e = new CipherSuite("SSL_NULL_WITH_NULL_NULL", "NULL", 0, 0, 0, 0, 512, 3);
    public static final CipherSuite SSL_RSA_WITH_NULL_MD5 = new CipherSuite("SSL_RSA_WITH_NULL_MD5", "NULL", 1, 0, 0, 0, -1, 4);
    public static final CipherSuite SSL_RSA_WITH_NULL_SHA = new CipherSuite("SSL_RSA_WITH_NULL_SHA", "NULL", 2, 0, 0, 0, -1, 4);
    public static final CipherSuite TLS_RSA_WITH_NULL_SHA256 = new CipherSuite("TLS_RSA_WITH_NULL_SHA256", "NULL", 59, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
    public static final CipherSuite SSL_RSA_EXPORT_WITH_RC4_40_MD5 = new CipherSuite("SSL_RSA_EXPORT_WITH_RC4_40_MD5", SecurityProvider.ALG_CIPHER_RC4, 3, 5, 16, 0, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_RSA_WITH_RC4_128_MD5 = new CipherSuite("SSL_RSA_WITH_RC4_128_MD5", SecurityProvider.ALG_CIPHER_RC4, 4, 16, 16, 0, -1, 4);
    public static final CipherSuite SSL_RSA_WITH_RC4_128_SHA = new CipherSuite("SSL_RSA_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 5, 16, 16, 0, -1, 5);
    public static final CipherSuite SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5 = new CipherSuite("SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5", SecurityProvider.ALG_CIPHER_RC2, 6, 5, 16, 8, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_RSA_WITH_IDEA_CBC_SHA = new CipherSuite("SSL_RSA_WITH_IDEA_CBC_SHA", SecurityProvider.ALG_CIPHER_IDEA, 7, 16, 16, 8, -1, 2, SSLContext.VERSION_TLS11, 4);
    public static final CipherSuite SSL_RSA_EXPORT_WITH_DES40_CBC_SHA = new CipherSuite("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 8, 5, 8, 8, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_RSA_WITH_DES_CBC_SHA = new CipherSuite("SSL_RSA_WITH_DES_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 9, 8, 8, 8, -1, 2, SSLContext.VERSION_TLS11, 4);
    public static final CipherSuite SSL_RSA_WITH_3DES_EDE_CBC_SHA = new CipherSuite("SSL_RSA_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 10, 24, 24, 8, -1, 5);
    public static final CipherSuite SSL_DH_DSS_EXPORT_WITH_DES40_CBC_SHA = new CipherSuite("SSL_DH_DSS_EXPORT_WITH_DES40_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 11, 5, 8, 8, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DH_DSS_WITH_DES_CBC_SHA = new CipherSuite("SSL_DH_DSS_WITH_DES_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 12, 8, 8, 8, -1, 2, SSLContext.VERSION_TLS11, 4);
    public static final CipherSuite SSL_DH_DSS_WITH_3DES_EDE_CBC_SHA = new CipherSuite("SSL_DH_DSS_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 13, 24, 24, 8, -1, 4);
    public static final CipherSuite SSL_DH_RSA_EXPORT_WITH_DES40_CBC_SHA = new CipherSuite("SSL_DH_RSA_EXPORT_WITH_DES40_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 14, 5, 8, 8, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DH_RSA_WITH_DES_CBC_SHA = new CipherSuite("SSL_DH_RSA_WITH_DES_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 15, 8, 8, 8, -1, 2, SSLContext.VERSION_TLS11, 4);
    public static final CipherSuite SSL_DH_RSA_WITH_3DES_EDE_CBC_SHA = new CipherSuite("SSL_DH_RSA_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 16, 24, 24, 8, -1, 4);
    public static final CipherSuite SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA = new CipherSuite("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 17, 5, 8, 8, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DHE_DSS_WITH_DES_CBC_SHA = new CipherSuite("SSL_DHE_DSS_WITH_DES_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 18, 8, 8, 8, -1, 2, SSLContext.VERSION_TLS11, 4);
    public static final CipherSuite SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA = new CipherSuite("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 19, 24, 24, 8, -1, 4);
    public static final CipherSuite SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA = new CipherSuite("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 20, 5, 8, 8, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DHE_RSA_WITH_DES_CBC_SHA = new CipherSuite("SSL_DHE_RSA_WITH_DES_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 21, 8, 8, 8, -1, 2, SSLContext.VERSION_TLS11, 4);
    public static final CipherSuite SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA = new CipherSuite("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 22, 24, 24, 8, -1, 4);
    public static final CipherSuite SSL_DH_anon_EXPORT_WITH_RC4_40_MD5 = new CipherSuite("SSL_DH_anon_EXPORT_WITH_RC4_40_MD5", SecurityProvider.ALG_CIPHER_RC4, 23, 5, 16, 0, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DH_anon_WITH_RC4_128_MD5 = new CipherSuite("SSL_DH_anon_WITH_RC4_128_MD5", SecurityProvider.ALG_CIPHER_RC4, 24, 16, 16, 0, -1, 4);
    public static final CipherSuite SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA = new CipherSuite("SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 25, 5, 8, 8, 512, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DH_anon_WITH_DES_CBC_SHA = new CipherSuite("SSL_DH_anon_WITH_DES_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 26, 8, 8, 8, -1, 2, SSLContext.VERSION_TLS11, 4);
    public static final CipherSuite SSL_DH_anon_WITH_3DES_EDE_CBC_SHA = new CipherSuite("SSL_DH_anon_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 27, 24, 24, 8, -1, 4);
    private static final CipherSuite s = new CipherSuite("SSL_FORTEZZA_DMS_WITH_NULL_SHA", 28);
    private static final CipherSuite t = new CipherSuite("SSL_FORTEZZA_DMS_WITH_FORTEZZA_CBC_SHA", 29);
    public static final CipherSuite SSL_RSA_EXPORT1024_WITH_DES_CBC_SHA = new CipherSuite("SSL_RSA_EXPORT1024_WITH_DES_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 98, 8, 8, 8, 1024, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DHE_DSS_EXPORT1024_WITH_DES_CBC_SHA = new CipherSuite("SSL_DHE_DSS_EXPORT1024_WITH_DES_CBC_SHA", SecurityProvider.ALG_CIPHER_DES, 99, 8, 8, 8, 1024, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_RSA_EXPORT1024_WITH_RC4_56_SHA = new CipherSuite("SSL_RSA_EXPORT1024_WITH_RC4_56_SHA", SecurityProvider.ALG_CIPHER_RC4, 100, 7, 16, 0, 1024, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DHE_DSS_EXPORT1024_WITH_RC4_56_SHA = new CipherSuite("SSL_DHE_DSS_EXPORT1024_WITH_RC4_56_SHA", SecurityProvider.ALG_CIPHER_RC4, 101, 7, 16, 0, 1024, 2, SSLContext.VERSION_TLS10, 4);
    public static final CipherSuite SSL_DHE_DSS_WITH_RC4_128_SHA = new CipherSuite("SSL_DHE_DSS_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 102, 16, 16, 0, -1, 4);

    static {
        CipherSuite cipherSuite = new CipherSuite("TLS_RSA_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 47, 16, 16, 16, -1, 5);
        TLS_RSA_WITH_AES_128_CBC_SHA = cipherSuite;
        TLS_RSA_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_RSA_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 60, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        SSL_RSA_WITH_AES_128_CBC_SHA = cipherSuite;
        CipherSuite cipherSuite2 = new CipherSuite("TLS_DH_DSS_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 48, 16, 16, 16, -1, 4);
        TLS_DH_DSS_WITH_AES_128_CBC_SHA = cipherSuite2;
        SSL_DH_DSS_WITH_AES_128_CBC_SHA = cipherSuite2;
        TLS_DH_DSS_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_DH_DSS_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 62, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite3 = new CipherSuite("TLS_DH_RSA_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49, 16, 16, 16, -1, 4);
        TLS_DH_RSA_WITH_AES_128_CBC_SHA = cipherSuite3;
        SSL_DH_RSA_WITH_AES_128_CBC_SHA = cipherSuite3;
        TLS_DH_RSA_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_DH_RSA_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 63, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite4 = new CipherSuite("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 50, 16, 16, 16, -1, 4);
        TLS_DHE_DSS_WITH_AES_128_CBC_SHA = cipherSuite4;
        SSL_DHE_DSS_WITH_AES_128_CBC_SHA = cipherSuite4;
        TLS_DHE_DSS_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 64, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite5 = new CipherSuite("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 51, 16, 16, 16, -1, 4);
        TLS_DHE_RSA_WITH_AES_128_CBC_SHA = cipherSuite5;
        SSL_DHE_RSA_WITH_AES_128_CBC_SHA = cipherSuite5;
        TLS_DHE_RSA_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 103, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite6 = new CipherSuite("TLS_DH_anon_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 52, 16, 16, 16, -1, 4);
        TLS_DH_anon_WITH_AES_128_CBC_SHA = cipherSuite6;
        SSL_DH_anon_WITH_AES_128_CBC_SHA = cipherSuite6;
        TLS_DH_anon_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_DH_anon_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 108, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite7 = new CipherSuite("TLS_RSA_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 53, 32, 32, 16, -1, 5);
        TLS_RSA_WITH_AES_256_CBC_SHA = cipherSuite7;
        TLS_RSA_WITH_AES_256_CBC_SHA256 = new CipherSuite("TLS_RSA_WITH_AES_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 61, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        SSL_RSA_WITH_AES_256_CBC_SHA = cipherSuite7;
        CipherSuite cipherSuite8 = new CipherSuite("TLS_DH_DSS_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 54, 32, 32, 16, -1, 4);
        TLS_DH_DSS_WITH_AES_256_CBC_SHA = cipherSuite8;
        SSL_DH_DSS_WITH_AES_256_CBC_SHA = cipherSuite8;
        TLS_DH_DSS_WITH_AES_256_CBC_SHA256 = new CipherSuite("TLS_DH_DSS_WITH_AES_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 104, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite9 = new CipherSuite("TLS_DH_RSA_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 55, 32, 32, 16, -1, 4);
        TLS_DH_RSA_WITH_AES_256_CBC_SHA = cipherSuite9;
        SSL_DH_RSA_WITH_AES_256_CBC_SHA = cipherSuite9;
        TLS_DH_RSA_WITH_AES_256_CBC_SHA256 = new CipherSuite("TLS_DH_RSA_WITH_AES_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 105, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite10 = new CipherSuite("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 56, 32, 32, 16, -1, 4);
        TLS_DHE_DSS_WITH_AES_256_CBC_SHA = cipherSuite10;
        SSL_DHE_DSS_WITH_AES_256_CBC_SHA = cipherSuite10;
        TLS_DHE_DSS_WITH_AES_256_CBC_SHA256 = new CipherSuite("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 106, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite11 = new CipherSuite("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 57, 32, 32, 16, -1, 4);
        TLS_DHE_RSA_WITH_AES_256_CBC_SHA = cipherSuite11;
        SSL_DHE_RSA_WITH_AES_256_CBC_SHA = cipherSuite11;
        TLS_DHE_RSA_WITH_AES_256_CBC_SHA256 = new CipherSuite("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 107, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite12 = new CipherSuite("TLS_DH_anon_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 58, 32, 32, 16, -1, 4);
        TLS_DH_anon_WITH_AES_256_CBC_SHA = cipherSuite12;
        SSL_DH_anon_WITH_AES_256_CBC_SHA = cipherSuite12;
        TLS_DH_anon_WITH_AES_256_CBC_SHA256 = new CipherSuite("TLS_DH_anon_WITH_AES_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 109, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_WITH_CAMELLIA_128_CBC_SHA = new CipherSuite("TLS_RSA_WITH_CAMELLIA_128_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 65, 16, 16, 16, -1, 5);
        TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 186, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA = new CipherSuite("TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 66, 16, 16, 16, -1, 4);
        TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 187, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA = new CipherSuite("TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 67, 16, 16, 16, -1, 4);
        TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 188, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA = new CipherSuite("TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 68, 16, 16, 16, -1, 4);
        TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 189, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA = new CipherSuite("TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 69, 16, 16, 16, -1, 4);
        TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 190, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA = new CipherSuite("TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 70, 16, 16, 16, -1, 4);
        TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 191, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_WITH_CAMELLIA_256_CBC_SHA = new CipherSuite("TLS_RSA_WITH_CAMELLIA_256_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 132, 32, 32, 16, -1, 5);
        TLS_RSA_WITH_CAMELLIA_256_CBC_SHA256 = new CipherSuite("TLS_RSA_WITH_CAMELLIA_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 192, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA = new CipherSuite("TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 133, 32, 32, 16, -1, 4);
        TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256 = new CipherSuite("TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, Wbxml.EXT_1, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA = new CipherSuite("TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 134, 32, 32, 16, -1, 4);
        TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256 = new CipherSuite("TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, Wbxml.EXT_2, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA = new CipherSuite("TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, EMVL2CommandID.EMV_L2_PIN_ENTRY_SHOW_PROMPT, 32, 32, 16, -1, 4);
        TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256 = new CipherSuite("TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, Wbxml.OPAQUE, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA = new CipherSuite("TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 136, 32, 32, 16, -1, 4);
        TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256 = new CipherSuite("TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 196, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA = new CipherSuite("TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA", SecurityProvider.ALG_CIPHER_CAMELLIA, 137, 32, 32, 16, -1, 4);
        TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256 = new CipherSuite("TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 197, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        u = new CipherSuite("PRIVATE_NETSCAPE_FIPS140_RSA_WITH_3DES_EDE_CBC_SHA", 65504);
        v = new CipherSuite("PRIVATE_NETSCAPE_FIPS140_RSA_WITH_DES_CBC_SHA", 65505);
        w = new CipherSuite("OBSOLETE_EXPORT1024_0x0060", 96);
        x = new CipherSuite("OBSOLETE_EXPORT1024_0x0061", 97);
        y = new CipherSuite("TLS_KRB5_WITH_DES_CBC_SHA", 30);
        z = new CipherSuite("TLS_KRB5_WITH_3DES_EDE_CBC_SHA", 31);
        A = new CipherSuite("TLS_KRB5_WITH_RC4_128_SHA", 32);
        B = new CipherSuite("TLS_KRB5_WITH_IDEA_CBC_SHA", 33);
        C = new CipherSuite("TLS_KRB5_WITH_DES_CBC_MD5", 34);
        D = new CipherSuite("TLS_KRB5_WITH_3DES_EDE_CBC_MD5", 35);
        E = new CipherSuite("TLS_KRB5_WITH_RC4_128_MD5", 36);
        F = new CipherSuite("TLS_KRB5_WITH_IDEA_CBC_MD5", 37);
        G = new CipherSuite("TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA", 38);
        H = new CipherSuite("TLS_KRB5_EXPORT_WITH_RC2_CBC_40_SHA", 39);
        I = new CipherSuite("TLS_KRB5_EXPORT_WITH_RC4_40_SHA", 40);
        J = new CipherSuite("TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5", 41);
        K = new CipherSuite("TLS_KRB5_EXPORT_WITH_RC2_CBC_40_MD5", 42);
        L = new CipherSuite("TLS_KRB5_EXPORT_WITH_RC4_40_MD5", 43);
        PRIVATE_RSA_WITH_RC2_CBC_MD5 = new CipherSuite("PRIVATE_RSA_WITH_RC2_CBC_MD5", SecurityProvider.ALG_CIPHER_RC2, 65371, 16, 16, 8, -1, 3);
        f = new CipherSuite("SSL2_RSA_WITH_RC4_128_MD5", SecurityProvider.ALG_CIPHER_RC4, 65664, 16, 16, 0, -1, 2);
        g = new CipherSuite("SSL2_RSA_WITH_RC4_128_EXPORT40_MD5", SecurityProvider.ALG_CIPHER_RC4, 131200, 5, 16, 0, 512, 2, 2, 2);
        h = new CipherSuite("SSL2_RSA_WITH_RC2_128_CBC_MD5", SecurityProvider.ALG_CIPHER_RC2, 196736, 16, 16, 8, -1, 2);
        i = new CipherSuite("SSL2_RSA_WITH_RC2_128_CBC_EXPORT40_MD5", SecurityProvider.ALG_CIPHER_RC2, 262272, 5, 16, 8, 512, 2, 2, 2);
        j = new CipherSuite("SSL2_RSA_WITH_IDEA_128_CBC_MD5", SecurityProvider.ALG_CIPHER_IDEA, 327808, 16, 16, 8, -1, 2, 2, 2);
        k = new CipherSuite("SSL2_RSA_WITH_DES_64_CBC_MD5", SecurityProvider.ALG_CIPHER_DES, 393280, 8, 8, 8, -1, 2, 2, 2);
        l = new CipherSuite("SSL2_RSA_WITH_DES_192_EDE3_CBC_MD5", SecurityProvider.ALG_CIPHER_3DES, 458944, 24, 24, 8, -1, 2, 2, 2);
        TLS_PSK_WITH_RC4_128_SHA = new CipherSuite("TLS_PSK_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 138, 16, 16, 0, -1, 4);
        TLS_PSK_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_PSK_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 139, 24, 24, 8, -1, 4);
        TLS_PSK_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_PSK_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 140, 16, 16, 16, -1, 4);
        TLS_PSK_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_PSK_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 141, 32, 32, 16, -1, 4);
        TLS_PSK_WITH_NULL_SHA = new CipherSuite("TLS_PSK_WITH_NULL_SHA", "NULL", 44, 0, 0, 0, -1, 4);
        TLS_DHE_PSK_WITH_RC4_128_SHA = new CipherSuite("TLS_DHE_PSK_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 142, 16, 16, 0, -1, 4);
        TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 143, 24, 24, 8, -1, 4);
        TLS_DHE_PSK_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_DHE_PSK_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 144, 16, 16, 16, -1, 4);
        TLS_DHE_PSK_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_DHE_PSK_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 145, 32, 32, 16, -1, 4);
        TLS_DHE_PSK_WITH_NULL_SHA = new CipherSuite("TLS_DHE_PSK_WITH_NULL_SHA", "NULL", 45, 0, 0, 0, -1, 4);
        TLS_RSA_PSK_WITH_RC4_128_SHA = new CipherSuite("TLS_RSA_PSK_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 146, 16, 16, 0, -1, 4);
        TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 147, 24, 24, 8, -1, 4);
        TLS_RSA_PSK_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_RSA_PSK_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 148, 16, 16, 16, -1, 4);
        TLS_RSA_PSK_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_RSA_PSK_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 149, 32, 32, 16, -1, 4);
        TLS_RSA_PSK_WITH_NULL_SHA = new CipherSuite("TLS_RSA_PSK_WITH_NULL_SHA", "NULL", 46, 0, 0, 0, -1, 4);
        TLS_ECDHE_PSK_WITH_RC4_128_SHA = new CipherSuite("TLS_ECDHE_PSK_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 49203, 16, 16, 0, -1, 4);
        TLS_ECDHE_PSK_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_ECDHE_PSK_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 49204, 24, 24, 8, -1, 4);
        TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49205, 16, 16, 16, -1, 4);
        TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49206, 32, 32, 16, -1, 4);
        TLS_ECDHE_PSK_WITH_NULL_SHA = new CipherSuite("TLS_ECDHE_PSK_WITH_NULL_SHA", "NULL", 57, 0, 0, 0, -1, 4);
        TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 49207, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA384 = new CipherSuite("TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_AES, 49208, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDHE_PSK_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_ECDHE_PSK_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 49306, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDHE_PSK_WITH_CAMELLIA_256_CBC_SHA384 = new CipherSuite("TLS_ECDHE_PSK_WITH_CAMELLIA_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA, 49307, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDHE_PSK_WITH_NULL_SHA256 = new CipherSuite("TLS_ECDHE_PSK_WITH_NULL_SHA256", "NULL", 58, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDHE_PSK_WITH_NULL_SHA384 = new CipherSuite("TLS_ECDHE_PSK_WITH_NULL_SHA384", "NULL", 59, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_PSK_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 174, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_AES_256_CBC_SHA384 = new CipherSuite("TLS_PSK_WITH_AES_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_AES, 175, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_PSK_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 49300, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_CAMELLIA_256_CBC_SHA384 = new CipherSuite("TLS_PSK_WITH_CAMELLIA_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA, 49301, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_PSK_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_OSC_TRIM1_REG, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_PSK_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_PHY_TXCTL_REG, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_PSK_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49294, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_PSK_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49295, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_NULL_SHA256 = new CipherSuite("TLS_PSK_WITH_NULL_SHA256", "NULL", FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_DESC_STRING, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_PSK_WITH_NULL_SHA384 = new CipherSuite("TLS_PSK_WITH_NULL_SHA384", "NULL", FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_CHIPTOP_REG, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_DHE_PSK_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_USB_REG, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_AES_256_CBC_SHA384 = new CipherSuite("TLS_DHE_PSK_WITH_AES_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_AES, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_OTP_REG, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_DHE_PSK_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 49302, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_CAMELLIA_256_CBC_SHA384 = new CipherSuite("TLS_DHE_PSK_WITH_CAMELLIA_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA, 49303, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_DHE_PSK_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 170, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_DHE_PSK_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, 171, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_DHE_PSK_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49296, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_DHE_PSK_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49297, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_NULL_SHA256 = new CipherSuite("TLS_DHE_PSK_WITH_NULL_SHA256", "NULL", FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_SFR, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_PSK_WITH_NULL_SHA384 = new CipherSuite("TLS_DHE_PSK_WITH_NULL_SHA384", "NULL", 181, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_RSA_PSK_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 182, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_AES_256_CBC_SHA384 = new CipherSuite("TLS_RSA_PSK_WITH_AES_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_AES, 183, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_RSA_PSK_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 49304, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_CAMELLIA_256_CBC_SHA384 = new CipherSuite("TLS_RSA_PSK_WITH_CAMELLIA_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA, 49305, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_RSA_PSK_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 172, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_RSA_PSK_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, 173, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_RSA_PSK_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49298, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_RSA_PSK_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49299, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_NULL_SHA256 = new CipherSuite("TLS_RSA_PSK_WITH_NULL_SHA256", "NULL", 184, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_RSA_PSK_WITH_NULL_SHA384 = new CipherSuite("TLS_RSA_PSK_WITH_NULL_SHA384", "NULL", 185, 0, 0, 0, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDH_ECDSA_WITH_NULL_SHA = new CipherSuite("TLS_ECDH_ECDSA_WITH_NULL_SHA", "NULL", 49153, 0, 0, 0, -1, 4);
        TLS_ECDH_ECDSA_WITH_RC4_128_SHA = new CipherSuite("TLS_ECDH_ECDSA_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 49154, 16, 16, 0, -1, 4);
        TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 49155, 24, 24, 8, -1, 4);
        TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49156, 16, 16, 16, -1, 4);
        TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 49189, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49157, 32, 32, 16, -1, 4);
        TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384 = new CipherSuite("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_AES, 49190, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDH_ECDSA_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_ECDH_ECDSA_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 49268, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDH_ECDSA_WITH_CAMELLIA_256_CBC_SHA384 = new CipherSuite("TLS_ECDH_ECDSA_WITH_CAMELLIA_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA, 49269, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDHE_ECDSA_WITH_NULL_SHA = new CipherSuite("TLS_ECDHE_ECDSA_WITH_NULL_SHA", "NULL", 49158, 0, 0, 0, -1, 4);
        TLS_ECDHE_ECDSA_WITH_RC4_128_SHA = new CipherSuite("TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 49159, 16, 16, 0, -1, 5);
        TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 49160, 24, 24, 8, -1, 5);
        TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49161, 16, 16, 16, -1, 5);
        TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 49187, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49162, 32, 32, 16, -1, 5);
        TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384 = new CipherSuite("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_AES, 49188, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 49266, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384 = new CipherSuite("TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA, 49267, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_ECDH_RSA_WITH_NULL_SHA = new CipherSuite("TLS_ECDH_RSA_WITH_NULL_SHA", "NULL", 49163, 0, 0, 0, -1, 4);
        TLS_ECDH_RSA_WITH_RC4_128_SHA = new CipherSuite("TLS_ECDH_RSA_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 49164, 16, 16, 0, -1, 4);
        TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 49165, 24, 24, 8, -1, 4);
        TLS_ECDH_RSA_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49166, 16, 16, 16, -1, 4);
        TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 49193, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDH_RSA_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49167, 32, 32, 16, -1, 4);
        TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384 = new CipherSuite("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_AES, 49194, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDH_RSA_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_ECDH_RSA_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 49272, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDH_RSA_WITH_CAMELLIA_256_CBC_SHA384 = new CipherSuite("TLS_ECDH_RSA_WITH_CAMELLIA_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA, 49273, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDHE_RSA_WITH_NULL_SHA = new CipherSuite("TLS_ECDHE_RSA_WITH_NULL_SHA", "NULL", 49168, 0, 0, 0, -1, 4);
        TLS_ECDHE_RSA_WITH_RC4_128_SHA = new CipherSuite("TLS_ECDHE_RSA_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 49169, 16, 16, 0, -1, 5);
        TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 49170, 24, 24, 8, -1, 5);
        TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49171, 16, 16, 16, -1, 5);
        TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256 = new CipherSuite("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_AES, 49191, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49172, 32, 32, 16, -1, 5);
        TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384 = new CipherSuite("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_AES, 49192, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256 = new CipherSuite("TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA, 49270, 16, 16, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384 = new CipherSuite("TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA, 49271, 32, 32, 16, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        CipherSuite cipherSuite13 = new CipherSuite("TLS_ECDH_anon_WITH_NULL_SHA", "NULL", 49173, 0, 0, 0, -1, 4);
        TLS_ECDH_anon_WITH_NULL_SHA = cipherSuite13;
        TLS_ECDH_anon_WITH_RC4_128_SHA = new CipherSuite("TLS_ECDH_anon_WITH_RC4_128_SHA", SecurityProvider.ALG_CIPHER_RC4, 49174, 16, 16, 0, -1, 4);
        TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA = new CipherSuite("TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", SecurityProvider.ALG_CIPHER_3DES, 49175, 24, 24, 8, -1, 4);
        TLS_ECDH_anon_WITH_AES_128_CBC_SHA = new CipherSuite("TLS_ECDH_anon_WITH_AES_128_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49176, 16, 16, 16, -1, 4);
        TLS_ECDH_anon_WITH_AES_256_CBC_SHA = new CipherSuite("TLS_ECDH_anon_WITH_AES_256_CBC_SHA", SecurityProvider.ALG_CIPHER_AES, 49177, 32, 32, 16, -1, 4);
        TLS_RSA_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_RSA_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 156, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_RSA_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_RSA_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, 157, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_DHE_RSA_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 158, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_RSA_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, 159, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_RSA_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_DH_RSA_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 160, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_RSA_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_DH_RSA_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL1_REG, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_DSS_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL2_REG, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_DSS_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_SR_CTL0_REG, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_DSS_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_DH_DSS_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_SR_CTL1_REG, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_DSS_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_DH_DSS_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_REG_TRIM_REG, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_anon_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_DH_anon_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_CLK30K_TRIM_REG, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_anon_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_DH_anon_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_OSC_TRIM0_REG, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 49195, 16, 16, 4, -1, 5);
        TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, 49196, 32, 32, 4, -1, 5);
        TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 49197, 16, 16, 4, -1, 4);
        TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, 49198, 32, 32, 4, -1, 4);
        TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 49199, 16, 16, 4, -1, 5);
        TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, 49200, 32, 32, 4, -1, 5);
        TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256 = new CipherSuite("TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_AES_GCM, 49201, 16, 16, 4, -1, 4);
        TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384 = new CipherSuite("TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_AES_GCM, 49202, 32, 32, 4, -1, 4);
        TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49274, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49275, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 5);
        TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49276, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49277, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_RSA_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_DH_RSA_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49278, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_RSA_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_DH_RSA_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49279, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49280, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49281, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_DSS_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_DH_DSS_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49282, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_DSS_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_DH_DSS_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49283, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        CipherSuite cipherSuite14 = new CipherSuite("TLS_DH_anon_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49284, 16, 16, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_anon_WITH_CAMELLIA_128_GCM_SHA256 = cipherSuite14;
        CipherSuite cipherSuite15 = new CipherSuite("TLS_DH_anon_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49285, 32, 32, 4, -1, SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12, 4);
        TLS_DH_anon_WITH_CAMELLIA_256_GCM_SHA384 = cipherSuite15;
        TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49286, 16, 16, 4, -1, 5);
        TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49287, 32, 32, 4, -1, 5);
        TLS_ECDH_ECDSA_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_ECDH_ECDSA_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49288, 16, 16, 4, -1, 4);
        TLS_ECDH_ECDSA_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_ECDH_ECDSA_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49289, 32, 32, 4, -1, 4);
        TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49290, 16, 16, 4, -1, 5);
        TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49291, 32, 32, 4, -1, 5);
        TLS_ECDH_RSA_WITH_CAMELLIA_128_GCM_SHA256 = new CipherSuite("TLS_ECDH_RSA_WITH_CAMELLIA_128_GCM_SHA256", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49292, 16, 16, 4, -1, 4);
        TLS_ECDH_RSA_WITH_CAMELLIA_256_GCM_SHA384 = new CipherSuite("TLS_ECDH_RSA_WITH_CAMELLIA_256_GCM_SHA384", SecurityProvider.ALG_CIPHER_CAMELLIA_GCM, 49293, 32, 32, 4, -1, 4);
        ArrayList arrayList = o;
        arrayList.add(cipherSuite13);
        arrayList.add(SSL_RSA_WITH_NULL_MD5);
        arrayList.add(TLS_PSK_WITH_NULL_SHA);
        arrayList.add(TLS_PSK_WITH_NULL_SHA256);
        arrayList.add(TLS_PSK_WITH_NULL_SHA384);
        arrayList.add(TLS_RSA_PSK_WITH_NULL_SHA);
        arrayList.add(TLS_DHE_PSK_WITH_NULL_SHA);
        arrayList.add(TLS_RSA_PSK_WITH_NULL_SHA256);
        arrayList.add(TLS_DHE_PSK_WITH_NULL_SHA256);
        arrayList.add(TLS_RSA_PSK_WITH_NULL_SHA384);
        arrayList.add(TLS_DHE_PSK_WITH_NULL_SHA384);
        arrayList.add(TLS_ECDH_RSA_WITH_NULL_SHA);
        arrayList.add(TLS_ECDH_ECDSA_WITH_NULL_SHA);
        arrayList.add(SSL_RSA_WITH_NULL_SHA);
        arrayList.add(TLS_ECDHE_RSA_WITH_NULL_SHA);
        arrayList.add(TLS_ECDHE_ECDSA_WITH_NULL_SHA);
        arrayList.add(TLS_RSA_WITH_NULL_SHA256);
        arrayList.add(SSL_DH_anon_EXPORT_WITH_RC4_40_MD5);
        arrayList.add(SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA);
        arrayList.add(SSL_DH_anon_WITH_RC4_128_MD5);
        arrayList.add(SSL_DH_anon_WITH_DES_CBC_SHA);
        arrayList.add(TLS_ECDH_anon_WITH_RC4_128_SHA);
        arrayList.add(SSL_DH_anon_WITH_3DES_EDE_CBC_SHA);
        arrayList.add(TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA);
        arrayList.add(TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA);
        arrayList.add(TLS_DH_anon_WITH_AES_128_CBC_SHA);
        arrayList.add(TLS_ECDH_anon_WITH_AES_128_CBC_SHA);
        arrayList.add(TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA);
        arrayList.add(TLS_DH_anon_WITH_AES_256_CBC_SHA);
        arrayList.add(TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList.add(TLS_DH_anon_WITH_AES_128_CBC_SHA256);
        arrayList.add(cipherSuite14);
        arrayList.add(TLS_DH_anon_WITH_AES_128_GCM_SHA256);
        arrayList.add(TLS_ECDH_anon_WITH_AES_256_CBC_SHA);
        arrayList.add(TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256);
        arrayList.add(TLS_DH_anon_WITH_AES_256_CBC_SHA256);
        arrayList.add(cipherSuite15);
        arrayList.add(TLS_DH_anon_WITH_AES_256_GCM_SHA384);
        arrayList.add(SSL_DH_DSS_EXPORT_WITH_DES40_CBC_SHA);
        arrayList.add(SSL_DH_RSA_EXPORT_WITH_DES40_CBC_SHA);
        arrayList.add(SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5);
        arrayList.add(SSL_RSA_EXPORT_WITH_RC4_40_MD5);
        arrayList.add(SSL_RSA_EXPORT_WITH_DES40_CBC_SHA);
        arrayList.add(SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA);
        arrayList.add(SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA);
        arrayList.add(SSL_RSA_EXPORT1024_WITH_RC4_56_SHA);
        arrayList.add(SSL_DHE_DSS_EXPORT1024_WITH_RC4_56_SHA);
        arrayList.add(SSL_RSA_EXPORT1024_WITH_DES_CBC_SHA);
        arrayList.add(SSL_DHE_DSS_EXPORT1024_WITH_DES_CBC_SHA);
        arrayList.add(TLS_PSK_WITH_RC4_128_SHA);
        arrayList.add(TLS_PSK_WITH_3DES_EDE_CBC_SHA);
        arrayList.add(TLS_PSK_WITH_AES_128_CBC_SHA);
        arrayList.add(TLS_PSK_WITH_AES_256_CBC_SHA);
        ArrayList arrayList2 = o;
        arrayList2.add(TLS_PSK_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList2.add(TLS_PSK_WITH_AES_128_CBC_SHA256);
        arrayList2.add(TLS_PSK_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList2.add(TLS_PSK_WITH_AES_128_GCM_SHA256);
        arrayList2.add(TLS_PSK_WITH_CAMELLIA_256_CBC_SHA384);
        arrayList2.add(TLS_PSK_WITH_AES_256_CBC_SHA384);
        arrayList2.add(TLS_PSK_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList2.add(TLS_PSK_WITH_AES_256_GCM_SHA384);
        arrayList2.add(TLS_RSA_PSK_WITH_RC4_128_SHA);
        arrayList2.add(TLS_DHE_PSK_WITH_RC4_128_SHA);
        arrayList2.add(TLS_ECDHE_PSK_WITH_RC4_128_SHA);
        arrayList2.add(TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA);
        arrayList2.add(TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA);
        arrayList2.add(TLS_RSA_PSK_WITH_AES_128_CBC_SHA);
        arrayList2.add(TLS_DHE_PSK_WITH_AES_128_CBC_SHA);
        arrayList2.add(TLS_ECDHE_PSK_WITH_3DES_EDE_CBC_SHA);
        arrayList2.add(TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA);
        arrayList2.add(TLS_RSA_PSK_WITH_AES_256_CBC_SHA);
        arrayList2.add(TLS_RSA_PSK_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList2.add(TLS_RSA_PSK_WITH_AES_128_CBC_SHA256);
        arrayList2.add(TLS_RSA_PSK_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList2.add(TLS_RSA_PSK_WITH_AES_128_GCM_SHA256);
        arrayList2.add(TLS_DHE_PSK_WITH_AES_256_CBC_SHA);
        arrayList2.add(TLS_DHE_PSK_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList2.add(TLS_DHE_PSK_WITH_AES_128_CBC_SHA256);
        arrayList2.add(TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA);
        arrayList2.add(TLS_ECDHE_PSK_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList2.add(TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA256);
        arrayList2.add(TLS_DHE_PSK_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList2.add(TLS_DHE_PSK_WITH_AES_128_GCM_SHA256);
        arrayList2.add(TLS_RSA_PSK_WITH_CAMELLIA_256_CBC_SHA384);
        arrayList2.add(TLS_RSA_PSK_WITH_AES_256_CBC_SHA384);
        arrayList2.add(TLS_RSA_PSK_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList2.add(TLS_RSA_PSK_WITH_AES_256_GCM_SHA384);
        arrayList2.add(TLS_DHE_PSK_WITH_CAMELLIA_256_CBC_SHA384);
        arrayList2.add(TLS_DHE_PSK_WITH_AES_256_CBC_SHA384);
        arrayList2.add(TLS_ECDHE_PSK_WITH_CAMELLIA_256_CBC_SHA384);
        arrayList2.add(TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA384);
        arrayList2.add(TLS_DHE_PSK_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList2.add(TLS_DHE_PSK_WITH_AES_256_GCM_SHA384);
        arrayList2.add(SSL_RSA_WITH_RC4_128_MD5);
        arrayList2.add(SSL_DH_DSS_WITH_DES_CBC_SHA);
        arrayList2.add(SSL_DH_RSA_WITH_DES_CBC_SHA);
        arrayList2.add(SSL_RSA_WITH_DES_CBC_SHA);
        arrayList2.add(SSL_DHE_DSS_WITH_DES_CBC_SHA);
        arrayList2.add(SSL_DHE_RSA_WITH_DES_CBC_SHA);
        arrayList2.add(SSL_RSA_WITH_IDEA_CBC_SHA);
        arrayList2.add(SSL_RSA_WITH_RC4_128_SHA);
        arrayList2.add(TLS_ECDH_RSA_WITH_RC4_128_SHA);
        arrayList2.add(TLS_ECDH_ECDSA_WITH_RC4_128_SHA);
        ArrayList arrayList3 = o;
        arrayList3.add(SSL_DHE_DSS_WITH_RC4_128_SHA);
        arrayList3.add(SSL_DH_DSS_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(SSL_DH_RSA_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(TLS_ECDHE_RSA_WITH_RC4_128_SHA);
        arrayList3.add(TLS_ECDHE_ECDSA_WITH_RC4_128_SHA);
        arrayList3.add(TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA);
        arrayList3.add(TLS_DH_DSS_WITH_AES_128_CBC_SHA);
        arrayList3.add(TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA);
        arrayList3.add(TLS_DH_RSA_WITH_AES_128_CBC_SHA);
        arrayList3.add(SSL_RSA_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(TLS_RSA_WITH_CAMELLIA_128_CBC_SHA);
        arrayList3.add(TLS_RSA_WITH_AES_128_CBC_SHA);
        arrayList3.add(SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA);
        arrayList3.add(TLS_ECDH_RSA_WITH_AES_128_CBC_SHA);
        arrayList3.add(TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA);
        arrayList3.add(TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA);
        arrayList3.add(TLS_DH_DSS_WITH_AES_256_CBC_SHA);
        arrayList3.add(TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA);
        arrayList3.add(TLS_DH_RSA_WITH_AES_256_CBC_SHA);
        arrayList3.add(TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList3.add(TLS_DH_DSS_WITH_AES_128_CBC_SHA256);
        arrayList3.add(TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList3.add(TLS_DH_RSA_WITH_AES_128_CBC_SHA256);
        arrayList3.add(TLS_DH_DSS_WITH_AES_128_GCM_SHA256);
        arrayList3.add(TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
        arrayList3.add(TLS_ECDH_RSA_WITH_AES_256_CBC_SHA);
        arrayList3.add(TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA);
        arrayList3.add(TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256);
        arrayList3.add(TLS_DH_DSS_WITH_AES_256_CBC_SHA256);
        arrayList3.add(TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256);
        arrayList3.add(TLS_DH_RSA_WITH_AES_256_CBC_SHA256);
        arrayList3.add(TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA);
        arrayList3.add(TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA);
        arrayList3.add(TLS_DHE_DSS_WITH_AES_128_CBC_SHA);
        arrayList3.add(TLS_DHE_RSA_WITH_AES_128_CBC_SHA);
        arrayList3.add(TLS_RSA_WITH_CAMELLIA_256_CBC_SHA);
        arrayList3.add(TLS_RSA_WITH_AES_256_CBC_SHA);
        arrayList3.add(TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList3.add(TLS_RSA_WITH_AES_128_CBC_SHA256);
        arrayList3.add(TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256);
        arrayList3.add(TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256);
        arrayList3.add(TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList3.add(TLS_RSA_WITH_AES_128_GCM_SHA256);
        arrayList3.add(TLS_DH_DSS_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList3.add(TLS_DH_RSA_WITH_CAMELLIA_128_GCM_SHA256);
        ArrayList arrayList4 = o;
        arrayList4.add(TLS_ECDH_RSA_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList4.add(TLS_ECDH_ECDSA_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList4.add(TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA);
        arrayList4.add(TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA);
        arrayList4.add(TLS_ECDH_RSA_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList4.add(TLS_ECDH_ECDSA_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList4.add(TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256);
        arrayList4.add(TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256);
        arrayList4.add(TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA);
        arrayList4.add(TLS_DHE_DSS_WITH_AES_256_CBC_SHA);
        arrayList4.add(TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA);
        arrayList4.add(TLS_DHE_RSA_WITH_AES_256_CBC_SHA);
        arrayList4.add(TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList4.add(TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList4.add(TLS_DHE_DSS_WITH_AES_128_CBC_SHA256);
        arrayList4.add(TLS_DHE_RSA_WITH_AES_128_CBC_SHA256);
        arrayList4.add(TLS_RSA_WITH_CAMELLIA_256_CBC_SHA256);
        arrayList4.add(TLS_RSA_WITH_AES_256_CBC_SHA256);
        arrayList4.add(TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA);
        arrayList4.add(TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA);
        arrayList4.add(TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256);
        arrayList4.add(TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256);
        arrayList4.add(TLS_DHE_DSS_WITH_AES_256_CBC_SHA256);
        arrayList4.add(TLS_DHE_RSA_WITH_AES_256_CBC_SHA256);
        arrayList4.add(TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList4.add(TLS_DHE_DSS_WITH_AES_128_GCM_SHA256);
        arrayList4.add(TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList4.add(TLS_DHE_RSA_WITH_AES_128_GCM_SHA256);
        arrayList4.add(TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList4.add(TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256);
        arrayList4.add(TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256);
        arrayList4.add(TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256);
        arrayList4.add(TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList4.add(TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256);
        arrayList4.add(TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256);
        arrayList4.add(TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256);
        arrayList4.add(TLS_DH_DSS_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList4.add(TLS_DH_DSS_WITH_AES_256_GCM_SHA384);
        arrayList4.add(TLS_DH_RSA_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList4.add(TLS_DH_RSA_WITH_AES_256_GCM_SHA384);
        arrayList4.add(TLS_ECDH_RSA_WITH_CAMELLIA_256_CBC_SHA384);
        arrayList4.add(TLS_ECDH_ECDSA_WITH_CAMELLIA_256_CBC_SHA384);
        arrayList4.add(TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384);
        arrayList4.add(TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384);
        arrayList4.add(TLS_ECDH_RSA_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList4.add(TLS_ECDH_ECDSA_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList4.add(TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384);
        arrayList4.add(TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384);
        CipherSuite cipherSuite16 = TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384;
        arrayList4.add(cipherSuite16);
        CipherSuite cipherSuite17 = TLS_RSA_WITH_AES_256_GCM_SHA384;
        arrayList4.add(cipherSuite17);
        ArrayList arrayList5 = o;
        arrayList5.add(TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList5.add(TLS_DHE_DSS_WITH_AES_256_GCM_SHA384);
        arrayList5.add(TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList5.add(TLS_DHE_RSA_WITH_AES_256_GCM_SHA384);
        arrayList5.add(TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384);
        arrayList5.add(TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384);
        arrayList5.add(TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384);
        arrayList5.add(TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384);
        arrayList5.add(TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList5.add(TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384);
        arrayList5.add(TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384);
        arrayList5.add(TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384);
        HashMap map = p;
        map.put("DH_anon_EXPORT", new Integer(1));
        map.put("DH_anon", new Integer(2));
        map.put("ECDH_anon", new Integer(3));
        map.put("DH_DSS_EXPORT", new Integer(4));
        map.put("DH_RSA_EXPORT", new Integer(5));
        map.put("RSA_EXPORT", new Integer(6));
        map.put("DHE_DSS_EXPORT", new Integer(7));
        map.put("DHE_RSA_EXPORT", new Integer(8));
        map.put("RSA_EXPORT1024", new Integer(9));
        map.put("DHE_DSS_EXPORT1024", new Integer(10));
        map.put("PSK", new Integer(11));
        map.put("RSA_PSK", new Integer(12));
        map.put("DHE_PSK", new Integer(13));
        map.put("ECDHE_PSK", new Integer(14));
        map.put(KeyTypeNames.DH_DSS, new Integer(15));
        map.put(KeyTypeNames.DH_RSA, new Integer(16));
        map.put("ECDH_ECDSA", new Integer(17));
        map.put("ECDH_RSA", new Integer(17));
        map.put("RSA", new Integer(18));
        map.put("DHE_DSS", new Integer(19));
        map.put("DHE_RSA", new Integer(20));
        map.put("ECDHE_ECDSA", new Integer(21));
        map.put("ECDHE_RSA", new Integer(21));
        ArrayList arrayList6 = q;
        arrayList6.add(SecurityProvider.ALG_DIGEST_MD5);
        arrayList6.add("SHA");
        arrayList6.add(SecurityProvider.ALG_DIGEST_SHA224);
        arrayList6.add(SecurityProvider.ALG_DIGEST_SHA256);
        arrayList6.add(SecurityProvider.ALG_DIGEST_SHA384);
        arrayList6.add(SecurityProvider.ALG_DIGEST_SHA512);
        c.sort();
        b.sort();
        CS_SSL_V2 = new CipherSuite[]{l, j, f, h, k, g, i};
        CipherSuite cipherSuite18 = SSL_RSA_WITH_3DES_EDE_CBC_SHA;
        CipherSuite cipherSuite19 = SSL_RSA_WITH_IDEA_CBC_SHA;
        CipherSuite cipherSuite20 = SSL_RSA_WITH_RC4_128_MD5;
        CipherSuite cipherSuite21 = SSL_RSA_WITH_DES_CBC_SHA;
        CipherSuite cipherSuite22 = SSL_RSA_EXPORT_WITH_RC4_40_MD5;
        CipherSuite cipherSuite23 = SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5;
        Z = new CipherSuite[]{cipherSuite18, cipherSuite19, cipherSuite20, PRIVATE_RSA_WITH_RC2_CBC_MD5, cipherSuite21, cipherSuite22, cipherSuite23};
        CS_RSA = a(new CipherSuite[]{cipherSuite20, SSL_RSA_WITH_RC4_128_SHA, cipherSuite19, cipherSuite21, cipherSuite18, SSL_RSA_WITH_AES_128_CBC_SHA, TLS_RSA_WITH_AES_128_CBC_SHA256, SSL_RSA_WITH_AES_256_CBC_SHA, TLS_RSA_WITH_AES_256_CBC_SHA256, TLS_RSA_WITH_CAMELLIA_128_CBC_SHA, TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, TLS_RSA_WITH_CAMELLIA_256_CBC_SHA256, TLS_RSA_WITH_AES_128_GCM_SHA256, cipherSuite17, TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256, cipherSuite16});
        CS_RSA_WITH_NULL = a(new CipherSuite[]{SSL_RSA_WITH_NULL_MD5, SSL_RSA_WITH_NULL_SHA, TLS_RSA_WITH_NULL_SHA256});
        CS_RSA_EXPORT = a(new CipherSuite[]{cipherSuite22, cipherSuite23, SSL_RSA_EXPORT_WITH_DES40_CBC_SHA});
        CS_RSA_EXPORT1024 = a(new CipherSuite[]{SSL_RSA_EXPORT1024_WITH_DES_CBC_SHA, SSL_RSA_EXPORT1024_WITH_RC4_56_SHA});
        CS_DHE_RSA = a(new CipherSuite[]{SSL_DHE_RSA_WITH_DES_CBC_SHA, SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA, SSL_DHE_RSA_WITH_AES_128_CBC_SHA, TLS_DHE_RSA_WITH_AES_128_CBC_SHA256, SSL_DHE_RSA_WITH_AES_256_CBC_SHA, TLS_DHE_RSA_WITH_AES_256_CBC_SHA256, TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA, TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256, TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA, TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256, TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256, TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384});
        CS_DHE_RSA_EXPORT = a(new CipherSuite[]{SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA});
        CS_DHE_DSS = a(new CipherSuite[]{SSL_DHE_DSS_WITH_DES_CBC_SHA, SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA, SSL_DHE_DSS_WITH_RC4_128_SHA, SSL_DHE_DSS_WITH_AES_128_CBC_SHA, TLS_DHE_DSS_WITH_AES_128_CBC_SHA256, SSL_DHE_DSS_WITH_AES_256_CBC_SHA, TLS_DHE_DSS_WITH_AES_256_CBC_SHA256, TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA, TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256, TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA, TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256, TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256, TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384});
        CS_DHE_DSS_EXPORT = a(new CipherSuite[]{SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA});
        CS_DHE_DSS_EXPORT1024 = a(new CipherSuite[]{SSL_DHE_DSS_EXPORT1024_WITH_DES_CBC_SHA, SSL_DHE_DSS_EXPORT1024_WITH_RC4_56_SHA});
        CS_DH_RSA = a(new CipherSuite[]{SSL_DH_RSA_WITH_DES_CBC_SHA, SSL_DH_RSA_WITH_3DES_EDE_CBC_SHA, SSL_DH_RSA_WITH_AES_128_CBC_SHA, TLS_DH_RSA_WITH_AES_128_CBC_SHA256, SSL_DH_RSA_WITH_AES_256_CBC_SHA, TLS_DH_RSA_WITH_AES_256_CBC_SHA256, TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA, TLS_DH_RSA_WITH_CAMELLIA_128_CBC_SHA256, TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA, TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256, TLS_DH_RSA_WITH_AES_128_GCM_SHA256, TLS_DH_RSA_WITH_AES_256_GCM_SHA384, TLS_DH_RSA_WITH_CAMELLIA_128_GCM_SHA256, TLS_DH_RSA_WITH_CAMELLIA_256_GCM_SHA384});
        CS_DH_RSA_EXPORT = a(new CipherSuite[]{SSL_DH_RSA_EXPORT_WITH_DES40_CBC_SHA});
        CS_DH_DSS = a(new CipherSuite[]{SSL_DH_DSS_WITH_DES_CBC_SHA, SSL_DH_DSS_WITH_3DES_EDE_CBC_SHA, SSL_DH_DSS_WITH_AES_128_CBC_SHA, TLS_DH_DSS_WITH_AES_128_CBC_SHA256, SSL_DH_DSS_WITH_AES_256_CBC_SHA, TLS_DH_DSS_WITH_AES_256_CBC_SHA256, TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA, TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256, TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, TLS_DH_DSS_WITH_AES_128_GCM_SHA256, TLS_DH_DSS_WITH_AES_256_GCM_SHA384, TLS_DH_DSS_WITH_CAMELLIA_128_GCM_SHA256, TLS_DH_DSS_WITH_CAMELLIA_256_GCM_SHA384});
        CS_DH_DSS_EXPORT = a(new CipherSuite[]{SSL_DH_DSS_EXPORT_WITH_DES40_CBC_SHA});
        CS_DH_ANON = a(new CipherSuite[]{SSL_DH_anon_WITH_RC4_128_MD5, SSL_DH_anon_WITH_DES_CBC_SHA, SSL_DH_anon_WITH_3DES_EDE_CBC_SHA, SSL_DH_anon_WITH_AES_128_CBC_SHA, TLS_DH_anon_WITH_AES_128_CBC_SHA256, SSL_DH_anon_WITH_AES_256_CBC_SHA, TLS_DH_anon_WITH_AES_256_CBC_SHA256, TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA, TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, TLS_DH_anon_WITH_AES_128_GCM_SHA256, TLS_DH_anon_WITH_AES_256_GCM_SHA384, TLS_DH_anon_WITH_CAMELLIA_128_GCM_SHA256, TLS_DH_anon_WITH_CAMELLIA_256_GCM_SHA384});
        CS_DH_ANON_EXPORT = a(new CipherSuite[]{SSL_DH_anon_EXPORT_WITH_RC4_40_MD5, SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA});
        CipherSuite cipherSuite24 = TLS_PSK_WITH_RC4_128_SHA;
        CipherSuite cipherSuite25 = TLS_PSK_WITH_3DES_EDE_CBC_SHA;
        CipherSuite cipherSuite26 = TLS_PSK_WITH_AES_128_CBC_SHA;
        CipherSuite cipherSuite27 = TLS_PSK_WITH_AES_128_CBC_SHA256;
        CipherSuite cipherSuite28 = TLS_PSK_WITH_AES_128_GCM_SHA256;
        CipherSuite cipherSuite29 = TLS_PSK_WITH_AES_256_CBC_SHA;
        CipherSuite cipherSuite30 = TLS_PSK_WITH_AES_256_CBC_SHA384;
        CipherSuite cipherSuite31 = TLS_PSK_WITH_AES_256_GCM_SHA384;
        CipherSuite cipherSuite32 = TLS_PSK_WITH_CAMELLIA_128_CBC_SHA256;
        CipherSuite cipherSuite33 = TLS_PSK_WITH_CAMELLIA_128_GCM_SHA256;
        CipherSuite cipherSuite34 = TLS_PSK_WITH_CAMELLIA_256_CBC_SHA384;
        CipherSuite cipherSuite35 = TLS_PSK_WITH_CAMELLIA_256_GCM_SHA384;
        CS_PSK = a(new CipherSuite[]{cipherSuite24, cipherSuite25, cipherSuite26, cipherSuite27, cipherSuite28, cipherSuite29, cipherSuite30, cipherSuite31, cipherSuite32, cipherSuite33, cipherSuite34, cipherSuite35});
        CipherSuite cipherSuite36 = TLS_DHE_PSK_WITH_RC4_128_SHA;
        CipherSuite cipherSuite37 = TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA;
        CipherSuite cipherSuite38 = TLS_DHE_PSK_WITH_AES_128_CBC_SHA;
        CipherSuite cipherSuite39 = TLS_DHE_PSK_WITH_AES_128_CBC_SHA256;
        CipherSuite cipherSuite40 = TLS_DHE_PSK_WITH_AES_128_GCM_SHA256;
        CipherSuite cipherSuite41 = TLS_DHE_PSK_WITH_AES_256_CBC_SHA;
        CipherSuite cipherSuite42 = TLS_DHE_PSK_WITH_AES_256_CBC_SHA384;
        CipherSuite cipherSuite43 = TLS_DHE_PSK_WITH_AES_256_GCM_SHA384;
        CipherSuite cipherSuite44 = TLS_DHE_PSK_WITH_CAMELLIA_128_CBC_SHA256;
        CipherSuite cipherSuite45 = TLS_DHE_PSK_WITH_CAMELLIA_128_GCM_SHA256;
        CipherSuite cipherSuite46 = TLS_DHE_PSK_WITH_CAMELLIA_256_CBC_SHA384;
        CipherSuite cipherSuite47 = TLS_DHE_PSK_WITH_CAMELLIA_256_GCM_SHA384;
        CS_DHE_PSK = a(new CipherSuite[]{cipherSuite36, cipherSuite37, cipherSuite38, cipherSuite39, cipherSuite40, cipherSuite41, cipherSuite42, cipherSuite43, cipherSuite44, cipherSuite45, cipherSuite46, cipherSuite47});
        CipherSuite cipherSuite48 = TLS_RSA_PSK_WITH_RC4_128_SHA;
        CipherSuite cipherSuite49 = TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA;
        CipherSuite cipherSuite50 = TLS_RSA_PSK_WITH_AES_128_CBC_SHA;
        CipherSuite cipherSuite51 = TLS_RSA_PSK_WITH_AES_128_CBC_SHA256;
        CipherSuite cipherSuite52 = TLS_RSA_PSK_WITH_AES_128_GCM_SHA256;
        CipherSuite cipherSuite53 = TLS_RSA_PSK_WITH_AES_256_CBC_SHA;
        CipherSuite cipherSuite54 = TLS_RSA_PSK_WITH_AES_256_CBC_SHA384;
        CipherSuite cipherSuite55 = TLS_RSA_PSK_WITH_AES_256_GCM_SHA384;
        CipherSuite cipherSuite56 = TLS_RSA_PSK_WITH_CAMELLIA_128_CBC_SHA256;
        CipherSuite cipherSuite57 = TLS_RSA_PSK_WITH_CAMELLIA_128_GCM_SHA256;
        CipherSuite cipherSuite58 = TLS_RSA_PSK_WITH_CAMELLIA_256_CBC_SHA384;
        CipherSuite cipherSuite59 = TLS_RSA_PSK_WITH_CAMELLIA_256_GCM_SHA384;
        CS_RSA_PSK = a(new CipherSuite[]{cipherSuite48, cipherSuite49, cipherSuite50, cipherSuite51, cipherSuite52, cipherSuite53, cipherSuite54, cipherSuite55, cipherSuite56, cipherSuite57, cipherSuite58, cipherSuite59});
        CipherSuite cipherSuite60 = TLS_ECDHE_PSK_WITH_RC4_128_SHA;
        CipherSuite cipherSuite61 = TLS_ECDHE_PSK_WITH_3DES_EDE_CBC_SHA;
        CipherSuite cipherSuite62 = TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA;
        CipherSuite cipherSuite63 = TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA256;
        CipherSuite cipherSuite64 = TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA;
        CipherSuite cipherSuite65 = TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA384;
        CipherSuite cipherSuite66 = TLS_ECDHE_PSK_WITH_CAMELLIA_128_CBC_SHA256;
        CipherSuite cipherSuite67 = TLS_ECDHE_PSK_WITH_CAMELLIA_256_CBC_SHA384;
        CS_ECDHE_PSK = a(new CipherSuite[]{cipherSuite60, cipherSuite61, cipherSuite62, cipherSuite63, cipherSuite64, cipherSuite65, cipherSuite66, cipherSuite67});
        CS_ALL_PSK = a(new CipherSuite[]{cipherSuite24, cipherSuite25, cipherSuite26, cipherSuite27, cipherSuite28, cipherSuite29, cipherSuite30, cipherSuite31, cipherSuite32, cipherSuite33, cipherSuite34, cipherSuite35, cipherSuite36, cipherSuite37, cipherSuite38, cipherSuite39, cipherSuite40, cipherSuite41, cipherSuite42, cipherSuite43, cipherSuite44, cipherSuite45, cipherSuite46, cipherSuite47, cipherSuite48, cipherSuite49, cipherSuite50, cipherSuite51, cipherSuite52, cipherSuite53, cipherSuite54, cipherSuite55, cipherSuite56, cipherSuite57, cipherSuite58, cipherSuite59, cipherSuite60, cipherSuite61, cipherSuite62, cipherSuite63, cipherSuite64, cipherSuite65, cipherSuite66, cipherSuite67});
        CipherSuite cipherSuite68 = TLS_ECDH_ECDSA_WITH_CAMELLIA_128_GCM_SHA256;
        CipherSuite cipherSuite69 = TLS_ECDH_ECDSA_WITH_CAMELLIA_256_GCM_SHA384;
        CS_ECDH_ECDSA = a(new CipherSuite[]{TLS_ECDH_ECDSA_WITH_RC4_128_SHA, TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA, TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA, TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256, TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA, TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384, TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256, TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384, TLS_ECDH_ECDSA_WITH_CAMELLIA_128_CBC_SHA256, TLS_ECDH_ECDSA_WITH_CAMELLIA_256_CBC_SHA384, cipherSuite68, cipherSuite69});
        CipherSuite cipherSuite70 = TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
        CipherSuite cipherSuite71 = TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384;
        CipherSuite cipherSuite72 = TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256;
        CipherSuite cipherSuite73 = TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384;
        CS_ECDHE_ECDSA = a(new CipherSuite[]{TLS_ECDHE_ECDSA_WITH_RC4_128_SHA, TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA, TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256, TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384, cipherSuite70, cipherSuite71, TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256, TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384, cipherSuite72, cipherSuite73});
        CipherSuite cipherSuite74 = TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256;
        CipherSuite cipherSuite75 = TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384;
        CipherSuite cipherSuite76 = TLS_ECDH_RSA_WITH_CAMELLIA_128_GCM_SHA256;
        CipherSuite cipherSuite77 = TLS_ECDH_RSA_WITH_CAMELLIA_256_GCM_SHA384;
        CS_ECDH_RSA = a(new CipherSuite[]{TLS_ECDH_RSA_WITH_RC4_128_SHA, TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA, TLS_ECDH_RSA_WITH_AES_128_CBC_SHA, TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256, TLS_ECDH_RSA_WITH_AES_256_CBC_SHA, TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384, cipherSuite74, cipherSuite75, TLS_ECDH_RSA_WITH_CAMELLIA_128_CBC_SHA256, TLS_ECDH_RSA_WITH_CAMELLIA_256_CBC_SHA384, cipherSuite76, cipherSuite77});
        CipherSuite cipherSuite78 = TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
        CipherSuite cipherSuite79 = TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384;
        CipherSuite cipherSuite80 = TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256;
        CipherSuite cipherSuite81 = TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384;
        CS_ECDHE_RSA = a(new CipherSuite[]{TLS_ECDHE_RSA_WITH_RC4_128_SHA, TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA, TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, cipherSuite78, cipherSuite79, TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256, TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384, cipherSuite80, cipherSuite81});
        CS_ECDH_ANON = a(new CipherSuite[]{TLS_ECDH_anon_WITH_RC4_128_SHA, TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA, TLS_ECDH_anon_WITH_AES_128_CBC_SHA, TLS_ECDH_anon_WITH_AES_256_CBC_SHA});
        CS_AEAD_GCM = a(new CipherSuite[]{cipherSuite70, cipherSuite71, cipherSuite72, cipherSuite73, TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256, TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384, cipherSuite68, cipherSuite69, cipherSuite78, cipherSuite79, cipherSuite80, cipherSuite81, cipherSuite74, cipherSuite75, cipherSuite76, cipherSuite77, TLS_RSA_WITH_AES_128_GCM_SHA256, TLS_RSA_WITH_AES_256_GCM_SHA384, TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256, TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384, TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256, TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384, TLS_DH_RSA_WITH_AES_128_GCM_SHA256, TLS_DH_RSA_WITH_AES_256_GCM_SHA384, TLS_DH_RSA_WITH_CAMELLIA_128_GCM_SHA256, TLS_DH_RSA_WITH_CAMELLIA_256_GCM_SHA384, TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256, TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384, TLS_DH_DSS_WITH_AES_128_GCM_SHA256, TLS_DH_DSS_WITH_AES_256_GCM_SHA384, TLS_DH_DSS_WITH_CAMELLIA_128_GCM_SHA256, TLS_DH_DSS_WITH_CAMELLIA_256_GCM_SHA384, TLS_PSK_WITH_AES_128_GCM_SHA256, TLS_PSK_WITH_AES_256_GCM_SHA384, TLS_PSK_WITH_CAMELLIA_128_GCM_SHA256, TLS_PSK_WITH_CAMELLIA_256_GCM_SHA384, TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, TLS_DHE_PSK_WITH_AES_256_GCM_SHA384, TLS_DHE_PSK_WITH_CAMELLIA_128_GCM_SHA256, TLS_DHE_PSK_WITH_CAMELLIA_256_GCM_SHA384, TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, TLS_RSA_PSK_WITH_CAMELLIA_128_GCM_SHA256, TLS_RSA_PSK_WITH_CAMELLIA_256_GCM_SHA384, TLS_DH_anon_WITH_AES_128_GCM_SHA256, TLS_DH_anon_WITH_AES_256_GCM_SHA384, TLS_DH_anon_WITH_CAMELLIA_128_GCM_SHA256, TLS_DH_anon_WITH_CAMELLIA_256_GCM_SHA384});
        SSL_RSA_WITH_RC4_MD5 = SSL_RSA_WITH_RC4_128_MD5;
        SSL_RSA_WITH_RC4_SHA = SSL_RSA_WITH_RC4_128_SHA;
        SSL_DH_anon_WITH_RC4_MD5 = SSL_DH_anon_WITH_RC4_128_MD5;
    }

    private CipherSuite(String str, String str2, String str3, String str4, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.M = str;
        this.P = str3;
        this.N = i6;
        this.R = i2;
        this.S = i3;
        this.T = i4;
        this.U = i5;
        this.O = str2;
        this.V = c(str2);
        this.Q = str4;
        if (SecurityProvider.ALG_DIGEST_SHA384.equals(str4)) {
            this.W = SecurityProvider.ALG_DIGEST_SHA384;
        } else if (SecurityProvider.ALG_DIGEST_SHA512.equals(this.Q)) {
            this.W = SecurityProvider.ALG_DIGEST_SHA512;
        } else {
            this.W = SecurityProvider.ALG_DIGEST_SHA256;
        }
        this.X = i7;
        this.Y = i8;
        if (getKeySizeLimit() != -1) {
            this.Y = SSLContext.VERSION_TLS10;
        }
        if (i2 != 22016 && i2 != 255) {
            a(this);
        }
        if (i9 >= 4) {
            c.insertSorted(this);
        }
        if (i9 >= 5) {
            b.insertSorted(this);
        }
    }

    private CipherSuite(String str, String str2, String str3, String str4, int i2, int i3, int i4, int i5, int i6, int i7) {
        this(str, str2, str3, str4, i2, i3, i4, i5, i6, 2, SSLContext.VERSION_TLS12, i7);
    }

    public CipherSuite(String str, String str2, String str3, String str4, int i2, int i3, int i4) {
        this(str, str2, str3, str4, i2, i3, i3, i4, -1, 4);
    }

    private CipherSuite(String str, String str2, int i2, int i3, int i4, int i5, int i6, int i7) {
        this(str, b(str), str2, a(str), i2, i3, i4, i5, i6, i7);
    }

    private CipherSuite(String str, String str2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this(str, b(str), str2, a(str), i2, i3, i4, i5, i6, i7, i8, i9);
    }

    private static String a(String str) {
        return str.substring(str.lastIndexOf(95) + 1);
    }

    private static String b(String str) {
        return str.substring(str.indexOf(95) + 1, str.indexOf("WITH") - 1);
    }

    private static boolean c(String str) {
        String lowerCase = str.toLowerCase();
        return lowerCase.indexOf("null") == -1 && lowerCase.indexOf("anon") == -1 && (lowerCase.indexOf("psk") == -1 || lowerCase.indexOf("rsa") != -1);
    }

    private CipherSuite(String str, int i2) {
        this.M = str;
        this.O = "NULL";
        this.P = "NULL";
        this.Q = "NULL";
        this.R = i2;
        a(this);
    }

    public String getName() {
        return this.M;
    }

    public int getID() {
        return this.R;
    }

    int a() {
        return this.U == 0 ? 1 : 2;
    }

    public String getCipherAlgorithm() {
        return this.P;
    }

    public String getMacAlgorithm() {
        return this.Q;
    }

    public String getPrfAlgorithm() {
        return this.W;
    }

    public String getKeyExchangeAlgorithm() {
        return this.O;
    }

    boolean d() {
        return this.V;
    }

    public boolean isExportable() {
        return this.P.equals("NULL") || this.N != -1;
    }

    public int getKeySizeLimit() {
        return this.N;
    }

    public int getHashSize() {
        if (!c()) {
            if (this.Q.equals("SHA")) {
                return 20;
            }
            if (this.Q.startsWith(SecurityProvider.ALG_DIGEST_SHA256)) {
                return 32;
            }
            if (this.Q.startsWith(SecurityProvider.ALG_DIGEST_SHA384)) {
                return 48;
            }
            if (this.Q.equals(SecurityProvider.ALG_DIGEST_MD5)) {
                return 16;
            }
            if (this.Q.equals(SecurityProvider.ALG_DIGEST_SHA512)) {
                return 64;
            }
            if (this.Q.equals(SecurityProvider.ALG_DIGEST_SHA224)) {
                return 28;
            }
        }
        return 0;
    }

    public int getKeyLength() {
        return this.S;
    }

    public int getExpandedKeyLength() {
        return this.T;
    }

    public int getIVSize() {
        return this.U;
    }

    public boolean isAvailable() {
        return a(0, SSLContext.VERSION_TLS12, null);
    }

    boolean c() {
        return this.P.indexOf("/GCM/") != -1;
    }

    boolean a(int i2, int i3, SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        boolean z2;
        if (equals(TLS_FALLBACK_SCSV)) {
            return true;
        }
        if (!a(i2, i3) || this.Q.equals("NULL")) {
            return false;
        }
        if (!this.P.equals("NULL") && !SecurityProvider.a(this.P, SecurityProvider.c, this)) {
            return false;
        }
        String str = this.O;
        if (!str.startsWith("RSA") && !str.startsWith("DHE_RSA") && !str.startsWith(KeyTypeNames.DH_RSA) && !str.startsWith("ECDHE_RSA") && !str.startsWith("ECDH_RSA")) {
            z2 = false;
        } else {
            if (!SecurityProvider.a("RSA", SecurityProvider.b, this)) {
                return false;
            }
            if (signatureAndHashAlgorithmList != null && ((str.startsWith("DHE_") || str.startsWith("ECDHE_")) && signatureAndHashAlgorithmList.a(1) == null)) {
                return false;
            }
            z2 = true;
        }
        if (str.startsWith("DHE_DSS") || str.startsWith(KeyTypeNames.DH_DSS)) {
            if (!SecurityProvider.a("DSA", SecurityProvider.b, this)) {
                return false;
            }
            if (signatureAndHashAlgorithmList != null && str.startsWith("DHE_") && signatureAndHashAlgorithmList.a(2) == null) {
                return false;
            }
            z2 = true;
        }
        if (str.startsWith("DH")) {
            if (!SecurityProvider.a("DH", SecurityProvider.b, this)) {
                return false;
            }
            z2 = true;
        }
        if (str.startsWith("ECDHE_ECDSA") || str.startsWith("ECDH_ECDSA")) {
            if (!SecurityProvider.a("ECDSA", SecurityProvider.b, this)) {
                return false;
            }
            if (signatureAndHashAlgorithmList != null && str.startsWith("ECDHE_") && signatureAndHashAlgorithmList.a(3) == null) {
                return false;
            }
            z2 = true;
        }
        if (str.startsWith(SecurityProvider.ALG_KEYEX_ECDH)) {
            if (!SecurityProvider.a(SecurityProvider.ALG_KEYEX_ECDH, SecurityProvider.b, this)) {
                return false;
            }
            z2 = true;
        }
        if (str.endsWith("PSK")) {
            return true;
        }
        return z2;
    }

    boolean a(int i2, int i3) {
        return i2 <= this.Y && i3 >= this.X;
    }

    boolean a(SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        if (signatureAndHashAlgorithmList == null) {
            return true;
        }
        String str = this.O;
        if (str.startsWith("DHE_RSA") || str.startsWith("ECDHE_RSA")) {
            if (signatureAndHashAlgorithmList.a(1) != null) {
                return true;
            }
        } else if (str.startsWith("DHE_DSS")) {
            if (signatureAndHashAlgorithmList.a(2) != null) {
                return true;
            }
        } else if (!str.startsWith("ECDHE_ECDSA") || signatureAndHashAlgorithmList.a(3) != null) {
            return true;
        }
        return false;
    }

    public int getAllowedMinVersion() {
        return this.X;
    }

    public int getAllowedMaxVersion() {
        return this.Y;
    }

    CipherSuite b() {
        if (r == null) {
            r = new Hashtable();
            int length = Z.length;
            for (int i2 = 0; i2 < length; i2++) {
                r.put(Z[i2], CS_SSL_V2[i2]);
            }
        }
        return (CipherSuite) r.get(this);
    }

    void a(ag agVar) throws IOException {
        agVar.a(this.R);
    }

    void b(ag agVar) throws IOException {
        agVar.e(this.R);
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof CipherSuite) && this.R == ((CipherSuite) obj).R;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x014f A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0151 A[ORIG_RETURN, RETURN] */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int compareTo(java.lang.Object r12) {
        /*
            Method dump skipped, instruction units count: 339
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.CipherSuite.compareTo(java.lang.Object):int");
    }

    public String toString() {
        return this.M;
    }

    public int hashCode() {
        return this.R;
    }

    static CipherSuite a(int i2) {
        CipherSuite cipherSuite = (CipherSuite) m.get(new Integer(i2));
        if (cipherSuite != null) {
            return cipherSuite;
        }
        StringBuffer stringBuffer = new StringBuffer("Unknown Ciphersuite with ID 0x");
        stringBuffer.append(Utils.toString(i2));
        return new CipherSuite(stringBuffer.toString(), i2);
    }

    public static CipherSuite getByName(String str) {
        return (CipherSuite) n.get(str.toLowerCase());
    }

    static void a(CipherSuite cipherSuite) {
        Integer num = new Integer(cipherSuite.getID());
        HashMap map = m;
        if (map.get(num) != null) {
            return;
        }
        map.put(num, cipherSuite);
        String name = cipherSuite.getName();
        if (name != null) {
            String lowerCase = name.toLowerCase();
            HashMap map2 = n;
            map2.put(lowerCase, cipherSuite);
            if (lowerCase.startsWith("tls")) {
                StringBuffer stringBuffer = new StringBuffer("ssl");
                stringBuffer.append(lowerCase.substring(3));
                map2.put(stringBuffer.toString(), cipherSuite);
            } else if (lowerCase.startsWith("ssl_")) {
                StringBuffer stringBuffer2 = new StringBuffer("tls");
                stringBuffer2.append(lowerCase.substring(3));
                map2.put(stringBuffer2.toString(), cipherSuite);
            }
        }
        a.add(cipherSuite);
    }

    private static CipherSuite[] a(CipherSuite[] cipherSuiteArr) {
        CipherSuiteList cipherSuiteList = new CipherSuiteList();
        for (CipherSuite cipherSuite : cipherSuiteArr) {
            cipherSuiteList.insertSorted(cipherSuite);
        }
        return cipherSuiteList.toArray();
    }

    public static CipherSuite[] getDefault() {
        return b.toArray();
    }
}

package iaik.security.random;

import iaik.security.md.Md5;
import iaik.security.md.RawHash;
import iaik.security.md.RipeMd128;
import iaik.security.md.RipeMd160;
import iaik.security.md.SHA;
import iaik.security.md.SHA256;
import iaik.security.md.SHA384;
import iaik.security.md.SHA512;
import iaik.security.md.Whirlpool;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandomSpi;

/* JADX INFO: loaded from: classes.dex */
class SecRandomSpi extends SecureRandomSpi {
    private static final long serialVersionUID = 1026145964549769487L;
    private transient v a;
    private boolean b;

    public static final class AES128SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 3969874762665805722L;

        public AES128SP80090RandomSpi() {
            super(new b(), true);
        }
    }

    public static final class AES192SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 7766844317359115382L;

        public AES192SP80090RandomSpi() {
            super(new c(), true);
        }
    }

    public static final class AES256SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -8657195256293109651L;

        public AES256SP80090RandomSpi() {
            super(new d(), true);
        }
    }

    public static final class AnsiRandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 6166630275777915286L;

        public AnsiRandomSpi() {
            super(new e());
        }
    }

    public static final class HMacSHA1SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 8757279090930967629L;

        public HMacSHA1SP80090RandomSpi() throws NoSuchAlgorithmException {
            super(new i(), true);
        }
    }

    public static final class HMacSHA224SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 845862963760553442L;

        public HMacSHA224SP80090RandomSpi() throws NoSuchAlgorithmException {
            super(new j(), true);
        }
    }

    public static final class HMacSHA256SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -4027982932943055125L;

        public HMacSHA256SP80090RandomSpi() throws NoSuchAlgorithmException {
            super(new k(), true);
        }
    }

    public static final class HMacSHA384SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -4887057756858043006L;

        public HMacSHA384SP80090RandomSpi() throws NoSuchAlgorithmException {
            super(new l(), true);
        }
    }

    public static final class HMacSHA512SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 5764442277342864731L;

        public HMacSHA512SP80090RandomSpi() throws NoSuchAlgorithmException {
            super(new m(), true);
        }
    }

    public static final class MD5RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -7368828020525468171L;

        public MD5RandomSpi() {
            super(new o(new Md5()));
        }
    }

    public static final class RipeMd128RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -6254788928302103077L;

        public RipeMd128RandomSpi() {
            super(new o(new RipeMd128()));
        }
    }

    public static final class RipeMd160FIPS186RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 3844670659664267430L;

        public RipeMd160FIPS186RandomSpi() {
            super(new g(new RawHash(new RipeMd160())));
        }
    }

    public static final class RipeMd160RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 2453569129397920410L;

        public RipeMd160RandomSpi() {
            super(new o(new RipeMd160()));
        }
    }

    public static final class SHA1FIPS186RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 9058249698430317509L;

        public SHA1FIPS186RandomSpi() {
            super(new g(new RawHash(new SHA())));
        }
    }

    public static final class SHA1RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 5464978605194477411L;

        public SHA1RandomSpi() {
            super(new o(new SHA()));
        }
    }

    public static final class SHA1SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 8562823441446625238L;

        public SHA1SP80090RandomSpi() {
            super(new p(), true);
        }
    }

    public static final class SHA224SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -8960500252887513637L;

        public SHA224SP80090RandomSpi() {
            super(new q(), true);
        }
    }

    public static final class SHA256FIPS186RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -3855642307607385108L;

        public SHA256FIPS186RandomSpi() {
            super(new g(new RawHash(new SHA256())));
        }
    }

    public static final class SHA256RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 8650764158647030503L;

        public SHA256RandomSpi() {
            super(new o(new SHA256()));
        }
    }

    public static final class SHA256SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -697899680374865036L;

        public SHA256SP80090RandomSpi() {
            super(new r(), true);
        }
    }

    public static final class SHA384FIPS186RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 2969108343072208705L;

        public SHA384FIPS186RandomSpi() {
            super(new g(new RawHash(new SHA384())));
        }
    }

    public static final class SHA384RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -8193566034848828173L;

        public SHA384RandomSpi() {
            super(new o(new SHA384()));
        }
    }

    public static final class SHA384SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 4109588963234523926L;

        public SHA384SP80090RandomSpi() {
            super(new s(), true);
        }
    }

    public static final class SHA512FIPS186RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 388378569762790250L;

        public SHA512FIPS186RandomSpi() {
            super(new g(new RawHash(new SHA512())));
        }
    }

    public static final class SHA512RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 2166078516552030657L;

        public SHA512RandomSpi() {
            super(new o(new SHA512()));
        }
    }

    public static final class SHA512SP80090RandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = 1424316995942736055L;

        public SHA512SP80090RandomSpi() {
            super(new t(), true);
        }
    }

    public static final class WhirlpoolRandomSpi extends SecRandomSpi {
        private static final long serialVersionUID = -3227409675792327092L;

        public WhirlpoolRandomSpi() {
            super(new o(new Whirlpool()));
        }
    }

    SecRandomSpi(v vVar) {
        this(vVar, false);
    }

    SecRandomSpi(v vVar, boolean z) {
        this.a = vVar;
        this.b = z;
    }

    v a() {
        return this.a;
    }

    @Override // java.security.SecureRandomSpi
    protected byte[] engineGenerateSeed(int i) {
        return VarLengthSeedGenerator.getDefault(i << 3).getSeed();
    }

    @Override // java.security.SecureRandomSpi
    protected void engineNextBytes(byte[] bArr) {
        if (bArr != null) {
            if (!this.b) {
                this.a.engineSetSeed(engineGenerateSeed(32));
                this.b = true;
            }
            this.a.engineNextBytes(bArr);
        }
    }

    @Override // java.security.SecureRandomSpi
    protected void engineSetSeed(byte[] bArr) {
        if (bArr != null) {
            this.b = true;
            this.a.engineSetSeed(bArr);
        }
    }
}

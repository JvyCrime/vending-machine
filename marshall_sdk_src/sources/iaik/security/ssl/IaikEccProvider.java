package iaik.security.ssl;

import iaik.security.ecc.ECCException;
import iaik.security.ecc.ecdsa.ECDSAKeyPairGeneratorImpl;
import iaik.security.ecc.ecdsa.ECDSAParameter;
import iaik.security.ecc.ecdsa.ECPrivateKey;
import iaik.security.ecc.ecdsa.ECPublicKey;
import iaik.security.ecc.interfaces.ECDSAParams;
import iaik.security.ecc.math.ecgroup.AffineCoordinate;
import iaik.security.ecc.math.ecgroup.ECGroupFactory;
import iaik.security.ecc.math.ecgroup.ECPoint;
import iaik.security.ecc.math.ecgroup.EllipticCurve;
import iaik.security.ecc.parameter.ECCParameterFactory;
import iaik.security.ecc.provider.ECCProvider;
import iaik.security.ecc.spec.ECCParameterSpec;
import iaik.security.ecc.util.PointCoDec;
import iaik.security.ecc.util.PointCoDecImpl;
import iaik.security.ecc.util.PointFormatter;
import iaik.security.ssl.SupportedEllipticCurves;
import iaik.security.ssl.SupportedPointFormats;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import javax.crypto.KeyAgreement;

/* JADX INFO: loaded from: classes.dex */
public class IaikEccProvider extends IaikProvider {
    public static final String EC_DEFAULT_BINARY = "sect283k1";
    public static final String EC_DEFAULT_PRIME = "secp256r1";
    private static final HashMap g = new HashMap(20);
    private static final Vector h = new Vector(20);
    private static int i = -1;
    private double j;

    private static final class a {
        private ECDSAParams a;

        public a(ECDSAParams eCDSAParams) {
            this.a = eCDSAParams;
        }

        public int hashCode() {
            return this.a.getG().hashCode() + this.a.getR().hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj || !(obj instanceof a)) {
                return true;
            }
            ECDSAParams eCDSAParams = this.a;
            ECDSAParams eCDSAParams2 = ((a) obj).a;
            if (eCDSAParams.getK() != null && eCDSAParams2.getK() != null && !eCDSAParams.getK().equals(eCDSAParams2.getK())) {
                z = false;
            }
            return (z && eCDSAParams.getG().equals(eCDSAParams2.getG())) ? eCDSAParams.getR().equals(eCDSAParams2.getR()) : z;
        }
    }

    static {
        ECCParameterSpec parameterByName;
        ECCParameterFactory eCCParameterFactory = ECCParameterFactory.getInstance();
        Enumeration names = eCCParameterFactory.getNames();
        while (names.hasMoreElements()) {
            String string = (String) names.nextElement();
            if (string.startsWith("brainpoolp")) {
                StringBuffer stringBuffer = new StringBuffer("brainpoolP");
                stringBuffer.append(string.substring(10));
                string = stringBuffer.toString();
            }
            SupportedEllipticCurves.NamedCurve registeredCurveByName = SupportedEllipticCurves.getRegisteredCurveByName(string);
            if (registeredCurveByName != null && (parameterByName = eCCParameterFactory.getParameterByName(string)) != null) {
                try {
                    g.put(new a(new ECDSAParameter(parameterByName)), registeredCurveByName);
                    h.addElement(registeredCurveByName);
                } catch (ECCException unused) {
                }
            }
        }
    }

    private static final void a(boolean z, SupportedPointFormats supportedPointFormats) throws Exception {
        if (supportedPointFormats == null && z) {
            throw new Exception("Peer used compressed point format although not requested!");
        }
        if (supportedPointFormats != null) {
            if (z) {
                if (supportedPointFormats.getPointFormat(2) == null) {
                    throw new Exception("Peer used compressed char2 point format although not requested!");
                }
            } else if (supportedPointFormats.getPointFormat(1) == null) {
                throw new Exception("Peer used compressed prime point format although not requested!");
            }
        }
    }

    private static final ECPublicKey a(PublicKey publicKey) throws Exception {
        if (publicKey instanceof ECPublicKey) {
            return (ECPublicKey) publicKey;
        }
        try {
            return new ECPublicKey(publicKey.getEncoded());
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error parsing EC public key: ");
            stringBuffer.append(e.toString());
            throw new Exception(stringBuffer.toString());
        }
    }

    private static final ECPrivateKey a(PrivateKey privateKey) throws Exception {
        if (privateKey instanceof ECPrivateKey) {
            return (ECPrivateKey) privateKey;
        }
        try {
            return new ECPrivateKey(privateKey.getEncoded());
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error parsing EC private key: ");
            stringBuffer.append(e.toString());
            throw new Exception(stringBuffer.toString());
        }
    }

    public IaikEccProvider() {
        if (Security.getProvider("IAIK_ECC") == null) {
            ECCProvider.addAsProvider();
        }
        Provider provider = Security.getProvider("IAIK_ECC");
        if (provider == null) {
            System.err.println("Could not add ECC provider! IAIK-ECC crypto provider not installed!");
            throw new RuntimeException("Could not add ECC provider! IAIK-ECC crypto provider not installed!");
        }
        this.j = provider.getVersion();
    }

    @Override // iaik.security.ssl.IaikProvider, iaik.security.ssl.SecurityProvider
    protected Signature getSignature(String str, int i2, Key key, SecureRandom secureRandom) throws Exception {
        if (str.indexOf("ECDSA") != -1) {
            Signature signature = Signature.getInstance(str, "IAIK_ECC");
            if (i2 != 1) {
                if (i2 != 2) {
                    return signature;
                }
                signature.initVerify((PublicKey) key);
                return signature;
            }
            if (secureRandom == null) {
                signature.initSign((PrivateKey) key);
                return signature;
            }
            signature.initSign((PrivateKey) key, secureRandom);
            return signature;
        }
        return super.getSignature(str, i2, key, secureRandom);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public KeyAgreement getKeyAgreement(String str, int i2, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws Exception {
        if (str.equals(SecurityProvider.ALG_KEYEX_ECDH)) {
            KeyAgreement keyAgreement = KeyAgreement.getInstance(str, "IAIK_ECC");
            if (i2 == 0) {
                return keyAgreement;
            }
            if (algorithmParameterSpec == null) {
                keyAgreement.init(key, secureRandom);
                return keyAgreement;
            }
            keyAgreement.init(key, algorithmParameterSpec, secureRandom);
            return keyAgreement;
        }
        return super.getKeyAgreement(str, i2, key, algorithmParameterSpec, secureRandom);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public int getKeyLength(PublicKey publicKey) {
        if (publicKey instanceof ECPublicKey) {
            return ((ECPublicKey) publicKey).getParameter().getR().bitLength();
        }
        return super.getKeyLength(publicKey);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public int getKeyLength(PrivateKey privateKey) {
        if (privateKey instanceof ECPrivateKey) {
            return ((ECPrivateKey) privateKey).getParameter().getR().bitLength();
        }
        return super.getKeyLength(privateKey);
    }

    @Override // iaik.security.ssl.SecurityProvider
    public byte[] encodeECPublicKey(PublicKey publicKey, SupportedPointFormats supportedPointFormats) throws Exception {
        int i2;
        int id;
        ECPoint w = a(publicKey).getW();
        if (supportedPointFormats != null) {
            SupportedPointFormats.ECPointFormat[] pointFormatList = supportedPointFormats.getPointFormatList();
            boolean z = w.getCurve().getField().getFieldId() == 2;
            for (int i3 = 0; i3 < pointFormatList.length && (id = pointFormatList[0].getID()) != 0; i3++) {
                if (id != 1) {
                    if (id == 2 && z) {
                        i2 = 2;
                        break;
                    }
                } else {
                    if (!z) {
                        i2 = 2;
                        break;
                    }
                }
            }
            i2 = 1;
        } else {
            i2 = 1;
        }
        AffineCoordinate affine = w.getCoordinates().toAffine();
        PointCoDec pointCodec = PointFormatter.getInstance().getPointCodec();
        try {
            return pointCodec.encodePoint(affine, i2);
        } catch (Exception unused) {
            if (i2 == 2) {
                return pointCodec.encodePoint(affine, 1);
            }
            return null;
        }
    }

    @Override // iaik.security.ssl.SecurityProvider
    public PublicKey decodeECPublicKey(byte[] bArr, SupportedEllipticCurves.NamedCurve namedCurve, SupportedPointFormats supportedPointFormats, SupportedEllipticCurves supportedEllipticCurves) throws Exception {
        ECCParameterSpec parameterByName = ECCParameterFactory.getInstance().getParameterByName(namedCurve.getName());
        EllipticCurve curve = ECGroupFactory.getInstance().getCurve(parameterByName.getA(), parameterByName.getB(), parameterByName.getR(), 0);
        boolean z = curve.getField().getFieldId() == 2;
        if (supportedEllipticCurves != null) {
            if (!(supportedEllipticCurves.getCurve(namedCurve.getID()) != null)) {
                if (z) {
                    if (supportedEllipticCurves.getCurve(65282) == null) {
                        StringBuffer stringBuffer = new StringBuffer("Server selected not supported curve: ");
                        stringBuffer.append(namedCurve);
                        throw new Exception(stringBuffer.toString());
                    }
                } else if (supportedEllipticCurves.getCurve(65281) == null) {
                    StringBuffer stringBuffer2 = new StringBuffer("Server selected not supported curve: ");
                    stringBuffer2.append(namedCurve);
                    throw new Exception(stringBuffer2.toString());
                }
            }
        }
        if (bArr[0] != 4) {
            a(z, supportedPointFormats);
        }
        PointCoDecImpl pointCodec = PointFormatter.getInstance().getPointCodec();
        if (pointCodec instanceof PointCoDecImpl) {
            pointCodec.setVerifyPoint(true);
        }
        return new ECPublicKey(new ECDSAParameter(parameterByName), curve.newPoint(pointCodec.decodePoint(bArr, curve)));
    }

    @Override // iaik.security.ssl.SecurityProvider
    public PublicKey decodeECPublicKey(byte[] bArr, PrivateKey privateKey, SupportedPointFormats supportedPointFormats) throws Exception {
        ECDSAParams parameter = a(privateKey).getParameter();
        EllipticCurve curve = parameter.getG().getCurve();
        if (bArr[0] != 4) {
            a(curve.getField().getFieldId() == 2, supportedPointFormats);
        }
        PointCoDecImpl pointCodec = PointFormatter.getInstance().getPointCodec();
        if (pointCodec instanceof PointCoDecImpl) {
            pointCodec.setVerifyPoint(true);
        }
        return new ECPublicKey(parameter, curve.newPoint(pointCodec.decodePoint(bArr, curve)));
    }

    @Override // iaik.security.ssl.SecurityProvider
    public KeyPair generateECKeyPair(PublicKey publicKey) throws Exception {
        ECPublicKey eCPublicKey;
        if (publicKey instanceof ECPublicKey) {
            eCPublicKey = (ECPublicKey) publicKey;
        } else {
            try {
                eCPublicKey = new ECPublicKey(publicKey.getEncoded());
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("Error parsing EC public key: ");
                stringBuffer.append(e.toString());
                throw new Exception(stringBuffer.toString());
            }
        }
        ECDSAKeyPairGeneratorImpl keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "IAIK_ECC");
        keyPairGenerator.initialize(eCPublicKey.getParameter(), getSecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    @Override // iaik.security.ssl.SecurityProvider
    public KeyPair generateECKeyPair(SupportedEllipticCurves supportedEllipticCurves, SupportedPointFormats supportedPointFormats) throws Exception {
        String name;
        SupportedPointFormats.ECPointFormat[] pointFormatList;
        ECCParameterSpec parameterByName = null;
        SupportedEllipticCurves.NamedCurve[] ellipticCurveList = supportedEllipticCurves != null ? supportedEllipticCurves.getEllipticCurveList() : null;
        if (ellipticCurveList == null) {
            ellipticCurveList = supportedPointFormats != null && (pointFormatList = supportedPointFormats.getPointFormatList()) != null && pointFormatList[0].getID() == SupportedPointFormats.PF_COMPRESSED_CHAR2.getID() ? new SupportedEllipticCurves.NamedCurve[]{SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_CHAR2} : new SupportedEllipticCurves.NamedCurve[]{SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_PRIME};
        }
        for (SupportedEllipticCurves.NamedCurve namedCurve : ellipticCurveList) {
            ECCParameterFactory eCCParameterFactory = ECCParameterFactory.getInstance();
            if (namedCurve.equals(SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_PRIME)) {
                name = EC_DEFAULT_PRIME;
            } else {
                name = namedCurve.equals(SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_CHAR2) ? EC_DEFAULT_BINARY : namedCurve.getName();
            }
            parameterByName = eCCParameterFactory.getParameterByName(name);
            if (parameterByName != null) {
                break;
            }
        }
        if (parameterByName == null) {
            throw new Exception("Requested curves not supported!");
        }
        ECDSAKeyPairGeneratorImpl keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "IAIK_ECC");
        keyPairGenerator.initialize(parameterByName);
        return keyPairGenerator.generateKeyPair();
    }

    @Override // iaik.security.ssl.SecurityProvider
    public SupportedEllipticCurves.NamedCurve getCurve(PublicKey publicKey) {
        try {
            return (SupportedEllipticCurves.NamedCurve) g.get(new a(a(publicKey).getParameter()));
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // iaik.security.ssl.SecurityProvider
    public String getCurveName(PublicKey publicKey) {
        SupportedEllipticCurves.NamedCurve curve = getCurve(publicKey);
        if (curve != null) {
            return curve.getName();
        }
        return null;
    }

    @Override // iaik.security.ssl.SecurityProvider
    public SupportedPointFormats.ECPointFormat getECPointFormat(PublicKey publicKey) {
        SupportedPointFormats.ECPointFormat eCPointFormat;
        try {
            ECPublicKey eCPublicKeyA = a(publicKey);
            if (((byte[]) eCPublicKeyA.toASN1Object().getComponentAt(1).getValue())[0] != 4) {
                if (eCPublicKeyA.getW().getCurve().getField().getFieldId() == 2) {
                    eCPointFormat = (SupportedPointFormats.ECPointFormat) SupportedPointFormats.PF_COMPRESSED_CHAR2.clone();
                } else {
                    eCPointFormat = (SupportedPointFormats.ECPointFormat) SupportedPointFormats.PF_COMPRESSED_PRIME.clone();
                }
            } else {
                eCPointFormat = (SupportedPointFormats.ECPointFormat) SupportedPointFormats.PF_UNCOMPRESSED.clone();
            }
            return eCPointFormat;
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // iaik.security.ssl.SecurityProvider
    public boolean isBinary(PublicKey publicKey) throws Exception {
        return a(publicKey).getW().getCurve().getField().getFieldId() == 2;
    }

    @Override // iaik.security.ssl.SecurityProvider
    public boolean checkIfOnSameCurve(PublicKey publicKey, PublicKey publicKey2) {
        boolean zEquals;
        String oid;
        boolean z = false;
        try {
            ECDSAParams parameter = a(publicKey).getParameter();
            ECDSAParams parameter2 = a(publicKey2).getParameter();
            String oid2 = parameter.getOID();
            if (oid2 == null || (oid = parameter2.getOID()) == null) {
                z = true;
                zEquals = false;
            } else {
                zEquals = oid2.equals(oid);
            }
            if (!z) {
                return zEquals;
            }
            try {
                HashMap map = g;
                return ((SupportedEllipticCurves.NamedCurve) map.get(new a(parameter))).equals((SupportedEllipticCurves.NamedCurve) map.get(new a(parameter2)));
            } catch (Exception unused) {
                z = zEquals;
                return z;
            }
        } catch (Exception unused2) {
        }
    }

    @Override // iaik.security.ssl.SecurityProvider
    public boolean isPointFormatSupported(SupportedPointFormats.ECPointFormat eCPointFormat) {
        if (eCPointFormat.equals(SupportedPointFormats.PF_UNCOMPRESSED)) {
            return true;
        }
        if (eCPointFormat.equals(SupportedPointFormats.PF_COMPRESSED_PRIME) || eCPointFormat.equals(SupportedPointFormats.PF_COMPRESSED_CHAR2)) {
            if (i == -1) {
                i = PointFormatter.getInstance().getPointCodec().getSupportedCompressions();
            }
            if ((i & 2) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // iaik.security.ssl.SecurityProvider
    public boolean isNamedCurveSupported(SupportedEllipticCurves.NamedCurve namedCurve) {
        return h.contains(namedCurve) || SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_PRIME.equals(namedCurve) || SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_CHAR2.equals(namedCurve);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002d  */
    @Override // iaik.security.ssl.SecurityProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean checkKeyEllipticCurve(java.security.PublicKey r3, iaik.security.ssl.SupportedEllipticCurves r4) {
        /*
            r2 = this;
            r0 = 0
            if (r4 == 0) goto L2d
            iaik.security.ssl.SupportedEllipticCurves$NamedCurve r1 = r2.getCurve(r3)
            if (r1 == 0) goto L2e
            int r1 = r1.getID()
            iaik.security.ssl.SupportedEllipticCurves$NamedCurve r1 = r4.getCurve(r1)
            if (r1 != 0) goto L2d
            boolean r3 = r2.isBinary(r3)     // Catch: java.lang.Exception -> L2e
            if (r3 == 0) goto L23
            r3 = 65282(0xff02, float:9.148E-41)
            iaik.security.ssl.SupportedEllipticCurves$NamedCurve r3 = r4.getCurve(r3)     // Catch: java.lang.Exception -> L2e
            if (r3 != 0) goto L2d
            goto L2e
        L23:
            r3 = 65281(0xff01, float:9.1478E-41)
            iaik.security.ssl.SupportedEllipticCurves$NamedCurve r3 = r4.getCurve(r3)     // Catch: java.lang.Exception -> L2e
            if (r3 != 0) goto L2d
            goto L2e
        L2d:
            r0 = 1
        L2e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.IaikEccProvider.checkKeyEllipticCurve(java.security.PublicKey, iaik.security.ssl.SupportedEllipticCurves):boolean");
    }

    @Override // iaik.security.ssl.SecurityProvider
    public boolean checkKeyECPointFormat(PublicKey publicKey, SupportedPointFormats supportedPointFormats) {
        SupportedPointFormats.ECPointFormat eCPointFormat = getECPointFormat(publicKey);
        if (eCPointFormat == null) {
            return false;
        }
        if (supportedPointFormats == null) {
            if (!eCPointFormat.equals(SupportedPointFormats.PF_UNCOMPRESSED)) {
                return false;
            }
        } else if (!eCPointFormat.equals(SupportedPointFormats.PF_UNCOMPRESSED)) {
            try {
                a(eCPointFormat.equals(SupportedPointFormats.PF_COMPRESSED_CHAR2), supportedPointFormats);
            } catch (Exception unused) {
                return false;
            }
        }
        return true;
    }
}

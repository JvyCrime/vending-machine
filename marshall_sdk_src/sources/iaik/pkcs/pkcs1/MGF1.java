package iaik.pkcs.pkcs1;

import iaik.asn1.ObjectID;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.CryptoUtils;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class MGF1 extends MaskGenerationAlgorithm implements Cloneable {
    public static final ObjectID OID = AlgorithmID.mgf1.getAlgorithm();
    static Class a;
    private AlgorithmID b;
    private MessageDigest c;

    public MGF1() {
        super("MGF1");
        this.b = (AlgorithmID) AlgorithmID.sha1.clone();
        try {
            a();
        } catch (NoSuchAlgorithmException unused) {
        }
    }

    public MGF1(AlgorithmID algorithmID, MessageDigest messageDigest) {
        super("MGF1");
        Objects.requireNonNull(algorithmID, "Cannot create a MGF1 object with null hash algorithm.");
        this.b = algorithmID;
        this.c = messageDigest;
    }

    private void a() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = this.c;
        if (messageDigest != null) {
            messageDigest.reset();
            return;
        }
        AlgorithmID algorithmID = this.b;
        if (algorithmID != null) {
            try {
                this.c = algorithmID.getMessageDigestInstance("IAIK");
            } catch (NoSuchAlgorithmException unused) {
                this.c = this.b.getMessageDigestInstance();
            }
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.pkcs.pkcs1.MaskGenerationAlgorithmSpi
    public Object clone() {
        try {
            Object objClone = super.clone();
            try {
                this.b = (AlgorithmID) this.b.clone();
                MessageDigest messageDigest = this.c;
                if (messageDigest == null) {
                    return objClone;
                }
                this.c = (MessageDigest) messageDigest.clone();
                return objClone;
            } catch (CloneNotSupportedException unused) {
                return objClone;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    @Override // iaik.pkcs.pkcs1.MaskGenerationAlgorithmSpi
    protected AlgorithmParameters engineGetParameters() {
        AlgorithmID algorithmID = this.b;
        AlgorithmParameters algorithmParameters = null;
        if (algorithmID == null) {
            return null;
        }
        MGF1ParameterSpec mGF1ParameterSpec = new MGF1ParameterSpec(algorithmID);
        try {
            algorithmParameters = AlgorithmParameters.getInstance("MGF1", "IAIK");
            algorithmParameters.init(mGF1ParameterSpec);
            return algorithmParameters;
        } catch (Exception unused) {
            return algorithmParameters;
        }
    }

    @Override // iaik.pkcs.pkcs1.MaskGenerationAlgorithmSpi
    protected void engineMask(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4) {
        try {
            a();
            byte[] bArr3 = new byte[4];
            int i5 = 0;
            while (i5 < i3) {
                this.c.update(bArr, i, i2);
                this.c.update(bArr3);
                byte[] bArrDigest = this.c.digest();
                CryptoUtils.increment(bArr3);
                for (int i6 = 0; i6 < bArrDigest.length && i5 < i3; i6++) {
                    bArr2[i4] = (byte) (bArrDigest[i6] ^ bArr2[i4]);
                    i5++;
                    i4++;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No MessageDigest engine available: ");
            stringBuffer.append(e.getMessage());
            throw new NullPointerException(stringBuffer.toString());
        }
    }

    @Override // iaik.pkcs.pkcs1.MaskGenerationAlgorithmSpi
    protected void engineReset() {
        MessageDigest messageDigest = this.c;
        if (messageDigest != null) {
            messageDigest.reset();
        }
    }

    @Override // iaik.pkcs.pkcs1.MaskGenerationAlgorithmSpi
    protected void engineSetParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
        if (algorithmParameters != null) {
            try {
                Class clsClass$ = a;
                if (clsClass$ == null) {
                    clsClass$ = class$("iaik.pkcs.pkcs1.MGF1ParameterSpec");
                    a = clsClass$;
                }
                engineSetParameters(algorithmParameters.getParameterSpec(clsClass$));
            } catch (InvalidParameterSpecException unused) {
                throw new InvalidAlgorithmParameterException("Only MGF1Parameters allowed.");
            }
        }
    }

    @Override // iaik.pkcs.pkcs1.MaskGenerationAlgorithmSpi
    protected void engineSetParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof MGF1ParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Parameter must be of type MGF1ParameterSpec");
        }
        MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) algorithmParameterSpec;
        this.b = mGF1ParameterSpec.getHashAlgorithm();
        try {
            this.c = mGF1ParameterSpec.getHashEngine();
        } catch (NoSuchAlgorithmException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No hash engine available for the requested hash algorithm: ");
            stringBuffer.append(e.getMessage());
            throw new InvalidAlgorithmParameterException(stringBuffer.toString());
        }
    }

    public ObjectID getOID() {
        return OID;
    }
}

package iaik.pkcs.pkcs1;

import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.SHA;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class RSAPssParameterSpec extends RSAPssSaltParameterSpec implements Cloneable {
    public static final AlgorithmID DEFAULT_HASH_ALGORITHM;
    public static final AlgorithmID DEFAULT_MASK_GEN_ALGORITHM;
    public static final int DEFAULT_TRAILER_FIELD;
    private AlgorithmID a;
    private MessageDigest b;
    private AlgorithmID c;
    private MaskGenerationAlgorithm d;
    private int e;
    private Boolean f;

    static {
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.sha1.clone();
        DEFAULT_HASH_ALGORITHM = algorithmID;
        AlgorithmID algorithmID2 = (AlgorithmID) AlgorithmID.mgf1.clone();
        DEFAULT_MASK_GEN_ALGORITHM = algorithmID2;
        algorithmID2.setParameter(algorithmID.toASN1Object());
        DEFAULT_TRAILER_FIELD = 1;
    }

    public RSAPssParameterSpec() {
        this.a = (AlgorithmID) DEFAULT_HASH_ALGORITHM.clone();
        this.b = new SHA();
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.mgf1.clone();
        this.c = algorithmID;
        algorithmID.setParameter(this.a.toASN1Object());
        this.d = new MGF1(this.c, this.b);
        this.e = DEFAULT_TRAILER_FIELD;
    }

    public RSAPssParameterSpec(AlgorithmID algorithmID, AlgorithmID algorithmID2, int i) {
        super(i);
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create RSAPssParameterSpec. Missing hashAlgorithm id!");
        }
        this.a = algorithmID;
        if (algorithmID2 == null) {
            throw new IllegalArgumentException("Cannot create RSAPssParameterSpec. Missing maskGenAlgorithm id!");
        }
        this.c = algorithmID2;
        this.e = DEFAULT_TRAILER_FIELD;
    }

    public RSAPssParameterSpec(AlgorithmID algorithmID, AlgorithmID algorithmID2, byte[] bArr) {
        super(bArr);
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create RSAPssParameterSpec. Missing hashAlgorithm id!");
        }
        this.a = algorithmID;
        if (algorithmID2 == null) {
            throw new IllegalArgumentException("Cannot create RSAPssParameterSpec. Missing maskGenAlgorithm id!");
        }
        this.c = algorithmID2;
        this.e = DEFAULT_TRAILER_FIELD;
    }

    public Object clone() {
        RSAPssParameterSpec rSAPssParameterSpec;
        RSAPssParameterSpec rSAPssParameterSpec2 = null;
        try {
            rSAPssParameterSpec = (RSAPssParameterSpec) super.clone();
        } catch (CloneNotSupportedException unused) {
        }
        try {
            rSAPssParameterSpec.a = (AlgorithmID) this.a.clone();
            rSAPssParameterSpec.c = (AlgorithmID) this.c.clone();
            rSAPssParameterSpec.saltLength_ = this.saltLength_;
            rSAPssParameterSpec.e = this.e;
            MessageDigest messageDigest = this.b;
            if (messageDigest != null) {
                rSAPssParameterSpec.b = (MessageDigest) messageDigest.clone();
            }
            MaskGenerationAlgorithm maskGenerationAlgorithm = this.d;
            if (maskGenerationAlgorithm != null) {
                rSAPssParameterSpec.d = (MaskGenerationAlgorithm) maskGenerationAlgorithm.clone();
            }
            Boolean bool = this.f;
            if (bool == null) {
                return rSAPssParameterSpec;
            }
            rSAPssParameterSpec.f = new Boolean(bool.booleanValue());
            return rSAPssParameterSpec;
        } catch (CloneNotSupportedException unused2) {
            rSAPssParameterSpec2 = rSAPssParameterSpec;
            return rSAPssParameterSpec2;
        }
    }

    @Override // iaik.pkcs.pkcs1.RSAPssSaltParameterSpec
    public boolean equals(Object obj) {
        boolean z = false;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RSAPssParameterSpec)) {
            return false;
        }
        RSAPssParameterSpec rSAPssParameterSpec = (RSAPssParameterSpec) obj;
        boolean zEquals = super.equals(obj);
        if (!zEquals) {
            return zEquals;
        }
        if (this.a.equals(rSAPssParameterSpec.a) && this.c.equals(rSAPssParameterSpec.c, true) && this.e == rSAPssParameterSpec.e) {
            z = true;
        }
        return z & zEquals;
    }

    public Boolean getEncodeDefaultValues() {
        return this.f;
    }

    public AlgorithmID getHashAlgorithm() {
        return this.a;
    }

    public MessageDigest getHashEngine() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = this.b;
        if (messageDigest == null) {
            try {
                this.b = this.a.getMessageDigestInstance("IAIK");
            } catch (NoSuchAlgorithmException unused) {
                this.b = this.a.getMessageDigestInstance();
            }
        } else {
            messageDigest.reset();
        }
        return this.b;
    }

    public MaskGenerationAlgorithm getMGFEngine() throws NoSuchAlgorithmException {
        if (this.d == null) {
            this.d = this.c.getMaskGenerationAlgorithmInstance();
            try {
                this.d.setParameters(this.c.getAlgorithmParameters());
            } catch (InvalidAlgorithmParameterException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot init MGF parameters: ");
                stringBuffer.append(e.getMessage());
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
        }
        return this.d;
    }

    public AlgorithmID getMaskGenAlgorithm() {
        return this.c;
    }

    public int getTrailerField() {
        return this.e;
    }

    @Override // iaik.pkcs.pkcs1.RSAPssSaltParameterSpec
    public int hashCode() {
        return (this.a.hashCode() ^ this.c.hashCode()) ^ this.saltLength_;
    }

    public void setEncodeDefaultValues(Boolean bool) {
        this.f = bool;
    }

    public void setHashEngine(MessageDigest messageDigest) {
        this.b = messageDigest;
    }

    public void setMGFEngine(MaskGenerationAlgorithm maskGenerationAlgorithm) {
        this.d = maskGenerationAlgorithm;
    }

    public void setTrailerField(int i) {
        this.e = i;
    }

    @Override // iaik.pkcs.pkcs1.RSAPssSaltParameterSpec
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Hash algorithm: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Mask generation algorithm: ");
        stringBuffer3.append(this.c);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("Salt length: ");
        stringBuffer4.append(this.saltLength_);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append("Trailer field: ");
        stringBuffer5.append(this.e);
        stringBuffer5.append("\n");
        stringBuffer.append(stringBuffer5.toString());
        return stringBuffer.toString();
    }
}

package iaik.pkcs.pkcs1;

import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.SHA;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class RSAOaepParameterSpec extends RSAOaepPSourceParameterSpec implements Cloneable {
    public static final AlgorithmID DEFAULT_HASH_ALGORITHM;
    public static final AlgorithmID DEFAULT_MASK_GEN_ALGORITHM;
    private AlgorithmID a;
    private MessageDigest b;
    private AlgorithmID c;
    private MaskGenerationAlgorithm d;
    private Boolean e;

    static {
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.sha1.clone();
        DEFAULT_HASH_ALGORITHM = algorithmID;
        AlgorithmID algorithmID2 = (AlgorithmID) AlgorithmID.mgf1.clone();
        DEFAULT_MASK_GEN_ALGORITHM = algorithmID2;
        algorithmID2.setParameter(algorithmID.toASN1Object());
    }

    public RSAOaepParameterSpec() {
        this.a = (AlgorithmID) DEFAULT_HASH_ALGORITHM.clone();
        this.b = new SHA();
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.mgf1.clone();
        this.c = algorithmID;
        algorithmID.setParameter(this.a.toASN1Object());
        this.d = new MGF1(this.a, this.b);
    }

    public RSAOaepParameterSpec(AlgorithmID algorithmID, AlgorithmID algorithmID2, AlgorithmID algorithmID3) {
        super(algorithmID3);
        if (algorithmID == null) {
            throw new IllegalArgumentException("HashAlgorithm id must not be null!");
        }
        this.a = algorithmID;
        if (algorithmID2 == null) {
            throw new IllegalArgumentException("MaskGenAlgorithm id must no be null!");
        }
        this.c = algorithmID2;
    }

    public Object clone() {
        RSAOaepParameterSpec rSAOaepParameterSpec;
        RSAOaepParameterSpec rSAOaepParameterSpec2 = null;
        try {
            rSAOaepParameterSpec = (RSAOaepParameterSpec) super.clone();
        } catch (CloneNotSupportedException unused) {
        }
        try {
            rSAOaepParameterSpec.a = (AlgorithmID) this.a.clone();
            rSAOaepParameterSpec.c = (AlgorithmID) this.c.clone();
            rSAOaepParameterSpec.pSourceAlgorithm_ = (AlgorithmID) this.pSourceAlgorithm_.clone();
            MessageDigest messageDigest = this.b;
            if (messageDigest != null) {
                rSAOaepParameterSpec.b = (MessageDigest) messageDigest.clone();
            }
            MaskGenerationAlgorithm maskGenerationAlgorithm = this.d;
            if (maskGenerationAlgorithm != null) {
                rSAOaepParameterSpec.d = (MaskGenerationAlgorithm) maskGenerationAlgorithm.clone();
            }
            Boolean bool = this.e;
            if (bool == null) {
                return rSAOaepParameterSpec;
            }
            rSAOaepParameterSpec.e = new Boolean(bool.booleanValue());
            return rSAOaepParameterSpec;
        } catch (CloneNotSupportedException unused2) {
            rSAOaepParameterSpec2 = rSAOaepParameterSpec;
            return rSAOaepParameterSpec2;
        }
    }

    @Override // iaik.pkcs.pkcs1.RSAOaepPSourceParameterSpec
    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof RSAOaepParameterSpec)) {
                return false;
            }
            RSAOaepParameterSpec rSAOaepParameterSpec = (RSAOaepParameterSpec) obj;
            if (!super.equals(obj) || !this.a.equals(rSAOaepParameterSpec.a) || !this.c.equals(rSAOaepParameterSpec.c, true) || !this.pSourceAlgorithm_.equals(rSAOaepParameterSpec.pSourceAlgorithm_, true)) {
                return false;
            }
        }
        return true;
    }

    public Boolean getEncodeDefaultValues() {
        return this.e;
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

    @Override // iaik.pkcs.pkcs1.RSAOaepPSourceParameterSpec
    public int hashCode() {
        return (this.a.hashCode() ^ this.c.hashCode()) ^ this.pSourceAlgorithm_.hashCode();
    }

    public void setEncodeDefaultValues(Boolean bool) {
        this.e = bool;
    }

    public void setHashEngine(MessageDigest messageDigest) {
        this.b = messageDigest;
    }

    public void setMGFEngine(MaskGenerationAlgorithm maskGenerationAlgorithm) {
        this.d = maskGenerationAlgorithm;
    }

    @Override // iaik.pkcs.pkcs1.RSAOaepPSourceParameterSpec
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
        stringBuffer4.append("PSource algorithm: ");
        stringBuffer4.append(this.pSourceAlgorithm_);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        return stringBuffer.toString();
    }
}

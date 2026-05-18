package iaik.pkcs.pkcs1;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.structures.AlgorithmID;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RSAOaepPSourceParameterSpec extends PKCS1AlgorithmParameterSpec {
    public static final AlgorithmID DEFAULT_PSOURCE_ALGORITHM;
    protected AlgorithmID pSourceAlgorithm_;

    static {
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.pSpecified.clone();
        DEFAULT_PSOURCE_ALGORITHM = algorithmID;
        algorithmID.setParameter(new OCTET_STRING());
    }

    public RSAOaepPSourceParameterSpec() {
        this.pSourceAlgorithm_ = a();
    }

    public RSAOaepPSourceParameterSpec(AlgorithmID algorithmID) {
        if (algorithmID == null) {
            throw new IllegalArgumentException("PSourceAlgorithm must not be null.");
        }
        this.pSourceAlgorithm_ = algorithmID;
        getLabel();
    }

    private static AlgorithmID a() {
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.pSpecified.clone();
        algorithmID.setParameter(new OCTET_STRING());
        return algorithmID;
    }

    public static RSAOaepPSourceParameterSpec getDefault() {
        RSAOaepPSourceParameterSpec rSAOaepPSourceParameterSpec = new RSAOaepPSourceParameterSpec();
        AlgorithmID algorithmID = (AlgorithmID) AlgorithmID.pSpecified.clone();
        rSAOaepPSourceParameterSpec.pSourceAlgorithm_ = algorithmID;
        algorithmID.setParameter(new OCTET_STRING());
        return rSAOaepPSourceParameterSpec;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RSAOaepPSourceParameterSpec) {
            return this.pSourceAlgorithm_.equals(((RSAOaepPSourceParameterSpec) obj).pSourceAlgorithm_, true);
        }
        return false;
    }

    public byte[] getLabel() {
        ASN1Object parameter;
        AlgorithmID algorithmID = this.pSourceAlgorithm_;
        byte[] bArr = null;
        if (algorithmID != null && (parameter = algorithmID.getParameter()) != null) {
            if (parameter.isA(ASN.OCTET_STRING)) {
                OCTET_STRING octet_string = (OCTET_STRING) this.pSourceAlgorithm_.getParameter();
                if (octet_string != null) {
                    bArr = (byte[]) ((byte[]) octet_string.getValue()).clone();
                }
            } else if (!parameter.isA(ASN.NULL)) {
                throw new IllegalArgumentException("Invalid PSource parameters! Expected ASN.1 OCTET_STRING!");
            }
        }
        return bArr == null ? new byte[0] : bArr;
    }

    public AlgorithmID getPSourceAlgorithm() {
        return this.pSourceAlgorithm_;
    }

    public int hashCode() {
        return this.pSourceAlgorithm_.hashCode();
    }

    public void setLabel(byte[] bArr) {
        if (bArr != null) {
            AlgorithmID algorithmID = this.pSourceAlgorithm_;
            Objects.requireNonNull(algorithmID, "PSourceAlgorithm not set!");
            algorithmID.setParameter(new OCTET_STRING((byte[]) bArr.clone()));
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PSourceAlgorithm: ");
        stringBuffer.append(this.pSourceAlgorithm_);
        return stringBuffer.toString();
    }
}

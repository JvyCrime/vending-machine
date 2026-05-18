package iaik.pkcs.pkcs1;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
public class RSAPssSaltParameterSpec extends PKCS1AlgorithmParameterSpec {
    public static final int DEFAULT_SALT_LENGTH = 20;
    protected int saltLength_;
    protected byte[] salt_;

    public RSAPssSaltParameterSpec() {
        this.saltLength_ = 20;
    }

    public RSAPssSaltParameterSpec(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Cannot set saltLength parameter; must not be negative.");
        }
        this.saltLength_ = i;
    }

    public RSAPssSaltParameterSpec(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Salt must not be null.");
        }
        this.salt_ = bArr;
        this.saltLength_ = bArr.length;
    }

    public boolean equals(Object obj) {
        boolean zSecureEqualsBlock;
        byte[] bArr;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RSAPssSaltParameterSpec)) {
            return false;
        }
        RSAPssSaltParameterSpec rSAPssSaltParameterSpec = (RSAPssSaltParameterSpec) obj;
        boolean z = this.saltLength_ == rSAPssSaltParameterSpec.saltLength_;
        if (!z) {
            return z;
        }
        byte[] bArr2 = this.salt_;
        if (bArr2 == null || (bArr = rSAPssSaltParameterSpec.salt_) == null) {
            zSecureEqualsBlock = bArr2 == null && rSAPssSaltParameterSpec.salt_ == null;
        } else {
            zSecureEqualsBlock = CryptoUtils.secureEqualsBlock(bArr2, bArr);
        }
        return z & zSecureEqualsBlock;
    }

    public byte[] getSalt() {
        return this.salt_;
    }

    public int getSaltLength() {
        return this.saltLength_;
    }

    public int hashCode() {
        return this.saltLength_;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Salt length: ");
        stringBuffer.append(this.saltLength_);
        return stringBuffer.toString();
    }
}

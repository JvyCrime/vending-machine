package iaik.security.dsa;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.utils.CryptoUtils;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public class SHA2withDSAParams extends DSAParams {
    private int a;
    private byte[] b;

    public SHA2withDSAParams(ASN1Object aSN1Object) throws CodingException {
        super(aSN1Object);
        this.a = -1;
    }

    public SHA2withDSAParams(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        super(bigInteger, bigInteger2, bigInteger3);
        this.a = -1;
    }

    public SHA2withDSAParams(java.security.interfaces.DSAParams dSAParams) {
        super(dSAParams);
        this.a = -1;
        if (dSAParams instanceof SHA2withDSAParameterSpec) {
            SHA2withDSAParameterSpec sHA2withDSAParameterSpec = (SHA2withDSAParameterSpec) dSAParams;
            this.a = sHA2withDSAParameterSpec.getCounter();
            this.b = sHA2withDSAParameterSpec.getSeed();
        }
        if (dSAParams instanceof SHA2withDSAParams) {
            SHA2withDSAParams sHA2withDSAParams = (SHA2withDSAParams) dSAParams;
            this.a = sHA2withDSAParams.getCounter();
            this.b = sHA2withDSAParams.getSeed();
        }
    }

    void a(int i) {
        this.a = i;
    }

    void a(byte[] bArr) {
        this.b = bArr;
    }

    @Override // iaik.security.dsa.DSAParams
    public boolean equals(Object obj) {
        return (obj instanceof SHA2withDSAParams) && super.equals(obj) && CryptoUtils.equalsBlock(this.b, ((SHA2withDSAParams) obj).b);
    }

    public int getCounter() {
        return this.a;
    }

    public byte[] getSeed() {
        return this.b;
    }

    @Override // iaik.security.dsa.DSAParams
    public int hashCode() {
        int i = 0;
        byte b = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i >= bArr.length) {
                return super.hashCode() + (b << 24);
            }
            b = (byte) (b ^ bArr[i]);
            i++;
        }
    }
}

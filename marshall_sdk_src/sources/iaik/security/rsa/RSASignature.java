package iaik.security.rsa;

import iaik.asn1.DerCoder;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.Padding;
import iaik.utils.CryptoUtils;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class RSASignature extends b {
    private byte[][] a;

    protected RSASignature(AlgorithmID algorithmID, MessageDigest messageDigest) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(algorithmID.getName());
        stringBuffer.append("withRSA");
        super(stringBuffer.toString(), algorithmID, messageDigest, Padding.PADDING_PKCS1);
    }

    RSASignature(AlgorithmID algorithmID, MessageDigest messageDigest, byte[][] bArr) {
        this(algorithmID, messageDigest);
        a(bArr);
    }

    protected RSASignature(String str, MessageDigest messageDigest) {
        super(str, messageDigest, Padding.PADDING_PKCS1);
    }

    void a(byte[][] bArr) {
        this.a = bArr;
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        byte[] bArrEncode;
        Objects.requireNonNull(this.c, "Cannot calculate signature. Digest algorithm must not be null!");
        byte[] bArrA = a();
        try {
            byte[][] bArr = this.a;
            if (bArr != null) {
                byte[] bArr2 = bArr[0];
                int length = bArr2.length;
                bArrEncode = new byte[bArrA.length + length];
                System.arraycopy(bArr2, 0, bArrEncode, 0, length);
                System.arraycopy(bArrA, 0, bArrEncode, length, bArrA.length);
            } else {
                SEQUENCE sequence = new SEQUENCE();
                sequence.addComponent(this.c.toASN1Object());
                sequence.addComponent(new OCTET_STRING(bArrA));
                bArrEncode = DerCoder.encode(sequence);
            }
            return a(bArrEncode);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Signing error: ");
            stringBuffer.append(e.toString());
            throw new SignatureException(stringBuffer.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        byte[] bArrA = a();
        try {
            byte[] bArrA2 = a(bArr);
            try {
                if (this.a == null) {
                    if (this.c == null) {
                        throw new NullPointerException("Cannot calculate signature. Digest algorithm must not be null!");
                    }
                    SEQUENCE sequence = new SEQUENCE();
                    sequence.addComponent(this.c.toASN1Object());
                    sequence.addComponent(new OCTET_STRING(bArrA));
                    return CryptoUtils.equalsBlock(DerCoder.encode(sequence), bArrA2);
                }
                int i = 0;
                while (true) {
                    byte[][] bArr2 = this.a;
                    if (i >= bArr2.length) {
                        return false;
                    }
                    byte[] bArr3 = bArr2[i];
                    int length = bArr3.length;
                    if (bArrA2.length > length && CryptoUtils.equalsBlock(bArr3, 0, bArrA2, 0, length)) {
                        int length2 = bArrA2.length - length;
                        if (length2 == bArrA.length) {
                            return CryptoUtils.secureEqualsBlock(bArrA, 0, bArrA2, length, length2);
                        }
                        return false;
                    }
                    i++;
                }
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Signature ASN.1 formatting error: ");
                stringBuffer.append(e.toString());
                throw new SignatureException(stringBuffer.toString());
            }
        } catch (Exception e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Signature decryption error: ");
            stringBuffer2.append(e2.toString());
            throw new SignatureException(stringBuffer2.toString());
        }
    }
}

package iaik.security.cipher;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.utils.Util;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParametersSpi;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class GCMParameters extends AlgorithmParametersSpi {
    private byte[] a;
    private int b;
    private byte[] c;
    private byte[] d;

    public GCMParameters() throws InvalidAlgorithmParameterException {
        this(null, null, null, -1);
    }

    public GCMParameters(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) throws InvalidAlgorithmParameterException {
        this.a = bArr2;
        this.b = i;
        this.c = bArr;
        this.d = bArr3;
        if ((bArr3 == null || bArr3.length == 0) && i == -1) {
            return;
        }
        i = i == -1 ? bArr3.length : i;
        if ((i < 12 || i > 16) && i != 4 && i != 8) {
            throw new InvalidAlgorithmParameterException("Invalid MAC length - valid length values: 4,8,12,13,14,15,16 bytes!");
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new OCTET_STRING(this.a));
        sequence.addComponent(new INTEGER(this.b));
        return DerCoder.encode(sequence);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        try {
            byte[] bArr = this.d;
            GCMParameterSpec gCMParameterSpec = bArr == null ? new GCMParameterSpec(this.c, this.a, this.b) : new GCMParameterSpec(this.c, this.a, bArr);
            if (gCMParameterSpec.getClass().isAssignableFrom(cls)) {
                return gCMParameterSpec;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Can not convert to class ");
            stringBuffer.append(cls.getName());
            throw new InvalidParameterSpecException(stringBuffer.toString());
        } catch (Exception e) {
            throw new InvalidParameterSpecException(e.toString());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof GCMParameterSpec) {
            GCMParameterSpec gCMParameterSpec = (GCMParameterSpec) algorithmParameterSpec;
            this.a = gCMParameterSpec.getNonce();
            this.b = gCMParameterSpec.getMacLength();
            this.c = gCMParameterSpec.getAAD();
            this.d = gCMParameterSpec.getMac();
            return;
        }
        if (algorithmParameterSpec instanceof IvParameterSpec) {
            this.a = ((IvParameterSpec) algorithmParameterSpec).getIV();
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can not initialize from class ");
        stringBuffer.append(algorithmParameterSpec.getClass().getName());
        throw new InvalidParameterSpecException(stringBuffer.toString());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            ASN1Object aSN1ObjectDecode = DerCoder.decode(bArr);
            if (!aSN1ObjectDecode.isA(ASN.SEQUENCE)) {
                throw new IOException("Cannot decode GCM params. Invalid ASN.1 type!");
            }
            this.a = (byte[]) aSN1ObjectDecode.getComponentAt(0).getValue();
            int iIntValue = ((BigInteger) aSN1ObjectDecode.getComponentAt(1).getValue()).intValue();
            this.b = iIntValue;
            if (iIntValue == -1) {
                this.b = 12;
            }
            int i = this.b;
            if ((i < 12 || i > 16) && i != 4 && i != 8) {
                throw new IOException("Invalid MAC length - valid length values: 4,8,12,13,14,15,16 bytes!");
            }
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("DER decoding error. ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr, String str) throws IOException {
        engineInit(bArr);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected String engineToString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("AAD: ");
        byte[] bArr = this.c;
        stringBuffer2.append(bArr != null ? Util.toString(bArr) : "null");
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("nonce: ");
        byte[] bArr2 = this.a;
        stringBuffer3.append(bArr2 != null ? Util.toString(bArr2) : "null");
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("macLength: ");
        stringBuffer4.append(this.b);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        return stringBuffer.toString();
    }
}

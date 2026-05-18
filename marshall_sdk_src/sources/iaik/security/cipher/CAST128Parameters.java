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
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class CAST128Parameters extends AlgorithmParametersSpi {
    int a;
    byte[] b;

    private boolean a(byte[] bArr) {
        if (bArr == null) {
            return true;
        }
        for (byte b : bArr) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        SEQUENCE sequence = new SEQUENCE();
        byte[] bArr = this.b;
        if (bArr != null && !a(bArr)) {
            sequence.addComponent(new OCTET_STRING(this.b));
        }
        sequence.addComponent(new INTEGER(this.a));
        return DerCoder.encode(sequence);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        CAST128ParameterSpec cAST128ParameterSpec = new CAST128ParameterSpec(this.a, this.b);
        if (cAST128ParameterSpec.getClass().isAssignableFrom(cls)) {
            return cAST128ParameterSpec;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Can not convert to class ");
        stringBuffer.append(cls.getName());
        throw new InvalidParameterSpecException(stringBuffer.toString());
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof CAST128ParameterSpec) {
            CAST128ParameterSpec cAST128ParameterSpec = (CAST128ParameterSpec) algorithmParameterSpec;
            this.a = cAST128ParameterSpec.getKeyLength();
            byte[] iv = cAST128ParameterSpec.getIV();
            this.b = iv;
            if (iv != null && iv.length != 8) {
                throw new InvalidParameterSpecException("IV must be 8 octets long!");
            }
            return;
        }
        if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
            throw new InvalidParameterSpecException("Parameter must be a CAST128ParameterSpec.");
        }
        byte[] iv2 = ((IvParameterSpec) algorithmParameterSpec).getIV();
        this.b = iv2;
        this.a = -1;
        if (iv2 != null && iv2.length != 8) {
            throw new InvalidParameterSpecException("IV must be 8 octets long!");
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            ASN1Object aSN1ObjectDecode = DerCoder.decode(bArr);
            if (!aSN1ObjectDecode.isA(ASN.SEQUENCE)) {
                if (!aSN1ObjectDecode.isA(ASN.OCTET_STRING)) {
                    throw new IOException("Cannot decode CAST params. Invalid ASN.1 type!");
                }
                this.a = -1;
                this.b = (byte[]) aSN1ObjectDecode.getValue();
                return;
            }
            int i = 0;
            int iCountComponents = aSN1ObjectDecode.countComponents();
            if (iCountComponents < 1 || iCountComponents > 2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid number of components (");
                stringBuffer.append(iCountComponents);
                stringBuffer.append(". Only 1 or 2 allowed!");
                throw new IOException(stringBuffer.toString());
            }
            if (aSN1ObjectDecode.countComponents() == 2) {
                ASN1Object componentAt = aSN1ObjectDecode.getComponentAt(0);
                if (!componentAt.isA(ASN.OCTET_STRING)) {
                    throw new IOException("Invalid iv component type! Expected ASN.1 OCTET_STRING.");
                }
                this.b = (byte[]) componentAt.getValue();
                i = 1;
            }
            if (!aSN1ObjectDecode.getComponentAt(i).isA(ASN.INTEGER)) {
                throw new IOException("Invalid keyLength component type! Expected ASN.1 INTEGER.");
            }
            this.a = ((BigInteger) aSN1ObjectDecode.getComponentAt(i).getValue()).intValue();
        } catch (CodingException e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("DER decoding error. ");
            stringBuffer2.append(e.toString());
            throw new IOException(stringBuffer2.toString());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr, String str) throws IOException {
        engineInit(bArr);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected String engineToString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("keyLength: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append("IV: ");
        if (this.b == null) {
            string = "00:00:00:00:00:00:00:00\n";
        } else {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("IV: ");
            stringBuffer3.append(Util.toString(this.b));
            stringBuffer3.append("\n");
            string = stringBuffer3.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

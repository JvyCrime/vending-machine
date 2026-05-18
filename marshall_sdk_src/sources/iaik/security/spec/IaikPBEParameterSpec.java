package iaik.security.spec;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.utils.Util;
import java.math.BigInteger;
import javax.crypto.spec.PBEParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class IaikPBEParameterSpec extends PBEParameterSpec implements ASN1Type {
    private byte[] a;
    private int b;

    public IaikPBEParameterSpec(ASN1Object aSN1Object) throws CodingException {
        super(new byte[0], 0);
        decode(aSN1Object);
    }

    public IaikPBEParameterSpec(byte[] bArr, int i) {
        super(new byte[0], 0);
        this.a = bArr;
        this.b = i;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = (byte[]) aSN1Object.getComponentAt(0).getValue();
        this.b = 1;
        if (aSN1Object.countComponents() == 2) {
            this.b = ((BigInteger) aSN1Object.getComponentAt(1).getValue()).intValue();
        }
    }

    @Override // javax.crypto.spec.PBEParameterSpec
    public int getIterationCount() {
        return this.b;
    }

    @Override // javax.crypto.spec.PBEParameterSpec
    public byte[] getSalt() {
        return this.a;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new OCTET_STRING(this.a));
        sequence.addComponent(new INTEGER(BigInteger.valueOf(this.b)));
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Salt: ");
        byte[] bArr = this.a;
        if (bArr != null) {
            stringBuffer.append(Util.toString(bArr));
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("\nIteration Count: ");
        stringBuffer2.append(this.b);
        stringBuffer.append(stringBuffer2.toString());
        return stringBuffer.toString();
    }
}

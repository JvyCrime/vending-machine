package iaik.x509.extensions.qualified.structures.etsi;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.PrintableString;
import iaik.asn1.SEQUENCE;
import iaik.x509.extensions.qualified.structures.QCStatementInfo;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class QcEuLimitValue extends QCStatementInfo {
    public static final ObjectID statementID = ObjectID.qcEuLimitValue;
    Object a;
    int b;
    int c;

    public QcEuLimitValue() {
    }

    public QcEuLimitValue(int i, int i2, int i3) throws IllegalArgumentException {
        if (i < 1 || i > 999) {
            throw new IllegalArgumentException("Invalid ISO 4217 curreny code. Must be between 1 .. 999.");
        }
        this.a = BigInteger.valueOf(i);
        this.b = i2;
        this.c = i3;
    }

    public QcEuLimitValue(String str, int i, int i2) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Cannot create QcEuLimitValue. Missing currency specification.");
        }
        if (str.length() != 3) {
            throw new IllegalArgumentException("Invalid ISO 4217 currency code");
        }
        this.a = str;
        this.b = i;
        this.c = i2;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = aSN1Object.getComponentAt(0).getValue();
        this.b = ((BigInteger) aSN1Object.getComponentAt(1).getValue()).intValue();
        this.c = ((BigInteger) aSN1Object.getComponentAt(2).getValue()).intValue();
    }

    public int getAmount() {
        return this.b;
    }

    public Object getCurrency() {
        return this.a;
    }

    public int getExponent() {
        return this.c;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ObjectID getStatementID() {
        return statementID;
    }

    public BigInteger getValue() {
        return BigInteger.valueOf(this.b).multiply(BigInteger.valueOf(10L).pow(this.c));
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a instanceof BigInteger ? new INTEGER((BigInteger) this.a) : new PrintableString((String) this.a));
        sequence.addComponent(new INTEGER(this.b));
        sequence.addComponent(new INTEGER(this.c));
        return sequence;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Currency: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Amount: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("Exponent: ");
        stringBuffer4.append(this.c);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        return stringBuffer.toString();
    }
}

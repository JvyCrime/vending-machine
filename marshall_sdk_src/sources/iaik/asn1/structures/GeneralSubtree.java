package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public class GeneralSubtree implements ASN1Type {
    GeneralName a;
    int b = 0;
    int c = -1;

    public GeneralSubtree() {
    }

    public GeneralSubtree(GeneralName generalName) {
        this.a = generalName;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        try {
            this.a = new GeneralName(aSN1Object.getComponentAt(0));
            for (int i = 1; i < aSN1Object.countComponents(); i++) {
                CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
                int tag = con_spec.getAsnType().getTag();
                if (tag == 0) {
                    if (con_spec.isImplicitlyTagged()) {
                        con_spec.forceImplicitlyTagged(ASN.INTEGER);
                    }
                    this.b = ((BigInteger) ((ASN1Object) con_spec.getValue()).getValue()).intValue();
                } else if (tag == 1) {
                    if (con_spec.isImplicitlyTagged()) {
                        con_spec.forceImplicitlyTagged(ASN.INTEGER);
                    }
                    this.c = ((BigInteger) ((ASN1Object) con_spec.getValue()).getValue()).intValue();
                }
            }
        } catch (Exception e) {
            throw new CodingException(e.getMessage());
        }
    }

    public GeneralName getBase() {
        return this.a;
    }

    public int getMaximum() {
        return this.c;
    }

    public int getMinimum() {
        return this.b;
    }

    public void setMaximum(int i) {
        this.c = i;
    }

    public void setMinimum(int i) {
        this.b = i;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a.toASN1Object());
        if (this.b != 0) {
            sequence.addComponent(new CON_SPEC(0, new INTEGER(this.b), true));
        }
        if (this.c != -1) {
            sequence.addComponent(new CON_SPEC(1, new INTEGER(this.c), true));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("base: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("minimum: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        if (this.c != -1) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("maximum: ");
            stringBuffer4.append(this.c);
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        stringBuffer.setLength(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }
}

package iaik.x509.ocsp.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.IA5String;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.math.BigInteger;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public class CrlID extends V3Extension {
    public static final ObjectID oid = ObjectID.ocspExt_CrlID;
    private String a;
    private BigInteger b;
    private ChoiceOfTime c;

    public int getCrlNum() {
        BigInteger bigInteger = this.b;
        if (bigInteger == null) {
            return -1;
        }
        return bigInteger.intValue();
    }

    public BigInteger getCrlNumber() {
        return this.b;
    }

    public Date getCrlTime() {
        ChoiceOfTime choiceOfTime = this.c;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    public String getCrlUrl() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            try {
                CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
                ASN1Object aSN1Object2 = (ASN1Object) con_spec.getValue();
                int tag = con_spec.getAsnType().getTag();
                if (tag == 0) {
                    this.a = (String) aSN1Object2.getValue();
                } else if (tag == 1) {
                    this.b = (BigInteger) aSN1Object2.getValue();
                } else {
                    if (tag != 2) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Invalid tag in CrlID encoding: ");
                        stringBuffer.append(tag);
                        throw new X509ExtensionException(stringBuffer.toString());
                    }
                    this.c = new ChoiceOfTime(aSN1Object2);
                }
            } catch (CodingException e) {
                throw new X509ExtensionException(e.getMessage());
            }
        }
    }

    public void setCrlNum(int i) {
        this.b = i == -1 ? null : BigInteger.valueOf(i);
    }

    public void setCrlNumber(BigInteger bigInteger) {
        this.b = bigInteger;
    }

    public void setCrlTime(Date date) {
        if (date == null) {
            this.c = null;
        } else {
            this.c = new ChoiceOfTime(date, ASN.GeneralizedTime);
        }
    }

    public void setCrlUrl(String str) {
        this.a = str;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        if (this.a != null) {
            sequence.addComponent(new CON_SPEC(0, new IA5String(this.a)));
        }
        if (this.b != null) {
            sequence.addComponent(new CON_SPEC(1, new INTEGER(this.b)));
        }
        if (this.c != null) {
            sequence.addComponent(new CON_SPEC(2, this.c.toASN1Object()));
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("crlUrl: ");
            stringBuffer2.append(this.a);
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\ncrlNum: ");
            stringBuffer3.append(this.b.toString());
            stringBuffer.append(stringBuffer3.toString());
        }
        if (this.c != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("\ncrlTime: ");
            stringBuffer4.append(this.c);
            stringBuffer.append(stringBuffer4.toString());
        }
        return stringBuffer.toString();
    }
}

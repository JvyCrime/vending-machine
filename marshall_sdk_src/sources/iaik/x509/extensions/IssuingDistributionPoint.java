package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.BIT_STRING;
import iaik.asn1.BOOLEAN;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.GeneralNames;
import iaik.asn1.structures.RDN;
import iaik.utils.InternalErrorException;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;

/* JADX INFO: loaded from: classes2.dex */
public class IssuingDistributionPoint extends V3Extension {
    public static final ObjectID oid = ObjectID.crlExt_IssuingDistributionPoint;
    private ASN1Type a = null;
    private boolean b = false;
    private boolean c = false;
    private int d = -1;
    private boolean e = false;
    private boolean f = false;

    public ASN1Type getDistributionPointName() {
        return this.a;
    }

    public boolean getIndirectCRL() {
        return this.e;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public boolean getOnlyContainsAttributeCerts() {
        return this.f;
    }

    public boolean getOnlyContainsCaCerts() {
        return this.c;
    }

    public boolean getOnlyContainsUserCerts() {
        return this.b;
    }

    public int getReasonFlags() {
        return this.d;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        ASN1Type rdn;
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new X509ExtensionException("No DistributionPoint!");
        }
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            try {
                CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
                int tag = con_spec.getAsnType().getTag();
                if (tag == 0) {
                    CON_SPEC con_spec2 = (CON_SPEC) con_spec.getValue();
                    int tag2 = con_spec2.getAsnType().getTag();
                    if (tag2 == 0) {
                        con_spec2.forceImplicitlyTagged(ASN.SEQUENCE);
                        rdn = new GeneralNames((ASN1Object) con_spec2.getValue());
                    } else {
                        if (tag2 != 1) {
                            throw new CodingException("DistributionPointName: Unknown type [parse]!");
                        }
                        con_spec2.forceImplicitlyTagged(ASN.SET);
                        rdn = new RDN((ASN1Object) con_spec2.getValue());
                    }
                    this.a = rdn;
                } else if (tag == 1) {
                    con_spec.forceImplicitlyTagged(ASN.BOOLEAN);
                    this.b = ((Boolean) ((BOOLEAN) con_spec.getValue()).getValue()).booleanValue();
                } else if (tag == 2) {
                    con_spec.forceImplicitlyTagged(ASN.BOOLEAN);
                    this.c = ((Boolean) ((BOOLEAN) con_spec.getValue()).getValue()).booleanValue();
                } else if (tag == 3) {
                    con_spec.forceImplicitlyTagged(ASN.BIT_STRING);
                    this.d = Integer.parseInt(new StringBuffer(((BIT_STRING) con_spec.getValue()).getBinaryString()).reverse().toString(), 2);
                } else if (tag == 4) {
                    con_spec.forceImplicitlyTagged(ASN.BOOLEAN);
                    this.e = ((Boolean) ((BOOLEAN) con_spec.getValue()).getValue()).booleanValue();
                } else {
                    if (tag != 5) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Invalid issuingDistributionPoint: unsupported tag number: ");
                        stringBuffer.append(tag);
                        throw new CodingException(stringBuffer.toString());
                    }
                    con_spec.forceImplicitlyTagged(ASN.BOOLEAN);
                    this.f = ((Boolean) ((BOOLEAN) con_spec.getValue()).getValue()).booleanValue();
                }
            } catch (Exception e) {
                throw new X509ExtensionException(e.getMessage());
            }
        }
        if (this.b && this.c) {
            throw new X509ExtensionException("OnlyContainsUserCerts and OnlyContainsCaCerts are not allowed both to be true!");
        }
    }

    public void setDistributionPointName(ASN1Type aSN1Type) throws IllegalArgumentException {
        if (!(aSN1Type instanceof GeneralNames) && !(aSN1Type instanceof RDN)) {
            throw new IllegalArgumentException("Only instances of GeneralNames or RDN are accepted!");
        }
        this.a = aSN1Type;
    }

    public void setIndirectCRL(boolean z) {
        this.e = z;
    }

    public void setOnlyContainsAttributeCerts(boolean z) {
        this.f = z;
    }

    public void setOnlyContainsCaCerts(boolean z) {
        this.c = z;
    }

    public void setOnlyContainsUserCerts(boolean z) {
        this.b = z;
    }

    public void setReasonFlags(int i) {
        this.d = i;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        int i;
        if (this.b && this.c) {
            throw new X509ExtensionException("OnlyContainsUserCerts and OnlyContainsCaCerts are not allowed both to be true!");
        }
        SEQUENCE sequence = new SEQUENCE();
        try {
            ASN1Type aSN1Type = this.a;
            if (aSN1Type != null) {
                if (aSN1Type instanceof GeneralNames) {
                    i = 0;
                } else {
                    if (!(aSN1Type instanceof RDN)) {
                        throw new InternalErrorException("DistributionPointName: Unknown type [create]!");
                    }
                    i = 1;
                }
                sequence.addComponent(new CON_SPEC(0, new CON_SPEC(i, this.a.toASN1Object(), true)));
            }
            if (this.b) {
                sequence.addComponent(new CON_SPEC(1, new BOOLEAN(this.b), true));
            }
            if (this.c) {
                sequence.addComponent(new CON_SPEC(2, new BOOLEAN(this.c), true));
            }
            int i2 = this.d;
            if (i2 != -1) {
                StringBuffer stringBuffer = new StringBuffer(Integer.toBinaryString(i2));
                stringBuffer.reverse();
                sequence.addComponent(new CON_SPEC(3, new BIT_STRING(stringBuffer.toString()), true));
            }
            if (this.e) {
                sequence.addComponent(new CON_SPEC(4, new BOOLEAN(this.e), true));
            }
            if (this.f) {
                sequence.addComponent(new CON_SPEC(5, new BOOLEAN(this.f), true));
            }
            return sequence;
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            stringBuffer.append("DistributionPoint: ");
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.a.toString());
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("onlyContainsUserCerts: ");
        stringBuffer3.append(this.b);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("onlyContainsCaCerts: ");
        stringBuffer4.append(this.c);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        if (this.d > 0) {
            stringBuffer.append("reasonFlags: ");
            if ((this.d & 1) > 0) {
                stringBuffer.append("unused | ");
            }
            if ((this.d & 2) > 0) {
                stringBuffer.append("keyCompromise | ");
            }
            if ((this.d & 4) > 0) {
                stringBuffer.append("cACompromise | ");
            }
            if ((this.d & 8) > 0) {
                stringBuffer.append("affiliationChanged|");
            }
            if ((this.d & 16) > 0) {
                stringBuffer.append("superseded | ");
            }
            if ((this.d & 32) > 0) {
                stringBuffer.append("cessationOfOperation | ");
            }
            if ((this.d & 64) > 0) {
                stringBuffer.append("certificateHold | ");
            }
            stringBuffer.setLength(stringBuffer.length() - 3);
            stringBuffer.append("\n");
        }
        StringBuffer stringBuffer5 = new StringBuffer();
        stringBuffer5.append("indirectCRL: ");
        stringBuffer5.append(this.e);
        stringBuffer5.append("\n");
        stringBuffer.append(stringBuffer5.toString());
        StringBuffer stringBuffer6 = new StringBuffer();
        stringBuffer6.append("onlyContainsAttributeCerts: ");
        stringBuffer6.append(this.f);
        stringBuffer6.append("\n");
        stringBuffer.append(stringBuffer6.toString());
        stringBuffer.setLength(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }
}

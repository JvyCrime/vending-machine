package iaik.x509.attr;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Target implements ASN1Type {
    public static final int TARGET_CERT = 2;
    public static final int TARGET_GROUP = 1;
    public static final int TARGET_NAME = 0;
    private static volatile TargetChecker a;

    protected Target() {
    }

    protected Target(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    static TargetChecker a() {
        TargetChecker targetChecker = a;
        if (targetChecker != null) {
            return targetChecker;
        }
        a aVar = new a();
        a = aVar;
        return aVar;
    }

    public static Target parseTarget(ASN1Object aSN1Object) throws CodingException {
        Target targetName;
        Objects.requireNonNull(aSN1Object, "Cannot parse Target from null ASN.1 object!");
        if (!aSN1Object.isA(ASN.CON_SPEC)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 type (");
            stringBuffer.append(aSN1Object.getAsnType());
            stringBuffer.append("). Expected CON_SPEC!");
            throw new CodingException(stringBuffer.toString());
        }
        CON_SPEC con_spec = (CON_SPEC) aSN1Object;
        int tag = con_spec.getAsnType().getTag();
        if (tag == 0) {
            targetName = new TargetName();
        } else if (tag == 1) {
            targetName = new TargetGroup();
        } else {
            if (tag != 2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Invalid Target type: ");
                stringBuffer2.append(tag);
                throw new CodingException(stringBuffer2.toString());
            }
            targetName = new TargetCert();
            con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
        }
        targetName.decodeUnTaggedASN1Object((ASN1Object) con_spec.getValue());
        return targetName;
    }

    public static synchronized void setTargetChecker(TargetChecker targetChecker) {
        a = targetChecker;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        if (!aSN1Object.isA(ASN.CON_SPEC)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 type (");
            stringBuffer.append(aSN1Object.getAsnType().getTag());
            stringBuffer.append("). Expected CON_SPEC.");
            throw new CodingException(stringBuffer.toString());
        }
        CON_SPEC con_spec = (CON_SPEC) aSN1Object;
        int tag = con_spec.getAsnType().getTag();
        if (tag == getType()) {
            if (tag == 2) {
                con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
            }
            decodeUnTaggedASN1Object((ASN1Object) con_spec.getValue());
            return;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Invalid Target type (");
        stringBuffer2.append(tag);
        stringBuffer2.append("). Expected ");
        stringBuffer2.append(getTypeAsString());
        stringBuffer2.append(" (");
        stringBuffer2.append(getType());
        stringBuffer2.append(").");
        throw new CodingException(stringBuffer2.toString());
    }

    public abstract void decodeUnTaggedASN1Object(ASN1Object aSN1Object) throws CodingException;

    public abstract boolean equals(Object obj);

    public abstract int getType();

    protected abstract String getTypeAsString();

    public int hashCode() {
        return getType();
    }

    public boolean isTargetFor(Object obj) throws TargetException {
        return a().isTargetFor(this, obj);
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        ASN1Object unTaggedASN1Object = toUnTaggedASN1Object();
        int type = getType();
        return new CON_SPEC(type, unTaggedASN1Object, type == 2);
    }

    public String toString() {
        return getTypeAsString();
    }

    public abstract ASN1Object toUnTaggedASN1Object() throws CodingException;
}

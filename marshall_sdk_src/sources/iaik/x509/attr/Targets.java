package iaik.x509.attr;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.SEQUENCE;
import iaik.utils.Util;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class Targets implements ASN1Type {
    private Vector a;

    public Targets() {
        this.a = new Vector();
    }

    public Targets(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public boolean addTarget(Target target) {
        if (this.a.contains(target)) {
            return false;
        }
        this.a.addElement(target);
        return true;
    }

    public boolean containsTarget(Target target) {
        return this.a.contains(target);
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 type (");
            stringBuffer.append(aSN1Object.getAsnType().getTag());
            stringBuffer.append("). Expected SEQUENCE.");
            throw new CodingException(stringBuffer.toString());
        }
        this.a.removeAllElements();
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            this.a.addElement(Target.parseTarget(aSN1Object.getComponentAt(i)));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Targets) {
            Targets targets = (Targets) obj;
            if (this.a.size() == targets.a.size()) {
                Target[] targets2 = getTargets();
                Target[] targets3 = targets.getTargets();
                for (int i = 0; i < targets2.length; i++) {
                    if (targets2[i].equals(targets3[i])) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Target[] getTargets() {
        Target[] targetArr = new Target[this.a.size()];
        this.a.copyInto(targetArr);
        return targetArr;
    }

    public int hashCode() {
        int size = size();
        try {
            return Util.calculateHashCode(DerCoder.encode(toASN1Object()));
        } catch (CodingException unused) {
            return size;
        }
    }

    public boolean isTargetFor(Object obj) throws TargetException {
        boolean z = true;
        if (this.a.size() != 0) {
            Enumeration enumerationElements = this.a.elements();
            TargetException e = null;
            while (true) {
                if (!enumerationElements.hasMoreElements()) {
                    z = false;
                    break;
                }
                try {
                } catch (TargetException e2) {
                    e = e2;
                }
                if (((Target) enumerationElements.nextElement()).isTargetFor(obj)) {
                    break;
                }
            }
            if (!z && e != null) {
                throw e;
            }
        }
        return z;
    }

    public void removeAllTargets() {
        this.a.removeAllElements();
    }

    public boolean removeTarget(Target target) {
        return this.a.removeElement(target);
    }

    public void setTargets(Target[] targetArr) {
        this.a.removeAllElements();
        if (targetArr != null) {
            for (Target target : targetArr) {
                this.a.addElement(target);
            }
        }
    }

    public int size() {
        return this.a.size();
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            sequence.addComponent(((Target) enumerationElements.nextElement()).toASN1Object());
        }
        return sequence;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a.isEmpty()) {
            string = "Empty Target list.";
        } else {
            if (z) {
                Enumeration enumerationElements = this.a.elements();
                int i = 1;
                while (enumerationElements.hasMoreElements()) {
                    if (i > 1) {
                        stringBuffer.append("\n");
                    }
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Target ");
                    stringBuffer2.append(i);
                    stringBuffer2.append(":\n");
                    stringBuffer.append(stringBuffer2.toString());
                    Util.printIndented(enumerationElements.nextElement().toString(), true, stringBuffer);
                    i++;
                }
                return stringBuffer.toString();
            }
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Target list contains ");
            stringBuffer3.append(this.a.size());
            stringBuffer3.append(" element(s).");
            string = stringBuffer3.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

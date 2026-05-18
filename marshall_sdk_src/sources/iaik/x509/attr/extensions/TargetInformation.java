package iaik.x509.attr.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.attr.Target;
import iaik.x509.attr.TargetException;
import iaik.x509.attr.Targets;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class TargetInformation extends V3Extension {
    public static final ObjectID oid = ObjectID.attrCertExt_TargetInformation;
    private Vector a = new Vector();

    public boolean addTargetElement(Target target) {
        Targets targets;
        if (target == null || containsTargetElement(target)) {
            return false;
        }
        if (this.a.isEmpty()) {
            targets = new Targets();
            this.a.addElement(targets);
        } else {
            targets = (Targets) this.a.elementAt(0);
        }
        return targets.addTarget(target);
    }

    public boolean containsTargetElement(Target target) {
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            if (((Targets) enumerationElements.nextElement()).containsTarget(target)) {
                return true;
            }
        }
        return false;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public Target[] getTargetElements() {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            for (Target target : ((Targets) enumerationElements.nextElement()).getTargets()) {
                vector.addElement(target);
            }
        }
        Target[] targetArr = new Target[vector.size()];
        vector.copyInto(targetArr);
        return targetArr;
    }

    public Targets[] getTargets() {
        Targets[] targetsArr = new Targets[this.a.size()];
        this.a.copyInto(targetsArr);
        return targetsArr;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 type (");
            stringBuffer.append(aSN1Object.getAsnType().getName());
            stringBuffer.append(") of TargetInformation extension; must be a SEQUENCE!");
            throw new X509ExtensionException(stringBuffer.toString());
        }
        this.a.removeAllElements();
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            try {
                this.a.addElement(new Targets(aSN1Object.getComponentAt(i)));
            } catch (CodingException e) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Error parsing Targets object: ");
                stringBuffer2.append(e.getMessage());
                throw new X509ExtensionException(stringBuffer2.toString());
            }
        }
    }

    public boolean isTargetFor(Object obj) throws TargetException {
        Target[] targetElements = getTargetElements();
        boolean z = true;
        if (targetElements.length != 0) {
            TargetException e = null;
            int i = 0;
            while (true) {
                if (i >= targetElements.length) {
                    z = false;
                    break;
                }
                try {
                } catch (TargetException e2) {
                    e = e2;
                }
                if (targetElements[i].isTargetFor(obj)) {
                    break;
                }
                i++;
            }
            if (!z && e != null) {
                throw e;
            }
        }
        return z;
    }

    public int numberOfTargetElements() {
        return getTargetElements().length;
    }

    public int numberOfTargets() {
        return this.a.size();
    }

    public void removeAllTargets() {
        this.a.removeAllElements();
    }

    public boolean removeTargetElement(Target target) {
        Enumeration enumerationElements = this.a.elements();
        boolean z = false;
        while (enumerationElements.hasMoreElements()) {
            Targets targets = (Targets) enumerationElements.nextElement();
            if (targets.removeTarget(target)) {
                z = true;
                if (targets.size() == 0) {
                    this.a.removeElement(targets);
                }
            }
        }
        return z;
    }

    public boolean removeTargets(Targets targets) {
        return this.a.removeElement(targets);
    }

    public void setTargetElements(Target[] targetArr) {
        this.a.removeAllElements();
        if (targetArr != null) {
            Targets targets = new Targets();
            targets.setTargets(targetArr);
            this.a.addElement(targets);
        }
    }

    public void setTargets(Targets[] targetsArr) {
        this.a.removeAllElements();
        if (targetsArr != null) {
            for (Targets targets : targetsArr) {
                this.a.addElement(targets);
            }
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        SEQUENCE sequence = new SEQUENCE();
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            try {
                sequence.addComponent(((Targets) enumerationElements.nextElement()).toASN1Object());
            } catch (CodingException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot create ASN.1 Targets: ");
                stringBuffer.append(e.getMessage());
                throw new X509ExtensionException(stringBuffer.toString());
            }
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
            string = "This TargetInformation does not contain any Targets.";
        } else {
            int size = this.a.size();
            if (z) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Target list contains ");
                stringBuffer2.append(size);
                stringBuffer2.append(" Targets object(s):\n");
                stringBuffer.append(stringBuffer2.toString());
                Enumeration enumerationElements = this.a.elements();
                int i = 1;
                while (enumerationElements.hasMoreElements()) {
                    if (i > 1) {
                        stringBuffer.append("\n");
                    }
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("Targets ");
                    stringBuffer3.append(i);
                    stringBuffer3.append(":\n");
                    stringBuffer.append(stringBuffer3.toString());
                    Util.printIndented(((Targets) enumerationElements.nextElement()).toString(true), true, stringBuffer);
                    i++;
                }
                return stringBuffer.toString();
            }
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Target list contains ");
            stringBuffer4.append(size);
            stringBuffer4.append(" Targets object(s).");
            string = stringBuffer4.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

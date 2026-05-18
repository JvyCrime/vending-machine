package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.SEQUENCE;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class GeneralNames implements ASN1Type {
    private Vector a;

    public GeneralNames() {
        this.a = new Vector();
    }

    public GeneralNames(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public GeneralNames(GeneralName generalName) {
        this();
        this.a.addElement(generalName);
    }

    public void addName(GeneralName generalName) {
        this.a.addElement(generalName);
    }

    public boolean contains(GeneralName generalName) {
        Objects.requireNonNull(generalName, "generalName must not be null!");
        return this.a.contains(generalName);
    }

    public boolean containsGeneralName(int i) {
        Enumeration names = getNames();
        while (names.hasMoreElements()) {
            if (((GeneralName) names.nextElement()).getType() == i) {
                return true;
            }
        }
        return false;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        if (aSN1Object.isA(ASN.CON_SPEC)) {
            this.a.addElement(new GeneralName(aSN1Object));
            return;
        }
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            this.a.addElement(new GeneralName(aSN1Object.getComponentAt(i)));
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GeneralNames)) {
            return false;
        }
        GeneralNames generalNames = (GeneralNames) obj;
        if (this.a.size() != generalNames.a.size()) {
            return false;
        }
        Enumeration enumerationElements = this.a.elements();
        Enumeration enumerationElements2 = generalNames.a.elements();
        while (enumerationElements.hasMoreElements()) {
            if (!enumerationElements.nextElement().equals(enumerationElements2.nextElement())) {
                return false;
            }
        }
        return true;
    }

    public Enumeration getNames() {
        return this.a.elements();
    }

    public GeneralName[] getNames(int i) {
        Vector vector = new Vector();
        Enumeration names = getNames();
        while (names.hasMoreElements()) {
            GeneralName generalName = (GeneralName) names.nextElement();
            if (generalName.getType() == i) {
                vector.addElement(generalName);
            }
        }
        GeneralName[] generalNameArr = new GeneralName[vector.size()];
        vector.copyInto(generalNameArr);
        return generalNameArr;
    }

    public int hashCode() {
        Enumeration enumerationElements = this.a.elements();
        int iHashCode = 0;
        while (enumerationElements.hasMoreElements()) {
            iHashCode += enumerationElements.nextElement().hashCode();
        }
        return iHashCode;
    }

    public void removeAllNames() {
        this.a.removeAllElements();
    }

    public int size() {
        return this.a.size();
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        for (int i = 0; i < this.a.size(); i++) {
            sequence.addComponent(((GeneralName) this.a.elementAt(i)).toASN1Object());
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a.isEmpty()) {
            stringBuffer.append("empty");
        } else {
            for (int i = 0; i < this.a.size(); i++) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(this.a.elementAt(i));
                stringBuffer2.append("\n");
                stringBuffer.append(stringBuffer2.toString());
            }
            if (stringBuffer.length() >= 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }
}

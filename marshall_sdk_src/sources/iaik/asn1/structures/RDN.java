package iaik.asn1.structures;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SET;
import iaik.utils.RFC2253NameParserException;
import iaik.utils.Util;
import java.util.Enumeration;
import java.util.Vector;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public class RDN implements ASN1Type {
    protected Vector avas;

    public RDN() {
        this.avas = new Vector(3);
    }

    public RDN(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public RDN(ObjectID objectID, Object obj) {
        this();
        addAVA(new AVA(objectID, obj), false);
    }

    public void addAVA(ObjectID objectID, Object obj) {
        addAVA(new AVA(objectID, obj));
    }

    public void addAVA(AVA ava) {
        addAVA(ava, true);
    }

    public void addAVA(AVA ava, boolean z) {
        boolean z2 = false;
        if (z) {
            ObjectID type = ava.getType();
            int size = this.avas.size();
            for (int i = 0; i < size; i++) {
                if (((AVA) this.avas.elementAt(i)).getType().equals(type)) {
                    this.avas.setElementAt(ava, i);
                    break;
                }
            }
            z2 = true;
        } else {
            z2 = true;
        }
        if (z2) {
            this.avas.addElement(ava);
        }
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            addAVA(new AVA(aSN1Object.getComponentAt(i)), false);
        }
    }

    public Enumeration elements() {
        return this.avas.elements();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RDN)) {
            return false;
        }
        RDN rdn = (RDN) obj;
        int size = this.avas.size();
        if (size != rdn.avas.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!rdn.avas.contains((AVA) this.avas.elementAt(i))) {
                return false;
            }
        }
        return true;
    }

    public AVA getAVA() {
        return (AVA) this.avas.elementAt(0);
    }

    public AVA getAVA(ObjectID objectID) {
        Enumeration enumerationElements = this.avas.elements();
        while (enumerationElements.hasMoreElements()) {
            AVA ava = (AVA) enumerationElements.nextElement();
            if (ava.getType().equals(objectID)) {
                return ava;
            }
        }
        return null;
    }

    public AVA[] getAVAs() {
        AVA[] avaArr = new AVA[this.avas.size()];
        this.avas.copyInto(avaArr);
        return avaArr;
    }

    public AVA[] getAVAs(ObjectID objectID) {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.avas.elements();
        while (enumerationElements.hasMoreElements()) {
            AVA ava = (AVA) enumerationElements.nextElement();
            if (ava.getType().equals(objectID)) {
                vector.addElement(ava);
            }
        }
        AVA[] avaArr = new AVA[vector.size()];
        vector.copyInto(avaArr);
        return avaArr;
    }

    public String getRFC2253String() throws RFC2253NameParserException {
        return getRFC2253String(Util.getDefaultRFC2253StringEscaping());
    }

    public String getRFC2253String(boolean z) throws RFC2253NameParserException {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration enumerationElements = this.avas.elements();
        if (!enumerationElements.hasMoreElements()) {
            return stringBuffer.toString();
        }
        String rFC2253String = ((AVA) enumerationElements.nextElement()).getRFC2253String(z);
        while (true) {
            stringBuffer.append(rFC2253String);
            if (!enumerationElements.hasMoreElements()) {
                return stringBuffer.toString();
            }
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(Marker.ANY_NON_NULL_MARKER);
            stringBuffer2.append(((AVA) enumerationElements.nextElement()).getRFC2253String(z));
            rFC2253String = stringBuffer2.toString();
        }
    }

    public int hashCode() {
        int size = this.avas.size();
        Enumeration enumerationElements = this.avas.elements();
        while (enumerationElements.hasMoreElements()) {
            size = ((size >>> 25) | (size << 7)) + ((AVA) enumerationElements.nextElement()).hashCode();
        }
        return size;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        SET set = new SET(true);
        Enumeration enumerationElements = this.avas.elements();
        while (enumerationElements.hasMoreElements()) {
            set.addComponent(((AVA) enumerationElements.nextElement()).toASN1Object());
        }
        return set;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration enumerationElements = this.avas.elements();
        if (!enumerationElements.hasMoreElements()) {
            return stringBuffer.toString();
        }
        String string = ((AVA) enumerationElements.nextElement()).toString(z);
        while (true) {
            stringBuffer.append(string);
            if (!enumerationElements.hasMoreElements()) {
                return stringBuffer.toString();
            }
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(Marker.ANY_NON_NULL_MARKER);
            stringBuffer2.append(((AVA) enumerationElements.nextElement()).toString(z));
            string = stringBuffer2.toString();
        }
    }
}

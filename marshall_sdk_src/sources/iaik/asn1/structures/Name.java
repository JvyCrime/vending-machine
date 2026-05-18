package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.InternalErrorException;
import iaik.utils.RFC2253NameParser;
import iaik.utils.RFC2253NameParserException;
import iaik.utils.Util;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class Name implements ASN1Type, Principal {
    private Vector a;

    public Name() {
        this.a = new Vector();
    }

    public Name(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public Name(String str) throws RFC2253NameParserException {
        this();
        Enumeration enumerationElements = new RFC2253NameParser(str).parse().elements();
        while (enumerationElements.hasMoreElements()) {
            this.a.addElement(enumerationElements.nextElement());
        }
    }

    public Name(byte[] bArr) throws CodingException {
        this(new ASN1(bArr).toASN1Object());
    }

    public void addRDN(ObjectID objectID, Object obj) {
        this.a.addElement(new RDN(objectID, obj));
    }

    public void addRDN(RDN rdn) {
        this.a.addElement(rdn);
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        if (aSN1Object.isA(ASN.SET)) {
            this.a.addElement(new RDN(aSN1Object));
            return;
        }
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            this.a.addElement(new RDN(aSN1Object.getComponentAt(i)));
        }
    }

    public RDN element(ObjectID objectID) {
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            RDN rdn = (RDN) enumerationElements.nextElement();
            if (rdn.getAVA(objectID) != null) {
                return rdn;
            }
        }
        return null;
    }

    public Enumeration elements() {
        return this.a.elements();
    }

    public Enumeration elements(ObjectID objectID) {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            RDN rdn = (RDN) enumerationElements.nextElement();
            if (rdn.getAVA(objectID) != null) {
                vector.addElement(rdn);
            }
        }
        return vector.elements();
    }

    @Override // java.security.Principal
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Name)) {
            return false;
        }
        Name name = (Name) obj;
        int size = this.a.size();
        if (size != name.a.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!((RDN) this.a.elementAt(i)).equals((RDN) name.a.elementAt(i))) {
                return false;
            }
        }
        return true;
    }

    public byte[] getEncoded() {
        try {
            return new ASN1(toASN1Object()).toByteArray();
        } catch (Exception e) {
            throw new InternalErrorException(e.toString());
        }
    }

    @Override // java.security.Principal
    public String getName() {
        try {
            return getRFC2253String(false);
        } catch (Exception unused) {
            return toString();
        }
    }

    public String getRDN(ObjectID objectID) {
        try {
            return getRDN(objectID, false);
        } catch (RFC2253NameParserException unused) {
            return null;
        }
    }

    public String getRDN(ObjectID objectID, boolean z) throws RFC2253NameParserException {
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            AVA ava = ((RDN) enumerationElements.nextElement()).getAVA(objectID);
            if (ava != null) {
                return z ? ava.getRFC2253String() : (String) ava.getValue();
            }
        }
        return null;
    }

    public Object getRDNValue(ObjectID objectID) {
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            AVA ava = ((RDN) enumerationElements.nextElement()).getAVA(objectID);
            if (ava != null) {
                return ava.getValue();
            }
        }
        return null;
    }

    public Object[] getRDNValues(ObjectID objectID) {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            AVA[] aVAs = ((RDN) enumerationElements.nextElement()).getAVAs(objectID);
            if (aVAs.length > 0) {
                for (AVA ava : aVAs) {
                    vector.addElement(ava.getValue());
                }
            }
        }
        if (vector.size() == 0) {
            return null;
        }
        Object[] objArr = new Object[vector.size()];
        vector.copyInto(objArr);
        return objArr;
    }

    public RDN[] getRDNs() {
        RDN[] rdnArr = new RDN[this.a.size()];
        this.a.copyInto(rdnArr);
        return rdnArr;
    }

    public String[] getRDNs(ObjectID objectID) {
        try {
            return getRDNs(objectID, false);
        } catch (RFC2253NameParserException unused) {
            return null;
        }
    }

    public String[] getRDNs(ObjectID objectID, boolean z) throws RFC2253NameParserException {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            AVA[] aVAs = ((RDN) enumerationElements.nextElement()).getAVAs(objectID);
            if (aVAs.length > 0) {
                for (int i = 0; i < aVAs.length; i++) {
                    vector.addElement(z ? aVAs[i].getRFC2253String() : (String) aVAs[i].getValue());
                }
            }
        }
        if (vector.size() == 0) {
            return null;
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    public String getRFC2253String() throws RFC2253NameParserException {
        return getRFC2253String(Util.getDefaultRFC2253StringEscaping());
    }

    public String getRFC2253String(boolean z) throws RFC2253NameParserException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int size = this.a.size() - 1; size >= 0; size--) {
            stringBuffer.append(((RDN) this.a.elementAt(size)).getRFC2253String(z));
            if (size > 0) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    @Override // java.security.Principal
    public int hashCode() {
        int size = this.a.size();
        int iHashCode = size;
        for (int i = 0; i < size; i++) {
            iHashCode = ((iHashCode >>> 25) | (iHashCode << 7)) + ((RDN) this.a.elementAt(i)).hashCode();
        }
        return iHashCode;
    }

    public void insertRDNAt(ObjectID objectID, Object obj, int i) {
        this.a.insertElementAt(new RDN(objectID, obj), i);
    }

    public void insertRDNAt(RDN rdn, int i) {
        this.a.insertElementAt(rdn, i);
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public boolean removeRDN(ObjectID objectID) {
        int i = 0;
        boolean z = false;
        while (i < this.a.size()) {
            if (((RDN) this.a.elementAt(i)).getAVA(objectID) != null) {
                this.a.removeElementAt(i);
                i--;
                z = true;
            }
            i++;
        }
        return z;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        for (int i = 0; i < this.a.size(); i++) {
            sequence.addComponent(((RDN) this.a.elementAt(i)).toASN1Object());
        }
        return sequence;
    }

    @Override // java.security.Principal
    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int size = this.a.size() - 1; size >= 0; size--) {
            stringBuffer.append(((RDN) this.a.elementAt(size)).toString(z));
            if (size > 0) {
                stringBuffer.append(",");
            }
            if (z) {
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }
}

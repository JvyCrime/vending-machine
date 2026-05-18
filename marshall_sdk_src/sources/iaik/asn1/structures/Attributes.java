package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.SET;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class Attributes {
    byte[] a;
    protected Vector attributes_;
    ASN b;

    public Attributes() {
        this.attributes_ = new Vector();
    }

    public Attributes(ASN1Object aSN1Object) throws CodingException {
        this();
        a(aSN1Object);
    }

    public Attributes(InputStream inputStream) throws CodingException, IOException {
        this();
        ASN1 asn1 = new ASN1(inputStream);
        a(asn1.toASN1Object());
        this.a = asn1.toByteArray();
    }

    public Attributes(byte[] bArr) throws CodingException {
        this();
        ASN1 asn1 = new ASN1(bArr);
        a(asn1.toASN1Object());
        this.a = asn1.toByteArray();
    }

    public Attributes(Attribute[] attributeArr) {
        this();
        addAttributes(attributeArr);
    }

    private void a() {
        this.a = null;
        this.b = null;
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        if (!aSN1Object.isConstructed()) {
            throw new CodingException("Cannot parse attributes. Expected SET of SEQUENCE!");
        }
        this.b = aSN1Object.getAsnType();
        for (int i = 0; i < aSN1Object.countComponents(); i++) {
            this.attributes_.addElement(new Attribute(aSN1Object.getComponentAt(i)));
        }
    }

    public void addAttribute(Attribute attribute) {
        addAttribute(attribute, false);
    }

    public void addAttribute(Attribute attribute, boolean z) {
        if (attribute != null) {
            if (z) {
                int i = 0;
                Enumeration enumerationElements = this.attributes_.elements();
                while (true) {
                    if (!enumerationElements.hasMoreElements()) {
                        break;
                    }
                    if (((Attribute) enumerationElements.nextElement()).getType().equals(attribute.getType())) {
                        this.attributes_.setElementAt(attribute, i);
                        break;
                    }
                    i++;
                }
            } else {
                this.attributes_.addElement(attribute);
            }
            a();
        }
    }

    public void addAttributes(Attribute[] attributeArr) {
        if (attributeArr != null) {
            int size = this.attributes_.size();
            for (int i = 0; i < attributeArr.length; i++) {
                if (attributeArr[i] != null) {
                    this.attributes_.addElement(attributeArr[i]);
                }
            }
            if (this.attributes_.size() > size) {
                a();
            }
        }
    }

    public void clearAll() {
        this.attributes_.removeAllElements();
        a();
    }

    public Attribute getAttribute(ObjectID objectID) {
        Enumeration enumerationElements = this.attributes_.elements();
        while (enumerationElements.hasMoreElements()) {
            Attribute attribute = (Attribute) enumerationElements.nextElement();
            if (attribute.getType().equals(objectID)) {
                return attribute;
            }
        }
        return null;
    }

    public Enumeration getAttributes() {
        return this.attributes_.elements();
    }

    public Attribute[] getAttributes(ObjectID objectID) {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.attributes_.elements();
        while (enumerationElements.hasMoreElements()) {
            Attribute attribute = (Attribute) enumerationElements.nextElement();
            if (attribute.getType().equals(objectID)) {
                vector.addElement(attribute);
            }
        }
        Attribute[] attributeArr = new Attribute[vector.size()];
        vector.copyInto(attributeArr);
        return attributeArr;
    }

    public void insertAttributeAt(Attribute attribute, int i) {
        this.attributes_.insertElementAt(attribute, i);
        a();
    }

    public Attribute removeAttribute(ObjectID objectID) {
        Enumeration enumerationElements = this.attributes_.elements();
        while (enumerationElements.hasMoreElements()) {
            Attribute attribute = (Attribute) enumerationElements.nextElement();
            if (attribute.getType().equals(objectID)) {
                this.attributes_.removeElement(attribute);
                a();
                return attribute;
            }
        }
        return null;
    }

    public boolean removeAttribute(Attribute attribute) {
        return this.attributes_.removeElement(attribute);
    }

    public boolean removeAttributes(Attribute attribute) {
        boolean zRemoveElement;
        do {
            zRemoveElement = this.attributes_.removeElement(attribute);
        } while (zRemoveElement);
        return zRemoveElement;
    }

    public Attribute[] removeAttributes(ObjectID objectID) {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.attributes_.elements();
        while (enumerationElements.hasMoreElements()) {
            Attribute attribute = (Attribute) enumerationElements.nextElement();
            if (attribute.getType().equals(objectID)) {
                vector.addElement(attribute);
                this.attributes_.removeElement(attribute);
                a();
            }
        }
        Attribute[] attributeArr = new Attribute[vector.size()];
        vector.copyInto(attributeArr);
        return attributeArr;
    }

    public int size() {
        return this.attributes_.size();
    }

    public Attribute[] toArray() {
        Attribute[] attributeArr = new Attribute[this.attributes_.size()];
        this.attributes_.copyInto(attributeArr);
        return attributeArr;
    }

    public SEQUENCE toAsn1SEQUENCE() throws CodingException {
        return (SEQUENCE) ASN.createSequenceOf(toArray());
    }

    public SET toAsn1SET() throws CodingException {
        return toAsn1SET(false);
    }

    public SET toAsn1SET(boolean z) throws CodingException {
        return (SET) ASN.createSetOf(toArray(), z);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration enumerationElements = this.attributes_.elements();
        int i = 1;
        while (enumerationElements.hasMoreElements()) {
            if (i > 1) {
                stringBuffer.append("\n");
            }
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Attribute No. ");
            stringBuffer2.append(i);
            stringBuffer2.append(": ");
            stringBuffer.append(stringBuffer2.toString());
            stringBuffer.append(enumerationElements.nextElement());
            i++;
        }
        return stringBuffer.toString();
    }

    public void writeToAsn1SEQUENCE(OutputStream outputStream) throws IOException {
        try {
            if (this.a == null || !this.b.equals(ASN.SEQUENCE)) {
                DerCoder.encodeTo(toAsn1SEQUENCE(), outputStream);
            } else {
                outputStream.write(this.a);
            }
        } catch (CodingException e) {
            throw new IOException(e.toString());
        }
    }

    public void writeToAsn1SET(OutputStream outputStream) throws IOException {
        try {
            if (this.a == null || !this.b.equals(ASN.SET)) {
                DerCoder.encodeTo(toAsn1SET(false), outputStream);
            } else {
                outputStream.write(this.a);
            }
        } catch (CodingException e) {
            throw new IOException(e.toString());
        }
    }

    public void writeToAsn1SET(OutputStream outputStream, boolean z) throws IOException {
        try {
            DerCoder.encodeTo(toAsn1SET(z), outputStream);
        } catch (CodingException e) {
            throw new IOException(e.toString());
        }
    }
}

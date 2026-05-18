package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.AccessDescription;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public abstract class InfoAccess extends V3Extension {
    static Class a;
    private Vector b;

    public InfoAccess() {
        this.b = new Vector();
    }

    public InfoAccess(AccessDescription accessDescription) throws IllegalArgumentException {
        this();
        if (accessDescription == null) {
            throw new IllegalArgumentException("Cannot create a AuthorityInfoAccess from null accessDescription!");
        }
        this.b.addElement(accessDescription);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public void addAccessDescription(AccessDescription accessDescription) throws IllegalArgumentException {
        if (accessDescription == null) {
            throw new IllegalArgumentException("Cannot add a null accessDescription!");
        }
        this.b.addElement(accessDescription);
    }

    public AccessDescription getAccessDescription(ObjectID objectID) {
        AccessDescription accessDescription;
        Enumeration enumerationElements = this.b.elements();
        do {
            accessDescription = null;
            if (!enumerationElements.hasMoreElements()) {
                break;
            }
            accessDescription = (AccessDescription) enumerationElements.nextElement();
        } while (!accessDescription.getAccessMethod().equals(objectID));
        return accessDescription;
    }

    public Enumeration getAccessDescriptions() {
        return this.b.elements();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        this.b = new Vector();
        try {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.asn1.structures.AccessDescription");
                a = clsClass$;
            }
            AccessDescription[] accessDescriptionArr = (AccessDescription[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
            for (AccessDescription accessDescription : accessDescriptionArr) {
                this.b.addElement(accessDescription);
            }
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        } catch (Exception e2) {
            throw new X509ExtensionException(e2.toString());
        }
    }

    public void removeAllAccessDescriptions() {
        this.b.removeAllElements();
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        try {
            return ASN.createSequenceOf(this.b);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.b.size(); i++) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(this.b.elementAt(i).toString());
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (stringBuffer.length() >= 1) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}

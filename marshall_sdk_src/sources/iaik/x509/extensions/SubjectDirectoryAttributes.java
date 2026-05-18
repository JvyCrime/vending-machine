package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.Attribute;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;

/* JADX INFO: loaded from: classes2.dex */
public class SubjectDirectoryAttributes extends V3Extension {
    static Class b;
    public static final ObjectID oid = ObjectID.certExt_SubjectDirectoryAttributes;
    Attribute[] a;

    public SubjectDirectoryAttributes() {
    }

    public SubjectDirectoryAttributes(Attribute[] attributeArr) {
        this.a = attributeArr;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public Attribute getAttribute(ObjectID objectID) {
        if (this.a == null) {
            return null;
        }
        int i = 0;
        while (true) {
            Attribute[] attributeArr = this.a;
            if (i >= attributeArr.length) {
                return null;
            }
            if (attributeArr[i].getType().equals(objectID)) {
                return this.a[i];
            }
            i++;
        }
    }

    public Attribute[] getAttributes() {
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
        try {
            Class clsClass$ = b;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.asn1.structures.Attribute");
                b = clsClass$;
            }
            Attribute[] attributeArr = (Attribute[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
            this.a = attributeArr;
            if (attributeArr == null || attributeArr.length == 0) {
                throw new X509ExtensionException("Invalid SubjectDirectoryAttributes extension: no attributes!");
            }
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public void setAttributes(Attribute[] attributeArr) {
        this.a = attributeArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        Attribute[] attributeArr = this.a;
        if (attributeArr == null || attributeArr.length == 0) {
            throw new X509ExtensionException("Cannot create SubjectDirectoryAttributes extension with no attributes!");
        }
        try {
            return ASN.createSequenceOf(attributeArr);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            int i = 0;
            while (true) {
                Attribute[] attributeArr = this.a;
                if (i >= attributeArr.length) {
                    break;
                }
                stringBuffer.append(attributeArr[i]);
                i++;
            }
            if (stringBuffer.length() >= 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }
}

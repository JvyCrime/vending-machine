package iaik.pkcs.pkcs12;

import iaik.asn1.ASN1Object;
import iaik.asn1.BMPString;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.Attribute;
import iaik.utils.Util;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class Attributes {
    String a;
    byte[] b;

    public Attributes() {
    }

    public Attributes(String str, byte[] bArr) {
        this.a = str;
        this.b = bArr;
    }

    public Attribute[] getAttributes() {
        Vector vector = new Vector();
        if (this.a != null) {
            vector.addElement(new Attribute(ObjectID.friendlyName, new ASN1Object[]{new BMPString(this.a)}));
        }
        if (this.b != null) {
            vector.addElement(new Attribute(ObjectID.localKeyID, new ASN1Object[]{new OCTET_STRING(this.b)}));
        }
        if (vector.size() == 0) {
            return null;
        }
        int size = vector.size();
        Attribute[] attributeArr = new Attribute[size];
        for (int i = 0; i < size; i++) {
            attributeArr[i] = (Attribute) vector.elementAt(i);
        }
        return attributeArr;
    }

    public String getFriendlyName() {
        return this.a;
    }

    public byte[] getLocalKeyID() {
        return this.b;
    }

    protected void setAttributes(Attribute[] attributeArr) {
        if (attributeArr != null) {
            for (int i = 0; i < attributeArr.length; i++) {
                if (attributeArr[i].getType().equals(ObjectID.friendlyName)) {
                    this.a = (String) attributeArr[i].getValue()[0].getValue();
                } else if (attributeArr[i].getType().equals(ObjectID.localKeyID)) {
                    this.b = (byte[]) attributeArr[i].getValue()[0].getValue();
                }
            }
        }
    }

    public void setFriendlyName(String str) {
        this.a = str;
    }

    public void setLocalKeyID(byte[] bArr) {
        this.b = bArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Friendly name: ");
            stringBuffer2.append(this.a);
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("local key ID: ");
            stringBuffer3.append(Util.toString(this.b));
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        return stringBuffer.toString();
    }
}

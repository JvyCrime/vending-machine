package iaik.x509.extensions.qualified;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.extensions.qualified.structures.BiometricData;

/* JADX INFO: loaded from: classes2.dex */
public class BiometricInfo extends V3Extension {
    static Class b;
    public static final ObjectID oid = ObjectID.certExt_BiometricInfo;
    BiometricData[] a;

    public BiometricInfo() {
    }

    public BiometricInfo(BiometricData[] biometricDataArr) {
        this.a = biometricDataArr;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public BiometricData[] getBiometricDatas() {
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
                clsClass$ = class$("iaik.x509.extensions.qualified.structures.BiometricData");
                b = clsClass$;
            }
            this.a = (BiometricData[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public void setBiometricDatas(BiometricData[] biometricDataArr) {
        this.a = biometricDataArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        try {
            return ASN.createSequenceOf(this.a);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.a != null) {
            int i = 0;
            while (true) {
                BiometricData[] biometricDataArr = this.a;
                if (i >= biometricDataArr.length) {
                    break;
                }
                stringBuffer.append(biometricDataArr[i]);
                i++;
            }
            if (stringBuffer.length() >= 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }
}

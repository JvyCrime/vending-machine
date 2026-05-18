package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;

/* JADX INFO: loaded from: classes2.dex */
public class KeyUsage extends V3Extension {
    public static final int cRLSign = 64;
    public static final int contentCommitment = 2;
    public static final int dataEncipherment = 8;
    public static final int decipherOnly = 256;
    public static final int digitalSignature = 1;
    public static final int encipherOnly = 128;
    public static final int keyAgreement = 16;
    public static final int keyCertSign = 32;
    public static final int keyEncipherment = 4;
    public static final int nonRepudiation = 2;
    public static final ObjectID oid = ObjectID.certExt_KeyUsage;
    private int a;

    public KeyUsage() {
        this.a = 0;
    }

    public KeyUsage(int i) {
        this.a = 0;
        this.a = i;
    }

    public int get() {
        return this.a;
    }

    public boolean[] getBooleanArray() {
        boolean[] zArr = new boolean[9];
        int i = 0;
        for (int i2 = this.a; i2 != 0; i2 >>= 1) {
            if ((i2 & 1) != 0) {
                zArr[i] = true;
            }
            i++;
        }
        return zArr;
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
    public void init(ASN1Object aSN1Object) {
        this.a = Integer.parseInt(new StringBuffer(((BIT_STRING) aSN1Object).getBinaryString()).reverse().toString(), 2);
    }

    public boolean isSet(int i) {
        return (i & this.a) != 0;
    }

    public void set(int i) {
        this.a = i;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        StringBuffer stringBuffer = new StringBuffer(Integer.toBinaryString(this.a));
        stringBuffer.reverse();
        return new BIT_STRING(stringBuffer.toString());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if ((this.a & 1) != 0) {
            stringBuffer.append("digitalSignature | ");
        }
        if ((this.a & 2) != 0) {
            stringBuffer.append("nonRepudiation | ");
        }
        if ((this.a & 4) != 0) {
            stringBuffer.append("keyEncipherment | ");
        }
        if ((this.a & 8) != 0) {
            stringBuffer.append("dataEncipherment | ");
        }
        if ((this.a & 16) != 0) {
            stringBuffer.append("keyAgreement | ");
        }
        if ((this.a & 32) != 0) {
            stringBuffer.append("keyCertSign | ");
        }
        if ((this.a & 64) != 0) {
            stringBuffer.append("cRLSign | ");
        }
        if ((this.a & 128) != 0) {
            stringBuffer.append("encipherOnly | ");
        }
        if ((this.a & 256) != 0) {
            stringBuffer.append("decipherOnly | ");
        }
        if (stringBuffer.length() > 3) {
            stringBuffer.setLength(stringBuffer.length() - 3);
        }
        return stringBuffer.toString();
    }
}

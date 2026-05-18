package iaik.x509.extensions.netscape;

import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;

/* JADX INFO: loaded from: classes2.dex */
public class NetscapeCertType extends V3Extension {
    public static final int OBJECT_SIGNING = 16;
    public static final int OBJECT_SIGNING_CA = 1;
    public static final int SSL_CA = 4;
    public static final int SSL_CLIENT = 128;
    public static final int SSL_SERVER = 64;
    public static final int S_MIME = 32;
    public static final int S_MIME_CA = 2;
    public static final ObjectID oid = ObjectID.certExt_NetscapeCertType;
    private int a;

    public NetscapeCertType() {
        this.a = 0;
    }

    public NetscapeCertType(int i) {
        this.a = 0;
        this.a = i;
    }

    public int getCertType() {
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
    public void init(ASN1Object aSN1Object) {
        this.a = ((byte[]) ((BIT_STRING) aSN1Object).getValue())[0];
    }

    public void setCertType(int i) {
        this.a = i;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        byte[] bArr = {(byte) this.a};
        int i = 0;
        for (int i2 = 0; i2 < 8 && (bArr[0] & (1 << i2)) == 0; i2++) {
            i++;
        }
        return new BIT_STRING(bArr, i);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("NetscapeCertType:");
        if ((this.a & 128) > 0) {
            stringBuffer.append(" SSL Client |");
        }
        if ((this.a & 64) > 0) {
            stringBuffer.append(" SSL Server |");
        }
        if ((this.a & 32) > 0) {
            stringBuffer.append(" S/MIME |");
        }
        if ((this.a & 16) > 0) {
            stringBuffer.append(" Object Signing |");
        }
        if ((this.a & 4) > 0) {
            stringBuffer.append(" SSL CA |");
        }
        if ((this.a & 2) > 0) {
            stringBuffer.append(" S/MIME CA |");
        }
        if ((this.a & 1) > 0) {
            stringBuffer.append(" Object Signing CA |");
        }
        if (stringBuffer.charAt(stringBuffer.length() - 1) == '|') {
            stringBuffer.setLength(stringBuffer.length() - 2);
        }
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}

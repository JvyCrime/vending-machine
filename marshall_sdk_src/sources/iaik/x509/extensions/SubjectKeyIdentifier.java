package iaik.x509.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/* JADX INFO: loaded from: classes2.dex */
public class SubjectKeyIdentifier extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_SubjectKeyIdentifier;
    private byte[] a;

    public SubjectKeyIdentifier() {
    }

    public SubjectKeyIdentifier(PublicKey publicKey) throws NoSuchAlgorithmException, CodingException {
        try {
            byte[] bArr = (byte[]) ((BIT_STRING) DerCoder.decode(publicKey.getEncoded()).getComponentAt(1)).getValue();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(bArr);
            this.a = messageDigest.digest();
        } catch (Exception e) {
            throw new CodingException(e.getMessage());
        }
    }

    public SubjectKeyIdentifier(byte[] bArr) {
        this.a = bArr;
    }

    public byte[] get() {
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
        this.a = (byte[]) aSN1Object.getValue();
    }

    public void set(byte[] bArr) {
        this.a = bArr;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        try {
            return ASN.create(ASN.OCTET_STRING, this.a);
        } catch (InstantiationException unused) {
            return null;
        }
    }

    public String toString() {
        byte[] bArr = this.a;
        return bArr != null ? Util.toString(bArr) : "";
    }
}

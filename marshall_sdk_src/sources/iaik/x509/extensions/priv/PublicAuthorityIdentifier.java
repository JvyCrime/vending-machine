package iaik.x509.extensions.priv;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1String;
import iaik.asn1.ObjectID;
import iaik.asn1.UTF8String;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class PublicAuthorityIdentifier extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_PublicAuthorityIdentifier;
    private ASN1Object a;
    protected ASN1String code_;

    public PublicAuthorityIdentifier() {
        this.code_ = null;
        this.a = null;
    }

    public PublicAuthorityIdentifier(ASN1String aSN1String) {
        if (aSN1String == null) {
            throw new IllegalArgumentException("code must not be null!");
        }
        if (!aSN1String.isA(ASN.T61String) && !aSN1String.isA(ASN.PrintableString) && !aSN1String.isA(ASN.UNIString) && !aSN1String.isA(ASN.UTF8String) && !aSN1String.isA(ASN.BMPString)) {
            throw new IllegalArgumentException("PublicAuthorityIdentifier code must be TeletexString, PrintableString, UniversalString, UTF8String or BMPString!");
        }
        this.code_ = aSN1String;
        this.a = aSN1String;
    }

    public PublicAuthorityIdentifier(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("code must not be null!");
        }
        UTF8String uTF8String = new UTF8String(str);
        this.code_ = uTF8String;
        this.a = uTF8String;
    }

    public boolean equals(Object obj) {
        ASN1String aSN1String;
        if (this == obj) {
            return true;
        }
        if (obj instanceof PublicAuthorityIdentifier) {
            PublicAuthorityIdentifier publicAuthorityIdentifier = (PublicAuthorityIdentifier) obj;
            ASN1String aSN1String2 = this.code_;
            if (aSN1String2 != null && (aSN1String = publicAuthorityIdentifier.code_) != null) {
                return aSN1String2.equals(aSN1String);
            }
            if (aSN1String2 == null && publicAuthorityIdentifier.code_ == null) {
                return true;
            }
        }
        return false;
    }

    public ASN1String getCode() {
        return this.code_;
    }

    public String getCodeString() {
        ASN1String aSN1String = this.code_;
        if (aSN1String != null) {
            return (String) aSN1String.getValue();
        }
        return null;
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
        if (aSN1Object.isA(ASN.BOOLEAN)) {
            if (!((Boolean) aSN1Object.getValue()).booleanValue()) {
                throw new X509ExtensionException("Boolean value in PublicAuthorityIdentifier extension must be true but is false!");
            }
        } else {
            if (!aSN1Object.isStringType()) {
                throw new X509ExtensionException("Value in PublicAuthorityIdentifier extension must be of type BOOLEAN, TeletexString, PrintableString, UniversalString, UTF8String or BMPString!");
            }
            this.code_ = (ASN1String) aSN1Object;
        }
        this.a = aSN1Object;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        ASN1Object aSN1Object = this.a;
        Objects.requireNonNull(aSN1Object, "PublicAuthorityIdentifier code must not be null!");
        return aSN1Object;
    }

    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.code_ == null) {
            if (this.a != null) {
                string = "isPublicAuthority: true";
            }
            return stringBuffer.toString();
        }
        stringBuffer.append("code: ");
        string = this.code_.toString();
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

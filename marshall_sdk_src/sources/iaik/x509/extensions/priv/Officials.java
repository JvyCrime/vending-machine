package iaik.x509.extensions.priv;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;
import iaik.asn1.UTF8String;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class Officials extends V3Extension {
    public static final ObjectID oid = new ObjectID("1.2.40.0.10.3.4", "Officials", null, false);
    protected UTF8String administrationIndicator_;

    public Officials() {
        this.administrationIndicator_ = null;
    }

    public Officials(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("administration indicator must not be null!");
        }
        this.administrationIndicator_ = new UTF8String(str);
    }

    public boolean equals(Object obj) {
        UTF8String uTF8String;
        if (this == obj) {
            return true;
        }
        if (obj instanceof Officials) {
            Officials officials = (Officials) obj;
            UTF8String uTF8String2 = this.administrationIndicator_;
            if (uTF8String2 != null && (uTF8String = officials.administrationIndicator_) != null) {
                return uTF8String2.equals(uTF8String);
            }
            if (uTF8String2 == null && officials.administrationIndicator_ == null) {
                return true;
            }
        }
        return false;
    }

    public String getAdministrationIndicator() {
        UTF8String uTF8String = this.administrationIndicator_;
        if (uTF8String != null) {
            return (String) uTF8String.getValue();
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
        if (!aSN1Object.isA(ASN.UTF8String)) {
            throw new X509ExtensionException("Officials adminstration indicator must be an UTF8String!");
        }
        this.administrationIndicator_ = (UTF8String) aSN1Object;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        UTF8String uTF8String = this.administrationIndicator_;
        Objects.requireNonNull(uTF8String, "Cannot encode Officials extension with null administration indicator");
        return uTF8String;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.administrationIndicator_ != null) {
            stringBuffer.append("administrationIndicator: ");
            stringBuffer.append(this.administrationIndicator_.toString());
        }
        return stringBuffer.toString();
    }
}

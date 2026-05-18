package iaik.x509.attr;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509Extensions;
import iaik.x509.attr.extensions.AcceptableCertPolicies;
import iaik.x509.attr.extensions.AuditIdentity;
import iaik.x509.attr.extensions.BasicAttConstraints;
import iaik.x509.attr.extensions.NoRevAvail;
import iaik.x509.attr.extensions.ProxyInfo;
import iaik.x509.attr.extensions.TargetInformation;

/* JADX INFO: loaded from: classes2.dex */
public class AttributeCertificateExtensions extends X509Extensions {
    static Class P;
    static Class Q;
    static Class R;
    static Class S;
    static Class T;
    static Class U;

    static {
        ObjectID objectID = AuditIdentity.oid;
        Class clsClass$ = P;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.attr.extensions.AuditIdentity");
            P = clsClass$;
        }
        register(objectID, clsClass$);
        ObjectID objectID2 = NoRevAvail.oid;
        Class clsClass$2 = Q;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.x509.attr.extensions.NoRevAvail");
            Q = clsClass$2;
        }
        register(objectID2, clsClass$2);
        ObjectID objectID3 = TargetInformation.oid;
        Class clsClass$3 = R;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.x509.attr.extensions.TargetInformation");
            R = clsClass$3;
        }
        register(objectID3, clsClass$3);
        ObjectID objectID4 = ProxyInfo.oid;
        Class clsClass$4 = S;
        if (clsClass$4 == null) {
            clsClass$4 = class$("iaik.x509.attr.extensions.ProxyInfo");
            S = clsClass$4;
        }
        register(objectID4, clsClass$4);
        ObjectID objectID5 = BasicAttConstraints.oid;
        Class clsClass$5 = T;
        if (clsClass$5 == null) {
            clsClass$5 = class$("iaik.x509.attr.extensions.BasicAttConstraints");
            T = clsClass$5;
        }
        register(objectID5, clsClass$5);
        ObjectID objectID6 = AcceptableCertPolicies.oid;
        Class clsClass$6 = U;
        if (clsClass$6 == null) {
            clsClass$6 = class$("iaik.x509.attr.extensions.AcceptableCertPolicies");
            U = clsClass$6;
        }
        register(objectID6, clsClass$6);
    }

    public AttributeCertificateExtensions() {
        super(6, 6);
    }

    public AttributeCertificateExtensions(int i, int i2) {
        super(i, i2);
    }

    public AttributeCertificateExtensions(ASN1Object aSN1Object) throws X509ExtensionException {
        this();
        decode(aSN1Object);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }
}

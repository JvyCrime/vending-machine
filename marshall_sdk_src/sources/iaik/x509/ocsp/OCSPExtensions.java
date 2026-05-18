package iaik.x509.ocsp;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.x509.UnknownExtension;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.X509Extensions;
import iaik.x509.ocsp.extensions.AcceptableResponses;
import iaik.x509.ocsp.extensions.ArchiveCutoff;
import iaik.x509.ocsp.extensions.CrlID;
import iaik.x509.ocsp.extensions.ExtendedRevoked;
import iaik.x509.ocsp.extensions.Nonce;
import iaik.x509.ocsp.extensions.PreferredSignatureAlgorithms;
import iaik.x509.ocsp.extensions.ServiceLocator;
import iaik.x509.ocsp.extensions.commonpki.CertHash;

/* JADX INFO: loaded from: classes2.dex */
public class OCSPExtensions extends X509Extensions {
    static Class P;
    static Class Q;
    static Class R;
    static Class S;
    static Class T;
    static Class U;
    static Class V;
    static Class W;

    static {
        ObjectID objectID = AcceptableResponses.oid;
        Class clsClass$ = P;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.ocsp.extensions.AcceptableResponses");
            P = clsClass$;
        }
        register(objectID, clsClass$);
        ObjectID objectID2 = ArchiveCutoff.oid;
        Class clsClass$2 = Q;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.x509.ocsp.extensions.ArchiveCutoff");
            Q = clsClass$2;
        }
        register(objectID2, clsClass$2);
        ObjectID objectID3 = CrlID.oid;
        Class clsClass$3 = R;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.x509.ocsp.extensions.CrlID");
            R = clsClass$3;
        }
        register(objectID3, clsClass$3);
        ObjectID objectID4 = Nonce.oid;
        Class clsClass$4 = S;
        if (clsClass$4 == null) {
            clsClass$4 = class$("iaik.x509.ocsp.extensions.Nonce");
            S = clsClass$4;
        }
        register(objectID4, clsClass$4);
        ObjectID objectID5 = ServiceLocator.oid;
        Class clsClass$5 = T;
        if (clsClass$5 == null) {
            clsClass$5 = class$("iaik.x509.ocsp.extensions.ServiceLocator");
            T = clsClass$5;
        }
        register(objectID5, clsClass$5);
        ObjectID objectID6 = CertHash.oid;
        Class clsClass$6 = U;
        if (clsClass$6 == null) {
            clsClass$6 = class$("iaik.x509.ocsp.extensions.commonpki.CertHash");
            U = clsClass$6;
        }
        register(objectID6, clsClass$6);
        ObjectID objectID7 = ExtendedRevoked.oid;
        Class clsClass$7 = V;
        if (clsClass$7 == null) {
            clsClass$7 = class$("iaik.x509.ocsp.extensions.ExtendedRevoked");
            V = clsClass$7;
        }
        register(objectID7, clsClass$7);
        ObjectID objectID8 = PreferredSignatureAlgorithms.oid;
        Class clsClass$8 = W;
        if (clsClass$8 == null) {
            clsClass$8 = class$("iaik.x509.ocsp.extensions.PreferredSignatureAlgorithms");
            W = clsClass$8;
        }
        register(objectID8, clsClass$8);
    }

    public OCSPExtensions() {
        super(2, 4);
    }

    public OCSPExtensions(int i, int i2) {
        super(i, i2);
    }

    public OCSPExtensions(ASN1Object aSN1Object) throws X509ExtensionException {
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

    @Override // iaik.x509.X509Extensions
    public boolean addExtension(V3Extension v3Extension) throws X509ExtensionException {
        byte[] bArrEncode = v3Extension.toASN1Object() == null ? new byte[0] : (!v3Extension.getObjectID().equals(Nonce.oid) || Nonce.getWrapNonceValue()) ? DerCoder.encode(v3Extension.toASN1Object()) : ((Nonce) v3Extension).getValue();
        if (v3Extension.isCritical()) {
            createExtensionsTable(true);
            return this.critical_extensions.put(v3Extension.getObjectID(), bArrEncode) != null;
        }
        createExtensionsTable(false);
        return this.noncritical_extensions.put(v3Extension.getObjectID(), bArrEncode) != null;
    }

    @Override // iaik.x509.X509Extensions
    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        boolean z;
        V3Extension unknownExtension;
        ASN1Object aSN1ObjectDecode = null;
        byte[] bArr = this.critical_extensions != null ? (byte[]) this.critical_extensions.get(objectID) : null;
        if (bArr == null) {
            if (this.noncritical_extensions != null) {
                bArr = (byte[]) this.noncritical_extensions.get(objectID);
            }
            if (bArr == null) {
                return null;
            }
            z = false;
        } else {
            z = true;
        }
        try {
            unknownExtension = X509Extensions.create(objectID);
        } catch (InstantiationException unused) {
            unknownExtension = new UnknownExtension(objectID);
        }
        unknownExtension.setCritical(z);
        try {
            try {
                if (objectID.equals(Nonce.oid)) {
                    if (bArr[0] == 4) {
                        try {
                            aSN1ObjectDecode = DerCoder.decode(bArr);
                        } catch (CodingException unused2) {
                        }
                    }
                    if (aSN1ObjectDecode == null) {
                        aSN1ObjectDecode = new OCTET_STRING(bArr);
                    }
                } else {
                    aSN1ObjectDecode = DerCoder.decode(bArr);
                }
                unknownExtension.init(aSN1ObjectDecode);
                return unknownExtension;
            } catch (CodingException e) {
                throw new X509ExtensionInitException(objectID, z, e.toString());
            }
        } catch (X509ExtensionException e2) {
            throw new X509ExtensionInitException(objectID, z, e2.toString());
        }
    }
}

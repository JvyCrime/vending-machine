package iaik.x509.attr;

import iaik.asn1.ASN1Object;
import iaik.x509.RevokedCertificate;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public class RevokedAttributeCertificate extends RevokedCertificate {
    public RevokedAttributeCertificate(ASN1Object aSN1Object) throws CRLException {
        super(aSN1Object);
    }

    public RevokedAttributeCertificate(AttributeCertificate attributeCertificate, Date date) {
        super(attributeCertificate.getSerialNumber(), date);
    }

    public RevokedAttributeCertificate(BigInteger bigInteger, Date date) {
        super(bigInteger, date);
    }
}

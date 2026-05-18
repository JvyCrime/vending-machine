package iaik.x509.ocsp;

import androidx.core.os.EnvironmentCompat;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.NULL;
import iaik.utils.InternalErrorException;

/* JADX INFO: loaded from: classes2.dex */
public class CertStatus {
    public static final int GOOD = 0;
    public static final int REVOKED = 1;
    public static final int UNKNOWN = 2;
    int a;
    RevokedInfo b;
    UnknownInfo c;

    public CertStatus() {
        this.a = 0;
    }

    public CertStatus(ASN1Object aSN1Object) throws CodingException {
        a(aSN1Object);
    }

    public CertStatus(RevokedInfo revokedInfo) {
        if (revokedInfo == null) {
            throw new IllegalArgumentException("Cannot create revoked CertStatus. Missing RevokedInfo!");
        }
        this.a = 1;
        this.b = revokedInfo;
    }

    public CertStatus(UnknownInfo unknownInfo) {
        this.a = 2;
        this.c = unknownInfo == null ? new UnknownInfo() : unknownInfo;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void a(iaik.asn1.ASN1Object r5) throws iaik.asn1.CodingException {
        /*
            r4 = this;
            iaik.asn1.CON_SPEC r5 = (iaik.asn1.CON_SPEC) r5
            iaik.asn1.ASN r0 = r5.getAsnType()
            int r0 = r0.getTag()
            r4.a = r0
            if (r0 == 0) goto L82
            r1 = 1
            if (r0 == r1) goto L70
            r2 = 2
            if (r0 != r2) goto L57
            int r0 = r5.countComponents()
            r2 = 0
            if (r0 != r1) goto L2c
            iaik.asn1.ASN1Object r0 = r5.getComponentAt(r2)
            iaik.asn1.ASN r0 = r0.getAsnType()
            iaik.asn1.ASN r3 = iaik.asn1.ASN.ENUMERATED
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L41
            goto L42
        L2c:
            java.lang.Object r0 = r5.getValue()
            boolean r3 = r0 instanceof byte[]
            if (r3 == 0) goto L41
            byte[] r0 = (byte[]) r0
            byte[] r0 = (byte[]) r0
            int r0 = r0.length
            if (r0 <= 0) goto L41
            iaik.asn1.ASN r0 = iaik.asn1.ASN.ENUMERATED
            r5.forceImplicitlyTagged(r0)
            goto L42
        L41:
            r1 = 0
        L42:
            if (r1 != 0) goto L49
            iaik.asn1.ASN r0 = iaik.asn1.ASN.NULL
            r5.forceImplicitlyTagged(r0)
        L49:
            iaik.x509.ocsp.UnknownInfo r0 = new iaik.x509.ocsp.UnknownInfo
            java.lang.Object r5 = r5.getValue()
            iaik.asn1.ASN1Object r5 = (iaik.asn1.ASN1Object) r5
            r0.<init>(r5)
            r4.c = r0
            goto L82
        L57:
            iaik.asn1.CodingException r5 = new iaik.asn1.CodingException
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "Unsupported cert status. Invalid tag:"
            r0.append(r1)
            int r1 = r4.a
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L70:
            iaik.asn1.ASN r0 = iaik.asn1.ASN.SEQUENCE
            r5.forceImplicitlyTagged(r0)
            iaik.x509.ocsp.RevokedInfo r0 = new iaik.x509.ocsp.RevokedInfo
            java.lang.Object r5 = r5.getValue()
            iaik.asn1.ASN1Object r5 = (iaik.asn1.ASN1Object) r5
            r0.<init>(r5)
            r4.b = r0
        L82:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.x509.ocsp.CertStatus.a(iaik.asn1.ASN1Object):void");
    }

    public int getCertStatus() {
        return this.a;
    }

    public String getCertStatusName() {
        int i = this.a;
        if (i == 0) {
            return "good";
        }
        if (i == 1) {
            return "revoked";
        }
        if (i == 2) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("undefined cert status: ");
        stringBuffer.append(this.a);
        return stringBuffer.toString();
    }

    public RevokedInfo getRevokedInfo() {
        return this.b;
    }

    public UnknownInfo getUnknownInfo() {
        return this.c;
    }

    public ASN1Object toASN1Object() {
        int i = this.a;
        if (i == 0) {
            return new CON_SPEC(0, new NULL(), true);
        }
        if (i == 1) {
            return new CON_SPEC(1, this.b.toASN1Object(), true);
        }
        if (i != 2) {
            return null;
        }
        return new CON_SPEC(2, this.c.toASN1Object(), true);
    }

    public String toString() {
        StringBuffer stringBuffer;
        Object obj;
        int i = this.a;
        if (i == 0) {
            return "good";
        }
        if (i == 1) {
            stringBuffer = new StringBuffer();
            stringBuffer.append("Revoked: ");
            obj = this.b;
        } else {
            if (i != 2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Unsupported cert status: ");
                stringBuffer2.append(this.a);
                throw new InternalErrorException(stringBuffer2.toString());
            }
            stringBuffer = new StringBuffer();
            stringBuffer.append("unknown ");
            obj = this.c;
        }
        stringBuffer.append(obj);
        return stringBuffer.toString();
    }
}

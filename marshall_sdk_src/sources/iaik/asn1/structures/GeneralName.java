package iaik.asn1.structures;

import androidx.core.view.MotionEventCompat;
import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.StringTokenizer;

/* JADX INFO: loaded from: classes.dex */
public class GeneralName {
    public static final int dNSName = 2;
    public static final int directoryName = 4;
    public static final int ediPartyName = 5;
    public static final int iPAddress = 7;
    public static final int otherName = 0;
    public static final int registeredID = 8;
    public static final int rfc822Name = 1;
    public static final int uniformResourceIdentifier = 6;
    public static final int x400Address = 3;
    private int a;
    private Object b;
    private int c;

    public GeneralName(int i, Object obj) throws IllegalArgumentException {
        this.a = -1;
        this.c = -1;
        switch (i) {
            case 0:
                if (!(obj instanceof ASN1Object) && !(obj instanceof OtherName)) {
                    throw new IllegalArgumentException("Cannot create GeneralName! Expecting ASN1Object for type otherName!");
                }
                break;
            case 1:
            case 2:
            case 6:
                if (!(obj instanceof String)) {
                    throw new IllegalArgumentException("Cannot create GeneralName! Wrong generalName argument! Expected a String instance!");
                }
                break;
            case 3:
            case 5:
                if (!(obj instanceof ASN1Object)) {
                    throw new IllegalArgumentException("Cannot create GeneralName! Wrong generalName argument! Expecting ASN1Object!");
                }
                break;
            case 4:
                if (!(obj instanceof Name)) {
                    throw new IllegalArgumentException("Cannot create GeneralName type directoryName! Wrong generalName argument! Expected a Name instance!");
                }
                break;
            case 7:
                if (obj instanceof InetAddress) {
                    this.b = ((InetAddress) obj).getAddress();
                } else if (obj instanceof byte[]) {
                    int length = ((byte[]) obj).length;
                    if (length != 4 && length != 8 && length != 16 && length != 32) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Invalid ipAddress format (invalid length ");
                        stringBuffer.append(length);
                        stringBuffer.append(")!");
                        throw new IllegalArgumentException(stringBuffer.toString());
                    }
                    this.b = obj;
                } else {
                    if (!(obj instanceof String)) {
                        throw new IllegalArgumentException("Cannot create GeneralName! Wrong ipAddress format! Expected InetAddress object or byte array!");
                    }
                    try {
                        this.b = a((String) obj);
                    } catch (CodingException e) {
                        try {
                            this.b = InetAddress.getByName((String) obj).getAddress();
                        } catch (UnknownHostException unused) {
                            StringBuffer stringBuffer2 = new StringBuffer();
                            stringBuffer2.append("Cannot create GeneralName from given ipAddress: ");
                            stringBuffer2.append(e.getMessage());
                            throw new IllegalArgumentException(stringBuffer2.toString());
                        }
                    }
                }
                break;
            case 8:
                if (!(obj instanceof ObjectID)) {
                    throw new IllegalArgumentException("Cannot create GeneralName type registeredID! Wrong generalName argument! Expected an ObjectID instance!");
                }
                break;
            default:
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Cannot create GeneralName! Illegal type specification: ");
                stringBuffer3.append(i);
                stringBuffer3.append("!. ");
                stringBuffer3.append("Type has to be in the range of 0...8!");
                throw new IllegalArgumentException(stringBuffer3.toString());
        }
        if (i != 7) {
            this.b = obj;
        }
        this.a = i;
    }

    public GeneralName(ASN1Object aSN1Object) throws CodingException {
        Object obj;
        this.a = -1;
        this.c = -1;
        if (!aSN1Object.isA(ASN.CON_SPEC)) {
            throw new CodingException("No GeneralName structure!");
        }
        CON_SPEC con_spec = (CON_SPEC) aSN1Object;
        int tag = aSN1Object.getAsnType().getTag();
        this.a = tag;
        this.c = 0;
        switch (tag) {
            case 0:
            case 3:
            case 5:
                if (con_spec.countComponents() > 1) {
                    con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                } else {
                    this.c = 1;
                }
                Object value = con_spec.getValue();
                this.b = value;
                if (this.a == 0) {
                    if (!(value instanceof ASN1Object)) {
                        throw new CodingException("OtherName must be ASN1Object!");
                    }
                    ASN1Object aSN1Object2 = (ASN1Object) value;
                    int iCountComponents = aSN1Object2.countComponents();
                    if (iCountComponents != 2) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Invalid number (");
                        stringBuffer.append(iCountComponents);
                        stringBuffer.append(") of components in OtherName. Must be 2!");
                        throw new CodingException(stringBuffer.toString());
                    }
                    ASN1Object componentAt = aSN1Object2.getComponentAt(0);
                    if (!componentAt.isA(ASN.ObjectID)) {
                        throw new CodingException("Missing type component of OtherName!");
                    }
                    try {
                        OtherName otherNameCreate = OtherName.create((ObjectID) componentAt);
                        otherNameCreate.decode(aSN1Object2);
                        this.b = otherNameCreate;
                        return;
                    } catch (InstantiationException unused) {
                        return;
                    }
                }
                return;
            case 1:
            case 2:
            case 6:
                con_spec.forceImplicitlyTagged(ASN.IA5String);
                this.b = ((ASN1Object) con_spec.getValue()).getValue();
                return;
            case 4:
                Object value2 = con_spec.getValue();
                if ((value2 instanceof ASN1Object) && ((ASN1Object) value2).getAsnType().equals(ASN.SEQUENCE)) {
                    this.c = 1;
                } else {
                    con_spec.forceImplicitlyTagged(ASN.SEQUENCE);
                }
                ASN1Object aSN1Object3 = (ASN1Object) con_spec.getValue();
                if (!(aSN1Object3 instanceof SEQUENCE)) {
                    throw new CodingException("Cannot parse GeneralName of type directoryName!  Name has to be a SEQUENCE!");
                }
                this.b = new Name(aSN1Object3);
                return;
            case 7:
                con_spec.forceImplicitlyTagged(ASN.OCTET_STRING);
                ASN1Object aSN1Object4 = (ASN1Object) con_spec.getValue();
                if (!(aSN1Object4 instanceof OCTET_STRING)) {
                    throw new CodingException("Cannot parse GeneralName. Expected OCTET_STRING!");
                }
                byte[] bArr = (byte[]) aSN1Object4.getValue();
                int length = bArr.length;
                obj = bArr;
                if (length != 4) {
                    obj = bArr;
                    if (length != 8) {
                        obj = bArr;
                        if (length != 16) {
                            obj = bArr;
                            if (length != 32) {
                                StringBuffer stringBuffer2 = new StringBuffer();
                                stringBuffer2.append("Invalid ipAddress format (invalid length ");
                                stringBuffer2.append(length);
                                stringBuffer2.append(")!");
                                throw new IllegalArgumentException(stringBuffer2.toString());
                            }
                        }
                    }
                }
                break;
            case 8:
                Object value3 = con_spec.getValue();
                if ((value3 instanceof ASN1Object) && ((ASN1Object) value3).getAsnType().equals(ASN.ObjectID)) {
                    this.c = 1;
                } else {
                    con_spec.forceImplicitlyTagged(ASN.ObjectID);
                }
                ASN1Object aSN1Object5 = (ASN1Object) con_spec.getValue();
                boolean z = aSN1Object5 instanceof ObjectID;
                obj = aSN1Object5;
                if (!z) {
                    throw new CodingException("Cannot parse GeneralName of type registeredID. Expected an ObjectID instance!");
                }
                break;
            default:
                throw new CodingException("Cannot parse GeneralName! Illegal type specification! Type has to be in the range of 0...8!");
        }
        this.b = obj;
    }

    private static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            int length = bArr.length;
            if (length < 16) {
                StringBuffer stringBuffer = new StringBuffer(length == 4 ? 15 : 32);
                stringBuffer.append(Integer.toString(bArr[0] & 255));
                for (int i = 1; i < length; i++) {
                    if (i == 4) {
                        stringBuffer.append("/");
                    } else {
                        stringBuffer.append(".");
                    }
                    stringBuffer.append(Integer.toString(bArr[i] & 255));
                }
                return stringBuffer.toString();
            }
            StringBuffer stringBuffer2 = new StringBuffer(length == 16 ? 39 : 81);
            stringBuffer2.append(Integer.toHexString(((bArr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[1] & 255)));
            int i2 = 2;
            while (i2 < length) {
                if (i2 == 16) {
                    stringBuffer2.append("/");
                } else {
                    stringBuffer2.append(":");
                }
                int i3 = (bArr[i2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
                int i4 = i2 + 1;
                stringBuffer2.append(Integer.toHexString(i3 | (bArr[i4] & 255)));
                i2 = i4 + 1;
            }
            return stringBuffer2.toString().toUpperCase();
        } catch (Throwable unused) {
            return "";
        }
    }

    private static byte[] a(String str) throws CodingException {
        byte[] bArr;
        Objects.requireNonNull(str, "ipAddress must not be null!");
        int i = 0;
        if (str.indexOf(".") != -1) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, "./");
            int iCountTokens = stringTokenizer.countTokens();
            if (iCountTokens != 4 && iCountTokens != 8) {
                throw new CodingException("Not supported ipv4 address format (invalid length)!");
            }
            bArr = new byte[iCountTokens];
            while (stringTokenizer.hasMoreTokens()) {
                int i2 = Integer.parseInt(stringTokenizer.nextToken());
                if (i2 < 0 || i2 > 255) {
                    throw new CodingException("Not supported ipv4 address format (out of range)!");
                }
                bArr[i] = (byte) i2;
                i++;
            }
        } else {
            StringTokenizer stringTokenizer2 = new StringTokenizer(str, ":/");
            int iCountTokens2 = stringTokenizer2.countTokens();
            if (iCountTokens2 != 8 && iCountTokens2 != 16) {
                throw new CodingException("Not supported ipv6 address format (invalid length)!");
            }
            bArr = new byte[iCountTokens2 * 2];
            while (stringTokenizer2.hasMoreTokens()) {
                int i3 = Integer.parseInt(stringTokenizer2.nextToken(), 16);
                if (i3 < 0 || i3 > 65535) {
                    throw new CodingException("Not supported ipv64 address format (out of range)!");
                }
                int i4 = i + 1;
                bArr[i] = (byte) (i3 >> 8);
                i = i4 + 1;
                bArr[i4] = (byte) i3;
            }
        }
        return bArr;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GeneralName)) {
            return false;
        }
        GeneralName generalName = (GeneralName) obj;
        int i = this.a;
        if (i != generalName.a) {
            return false;
        }
        switch (i) {
            case 0:
            case 3:
            case 5:
                return CryptoUtils.equalsBlock(DerCoder.encode((ASN1Object) this.b), DerCoder.encode((ASN1Object) generalName.b));
            case 1:
            case 2:
            case 4:
            case 6:
            case 8:
                return this.b.equals(generalName.b);
            case 7:
                return CryptoUtils.equalsBlock((byte[]) this.b, (byte[]) generalName.b);
            default:
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("GeneralName: type ");
                stringBuffer.append(this.a);
                stringBuffer.append(" not implemented yet!");
                throw new InternalErrorException(stringBuffer.toString());
        }
    }

    public Object getName() {
        return this.a == 7 ? a((byte[]) this.b) : this.b;
    }

    public int getType() {
        return this.a;
    }

    public int hashCode() {
        int iCalculateHashCode;
        int i = this.a;
        switch (i) {
            case 0:
            case 3:
            case 5:
                iCalculateHashCode = Util.calculateHashCode(DerCoder.encode((ASN1Object) this.b));
                break;
            case 1:
            case 2:
            case 4:
            case 6:
            case 8:
                iCalculateHashCode = this.b.hashCode();
                break;
            case 7:
                iCalculateHashCode = Util.calculateHashCode((byte[]) this.b);
                break;
            default:
                iCalculateHashCode = -1038130864;
                break;
        }
        return i + iCalculateHashCode;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public iaik.asn1.ASN1Object toASN1Object() throws iaik.asn1.CodingException {
        /*
            r6 = this;
            int r0 = r6.a
            r1 = 0
            r2 = 1
            switch(r0) {
                case 0: goto L50;
                case 1: goto L45;
                case 2: goto L45;
                case 3: goto L40;
                case 4: goto L36;
                case 5: goto L40;
                case 6: goto L45;
                case 7: goto L2a;
                case 8: goto L25;
                default: goto L7;
            }
        L7:
            iaik.asn1.CodingException r0 = new iaik.asn1.CodingException
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r2 = "GeneralName: type "
            r1.append(r2)
            int r2 = r6.a
            r1.append(r2)
            java.lang.String r2 = " not implemented yet!"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L25:
            java.lang.Object r0 = r6.b
            iaik.asn1.ObjectID r0 = (iaik.asn1.ObjectID) r0
            goto L4e
        L2a:
            iaik.asn1.OCTET_STRING r0 = new iaik.asn1.OCTET_STRING
            java.lang.Object r3 = r6.b
            byte[] r3 = (byte[]) r3
            byte[] r3 = (byte[]) r3
            r0.<init>(r3)
            goto L4e
        L36:
            java.lang.Object r0 = r6.b
            iaik.asn1.structures.Name r0 = (iaik.asn1.structures.Name) r0
            iaik.asn1.ASN1Object r0 = r0.toASN1Object()
            r3 = 0
            goto L5d
        L40:
            java.lang.Object r0 = r6.b
        L42:
            iaik.asn1.ASN1Object r0 = (iaik.asn1.ASN1Object) r0
            goto L4e
        L45:
            iaik.asn1.IA5String r0 = new iaik.asn1.IA5String
            java.lang.Object r3 = r6.b
            java.lang.String r3 = (java.lang.String) r3
            r0.<init>(r3)
        L4e:
            r3 = 1
            goto L5d
        L50:
            java.lang.Object r0 = r6.b
            boolean r3 = r0 instanceof iaik.asn1.structures.OtherName
            if (r3 == 0) goto L42
            iaik.asn1.structures.OtherName r0 = (iaik.asn1.structures.OtherName) r0
            iaik.asn1.ASN1Object r0 = r0.toASN1Object()
            goto L4e
        L5d:
            int r4 = r6.c
            r5 = -1
            if (r4 <= r5) goto L66
            if (r4 != 0) goto L65
            r1 = 1
        L65:
            r3 = r1
        L66:
            iaik.asn1.CON_SPEC r1 = new iaik.asn1.CON_SPEC
            int r2 = r6.a
            r1.<init>(r2, r0, r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.asn1.structures.GeneralName.toASN1Object():iaik.asn1.ASN1Object");
    }

    public String toString() {
        String str;
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        switch (this.a) {
            case 0:
                str = "otherName: ";
                break;
            case 1:
                str = "rfc822Name: ";
                break;
            case 2:
                str = "dNSName: ";
                break;
            case 3:
                str = "x400Address: ";
                break;
            case 4:
                str = "directoryName: ";
                break;
            case 5:
                str = "ediPartyName: ";
                break;
            case 6:
                str = "uniformResourceIdentifier: ";
                break;
            case 7:
                str = "iPAddress: ";
                break;
            case 8:
                str = "registeredID: ";
                break;
            default:
                str = "undefined";
                break;
        }
        stringBuffer.append(str);
        int i = this.a;
        if (i >= 0) {
            if (i == 7) {
                string = a((byte[]) this.b);
                if (string != null) {
                }
            } else {
                string = this.b.toString();
            }
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
}

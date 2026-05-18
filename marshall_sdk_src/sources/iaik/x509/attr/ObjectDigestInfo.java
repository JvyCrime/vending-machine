package iaik.x509.attr;

import androidx.core.os.EnvironmentCompat;
import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CodingException;
import iaik.asn1.ENUMERATED;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

/* JADX INFO: loaded from: classes2.dex */
public class ObjectDigestInfo {
    public static final int OTHER_OBJECT_TYPES = 2;
    public static final int PUBLIC_KEY = 0;
    public static final int PUBLIC_KEY_CERT = 1;
    int a;
    ObjectID b;
    AlgorithmID c;
    byte[] d;

    ObjectDigestInfo() {
        this.a = -1;
    }

    public ObjectDigestInfo(int i, AlgorithmID algorithmID, byte[] bArr, ObjectID objectID) {
        if (i < 0 || i > 2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot create ObjectDigestInfo. Invalid object type: ");
            stringBuffer.append(i);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (i == 2 && objectID == null) {
            throw new IllegalArgumentException("Cannot create ObjectDigestInfo for otherObjectTypes. Missing otherObjectTypeID!");
        }
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create ObjectDigestInfo. Missing digestAlgorithm!");
        }
        if (bArr == null) {
            throw new IllegalArgumentException("Cannot create ObjectDigestInfo. Missing digest value!");
        }
        this.a = i;
        this.c = algorithmID;
        this.d = bArr;
        if (i == 2) {
            this.b = objectID;
        }
    }

    public ObjectDigestInfo(ASN1Object aSN1Object) throws CodingException {
        a(aSN1Object);
    }

    public ObjectDigestInfo(PublicKey publicKey, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        if (publicKey == null) {
            throw new IllegalArgumentException("Cannot create ObjectDigestInfo. Missing publicKey!");
        }
        String lowerCase = publicKey.getFormat().toLowerCase();
        if (!lowerCase.equals("x.509") && !lowerCase.equals("x509")) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot create ObjectDigestInfo from public key: Invalid encoding format: ");
            stringBuffer.append(lowerCase);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create ObjectDigestInfo. Missing digestAlgorithm!");
        }
        this.a = 0;
        this.c = algorithmID;
        this.d = calculateDigest(publicKey.getEncoded(), algorithmID);
    }

    public ObjectDigestInfo(X509Certificate x509Certificate, AlgorithmID algorithmID) throws NoSuchAlgorithmException, CertificateEncodingException {
        if (x509Certificate == null) {
            throw new IllegalArgumentException("Cannot create ObjectDigestInfo. Missing publicKey!");
        }
        if (algorithmID == null) {
            throw new IllegalArgumentException("Cannot create ObjectDigestInfo. Missing digestAlgorithm!");
        }
        this.a = 1;
        this.c = algorithmID;
        this.d = calculateDigest(x509Certificate.getEncoded(), algorithmID);
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        int i;
        int iIntValue = ((Integer) aSN1Object.getComponentAt(0).getValue()).intValue();
        this.a = iIntValue;
        if (iIntValue < 0 || iIntValue > 2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("cannot decode ObjectDigestInfo. Invalid object type: ");
            stringBuffer.append(this.a);
            throw new CodingException(stringBuffer.toString());
        }
        if (aSN1Object.countComponents() == 4) {
            this.b = (ObjectID) aSN1Object.getComponentAt(1);
            i = 2;
        } else {
            i = 1;
        }
        if (this.a == 2 && this.b == null) {
            throw new CodingException("Cannot decode ObjectDigestInfo. Missing otherObjectTypeID for otherObjectTypes!");
        }
        this.c = new AlgorithmID(aSN1Object.getComponentAt(i + 0));
        this.d = (byte[]) aSN1Object.getComponentAt(i + 1).getValue();
    }

    public static byte[] calculateDigest(byte[] bArr, AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        return algorithmID.getMessageDigestInstance().digest(bArr);
    }

    public boolean equals(Object obj) {
        ObjectID objectID;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ObjectDigestInfo)) {
            return false;
        }
        ObjectDigestInfo objectDigestInfo = (ObjectDigestInfo) obj;
        if (this.a != objectDigestInfo.a || !this.c.equals(objectDigestInfo.c) || !CryptoUtils.equalsBlock(this.d, objectDigestInfo.d)) {
            return false;
        }
        ObjectID objectID2 = this.b;
        return (objectID2 == null || (objectID = objectDigestInfo.b) == null) ? objectID2 == null && objectDigestInfo.b == null : objectID2.equals(objectID);
    }

    public AlgorithmID getDigestAlgorithm() {
        return this.c;
    }

    public byte[] getObjectDigest() {
        return this.d;
    }

    public int getObjectType() {
        return this.a;
    }

    public String getObjectTypeName() {
        String string;
        int i = this.a;
        if (i == 0) {
            return "publicKey";
        }
        if (i == 1) {
            return "publicKeyCert";
        }
        if (i != 2) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("otherObjectTypes");
        if (this.b != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(" (");
            stringBuffer2.append(this.b.getName());
            stringBuffer2.append(")");
            string = stringBuffer2.toString();
        } else {
            string = "";
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    public ObjectID getOtherObjectTypeID() {
        return this.b;
    }

    public int hashCode() {
        return Util.calculateHashCode(this.d);
    }

    public boolean identifiesCert(X509Certificate x509Certificate) throws NoSuchAlgorithmException, CertificateEncodingException {
        if (this.a != 1) {
            return false;
        }
        return CryptoUtils.equalsBlock(this.d, calculateDigest(x509Certificate.getEncoded(), this.c));
    }

    public boolean identifiesKey(PublicKey publicKey) throws NoSuchAlgorithmException {
        if (this.a != 0) {
            return false;
        }
        return CryptoUtils.equalsBlock(calculateDigest(publicKey.getEncoded(), this.c), this.d);
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new ENUMERATED(this.a));
        ObjectID objectID = this.b;
        if (objectID != null) {
            sequence.addComponent(objectID);
        }
        sequence.addComponent(this.c.toASN1Object());
        sequence.addComponent(new BIT_STRING(this.d));
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("digestObjectType: ");
        stringBuffer2.append(getObjectTypeName());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("digestAlgorithm: ");
        stringBuffer3.append(this.c);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("objectDigest: ");
        stringBuffer4.append(Util.toString(this.d));
        stringBuffer.append(stringBuffer4.toString());
        return stringBuffer.toString();
    }
}

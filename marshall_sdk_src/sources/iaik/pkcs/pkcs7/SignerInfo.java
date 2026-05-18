package iaik.pkcs.pkcs7;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.EncodeListener;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Attribute;
import iaik.utils.Util;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class SignerInfo implements ASN1Type, EncodeListener {
    static Class k;
    a a;
    int b;
    IssuerAndSerialNumber c;
    AlgorithmID d;
    Attribute[] e;
    AlgorithmID f;
    byte[] g;
    Attribute[] h;
    PrivateKey i;
    RSACipherProvider j;

    public SignerInfo() {
        this.b = 1;
        this.d = AlgorithmID.sha;
        this.f = AlgorithmID.rsaEncryption;
        this.h = null;
        this.j = RSACipherProvider.getDefault();
    }

    public SignerInfo(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public SignerInfo(IssuerAndSerialNumber issuerAndSerialNumber, AlgorithmID algorithmID, AlgorithmID algorithmID2, PrivateKey privateKey) {
        this();
        if (algorithmID2.equals(AlgorithmID.rsaEncryption)) {
            this.c = issuerAndSerialNumber;
            this.d = algorithmID;
            this.f = algorithmID2;
            this.i = privateKey;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Algorithm ");
        stringBuffer.append(algorithmID2.getName());
        stringBuffer.append(" not supported for digest encryption!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public SignerInfo(IssuerAndSerialNumber issuerAndSerialNumber, AlgorithmID algorithmID, PrivateKey privateKey) {
        this();
        this.c = issuerAndSerialNumber;
        this.d = algorithmID;
        this.i = privateKey;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        int i = 0;
        try {
            this.b = ((BigInteger) aSN1Object.getComponentAt(0).getValue()).intValue();
            this.c = new IssuerAndSerialNumber(aSN1Object.getComponentAt(1));
            this.d = new AlgorithmID(aSN1Object.getComponentAt(2));
            ASN1Object componentAt = aSN1Object.getComponentAt(3);
            if (componentAt.isA(ASN.CON_SPEC)) {
                if (this.b != 2) {
                    ((CON_SPEC) componentAt).forceImplicitlyTagged(ASN.SET);
                }
                ASN1Object aSN1Object2 = (ASN1Object) componentAt.getValue();
                Class clsClass$ = k;
                if (clsClass$ == null) {
                    clsClass$ = class$("iaik.asn1.structures.Attribute");
                    k = clsClass$;
                }
                this.e = (Attribute[]) ASN.parseSequenceOf(aSN1Object2, clsClass$);
                i = 1;
            }
            this.f = new AlgorithmID(aSN1Object.getComponentAt(i + 3));
            this.g = (byte[]) aSN1Object.getComponentAt(i + 4).getValue();
            int i2 = i + 5;
            if (i2 < aSN1Object.countComponents()) {
                CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i2);
                con_spec.forceImplicitlyTagged(this.b == 2 ? ASN.SEQUENCE : ASN.SET);
                ASN1Object aSN1Object3 = (ASN1Object) con_spec.getValue();
                Class clsClass$2 = k;
                if (clsClass$2 == null) {
                    clsClass$2 = class$("iaik.asn1.structures.Attribute");
                    k = clsClass$2;
                }
                this.h = (Attribute[]) ASN.parseSequenceOf(aSN1Object3, clsClass$2);
            }
        } catch (Exception e) {
            throw new CodingException(e.toString());
        }
    }

    @Override // iaik.asn1.EncodeListener
    public void encodeCalled(ASN1Object aSN1Object, int i) throws CodingException {
        byte[] bArrDigest;
        AlgorithmID contentEncryptionAlgorithm;
        SecretKey secretKey;
        try {
            if (i == 1) {
                aSN1Object.setValue(this.a.getMessageDigest(this.d));
                return;
            }
            if (i != 2) {
                return;
            }
            if (this.g == null) {
                if (this.e == null) {
                    bArrDigest = this.a.getMessageDigest(this.d);
                } else {
                    MessageDigest messageDigest = MessageDigest.getInstance(this.d.getName());
                    bArrDigest = this.b == 2 ? messageDigest.digest(DerCoder.encode(ASN.createSequenceOf(this.e))) : messageDigest.digest(DerCoder.encode(ASN.createSetOf(this.e, true)));
                }
                this.g = this.j.cipher(1, this.i, new DigestInfo(this.d, bArrDigest).toByteArray());
                a aVar = this.a;
                if ((aVar instanceof SignedAndEnvelopedData) || (aVar instanceof SignedAndEnvelopedDataStream)) {
                    if (aVar instanceof SignedAndEnvelopedData) {
                        SignedAndEnvelopedData signedAndEnvelopedData = (SignedAndEnvelopedData) aVar;
                        contentEncryptionAlgorithm = signedAndEnvelopedData.getEncryptedContentInfo().getContentEncryptionAlgorithm();
                        secretKey = signedAndEnvelopedData.c;
                    } else {
                        SignedAndEnvelopedDataStream signedAndEnvelopedDataStream = (SignedAndEnvelopedDataStream) aVar;
                        contentEncryptionAlgorithm = signedAndEnvelopedDataStream.getEncryptedContentInfo().getContentEncryptionAlgorithm();
                        secretKey = signedAndEnvelopedDataStream.c;
                    }
                    Cipher cipherInstance = contentEncryptionAlgorithm.getCipherInstance();
                    try {
                        cipherInstance.init(1, secretKey, contentEncryptionAlgorithm.getAlgorithmParameters(), (SecureRandom) null);
                    } catch (NoSuchAlgorithmException unused) {
                        ASN1Object parameter = contentEncryptionAlgorithm.getParameter();
                        cipherInstance.init(1, secretKey, (parameter == null || !parameter.isA(ASN.OCTET_STRING)) ? null : new IvParameterSpec((byte[]) parameter.getValue()), (SecureRandom) null);
                    }
                    this.g = cipherInstance.doFinal(this.g);
                }
            }
            aSN1Object.setValue(this.g);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to encrypt digest: ");
            stringBuffer.append(e);
            throw new CodingException(stringBuffer.toString());
        }
    }

    public Attribute getAuthenticatedAttribute(ObjectID objectID) {
        if (this.e == null) {
            return null;
        }
        int i = 0;
        while (true) {
            Attribute[] attributeArr = this.e;
            if (i >= attributeArr.length) {
                return null;
            }
            if (attributeArr[i].getType().equals(objectID)) {
                return this.e[i];
            }
            i++;
        }
    }

    public Attribute[] getAuthenticatedAttributes() {
        return this.e;
    }

    public byte[] getDigest(PublicKey publicKey) throws SignatureException, InvalidKeyException {
        try {
            DigestInfo digestInfo = new DigestInfo(this.j.cipher(2, publicKey, this.g));
            if (digestInfo.checkDigestAlgorithm(this.d)) {
                return digestInfo.getDigest();
            }
            throw new SignatureException("Digest algorithm mismatch!");
        } catch (CodingException e) {
            throw new SignatureException(e.toString());
        } catch (NoSuchAlgorithmException e2) {
            throw new SignatureException(e2.toString());
        } catch (NoSuchProviderException e3) {
            throw new SignatureException(e3.toString());
        } catch (GeneralSecurityException e4) {
            throw new SignatureException(e4.toString());
        }
    }

    public AlgorithmID getDigestAlgorithm() {
        return this.d;
    }

    public AlgorithmID getDigestEncryptionAlgorithm() {
        return this.f;
    }

    public byte[] getEncryptedDigest() {
        return this.g;
    }

    public IssuerAndSerialNumber getIssuerAndSerialNumber() {
        return this.c;
    }

    public RSACipherProvider getRSACipherProvider() {
        return this.j;
    }

    public Attribute getUnauthenticatedAttribute(ObjectID objectID) {
        if (this.h == null) {
            return null;
        }
        int i = 0;
        while (true) {
            Attribute[] attributeArr = this.h;
            if (i >= attributeArr.length) {
                return null;
            }
            if (attributeArr[i].getType().equals(objectID)) {
                return this.h[i];
            }
            i++;
        }
    }

    public Attribute[] getUnauthenticatedAttributes() {
        return this.h;
    }

    public int getVersion() {
        return this.b;
    }

    public void setAuthenticatedAttributes(Attribute[] attributeArr) {
        this.e = attributeArr;
    }

    public void setEncryptedDigest(byte[] bArr) {
        this.g = bArr;
    }

    public void setRSACipherProvider(RSACipherProvider rSACipherProvider) {
        if (rSACipherProvider == null) {
            throw new IllegalArgumentException("Provider is not alloed tp be null!");
        }
        this.j = rSACipherProvider;
    }

    public void setUnauthenticatedAttributes(Attribute[] attributeArr) {
        this.h = attributeArr;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        boolean z;
        SEQUENCE sequence = new SEQUENCE();
        try {
            sequence.addComponent(new INTEGER(this.b));
            sequence.addComponent(this.c.toASN1Object());
            sequence.addComponent(this.d.toASN1Object());
            Attribute[] attributeArr = this.e;
            if (attributeArr != null && attributeArr.length > 0) {
                OCTET_STRING octet_string = new OCTET_STRING();
                int length = this.e.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        z = false;
                        break;
                    }
                    if (this.e[i].getType().equals(ObjectID.messageDigest)) {
                        z = true;
                        break;
                    }
                    i++;
                }
                if (!z) {
                    if (this.a instanceof Content) {
                        encodeCalled(octet_string, 1);
                    } else {
                        octet_string.addEncodeListener(this, 1);
                    }
                    Attribute[] attributeArr2 = (Attribute[]) Util.resizeArray(this.e, length + 1);
                    this.e = attributeArr2;
                    attributeArr2[length] = new Attribute(ObjectID.messageDigest, new ASN1Object[]{octet_string});
                }
                sequence.addComponent(this.b == 2 ? new CON_SPEC(2, ASN.createSequenceOf(this.e), false) : new CON_SPEC(0, ASN.createSetOf(this.e, true), true));
            }
            sequence.addComponent(this.f.toASN1Object());
            OCTET_STRING octet_string2 = new OCTET_STRING();
            if (this.a instanceof Content) {
                encodeCalled(octet_string2, 2);
            } else {
                octet_string2.addEncodeListener(this, 2);
            }
            sequence.addComponent(octet_string2);
            Attribute[] attributeArr3 = this.h;
            if (attributeArr3 != null) {
                sequence.addComponent(this.b == 2 ? new CON_SPEC(3, ASN.createSequenceOf(attributeArr3), true) : new CON_SPEC(1, ASN.createSetOf(attributeArr3), true));
            }
            return sequence;
        } catch (Exception e) {
            throw new CodingException(e.toString());
        }
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.b);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(this.c);
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("digest_algorithm: ");
        stringBuffer3.append(this.d);
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("digest_encryption_algorithm: ");
        stringBuffer4.append(this.f);
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        int i = 0;
        if (this.g != null) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("encrypted_digest: ");
            stringBuffer5.append(this.g.length);
            stringBuffer5.append(" Bytes [");
            stringBuffer5.append(Util.toString(this.g, 0, 5));
            stringBuffer5.append("...]\n");
            string = stringBuffer5.toString();
        } else {
            string = "encrypted_digest: yet not set\n";
        }
        stringBuffer.append(string);
        if (this.e != null) {
            stringBuffer.append("authenticated_attributes:\n");
            int i2 = 0;
            while (true) {
                Attribute[] attributeArr = this.e;
                if (i2 >= attributeArr.length) {
                    break;
                }
                stringBuffer.append(attributeArr[i2].toString());
                i2++;
            }
        }
        if (this.h != null) {
            stringBuffer.append("unauthenticated_attributes:\n");
            while (true) {
                Attribute[] attributeArr2 = this.h;
                if (i >= attributeArr2.length) {
                    break;
                }
                stringBuffer.append(attributeArr2[i].toString());
                i++;
            }
        }
        return stringBuffer.toString();
    }
}

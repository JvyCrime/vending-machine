package iaik.pkcs.pkcs10;

import iaik.asn1.ASN;
import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.SET;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Attribute;
import iaik.asn1.structures.AttributeValue;
import iaik.asn1.structures.Name;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.utils.Util;
import iaik.x509.PublicKeyInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class CertificateRequest implements CertRequest, Serializable {
    static Class a = null;
    private static final long serialVersionUID = 3287043426874812366L;
    private ASN1 b;
    private int c;
    private Name d;
    private PublicKey e;
    private AlgorithmID f;
    private byte[] g;
    private Attribute[] h;
    private boolean i;
    private byte[] j;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    public CertificateRequest(InputStream inputStream) throws PKCSParsingException, IOException {
        this.c = 0;
        try {
            this.b = new ASN1(inputStream);
            d();
        } catch (CodingException e) {
            throw new PKCSParsingException(e.toString());
        }
    }

    public CertificateRequest(PublicKey publicKey, Name name) throws InvalidKeyException {
        this.c = 0;
        this.e = publicKey;
        this.d = name;
        a();
        this.c = 0;
    }

    public CertificateRequest(byte[] bArr) throws PKCSParsingException {
        this.c = 0;
        try {
            this.b = new ASN1(bArr);
            d();
        } catch (CodingException e) {
            throw new PKCSParsingException(e.toString());
        }
    }

    private void a() {
        this.i = true;
        this.j = null;
        this.b = null;
    }

    private void b() {
        if (this.i) {
            throw new RuntimeException("Cannot perform operation, certificate has to be signed first");
        }
    }

    private void c() {
        this.i = false;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private void d() throws PKCSParsingException {
        try {
            ASN1Object componentAt = this.b.getComponentAt(0);
            this.f = new AlgorithmID(this.b.getComponentAt(1));
            this.g = (byte[]) ((BIT_STRING) this.b.getComponentAt(2)).getValue();
            this.c = ((BigInteger) componentAt.getComponentAt(0).getValue()).intValue();
            this.d = new Name(componentAt.getComponentAt(1));
            try {
                this.e = PublicKeyInfo.getPublicKey(componentAt.getComponentAt(2));
                if (componentAt.countComponents() > 3) {
                    ASN1Object componentAt2 = componentAt.getComponentAt(3);
                    ((CON_SPEC) componentAt2).forceImplicitlyTagged(ASN.SEQUENCE);
                    if (componentAt2.getAsnType().getTag() != 0) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Unknown context specific tag: ");
                        stringBuffer.append(componentAt2.getAsnType().getTag());
                        throw new PKCSParsingException(stringBuffer.toString());
                    }
                    ASN1Object aSN1Object = (ASN1Object) componentAt2.getValue();
                    Class clsClass$ = a;
                    if (clsClass$ == null) {
                        clsClass$ = class$("iaik.asn1.structures.Attribute");
                        a = clsClass$;
                    }
                    this.h = (Attribute[]) ASN.parseSequenceOf(aSN1Object, clsClass$);
                }
                this.b.clearASN1Object();
                c();
            } catch (InvalidKeyException e) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Unable to create PublicKey: ");
                stringBuffer2.append(e.toString());
                throw new PKCSParsingException(stringBuffer2.toString());
            }
        } catch (CodingException e2) {
            throw new PKCSParsingException(e2.toString());
        } catch (RuntimeException e3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Certificate request format error: ");
            stringBuffer3.append(e3.toString());
            throw new PKCSParsingException(stringBuffer3.toString());
        }
    }

    private ASN1Object e() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new INTEGER(this.c));
        sequence.addComponent(this.d.toASN1Object());
        sequence.addComponent(DerCoder.decode(this.e.getEncoded()));
        sequence.addComponent(this.h != null ? new CON_SPEC(0, ASN.createSetOf(this.h), true) : new CON_SPEC(0, new SET(), true));
        return sequence;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            this.b = new ASN1(objectInputStream);
            d();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to restore CertificateRequest: ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        } catch (PKCSParsingException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Unable to restore CertificateRequest: ");
            stringBuffer2.append(e2.toString());
            throw new IOException(stringBuffer2.toString());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.write(toByteArray());
    }

    public void addAttribute(Attribute attribute) {
        Attribute[] attributeArr = this.h;
        if (attributeArr == null) {
            this.h = new Attribute[]{attribute};
            return;
        }
        Attribute[] attributeArr2 = new Attribute[attributeArr.length + 1];
        System.arraycopy(attributeArr, 0, attributeArr2, 0, attributeArr.length);
        attributeArr2[this.h.length] = attribute;
        this.h = attributeArr2;
        a();
    }

    public Attribute getAttribute(ObjectID objectID) {
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

    public AttributeValue getAttributeValue(ObjectID objectID) throws PKCSException {
        Attribute attribute = getAttribute(objectID);
        if (attribute == null) {
            return null;
        }
        try {
            return attribute.getAttributeValue();
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot decode attribute ");
            stringBuffer.append(objectID.getName());
            stringBuffer.append(": ");
            stringBuffer.append(e.getMessage());
            throw new PKCSException(stringBuffer.toString());
        }
    }

    public Attribute[] getAttributes() {
        return this.h;
    }

    public Attribute[] getAttributes(ObjectID objectID) {
        if (this.h == null) {
            return null;
        }
        Vector vector = new Vector();
        int i = 0;
        while (true) {
            Attribute[] attributeArr = this.h;
            if (i >= attributeArr.length) {
                break;
            }
            if (attributeArr[i].getType().equals(objectID)) {
                vector.addElement(this.h[i]);
            }
            i++;
        }
        if (vector.isEmpty()) {
            return null;
        }
        Attribute[] attributeArr2 = new Attribute[vector.size()];
        vector.copyInto(attributeArr2);
        return attributeArr2;
    }

    public byte[] getCertificateRequestInfo() throws PKCSException {
        try {
            ASN1 asn1 = this.b;
            return (asn1 == null || asn1.toByteArray() == null) ? DerCoder.encode(e()) : this.b.getFirstObject();
        } catch (CodingException e) {
            throw new PKCSException(e.toString());
        }
    }

    public byte[] getFingerprint() {
        b();
        return this.b.fingerprint();
    }

    public byte[] getFingerprint(String str) throws NoSuchAlgorithmException {
        b();
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(this.b.toByteArray());
        return messageDigest.digest();
    }

    public byte[] getFingerprintSHA() {
        if (this.j == null) {
            try {
                this.j = getFingerprint("SHA");
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Algorithm SHA not available: ");
                stringBuffer.append(e.toString());
                throw new RuntimeException(stringBuffer.toString());
            }
        }
        return this.j;
    }

    @Override // iaik.pkcs.pkcs10.CertRequest
    public PublicKey getPublicKey() {
        return this.e;
    }

    public AlgorithmID getSignatureAlgorithmID() {
        return this.f;
    }

    public Name getSubject() {
        return this.d;
    }

    public int getVersion() {
        return this.c;
    }

    public void setAttributes(Attribute[] attributeArr) {
        this.h = attributeArr;
        a();
    }

    public void setSignature(AlgorithmID algorithmID, byte[] bArr) throws SignatureException {
        if (algorithmID == null) {
            throw new SignatureException("Cannot sign this request. No signature algorithm specified!");
        }
        if (bArr == null) {
            throw new SignatureException("Cannot sign this request. No signature value specified!");
        }
        this.f = algorithmID;
        this.g = bArr;
        try {
            a();
            ASN1Object aSN1ObjectE = e();
            BIT_STRING bit_string = new BIT_STRING(this.g);
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectE);
            sequence.addComponent(this.f.toASN1Object());
            sequence.addComponent(bit_string);
            this.b = new ASN1(sequence);
            c();
        } catch (CodingException unused) {
            throw new SignatureException("Cann't sign CertRequest!");
        }
    }

    public void sign(AlgorithmID algorithmID, PrivateKey privateKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        sign(algorithmID, privateKey, null);
    }

    public void sign(AlgorithmID algorithmID, PrivateKey privateKey, String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        AlgorithmParameters signatureParameters;
        if (privateKey == null) {
            throw new InvalidKeyException("Cannot sign this request. No private key specified!");
        }
        if (algorithmID == null) {
            throw new NoSuchAlgorithmException("Cannot sign this request. No algorithm specified!");
        }
        this.f = algorithmID;
        Signature signatureInstance = algorithmID.getSignatureInstance(str);
        signatureInstance.initSign(privateKey);
        try {
            if (!AlgorithmID.getDoNotIncludeParameters(this.f) && !this.f.hasParameters() && (signatureParameters = Util.getSignatureParameters(signatureInstance)) != null) {
                this.f.setAlgorithmParameters(signatureParameters);
            }
        } catch (Exception unused) {
        }
        try {
            a();
            ASN1Object aSN1ObjectE = e();
            signatureInstance.update(DerCoder.encode(aSN1ObjectE));
            this.g = signatureInstance.sign();
            BIT_STRING bit_string = new BIT_STRING(this.g);
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(aSN1ObjectE);
            sequence.addComponent(this.f.toASN1Object());
            sequence.addComponent(bit_string);
            this.b = new ASN1(sequence);
            c();
        } catch (CodingException unused2) {
            throw new SignatureException("Cann't sign CertRequest!");
        }
    }

    public byte[] toByteArray() {
        b();
        return this.b.toByteArray();
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Version: ");
        stringBuffer2.append(this.c);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        if (this.d != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Subject: ");
            stringBuffer3.append(this.d);
            stringBuffer3.append("\n");
            stringBuffer.append(stringBuffer3.toString());
        }
        PublicKey publicKey = this.e;
        if (publicKey != null) {
            stringBuffer.append(publicKey.toString());
        }
        if (this.f != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Signature algorithm: ");
            stringBuffer4.append(this.f);
            stringBuffer4.append("\n");
            stringBuffer.append(stringBuffer4.toString());
        }
        stringBuffer.append("\n");
        Attribute[] attributeArr = this.h;
        if (attributeArr != null && attributeArr.length > 0) {
            if (z) {
                int i = 0;
                while (i < this.h.length) {
                    StringBuffer stringBuffer5 = new StringBuffer();
                    stringBuffer5.append("Attribute ");
                    int i2 = i + 1;
                    stringBuffer5.append(i2);
                    stringBuffer5.append(": ");
                    stringBuffer.append(stringBuffer5.toString());
                    stringBuffer.append(this.h[i]);
                    stringBuffer.append("\n");
                    i = i2;
                }
            } else {
                stringBuffer.append("Attributes: yes\n");
            }
        }
        StringBuffer stringBuffer6 = new StringBuffer();
        stringBuffer6.append("Fingerprint (MD5)  : ");
        stringBuffer6.append(Util.toString(getFingerprint()));
        stringBuffer6.append("\n");
        stringBuffer.append(stringBuffer6.toString());
        StringBuffer stringBuffer7 = new StringBuffer();
        stringBuffer7.append("Fingerprint (SHA-1): ");
        stringBuffer7.append(Util.toString(getFingerprintSHA()));
        stringBuffer7.append("\n");
        stringBuffer.append(stringBuffer7.toString());
        return stringBuffer.toString();
    }

    @Override // iaik.pkcs.pkcs10.CertRequest
    public boolean verify() throws SignatureException {
        return verify(null);
    }

    public boolean verify(String str) throws SignatureException {
        b();
        try {
            Signature signatureInstance = this.f.getSignatureInstance(str);
            byte[] firstObject = this.b.getFirstObject();
            signatureInstance.initVerify(this.e);
            signatureInstance.update(firstObject);
            return signatureInstance.verify(this.g);
        } catch (CodingException e) {
            throw new SignatureException(e.getMessage());
        } catch (InvalidKeyException e2) {
            throw new SignatureException(e2.getMessage());
        } catch (NoSuchAlgorithmException e3) {
            throw new SignatureException(e3.getMessage());
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        b();
        this.b.writeTo(outputStream);
    }
}

package iaik.pkcs.pkcs7;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.AlgorithmID;
import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class DigestInfo implements ASN1Type {
    private AlgorithmID a;
    private byte[] b;

    public DigestInfo(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public DigestInfo(AlgorithmID algorithmID, byte[] bArr) {
        this.a = algorithmID;
        this.b = bArr;
    }

    public DigestInfo(byte[] bArr) throws CodingException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        DerInputStream derInputStream = new DerInputStream(byteArrayInputStream);
        try {
            if (derInputStream.nextTag() != 16) {
                throw new CodingException("DigestInfo must be SEQUENCE!");
            }
            DerInputStream sequence = derInputStream.readSequence();
            this.a = new AlgorithmID(sequence);
            if (sequence.nextTag() != 4) {
                throw new CodingException("Digest component must be OCTET STRING!");
            }
            this.b = sequence.readOctetStringByteArray();
            if (byteArrayInputStream.read() != -1) {
                throw new CodingException("Invalid DigestInfo encoding: too long!");
            }
        } catch (IOException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error decoding DigestInfo: ");
            stringBuffer.append(e.toString());
            throw new CodingException(stringBuffer.toString());
        }
    }

    public boolean checkDigestAlgorithm(AlgorithmID algorithmID) {
        return this.a.equals(algorithmID, true);
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            throw new CodingException("DigestInfo must be a SEQUENCE!");
        }
        if (aSN1Object.countComponents() != 2) {
            throw new CodingException("DigestInfo must have two components!");
        }
        try {
            this.a = new AlgorithmID(aSN1Object.getComponentAt(0));
            this.b = (byte[]) aSN1Object.getComponentAt(1).getValue();
        } catch (Exception e) {
            throw new CodingException(e.toString());
        }
    }

    public byte[] getDigest() {
        return this.b;
    }

    public AlgorithmID getDigestAlgorithm() {
        return this.a;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a.toASN1Object());
        sequence.addComponent(new OCTET_STRING(this.b));
        return sequence;
    }

    public byte[] toByteArray() {
        return DerCoder.encode(toASN1Object());
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("digest_algorithm: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("digest: ");
        stringBuffer3.append(this.b.length);
        stringBuffer3.append(" Bytes [");
        stringBuffer3.append(Util.toString(this.b, 0, 5));
        stringBuffer3.append("...]\n");
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }
}

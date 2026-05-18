package iaik.x509;

import iaik.asn1.ASN1;
import iaik.asn1.CodingException;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs10.CertRequest;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/* JADX INFO: loaded from: classes2.dex */
public class NetscapeCertRequest implements CertRequest {
    private ASN1 a;

    public NetscapeCertRequest(InputStream inputStream) throws IOException, CodingException {
        this.a = null;
        if (inputStream == null) {
            throw new IOException("Cannot create NetscapeCertRequest from a null inputstream!");
        }
        this.a = new ASN1(inputStream);
    }

    public NetscapeCertRequest(byte[] bArr) throws CodingException {
        this.a = null;
        if (bArr == null) {
            throw new CodingException("Cannot create NetscapeCertRequest from a null byte array!");
        }
        this.a = new ASN1(bArr);
    }

    @Override // iaik.pkcs.pkcs10.CertRequest
    public PublicKey getPublicKey() throws InvalidKeyException {
        try {
            return PublicKeyInfo.getPublicKey(this.a.getComponentAt(0).getComponentAt(0));
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unable to create PublicKey: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // iaik.pkcs.pkcs10.CertRequest
    public boolean verify() throws SignatureException {
        try {
            ASN1 asn1 = new ASN1(this.a.getComponentAt(0));
            Signature signatureInstance = new AlgorithmID(this.a.getComponentAt(1)).getSignatureInstance();
            byte[] bArr = (byte[]) this.a.getComponentAt(2).getValue();
            signatureInstance.initVerify(getPublicKey());
            signatureInstance.update(asn1.toByteArray());
            return signatureInstance.verify(bArr);
        } catch (Exception e) {
            throw new SignatureException(e.getMessage());
        }
    }
}

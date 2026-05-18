package iaik.x509.stream;

import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.asn1.structures.Name;
import iaik.x509.RevokedCertificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509Extensions;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public class X509CRLStream {
    private CRLListener a;
    private InputStream b;
    private byte[] c;
    private int d;

    public X509CRLStream(CRLListener cRLListener) {
        this.a = cRLListener;
    }

    private void a() {
        this.d = 0;
    }

    private void a(int i) throws IOException {
        while (i > this.c.length - this.d) {
            e();
        }
        int i2 = 0;
        while (i2 < i) {
            int i3 = this.b.read(this.c, this.d, i - i2);
            if (i3 < 0) {
                throw new EOFException("Unexpected EOF while reading data.");
            }
            i2 += i3;
            this.d += i3;
        }
    }

    private void a(InputStream inputStream) {
        this.b = inputStream;
        this.c = inputStream != null ? new byte[128] : null;
    }

    private int b() {
        return this.d;
    }

    private byte[] c() {
        return this.c;
    }

    private int d() throws IOException {
        int i = this.b.read();
        if (i != -1) {
            if (this.d >= this.c.length) {
                e();
            }
            byte[] bArr = this.c;
            int i2 = this.d;
            this.d = i2 + 1;
            bArr[i2] = (byte) i;
        }
        return i;
    }

    private void e() {
        byte[] bArr = this.c;
        byte[] bArr2 = new byte[bArr.length * 2];
        System.arraycopy(bArr, 0, bArr2, 0, this.d);
        this.c = bArr2;
    }

    private int f() throws IOException {
        return d();
    }

    private int g() throws IOException, CodingException {
        int iD = d();
        if (iD == -1) {
            throw new EOFException("Unexpected EOF! Expected encoded length.");
        }
        int i = iD & 255;
        if (i < 128) {
            return i;
        }
        if (i == 128) {
            throw new CodingException("Invalid DER encoding. Indefinite length encoding is not allowed.");
        }
        int i2 = i & 127;
        if (i2 > 4) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Encoded length is too large. Takes ");
            stringBuffer.append(i2);
            stringBuffer.append(" octets. Maximum is 4.");
            throw new CodingException(stringBuffer.toString());
        }
        int i3 = 0;
        while (true) {
            i2--;
            if (i2 < 0) {
                return i3;
            }
            int iD2 = d();
            if (iD2 == -1) {
                throw new EOFException("Unexpected EOF! Expected a length octet.");
            }
            i3 = (i3 << 8) | (iD2 & 255);
        }
    }

    void a(DerInputStream derInputStream) throws IOException, CRLException {
        try {
            try {
                CRLListener cRLListener = this.a;
                if (cRLListener instanceof RevokedCertificatesCRLListener) {
                    RevokedCertificatesCRLListener revokedCertificatesCRLListener = (RevokedCertificatesCRLListener) cRLListener;
                    a((InputStream) derInputStream);
                    while (true) {
                        a();
                        int iF = f();
                        if (iF == -1) {
                            break;
                        }
                        if (iF != 48) {
                            throw new CRLException("Invalid CRL entry that is not a SEQUENCE.");
                        }
                        int iG = g();
                        int iB = b();
                        int iF2 = f();
                        if (iF2 == -1) {
                            throw new EOFException("Unexpected EOF in entry! Expected an INTEGER.");
                        }
                        if (iF2 != 2) {
                            throw new CRLException("Invalid CRL entry, which does not start with an INTEGER.");
                        }
                        int iG2 = g();
                        int iB2 = b();
                        a(iG - (iB2 - iB));
                        revokedCertificatesCRLListener.revokedCertificate(c(), 0, iB + iG, iB2, iG2);
                    }
                } else {
                    while (derInputStream.nextTag() != -1) {
                        this.a.revokedCertificate(new RevokedCertificate(DerCoder.decode(derInputStream)));
                    }
                }
            } catch (CodingException e) {
                throw new CRLException(e.toString());
            } catch (X509ExtensionException e2) {
                throw new CRLException(e2.toString());
            }
        } finally {
            a((InputStream) null);
        }
    }

    void a(DerInputStream derInputStream, a aVar) throws IOException, CRLException {
        try {
            int iIntValue = derInputStream.nextTag() == 2 ? derInputStream.readInteger().intValue() : -1;
            Signature signature = this.a.getSignature(new AlgorithmID(derInputStream));
            if (signature != null) {
                aVar.a(signature);
            } else {
                aVar.a(false);
            }
            Name name = new Name(DerCoder.decode(derInputStream));
            Date date = new ChoiceOfTime(DerCoder.decode(derInputStream)).getDate();
            int iNextTag = derInputStream.nextTag();
            this.a.header(iIntValue, name, date, (iNextTag == 23 || iNextTag == 24) ? new ChoiceOfTime(DerCoder.decode(derInputStream)).getDate() : null);
            if (derInputStream.nextTag() == 16) {
                a(derInputStream.readSequence());
            }
            this.a.extensions(derInputStream.nextIsContextSpecific() ? new X509Extensions(DerCoder.decode(derInputStream.readContextSpecific())) : null);
        } catch (CodingException e) {
            throw new CRLException(e.toString());
        } catch (X509ExtensionException e2) {
            throw new CRLException(e2.toString());
        } catch (InvalidKeyException e3) {
            throw new CRLException(e3.toString());
        } catch (NoSuchAlgorithmException e4) {
            throw new CRLException(e4.toString());
        } catch (SignatureException e5) {
            throw new CRLException(e5.toString());
        }
    }

    public CRLListener getListener() {
        return this.a;
    }

    public void parse(InputStream inputStream) throws IOException, CRLException {
        a aVar = new a(inputStream);
        boolean zVerify = false;
        aVar.a(false);
        DerInputStream derInputStream = new DerInputStream(aVar);
        if (derInputStream.nextTag() != 16) {
            throw new CRLException("Invalid CRL. CRL is not a SEQUENCE.");
        }
        DerInputStream sequence = derInputStream.readSequence();
        aVar.a(true);
        if (sequence.nextTag() != 16) {
            throw new CRLException("Invalid CRL. tbsCertList TBSCertList (SEQUENCE) expected.");
        }
        a(sequence.readSequence(), aVar);
        aVar.a(false);
        AlgorithmID algorithmID = new AlgorithmID(sequence);
        if (sequence.nextTag() != 3) {
            throw new CRLException("Invalid CRL. signatureValue BIT STRING expected.");
        }
        byte[] bArr = (byte[]) sequence.readBitString().getValue();
        Signature signatureA = aVar.a();
        if (signatureA != null) {
            try {
                zVerify = signatureA.verify(bArr);
            } catch (SignatureException unused) {
            }
        }
        this.a.signature(algorithmID, bArr, zVerify);
        inputStream.close();
    }
}

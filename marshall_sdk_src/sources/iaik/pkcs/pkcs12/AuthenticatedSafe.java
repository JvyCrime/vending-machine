package iaik.pkcs.pkcs12;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.pkcs.pkcs7.ContentInfo;
import iaik.pkcs.pkcs7.Data;
import iaik.pkcs.pkcs7.EncryptedContentInfo;
import iaik.pkcs.pkcs7.EncryptedData;
import iaik.security.cipher.PBEKeyBMP;
import iaik.utils.InternalErrorException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class AuthenticatedSafe implements ASN1Type {
    public static final int PASSWORD_ENCRYPTED = 2;
    public static final int PUBLIC_KEY_ENCRYPTED = 3;
    public static final int UNENCRYPTED = 1;
    static Class c;
    EncryptedContentInfo a;
    SafeBag[] b;
    private ContentInfo d;
    private int e;
    private int f;

    public AuthenticatedSafe(int i, SafeBag[] safeBagArr) throws PKCSException {
        this(i, safeBagArr, 1024);
    }

    public AuthenticatedSafe(int i, SafeBag[] safeBagArr, int i2) throws PKCSException {
        this.f = i2;
        this.e = i;
        this.b = safeBagArr;
        try {
            byte[] bArrEncodeSafeContents = SafeBag.encodeSafeContents(safeBagArr);
            if (i == 2) {
                EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo(ObjectID.pkcs7_data, bArrEncodeSafeContents);
                this.a = encryptedContentInfo;
                encryptedContentInfo.setBlockSize(this.f);
            } else {
                if (i == 3) {
                    throw new RuntimeException("PUBLIC_KEY_ENCRYPTED not implemented");
                }
                if (i != 1) {
                    throw new PKCSException("Unknown mode!");
                }
                Data data = new Data(bArrEncodeSafeContents);
                data.setBlockSize(this.f);
                this.d = new ContentInfo(data);
            }
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unknown mode! ");
            stringBuffer.append(e);
            throw new PKCSException(stringBuffer.toString());
        }
    }

    public AuthenticatedSafe(ASN1Object aSN1Object) throws PKCSParsingException {
        this.f = 1024;
        try {
            decode(aSN1Object);
        } catch (CodingException e) {
            throw new PKCSParsingException(e.toString());
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    int a() {
        return this.e;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        try {
            ContentInfo contentInfo = new ContentInfo(aSN1Object);
            this.d = contentInfo;
            if (contentInfo.getContentType().equals(ObjectID.pkcs7_encryptedData)) {
                this.e = 2;
                this.a = (EncryptedContentInfo) ((EncryptedData) this.d.getContent()).getEncryptedContentInfo();
            } else {
                if (this.d.getContentType().equals(ObjectID.pkcs7_envelopedData)) {
                    this.e = 3;
                    throw new CodingException("public-key encrypted not implemented.");
                }
                if (!this.d.getContentType().equals(ObjectID.pkcs7_data)) {
                    throw new CodingException("Unknown content type in AuthenticatedSafes.");
                }
                this.e = 1;
                this.b = SafeBag.parseSafeContents(((Data) this.d.getContent()).getData());
            }
        } catch (PKCSParsingException e) {
            throw new CodingException(e.toString());
        }
    }

    public void decrypt(char[] cArr) throws PKCSException, NoSuchAlgorithmException {
        int i = this.e;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    throw new PKCSException("privacy mode PUBLIC_KEY_ENCRYPTED not implemented.");
                }
                throw new PKCSException("Unknown authenticated safes mode.");
            }
            try {
                AlgorithmParameters algorithmParameters = this.a.getContentEncryptionAlgorithm().getAlgorithmParameters("PBE");
                Class clsClass$ = c;
                if (clsClass$ == null) {
                    clsClass$ = class$("javax.crypto.spec.PBEParameterSpec");
                    c = clsClass$;
                }
                this.a.setupCipher(new PBEKeyBMP(cArr), algorithmParameters.getParameterSpec(clsClass$));
                this.b = SafeBag.parseSafeContents(DerCoder.encode(DerCoder.decode(this.a.getContent())));
            } catch (Exception e) {
                throw new PKCSException(e.toString());
            }
        }
        if (this.b == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            SafeBag[] safeBagArr = this.b;
            if (i2 >= safeBagArr.length) {
                return;
            }
            if (safeBagArr[i2] instanceof PKCS8ShroudedKeyBag) {
                try {
                    ((PKCS8ShroudedKeyBag) safeBagArr[i2]).decrypt(cArr);
                } catch (GeneralSecurityException e2) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Unable to decrypt PrivateKey! ");
                    stringBuffer.append(e2);
                    throw new PKCSException(stringBuffer.toString());
                }
            }
            i2++;
        }
    }

    public void encrypt(char[] cArr, AlgorithmID algorithmID) throws PKCSException, NoSuchAlgorithmException {
        int i = this.e;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    throw new RuntimeException("PUBLIC_KEY_ENCRYPTED not implemented");
                }
                throw new PKCSException("Unknown mode!");
            }
            if (!algorithmID.equals(AlgorithmID.pbeWithSHAAnd40BitRC2_CBC) && !algorithmID.equals(AlgorithmID.pbeWithSHAAnd3_KeyTripleDES_CBC)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(algorithmID.getName());
                stringBuffer.append(" not allowed!");
                throw new NoSuchAlgorithmException(stringBuffer.toString());
            }
            try {
                AlgorithmParameters algorithmParameters = algorithmID.getAlgorithmParameters("PBE");
                Class clsClass$ = c;
                if (clsClass$ == null) {
                    clsClass$ = class$("javax.crypto.spec.PBEParameterSpec");
                    c = clsClass$;
                }
                try {
                    this.a.setupCipher(algorithmID, new PBEKeyBMP(cArr), algorithmParameters.getParameterSpec(clsClass$));
                    EncryptedData encryptedData = new EncryptedData(this.a);
                    encryptedData.setBlockSize(this.f);
                    this.d = new ContentInfo(encryptedData);
                } catch (InvalidAlgorithmParameterException e) {
                    throw new PKCSException(e.toString());
                } catch (KeyException e2) {
                    throw new PKCSException(e2.toString());
                }
            } catch (Exception e3) {
                throw new InternalErrorException(e3);
            }
        }
    }

    public SafeBag[] getSafeBags() {
        return this.b;
    }

    public void setBlockSize(int i) {
        this.f = i;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        try {
            return this.d.toASN1Object();
        } catch (PKCSException e) {
            throw new CodingException(e.toString());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String toString() {
        /*
            r5 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "mode: "
            r0.append(r1)
            int r1 = r5.e
            java.lang.String r2 = "\n"
            r3 = 1
            if (r1 == r3) goto L3f
            r3 = 2
            if (r1 == r3) goto L1b
            r3 = 3
            if (r1 == r3) goto L18
            goto L44
        L18:
            java.lang.String r1 = "PUBLIC_KEY_ENCRYPTED\n"
            goto L41
        L1b:
            java.lang.String r1 = "PASSWORD_ENCRYPTED\n"
            r0.append(r1)
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r3 = "Content encrypted with: "
            r1.append(r3)
            iaik.pkcs.pkcs7.EncryptedContentInfo r3 = r5.a
            iaik.asn1.structures.AlgorithmID r3 = r3.getContentEncryptionAlgorithm()
            java.lang.String r3 = r3.getName()
            r1.append(r3)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto L41
        L3f:
            java.lang.String r1 = "UNENCRYPTED\n"
        L41:
            r0.append(r1)
        L44:
            iaik.pkcs.pkcs12.SafeBag[] r1 = r5.b
            if (r1 != 0) goto L4e
            java.lang.String r1 = "No SafeBags or not decrypted yet.\n"
            r0.append(r1)
            goto L79
        L4e:
            r1 = 0
        L4f:
            iaik.pkcs.pkcs12.SafeBag[] r3 = r5.b
            int r3 = r3.length
            if (r1 >= r3) goto L79
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            java.lang.String r4 = "\nSafeBag: "
            r3.append(r4)
            r3.append(r1)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            r0.append(r3)
            iaik.pkcs.pkcs12.SafeBag[] r3 = r5.b
            r3 = r3[r1]
            java.lang.String r3 = r3.toString()
            r0.append(r3)
            int r1 = r1 + 1
            goto L4f
        L79:
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.pkcs.pkcs12.AuthenticatedSafe.toString():java.lang.String");
    }
}

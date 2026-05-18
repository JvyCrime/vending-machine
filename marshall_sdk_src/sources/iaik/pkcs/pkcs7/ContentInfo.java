package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import iaik.utils.TracedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class ContentInfo {
    static Class d;
    static Class e;
    static Class f;
    static Class g;
    static Class h;
    static Class i;
    private static b j = new b();
    ObjectID a;
    Content b;
    byte[] c;

    static {
        ObjectID objectID = ObjectID.pkcs7_data;
        Class clsClass$ = d;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.pkcs.pkcs7.Data");
            d = clsClass$;
        }
        register(objectID, clsClass$);
        ObjectID objectID2 = ObjectID.pkcs7_digestedData;
        Class clsClass$2 = e;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.pkcs.pkcs7.DigestedData");
            e = clsClass$2;
        }
        register(objectID2, clsClass$2);
        ObjectID objectID3 = ObjectID.pkcs7_signedData;
        Class clsClass$3 = f;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.pkcs.pkcs7.SignedData");
            f = clsClass$3;
        }
        register(objectID3, clsClass$3);
        ObjectID objectID4 = ObjectID.pkcs7_envelopedData;
        Class clsClass$4 = g;
        if (clsClass$4 == null) {
            clsClass$4 = class$("iaik.pkcs.pkcs7.EnvelopedData");
            g = clsClass$4;
        }
        register(objectID4, clsClass$4);
        ObjectID objectID5 = ObjectID.pkcs7_signedAndEnvelopedData;
        Class clsClass$5 = h;
        if (clsClass$5 == null) {
            clsClass$5 = class$("iaik.pkcs.pkcs7.SignedAndEnvelopedData");
            h = clsClass$5;
        }
        register(objectID5, clsClass$5);
        ObjectID objectID6 = ObjectID.pkcs7_encryptedData;
        Class clsClass$6 = i;
        if (clsClass$6 == null) {
            clsClass$6 = class$("iaik.pkcs.pkcs7.EncryptedData");
            i = clsClass$6;
        }
        register(objectID6, clsClass$6);
    }

    private ContentInfo() {
    }

    public ContentInfo(ASN1Object aSN1Object) throws PKCSParsingException {
        try {
            decode(new DerInputStream(new ByteArrayInputStream(DerCoder.encode(aSN1Object))));
        } catch (IOException e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error parsing ContentInfo: ");
            stringBuffer.append(e2.getMessage());
            throw new PKCSParsingException(stringBuffer.toString());
        }
    }

    public ContentInfo(ObjectID objectID) {
        this.a = objectID;
        this.b = null;
    }

    public ContentInfo(Content content) {
        this.a = content.getContentType();
        this.b = content;
    }

    public ContentInfo(InputStream inputStream) throws PKCSParsingException, IOException {
        if (inputStream instanceof DerInputStream) {
            decode((DerInputStream) inputStream);
        } else {
            decode(new DerInputStream(inputStream));
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static Content create(ObjectID objectID) throws PKCSException {
        try {
            return (Content) j.create(objectID);
        } catch (InstantiationException unused) {
            throw new PKCSException("No PKCS#7 type registered for the given object ID!");
        }
    }

    public static Content create(ObjectID objectID, ASN1Object aSN1Object) throws PKCSParsingException {
        try {
            Content contentCreate = create(objectID);
            contentCreate.decode(aSN1Object);
            return contentCreate;
        } catch (PKCSException e2) {
            throw new PKCSParsingException(e2.getMessage());
        }
    }

    public static void register(ObjectID objectID, Class cls) {
        j.register(objectID, cls);
    }

    protected void decode(DerInputStream derInputStream) throws PKCSParsingException, IOException {
        DerInputStream sequence = derInputStream.readSequence();
        this.a = sequence.readObjectID();
        if (sequence.nextTag() == -1) {
            this.b = null;
            return;
        }
        DerInputStream contextSpecific = sequence.readContextSpecific();
        try {
            this.b = create(this.a);
            TracedInputStream tracedInputStream = new TracedInputStream(contextSpecific);
            this.b.decode(tracedInputStream);
            this.c = tracedInputStream.getTracedData();
            contextSpecific.readEOC();
        } catch (PKCSException e2) {
            throw new PKCSParsingException(e2.getMessage());
        }
    }

    public void destroyCriticalData() {
    }

    public Content getContent() {
        return this.b;
    }

    public InputStream getContentInputStream() {
        if (this.c == null) {
            return null;
        }
        return new ByteArrayInputStream(this.c);
    }

    public ObjectID getContentType() {
        return this.a;
    }

    public byte[] getEncoded() throws PKCSException {
        return toByteArray();
    }

    public boolean hasContent() {
        return this.b != null;
    }

    public void setContent(Content content) {
        this.a = content.getContentType();
        this.b = content;
    }

    public ASN1Object toASN1Object() throws PKCSException {
        if (this.a == null) {
            throw new PKCSException("Cannot create ASN.1 object. At least the content type must be set!");
        }
        Content content = this.b;
        boolean z = content != null && content.getBlockSize() > 0;
        SEQUENCE sequence = new SEQUENCE(z);
        sequence.addComponent(this.a);
        if (this.b != null) {
            CON_SPEC con_spec = new CON_SPEC(0, this.b.toASN1Object());
            con_spec.setIndefiniteLength(z);
            sequence.addComponent(con_spec);
        }
        return sequence;
    }

    public byte[] toByteArray() throws PKCSException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DerCoder.encodeTo(toASN1Object(), byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new PKCSException(e2.getMessage());
        }
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("ContentType: ");
        stringBuffer2.append(this.a.getName());
        stringBuffer2.append(" {\n");
        stringBuffer.append(stringBuffer2.toString());
        if (this.b == null) {
            string = "No content!\n";
        } else {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append(this.b.toString(z));
            stringBuffer3.append("\n}");
            string = stringBuffer3.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    public void writeTo(OutputStream outputStream) throws PKCSException, IOException {
        DerCoder.encodeTo(toASN1Object(), outputStream);
    }
}

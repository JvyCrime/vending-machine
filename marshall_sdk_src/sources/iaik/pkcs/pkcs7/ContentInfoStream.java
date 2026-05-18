package iaik.pkcs.pkcs7;

import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.DerCoder;
import iaik.asn1.DerInputStream;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.pkcs.PKCSException;
import iaik.pkcs.PKCSParsingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class ContentInfoStream {
    static Class e;
    static Class f;
    static Class g;
    static Class h;
    static Class i;
    static Class j;
    private static b k = new b();
    ObjectID a;
    ContentStream b;
    DerInputStream c;
    boolean d;

    static {
        ObjectID objectID = ObjectID.pkcs7_data;
        Class clsClass$ = e;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.pkcs.pkcs7.DataStream");
            e = clsClass$;
        }
        register(objectID, clsClass$);
        ObjectID objectID2 = ObjectID.pkcs7_digestedData;
        Class clsClass$2 = f;
        if (clsClass$2 == null) {
            clsClass$2 = class$("iaik.pkcs.pkcs7.DigestedDataStream");
            f = clsClass$2;
        }
        register(objectID2, clsClass$2);
        ObjectID objectID3 = ObjectID.pkcs7_signedData;
        Class clsClass$3 = g;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.pkcs.pkcs7.SignedDataStream");
            g = clsClass$3;
        }
        register(objectID3, clsClass$3);
        ObjectID objectID4 = ObjectID.pkcs7_envelopedData;
        Class clsClass$4 = h;
        if (clsClass$4 == null) {
            clsClass$4 = class$("iaik.pkcs.pkcs7.EnvelopedDataStream");
            h = clsClass$4;
        }
        register(objectID4, clsClass$4);
        ObjectID objectID5 = ObjectID.pkcs7_signedAndEnvelopedData;
        Class clsClass$5 = i;
        if (clsClass$5 == null) {
            clsClass$5 = class$("iaik.pkcs.pkcs7.SignedAndEnvelopedDataStream");
            i = clsClass$5;
        }
        register(objectID5, clsClass$5);
        ObjectID objectID6 = ObjectID.pkcs7_encryptedData;
        Class clsClass$6 = j;
        if (clsClass$6 == null) {
            clsClass$6 = class$("iaik.pkcs.pkcs7.EncryptedDataStream");
            j = clsClass$6;
        }
        register(objectID6, clsClass$6);
    }

    private ContentInfoStream() {
        this.c = null;
        this.d = false;
    }

    public ContentInfoStream(ObjectID objectID) {
        this.c = null;
        this.d = false;
        this.a = objectID;
        this.b = null;
    }

    public ContentInfoStream(ContentStream contentStream) {
        this.c = null;
        this.d = false;
        this.a = contentStream.getContentType();
        this.b = contentStream;
    }

    public ContentInfoStream(InputStream inputStream) throws PKCSParsingException, IOException {
        this.c = null;
        this.d = false;
        if (inputStream instanceof DerInputStream) {
            decode((DerInputStream) inputStream);
        } else {
            decode(new DerInputStream(inputStream));
        }
    }

    private void a() throws PKCSParsingException, IOException {
        if (this.c != null) {
            try {
                ContentStream contentStreamCreate = create(this.a);
                this.b = contentStreamCreate;
                contentStreamCreate.decode(this.c);
                this.d = true;
            } catch (PKCSException e2) {
                throw new PKCSParsingException(e2.getMessage());
            }
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    public static ContentStream create(ObjectID objectID) throws PKCSException {
        try {
            return (ContentStream) k.create(objectID);
        } catch (InstantiationException unused) {
            throw new PKCSException("No PKCS#7 type registered for the given object ID!");
        }
    }

    public static ContentStream create(ObjectID objectID, InputStream inputStream) throws PKCSParsingException, IOException {
        try {
            ContentStream contentStreamCreate = create(objectID);
            contentStreamCreate.decode(inputStream);
            return contentStreamCreate;
        } catch (PKCSException e2) {
            throw new PKCSParsingException(e2.getMessage());
        }
    }

    public static void register(ObjectID objectID, Class cls) {
        k.register(objectID, cls);
    }

    protected void decode(DerInputStream derInputStream) throws PKCSParsingException, IOException {
        DerInputStream sequence = derInputStream.readSequence();
        this.a = sequence.readObjectID();
        if (sequence.nextTag() == -1) {
            this.b = null;
        } else {
            this.c = sequence.readContextSpecific();
        }
    }

    public void destroyCriticalData() {
    }

    public ContentStream getContent() throws PKCSParsingException, IOException {
        ContentStream contentStream = this.b;
        if (contentStream != null) {
            return contentStream;
        }
        a();
        return this.b;
    }

    public InputStream getContentInputStream() {
        this.d = true;
        return this.c;
    }

    public ObjectID getContentType() {
        return this.a;
    }

    public boolean hasContent() {
        return (this.b == null && this.c == null) ? false : true;
    }

    public void setContent(ContentStream contentStream) {
        this.a = contentStream.getContentType();
        this.b = contentStream;
    }

    public ASN1Object toASN1Object() throws PKCSException {
        if (this.a == null) {
            throw new PKCSException("Cannot create ASN.1 object. At least the content type must be set!");
        }
        SEQUENCE sequence = new SEQUENCE(true);
        sequence.addComponent(this.a);
        if (!this.d) {
            try {
                a();
            } catch (IOException e2) {
                throw new PKCSException(e2.getMessage());
            }
        }
        if (this.b != null) {
            CON_SPEC con_spec = new CON_SPEC(0, this.b.toASN1Object());
            con_spec.setIndefiniteLength(true);
            sequence.addComponent(con_spec);
        }
        return sequence;
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

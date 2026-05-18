package iaik.x509.ocsp;

import iaik.asn1.ASN1;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.ObjectFactory;

/* JADX INFO: loaded from: classes2.dex */
public class ResponseBytes {
    static Class c;
    private static ObjectFactory d = new ObjectFactory();
    ObjectID a;
    Response b;

    static {
        ObjectID objectID = ObjectID.basicOcspResponse;
        Class clsClass$ = c;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.ocsp.BasicOCSPResponse");
            c = clsClass$;
        }
        register(objectID, clsClass$);
    }

    public ResponseBytes(ASN1Object aSN1Object) throws CodingException, UnknownResponseException {
        decode(aSN1Object);
    }

    public ResponseBytes(Response response) {
        this.b = response;
        this.a = response.getResponseType();
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static Response create(ObjectID objectID) throws InstantiationException {
        return (Response) d.create(objectID);
    }

    public static void register(ObjectID objectID, Class cls) {
        d.register(objectID, cls);
    }

    public void decode(ASN1Object aSN1Object) throws CodingException, UnknownResponseException {
        ObjectID objectID = (ObjectID) aSN1Object.getComponentAt(0);
        this.a = objectID;
        try {
            Response responseCreate = create(objectID);
            this.b = responseCreate;
            responseCreate.decode((byte[]) aSN1Object.getComponentAt(1).getValue());
        } catch (InstantiationException unused) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unknown response type: ");
            stringBuffer.append(this.a.getName());
            throw new UnknownResponseException(stringBuffer.toString(), this.a, new ASN1((byte[]) aSN1Object.getComponentAt(1).getValue()));
        }
    }

    public Response getResponse() {
        return this.b;
    }

    public ObjectID getResponseType() {
        return this.a;
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a);
        sequence.addComponent(new OCTET_STRING(this.b.getEncoded()));
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("responseType: ");
        stringBuffer2.append(this.a.getName());
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        stringBuffer.append(this.b);
        return stringBuffer.toString();
    }
}

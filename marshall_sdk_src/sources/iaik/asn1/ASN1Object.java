package iaik.asn1;

import iaik.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class ASN1Object implements Cloneable {
    protected ASN asnType;
    protected d[] encode_listener;
    protected boolean constructed = false;
    protected boolean indefinite_length = false;
    protected boolean isStringType = false;
    protected boolean stream_mode = false;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    protected ASN1Object() {
    }

    public void addComponent(ASN1Object aSN1Object) throws CodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ASN1: ");
        stringBuffer.append(this.asnType.getName());
        stringBuffer.append(" does not support addComponent()!");
        throw new CodingException(stringBuffer.toString());
    }

    public void addEncodeListener(EncodeListener encodeListener, int i) {
        d dVar = new d(encodeListener, i, -1);
        d[] dVarArr = this.encode_listener;
        if (dVarArr == null) {
            this.encode_listener = new d[]{dVar};
            return;
        }
        d[] dVarArr2 = (d[]) Util.resizeArray(dVarArr, dVarArr.length + 1);
        this.encode_listener = dVarArr2;
        dVarArr2[dVarArr2.length - 1] = dVar;
    }

    public Object clone() {
        ASN1Object aSN1Object = null;
        try {
            ASN1Object aSN1Object2 = (ASN1Object) super.clone();
            try {
                d[] dVarArr = this.encode_listener;
                if (dVarArr == null) {
                    return aSN1Object2;
                }
                aSN1Object2.encode_listener = (d[]) dVarArr.clone();
                return aSN1Object2;
            } catch (CloneNotSupportedException unused) {
                aSN1Object = aSN1Object2;
                return aSN1Object;
            }
        } catch (CloneNotSupportedException unused2) {
        }
    }

    public int countComponents() throws CodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ASN1: ");
        stringBuffer.append(this.asnType.getName());
        stringBuffer.append(" does not support countComponents()!");
        throw new CodingException(stringBuffer.toString());
    }

    protected abstract void decode(int i, InputStream inputStream) throws CodingException, IOException;

    protected abstract void encode(OutputStream outputStream) throws IOException;

    protected void encodeObject(OutputStream outputStream, boolean z) throws IOException {
        if (this.encode_listener != null) {
            int i = 0;
            while (true) {
                try {
                    d[] dVarArr = this.encode_listener;
                    if (i >= dVarArr.length) {
                        break;
                    }
                    d dVar = dVarArr[i];
                    if (dVar.c() == -1) {
                        dVar.a().encodeCalled(this, dVar.b());
                    }
                    i++;
                } catch (CodingException e) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Error from EncodeListener: ");
                    stringBuffer.append(e.getMessage());
                    throw new IOException(stringBuffer.toString());
                }
            }
        }
        this.stream_mode = z;
        encode(outputStream);
    }

    public ASN getAsnType() {
        return this.asnType;
    }

    public ASN1Object getComponentAt(int i) throws CodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ASN1: ");
        stringBuffer.append(this.asnType.getName());
        stringBuffer.append(" does not support getComponentAt(int)!");
        throw new CodingException(stringBuffer.toString());
    }

    public abstract Object getValue();

    public boolean indefiniteLength() {
        return this.indefinite_length;
    }

    public boolean isA(ASN asn) {
        return asn.equals(ASN.CON_SPEC) ? this instanceof CON_SPEC : this.asnType.equals(asn);
    }

    public boolean isConstructed() {
        return this.constructed;
    }

    public boolean isStringType() {
        return this.isStringType;
    }

    public void setIndefiniteLength(boolean z) {
        this.indefinite_length = z;
    }

    public abstract void setValue(Object obj);

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.asnType.getName());
        if (this.constructed) {
            stringBuffer.append("[C]");
        }
        if (this.indefinite_length) {
            stringBuffer.append("[I]");
        }
        stringBuffer.append(" = ");
        return stringBuffer.toString();
    }
}

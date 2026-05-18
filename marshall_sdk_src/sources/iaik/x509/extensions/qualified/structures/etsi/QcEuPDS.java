package iaik.x509.extensions.qualified.structures.etsi;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.IA5String;
import iaik.asn1.ObjectID;
import iaik.asn1.PrintableString;
import iaik.asn1.SEQUENCE;
import iaik.x509.extensions.qualified.structures.QCStatementInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class QcEuPDS extends QCStatementInfo {
    public static final ObjectID statementID = ObjectID.qcEuPDS;
    private ArrayList a;

    public static final class PdsLocation implements ASN1Type {
        private IA5String a;
        private PrintableString b;

        public PdsLocation(ASN1Object aSN1Object) throws CodingException {
            decode(aSN1Object);
        }

        public PdsLocation(String str, String str2) {
            Objects.requireNonNull(str, "url must not be null!");
            Objects.requireNonNull(str2, "language code must not be null!");
            if (str2.length() != 2) {
                throw new IllegalArgumentException("language code must not contain more than two characters!");
            }
            this.a = new IA5String(str);
            this.b = new PrintableString(str2);
        }

        @Override // iaik.asn1.ASN1Type
        public void decode(ASN1Object aSN1Object) throws CodingException {
            if (!aSN1Object.isA(ASN.SEQUENCE)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid ASN.1 type (");
                stringBuffer.append(aSN1Object.getAsnType());
                stringBuffer.append("). PdsLocation must be SEQUENCE!");
                throw new CodingException(stringBuffer.toString());
            }
            int iCountComponents = aSN1Object.countComponents();
            if (iCountComponents != 2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Invalid number of components (");
                stringBuffer2.append(iCountComponents);
                stringBuffer2.append("). Expected 2!");
                throw new CodingException(stringBuffer2.toString());
            }
            ASN1Object componentAt = aSN1Object.getComponentAt(0);
            if (!componentAt.isA(ASN.IA5String)) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid ASN.1 type (");
                stringBuffer3.append(componentAt.getAsnType());
                stringBuffer3.append("). url must be IA5String!");
                throw new CodingException(stringBuffer3.toString());
            }
            this.a = (IA5String) componentAt;
            ASN1Object componentAt2 = aSN1Object.getComponentAt(1);
            if (componentAt2.isA(ASN.PrintableString)) {
                this.b = (PrintableString) componentAt2;
                return;
            }
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Invalid ASN.1 type (");
            stringBuffer4.append(componentAt2.getAsnType());
            stringBuffer4.append("). language code must be PrintableString!");
            throw new CodingException(stringBuffer4.toString());
        }

        public String getLanguage() {
            return (String) this.b.getValue();
        }

        public String getUrl() {
            return (String) this.a.getValue();
        }

        @Override // iaik.asn1.ASN1Type
        public ASN1Object toASN1Object() {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(this.a);
            sequence.addComponent(this.b);
            return sequence;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("PDS ");
            stringBuffer.append(getLanguage());
            stringBuffer.append(": ");
            stringBuffer.append(getUrl());
            return stringBuffer.toString();
        }
    }

    public QcEuPDS() {
        this.a = new ArrayList(3);
    }

    public QcEuPDS(ASN1Object aSN1Object) throws CodingException {
        this();
        decode(aSN1Object);
    }

    public void addPdsLocation(PdsLocation pdsLocation) {
        Objects.requireNonNull(pdsLocation, "pdsLocation must not be null!");
        this.a.add(pdsLocation);
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a.clear();
        if (!aSN1Object.isA(ASN.SEQUENCE)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid ASN.1 type (");
            stringBuffer.append(aSN1Object.getAsnType());
            stringBuffer.append("). PdsLocations must be SEQUENCE OF!");
            throw new CodingException(stringBuffer.toString());
        }
        int iCountComponents = aSN1Object.countComponents();
        for (int i = 0; i < iCountComponents; i++) {
            this.a.add(new PdsLocation(aSN1Object.getComponentAt(i)));
        }
    }

    public PdsLocation[] getPdsLocations() {
        return (PdsLocation[]) this.a.toArray(new PdsLocation[0]);
    }

    public String getPdsUrl(String str) {
        String url = null;
        for (PdsLocation pdsLocation : this.a) {
            if (pdsLocation.getLanguage().equals(str)) {
                url = pdsLocation.getUrl();
            }
        }
        return url;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ObjectID getStatementID() {
        return statementID;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            sequence.addComponent(((PdsLocation) it.next()).toASN1Object());
        }
        return sequence;
    }

    @Override // iaik.x509.extensions.qualified.structures.QCStatementInfo
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next());
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}

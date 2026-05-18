package iaik.utils;

import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.PrintableString;
import iaik.asn1.UTF8String;
import iaik.asn1.structures.Name;
import iaik.asn1.structures.RDN;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class RFC2253NameParser {
    protected StringBuffer nameStrBuf_;
    protected static final Hashtable associations_ = new Hashtable(50);
    static Hashtable a = new Hashtable(50);
    protected int position_ = 0;
    protected ParsedName name_ = new ParsedName();

    protected static class AVA {
        protected boolean hasHexValue_ = false;
        protected String type_;
        protected String value_;

        protected AVA() {
        }
    }

    protected static class ParsedName {
        protected Vector rDNs_ = new Vector();

        protected ParsedName() {
        }
    }

    protected static class ParsedRDN {
        protected Vector aVAs_ = new Vector();

        protected ParsedRDN() {
        }
    }

    protected static class ShortNameOIDAssociation {
        protected ShortNameOIDAssociation() {
        }

        protected ObjectID getOID(String str) throws RFC2253NameParserException {
            ObjectID objectID = (ObjectID) RFC2253NameParser.associations_.get(str);
            if (objectID == null) {
                if (str != null && (objectID = (ObjectID) RFC2253NameParser.a.get(str.toUpperCase())) == null) {
                    objectID = (ObjectID) RFC2253NameParser.a.get(str.toUpperCase(Locale.US));
                }
                if (objectID == null) {
                    throw new RFC2253NameParserException("No Object identifier found for Short Name \"{0}\".", new Object[]{str});
                }
            }
            return objectID;
        }
    }

    static {
        register("CN", ObjectID.commonName, false);
        register("C", ObjectID.country, false);
        register("L", ObjectID.locality, false);
        register("ST", ObjectID.stateOrProvince, false);
        register("STREET", ObjectID.streetAddress, false);
        register("O", ObjectID.organization, false);
        register("OU", ObjectID.organizationalUnit, false);
        register("T", ObjectID.title, false);
        register("SN", ObjectID.surName, false);
        register("DC", ObjectID.domainComponent, false);
        register("UID", ObjectID.userid, false);
        register("serialNumber", ObjectID.serialNumber, false);
        register("postalAddress", ObjectID.postalAddress, false);
        register("postalCode", ObjectID.postalCode, false);
        register("telephoneNumber", ObjectID.telephoneNumber, false);
        register("telexNumber", ObjectID.telexNumber, false);
        register("description", ObjectID.description, false);
        register("givenName", ObjectID.givenName, false);
        register("initials", ObjectID.initials, false);
        register("generationQualifier", ObjectID.generationQualifier, false);
        register("uniqueIdentifier", ObjectID.uniqueIdentifier, false);
        register("dnQualifier", ObjectID.dnQualifier, false);
        register("pseudonym", ObjectID.pseudonym, false);
        register("dateOfBirth", ObjectID.dateOfBirth, false);
        register("placeOfBirth", ObjectID.placeOfBirth, false);
        register("gender", ObjectID.gender, false);
        register("countryOfCitizenship", ObjectID.countryOfCitizenship, false);
        register("countryOfResidence", ObjectID.countryOfResidence, false);
        register("personalTitle", ObjectID.personalTitle, false);
        register("EMail", ObjectID.emailAddress, false);
        register("E", ObjectID.emailAddress, false);
        register("EA", ObjectID.emailAddress, false);
        register("MAIL", ObjectID.emailAddress, false);
        register("EMAIL", ObjectID.emailAddress, false);
        register("emailAddress", ObjectID.emailAddress, false);
        register("EmailAddress", ObjectID.emailAddress, false);
        register("EMAILADDRESS", ObjectID.emailAddress, false);
    }

    public RFC2253NameParser(String str) {
        try {
            this.nameStrBuf_ = new StringBuffer(str);
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    public static void register(String str, ObjectID objectID) {
        register(str, objectID, true);
    }

    public static void register(String str, ObjectID objectID, boolean z) {
        associations_.put(str, objectID);
        if (str == null || z) {
            return;
        }
        a.put(str.toUpperCase(), objectID);
    }

    public Name parse() throws RFC2253NameParserException {
        boolean z;
        Object uTF8String;
        if (this.nameStrBuf_.length() == 0) {
            return new Name();
        }
        do {
            this.name_.rDNs_.addElement(parseRDN());
            skipSpace();
            if (this.position_ == this.nameStrBuf_.length()) {
                z = false;
            } else {
                if (this.nameStrBuf_.charAt(this.position_) != ',' && this.nameStrBuf_.charAt(this.position_) != ';') {
                    throw new RFC2253NameParserException("RDNs are not separated by comma or semicolon (position {0,number,integer}).", new Object[]{new Integer(this.position_)});
                }
                this.position_++;
                z = true;
            }
            skipSpace();
        } while (z);
        Name name = new Name();
        ShortNameOIDAssociation shortNameOIDAssociation = new ShortNameOIDAssociation();
        for (int size = this.name_.rDNs_.size() - 1; size >= 0; size--) {
            RDN rdn = new RDN();
            ParsedRDN parsedRDN = (ParsedRDN) this.name_.rDNs_.elementAt(size);
            for (int i = 0; i < parsedRDN.aVAs_.size(); i++) {
                AVA ava = (AVA) parsedRDN.aVAs_.elementAt(i);
                ObjectID oid = "0123456789".indexOf(ava.type_.charAt(0)) == -1 ? shortNameOIDAssociation.getOID(ava.type_) : new ObjectID(ava.type_);
                if (ava.hasHexValue_) {
                    byte[] bytes = null;
                    try {
                        bytes = ava.value_.getBytes("ISO8859_1");
                        uTF8String = DerCoder.decode(bytes);
                    } catch (Exception unused) {
                        StringBuffer stringBuffer = new StringBuffer();
                        if (bytes != null) {
                            for (int i2 = 0; i2 < bytes.length; i2++) {
                                if (bytes[i2] < 16) {
                                    stringBuffer.append("0");
                                }
                                stringBuffer.append(Integer.toHexString(bytes[i2]));
                            }
                        }
                        throw new RFC2253NameParserException("Cannot create AVA due to invalid hex value in attribute value (\"{0}\")", new Object[]{stringBuffer.toString()});
                    }
                } else {
                    uTF8String = ava.value_;
                }
                if (uTF8String instanceof String) {
                    String str = (String) uTF8String;
                    if (!PrintableString.isPrintableString(str)) {
                        uTF8String = new UTF8String(str);
                    }
                }
                rdn.addAVA(new iaik.asn1.structures.AVA(oid, uTF8String), false);
            }
            name.addRDN(rdn);
        }
        return name;
    }

    protected AVA parseAVA() throws RFC2253NameParserException {
        String attrType = parseAttrType();
        skipSpace();
        if (this.position_ >= this.nameStrBuf_.length() || this.nameStrBuf_.charAt(this.position_) != '=') {
            throw new RFC2253NameParserException("Cannot find attribute value after having parsed an equal (position {0,number,integer}).", new Object[]{new Integer(this.position_)});
        }
        this.position_++;
        skipSpace();
        AVA attrValue = parseAttrValue();
        attrValue.type_ = attrType;
        skipSpace();
        return attrValue;
    }

    protected String parseAttrType() throws RFC2253NameParserException {
        int i;
        int i2;
        boolean z;
        int i3;
        skipOIDIntro();
        if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".indexOf(this.nameStrBuf_.charAt(this.position_)) != -1) {
            i = this.position_;
            while (i < this.nameStrBuf_.length() && (this.nameStrBuf_.charAt(i) == '-' || "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".indexOf(this.nameStrBuf_.charAt(i)) != -1 || "0123456789".indexOf(this.nameStrBuf_.charAt(i)) != -1)) {
                i++;
            }
            if (i - this.position_ < 1) {
                throw new RFC2253NameParserException("Attribute type short name is corrupt (position {0,number,integer}).", new Object[]{new Integer(this.position_)});
            }
        } else {
            i = this.position_;
            int i4 = i;
            while (true) {
                if (i < this.nameStrBuf_.length() && "0123456789".indexOf(this.nameStrBuf_.charAt(i)) != -1) {
                    i++;
                } else {
                    if (i - i4 < 1) {
                        throw new RFC2253NameParserException("Object Identifier representing attribute type is corrupt (position {0,number,integer}).", new Object[]{new Integer(this.position_)});
                    }
                    if (this.nameStrBuf_.charAt(i) == '.') {
                        i3 = i + 1;
                        i2 = i3;
                        z = true;
                    } else {
                        i2 = i4;
                        z = false;
                        i3 = i;
                    }
                    if (!z) {
                        break;
                    }
                    i = i3;
                    i4 = i2;
                }
            }
        }
        String strSubstring = this.nameStrBuf_.toString().substring(this.position_, i);
        this.position_ = i;
        return strSubstring;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected iaik.utils.RFC2253NameParser.AVA parseAttrValue() throws iaik.utils.RFC2253NameParserException {
        /*
            Method dump skipped, instruction units count: 495
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.utils.RFC2253NameParser.parseAttrValue():iaik.utils.RFC2253NameParser$AVA");
    }

    protected char parseHexPair() throws RFC2253NameParserException {
        int length = this.nameStrBuf_.length();
        int i = this.position_;
        if (length - i < 2) {
            throw new RFC2253NameParserException("Cannot parse hex pair (position {0,number,integer}).", new Object[]{new Integer(this.position_)});
        }
        if ("0123456789ABCDEFabcdef".indexOf(this.nameStrBuf_.charAt(i)) == -1 || "0123456789ABCDEFabcdef".indexOf(this.nameStrBuf_.charAt(this.position_ + 1)) == -1) {
            throw new RFC2253NameParserException("Invalid hex pair character found when parsing a hex pair (position {0,number,integer}).", new Object[]{new Integer(this.position_)});
        }
        String string = this.nameStrBuf_.toString();
        int i2 = this.position_;
        char c = (char) Integer.parseInt(string.substring(i2, i2 + 2), 16);
        this.position_ += 2;
        return c;
    }

    protected char parseHexPairEscapeSequence() throws RFC2253NameParserException {
        int i;
        int hexPair;
        char hexPair2 = parseHexPair();
        if ((hexPair2 & 128) == 0) {
            return hexPair2;
        }
        if ((hexPair2 & 224) == 224) {
            this.position_++;
            char hexPair3 = parseHexPair();
            this.position_++;
            i = ((hexPair2 & 15) << 12) + ((hexPair3 & '?') << 6);
            hexPair = parseHexPair() & '?';
        } else {
            this.position_++;
            i = (hexPair2 & 31) << 6;
            hexPair = parseHexPair() & '?';
        }
        return (char) (i + hexPair);
    }

    protected ParsedRDN parseRDN() throws RFC2253NameParserException {
        boolean z;
        ParsedRDN parsedRDN = new ParsedRDN();
        do {
            parsedRDN.aVAs_.addElement(parseAVA());
            skipSpace();
            z = false;
            if (this.position_ < this.nameStrBuf_.length() && this.nameStrBuf_.charAt(this.position_) == '+') {
                this.position_++;
                skipSpace();
                if (this.position_ == this.nameStrBuf_.length()) {
                    throw new RFC2253NameParserException("Cannot find another AVA after having parsed a plus (position {0,number,integer}).", new Object[]{new Integer(this.position_)});
                }
                z = true;
            }
        } while (z);
        return parsedRDN;
    }

    protected void skipOIDIntro() {
        if (this.nameStrBuf_.length() - this.position_ < 4) {
            return;
        }
        String string = this.nameStrBuf_.toString();
        int i = this.position_;
        if (string.substring(i, i + 4).equalsIgnoreCase("oid.")) {
            this.position_ += 4;
        }
    }

    protected void skipSpace() {
        while (this.position_ < this.nameStrBuf_.length() && this.nameStrBuf_.charAt(this.position_) == ' ') {
            this.position_++;
        }
    }
}

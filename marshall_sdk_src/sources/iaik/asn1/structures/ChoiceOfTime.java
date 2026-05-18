package iaik.asn1.structures;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.GeneralizedTime;
import iaik.asn1.UTCTime;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class ChoiceOfTime {
    private static final TimeZone a = TimeZone.getTimeZone("GMT");
    private ASN1Object b;

    public ChoiceOfTime() {
        this(new Date(), false);
    }

    public ChoiceOfTime(ASN1Object aSN1Object) throws CodingException {
        ASN asnType = aSN1Object.getAsnType();
        if (!asnType.equals(ASN.UTCTime) && !asnType.equals(ASN.GeneralizedTime)) {
            throw new CodingException("Must be a UTCTime or a GeneralizedTime");
        }
        this.b = aSN1Object;
    }

    public ChoiceOfTime(String str, ASN asn) throws ParseException {
        ASN1Object generalizedTime;
        if (asn.equals(ASN.UTCTime)) {
            generalizedTime = new UTCTime(str);
        } else {
            if (!asn.equals(ASN.GeneralizedTime)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot create ChoiceOfTime for encoding format ");
                stringBuffer.append(asn);
                stringBuffer.append(". Only UTCTime and GeneralizedTime are allowed!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            generalizedTime = new GeneralizedTime(str);
        }
        this.b = generalizedTime;
    }

    public ChoiceOfTime(Date date) {
        this(date, false);
    }

    public ChoiceOfTime(Date date, ASN asn) {
        this(date, asn, true);
    }

    public ChoiceOfTime(Date date, ASN asn, boolean z) {
        if (asn.equals(ASN.UTCTime) || asn.equals(ASN.GeneralizedTime)) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar(a);
            gregorianCalendar.clear();
            gregorianCalendar.setTime(date);
            a(gregorianCalendar, asn, z);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot create ChoiceOfTime for encoding format ");
        stringBuffer.append(asn);
        stringBuffer.append(". Only UTCTime and GeneralizedTime are allowed!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public ChoiceOfTime(Date date, boolean z) {
        this(date, z, true);
    }

    public ChoiceOfTime(Date date, boolean z, boolean z2) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(a);
        gregorianCalendar.clear();
        gregorianCalendar.setTime(date);
        a(gregorianCalendar, (!z || gregorianCalendar.get(1) < 2050) ? ASN.UTCTime : ASN.GeneralizedTime, z2);
    }

    private static String a(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("0");
        stringBuffer.append(String.valueOf(i));
        return stringBuffer.toString().substring(r2.length() - 2);
    }

    private static String a(String str) {
        int length = str.length() - 1;
        while (length > 0 && str.charAt(length) == '0') {
            length--;
        }
        return str.substring(0, length + 1);
    }

    private Calendar a() throws IllegalArgumentException {
        int i;
        int i2;
        int iIntValue;
        int i3;
        String str;
        int i4;
        String str2 = (String) this.b.getValue();
        ASN asnType = this.b.getAsnType();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(a);
        gregorianCalendar.clear();
        int length = 0;
        int i5 = 0;
        while (length < str2.length() - 1) {
            try {
                switch (i5) {
                    case 0:
                        if (!asnType.equals(ASN.UTCTime)) {
                            i = length + 4;
                            gregorianCalendar.set(1, new Integer(str2.substring(length, i)).intValue());
                            length = i;
                            i5++;
                        } else {
                            int i6 = length + 2;
                            int i7 = Integer.parseInt(str2.substring(length, i6));
                            gregorianCalendar.set(1, i7 + (i7 >= 50 ? 1900 : 2000));
                            length = i6;
                            i5++;
                        }
                        break;
                    case 1:
                        i = length + 2;
                        gregorianCalendar.set(2, new Integer(str2.substring(length, i)).intValue() - 1);
                        length = i;
                        i5++;
                        break;
                    case 2:
                        i2 = 5;
                        i = length + 2;
                        iIntValue = new Integer(str2.substring(length, i)).intValue();
                        gregorianCalendar.set(i2, iIntValue);
                        length = i;
                        i5++;
                        break;
                    case 3:
                        i3 = length + 2;
                        gregorianCalendar.set(11, new Integer(str2.substring(length, i3)).intValue());
                        length = i3;
                        i5++;
                        break;
                    case 4:
                        i3 = length + 2;
                        gregorianCalendar.set(12, new Integer(str2.substring(length, i3)).intValue());
                        length = i3;
                        i5++;
                        break;
                    case 5:
                        if (str2.charAt(length) == '+' || str2.charAt(length) == '-') {
                            i5++;
                        } else {
                            i2 = 13;
                            i = length + 2;
                            try {
                                iIntValue = new Integer(str2.substring(length, i)).intValue();
                                gregorianCalendar.set(i2, iIntValue);
                                length = i;
                                i5++;
                            } catch (NumberFormatException unused) {
                                length = i;
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append("Error parsing date: ");
                                stringBuffer.append(length);
                                throw new IllegalArgumentException(stringBuffer.toString());
                            }
                        }
                        break;
                    case 6:
                        if ((str2.charAt(length) == '.' || str2.charAt(length) == ',') && asnType.equals(ASN.GeneralizedTime)) {
                            int i8 = length + 1;
                            int i9 = i8;
                            while (i9 < str2.length() && str2.charAt(i9) != '+' && str2.charAt(i9) != '-' && str2.charAt(i9) != 'Z') {
                                try {
                                    i9++;
                                } catch (NumberFormatException unused2) {
                                    length = i9;
                                    StringBuffer stringBuffer2 = new StringBuffer();
                                    stringBuffer2.append("Error parsing date: ");
                                    stringBuffer2.append(length);
                                    throw new IllegalArgumentException(stringBuffer2.toString());
                                }
                            }
                            if (i9 > i8) {
                                String strSubstring = str2.substring(i8, Math.min(i8 + 3, i9));
                                int length2 = 3 - strSubstring.length();
                                if (length2 != 1) {
                                    str = length2 == 2 ? "00" : "0";
                                    gregorianCalendar.set(14, new Integer(strSubstring).intValue());
                                }
                                strSubstring = strSubstring.concat(str);
                                gregorianCalendar.set(14, new Integer(strSubstring).intValue());
                            }
                            length = i9;
                        }
                        i5++;
                        break;
                    case 7:
                        if (str2.charAt(length) == '+') {
                            int i10 = length + 1;
                            int i11 = i10 + 2;
                            try {
                                gregorianCalendar.add(11, -new Integer(str2.substring(i10, i11)).intValue());
                                i4 = i11 + 2;
                            } catch (NumberFormatException unused3) {
                                length = i11;
                            }
                            try {
                                gregorianCalendar.add(12, -new Integer(str2.substring(i11, i4)).intValue());
                            } catch (NumberFormatException unused4) {
                                length = i4;
                                StringBuffer stringBuffer22 = new StringBuffer();
                                stringBuffer22.append("Error parsing date: ");
                                stringBuffer22.append(length);
                                throw new IllegalArgumentException(stringBuffer22.toString());
                            }
                            break;
                        } else if (str2.charAt(length) == '-') {
                            int i12 = length + 1;
                            int i13 = i12 + 2;
                            gregorianCalendar.add(11, new Integer(str2.substring(i12, i13)).intValue());
                            i4 = i13 + 2;
                            gregorianCalendar.add(12, new Integer(str2.substring(i13, i4)).intValue());
                        }
                    case 8:
                        length = str2.length();
                        i5++;
                        break;
                    default:
                        i5++;
                        break;
                }
            } catch (NumberFormatException unused5) {
            }
        }
        return gregorianCalendar;
    }

    private void a(Calendar calendar, ASN asn, boolean z) {
        int i;
        StringBuffer stringBuffer = asn.equals(ASN.UTCTime) ? new StringBuffer(a(calendar.get(1))) : new StringBuffer(String.valueOf(calendar.get(1)));
        stringBuffer.append(a(calendar.get(2) + 1));
        stringBuffer.append(a(calendar.get(5)));
        stringBuffer.append(a(calendar.get(11)));
        stringBuffer.append(a(calendar.get(12)));
        stringBuffer.append(a(calendar.get(13)));
        if (z && asn.equals(ASN.GeneralizedTime) && (i = calendar.get(14)) > 0) {
            stringBuffer.append('.');
            stringBuffer.append(a(String.valueOf(i)));
        }
        stringBuffer.append('Z');
        this.b = asn.equals(ASN.UTCTime) ? new UTCTime(stringBuffer.toString()) : new GeneralizedTime(stringBuffer.toString());
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof ChoiceOfTime)) {
                return false;
            }
            ChoiceOfTime choiceOfTime = (ChoiceOfTime) obj;
            ASN1Object aSN1Object = this.b;
            if (aSN1Object == null || choiceOfTime.b == null) {
                if (aSN1Object != null || choiceOfTime.b != null) {
                    return false;
                }
            } else {
                if (!aSN1Object.getAsnType().equals(choiceOfTime.b.getAsnType())) {
                    return false;
                }
                if (!this.b.getValue().equals(choiceOfTime.b.getValue())) {
                    return getDate().equals(choiceOfTime.getDate());
                }
            }
        }
        return true;
    }

    public Date getDate() {
        return a().getTime();
    }

    public ASN getEncodingType() {
        return this.b.getAsnType();
    }

    public int hashCode() {
        Object value = this.b.getValue();
        if (value == null) {
            return -1;
        }
        return value.hashCode();
    }

    public void setEncodingType(ASN asn) throws IllegalArgumentException {
        if (asn.equals(getEncodingType())) {
            return;
        }
        a(a(), asn, true);
    }

    public ASN1Object toASN1Object() {
        return this.b;
    }

    public String toString() {
        return getDate().toString();
    }
}

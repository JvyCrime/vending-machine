package org.ksoap2.serialization;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.math.BigDecimal;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class MarshalFloat implements Marshal {
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$math$BigDecimal;

    @Override // org.ksoap2.serialization.Marshal
    public Object readInstance(XmlPullParser xmlPullParser, String str, String str2, PropertyInfo propertyInfo) throws XmlPullParserException, IOException {
        String strNextText = xmlPullParser.nextText();
        if (str2.equals(TypedValues.Custom.S_FLOAT)) {
            return new Float(strNextText);
        }
        if (str2.equals("double")) {
            return new Double(strNextText);
        }
        if (str2.equals("decimal")) {
            return new BigDecimal(strNextText);
        }
        throw new RuntimeException("float, double, or decimal expected");
    }

    @Override // org.ksoap2.serialization.Marshal
    public void writeInstance(XmlSerializer xmlSerializer, Object obj) throws IOException {
        xmlSerializer.text(obj.toString());
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // org.ksoap2.serialization.Marshal
    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        String str = soapSerializationEnvelope.xsd;
        Class clsClass$ = class$java$lang$Float;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Float");
            class$java$lang$Float = clsClass$;
        }
        soapSerializationEnvelope.addMapping(str, TypedValues.Custom.S_FLOAT, clsClass$, this);
        String str2 = soapSerializationEnvelope.xsd;
        Class clsClass$2 = class$java$lang$Double;
        if (clsClass$2 == null) {
            clsClass$2 = class$("java.lang.Double");
            class$java$lang$Double = clsClass$2;
        }
        soapSerializationEnvelope.addMapping(str2, "double", clsClass$2, this);
        String str3 = soapSerializationEnvelope.xsd;
        Class clsClass$3 = class$java$math$BigDecimal;
        if (clsClass$3 == null) {
            clsClass$3 = class$("java.math.BigDecimal");
            class$java$math$BigDecimal = clsClass$3;
        }
        soapSerializationEnvelope.addMapping(str3, "decimal", clsClass$3, this);
    }
}

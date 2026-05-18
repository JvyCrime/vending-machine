package org.ksoap2.serialization;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
class DM implements Marshal {
    DM() {
    }

    @Override // org.ksoap2.serialization.Marshal
    public Object readInstance(XmlPullParser xmlPullParser, String str, String str2, PropertyInfo propertyInfo) throws XmlPullParserException, IOException {
        String strNextText = xmlPullParser.nextText();
        char cCharAt = str2.charAt(0);
        if (cCharAt == 'b') {
            return new Boolean(SoapEnvelope.stringToBoolean(strNextText));
        }
        if (cCharAt == 'i') {
            return new Integer(Integer.parseInt(strNextText));
        }
        if (cCharAt == 'l') {
            return new Long(Long.parseLong(strNextText));
        }
        if (cCharAt == 's') {
            return strNextText;
        }
        throw new RuntimeException();
    }

    @Override // org.ksoap2.serialization.Marshal
    public void writeInstance(XmlSerializer xmlSerializer, Object obj) throws IOException {
        if (obj instanceof AttributeContainer) {
            AttributeContainer attributeContainer = (AttributeContainer) obj;
            int attributeCount = attributeContainer.getAttributeCount();
            for (int i = 0; i < attributeCount; i++) {
                AttributeInfo attributeInfo = new AttributeInfo();
                attributeContainer.getAttributeInfo(i, attributeInfo);
                xmlSerializer.attribute(attributeInfo.getNamespace(), attributeInfo.getName(), attributeInfo.getValue().toString());
            }
        }
        xmlSerializer.text(obj.toString());
    }

    @Override // org.ksoap2.serialization.Marshal
    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, "int", PropertyInfo.INTEGER_CLASS, this);
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, "long", PropertyInfo.LONG_CLASS, this);
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, TypedValues.Custom.S_STRING, PropertyInfo.STRING_CLASS, this);
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, TypedValues.Custom.S_BOOLEAN, PropertyInfo.BOOLEAN_CLASS, this);
    }
}

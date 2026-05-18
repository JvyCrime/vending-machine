package org.ksoap2.serialization;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class MarshalHashtable implements Marshal {
    public static final Class HASHTABLE_CLASS = new Hashtable().getClass();
    public static final String NAME = "Map";
    public static final String NAMESPACE = "http://xml.apache.org/xml-soap";
    SoapSerializationEnvelope envelope;

    @Override // org.ksoap2.serialization.Marshal
    public Object readInstance(XmlPullParser xmlPullParser, String str, String str2, PropertyInfo propertyInfo) throws XmlPullParserException, IOException {
        Hashtable hashtable = new Hashtable();
        String name = xmlPullParser.getName();
        while (xmlPullParser.nextTag() != 3) {
            ItemSoapObject itemSoapObject = new ItemSoapObject(this, hashtable);
            xmlPullParser.require(2, null, "item");
            xmlPullParser.nextTag();
            Object obj = this.envelope.read(xmlPullParser, itemSoapObject, 0, null, null, PropertyInfo.OBJECT_TYPE);
            xmlPullParser.nextTag();
            if (obj != null) {
                itemSoapObject.setProperty(0, obj);
            }
            Object obj2 = this.envelope.read(xmlPullParser, itemSoapObject, 1, null, null, PropertyInfo.OBJECT_TYPE);
            xmlPullParser.nextTag();
            if (obj2 != null) {
                itemSoapObject.setProperty(1, obj2);
            }
            xmlPullParser.require(3, null, "item");
        }
        xmlPullParser.require(3, null, name);
        return hashtable;
    }

    @Override // org.ksoap2.serialization.Marshal
    public void writeInstance(XmlSerializer xmlSerializer, Object obj) throws IOException {
        Hashtable hashtable = (Hashtable) obj;
        SoapObject soapObject = new SoapObject(null, null);
        soapObject.addProperty("key", (Object) null);
        soapObject.addProperty("value", (Object) null);
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            xmlSerializer.startTag("", "item");
            Object objNextElement = enumerationKeys.nextElement();
            soapObject.setProperty(0, objNextElement);
            soapObject.setProperty(1, hashtable.get(objNextElement));
            this.envelope.writeObjectBody(xmlSerializer, soapObject);
            xmlSerializer.endTag("", "item");
        }
    }

    class ItemSoapObject extends SoapObject {
        Hashtable h;
        int resolvedIndex;
        private final /* synthetic */ MarshalHashtable this$0;

        ItemSoapObject(MarshalHashtable marshalHashtable, Hashtable hashtable) {
            super(null, null);
            this.this$0 = marshalHashtable;
            this.resolvedIndex = -1;
            this.h = hashtable;
            addProperty("key", (Object) null);
            addProperty("value", (Object) null);
        }

        @Override // org.ksoap2.serialization.SoapObject, org.ksoap2.serialization.KvmSerializable
        public void setProperty(int i, Object obj) {
            int i2 = this.resolvedIndex;
            if (i2 == -1) {
                super.setProperty(i, obj);
                this.resolvedIndex = i;
                return;
            }
            Object property = getProperty(i2 == 0 ? 0 : 1);
            if (i == 0) {
                this.h.put(obj, property);
            } else {
                this.h.put(property, obj);
            }
        }
    }

    @Override // org.ksoap2.serialization.Marshal
    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        this.envelope = soapSerializationEnvelope;
        soapSerializationEnvelope.addMapping(NAMESPACE, NAME, HASHTABLE_CLASS, this);
    }
}

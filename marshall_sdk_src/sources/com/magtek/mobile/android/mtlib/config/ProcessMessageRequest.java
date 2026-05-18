package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class ProcessMessageRequest extends BaseObject {
    public static final int PROP_PAYLOAD = 0;
    public RequestPayload Payload;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 1;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        if (i != 0) {
            return null;
        }
        return this.Payload;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        if (i != 0) {
            return;
        }
        propertyInfo.name = "Payload";
        propertyInfo.type = this.Payload.getClass();
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void setProperty(int i, Object obj) {
        if (i != 0) {
            return;
        }
        this.Payload = (RequestPayload) obj;
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "ProcessMessageRequest", getClass());
        new RequestPayload().register(soapSerializationEnvelope);
    }
}

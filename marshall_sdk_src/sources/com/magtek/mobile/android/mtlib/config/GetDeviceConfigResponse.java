package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class GetDeviceConfigResponse extends BaseObject {
    public ProcessMessageResponse GetDeviceConfigResult;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 1;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        return this.GetDeviceConfigResult;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        propertyInfo.name = "GetDeviceConfigResult";
        propertyInfo.type = new ProcessMessageResponse().getClass();
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void setProperty(int i, Object obj) {
        this.GetDeviceConfigResult = (ProcessMessageResponse) obj;
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "GetDeviceConfigResponse", getClass());
        new ProcessMessageResponse().register(soapSerializationEnvelope);
    }
}

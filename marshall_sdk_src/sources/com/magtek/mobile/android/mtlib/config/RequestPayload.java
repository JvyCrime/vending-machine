package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class RequestPayload extends BaseObject {
    public static final int PROP_DEVICEINFO = 0;
    public static final int PROP_READERTYPE = 1;
    public static final int PROP_VERSION = 2;
    public String Version = "";
    public SCRAConfigurationDeviceInfo DeviceInfo = null;
    public SCRAConfigurationReaderType ReaderType = null;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 3;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        if (i == 0) {
            return this.DeviceInfo;
        }
        if (i == 1) {
            return this.ReaderType;
        }
        if (i != 2) {
            return null;
        }
        return this.Version;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        if (i == 0) {
            propertyInfo.name = "DeviceInfo";
            propertyInfo.type = this.DeviceInfo.getClass();
        } else if (i == 1) {
            propertyInfo.name = "ReaderType";
            propertyInfo.type = this.ReaderType.getClass();
        } else {
            if (i != 2) {
                return;
            }
            propertyInfo.name = "Version";
            propertyInfo.type = this.Version.getClass();
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void setProperty(int i, Object obj) {
        if (i == 0) {
            this.DeviceInfo = (SCRAConfigurationDeviceInfo) obj;
        } else if (i == 1) {
            this.ReaderType = (SCRAConfigurationReaderType) obj;
        } else {
            if (i != 2) {
                return;
            }
            this.Version = (String) obj;
        }
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "RequestPayload", getClass());
        new SCRAConfigurationDeviceInfo().register(soapSerializationEnvelope);
        new SCRAConfigurationReaderType().register(soapSerializationEnvelope);
    }
}

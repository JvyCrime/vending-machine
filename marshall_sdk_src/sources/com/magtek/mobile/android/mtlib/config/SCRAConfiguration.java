package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class SCRAConfiguration extends BaseObject {
    public ArrayOfConfigParam ConfigParams;
    public SCRAConfigurationDeviceInfo DeviceInfo;
    public SCRAConfigurationReaderType ReaderType;
    public StatusCode StatusCode;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 4;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        if (i == 0) {
            return this.ReaderType;
        }
        if (i == 1) {
            return this.DeviceInfo;
        }
        if (i == 2) {
            return this.ConfigParams;
        }
        if (i != 3) {
            return null;
        }
        return this.StatusCode;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        if (i == 0) {
            propertyInfo.name = "ReaderType";
            propertyInfo.type = this.ReaderType.getClass();
            return;
        }
        if (i == 1) {
            propertyInfo.name = "DeviceInfo";
            propertyInfo.type = this.DeviceInfo.getClass();
        } else if (i == 2) {
            propertyInfo.name = "ConfigParams";
            propertyInfo.type = this.ConfigParams.getClass();
        } else {
            if (i != 3) {
                return;
            }
            propertyInfo.name = "StatusCode";
            propertyInfo.type = this.StatusCode.getClass();
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void setProperty(int i, Object obj) {
        if (i == 0) {
            this.ReaderType = (SCRAConfigurationReaderType) obj;
            return;
        }
        if (i == 1) {
            this.DeviceInfo = (SCRAConfigurationDeviceInfo) obj;
        } else if (i == 2) {
            this.ConfigParams = (ArrayOfConfigParam) obj;
        } else {
            if (i != 3) {
                return;
            }
            this.StatusCode = (StatusCode) obj;
        }
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "SCRAConfiguration", getClass());
        new SCRAConfigurationReaderType().register(soapSerializationEnvelope);
        new SCRAConfigurationDeviceInfo().register(soapSerializationEnvelope);
        new ArrayOfConfigParam().register(soapSerializationEnvelope);
        new StatusCode().register(soapSerializationEnvelope);
    }
}

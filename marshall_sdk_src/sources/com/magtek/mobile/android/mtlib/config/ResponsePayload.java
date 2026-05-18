package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class ResponsePayload extends BaseObject {
    public static final int PROP_SCRACONFIGURATION = 0;
    public static final int PROP_STATUSCODE = 1;
    public static final int PROP_VERSION = 2;
    public String Version = "";
    public StatusCode StatusCode = null;
    public ArrayOfSCRAConfiguration SCRAConfigurations = null;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 3;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        if (i == 0) {
            return this.SCRAConfigurations;
        }
        if (i == 1) {
            return this.StatusCode;
        }
        if (i != 2) {
            return null;
        }
        return this.Version;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        if (i == 0) {
            propertyInfo.name = "SCRAConfigurations";
            propertyInfo.type = this.SCRAConfigurations.getClass();
        } else if (i == 1) {
            propertyInfo.name = "StatusCode";
            propertyInfo.type = this.StatusCode.getClass();
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
            this.SCRAConfigurations = (ArrayOfSCRAConfiguration) obj;
        } else if (i == 1) {
            this.StatusCode = (StatusCode) obj;
        } else {
            if (i != 2) {
                return;
            }
            this.Version = (String) obj;
        }
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "ResponsePayload", getClass());
        new ArrayOfSCRAConfiguration().register(soapSerializationEnvelope);
        new StatusCode().register(soapSerializationEnvelope);
    }
}

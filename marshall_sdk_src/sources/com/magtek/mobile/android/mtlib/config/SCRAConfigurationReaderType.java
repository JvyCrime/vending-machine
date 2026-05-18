package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class SCRAConfigurationReaderType extends BaseObject {
    public static final int PROP_NAME = 2;
    public static final int PROP_SDK = 3;
    public static final int PROP_TYPE = 1;
    public static final int PROP_VERSION = 0;
    private String Name;
    private String SDK;
    private String Type;
    private String Version;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 3;
    }

    public SCRAConfigurationReaderType() {
        this.Version = "";
        this.Type = "";
        this.Name = "";
        this.SDK = "";
    }

    public SCRAConfigurationReaderType(SoapObject soapObject) {
        try {
            this.Version = "";
            this.Type = "";
            this.Name = "";
            this.SDK = "";
            this.Name = soapObject.getPrimitivePropertyAsString("Name");
            this.Version = soapObject.getPrimitivePropertyAsString("Version");
            this.Type = soapObject.getPrimitivePropertyAsString("Type");
            this.SDK = soapObject.getPrimitivePropertyAsString("SDK");
        } catch (Exception unused) {
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        if (i == 0) {
            return this.Version;
        }
        if (i == 1) {
            return this.Type;
        }
        if (i == 2) {
            return this.Name;
        }
        if (i != 3) {
            return null;
        }
        return this.SDK;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        if (i == 0) {
            propertyInfo.name = "Version";
            propertyInfo.type = this.Version.getClass();
            return;
        }
        if (i == 1) {
            propertyInfo.name = "Type";
            propertyInfo.type = this.Type.getClass();
        } else if (i == 2) {
            propertyInfo.name = "Name";
            propertyInfo.type = this.Name.getClass();
        } else {
            if (i != 3) {
                return;
            }
            propertyInfo.name = "SDK";
            propertyInfo.type = this.SDK.getClass();
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void setProperty(int i, Object obj) {
        if (i == 0) {
            this.Version = (String) obj;
            return;
        }
        if (i == 1) {
            this.Type = (String) obj;
        } else if (i == 2) {
            this.Name = (String) obj;
        } else {
            if (i != 3) {
                return;
            }
            this.SDK = (String) obj;
        }
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "SCRAConfigurationReaderType", getClass());
    }
}

package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class ConfigParam extends BaseObject {
    public String Name;
    public String Type;
    public String Value;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 3;
    }

    public ConfigParam() {
        this.Name = "";
        this.Value = "";
        this.Type = "";
    }

    public ConfigParam(SoapObject soapObject) {
        try {
            this.Name = soapObject.getPrimitivePropertyAsString("Name");
            this.Value = soapObject.getPrimitivePropertyAsString("Value");
            this.Type = soapObject.getPrimitivePropertyAsString("Type");
        } catch (Exception unused) {
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        if (i == 0) {
            return this.Name;
        }
        if (i == 1) {
            return this.Value;
        }
        if (i != 2) {
            return null;
        }
        return this.Type;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        if (i == 0) {
            propertyInfo.name = "Name";
            propertyInfo.type = this.Name.getClass();
        } else if (i == 1) {
            propertyInfo.name = "Value";
            propertyInfo.type = this.Value.getClass();
        } else {
            if (i != 2) {
                return;
            }
            propertyInfo.name = "Type";
            propertyInfo.type = this.Type.getClass();
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void setProperty(int i, Object obj) {
        if (i == 0) {
            this.Name = (String) obj;
        } else if (i == 1) {
            this.Value = (String) obj;
        } else {
            if (i != 2) {
                return;
            }
            this.Type = (String) obj;
        }
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "ConfigParam", getClass());
    }
}

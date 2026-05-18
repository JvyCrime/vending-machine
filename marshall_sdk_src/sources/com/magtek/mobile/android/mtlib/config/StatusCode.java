package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class StatusCode extends BaseObject {
    public static final int PROP_DESCRIPTION = 1;
    public static final int PROP_NUMBER = 0;
    public static final int PROP_VERSION = 2;
    public String Description;
    public int Number;
    public String Version;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 3;
    }

    public StatusCode() {
        this.Number = 0;
        this.Description = "";
        this.Version = "";
    }

    public StatusCode(SoapObject soapObject) {
        try {
            this.Number = 0;
            this.Description = "";
            this.Version = "";
            this.Number = Integer.parseInt(soapObject.getPrimitivePropertyAsString("Number"));
            if (soapObject.getPropertyCount() > 1) {
                this.Description = soapObject.getPrimitivePropertyAsString("Description");
                this.Version = soapObject.getPrimitivePropertyAsString("Version");
            }
        } catch (Exception unused) {
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        if (i == 0) {
            return Integer.valueOf(this.Number);
        }
        if (i == 1) {
            return this.Description;
        }
        if (i != 2) {
            return null;
        }
        return this.Version;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        if (i == 0) {
            propertyInfo.name = "Number";
            propertyInfo.type = Integer.TYPE;
        } else if (i == 1) {
            propertyInfo.name = "Description";
            propertyInfo.type = this.Description.getClass();
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
            this.Number = Integer.parseInt(obj.toString());
        } else if (i == 1) {
            this.Description = (String) obj;
        } else {
            if (i != 2) {
                return;
            }
            this.Version = (String) obj;
        }
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "StatusCode", getClass());
    }
}

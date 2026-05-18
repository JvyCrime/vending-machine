package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class SCRAConfigurationDeviceInfo extends BaseObject {
    public static final int PROP_DEVICE = 4;
    public static final int PROP_FIRMWARE = 2;
    public static final int PROP_MODEL = 5;
    public static final int PROP_PLATFORM = 3;
    public static final int PROP_PRODUCT = 6;
    public static final int PROP_RELEASE = 1;
    public static final int PROP_SDK = 0;
    public static final int PROP_STATUS = 7;
    private String Device;
    private String Firmware;
    private String Model;
    private String Platform;
    private String Product;
    private String Release;
    private String SDK;
    private String Status;

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 8;
    }

    public SCRAConfigurationDeviceInfo() {
        this.SDK = "";
        this.Release = "";
        this.Firmware = "";
        this.Platform = "";
        this.Device = "";
        this.Model = "";
        this.Product = "";
        this.Status = "";
    }

    public SCRAConfigurationDeviceInfo(SoapObject soapObject) {
        try {
            this.SDK = "";
            this.Release = "";
            this.Firmware = "";
            this.Platform = "";
            this.Device = "";
            this.Model = "";
            this.Product = "";
            this.Status = "";
            this.Platform = soapObject.getPrimitivePropertyAsString("Platform");
            this.Device = soapObject.getPrimitivePropertyAsString("Device");
            this.Model = soapObject.getPrimitivePropertyAsString("Model");
            this.Product = soapObject.getPrimitivePropertyAsString("Product");
            this.SDK = soapObject.getPrimitivePropertyAsString("SDK");
            this.Release = soapObject.getPrimitivePropertyAsString("Release");
            this.Firmware = soapObject.getPrimitivePropertyAsString("Firmware");
            this.Status = soapObject.getPrimitivePropertyAsString("Status");
        } catch (Exception unused) {
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return this.SDK;
            case 1:
                return this.Release;
            case 2:
                return this.Firmware;
            case 3:
                return this.Platform;
            case 4:
                return this.Device;
            case 5:
                return this.Model;
            case 6:
                return this.Product;
            case 7:
                return this.Status;
            default:
                return null;
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.name = "SDK";
                propertyInfo.type = this.SDK.getClass();
                break;
            case 1:
                propertyInfo.name = "Release";
                propertyInfo.type = this.Release.getClass();
                break;
            case 2:
                propertyInfo.name = "Firmware";
                propertyInfo.type = this.Firmware.getClass();
                break;
            case 3:
                propertyInfo.name = "Platform";
                propertyInfo.type = this.Platform.getClass();
                break;
            case 4:
                propertyInfo.name = "Device";
                propertyInfo.type = this.Device.getClass();
                break;
            case 5:
                propertyInfo.name = "Model";
                propertyInfo.type = this.Model.getClass();
                break;
            case 6:
                propertyInfo.name = "Product";
                propertyInfo.type = this.Product.getClass();
                break;
            case 7:
                propertyInfo.name = "Status";
                propertyInfo.type = this.Status.getClass();
                break;
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void setProperty(int i, Object obj) {
        switch (i) {
            case 0:
                this.SDK = (String) obj;
                break;
            case 1:
                this.Release = (String) obj;
                break;
            case 2:
                this.Firmware = (String) obj;
                break;
            case 3:
                this.Platform = (String) obj;
                break;
            case 4:
                this.Device = (String) obj;
                break;
            case 5:
                this.Model = (String) obj;
                break;
            case 6:
                this.Product = (String) obj;
                break;
            case 7:
                this.Status = (String) obj;
                break;
        }
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping("http://ns.magtek.com/deviceconfig/", "SCRAConfigurationDeviceInfo", getClass());
    }
}

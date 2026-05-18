package com.magtek.mobile.android.mtlib.config;

import org.ksoap2.serialization.SoapObject;

/* JADX INFO: loaded from: classes.dex */
public class GetDeviceConfig {
    private static final String METHOD_NAME = "GetDeviceConfig";
    private static final String NAMESPACE = "http://ns.magtek.com.deviceconfig/";
    public ProcessMessageRequest ConfigRequest;

    public String GetSoapAction() {
        return "http://ns.magtek.com.deviceconfig/GetDeviceConfig";
    }

    public SoapObject GetSoapParams() {
        SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
        soapObject.addProperty("ConfigRequest", this.ConfigRequest);
        return soapObject;
    }
}

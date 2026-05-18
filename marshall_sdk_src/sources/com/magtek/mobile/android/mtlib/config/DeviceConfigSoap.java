package com.magtek.mobile.android.mtlib.config;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/* JADX INFO: loaded from: classes.dex */
public final class DeviceConfigSoap {
    public String Address;
    public boolean IsDotNet = true;

    public GetDeviceConfigResponse GetDeviceConfig(GetDeviceConfig getDeviceConfig) throws Exception {
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(110);
        soapSerializationEnvelope.dotNet = this.IsDotNet;
        soapSerializationEnvelope.setOutputSoapObject(getDeviceConfig.GetSoapParams());
        new GetDeviceConfigResponse().register(soapSerializationEnvelope);
        new HttpTransportSE(this.Address).call(getDeviceConfig.GetSoapAction(), soapSerializationEnvelope);
        return (GetDeviceConfigResponse) soapSerializationEnvelope.bodyIn;
    }
}

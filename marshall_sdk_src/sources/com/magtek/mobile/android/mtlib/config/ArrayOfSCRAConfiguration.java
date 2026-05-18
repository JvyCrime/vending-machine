package com.magtek.mobile.android.mtlib.config;

import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public class ArrayOfSCRAConfiguration extends LiteralArrayVector {
    @Override // com.magtek.mobile.android.mtlib.config.LiteralArrayVector
    protected String getItemDescriptor() {
        return "SCRAConfiguration";
    }

    @Override // com.magtek.mobile.android.mtlib.config.LiteralArrayVector
    protected Class getElementClass() {
        return new SCRAConfiguration().getClass();
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        super.register(soapSerializationEnvelope, "http://ns.magtek.com/deviceconfig/", "ArrayOfSCRAConfiguration");
    }
}

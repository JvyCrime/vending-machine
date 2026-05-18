package com.magtek.mobile.android.mtlib.config;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/* JADX INFO: loaded from: classes.dex */
public abstract class LiteralArrayVector extends Vector implements KvmSerializable {
    protected abstract Class getElementClass();

    protected String getItemDescriptor() {
        return "item";
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        return this;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return 1;
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope, String str, String str2) {
        soapSerializationEnvelope.addMapping(str, str2, getClass());
        registerElementClass(soapSerializationEnvelope, str);
    }

    private void registerElementClass(SoapSerializationEnvelope soapSerializationEnvelope, String str) {
        Class elementClass = getElementClass();
        try {
            if (elementClass.newInstance() instanceof KvmSerializable) {
                soapSerializationEnvelope.addMapping(str, "", elementClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        propertyInfo.name = getItemDescriptor();
        propertyInfo.type = getElementClass();
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void setProperty(int i, Object obj) {
        addElement(obj);
    }
}

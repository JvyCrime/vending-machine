package org.ksoap2.serialization;

/* JADX INFO: loaded from: classes2.dex */
public class SoapPrimitive extends AttributeContainer {
    String name;
    String namespace;
    String value;

    public SoapPrimitive(String str, String str2, String str3) {
        this.namespace = str;
        this.name = str2;
        this.value = str3;
    }

    public boolean equals(Object obj) {
        String str;
        String str2;
        if (!(obj instanceof SoapPrimitive)) {
            return false;
        }
        SoapPrimitive soapPrimitive = (SoapPrimitive) obj;
        return (this.name.equals(soapPrimitive.name) && ((str = this.namespace) != null ? str.equals(soapPrimitive.namespace) : soapPrimitive.namespace == null) && ((str2 = this.value) != null ? str2.equals(soapPrimitive.value) : soapPrimitive.value == null)) && attributesAreEqual(soapPrimitive);
    }

    public int hashCode() {
        int iHashCode = this.name.hashCode();
        String str = this.namespace;
        return iHashCode ^ (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return this.value;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getName() {
        return this.name;
    }
}

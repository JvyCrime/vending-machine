package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInformation {
    public String Boot1FirmwareNumber;
    public String Boot2FirmwareNumber;
    public String BuildInfo;
    public String CapabilityString;
    public String FirmwareNumber;
    public String MACAddress;
    public String Manufacturer;
    public String MaxAppMsgSize;
    public String ProductID;
    public String ProductName;
    public byte[] SerialNumber;

    DeviceInformation() {
        clearData();
    }

    public void clearData() {
        this.ProductID = "";
        this.MaxAppMsgSize = "";
        this.CapabilityString = "";
        this.Manufacturer = "";
        this.ProductName = "";
        this.SerialNumber = null;
        this.FirmwareNumber = "";
        this.BuildInfo = "";
        this.MACAddress = "";
        this.Boot1FirmwareNumber = "";
        this.Boot2FirmwareNumber = "";
    }
}

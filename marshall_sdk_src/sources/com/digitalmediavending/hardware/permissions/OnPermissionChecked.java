package com.digitalmediavending.hardware.permissions;

/* JADX INFO: loaded from: classes.dex */
public interface OnPermissionChecked {
    void fileAccessNotGiven();

    void fileAccessObtained();

    void noUSBDevicesFound();

    void storageAccessNotGiven();

    void usbPermissionGiven(boolean status);
}

package com.felhr.deviceids;

import com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbId;

/* JADX INFO: loaded from: classes.dex */
public class CH34xIds {
    private static final ConcreteDevice[] ch34xDevices = {new ConcreteDevice(17224, 21795), new ConcreteDevice(UsbId.VENDOR_QINHENG, UsbId.QINHENG_HL340), new ConcreteDevice(UsbId.VENDOR_QINHENG, 21795), new ConcreteDevice(UsbId.VENDOR_QINHENG, 1093)};

    private CH34xIds() {
    }

    public static boolean isDeviceSupported(int i, int i2) {
        int i3 = 0;
        while (true) {
            ConcreteDevice[] concreteDeviceArr = ch34xDevices;
            if (i3 > concreteDeviceArr.length - 1) {
                return false;
            }
            if (concreteDeviceArr[i3].vendorId == i && concreteDeviceArr[i3].productId == i2) {
                return true;
            }
            i3++;
        }
    }

    private static class ConcreteDevice {
        public int productId;
        public int vendorId;

        public ConcreteDevice(int i, int i2) {
            this.vendorId = i;
            this.productId = i2;
        }
    }
}

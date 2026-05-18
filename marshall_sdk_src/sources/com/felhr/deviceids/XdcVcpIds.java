package com.felhr.deviceids;

/* JADX INFO: loaded from: classes.dex */
public class XdcVcpIds {
    private static final ConcreteDevice[] xdcvcpDevices = {new ConcreteDevice(9805, 562), new ConcreteDevice(9805, 288)};

    public static boolean isDeviceSupported(int i, int i2) {
        int i3 = 0;
        while (true) {
            ConcreteDevice[] concreteDeviceArr = xdcvcpDevices;
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

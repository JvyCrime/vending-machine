package com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver;

import android.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ProbeTable {
    private final Map<Pair<Integer, Integer>, Class<? extends UsbSerialDriver>> mProbeTable = new LinkedHashMap();

    public ProbeTable addProduct(int vendorId, int productId, Class<? extends UsbSerialDriver> driverClass) {
        this.mProbeTable.put(Pair.create(Integer.valueOf(vendorId), Integer.valueOf(productId)), driverClass);
        return this;
    }

public Class<? extends UsbSerialDriver> findDriver(int vendorId, int productId) {
        return this.mProbeTable.get(Pair.create(Integer.valueOf(vendorId), Integer.valueOf(productId)));
    }
}

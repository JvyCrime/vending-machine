package com.ftdi.j2xx;

import androidx.core.view.MotionEventCompat;
import kotlin.UShort;

/* JADX INFO: loaded from: classes.dex */
class FT_EE_232B_Ctrl extends FT_EE_Ctrl {
    private static final short CHECKSUM_LOCATION = 63;
    private static final short EEPROM_SIZE = 64;
    private static FT_Device ft_device;

    FT_EE_232B_Ctrl(FT_Device fT_Device) {
        super(fT_Device);
        ft_device = fT_Device;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    short programEeprom(FT_EEPROM ft_eeprom) {
        int[] iArr = new int[64];
        if (ft_eeprom.getClass() != FT_EEPROM.class) {
            return (short) 1;
        }
        for (short s = 0; s < 64; s = (short) (s + 1)) {
            try {
                iArr[s] = readWord(s);
            } catch (Exception e) {
                e.printStackTrace();
                return (short) 0;
            }
        }
        iArr[1] = ft_eeprom.VendorId;
        iArr[2] = ft_eeprom.ProductId;
        iArr[3] = ft_device.mDeviceInfoNode.bcdDevice;
        iArr[4] = setUSBConfig(ft_eeprom);
        int stringDescriptor = setStringDescriptor(ft_eeprom.Product, iArr, setStringDescriptor(ft_eeprom.Manufacturer, iArr, 10, 7, true), 8, true);
        if (ft_eeprom.SerNumEnable) {
            setStringDescriptor(ft_eeprom.SerialNumber, iArr, stringDescriptor, 9, true);
        }
        if (iArr[1] == 0 || iArr[2] == 0) {
            return (short) 2;
        }
        return programEeprom(iArr, 63) ? (short) 0 : (short) 1;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    FT_EEPROM readEeprom() {
        FT_EEPROM ft_eeprom = new FT_EEPROM();
        int[] iArr = new int[64];
        for (int i = 0; i < 64; i++) {
            try {
                iArr[i] = readWord((short) i);
            } catch (Exception unused) {
                return null;
            }
        }
        ft_eeprom.VendorId = (short) iArr[1];
        ft_eeprom.ProductId = (short) iArr[2];
        getUSBConfig(ft_eeprom, iArr[4]);
        ft_eeprom.Manufacturer = getStringDescriptor(10, iArr);
        int length = ft_eeprom.Manufacturer.length() + 1 + 10;
        ft_eeprom.Product = getStringDescriptor(length, iArr);
        ft_eeprom.SerialNumber = getStringDescriptor(length + ft_eeprom.Product.length() + 1, iArr);
        return ft_eeprom;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    int getUserSize() {
        return (((63 - ((((((readWord((short) 7) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) / 2) + 10) + (((readWord((short) 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) / 2)) + 1)) - 1) - (((65280 & readWord((short) 9)) >> 8) / 2)) * 2;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    int writeUserData(byte[] bArr) {
        if (bArr.length > getUserSize()) {
            return 0;
        }
        int[] iArr = new int[64];
        for (short s = 0; s < 64; s = (short) (s + 1)) {
            iArr[s] = readWord(s);
        }
        short userSize = (short) (((short) ((63 - (getUserSize() / 2)) - 1)) & UShort.MAX_VALUE);
        int i = 0;
        while (i < bArr.length) {
            int i2 = i + 1;
            iArr[userSize] = ((i2 < bArr.length ? bArr[i2] & 255 : 0) << 8) | (bArr[i] & 255);
            i += 2;
            userSize = (short) (userSize + 1);
        }
        if (iArr[1] == 0 || iArr[2] == 0 || !programEeprom(iArr, 63)) {
            return 0;
        }
        return bArr.length;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    byte[] readUserData(int i) {
        byte[] bArr = new byte[i];
        if (i == 0 || i > getUserSize()) {
            return null;
        }
        short userSize = (short) (((short) ((63 - (getUserSize() / 2)) - 1)) & UShort.MAX_VALUE);
        int i2 = 0;
        while (i2 < i) {
            short s = (short) (userSize + 1);
            int word = readWord(userSize);
            int i3 = i2 + 1;
            if (i3 < i) {
                bArr[i3] = (byte) (word & 255);
            }
            bArr[i2] = (byte) ((word & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            i2 += 2;
            userSize = s;
        }
        return bArr;
    }
}

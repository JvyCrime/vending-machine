package com.ftdi.j2xx;

import androidx.core.view.MotionEventCompat;
import kotlin.UShort;

/* JADX INFO: loaded from: classes.dex */
class FT_EE_245R_Ctrl extends FT_EE_Ctrl {
    private static final short EEPROM_SIZE = 80;
    private static final short EE_MAX_SIZE = 1024;
    private static final short ENDOFUSERLOCATION = 63;
    private static final int EXTERNAL_OSCILLATOR = 2;
    private static final int HIGH_CURRENT_IO = 4;
    private static final int INVERT_CTS = 2048;
    private static final int INVERT_DCD = 16384;
    private static final int INVERT_DSR = 8192;
    private static final int INVERT_DTR = 4096;
    private static final int INVERT_RI = 32768;
    private static final int INVERT_RTS = 1024;
    private static final int INVERT_RXD = 512;
    private static final int INVERT_TXD = 256;
    private static final int LOAD_D2XX_DRIVER = 8;
    private static FT_Device ft_device;

    FT_EE_245R_Ctrl(FT_Device fT_Device) {
        super(fT_Device);
        ft_device = fT_Device;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    boolean writeWord(short s, short s2) {
        int i = s2 & UShort.MAX_VALUE;
        int i2 = s & UShort.MAX_VALUE;
        if (s >= 1024) {
            return false;
        }
        byte latencyTimer = ft_device.getLatencyTimer();
        ft_device.setLatencyTimer((byte) 119);
        boolean z = ft_device.getConnection().controlTransfer(64, 145, i, i2, null, 0, 0) == 0;
        ft_device.setLatencyTimer(latencyTimer);
        return z;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    short programEeprom(FT_EEPROM ft_eeprom) {
        int[] iArr = new int[80];
        if (ft_eeprom.getClass() != FT_EEPROM_245R.class) {
            return (short) 1;
        }
        FT_EEPROM_245R ft_eeprom_245r = (FT_EEPROM_245R) ft_eeprom;
        for (short s = 0; s < 80; s = (short) (s + 1)) {
            try {
                iArr[s] = readWord(s);
            } catch (Exception e) {
                e.printStackTrace();
                return (short) 0;
            }
        }
        int i = (iArr[0] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | 0;
        if (ft_eeprom_245r.HighIO) {
            i |= 4;
        }
        if (ft_eeprom_245r.LoadVCP) {
            i |= 8;
        }
        iArr[0] = ft_eeprom_245r.ExternalOscillator ? i | 2 : i & 65533;
        iArr[1] = ft_eeprom_245r.VendorId;
        iArr[2] = ft_eeprom_245r.ProductId;
        iArr[3] = 1536;
        iArr[4] = setUSBConfig(ft_eeprom);
        int deviceControl = setDeviceControl(ft_eeprom);
        if (ft_eeprom_245r.InvertTXD) {
            deviceControl |= 256;
        }
        if (ft_eeprom_245r.InvertRXD) {
            deviceControl |= 512;
        }
        if (ft_eeprom_245r.InvertRTS) {
            deviceControl |= 1024;
        }
        if (ft_eeprom_245r.InvertCTS) {
            deviceControl |= 2048;
        }
        if (ft_eeprom_245r.InvertDTR) {
            deviceControl |= 4096;
        }
        if (ft_eeprom_245r.InvertDSR) {
            deviceControl |= 8192;
        }
        if (ft_eeprom_245r.InvertDCD) {
            deviceControl |= 16384;
        }
        if (ft_eeprom_245r.InvertRI) {
            deviceControl |= 32768;
        }
        iArr[5] = deviceControl;
        iArr[10] = ft_eeprom_245r.CBus0 | (ft_eeprom_245r.CBus1 << 4) | (ft_eeprom_245r.CBus2 << 8) | (ft_eeprom_245r.CBus3 << 12);
        iArr[11] = ft_eeprom_245r.CBus4;
        int stringDescriptor = setStringDescriptor(ft_eeprom_245r.Product, iArr, setStringDescriptor(ft_eeprom_245r.Manufacturer, iArr, 12, 7, true), 8, true);
        if (ft_eeprom_245r.SerNumEnable) {
            setStringDescriptor(ft_eeprom_245r.SerialNumber, iArr, stringDescriptor, 9, true);
        }
        if (iArr[1] == 0 || iArr[2] == 0) {
            return (short) 2;
        }
        byte latencyTimer = ft_device.getLatencyTimer();
        ft_device.setLatencyTimer((byte) 119);
        boolean zProgramEeprom = programEeprom(iArr, 80);
        ft_device.setLatencyTimer(latencyTimer);
        return zProgramEeprom ? (short) 0 : (short) 1;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    FT_EEPROM readEeprom() {
        FT_EEPROM_245R ft_eeprom_245r = new FT_EEPROM_245R();
        int[] iArr = new int[80];
        for (int i = 0; i < 80; i++) {
            try {
                iArr[i] = readWord((short) i);
            } catch (Exception unused) {
                return null;
            }
        }
        if ((iArr[0] & 4) == 4) {
            ft_eeprom_245r.HighIO = true;
        } else {
            ft_eeprom_245r.HighIO = false;
        }
        if ((iArr[0] & 8) == 8) {
            ft_eeprom_245r.LoadVCP = true;
        } else {
            ft_eeprom_245r.LoadVCP = false;
        }
        if ((iArr[0] & 2) == 2) {
            ft_eeprom_245r.ExternalOscillator = true;
        } else {
            ft_eeprom_245r.ExternalOscillator = false;
        }
        ft_eeprom_245r.VendorId = (short) iArr[1];
        ft_eeprom_245r.ProductId = (short) iArr[2];
        getUSBConfig(ft_eeprom_245r, iArr[4]);
        getDeviceControl(ft_eeprom_245r, iArr[5]);
        if ((iArr[5] & 256) == 256) {
            ft_eeprom_245r.InvertTXD = true;
        } else {
            ft_eeprom_245r.InvertTXD = false;
        }
        if ((iArr[5] & 512) == 512) {
            ft_eeprom_245r.InvertRXD = true;
        } else {
            ft_eeprom_245r.InvertRXD = false;
        }
        if ((iArr[5] & 1024) == 1024) {
            ft_eeprom_245r.InvertRTS = true;
        } else {
            ft_eeprom_245r.InvertRTS = false;
        }
        if ((iArr[5] & 2048) == 2048) {
            ft_eeprom_245r.InvertCTS = true;
        } else {
            ft_eeprom_245r.InvertCTS = false;
        }
        if ((iArr[5] & 4096) == 4096) {
            ft_eeprom_245r.InvertDTR = true;
        } else {
            ft_eeprom_245r.InvertDTR = false;
        }
        if ((iArr[5] & 8192) == 8192) {
            ft_eeprom_245r.InvertDSR = true;
        } else {
            ft_eeprom_245r.InvertDSR = false;
        }
        if ((iArr[5] & 16384) == 16384) {
            ft_eeprom_245r.InvertDCD = true;
        } else {
            ft_eeprom_245r.InvertDCD = false;
        }
        if ((iArr[5] & 32768) == 32768) {
            ft_eeprom_245r.InvertRI = true;
        } else {
            ft_eeprom_245r.InvertRI = false;
        }
        int i2 = iArr[10];
        ft_eeprom_245r.CBus0 = (byte) (i2 & 15);
        ft_eeprom_245r.CBus1 = (byte) ((i2 & 240) >> 4);
        ft_eeprom_245r.CBus2 = (byte) ((i2 & 3840) >> 8);
        ft_eeprom_245r.CBus3 = (byte) ((i2 & 61440) >> 12);
        ft_eeprom_245r.CBus4 = (byte) (iArr[11] & 255);
        ft_eeprom_245r.Manufacturer = getStringDescriptor(((iArr[7] & 255) - 128) / 2, iArr);
        ft_eeprom_245r.Product = getStringDescriptor(((iArr[8] & 255) - 128) / 2, iArr);
        ft_eeprom_245r.SerialNumber = getStringDescriptor(((iArr[9] & 255) - 128) / 2, iArr);
        return ft_eeprom_245r;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    int getUserSize() {
        return (((63 - ((((((readWord((short) 7) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) / 2) + 12) + (((readWord((short) 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) / 2)) + 1)) - (((65280 & readWord((short) 9)) >> 8) / 2)) - 1) * 2;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    int writeUserData(byte[] bArr) {
        if (bArr.length > getUserSize()) {
            return 0;
        }
        int[] iArr = new int[80];
        for (short s = 0; s < 80; s = (short) (s + 1)) {
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
        if (iArr[1] == 0 || iArr[2] == 0) {
            return 0;
        }
        byte latencyTimer = ft_device.getLatencyTimer();
        ft_device.setLatencyTimer((byte) 119);
        boolean zProgramEeprom = programEeprom(iArr, 63);
        ft_device.setLatencyTimer(latencyTimer);
        if (zProgramEeprom) {
            return bArr.length;
        }
        return 0;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    byte[] readUserData(int i) {
        byte[] bArr = new byte[i];
        if (i == 0 || i > getUserSize()) {
            return null;
        }
        short userSize = (short) ((63 - (getUserSize() / 2)) - 1);
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

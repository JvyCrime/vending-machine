package com.ftdi.j2xx;

import androidx.core.view.MotionEventCompat;

/* JADX INFO: loaded from: classes.dex */
class FT_EE_X_Ctrl extends FT_EE_Ctrl {
    private static final int BCD_ENABLE = 1;
    private static final int CBUS_DRIVE = 48;
    private static final int CBUS_SCHMITT = 128;
    private static final int CBUS_SLEW = 64;
    private static final int DBUS_DRIVE = 3;
    private static final int DBUS_SCHMITT = 8;
    private static final int DBUS_SLEW = 4;
    private static final int DEACTIVATE_SLEEP = 4;
    private static final String DEFAULT_PID = "6015";
    private static final int DEVICE_TYPE_EE_LOC = 73;
    private static final short EE_MAX_SIZE = 1024;
    private static final byte FIFO = 1;
    private static final int FORCE_POWER_ENABLE = 2;
    private static final byte FT1248 = 2;
    private static final int FT1248_BIT_ORDER = 32;
    private static final int FT1248_CLK_POLARITY = 16;
    private static final int FT1248_FLOW_CTRL = 64;
    private static final byte I2C = 3;
    private static final int I2C_DISABLE_SCHMITT = 128;
    private static final int INVERT_CTS = 2048;
    private static final int INVERT_DCD = 16384;
    private static final int INVERT_DSR = 8192;
    private static final int INVERT_DTR = 4096;
    private static final int INVERT_RI = 32768;
    private static final int INVERT_RTS = 1024;
    private static final int INVERT_RXD = 512;
    private static final int INVERT_TXD = 256;
    private static final int LOAD_DRIVER = 128;
    private static final int RS485_ECHO = 8;
    private static final byte UART = 0;
    private static final int VBUS_SUSPEND = 64;
    private static FT_Device ft_device;

    FT_EE_X_Ctrl(FT_Device fT_Device) {
        super(fT_Device);
        ft_device = fT_Device;
        this.mEepromSize = 128;
        this.mEepromType = (short) 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:137:0x01ea, code lost:
    
        r14 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x01eb, code lost:
    
        r14.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x01ee, code lost:
    
        return 0;
     */
    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    short programEeprom(com.ftdi.j2xx.FT_EEPROM r14) {
        /*
            Method dump skipped, instruction units count: 495
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ftdi.j2xx.FT_EE_X_Ctrl.programEeprom(com.ftdi.j2xx.FT_EEPROM):short");
    }

    boolean programXeeprom(int[] iArr, int i) {
        int i2 = 43690;
        int i3 = 0;
        do {
            int i4 = iArr[i3] & 65535;
            writeWord((short) i3, (short) i4);
            int i5 = (i2 ^ i4) & 65535;
            i2 = (((i5 & 32768) > 0 ? 1 : 0) | ((i5 << 1) & 65535)) & 65535;
            i3++;
            if (i3 == 18) {
                i3 = 64;
            }
        } while (i3 != i);
        writeWord((short) i, (short) i2);
        return true;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    FT_EEPROM readEeprom() {
        FT_EEPROM_X_Series fT_EEPROM_X_Series = new FT_EEPROM_X_Series();
        int[] iArr = new int[this.mEepromSize];
        for (short s = 0; s < this.mEepromSize; s = (short) (s + 1)) {
            try {
                iArr[s] = readWord(s);
            } catch (Exception unused) {
                return null;
            }
        }
        if ((iArr[0] & 1) > 0) {
            fT_EEPROM_X_Series.BCDEnable = true;
        } else {
            fT_EEPROM_X_Series.BCDEnable = false;
        }
        if ((iArr[0] & 2) > 0) {
            fT_EEPROM_X_Series.BCDForceCBusPWREN = true;
        } else {
            fT_EEPROM_X_Series.BCDForceCBusPWREN = false;
        }
        if ((iArr[0] & 4) > 0) {
            fT_EEPROM_X_Series.BCDDisableSleep = true;
        } else {
            fT_EEPROM_X_Series.BCDDisableSleep = false;
        }
        if ((iArr[0] & 8) > 0) {
            fT_EEPROM_X_Series.RS485EchoSuppress = true;
        } else {
            fT_EEPROM_X_Series.RS485EchoSuppress = false;
        }
        if ((iArr[0] & 64) > 0) {
            fT_EEPROM_X_Series.PowerSaveEnable = true;
        } else {
            fT_EEPROM_X_Series.PowerSaveEnable = false;
        }
        if ((iArr[0] & 128) > 0) {
            fT_EEPROM_X_Series.A_LoadVCP = true;
            fT_EEPROM_X_Series.A_LoadD2XX = false;
        } else {
            fT_EEPROM_X_Series.A_LoadVCP = false;
            fT_EEPROM_X_Series.A_LoadD2XX = true;
        }
        fT_EEPROM_X_Series.VendorId = (short) iArr[1];
        fT_EEPROM_X_Series.ProductId = (short) iArr[2];
        getUSBConfig(fT_EEPROM_X_Series, iArr[4]);
        getDeviceControl(fT_EEPROM_X_Series, iArr[5]);
        if ((iArr[5] & 16) > 0) {
            fT_EEPROM_X_Series.FT1248ClockPolarity = true;
        } else {
            fT_EEPROM_X_Series.FT1248ClockPolarity = false;
        }
        if ((iArr[5] & 32) > 0) {
            fT_EEPROM_X_Series.FT1248LSB = true;
        } else {
            fT_EEPROM_X_Series.FT1248LSB = false;
        }
        if ((iArr[5] & 64) > 0) {
            fT_EEPROM_X_Series.FT1248FlowControl = true;
        } else {
            fT_EEPROM_X_Series.FT1248FlowControl = false;
        }
        if ((iArr[5] & 128) > 0) {
            fT_EEPROM_X_Series.I2CDisableSchmitt = true;
        } else {
            fT_EEPROM_X_Series.I2CDisableSchmitt = false;
        }
        if ((iArr[5] & 256) == 256) {
            fT_EEPROM_X_Series.InvertTXD = true;
        } else {
            fT_EEPROM_X_Series.InvertTXD = false;
        }
        if ((iArr[5] & 512) == 512) {
            fT_EEPROM_X_Series.InvertRXD = true;
        } else {
            fT_EEPROM_X_Series.InvertRXD = false;
        }
        if ((iArr[5] & 1024) == 1024) {
            fT_EEPROM_X_Series.InvertRTS = true;
        } else {
            fT_EEPROM_X_Series.InvertRTS = false;
        }
        if ((iArr[5] & 2048) == 2048) {
            fT_EEPROM_X_Series.InvertCTS = true;
        } else {
            fT_EEPROM_X_Series.InvertCTS = false;
        }
        if ((iArr[5] & 4096) == 4096) {
            fT_EEPROM_X_Series.InvertDTR = true;
        } else {
            fT_EEPROM_X_Series.InvertDTR = false;
        }
        if ((iArr[5] & 8192) == 8192) {
            fT_EEPROM_X_Series.InvertDSR = true;
        } else {
            fT_EEPROM_X_Series.InvertDSR = false;
        }
        if ((iArr[5] & 16384) == 16384) {
            fT_EEPROM_X_Series.InvertDCD = true;
        } else {
            fT_EEPROM_X_Series.InvertDCD = false;
        }
        if ((iArr[5] & 32768) == 32768) {
            fT_EEPROM_X_Series.InvertRI = true;
        } else {
            fT_EEPROM_X_Series.InvertRI = false;
        }
        short s2 = (short) (iArr[6] & 3);
        if (s2 == 0) {
            fT_EEPROM_X_Series.AD_DriveCurrent = (byte) 0;
        } else if (s2 == 1) {
            fT_EEPROM_X_Series.AD_DriveCurrent = (byte) 1;
        } else if (s2 == 2) {
            fT_EEPROM_X_Series.AD_DriveCurrent = (byte) 2;
        } else if (s2 == 3) {
            fT_EEPROM_X_Series.AD_DriveCurrent = (byte) 3;
        }
        if (((short) (iArr[6] & 4)) == 4) {
            fT_EEPROM_X_Series.AD_SlowSlew = true;
        } else {
            fT_EEPROM_X_Series.AD_SlowSlew = false;
        }
        if (((short) (iArr[6] & 8)) == 8) {
            fT_EEPROM_X_Series.AD_SchmittInput = true;
        } else {
            fT_EEPROM_X_Series.AD_SchmittInput = false;
        }
        short s3 = (short) ((iArr[6] & 48) >> 4);
        if (s3 == 0) {
            fT_EEPROM_X_Series.AC_DriveCurrent = (byte) 0;
        } else if (s3 == 1) {
            fT_EEPROM_X_Series.AC_DriveCurrent = (byte) 1;
        } else if (s3 == 2) {
            fT_EEPROM_X_Series.AC_DriveCurrent = (byte) 2;
        } else if (s3 == 3) {
            fT_EEPROM_X_Series.AC_DriveCurrent = (byte) 3;
        }
        if (((short) (iArr[6] & 64)) == 64) {
            fT_EEPROM_X_Series.AC_SlowSlew = true;
        } else {
            fT_EEPROM_X_Series.AC_SlowSlew = false;
        }
        if (((short) (iArr[6] & 128)) == 128) {
            fT_EEPROM_X_Series.AC_SchmittInput = true;
        } else {
            fT_EEPROM_X_Series.AC_SchmittInput = false;
        }
        fT_EEPROM_X_Series.I2CSlaveAddress = iArr[10];
        fT_EEPROM_X_Series.I2CDeviceID = iArr[11];
        fT_EEPROM_X_Series.I2CDeviceID |= (iArr[12] & 255) << 16;
        fT_EEPROM_X_Series.CBus0 = (byte) (iArr[13] & 255);
        fT_EEPROM_X_Series.CBus1 = (byte) ((iArr[13] >> 8) & 255);
        fT_EEPROM_X_Series.CBus2 = (byte) (iArr[14] & 255);
        fT_EEPROM_X_Series.CBus3 = (byte) ((iArr[14] >> 8) & 255);
        fT_EEPROM_X_Series.CBus4 = (byte) (iArr[15] & 255);
        fT_EEPROM_X_Series.CBus5 = (byte) ((iArr[15] >> 8) & 255);
        fT_EEPROM_X_Series.CBus6 = (byte) (iArr[16] & 255);
        this.mEepromType = (short) (iArr[73] >> 8);
        fT_EEPROM_X_Series.Manufacturer = getStringDescriptor((iArr[7] & 255) / 2, iArr);
        fT_EEPROM_X_Series.Product = getStringDescriptor((iArr[8] & 255) / 2, iArr);
        fT_EEPROM_X_Series.SerialNumber = getStringDescriptor((iArr[9] & 255) / 2, iArr);
        return fT_EEPROM_X_Series;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    int getUserSize() {
        int word = readWord((short) 9);
        return (((this.mEepromSize - 1) - 1) - ((((word & 255) / 2) + (((word & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) / 2)) + 1)) * 2;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    int writeUserData(byte[] bArr) {
        if (bArr.length > getUserSize()) {
            return 0;
        }
        int[] iArr = new int[this.mEepromSize];
        for (short s = 0; s < this.mEepromSize; s = (short) (s + 1)) {
            iArr[s] = readWord(s);
        }
        short userSize = (short) (((this.mEepromSize - (getUserSize() / 2)) - 1) - 1);
        int i = 0;
        while (i < bArr.length) {
            int i2 = i + 1;
            iArr[userSize] = ((i2 < bArr.length ? bArr[i2] & 255 : 0) << 8) | (bArr[i] & 255);
            i += 2;
            userSize = (short) (userSize + 1);
        }
        if (iArr[1] == 0 || iArr[2] == 0 || !programXeeprom(iArr, this.mEepromSize - 1)) {
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
        short userSize = (short) (((this.mEepromSize - (getUserSize() / 2)) - 1) - 1);
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

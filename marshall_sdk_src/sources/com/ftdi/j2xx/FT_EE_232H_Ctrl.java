package com.ftdi.j2xx;

import androidx.core.view.MotionEventCompat;
import com.ftdi.j2xx.D2xxManager;

/* JADX INFO: loaded from: classes.dex */
class FT_EE_232H_Ctrl extends FT_EE_Ctrl {
    private static final int AL_DRIVE_CURRENT = 3;
    private static final int AL_FAST_SLEW = 4;
    private static final int AL_SCHMITT_INPUT = 8;
    private static final int BL_DRIVE_CURRENT = 768;
    private static final int BL_FAST_SLEW = 1024;
    private static final int BL_SCHMITT_INPUT = 2048;
    private static final String DEFAULT_PID = "6014";
    private static final byte EEPROM_SIZE_LOCATION = 15;
    private static FT_Device ft_device;

    FT_EE_232H_Ctrl(FT_Device fT_Device) throws D2xxManager.D2xxException {
        super(fT_Device);
        getEepromSize((byte) 15);
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    short programEeprom(FT_EEPROM ft_eeprom) {
        int[] iArr = new int[this.mEepromSize];
        if (ft_eeprom.getClass() != FT_EEPROM_232H.class) {
            return (short) 1;
        }
        FT_EEPROM_232H ft_eeprom_232h = (FT_EEPROM_232H) ft_eeprom;
        try {
            if (ft_eeprom_232h.FIFO) {
                iArr[0] = iArr[0] | 1;
            } else if (ft_eeprom_232h.FIFOTarget) {
                iArr[0] = iArr[0] | 2;
            } else if (ft_eeprom_232h.FastSerial) {
                iArr[0] = iArr[0] | 4;
            }
            if (ft_eeprom_232h.FT1248) {
                iArr[0] = iArr[0] | 8;
            }
            if (ft_eeprom_232h.LoadVCP) {
                iArr[0] = iArr[0] | 16;
            }
            if (ft_eeprom_232h.FT1248ClockPolarity) {
                iArr[0] = iArr[0] | 256;
            }
            if (ft_eeprom_232h.FT1248LSB) {
                iArr[0] = iArr[0] | 512;
            }
            if (ft_eeprom_232h.FT1248FlowControl) {
                iArr[0] = iArr[0] | 1024;
            }
            if (ft_eeprom_232h.PowerSaveEnable) {
                iArr[0] = iArr[0] | 32768;
            }
            iArr[1] = ft_eeprom_232h.VendorId;
            iArr[2] = ft_eeprom_232h.ProductId;
            iArr[3] = 2304;
            iArr[4] = setUSBConfig(ft_eeprom);
            iArr[5] = setDeviceControl(ft_eeprom);
            byte b = ft_eeprom_232h.AL_DriveCurrent;
            if (b == -1) {
                b = 0;
            }
            iArr[6] = b | iArr[6];
            if (ft_eeprom_232h.AL_SlowSlew) {
                iArr[6] = iArr[6] | 4;
            }
            if (ft_eeprom_232h.AL_SchmittInput) {
                iArr[6] = iArr[6] | 8;
            }
            byte b2 = ft_eeprom_232h.BL_DriveCurrent;
            if (b2 == -1) {
                b2 = 0;
            }
            iArr[6] = ((short) (b2 << 8)) | iArr[6];
            if (ft_eeprom_232h.BL_SlowSlew) {
                iArr[6] = iArr[6] | 1024;
            }
            if (ft_eeprom_232h.BL_SchmittInput) {
                iArr[6] = iArr[6] | 2048;
            }
            int stringDescriptor = setStringDescriptor(ft_eeprom_232h.Product, iArr, setStringDescriptor(ft_eeprom_232h.Manufacturer, iArr, 80, 7, false), 8, false);
            if (ft_eeprom_232h.SerNumEnable) {
                setStringDescriptor(ft_eeprom_232h.SerialNumber, iArr, stringDescriptor, 9, false);
            }
            iArr[10] = 0;
            iArr[11] = 0;
            iArr[12] = 0;
            iArr[12] = ft_eeprom_232h.CBus0 | (ft_eeprom_232h.CBus1 << 4) | (ft_eeprom_232h.CBus2 << 8) | (ft_eeprom_232h.CBus3 << 12);
            iArr[13] = 0;
            iArr[13] = (ft_eeprom_232h.CBus7 << 12) | ft_eeprom_232h.CBus4 | (ft_eeprom_232h.CBus5 << 4) | (ft_eeprom_232h.CBus6 << 8);
            iArr[14] = 0;
            iArr[14] = ft_eeprom_232h.CBus8 | (ft_eeprom_232h.CBus9 << 4);
            iArr[15] = this.mEepromType;
            iArr[69] = 72;
            if (this.mEepromType == 70) {
                return (short) 1;
            }
            if (iArr[1] == 0 || iArr[2] == 0) {
                return (short) 2;
            }
            return programEeprom(iArr, this.mEepromSize - 1) ? (short) 0 : (short) 1;
        } catch (Exception e) {
            e.printStackTrace();
            return (short) 0;
        }
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    FT_EEPROM readEeprom() {
        FT_EEPROM_232H ft_eeprom_232h = new FT_EEPROM_232H();
        int[] iArr = new int[this.mEepromSize];
        if (this.mEepromBlank) {
            return ft_eeprom_232h;
        }
        for (short s = 0; s < this.mEepromSize; s = (short) (s + 1)) {
            try {
                iArr[s] = readWord(s);
            } catch (Exception unused) {
                return null;
            }
        }
        ft_eeprom_232h.UART = false;
        int i = iArr[0] & 15;
        if (i == 0) {
            ft_eeprom_232h.UART = true;
        } else if (i == 1) {
            ft_eeprom_232h.FIFO = true;
        } else if (i == 2) {
            ft_eeprom_232h.FIFOTarget = true;
        } else if (i == 4) {
            ft_eeprom_232h.FastSerial = true;
        } else if (i == 8) {
            ft_eeprom_232h.FT1248 = true;
        } else {
            ft_eeprom_232h.UART = true;
        }
        if ((iArr[0] & 16) > 0) {
            ft_eeprom_232h.LoadVCP = true;
            ft_eeprom_232h.LoadD2XX = false;
        } else {
            ft_eeprom_232h.LoadVCP = false;
            ft_eeprom_232h.LoadD2XX = true;
        }
        if ((iArr[0] & 256) > 0) {
            ft_eeprom_232h.FT1248ClockPolarity = true;
        } else {
            ft_eeprom_232h.FT1248ClockPolarity = false;
        }
        if ((iArr[0] & 512) > 0) {
            ft_eeprom_232h.FT1248LSB = true;
        } else {
            ft_eeprom_232h.FT1248LSB = false;
        }
        if ((iArr[0] & 1024) > 0) {
            ft_eeprom_232h.FT1248FlowControl = true;
        } else {
            ft_eeprom_232h.FT1248FlowControl = false;
        }
        if ((iArr[0] & 32768) > 0) {
            ft_eeprom_232h.PowerSaveEnable = true;
        }
        ft_eeprom_232h.VendorId = (short) iArr[1];
        ft_eeprom_232h.ProductId = (short) iArr[2];
        getUSBConfig(ft_eeprom_232h, iArr[4]);
        getDeviceControl(ft_eeprom_232h, iArr[5]);
        int i2 = iArr[6] & 3;
        if (i2 == 0) {
            ft_eeprom_232h.AL_DriveCurrent = (byte) 0;
        } else if (i2 == 1) {
            ft_eeprom_232h.AL_DriveCurrent = (byte) 1;
        } else if (i2 == 2) {
            ft_eeprom_232h.AL_DriveCurrent = (byte) 2;
        } else if (i2 == 3) {
            ft_eeprom_232h.AL_DriveCurrent = (byte) 3;
        }
        if ((iArr[6] & 4) > 0) {
            ft_eeprom_232h.AL_SlowSlew = true;
        } else {
            ft_eeprom_232h.AL_SlowSlew = false;
        }
        if ((iArr[6] & 8) > 0) {
            ft_eeprom_232h.AL_SchmittInput = true;
        } else {
            ft_eeprom_232h.AL_SchmittInput = false;
        }
        short s2 = (short) ((iArr[6] & 768) >> 8);
        if (s2 == 0) {
            ft_eeprom_232h.BL_DriveCurrent = (byte) 0;
        } else if (s2 == 1) {
            ft_eeprom_232h.BL_DriveCurrent = (byte) 1;
        } else if (s2 == 2) {
            ft_eeprom_232h.BL_DriveCurrent = (byte) 2;
        } else if (s2 == 3) {
            ft_eeprom_232h.BL_DriveCurrent = (byte) 3;
        }
        if ((iArr[6] & 1024) > 0) {
            ft_eeprom_232h.BL_SlowSlew = true;
        } else {
            ft_eeprom_232h.BL_SlowSlew = false;
        }
        if ((iArr[6] & 2048) > 0) {
            ft_eeprom_232h.BL_SchmittInput = true;
        } else {
            ft_eeprom_232h.BL_SchmittInput = false;
        }
        ft_eeprom_232h.CBus0 = (byte) ((iArr[12] >> 0) & 15);
        ft_eeprom_232h.CBus1 = (byte) ((iArr[12] >> 4) & 15);
        ft_eeprom_232h.CBus2 = (byte) ((iArr[12] >> 8) & 15);
        ft_eeprom_232h.CBus3 = (byte) ((iArr[12] >> 12) & 15);
        ft_eeprom_232h.CBus4 = (byte) ((iArr[13] >> 0) & 15);
        ft_eeprom_232h.CBus5 = (byte) ((iArr[13] >> 4) & 15);
        ft_eeprom_232h.CBus6 = (byte) ((iArr[13] >> 8) & 15);
        ft_eeprom_232h.CBus7 = (byte) ((iArr[13] >> 12) & 15);
        ft_eeprom_232h.CBus8 = (byte) ((iArr[14] >> 0) & 15);
        ft_eeprom_232h.CBus9 = (byte) ((iArr[14] >> 4) & 15);
        ft_eeprom_232h.Manufacturer = getStringDescriptor((iArr[7] & 255) / 2, iArr);
        ft_eeprom_232h.Product = getStringDescriptor((iArr[8] & 255) / 2, iArr);
        ft_eeprom_232h.SerialNumber = getStringDescriptor((iArr[9] & 255) / 2, iArr);
        return ft_eeprom_232h;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    int getUserSize() {
        int word = readWord((short) 9);
        int i = ((word & 255) / 2) + 1;
        return (((this.mEepromSize - i) - 1) - ((((word & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) / 2) + 1)) * 2;
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
        if (iArr[1] == 0 || iArr[2] == 0 || !programEeprom(iArr, this.mEepromSize - 1)) {
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

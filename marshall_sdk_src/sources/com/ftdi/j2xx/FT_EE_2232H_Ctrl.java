package com.ftdi.j2xx;

import androidx.core.view.MotionEventCompat;
import com.ftdi.j2xx.D2xxManager;

/* JADX INFO: loaded from: classes.dex */
class FT_EE_2232H_Ctrl extends FT_EE_Ctrl {
    private static final int AH_DRIVE_CURRENT = 48;
    private static final int AH_FAST_SLEW = 64;
    private static final int AH_SCHMITT_INPUT = 128;
    private static final int AL_DRIVE_CURRENT = 3;
    private static final int AL_FAST_SLEW = 4;
    private static final int AL_SCHMITT_INPUT = 8;
    private static final int A_245_FIFO = 1;
    private static final int A_245_FIFO_TARGET = 2;
    private static final int A_FAST_SERIAL = 4;
    private static final int A_LOAD_VCP_DRIVER = 8;
    private static final int A_UART_RS232 = 0;
    private static final int BH_DRIVE_CURRENT = 12288;
    private static final int BH_FAST_SLEW = 16384;
    private static final int BH_SCHMITT_INPUT = 32768;
    private static final int BL_DRIVE_CURRENT = 768;
    private static final int BL_FAST_SLEW = 1024;
    private static final int BL_SCHMITT_INPUT = 2048;
    private static final String DEFAULT_PID = "6010";
    private static final byte EEPROM_SIZE_LOCATION = 12;
    private static final int INVERT_CTS = 2048;
    private static final int INVERT_DCD = 16384;
    private static final int INVERT_DSR = 8192;
    private static final int INVERT_DTR = 4096;
    private static final int INVERT_RI = 32768;
    private static final int INVERT_RTS = 1024;
    private static final int INVERT_RXD = 512;
    private static final int INVERT_TXD = 256;
    private static final int TPRDRV = 24;

    FT_EE_2232H_Ctrl(FT_Device fT_Device) throws D2xxManager.D2xxException {
        super(fT_Device);
        getEepromSize((byte) 12);
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    short programEeprom(FT_EEPROM ft_eeprom) {
        boolean z;
        int i;
        int[] iArr = new int[this.mEepromSize];
        if (ft_eeprom.getClass() != FT_EEPROM_2232H.class) {
            return (short) 1;
        }
        FT_EEPROM_2232H ft_eeprom_2232h = (FT_EEPROM_2232H) ft_eeprom;
        try {
            if (!ft_eeprom_2232h.A_UART) {
                if (ft_eeprom_2232h.A_FIFO) {
                    iArr[0] = iArr[0] | 1;
                } else if (ft_eeprom_2232h.A_FIFOTarget) {
                    iArr[0] = iArr[0] | 2;
                } else {
                    iArr[0] = iArr[0] | 4;
                }
            }
            if (ft_eeprom_2232h.A_LoadVCP) {
                iArr[0] = iArr[0] | 8;
            }
            if (!ft_eeprom_2232h.B_UART) {
                if (ft_eeprom_2232h.B_FIFO) {
                    iArr[0] = iArr[0] | 256;
                } else if (ft_eeprom_2232h.B_FIFOTarget) {
                    iArr[0] = iArr[0] | 512;
                } else {
                    iArr[0] = iArr[0] | 1024;
                }
            }
            if (ft_eeprom_2232h.B_LoadVCP) {
                iArr[0] = iArr[0] | 2048;
            }
            if (ft_eeprom_2232h.PowerSaveEnable) {
                iArr[0] = iArr[0] | 32768;
            }
            iArr[1] = ft_eeprom_2232h.VendorId;
            iArr[2] = ft_eeprom_2232h.ProductId;
            iArr[3] = 1792;
            iArr[4] = setUSBConfig(ft_eeprom);
            iArr[5] = setDeviceControl(ft_eeprom);
            iArr[6] = 0;
            byte b = ft_eeprom_2232h.AL_DriveCurrent;
            if (b == -1) {
                b = 0;
            }
            iArr[6] = b | iArr[6];
            if (ft_eeprom_2232h.AL_SlowSlew) {
                iArr[6] = iArr[6] | 4;
            }
            if (ft_eeprom_2232h.AL_SchmittInput) {
                iArr[6] = iArr[6] | 8;
            }
            byte b2 = ft_eeprom_2232h.AH_DriveCurrent;
            if (b2 == -1) {
                b2 = 0;
            }
            iArr[6] = ((short) (b2 << 4)) | iArr[6];
            if (ft_eeprom_2232h.AH_SlowSlew) {
                iArr[6] = iArr[6] | 64;
            }
            if (ft_eeprom_2232h.AH_SchmittInput) {
                iArr[6] = iArr[6] | 128;
            }
            byte b3 = ft_eeprom_2232h.BL_DriveCurrent;
            if (b3 == -1) {
                b3 = 0;
            }
            iArr[6] = ((short) (b3 << 8)) | iArr[6];
            if (ft_eeprom_2232h.BL_SlowSlew) {
                iArr[6] = iArr[6] | 1024;
            }
            if (ft_eeprom_2232h.BL_SchmittInput) {
                iArr[6] = iArr[6] | 2048;
            }
            iArr[6] = ((short) (ft_eeprom_2232h.BH_DriveCurrent << 12)) | iArr[6];
            if (ft_eeprom_2232h.BH_SlowSlew) {
                iArr[6] = iArr[6] | 16384;
            }
            if (ft_eeprom_2232h.BH_SchmittInput) {
                iArr[6] = iArr[6] | 32768;
            }
            if (this.mEepromType == 70) {
                z = true;
                i = 13;
            } else {
                z = false;
                i = 77;
            }
            int stringDescriptor = setStringDescriptor(ft_eeprom_2232h.Product, iArr, setStringDescriptor(ft_eeprom_2232h.Manufacturer, iArr, i, 7, z), 8, z);
            if (ft_eeprom_2232h.SerNumEnable) {
                setStringDescriptor(ft_eeprom_2232h.SerialNumber, iArr, stringDescriptor, 9, z);
            }
            int i2 = ft_eeprom_2232h.TPRDRV;
            if (i2 == 0) {
                iArr[11] = 0;
            } else if (i2 == 1) {
                iArr[11] = 8;
            } else if (i2 == 2) {
                iArr[11] = 16;
            } else if (i2 == 3) {
                iArr[11] = 24;
            } else {
                iArr[11] = 0;
            }
            iArr[12] = this.mEepromType;
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
        FT_EEPROM_2232H ft_eeprom_2232h = new FT_EEPROM_2232H();
        int[] iArr = new int[this.mEepromSize];
        if (this.mEepromBlank) {
            return ft_eeprom_2232h;
        }
        for (short s = 0; s < this.mEepromSize; s = (short) (s + 1)) {
            try {
                iArr[s] = readWord(s);
            } catch (Exception unused) {
                return null;
            }
        }
        int i = iArr[0];
        short s2 = (short) (i & 7);
        if (s2 == 0) {
            ft_eeprom_2232h.A_UART = true;
        } else if (s2 == 1) {
            ft_eeprom_2232h.A_FIFO = true;
        } else if (s2 == 2) {
            ft_eeprom_2232h.A_FIFOTarget = true;
        } else if (s2 == 4) {
            ft_eeprom_2232h.A_FastSerial = true;
        } else {
            ft_eeprom_2232h.A_UART = true;
        }
        if (((short) ((i & 8) >> 3)) == 1) {
            ft_eeprom_2232h.A_LoadVCP = true;
            ft_eeprom_2232h.A_LoadD2XX = false;
        } else {
            ft_eeprom_2232h.A_LoadVCP = false;
            ft_eeprom_2232h.A_LoadD2XX = true;
        }
        short s3 = (short) ((i & 1792) >> 8);
        if (s3 == 0) {
            ft_eeprom_2232h.B_UART = true;
        } else if (s3 == 1) {
            ft_eeprom_2232h.B_FIFO = true;
        } else if (s3 == 2) {
            ft_eeprom_2232h.B_FIFOTarget = true;
        } else if (s3 == 4) {
            ft_eeprom_2232h.B_FastSerial = true;
        } else {
            ft_eeprom_2232h.B_UART = true;
        }
        if (((short) ((i & 2048) >> 11)) == 1) {
            ft_eeprom_2232h.B_LoadVCP = true;
            ft_eeprom_2232h.B_LoadD2XX = false;
        } else {
            ft_eeprom_2232h.B_LoadVCP = false;
            ft_eeprom_2232h.B_LoadD2XX = true;
        }
        if (((short) ((i & 32768) >> 15)) == 1) {
            ft_eeprom_2232h.PowerSaveEnable = true;
        } else {
            ft_eeprom_2232h.PowerSaveEnable = false;
        }
        ft_eeprom_2232h.VendorId = (short) iArr[1];
        ft_eeprom_2232h.ProductId = (short) iArr[2];
        getUSBConfig(ft_eeprom_2232h, iArr[4]);
        getDeviceControl(ft_eeprom_2232h, iArr[5]);
        short s4 = (short) (iArr[6] & 3);
        if (s4 == 0) {
            ft_eeprom_2232h.AL_DriveCurrent = (byte) 0;
        } else if (s4 == 1) {
            ft_eeprom_2232h.AL_DriveCurrent = (byte) 1;
        } else if (s4 == 2) {
            ft_eeprom_2232h.AL_DriveCurrent = (byte) 2;
        } else if (s4 == 3) {
            ft_eeprom_2232h.AL_DriveCurrent = (byte) 3;
        }
        if (((short) (iArr[6] & 4)) == 4) {
            ft_eeprom_2232h.AL_SlowSlew = true;
        } else {
            ft_eeprom_2232h.AL_SlowSlew = false;
        }
        if (((short) (iArr[6] & 8)) == 8) {
            ft_eeprom_2232h.AL_SchmittInput = true;
        } else {
            ft_eeprom_2232h.AL_SchmittInput = false;
        }
        short s5 = (short) ((iArr[6] & 48) >> 4);
        if (s5 == 0) {
            ft_eeprom_2232h.AH_DriveCurrent = (byte) 0;
        } else if (s5 == 1) {
            ft_eeprom_2232h.AH_DriveCurrent = (byte) 1;
        } else if (s5 == 2) {
            ft_eeprom_2232h.AH_DriveCurrent = (byte) 2;
        } else if (s5 == 3) {
            ft_eeprom_2232h.AH_DriveCurrent = (byte) 3;
        }
        if (((short) (iArr[6] & 64)) == 64) {
            ft_eeprom_2232h.AH_SlowSlew = true;
        } else {
            ft_eeprom_2232h.AH_SlowSlew = false;
        }
        short s6 = (short) (iArr[6] & 128);
        if (s6 == 128) {
            ft_eeprom_2232h.AH_SchmittInput = true;
        } else {
            ft_eeprom_2232h.AH_SchmittInput = false;
        }
        short s7 = (short) ((iArr[6] & 768) >> 8);
        if (s7 == 0) {
            ft_eeprom_2232h.BL_DriveCurrent = (byte) 0;
        } else if (s7 == 1) {
            ft_eeprom_2232h.BL_DriveCurrent = (byte) 1;
        } else if (s7 == 2) {
            ft_eeprom_2232h.BL_DriveCurrent = (byte) 2;
        } else if (s7 == 3) {
            ft_eeprom_2232h.BL_DriveCurrent = (byte) 3;
        }
        if (((short) (iArr[6] & 1024)) == 1024) {
            ft_eeprom_2232h.BL_SlowSlew = true;
        } else {
            ft_eeprom_2232h.BL_SlowSlew = false;
        }
        int i2 = iArr[6];
        if (s6 == 2048) {
            ft_eeprom_2232h.BL_SchmittInput = true;
        } else {
            ft_eeprom_2232h.BL_SchmittInput = false;
        }
        short s8 = (short) ((iArr[6] & BH_DRIVE_CURRENT) >> 12);
        if (s8 == 0) {
            ft_eeprom_2232h.BH_DriveCurrent = (byte) 0;
        } else if (s8 == 1) {
            ft_eeprom_2232h.BH_DriveCurrent = (byte) 1;
        } else if (s8 == 2) {
            ft_eeprom_2232h.BH_DriveCurrent = (byte) 2;
        } else if (s8 == 3) {
            ft_eeprom_2232h.BH_DriveCurrent = (byte) 3;
        }
        if (((short) (iArr[6] & 16384)) == 16384) {
            ft_eeprom_2232h.BH_SlowSlew = true;
        } else {
            ft_eeprom_2232h.BH_SlowSlew = false;
        }
        if (((short) (iArr[6] & 32768)) == Short.MIN_VALUE) {
            ft_eeprom_2232h.BH_SchmittInput = true;
        } else {
            ft_eeprom_2232h.BH_SchmittInput = false;
        }
        short s9 = (short) ((iArr[11] & 24) >> 3);
        if (s9 < 4) {
            ft_eeprom_2232h.TPRDRV = s9;
        } else {
            ft_eeprom_2232h.TPRDRV = 0;
        }
        int i3 = iArr[7] & 255;
        if (this.mEepromType == 70) {
            ft_eeprom_2232h.Manufacturer = getStringDescriptor((i3 - 128) / 2, iArr);
            ft_eeprom_2232h.Product = getStringDescriptor(((iArr[8] & 255) - 128) / 2, iArr);
            ft_eeprom_2232h.SerialNumber = getStringDescriptor(((iArr[9] & 255) - 128) / 2, iArr);
        } else {
            ft_eeprom_2232h.Manufacturer = getStringDescriptor(i3 / 2, iArr);
            ft_eeprom_2232h.Product = getStringDescriptor((iArr[8] & 255) / 2, iArr);
            ft_eeprom_2232h.SerialNumber = getStringDescriptor((iArr[9] & 255) / 2, iArr);
        }
        return ft_eeprom_2232h;
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

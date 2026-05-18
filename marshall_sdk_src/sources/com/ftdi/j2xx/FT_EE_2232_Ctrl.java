package com.ftdi.j2xx;

import androidx.core.view.MotionEventCompat;
import com.ftdi.j2xx.D2xxManager;

/* JADX INFO: loaded from: classes.dex */
class FT_EE_2232_Ctrl extends FT_EE_Ctrl {
    private static final short CHECKSUM_LOCATION = 63;
    private static final String DEFAULT_PID = "6010";
    private static final byte EEPROM_SIZE_LOCATION = 10;

    FT_EE_2232_Ctrl(FT_Device fT_Device) throws D2xxManager.D2xxException {
        super(fT_Device);
        getEepromSize((byte) 10);
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    short programEeprom(FT_EEPROM ft_eeprom) {
        boolean z;
        int i;
        int[] iArr = new int[this.mEepromSize];
        if (ft_eeprom.getClass() != FT_EEPROM_2232D.class) {
            return (short) 1;
        }
        FT_EEPROM_2232D ft_eeprom_2232d = (FT_EEPROM_2232D) ft_eeprom;
        try {
            iArr[0] = 0;
            if (ft_eeprom_2232d.A_FIFO) {
                iArr[0] = iArr[0] | 1;
            } else if (ft_eeprom_2232d.A_FIFOTarget) {
                iArr[0] = iArr[0] | 2;
            } else {
                iArr[0] = iArr[0] | 4;
            }
            if (ft_eeprom_2232d.A_HighIO) {
                iArr[0] = iArr[0] | 16;
            }
            if (ft_eeprom_2232d.A_LoadVCP) {
                iArr[0] = iArr[0] | 8;
            } else if (ft_eeprom_2232d.B_FIFO) {
                iArr[0] = iArr[0] | 256;
            } else if (ft_eeprom_2232d.B_FIFOTarget) {
                iArr[0] = iArr[0] | 512;
            } else {
                iArr[0] = iArr[0] | 1024;
            }
            if (ft_eeprom_2232d.B_HighIO) {
                iArr[0] = iArr[0] | 4096;
            }
            if (ft_eeprom_2232d.B_LoadVCP) {
                iArr[0] = iArr[0] | 2048;
            }
            iArr[1] = ft_eeprom_2232d.VendorId;
            iArr[2] = ft_eeprom_2232d.ProductId;
            iArr[3] = 1280;
            iArr[4] = setUSBConfig(ft_eeprom);
            iArr[4] = setDeviceControl(ft_eeprom);
            if (this.mEepromType == 70) {
                z = true;
                i = 11;
            } else {
                z = false;
                i = 75;
            }
            int stringDescriptor = setStringDescriptor(ft_eeprom_2232d.Product, iArr, setStringDescriptor(ft_eeprom_2232d.Manufacturer, iArr, i, 7, z), 8, z);
            if (ft_eeprom_2232d.SerNumEnable) {
                setStringDescriptor(ft_eeprom_2232d.SerialNumber, iArr, stringDescriptor, 9, z);
            }
            iArr[10] = this.mEepromType;
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
        FT_EEPROM_2232D ft_eeprom_2232d = new FT_EEPROM_2232D();
        int[] iArr = new int[this.mEepromSize];
        for (int i = 0; i < this.mEepromSize; i++) {
            try {
                iArr[i] = readWord((short) i);
            } catch (Exception unused) {
                return null;
            }
        }
        short s = (short) (iArr[0] & 7);
        if (s == 0) {
            ft_eeprom_2232d.A_UART = true;
        } else if (s == 1) {
            ft_eeprom_2232d.A_FIFO = true;
        } else if (s == 2) {
            ft_eeprom_2232d.A_FIFOTarget = true;
        } else if (s == 4) {
            ft_eeprom_2232d.A_FastSerial = true;
        }
        if (((short) ((iArr[0] & 8) >> 3)) == 1) {
            ft_eeprom_2232d.A_LoadVCP = true;
        } else {
            ft_eeprom_2232d.A_HighIO = true;
        }
        if (((short) ((iArr[0] & 16) >> 4)) == 1) {
            ft_eeprom_2232d.A_HighIO = true;
        }
        short s2 = (short) ((iArr[0] & 1792) >> 8);
        if (s2 == 0) {
            ft_eeprom_2232d.B_UART = true;
        } else if (s2 == 1) {
            ft_eeprom_2232d.B_FIFO = true;
        } else if (s2 == 2) {
            ft_eeprom_2232d.B_FIFOTarget = true;
        } else if (s2 == 4) {
            ft_eeprom_2232d.B_FastSerial = true;
        }
        if (((short) ((iArr[0] & 2048) >> 11)) == 1) {
            ft_eeprom_2232d.B_LoadVCP = true;
        } else {
            ft_eeprom_2232d.B_LoadD2XX = true;
        }
        if (((short) ((iArr[0] & 4096) >> 12)) == 1) {
            ft_eeprom_2232d.B_HighIO = true;
        }
        ft_eeprom_2232d.VendorId = (short) iArr[1];
        ft_eeprom_2232d.ProductId = (short) iArr[2];
        getUSBConfig(ft_eeprom_2232d, iArr[4]);
        int i2 = iArr[7] & 255;
        if (this.mEepromType == 70) {
            ft_eeprom_2232d.Manufacturer = getStringDescriptor((i2 - 128) / 2, iArr);
            ft_eeprom_2232d.Product = getStringDescriptor(((iArr[8] & 255) - 128) / 2, iArr);
            ft_eeprom_2232d.SerialNumber = getStringDescriptor(((iArr[9] & 255) - 128) / 2, iArr);
        } else {
            ft_eeprom_2232d.Manufacturer = getStringDescriptor(i2 / 2, iArr);
            ft_eeprom_2232d.Product = getStringDescriptor((iArr[8] & 255) / 2, iArr);
            ft_eeprom_2232d.SerialNumber = getStringDescriptor((iArr[9] & 255) / 2, iArr);
        }
        return ft_eeprom_2232d;
    }

    @Override // com.ftdi.j2xx.FT_EE_Ctrl
    int getUserSize() {
        int word = readWord((short) 9);
        return (((this.mEepromSize - 1) - 1) - ((word & 255) + (((word & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) / 2))) * 2;
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

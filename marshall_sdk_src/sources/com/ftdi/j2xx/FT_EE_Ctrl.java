package com.ftdi.j2xx;

import android.util.Log;
import com.ftdi.j2xx.D2xxManager;
import iaik.security.ssl.SSLContext;
import kotlin.UShort;

/* JADX INFO: loaded from: classes.dex */
class FT_EE_Ctrl {
    private static final int BUS_POWERED = 128;
    private static final short EE_MAX_SIZE = 1024;
    private static final int ENABLE_SERIAL_NUMBER = 8;
    private static final int PULL_DOWN_IN_USB_SUSPEND = 4;
    private static final int SELF_POWERED = 64;
    private static final int USB_REMOTE_WAKEUP = 32;
    private FT_Device mDevice;
    boolean mEepromBlank;
    int mEepromSize;
    short mEepromType;

    int getUserSize() {
        return 0;
    }

    short programEeprom(FT_EEPROM ft_eeprom) {
        return (short) 1;
    }

    FT_EEPROM readEeprom() {
        return null;
    }

    byte[] readUserData(int i) {
        return null;
    }

    int writeUserData(byte[] bArr) {
        return 0;
    }

    static final class EepromType {
        static final short INVALID = 255;
        static final short TYPE_46 = 70;
        static final short TYPE_52 = 82;
        static final short TYPE_56 = 86;
        static final short TYPE_66 = 102;
        static final short TYPE_MTP = 1;

        EepromType() {
        }
    }

    FT_EE_Ctrl(FT_Device fT_Device) {
        this.mDevice = fT_Device;
    }

    int readWord(short s) {
        byte[] bArr = new byte[2];
        if (s >= 1024) {
            return -1;
        }
        this.mDevice.getConnection().controlTransfer(-64, 144, 0, s, bArr, 2, 0);
        return ((bArr[1] & 255) << 8) | (bArr[0] & 255);
    }

    boolean writeWord(short s, short s2) {
        return s < 1024 && this.mDevice.getConnection().controlTransfer(64, 145, s2 & UShort.MAX_VALUE, s & UShort.MAX_VALUE, null, 0, 0) == 0;
    }

    int eraseEeprom() {
        return this.mDevice.getConnection().controlTransfer(64, 146, 0, 0, null, 0, 0);
    }

    boolean programEeprom(int[] iArr, int i) {
        int i2 = 43690;
        int i3 = 0;
        while (i3 < i) {
            writeWord((short) i3, (short) iArr[i3]);
            int i4 = (i2 ^ iArr[i3]) & 65535;
            i2 = (((short) ((i4 >> 15) & 65535)) | ((short) ((i4 << 1) & 65535))) & 65535;
            i3++;
            Log.d("FT_EE_Ctrl", "Entered WriteWord Checksum : " + i2);
        }
        writeWord((short) i, (short) i2);
        return true;
    }

    int setUSBConfig(Object obj) {
        FT_EEPROM ft_eeprom = (FT_EEPROM) obj;
        int i = ft_eeprom.RemoteWakeup ? 160 : 128;
        if (ft_eeprom.SelfPowered) {
            i |= 64;
        }
        return ((ft_eeprom.MaxPower / 2) << 8) | i;
    }

    void getUSBConfig(FT_EEPROM ft_eeprom, int i) {
        ft_eeprom.MaxPower = (short) (((byte) (i >> 8)) * 2);
        byte b = (byte) i;
        if ((b & 64) == 64 && (b & 128) == 128) {
            ft_eeprom.SelfPowered = true;
        } else {
            ft_eeprom.SelfPowered = false;
        }
        if ((b & 32) == 32) {
            ft_eeprom.RemoteWakeup = true;
        } else {
            ft_eeprom.RemoteWakeup = false;
        }
    }

    int setDeviceControl(Object obj) {
        FT_EEPROM ft_eeprom = (FT_EEPROM) obj;
        int i = ft_eeprom.PullDownEnable ? 4 : 0;
        return ft_eeprom.SerNumEnable ? i | 8 : i & 247;
    }

    void getDeviceControl(Object obj, int i) {
        FT_EEPROM ft_eeprom = (FT_EEPROM) obj;
        if ((i & 4) > 0) {
            ft_eeprom.PullDownEnable = true;
        } else {
            ft_eeprom.PullDownEnable = false;
        }
        if ((i & 8) > 0) {
            ft_eeprom.SerNumEnable = true;
        } else {
            ft_eeprom.SerNumEnable = false;
        }
    }

    int setStringDescriptor(String str, int[] iArr, int i, int i2, boolean z) {
        int length = (str.length() * 2) + 2;
        iArr[i2] = (length << 8) | (i * 2);
        if (z) {
            iArr[i2] = iArr[i2] + 128;
        }
        char[] charArray = str.toCharArray();
        int i3 = i + 1;
        iArr[i] = length | SSLContext.VERSION_SSL30;
        int i4 = (length - 2) / 2;
        int i5 = 0;
        while (true) {
            int i6 = i3 + 1;
            iArr[i3] = charArray[i5];
            i5++;
            if (i5 >= i4) {
                return i6;
            }
            i3 = i6;
        }
    }

    String getStringDescriptor(int i, int[] iArr) {
        int i2 = ((iArr[i] & 255) / 2) - 1;
        int i3 = i + 1;
        int i4 = i2 + i3;
        String str = "";
        while (i3 < i4) {
            str = String.valueOf(str) + ((char) iArr[i3]);
            i3++;
        }
        return str;
    }

    void clearUserDataArea(int i, int i2, int[] iArr) {
        while (i < i2) {
            iArr[i] = 0;
            i++;
        }
    }

    int getEepromSize(byte b) throws D2xxManager.D2xxException {
        short word = (short) readWord((short) (b & (-1)));
        if (word != 65535) {
            if (word == 70) {
                this.mEepromType = (short) 70;
                this.mEepromSize = 64;
                this.mEepromBlank = false;
                return 64;
            }
            if (word == 82) {
                this.mEepromType = (short) 82;
                this.mEepromSize = 1024;
                this.mEepromBlank = false;
                return 1024;
            }
            if (word == 86) {
                this.mEepromType = (short) 86;
                this.mEepromSize = 128;
                this.mEepromBlank = false;
                return 128;
            }
            if (word != 102) {
                return 0;
            }
            this.mEepromType = (short) 102;
            this.mEepromSize = 128;
            this.mEepromBlank = false;
            return 256;
        }
        boolean zWriteWord = writeWord((short) 192, (short) 192);
        readWord((short) 192);
        readWord((short) 64);
        readWord((short) 0);
        if (!zWriteWord) {
            this.mEepromType = (short) 255;
            this.mEepromSize = 0;
            return 0;
        }
        this.mEepromBlank = true;
        if ((readWord((short) 0) & 255) == 192) {
            eraseEeprom();
            this.mEepromType = (short) 70;
            this.mEepromSize = 64;
            return 64;
        }
        if ((readWord((short) 64) & 255) == 192) {
            eraseEeprom();
            this.mEepromType = (short) 86;
            this.mEepromSize = 128;
            return 128;
        }
        if ((readWord((short) 192) & 255) == 192) {
            eraseEeprom();
            this.mEepromType = (short) 102;
            this.mEepromSize = 128;
            return 256;
        }
        eraseEeprom();
        return 0;
    }
}

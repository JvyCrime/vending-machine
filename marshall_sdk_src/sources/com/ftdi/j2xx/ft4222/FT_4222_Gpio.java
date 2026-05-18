package com.ftdi.j2xx.ft4222;

import android.util.Log;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.interfaces.Gpio;

/* JADX INFO: loaded from: classes.dex */
public class FT_4222_Gpio implements Gpio {
    static final int GET_DIRECTION = 33;
    static final int GET_OPEN_DRAIN = 35;
    static final int GET_PULL_DOWN = 36;
    static final int GET_PULL_UP = 34;
    static final int GET_STATUS = 32;
    static final int SET_DIRECTION = 33;
    static final int SET_OPEN_DRAIN = 35;
    static final int SET_PULL_DOWN = 36;
    static final int SET_PULL_UP = 34;
    private static final int TOTAL_GPIOS = 4;
    private FT_4222_Device mFT4222Device;
    private FT_Device mFtDev;

    boolean IntToBool(int i) {
        return i != 0;
    }

    public FT_4222_Gpio(FT_4222_Device fT_4222_Device) {
        this.mFT4222Device = fT_4222_Device;
        this.mFtDev = fT_4222_Device.mFtDev;
    }

    int cmdSet(int i, int i2) {
        return this.mFtDev.VendorCmdSet(33, i | (i2 << 8));
    }

    int cmdSet(int i, int i2, byte[] bArr, int i3) {
        return this.mFtDev.VendorCmdSet(33, i | (i2 << 8), bArr, i3);
    }

    int cmdGet(int i, int i2, byte[] bArr, int i3) {
        return this.mFtDev.VendorCmdGet(32, i | (i2 << 8), bArr, i3);
    }

    @Override // com.ftdi.j2xx.interfaces.Gpio
    public int init(int[] iArr) {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        gpio_dev gpio_devVar = new gpio_dev(getFWVersion());
        byte[] bArr = new byte[1];
        gpio_mgr gpio_mgrVar = new gpio_mgr();
        int iInit = this.mFT4222Device.init();
        if (iInit != 0) {
            Log.e("GPIO_M", "FT4222_GPIO init - 1 NG ftStatus:" + iInit);
            return iInit;
        }
        if (chiptop_mgrVar.chip_mode == 2 || chiptop_mgrVar.chip_mode == 3) {
            return 1013;
        }
        getStatus(gpio_devVar);
        byte b = gpio_devVar.dir;
        bArr[0] = gpio_devVar.dat[0];
        for (int i = 0; i < 4; i++) {
            b = (byte) ((iArr[i] == 1 ? b | (1 << i) : b & (~(1 << i))) & 15);
        }
        gpio_mgrVar.lastGpioData = bArr[0];
        cmdSet(33, b);
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.Gpio
    public int read(int i, boolean[] zArr) {
        gpio_dev gpio_devVar = new gpio_dev(getFWVersion());
        int iCheck = check(i);
        if (iCheck != 0) {
            return iCheck;
        }
        int status = getStatus(gpio_devVar);
        if (status != 0) {
            return status;
        }
        getGpioPinLevel(i, gpio_devVar.dat[0], zArr);
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.Gpio
    public int write(int i, boolean z) {
        gpio_dev gpio_devVar = new gpio_dev(getFWVersion());
        int iCheck = check(i);
        if (iCheck != 0) {
            return iCheck;
        }
        if (!is_GPIOPort_Valid_Output(i)) {
            return 1015;
        }
        getStatus(gpio_devVar);
        if (z) {
            byte[] bArr = gpio_devVar.dat;
            bArr[0] = (byte) ((1 << i) | bArr[0]);
        } else {
            byte[] bArr2 = gpio_devVar.dat;
            bArr2[0] = (byte) ((~(1 << i)) & 15 & bArr2[0]);
        }
        return this.mFtDev.write(gpio_devVar.dat, 1) > 0 ? 0 : 18;
    }

    int check(int i) {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        if (chiptop_mgrVar.chip_mode == 2 || chiptop_mgrVar.chip_mode == 3) {
            return 1013;
        }
        return i >= 4 ? 1014 : 0;
    }

    int getStatus(gpio_dev gpio_devVar) {
        byte[] bArr = getFWVersion() < 'B' ? new byte[8] : new byte[6];
        int iCmdGet = cmdGet(32, 0, bArr, bArr.length);
        gpio_devVar.usb.ep_in = bArr[0];
        gpio_devVar.usb.ep_out = bArr[1];
        gpio_devVar.mask = bArr[bArr.length - 3];
        gpio_devVar.dir = bArr[bArr.length - 2];
        gpio_devVar.dat[0] = bArr[bArr.length - 1];
        if (iCmdGet == bArr.length) {
            return 0;
        }
        return iCmdGet;
    }

    void getGpioPinLevel(int i, byte b, boolean[] zArr) {
        zArr[0] = IntToBool(((b & (1 << i)) >> i) & 1);
    }

    boolean is_GPIOPort(int i) {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        byte b = chiptop_mgrVar.chip_mode;
        if (b == 0) {
            if ((i == 0 || i == 1) && (chiptop_mgrVar.function == 1 || chiptop_mgrVar.function == 2)) {
                z = false;
            }
            if (IntToBool(chiptop_mgrVar.enable_suspend_out) && i == 2) {
                z = false;
            }
            if (!IntToBool(chiptop_mgrVar.enable_wakeup_int) || i != 3) {
                return z;
            }
        } else if (b == 1) {
            z = (i == 0 || i == 1) ? false : true;
            if (IntToBool(chiptop_mgrVar.enable_suspend_out) && i == 2) {
                z = false;
            }
            if (!IntToBool(chiptop_mgrVar.enable_wakeup_int) || i != 3) {
                return z;
            }
        } else if (b != 2 && b != 3) {
            return true;
        }
        return false;
    }

    boolean is_GPIOPort_Valid_Output(int i) {
        gpio_dev gpio_devVar = new gpio_dev(getFWVersion());
        boolean zIs_GPIOPort = is_GPIOPort(i);
        getStatus(gpio_devVar);
        if (!zIs_GPIOPort || ((gpio_devVar.dir >> i) & 1) == 1) {
            return zIs_GPIOPort;
        }
        return false;
    }

    boolean is_GPIOPort_Valid_Input(int i) {
        gpio_dev gpio_devVar = new gpio_dev(getFWVersion());
        boolean zIs_GPIOPort = is_GPIOPort(i);
        getStatus(gpio_devVar);
        if (!zIs_GPIOPort || ((gpio_devVar.dir >> i) & 1) == 0) {
            return zIs_GPIOPort;
        }
        return false;
    }

    boolean update_GPIO_Status(int i, int i2) {
        gpio_mgr gpio_mgrVar = new gpio_mgr();
        if (gpio_mgrVar.gpioStatus[i] == i2) {
            return true;
        }
        gpio_mgrVar.gpioStatus[i] = i2;
        char c = 0;
        char c2 = 0;
        char c3 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            int i4 = gpio_mgrVar.gpioStatus[i3];
            if (i4 == 1) {
                c = (char) (c + (1 << i3));
            } else if (i4 == 2) {
                c2 = (char) (c2 + (1 << i3));
            } else if (i4 == 3) {
                c3 = (char) (c3 + (1 << i3));
            }
        }
        int iCmdSet = cmdSet(34, c) | cmdSet(36, c2) | cmdSet(35, c3);
        if (iCmdSet == 0) {
            gpio_mgrVar.gpioStatus[i] = i2;
        }
        return iCmdSet == 0;
    }

    char getFWVersion() {
        return this.mFT4222Device.GetVersion();
    }

    @Override // com.ftdi.j2xx.interfaces.Gpio
    public int getInterruptStatus(boolean[] zArr) {
        byte[] bArr;
        int queueStatus = this.mFtDev.getQueueStatus();
        zArr[0] = false;
        if (queueStatus > 0) {
            bArr = new byte[queueStatus];
            this.mFtDev.read(bArr, queueStatus);
        } else {
            bArr = null;
        }
        for (int i = 0; i < queueStatus; i++) {
            if (8 == (bArr[i] & 8)) {
                zArr[0] = true;
            }
        }
        return 0;
    }
}

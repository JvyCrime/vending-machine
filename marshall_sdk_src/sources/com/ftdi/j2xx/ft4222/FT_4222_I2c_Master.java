package com.ftdi.j2xx.ft4222;

import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import com.ftdi.j2xx.interfaces.I2cMaster;

/* JADX INFO: loaded from: classes.dex */
public class FT_4222_I2c_Master implements I2cMaster {
    FT_4222_Device mFt4222Dev;
    FT_Device mFtDev;
    int mI2cMasterKbps;

    private int i2c_master_setup_timer_period(int i, int i2) {
        double d = i != 1 ? i != 2 ? i != 3 ? 16.666666666666668d : 12.5d : 20.833333333333332d : 41.666666666666664d;
        if (i2 <= 100) {
            int i3 = (int) ((((1000000.0d / ((double) i2)) / (d * 8.0d)) - 1.0d) + 0.5d);
            if (i3 > 127) {
                return 127;
            }
            return i3;
        }
        if ((100 < i2 && i2 <= 400) || (400 < i2 && i2 <= 1000)) {
            return ((int) ((((1000000.0d / ((double) i2)) / (d * 6.0d)) - 1.0d) + 0.5d)) | 192;
        }
        if (1000 >= i2 || i2 > 3400) {
            return 74;
        }
        return (((int) ((((1000000.0d / ((double) i2)) / (d * 6.0d)) - 1.0d) + 0.5d)) | 128) & (-65);
    }

    int I2C_Address_Check(int i) {
        return (i & 64512) > 0 ? 1007 : 0;
    }

    public FT_4222_I2c_Master(FT_4222_Device fT_4222_Device) {
        this.mFt4222Dev = fT_4222_Device;
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

    @Override // com.ftdi.j2xx.interfaces.I2cMaster
    public int init(int i) {
        byte[] bArr = new byte[1];
        int iInit = this.mFt4222Dev.init();
        if (iInit != 0) {
            return iInit;
        }
        if (!I2C_Mode_Check()) {
            return 1012;
        }
        cmdSet(81, 0);
        int clock = this.mFt4222Dev.getClock(bArr);
        if (clock != 0) {
            return clock;
        }
        int iI2c_master_setup_timer_period = i2c_master_setup_timer_period(bArr[0], i);
        int iCmdSet = cmdSet(5, 1);
        if (iCmdSet < 0) {
            return iCmdSet;
        }
        this.mFt4222Dev.mChipStatus.function = (byte) 1;
        int iCmdSet2 = cmdSet(82, iI2c_master_setup_timer_period);
        if (iCmdSet2 < 0) {
            return iCmdSet2;
        }
        this.mI2cMasterKbps = i;
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.I2cMaster
    public int reset() {
        int iI2C_Check = I2C_Check(true);
        return iI2C_Check != 0 ? iI2C_Check : cmdSet(81, 1);
    }

    @Override // com.ftdi.j2xx.interfaces.I2cMaster
    public int read(int i, byte[] bArr, int i2, int[] iArr) {
        return readEx(i, 6, bArr, i2, iArr);
    }

    @Override // com.ftdi.j2xx.interfaces.I2cMaster
    public int readEx(int i, int i2, byte[] bArr, int i3, int[] iArr) {
        short s = (short) (65535 & i);
        short s2 = (short) i3;
        byte[] bArr2 = new byte[4];
        if (bArr.length < i3) {
            return FT_4222_Defines.FT4222_STATUS.FT4222_EXCEED_ARRAY_LENGTH;
        }
        int iI2C_Version_Check = I2C_Version_Check(i2);
        if (iI2C_Version_Check != 0) {
            return iI2C_Version_Check;
        }
        int iI2C_Address_Check = I2C_Address_Check(i);
        if (iI2C_Address_Check != 0) {
            return iI2C_Address_Check;
        }
        if (i3 < 1) {
            return 6;
        }
        int iI2C_Check = I2C_Check(true);
        if (iI2C_Check != 0) {
            return iI2C_Check;
        }
        bArr2[0] = (byte) ((s << 1) + 1);
        bArr2[1] = (byte) i2;
        bArr2[2] = (byte) ((s2 >> 8) & 255);
        bArr2[3] = (byte) (s2 & 255);
        if (4 != this.mFtDev.write(bArr2, 4)) {
            return 1011;
        }
        byte[] bArr3 = new byte[1024];
        iArr[0] = 0;
        int i4 = 0;
        while (i3 > 0) {
            int queueStatus = this.mFtDev.getQueueStatus();
            if (queueStatus == 0) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException unused) {
                }
            } else {
                if (queueStatus > i3) {
                    queueStatus = i3;
                }
                if (queueStatus > 1024) {
                    queueStatus = 1024;
                }
                int i5 = this.mFtDev.read(bArr3, queueStatus);
                if (i5 < 0) {
                    return 1011;
                }
                for (int i6 = 0; i6 < i5; i6++) {
                    bArr[i4] = bArr3[i6];
                    i4++;
                }
                iArr[0] = iArr[0] + i5;
                i3 -= i5;
            }
        }
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.I2cMaster
    public int write(int i, byte[] bArr, int i2, int[] iArr) {
        return writeEx(i, 6, bArr, i2, iArr);
    }

    @Override // com.ftdi.j2xx.interfaces.I2cMaster
    public int writeEx(int i, int i2, byte[] bArr, int i3, int[] iArr) {
        short s = (short) i;
        short s2 = (short) i3;
        int i4 = i3 + 4;
        byte[] bArr2 = new byte[i4];
        int[] iArr2 = new int[1];
        if (bArr.length < i3) {
            return FT_4222_Defines.FT4222_STATUS.FT4222_EXCEED_ARRAY_LENGTH;
        }
        int iI2C_Version_Check = I2C_Version_Check(i2);
        if (iI2C_Version_Check != 0) {
            return iI2C_Version_Check;
        }
        int iI2C_Address_Check = I2C_Address_Check(i);
        if (iI2C_Address_Check != 0) {
            return iI2C_Address_Check;
        }
        if (i3 < 1) {
            return 6;
        }
        int iI2C_Check = I2C_Check(true);
        if (iI2C_Check != 0) {
            return iI2C_Check;
        }
        if (6 == i2) {
            return writeAuto(i, i2, bArr, i3, iArr);
        }
        int maxTransferSize = getMaxTransferSize(iArr2);
        if (maxTransferSize != 0) {
            return maxTransferSize;
        }
        if (i3 > iArr2[0]) {
            return 1010;
        }
        iArr[0] = 0;
        bArr2[0] = (byte) (s << 1);
        bArr2[1] = (byte) i2;
        bArr2[2] = (byte) ((s2 >> 8) & 255);
        bArr2[3] = (byte) (s2 & 255);
        for (int i5 = 0; i5 < i3; i5++) {
            bArr2[i5 + 4] = bArr[i5];
        }
        int iWrite = this.mFtDev.write(bArr2, i4);
        if (iWrite < 0) {
            return 10;
        }
        iArr[0] = iWrite - 4;
        return 0;
    }

    int writeAuto(int i, int i2, byte[] bArr, int i3, int[] iArr) {
        boolean z;
        byte b;
        short s = (short) i;
        int[] iArr2 = new int[1];
        int maxTransferSize = getMaxTransferSize(iArr2);
        if (maxTransferSize != 0) {
            return maxTransferSize;
        }
        byte[] bArr2 = new byte[iArr2[0] + 4];
        bArr2[0] = (byte) (s << 1);
        iArr[0] = 0;
        boolean z2 = true;
        int i4 = 0;
        while (i3 > 0) {
            int iMin = Math.min(iArr2[0], i3);
            if (z2) {
                b = (i2 & 2) == 2 ? (byte) (i2 & 3) : (byte) 0;
                z = false;
            } else {
                z = z2;
                b = 0;
            }
            if (i3 == iMin && (i2 & 4) == 4) {
                b = (byte) (b | 4);
            }
            if (b == 0) {
                b = -128;
            }
            bArr2[1] = b;
            bArr2[2] = (byte) ((iMin >> 8) & 255);
            bArr2[3] = (byte) (iMin & 255);
            for (int i5 = 0; i5 < iMin; i5++) {
                bArr2[i5 + 4] = bArr[i4];
                i4++;
            }
            int iWrite = this.mFtDev.write(bArr2, iMin + 4);
            if (iWrite < 0) {
                return 10;
            }
            int i6 = iWrite - 4;
            iArr[0] = iArr[0] + i6;
            i3 -= i6;
            z2 = z;
        }
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.I2cMaster
    public int getStatus(int i, byte[] bArr) {
        int iI2C_Check = I2C_Check(true);
        return iI2C_Check != 0 ? iI2C_Check : this.mFtDev.VendorCmdGet(34, 62900, bArr, 1) < 0 ? 18 : 0;
    }

    boolean I2C_Mode_Check() {
        return this.mFt4222Dev.mChipStatus.chip_mode == 0 || this.mFt4222Dev.mChipStatus.chip_mode == 3;
    }

    int I2C_Check(boolean z) {
        return z ? this.mFt4222Dev.mChipStatus.function != 1 ? 1004 : 0 : this.mFt4222Dev.mChipStatus.function != 2 ? 1004 : 0;
    }

    int I2C_Version_Check(int i) {
        FT_Device fT_Device = this.mFtDev;
        if (fT_Device == null || !fT_Device.isOpen()) {
            return 3;
        }
        if (i == 6 || getFWVersion() >= 'B') {
            return 0;
        }
        return FT_4222_Defines.FT4222_STATUS.FT4222_FUN_NOT_SUPPORT;
    }

    public int getMaxTransferSize(int[] iArr) {
        iArr[0] = 0;
        int maxBuckSize = this.mFt4222Dev.getMaxBuckSize();
        if (this.mFt4222Dev.mChipStatus.function != 1) {
            return 17;
        }
        iArr[0] = maxBuckSize - 4;
        return 0;
    }

    char getFWVersion() {
        return this.mFt4222Dev.GetVersion();
    }
}

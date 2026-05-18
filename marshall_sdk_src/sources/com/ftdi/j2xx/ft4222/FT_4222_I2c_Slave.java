package com.ftdi.j2xx.ft4222;

import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import com.ftdi.j2xx.interfaces.I2cSlave;

/* JADX INFO: loaded from: classes.dex */
public class FT_4222_I2c_Slave implements I2cSlave {
    FT_4222_Device mFt4222Dev;
    FT_Device mFtDev;

    public FT_4222_I2c_Slave(FT_4222_Device fT_4222_Device) {
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

    @Override // com.ftdi.j2xx.interfaces.I2cSlave
    public int init() {
        int iInit = this.mFt4222Dev.init();
        if (iInit != 0) {
            return iInit;
        }
        if (!I2C_ModeCheck()) {
            return 1012;
        }
        int iCmdSet = cmdSet(5, 2);
        if (iCmdSet < 0) {
            return iCmdSet;
        }
        this.mFt4222Dev.mChipStatus.function = (byte) 2;
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.I2cSlave
    public int reset() {
        int iI2C_Check = I2C_Check(false);
        return iI2C_Check != 0 ? iI2C_Check : cmdSet(91, 1);
    }

    @Override // com.ftdi.j2xx.interfaces.I2cSlave
    public int getAddress(int[] iArr) {
        byte[] bArr = new byte[1];
        int iI2C_Check = I2C_Check(false);
        if (iI2C_Check != 0) {
            return iI2C_Check;
        }
        if (this.mFtDev.VendorCmdGet(33, 92, bArr, 1) < 0) {
            return 18;
        }
        iArr[0] = bArr[0];
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.I2cSlave
    public int setAddress(int i) {
        byte[] bArr = {(byte) (i & 255)};
        int iI2C_Check = I2C_Check(false);
        return iI2C_Check != 0 ? iI2C_Check : cmdSet(92, bArr[0]) < 0 ? 18 : 0;
    }

    @Override // com.ftdi.j2xx.interfaces.I2cSlave
    public int read(byte[] bArr, int i, int[] iArr) {
        int i2 = i;
        long jCurrentTimeMillis = System.currentTimeMillis();
        int readTimeout = this.mFtDev.getReadTimeout();
        if (bArr.length < i2) {
            return FT_4222_Defines.FT4222_STATUS.FT4222_EXCEED_ARRAY_LENGTH;
        }
        if (i2 < 1) {
            return 6;
        }
        int iI2C_Check = I2C_Check(false);
        if (iI2C_Check != 0) {
            return iI2C_Check;
        }
        byte[] bArr2 = new byte[1024];
        iArr[0] = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 > 0) {
            int queueStatus = this.mFtDev.getQueueStatus();
            if (queueStatus == 0) {
                i3++;
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException unused) {
                }
                if (5 == i3 && System.currentTimeMillis() - jCurrentTimeMillis > readTimeout) {
                    break;
                }
            } else {
                if (queueStatus > i2) {
                    queueStatus = i2;
                }
                if (queueStatus > 1024) {
                    queueStatus = 1024;
                }
                int i5 = this.mFtDev.read(bArr2, queueStatus);
                if (i5 < 0) {
                    return 1011;
                }
                for (int i6 = 0; i6 < i5; i6++) {
                    bArr[i4] = bArr2[i6];
                    i4++;
                }
                iArr[0] = iArr[0] + i5;
                i2 -= i5;
                i3 = 0;
            }
        }
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.I2cSlave
    public int write(byte[] bArr, int i, int[] iArr) {
        int[] iArr2 = new int[1];
        if (bArr.length < i) {
            return FT_4222_Defines.FT4222_STATUS.FT4222_EXCEED_ARRAY_LENGTH;
        }
        if (i < 1) {
            return 6;
        }
        int iI2C_Check = I2C_Check(false);
        if (iI2C_Check != 0) {
            return iI2C_Check;
        }
        int maxTransferSize = getMaxTransferSize(iArr2);
        if (maxTransferSize != 0) {
            return maxTransferSize;
        }
        byte[] bArr2 = new byte[iArr2[0]];
        iArr[0] = 0;
        int i2 = 0;
        while (i > 0) {
            int iMin = Math.min(iArr2[0], i);
            for (int i3 = 0; i3 < iMin; i3++) {
                bArr2[i3] = bArr[i2];
                i2++;
            }
            int iWrite = this.mFtDev.write(bArr2, iMin);
            if (iWrite < 0) {
                return 10;
            }
            iArr[0] = iArr[0] + iWrite;
            i -= iWrite;
        }
        return 0;
    }

    boolean I2C_ModeCheck() {
        return this.mFt4222Dev.mChipStatus.chip_mode == 0 || this.mFt4222Dev.mChipStatus.chip_mode == 3;
    }

    int I2C_Check(boolean z) {
        return z ? this.mFt4222Dev.mChipStatus.function != 1 ? 1004 : 0 : this.mFt4222Dev.mChipStatus.function != 2 ? 1004 : 0;
    }

    int getMaxTransferSize(int[] iArr) {
        iArr[0] = 0;
        int maxBuckSize = this.mFt4222Dev.getMaxBuckSize();
        if (this.mFt4222Dev.mChipStatus.function != 2) {
            return 17;
        }
        iArr[0] = maxBuckSize;
        return 0;
    }

    public int setClockStretch(boolean z) {
        if (z) {
            return cmdSet(94, 1);
        }
        return cmdSet(94, 0);
    }
}

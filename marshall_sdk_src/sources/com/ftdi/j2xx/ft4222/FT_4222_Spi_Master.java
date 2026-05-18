package com.ftdi.j2xx.ft4222;

import android.util.Log;
import androidx.core.view.MotionEventCompat;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.interfaces.SpiMaster;
import junit.framework.Assert;

/* JADX INFO: loaded from: classes.dex */
public class FT_4222_Spi_Master implements SpiMaster {
    private static final String TAG = "FTDI_Device::";
    private FT_4222_Device mFT4222Device;
    private FT_Device mFTDevice;
    private byte[] mPackWrBuf = new byte[16384];
    private byte[] mPackRdBuf = new byte[16384];

    public FT_4222_Spi_Master(FT_4222_Device fT_4222_Device) {
        this.mFT4222Device = fT_4222_Device;
        this.mFTDevice = fT_4222_Device.mFtDev;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiMaster
    public int init(int i, int i2, int i3, int i4, byte b) {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        SPI_MasterCfg sPI_MasterCfg = this.mFT4222Device.mSpiMasterCfg;
        sPI_MasterCfg.ioLine = i;
        sPI_MasterCfg.clock = i2;
        sPI_MasterCfg.cpol = i3;
        sPI_MasterCfg.cpha = i4;
        sPI_MasterCfg.ssoMap = b;
        byte b2 = 1;
        if (sPI_MasterCfg.ioLine != 1 && sPI_MasterCfg.ioLine != 2 && sPI_MasterCfg.ioLine != 4) {
            return 6;
        }
        this.mFT4222Device.cleanRxData();
        byte b3 = chiptop_mgrVar.chip_mode;
        if (b3 != 0) {
            if (b3 == 1) {
                b2 = 7;
            } else if (b3 == 2) {
                b2 = 15;
            } else if (b3 != 3) {
                b2 = 0;
            }
        }
        if ((sPI_MasterCfg.ssoMap & b2) == 0) {
            return 6;
        }
        sPI_MasterCfg.ssoMap = (byte) (sPI_MasterCfg.ssoMap & b2);
        if (this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.ioLine << 8) | 66) < 0 || this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.clock << 8) | 68) < 0 || this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.cpol << 8) | 69) < 0 || this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.cpha << 8) | 70) < 0 || this.mFTDevice.VendorCmdSet(33, 67) < 0 || this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.ssoMap << 8) | 72) < 0 || this.mFTDevice.VendorCmdSet(33, 773) < 0) {
            return 4;
        }
        chiptop_mgrVar.function = (byte) 3;
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiMaster
    public int setLines(int i) {
        if (this.mFT4222Device.mChipStatus.function != 3) {
            return 1003;
        }
        if (i == 0) {
            return 17;
        }
        if (this.mFTDevice.VendorCmdSet(33, (i << 8) | 66) < 0 || this.mFTDevice.VendorCmdSet(33, 330) < 0) {
            return 4;
        }
        this.mFT4222Device.mSpiMasterCfg.ioLine = i;
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiMaster
    public int singleWrite(byte[] bArr, int i, int[] iArr, boolean z) {
        return singleReadWrite(new byte[bArr.length], bArr, i, iArr, z);
    }

    @Override // com.ftdi.j2xx.interfaces.SpiMaster
    public int singleRead(byte[] bArr, int i, int[] iArr, boolean z) {
        return singleReadWrite(bArr, new byte[bArr.length], i, iArr, z);
    }

    @Override // com.ftdi.j2xx.interfaces.SpiMaster
    public int singleReadWrite(byte[] bArr, byte[] bArr2, int i, int[] iArr, boolean z) {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        SPI_MasterCfg sPI_MasterCfg = this.mFT4222Device.mSpiMasterCfg;
        if (bArr2 == null || bArr == null || iArr == null) {
            return 1009;
        }
        iArr[0] = 0;
        if (chiptop_mgrVar.function != 3 || sPI_MasterCfg.ioLine != 1) {
            return 1005;
        }
        if (i == 0) {
            return 6;
        }
        if (i > bArr2.length || i > bArr.length) {
            Assert.assertTrue("sizeToTransfer > writeBuffer.length || sizeToTransfer > readBuffer.length", false);
        }
        if (bArr2.length != bArr.length || bArr2.length == 0) {
            Assert.assertTrue("writeBuffer.length != readBuffer.length || writeBuffer.length == 0", false);
        }
        iArr[0] = sendReadWriteBuffer(this.mFTDevice, bArr2, bArr, i);
        if (z) {
            this.mFTDevice.write(null, 0);
        }
        if (iArr[0] == -1) {
            return 10;
        }
        return iArr[0] == -2 ? 1011 : 0;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiMaster
    public int multiReadWrite(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int[] iArr) {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        SPI_MasterCfg sPI_MasterCfg = this.mFT4222Device.mSpiMasterCfg;
        if (i3 > 0 && bArr == null) {
            return 1009;
        }
        int i4 = i + i2;
        if (i4 > 0 && bArr2 == null) {
            return 1009;
        }
        if (i3 > 0 && iArr == null) {
            return 1009;
        }
        if (chiptop_mgrVar.function != 3 || sPI_MasterCfg.ioLine == 1) {
            return 1006;
        }
        if (i > 15) {
            Log.e(TAG, "The maxium single write bytes are 15 bytes");
            return 6;
        }
        byte[] bArr3 = new byte[i + 5 + i2];
        bArr3[0] = (byte) ((i & 15) | 128);
        bArr3[1] = (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr3[2] = (byte) (i2 & 255);
        bArr3[3] = (byte) ((65280 & i3) >> 8);
        bArr3[4] = (byte) (i3 & 255);
        for (int i5 = 0; i5 < i4; i5++) {
            bArr3[i5 + 5] = bArr2[i5];
        }
        iArr[0] = setMultiReadWritePackage(this.mFTDevice, bArr3, bArr, i3);
        return 0;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiMaster
    public int reset() {
        return this.mFTDevice.VendorCmdSet(33, 74) < 0 ? 4 : 0;
    }

    public int setDrivingStrength(int i, int i2, int i3) {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        if (chiptop_mgrVar.function == 3 || chiptop_mgrVar.function == 4) {
            return (this.mFTDevice.VendorCmdSet(33, ((((i << 4) | (i2 << 2)) | i3) << 8) | 160) >= 0 && this.mFTDevice.VendorCmdSet(33, ((chiptop_mgrVar.function != 3 ? 4 : 3) << 8) | 5) >= 0) ? 0 : 4;
        }
        return 1003;
    }

    private int setMultiReadWritePackage(FT_Device fT_Device, byte[] bArr, byte[] bArr2, int i) {
        if (fT_Device == null || !fT_Device.isOpen()) {
            return -1;
        }
        sendMultiWriteBuffer(fT_Device, bArr);
        return sendMultiReadBuffer(fT_Device, bArr2, i);
    }

    private int sendMultiWriteBuffer(FT_Device fT_Device, byte[] bArr) {
        byte[] bArr2;
        int length = bArr.length;
        byte[] bArr3 = this.mPackWrBuf;
        int length2 = length / bArr3.length;
        int length3 = bArr.length % bArr3.length;
        int i = 0;
        for (int i2 = 0; i2 < length2; i2++) {
            int i3 = 0;
            while (true) {
                bArr2 = this.mPackWrBuf;
                if (i3 >= bArr2.length) {
                    break;
                }
                bArr2[i3] = bArr[i];
                i++;
                i3++;
            }
            int iWrite = fT_Device.write(bArr2, bArr2.length);
            if (this.mPackWrBuf.length != iWrite) {
                Log.e(TAG, "sendMultiWriteBuffer write error!!!");
                return -1;
            }
            if (iWrite <= 0) {
                return iWrite;
            }
        }
        if (length3 > 0) {
            for (int i4 = 0; i4 < length3; i4++) {
                this.mPackWrBuf[i4] = bArr[i];
                i++;
            }
            int iWrite2 = fT_Device.write(this.mPackWrBuf, length3);
            if (length3 != iWrite2) {
                Log.e(TAG, "sendMultiWriteBuffer write error!!!");
                return -1;
            }
            if (iWrite2 <= 0) {
                return iWrite2;
            }
        }
        return i;
    }

    private int sendMultiReadBuffer(FT_Device fT_Device, byte[] bArr, int i) {
        return fT_Device.read(bArr, i);
    }

    private int sendReadWriteBuffer(FT_Device fT_Device, byte[] bArr, byte[] bArr2, int i) {
        byte[] bArr3;
        byte[] bArr4 = this.mPackWrBuf;
        int length = i / bArr4.length;
        int length2 = i % bArr4.length;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            int i5 = 0;
            while (true) {
                bArr3 = this.mPackWrBuf;
                if (i5 >= bArr3.length) {
                    break;
                }
                bArr3[i5] = bArr[i2];
                i2++;
                i5++;
            }
            int readWritePackage = setReadWritePackage(fT_Device, bArr3, this.mPackRdBuf, bArr3.length);
            if (readWritePackage <= 0) {
                return readWritePackage;
            }
            int i6 = 0;
            while (true) {
                byte[] bArr5 = this.mPackRdBuf;
                if (i6 >= bArr5.length) {
                    break;
                }
                bArr2[i3] = bArr5[i6];
                i3++;
                i6++;
            }
        }
        if (length2 > 0) {
            for (int i7 = 0; i7 < length2; i7++) {
                this.mPackWrBuf[i7] = bArr[i2];
                i2++;
            }
            int readWritePackage2 = setReadWritePackage(fT_Device, this.mPackWrBuf, this.mPackRdBuf, length2);
            if (readWritePackage2 <= 0) {
                return readWritePackage2;
            }
            for (int i8 = 0; i8 < length2; i8++) {
                bArr2[i3] = this.mPackRdBuf[i8];
                i3++;
            }
        }
        return i3;
    }

    private int setReadWritePackage(FT_Device fT_Device, byte[] bArr, byte[] bArr2, int i) {
        if (fT_Device != null && fT_Device.isOpen()) {
            if (i != fT_Device.write(bArr, i)) {
                Log.e(TAG, "setReadWritePackage write error!!!");
                return -1;
            }
            int i2 = 0;
            while (i2 < i) {
                int queueStatus = fT_Device.getQueueStatus();
                if (queueStatus > 0) {
                    int i3 = fT_Device.read(bArr, queueStatus);
                    for (int i4 = 0; i4 < i3; i4++) {
                        int i5 = i2 + i4;
                        if (i5 < i) {
                            bArr2[i5] = bArr[i4];
                        }
                    }
                    i2 += i3;
                }
                Thread.yield();
            }
            if (i == i2) {
                return i2;
            }
            Log.e(TAG, "SingleReadWritePackage timeout!!!!");
        }
        return -1;
    }
}

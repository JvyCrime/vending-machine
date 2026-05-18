package com.ftdi.j2xx.ft4222;

import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.interfaces.Gpio;
import com.ftdi.j2xx.interfaces.I2cMaster;
import com.ftdi.j2xx.interfaces.I2cSlave;
import com.ftdi.j2xx.interfaces.SpiMaster;
import com.ftdi.j2xx.interfaces.SpiSlave;

/* JADX INFO: loaded from: classes.dex */
public class FT_4222_Device {
    protected FT_Device mFtDev;
    protected char version;
    protected String TAG = "FT4222";
    protected chiptop_mgr mChipStatus = new chiptop_mgr();
    protected SPI_MasterCfg mSpiMasterCfg = new SPI_MasterCfg();
    protected gpio_mgr mGpio = new gpio_mgr();

    public FT_4222_Device(FT_Device fT_Device) {
        this.mFtDev = fT_Device;
    }

    public int init() {
        byte[] bArr = new byte[13];
        if (this.mFtDev.VendorCmdGet(32, 1, bArr, 13) != 13) {
            return 18;
        }
        this.mChipStatus.formByteArray(bArr);
        byte[] bArr2 = new byte[12];
        if (this.mFtDev.VendorCmdGet(32, 0, bArr2, 12) < 0) {
            return 18;
        }
        if (bArr2[2] == 1) {
            this.version = 'A';
        } else if (bArr2[2] == 2) {
            this.version = 'B';
        } else if (bArr2[2] == 3) {
            this.version = 'C';
        } else if (bArr2[2] >= 4) {
            this.version = 'D';
        }
        return 0;
    }

    public int setClock(byte b) {
        if (b == this.mChipStatus.clk_ctl) {
            return 0;
        }
        int iVendorCmdSet = this.mFtDev.VendorCmdSet(33, (b << 8) | 4);
        if (iVendorCmdSet == 0) {
            this.mChipStatus.clk_ctl = b;
        }
        return iVendorCmdSet;
    }

    public int getClock(byte[] bArr) {
        if (this.mFtDev.VendorCmdGet(32, 4, bArr, 1) < 0) {
            return 18;
        }
        this.mChipStatus.clk_ctl = bArr[0];
        return 0;
    }

    public boolean cleanRxData() {
        int queueStatus = this.mFtDev.getQueueStatus();
        if (queueStatus > 0) {
            return this.mFtDev.read(new byte[queueStatus], queueStatus) == queueStatus;
        }
        return true;
    }

    protected int getMaxBuckSize() {
        if (this.mChipStatus.fs_only != 0) {
            return 64;
        }
        byte b = this.mChipStatus.chip_mode;
        return (b == 1 || b == 2) ? 256 : 512;
    }

    public boolean isFT4222Device() {
        FT_Device fT_Device = this.mFtDev;
        if (fT_Device == null) {
            return false;
        }
        int i = fT_Device.getDeviceInfo().bcdDevice & 65280;
        if (i == 5888) {
            this.mFtDev.getDeviceInfo().type = 12;
            return true;
        }
        if (i == 6144) {
            this.mFtDev.getDeviceInfo().type = 10;
            return true;
        }
        if (i != 6400) {
            return false;
        }
        this.mFtDev.getDeviceInfo().type = 11;
        return true;
    }

    public I2cMaster getI2cMasterDevice() {
        if (isFT4222Device()) {
            return new FT_4222_I2c_Master(this);
        }
        return null;
    }

    public I2cSlave getI2cSlaveDevice() {
        if (isFT4222Device()) {
            return new FT_4222_I2c_Slave(this);
        }
        return null;
    }

    public SpiMaster getSpiMasterDevice() {
        if (isFT4222Device()) {
            return new FT_4222_Spi_Master(this);
        }
        return null;
    }

    public SpiSlave getSpiSlaveDevice() {
        if (isFT4222Device()) {
            return new FT_4222_Spi_Slave(this);
        }
        return null;
    }

    public Gpio getGpioDevice() {
        if (isFT4222Device()) {
            return new FT_4222_Gpio(this);
        }
        return null;
    }

    public int setSuspendOut(boolean z) {
        int iVendorCmdSet;
        if (z) {
            iVendorCmdSet = this.mFtDev.VendorCmdSet(33, 263);
        } else {
            iVendorCmdSet = this.mFtDev.VendorCmdSet(33, 7);
        }
        if (iVendorCmdSet == 0) {
            if (z) {
                this.mChipStatus.enable_suspend_out = (byte) 1;
            } else {
                this.mChipStatus.enable_suspend_out = (byte) 0;
            }
        }
        return iVendorCmdSet;
    }

    public int setInterruptEnable(boolean z) {
        int iVendorCmdSet;
        if (z) {
            iVendorCmdSet = this.mFtDev.VendorCmdSet(33, 262);
        } else {
            iVendorCmdSet = this.mFtDev.VendorCmdSet(33, 6);
        }
        if (iVendorCmdSet == 0) {
            if (z) {
                this.mChipStatus.enable_wakeup_int = (byte) 1;
            } else {
                this.mChipStatus.enable_wakeup_int = (byte) 0;
            }
        }
        return iVendorCmdSet;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int setInterruptTrigger(int r6) {
        /*
            r5 = this;
            r0 = 4
            r1 = 8
            r2 = 2
            r3 = 1
            if (r6 == r3) goto Lf
            if (r6 == r2) goto Lf
            if (r6 == r0) goto Lf
            if (r6 == r1) goto Lf
            r6 = 6
            return r6
        Lf:
            r4 = 0
            if (r6 == r3) goto L18
            if (r6 == r2) goto L1d
            if (r6 == r0) goto L1c
            if (r6 == r1) goto L1a
        L18:
            r2 = 0
            goto L1d
        L1a:
            r2 = 3
            goto L1d
        L1c:
            r2 = 1
        L1d:
            com.ftdi.j2xx.FT_Device r0 = r5.mFtDev
            r3 = 33
            int r1 = r2 << 8
            r1 = r1 | 16
            int r0 = r0.VendorCmdSet(r3, r1)
            if (r0 != 0) goto L2f
            com.ftdi.j2xx.ft4222.gpio_mgr r1 = r5.mGpio
            r1.intrLevel = r6
        L2f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ftdi.j2xx.ft4222.FT_4222_Device.setInterruptTrigger(int):int");
    }

    public int chipReset() {
        return this.mFtDev.VendorCmdSet(33, 128);
    }

    public char GetVersion() {
        return this.version;
    }
}

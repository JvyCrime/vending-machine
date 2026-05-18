package com.ftdi.j2xx.ft4222;

import android.util.Log;
import com.ftdi.j2xx.FT_Device;
import com.ftdi.j2xx.interfaces.SpiSlave;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class FT_4222_Spi_Slave implements SpiSlave {
    private static final String TAG = "FTDI_Device::";
    private FT_4222_Device mFT4222Device;
    private FT_Device mFTDevice;
    private Lock m_pDevLock = new ReentrantLock();

    public FT_4222_Spi_Slave(FT_4222_Device fT_4222_Device) {
        this.mFT4222Device = fT_4222_Device;
        this.mFTDevice = fT_4222_Device.mFtDev;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiSlave
    public int init() {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        SPI_MasterCfg sPI_MasterCfg = this.mFT4222Device.mSpiMasterCfg;
        sPI_MasterCfg.ioLine = 1;
        sPI_MasterCfg.clock = 2;
        sPI_MasterCfg.cpol = 0;
        sPI_MasterCfg.cpha = 0;
        sPI_MasterCfg.ssoMap = (byte) 1;
        this.m_pDevLock.lock();
        this.mFT4222Device.cleanRxData();
        int i = this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.ioLine << 8) | 66) < 0 ? 4 : 0;
        if (this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.clock << 8) | 68) < 0) {
            i = 4;
        }
        if (this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.cpol << 8) | 69) < 0) {
            i = 4;
        }
        if (this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.cpha << 8) | 70) < 0) {
            i = 4;
        }
        if (this.mFTDevice.VendorCmdSet(33, 67) < 0) {
            i = 4;
        }
        if (this.mFTDevice.VendorCmdSet(33, (sPI_MasterCfg.ssoMap << 8) | 72) < 0) {
            i = 4;
        }
        if (this.mFTDevice.VendorCmdSet(33, 1029) < 0) {
            i = 4;
        }
        this.m_pDevLock.unlock();
        chiptop_mgrVar.function = (byte) 4;
        setRxQuickResponse(false);
        return i;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiSlave
    public int getRxStatus(int[] iArr) {
        if (iArr == null) {
            return 1009;
        }
        int iCheck = check();
        if (iCheck != 0) {
            return iCheck;
        }
        this.m_pDevLock.lock();
        int queueStatus = this.mFTDevice.getQueueStatus();
        this.m_pDevLock.unlock();
        if (queueStatus >= 0) {
            iArr[0] = queueStatus;
            return 0;
        }
        iArr[0] = -1;
        return 4;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiSlave
    public int read(byte[] bArr, int i, int[] iArr) {
        this.m_pDevLock.lock();
        FT_Device fT_Device = this.mFTDevice;
        if (fT_Device == null || !fT_Device.isOpen()) {
            this.m_pDevLock.unlock();
            return 3;
        }
        int i2 = this.mFTDevice.read(bArr, i);
        this.m_pDevLock.unlock();
        iArr[0] = i2;
        return i2 >= 0 ? 0 : 4;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiSlave
    public int write(byte[] bArr, int i, int[] iArr) {
        if (iArr == null || bArr == null) {
            return 1009;
        }
        int iCheck = check();
        if (iCheck != 0) {
            return iCheck;
        }
        if (i > 512) {
            return 1010;
        }
        this.m_pDevLock.lock();
        iArr[0] = this.mFTDevice.write(bArr, i);
        this.m_pDevLock.unlock();
        if (iArr[0] == i) {
            return iCheck;
        }
        Log.e(TAG, "Error write =" + i + " tx=" + iArr[0]);
        return 4;
    }

    private int check() {
        return this.mFT4222Device.mChipStatus.function != 4 ? 1003 : 0;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiSlave
    public int reset() {
        this.m_pDevLock.lock();
        int i = this.mFTDevice.VendorCmdSet(33, 74) < 0 ? 4 : 0;
        this.m_pDevLock.unlock();
        return i;
    }

    public int setDrivingStrength(int i, int i2, int i3) {
        chiptop_mgr chiptop_mgrVar = this.mFT4222Device.mChipStatus;
        if (chiptop_mgrVar.function != 3 && chiptop_mgrVar.function != 4) {
            return 1003;
        }
        int i4 = (i << 4) | (i2 << 2) | i3;
        int i5 = chiptop_mgrVar.function != 3 ? 4 : 3;
        this.m_pDevLock.lock();
        int i6 = this.mFTDevice.VendorCmdSet(33, (i5 << 8) | 5) >= 0 ? this.mFTDevice.VendorCmdSet(33, (i4 << 8) | 160) < 0 ? 4 : 0 : 4;
        this.m_pDevLock.unlock();
        return i6;
    }

    @Override // com.ftdi.j2xx.interfaces.SpiSlave
    public int setMode(int i, int i2) {
        int i3 = this.mFTDevice.VendorCmdSet(33, (i << 8) | 69) < 0 ? 4 : 0;
        if (this.mFTDevice.VendorCmdSet(33, (i2 << 8) | 70) < 0) {
            i3 = 4;
        }
        if (this.mFTDevice.VendorCmdSet(33, 1029) < 0) {
            return 4;
        }
        return i3;
    }

    public int setRxQuickResponse(boolean z) {
        if (this.mFT4222Device.version == 'A' || this.mFT4222Device.version == 'B' || this.mFT4222Device.version == 'C') {
            return 17;
        }
        return this.mFTDevice.VendorCmdSet(33, ((z ? 1 : 0) << 8) | 129) < 0 ? 4 : 0;
    }
}

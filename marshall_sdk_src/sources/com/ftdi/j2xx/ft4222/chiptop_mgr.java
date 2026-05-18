package com.ftdi.j2xx.ft4222;

/* JADX INFO: compiled from: FT_4222_Device.java */
/* JADX INFO: loaded from: classes.dex */
class chiptop_mgr {
    byte[] chip_info_addr;
    byte chip_mode;
    byte clk_ctl;
    byte enable_suspend_out;
    byte enable_wakeup_int;
    byte fs_only;
    byte function;
    byte gpio_mask;
    byte high_speed_chip;
    byte total_ep;
    byte total_if;

    public chiptop_mgr() {
        this.chip_info_addr = new byte[3];
    }

    public chiptop_mgr(byte[] bArr) {
        this.chip_info_addr = new byte[3];
        formByteArray(bArr);
    }

    void formByteArray(byte[] bArr) {
        this.chip_mode = bArr[0];
        this.high_speed_chip = bArr[1];
        this.fs_only = bArr[2];
        this.total_if = bArr[3];
        this.total_ep = bArr[4];
        this.clk_ctl = bArr[5];
        this.function = bArr[6];
        this.gpio_mask = bArr[7];
        this.enable_suspend_out = bArr[8];
        this.enable_wakeup_int = bArr[9];
        byte[] bArr2 = this.chip_info_addr;
        bArr2[0] = bArr[10];
        bArr2[1] = bArr[11];
        bArr2[2] = bArr[12];
    }
}

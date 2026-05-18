package com.ftdi.j2xx;

/* JADX INFO: compiled from: D2xxManager.java */
/* JADX INFO: loaded from: classes.dex */
class FtVidPid {
    private int mProductId;
    private int mVendorId;

    FtVidPid(int i, int i2) {
        this.mVendorId = i;
        this.mProductId = i2;
    }

    FtVidPid() {
        this.mVendorId = 0;
        this.mProductId = 0;
    }

    public void setVid(int i) {
        this.mVendorId = i;
    }

    public void setPid(int i) {
        this.mProductId = i;
    }

    public int getVid() {
        return this.mVendorId;
    }

    public int getPid() {
        return this.mProductId;
    }

    public String toString() {
        return "Vendor: " + String.format("%04x", Integer.valueOf(this.mVendorId)) + ", Product: " + String.format("%04x", Integer.valueOf(this.mProductId));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FtVidPid)) {
            return false;
        }
        FtVidPid ftVidPid = (FtVidPid) obj;
        return this.mVendorId == ftVidPid.mVendorId && this.mProductId == ftVidPid.mProductId;
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}

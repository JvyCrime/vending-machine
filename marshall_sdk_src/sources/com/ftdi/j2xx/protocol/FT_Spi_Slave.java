package com.ftdi.j2xx.protocol;

import androidx.core.view.MotionEventCompat;
import com.ftdi.j2xx.interfaces.SpiSlave;
import junit.framework.Assert;

/* JADX INFO: loaded from: classes.dex */
public class FT_Spi_Slave extends SpiSlaveThread {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$ftdi$j2xx$protocol$FT_Spi_Slave$DECODE_STATE = null;
    private static final int FT4222_SPI_SLAVE_SYNC_WORD = 90;
    private static final int SPI_ACK = 132;
    private static final int SPI_MASTER_TRANSFER = 128;
    private static final int SPI_QUERY_VER = 136;
    private static final int SPI_SHART_SLAVE_TRANSFER = 131;
    private static final int SPI_SHORT_MASTER_TRANSFER = 130;
    private static final int SPI_SLAVE_TRANSFER = 129;
    private byte[] mBuffer;
    private int mBufferSize;
    private int mCheckSum;
    private int mCmd;
    private int mCurrentBufferSize;
    private DECODE_STATE mDecodeState = DECODE_STATE.STATE_SYNC;
    private boolean mIsOpened;
    private int mSn;
    private SpiSlave mSpiSlave;
    private SpiSlaveListener mSpiSlaveListener;
    private int mSync;
    private int mWrSn;

    private enum DECODE_STATE {
        STATE_SYNC,
        STATE_CMD,
        STATE_SN,
        STATE_SIZE_HIGH,
        STATE_SIZE_LOW,
        STATE_COLLECT_DATA,
        STATE_CHECKSUM_HIGH,
        STATE_CHECKSUM_LOW;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static DECODE_STATE[] valuesCustom() {
            DECODE_STATE[] decode_stateArrValuesCustom = values();
            int length = decode_stateArrValuesCustom.length;
            DECODE_STATE[] decode_stateArr = new DECODE_STATE[length];
            System.arraycopy(decode_stateArrValuesCustom, 0, decode_stateArr, 0, length);
            return decode_stateArr;
        }
    }

    private boolean check_valid_spi_cmd(int i) {
        return i == 128 || i == 130 || i == 136;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$ftdi$j2xx$protocol$FT_Spi_Slave$DECODE_STATE() {
        int[] iArr = $SWITCH_TABLE$com$ftdi$j2xx$protocol$FT_Spi_Slave$DECODE_STATE;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[DECODE_STATE.valuesCustom().length];
        try {
            iArr2[DECODE_STATE.STATE_CHECKSUM_HIGH.ordinal()] = 7;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[DECODE_STATE.STATE_CHECKSUM_LOW.ordinal()] = 8;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[DECODE_STATE.STATE_CMD.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[DECODE_STATE.STATE_COLLECT_DATA.ordinal()] = 6;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[DECODE_STATE.STATE_SIZE_HIGH.ordinal()] = 4;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[DECODE_STATE.STATE_SIZE_LOW.ordinal()] = 5;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[DECODE_STATE.STATE_SN.ordinal()] = 3;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[DECODE_STATE.STATE_SYNC.ordinal()] = 1;
        } catch (NoSuchFieldError unused8) {
        }
        $SWITCH_TABLE$com$ftdi$j2xx$protocol$FT_Spi_Slave$DECODE_STATE = iArr2;
        return iArr2;
    }

    public FT_Spi_Slave(SpiSlave spiSlave) {
        this.mSpiSlave = spiSlave;
    }

    public void registerSpiSlaveListener(SpiSlaveListener spiSlaveListener) {
        this.mSpiSlaveListener = spiSlaveListener;
    }

    public int open() {
        if (this.mIsOpened) {
            return 1;
        }
        this.mIsOpened = true;
        this.mSpiSlave.init();
        start();
        return 0;
    }

    public int close() {
        if (!this.mIsOpened) {
            return 3;
        }
        sendMessage(new SpiSlaveRequestEvent(-1, true, null, null, null));
        this.mIsOpened = false;
        return 0;
    }

    public int write(byte[] bArr) {
        if (!this.mIsOpened) {
            return 3;
        }
        if (bArr.length > 65536) {
            return 1010;
        }
        int[] iArr = new int[1];
        int length = bArr.length;
        int checkSum = getCheckSum(bArr, 90, 129, this.mWrSn, length);
        int length2 = bArr.length + 8;
        byte[] bArr2 = new byte[length2];
        bArr2[0] = 0;
        bArr2[1] = 90;
        bArr2[2] = -127;
        bArr2[3] = (byte) this.mWrSn;
        bArr2[4] = (byte) ((length & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        int i = 6;
        bArr2[5] = (byte) (length & 255);
        int i2 = 0;
        while (i2 < bArr.length) {
            bArr2[i] = bArr[i2];
            i2++;
            i++;
        }
        bArr2[i] = (byte) ((checkSum & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr2[i + 1] = (byte) (checkSum & 255);
        this.mSpiSlave.write(bArr2, length2, iArr);
        if (iArr[0] != length2) {
            return 4;
        }
        int i3 = this.mWrSn + 1;
        this.mWrSn = i3;
        if (i3 >= 256) {
            this.mWrSn = 0;
        }
        return 0;
    }

    private int getCheckSum(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5 = 0;
        if (bArr != null) {
            int i6 = 0;
            while (i5 < bArr.length) {
                i6 += bArr[i5] & 255;
                i5++;
            }
            i5 = i6;
        }
        return i5 + i + i2 + i3 + ((65280 & i4) >> 8) + (i4 & 255);
    }

    private void spi_push_req_ack_queue() {
        int i = this.mSn;
        int checkSum = getCheckSum(null, 90, 132, i, 0);
        this.mSpiSlave.write(new byte[]{0, 90, -124, (byte) i, 0, 0, (byte) ((65280 & checkSum) >> 8), (byte) (checkSum & 255)}, 8, new int[1]);
    }

    private void sp_slave_parse_and_push_queue(byte[] bArr) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            switch ($SWITCH_TABLE$com$ftdi$j2xx$protocol$FT_Spi_Slave$DECODE_STATE()[this.mDecodeState.ordinal()]) {
                case 1:
                    if (i2 != 90) {
                        z = true;
                    } else {
                        this.mDecodeState = DECODE_STATE.STATE_CMD;
                        this.mSync = i2;
                    }
                    break;
                case 2:
                    if (check_valid_spi_cmd(i2)) {
                        this.mCmd = i2;
                    } else {
                        z = true;
                        z2 = true;
                    }
                    this.mDecodeState = DECODE_STATE.STATE_SN;
                    break;
                case 3:
                    this.mSn = i2;
                    this.mDecodeState = DECODE_STATE.STATE_SIZE_HIGH;
                    break;
                case 4:
                    this.mBufferSize = i2 * 256;
                    this.mDecodeState = DECODE_STATE.STATE_SIZE_LOW;
                    break;
                case 5:
                    int i3 = this.mBufferSize + i2;
                    this.mBufferSize = i3;
                    this.mCurrentBufferSize = 0;
                    this.mBuffer = new byte[i3];
                    this.mDecodeState = DECODE_STATE.STATE_COLLECT_DATA;
                    break;
                case 6:
                    byte[] bArr2 = this.mBuffer;
                    int i4 = this.mCurrentBufferSize;
                    bArr2[i4] = bArr[i];
                    int i5 = i4 + 1;
                    this.mCurrentBufferSize = i5;
                    if (i5 == this.mBufferSize) {
                        this.mDecodeState = DECODE_STATE.STATE_CHECKSUM_HIGH;
                    }
                    break;
                case 7:
                    this.mCheckSum = i2 * 256;
                    this.mDecodeState = DECODE_STATE.STATE_CHECKSUM_LOW;
                    break;
                case 8:
                    this.mCheckSum += i2;
                    if (this.mCheckSum != getCheckSum(this.mBuffer, this.mSync, this.mCmd, this.mSn, this.mBufferSize)) {
                        z2 = true;
                    } else if (this.mCmd == 128) {
                        spi_push_req_ack_queue();
                        if (this.mSpiSlaveListener != null) {
                            this.mSpiSlaveListener.OnDataReceived(new SpiSlaveResponseEvent(3, 0, this.mBuffer, null, null));
                        }
                    }
                    z = true;
                    break;
            }
            if (z2 && this.mSpiSlaveListener != null) {
                this.mSpiSlaveListener.OnDataReceived(new SpiSlaveResponseEvent(3, 1, null, null, null));
            }
            if (z) {
                this.mDecodeState = DECODE_STATE.STATE_SYNC;
                this.mSync = 0;
                this.mCmd = 0;
                this.mSn = 0;
                this.mBufferSize = 0;
                this.mCurrentBufferSize = 0;
                this.mCheckSum = 0;
                this.mBuffer = null;
                z = false;
                z2 = false;
            }
        }
    }

    @Override // com.ftdi.j2xx.protocol.SpiSlaveThread
    protected boolean pollData() {
        int i;
        byte[] bArr;
        int[] iArr = new int[1];
        int rxStatus = this.mSpiSlave.getRxStatus(iArr);
        if (iArr[0] > 0 && rxStatus == 0 && (rxStatus = this.mSpiSlave.read((bArr = new byte[(i = iArr[0])]), i, iArr)) == 0) {
            sp_slave_parse_and_push_queue(bArr);
        }
        if (rxStatus == 4 && this.mSpiSlaveListener != null) {
            this.mSpiSlaveListener.OnDataReceived(new SpiSlaveResponseEvent(3, 2, this.mBuffer, null, null));
        }
        try {
            Thread.sleep(10L);
        } catch (InterruptedException unused) {
        }
        return true;
    }

    @Override // com.ftdi.j2xx.protocol.SpiSlaveThread
    protected void requestEvent(SpiSlaveEvent spiSlaveEvent) {
        if (spiSlaveEvent instanceof SpiSlaveRequestEvent) {
            spiSlaveEvent.getEventType();
            return;
        }
        Assert.assertTrue("processEvent wrong type" + spiSlaveEvent.getEventType(), false);
    }

    @Override // com.ftdi.j2xx.protocol.SpiSlaveThread
    protected boolean isTerminateEvent(SpiSlaveEvent spiSlaveEvent) {
        if (!Thread.interrupted()) {
            return true;
        }
        if (spiSlaveEvent instanceof SpiSlaveRequestEvent) {
            if (spiSlaveEvent.getEventType() == -1) {
                return true;
            }
        } else {
            Assert.assertTrue("processEvent wrong type" + spiSlaveEvent.getEventType(), false);
        }
        return false;
    }
}

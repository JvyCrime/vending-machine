package com.magtek.mobile.android.mtlib;

import android.content.Context;
import android.util.Log;
import com.bitmick.marshall.vmc.marshall_t;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
class MTSCRADevice implements IMTSCRADevice, MTServiceAdapter {
    private static final String TAG = "MTSCRADevice";
    private Context mAppContext;
    private IMTCardData m_cardData;
    private IMTCardDataHandler m_cardDataHandler;
    private MTCardDataState m_cardDataState;
    protected String m_configuration;
    private MTConnectionState m_connectionState;
    private MTDataFormat m_dataFormat;
    private int m_dataThreshold;
    private MTDeviceAdapter m_deviceAdapter;
    private byte[] m_emvData;
    private int m_emvDataLen;
    private byte[] m_extendedCommanResponse;
    private int m_extendedCommanResponseLen;
    private IMTService m_service;

    public MTSCRADevice(Context context) {
        this.mAppContext = context;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRADevice
    public boolean initialize(MTDeviceAdapter mTDeviceAdapter, IMTService iMTService, MTDataFormat mTDataFormat, int i) {
        this.m_deviceAdapter = mTDeviceAdapter;
        this.m_dataThreshold = i;
        this.m_service = iMTService;
        this.m_dataFormat = mTDataFormat;
        this.m_cardData = null;
        this.m_connectionState = MTConnectionState.Disconnected;
        this.m_cardDataState = MTCardDataState.DataNotReady;
        return true;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRADevice
    public void setConfiguration(String str) {
        this.m_configuration = str;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRADevice
    public boolean open() {
        try {
            this.m_emvDataLen = 0;
            this.m_emvData = null;
            this.m_extendedCommanResponseLen = 0;
            this.m_extendedCommanResponse = null;
            this.m_cardData = null;
            this.m_cardDataState = MTCardDataState.DataNotReady;
            if (this.m_service == null || this.m_connectionState != MTConnectionState.Disconnected) {
                return false;
            }
            Log.i(TAG, "Device Is Disconnected");
            this.m_service.setConfiguration(this.m_configuration);
            this.m_service.initialize(this.mAppContext, this);
            this.m_service.connect();
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRADevice
    public void close() {
        try {
            Log.i(TAG, "MTSCRADevice Close");
            IMTService iMTService = this.m_service;
            if (iMTService != null) {
                iMTService.disconnect();
            }
        } catch (Exception unused) {
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRADevice
    public boolean sendCommand(byte[] bArr) {
        try {
            IMTService iMTService = this.m_service;
            if (iMTService == null) {
                return false;
            }
            iMTService.sendData(bArr);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private MTConnectionState getConnectionState(MTServiceState mTServiceState) {
        MTConnectionState mTConnectionState = MTConnectionState.Error;
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtlib$MTServiceState[mTServiceState.ordinal()];
        if (i == 1) {
            return MTConnectionState.Disconnected;
        }
        if (i == 2) {
            return MTConnectionState.Connected;
        }
        if (i != 3) {
            return i != 4 ? mTConnectionState : MTConnectionState.Disconnecting;
        }
        return MTConnectionState.Connecting;
    }

    private void setConnectionState(MTConnectionState mTConnectionState) {
        if (mTConnectionState == this.m_connectionState) {
            return;
        }
        this.m_connectionState = mTConnectionState;
        MTDeviceAdapter mTDeviceAdapter = this.m_deviceAdapter;
        if (mTDeviceAdapter != null) {
            mTDeviceAdapter.OnMessage(0, -1, -1, mTConnectionState);
        }
    }

    private void setCardDataState(MTCardDataState mTCardDataState) {
        if (mTCardDataState == this.m_cardDataState) {
            return;
        }
        this.m_cardDataState = mTCardDataState;
        MTDeviceAdapter mTDeviceAdapter = this.m_deviceAdapter;
        if (mTDeviceAdapter != null) {
            mTDeviceAdapter.OnMessage(1, -1, -1, mTCardDataState);
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTServiceAdapter
    public void OnServiceState(MTServiceState mTServiceState) {
        setConnectionState(getConnectionState(mTServiceState));
    }

    @Override // com.magtek.mobile.android.mtlib.MTServiceAdapter
    public void OnDeviceData(byte[] bArr) {
        handleDeviceData(bArr);
    }

    @Override // com.magtek.mobile.android.mtlib.MTServiceAdapter
    public void OnCommandData(byte[] bArr) {
        handleCommandData(bArr);
    }

    private void handleDeviceData(byte[] bArr) {
        if (bArr != null) {
            String str = TAG;
            Log.i(str, "Device Data=" + MTParser.getHexString(bArr));
            if (bArr.length >= 8) {
                int i = ((bArr[0] & 255) << 8) + (bArr[1] & 255);
                int i2 = ((bArr[2] & 255) << 8) + (bArr[3] & 255);
                byte[] bArr2 = {bArr[4], bArr[5]};
                int i3 = ((bArr[6] & 255) << 8) + (bArr[7] & 255);
                if (this.m_emvData == null) {
                    this.m_emvDataLen = 0;
                    byte[] bArr3 = new byte[i3 + 4];
                    this.m_emvData = bArr3;
                    Arrays.fill(bArr3, (byte) 0);
                    byte[] bArr4 = this.m_emvData;
                    bArr4[0] = bArr2[0];
                    bArr4[1] = bArr2[1];
                    bArr4[2] = bArr[6];
                    bArr4[3] = bArr[7];
                }
                if (bArr.length >= i + 8) {
                    System.arraycopy(bArr, 8, this.m_emvData, i2 + 4, i);
                    this.m_emvDataLen += i;
                }
                if (this.m_emvDataLen >= i3) {
                    Log.i(str, "[MTSCRADevice] EMV Data =" + MTParser.getHexString(bArr));
                    MTDeviceAdapter mTDeviceAdapter = this.m_deviceAdapter;
                    if (mTDeviceAdapter != null) {
                        mTDeviceAdapter.OnMessage(4, -1, -1, this.m_emvData);
                    }
                    this.m_emvDataLen = 0;
                    this.m_emvData = null;
                }
            }
        }
    }

    protected void handleCommandData(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        if (bArr[0] == 10) {
            if (bArr.length < 8) {
                byte[] bArr2 = {bArr[4], bArr[5]};
                MTDeviceAdapter mTDeviceAdapter = this.m_deviceAdapter;
                if (mTDeviceAdapter != null) {
                    mTDeviceAdapter.OnMessage(5, -1, -1, bArr2);
                }
                this.m_extendedCommanResponseLen = 0;
                this.m_extendedCommanResponse = null;
                return;
            }
            int i = (bArr[1] & 255) - 6;
            int i2 = ((bArr[2] & 255) << 8) + (bArr[3] & 255);
            byte[] bArr3 = {bArr[4], bArr[5]};
            int i3 = ((bArr[6] & 255) << 8) + (bArr[7] & 255);
            if (this.m_extendedCommanResponse == null) {
                this.m_extendedCommanResponseLen = 0;
                byte[] bArr4 = new byte[i3 + 4];
                this.m_extendedCommanResponse = bArr4;
                Arrays.fill(bArr4, (byte) 0);
                byte[] bArr5 = this.m_extendedCommanResponse;
                bArr5[0] = bArr3[0];
                bArr5[1] = bArr3[1];
                bArr5[2] = bArr[6];
                bArr5[3] = bArr[7];
            }
            if (bArr.length - 8 >= i) {
                System.arraycopy(bArr, 8, this.m_extendedCommanResponse, i2 + 4, i);
                this.m_extendedCommanResponseLen += i;
            }
            if (this.m_extendedCommanResponseLen >= i3) {
                MTDeviceAdapter mTDeviceAdapter2 = this.m_deviceAdapter;
                if (mTDeviceAdapter2 != null) {
                    mTDeviceAdapter2.OnMessage(5, -1, -1, this.m_extendedCommanResponse);
                }
                this.m_extendedCommanResponseLen = 0;
                this.m_extendedCommanResponse = null;
                return;
            }
            sendCommand(new byte[]{marshall_t.status_vpos_please_insert_or_swipe_card, 0});
            return;
        }
        MTDeviceAdapter mTDeviceAdapter3 = this.m_deviceAdapter;
        if (mTDeviceAdapter3 != null) {
            mTDeviceAdapter3.OnMessage(3, -1, -1, bArr);
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTServiceAdapter
    public void OnCardData(byte[] bArr) {
        handleCardData(bArr);
    }

    private void handleCardData(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        if (this.m_cardData == null) {
            int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtlib$MTDataFormat[this.m_dataFormat.ordinal()];
            if (i == 1) {
                MTASCCardData mTASCCardData = new MTASCCardData();
                this.m_cardData = mTASCCardData;
                this.m_cardDataHandler = mTASCCardData;
            } else if (i == 2) {
                MTHIDCardData mTHIDCardData = new MTHIDCardData();
                this.m_cardData = mTHIDCardData;
                this.m_cardDataHandler = mTHIDCardData;
                mTHIDCardData.setDataThreshold(this.m_dataThreshold);
            } else if (i == 3) {
                MTTLVCardData mTTLVCardData = new MTTLVCardData();
                this.m_cardData = mTTLVCardData;
                this.m_cardDataHandler = mTTLVCardData;
                mTTLVCardData.setConfiguration(this.m_configuration);
            }
        }
        IMTCardDataHandler iMTCardDataHandler = this.m_cardDataHandler;
        if (iMTCardDataHandler != null) {
            iMTCardDataHandler.handleData(bArr);
            if (this.m_cardDataHandler.isDataReady()) {
                setCardDataState(MTCardDataState.DataReady);
                if (this.m_deviceAdapter != null) {
                    try {
                        Object objNewInstance = this.m_cardData.getClass().newInstance();
                        IMTCardData iMTCardData = (IMTCardData) objNewInstance;
                        IMTCardDataHandler iMTCardDataHandler2 = (IMTCardDataHandler) objNewInstance;
                        iMTCardDataHandler2.setConfiguration(this.m_configuration);
                        iMTCardDataHandler2.setData(this.m_cardData.getData());
                        this.m_deviceAdapter.OnMessage(2, -1, -1, iMTCardData);
                    } catch (Exception unused) {
                    }
                }
                this.m_cardDataHandler.clearData();
                return;
            }
            setCardDataState(MTCardDataState.DataNotReady);
        }
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtlib.MTSCRADevice$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtlib$MTDataFormat;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtlib$MTServiceState;

        static {
            int[] iArr = new int[MTDataFormat.values().length];
            $SwitchMap$com$magtek$mobile$android$mtlib$MTDataFormat = iArr;
            try {
                iArr[MTDataFormat.SCRA_ASC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTDataFormat[MTDataFormat.SCRA_HID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTDataFormat[MTDataFormat.SCRA_TLV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[MTServiceState.values().length];
            $SwitchMap$com$magtek$mobile$android$mtlib$MTServiceState = iArr2;
            try {
                iArr2[MTServiceState.Disconnected.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTServiceState[MTServiceState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTServiceState[MTServiceState.Connecting.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTServiceState[MTServiceState.Disconnecting.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTServiceAdapter
    public void OnCardDataError() {
        setCardDataState(MTCardDataState.DataError);
    }

    @Override // com.magtek.mobile.android.mtlib.MTServiceAdapter
    public void OnDeviceError() {
        MTDeviceAdapter mTDeviceAdapter = this.m_deviceAdapter;
        if (mTDeviceAdapter != null) {
            mTDeviceAdapter.OnMessage(6, -1, -1, null);
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTServiceAdapter
    public void OnBondingFailure() {
        MTDeviceAdapter mTDeviceAdapter = this.m_deviceAdapter;
        if (mTDeviceAdapter != null) {
            mTDeviceAdapter.OnMessage(7, -1, -1, null);
        }
    }
}

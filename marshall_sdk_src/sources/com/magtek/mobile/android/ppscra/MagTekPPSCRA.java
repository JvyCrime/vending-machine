package com.magtek.mobile.android.ppscra;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MagTekPPSCRA implements PPSCRADeviceAdapter {
    private static final String a = "MagTekPPSCRA";
    private Context b;
    private Handler c;
    private MTConnectionType d;
    private String e;
    private IMTPPService f;
    private boolean g = false;
    private String h = "";
    private byte[] i = null;
    private String j = "";
    private PPSCRADevice k;
    private PPSCRADeviceAdapter l;

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:217)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:68)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:96)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:102)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:96)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    @Override // com.magtek.mobile.android.ppscra.PPSCRADeviceAdapter
    public void OnMessage(int r2, int r3, int r4, java.lang.Object r5) {
        /*
            r1 = this;
            r0 = 11
            if (r2 == r0) goto L48
            r0 = 12
            if (r2 == r0) goto L3e
            r0 = 99
            if (r2 == r0) goto L34
            switch(r2) {
                case 21: goto L3e;
                case 22: goto L3e;
                case 23: goto L3e;
                case 24: goto L3e;
                case 25: goto L3e;
                case 26: goto L2a;
                case 27: goto L3e;
                case 28: goto L3e;
                default: goto Lf;
            }
        Lf:
            switch(r2) {
                case 31: goto L3e;
                case 32: goto L3e;
                case 33: goto L3e;
                case 34: goto L3e;
                case 35: goto L3e;
                default: goto L12;
            }
        L12:
            switch(r2) {
                case 51: goto L3e;
                case 52: goto L3e;
                case 53: goto L3e;
                case 54: goto L3e;
                default: goto L15;
            }
        L15:
            switch(r2) {
                case 61: goto L1c;
                case 62: goto L3e;
                case 63: goto L3e;
                case 64: goto L3e;
                case 65: goto L3e;
                case 66: goto L3e;
                default: goto L18;
            }
        L18:
            switch(r2) {
                case 71: goto L3e;
                case 72: goto L3e;
                case 73: goto L3e;
                case 74: goto L3e;
                case 75: goto L3e;
                case 76: goto L3e;
                case 77: goto L3e;
                default: goto L1b;
            }
        L1b:
            goto L4d
        L1c:
            byte r2 = (byte) r3
            if (r4 <= 0) goto L4d
            if (r2 == 0) goto L24
            r3 = 2
            if (r2 != r3) goto L4d
        L24:
            com.magtek.mobile.android.ppscra.PPSCRADevice r2 = r1.k     // Catch: java.lang.Exception -> L4d
            r2.getUserSignature()     // Catch: java.lang.Exception -> L4d
            goto L4d
        L2a:
            android.os.Handler r0 = r1.c     // Catch: java.lang.Exception -> L4d
            android.os.Message r2 = r0.obtainMessage(r2, r3, r4, r5)     // Catch: java.lang.Exception -> L4d
            r2.sendToTarget()     // Catch: java.lang.Exception -> L4d
            goto L4d
        L34:
            android.os.Handler r3 = r1.c     // Catch: java.lang.Exception -> L4d
            android.os.Message r2 = r3.obtainMessage(r2, r5)     // Catch: java.lang.Exception -> L4d
            r2.sendToTarget()     // Catch: java.lang.Exception -> L4d
            goto L4d
        L3e:
            android.os.Handler r0 = r1.c     // Catch: java.lang.Exception -> L4d
            android.os.Message r2 = r0.obtainMessage(r2, r3, r4, r5)     // Catch: java.lang.Exception -> L4d
            r2.sendToTarget()     // Catch: java.lang.Exception -> L4d
            goto L4d
        L48:
            com.magtek.mobile.android.ppscra.MTConnectionState r5 = (com.magtek.mobile.android.ppscra.MTConnectionState) r5     // Catch: java.lang.Exception -> L4d
            r1.OnConnectionState(r5)     // Catch: java.lang.Exception -> L4d
        L4d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.ppscra.MagTekPPSCRA.OnMessage(int, int, int, java.lang.Object):void");
    }

    protected void OnConnectionState(MTConnectionState mTConnectionState) {
        String str = a;
        Log.i(str, "OnConnectionState");
        if (this.c != null) {
            Message message = new Message();
            message.what = 11;
            message.obj = mTConnectionState;
            this.c.sendMessage(message);
            if (mTConnectionState == MTConnectionState.Disconnected) {
                Log.i(str, "OnConnectionState Disconnected");
            }
        }
    }

    protected void createBLEService() {
        if (this.f != null) {
            Log.i(a, "*** Service is not NULL [" + toString() + "]");
            this.f = null;
        }
        MTPPBLEService mTPPBLEService = new MTPPBLEService();
        this.f = mTPPBLEService;
        mTPPBLEService.setConnectionRetry(this.g);
        Log.i(a, "createBLEService [" + this.f.toString() + "]");
        this.f.setAddress(this.e);
        PPSCRADevice pPSCRADevice = this.k;
        if (pPSCRADevice != null) {
            pPSCRADevice.initialize(this.l, this.f);
            this.k.open();
        }
    }

    protected void createUSBService() {
        if (this.f != null) {
            Log.i(a, "*** Service is not NULL [" + toString() + "]");
            this.f = null;
        }
        MTPPUSBService mTPPUSBService = new MTPPUSBService();
        this.f = mTPPUSBService;
        mTPPUSBService.setAddress(this.e);
        PPSCRADevice pPSCRADevice = this.k;
        if (pPSCRADevice != null) {
            pPSCRADevice.initialize(this.l, this.f);
            this.k.open();
        }
    }

    protected void createNetService() {
        if (this.f != null) {
            Log.i(a, "*** Service is not NULL [" + toString() + "]");
            this.f = null;
        }
        MTPPNetService mTPPNetService = new MTPPNetService();
        this.f = mTPPNetService;
        mTPPNetService.setAddress(this.e);
        if (this.d == MTConnectionType.Net_TLS12) {
            this.f.setTLS(true, false);
            this.f.setClientCertificate(this.h, this.i, this.j);
        } else if (this.d == MTConnectionType.Net_TLS12_Trust_All) {
            this.f.setTLS(true, true);
            this.f.setClientCertificate(this.h, this.i, this.j);
        }
        PPSCRADevice pPSCRADevice = this.k;
        if (pPSCRADevice != null) {
            pPSCRADevice.initialize(this.l, this.f);
            this.k.open();
        }
    }

    public MagTekPPSCRA(Context context, Handler handler) {
        this.k = null;
        Log.i(a, "MagTekPPSCRA Constructor");
        this.d = MTConnectionType.BLE;
        this.b = context;
        this.c = handler;
        this.k = new PPSCRADevice(this.b);
    }

    public String getSDKVersion() {
        Log.i(a, "MagTekPPSCRA getSDKVersion");
        return "102.01";
    }

    public void setConnectionType(MTConnectionType mTConnectionType) {
        this.d = mTConnectionType;
    }

    public void setDeviceAddress(String str) {
        Log.i(a, "MagTekPPSCRA setDeviceAddress: " + str);
        this.e = str;
    }

    public boolean loadClientCertificate(String str, byte[] bArr, String str2) {
        this.h = str;
        this.i = bArr;
        this.j = str2;
        return true;
    }

    public void setConnectionRetry(boolean z) {
        this.g = z;
    }

    public long openDevice() {
        String str = a;
        Log.i(str, "MagTekPPSCRA openDevice");
        this.l = this;
        IMTPPService iMTPPService = this.f;
        if (iMTPPService != null && iMTPPService.getState() != MTServiceState.Disconnected) {
            Log.i(str, "openDevice: *** Connection already exists.");
            return -1L;
        }
        this.f = null;
        switch (AnonymousClass1.a[this.d.ordinal()]) {
            case 1:
            case 2:
                createBLEService();
                return 0L;
            case 3:
                createUSBService();
                return 0L;
            case 4:
            case 5:
            case 6:
                createNetService();
                return 0L;
            default:
                return 0L;
        }
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.ppscra.MagTekPPSCRA$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[MTConnectionType.values().length];
            a = iArr;
            try {
                iArr[MTConnectionType.BLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[MTConnectionType.BLEEMV.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[MTConnectionType.USB.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[MTConnectionType.Net.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[MTConnectionType.Net_TLS12.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[MTConnectionType.Net_TLS12_Trust_All.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public long closeDevice() {
        Log.i(a, "MagTekPPSCRA closeDevice");
        this.k.close();
        this.f = null;
        return 0L;
    }

    public boolean isDeviceOpened() {
        Log.i(a, "MagTekPPSCRA isDeviceOpened");
        IMTPPService iMTPPService = this.f;
        return iMTPPService != null && iMTPPService.getState() == MTServiceState.Connected;
    }

    public boolean isDeviceSRED() {
        Log.i(a, "MagTekPPSCRA isDeviceSRED");
        return this.k.isDeviceSRED();
    }

    public long deviceReset() {
        Log.i(a, "MagTekPPSCRA deviceReset");
        return this.k.deviceReset();
    }

    public byte getStatusCode() {
        Log.i(a, "MagTekPPSCRA getStatusCode");
        return this.k.getStatusCode();
    }

    public byte getCommandStatus() {
        Log.i(a, "MagTekPPSCRA getCommandStatus");
        return this.k.getCommandStatus();
    }

    public long cancelOperation() {
        Log.i(a, "MagTekPPSCRA cancelOperation");
        return this.k.cancelOperation();
    }

    public long requestBypassPINCommand() {
        Log.i(a, "MagTekPPSCRA requestBypassPINCommand");
        return this.k.requestBypassPINCommand();
    }

    public long setPAN(String str) {
        return this.k.setPAN(str);
    }

    public long setAmount(byte b, String str) {
        Log.i(a, "MagTekPPSCRA setAmount, amountType=" + String.format("%02X ", Byte.valueOf(b)) + ", amount=" + str);
        return this.k.setAmount(b, str);
    }

    public long endSession(byte b) {
        Log.i(a, "MagTekPPSCRA endSession, displayMessageID=" + String.format("%02X ", Byte.valueOf(b)));
        return this.k.endSession(b);
    }

    public long requestChallengeAndSession() {
        Log.i(a, "MagTekPPSCRA requestChallengeAndSession");
        return this.k.requestChallengeAndSession();
    }

    public long requestConfirmationSession(byte b, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Log.i(a, "MagTekPPSCRA requestConfirmationSession, mode=" + String.format("%02X ", Byte.valueOf(b)));
        return this.k.requestConfirmationSession(b, bArr, bArr2, bArr3);
    }

    public long endL1Session() {
        Log.i(a, "MagTekPPSCRA endL1Session");
        return this.k.requestConfirmationSession((byte) 0, null, null, null);
    }

    public long requestPowerUpResetICC(byte b, byte b2) {
        Log.i(a, "MagTekPPSCRA requestPowerUpResetICC, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", operation=" + String.format("%02X ", Byte.valueOf(b2)));
        return this.k.requestPowerUpResetICC(b, b2);
    }

    public long requestPowerDownICC(byte b) {
        Log.i(a, "MagTekPPSCRA requestPowerDownICCWithWaitTime, waitTime=" + String.format("%02X ", Byte.valueOf(b)));
        return this.k.requestPowerDownICC(b);
    }

    public long requestICCAPDU(byte[] bArr) {
        Log.i(a, "MagTekPPSCRA requestICCAPDU");
        return this.k.requestICCAPDU(bArr);
    }

    public long sendSpecialCommand(byte[] bArr) {
        Log.i(a, "MagTekPPSCRA sendSpecialCommand");
        return this.k.sendSpecialCommand(bArr);
    }

    public long getSpecialCommand(byte[] bArr) {
        Log.i(a, "MagTekPPSCRA getSpecialCommand");
        return this.k.getSpecialCommand(bArr);
    }

    public long requestGetEMVTags(byte b, byte b2, byte[] bArr, byte b3, byte b4, byte[] bArr2) {
        Log.i(a, "MagTekPPSCRA requestEMVTags, tagType=" + String.format("%02X ", Byte.valueOf(b)) + ", operation=" + String.format("%02X ", Byte.valueOf(b2)) + ", database=" + String.format("%02X ", Byte.valueOf(b3)) + ", option=" + String.format("%02X ", Byte.valueOf(b4)));
        return this.k.requestGetEMVTags(b, b2, bArr, b3, b4, bArr2);
    }

    public long requestSetEMVTags(byte b, byte b2, byte[] bArr, byte b3, byte b4, byte[] bArr2) {
        Log.i(a, "MagTekPPSCRA requestEMVTags, tagType=" + String.format("%02X ", Byte.valueOf(b)) + ", operation=" + String.format("%02X ", Byte.valueOf(b2)) + ", database=" + String.format("%02X ", Byte.valueOf(b3)) + ", option=" + String.format("%02X ", Byte.valueOf(b4)));
        return this.k.requestSetEMVTags(b, b2, bArr, b3, b4, bArr2);
    }

    public long setCAPublicKey(byte b, byte[] bArr) {
        Log.i(a, "MagTekPPSCRA setCAPublicKey, operation=" + String.format("%02X ", Byte.valueOf(b)));
        return this.k.setCAPublicKey(b, bArr);
    }

    public long setDisplayMessage(byte b, byte b2) {
        Log.i(a, "MagTekPPSCRA setDisplayMessage, timeOut=" + String.format("%02X ", Byte.valueOf(b)) + ", messageID=" + String.format("%02X ", Byte.valueOf(b2)));
        return this.k.setDisplayMessage(b, b2);
    }

    public long sendBigBlockData(byte b, byte[] bArr) {
        Log.i(a, "MagTekPPSCRA sendBigBlockData");
        return this.k.sendBigBlockData(b, bArr);
    }

    public long sendBitmap(byte b, byte b2, byte[] bArr) {
        Log.i(a, "MagTekPPSCRA setBitmap, Slot=" + ((int) b) + ", Option=" + ((int) b2));
        return this.k.sendBitmap(b, b2, bArr);
    }

    public DeviceInformation getDeviceInformation() {
        Log.i(a, "MagTekPPSCRA getDeviceInformation");
        return this.k.getDeviceInformation();
    }

    public long requestDeviceInformation(byte b) {
        Log.i(a, "MagTekPPSCRA requestDeviceInformation, mode=" + String.format("%02X ", Byte.valueOf(b)));
        return this.k.requestDeviceInformation(b);
    }

    public DeviceStateStatus getDeviceStateStatus() {
        Log.i(a, "MagTekPPSCRA getDeviceStateStatus");
        return this.k.getDeviceStateStatus();
    }

    public long requestDeviceStatus() {
        Log.i(a, "MagTekPPSCRA requestDeviceStatus");
        return this.k.requestDeviceStatus();
    }

    public long requestKernelInformation(byte b) {
        Log.i(a, "MagTekPPSCRA requestKernelInformation");
        return this.k.requestKernelInformation(b);
    }

    public byte[] getBINTableData() {
        Log.i(a, "MagTekPPSCRA getBINTableData");
        return this.k.getBINTableData();
    }

    public long requestBINTableData() {
        Log.i(a, "MagTekPPSCRA requestBINTableData");
        return this.k.requestBINTableData();
    }

    public long setBINTableData(byte[] bArr, byte[] bArr2) {
        Log.i(a, "MagTekPPSCRA setBINTableData");
        return this.k.setBINTableData(bArr, bArr2);
    }

    public String getKSN() {
        Log.i(a, "MagTekPPSCRA getKSN");
        CardDataInfo cardDataInfo = this.k.getCardDataInfo();
        UserDataEntry userDataEntry = this.k.getUserDataEntry();
        if (cardDataInfo != null && cardDataInfo.KSN != null && cardDataInfo.KSN.length >= 10) {
            return PPSCRACommon.getHexString(Arrays.copyOfRange(cardDataInfo.KSN, 0, 10), 0, null);
        }
        if (userDataEntry != null && userDataEntry.MSRKSN != null) {
            return PPSCRACommon.getHexString(Arrays.copyOfRange(userDataEntry.MSRKSN, 0, 10), 0, null);
        }
        PINData pINData = this.k.getPINData();
        return (pINData == null || pINData.PINKSN == null) ? "" : PPSCRACommon.getHexString(pINData.PINKSN, 0, null);
    }

    public long requestCard(byte b, byte b2, byte b3) {
        Log.i(a, "MagTekPPSCRA requestCard, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", messageID=" + String.format("%02X ", Byte.valueOf(b2)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b3)));
        return this.k.requestCard(b, b2, b3);
    }

    public long requestManualCardData(byte b, byte b2, byte b3) {
        Log.i(a, "MagTekPPSCRA requestManualCardData, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b2)) + ", option=" + String.format("%02X ", Byte.valueOf(b3)));
        return this.k.requestManualCardData(b, b2, b3);
    }

    public long requestUserDataEntry(byte b, byte b2, byte b3) {
        Log.i(a, "MagTekPPSCRA requestUserDataEntry, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", displayMessage=" + String.format("%02X ", Byte.valueOf(b2)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b3)));
        return this.k.requestUserDataEntry(b, b2, b3);
    }

    public long requestResponse(byte b, byte b2, byte b3, byte b4) {
        Log.i(a, "MagTekPPSCRA requestResponse, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", selectMsg=" + String.format("%02X ", Byte.valueOf(b2)) + ", keyMask=" + String.format("%02X ", Byte.valueOf(b3)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b4)));
        return this.k.requestResponse(b, b2, b3, b4);
    }

    public long confirmAmount(byte b, byte b2) {
        Log.i(a, "MagTekPPSCRA confirmAmount");
        return this.k.requestResponse(b, (byte) 1, (byte) 6, b2);
    }

    public long selectCreditDebit(byte b, byte b2) {
        Log.i(a, "MagTekPPSCRA confirmAmount");
        return this.k.requestResponse(b, (byte) 0, (byte) 7, b2);
    }

    public long requestPIN(byte b, byte b2, byte b3, byte b4, byte b5, byte b6) {
        Log.i(a, "MagTekPPSCRA requestPIN, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", pinMode=" + String.format("%02X ", Byte.valueOf(b2)) + ", maxPINLength=" + String.format("%02X ", Byte.valueOf(b3)) + ", minPINLength=" + String.format("%02X ", Byte.valueOf(b4)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b5)) + ", option=" + String.format("%02X ", Byte.valueOf(b6)));
        return this.k.requestPIN(b, b2, b3, b4, b5, b6);
    }

    public long requestSignature(byte b, byte b2, byte b3) {
        Log.i(a, "PPSCRADevice requestSignature, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b2)) + ", option=" + String.format("%02X ", Byte.valueOf(b3)));
        return this.k.requestSignature(b, b2, b3);
    }

    public long requestTipCashback(byte b, byte b2, byte b3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte b4, byte b5, byte b6, byte b7) {
        Log.i(a, "MagTekPPSCRA requestTipCashback, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", mode=" + String.format("%02X ", Byte.valueOf(b2)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b3)) + ", tipSelectionMode=" + String.format("%02X ", Byte.valueOf(b4)) + ", leftAmount=" + String.format("%02X ", Byte.valueOf(b5)) + ", middleAmount=" + String.format("%02X ", Byte.valueOf(b6)) + ", rightAmount=" + String.format("%02X ", Byte.valueOf(b7)));
        return this.k.requestTipCashback(b, b2, b3, bArr, bArr2, bArr3, b4, b5, b6, b7);
    }

    public long startEMVTransaction(byte b, byte b2, byte b3, byte b4, byte b5, byte[] bArr, byte b6, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte b7, byte b8, byte b9, byte[] bArr6, byte[] bArr7, byte b10, byte b11, byte[] bArr8, byte[] bArr9) {
        Log.i(a, "PPSCRADevice startEMVTransation, cardType=" + String.format("%02X ", Byte.valueOf(b)) + ", confirmationTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", pinEnteringTime=" + String.format("%02X ", Byte.valueOf(b3)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b4)) + ", option=" + String.format("%02X ", Byte.valueOf(b5)) + ", transactionType=" + String.format("%02X ", Byte.valueOf(b6)) + ", categoryCode=" + String.format("%02X ", Byte.valueOf(b7)) + ", chipMode=" + String.format("%02X ", Byte.valueOf(b8)) + ", operatingMode=" + String.format("%02X ", Byte.valueOf(b9)));
        return this.k.startEMVTransaction(b, b2, b3, b4, b5, bArr, b6, bArr2, bArr3, bArr4, bArr5, b7, b8, b9, bArr6, bArr7, b10, b11, bArr8, bArr9);
    }

    public long requestSmartCard(byte b, byte b2, byte b3, byte b4, byte b5, byte[] bArr, byte b6, byte[] bArr2, byte[] bArr3) {
        Log.i(a, "MagTekPPSCRA requestSmartCard, cardType=" + String.format("%02X ", Byte.valueOf(b)) + ", confirmationTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", pinEnteringTime=" + String.format("%02X ", Byte.valueOf(b3)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b4)) + ", option=" + String.format("%02X ", Byte.valueOf(b5)) + ", transactionType=" + String.format("%02X ", Byte.valueOf(b6)));
        return this.k.requestSmartCard(b, b2, b3, b4, b5, bArr, b6, bArr2, bArr3);
    }

    public long sendAcquirerResponse(byte[] bArr) {
        Log.i(a, "MagTekPPSCRA sendAcquirerResponse");
        return this.k.sendAcquirerResponse(bArr);
    }

    public CardDataInfo getCardDataInfo() {
        Log.i(a, "MagTekPPSCRA getCardDataInfo");
        return this.k.getCardDataInfo();
    }

    public CardStatus getCardStatus() {
        Log.i(a, "MagTekPPSCRA getCardStatus");
        return this.k.getCardStatus();
    }

    public long requestDeviceConfiguration() {
        Log.i(a, "MagTekPPSCRA requestDeviceConfiguration");
        return this.k.requestDeviceConfiguration();
    }

    public String getProductID() {
        Log.i(a, "MagTekPPSCRA getProductID");
        return this.k.getProductID();
    }

    public byte[] getDeviceSerial() {
        Log.i(a, "MagTekPPSCRA getDeviceSerial");
        return this.k.getDeviceSerial();
    }

    public String getDeviceModel() {
        Log.i(a, "MagTekPPSCRA getDeviceModel");
        return this.k.getDeviceModel();
    }

    public String getDeviceFirmwareVersion() {
        Log.i(a, "MagTekPPSCRA getDeviceFirmwareVersion");
        return this.k.getDeviceFirmwareVersion();
    }

    public boolean isDeviceConnected() {
        Log.i(a, "MagTekPPSCRA isDeviceOpened");
        IMTPPService iMTPPService = this.f;
        return iMTPPService != null && iMTPPService.getState() == MTServiceState.Connected;
    }

    public String getPINKSN() {
        Log.i(a, "MagTekPPSCRA getPINKSN");
        PINData pINData = this.k.getPINData();
        return (pINData == null || pINData.PINKSN == null) ? "" : PPSCRACommon.getHexString(pINData.PINKSN, 0, null);
    }

    public int getDeviceConnected() {
        Log.i(a, "MagTekPPSCRA getDeviceConnected");
        IMTPPService iMTPPService = this.f;
        return (iMTPPService == null || iMTPPService.getState() != MTServiceState.Connected) ? 0 : 1;
    }

    public byte getSessionState() {
        Log.i(a, "MagTekPPSCRA getSessionState");
        return this.k.getSessionState();
    }

    public String getPAN() {
        Log.i(a, "MagTekPPSCRA getPAN");
        return this.k.getCardDataInfo().getMaskedPAN();
    }

    public String getEncodeType() {
        return String.format("%02X", Byte.valueOf(this.k.getCardStatus().getCardType()));
    }

    public String getTrack1() {
        Log.i(a, "MagTekPPSCRA getTrack1");
        return this.k.getCardDataInfo().getTrack1();
    }

    public String getTrack2() {
        Log.i(a, "MagTekPPSCRA getTrack2");
        return this.k.getCardDataInfo().getTrack2();
    }

    public String getTrack3() {
        Log.i(a, "MagTekPPSCRA getTrack3");
        return this.k.getCardDataInfo().getTrack3();
    }

    public String getTrack1Masked() {
        Log.i(a, "MagTekPPSCRA getTrack1Masked");
        return this.k.getCardDataInfo().getTrack1Masked();
    }

    public String getTrack2Masked() {
        Log.i(a, "MagTekPPSCRA getTrack2Masked");
        return this.k.getCardDataInfo().getTrack2Masked();
    }

    public String getTrack3Masked() {
        Log.i(a, "MagTekPPSCRA getTrack3Masked");
        return this.k.getCardDataInfo().getTrack3Masked();
    }

    public String getMaskedTracks() {
        Log.i(a, "MagTekPPSCRA getMaskedTracks");
        CardDataInfo cardDataInfo = this.k.getCardDataInfo();
        return cardDataInfo.getTrack1Masked() + cardDataInfo.getTrack2Masked() + cardDataInfo.getTrack3Masked();
    }

    public String getMagnePrint() {
        Log.i(a, "MagTekPPSCRA getMagnePrint");
        return this.k.getCardDataInfo().getMagnePrint();
    }

    public String getKSNMagnePrintStatus() {
        Log.i(a, "MagTekPPSCRA getMagneKSNPrintStatus");
        return this.k.getCardDataInfo().getKSNMagnePrintStatus() == 0 ? "0" : "1";
    }

    public String getTrack1DecodeStatus() {
        Log.i(a, "MagTekPPSCRA getTrack1DecodeStatus");
        return this.k.getCardDataInfo().getTrack1DecodeStatus() == 0 ? "0" : "1";
    }

    public String getTrack2DecodeStatus() {
        Log.i(a, "MagTekPPSCRA getTrack2DecodeStatus");
        return this.k.getCardDataInfo().getTrack2DecodeStatus() == 0 ? "0" : "1";
    }

    public String getTrack3DecodeStatus() {
        Log.i(a, "MagTekPPSCRA getTrack3DecodeStatus");
        return this.k.getCardDataInfo().getTrack3DecodeStatus() == 0 ? "0" : "1";
    }

    public String getLastName() {
        Log.i(a, "MagTekPPSCRA getLastName");
        return this.k.getCardDataInfo().getLastName();
    }

    public String getFirstName() {
        Log.i(a, "MagTekPPSCRA getFirstName");
        return this.k.getCardDataInfo().getFirstName();
    }

    public String getMiddleName() {
        Log.i(a, "MagTekPPSCRA getMiddleName");
        return this.k.getCardDataInfo().getMiddleName();
    }

    public String getExpDate() {
        Log.i(a, "MagTekPPSCRA getExpDate");
        return this.k.getCardDataInfo().getExpDate();
    }

    public String getPINStatusCode() {
        Log.i(a, "MagTekPPSCRA getPINStatusCode");
        return this.k.getPINData().getStatus() == 0 ? "0" : "1";
    }

    public PINData getPINData() {
        Log.i(a, "MagTekPPSCRA getPINData");
        return this.k.getPINData();
    }

    public String getEPB() {
        Log.i(a, "MagTekPPSCRA getEPB");
        return PPSCRACommon.getHexString(this.k.getPINData().getEncryptedPINBlock(), 0, null);
    }

    public void clearBuffer() {
        Log.i(a, "MagTekPPSCRA clearBuffer");
        this.k.clearData();
    }

    public long getSelectedMenuItem(byte b, byte b2, byte b3) {
        Log.i(a, "MagTekPPSCRA getSelectedMenuItem, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", menuSelectionMode=" + String.format("%02X ", Byte.valueOf(b2)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b3)));
        return this.k.getSelectedMenuItem(b, b2, b3);
    }

    public long requestClearTextUserDataEntry(byte b, byte b2, byte b3) {
        Log.i(a, "MagTekPPSCRA requestClearTextUserDataEntry, waitTime=" + String.format("%02X ", Byte.valueOf(b)) + ", displayMessage=" + String.format("%02X ", Byte.valueOf(b2)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b3)));
        return this.k.requestClearTextUserDataEntry(b, b2, b3);
    }

    public ClearTextUserDataEntry getClearTextUserDataEntry(boolean z) {
        Log.i(a, "MagTekPPSCRA getClearTextUserDataEntry");
        ClearTextUserDataEntry clearTextUserDataEntry = this.k.getClearTextUserDataEntry();
        if (z) {
            this.k.clearClearTextUserDataEntry();
        }
        return clearTextUserDataEntry;
    }

    public byte[] getEMVTagsData(boolean z) {
        Log.i(a, "MagTekPPSCRA getEMVTagsData");
        byte[] eMVTagsData = this.k.getEMVTagsData();
        if (z) {
            this.k.clearEMVTagsData();
        }
        return eMVTagsData;
    }

    public byte[] getCAPublicKeyData(boolean z) {
        Log.i(a, "MagTekPPSCRA getCAPublicKeyData");
        byte[] cAPublicKeyData = this.k.getCAPublicKeyData();
        if (z) {
            this.k.clearCAPublicKeyData();
        }
        return cAPublicKeyData;
    }

    public byte[] getEMVCompleteData(boolean z) {
        Log.i(a, "MagTekPPSCRA getEMVCompleteData");
        byte[] eMVARQCData = this.k.getEMVARQCData();
        if (z) {
            this.k.clearEMVARQCData();
        }
        return eMVARQCData;
    }

    public byte[] getAPDUData(boolean z) {
        Log.i(a, "MagTekPPSCRA getAPDUData");
        byte[] aPDUData = this.k.getAPDUData();
        if (z) {
            this.k.clearAPDUData();
        }
        return aPDUData;
    }

    public byte[] getPowerUpICC(boolean z) {
        Log.i(a, "MagTekPPSCRA getPowerUpICC");
        byte[] aTRData = this.k.getATRData();
        if (z) {
            this.k.clearATRData();
        }
        return aTRData;
    }

    public byte[] getEMVTransactionComplete(boolean z) {
        Log.i(a, "MagTekPPSCRA getEMVTransactionComplete");
        byte[] eMVData = this.k.getEMVData();
        if (z) {
            this.k.clearEMVData();
        }
        return eMVData;
    }

    public UserDataEntry getUserDataEntry(boolean z) {
        Log.i(a, "MagTekPPSCRA getUserDataEntry");
        UserDataEntry userDataEntry = this.k.getUserDataEntry();
        if (z) {
            this.k.clearUserDataEntry();
        }
        return userDataEntry;
    }

    public byte[] getSignatureData(boolean z) {
        Log.i(a, "PPSCRADevice getSignatureData");
        byte[] signatureData = this.k.getSignatureData();
        if (z) {
            this.k.clearSignatureData();
        }
        return signatureData;
    }

    public long getFnKeyPressed() {
        byte fnKeyPressed = this.k.getFnKeyPressed();
        if (fnKeyPressed == 0) {
            return 9L;
        }
        return fnKeyPressed;
    }
}

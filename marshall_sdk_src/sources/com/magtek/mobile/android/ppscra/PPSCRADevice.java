package com.magtek.mobile.android.ppscra;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.digitalmediavending.hardware.utils.AppConstants;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* JADX INFO: loaded from: classes.dex */
public class PPSCRADevice implements MTServiceAdapter {
    public static final String ACTION_DEVICE_ATTACHED = "ACTION_DEVICE_ATTACHED";
    public static final String ACTION_DEVICE_CONNECTED = "ACTION_DEVICE_CONNECTED";
    public static final String ACTION_DEVICE_CONNECTING = "ACTION_DEVICE_CONNECTING";
    public static final String ACTION_DEVICE_DATA = "ACTION_DEVICE_DATA";
    public static final String ACTION_DEVICE_DEBUG_DATA = "ACTION_USB_DEBUG_DATA";
    public static final String ACTION_DEVICE_DENIED = "ACTION_DEVICE_DENIED";
    public static final String ACTION_DEVICE_DETACHED = "ACTION_DEVICE_DETACHED";
    public static final String ACTION_DEVICE_DISCONNECTED = "ACTION_DEVICE_DISCONNECTED";
    public static final String ACTION_DEVICE_DISCONNECTING = "ACTION_DEVICE_DISCONNECTING";
    public static final String ACTION_DEVICE_GRANTED = "ACTION_DEVICE_GRANTED";
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private static final String b = "PPSCRADevice";
    private CardDataInfo B;
    private CardStatus C;
    private DeviceInformation D;
    private DeviceStateStatus E;
    private PINData F;
    private UserDataEntry G;
    private ClearTextUserDataEntry H;
    private MTConnectionState M;
    private IMTPPService N;
    private PPSCRADeviceAdapter O;
    private Context c;
    private byte[] r;
    private Object d = null;
    private Object e = null;
    private Object f = null;
    private Object g = null;
    private Object h = null;
    private Queue<a> i = new LinkedList();
    private byte j = 0;
    private byte k = 0;
    private byte l = 0;
    private boolean m = false;
    private short n = 0;
    private short o = 0;
    private short p = 0;
    private byte q = 0;
    private byte[] s = null;
    private byte[] t = null;
    private byte[] u = null;
    private byte[] v = null;
    private byte[] w = null;
    private byte[] x = null;
    private byte[] y = null;
    private byte[] z = null;
    private byte A = 0;
    List<HashMap<String, String>> a = null;
    private boolean I = false;
    private boolean J = false;
    private boolean K = false;
    private boolean L = false;
    private Handler P = new Handler(Looper.getMainLooper()) { // from class: com.magtek.mobile.android.ppscra.PPSCRADevice.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                a aVarF = PPSCRADevice.this.f();
                if (aVarF != null) {
                    Log.w(PPSCRADevice.b, "Command Timeout: 0x" + String.format("%02X ", Byte.valueOf(aVarF.b)));
                    PPSCRADevice.this.j = (byte) 2;
                }
                PPSCRADevice.this.g();
            } catch (Exception unused) {
            }
        }
    };

    private void j(byte[] bArr) {
    }

    private void k(byte[] bArr) {
    }

    private void l(byte[] bArr) {
    }

    private void n(byte[] bArr) {
    }

    private class a {
        private byte b;
        private byte[] c;

        private a() {
        }
    }

    public PPSCRADevice(Context context) {
        Log.i(b, "PPSCRADevice Constructor");
        this.c = context;
        this.D = new DeviceInformation();
        this.E = new DeviceStateStatus();
        this.B = new CardDataInfo();
        this.C = new CardStatus();
        this.F = new PINData();
        this.G = new UserDataEntry();
        this.H = new ClearTextUserDataEntry();
    }

    public boolean initialize(PPSCRADeviceAdapter pPSCRADeviceAdapter, IMTPPService iMTPPService) {
        this.O = pPSCRADeviceAdapter;
        this.N = iMTPPService;
        this.M = MTConnectionState.Disconnected;
        return true;
    }

    private MTConnectionState a(MTServiceState mTServiceState) {
        MTConnectionState mTConnectionState = MTConnectionState.Error;
        int i = AnonymousClass2.a[mTServiceState.ordinal()];
        if (i == 1) {
            return MTConnectionState.Disconnected;
        }
        if (i == 2) {
            return MTConnectionState.Connected;
        }
        if (i == 3) {
            return MTConnectionState.Connecting;
        }
        if (i != 4) {
            return i != 5 ? mTConnectionState : MTConnectionState.Listening;
        }
        return MTConnectionState.Disconnecting;
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.ppscra.PPSCRADevice$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[MTServiceState.values().length];
            a = iArr;
            try {
                iArr[MTServiceState.Disconnected.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[MTServiceState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[MTServiceState.Connecting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[MTServiceState.Disconnecting.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[MTServiceState.Listening.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public boolean isListeningForConnection() {
        return this.M == MTConnectionState.Listening;
    }

    private void a(MTConnectionState mTConnectionState) {
        byte[] deviceSerialNumber;
        if (mTConnectionState == this.M) {
            return;
        }
        this.M = mTConnectionState;
        if (mTConnectionState == MTConnectionState.Connected) {
            this.i.clear();
            this.j = (byte) 0;
            this.k = (byte) 0;
            IMTPPService iMTPPService = this.N;
            if (iMTPPService != null && (deviceSerialNumber = iMTPPService.getDeviceSerialNumber()) != null) {
                this.D.SerialNumber = deviceSerialNumber;
            }
        } else if (this.M == MTConnectionState.Disconnected) {
            this.i.clear();
            this.D = new DeviceInformation();
        }
        PPSCRADeviceAdapter pPSCRADeviceAdapter = this.O;
        if (pPSCRADeviceAdapter != null) {
            pPSCRADeviceAdapter.OnMessage(11, -1, -1, mTConnectionState);
        }
    }

    @Override // com.magtek.mobile.android.ppscra.MTServiceAdapter
    public void OnServiceState(MTServiceState mTServiceState) {
        a(a(mTServiceState));
    }

    @Override // com.magtek.mobile.android.ppscra.MTServiceAdapter
    public void OnDeviceData(byte[] bArr) {
        f(bArr);
    }

    protected void sendDeviceEvent(int i, int i2, int i3, Object obj) {
        PPSCRADeviceAdapter pPSCRADeviceAdapter = this.O;
        if (pPSCRADeviceAdapter != null) {
            pPSCRADeviceAdapter.OnMessage(i, i2, i3, obj);
        }
    }

    private void a(byte[] bArr) {
        if (bArr.length > 0) {
            sendDeviceEvent(99, 0, 0, bArr);
        }
    }

    private void b(byte[] bArr) {
        if (bArr.length > 0) {
            sendDeviceEvent(35, 0, 0, bArr);
        }
    }

    private void a(byte b2, byte[] bArr) {
        if (bArr.length > 0) {
            sendDeviceEvent(32, b2, -1, bArr);
        }
    }

    private void a(byte b2, byte b3, byte[] bArr) {
        sendDeviceEvent(52, b2, b3, bArr);
    }

    private void c(byte[] bArr) {
        sendDeviceEvent(54, 0, 0, bArr);
    }

    private void d(byte[] bArr) {
        sendDeviceEvent(77, 0, 0, bArr);
    }

    private void b() {
        sendDeviceEvent(33, 0, 0, null);
    }

    private void c() {
        sendDeviceEvent(34, 0, 0, null);
    }

    private void d() {
        this.j = (byte) 1;
        Handler handler = this.P;
        handler.sendMessageDelayed(handler.obtainMessage(0), 5000L);
    }

    private void e() {
        this.P.removeMessages(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public a f() {
        if (this.i.size() > 0) {
            return this.i.peek();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        if (this.i.size() == 0) {
            return;
        }
        e();
        this.i.remove();
        if (this.i.size() > 0) {
            a aVarPeek = this.i.peek();
            d();
            IMTPPService iMTPPService = this.N;
            if (iMTPPService != null) {
                iMTPPService.sendData(aVarPeek.c);
            }
        }
    }

    private long e(byte[] bArr) {
        if (this.N == null) {
            Log.w(b, "PPSCRADevice - PPSCRA Device Service is not running");
            return -1L;
        }
        if (bArr.length < 2) {
            return -1L;
        }
        boolean z = this.i.size() == 0;
        a aVar = new a();
        aVar.b = bArr[1];
        aVar.c = bArr;
        this.i.add(aVar);
        if (z) {
            e();
            d();
            this.N.sendData(bArr);
        } else {
            Log.w(b, "PPSCRADevice sendCommand defered, commands pending =" + this.i.size());
        }
        return -1L;
    }

    public long sendMultiData(byte b2, byte[] bArr) {
        int i;
        Log.i(b, "PPSCRADevice sendMultiData");
        if (bArr == null || bArr.length == 0) {
            return -1L;
        }
        long j = 0;
        try {
            int length = bArr.length;
            byte[] bArr2 = new byte[9];
            Arrays.fill(bArr2, (byte) 0);
            bArr2[0] = 1;
            bArr2[1] = 16;
            bArr2[2] = b2;
            bArr2[3] = 0;
            bArr2[4] = (byte) (length & 255);
            bArr2[5] = (byte) ((length >> 8) & 255);
            bArr2[6] = (byte) ((length >> 16) & 255);
            bArr2[7] = (byte) ((length >> 24) & 255);
            if (length / 60 > 255) {
                bArr2[8] = 1;
            } else {
                bArr2[8] = 0;
            }
            if (e(bArr2) == 0) {
                return -1L;
            }
            long j2 = 0;
            int i2 = 1;
            int i3 = 0;
            while (true) {
                if (length > 60) {
                    i = length - 60;
                    length = 60;
                } else {
                    i = 0;
                }
                if (i2 > 255) {
                    i2 = 1;
                }
                try {
                    byte[] bArr3 = new byte[length + 5];
                    Arrays.fill(bArr3, (byte) 0);
                    bArr3[0] = 1;
                    bArr3[1] = 16;
                    bArr3[2] = b2;
                    bArr3[3] = (byte) i2;
                    bArr3[4] = (byte) length;
                    System.arraycopy(bArr, i3, bArr3, 5, length);
                    if (e(bArr3) == 0) {
                        j2 = -1;
                    }
                    i3 += length;
                    i2++;
                    if (i <= 0 || j2 != 0) {
                        return j2;
                    }
                    length = i;
                } catch (Exception unused) {
                    j = j2;
                    Log.w(b, "PPSCRADevice sendMultiData - Exception caught");
                    return j;
                }
            }
        } catch (Exception unused2) {
        }
    }

    public boolean isDeviceSRED() {
        String str;
        Log.i(b, "PPSCRADevice isDeviceSRED");
        String str2 = this.D.CapabilityString;
        if (str2 == null || str2.isEmpty()) {
            Object obj = new Object();
            this.e = obj;
            synchronized (obj) {
                requestDeviceInformation((byte) 2);
                a(this.e);
                str = this.D.CapabilityString;
            }
            str2 = str;
        }
        return str2.contains("SR=1");
    }

    public byte getCommandStatus() {
        Log.i(b, "PPSCRADevice getCommandStatus, Status=" + ((int) this.j));
        return this.j;
    }

    public boolean open() {
        try {
            if (this.N == null || this.M != MTConnectionState.Disconnected) {
                return false;
            }
            Log.i(b, "Device Is Disconnected");
            this.N.initialize(this.c, this);
            this.N.connect();
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public void close() {
        try {
            Log.i(b, "MTSCRADevice Close");
            IMTPPService iMTPPService = this.N;
            if (iMTPPService != null) {
                iMTPPService.disconnect();
                this.N = null;
            }
        } catch (Exception unused) {
        }
    }

    public long deviceReset() {
        Log.i(b, "PPSCRADevice deviceReset");
        return e(new byte[]{1, -1, 0});
    }

    public byte getStatusCode() {
        Log.i(b, "PPSCRADevice getStatusCode");
        return this.k;
    }

    public long cancelOperation() {
        Log.i(b, "PPSCRADevice cancelOperation");
        return e(new byte[]{1, 5, 0});
    }

    public long requestBypassPINCommand() {
        Log.i(b, "PPSCRADevice requestBypassPINCommand");
        return e(new byte[]{1, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, 0});
    }

    public long setPAN(String str) {
        Log.i(b, "PPSCRADevice setPAN, accountNumber=" + str);
        try {
            byte[] bArr = new byte[str.length() + 4];
            bArr[0] = 1;
            bArr[1] = 13;
            bArr[2] = 1;
            bArr[3] = (byte) str.length();
            for (int i = 0; i < str.length(); i++) {
                bArr[i + 4] = (byte) str.charAt(i);
            }
            return e(bArr);
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice setPAN - Exception caught");
            return -1L;
        }
    }

    public long setAmount(byte b2, String str) {
        String str2 = b;
        StringBuilder sb = new StringBuilder();
        sb.append("PPSCRADevice setAmount, amountType=");
        sb.append(String.format("%02X ", Byte.valueOf(b2)));
        sb.append(", amount=");
        sb.append(str);
        Log.i(str2, sb.toString());
        byte[] bArr = new byte[26];
        bArr[0] = 1;
        bArr[1] = 13;
        bArr[2] = 0;
        bArr[3] = (byte) str.length();
        bArr[4] = b2;
        for (int i = 0; i < str.length(); i++) {
            bArr[i + 5] = (byte) str.charAt(i);
        }
        return e(bArr);
    }

    public long endSession(byte b2) {
        Log.i(b, "PPSCRADevice endSession, displayMessageID=" + String.format("%02X ", Byte.valueOf(b2)));
        try {
            return e(new byte[]{1, 2, b2});
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice endSession - Exception caught");
            return -1L;
        }
    }

    public long requestChallengeAndSession() {
        Log.i(b, "PPSCRADevice requestChallengeAndSession");
        return e(new byte[]{0, -87, 12});
    }

    public long requestConfirmationSession(byte b2, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        String str = b;
        StringBuilder sb = new StringBuilder();
        sb.append("PPSCRADevice requestConfirmationSessionbyte, mode=");
        sb.append(String.format("%02X ", Byte.valueOf(b2)));
        Log.i(str, sb.toString());
        try {
            byte[] bArr4 = new byte[19];
            Arrays.fill(bArr4, (byte) 0);
            bArr4[0] = 1;
            bArr4[1] = PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY;
            bArr4[2] = b2;
            int length = bArr != null ? bArr.length : 0;
            int length2 = bArr2 != null ? bArr2.length : 0;
            int length3 = bArr3 != null ? bArr3.length : 0;
            if (length2 > 4) {
                length2 = 4;
            }
            if (length > 4) {
                length = 4;
            }
            if (length3 > 8) {
                length3 = 8;
            }
            for (int i = 0; i < length; i++) {
                bArr4[i + 3] = bArr[i];
            }
            for (int i2 = 0; i2 < length2; i2++) {
                bArr4[i2 + 7] = bArr2[i2];
            }
            for (int i3 = 0; i3 < length3; i3++) {
                bArr4[i3 + 11] = bArr3[i3];
            }
            return e(bArr4);
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice requestConfirmationSessionbyte - Exception caught");
            return -1L;
        }
    }

    public long requestPowerUpResetICC(byte b2, byte b3) {
        Log.i(b, "PPSCRADevice requestPowerUpResetICC, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", operation=" + String.format("%02X ", Byte.valueOf(b3)));
        byte[] bArr = new byte[18];
        Arrays.fill(bArr, (byte) 0);
        bArr[0] = 1;
        bArr[1] = -90;
        bArr[2] = b2;
        bArr[3] = b3;
        return e(bArr);
    }

    public long requestPowerDownICC(byte b2) {
        Log.i(b, "PPSCRADevice requestPowerDownICC, waitTime=" + String.format("%02X ", Byte.valueOf(b2)));
        try {
            byte[] bArr = new byte[18];
            Arrays.fill(bArr, (byte) 0);
            bArr[0] = 1;
            bArr[1] = -90;
            bArr[2] = b2;
            bArr[3] = 2;
            return e(bArr);
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice requestPowerDownICC - Exception caught");
            return -1L;
        }
    }

    public long requestICCAPDU(byte[] bArr) {
        long jSendMultiData;
        Log.i(b, "PPSCRADevice requestICCAPDU");
        try {
            jSendMultiData = sendMultiData((byte) -89, bArr);
            if (jSendMultiData != 0) {
                return jSendMultiData;
            }
            try {
                byte[] bArr2 = new byte[18];
                Arrays.fill(bArr2, (byte) 0);
                bArr2[0] = 1;
                bArr2[1] = -89;
                return e(bArr2);
            } catch (Exception unused) {
                Log.w(b, "PPSCRADevice requestICCAPDU - Exception caught");
                return jSendMultiData;
            }
        } catch (Exception unused2) {
            jSendMultiData = -1;
        }
    }

    public long sendSpecialCommand(byte[] bArr) {
        Log.i(b, "PPSCRADevice sendSpecialCommand");
        if (bArr == null) {
            return -1L;
        }
        try {
            int length = bArr.length;
            byte[] bArr2 = new byte[length + 1];
            Arrays.fill(bArr2, (byte) 0);
            bArr2[0] = 1;
            System.arraycopy(bArr, 0, bArr2, 1, length);
            return e(bArr2);
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice sendSpecialCommand - Exception caught");
            return -1L;
        }
    }

    public long getSpecialCommand(byte[] bArr) {
        Log.i(b, "PPSCRADevice getSpecialCommand");
        if (bArr == null) {
            return -1L;
        }
        try {
            int length = bArr.length;
            byte[] bArr2 = new byte[length + 1];
            Arrays.fill(bArr2, (byte) 0);
            bArr2[0] = 0;
            System.arraycopy(bArr, 0, bArr2, 1, length);
            return e(bArr2);
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice getSpecialCommand - Exception caught");
            return -1L;
        }
    }

    public long requestGetEMVTags(byte b2, byte b3, byte[] bArr, byte b4, byte b5, byte[] bArr2) {
        int length;
        int length2;
        Log.i(b, "PPSCRADevice requestEMVTags, tagType=" + String.format("%02X ", Byte.valueOf(b2)) + ", tagOperation=" + String.format("%02X ", Byte.valueOf(b3)));
        try {
            if (b3 != 0) {
                if (b3 != 15) {
                    return -1L;
                }
                if (bArr2 != null) {
                    length2 = bArr2.length;
                    if (length2 > 4) {
                        length2 = 4;
                    }
                } else {
                    length2 = 0;
                }
                byte[] bArr3 = new byte[length2 + 6];
                Arrays.fill(bArr3, (byte) 0);
                bArr3[0] = 1;
                bArr3[1] = -95;
                bArr3[2] = b2;
                bArr3[3] = b3;
                bArr3[4] = b4;
                bArr3[5] = b5;
                if (length2 > 0) {
                    System.arraycopy(bArr2, 0, bArr3, 6, length2);
                }
                return e(bArr3);
            }
            long jSendMultiData = bArr != null ? sendMultiData((byte) -95, bArr) : -1L;
            if (jSendMultiData != 0) {
                return jSendMultiData;
            }
            if (bArr2 != null) {
                length = bArr2.length;
                if (length > 4) {
                    length = 4;
                }
            } else {
                length = 0;
            }
            byte[] bArr4 = new byte[length + 6];
            Arrays.fill(bArr4, (byte) 0);
            bArr4[0] = 1;
            bArr4[1] = -95;
            bArr4[2] = b2;
            bArr4[3] = b3;
            bArr4[4] = b4;
            bArr4[5] = b5;
            if (length > 0) {
                System.arraycopy(bArr2, 0, bArr4, 6, length);
            }
            return e(bArr4);
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice requestSetEMVTagsWithTagType - Exception caught");
            return -1L;
        }
    }

    public long requestSetEMVTags(byte b2, byte b3, byte[] bArr, byte b4, byte b5, byte[] bArr2) {
        int length;
        int length2;
        Log.i(b, "PPSCRADevice requestEMVTags, tagType=" + String.format("%02X ", Byte.valueOf(b2)) + ", tagOperation=" + String.format("%02X ", Byte.valueOf(b3)));
        try {
            if (b3 == -1) {
                if (bArr2 != null) {
                    length = bArr2.length;
                    if (length > 4) {
                        length = 4;
                    }
                } else {
                    length = 0;
                }
                byte[] bArr3 = new byte[length + 6];
                Arrays.fill(bArr3, (byte) 0);
                bArr3[0] = 1;
                bArr3[1] = -95;
                bArr3[2] = b2;
                bArr3[3] = b3;
                bArr3[4] = b4;
                bArr3[5] = b5;
                if (length > 0) {
                    System.arraycopy(bArr2, 0, bArr3, 6, length);
                }
                return e(bArr3);
            }
            if (b3 != 1) {
                return -1L;
            }
            long jSendMultiData = bArr != null ? sendMultiData((byte) -95, bArr) : -1L;
            if (jSendMultiData != 0) {
                return jSendMultiData;
            }
            if (bArr2 != null) {
                length2 = bArr2.length;
                if (length2 > 4) {
                    length2 = 4;
                }
            } else {
                length2 = 0;
            }
            byte[] bArr4 = new byte[length2 + 6];
            Arrays.fill(bArr4, (byte) 0);
            bArr4[0] = 1;
            bArr4[1] = -95;
            bArr4[2] = b2;
            bArr4[3] = b3;
            bArr4[4] = b4;
            bArr4[5] = b5;
            if (length2 > 0) {
                System.arraycopy(bArr2, 0, bArr4, 6, length2);
            }
            return e(bArr4);
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice requestSetEMVTagsWithTagType - Exception caught");
            return -1L;
        }
    }

    public long setCAPublicKey(byte b2, byte[] bArr) {
        long jSendMultiData;
        Log.i(b, "PPSCRADevice setCAPublicKey, operation=" + String.format("%02X ", Byte.valueOf(b2)));
        long j = 0;
        if (bArr != null) {
            try {
                jSendMultiData = sendMultiData((byte) -91, bArr);
            } catch (Exception unused) {
                Log.w(b, "PPSCRADevice setCAPublicKey - Exception caught");
                return j;
            }
        } else {
            jSendMultiData = 0;
        }
        if (jSendMultiData == 0) {
            try {
                jSendMultiData = e(new byte[]{1, -91, b2});
            } catch (Exception unused2) {
                j = jSendMultiData;
                Log.w(b, "PPSCRADevice setCAPublicKey - Exception caught");
                return j;
            }
        }
        return jSendMultiData;
    }

    public long setDisplayMessage(byte b2, byte b3) {
        Log.i(b, "PPSCRADevice setDisplayMessage, timeOut=" + String.format("%02X ", Byte.valueOf(b2)) + ", messageID=" + String.format("%02X ", Byte.valueOf(b3)));
        try {
            return e(new byte[]{1, 7, b2, b3});
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice setDisplayMessage - Exception caught");
            return -1L;
        }
    }

    public long sendBigBlockData(byte b2, byte[] bArr) {
        Log.i(b, "PPSCRADevice sendBigBlockData");
        if (bArr == null) {
            return -1L;
        }
        try {
            return sendMultiData(b2, bArr);
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice sendBigBlockData - Exception caught");
            return -1L;
        }
    }

    public long sendBitmap(byte b2, byte b3, byte[] bArr) {
        Log.i(b, "PPSCRADevice setBitmap, Slot=" + ((int) b2) + ", Otion=" + ((int) b3));
        try {
            byte[] bArr2 = {1, 12, b2, b3};
            if (b3 == 0) {
                return e(bArr2);
            }
            if (b3 != 1 && b3 != 2) {
                return -1L;
            }
            long jSendMultiData = bArr != null ? sendMultiData((byte) 12, bArr) : -1L;
            return jSendMultiData == 0 ? e(bArr2) : jSendMultiData;
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice setBitmap - Exception caught");
            return -1L;
        }
    }

    public DeviceInformation getDeviceInformation() {
        Log.i(b, "PPSCRADevice getDeviceInformation");
        return this.D;
    }

    public long requestDeviceInformation(byte b2) {
        Log.i(b, "PPSCRADevice requestDeviceInformation, mode=" + String.format("%02X ", Byte.valueOf(b2)));
        e(new byte[]{1, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, b2});
        return e(new byte[]{0, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, b2});
    }

    public DeviceStateStatus getDeviceStateStatus() {
        Log.i(b, "PPSCRADevice getDeviceStateStatus");
        return this.E;
    }

    public long requestDeviceStatus() {
        Log.i(b, "PPSCRADevice requestDeviceStatus");
        return e(new byte[]{1, 8});
    }

    public long requestKernelInformation(byte b2) {
        Log.i(b, "PPSCRADevice requestKernelInformation");
        return e(new byte[]{1, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, b2});
    }

    public byte[] getBINTableData() {
        Log.i(b, "PPSCRADevice getBINTableData");
        return this.s;
    }

    public long requestBINTableData() {
        Log.i(b, "PPSCRADevice requestBINTableData");
        return e(new byte[]{0, 50, 0});
    }

    public long setBINTableData(byte[] bArr, byte[] bArr2) {
        long jSendMultiData;
        Log.i(b, "PPSCRADevice setBINTableData");
        if (bArr != null) {
            int length = bArr.length;
            int length2 = bArr2 != null ? bArr2.length : 0;
            byte[] bArr3 = new byte[length + length2];
            Arrays.fill(bArr3, (byte) 0);
            System.arraycopy(bArr, 0, bArr3, 0, length);
            System.arraycopy(bArr2, 0, bArr3, length, length2);
            jSendMultiData = sendMultiData((byte) 50, bArr3);
        } else {
            jSendMultiData = -1;
        }
        return jSendMultiData == 0 ? e(new byte[]{1, 50, 0}) : jSendMultiData;
    }

    public long requestCard(byte b2, byte b3, byte b4) {
        Log.i(b, "PPSCRADevice requestCard, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", messageID=" + String.format("%02X ", Byte.valueOf(b3)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b4)));
        return e(new byte[]{1, 3, b2, b3, b4});
    }

    public long requestManualCardData(byte b2, byte b3, byte b4) {
        Log.i(b, "PPSCRADevice requestManualCardData, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b3)) + ", options=" + String.format("%02X ", Byte.valueOf(b4)));
        return e(new byte[]{1, 17, b2, b4, b3});
    }

    public long requestUserDataEntry(byte b2, byte b3, byte b4) {
        Log.i(b, "PPSCRADevice requestUserDataEntry, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", displayMessageID=" + String.format("%02X ", Byte.valueOf(b3)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b4)));
        return e(new byte[]{1, 20, b2, b3, b4});
    }

    public long requestResponse(byte b2, byte b3, byte b4, byte b5) {
        Log.i(b, "PPSCRADevice requestResponse, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", selectMsg=" + String.format("%02X ", Byte.valueOf(b3)) + ", keyMask=" + String.format("%02X ", Byte.valueOf(b4)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b5)));
        return e(new byte[]{1, 6, b2, b3, b4, b5});
    }

    public long requestPIN(byte b2, byte b3, byte b4, byte b5, byte b6, byte b7) {
        Log.i(b, "PPSCRADevice requestPIN, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", pinMode=" + String.format("%02X ", Byte.valueOf(b3)) + ", maxPINLength=" + String.format("%02X ", Byte.valueOf(b4)) + ", minPINLength=" + String.format("%02X ", Byte.valueOf(b5)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b6)) + ", option=" + String.format("%02X ", Byte.valueOf(b7)));
        return e(new byte[]{1, 4, b2, b3, (byte) ((b4 << 4) + b5), b6, b7});
    }

    public long requestSignature(byte b2, byte b3, byte b4) {
        Log.i(b, "PPSCRADevice requestSignature, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b3)) + ", option=" + String.format("%02X ", Byte.valueOf(b4)));
        return e(new byte[]{1, 18, b2, b4, b3});
    }

    public long requestTipCashback(byte b2, byte b3, byte b4, byte[] bArr, byte[] bArr2, byte[] bArr3, byte b5, byte b6, byte b7, byte b8) {
        String str = b;
        StringBuilder sb = new StringBuilder();
        sb.append("PPSCRADevice requestTipCashback, waitTime=");
        sb.append(String.format("%02X ", Byte.valueOf(b2)));
        sb.append(", mode=");
        sb.append(String.format("%02X ", Byte.valueOf(b3)));
        sb.append(", beepTones=");
        sb.append(String.format("%02X ", Byte.valueOf(b4)));
        sb.append(", tipSelectionMode=");
        sb.append(String.format("%02X ", Byte.valueOf(b5)));
        sb.append(", leftAmount=");
        sb.append(String.format("%02X ", Byte.valueOf(b6)));
        sb.append(", middleAmount=");
        sb.append(String.format("%02X ", Byte.valueOf(b7)));
        sb.append(", rightAmount=");
        sb.append(String.format("%02X ", Byte.valueOf(b8)));
        Log.i(str, sb.toString());
        byte[] bArr4 = new byte[50];
        Arrays.fill(bArr4, (byte) 0);
        bArr4[0] = 1;
        bArr4[1] = -96;
        bArr4[2] = b2;
        bArr4[3] = b3;
        bArr4[4] = b4;
        int length = bArr.length;
        if (length > 6) {
            length = 6;
        }
        int length2 = bArr2.length;
        int i = length2 <= 6 ? length2 : 6;
        int length3 = bArr3.length;
        int i2 = length3 <= 3 ? length3 : 3;
        for (int i3 = 0; i3 < length; i3++) {
            bArr4[i3 + 5] = bArr[i3];
        }
        for (int i4 = 0; i4 < i; i4++) {
            bArr4[i4 + 11] = bArr2[i4];
        }
        for (int i5 = 0; i5 < i2; i5++) {
            bArr4[i5 + 17] = bArr3[i5];
        }
        bArr4[20] = b5;
        bArr4[21] = b6;
        bArr4[22] = b7;
        bArr4[23] = b8;
        return e(bArr4);
    }

    public long startEMVTransaction(byte b2, byte b3, byte b4, byte b5, byte b6, byte[] bArr, byte b7, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte b8, byte b9, byte b10, byte[] bArr6, byte[] bArr7, byte b11, byte b12, byte[] bArr8, byte[] bArr9) {
        Log.i(b, "PPSCRADevice startEMVTransation, cardType=" + String.format("%02X ", Byte.valueOf(b2)) + ", confirmationTime=" + String.format("%02X ", Byte.valueOf(b3)) + ", pinEnteringTime=" + String.format("%02X ", Byte.valueOf(b4)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b5)) + ", option=" + String.format("%02X ", Byte.valueOf(b6)) + ", transactionType=" + String.format("%02X ", Byte.valueOf(b7)) + ", categoryCode=" + String.format("%02X ", Byte.valueOf(b8)) + ", chipMode=" + String.format("%02X ", Byte.valueOf(b9)) + ", operatingMode=" + String.format("%02X ", Byte.valueOf(b10)));
        byte[] bArr10 = new byte[64];
        Arrays.fill(bArr10, (byte) 0);
        bArr10[0] = 1;
        bArr10[1] = -94;
        bArr10[2] = b3;
        bArr10[3] = b4;
        bArr10[4] = 0;
        bArr10[5] = b5;
        bArr10[6] = b2;
        bArr10[7] = b6;
        if (bArr != null) {
            int length = bArr.length;
            if (length > 6) {
                length = 6;
            }
            if (length > 0) {
                System.arraycopy(bArr, 0, bArr10, 8, length);
            }
        }
        bArr10[14] = b7;
        if (bArr2 != null) {
            int length2 = bArr2.length;
            if (length2 > 6) {
                length2 = 6;
            }
            if (length2 > 0) {
                System.arraycopy(bArr2, 0, bArr10, 15, length2);
            }
        }
        if (bArr3 != null) {
            int length3 = bArr3.length;
            if (length3 > 6) {
                length3 = 6;
            }
            if (length3 > 0) {
                System.arraycopy(bArr3, 0, bArr10, 21, length3);
            }
        }
        if (bArr4 != null) {
            int length4 = bArr4.length;
            if (length4 > 6) {
                length4 = 6;
            }
            if (length4 > 0) {
                System.arraycopy(bArr4, 0, bArr10, 27, length4);
            }
        }
        if (bArr5 != null) {
            int length5 = bArr5.length;
            int i = length5 <= 2 ? length5 : 2;
            if (i > 0) {
                System.arraycopy(bArr5, 0, bArr10, 33, i);
            }
        }
        bArr10[35] = b8;
        bArr10[36] = b9;
        bArr10[37] = b10;
        if (bArr6 != null) {
            int length6 = bArr6.length;
            if (length6 > 6) {
                length6 = 6;
            }
            if (length6 > 0) {
                System.arraycopy(bArr6, 0, bArr10, 38, length6);
            }
        }
        if (bArr7 != null) {
            int length7 = bArr7.length;
            int i2 = length7 <= 3 ? length7 : 3;
            if (i2 > 0) {
                System.arraycopy(bArr7, 0, bArr10, 44, i2);
            }
        }
        bArr10[48] = b11;
        bArr10[49] = b12;
        if (bArr8 != null) {
            int length8 = bArr8.length;
            int i3 = length8 <= 6 ? length8 : 6;
            if (i3 > 0) {
                System.arraycopy(bArr8, 0, bArr10, 50, i3);
            }
        }
        if (bArr9 != null) {
            int length9 = bArr9.length;
            int i4 = length9 <= 8 ? length9 : 8;
            if (i4 > 0) {
                System.arraycopy(bArr9, 0, bArr10, 56, i4);
            }
        }
        return e(bArr10);
    }

    public long requestSmartCard(byte b2, byte b3, byte b4, byte b5, byte b6, byte[] bArr, byte b7, byte[] bArr2, byte[] bArr3) {
        Log.i(b, "PPSCRADevice requestSmartCard, cardType=" + String.format("%02X ", Byte.valueOf(b2)) + ", confirmationTime=" + String.format("%02X ", Byte.valueOf(b3)) + ", pinEnteringTime=" + String.format("%02X ", Byte.valueOf(b4)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b5)) + ", option=" + String.format("%02X ", Byte.valueOf(b6)) + ", transactionType=" + String.format("%02X ", Byte.valueOf(b7)));
        byte[] bArr4 = new byte[50];
        Arrays.fill(bArr4, (byte) 0);
        bArr4[0] = 1;
        bArr4[1] = -94;
        bArr4[2] = b3;
        bArr4[3] = b4;
        bArr4[4] = 0;
        bArr4[5] = b5;
        bArr4[6] = b2;
        bArr4[7] = b6;
        int length = bArr.length;
        if (length > 6) {
            length = 6;
        }
        int length2 = bArr2.length;
        int i = length2 <= 6 ? length2 : 6;
        for (int i2 = 0; i2 < length; i2++) {
            bArr4[i2 + 8] = bArr[i2];
        }
        bArr4[14] = b7;
        for (int i3 = 0; i3 < i; i3++) {
            bArr4[i3 + 15] = bArr2[i3];
        }
        int i4 = 29;
        if (bArr3 != null) {
            int length3 = bArr3.length;
            if (length3 <= 29) {
                i4 = length3;
            }
        } else {
            i4 = 0;
        }
        if (i4 > 0) {
            System.arraycopy(bArr3, 0, bArr4, 21, i4);
        }
        return e(bArr4);
    }

    public long sendAcquirerResponse(byte[] bArr) {
        Log.i(b, "PPSCRADevice sendAcquirerResponse");
        sendBigBlockData((byte) -92, bArr);
        byte[] bArr2 = new byte[14];
        Arrays.fill(bArr2, (byte) 0);
        bArr2[0] = 1;
        bArr2[1] = -92;
        return e(bArr2);
    }

    public long requestDeviceConfiguration() {
        Log.i(b, "PPSCRADevice requestDeviceConfiguration");
        byte[] bArr = new byte[9];
        Arrays.fill(bArr, (byte) 0);
        bArr[0] = 0;
        bArr[1] = 9;
        return e(bArr);
    }

    public String getProductID() {
        String str;
        Log.i(b, "PPSCRADevice getProductID");
        String str2 = this.D.ProductID;
        if (str2 != null && !str2.isEmpty()) {
            return str2;
        }
        Object obj = new Object();
        this.f = obj;
        synchronized (obj) {
            requestDeviceInformation((byte) 0);
            a(this.f);
            str = this.D.ProductID;
        }
        return str;
    }

    public byte[] getDeviceSerial() {
        Log.i(b, "PPSCRADevice getDeviceSerial");
        byte[] bArr = this.D.SerialNumber;
        if (bArr == null) {
            Object obj = new Object();
            this.d = obj;
            synchronized (obj) {
                requestDeviceInformation((byte) 5);
                a(this.d);
                bArr = this.D.SerialNumber;
            }
        }
        return bArr;
    }

    public String getDeviceModel() {
        String str;
        Log.i(b, "PPSCRADevice getDeviceModel");
        String str2 = this.D.ProductName;
        if (str2 != null && !str2.isEmpty()) {
            return str2;
        }
        Object obj = new Object();
        this.g = obj;
        synchronized (obj) {
            requestDeviceInformation((byte) 4);
            a(this.g);
            str = this.D.ProductName;
        }
        return str;
    }

    public String getDeviceFirmwareVersion() {
        String str;
        Log.i(b, "PPSCRADevice getDeviceFirmwareVersion");
        String str2 = this.D.FirmwareNumber;
        if (str2 != null && !str2.isEmpty()) {
            return str2;
        }
        Object obj = new Object();
        this.h = obj;
        synchronized (obj) {
            requestDeviceInformation((byte) 6);
            a(this.h);
            str = this.D.FirmwareNumber;
        }
        return str;
    }

    public byte getSessionState() {
        Log.i(b, "PPSCRADevice getSessionState");
        return this.l;
    }

    public CardDataInfo getCardDataInfo() {
        Log.i(b, "PPSCRADevice getCardDataInfo");
        return this.B;
    }

    public CardStatus getCardStatus() {
        Log.i(b, "PPSCRADevice getCardStatus");
        return this.C;
    }

    public PINData getPINData() {
        Log.i(b, "PPSCRADevice getPINData");
        return this.F;
    }

    public void clearCardInfo() {
        Log.i(b, "PPSCRADevice clearCardInfo");
        this.C.clearData();
        this.B.clearData();
    }

    public void clearData() {
        Log.i(b, "PPSCRADevice clearData");
        this.I = false;
        this.J = false;
        this.K = false;
        this.L = false;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = null;
        this.z = null;
        this.A = (byte) 0;
        this.B.clearData();
        this.C.clearData();
        this.E.clearData();
        this.F.clearData();
        this.G.clearData();
        this.H.clearData();
    }

    public UserDataEntry getUserDataEntry() {
        Log.i(b, "PPSCRADevice getUserDataEntry");
        return this.G;
    }

    public void clearUserDataEntry() {
        Log.i(b, "PPSCRADevice clearUserDataEntry");
        this.G.clearData();
    }

    public long getSelectedMenuItem(byte b2, byte b3, byte b4) {
        Log.i(b, "PPSCRADevice requestClearTextUserDataEntry, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", menuSelectionMode=" + String.format("%02X ", Byte.valueOf(b3)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b4)));
        return e(new byte[]{1, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, b2, b3, b4});
    }

    public long requestClearTextUserDataEntry(byte b2, byte b3, byte b4) {
        Log.i(b, "PPSCRADevice requestClearTextUserDataEntry, waitTime=" + String.format("%02X ", Byte.valueOf(b2)) + ", displayMessageID=" + String.format("%02X ", Byte.valueOf(b3)) + ", beepTones=" + String.format("%02X ", Byte.valueOf(b4)));
        return e(new byte[]{1, 31, b2, b3, b4});
    }

    public ClearTextUserDataEntry getClearTextUserDataEntry() {
        Log.i(b, "PPSCRADevice getClearTextUserDataEntry");
        return this.H;
    }

    public void clearClearTextUserDataEntry() {
        Log.i(b, "PPSCRADevice clearClearTextUserDataEntry");
        this.H.clearData();
    }

    public byte[] getEMVTagsData() {
        Log.i(b, "PPSCRADevice getEMVTagsData");
        return this.t;
    }

    public void clearEMVTagsData() {
        Log.i(b, "PPSCRADevice clearEMVTagsData");
        this.t = null;
    }

    public byte[] getCAPublicKeyData() {
        Log.i(b, "MagTekPPSCRA getCAPublicKeyData");
        return this.w;
    }

    public void clearCAPublicKeyData() {
        this.w = null;
    }

    public byte[] getEMVARQCData() {
        Log.i(b, "PPSCRADevice getEMVARQCData");
        return this.u;
    }

    public void clearEMVARQCData() {
        Log.i(b, "PPSCRADevice clearEMVARQCData");
        this.u = null;
    }

    public byte[] getAPDUData() {
        Log.i(b, "PPSCRADevice getAPDUData");
        return this.x;
    }

    public void clearAPDUData() {
        Log.i(b, "PPSCRADevice clearAPDUData");
        this.x = null;
    }

    public byte[] getEMVData() {
        Log.i(b, "PPSCRADevice getEMVData");
        return this.v;
    }

    public void clearEMVData() {
        Log.i(b, "PPSCRADevice clearEMVData");
        this.v = null;
        this.a = null;
    }

    public byte[] getATRData() {
        Log.i(b, "PPSCRADevice getATRData");
        return this.y;
    }

    public void clearATRData() {
        Log.i(b, "PPSCRADevice clearATRData");
        this.y = null;
    }

    public byte[] getSignatureData() {
        Log.i(b, "PPSCRADevice getSignatureData");
        return this.z;
    }

    public void clearSignatureData() {
        Log.i(b, "PPSCRADevice clearSignatureData");
        this.z = null;
    }

    public long getUserSignature() {
        Log.i(b, "PPSCRADevice getUserSignature");
        return e(new byte[]{1, 19});
    }

    public long requestMSR() {
        Log.i(b, "PPSCRADevice requestMSR");
        this.B.clearData();
        return e(new byte[]{1, 10, 0});
    }

    public long requestTransactionData() {
        Log.i(b, "PPSCRADevice requestTransactionData");
        byte[] bArr = new byte[6];
        Arrays.fill(bArr, (byte) 0);
        bArr[0] = 1;
        bArr[1] = -85;
        return e(bArr);
    }

    public long requestATR() {
        Log.i(b, "PPSCRADevice requestATR");
        return e(new byte[]{1, -93, 0});
    }

    public long requestKeyInformation(byte b2) {
        Log.i(b, "PPSCRADevice requestKeyInformation, identification=" + String.format("%02X ", Byte.valueOf(b2)));
        e(new byte[]{1, 14, b2, 0});
        return e(new byte[]{0, 14, b2, 0});
    }

    public byte getFnKeyPressed() {
        return this.A;
    }

    private void f(byte[] bArr) {
        if (bArr != null && bArr.length >= 1) {
            byte b2 = bArr[0];
            if (b2 == 32) {
                String str = b;
                Log.i(str, "Device Status received");
                if (this.L) {
                    this.L = false;
                    Log.i(str, "Sending Device Status");
                    a(bArr);
                }
            } else {
                a(bArr);
            }
            if (b2 == -93) {
                j(bArr);
                return;
            }
            if (b2 == -90) {
                k(bArr);
                return;
            }
            if (b2 == -84) {
                n(bArr);
                return;
            }
            if (b2 == 1) {
                p(bArr);
                return;
            }
            if (b2 == 9) {
                r(bArr);
                return;
            }
            if (b2 == 14) {
                s(bArr);
                return;
            }
            if (b2 == 26) {
                t(bArr);
                return;
            }
            if (b2 == 50) {
                u(bArr);
                return;
            }
            if (b2 == -88) {
                l(bArr);
                return;
            }
            if (b2 == -87) {
                m(bArr);
                return;
            }
            switch (b2) {
                case -31:
                case -30:
                case -29:
                case -28:
                case -27:
                    q(bArr);
                    break;
                default:
                    switch (b2) {
                        case 32:
                            v(bArr);
                            break;
                        case 33:
                            w(bArr);
                            break;
                        case 34:
                            y(bArr);
                            break;
                        case 35:
                            z(bArr);
                            break;
                        case 36:
                            A(bArr);
                            break;
                        case 37:
                            B(bArr);
                            break;
                        case 38:
                            h(bArr);
                            break;
                        case 39:
                            C(bArr);
                            break;
                        case 40:
                            K(bArr);
                            break;
                        case 41:
                            L(bArr);
                            break;
                        case 42:
                            o(bArr);
                            break;
                        default:
                            switch (b2) {
                                case 46:
                                    x(bArr);
                                    break;
                                case 47:
                                    g(bArr);
                                    break;
                                case 48:
                                    i(bArr);
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
    }

    private void g(byte[] bArr) {
        sendDeviceEvent(26, -1, -1, null);
        if (bArr == null || bArr.length < 2) {
            return;
        }
        sendDeviceEvent(26, bArr[1], -1, bArr.length > 4 ? Arrays.copyOfRange(bArr, 4, bArr.length) : null);
    }

    private void h(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return;
        }
        sendDeviceEvent(27, bArr[1], -1, bArr.length > 2 ? Arrays.copyOfRange(bArr, 2, bArr.length) : null);
    }

    private void i(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return;
        }
        sendDeviceEvent(28, bArr[1], -1, bArr.length > 2 ? Arrays.copyOfRange(bArr, 2, bArr.length) : null);
    }

    private void m(byte[] bArr) {
        Log.w(b, "PPSCRADevice processGetChallengeAndSessionKey");
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        d(Arrays.copyOfRange(bArr, 2, bArr.length));
    }

    private void o(byte[] bArr) {
        if (bArr == null || bArr.length < 3) {
            return;
        }
        sendDeviceEvent(66, bArr[1], bArr[2], null);
    }

    private void p(byte[] bArr) {
        byte b2;
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processCommandACKResponse Unexpected Data Length - Expecting: 3, Received:" + bArr.length);
            return;
        }
        byte b3 = bArr[1];
        a aVarF = f();
        if (aVarF == null || aVarF.b != (b2 = aVarF.b)) {
            return;
        }
        Log.i(b, "PPSCRADevice processCommandACKResponse - ACK Command Received:0x" + String.format("%02X", Byte.valueOf(b2)) + ", ACK Status=" + ((int) b3));
        if (b3 == 0) {
            this.j = (byte) 0;
        } else {
            this.j = (byte) 3;
        }
        this.k = b3;
        sendDeviceEvent(25, b3, b2, null);
        sendDeviceEvent(22, this.j, b2, null);
        if (b2 == 3) {
            this.I = true;
        }
        if (b2 == 17) {
            this.I = true;
        }
        if (b2 == -94) {
            this.J = true;
        }
        if (b2 == -90) {
            this.K = true;
        }
        if (b2 == 8) {
            this.L = true;
        }
        g();
    }

    private void q(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processWirelessConfiguration Unexpected Data Length");
            return;
        }
        byte b2 = bArr[0];
        a aVarF = f();
        if (aVarF == null || aVarF.b != b2) {
            return;
        }
        Log.i(b, "PPSCRADevice processWirelessConfiguration - Device Configuration Received:" + ((int) b2));
        g();
    }

    private void r(byte[] bArr) {
        if (bArr.length != 9) {
            Log.w(b, "PPSCRADevice processSetGetDeviceConfiguration Unexpected Data Length - Expecting:9, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[0];
        a aVarF = f();
        if (aVarF == null || aVarF.b != b2) {
            return;
        }
        Log.i(b, "PPSCRADevice processSetGetDeviceConfiguration - Device Configuration Received:" + ((int) b2));
        this.j = (byte) 0;
        sendDeviceEvent(31, 0, 0, Arrays.copyOfRange(bArr, 0, bArr.length));
        g();
    }

    private void s(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processKeyInformation Unexpected Data Length - Expecting: 3 or more, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[0];
        a aVarF = f();
        if (aVarF != null && aVarF.b == b2) {
            Log.i(b, "PPSCRADevice processKeyInformation - Key Information Received:" + ((int) b2));
            this.j = (byte) 0;
            g();
        }
        byte b3 = bArr[1];
        byte b4 = bArr[2];
        byte b5 = bArr[3];
        byte[] bArrCopyOfRange = null;
        int i = b5 + 4;
        if (b5 > 0 && bArr.length >= i) {
            bArrCopyOfRange = Arrays.copyOfRange(bArr, 4, i);
        }
        a(b3, b4, bArrCopyOfRange);
    }

    private void a(Object obj) {
        try {
            obj.wait(AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
        } catch (Exception unused) {
        }
    }

    private void b(Object obj) {
        if (obj != null) {
            synchronized (obj) {
                obj.notifyAll();
            }
        }
    }

    private void t(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processDeviceInformationResponse Unexpected Data Length - Expecting: 2 or more, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        a aVarF = f();
        if (aVarF != null && aVarF.b == 26) {
            byte[] bArr2 = aVarF.c;
            if (bArr2.length >= 3 && bArr2[2] == b2) {
                Log.i(b, "PPSCRADevice processDeviceInformationResponse - ACKed Mode:" + ((int) b2));
                this.j = (byte) 0;
                g();
            }
        }
        int i = 2;
        while (true) {
            if (i >= bArr.length) {
                break;
            }
            if (bArr[i] == 0) {
                bArr = Arrays.copyOfRange(bArr, 0, i);
                break;
            }
            i++;
        }
        switch (b2) {
            case 0:
                this.D.ProductID = PPSCRACommon.getTextString(bArr, 2);
                b(this.f);
                break;
            case 1:
                this.D.MaxAppMsgSize = PPSCRACommon.getTextString(bArr, 2);
                break;
            case 2:
                this.D.CapabilityString = PPSCRACommon.getTextString(bArr, 2);
                b(this.e);
                c();
                break;
            case 3:
                this.D.Manufacturer = PPSCRACommon.getTextString(bArr, 2);
                break;
            case 4:
                this.D.ProductName = PPSCRACommon.getTextString(bArr, 2);
                break;
            case 5:
                String textString = PPSCRACommon.getTextString(bArr, 2);
                Log.i(b, "PPSCRADevice processDeviceInformationResponse - Device Serial Number:" + textString);
                this.D.SerialNumber = PPSCRACommon.getByteArrayFromHexString(textString, "");
                b(this.d);
                b();
                break;
            case 6:
                this.D.FirmwareNumber = PPSCRACommon.getTextString(bArr, 2);
                break;
            case 7:
                this.D.BuildInfo = PPSCRACommon.getTextString(bArr, 2);
                break;
            case 8:
                this.D.MACAddress = PPSCRACommon.getTextString(bArr, 2);
                break;
            case 10:
                this.D.Boot1FirmwareNumber = PPSCRACommon.getTextString(bArr, 2);
                break;
            case 11:
                this.D.Boot1FirmwareNumber = PPSCRACommon.getTextString(bArr, 2);
                break;
        }
        a(b2, bArr);
    }

    private void u(byte[] bArr) {
        Log.w(b, "PPSCRADevice processSetGetBINTableData");
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        this.s = Arrays.copyOfRange(bArr, 2, bArr.length);
        c(bArr);
    }

    private void v(byte[] bArr) {
        byte b2;
        byte b3;
        if (bArr.length < 5) {
            Log.w(b, "PPSCRADevice processDeviceStateResponse Unexpected Data Length - Expecting: 5 or more, Received:" + bArr.length);
            return;
        }
        try {
            byte b4 = bArr[1];
            byte b5 = bArr[2];
            byte b6 = bArr[3];
            byte b7 = bArr[4];
            this.l = b5;
            if (bArr.length >= 7) {
                b2 = bArr[5];
                b3 = bArr[6];
            } else {
                b2 = 0;
                b3 = 0;
            }
            int i = b5 & 8;
            if (i == 8) {
                this.m = true;
                Log.i(b, "PPSCRADevice processDeviceStateResponse - Sending Data Avaiable");
                sendDeviceEvent(23, 0, 0, null);
            } else if (i == 0 && this.m) {
                this.m = false;
                sendDeviceEvent(24, 0, 0, null);
            }
            this.E.DeviceState = b4;
            this.E.SessionState = b5;
            this.E.DeviceStatus = b6;
            this.E.DeviceCertificateStatus = b7;
            this.E.HardwareStatus = b2;
            this.E.ICCMasterAndSessionKeyStatus = b3;
            b(bArr);
            a aVarF = f();
            if (aVarF == null || aVarF.b != 2) {
                return;
            }
            Log.i(b, "PPSCRADevice processDeviceStateResponse - Device Status Received");
            this.j = (byte) 0;
            g();
        } catch (Exception unused) {
            Log.w(b, "PPSCRADevice processDeviceStateResponse - Exception caught");
        }
    }

    private void w(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processUserDataEntryResponse Unexpected Data Length - Expecting: 2 or more, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        this.G.clearData();
        this.G.setStatus(b2);
        if (bArr.length >= 20) {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 2, 12);
            byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArr, 12, 20);
            String str = b;
            Log.i(str, "PPSCRADevice processUserDataEntryResponse - MSRKSN:" + PPSCRACommon.getHexString(bArrCopyOfRange));
            Log.i(str, "PPSCRADevice processUserDataEntryResponse - Encrypted User Data Block:" + PPSCRACommon.getHexString(bArrCopyOfRange2));
            this.G.setMSRKSN(bArrCopyOfRange);
            this.G.setEncryptedUserDataBlock(bArrCopyOfRange2);
        }
        sendDeviceEvent(62, b2, -1, this.G);
    }

    private void x(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processClearTextUserDataEntryResponse Unexpected Data Length - Expecting: 2 or more, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        this.H.clearData();
        this.H.setStatus(b2);
        if (bArr.length >= 4) {
            byte b3 = bArr[2];
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 4, bArr[3] + 4);
            this.H.setMode(b3);
            this.H.setData(bArrCopyOfRange);
        }
        sendDeviceEvent(65, b2, -1, this.H);
    }

    private void y(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processCardStatusResponse Unexpected Data Length - Expecting: 2 or more, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        if (b2 != 0) {
            Log.w(b, "PPSCRADevice processCardStatusResponse Operation Status=0x" + String.format("%02X ", Byte.valueOf(b2)));
            this.I = false;
            this.J = false;
            this.K = false;
            sendDeviceEvent(21, b2, -1, null);
            return;
        }
        if (bArr.length < 4) {
            Log.w(b, "PPSCRADevice processCardStatusResponse Operation Status=OK, Unexpected Data Length - Expecting: 4, Received:" + bArr.length);
            return;
        }
        if (bArr.length >= 4) {
            byte b3 = bArr[3];
            this.C.setStatus(bArr[2]);
            this.C.setCardType(b3);
            if (this.I) {
                this.I = false;
                Log.i(b, "Card Data Completed - Requesting MSR");
                this.B.clearData();
                requestMSR();
            }
            if (this.J) {
                this.J = false;
                Log.i(b, "EMV Transaction Completed - Requesting Transaction EMV Data");
                this.B.clearData();
                clearEMVData();
                requestTransactionData();
            }
            if (this.K) {
                this.K = false;
                Log.i(b, "Power Up Down Reset IIC Completed - Requesting ATR");
                requestATR();
            }
        }
        Log.i(b, "PPSCRADevice processCardStatusResponse - Sending Card Status");
        sendDeviceEvent(21, b2, -1, this.C);
    }

    private void z(byte[] bArr) {
        if (bArr.length < 3) {
            Log.w(b, "PPSCRADevice processCardDataResponse Unexpected Data Length - Expecting: 3 or more, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        byte b3 = bArr[2];
        if (bArr.length < 4) {
            Log.w(b, "PPSCRADevice processCardDataResponse Data Length not found, Received:" + bArr.length);
            return;
        }
        int i = bArr[3] + 4;
        if (bArr.length < i) {
            Log.w(b, "PPSCRADevice processCardDataResponse Unexpected Data Length - Expecting: " + i + ", Received:" + bArr.length);
            return;
        }
        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, i);
        if (b2 == 64) {
            this.B.EncryptedPANExpDateStatus = b3;
            if (b3 == 0) {
                this.B.EncryptedPANExpDate = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
                return;
            }
            return;
        }
        if (b2 == 65) {
            this.B.PINPADSerialNumber = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
            return;
        }
        if (b2 == 99) {
            this.B.KSN = Arrays.copyOfRange(bArrCopyOfRange, 4, 14);
            this.B.MagnePrintStatus = Arrays.copyOfRange(bArrCopyOfRange, 14, bArrCopyOfRange.length);
            return;
        }
        if (b2 != 100) {
            switch (b2) {
                case 1:
                    this.B.Track1Status = b3;
                    if (b3 == 0) {
                        this.B.Track1 = PPSCRACommon.getTextString(bArrCopyOfRange, 4);
                    }
                    break;
                case 2:
                    this.B.Track2Status = b3;
                    if (b3 == 0) {
                        this.B.Track2 = PPSCRACommon.getTextString(bArrCopyOfRange, 4);
                    }
                    break;
                case 3:
                    this.B.Track3Status = b3;
                    if (b3 == 0) {
                        this.B.Track3 = PPSCRACommon.getTextString(bArrCopyOfRange, 4);
                    }
                    break;
                case 4:
                    this.B.EncryptedTrack1Status = b3;
                    if (b3 == 0) {
                        this.B.EncryptedTrack1 = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
                    }
                    break;
                case 5:
                    this.B.EncryptedTrack2Status = b3;
                    if (b3 == 0) {
                        this.B.EncryptedTrack2 = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
                    }
                    break;
                case 6:
                    this.B.EncryptedTrack3Status = b3;
                    if (b3 == 0) {
                        this.B.EncryptedTrack3 = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
                    }
                    break;
                case 7:
                    this.B.KSNMagnePrintStatus = b3;
                    if (b3 == 0) {
                        this.B.MagnePrint = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
                    }
                    break;
            }
            return;
        }
        this.B.CBCMAC = Arrays.copyOfRange(bArrCopyOfRange, 4, bArrCopyOfRange.length);
    }

    private void A(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processPINResponse Unexpected Data Length - Expecting: 2 or more, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        this.F.clearData();
        this.F.setStatus(b2);
        if (bArr.length >= 20) {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 2, 12);
            byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArr, 12, 20);
            this.F.setPINKSN(bArrCopyOfRange);
            this.F.setEncryptedPINBlock(bArrCopyOfRange2);
        }
        sendDeviceEvent(53, b2, -1, this.F);
    }

    private void B(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processUserSelectionResponse Unexpected Data Length - Expecting: 3, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        this.A = (byte) 0;
        if (b2 != 0) {
            Log.w(b, "PPSCRADevice processUserSelectionResponse Operation Status=0x" + String.format("%02X ", Byte.valueOf(b2)));
            sendDeviceEvent(63, b2, -1, null);
            return;
        }
        if (bArr.length < 3) {
            Log.w(b, "PPSCRADevice processUserSelectionResponse Operation Status=OK, Unexpected Data Length - Expecting: 3, Received:" + bArr.length);
            return;
        }
        byte b3 = bArr[2];
        this.A = b3;
        sendDeviceEvent(63, b2, b3, null);
    }

    private void C(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processDisplayMessageDoneResponse Unexpected Data Length - Expecting: 21, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        if (b2 != 0) {
            Log.w(b, "PPSCRADevice processDisplayMessageDoneResponse Operation Status=0x" + String.format("%02X ", Byte.valueOf(b2)));
            sendDeviceEvent(51, b2, 0, null);
            return;
        }
        sendDeviceEvent(51, b2, 0, null);
    }

    private void h() {
        this.n = (short) 0;
        this.o = (short) 0;
        this.p = (short) 0;
        this.q = (byte) 0;
        this.r = null;
    }

    private void a(byte b2) {
        if (b2 == -95) {
            E(this.r);
        }
        if (b2 == -85) {
            J(this.r);
            return;
        }
        if (b2 == 0) {
            D(this.r);
            return;
        }
        switch (b2) {
            case -92:
                F(this.r);
                break;
            case -91:
                G(this.r);
                break;
            case -90:
                H(this.r);
                break;
            case -89:
                I(this.r);
                break;
        }
    }

    private void D(byte[] bArr) {
        this.z = bArr;
        sendDeviceEvent(64, 0, 0, bArr);
    }

    private void E(byte[] bArr) {
        this.t = bArr;
        sendDeviceEvent(73, 0, 0, bArr);
    }

    private void F(byte[] bArr) {
        this.u = bArr;
        sendDeviceEvent(71, 0, 0, bArr);
    }

    private void G(byte[] bArr) {
        this.w = bArr;
        sendDeviceEvent(74, 0, 0, bArr);
    }

    private void H(byte[] bArr) {
        this.y = bArr;
        sendDeviceEvent(76, 0, 0, bArr);
    }

    private void I(byte[] bArr) {
        this.x = bArr;
        sendDeviceEvent(75, 0, 0, bArr);
    }

    private void J(byte[] bArr) {
        sendDeviceEvent(72, 0, 0, bArr);
    }

    private void K(byte[] bArr) {
        if (bArr.length < 2) {
            Log.w(b, "PPSCRADevice processSignatureCaptureStateResponse Unexpected Data Length - Expecting at least 2, Received:" + bArr.length);
            return;
        }
        sendDeviceEvent(61, bArr[1], bArr.length >= 5 ? (bArr[3] & 255) + (bArr[4] << 8) : 0, null);
    }

    private void L(byte[] bArr) {
        if (bArr.length < 3) {
            Log.w(b, "PPSCRADevice processSendBigBlockDataToHostResponse Unexpected Data Length - Expecting: 3 or more, Received:" + bArr.length);
            return;
        }
        byte b2 = bArr[1];
        byte b3 = bArr[2];
        if (b3 == 0) {
            if (bArr.length < 6) {
                Log.w(b, "PPSCRADevice processSendBigBlockDataToHostResponse Block #0 Unexpected Data Length - Expecting: 6, Received:" + bArr.length);
                return;
            }
            h();
            byte b4 = bArr[4];
            byte b5 = bArr[5];
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
            byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
            byteBufferAllocate.put(b4);
            byteBufferAllocate.put(b5);
            this.n = byteBufferAllocate.getShort(0);
            String str = b;
            Log.i(str, "processSendBigBlockDataToHostResponse LowByte=0x" + String.format("%02X", Byte.valueOf(b4)) + ", HiByte=0x" + String.format("%02X", Byte.valueOf(b5)));
            StringBuilder sb = new StringBuilder();
            sb.append("processSendBigBlockDataToHostResponse Expected Block Data Length=0x");
            sb.append(String.format("%02X", Short.valueOf(this.n)));
            Log.i(str, sb.toString());
            this.r = new byte[this.n];
            this.q = (byte) 1;
            return;
        }
        if (b3 > 0 && b3 < 99) {
            if (this.q == b3) {
                if (bArr.length < 5) {
                    Log.w(b, "PPSCRADevice processSendBigBlockDataToHostResponse Block #" + ((int) b3) + " Unexpected Data Length - Expecting: 5 or more, Received:" + bArr.length);
                    return;
                }
                byte b6 = bArr[3];
                for (int i = 0; i < b6; i++) {
                    byte[] bArr2 = this.r;
                    short s = this.p;
                    this.p = (short) (s + 1);
                    bArr2[s] = bArr[i + 4];
                }
                this.q = (byte) (this.q + 1);
                return;
            }
            Log.w(b, "processSendBigBlockDataToHostResponse Unexpected Block Number=0x " + String.format("%02X", Byte.valueOf(b3)));
            return;
        }
        if (b3 == 99) {
            if (bArr.length < 3) {
                Log.w(b, "PPSCRADevice processSendBigBlockDataToHostResponse Block #99 Unexpected Data Length - Expecting: 3, Received:" + bArr.length);
                return;
            }
            String str2 = b;
            Log.i(str2, "processSendBigBlockDataToHostResponse Number of Bytes Received=0x" + String.format("%02X", Short.valueOf(this.p)));
            short s2 = this.n;
            if (s2 > 0 && this.p == s2) {
                Log.i(str2, "processSendBigBlockDataToHostResponse BlockDataLength=" + this.r.length);
                a(b2);
            } else {
                Log.w(str2, "processSendBigBlockDataToHostResponse Number of Bytes Expected=0x" + String.format("%02X", Short.valueOf(this.n)));
            }
            this.q = (byte) 99;
        }
    }
}

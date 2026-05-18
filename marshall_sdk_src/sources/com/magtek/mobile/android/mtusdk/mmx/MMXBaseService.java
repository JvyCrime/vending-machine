package com.magtek.mobile.android.mtusdk.mmx;

import android.content.Context;
import android.util.Log;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MMXBaseService implements IMMXService, Runnable {
    private static final String TAG = "MMXBaseService";
    protected Context mContext;
    protected final int OUTPUT_REPORT_SIZE = 65;
    protected final int INPUT_REPORT_SIZE = 64;
    protected final int INPUT_REPORT_POLLING_INTERVAL = 1;
    protected final int INPUT_REPORT_POLLING_PAUSE = 1;
    protected final byte PACKET_TYPE_SINGLE_DATA = 0;
    protected final byte PACKET_TYPE_START_DATA = 1;
    protected final byte PACKET_TYPE_CONTINUE_DATA = 2;
    protected final byte PACKET_TYPE_END_DATA = 3;
    protected final byte PACKET_TYPE_CANCEL = 4;
    protected int START_PAYLOAD_SIZE = 59;
    protected int PACKET_CONTINUE_DATA_SIZE = 61;
    protected int END_DATA_SIZE = 62;
    protected int SINGLE_DATA_SIZE = 62;
    protected IMMXServiceAdapter mServiceAdapter = null;
    protected String mAddress = "";
    protected MMXConnectionState mConnectionState = MMXConnectionState.Disconnected;
    protected ArrayList<byte[]> mDataList = null;
    protected MMXMessageReceiver mMessageReceiver = new MMXMessageReceiver();

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void connect() {
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void disconnect() {
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void sendData(byte[] bArr) {
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void initialize(Context context, IMMXServiceAdapter iMMXServiceAdapter) {
        this.mContext = context;
        this.mServiceAdapter = iMMXServiceAdapter;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void setAddress(String str) {
        this.mAddress = str;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public MMXConnectionState getState() {
        return this.mConnectionState;
    }

    protected void raiseOnConnectionStateChanged(MMXConnectionState mMXConnectionState) {
        IMMXServiceAdapter iMMXServiceAdapter = this.mServiceAdapter;
        if (iMMXServiceAdapter != null) {
            try {
                iMMXServiceAdapter.OnConnectionStateChanged(mMXConnectionState);
            } catch (Exception unused) {
            }
        }
    }

    protected void raiseOnDataReceived(byte[] bArr) {
        IMMXServiceAdapter iMMXServiceAdapter = this.mServiceAdapter;
        if (iMMXServiceAdapter != null) {
            try {
                iMMXServiceAdapter.OnDataReceived(bArr);
            } catch (Exception unused) {
            }
        }
    }

    protected void raiseOnDataProgress(int i) {
        IMMXServiceAdapter iMMXServiceAdapter = this.mServiceAdapter;
        if (iMMXServiceAdapter != null) {
            try {
                iMMXServiceAdapter.OnSendDataProgress(i);
            } catch (Exception unused) {
            }
        }
    }

    protected void setConnectionState(MMXConnectionState mMXConnectionState) {
        if (mMXConnectionState != this.mConnectionState) {
            this.mConnectionState = mMXConnectionState;
            raiseOnConnectionStateChanged(mMXConnectionState);
        }
    }

    protected void setDataReceived(byte[] bArr) {
        raiseOnDataReceived(bArr);
    }

    protected List<byte[]> getPackets(byte[] bArr) {
        if (bArr.length > this.SINGLE_DATA_SIZE) {
            return getMultiplePackets(bArr);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(getSinglePacket(bArr));
        return arrayList;
    }

    protected byte[] getSinglePacket(byte[] bArr) {
        byte[] bArr2 = new byte[bArr != null ? bArr.length + 2 : 2];
        bArr2[0] = 0;
        bArr2[1] = (byte) bArr.length;
        System.arraycopy(bArr, 0, bArr2, 2, bArr.length);
        return bArr2;
    }

    protected List<byte[]> getMultiplePackets(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        byte[] bArr2 = new byte[this.START_PAYLOAD_SIZE + 5];
        bArr2[0] = 1;
        byte[] lengthArray = getLengthArray(4, bArr.length);
        bArr2[1] = lengthArray[0];
        bArr2[2] = lengthArray[1];
        bArr2[3] = lengthArray[2];
        bArr2[4] = lengthArray[3];
        System.arraycopy(bArr, 0, bArr2, 5, this.START_PAYLOAD_SIZE);
        arrayList.add(bArr2);
        int i = this.START_PAYLOAD_SIZE;
        short s = 1;
        while (i < bArr.length - this.END_DATA_SIZE) {
            byte[] bArr3 = new byte[this.PACKET_CONTINUE_DATA_SIZE + 3];
            byte[] lengthArray2 = getLengthArray(2, s);
            bArr3[0] = 2;
            bArr3[1] = lengthArray2[0];
            bArr3[2] = lengthArray2[1];
            System.arraycopy(bArr, i, bArr3, 3, this.PACKET_CONTINUE_DATA_SIZE);
            arrayList.add(bArr3);
            s = (short) (s + 1);
            i += this.PACKET_CONTINUE_DATA_SIZE;
        }
        byte[] bArr4 = new byte[(bArr.length + 2) - i];
        bArr4[0] = 3;
        bArr4[1] = (byte) (bArr.length - i);
        System.arraycopy(bArr, i, bArr4, 2, bArr.length - i);
        arrayList.add(bArr4);
        return arrayList;
    }

    protected byte[] getLengthArray(int i, int i2) {
        byte[] bArr = new byte[i];
        int i3 = i;
        for (int i4 = 0; i4 < i; i4++) {
            i3--;
            bArr[i4] = (byte) ((i2 >> (i3 * 8)) & 255);
        }
        return bArr;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.magtek.mobile.android.mtusdk.mmx.MMXBaseService$1] */
    protected void startProcessInputData1(byte[] bArr) {
        new Thread(new Runnable() { // from class: com.magtek.mobile.android.mtusdk.mmx.MMXBaseService.1
            byte[] mData = null;

            @Override // java.lang.Runnable
            public void run() {
                MMXBaseService.this.processInputData(this.mData);
            }

            public Runnable init(byte[] bArr2) {
                if (bArr2 != null && bArr2.length > 0) {
                    this.mData = Arrays.copyOf(bArr2, bArr2.length);
                }
                return this;
            }
        }.init(bArr)).start();
    }

    protected void startProcessInputData(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        processInputData(Arrays.copyOf(bArr, bArr.length));
    }

    protected void processInputData(byte[] bArr) {
        if ((bArr != null ? bArr.length : 0) > 0) {
            try {
                Log.i(TAG, "Received=" + TLVParser.getHexString(bArr));
                while (bArr != null) {
                    bArr = parseInputReport(bArr);
                    if (bArr == null || bArr.length < 3) {
                        return;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "ProcessInputReport Exception: " + e.toString());
            }
        }
    }

    protected byte[] parseInputReport(byte[] bArr) {
        String str = TAG;
        Log.i(str, "Parsing=" + TLVParser.getHexString(bArr));
        byte[] bArr2 = null;
        if (bArr[0] == 0) {
            Log.i(str, "Parsing Single Packet");
            int i = bArr[1];
            if (i > 0) {
                byte[] bArr3 = new byte[i];
                System.arraycopy(bArr, 2, bArr3, 0, i);
                setDataReceived(bArr3);
            }
        } else if (bArr[0] == 1) {
            Log.i(str, "Parsing First Packet");
            this.mDataList = new ArrayList<>();
            int length = bArr.length;
            int i2 = this.START_PAYLOAD_SIZE;
            if (length >= i2 + 5) {
                byte[] bArr4 = new byte[i2];
                System.arraycopy(bArr, 5, bArr4, 0, i2);
                this.mDataList.add(0, bArr4);
            }
            int length2 = bArr.length - 64;
            if (length2 > 0) {
                bArr2 = new byte[length2];
                System.arraycopy(bArr, 64, bArr2, 0, length2);
            }
        } else if (bArr[0] == 2) {
            Log.i(str, "Parsing next packet");
            if (bArr.length >= this.PACKET_CONTINUE_DATA_SIZE + 3) {
                short s = (short) ((bArr[1] << 8) + bArr[2]);
                Log.i(str, "Parsing Packet " + ((int) s));
                int i3 = this.PACKET_CONTINUE_DATA_SIZE;
                byte[] bArr5 = new byte[i3];
                System.arraycopy(bArr, 3, bArr5, 0, i3);
                this.mDataList.add(s, bArr5);
            }
            int length3 = bArr.length - 64;
            if (length3 > 0) {
                bArr2 = new byte[length3];
                System.arraycopy(bArr, 64, bArr2, 0, length3);
            }
        } else if (bArr[0] == 3) {
            Log.i(str, "Parsing Last Packet");
            short size = (short) this.mDataList.size();
            int i4 = bArr[1];
            byte[] bArr6 = new byte[i4];
            System.arraycopy(bArr, 2, bArr6, 0, i4);
            this.mDataList.add(size, bArr6);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i5 = 0; i5 < this.mDataList.size(); i5++) {
                try {
                    byteArrayOutputStream.write(this.mDataList.get(i5));
                } catch (Exception unused) {
                }
            }
            setDataReceived(byteArrayOutputStream.toByteArray());
        }
        Log.i(TAG, "Remaining=" + TLVParser.getHexString(bArr2));
        return bArr2;
    }
}

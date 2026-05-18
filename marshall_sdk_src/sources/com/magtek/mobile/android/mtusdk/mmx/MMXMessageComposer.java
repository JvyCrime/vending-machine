package com.magtek.mobile.android.mtusdk.mmx;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MMXMessageComposer {
    int END_DATA_SIZE;
    int PACKET_CONTINUE_DATA_SIZE;
    int PacketSize;
    int SINGLE_DATA_SIZE;
    int START_PAYLOAD_SIZE;
    protected IMMXService mService;
    final byte PACKET_TYPE_SINGLE_DATA = 0;
    final byte PACKET_TYPE_START_DATA = 1;
    final byte PACKET_TYPE_CONTINUE_DATA = 2;
    final byte PACKET_TYPE_END_DATA = 3;
    final byte PACKET_TYPE_CANCEL = 4;
    byte[] BytesLeft = null;

    public byte[] ParseReport(byte[] bArr, byte[] bArr2) {
        return null;
    }

    protected void SendReceived(byte[] bArr) {
    }

    public MMXMessageComposer(IMMXService iMMXService) {
        this.START_PAYLOAD_SIZE = 59;
        this.PACKET_CONTINUE_DATA_SIZE = 61;
        this.END_DATA_SIZE = 62;
        this.SINGLE_DATA_SIZE = 62;
        this.PacketSize = 64;
        this.mService = iMMXService;
        if (iMMXService != null) {
            this.PacketSize = 64;
            this.START_PAYLOAD_SIZE = 64 - 5;
            this.PACKET_CONTINUE_DATA_SIZE = 64 - 3;
            this.END_DATA_SIZE = 64 - 2;
            this.SINGLE_DATA_SIZE = 64 - 2;
        }
    }

    public void SendPayload(byte[] bArr) {
        List<byte[]> listBuildPackages = BuildPackages(bArr);
        for (int i = 0; i < listBuildPackages.size(); i++) {
            IMMXService iMMXService = this.mService;
            if (iMMXService != null) {
                iMMXService.sendData(listBuildPackages.get(i));
            }
        }
    }

    private byte[] BuildSingleMessage(byte[] bArr) {
        byte[] bArr2 = new byte[bArr != null ? bArr.length + 2 : 2];
        bArr2[0] = 0;
        bArr2[1] = (byte) bArr.length;
        System.arraycopy(bArr, 0, bArr2, 2, bArr.length);
        return bArr2;
    }

    private List<byte[]> BuildBigBlocks(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        byte[] bArr2 = new byte[bArr.length + 5];
        bArr2[0] = 1;
        byte[] nByteLengthArray = getNByteLengthArray(4, bArr.length);
        bArr2[1] = nByteLengthArray[0];
        bArr2[2] = nByteLengthArray[1];
        bArr2[3] = nByteLengthArray[2];
        bArr2[4] = nByteLengthArray[3];
        System.arraycopy(bArr, 0, bArr2, 5, this.START_PAYLOAD_SIZE);
        arrayList.add(bArr2);
        int i = this.START_PAYLOAD_SIZE;
        short s = 1;
        while (i < bArr.length - this.END_DATA_SIZE) {
            byte[] bArr3 = new byte[bArr.length + 3];
            byte[] twoByteLengthArray = getTwoByteLengthArray(s);
            bArr3[1] = twoByteLengthArray[0];
            bArr3[2] = twoByteLengthArray[1];
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

    private List<byte[]> BuildPackages(byte[] bArr) {
        if (bArr.length > this.SINGLE_DATA_SIZE) {
            return BuildBigBlocks(bArr);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(BuildSingleMessage(bArr));
        return arrayList;
    }

    private static byte[] getTwoByteLengthArray(int i) {
        byte[] bArr = new byte[2];
        int i2 = 2;
        for (int i3 = 0; i3 < 2; i3++) {
            i2--;
            bArr[i3] = (byte) ((i >> (i2 * 8)) & 255);
        }
        return bArr;
    }

    private static byte[] getNByteLengthArray(int i, int i2) {
        byte[] bArr = new byte[i];
        int i3 = i;
        for (int i4 = 0; i4 < i; i4++) {
            i3--;
            bArr[i4] = (byte) ((i2 >> (i3 * 8)) & 255);
        }
        return bArr;
    }
}

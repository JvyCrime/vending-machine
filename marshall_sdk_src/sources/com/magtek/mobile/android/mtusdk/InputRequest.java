package com.magtek.mobile.android.mtusdk;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class InputRequest {
    public static byte INPUT_STATUS_CANCELLED = 1;
    public static byte INPUT_STATUS_COMPLETED = 0;
    public static byte INPUT_STATUS_TIMED_OUT = 2;
    public static byte INPUT_TYPE_APPLICATION = 0;
    public static byte INPUT_TYPE_LANGUAGE = 1;
    private List<String> mSelectionList;
    private byte mTimeout;
    private String mTitle;
    private byte mType;

    public byte Type() {
        return this.mType;
    }

    public byte Timeout() {
        return this.mTimeout;
    }

    public String Title() {
        return this.mTitle;
    }

    public List<String> SelectionList() {
        return this.mSelectionList;
    }

    public InputRequest() {
    }

    public InputRequest(byte[] bArr) {
        init(bArr);
    }

    public void init(byte[] bArr) {
        if (bArr == null || bArr.length <= 2) {
            return;
        }
        this.mType = bArr[0];
        this.mTimeout = bArr[1];
        List<String> selectionList = getSelectionList(bArr, 2);
        if (selectionList.size() > 1) {
            this.mTitle = selectionList.get(0);
            selectionList.remove(0);
            this.mSelectionList = selectionList;
        }
    }

    protected List<String> getSelectionList(byte[] bArr, int i) {
        int length;
        ArrayList arrayList = new ArrayList();
        if (bArr != null && (length = bArr.length) >= i) {
            int i2 = i;
            while (i < length) {
                if (bArr[i] == 0) {
                    int i3 = i - i2;
                    if (i3 >= 0) {
                        byte[] bArr2 = new byte[i3];
                        System.arraycopy(bArr, i2, bArr2, 0, i3);
                        arrayList.add(new String(bArr2));
                    }
                    i2 = i + 1;
                }
                i++;
            }
        }
        return arrayList;
    }
}

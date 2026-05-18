package com.felhr.usbserial;

import android.util.Log;
import com.felhr.utils.HexData;

/* JADX INFO: loaded from: classes.dex */
public class UsbSerialDebugger {
    private static final String CLASS_ID = "UsbSerialDebugger";
    public static final String ENCODING = "UTF-8";

    private UsbSerialDebugger() {
    }

    public static void printLogGet(byte[] bArr, boolean z) {
        if (!z) {
            Log.i(CLASS_ID, "Data obtained from write buffer: " + new String(bArr));
            return;
        }
        String str = CLASS_ID;
        Log.i(str, "Data obtained from write buffer: " + new String(bArr));
        Log.i(str, "Raw data from write buffer: " + HexData.hexToString(bArr));
        Log.i(str, "Number of bytes obtained from write buffer: " + bArr.length);
    }

    public static void printLogPut(byte[] bArr, boolean z) {
        if (!z) {
            Log.i(CLASS_ID, "Data obtained pushed to write buffer: " + new String(bArr));
            return;
        }
        String str = CLASS_ID;
        Log.i(str, "Data obtained pushed to write buffer: " + new String(bArr));
        Log.i(str, "Raw data pushed to write buffer: " + HexData.hexToString(bArr));
        Log.i(str, "Number of bytes pushed from write buffer: " + bArr.length);
    }

    public static void printReadLogGet(byte[] bArr, boolean z) {
        if (!z) {
            Log.i(CLASS_ID, "Data obtained from Read buffer: " + new String(bArr));
            return;
        }
        String str = CLASS_ID;
        Log.i(str, "Data obtained from Read buffer: " + new String(bArr));
        Log.i(str, "Raw data from Read buffer: " + HexData.hexToString(bArr));
        Log.i(str, "Number of bytes obtained from Read buffer: " + bArr.length);
    }

    public static void printReadLogPut(byte[] bArr, boolean z) {
        if (!z) {
            Log.i(CLASS_ID, "Data obtained pushed to read buffer: " + new String(bArr));
            return;
        }
        String str = CLASS_ID;
        Log.i(str, "Data obtained pushed to read buffer: " + new String(bArr));
        Log.i(str, "Raw data pushed to read buffer: " + HexData.hexToString(bArr));
        Log.i(str, "Number of bytes pushed from read buffer: " + bArr.length);
    }
}

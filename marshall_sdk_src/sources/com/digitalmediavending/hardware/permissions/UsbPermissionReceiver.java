package com.digitalmediavending.hardware.permissions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.digitalmediavending.hardware.ServiceTestActivity;

/* JADX INFO: loaded from: classes.dex */
public class UsbPermissionReceiver extends BroadcastReceiver {
    private final Context mContext;

    public UsbPermissionReceiver(Context mContext) {
        this.mContext = mContext;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Context context2 = this.mContext;
        if (context2 == null || ((ServiceTestActivity) context2).getPermissionChecker() == null) {
            return;
        }
        ((ServiceTestActivity) this.mContext).getPermissionChecker().handleUsbAccessResult(intent.getAction(), intent);
    }
}

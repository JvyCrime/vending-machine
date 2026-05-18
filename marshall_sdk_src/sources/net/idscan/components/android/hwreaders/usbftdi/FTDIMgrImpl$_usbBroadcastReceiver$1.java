package net.idscan.components.android.hwreaders.usbftdi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FTDIMgr.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"net/idscan/components/android/hwreaders/usbftdi/FTDIMgrImpl$_usbBroadcastReceiver$1", "Landroid/content/BroadcastReceiver;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
public final class FTDIMgrImpl$_usbBroadcastReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ FTDIMgrImpl this$0;

    FTDIMgrImpl$_usbBroadcastReceiver$1(FTDIMgrImpl fTDIMgrImpl) {
        this.this$0 = fTDIMgrImpl;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String action = intent.getAction();
        if (action == null) {
            return;
        }
        int iHashCode = action.hashCode();
        if (iHashCode != -2114103349) {
            if (iHashCode != -1608292967) {
                if (iHashCode != 1650878742 || !action.equals("com.ftdi.j2xx")) {
                    return;
                }
            } else if (!action.equals("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
                return;
            }
        } else if (!action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
            return;
        }
        this.this$0._handler.post(new Runnable() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIMgrImpl$_usbBroadcastReceiver$1$onReceive$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.this$0.updateDeviceList();
            }
        });
    }
}

package net.idscan.components.android.hwreaders.usbftdi;

import android.content.Context;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.idscan.components.android.hwreaders.common.DocumentData;
import net.idscan.components.android.hwreaders.common.DocumentType;
import net.idscan.components.android.hwreaders.common.Observable;

/* JADX INFO: compiled from: FTDIMgr.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fJ\b\u0010\u001a\u001a\u00020\u001bH&J\b\u0010\u001c\u001a\u00020\u001bH&J\u0016\u0010\u001d\u001a\u00020\u001b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0014R\u0018\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0014¨\u0006 "}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgr;", "", "acceptableDocumentTypes", "", "Lnet/idscan/components/android/hwreaders/common/DocumentType;", "getAcceptableDocumentTypes", "()Ljava/util/Set;", "deviceList", "", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "getDeviceList", "()Ljava/util/List;", "isAvailable", "", "()Z", "isEnabled", "observableData", "Lnet/idscan/components/android/hwreaders/common/Observable;", "Lnet/idscan/components/android/hwreaders/common/DocumentData;", "getObservableData", "()Lnet/idscan/components/android/hwreaders/common/Observable;", "observableDeviceEvent", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent;", "getObservableDeviceEvent", "observableState", "getObservableState", "disable", "", "enable", "setAcceptableDocumentTypes", "types", "Companion", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
public interface FTDIMgr {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void disable();

    void enable();

    Set<DocumentType> getAcceptableDocumentTypes();

    List<FTDIDevice> getDeviceList();

    Observable<DocumentData> getObservableData();

    Observable<DeviceEvent> getObservableDeviceEvent();

    Observable<Boolean> getObservableState();

    boolean isAvailable();

    /* JADX INFO: renamed from: isEnabled */
    boolean getIsEnabled();

    void setAcceptableDocumentTypes(Set<? extends DocumentType> types);

    /* JADX INFO: compiled from: FTDIMgr.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgr$Companion;", "", "()V", "create", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgr;", "context", "Landroid/content/Context;", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final FTDIMgr create(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return new FTDIMgrImpl(context);
        }
    }
}

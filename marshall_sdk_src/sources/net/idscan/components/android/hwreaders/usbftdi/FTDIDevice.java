package net.idscan.components.android.hwreaders.usbftdi;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Set;
import kotlin.Metadata;
import net.idscan.components.android.hwreaders.common.DocumentType;

/* JADX INFO: compiled from: FTDIDevice.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001 J\b\u0010\u001b\u001a\u00020\u001cH&J\b\u0010\u001d\u001a\u00020\u001cH&J\u0016\u0010\u001e\u001a\u00020\u001c2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000eR\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u000e¨\u0006!"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "", "acceptableDocumentTypes", "", "Lnet/idscan/components/android/hwreaders/common/DocumentType;", "getAcceptableDocumentTypes", "()Ljava/util/Set;", "id", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceId;", "getId", "()Lnet/idscan/components/android/hwreaders/usbftdi/DeviceId;", FirebaseAnalytics.Param.LOCATION, "", "getLocation", "()I", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "getName", "()Ljava/lang/String;", "pid", "getPid", "state", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice$State;", "getState", "()Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice$State;", "vid", "getVid", "connect", "", "disconnect", "setAcceptableDocumentTypes", "types", "State", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
public interface FTDIDevice {

    /* JADX INFO: compiled from: FTDIDevice.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice$State;", "", "(Ljava/lang/String;I)V", "Connecting", "Connected", "Disconnecting", "Disconnected", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
    public enum State {
        Connecting,
        Connected,
        Disconnecting,
        Disconnected
    }

    void connect();

    void disconnect();

    Set<DocumentType> getAcceptableDocumentTypes();

    DeviceId getId();

    int getLocation();

    String getName();

    int getPid();

    State getState();

    int getVid();

    void setAcceptableDocumentTypes(Set<? extends DocumentType> types);
}

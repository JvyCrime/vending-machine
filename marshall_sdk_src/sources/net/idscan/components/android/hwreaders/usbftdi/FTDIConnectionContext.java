package net.idscan.components.android.hwreaders.usbftdi;

import com.ftdi.j2xx.FT_Device;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.idscan.components.android.hwreaders.common.DocumentData;

/* JADX INFO: compiled from: FTDIConnection.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\u0002\u0010\rJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0005HÆ\u0003J\u0015\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nHÆ\u0003JI\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0010R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010¨\u0006\u001f"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIConnectionContext;", "", "device", "Lcom/ftdi/j2xx/FT_Device;", "state", "Lkotlin/Function0;", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIConnectionState;", "isClosed", "", "postData", "Lkotlin/Function1;", "Lnet/idscan/components/android/hwreaders/common/DocumentData;", "", "(Lcom/ftdi/j2xx/FT_Device;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "getDevice", "()Lcom/ftdi/j2xx/FT_Device;", "()Lkotlin/jvm/functions/Function0;", "getPostData", "()Lkotlin/jvm/functions/Function1;", "getState", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
public final /* data */ class FTDIConnectionContext {
    private final FT_Device device;
    private final Function0<Boolean> isClosed;
    private final Function1<DocumentData, Unit> postData;
    private final Function0<FTDIConnectionState> state;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FTDIConnectionContext copy$default(FTDIConnectionContext fTDIConnectionContext, FT_Device fT_Device, Function0 function0, Function0 function02, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            fT_Device = fTDIConnectionContext.device;
        }
        if ((i & 2) != 0) {
            function0 = fTDIConnectionContext.state;
        }
        if ((i & 4) != 0) {
            function02 = fTDIConnectionContext.isClosed;
        }
        if ((i & 8) != 0) {
            function1 = fTDIConnectionContext.postData;
        }
        return fTDIConnectionContext.copy(fT_Device, function0, function02, function1);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FT_Device getDevice() {
        return this.device;
    }

    public final Function0<FTDIConnectionState> component2() {
        return this.state;
    }

    public final Function0<Boolean> component3() {
        return this.isClosed;
    }

    public final Function1<DocumentData, Unit> component4() {
        return this.postData;
    }

    public final FTDIConnectionContext copy(FT_Device device, Function0<? extends FTDIConnectionState> state, Function0<Boolean> isClosed, Function1<? super DocumentData, Unit> postData) {
        Intrinsics.checkNotNullParameter(device, "device");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(isClosed, "isClosed");
        Intrinsics.checkNotNullParameter(postData, "postData");
        return new FTDIConnectionContext(device, state, isClosed, postData);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FTDIConnectionContext)) {
            return false;
        }
        FTDIConnectionContext fTDIConnectionContext = (FTDIConnectionContext) other;
        return Intrinsics.areEqual(this.device, fTDIConnectionContext.device) && Intrinsics.areEqual(this.state, fTDIConnectionContext.state) && Intrinsics.areEqual(this.isClosed, fTDIConnectionContext.isClosed) && Intrinsics.areEqual(this.postData, fTDIConnectionContext.postData);
    }

    public int hashCode() {
        FT_Device fT_Device = this.device;
        int iHashCode = (fT_Device != null ? fT_Device.hashCode() : 0) * 31;
        Function0<FTDIConnectionState> function0 = this.state;
        int iHashCode2 = (iHashCode + (function0 != null ? function0.hashCode() : 0)) * 31;
        Function0<Boolean> function02 = this.isClosed;
        int iHashCode3 = (iHashCode2 + (function02 != null ? function02.hashCode() : 0)) * 31;
        Function1<DocumentData, Unit> function1 = this.postData;
        return iHashCode3 + (function1 != null ? function1.hashCode() : 0);
    }

    public String toString() {
        return "FTDIConnectionContext(device=" + this.device + ", state=" + this.state + ", isClosed=" + this.isClosed + ", postData=" + this.postData + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FTDIConnectionContext(FT_Device device, Function0<? extends FTDIConnectionState> state, Function0<Boolean> isClosed, Function1<? super DocumentData, Unit> postData) {
        Intrinsics.checkNotNullParameter(device, "device");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(isClosed, "isClosed");
        Intrinsics.checkNotNullParameter(postData, "postData");
        this.device = device;
        this.state = state;
        this.isClosed = isClosed;
        this.postData = postData;
    }

    public final FT_Device getDevice() {
        return this.device;
    }

    public final Function0<FTDIConnectionState> getState() {
        return this.state;
    }

    public final Function0<Boolean> isClosed() {
        return this.isClosed;
    }

    public final Function1<DocumentData, Unit> getPostData() {
        return this.postData;
    }
}

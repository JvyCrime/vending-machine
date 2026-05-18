package net.idscan.components.android.hwreaders.usbftdi;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FTDIMgr.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent;", "", "()V", "OnAttached", "OnChanged", "OnDetached", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent$OnAttached;", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent$OnDetached;", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent$OnChanged;", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
public abstract class DeviceEvent {

    /* JADX INFO: compiled from: FTDIMgr.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent$OnAttached;", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent;", "device", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "(Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;)V", "getDevice", "()Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
    public static final /* data */ class OnAttached extends DeviceEvent {
        private final FTDIDevice device;

        public static /* synthetic */ OnAttached copy$default(OnAttached onAttached, FTDIDevice fTDIDevice, int i, Object obj) {
            if ((i & 1) != 0) {
                fTDIDevice = onAttached.device;
            }
            return onAttached.copy(fTDIDevice);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FTDIDevice getDevice() {
            return this.device;
        }

        public final OnAttached copy(FTDIDevice device) {
            Intrinsics.checkNotNullParameter(device, "device");
            return new OnAttached(device);
        }

        public boolean equals(Object other) {
            if (this != other) {
                return (other instanceof OnAttached) && Intrinsics.areEqual(this.device, ((OnAttached) other).device);
            }
            return true;
        }

        public int hashCode() {
            FTDIDevice fTDIDevice = this.device;
            if (fTDIDevice != null) {
                return fTDIDevice.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "OnAttached(device=" + this.device + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnAttached(FTDIDevice device) {
            super(null);
            Intrinsics.checkNotNullParameter(device, "device");
            this.device = device;
        }

        public final FTDIDevice getDevice() {
            return this.device;
        }
    }

    private DeviceEvent() {
    }

    public /* synthetic */ DeviceEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: FTDIMgr.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent$OnDetached;", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent;", "device", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "(Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;)V", "getDevice", "()Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
    public static final /* data */ class OnDetached extends DeviceEvent {
        private final FTDIDevice device;

        public static /* synthetic */ OnDetached copy$default(OnDetached onDetached, FTDIDevice fTDIDevice, int i, Object obj) {
            if ((i & 1) != 0) {
                fTDIDevice = onDetached.device;
            }
            return onDetached.copy(fTDIDevice);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FTDIDevice getDevice() {
            return this.device;
        }

        public final OnDetached copy(FTDIDevice device) {
            Intrinsics.checkNotNullParameter(device, "device");
            return new OnDetached(device);
        }

        public boolean equals(Object other) {
            if (this != other) {
                return (other instanceof OnDetached) && Intrinsics.areEqual(this.device, ((OnDetached) other).device);
            }
            return true;
        }

        public int hashCode() {
            FTDIDevice fTDIDevice = this.device;
            if (fTDIDevice != null) {
                return fTDIDevice.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "OnDetached(device=" + this.device + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnDetached(FTDIDevice device) {
            super(null);
            Intrinsics.checkNotNullParameter(device, "device");
            this.device = device;
        }

        public final FTDIDevice getDevice() {
            return this.device;
        }
    }

    /* JADX INFO: compiled from: FTDIMgr.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent$OnChanged;", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent;", "device", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "(Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;)V", "getDevice", "()Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
    public static final /* data */ class OnChanged extends DeviceEvent {
        private final FTDIDevice device;

        public static /* synthetic */ OnChanged copy$default(OnChanged onChanged, FTDIDevice fTDIDevice, int i, Object obj) {
            if ((i & 1) != 0) {
                fTDIDevice = onChanged.device;
            }
            return onChanged.copy(fTDIDevice);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FTDIDevice getDevice() {
            return this.device;
        }

        public final OnChanged copy(FTDIDevice device) {
            Intrinsics.checkNotNullParameter(device, "device");
            return new OnChanged(device);
        }

        public boolean equals(Object other) {
            if (this != other) {
                return (other instanceof OnChanged) && Intrinsics.areEqual(this.device, ((OnChanged) other).device);
            }
            return true;
        }

        public int hashCode() {
            FTDIDevice fTDIDevice = this.device;
            if (fTDIDevice != null) {
                return fTDIDevice.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "OnChanged(device=" + this.device + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnChanged(FTDIDevice device) {
            super(null);
            Intrinsics.checkNotNullParameter(device, "device");
            this.device = device;
        }

        public final FTDIDevice getDevice() {
            return this.device;
        }
    }
}

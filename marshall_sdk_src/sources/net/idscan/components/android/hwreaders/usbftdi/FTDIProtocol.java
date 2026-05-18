package net.idscan.components.android.hwreaders.usbftdi;

import java.util.Set;
import kotlin.Metadata;
import net.idscan.components.android.hwreaders.common.DocumentType;

/* JADX INFO: compiled from: FTDIConnection.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\b`\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&¨\u0006\n"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIProtocol;", "", "process", "", "context", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIConnectionContext;", "setupAcceptableTypes", "types", "", "Lnet/idscan/components/android/hwreaders/common/DocumentType;", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
public interface FTDIProtocol {
    void process(FTDIConnectionContext context);

    void setupAcceptableTypes(Set<? extends DocumentType> types);
}

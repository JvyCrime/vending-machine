package net.idscan.components.android.hwreaders.usbftdi;

import com.ftdi.j2xx.FT_Device;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.idscan.components.android.hwreaders.common.DocumentData;
import net.idscan.components.android.hwreaders.common.DocumentType;

/* JADX INFO: compiled from: FTDICommonProtocol.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0016\u0010\u0007\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0016¨\u0006\f"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDICommonProtocol;", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIProtocol;", "()V", "process", "", "context", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIConnectionContext;", "setupAcceptableTypes", "types", "", "Lnet/idscan/components/android/hwreaders/common/DocumentType;", "Companion", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
public final class FTDICommonProtocol implements FTDIProtocol {
    private static final Set<DocumentType> DOC_TYPE = SetsKt.setOf((Object[]) new DocumentType[]{DocumentType.PDF417, DocumentType.MAGNETIC_STRIPE});

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIProtocol
    public void setupAcceptableTypes(Set<? extends DocumentType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIProtocol
    public void process(FTDIConnectionContext context) throws InterruptedException {
        Intrinsics.checkNotNullParameter(context, "context");
        FT_Device device = context.getDevice();
        device.setBaudRate(9600);
        device.setDataCharacteristics((byte) 8, (byte) 0, (byte) 0);
        byte b = (byte) 0;
        device.setFlowControl((short) 0, b, b);
        device.purge((byte) 1);
        device.purge((byte) 2);
        device.restartInTask();
        byte[] bArr = new byte[512];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (!context.isClosed().invoke().booleanValue() && context.getDevice().isOpen()) {
            int iMin = Math.min(context.getDevice().getQueueStatus(), 512);
            if (iMin > 0) {
                int i = context.getDevice().read(bArr, iMin);
                if (i > 0) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else if (i < 0) {
                    return;
                }
            } else {
                if (iMin != 0) {
                    return;
                }
                if (byteArrayOutputStream.size() > 0) {
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.reset();
                    Function1<DocumentData, Unit> postData = context.getPostData();
                    Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                    postData.invoke(new DocumentData(bytes, DOC_TYPE));
                }
            }
            Thread.sleep(150L);
        }
    }
}

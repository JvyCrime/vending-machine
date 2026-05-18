package net.idscan.components.android.hwreaders.usbftdi;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import net.idscan.components.android.hwreaders.common.DocumentData;

/* JADX INFO: compiled from: FTDIConnection.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "p1", "Lnet/idscan/components/android/hwreaders/common/DocumentData;", "invoke"}, k = 3, mv = {1, 4, 2})
final /* synthetic */ class FTDIConnection$exec$ctx$3 extends FunctionReferenceImpl implements Function1<DocumentData, Unit> {
    FTDIConnection$exec$ctx$3(FTDIConnection fTDIConnection) {
        super(1, fTDIConnection, FTDIConnection.class, "postData", "postData(Lnet/idscan/components/android/hwreaders/common/DocumentData;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(DocumentData documentData) {
        invoke2(documentData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(DocumentData p1) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        ((FTDIConnection) this.receiver).postData(p1);
    }
}

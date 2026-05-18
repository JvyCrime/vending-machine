package net.idscan.components.android.hwreaders.usbftdi;

import android.content.Context;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FTDIMgr.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u0015\u0010\u0002\u001a\u00110\u0003¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgrImpl;", "p1", "Landroid/content/Context;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "context", "invoke"}, k = 3, mv = {1, 4, 2})
final /* synthetic */ class FTDIMgrImpl$Companion$TAG$1 extends FunctionReferenceImpl implements Function1<Context, FTDIMgrImpl> {
    public static final FTDIMgrImpl$Companion$TAG$1 INSTANCE = new FTDIMgrImpl$Companion$TAG$1();

    FTDIMgrImpl$Companion$TAG$1() {
        super(1, FTDIMgrImpl.class, "<init>", "<init>(Landroid/content/Context;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final FTDIMgrImpl invoke(Context p1) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        return new FTDIMgrImpl(p1);
    }
}

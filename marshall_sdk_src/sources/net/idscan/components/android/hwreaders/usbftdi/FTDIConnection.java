package net.idscan.components.android.hwreaders.usbftdi;

import android.content.Context;
import android.os.Handler;
import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.IOException;
import java.lang.Thread;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.idscan.components.android.hwreaders.common.DocumentData;
import net.idscan.components.android.hwreaders.common.DocumentType;

/* JADX INFO: compiled from: FTDIConnection.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012#\u0010\u000f\u001a\u001f\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0010\u0012#\u0010\u0016\u001a\u001f\u0012\u0013\u0012\u00110\u0017¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0010¢\u0006\u0002\u0010\u0019J\u0006\u0010\"\u001a\u00020\u0015J\u0006\u0010#\u001a\u00020\u0015J\b\u0010$\u001a\u00020\u0015H\u0002J\u0010\u0010%\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0017H\u0002J\u0010\u0010&\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0011H\u0002J\u0014\u0010'\u001a\u00020\u00152\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\f0\u000bR\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u0011@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R+\u0010\u0016\u001a\u001f\u0012\u0013\u0012\u00110\u0017¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R+\u0010\u000f\u001a\u001f\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIConnection;", "", "context", "Landroid/content/Context;", "mgr", "Lcom/ftdi/j2xx/D2xxManager;", "node", "Lcom/ftdi/j2xx/D2xxManager$FtDeviceInfoListNode;", "protocol", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIProtocol;", "acceptableTypes", "", "Lnet/idscan/components/android/hwreaders/common/DocumentType;", "handler", "Landroid/os/Handler;", "stateCallback", "Lkotlin/Function1;", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIConnectionState;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "state", "", "dataCallback", "Lnet/idscan/components/android/hwreaders/common/DocumentData;", "data", "(Landroid/content/Context;Lcom/ftdi/j2xx/D2xxManager;Lcom/ftdi/j2xx/D2xxManager$FtDeviceInfoListNode;Lnet/idscan/components/android/hwreaders/usbftdi/FTDIProtocol;Ljava/util/Set;Landroid/os/Handler;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "_isClosed", "", "value", "_state", "set_state", "(Lnet/idscan/components/android/hwreaders/usbftdi/FTDIConnectionState;)V", "_thread", "Ljava/lang/Thread;", "close", "closeQuietly", "exec", "postData", "postState", "setupAcceptableTypes", "types", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
public final class FTDIConnection {
    private volatile boolean _isClosed;
    private FTDIConnectionState _state;
    private final Thread _thread;
    private Set<? extends DocumentType> acceptableTypes;
    private final Context context;
    private Function1<? super DocumentData, Unit> dataCallback;
    private final Handler handler;
    private final D2xxManager mgr;
    private final D2xxManager.FtDeviceInfoListNode node;
    private final FTDIProtocol protocol;
    private Function1<? super FTDIConnectionState, Unit> stateCallback;

    public FTDIConnection(Context context, D2xxManager mgr, D2xxManager.FtDeviceInfoListNode node, FTDIProtocol protocol, Set<? extends DocumentType> acceptableTypes, Handler handler, Function1<? super FTDIConnectionState, Unit> function1, Function1<? super DocumentData, Unit> function12) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mgr, "mgr");
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        Intrinsics.checkNotNullParameter(acceptableTypes, "acceptableTypes");
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.context = context;
        this.mgr = mgr;
        this.node = node;
        this.protocol = protocol;
        this.acceptableTypes = acceptableTypes;
        this.handler = handler;
        this.stateCallback = function1;
        this.dataCallback = function12;
        final FTDIConnection$_thread$1 fTDIConnection$_thread$1 = new FTDIConnection$_thread$1(this);
        Thread thread = new Thread(new Runnable() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIConnection$sam$java_lang_Runnable$0
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                Intrinsics.checkNotNullExpressionValue(fTDIConnection$_thread$1.invoke(), "invoke(...)");
            }
        }, "ftdi-worker");
        this._thread = thread;
        this._state = FTDIConnectionState.Connecting;
        protocol.setupAcceptableTypes(this.acceptableTypes);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIConnection$$special$$inlined$apply$lambda$1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread2, Throwable th) {
                th.printStackTrace();
                this.this$0._isClosed = true;
                this.this$0.postState(FTDIConnectionState.Disconnected);
            }
        });
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void set_state(FTDIConnectionState fTDIConnectionState) {
        this._state = fTDIConnectionState;
        Function1<? super FTDIConnectionState, Unit> function1 = this.stateCallback;
        if (function1 != null) {
            function1.invoke(fTDIConnectionState);
        }
        if (fTDIConnectionState == FTDIConnectionState.Disconnected) {
            Function1 function12 = (Function1) null;
            this.stateCallback = function12;
            this.dataCallback = function12;
            this._isClosed = true;
        }
    }

    public final void close() {
        if (this._isClosed) {
            return;
        }
        this._isClosed = true;
        set_state(FTDIConnectionState.Disconnecting);
        this._thread.interrupt();
    }

    public final void closeQuietly() {
        Function1 function1 = (Function1) null;
        this.stateCallback = function1;
        this.dataCallback = function1;
        close();
    }

    public final void setupAcceptableTypes(Set<? extends DocumentType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        if (this._isClosed) {
            return;
        }
        this.acceptableTypes = types;
        this.protocol.setupAcceptableTypes(types);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void postState(final FTDIConnectionState state) {
        this.handler.post(new Runnable() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIConnection.postState.1
            @Override // java.lang.Runnable
            public final void run() {
                FTDIConnection.this.set_state(state);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void postData(final DocumentData data) {
        this.handler.post(new Runnable() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIConnection.postData.1
            @Override // java.lang.Runnable
            public final void run() {
                Iterator<DocumentType> it = data.getTypes().iterator();
                while (it.hasNext()) {
                    if (FTDIConnection.this.acceptableTypes.contains(it.next())) {
                        Function1 function1 = FTDIConnection.this.dataCallback;
                        if (function1 != null) {
                            return;
                        }
                        return;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x004d -> B:39:0x007a). Please report as a decompilation issue!!! */
    public final void exec() {
        if (this._isClosed) {
            postState(FTDIConnectionState.Disconnected);
            return;
        }
        try {
            FT_Device device = this.mgr.openByLocation(this.context, this.node.location);
            postState(FTDIConnectionState.Connected);
            Intrinsics.checkNotNullExpressionValue(device, "device");
            try {
            } catch (Throwable th) {
                th.printStackTrace();
                device = th;
            }
            try {
                try {
                    this.protocol.process(new FTDIConnectionContext(device, new Function0<FTDIConnectionState>() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIConnection$exec$ctx$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final FTDIConnectionState invoke() {
                            return this.this$0._state;
                        }
                    }, new Function0<Boolean>() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIConnection$exec$ctx$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Boolean invoke() {
                            return Boolean.valueOf(invoke2());
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final boolean invoke2() {
                            return this.this$0._isClosed;
                        }
                    }, new FTDIConnection$exec$ctx$3(this)));
                    boolean zIsOpen = device.isOpen();
                    device = device;
                    if (zIsOpen) {
                        device.close();
                        device = device;
                    }
                } catch (Throwable th2) {
                    if (device.isOpen()) {
                        try {
                            device.close();
                        } catch (Throwable th3) {
                            th3.printStackTrace();
                        }
                    }
                    throw th2;
                }
            } catch (IOException e) {
                e.printStackTrace();
                boolean zIsOpen2 = device.isOpen();
                device = device;
                if (zIsOpen2) {
                    device.close();
                    device = device;
                }
            } catch (InterruptedException unused) {
                if (device.isOpen()) {
                    device.close();
                }
                postState(FTDIConnectionState.Disconnected);
            }
            postState(FTDIConnectionState.Disconnected);
        } catch (Throwable unused2) {
            postState(FTDIConnectionState.Disconnected);
        }
    }
}

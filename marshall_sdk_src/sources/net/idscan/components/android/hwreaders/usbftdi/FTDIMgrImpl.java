package net.idscan.components.android.hwreaders.usbftdi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.ftdi.j2xx.D2xxManager;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.idscan.components.android.hwreaders.common.DocumentData;
import net.idscan.components.android.hwreaders.common.DocumentType;
import net.idscan.components.android.hwreaders.common.MutableObservable;
import net.idscan.components.android.hwreaders.common.Observable;
import net.idscan.components.android.hwreaders.common.Observer;
import net.idscan.components.android.hwreaders.usbftdi.DeviceEvent;
import net.idscan.components.android.hwreaders.usbftdi.FTDIDevice;

/* JADX INFO: compiled from: FTDIMgr.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u0000 C2\u00020\u0001:\u0002CDB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\nH\u0002J\b\u00107\u001a\u000205H\u0016J\u0010\u00108\u001a\u0002052\u0006\u00106\u001a\u00020\nH\u0002J\b\u00109\u001a\u000205H\u0016J\u0010\u0010:\u001a\u00020;2\u0006\u00106\u001a\u00020\nH\u0002J\u0016\u0010<\u001a\u0002052\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u001e\u0010>\u001a\u0002052\u0006\u00106\u001a\u00020\n2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J,\u0010?\u001a\u0002052\u0006\u00106\u001a\u00020\n2\u001a\u0010@\u001a\u0016\u0012\b\u0012\u00060\u000eR\u00020\u0000\u0012\b\u0012\u00060\u000eR\u00020\u00000AH\u0002J\b\u0010B\u001a\u000205H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\r\u001a&\u0012\u0004\u0012\u00020\n\u0012\b\u0012\u00060\u000eR\u00020\u00000\tj\u0012\u0012\u0004\u0012\u00020\n\u0012\b\u0012\u00060\u000eR\u00020\u0000`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u001e\u0010+\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\u001d@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b+\u0010)R\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00190-X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u001a\u00100\u001a\b\u0012\u0004\u0012\u00020\u001b0-X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u0010/R\u001a\u00102\u001a\b\u0012\u0004\u0012\u00020\u001d0-X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u0010/¨\u0006E"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgrImpl;", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgr;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_acceptableDocumentTypes", "", "Lnet/idscan/components/android/hwreaders/common/DocumentType;", "_connections", "Ljava/util/HashMap;", "Lcom/ftdi/j2xx/D2xxManager$FtDeviceInfoListNode;", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIConnection;", "Lkotlin/collections/HashMap;", "_deviceList", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgrImpl$FTDIDeviceImpl;", "_ftdiMgr", "Lcom/ftdi/j2xx/D2xxManager;", "get_ftdiMgr", "()Lcom/ftdi/j2xx/D2xxManager;", "_ftdiMgr$delegate", "Lkotlin/Lazy;", "_handler", "Landroid/os/Handler;", "_observableData", "Lnet/idscan/components/android/hwreaders/common/MutableObservable;", "Lnet/idscan/components/android/hwreaders/common/DocumentData;", "_observableDeviceEvent", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceEvent;", "_observableState", "", "_usbBroadcastReceiver", "Landroid/content/BroadcastReceiver;", "acceptableDocumentTypes", "getAcceptableDocumentTypes", "()Ljava/util/Set;", "deviceList", "", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "getDeviceList", "()Ljava/util/List;", "isAvailable", "()Z", "<set-?>", "isEnabled", "observableData", "Lnet/idscan/components/android/hwreaders/common/Observable;", "getObservableData", "()Lnet/idscan/components/android/hwreaders/common/Observable;", "observableDeviceEvent", "getObservableDeviceEvent", "observableState", "getObservableState", "connectDevice", "", "node", "disable", "disconnectDevice", "enable", "selectProtocol", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIProtocol;", "setAcceptableDocumentTypes", "types", "setupAcceptableTypes", "updateDevice", "mutator", "Lkotlin/Function1;", "updateDeviceList", "Companion", "FTDIDeviceImpl", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
final class FTDIMgrImpl implements FTDIMgr {
    private static final String ACTION_FTDI_USB_PERMISSION = "com.ftdi.j2xx";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = FTDIMgrImpl$Companion$TAG$1.INSTANCE.getClass().getName();
    private Set<? extends DocumentType> _acceptableDocumentTypes;
    private final HashMap<D2xxManager.FtDeviceInfoListNode, FTDIConnection> _connections;
    private final HashMap<D2xxManager.FtDeviceInfoListNode, FTDIDeviceImpl> _deviceList;

    /* JADX INFO: renamed from: _ftdiMgr$delegate, reason: from kotlin metadata */
    private final Lazy _ftdiMgr;
    private final Handler _handler;
    private final MutableObservable<DocumentData> _observableData;
    private final MutableObservable<DeviceEvent> _observableDeviceEvent;
    private final MutableObservable<Boolean> _observableState;
    private final BroadcastReceiver _usbBroadcastReceiver;
    private final Context context;
    private boolean isEnabled;
    private final Observable<DocumentData> observableData;
    private final Observable<DeviceEvent> observableDeviceEvent;
    private final Observable<Boolean> observableState;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FTDIConnectionState.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[FTDIConnectionState.Connecting.ordinal()] = 1;
            iArr[FTDIConnectionState.Connected.ordinal()] = 2;
            iArr[FTDIConnectionState.Disconnecting.ordinal()] = 3;
            iArr[FTDIConnectionState.Disconnected.ordinal()] = 4;
        }
    }

    private final D2xxManager get_ftdiMgr() {
        return (D2xxManager) this._ftdiMgr.getValue();
    }

    public FTDIMgrImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this._handler = new Handler(Looper.getMainLooper());
        this._ftdiMgr = LazyKt.lazy(new Function0<D2xxManager>() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIMgrImpl$_ftdiMgr$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final D2xxManager invoke() {
                return FTDIMgrImpl.INSTANCE.newD2xxManager(this.this$0.context);
            }
        });
        this._deviceList = new HashMap<>();
        this._connections = new HashMap<>();
        this._acceptableDocumentTypes = SetsKt.emptySet();
        MutableObservable<Boolean> mutableObservable = new MutableObservable<>(new Function1<Observer<? super Boolean>, Unit>() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIMgrImpl$_observableState$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Observer<? super Boolean> observer) {
                invoke2(observer);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Observer<? super Boolean> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                it.onNotify(Boolean.valueOf(this.this$0.getIsEnabled()));
            }
        });
        this._observableState = mutableObservable;
        this.observableState = mutableObservable;
        MutableObservable<DocumentData> mutableObservable2 = new MutableObservable<>(null, 1, null);
        this._observableData = mutableObservable2;
        this.observableData = mutableObservable2;
        MutableObservable<DeviceEvent> mutableObservable3 = new MutableObservable<>(null, 1, null);
        this._observableDeviceEvent = mutableObservable3;
        this.observableDeviceEvent = mutableObservable3;
        this._usbBroadcastReceiver = new FTDIMgrImpl$_usbBroadcastReceiver$1(this);
    }

    /* JADX INFO: compiled from: FTDIMgr.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgrImpl$Companion;", "", "()V", "ACTION_FTDI_USB_PERMISSION", "", "TAG", "kotlin.jvm.PlatformType", "newD2xxManager", "Lcom/ftdi/j2xx/D2xxManager;", "context", "Landroid/content/Context;", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final D2xxManager newD2xxManager(Context context) {
            try {
                return D2xxManager.getInstance(context);
            } catch (D2xxManager.D2xxException unused) {
                Log.w(FTDIMgrImpl.TAG, "FTDI is not supported");
                return null;
            }
        }
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public boolean isAvailable() {
        return get_ftdiMgr() != null;
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    /* JADX INFO: renamed from: isEnabled, reason: from getter */
    public boolean getIsEnabled() {
        return this.isEnabled;
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public List<FTDIDevice> getDeviceList() {
        Collection<FTDIDeviceImpl> collectionValues = this._deviceList.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "_deviceList.values");
        return CollectionsKt.toList(collectionValues);
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public Set<DocumentType> getAcceptableDocumentTypes() {
        return this._acceptableDocumentTypes;
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public Observable<Boolean> getObservableState() {
        return this.observableState;
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public Observable<DocumentData> getObservableData() {
        return this.observableData;
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public Observable<DeviceEvent> getObservableDeviceEvent() {
        return this.observableDeviceEvent;
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public void enable() {
        if (!isAvailable() || getIsEnabled()) {
            return;
        }
        D2xxManager d2xxManager = get_ftdiMgr();
        if (d2xxManager != null) {
            d2xxManager.setRequestPermission(true);
            d2xxManager.setUsbRegisterBroadcast(true);
        }
        Context context = this.context;
        BroadcastReceiver broadcastReceiver = this._usbBroadcastReceiver;
        IntentFilter intentFilter = new IntentFilter(ACTION_FTDI_USB_PERMISSION);
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        Unit unit = Unit.INSTANCE;
        context.registerReceiver(broadcastReceiver, intentFilter);
        this.isEnabled = true;
        this._observableState.notify(Boolean.valueOf(getIsEnabled()));
        updateDeviceList();
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public void disable() {
        if (getIsEnabled()) {
            this.context.unregisterReceiver(this._usbBroadcastReceiver);
            D2xxManager d2xxManager = get_ftdiMgr();
            if (d2xxManager != null) {
                d2xxManager.setRequestPermission(false);
                d2xxManager.setUsbRegisterBroadcast(false);
            }
            Collection<FTDIConnection> collectionValues = this._connections.values();
            Intrinsics.checkNotNullExpressionValue(collectionValues, "_connections.values");
            List list = CollectionsKt.toList(collectionValues);
            List<FTDIDevice> deviceList = getDeviceList();
            this._deviceList.clear();
            this._connections.clear();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((FTDIConnection) it.next()).closeQuietly();
            }
            Iterator<FTDIDevice> it2 = deviceList.iterator();
            while (it2.hasNext()) {
                this._observableDeviceEvent.notify(new DeviceEvent.OnDetached(it2.next()));
            }
            this.isEnabled = false;
            this._observableState.notify(Boolean.valueOf(getIsEnabled()));
        }
    }

    @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIMgr
    public void setAcceptableDocumentTypes(Set<? extends DocumentType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        this._acceptableDocumentTypes = types;
        Set<D2xxManager.FtDeviceInfoListNode> setKeySet = this._deviceList.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "_deviceList.keys");
        for (D2xxManager.FtDeviceInfoListNode node : CollectionsKt.toList(setKeySet)) {
            Intrinsics.checkNotNullExpressionValue(node, "node");
            setupAcceptableTypes(node, this._acceptableDocumentTypes);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void updateDeviceList() {
        /*
            Method dump skipped, instruction units count: 220
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.idscan.components.android.hwreaders.usbftdi.FTDIMgrImpl.updateDeviceList():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void connectDevice(final D2xxManager.FtDeviceInfoListNode node) {
        D2xxManager d2xxManager = get_ftdiMgr();
        if (getIsEnabled() && d2xxManager != null && this._connections.get(node) == null) {
            this._connections.put(node, new FTDIConnection(this.context, d2xxManager, node, selectProtocol(node), getAcceptableDocumentTypes(), this._handler, new Function1<FTDIConnectionState, Unit>() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIMgrImpl.connectDevice.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FTDIConnectionState fTDIConnectionState) {
                    invoke2(fTDIConnectionState);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FTDIConnectionState state) {
                    final FTDIDevice.State state2;
                    Intrinsics.checkNotNullParameter(state, "state");
                    if (state == FTDIConnectionState.Disconnected) {
                        FTDIMgrImpl.this._connections.remove(node);
                    }
                    int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
                    if (i == 1) {
                        state2 = FTDIDevice.State.Connecting;
                    } else if (i == 2) {
                        state2 = FTDIDevice.State.Connected;
                    } else if (i == 3) {
                        state2 = FTDIDevice.State.Disconnecting;
                    } else {
                        if (i != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                        state2 = FTDIDevice.State.Disconnected;
                    }
                    FTDIMgrImpl.this.updateDevice(node, new Function1<FTDIDeviceImpl, FTDIDeviceImpl>() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIMgrImpl.connectDevice.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final FTDIDeviceImpl invoke(FTDIDeviceImpl it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return new FTDIDeviceImpl(FTDIMgrImpl.this, it.getNode(), it.getId(), state2, it.getAcceptableDocumentTypes());
                        }
                    });
                }
            }, new Function1<DocumentData, Unit>() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIMgrImpl.connectDevice.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DocumentData documentData) {
                    invoke2(documentData);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(DocumentData data) {
                    Intrinsics.checkNotNullParameter(data, "data");
                    FTDIMgrImpl.this._observableData.notify(data);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void disconnectDevice(D2xxManager.FtDeviceInfoListNode node) {
        FTDIConnection fTDIConnection = this._connections.get(node);
        if (fTDIConnection != null) {
            fTDIConnection.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setupAcceptableTypes(D2xxManager.FtDeviceInfoListNode node, final Set<? extends DocumentType> types) {
        FTDIConnection fTDIConnection = this._connections.get(node);
        if (fTDIConnection != null) {
            fTDIConnection.setupAcceptableTypes(types);
        }
        updateDevice(node, new Function1<FTDIDeviceImpl, FTDIDeviceImpl>() { // from class: net.idscan.components.android.hwreaders.usbftdi.FTDIMgrImpl.setupAcceptableTypes.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FTDIDeviceImpl invoke(FTDIDeviceImpl it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new FTDIDeviceImpl(FTDIMgrImpl.this, it.getNode(), it.getId(), it.getState(), types);
            }
        });
    }

    private final FTDIProtocol selectProtocol(D2xxManager.FtDeviceInfoListNode node) {
        return new FTDICommonProtocol();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateDevice(D2xxManager.FtDeviceInfoListNode node, Function1<? super FTDIDeviceImpl, FTDIDeviceImpl> mutator) {
        FTDIDeviceImpl fTDIDeviceImpl = this._deviceList.get(node);
        if (fTDIDeviceImpl != null) {
            FTDIDeviceImpl fTDIDeviceImplInvoke = mutator.invoke(fTDIDeviceImpl);
            this._deviceList.put(node, fTDIDeviceImplInvoke);
            this._observableDeviceEvent.notify(new DeviceEvent.OnChanged(fTDIDeviceImplInvoke));
        }
    }

    /* JADX INFO: compiled from: FTDIMgr.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020!H\u0016J\u0016\u0010#\u001a\u00020!2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0013R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0013¨\u0006%"}, d2 = {"Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgrImpl$FTDIDeviceImpl;", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice;", "node", "Lcom/ftdi/j2xx/D2xxManager$FtDeviceInfoListNode;", "id", "Lnet/idscan/components/android/hwreaders/usbftdi/DeviceId;", "state", "Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice$State;", "acceptableDocumentTypes", "", "Lnet/idscan/components/android/hwreaders/common/DocumentType;", "(Lnet/idscan/components/android/hwreaders/usbftdi/FTDIMgrImpl;Lcom/ftdi/j2xx/D2xxManager$FtDeviceInfoListNode;Lnet/idscan/components/android/hwreaders/usbftdi/DeviceId;Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice$State;Ljava/util/Set;)V", "getAcceptableDocumentTypes", "()Ljava/util/Set;", "getId", "()Lnet/idscan/components/android/hwreaders/usbftdi/DeviceId;", FirebaseAnalytics.Param.LOCATION, "", "getLocation", "()I", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "getName", "()Ljava/lang/String;", "getNode", "()Lcom/ftdi/j2xx/D2xxManager$FtDeviceInfoListNode;", "pid", "getPid", "getState", "()Lnet/idscan/components/android/hwreaders/usbftdi/FTDIDevice$State;", "vid", "getVid", "connect", "", "disconnect", "setAcceptableDocumentTypes", "types", "component-usb-ftdi_release"}, k = 1, mv = {1, 4, 2})
    private final class FTDIDeviceImpl implements FTDIDevice {
        private final Set<DocumentType> acceptableDocumentTypes;
        private final DeviceId id;
        private final D2xxManager.FtDeviceInfoListNode node;
        private final FTDIDevice.State state;
        final /* synthetic */ FTDIMgrImpl this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public FTDIDeviceImpl(FTDIMgrImpl fTDIMgrImpl, D2xxManager.FtDeviceInfoListNode node, DeviceId id, FTDIDevice.State state, Set<? extends DocumentType> acceptableDocumentTypes) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(acceptableDocumentTypes, "acceptableDocumentTypes");
            this.this$0 = fTDIMgrImpl;
            this.node = node;
            this.id = id;
            this.state = state;
            this.acceptableDocumentTypes = acceptableDocumentTypes;
        }

        public final D2xxManager.FtDeviceInfoListNode getNode() {
            return this.node;
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public DeviceId getId() {
            return this.id;
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public FTDIDevice.State getState() {
            return this.state;
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public Set<DocumentType> getAcceptableDocumentTypes() {
            return this.acceptableDocumentTypes;
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public String getName() {
            String str = this.node.description;
            return str != null ? str : "Unknown";
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public int getVid() {
            return (this.node.id & ((int) 4294901760L)) >>> 16;
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public int getPid() {
            return this.node.id & 65535;
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public int getLocation() {
            return this.node.location;
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public void connect() {
            this.this$0.connectDevice(this.node);
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public void disconnect() {
            this.this$0.disconnectDevice(this.node);
        }

        @Override // net.idscan.components.android.hwreaders.usbftdi.FTDIDevice
        public void setAcceptableDocumentTypes(Set<? extends DocumentType> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            this.this$0.setupAcceptableTypes(this.node, types);
        }
    }
}

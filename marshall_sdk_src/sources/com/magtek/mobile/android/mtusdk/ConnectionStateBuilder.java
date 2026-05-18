package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class ConnectionStateBuilder {
    public static String CONNECTED = "connected";
    public static String CONNECTING = "connecting";
    public static String DISCONNECTED = "disconnected";
    public static String DISCONNECTING = "disconnecting";
    public static String ERROR = "error";

    public static ConnectionState GetValue(String str) {
        ConnectionState connectionState;
        ConnectionState connectionState2 = ConnectionState.Unknown;
        if (str == null) {
            return connectionState2;
        }
        try {
            if (str.equalsIgnoreCase(DISCONNECTED)) {
                connectionState = ConnectionState.Disconnected;
            } else if (str.equalsIgnoreCase(CONNECTING)) {
                connectionState = ConnectionState.Connecting;
            } else if (str.equalsIgnoreCase(ERROR)) {
                connectionState = ConnectionState.Error;
            } else if (str.equalsIgnoreCase(CONNECTED)) {
                connectionState = ConnectionState.Connected;
            } else {
                if (!str.equalsIgnoreCase(DISCONNECTING)) {
                    return connectionState2;
                }
                connectionState = ConnectionState.Disconnecting;
            }
            return connectionState;
        } catch (Exception unused) {
            return connectionState2;
        }
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.ConnectionStateBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionState;

        static {
            int[] iArr = new int[ConnectionState.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionState = iArr;
            try {
                iArr[ConnectionState.Connecting.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionState[ConnectionState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionState[ConnectionState.Disconnecting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionState[ConnectionState.Disconnected.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionState[ConnectionState.Error.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static String GetString(ConnectionState connectionState) {
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionState[connectionState.ordinal()];
        if (i == 1) {
            return CONNECTING;
        }
        if (i == 2) {
            return CONNECTED;
        }
        if (i == 3) {
            return DISCONNECTING;
        }
        if (i != 4) {
            return i != 5 ? "" : ERROR;
        }
        return DISCONNECTED;
    }
}

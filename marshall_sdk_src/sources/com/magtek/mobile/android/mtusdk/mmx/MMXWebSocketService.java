package com.magtek.mobile.android.mtusdk.mmx;

import android.content.Context;
import android.util.Log;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/* JADX INFO: loaded from: classes.dex */
public class MMXWebSocketService extends MMXBaseService {
    private static final String TAG = "MMXWebSocketService";
    private WebSocketClient mWebSocketClient;
    private Object mStateSyncToken = new Object();
    private Object mInputDataSyncToken = new Object();

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void initialize(Context context, IMMXServiceAdapter iMMXServiceAdapter) {
        this.mContext = context;
        this.mServiceAdapter = iMMXServiceAdapter;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void connect() {
        synchronized (this.mStateSyncToken) {
            if (this.mConnectionState != MMXConnectionState.Disconnected) {
                Log.i(TAG, "State is not Disconnected, cannot connect at this time.");
                return;
            }
            setConnectionState(MMXConnectionState.Connecting);
            Log.i(TAG, "connecting");
            try {
                WebSocketClient webSocketClient = new WebSocketClient(new URI(this.mAddress)) { // from class: com.magtek.mobile.android.mtusdk.mmx.MMXWebSocketService.1
                    @Override // org.java_websocket.client.WebSocketClient
                    public void onOpen(ServerHandshake serverHandshake) {
                        Log.i(MMXWebSocketService.TAG, "Opened");
                        MMXWebSocketService.this.setConnectionState(MMXConnectionState.Connected);
                    }

                    @Override // org.java_websocket.client.WebSocketClient
                    public void onMessage(String str) {
                        Log.i(MMXWebSocketService.TAG, "Received: " + str);
                    }

                    @Override // org.java_websocket.client.WebSocketClient
                    public void onMessage(ByteBuffer byteBuffer) {
                        byte[] bArrArray = byteBuffer.array();
                        if (bArrArray != null) {
                            Log.i(MMXWebSocketService.TAG, "*** Input Data=" + TLVParser.getHexString(bArrArray));
                            synchronized (MMXWebSocketService.this.mInputDataSyncToken) {
                                byte[] bArr = new byte[bArrArray.length];
                                System.arraycopy(bArrArray, 0, bArr, 0, bArrArray.length);
                                MMXWebSocketService.this.setDataReceived(bArr);
                            }
                        }
                    }

                    @Override // org.java_websocket.client.WebSocketClient
                    public void onClose(int i, String str, boolean z) {
                        Log.i(MMXWebSocketService.TAG, "Closed " + str);
                        MMXWebSocketService.this.setConnectionState(MMXConnectionState.Disconnected);
                    }

                    @Override // org.java_websocket.client.WebSocketClient
                    public void onError(Exception exc) {
                        Log.i(MMXWebSocketService.TAG, "Error " + exc.getMessage());
                    }
                };
                this.mWebSocketClient = webSocketClient;
                webSocketClient.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void disconnect() {
        if (this.mWebSocketClient != null) {
            setConnectionState(MMXConnectionState.Disconnecting);
            try {
                this.mWebSocketClient.close();
            } catch (Exception e) {
                Log.i(TAG, "Exception: " + e.toString());
            }
            setConnectionState(MMXConnectionState.Disconnected);
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void sendData(byte[] bArr) {
        Log.i(TAG, "Sending: " + TLVParser.getHexString(bArr));
        this.mWebSocketClient.send(bArr);
    }
}

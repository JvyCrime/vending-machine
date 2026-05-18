package com.digitalmediavending.hardware;

import android.util.Log;
import com.digitalmediavending.hardware.SocketServerInitializer;
import com.orhanobut.logger.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class SocketSendData {
    private static SocketServerInitializer.OnSocketConnectListener connectListener;
    private static ExecutorService executorService;
    private static SocketServerInitializer serverInitializer;

    private static void startSocket() {
        Logger.e("Start Socket was called successfully", new Object[0]);
        connectListener = new SocketServerInitializer.OnSocketConnectListener() { // from class: com.digitalmediavending.hardware.SocketSendData.1
            @Override // com.digitalmediavending.hardware.SocketServerInitializer.OnSocketConnectListener
            public void connected() {
                Log.e("Connected", "the socket on backend was connected");
            }

            @Override // com.digitalmediavending.hardware.SocketServerInitializer.OnSocketConnectListener
            public void connectedFail() {
                Log.e("Connected", "the socket on backend was connection failed");
            }
        };
        connect();
    }

    public static void connect() {
        Logger.e("Connect Socket was called successfully", new Object[0]);
        SocketServerInitializer socketServerInitializer = new SocketServerInitializer(connectListener);
        serverInitializer = socketServerInitializer;
        executorService.execute(socketServerInitializer);
    }

    public static void init() {
        if (serverInitializer == null) {
            Logger.e("SocketSendData was called successfully", new Object[0]);
            executorService = Executors.newFixedThreadPool(10);
            startSocket();
        }
    }

    public static boolean sendMsg(String msg) {
        try {
            if (!serverInitializer.isInit()) {
                return false;
            }
            executorService.execute(new SocketDataSendHelper(serverInitializer, msg));
            return true;
        } catch (NullPointerException e) {
            Logger.e("Backtest error " + e.getMessage(), new Object[0]);
            return false;
        }
    }

    public static void close() {
        serverInitializer.close();
    }
}

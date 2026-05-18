package com.digitalmediavending.hardware;

/* JADX INFO: loaded from: classes.dex */
public class SocketDataSendHelper implements Runnable {
    private String msg;
    private SocketServerInitializer server;

    public SocketDataSendHelper(SocketServerInitializer server, String msg) {
        this.server = server;
        this.msg = msg;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.server.sendMsg(this.msg);
    }
}

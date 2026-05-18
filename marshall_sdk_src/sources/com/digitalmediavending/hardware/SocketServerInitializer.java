package com.digitalmediavending.hardware;

import android.util.Log;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
public class SocketServerInitializer implements Runnable {
    public static final int SERVERPORT = 57482;
    private OnSocketConnectListener connectListener;
    private ServerSocket serverSocket;
    public Socket socket = null;
    SocketDataReceiver socketDataReceiver = null;

    public interface OnSocketConnectListener {
        void connected();

        void connectedFail();
    }

    public SocketServerInitializer(OnSocketConnectListener listener) {
        Logger.e("SocketServerInitializer was called successfully", new Object[0]);
        this.connectListener = listener;
    }

    @Override // java.lang.Runnable
    public void run() {
        createSocket();
    }

    public boolean isInit() {
        Socket socket = this.socket;
        return (socket == null || socket.isClosed() || this.socketDataReceiver == null) ? false : true;
    }

    public void createSocket() {
        Logger.e("createSocket was called successfully", new Object[0]);
        try {
            this.serverSocket = new ServerSocket(SERVERPORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Logger.e("Waiting for connection", new Object[0]);
                Log.e("Socket", "Waiting for connection");
                Socket socketAccept = this.serverSocket.accept();
                this.socket = socketAccept;
                getMessage(socketAccept);
                this.connectListener.connected();
                Log.e("Socket", "Connected to the socket");
                Logger.e("Connected to socket backend", new Object[0]);
            } catch (Exception e2) {
                Log.e("Socket", "Connection failed, error was " + e2.getMessage());
                this.connectListener.connectedFail();
                e2.printStackTrace();
            }
        }
    }

    public boolean sendMsg(String msg) {
        SocketDataReceiver socketDataReceiver = this.socketDataReceiver;
        if (socketDataReceiver != null) {
            return socketDataReceiver.sendMsg(msg);
        }
        return false;
    }

    public void getMessage(Socket socket) {
        Log.e("Socket", "Get message was called for socket initialization");
        Logger.e("Get message was called for socket initialization", new Object[0]);
        SocketDataReceiver socketDataReceiver = new SocketDataReceiver(socket);
        this.socketDataReceiver = socketDataReceiver;
        socketDataReceiver.start();
    }

    public void close() {
        try {
            Socket socket = this.socket;
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

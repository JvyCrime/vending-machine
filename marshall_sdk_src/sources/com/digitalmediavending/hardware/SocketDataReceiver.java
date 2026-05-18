package com.digitalmediavending.hardware;

import android.content.Intent;
import android.util.Log;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class SocketDataReceiver extends Thread {
    private Lock lock = new ReentrantLock();
    private int parseStatus;
    private StringBuilder sb;
    private Socket socket;
    private int startBlockCount;

    SocketDataReceiver(Socket socket) {
        this.socket = socket;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:69|4|5|(4:76|6|74|7)|(6:78|8|(1:81)(2:(4:80|20|(2:23|21)|84)(1:83)|82)|17|49|85)|10|(1:12)|67|13|17|49|85) */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003f, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0040, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ef A[Catch: IOException -> 0x00eb, all -> 0x00fa, TRY_LEAVE, TryCatch #10 {IOException -> 0x00eb, blocks: (B:52:0x00e7, B:56:0x00ef), top: B:72:0x00e7, outer: #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00e7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            Method dump skipped, instruction units count: 258
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.digitalmediavending.hardware.SocketDataReceiver.run():void");
    }

    private List<String> parseJson(String jsonData) {
        int length = jsonData.length();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < length; i++) {
            char cCharAt = jsonData.charAt(i);
            if (cCharAt == '{') {
                if (this.startBlockCount == 0) {
                    this.sb = new StringBuilder();
                }
                this.startBlockCount++;
                this.parseStatus = 1;
                this.sb.append(cCharAt);
            } else if (cCharAt == '}') {
                this.sb.append(cCharAt);
                int i2 = this.startBlockCount - 1;
                this.startBlockCount = i2;
                if (i2 == 0) {
                    this.parseStatus = 0;
                    arrayList.add(this.sb.toString());
                }
            } else if (this.parseStatus == 1) {
                this.sb.append(cCharAt);
            }
        }
        return arrayList;
    }

    public boolean sendMsg(String msg) {
        try {
            this.socket.getOutputStream().write(msg.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendBroadcast(String data, int msgId) {
        Logger.e("sendBroadcast was called successfully from thread " + Thread.currentThread().getName(), new Object[0]);
        Intent intent = new Intent("sendBroadcast");
        intent.putExtra("msgId", msgId);
        intent.putExtra("data", data);
        MainApp app = MainApp.getApp();
        Log.e("TAG", "Broadcast was called from our end");
        if (app != null) {
            Log.e("TAG", "the broadcast wasn't nullmsg id was " + msgId + "and data was " + data);
            app.sendBroadcast(intent);
        }
    }
}

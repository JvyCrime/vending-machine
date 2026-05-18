package com.digitalmediavending.hardware;

import android.app.Application;
import android.os.Build;
import android.os.Environment;
import com.digitalmediavending.logger.LoggerHelp;

/* JADX INFO: loaded from: classes.dex */
public class MainApp extends Application {
    public static LoggerHelp ResponseLogger;
    private static MainApp appContext;

    @Override // android.app.Application
    public void onCreate() {
        appContext = this;
        super.onCreate();
        ResponseLogger = new LoggerHelp(appContext, Environment.getExternalStorageDirectory().getAbsolutePath() + "/logger", "HARDWARE_LOGS", BuildConfig.APPLICATION_ID, 500);
        if (Build.VERSION.SDK_INT < 26) {
            initLogger();
        }
    }

    public static void initLogger() {
        ResponseLogger.init();
    }

    public static MainApp getApp() {
        return appContext;
    }
}

package com.digitalmediavending.logger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.blankj.utilcode.constant.CacheConstants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class LoggerHelp {
    private final String mBaseDirectory;
    private final Context mContext;
    private final String mFileName;
    private final String mPackageName;
    private final int maxFileSize;
    private SharedPreferences preferences;
    private String lastFileDate = null;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yy", Locale.getDefault());
    private final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("dd_MM_yy-HH:mm:ss.SSS", Locale.getDefault());

    public LoggerHelp(Context context, String str, String str2, String str3, int i) {
        this.mContext = context;
        this.mBaseDirectory = str;
        this.mFileName = str2;
        this.mPackageName = str3;
        this.maxFileSize = i;
    }

    public void init() {
        File file = new File(this.mBaseDirectory);
        Log.w("init: ", file.getAbsolutePath());
        boolean zMkdir = !file.exists() ? file.mkdir() : true;
        Log.w("init: ", String.valueOf(zMkdir));
        if (zMkdir) {
            this.preferences = this.mContext.getSharedPreferences(BuildConfig.LIBRARY_PACKAGE_NAME, 0);
        }
    }

    public void e(String str) {
        appendLog("ERROR", str);
    }

    public void w(String str) {
        appendLog("WARNING", str);
    }

    public void i(String str) {
        appendLog("INFO", str);
    }

    public void machineError(String str) {
        appendLog("MACHINE_ERROR", str);
    }

    public void service(String str) {
        appendLog("SERVICE", str);
    }

    public void socket(String str) {
        appendLog("SERVICE", str);
    }

    public void payment(String str) {
        appendLog("PAYMENT", str);
    }

    public void dispense(String str) {
        appendLog("DISPENSE", str);
    }

    private void appendLog(String str, String str2) {
        File logFile;
        try {
            if (this.preferences == null || (logFile = getLogFile()) == null) {
                return;
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
            bufferedWriter.append((CharSequence) this.simpleTimeFormat.format(new Date()));
            bufferedWriter.append((CharSequence) " : ");
            bufferedWriter.append((CharSequence) this.mPackageName);
            bufferedWriter.append((CharSequence) " : ");
            bufferedWriter.append((CharSequence) str);
            bufferedWriter.append((CharSequence) " : ");
            bufferedWriter.append((CharSequence) str2);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getLogFile() throws IOException {
        String str = this.simpleDateFormat.format(new Date());
        String str2 = this.lastFileDate;
        if (str2 == null) {
            this.lastFileDate = str;
        } else if (!str.equals(str2)) {
            this.lastFileDate = str;
            setPreferenceValue(0);
        }
        deleteOldFiles(7);
        File file = new File(this.mBaseDirectory + "/" + this.mFileName + "_" + this.lastFileDate + "_" + this.preferences.getInt(this.mFileName, 0) + ".txt");
        Log.w("getLogFile: ", file.getAbsolutePath());
        if (!file.exists()) {
            if (file.createNewFile()) {
                return file;
            }
            return null;
        }
        if (file.length() / 1024 < this.maxFileSize) {
            return file;
        }
        int i = this.preferences.getInt(this.mFileName, 0) + 1;
        setPreferenceValue(i);
        File file2 = new File(this.mBaseDirectory + "/" + this.mFileName + "_" + this.lastFileDate + "_" + i + ".txt");
        if (file2.exists() || file2.createNewFile()) {
            return file2;
        }
        return null;
    }

    private void deleteOldFiles(int i) {
        try {
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            long j = ((long) (i * 24 * CacheConstants.HOUR)) * 1000;
            File[] fileArrListFiles = new File(this.mBaseDirectory).listFiles();
            if (fileArrListFiles != null) {
                for (File file : fileArrListFiles) {
                    if (!file.isDirectory() && timeInMillis - file.lastModified() >= j) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPreferenceValue(int i) {
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.putInt(this.mFileName, i);
        editorEdit.apply();
    }
}

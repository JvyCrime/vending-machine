package com.digitalmediavending.logger;

import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* JADX INFO: loaded from: classes.dex */
public class RemoteMonitor {
    private final String downloadUrl = "https://bin.equinox.io/a/e93TBaoFgZw/ngrok-2.2.8-linux-arm.zip";
    private LoggerHelp loggerHelp;
    private final Context mContext;
    private final File zipDirectory;
    private final File zipFile;
    private final String zipFileName;

    public RemoteMonitor(Context context) {
        this.mContext = context;
        String str = "https://bin.equinox.io/a/e93TBaoFgZw/ngrok-2.2.8-linux-arm.zip".split("/")["https://bin.equinox.io/a/e93TBaoFgZw/ngrok-2.2.8-linux-arm.zip".split("/").length - 1];
        this.zipFileName = str;
        File file = new File(context.getFilesDir() + "/" + str);
        this.zipFile = file;
        this.zipDirectory = new File(file + "_unzipped");
        LoggerHelp loggerHelp = new LoggerHelp(context, Environment.getExternalStorageDirectory() + "/logger", "LOGGER_RESPONSE", BuildConfig.LIBRARY_PACKAGE_NAME, 500);
        this.loggerHelp = loggerHelp;
        loggerHelp.init();
    }

    public void startMonitor() {
        if (!this.zipFile.exists()) {
            try {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
                FileOutputStream fileOutputStreamOpenFileOutput = this.mContext.openFileOutput(this.zipFileName, 0);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://bin.equinox.io/a/e93TBaoFgZw/ngrok-2.2.8-linux-arm.zip").openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i != -1) {
                        fileOutputStreamOpenFileOutput.write(bArr, 0, i);
                    } else {
                        inputStream.close();
                        fileOutputStreamOpenFileOutput.close();
                        startUnzippingFile();
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            startUnzippingFile();
        }
    }

    private void startUnzippingFile() {
        try {
            if (!this.zipDirectory.exists()) {
                ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(this.zipFile));
                boolean zExists = this.zipDirectory.exists();
                if (!zExists) {
                    zExists = this.zipDirectory.mkdir();
                }
                if (!zExists) {
                    return;
                }
                while (true) {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry != null) {
                        if (!nextEntry.isDirectory()) {
                            FileOutputStream fileOutputStream = new FileOutputStream(this.zipDirectory.getAbsolutePath() + "/" + nextEntry.getName());
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int i = zipInputStream.read(bArr);
                                if (i == -1) {
                                    break;
                                } else {
                                    bufferedOutputStream.write(bArr, 0, i);
                                }
                            }
                            bufferedOutputStream.close();
                            zipInputStream.closeEntry();
                            fileOutputStream.close();
                        }
                    } else {
                        zipInputStream.close();
                        makeFileExecutable();
                        return;
                    }
                }
            } else {
                makeFileExecutable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeFileExecutable() throws Exception {
        File file = new File(this.zipDirectory.getAbsolutePath() + "/ngrok");
        if (!file.exists()) {
            return;
        }
        if (!(!file.canExecute() ? file.setExecutable(true, false) : true)) {
            return;
        }
        Process processStart = new ProcessBuilder(new String[0]).command("su", "-c", this.zipDirectory.getAbsolutePath() + "/ngrok authtoken test@123#").redirectErrorStream(true).start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processStart.getInputStream()));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                sb.append(line);
                sb.append("\n");
            } else {
                String string = sb.toString();
                int iWaitFor = processStart.waitFor();
                this.loggerHelp.e(string + "_" + iWaitFor);
                return;
            }
        }
    }
}

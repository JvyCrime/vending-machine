package com.google.firebase.crashlytics.internal.persistence;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public interface FileStore {
    File getFilesDir();

    String getFilesDirPath();
}

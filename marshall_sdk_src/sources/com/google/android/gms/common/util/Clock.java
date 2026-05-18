package com.google.android.gms.common.util;

/* JADX INFO: loaded from: classes.dex */
public interface Clock {
    long currentThreadTimeMillis();

    long currentTimeMillis();

    long elapsedRealtime();

    long nanoTime();
}

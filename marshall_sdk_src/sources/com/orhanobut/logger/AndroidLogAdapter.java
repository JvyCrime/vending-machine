package com.orhanobut.logger;

/* JADX INFO: loaded from: classes.dex */
public class AndroidLogAdapter implements LogAdapter {
    private final FormatStrategy formatStrategy;

    @Override // com.orhanobut.logger.LogAdapter
    public boolean isLoggable(int i, String str) {
        return true;
    }

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    public AndroidLogAdapter(FormatStrategy formatStrategy) {
        this.formatStrategy = (FormatStrategy) Utils.checkNotNull(formatStrategy);
    }

    @Override // com.orhanobut.logger.LogAdapter
    public void log(int i, String str, String str2) {
        this.formatStrategy.log(i, str, str2);
    }
}

package com.digitalmediavending.hardware.nayax_sdk_utils.menu;

/* JADX INFO: loaded from: classes.dex */
public class MenuItem {
    private String key;
    private String name;
    private Runnable runnable;

    public MenuItem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public MenuItem(String key, String name, Runnable runnable) {
        this.key = key;
        this.name = name;
        this.runnable = runnable;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }
}

package com.google.firebase.crashlytics.internal.common;

import com.google.firebase.crashlytics.internal.Logger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class KeysMap {
    private final Map<String, String> keys = new HashMap();
    private final int maxEntries;
    private final int maxEntryLength;

    public KeysMap(int i, int i2) {
        this.maxEntries = i;
        this.maxEntryLength = i2;
    }

    public synchronized Map<String, String> getKeys() {
        return Collections.unmodifiableMap(new HashMap(this.keys));
    }

    public synchronized void setKey(String str, String str2) {
        String strSanitizeKey = sanitizeKey(str);
        if (this.keys.size() < this.maxEntries || this.keys.containsKey(strSanitizeKey)) {
            this.keys.put(strSanitizeKey, str2 == null ? "" : sanitizeAttribute(str2));
        } else {
            Logger.getLogger().w("Ignored entry \"" + str + "\" when adding custom keys. Maximum allowable: " + this.maxEntries);
        }
    }

    public synchronized void setKeys(Map<String, String> map) {
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String strSanitizeKey = sanitizeKey(entry.getKey());
            if (this.keys.size() < this.maxEntries || this.keys.containsKey(strSanitizeKey)) {
                String value = entry.getValue();
                this.keys.put(strSanitizeKey, value == null ? "" : sanitizeAttribute(value));
            } else {
                i++;
            }
        }
        if (i > 0) {
            Logger.getLogger().w("Ignored " + i + " entries when adding custom keys. Maximum allowable: " + this.maxEntries);
        }
    }

    private String sanitizeKey(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Custom attribute key must not be null.");
        }
        return sanitizeAttribute(str);
    }

    public String sanitizeAttribute(String str) {
        if (str == null) {
            return str;
        }
        String strTrim = str.trim();
        int length = strTrim.length();
        int i = this.maxEntryLength;
        return length > i ? strTrim.substring(0, i) : strTrim;
    }
}

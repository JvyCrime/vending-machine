package com.google.firebase.crashlytics.internal.unity;

import android.content.Context;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.crashlytics.internal.Logger;
import com.google.firebase.crashlytics.internal.common.CommonUtils;

/* JADX INFO: loaded from: classes.dex */
public class ResourceUnityVersionProvider implements UnityVersionProvider {
    private static final String UNITY_EDITOR_VERSION = "com.google.firebase.crashlytics.unity_version";
    private static boolean isUnityVersionSet = false;
    private static String unityVersion;
    private final Context context;

    public static synchronized String resolveUnityEditorVersion(Context context) {
        if (isUnityVersionSet) {
            return unityVersion;
        }
        int resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, UNITY_EDITOR_VERSION, TypedValues.Custom.S_STRING);
        if (resourcesIdentifier != 0) {
            unityVersion = context.getResources().getString(resourcesIdentifier);
            isUnityVersionSet = true;
            Logger.getLogger().v("Unity Editor version is: " + unityVersion);
        }
        return unityVersion;
    }

    public ResourceUnityVersionProvider(Context context) {
        this.context = context;
    }

    @Override // com.google.firebase.crashlytics.internal.unity.UnityVersionProvider
    public String getUnityVersion() {
        return resolveUnityEditorVersion(this.context);
    }
}

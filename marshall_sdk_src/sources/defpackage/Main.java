package defpackage;

import com.bitmick.marshall.vmc.vmc_framework;
import com.bitmick.utils.Log;

/* JADX INFO: loaded from: classes.dex */
public class Main {
    private static final String TAG = "Main";

    public static void main(String[] strArr) {
        Log.d(vmc_framework.getInstance().getClass().getSimpleName(), String.format("marshall sdk version %s", vmc_framework.get_version()));
    }
}

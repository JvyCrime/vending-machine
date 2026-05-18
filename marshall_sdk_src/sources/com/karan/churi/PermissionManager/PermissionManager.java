package com.karan.churi.PermissionManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class PermissionManager {
    private Activity activity;

    public boolean checkAndRequestPermissions(Activity activity) {
        this.activity = activity;
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        List<String> permission = setPermission();
        ArrayList arrayList = new ArrayList();
        for (String str : permission) {
            if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(activity, (String[]) arrayList.toArray(new String[arrayList.size()]), 1212);
        return false;
    }

    public ArrayList<statusArray> getStatus() {
        ArrayList<statusArray> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (String str : setPermission()) {
            if (ContextCompat.checkSelfPermission(this.activity.getApplicationContext(), str) == 0) {
                arrayList2.add(str);
            } else {
                arrayList3.add(str);
            }
        }
        arrayList.add(new statusArray(arrayList2, arrayList3));
        return arrayList;
    }

    public List<String> setPermission() {
        ArrayList arrayList = new ArrayList();
        try {
            for (String str : this.activity.getApplicationContext().getPackageManager().getPackageInfo(this.activity.getApplicationContext().getPackageName(), 4096).requestedPermissions) {
                arrayList.add(str);
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public void checkResult(int i, String[] strArr, int[] iArr) {
        boolean z;
        boolean z2;
        if (i != 1212) {
            return;
        }
        List<String> permission = setPermission();
        HashMap map = new HashMap();
        Iterator<String> it = permission.iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                break;
            } else {
                map.put(it.next(), 0);
            }
        }
        if (iArr.length > 0) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                map.put(strArr[i2], Integer.valueOf(iArr[i2]));
            }
            Iterator<String> it2 = permission.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (((Integer) map.get(it2.next())).intValue() == -1) {
                        z2 = false;
                        break;
                    }
                } else {
                    z2 = true;
                    break;
                }
            }
            if (z2) {
                return;
            }
            Iterator<String> it3 = permission.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, it3.next())) {
                    z = true;
                    break;
                }
            }
            if (z) {
                ifCancelledAndCanRequest(this.activity);
            } else {
                ifCancelledAndCannotRequest(this.activity);
            }
        }
    }

    public void ifCancelledAndCanRequest(final Activity activity) {
        showDialogOK(activity, "Some Permission required for this app, please grant permission for the same", new DialogInterface.OnClickListener() { // from class: com.karan.churi.PermissionManager.PermissionManager.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i != -1) {
                    return;
                }
                PermissionManager.this.checkAndRequestPermissions(activity);
            }
        });
    }

    public void ifCancelledAndCannotRequest(Activity activity) {
        Toast.makeText(activity.getApplicationContext(), "Go to settings and enable permissions", 1).show();
    }

    private void showDialogOK(Activity activity, String str, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(activity).setMessage(str).setPositiveButton("OK", onClickListener).setNegativeButton("Cancel", onClickListener).create().show();
    }

    public class statusArray {
        public ArrayList<String> denied;
        public ArrayList<String> granted;

        statusArray(ArrayList<String> arrayList, ArrayList<String> arrayList2) {
            this.denied = arrayList2;
            this.granted = arrayList;
        }
    }
}

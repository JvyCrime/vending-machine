package com.digitalmediavending.hardware.permissions;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import com.blankj.utilcode.util.ShellUtils;
import com.digitalmediavending.hardware.MainApp;
import com.digitalmediavending.hardware.ServiceTestActivity;
import com.digitalmediavending.hardware.utils.AppConstants;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class PermissionChecker {
    private static final String TAG = "PermissionChecker";
    public final Activity mActivity;
    private final OnPermissionChecked mListener;
    private ArrayList<Boolean> permissionResultList;
    private final UsbPermissionReceiver usbPermissionReceiver;
    private UsbDevice currentUsbDevice = null;
    private int currentUsbDeviceIndex = 0;
    private final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    public PermissionChecker(Activity mActivity, UsbPermissionReceiver permissionReceiver, OnPermissionChecked mListener) {
        this.mActivity = mActivity;
        this.usbPermissionReceiver = permissionReceiver;
        this.mListener = mListener;
    }

    public void getAllPermission() {
        if (getReadFilePermissions() && getWriteFilePermissions()) {
            if (Build.VERSION.SDK_INT >= 30) {
                if (!Environment.isExternalStorageManager()) {
                    Intent intent = new Intent();
                    intent.setAction("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                    intent.setData(Uri.fromParts("package", this.mActivity.getPackageName(), null));
                    this.mActivity.startActivityForResult(intent, AppConstants.CHECK_PERMISSIONS_REQUEST_CODE);
                    return;
                }
                this.mListener.fileAccessObtained();
                checkDevicePermissions();
                return;
            }
            this.mListener.fileAccessObtained();
            checkDevicePermissions();
            return;
        }
        this.mListener.fileAccessNotGiven();
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != AppConstants.CHECK_PERMISSIONS_REQUEST_CODE || Build.VERSION.SDK_INT < 30) {
            return;
        }
        if (Environment.isExternalStorageManager()) {
            this.mListener.fileAccessObtained();
            checkDevicePermissions();
        } else {
            this.mListener.storageAccessNotGiven();
        }
    }

    private void checkDevicePermissions() {
        MainApp.ResponseLogger.i("PermissionChecker : checkDevicePermissions => Checking USB Access");
        if (this.permissionResultList == null) {
            this.permissionResultList = new ArrayList<>();
        }
        UsbManager usbManager = (UsbManager) this.mActivity.getSystemService("usb");
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        if (deviceList.size() > 0) {
            String str = this.currentUsbDeviceIndex < deviceList.size() ? (String) new ArrayList(deviceList.keySet()).get(this.currentUsbDeviceIndex) : null;
            if (str != null) {
                UsbDevice usbDevice = deviceList.get(str);
                this.currentUsbDevice = usbDevice;
                grantAutomaticUsbPermissionRoot(this.mActivity, usbDevice);
                if (!usbManager.hasPermission(this.currentUsbDevice)) {
                    this.mActivity.registerReceiver(this.usbPermissionReceiver, new IntentFilter("com.android.example.USB_PERMISSION"));
                    ((ServiceTestActivity) this.mActivity).updateUsbReceiver(true);
                    PendingIntent broadcast = PendingIntent.getBroadcast(this.mActivity, this.currentUsbDeviceIndex, new Intent("com.android.example.USB_PERMISSION"), 0);
                    MainApp.ResponseLogger.i("PermissionChecker : requestPermission => Permission required for Device => Product Name : " + this.currentUsbDevice.getProductName());
                    usbManager.requestPermission(this.currentUsbDevice, broadcast);
                    return;
                }
                MainApp.ResponseLogger.i("PermissionChecker : requestPermission => Already has permission for device => Product Name : " + this.currentUsbDevice.getProductName());
                this.permissionResultList.add(true);
                this.currentUsbDeviceIndex = this.currentUsbDeviceIndex + 1;
                checkDevicePermissions();
                return;
            }
            MainApp.ResponseLogger.i("PermissionChecker : requestPermission => Permission check completed for all devices");
            if (this.permissionResultList.size() == deviceList.size()) {
                boolean zContains = this.permissionResultList.contains(false);
                MainApp.ResponseLogger.i("PermissionChecker : requestPermission => Permission given for all => " + zContains);
                this.mListener.usbPermissionGiven(true);
                return;
            }
            this.mListener.usbPermissionGiven(false);
            return;
        }
        this.mListener.noUSBDevicesFound();
    }

    public void handleUsbAccessResult(String action, Intent intent) {
        if ("com.android.example.USB_PERMISSION".equals(action) && intent.getBooleanExtra("permission", false)) {
            MainApp.ResponseLogger.i("PermissionChecker : onReceive =>  Permission given successfully for Device => " + this.currentUsbDevice.getProductName());
            this.permissionResultList.add(true);
        } else {
            MainApp.ResponseLogger.i("PermissionChecker : onReceive => Permission not given for => " + this.currentUsbDevice.getProductName());
            this.permissionResultList.add(false);
        }
        this.currentUsbDeviceIndex++;
        checkDevicePermissions();
    }

    private boolean getReadFilePermissions() {
        return ShellUtils.execCmd("su 0 pm grant com.digitalmediavending.hardware android.permission.READ_EXTERNAL_STORAGE", true).result == 0;
    }

    private boolean getWriteFilePermissions() {
        return ShellUtils.execCmd("su 0 pm grant com.digitalmediavending.hardware android.permission.WRITE_EXTERNAL_STORAGE", true).result == 0;
    }

    public boolean grantAutomaticUsbPermissionRoot(Context context, UsbDevice usbDevice) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            Method declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            declaredMethod.setAccessible(true);
            IBinder iBinder = (IBinder) declaredMethod.invoke(null, "usb");
            Class<?> cls = Class.forName("android.hardware.usb.IUsbManager");
            Method declaredMethod2 = Class.forName("android.hardware.usb.IUsbManager$Stub").getDeclaredMethod("asInterface", IBinder.class);
            declaredMethod2.setAccessible(true);
            Object objInvoke = declaredMethod2.invoke(null, iBinder);
            System.out.println("UID : " + applicationInfo.uid + " " + applicationInfo.processName + " " + applicationInfo.permission);
            Method declaredMethod3 = cls.getDeclaredMethod("grantDevicePermission", UsbDevice.class, Integer.TYPE);
            declaredMethod3.setAccessible(true);
            declaredMethod3.invoke(objInvoke, usbDevice, Integer.valueOf(applicationInfo.uid));
            System.out.println("Method OK : " + iBinder + "  " + objInvoke);
            return true;
        } catch (Exception e) {
            System.err.println("Error trying to assing automatic usb permission : ");
            e.printStackTrace();
            return false;
        }
    }
}

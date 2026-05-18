package com.digitalmediavending.hardware;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.blankj.utilcode.util.ShellUtils;
import com.digitalmediavending.hardware.permissions.OnPermissionChecked;
import com.digitalmediavending.hardware.permissions.PermissionChecker;
import com.digitalmediavending.hardware.permissions.UsbPermissionReceiver;
import com.digitalmediavending.hardware.qrCodeScan.QRCodeScannerService;
import com.digitalmediavending.hardware.utils.LoggerHelp;
import com.digitalmediavending.hardware.wallcoilmachine.WallCoilMachineService;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ServiceTestActivity extends AppCompatActivity {
    private static final String TAG = "ServiceTestActivity";
    private Button allowAccessButton;
    public boolean isUsbPermissionReceiverRegistered = false;
    private PermissionChecker permissionChecker;
    private TextView searchResultText;
    private UsbPermissionReceiver usbPermissionReceiver;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_test_activity);
        this.usbPermissionReceiver = new UsbPermissionReceiver(this);
        if (Build.VERSION.SDK_INT >= 26) {
            getAccess();
        } else {
            startRelatedService();
        }
    }

    public void getAccess() {
        PermissionChecker permissionChecker = new PermissionChecker(this, this.usbPermissionReceiver, new OnPermissionChecked() { // from class: com.digitalmediavending.hardware.ServiceTestActivity.1
            @Override // com.digitalmediavending.hardware.permissions.OnPermissionChecked
            public void fileAccessNotGiven() {
            }

            @Override // com.digitalmediavending.hardware.permissions.OnPermissionChecked
            public void noUSBDevicesFound() {
            }

            @Override // com.digitalmediavending.hardware.permissions.OnPermissionChecked
            public void storageAccessNotGiven() {
            }

            @Override // com.digitalmediavending.hardware.permissions.OnPermissionChecked
            public void usbPermissionGiven(boolean status) {
                if (status) {
                    ServiceTestActivity.this.startRelatedService();
                    ServiceTestActivity.this.moveTaskToBack(true);
                }
            }

            @Override // com.digitalmediavending.hardware.permissions.OnPermissionChecked
            public void fileAccessObtained() {
                MainApp.initLogger();
            }
        });
        this.permissionChecker = permissionChecker;
        permissionChecker.getAllPermission();
    }

    public PermissionChecker getPermissionChecker() {
        return this.permissionChecker;
    }

    public void updateUsbReceiver(boolean isRegistered) {
        this.isUsbPermissionReceiverRegistered = isRegistered;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.permissionChecker.handleActivityResult(requestCode, resultCode, data);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.isUsbPermissionReceiverRegistered) {
            unregisterReceiver(this.usbPermissionReceiver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRelatedService() {
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(new Intent(getApplicationContext(), (Class<?>) WallCoilMachineService.class));
        } else {
            startService(new Intent(getApplicationContext(), (Class<?>) WallCoilMachineService.class));
        }
        moveTaskToBack(true);
    }

    private void initQrCodeScan() {
        final LoggerHelp loggerHelp = new LoggerHelp();
        new QRCodeScannerService(this, new QRCodeScannerService.OnDataFoundInterface() { // from class: com.digitalmediavending.hardware.-$$Lambda$ServiceTestActivity$d8GoEZ-l7-xI1NLXMDMLrBoP27M
            @Override // com.digitalmediavending.hardware.qrCodeScan.QRCodeScannerService.OnDataFoundInterface
            public final void dataFoundListener(JSONObject jSONObject) {
                this.f$0.lambda$initQrCodeScan$0$ServiceTestActivity(loggerHelp, jSONObject);
            }
        }).connectToQrCodeScanner();
    }

    public /* synthetic */ void lambda$initQrCodeScan$0$ServiceTestActivity(LoggerHelp loggerHelp, JSONObject jSONObject) {
        String str = "ServiceTestActivity => initQrCodeScan() =>" + jSONObject;
        loggerHelp.appendLog(str);
        this.searchResultText.setText(str);
    }

    public boolean killApp(String packageName) {
        StringBuilder sb = new StringBuilder();
        sb.append("am force-stop ");
        sb.append(packageName);
        sb.append("\n");
        return ShellUtils.execCmd(sb.toString(), true).result == 0;
    }
}

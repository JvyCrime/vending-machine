package com.digitalmediavending.hardware.wallcoilmachine;

import com.digitalmediavending.hardware.BuildConfig;
import com.digitalmediavending.hardware.MainApp;
import com.digitalmediavending.hardware.SocketSendData;
import com.digitalmediavending.hardware.utils.AppConstants;
import com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface;
import com.orhanobut.logger.Logger;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WallCoilMachineEventHandler implements EventHandlerInterface {
    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void causedException(String s) {
    }

    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void onResetTrays(String s) {
    }

    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void unableToParse(String s) {
    }

    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void onTxData(String s) {
        MainApp.ResponseLogger.service("DATA SENT :: " + s);
        Logger.e("On TX data was called and data was " + s, new Object[0]);
    }

    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void onRxData(String s) {
        MainApp.ResponseLogger.service("DATA RECEIVED :: " + s);
        Logger.e("On RX data was called and data was " + s, new Object[0]);
    }

    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void onHeartBeatReceived(String s) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "heartbeat");
            jSONObject.put("DOOR_STATUS", "CLOSE");
            jSONObject.put("LOCKING_TONGUE_STATUS", "RETRACTION");
            jSONObject.put("payment_terminal", "nayax");
            jSONObject.put("machine_type", "option2xl");
            jSONObject.put("hw_service_version", BuildConfig.VERSION_NAME);
            jSONObject.put("package_name", BuildConfig.APPLICATION_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SocketSendData.sendMsg(jSONObject.toString());
    }

    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void onVendingResponseReceived(String s) {
        Logger.e("Vending was intercepted :: " + s, new Object[0]);
        SocketSendData.sendMsg(s);
    }

    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void onDoorResponse(String s) {
        Logger.e("ON door response was intercepted :: " + s, new Object[0]);
        SocketSendData.sendMsg(s);
    }

    @Override // com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface
    public void onScanTrays(String s) {
        Logger.e("ON scan trays :: " + s, new Object[0]);
        SocketSendData.sendMsg(s);
    }
}

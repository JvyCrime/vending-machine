package com.digitalmediavending.wallcoilmachine.inner;

import com.digitalmediavending.hardware.utils.AppConstants;
import com.digitalmediavending.wallcoilmachine.utils.Constants;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.MessageFormat;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class DataParser {
    private String getAccessControlStatus(char c) {
        return c == '1' ? "TRIGGERED" : "RELEASED";
    }

    private String getLockExtensionStatus(char c) {
        return c == '1' ? "EXTENDED" : "NA";
    }

    private String getLockRetractionStatus(char c) {
        return c == '1' ? "RETRACTED" : "NA";
    }

    public String parseHeatBeatData(List<Integer> list) {
        String strIntToBin = Constants.intToBin(list.get(3).intValue());
        try {
            if (strIntToBin.length() != 8) {
                return null;
            }
            char[] charArray = strIntToBin.toCharArray();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "heartbeat");
            jSONObject.put("RESERVE_BIT", charArray[0] + charArray[1] + charArray[2]);
            jSONObject.put("ACCESS_CONTROL", getAccessControlStatus(charArray[3]));
            jSONObject.put("LOCK_1_OUT_STRETCHED", getLockExtensionStatus(charArray[4]));
            jSONObject.put("LOCK_1_RETRACTED", getLockRetractionStatus(charArray[5]));
            jSONObject.put("LOCK_2_OUT_STRETCHED", getLockExtensionStatus(charArray[6]));
            jSONObject.put("LOCK_2_RETRACTED", getLockRetractionStatus(charArray[7]));
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String parseVendingData(List<Integer> list) {
        int iIntValue = list.get(5).intValue();
        String str = iIntValue != 0 ? iIntValue != 1 ? iIntValue != 2 ? null : "timed_out" : "failed" : FirebaseAnalytics.Param.SUCCESS;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "vendout");
            jSONObject.put("row", Integer.toString(list.get(3).intValue()));
            jSONObject.put("col", Integer.toString(list.get(4).intValue()));
            jSONObject.put("status", str);
            jSONObject.put("status_details", (Object) null);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String parseDoorLockData(List<Integer> list) {
        int iIntValue = list.get(3).intValue();
        String str = iIntValue != 0 ? iIntValue != 1 ? null : "DEVICE_BUSY" : "EXECUTED_IMMEDIATELY";
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "door_operation");
            jSONObject.put("status", str);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String parseScanTrayData(List<Integer> list) {
        try {
            JSONArray jSONArray = new JSONArray();
            if (list.size() == 34) {
                for (int i = 3; i <= 32; i += 3) {
                    int i2 = i / 3;
                    String str = MessageFormat.format("{0}{1}{2}", Constants.intToBin(list.get(i).intValue()), Constants.intToBin(list.get(i + 1).intValue()), Constants.intToBin(list.get(i + 2).intValue()));
                    for (int length = str.length() - 1; length >= 0; length--) {
                        if (str.charAt(length) - '0' == 1) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("row", i2);
                            jSONObject.put("col", str.length() - length);
                            jSONObject.put("usage", 1);
                            jSONArray.put(jSONObject);
                        }
                    }
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(AppConstants.RESPONSE_TYPE_KEY, "responseChannelScan");
            jSONObject2.put("rowColList", jSONArray);
            jSONObject2.put("status", "ready");
            return jSONObject2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String parseResetTrayData(List<Integer> list) {
        int iIntValue = list.get(3).intValue();
        String str = iIntValue != 0 ? iIntValue != 1 ? null : "RESET_FAILED" : "RESET_SUCCESSFUL";
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AppConstants.RESPONSE_TYPE_KEY, "reset_cargo_lane");
            jSONObject.put("status", str);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

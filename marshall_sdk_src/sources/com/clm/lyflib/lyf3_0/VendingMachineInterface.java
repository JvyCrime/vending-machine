package com.clm.lyflib.lyf3_0;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface VendingMachineInterface {
    void onChannelScanRpt(MachineInfo mi);

    void onGetACInfo(ArrayList<Integer> acinfo);

    void onGetChannelType(int row, MachineInfo mi);

    void onGetChannelXy(int row, MachineInfo mi);

    void onGetDevInfo(MachineInfo mi);

    void onHeartBeat(MachineState ms);

    void onRxData(String s);

    void onRxDataAnalyzed(String desc);

    void onTxData(String s, String desc);

    void onVendoutRpt(VendoutInfo vi);
}

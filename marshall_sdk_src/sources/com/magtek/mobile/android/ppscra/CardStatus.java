package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public class CardStatus {
    public byte CardType;
    public byte Status;

    CardStatus() {
        clearData();
    }

    public void clearData() {
        this.Status = (byte) 0;
        this.CardType = (byte) 4;
    }

    public void setStatus(byte b) {
        this.Status = b;
    }

    public void setCardType(byte b) {
        this.CardType = b;
    }

    public byte getStatus() {
        return this.Status;
    }

    public byte getCardType() {
        return this.CardType;
    }
}

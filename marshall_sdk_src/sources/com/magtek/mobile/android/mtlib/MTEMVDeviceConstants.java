package com.magtek.mobile.android.mtlib;

import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;

/* JADX INFO: loaded from: classes.dex */
public class MTEMVDeviceConstants {
    public static final byte CARD_TYPE_CONTACTLESS_SMART_CARD = 4;
    public static final byte CARD_TYPE_CONTACT_SMART_CARD = 2;
    public static final byte CARD_TYPE_MAGNETIC_STRIPE = 1;
    public static final byte PROTOCOL_EXTENDER_REQUEST = 73;
    public static final byte PROTOCOL_EXTENDER_RESPONSE = 10;
    public static final byte SELECTION_STATUS_CANCELLED = 1;
    public static final byte SELECTION_STATUS_COMPLETED = 0;
    public static final byte SELECTION_STATUS_TIMED_OUT = 2;
    public static final byte SELECTION_TYPE_APPLICATION = 0;
    public static final byte SELECTION_TYPE_LANGUAGE = 1;
    public static final byte TRANSACTION_OPION_ACQUIRER_NOT_AVAILABLE = 4;
    public static final byte TRANSACTION_OPION_BYPASS_PIN = 1;
    public static final byte TRANSACTION_OPION_FORCE_ONLINE = 2;
    public static final byte TRANSACTION_OPION_NORMAL = 0;
    public static final byte TRANSACTION_TYPE_CASHBACK = 2;
    public static final byte TRANSACTION_TYPE_CASHBACK_CONTACTLESS = 9;
    public static final byte TRANSACTION_TYPE_CASH_ADVANCE = 1;
    public static final byte TRANSACTION_TYPE_DOMESTIC_CASH = -128;
    public static final byte TRANSACTION_TYPE_GOODS = 4;
    public static final byte TRANSACTION_TYPE_INTERNATIONAL_CASH = 64;
    public static final byte TRANSACTION_TYPE_INTERNATIONAL_GOODS = 16;
    public static final byte TRANSACTION_TYPE_PAYMENT = 0;
    public static final byte TRANSACTION_TYPE_REFUND = 32;
    public static final byte TRANSACTION_TYPE_SERVICE = 8;
    public static final byte[] EMV_COMMAND_START_TRANSACTION = {3, 0};
    public static final byte[] EMV_COMMAND_GET_STATUS = {3, 1};
    public static final byte[] EMV_COMMAND_SET_USER_SELECTION_RESULT = {3, 2};
    public static final byte[] EMV_COMMAND_SET_ACQUIRER_RESPONSE = {3, 3};
    public static final byte[] EMV_COMMAND_CANCEL_TRANSACTION = {3, 4};
    public static final byte[] EMV_COMMAND_SET_TERMINAL_CONFIGURATION = {3, 5};
    public static final byte[] EMV_COMMAND_GET_TERMINAL_CONFIGURATION = {3, 6};
    public static final byte[] EMV_COMMAND_SET_APPLICATION_CONFIGURATION = {3, 7};
    public static final byte[] EMV_COMMAND_GET_APPLICATION_CONFIGURATION = {3, 8};
    public static final byte[] EMV_COMMAND_SET_ACQUIRER_CA_PULIC_KEY = {3, 9};
    public static final byte[] EMV_COMMAND_GET_ACQUIRER_CA_PULIC_KEY = {3, 10};
    public static final byte[] EMV_COMMAND_GET_KERNEL_INFORMATION = {3, 11};
    public static final byte[] EMV_COMMAND_SET_DATE_TIME = {3, 12};
    public static final byte[] EMV_COMMAND_GET_DATE_TIME = {3, 13};
    public static final byte[] EMV_COMMAND_COMMIT_CONFIGURATION = {3, 14};
    public static final byte[] EMV_EVENT_TRANSACTION_STATUS = {3, 0};
    public static final byte[] EMV_EVENT_DISPLAY_MESSAGE_REQUEST = {3, 1};
    public static final byte[] EMV_EVENT_USER_SELECTION_REQUEST = {3, 2};
    public static final byte[] EMV_EVENT_ARQC_RECEIVED = {3, 3};
    public static final byte[] EMV_EVENT_TRANSACTION_RESULT = {3, 4};
    public static final byte[] CURRENCY_US_DOLLAR = {8, 64};
    public static final byte[] CURRENCY_EURO = {9, PPSCRADeviceValues.FUNCTION_KEY_ENTER};
    public static final byte[] CURRENCY_UK_POUND = {8, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM};
}

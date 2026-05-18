package com.magtek.mobile.android.mtusdk;

import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;

/* JADX INFO: loaded from: classes.dex */
public class EMVLanguage {
    public static EMVLanguage LANGUAGE_CHINESE;
    public static EMVLanguage[] LANGUAGE_LIST;
    private byte[] m_code;
    private String m_name;
    public static EMVLanguage LANGUAGE_ENGLISH = new EMVLanguage(new byte[]{marshall_t.marshalll_display_control_button_id_menu, 110}, "English");
    public static EMVLanguage LANGUAGE_FRENCH = new EMVLanguage(new byte[]{marshall_t.marshalll_display_control_button_id_right_arrow, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE}, "Français");
    public static EMVLanguage LANGUAGE_GERMAN = new EMVLanguage(new byte[]{marshall_t.marshalll_display_control_button_id_left_arrow, marshall_t.marshalll_display_control_button_id_menu}, "Deutsch");
    public static EMVLanguage LANGUAGE_ITALIAN = new EMVLanguage(new byte[]{marshall_t.marshalll_display_control_button_id_right_arrow, PPSCRADeviceValues.FUNCTION_KEY_RIGHT}, "Italiano");
    public static EMVLanguage LANGUAGE_SPANISH = new EMVLanguage(new byte[]{marshall_t.marshalll_display_control_button_id_menu, 115}, "Español");

    static {
        EMVLanguage eMVLanguage = new EMVLanguage(new byte[]{122, marshall_t.marshalll_display_control_button_id_back}, "中文");
        LANGUAGE_CHINESE = eMVLanguage;
        LANGUAGE_LIST = new EMVLanguage[]{LANGUAGE_ENGLISH, LANGUAGE_FRENCH, LANGUAGE_GERMAN, LANGUAGE_ITALIAN, LANGUAGE_SPANISH, eMVLanguage};
    }

    public static EMVLanguage GetLanguage(byte[] bArr) {
        if (bArr != null && bArr.length == 2) {
            int i = 0;
            while (true) {
                EMVLanguage[] eMVLanguageArr = LANGUAGE_LIST;
                if (i >= eMVLanguageArr.length) {
                    break;
                }
                byte[] code = eMVLanguageArr[i].getCode();
                if (bArr[0] == code[0] && bArr[1] == code[1]) {
                    return LANGUAGE_LIST[i];
                }
                i++;
            }
        }
        return null;
    }

    public EMVLanguage(byte[] bArr, String str) {
        this.m_code = bArr != null ? (byte[]) bArr.clone() : null;
        this.m_name = str;
    }

    public EMVLanguage(EMVLanguage eMVLanguage) {
        byte[] bArr = eMVLanguage.m_code;
        this.m_code = bArr != null ? (byte[]) bArr.clone() : null;
        this.m_name = eMVLanguage.m_name;
    }

    public byte[] getCode() {
        return this.m_code;
    }

    public void setCode(byte[] bArr) {
        this.m_code = bArr != null ? (byte[]) bArr.clone() : null;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String str) {
        this.m_name = str;
    }
}

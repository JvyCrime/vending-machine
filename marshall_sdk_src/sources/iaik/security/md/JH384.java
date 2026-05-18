package iaik.security.md;

import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class JH384 extends AbstractC0032a {
    private static final byte[] b = {72, 30, 59, -58, -40, 19, 57, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, 109, 59, 94, PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, marshall_t.status_vpos_please_insert_or_swipe_card, -34, -121, -101, 99, -6, -22, marshall_t.marshalll_display_control_button_id_back, -44, -128, -83, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, 51, 44, -53, 33, 72, 15, -126, marshall_t.marshalll_display_control_button_id_cancel, -104, -82, -56, 77, PPSCRADeviceValues.ACK_STATUS_CERT_NON_EXIST, -126, -71, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -44, 85, -22, 48, marshall_t.status_vpos_processing_error, 17, marshall_t.status_vpos_please_remove_card, 73, 54, -11, 85, -78, PPSCRADeviceValues.ACK_STATUS_INVALID_CERT_CRL_MESSAGE, 72, marshall_t.status_vpos_present_card, -20, -57, 37, 10, PPSCRADeviceValues.ACK_STATUS_REVOKED_CERT_CRL, -70, -12, 60, -31, 86, -101, ByteCompanionObject.MAX_VALUE, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, -37, marshall_t.status_vpos_try_again, 76, -98, -4, -67, 73, 99, -105, -81, 14, 88, -97, -62, 125, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, -128, -51, -128, PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP, PPSCRADeviceValues.ACK_STATUS_AMOUNT_NEEDED, -116, -99, -21, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, -38, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, 121, -127, -24, -8, -43, 55, 58, -12, 57, marshall_t.marshalll_display_control_button_id_cancel, -83, -35, -47, 122, PPSCRADeviceValues.FUNCTION_KEY_LEFT, -87, -76, -45, -67, -92, 117, -45, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, -105, 108, 63, -70, -104, marshall_t.status_vpos_please_remove_card, 115, ByteCompanionObject.MAX_VALUE};

    public JH384() {
        super(a());
    }

    private static AbstractC0044m a() {
        return a == 32 ? new C0045n(48, 64, b) : new C0046o(48, 64, b);
    }
}

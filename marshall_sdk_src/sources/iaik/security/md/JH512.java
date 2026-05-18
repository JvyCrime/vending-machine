package iaik.security.md;

import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.mtlib.MTAudioReader;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;

/* JADX INFO: loaded from: classes.dex */
public final class JH512 extends AbstractC0032a {
    private static final byte[] b = {111, -47, marshall_t.status_vpos_please_present_one_card_only, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, marshall_t.status_vpos_not_accepted, 0, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, 99, marshall_t.marshalll_display_control_button_id_touch, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, 5, 122, 21, -43, 67, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, 34, 94, -115, 12, -105, -17, 11, -23, 52, 18, 89, -14, -77, -61, 97, PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, 29, -96, -63, marshall_t.status_vpos_present_card_again, 111, -128, 30, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, -87, 5, 107, -22, 43, 109, -128, 88, -114, -52, -37, 32, 117, -70, -90, -87, 15, 58, 118, -70, -8, 59, -9, 1, 105, -26, 5, marshall_t.status_vpos_processing_error, -29, marshall_t.status_vpos_please_insert_or_swipe_card, 105, 70, -75, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, -114, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, 111, -26, 90, 16, marshall_t.status_vpos_present_card, -89, -48, -63, -124, 60, 36, 59, 110, PPSCRADeviceValues.FUNCTION_KEY_LEFT, -79, 45, 90, -63, MTAudioReader.PACKET_ID_LAST, -49, 87, -10, -20, -99, -79, -8, 86, -89, 6, -120, 124, 87, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, -79, 86, -29, -62, -4, -33, -26, -123, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, -5, marshall_t.status_vpos_insert_or_swipe_another_card, 90, 70, PPSCRADeviceValues.FUNCTION_KEY_ENTER, -52, -116, -35, marshall_t.status_vpos_please_present_one_card_only};

    public JH512() {
        super(a());
    }

    private static AbstractC0044m a() {
        return a == 32 ? new C0045n(64, 64, b) : new C0046o(64, 64, b);
    }
}

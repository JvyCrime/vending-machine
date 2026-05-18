package iaik.security.md;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class JH256 extends AbstractC0032a {
    private static final byte[] b = {-21, -104, -93, marshall_t.status_vpos_processing_error, 44, 32, -45, -21, PPSCRADeviceValues.ACK_STATUS_INVALID_CERT_CRL_MESSAGE, -51, -66, 123, -100, -78, marshall_t.status_vpos_try_again, -63, 28, PPSCRADeviceValues.ACK_STATUS_REVOKED_CERT_CRL, 81, -111, mdb_t.mdb_dev_addr_cashless_b, -44, -57, -6, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, 0, -126, -42, 126, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, 3, -92, 35, -98, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, 119, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, -71, marshall_t.status_vpos_try_again, marshall_t.marshall_func_code_ev_ext_ev_info_req, -5, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, 72, -44, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, 119, -51, -75, -85, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, 2, 107, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, 122, 86, marshall_t.marshall_packet_option_rfu_mask, 36, marshall_t.status_vpos_please_remove_card, 15, -1, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, PPSCRADeviceValues.FUNCTION_KEY_LEFT, -93, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, ByteCompanionObject.MAX_VALUE, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, 77, 117, 29, 20, 73, 8, -9, 125, -30, 98, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, 118, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -9, 118, 36, -113, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, -121, -43, -74, 87, marshall_t.status_vpos_present_card, -128, PPSCRADeviceValues.RESPONSE_SEND_BIG_BLOCK_DATA_TO_HOST, 108, 92, 94, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, 45, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, -114, 13, 108, 81, -124, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, -58, 87, 5, 122, 15, 123, -28, -45, marshall_t.marshalll_display_control_button_id_cancel, 112, 36, 18, -22, PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, -29, -85, 19, -45, 28, -41, 105};

    public JH256() {
        super(a());
    }

    private static AbstractC0044m a() {
        return a == 32 ? new C0045n(32, 64, b) : new C0046o(32, 64, b);
    }
}

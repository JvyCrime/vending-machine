package iaik.security.md;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_rsp_t;
import com.bitmick.marshall.vmc.mdb_t;
import com.magtek.mobile.android.mtlib.MTAudioReader;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class JH224 extends AbstractC0032a {
    private static final byte[] b = {45, -2, -35, 98, -7, -102, -104, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, -82, 124, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, -42, PPSCRADeviceValues.COMMAND_SET_GET_EXTENDED_DEVICE_CONFIGURATION, -42, 52, -25, -92, -125, 16, 5, -68, 48, 18, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, -72, mdb_t.mdb_dev_addr_cashless_b, marshall_t.status_vpos_card_error, -58, -55, marshall_t.marshalll_display_control_button_id_right_arrow, 20, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, marshall_t.marshalll_display_control_button_id_right_arrow, -39, PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, -97, 37, -128, 112, 111, -50, -98, -93, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, 29, -101, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, -36, 17, -24, 50, 95, 123, 54, 110, 16, -7, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, -123, ByteCompanionObject.MAX_VALUE, 2, -6, 6, -63, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, marshall_t.status_vpos_please_insert_card, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, 92, -40, -56, 64, -77, -105, -10, -95, ByteCompanionObject.MAX_VALUE, 110, 115, -128, MTAudioReader.PACKET_ID_LAST, -36, -33, PPSCRADeviceValues.ACK_STATUS_REVOKED_CERT_CRL, -91, -83, -22, -93, -45, -92, 49, -24, -34, -55, marshall_t.status_vpos_present_card_again, -102, marshall_t.marshalll_display_control_button_id_back, 34, -76, -87, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, -20, -122, -95, -28, -43, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -100, -27, 108, marshall_t.marshall_packet_option_rfu_mask, 21, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, 13, -22, -75, -85, 43, -65, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, 17, -36, marshall_t.marshall_packet_option_rfu_mask, -35, marshall_t.marshalll_display_control_button_id_left_arrow, -22, 110};

    public JH224() {
        super(a());
    }

    private static AbstractC0044m a() {
        return a == 32 ? new C0045n(28, 64, b) : new C0046o(28, 64, b);
    }
}

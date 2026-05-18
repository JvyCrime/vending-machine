package iaik.security.dsa;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_rsp_t;
import com.bitmick.marshall.vmc.mdb_t;
import com.magtek.mobile.android.mtlib.MTAudioReader;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.security.random.SecRandom;
import iaik.security.ssl.SSLContext;
import iaik.utils.NumberTheory;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class DSAKeyPairGenerator extends KeyPairGenerator implements java.security.interfaces.DSAKeyPairGenerator {
    static Class e;
    private static final byte[] f = {0, -63, 108, -70, -45, 77, marshall_t.status_vpos_present_card, 94, -59, 57, marshall_t.marshalll_display_control_button_id_right_arrow, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -42, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, -68, PPSCRADeviceValues.ACK_STATUS_AMOUNT_NEEDED, -60, 126, 89, -114, 35, -75, -87, -41, -59, -50, -56, 45, marshall_t.marshalll_display_control_button_id_menu, -74, -126, 125, marshall_t.status_vpos_please_use_mag, -23, marshall_t.status_vpos_present_card_again, PPSCRADeviceValues.FUNCTION_KEY_ENTER, 72, marshall_t.status_vpos_present_card, 48, PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP, -65, -15, -12, -53, 86, -12, 124, 110, 81, 5, marshall_t.status_vpos_please_present_one_card_only, -24, PPSCRADeviceValues.ACK_STATUS_INVALID_CERT_CRL_MESSAGE, 0, -13, 13, 67, -36, marshall_t.status_vpos_please_insert_card, -17, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, 36, -44, marshall_t.marshalll_display_control_button_id_right_arrow, 91};
    private static final byte[] g = {0, -73, -72, 16, -75, -116, 9, 52, -10, marshall_t.status_vpos_please_remove_card, -121, -113, 54, 11, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, -41, -52, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, -75, marshall_t.status_vpos_not_accepted, 77};
    private static final byte[] h = {76, marshall_t.status_vpos_present_card_again, -57, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, -67, -65, -69, -90, marshall_t.status_vpos_insert_or_swipe_another_card, -99, 126, 115, PPSCRADeviceValues.COMMAND_SET_GET_EXTENDED_DEVICE_CONFIGURATION, 57, -58, -55, 58, -122, -102, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, -59, -37, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, -70, 60, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, 88, -99, 123, marshall_t.status_vpos_not_accepted, 0, 63, -89, 53, -14, PPSCRADeviceValues.ACK_STATUS_CERT_NON_EXIST, -49, -48, 122, marshall_t.status_vpos_not_accepted, -15, 15, 53, 21, 95, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, -9, 3, 53, -81, 123, marshall_t.marshalll_display_control_button_id_touch, marshall_t.status_vpos_see_phone_for_instructions, 17, -95, 16, 53, 24, -5, -92, marshall_t.status_vpos_try_another_card, -105, 24};
    private static final byte[] i = {0, -28, -3, -67, -20, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, 58, -72, -122, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, 109, 95, -18, marshall_t.marshalll_display_control_button_id_cancel, 72, -78, 63, -52, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, marshall_t.marshalll_display_control_button_id_menu, -1, -58, 0, -51, 1, marshall_t.marshall_func_code_ev_ext_ev_info_req, 51, marshall_t.marshalll_display_control_button_id_back, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, -75, 72, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, 108, -104, -12, -41, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, -69, 125, PPSCRADeviceValues.ACK_STATUS_CERT_NON_EXIST, -52, -77, marshall_t.marshalll_display_control_button_id_touch, marshall_t.status_vpos_try_again, -11, 90, 5, -69, -96, 109, -35, -43, marshall_t.status_vpos_insert_card, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, marshall_t.status_vpos_processing_error, PPSCRADeviceValues.COMMAND_SET_GET_EXTENDED_DEVICE_CONFIGURATION, mdb_t.mdb_dev_addr_cashless_b, -38, 9, 111, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, 7, 95, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, 51, -65, -113, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, 59, -22, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, -54, marshall_t.status_vpos_present_card_again, 2, marshall_t.marshalll_display_control_button_id_touch, 67, -79, 124, -100, 0, -3, 81, -74, -98, -92, 4, PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP, -42, 9, 107, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, marshall_t.marshalll_display_control_button_id_menu, marshall_t.status_vpos_please_present_one_card_only, -35, 43};
    private static final byte[] j = {0, -125, -36, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -78, 5, marshall_t.marshalll_display_control_button_id_touch, 105, -92, -29, -120, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, -77, 4, marshall_t.marshalll_display_control_button_id_touch, 119, 2, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, 125, 105};
    private static final byte[] k = {98, -7, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, 90, 6, -5, 15, -93, -87, marshall_t.status_vpos_try_another_card, -74, -26, -40, -3, marshall_t.status_vpos_try_another_card, -56, 28, marshall_t.marshall_func_code_ev_ext_ev_info_req, marshall_t.status_vpos_please_present_one_card_only, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, marshall_t.marshalll_display_control_button_id_right_arrow, -12, -62, -25, marshall_t.marshall_func_code_ev_ext_ev_info_req, -4, 43, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, 81, 37, PPSCRADeviceValues.RESPONSE_SEND_BIG_BLOCK_DATA_TO_HOST, -45, marshall_t.status_vpos_card_error, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, -62, -90, -65, PPSCRADeviceValues.ACK_STATUS_CERT_NON_EXIST, 58, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, -24, 64, marshall_t.marshalll_display_control_button_id_right_arrow, marshall_t.status_vpos_please_remove_card, -22, 76, -14, -98, ByteCompanionObject.MAX_VALUE, -42, -47, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, 115, marshall_t.status_vpos_please_insert_card, 6, marshall_t.status_vpos_present_card_again, 91, -5, -47, -90, 28, 54, -15, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -70, -12, 122, 30, 98, 13, -6, -36, -11, 108, 118, -28, -116, marshall_t.status_vpos_present_card, -100, -62, 12, 43, -5, -77, 111, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, -116, 54, -125, marshall_t.status_vpos_try_another_card, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, -56, marshall_t.status_vpos_please_use_mag, 32, -48};
    private static final byte[] l = {0, -126, 125, -44, -100, -94, 5, 105, -124, -23, -125, PPSCRADeviceValues.FUNCTION_KEY_LEFT, -79, 52, 13, 93, PPSCRADeviceValues.FUNCTION_KEY_LEFT, -125, PPSCRADeviceValues.ACK_STATUS_INVALID_CERT_CRL_MESSAGE, -123, -78, 90, -54, -93, -126, -41, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, marshall_t.status_vpos_card_error, 110, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, 64, -124, 63, 10, 70, 122, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, 117, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, -63, -54, 59, 112, -70, marshall_t.marshalll_display_control_button_id_touch, -105, 7, 18, -10, -79, MTAudioReader.PACKET_ID_LAST, -19, marshall_t.status_vpos_not_accepted, -20, marshall_t.status_vpos_present_card_again, 19, -13, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, 10, marshall_t.marshalll_display_control_button_id_cancel, -69, -42, -97, marshall_t.status_vpos_card_error, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, PPSCRADeviceValues.RESPONSE_SEND_BIG_BLOCK_DATA_TO_HOST, 97, -85, 2, marshall_t.status_vpos_insert_card, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, -95, 51, 60, marshall_t.status_vpos_see_phone_for_instructions, 35, 93, -97, -73, -47, 14, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -29, -91, 94, -7, -80, marshall_t.status_vpos_please_insert_card, -57, -55, 32, -59, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, -38, 122, -61, -43, 15, 36, 13, -69, -114, marshall_t.status_vpos_insert_or_swipe_another_card, -38, -98, -69, 112, 33, 17, -59, 53, -126, -27, 53, -123, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, -97, 89, 57, 121, -77, 50, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, -56, -122, -125, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, PPSCRADeviceValues.COMMAND_SET_GET_EXTENDED_DEVICE_CONFIGURATION, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE};
    private static final byte[] m = {0, -6, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, 121, -38, -6, 63, 58, -79, -24, 10, 109, -11, -67, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, -14, 36, -40, -8, -41, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv};
    private static final byte[] n = {marshall_t.status_vpos_please_insert_card, -67, -11, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, 51, 4, marshall_t.marshall_packet_option_rfu_mask, 81, -63, 124, -91, 92, PPSCRADeviceValues.ACK_STATUS_REVOKED_CERT_CRL, -127, -75, -63, 125, 76, 32, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, 118, -123, 52, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, -49, -39, -4, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, -78, -31, -78, -79, 111, -96, 16, 72, -72, -1, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, -25, -87, 10, -31, marshall_t.marshall_func_code_ev_ext_ev_info_req, 24, 5, marshall_t.status_vpos_not_accepted, 52, -39, -43, 97, -33, PPSCRADeviceValues.FUNCTION_KEY_LEFT, 76, -56, -36, PPSCRADeviceValues.ACK_STATUS_INVALID_CERT_CRL_MESSAGE, -79, 81, -75, -33, marshall_t.marshalll_display_control_button_id_right_arrow, 89, 112, 107, 94, 87, -61, PPSCRADeviceValues.COMMAND_SET_GET_EXTENDED_DEVICE_CONFIGURATION, -94, -42, 88, 59, 125, 50, -46, -23, -31, -15, marshall_t.marshalll_display_control_button_id_right_arrow, marshall_t.status_vpos_not_accepted, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, 70, 13, -51, marshall_t.status_vpos_try_another_card, marshall_t.marshalll_display_control_button_id_cancel, 112, 54, -9, -7, -66, 11, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, -96, 93, 105, 93, 91, -127, 19, -87, 3, -53, marshall_t.status_vpos_card_error, 99, 86, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, -67, 54, marshall_t.status_vpos_please_insert_or_swipe_card, 93, 108, 21, marshall_t.marshalll_display_control_button_id_right_arrow, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, -6, 16, -93, 32, MTAudioReader.PACKET_ID_LAST, -31, -46, 52, 119, 19};
    private static final byte[] o = {0, -38, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, marshall_t.status_vpos_please_use_mag, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, 105, -57, 51, 125, marshall_t.status_vpos_not_accepted, 105, marshall_t.status_vpos_insert_or_swipe_another_card, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, -104, 70, -79, 108, -87, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, 111, -96, -76, -6, 6, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, -63, 123, -123, 58, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -44, -85, -68, 30, -21, -102, 124, 119, PPSCRADeviceValues.FUNCTION_KEY_LEFT, 60, -91, -82, -17, -7, 44, marshall_t.status_vpos_present_card, PPSCRADeviceValues.RESPONSE_SEND_BIG_BLOCK_DATA_TO_HOST, 17, mdb_t.mdb_dev_addr_cashless_b, 67, -20, -71, 1, PPSCRADeviceValues.ACK_STATUS_AMOUNT_NEEDED, 55, 51, 85, PPSCRADeviceValues.ACK_STATUS_CERT_NON_EXIST, marshall_t.marshalll_display_control_button_id_left_arrow, 43, 97, -42, 115, -85, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, -67, -2, PPSCRADeviceValues.ACK_STATUS_REVOKED_CERT_CRL, -14, -14, 31, -21, -122, marshall_t.marshalll_display_control_button_id_left_arrow, 97, -71, 6, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, -29, 35, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, -51, -36, -122, 85, marshall_t.status_vpos_please_insert_card, 7, -57, -68, -90, -128, -46, 122, 123, -10, PPSCRADeviceValues.FUNCTION_KEY_ENTER, -98, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -49, 35, -111, -1, -23, -3, -104, -80, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, -18, 73, 112, 15, -126, 51, 44, 21, -123, 1, -80, 31, -81, 4, -6, -125, 7, -56, 16, marshall_t.status_vpos_insert_card, -39, 115, 50, 30, PPSCRADeviceValues.RESPONSE_SEND_BIG_BLOCK_DATA_TO_HOST, 111, PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP, -98, -35, -24, -12, 8, marshall_t.marshall_packet_option_rfu_mask, -10, PPSCRADeviceValues.ACK_STATUS_AMOUNT_NEEDED, -74, marshall_t.status_vpos_try_again, 108, 88, marshall_t.status_vpos_please_use_mag, 34, mdb_t.mdb_dev_addr_cashless_b, 35, 92, 123, -3, marshall_t.marshall_packet_option_rfu_mask, -29, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, -10, -28, -1, 13, -14, -33, marshall_t.marshalll_display_control_button_id_touch, -19, -111, 28, -39, 112, marshall_t.status_vpos_card_error, marshall_t.status_vpos_please_use_mag, 7, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -42, -61, 29, -4, 5, -27, 92, PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP, -12, PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, -77, 76, -43, 93, PPSCRADeviceValues.ACK_STATUS_REVOKED_CERT_CRL, -96, 94, 53, -12, -7, marshall_t.status_vpos_present_card_again, marshall_t.status_vpos_see_phone_for_instructions, marshall_t.status_vpos_insert_or_swipe_another_card, -120, 107, 87, 94, 126, -125, -69, marshall_t.marshalll_display_control_button_id_back, 58, 119, 64, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, 20, -89, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, -20, 13, -42, -54, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, -43, -79, -50, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, 32, -116, 73, marshall_t.status_vpos_present_card, marshall_t.status_vpos_see_phone_for_instructions, 37, -83, -85, 119, -44, 98, -8, 92, -29, -30, marshall_t.marshalll_display_control_button_id_menu, 37, -95, -24, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, ByteCompanionObject.MAX_VALUE, -91, 63, -113, 18, -116, marshall_t.status_vpos_please_use_mag, 81, 10, -34, marshall_t.status_vpos_card_error, 73, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, -111, -75, -34, -111};
    private static final byte[] p = {0, -96, -97, 108, -80, -115, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, -47, -94, 77, 98, marshall_t.marshalll_display_control_button_id_menu, 92, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, -18, 43, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, 99, -111, 93, 91};
    private static final byte[] q = {0, -128, 123, -8, -59, -28, 57, marshall_t.status_vpos_please_insert_or_swipe_card, 35, 15, marshall_t.marshalll_display_control_button_id_menu, 28, -59, 30, -47, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, 58, -77, -80, -127, 60, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, 92, -120, 99, -62, 17, 77, 5, ByteCompanionObject.MAX_VALUE, 51, 90, 70, 111, 67, -71, -71, -3, 2, 124, 6, 12, marshall_t.status_vpos_please_present_one_card_only, -50, -116, 29, -89, -7, -6, -105, 45, -79, marshall_t.marshall_func_code_ev_ext_ev_info_req, -77, 111, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, marshall_t.status_vpos_try_another_card, 45, 0, -85, 121, -23, -28, 88, -91, marshall_t.marshalll_display_control_button_id_right_arrow, -31, -77, marshall_t.status_vpos_please_use_mag, -33, marshall_t.marshall_func_code_ev_ext_ev_info_req, 4, 45, 77, -40, -21, -127, -54, marshall_t.status_vpos_try_again, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, -40, marshall_t.marshall_func_code_ev_ext_ev_info_req, -45, -80, -10, -17, -127, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, 63, -97, -93, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, -80, -73, -97, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, 124, marshall_t.marshalll_display_control_button_id_touch, 119, PPSCRADeviceValues.ACK_STATUS_AMOUNT_NEEDED, -58, -100, 43, 67, 91, -92, -11, PPSCRADeviceValues.RESPONSE_SEND_BIG_BLOCK_DATA_TO_HOST, marshall_t.status_vpos_try_again, 67, 125, -45, 124, -1, 35, -70, -115, -80, -10, -101, 89, -42, marshall_t.status_vpos_please_remove_card, PPSCRADeviceValues.ACK_STATUS_AMOUNT_NEEDED, -101, 58, -95, 108, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, -58, -5, PPSCRADeviceValues.ACK_STATUS_AMOUNT_NEEDED, marshall_t.status_vpos_present_card_again, -33, -74, -3, -41, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, -94, 117, -91, -24, marshall_t.status_vpos_present_card, 30, 14, 43, -90, marshall_t.status_vpos_please_insert_or_swipe_card, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, -2, -66, -5, -9, -53, -71, 43, 11, 53, -85, PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, 16, marshall_t.status_vpos_try_another_card, marshall_t.status_vpos_insert_card, 16, 33, marshall_t.marshalll_display_control_button_id_right_arrow, 117, 29, -128, -9, marshall_t.marshall_packet_option_rfu_mask, -79, 32, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, 21, 10, 99, 76, 31, -97, marshall_t.marshalll_display_control_button_id_left_arrow, 91, -28, 8, 92, 122, -33, 5, 2, -99, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, -33, marshall_t.marshalll_display_control_button_id_left_arrow, -70, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, -125, 34, -50, 21, 18, 54, -90, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, PPSCRADeviceValues.FUNCTION_KEY_ENTER, 11, marshall_t.status_vpos_processing_error, 99, 64, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, 32, 55, 14, -78, -83, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, -96, -45, -22, -1, PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO, 8, marshall_t.status_vpos_please_use_mag, -55, PPSCRADeviceValues.ACK_STATUS_AMOUNT_NEEDED, -104, 17, -100, 87, 88, 86, 98, marshall_t.status_vpos_please_insert_or_swipe_card, 110, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, -60, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, PPSCRADeviceValues.RESPONSE_SEND_BIG_BLOCK_DATA_TO_HOST, -17, marshall_t.marshalll_display_control_button_id_touch, -56, -113, -97, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, -70, 19, -85, 81, 21, 76, PPSCRADeviceValues.FUNCTION_KEY_ENTER, -63, 115, -60, 126};
    private static HashMap r;
    SecureRandom a;
    int b;
    java.security.interfaces.DSAParams c;
    boolean d;

    static {
        b();
    }

    public DSAKeyPairGenerator() {
        this("DSA");
    }

    public DSAKeyPairGenerator(String str) {
        super(str);
        this.a = null;
        this.b = -1;
        this.d = false;
        this.c = null;
    }

    private static void b() {
        r = new HashMap();
        b(512, new DSAParams(new BigInteger(f), new BigInteger(g), new BigInteger(h)));
        b(SSLContext.VERSION_SSL30, new DSAParams(new BigInteger(i), new BigInteger(j), new BigInteger(k)));
        b(1024, new DSAParams(new BigInteger(l), new BigInteger(m), new BigInteger(n)));
        b(2048, new DSAParams(new BigInteger(o), new BigInteger(p), new BigInteger(q)));
    }

    private static void b(int i2, java.security.interfaces.DSAParams dSAParams) {
        r.put(new Integer(i2), dSAParams);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    BigInteger a(java.security.interfaces.DSAParams dSAParams, BigInteger bigInteger) {
        return dSAParams.getG().modPow(bigInteger, dSAParams.getP());
    }

    java.security.interfaces.DSAParams a(int i2) {
        return (java.security.interfaces.DSAParams) r.get(new Integer(i2));
    }

    java.security.interfaces.DSAParams a(java.security.interfaces.DSAParams dSAParams) {
        return dSAParams instanceof DSAParams ? dSAParams : new DSAParams(dSAParams);
    }

    void a() {
        if (this.b == -1) {
            this.b = 1024;
        }
        if (this.b < 512) {
            this.b = 512;
        }
        if (this.b > 4096) {
            this.b = 4096;
        }
        this.b &= 65472;
    }

    void a(int i2, java.security.interfaces.DSAParams dSAParams) {
        synchronized (r) {
            b(i2, dSAParams);
        }
    }

    BigInteger b(java.security.interfaces.DSAParams dSAParams) {
        BigInteger q2 = dSAParams.getQ();
        return new BigInteger(q2.bitLength() + 8, this.a).mod(q2.subtract(NumberTheory.ONE)).add(NumberTheory.ONE);
    }

    java.security.interfaces.DSAParams b(int i2) {
        java.security.interfaces.DSAParams dSAParamsA = a(i2);
        if (this.d || dSAParamsA == null) {
            try {
                DSAParameterGenerator dSAParameterGenerator = new DSAParameterGenerator();
                dSAParameterGenerator.engineInit(i2, this.a);
                AlgorithmParameters algorithmParametersEngineGenerateParameters = dSAParameterGenerator.engineGenerateParameters();
                Class clsClass$ = e;
                if (clsClass$ == null) {
                    clsClass$ = class$("java.security.spec.DSAParameterSpec");
                    e = clsClass$;
                }
                dSAParamsA = (DSAParameterSpec) algorithmParametersEngineGenerateParameters.getParameterSpec(clsClass$);
                a(i2, dSAParamsA);
            } catch (InvalidParameterSpecException e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return dSAParamsA;
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public KeyPair generateKeyPair() {
        if (this.a == null) {
            this.a = SecRandom.getDefault();
        }
        a();
        if (this.c == null) {
            this.c = b(this.b);
        }
        BigInteger bigIntegerB = b(this.c);
        return new KeyPair(new DSAPublicKey(a(this.c, bigIntegerB), this.c), new DSAPrivateKey(bigIntegerB, this.c));
    }

    @Override // java.security.KeyPairGenerator
    public void initialize(int i2) {
        initialize(i2, (SecureRandom) null);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(int i2, SecureRandom secureRandom) {
        this.a = secureRandom;
        this.b = i2;
        this.c = null;
    }

    @Override // java.security.interfaces.DSAKeyPairGenerator
    public void initialize(int i2, boolean z, SecureRandom secureRandom) throws InvalidParameterException {
        this.a = secureRandom;
        this.b = i2;
        this.d = z;
        this.c = null;
    }

    @Override // java.security.interfaces.DSAKeyPairGenerator
    public void initialize(java.security.interfaces.DSAParams dSAParams, SecureRandom secureRandom) throws InvalidParameterException {
        this.a = secureRandom;
        this.b = -1;
        this.c = a(dSAParams);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof DSAParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Not a DSAParameterSpec");
        }
        initialize((java.security.interfaces.DSAParams) algorithmParameterSpec, secureRandom);
    }
}

package iaik.security.dh;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_rsp_t;
import com.magtek.mobile.android.mtlib.MTAudioReader;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.security.random.SecRandom;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class DHKeyPairGenerator extends KeyPairGenerator {
    private static final byte[] c = {2};
    private static final byte[] d = {-1, -57, -123, 98, ByteCompanionObject.MAX_VALUE, -101, PPSCRADeviceValues.RESPONSE_CLEAR_TEXT_USER_DATA_ENTRY, marshall_t.marshalll_display_control_button_id_right_arrow, -36, 85, -49, -49, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, -127, marshall_t.status_vpos_see_phone_for_instructions, PPSCRADeviceValues.ACK_STATUS_CRL_NON_EXIST, -56, -54, 76, 37, 121, -93, 107, -111, 34, 64, -92, -102, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, -49, MTAudioReader.PACKET_ID_LAST, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, -27, -71, marshall_t.marshalll_display_control_button_id_menu, -23, 12, 58, 67, 92, -128, marshall_t.status_vpos_try_another_card, 45, marshall_t.status_vpos_not_accepted, 124, marshall_t.marshalll_display_control_button_id_back, -3, -31, 24, -100, marshall_t.status_vpos_try_again, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, -37, -46, -97, -122, -123, 86, -90, 87, 29, 124, -122, -3};
    private static final byte[] e = {-24, 8, -30, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, -100, 77, 111, -54, 99, -28, -39, 119, 15, 85, -58, 20, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, 110, 93, marshall_t.marshalll_display_control_button_id_cancel, marshall_t.marshall_func_code_ev_ext_ev_info_req, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, 97, -78, 50, 43, 35, -127, -65, -79, 58, -5, marshall_t.marshalll_display_control_button_id_back, -66, -92, 20, marshall_t.marshall_func_code_ev_ext_ev_info_req, -91, 35, -85, marshall_t.status_vpos_card_error, 18, -34, -71, 98, -14, -50, -72, 93, 91, -70, PPSCRADeviceValues.ACK_STATUS_INVALID_CERT_CRL_MESSAGE, PPSCRADeviceValues.RESPONSE_SELECTED_MENU_ITEM, -43, -7, -113, 34, 72, 14, 32, 16, -20, 89, 77, 89, 125, -59, -48, -35, -25, -114, marshall_t.marshall_func_code_ev_ext_ev_info_req, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, 94, 5, -48, -85, -28, 51, -6, marshall_t.marshalll_display_control_button_id_right_arrow, -99, 98, -21, -100, -114, 107, 29, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, 122, -55, 28, 126, 7, -127, 119, -8, 17, -35, marshall_t.marshall_packet_option_rfu_mask, 55, 112, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, 11, -98, 30, -63, marshall_t.status_vpos_please_insert_or_swipe_card, 5, -48, PPSCRADeviceValues.ACK_STATUS_REVOKED_CERT_CRL, -27, 81, 97, -105, marshall_t.marshall_packet_option_rfu_mask, 51, marshall_t.status_vpos_present_card, 125, 16, 59, -98, 33, -125, -8, -29, 87, -93};
    private static final byte[] f = {-7, -37, marshall_t.marshall_func_code_ev_ext_ev_info_req, -24, -78, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -124, -24, -1, marshall_t.status_vpos_not_accepted, PPSCRADeviceValues.COMMAND_SET_GET_EXTENDED_DEVICE_CONFIGURATION, -60, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, -5, 12, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, 36, -37, 108, -34, 20, -56, -21, 91, 121, 49, 55, -96, -18, -2, -102, -73, -51, 11, -5, 54, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, -127, marshall_t.status_vpos_present_card_again, 111, -81, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, -105, 119, -29, -104, 121, -92, -33, 14, -47, marshall_t.status_vpos_present_card, marshall_t.status_vpos_processing_error, 110, -126, 111, 122, -94, 118, 111, -104, 1, -7, -15, 118, -48, marshall_t.marshall_packet_option_rfu_mask, -69, -15, -38, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, -77, -29, -111, marshall_t.status_vpos_try_again, 2, 55, 32, 11, 44, 54, 63, -7, marshall_t.marshall_packet_option_rfu_mask, -53, -28, -122, 59, -100, -10, -38, 11, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, marshall_t.status_vpos_insert_card, PPSCRADeviceValues.RESPONSE_DISPLAY_MESSAGE_DONE, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, -91, -101, marshall_t.status_vpos_insert_card, -115, -30, -122, -37, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, -67, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, 2, 109, marshall_t.status_vpos_please_insert_or_swipe_card, -124, marshall_t.marshall_func_code_ev_ext_ev_info_req, -14, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, 112, 5, -42, 20, -65, 12, ByteCompanionObject.MAX_VALUE, -12, -27, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, 21, -69, marshall_t.marshalll_display_control_button_id_menu, 10, -122, -93, -53, -90, -83, PPSCRADeviceValues.EMV_COMMAND_MERCHANT_BYPASS_PIN, marshall_t.status_vpos_try_another_card, -23, 24, marshall_t.status_vpos_try_again, 44, marshall_t.status_vpos_insert_card, -101, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, -94, -44, -97, 98, 87, -48, -58, 48, 37, PPSCRADeviceValues.ACK_STATUS_SYSTEM_NOT_AVAILABLE, 86, -45, -97, -7, 4, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, PPSCRADeviceValues.COMMAND_GET_SELECTED_MENU_ITEM, 31, -125, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, marshall_t.status_vpos_please_insert_card, -127, 19, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, -28, 5, -36, 43, 19, -49, -53, -100, -44, 81, -120, 93, -116, -8, -113, marshall_t.status_vpos_please_insert_card, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, -36, -54, -52, -99, 126, ByteCompanionObject.MAX_VALUE, marshall_t.status_vpos_present_card, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, -122, 87, marshall_t.status_vpos_please_remove_card, 16, -111, -34, -12, 37, 9, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, -90, -127, 123, -116, marshall_t.status_vpos_please_present_one_card_only, -76, marshall_t.status_vpos_try_another_card, -52, 76, 67, 77, -70, -101, PPSCRADeviceValues.FUNCTION_KEY_LEFT, PPSCRADeviceValues.ACK_STATUS_REVOKED_CERT_CRL, -98, -9, 94, marshall_t.status_vpos_please_use_mag, 111, -113, -49, PPSCRADeviceValues.FUNCTION_KEY_ENTER, -76, 89, -97, 4, PPSCRADeviceValues.ACK_STATUS_CERT_NON_EXIST, marshall_t.marshalll_display_control_button_id_menu, 7, 12, 58, -42, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, PPSCRADeviceValues.COMMAND_UPDATE_DEVICE, -53, -92, -128, -13, -42, -99, 13, -14, -1, -21, 19, 57, -67, 77, -11, 0, -65, 122, marshall_t.status_vpos_present_card_again};
    BigInteger a;
    BigInteger b;
    private SecureRandom g;
    private int h;
    private int i;

    public DHKeyPairGenerator() {
        super("DH");
        this.h = -1;
        this.i = -1;
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public KeyPair generateKeyPair() {
        if (this.g == null) {
            this.g = SecRandom.getDefault();
        }
        if (this.h < 0) {
            this.h = 1024;
        }
        if (this.i <= 0) {
            this.i = 300;
            int i = this.h;
            if (i > 7680) {
                this.i = i > 15360 ? 512 : 384;
            }
        }
        DHParameterSpec dHParameterSpec = new DHParameterSpec(this.a, this.b);
        if (this.a == null || this.b == null) {
            try {
                DHGenParameterSpec dHGenParameterSpec = new DHGenParameterSpec(this.h, this.i);
                DHParameterGenerator dHParameterGenerator = new DHParameterGenerator();
                dHParameterGenerator.engineInit(dHGenParameterSpec, this.g);
                dHParameterSpec = (DHParameterSpec) dHParameterGenerator.engineGenerateParameters().getParameterSpec(dHParameterSpec.getClass());
            } catch (InvalidAlgorithmParameterException e2) {
                throw new RuntimeException(e2.toString());
            } catch (InvalidParameterSpecException e3) {
                throw new RuntimeException(e3.toString());
            }
        }
        BigInteger bit = new BigInteger(this.i, this.g).setBit(this.i - 1);
        return new KeyPair(new DHPublicKey(dHParameterSpec.getG().modPow(bit, dHParameterSpec.getP()), dHParameterSpec), new DHPrivateKey(bit, dHParameterSpec));
    }

    @Override // java.security.KeyPairGenerator
    public void initialize(int i) {
        initialize(i, (SecureRandom) null);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(int i, SecureRandom secureRandom) {
        BigInteger bigInteger;
        this.g = secureRandom;
        this.h = i;
        if (i == 512) {
            this.a = new BigInteger(1, d);
            bigInteger = new BigInteger(c);
        } else if (i == 1024) {
            this.a = new BigInteger(1, e);
            bigInteger = new BigInteger(c);
        } else if (i != 2048) {
            bigInteger = null;
            this.a = null;
        } else {
            this.a = new BigInteger(1, f);
            bigInteger = new BigInteger(c);
        }
        this.b = bigInteger;
        this.i = -1;
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        this.g = secureRandom;
        if (!(algorithmParameterSpec instanceof DHParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Parameter must be a DHParameterSpec.");
        }
        DHParameterSpec dHParameterSpec = (DHParameterSpec) algorithmParameterSpec;
        this.a = dHParameterSpec.getP();
        this.b = dHParameterSpec.getG();
        this.h = this.a.bitLength();
        int l = dHParameterSpec.getL();
        this.i = l;
        if (l > this.h) {
            throw new InvalidAlgorithmParameterException("The size of the exponent must be less than the size of the modulus");
        }
    }
}

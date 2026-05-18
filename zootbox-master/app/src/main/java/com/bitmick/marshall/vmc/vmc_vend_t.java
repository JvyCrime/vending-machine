package com.bitmick.marshall.vmc;

import com.bitmick.marshall.models.MsgObject;
import com.bitmick.marshall.models.vmc_configuration;
import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_req_t;
import com.bitmick.marshall.vmc.mdb_rsp_t;
import com.bitmick.marshall.vmc.mdb_t;
import com.bitmick.utils.ByteArrayUtils;
import com.bitmick.utils.Log;
import com.bitmick.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class vmc_vend_t extends vmc_client_t {
    public static final int MULTI_VEND_PROD_CODE = 65535;
    public static final int MUTLTI_VEND_MAX_PRODUCTS = (marshall_t.MARSHALL_MSG_MAX_SIZE - 12) / 10;
    public static final String TAG = "vmc_vend_t";
    public static final byte card_entry_type_cnous_e = 7;
    public static final byte card_entry_type_contact_e = 3;
    public static final byte card_entry_type_contactless_e = 2;
    public static final byte card_entry_type_hid_e = 5;
    public static final byte card_entry_type_keypad_e = 8;
    public static final byte card_entry_type_mifare_e = 4;
    public static final byte card_entry_type_msr_swipe_e = 1;
    public static final byte card_entry_type_nfc_e = 6;
    public static final byte card_type_amex_e = 8;
    public static final byte card_type_china_union_pay_e = 4;
    public static final byte card_type_diners_e = 9;
    public static final byte card_type_discover_e = 10;
    public static final byte card_type_eftpos_e = 11;
    public static final byte card_type_interac_e = 6;
    public static final byte card_type_jcb_e = 12;
    public static final byte card_type_maestro_e = 5;
    public static final byte card_type_magstripe_e = 13;
    public static final byte card_type_mastercard_e = 3;
    public static final byte card_type_monyx_app_prepaid_e = 14;
    public static final byte card_type_monyx_card_e = 7;
    public static final byte card_type_monyx_padd_balance_e = 15;
    public static final byte card_type_proprietary_e = 1;
    public static final byte card_type_unknown_e = 0;
    public static final byte card_type_visa_e = 2;
    public static final int ereceipt_type_dynamic_ereceipt_e = 2;
    public static final int ereceipt_type_dynamic_qr_e = 0;
    public static final int ereceipt_type_static_ereceipt_e = 1;
    public static final int remote_vend_option_not_last = 1;
    public static final int selection_deny_reason_blocked_e = 5;
    public static final int selection_deny_reason_busy_wrong_mode_e = 9;
    public static final int selection_deny_reason_defective_e = 3;
    public static final int selection_deny_reason_empty_e = 2;
    public static final int selection_deny_reason_ingridient_over_e = 4;
    public static final int selection_deny_reason_inhibited_e = 6;
    public static final int selection_deny_reason_insufficient_funds_e = 10;
    public static final int selection_deny_reason_machine_busy_e = 255;
    public static final int selection_deny_reason_not_exist_e = 1;
    public static final int selection_deny_reason_product_expired_e = 7;
    public static final int selection_deny_reason_temperature_out_of_range_e = 8;
    public static final int selection_deny_reason_unknown_e = 0;
    public static final int session_status_fail_to_dispense_e = 2;
    public static final int session_status_ok_e = 0;
    public static final int session_status_session_timeout_e = 3;
    public static final int session_status_user_cancel_e = 1;
    public static final int session_status_vend_denied_e = 4;
    public static final int session_type_credit_e = 0;
    public static final int session_type_info_e = 1;
    public static final int session_type_none_e = 255;
    public static final byte status_ftp_status = 6;
    public static final byte status_machine_status = 5;
    public static final byte status_settlement = 4;
    public static final byte status_unexpected_error = 0;
    public static final byte status_unexpected_out_of_seq = 2;
    public static final byte status_unexpected_pending_3rd_party_rcvd = 3;
    public static final byte status_unexpected_timeout = 1;
    public static final byte vmc_auth_status_approved_e = 0;
    public static final byte vmc_auth_status_declined_e = 1;
    private static final int vmc_event_cash_sale_e = 9;
    private static final int vmc_event_init_done_e = 0;
    private static final int vmc_event_pp_pay_e = 10;
    private static final int vmc_event_qr_ereceipt_e = 18;
    private static final int vmc_event_reader_enable_e = 1;
    private static final int vmc_event_reader_state_req = 14;
    private static final int vmc_event_reader_state_rsp = 15;
    private static final int vmc_event_remote_selection_deny = 17;
    private static final int vmc_event_remote_selection_notify = 16;
    private static final int vmc_event_send_transfer_data_e = 11;
    private static final int vmc_event_session_begin_e = 2;
    private static final int vmc_event_session_cancel_e = 3;
    private static final int vmc_event_session_close_e = 8;
    private static final int vmc_event_session_end_e = 7;
    private static final int vmc_event_session_status_req = 12;
    private static final int vmc_event_session_status_rsp = 13;
    private static final int vmc_event_vend_approved_e = 5;
    private static final int vmc_event_vend_denied_e = 6;
    private static final int vmc_event_vend_request_e = 4;
    private static final int vmc_event_vmc_reader_event_e = 19;
    private static final int vmc_vend_state_idle_e = 1;
    private static final int vmc_vend_state_init_e = 0;
    private static final int vmc_vend_state_reader_disable_e = 6;
    private static final int vmc_vend_state_reader_enabled_e = 2;
    private static final int vmc_vend_state_vend_process_e = 4;
    private static final int vmc_vend_state_wait_end_session_e = 5;
    private static final int vmc_vend_state_wait_vend_request_e = 3;
    private vend_session_t m_current_session;
    private boolean m_in_process;
    private vend_session_data_t m_session_data;
    private int m_session_type;
    private vend_callbacks_t m_vend_callbacks;
    private int m_vend_state;
    private long m_vend_state_time_ms;
    private vmc_configuration m_vmc_configuration;

    public interface vend_callbacks_t {
        void onOpenedSessions(short[] sArr);

        void onReaderState(boolean z);

        void onReady(vend_session_t vend_session_tVar);

        void onReceipt(int i, String str);

        void onRemoteVend(int i, int i2, int i3);

        void onSessionBegin(int i);

        void onSettlement(boolean z);

        void onStatus(int i);

        void onTransactionInfo(vend_session_data_t vend_session_data_tVar);

        boolean onVendApproved(vend_session_t vend_session_tVar);

        void onVendDenied(vend_session_t vend_session_tVar);
    }

    public static class vend_session_data_t {
        public long transaction_id = -1;
        public short choose_product_timeout = -1;
        public int card_type = -1;
        public int card_entry_mode = -1;
        public String card_bin = null;
        public String card_bin_hash = null;
        public String prop_card_uid = null;
        public int vmc_auth_status = -1;
        public int com_status = -1;
        public String excel_data = null;
        public String cc_last_4_digits = null;
    }

    public static class vend_item_t {
        public static final int UNIT_DONT_CARE = 0;
        public short code;
        public int price;
        public int qty;
        public byte unit;

        public vend_item_t(short s, int i, int i2, byte b) {
            this.code = s;
            this.price = i;
            this.qty = i2;
            this.unit = b;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public vend_item_t m9clone() {
            return new vend_item_t(this.code, this.price, this.qty, this.unit);
        }
    }

    public static class vend_session_t {
        public vend_session_data_t data;
        public int funds_avail;
        public ArrayList<vend_item_t> products_list;
        public int session_status;

        public vend_session_t(int i, int i2, byte b, int i3) {
            init();
            ArrayList<vend_item_t> arrayList = new ArrayList<>();
            this.products_list = arrayList;
            arrayList.add(new vend_item_t((short) i, (short) i3, i2, b));
        }

        public vend_session_t(ArrayList<vend_item_t> arrayList) {
            init();
            this.products_list = arrayList;
        }

        private void init() {
            this.funds_avail = 0;
            this.session_status = 0;
        }

        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public vend_session_t m10clone() {
            ArrayList arrayList = new ArrayList();
            Iterator<vend_item_t> it = this.products_list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().m9clone());
            }
            return new vend_session_t(arrayList);
        }
    }

    public static class cash_sale_t {
        public int product_code;
        public int product_price;

        public cash_sale_t(int i, int i2) {
            this.product_code = i;
            this.product_price = i2;
        }
    }

    public vmc_vend_t() {
        super(TAG);
        init();
    }

    @Override // com.bitmick.marshall.vmc.vmc_client_t
    public void start() {
        if (this.m_vend_callbacks == null) {
            Log.d(TAG, "cannot start vend process without registering vend callbacks");
        } else {
            super.start();
        }
    }

    @Override // com.bitmick.marshall.vmc.vmc_client_t
    public void onReady(vmc_link vmc_linkVar) {
        this.m_vmc_link = vmc_linkVar;
        this.m_vmc_configuration = this.m_vmc_link.get_configuration();
        this.m_in_process = false;
        notify(0, null);
    }

    @Override // com.bitmick.marshall.vmc.vmc_client_t
    public void onCommError() {
        init();
    }

    @Override // com.bitmick.marshall.vmc.vmc_client_t
    public void onTimerTick(long j) {
        super.onTimerTick(j);
    }

    @Override // com.bitmick.marshall.vmc.vmc_client_t
    public boolean handleMarshallMessage(marshall_t.msg_marshall_t msg_marshall_tVar) {
        String strByteArray2String;
        byte b = msg_marshall_tVar.func_code;
        if (b == -128) {
            mdb_rsp_t.msg_mdb_rsp_t msg_mdb_rsp_tVar = (mdb_rsp_t.msg_mdb_rsp_t) ((marshall_t.msg_mdb_t) msg_marshall_tVar.body).message;
            byte b2 = msg_mdb_rsp_tVar.response;
            if (b2 == -122) {
                mdb_t.msg_sessions_status_t msg_sessions_status_tVar = (mdb_t.msg_sessions_status_t) msg_mdb_rsp_tVar.body;
                Log.d(TAG, String.format("got %d opened sessions", Byte.valueOf(msg_sessions_status_tVar.num_opened_sessions)));
                notify(13, msg_sessions_status_tVar);
            } else if (b2 == -120) {
                mdb_t.msg_reader_state_t msg_reader_state_tVar = (mdb_t.msg_reader_state_t) msg_mdb_rsp_tVar.body;
                String str = TAG;
                Object[] objArr = new Object[1];
                objArr[0] = msg_reader_state_tVar.state != 0 ? "enabled" : "disabled";
                Log.d(str, String.format("reader state: %s", objArr));
                notify(15, msg_reader_state_tVar);
            } else {
                switch (b2) {
                    case -1:
                        Log.d(TAG, "mdb_response_poll_diagnostics_response");
                        break;
                    case 0:
                        Log.d(TAG, "mdb_response_poll_just_reset");
                        break;
                    case 1:
                        Log.d(TAG, "mdb_response_poll_reader_config_data");
                        break;
                    case 2:
                        Log.d(TAG, "mdb_response_poll_display_req");
                        break;
                    case 3:
                        mdb_rsp_t.msg_begin_session_rsp_t msg_begin_session_rsp_tVar = (mdb_rsp_t.msg_begin_session_rsp_t) msg_mdb_rsp_tVar.body;
                        Log.d(TAG, "received begin session");
                        notify(2, Integer.valueOf(msg_begin_session_rsp_tVar.funds_available & 0xFFFF));
                        break;
                    case 4:
                        Log.d(TAG, "received session cancel");
                        notify(3, null);
                        break;
                    case 5:
                        mdb_rsp_t.msg_vend_approved_rsp_t msg_vend_approved_rsp_tVar = (mdb_rsp_t.msg_vend_approved_rsp_t) msg_mdb_rsp_tVar.body;
                        Log.d(TAG, "received vend approved");
                        notify(5, Short.valueOf(msg_vend_approved_rsp_tVar.vend_amount));
                        break;
                    case 6:
                        Log.d(TAG, "received vend denied");
                        notify(6, null);
                        break;
                    case 7:
                        Log.d(TAG, "received end session");
                        notify(7, null);
                        break;
                    case 8:
                        Log.d(TAG, "mdb_response_poll_cancelled");
                        break;
                    case 9:
                        Log.d(TAG, "mdb_response_poll_peripheral_id");
                        break;
                    case 10:
                        Log.d(TAG, "mdb_response_poll_malfunction_error");
                        break;
                    case 11:
                        Log.d(TAG, "mdb_response_poll_cmd_out_of_seq");
                        break;
                    default:
                        switch (b2) {
                            case 13:
                                Log.d(TAG, "mdb_response_poll_revalue_approved");
                                break;
                            case 14:
                                Log.d(TAG, "mdb_response_poll_revalue_denied");
                                break;
                            case 15:
                                Log.d(TAG, "mdb_response_poll_revalue_limit_amount");
                                break;
                            case 16:
                                Log.d(TAG, "mdb_response_poll_user_file_data");
                                break;
                            case 17:
                                Log.d(TAG, "mdb_response_poll_time_date_req");
                                break;
                            case 18:
                                Log.d(TAG, "mdb_response_poll_data_entry_req");
                                break;
                            case 19:
                                Log.d(TAG, "mdb_response_poll_data_entry_cancel");
                                break;
                            case 20:
                                Object obj = (mdb_rsp_t.msg_remote_selection_t) msg_mdb_rsp_tVar.body;
                                Log.d(TAG, "mdb_response_poll_selection_request");
                                notify(16, obj);
                                break;
                            default:
                                switch (b2) {
                                    case 27:
                                        Log.d(TAG, "mdb_response_poll_ftl_req_to_rcv");
                                        break;
                                    case 28:
                                        Log.d(TAG, "mdb_response_poll_ftl_retry_deny");
                                        break;
                                    case 29:
                                        Log.d(TAG, "mdb_response_poll_ftl_send_block");
                                        break;
                                    case 30:
                                        Log.d(TAG, "mdb_response_poll_ok_to_send");
                                        break;
                                    case 31:
                                        Log.d(TAG, "mdb_response_poll_req_to_send");
                                        break;
                                    default:
                                        Log.d(TAG, String.format("unknown mdb response: %d", Byte.valueOf(msg_mdb_rsp_tVar.response)));
                                        break;
                                }
                                break;
                        }
                        break;
                }
            }
        } else if (b == 17) {
            notify(18, (marshall_t.msg_ereceipt_t) msg_marshall_tVar.body);
        } else if (b == 10) {
            marshall_t.msg_transfer_data_t msg_transfer_data_tVar = (marshall_t.msg_transfer_data_t) msg_marshall_tVar.body;
            this.m_session_data = new vend_session_data_t();
            if ((msg_transfer_data_tVar.encode_bitmap & 1) != 0) {
                this.m_session_data.transaction_id = ByteArrayUtils.byteArrToLong(msg_transfer_data_tVar.transaction_id, 0);
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 2) != 0) {
                this.m_session_data.choose_product_timeout = msg_transfer_data_tVar.choose_product_timeout;
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 4) != 0) {
                this.m_session_data.card_type = msg_transfer_data_tVar.card_type;
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 8) != 0) {
                this.m_session_data.card_entry_mode = msg_transfer_data_tVar.card_entry_mode;
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 16) != 0) {
                this.m_session_data.card_bin = new String(msg_transfer_data_tVar.card_bin);
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 32) != 0) {
                this.m_session_data.card_bin_hash = StringUtils.buf2hex_str(msg_transfer_data_tVar.card_bin_hash);
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 64) != 0) {
                int i = this.m_session_data.card_entry_mode;
                if (i == 1) {
                    if (msg_transfer_data_tVar.prop_card_uid.length == 4) {
                        strByteArray2String = StringUtils.int2Str(ByteArrayUtils.byteArrToInteger(msg_transfer_data_tVar.prop_card_uid, 0));
                    } else {
                        strByteArray2String = StringUtils.byteArray2String(msg_transfer_data_tVar.prop_card_uid);
                    }
                } else {
                    strByteArray2String = (i == 4 && this.m_session_data.card_type == 1 && msg_transfer_data_tVar.prop_card_uid.length == 4) ? String.format("%08x", Integer.valueOf(ByteArrayUtils.byteArrToInteger(msg_transfer_data_tVar.prop_card_uid, 0))) : StringUtils.buf2hex_str(msg_transfer_data_tVar.prop_card_uid);
                }
                this.m_session_data.prop_card_uid = strByteArray2String;
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 128) != 0) {
                this.m_session_data.vmc_auth_status = msg_transfer_data_tVar.vmc_auth_status;
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 256) != 0) {
                this.m_session_data.com_status = msg_transfer_data_tVar.com_status;
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 512) != 0) {
                this.m_session_data.card_entry_mode = msg_transfer_data_tVar.card_entry_mode;
            }
            if ((msg_transfer_data_tVar.encode_bitmap & 1024) != 0) {
                this.m_session_data.cc_last_4_digits = new String(msg_transfer_data_tVar.cc_last_4_digits);
            }
            vend_session_t vend_session_tVar = this.m_current_session;
            if (vend_session_tVar != null) {
                vend_session_tVar.data = this.m_session_data;
            }
            this.m_vend_callbacks.onTransactionInfo(this.m_session_data);
        } else if (b == 11) {
            marshall_t.msg_status_t msg_status_tVar = (marshall_t.msg_status_t) msg_marshall_tVar.body;
            String str2 = TAG;
            Log.d(str2, String.format("received status: %d", Byte.valueOf(msg_status_tVar.status)));
            if (msg_status_tVar.status == 4) {
                boolean z = msg_status_tVar.data[0] == 0;
                Object[] objArr2 = new Object[1];
                objArr2[0] = z ? "done" : "error";
                Log.d(str2, String.format("settlement %s", objArr2));
                this.m_vend_callbacks.onSettlement(z);
            } else {
                this.m_vend_callbacks.onStatus(msg_status_tVar.status);
            }
        }
        return false;
    }

    private boolean change_state(int i) {
        this.m_vend_state = i;
        if (i == 0) {
            this.m_vend_state_time_ms = 0L;
            return false;
        }
        if (i != 1) {
            if (i == 2) {
                readerEnable();
            } else {
                if (i != 6) {
                    return false;
                }
                readerDisable();
            }
            return true;
        }
        this.m_in_process = false;
        this.m_session_type = 255;
        boolean z = this.m_vmc_configuration.reader_always_on;
        vend_session_t vend_session_tVar = this.m_current_session;
        if (z | (vend_session_tVar != null && vend_session_tVar.session_status == 4 && this.m_vmc_configuration.vend_denied_policy == 1)) {
            session_start(0);
        }
        vend_session_t vend_session_tVar2 = this.m_current_session;
        this.m_current_session = null;
        this.m_vend_callbacks.onReady(vend_session_tVar2);
        return false;
    }

    @Override // com.bitmick.marshall.vmc.vmc_client_t
    protected boolean handleMessage(MsgObject msgObject) {
        boolean zChange_state;
        super.handleMessage(msgObject);
        do {
            zChange_state = false;
            switch (this.m_vend_state) {
                case 0:
                    if (msgObject.id == 0) {
                        zChange_state = change_state(1);
                    }
                    break;
                case 1:
                case 2:
                    switch (msgObject.id) {
                        case 0:
                            zChange_state = change_state(1);
                            break;
                        case 1:
                            change_state(2);
                            break;
                        case 2:
                            int iIntValue = ((Integer) msgObject.data).intValue();
                            String str = TAG;
                            Log.d(str, String.format("session_begin. funds available: %d", Integer.valueOf(iIntValue)));
                            int i = this.m_session_type;
                            if (i == 0) {
                                this.m_vend_callbacks.onSessionBegin(iIntValue);
                                zChange_state = change_state(3);
                            } else if (i == 1) {
                                this.m_current_session = new vend_session_t(65535, 0, (byte) 0, 0);
                                vendSessionType(1);
                                vend_item_t vend_item_tVar = this.m_current_session.products_list.get(0);
                                vendRequest(vend_item_tVar.code, (short) vend_item_tVar.price);
                                zChange_state = change_state(4);
                            } else {
                                Log.d(str, "error: specify session type");
                                vendCancel();
                                vendSessionComplete();
                            }
                            break;
                        case 3:
                            if (this.m_vend_state == 2) {
                                zChange_state = change_state(6);
                            }
                            break;
                        case 4:
                            if (this.m_vmc_configuration.always_idle) {
                                vend_session_t vend_session_tVar = (vend_session_t) msgObject.data;
                                this.m_current_session = vend_session_tVar;
                                ArrayList<vend_item_t> arrayList = vend_session_tVar.products_list;
                                if (this.m_vmc_configuration.multi_vend_support && !this.m_vmc_configuration.multi_session_support) {
                                    vendExMultiVendRequest(arrayList);
                                } else {
                                    vend_item_t vend_item_tVar2 = arrayList.get(0);
                                    vendRequest(vend_item_tVar2.code, (short) vend_item_tVar2.price);
                                }
                                zChange_state = change_state(4);
                            }
                            break;
                        case 8:
                            vend_session_t vend_session_tVar2 = (vend_session_t) msgObject.data;
                            Log.d(TAG, "session close");
                            int i2 = vend_session_tVar2.session_status;
                            byte b = i2 != 0 ? i2 != 1 ? i2 != 2 ? (byte) 3 : (byte) 2 : (byte) 1 : (byte) 0;
                            vend_item_t vend_item_tVar3 = vend_session_tVar2.products_list.get(0);
                            vendCloseSession(b, vend_item_tVar3.code, (short) vend_item_tVar3.price, vend_item_tVar3.qty, vend_item_tVar3.unit);
                            break;
                        case 9:
                            cash_sale_t cash_sale_tVar = (cash_sale_t) msgObject.data;
                            vendCashSale((short) cash_sale_tVar.product_code, (short) cash_sale_tVar.product_price);
                            break;
                        case 10:
                            vendExPay((byte[]) msgObject.data);
                            break;
                        case 12:
                            vendSessionsStatus();
                            break;
                        case 13:
                            this.m_vend_callbacks.onOpenedSessions(((mdb_t.msg_sessions_status_t) msgObject.data).sessions);
                            break;
                        case 14:
                            readerStateRequest();
                            break;
                        case 15:
                            this.m_vend_callbacks.onReaderState(((mdb_t.msg_reader_state_t) msgObject.data).state != 0);
                            break;
                        case 16:
                            mdb_rsp_t.msg_remote_selection_t msg_remote_selection_tVar = (mdb_rsp_t.msg_remote_selection_t) msgObject.data;
                            vend_callbacks_t vend_callbacks_tVar = this.m_vend_callbacks;
                            if (vend_callbacks_tVar != null) {
                                vend_callbacks_tVar.onRemoteVend(msg_remote_selection_tVar.funds_available, msg_remote_selection_tVar.item_number, msg_remote_selection_tVar.item_options);
                            }
                            break;
                        case 17:
                            vendSelectionDenied((short) 0, ((Byte) msgObject.data).byteValue());
                            break;
                        case 19:
                            vmcReaderEvent((marshall_t.msg_reader_t) msgObject.data);
                            break;
                    }
                    break;
                case 3:
                    int i3 = msgObject.id;
                    if (i3 == 0) {
                        zChange_state = change_state(1);
                    } else if (i3 == 11) {
                        transferData((marshall_t.msg_transfer_data_t) msgObject.data);
                    } else if (i3 == 3) {
                        vendCancel();
                        vendSessionComplete();
                        zChange_state = change_state(5);
                    } else if (i3 == 4) {
                        vend_session_t vend_session_tVar3 = (vend_session_t) msgObject.data;
                        this.m_current_session = vend_session_tVar3;
                        ArrayList<vend_item_t> arrayList2 = vend_session_tVar3.products_list;
                        if (this.m_vmc_configuration.multi_vend_support && !this.m_vmc_configuration.multi_session_support) {
                            vendExMultiVendRequest(arrayList2);
                        } else {
                            vend_item_t vend_item_tVar4 = arrayList2.get(0);
                            vendRequest(vend_item_tVar4.code, (short) vend_item_tVar4.price);
                        }
                        zChange_state = change_state(4);
                    }
                    break;
                case 4:
                    int i4 = msgObject.id;
                    if (i4 == 0) {
                        zChange_state = change_state(1);
                    } else if (i4 == 3) {
                        vendCancel();
                    } else if (i4 == 11) {
                        transferData((marshall_t.msg_transfer_data_t) msgObject.data);
                    } else if (i4 == 5) {
                        if (this.m_vend_callbacks.onVendApproved(this.m_current_session)) {
                            if (this.m_vmc_configuration.multi_vend_support && !this.m_vmc_configuration.multi_session_support) {
                                vendExMultiVendSuccess(this.m_current_session.products_list);
                            } else {
                                vendSuccess(this.m_current_session.products_list.get(0).code);
                            }
                        } else {
                            vendFailure();
                        }
                        vendSessionComplete();
                        zChange_state = change_state(5);
                    } else if (i4 == 6) {
                        vendSessionComplete();
                        this.m_current_session.session_status = 4;
                        this.m_vend_callbacks.onVendDenied(this.m_current_session);
                        zChange_state = change_state(5);
                    } else if (i4 == 7) {
                        Log.d(TAG, "time-out. probably waiting for vend_request which never came...");
                        if (this.m_vmc_configuration.reader_always_on) {
                            zChange_state = change_state(1);
                        } else {
                            zChange_state = change_state(6);
                        }
                    }
                    break;
                case 5:
                    int i5 = msgObject.id;
                    if (i5 == 0) {
                        zChange_state = change_state(1);
                    } else if (i5 == 7) {
                        if (this.m_vmc_configuration.reader_always_on) {
                            zChange_state = change_state(1);
                        } else if (this.m_vmc_configuration.vend_denied_policy != 1) {
                            zChange_state = change_state(6);
                        } else {
                            zChange_state = change_state(1);
                        }
                    } else if (i5 == 18) {
                        marshall_t.msg_ereceipt_t msg_ereceipt_tVar = (marshall_t.msg_ereceipt_t) msgObject.data;
                        byte b2 = msg_ereceipt_tVar.qr_type;
                        String strBuf2hex_str = StringUtils.buf2hex_str(msg_ereceipt_tVar.data);
                        Log.d(TAG, String.format("received e-receipt qr. type: %d, data: %s", Byte.valueOf(b2), strBuf2hex_str));
                        vend_callbacks_t vend_callbacks_tVar2 = this.m_vend_callbacks;
                        if (vend_callbacks_tVar2 != null) {
                            vend_callbacks_tVar2.onReceipt(b2, strBuf2hex_str);
                        }
                    }
                    break;
                case 6:
                    zChange_state = change_state(1);
                    break;
            }
        } while (zChange_state);
        return true;
    }

    private void init() {
        this.m_vmc_link = null;
        this.m_vend_state = 0;
        this.m_in_process = false;
    }

    private void reader(byte b) {
        this.m_vmc_link.transmit_marshall(marshall_t.mdb_req(mdb_req_t.reader_req(b)));
    }

    private void vend(byte b, short s, short s2) {
        this.m_vmc_link.transmit_marshall(marshall_t.mdb_req(mdb_req_t.vend_req(b, s, s2)));
    }

    private void mdb_ex(byte b, byte b2) {
        this.m_vmc_link.transmit_marshall(marshall_t.mdb_req(mdb_req_t.mdb_ex(b, b2)));
    }

    private void vendExCloseSession(byte b, short s, short s2, int i, byte b2) {
        mdb_req_t.msg_vend_t msg_vend_tVar = new mdb_req_t.msg_vend_t();
        msg_vend_tVar.sub_command = (byte) -128;
        msg_vend_tVar.status = b;
        msg_vend_tVar.item_price = s;
        msg_vend_tVar.item_number = s2;
        msg_vend_tVar.quantity = i;
        msg_vend_tVar.unit_of_measure = b2;
        this.m_vmc_link.transmit_marshall(marshall_t.mdb_req(new mdb_req_t.msg_mdb_req_t((byte) 3, msg_vend_tVar)));
    }

    private void vendExPay(byte[] bArr) {
        mdb_req_t.msg_ex_pay_t msg_ex_pay_tVar = new mdb_req_t.msg_ex_pay_t();
        msg_ex_pay_tVar.sub_command = (byte) -125;
        msg_ex_pay_tVar.pp_number = bArr;
        this.m_vmc_link.transmit_marshall(marshall_t.mdb_req(new mdb_req_t.msg_mdb_req_t((byte) 3, msg_ex_pay_tVar)));
    }

    private void vendExMultiVendRequest(ArrayList<vend_item_t> arrayList) {
        mdb_req_t.msg_ex_multi_vend_req_t msg_ex_multi_vend_req_tVar = new mdb_req_t.msg_ex_multi_vend_req_t();
        msg_ex_multi_vend_req_tVar.sub_command = (byte) -126;
        int i = 0;
        int i2 = 0;
        for (vend_item_t vend_item_tVar : arrayList) {
            msg_ex_multi_vend_req_tVar.add(vend_item_tVar.code, (short) vend_item_tVar.price, vend_item_tVar.qty, vend_item_tVar.unit);
            i += vend_item_tVar.price * vend_item_tVar.qty;
            i2++;
            if (i2 == MUTLTI_VEND_MAX_PRODUCTS) {
                break;
            }
        }
        Log.d(TAG, String.format("Vend Multi-Request, total products: %d, total: price: %d", Integer.valueOf(i2), Integer.valueOf(i)));
        this.m_vmc_link.transmit_marshall(marshall_t.mdb_req(new mdb_req_t.msg_mdb_req_t((byte) 3, msg_ex_multi_vend_req_tVar)));
    }

    private void vendExMultiVendSuccess(ArrayList<vend_item_t> arrayList) {
        mdb_req_t.msg_ex_multi_vend_success_t msg_ex_multi_vend_success_tVar = new mdb_req_t.msg_ex_multi_vend_success_t();
        msg_ex_multi_vend_success_tVar.sub_command = (byte) -124;
        int i = 0;
        int i2 = 0;
        for (vend_item_t vend_item_tVar : arrayList) {
            msg_ex_multi_vend_success_tVar.add(vend_item_tVar.code, (short) vend_item_tVar.price, vend_item_tVar.qty, vend_item_tVar.unit);
            i += vend_item_tVar.price * vend_item_tVar.qty;
            i2++;
            if (i2 == MUTLTI_VEND_MAX_PRODUCTS) {
                break;
            }
        }
        Log.d(TAG, String.format("Vend Multi-Success, products: %d, total: price: %d", Integer.valueOf(arrayList.size()), Integer.valueOf(i)));
        this.m_vmc_link.transmit_marshall(marshall_t.mdb_req(new mdb_req_t.msg_mdb_req_t((byte) 3, msg_ex_multi_vend_success_tVar)));
    }

    private void readerEnable() {
        Log.d(TAG, "Reader Enable");
        reader((byte) 1);
    }

    private void readerDisable() {
        Log.d(TAG, "Reader Disable");
        reader((byte) 0);
    }

    private void readerStateRequest() {
        Log.d(TAG, "Reader State request");
        reader((byte) -121);
    }

    private void readerCancel() {
        Log.d(TAG, "Reader Cancel");
        reader((byte) 2);
    }

    private void vendRequest(short s, short s2) {
        Log.d(TAG, String.format("Vend Request, product: %d, price: %d", Short.valueOf(s), Integer.valueOf(65535 & s2)));
        vend((byte) 0, s2, s);
    }

    private void vendSuccess(short s) {
        Log.d(TAG, String.format("Vend Success, product: %d", Short.valueOf(s)));
        vend((byte) 2, (short) 0, s);
    }

    private void vendSessionComplete() {
        Log.d(TAG, "Vend Session Complete");
        vend((byte) 4, (short) 0, (short) 0);
    }

    private void vendSelectionDenied(short s, byte b) {
        Log.d(TAG, String.format("Vend Success, product: %d", Short.valueOf(s)));
        this.m_vmc_link.transmit_marshall(marshall_t.mdb_req(mdb_req_t.mdb_vend_selection_denied((byte) 7, s, b)));
    }

    private void vendCloseSession(byte b, short s, short s2, int i, byte b2) {
        Log.d(TAG, String.format("Vend End Session, prod: %d, price: %d, quantity: %d", Short.valueOf(s), Short.valueOf(s2), Integer.valueOf(i)));
        vendExCloseSession(b, s2, s, i, b2);
    }

    private void vendSessionType(int i) {
        Log.d(TAG, String.format("Vend Session Type: %d", Integer.valueOf(i)));
        mdb_ex((byte) -127, (byte) i);
    }

    private void vendSessionsStatus() {
        Log.d(TAG, "Requesting sessions");
        vend((byte) -123, (short) 0, (short) 0);
    }

    private void vendCancel() {
        Log.d(TAG, "Vend Cancel");
        vend((byte) 1, (short) 0, (short) 0);
    }

    private void vendFailure() {
        Log.d(TAG, "Vend Failure");
        vend((byte) 3, (short) 0, (short) 0);
    }

    private void vendCashSale(short s, short s2) {
        Log.d(TAG, String.format("Cash Sale, product: %d, price: %d", Short.valueOf(s), Short.valueOf(s2)));
        vend((byte) 5, s2, s);
    }

    private void transferData(marshall_t.msg_transfer_data_t msg_transfer_data_tVar) {
        Log.d(TAG, "TransferData");
        this.m_vmc_link.transmit_marshall(marshall_t.request((byte) 10, (byte) 1, msg_transfer_data_tVar));
    }

    private void vmcReaderEvent(marshall_t.msg_reader_t msg_reader_tVar) {
        Log.d(TAG, "RdrEvent");
        this.m_vmc_link.transmit_marshall(marshall_t.request((byte) -111, (byte) 1, msg_reader_tVar));
    }

    public void register_callbacks(vend_callbacks_t vend_callbacks_tVar) {
        this.m_vend_callbacks = vend_callbacks_tVar;
    }

    public boolean is_ready() {
        return !this.m_in_process && this.m_vend_state == 1;
    }

    public void session_start(int i) {
        if (is_ready()) {
            this.m_in_process = true;
            this.m_session_type = i;
            notify(1, null);
        }
    }

    public void session_modify(int i) {
        this.m_session_type = i;
    }

    public int get_session_type() {
        return this.m_session_type;
    }

    public void session_cancel() {
        notify(3, null);
    }

    public void session_close(vend_session_t vend_session_tVar) {
        notify(8, vend_session_tVar);
    }

    public void vend_request(vend_session_t vend_session_tVar) {
        notify(4, vend_session_tVar);
    }

    public void vend_cash_sale(cash_sale_t cash_sale_tVar) {
        notify(9, cash_sale_tVar);
    }

    public void vend_deny_selection(short s, int i) {
        notify(17, Integer.valueOf(i));
    }

    public void vend_pp_pay(String str) {
        notify(10, StringUtils.hex_str2buf(str));
    }

    public void vmc_reader_event(byte b, byte b2, byte b3, byte[] bArr) {
        marshall_t.msg_reader_t msg_reader_tVar = new marshall_t.msg_reader_t();
        msg_reader_tVar.event_type = b;
        msg_reader_tVar.payment_type_key_value = b2;
        msg_reader_tVar.payment_type_data = b3;
        msg_reader_tVar.payment_type_key_value = b2;
        notify(19, msg_reader_tVar);
    }

    public void transfer_data(marshall_t.msg_transfer_data_t msg_transfer_data_tVar) {
        notify(11, msg_transfer_data_tVar);
    }

    public void get_session_status() {
        notify(12, null);
    }

    public void get_reader_state() {
        notify(14, null);
    }

    public void client_gateway_auth(boolean z) {
        marshall_t.msg_transfer_data_t msg_transfer_data_tVar = new marshall_t.msg_transfer_data_t();
        msg_transfer_data_tVar.encode_bitmap = 128;
        msg_transfer_data_tVar.vmc_auth_status = !z ? (byte) 1 : (byte) 0;
        transfer_data(msg_transfer_data_tVar);
    }

    public long get_transaction_id() {
        vend_session_t vend_session_tVar = this.m_current_session;
        if (vend_session_tVar == null || vend_session_tVar.data == null) {
            return -1L;
        }
        return this.m_current_session.data.transaction_id;
    }
}

package com.bitmick.marshall.vmc;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_t;
import com.bitmick.utils.ByteArrayUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class mdb_req_t extends mdb_t {
    public static final String TAG = "mdb_req_t";
    public static final byte mdb_close_session_status_fail_to_dispense = 2;
    public static final byte mdb_close_session_status_ok = 0;
    public static final byte mdb_close_session_status_session_timeout = 3;
    public static final byte mdb_close_session_status_user_cancel = 1;
    public static final byte mdb_command_expansion = 7;
    public static final byte mdb_command_poll = 2;
    public static final byte mdb_command_reader = 4;
    public static final byte mdb_command_reset = 0;
    public static final byte mdb_command_revalue = 5;
    public static final byte mdb_command_setup = 1;
    public static final byte mdb_command_vend = 3;
    public static final byte mdb_sub_command_expansion_ftl_diagnostics = -1;
    public static final byte mdb_sub_command_expansion_ftl_ok_to_send = -3;
    public static final byte mdb_sub_command_expansion_ftl_req_to_rcv = -6;
    public static final byte mdb_sub_command_expansion_ftl_req_to_send = -2;
    public static final byte mdb_sub_command_expansion_ftl_retry_deny = -5;
    public static final byte mdb_sub_command_expansion_ftl_send_block = -4;
    public static final byte mdb_sub_command_expansion_opt_feature_en = 4;
    public static final byte mdb_sub_command_expansion_rd_user_file = 1;
    public static final byte mdb_sub_command_expansion_request_id = 0;
    public static final byte mdb_sub_command_expansion_wr_time_date = 3;
    public static final byte mdb_sub_command_expansion_wr_user_file = 2;
    public static final byte mdb_sub_command_get_sessions_status = -123;
    public static final byte mdb_sub_command_multivend_close_session = -123;
    public static final byte mdb_sub_command_multivend_request = -126;
    public static final byte mdb_sub_command_multivend_success = -124;
    public static final byte mdb_sub_command_prepaid_pay = -125;
    public static final byte mdb_sub_command_reader_cancel = 2;
    public static final byte mdb_sub_command_reader_data_entry_rsp = 3;
    public static final byte mdb_sub_command_reader_disable = 0;
    public static final byte mdb_sub_command_reader_enable = 1;
    public static final byte mdb_sub_command_reader_state_req = -121;
    public static final byte mdb_sub_command_revalue_limit_request = 1;
    public static final byte mdb_sub_command_revalue_request = 0;
    public static final byte mdb_sub_command_selection_denied = 7;
    public static final byte mdb_sub_command_setup_config_data = 0;
    public static final byte mdb_sub_command_setup_max_min_prices = 1;
    public static final byte mdb_sub_command_vend_cancel = 1;
    public static final byte mdb_sub_command_vend_cash_sale = 5;
    public static final byte mdb_sub_command_vend_close_session = -128;
    public static final byte mdb_sub_command_vend_failure = 3;
    public static final byte mdb_sub_command_vend_negative_req = 6;
    public static final byte mdb_sub_command_vend_request = 0;
    public static final byte mdb_sub_command_vend_session_complete = 4;
    public static final byte mdb_sub_command_vend_session_type = -127;
    public static final byte mdb_sub_command_vend_success = 2;

    public static class msg_mdb_req_t extends marshall_t.msg_i {
        public marshall_t.msg_i body;
        public byte command;

        public msg_mdb_req_t(byte b, marshall_t.msg_i msg_iVar) {
            this.command = b;
            this.body = msg_iVar;
        }

        public msg_mdb_req_t(byte[] bArr, int i, int i2) {
            decode(bArr, i, i2);
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            int iDecode = i + 1;
            byte b = (byte) (bArr[i] + marshall_t.marshall_packet_option_rfu_mask);
            this.command = b;
            this.body = null;
            if (b == 0) {
                this.body = new msg_reset_t();
            } else if (b != 1) {
                if (b == 2) {
                    this.body = new msg_poll_t();
                } else if (b == 3) {
                    this.body = new msg_vend_t();
                } else if (b == 4) {
                    this.body = new msg_reader_t();
                }
            } else if (bArr[iDecode] == 0) {
                this.body = new msg_setup_config_data_t();
            } else {
                this.body = new msg_setup_max_min_prices_t();
            }
            marshall_t.msg_i msg_iVar = this.body;
            if (msg_iVar != null) {
                iDecode += msg_iVar.decode(bArr, iDecode, i2 - iDecode);
            }
            return iDecode - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            int iEncode = i + 1;
            bArr[i] = (byte) (this.command + 16);
            marshall_t.msg_i msg_iVar = this.body;
            if (msg_iVar != null) {
                iEncode += msg_iVar.encode(bArr, iEncode, i2 - iEncode);
            }
            return iEncode - i;
        }
    }

    public static class msg_reset_t extends marshall_t.msg_i {
        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            return i - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            return i - i;
        }

        public msg_reset_t() {
        }

        public msg_reset_t(byte[] bArr, int i, int i2) {
            decode(bArr, i, i2);
        }
    }

    public static class msg_reader_t extends marshall_t.msg_i {
        public byte data;

        public msg_reader_t() {
        }

        public msg_reader_t(byte[] bArr, int i, int i2) {
            decode(bArr, i, i2);
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            this.data = bArr[i];
            return (i + 1) - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            bArr[i] = this.data;
            return (i + 1) - i;
        }
    }

    public static class msg_setup_config_data_t extends marshall_t.msg_i {
        public byte cols_on_display;
        public byte display_info;
        public byte feature_level;
        public byte rows_on_display;
        public byte sub_command;

        public msg_setup_config_data_t() {
        }

        public msg_setup_config_data_t(byte[] bArr, int i, int i2) {
            decode(bArr, i, i2);
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            this.sub_command = bArr[i];
            int i4 = i3 + 1;
            this.feature_level = bArr[i3];
            int i5 = i4 + 1;
            this.cols_on_display = bArr[i4];
            int i6 = i5 + 1;
            this.rows_on_display = bArr[i5];
            this.display_info = bArr[i6];
            return (i6 + 1) - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            this.sub_command = (byte) 0;
            int i3 = i + 1;
            bArr[i] = 0;
            int i4 = i3 + 1;
            bArr[i3] = this.feature_level;
            int i5 = i4 + 1;
            bArr[i4] = this.cols_on_display;
            int i6 = i5 + 1;
            bArr[i5] = this.rows_on_display;
            bArr[i6] = this.display_info;
            return (i6 + 1) - i;
        }
    }

    public static class msg_setup_max_min_prices_t extends marshall_t.msg_i {
        public short currency_code;
        public boolean expanded_mode;
        public short max_price;
        public short min_price;
        public byte sub_command;

        public msg_setup_max_min_prices_t() {
        }

        public msg_setup_max_min_prices_t(byte[] bArr, int i, int i2) {
            decode(bArr, i, i2);
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            this.sub_command = bArr[i];
            this.max_price = ByteArrayUtils.byteArrToShort(bArr, i3);
            int i4 = i3 + 2;
            this.min_price = ByteArrayUtils.byteArrToShort(bArr, i4);
            int i5 = i4 + 2;
            if (this.expanded_mode) {
                this.currency_code = ByteArrayUtils.byteArrToShort(bArr, i5);
            }
            return (i5 + 2) - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            this.sub_command = (byte) 1;
            int iShortToByteArray = ByteArrayUtils.shortToByteArray(bArr, i, this.max_price) + i;
            int iShortToByteArray2 = iShortToByteArray + ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray, this.min_price);
            if (this.expanded_mode) {
                iShortToByteArray2 = ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray2, this.currency_code);
            }
            return iShortToByteArray2 - i;
        }
    }

    public static class msg_poll_t extends marshall_t.msg_i {
        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            return i - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            return i - i;
        }

        public msg_poll_t() {
        }

        public msg_poll_t(byte[] bArr, int i, int i2) {
            decode(bArr, i, i2);
        }
    }

    public static class msg_vend_t extends marshall_t.msg_i {
        public short item_number;
        public short item_price;
        public int quantity;
        public byte status;
        public byte sub_command;
        public byte unit_of_measure;

        public msg_vend_t() {
        }

        public msg_vend_t(byte[] bArr, int i, int i2) {
            decode(bArr, i, i2);
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            this.sub_command = bArr[i];
            this.item_price = ByteArrayUtils.byteArrToShort(bArr, i3);
            int i4 = i3 + 2;
            this.item_number = ByteArrayUtils.byteArrToShort(bArr, i4);
            return (i4 + 2) - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            int iShortToByteArray;
            int iShortToByteArray2 = i + 1;
            byte b = this.sub_command;
            bArr[i] = b;
            if (b != -128) {
                if (b == 0) {
                    iShortToByteArray2 += ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray2, this.item_price);
                    iShortToByteArray = ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray2, this.item_number);
                } else if (b == 2) {
                    iShortToByteArray = ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray2, this.item_number);
                } else if (b == 5) {
                    iShortToByteArray2 += ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray2, this.item_price);
                    iShortToByteArray = ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray2, this.item_number);
                }
                iShortToByteArray2 += iShortToByteArray;
            } else {
                int i3 = iShortToByteArray2 + 1;
                bArr[iShortToByteArray2] = this.status;
                int iShortToByteArray3 = i3 + ByteArrayUtils.shortToByteArray(bArr, i3, this.item_price);
                int iShortToByteArray4 = iShortToByteArray3 + ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray3, this.item_number);
                int iIntToByteArray = iShortToByteArray4 + ByteArrayUtils.intToByteArray(bArr, iShortToByteArray4, this.quantity);
                iShortToByteArray2 = iIntToByteArray + 1;
                bArr[iIntToByteArray] = this.unit_of_measure;
            }
            return iShortToByteArray2 - i;
        }
    }

    public static class msg_ex_t extends marshall_t.msg_i {
        public byte sub_command;
        public byte type;

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            this.sub_command = bArr[i];
            this.type = bArr[i3];
            return (i3 + 1) - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            bArr[i] = this.sub_command;
            bArr[i3] = this.type;
            return (i3 + 1) - i;
        }
    }

    public static class msg_ex_selection_denied_t extends marshall_t.msg_i {
        public short item_number;
        public byte reason;
        public byte sub_command;

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            this.sub_command = bArr[i];
            this.item_number = ByteArrayUtils.byteArrToShort(bArr, i3);
            int i4 = i3 + 2;
            this.reason = bArr[i4];
            return (i4 + 1) - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            bArr[i] = this.sub_command;
            int iShortToByteArray = i3 + ByteArrayUtils.shortToByteArray(bArr, i3, this.item_number);
            bArr[iShortToByteArray] = this.reason;
            return (iShortToByteArray + 1) - i;
        }
    }

    public static class msg_ex_pay_t extends marshall_t.msg_i {
        public byte[] pp_number;
        public byte sub_command;

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            return i - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            bArr[i] = this.sub_command;
            return (i3 + ByteArrayUtils.subByteArrToByteArray(bArr, i3, this.pp_number)) - i;
        }
    }

    public static class msg_ex_multi_vend_req_t extends marshall_t.msg_i {
        public ArrayList<prod_item_t> list = new ArrayList<>();
        public byte sub_command;

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            return i - i;
        }

        private static class prod_item_t {
            public short code;
            public short price;
            public int qty;
            public byte unit;

            public prod_item_t(short s, short s2, int i, byte b) {
                this.code = s;
                this.price = s2;
                this.qty = i;
                this.unit = b;
            }
        }

        public void add(short s, short s2, int i, byte b) {
            this.list.add(new prod_item_t(s, s2, i, b));
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            bArr[i] = this.sub_command;
            for (prod_item_t prod_item_tVar : this.list) {
                int iShortToByteArray = i3 + ByteArrayUtils.shortToByteArray(bArr, i3, prod_item_tVar.code);
                int iShortToByteArray2 = iShortToByteArray + ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray, prod_item_tVar.price);
                int iIntToByteArray = iShortToByteArray2 + ByteArrayUtils.intToByteArray(bArr, iShortToByteArray2, prod_item_tVar.qty);
                bArr[iIntToByteArray] = prod_item_tVar.unit;
                i3 = iIntToByteArray + 1;
            }
            return i3 - i;
        }
    }

    public static class msg_ex_multi_vend_success_t extends marshall_t.msg_i {
        public ArrayList<prod_item_t> list = new ArrayList<>();
        public byte sub_command;

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            return i - i;
        }

        private static class prod_item_t {
            public short code;
            public short price;
            public int qty;
            public byte unit;

            public prod_item_t(short s, short s2, int i, byte b) {
                this.code = s;
                this.price = s2;
                this.qty = i;
                this.unit = b;
            }
        }

        public void add(short s, short s2, int i, byte b) {
            this.list.add(new prod_item_t(s, s2, i, b));
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            bArr[i] = this.sub_command;
            for (prod_item_t prod_item_tVar : this.list) {
                int iShortToByteArray = i3 + ByteArrayUtils.shortToByteArray(bArr, i3, prod_item_tVar.code);
                int iShortToByteArray2 = iShortToByteArray + ByteArrayUtils.shortToByteArray(bArr, iShortToByteArray, prod_item_tVar.price);
                int iIntToByteArray = iShortToByteArray2 + ByteArrayUtils.intToByteArray(bArr, iShortToByteArray2, prod_item_tVar.qty);
                bArr[iIntToByteArray] = prod_item_tVar.unit;
                i3 = iIntToByteArray + 1;
            }
            return i3 - i;
        }
    }

    public static class msg_expansion_t extends marshall_t.msg_i {
        public marshall_t.msg_i body;
        public byte sub_command;

        public msg_expansion_t() {
        }

        public msg_expansion_t(byte[] bArr, int i, int i2) {
            decode(bArr, i, i2);
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int decode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            byte b = bArr[i];
            this.sub_command = b;
            if (b == -6) {
                this.body = new mdb_t.msg_ftl_req_to_receive_t();
            } else if (b == -4) {
                this.body = new mdb_t.msg_ftl_send_block_t();
            } else if (b == -3) {
                this.body = new mdb_t.msg_ftl_ok_to_send_t();
            } else if (b == -2) {
                this.body = new mdb_t.msg_ftl_req_to_send_t();
            }
            return (i3 + this.body.decode(bArr, i3, i2 - i3)) - i;
        }

        @Override // com.bitmick.marshall.vmc.marshall_t.msg_i
        public int encode(byte[] bArr, int i, int i2) {
            int i3 = i + 1;
            bArr[i] = this.sub_command;
            return (i3 + this.body.encode(bArr, i3, i2 - i3)) - i;
        }
    }

    public static msg_mdb_req_t pool_req() {
        return new msg_mdb_req_t((byte) 2, new msg_poll_t());
    }

    public static msg_mdb_req_t reader_req(byte b) {
        msg_reader_t msg_reader_tVar = new msg_reader_t();
        msg_reader_tVar.data = b;
        return new msg_mdb_req_t((byte) 4, msg_reader_tVar);
    }

    public static msg_mdb_req_t vend_req(byte b, short s, short s2) {
        msg_vend_t msg_vend_tVar = new msg_vend_t();
        msg_vend_tVar.sub_command = b;
        msg_vend_tVar.item_price = s;
        msg_vend_tVar.item_number = s2;
        return new msg_mdb_req_t((byte) 3, msg_vend_tVar);
    }

    public static msg_mdb_req_t mdb_ex(byte b, byte b2) {
        msg_ex_t msg_ex_tVar = new msg_ex_t();
        msg_ex_tVar.sub_command = b;
        msg_ex_tVar.type = b2;
        return new msg_mdb_req_t((byte) 3, msg_ex_tVar);
    }

    public static msg_mdb_req_t mdb_vend_selection_denied(byte b, short s, byte b2) {
        msg_ex_selection_denied_t msg_ex_selection_denied_tVar = new msg_ex_selection_denied_t();
        msg_ex_selection_denied_tVar.sub_command = b;
        msg_ex_selection_denied_tVar.item_number = s;
        msg_ex_selection_denied_tVar.reason = b2;
        return new msg_mdb_req_t((byte) 3, msg_ex_selection_denied_tVar);
    }

    public static msg_mdb_req_t expansion_ftl_req_to_send(byte b, byte b2, byte b3, byte b4, byte b5) {
        mdb_t.msg_ftl_req_to_send_t msg_ftl_req_to_send_tVar = new mdb_t.msg_ftl_req_to_send_t();
        msg_ftl_req_to_send_tVar.src = b;
        msg_ftl_req_to_send_tVar.dst = b2;
        msg_ftl_req_to_send_tVar.file_id = b3;
        msg_ftl_req_to_send_tVar.len = b4;
        msg_ftl_req_to_send_tVar.control = b5;
        msg_expansion_t msg_expansion_tVar = new msg_expansion_t();
        msg_expansion_tVar.sub_command = (byte) -2;
        msg_expansion_tVar.body = msg_ftl_req_to_send_tVar;
        return new msg_mdb_req_t((byte) 7, msg_ftl_req_to_send_tVar);
    }

    public static msg_mdb_req_t expansion_ftl_ok_to_send(byte b, byte b2) {
        mdb_t.msg_ftl_ok_to_send_t msg_ftl_ok_to_send_tVar = new mdb_t.msg_ftl_ok_to_send_t();
        msg_ftl_ok_to_send_tVar.src = b;
        msg_ftl_ok_to_send_tVar.dst = b2;
        msg_expansion_t msg_expansion_tVar = new msg_expansion_t();
        msg_expansion_tVar.sub_command = (byte) -3;
        msg_expansion_tVar.body = msg_ftl_ok_to_send_tVar;
        return new msg_mdb_req_t((byte) 7, msg_ftl_ok_to_send_tVar);
    }

    public static msg_mdb_req_t expansion_ftl_send_block(byte b, byte b2, byte[] bArr) {
        mdb_t.msg_ftl_send_block_t msg_ftl_send_block_tVar = new mdb_t.msg_ftl_send_block_t();
        msg_ftl_send_block_tVar.dst = b;
        msg_ftl_send_block_tVar.block_num = b2;
        msg_ftl_send_block_tVar.data = bArr;
        msg_expansion_t msg_expansion_tVar = new msg_expansion_t();
        msg_expansion_tVar.sub_command = (byte) -4;
        msg_expansion_tVar.body = msg_ftl_send_block_tVar;
        return new msg_mdb_req_t((byte) 7, msg_expansion_tVar);
    }

    public static msg_mdb_req_t expansion_ftl_retry_deny(byte b, byte b2, byte b3) {
        mdb_t.msg_ftl_retry_deny_t msg_ftl_retry_deny_tVar = new mdb_t.msg_ftl_retry_deny_t();
        msg_ftl_retry_deny_tVar.src = b;
        msg_ftl_retry_deny_tVar.dst = b2;
        msg_ftl_retry_deny_tVar.retry_delay = b3;
        msg_expansion_t msg_expansion_tVar = new msg_expansion_t();
        msg_expansion_tVar.sub_command = (byte) -5;
        msg_expansion_tVar.body = msg_expansion_tVar;
        return new msg_mdb_req_t((byte) 7, msg_ftl_retry_deny_tVar);
    }

    public static msg_mdb_req_t expansion_ftl_req_to_receive(byte b, byte b2, byte b3, byte b4, byte b5) {
        mdb_t.msg_ftl_req_to_receive_t msg_ftl_req_to_receive_tVar = new mdb_t.msg_ftl_req_to_receive_t();
        msg_ftl_req_to_receive_tVar.src = b;
        msg_ftl_req_to_receive_tVar.dst = b2;
        msg_ftl_req_to_receive_tVar.file_id = b3;
        msg_ftl_req_to_receive_tVar.max_len = b4;
        msg_ftl_req_to_receive_tVar.control = b5;
        msg_expansion_t msg_expansion_tVar = new msg_expansion_t();
        msg_expansion_tVar.sub_command = (byte) -6;
        msg_expansion_tVar.body = msg_ftl_req_to_receive_tVar;
        return new msg_mdb_req_t((byte) 7, msg_expansion_tVar);
    }
}

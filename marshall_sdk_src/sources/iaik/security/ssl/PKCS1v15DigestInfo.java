package iaik.security.ssl;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class PKCS1v15DigestInfo {
    static final byte[][] a;
    static final byte[][] b;
    private static final byte[][] c;
    private static final byte[][] d;
    private static final byte[][] e;
    private static final byte[][] f;
    private static final HashMap g;

    static {
        byte[][] bArr = {new byte[]{48, 32, 48, 12, 6, 8, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, -122, 72, -122, -9, 13, 2, 5, 5, 0, 4, 16}, new byte[]{48, 30, 48, 10, 6, 8, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, -122, 72, -122, -9, 13, 2, 5, 4, 16}};
        c = bArr;
        byte[][] bArr2 = {new byte[]{48, 33, 48, 9, 6, 5, 43, 14, 3, 2, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, 5, 0, 4, 20}, new byte[]{48, 31, 48, 7, 6, 5, 43, 14, 3, 2, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, 4, 20}};
        d = bArr2;
        byte[][] bArr3 = {new byte[]{48, 45, 48, 13, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 4, 5, 0, 4, 28}, new byte[]{48, 43, 48, 11, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 4, 4, 28}};
        e = bArr3;
        byte[][] bArr4 = {new byte[]{48, 49, 48, 13, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 1, 5, 0, 4, 32}, new byte[]{48, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, 48, 11, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 1, 4, 32}};
        f = bArr4;
        byte[][] bArr5 = {new byte[]{48, marshall_t.status_vpos_processing_error, 48, 13, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 2, 5, 0, 4, 48}, new byte[]{48, 63, 48, 11, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 2, 4, 48}};
        a = bArr5;
        byte[][] bArr6 = {new byte[]{48, 81, 48, 13, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 3, 5, 0, 4, 64}, new byte[]{48, marshall_t.status_vpos_please_insert_card, 48, 11, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 3, 4, 64}};
        b = bArr6;
        HashMap map = new HashMap(6);
        g = map;
        map.put(SecurityProvider.ALG_DIGEST_MD5, bArr);
        map.put("SHA", bArr2);
        map.put("SHA1", bArr2);
        map.put(SecurityProvider.ALG_DIGEST_SHA224, bArr3);
        map.put(SecurityProvider.ALG_DIGEST_SHA256, bArr4);
        map.put(SecurityProvider.ALG_DIGEST_SHA384, bArr5);
        map.put(SecurityProvider.ALG_DIGEST_SHA512, bArr6);
    }

    static byte[] a(String str, byte[] bArr, boolean z) {
        byte[][] bArr2 = (byte[][]) g.get(str);
        if (bArr2 == null) {
            return null;
        }
        byte[] bArr3 = bArr2[!z ? 1 : 0];
        int length = bArr3.length;
        int length2 = bArr.length;
        byte[] bArr4 = new byte[length + length2];
        System.arraycopy(bArr3, 0, bArr4, 0, length);
        System.arraycopy(bArr, 0, bArr4, length, length2);
        return bArr4;
    }
}

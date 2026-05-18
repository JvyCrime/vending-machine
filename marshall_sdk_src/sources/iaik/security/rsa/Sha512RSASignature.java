package iaik.security.rsa;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_t;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.SHA512;

/* JADX INFO: loaded from: classes.dex */
public class Sha512RSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.sha512.clone();
    static final byte[][] b = {new byte[]{48, 81, 48, 13, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 3, 5, 0, 4, 64}, new byte[]{48, marshall_t.status_vpos_please_insert_card, 48, 11, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 3, 4, 64}};

    public Sha512RSASignature() {
        super(a, new SHA512(), b);
    }
}

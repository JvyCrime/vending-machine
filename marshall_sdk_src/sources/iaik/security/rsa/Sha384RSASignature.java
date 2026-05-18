package iaik.security.rsa;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_t;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.SHA384;

/* JADX INFO: loaded from: classes.dex */
public class Sha384RSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.sha384.clone();
    static final byte[][] b = {new byte[]{48, marshall_t.status_vpos_processing_error, 48, 13, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 2, 5, 0, 4, 48}, new byte[]{48, 63, 48, 11, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 2, 4, 48}};

    public Sha384RSASignature() {
        super(a, new SHA384(), b);
    }
}

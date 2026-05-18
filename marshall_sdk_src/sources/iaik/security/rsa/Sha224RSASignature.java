package iaik.security.rsa;

import com.bitmick.marshall.vmc.marshall_t;
import com.bitmick.marshall.vmc.mdb_t;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.SHA224;

/* JADX INFO: loaded from: classes.dex */
public class Sha224RSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.sha224.clone();
    static final byte[][] b = {new byte[]{48, 45, 48, 13, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 4, 5, 0, 4, 28}, new byte[]{48, 43, 48, 11, 6, 9, mdb_t.mdb_dev_addr_cashless_b, -122, 72, 1, marshall_t.marshalll_display_control_button_id_menu, 3, 4, 2, 4, 4, 28}};

    public Sha224RSASignature() {
        super(a, new SHA224(), b);
    }
}

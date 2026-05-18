package iaik.security.mac;

import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import iaik.security.cipher.VarLengthKeyGenerator;

/* JADX INFO: loaded from: classes.dex */
public class HMacSha224KeyGenerator extends VarLengthKeyGenerator {
    public HMacSha224KeyGenerator() {
        super("HmacSHA224", DataTypeTag.CONSTRUCTIVE, -1, 512);
    }
}

package iaik.security.dsa;

import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import iaik.security.md.SHA224;

/* JADX INFO: loaded from: classes.dex */
public class SHA224withDSAParameterGenerator extends SHA2withDSAParameterGenerator {
    public SHA224withDSAParameterGenerator() {
        super(new SHA224(), DataTypeTag.CONSTRUCTIVE);
    }
}

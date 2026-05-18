package iaik.security.dsa;

import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import iaik.security.ssl.SecurityProvider;
import java.security.InvalidParameterException;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class SHA224withDSAKeyPairGenerator extends a {
    public SHA224withDSAKeyPairGenerator() {
        super(SecurityProvider.ALG_DIGEST_SHA224, DataTypeTag.CONSTRUCTIVE);
    }

    @Override // iaik.security.dsa.DSAKeyPairGenerator, java.security.KeyPairGenerator
    public void initialize(int i) {
        initialize(i, (SecureRandom) null);
    }

    @Override // iaik.security.dsa.DSAKeyPairGenerator, java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(int i, SecureRandom secureRandom) {
        if (i == 1024 || i == 2048) {
            this.a = secureRandom;
            this.b = i;
            this.c = null;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid modulus length (");
            stringBuffer.append(i);
            stringBuffer.append("). Must be 1024 or 2048!");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    @Override // iaik.security.dsa.DSAKeyPairGenerator, java.security.interfaces.DSAKeyPairGenerator
    public void initialize(int i, boolean z, SecureRandom secureRandom) throws InvalidParameterException {
        if (i == 1024 || i == 2048) {
            this.a = secureRandom;
            this.b = i;
            this.d = z;
            this.c = null;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid modulus length (");
        stringBuffer.append(i);
        stringBuffer.append("). Must be 1024 or 2048!");
        throw new IllegalArgumentException(stringBuffer.toString());
    }
}

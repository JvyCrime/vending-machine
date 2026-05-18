package iaik.security.cipher;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class CAST128KeyWrap extends AbstractC0023b {
    static Class j;
    static Class k;
    private CAST128WrapParameterSpec l;

    public CAST128KeyWrap() {
        super(new m(), "CAST128WrapCAST128", -1, (byte[]) CMS_KEY_WRAP_IV.clone());
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.security.cipher.AbstractC0023b, iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        try {
            if (this.l == null) {
                AlgorithmParameters algorithmParametersE = this.a.e();
                Class clsClass$ = k;
                if (clsClass$ == null) {
                    clsClass$ = class$("iaik.security.cipher.CAST128ParameterSpec");
                    k = clsClass$;
                }
                this.l = new CAST128WrapParameterSpec(((CAST128ParameterSpec) algorithmParametersE.getParameterSpec(clsClass$)).getKeyLength());
            }
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("CAST128WrapCAST128", "IAIK");
            algorithmParameters.init(this.l);
            return algorithmParameters;
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameters != null) {
            try {
                Class clsClass$ = j;
                if (clsClass$ == null) {
                    clsClass$ = class$("iaik.security.cipher.CAST128WrapParameterSpec");
                    j = clsClass$;
                }
                this.l = (CAST128WrapParameterSpec) algorithmParameters.getParameterSpec(clsClass$);
            } catch (InvalidParameterSpecException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid parameters: ");
                stringBuffer.append(e.getMessage());
                throw new InvalidAlgorithmParameterException(stringBuffer.toString());
            }
        }
        engineInit(i, key, this.l, secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null) {
            engineInit(i, key, secureRandom);
            engineGetParameters();
        } else {
            if (!(algorithmParameterSpec instanceof CAST128WrapParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Invalid Parameters; expected CAST128WrapParameterSpec!");
            }
            this.kek = key;
            this.random = secureRandom;
            this.d = a(i);
            this.l = (CAST128WrapParameterSpec) algorithmParameterSpec;
            byte[] bArr = (byte[]) this.keyWrapIV.clone();
            if (this.d == 1) {
                getRandom().nextBytes(bArr);
            }
            super.engineInit(i, key, new CAST128ParameterSpec(this.l.getKeyLength(), bArr), secureRandom);
        }
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected void engineInit(int i, Key key, byte[] bArr, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        super.engineInit(i, key, new CAST128ParameterSpec(this.l.getKeyLength(), bArr), secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected Key finishUnWrap(byte[] bArr, String str, int i) throws InvalidKeyException {
        try {
            return new SecretKeySpec(decomposeLCEKPAD(bArr), "CAST128");
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not unwrap key: ");
            stringBuffer.append(e.getMessage());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected byte[] prepareWrap(Key key) throws InvalidKeyException {
        return computeLCEKPAD(key);
    }
}

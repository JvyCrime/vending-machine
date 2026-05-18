package iaik.security.cipher;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class RC2KeyWrap extends AbstractC0023b {
    static Class j;
    static Class k;
    private RC2WrapParameterSpec l;

    public RC2KeyWrap() {
        super(new D(), "RC2WrapRC2", -1, (byte[]) CMS_KEY_WRAP_IV.clone());
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
                    clsClass$ = class$("javax.crypto.spec.RC2ParameterSpec");
                    k = clsClass$;
                }
                this.l = new RC2WrapParameterSpec(((RC2ParameterSpec) algorithmParametersE.getParameterSpec(clsClass$)).getEffectiveKeyBits());
            }
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("RC2WrapRC2", "IAIK");
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
                    clsClass$ = class$("iaik.security.cipher.RC2WrapParameterSpec");
                    j = clsClass$;
                }
                this.l = (RC2WrapParameterSpec) algorithmParameters.getParameterSpec(clsClass$);
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
            if (!(algorithmParameterSpec instanceof RC2WrapParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Invalid Parameters; expected RC2WrapParameterSpec!");
            }
            this.kek = key;
            this.random = secureRandom;
            this.d = a(i);
            this.l = (RC2WrapParameterSpec) algorithmParameterSpec;
            byte[] bArr = (byte[]) this.keyWrapIV.clone();
            if (this.d == 1) {
                getRandom().nextBytes(bArr);
            }
            super.engineInit(i, key, new RC2ParameterSpec(this.l.getEffectiveKeyBits(), bArr), secureRandom);
        }
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected void engineInit(int i, Key key, byte[] bArr, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.l != null) {
            super.engineInit(i, key, new RC2ParameterSpec(this.l.getEffectiveKeyBits(), bArr), secureRandom);
        } else {
            super.engineInit(i, key, new IvParameterSpec(bArr), secureRandom);
            engineGetParameters();
        }
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected Key finishUnWrap(byte[] bArr, String str, int i) throws InvalidKeyException {
        try {
            return new SecretKeySpec(decomposeLCEKPAD(bArr), "RC2");
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
